package ve.gob.cne.sarc.autenticarCiudadano.web.autenticarCiudadano.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ve.gob.cne.sarc.autenticarCiudadano.utils.ProcesadorListas;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.reporte.GenerarActas;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.autenticarCiudadano.web.autenticarCiudadano.controllers.DroolsControlador;

@Controller
public class CorreccionControlador {
	@Autowired
	ServletContext context;
	Logger log=Logger.getLogger(getClass().getName());
	
	@Autowired
	private AdministradorPropiedades properties;

	private String DIRECCION_PLANTILLA = "";

	public static final String DIRECCION_MODAL = "/resources/pages/templates/template.html";
	public static final String DIRECCION_REVISION_REGISTRADOR = "/resources/pages/templates/verificacion_registrador.html";
	public static final String PLANTILLA_DECLARANTES = "/resources/js/json/RNACI/FORMULARIO_COR/formulario_padres_sin_id.json";
	public static final String PLANTILLA_TESTIGOS = "/resources/js/json/RNACI/FORMULARIO_COR/formulario_declaracion.json";
	public static final String[] POSIBLES_DECLARANTES = new String[]{"MAD","PAD"};
	public static final String[] PREFIJOS = new String[]{"TDM","TDP"};

	@Autowired 
    private ParticipanteServicioCliente clienteParticipante;
	
	@Autowired 
    private SolicitudServicioCliente clienteSolicitud; 
    
    @Autowired 
    private CatalogoServicioCliente catalogoServicioCliente;
    
    @Autowired
    private SolicitudServicioCliente solicitudServicioCliente;
    
    Solicitud solicitud = new Solicitud();
    
	/**
	 * <p>Presenta el modal en pantalla</p>
	 * @param session
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/iniciarCorreccion", method = RequestMethod.POST)
	public @ResponseBody String  iniciarCorreccion(@RequestBody String data) throws Exception {
		String htmlValido = Util.leerArchivo(context.getRealPath(DIRECCION_MODAL));
		return htmlValido;
	}
	
	@RequestMapping(value="/infoDeclaracion", method = RequestMethod.POST)
	public @ResponseBody String  infoDeclaracion(@RequestBody String datos) throws Exception {
		ObjectMapper mapper= new ObjectMapper();
		JSONObject modelo = new JSONObject(datos);
		List<DeclaracionJurada> listaDeclaraciones = clienteParticipante.consultarDeclaracionJurada(modelo.getString("id"), DroolsControlador.tokenAutenticar);
		DeclaracionJurada declaracionJurada = listaDeclaraciones.get(0);
		//Busca los participantes presentes en la declaracion jurada
		List<Participante> listaParticipantes = declaracionJurada.getParticipantes();
		//Buscala lista de posibles declarantes
		String codigoDeclarante = null;
		List<String> declarantes = new ArrayList<>();
		for (int i = 0; i < POSIBLES_DECLARANTES.length; i++) {
			Participante p = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, POSIBLES_DECLARANTES[i]);
			if (p != null) {
				declarantes.add(POSIBLES_DECLARANTES[i]);
				declarantes.add(PREFIJOS[i] + 1);
				declarantes.add(PREFIJOS[i] + 2);
			}
		}
		Solicitud solicitud = clienteSolicitud.consultarDetalleSolicitud(modelo.getString("id"), DroolsControlador.tokenAutenticar);
        String formulario = Util.leerArchivo(context.getRealPath("/resources/js/json/RNACI/FORMULARIO_COR/observaciones.json"));
        JSONObject jformulario = new JSONObject(formulario);
        modelo.put("declarantes", mapper.writeValueAsString(declarantes));
		modelo.put("observaciones", solicitud.getMotivoCambioEstado());
		modelo.put("titulo", "Observaciones");
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", modelo);
		respuesta.put("vista",jformulario.getJSONArray("fields"));
		return respuesta.toString();
	}

	
	
	/**
	 * <p>Consulta los datos de la declaraci&oacute;n jurada asociada a la solicitud</p>
	 * @param data N&uacute;mero de solicitud
	 * @param sesion
	 * @return
	 * @throws GeneralException 
	 * @throws JSONException 
	 * @throws JRException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value="/procesarCorreccion", method = RequestMethod.POST)
	public @ResponseBody String  procesarVerificacion(@RequestBody String data) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException{
		JSONObject modelo = new JSONObject(data);
		String codigoDeclarante =modelo.getString("codigoDeclarante");
		JSONObject mDeclaracion = generarFormularioDeclaracion(modelo.getString("id"), codigoDeclarante);
		JSONObject vista = mDeclaracion.getJSONObject("formulario");
		TipoParticipante tipoParticipante = catalogoServicioCliente.consultarTipoParticipantePorCodigo(modelo.get("codigoDeclarante").toString(), false, DroolsControlador.tokenAutenticar);
		
		modelo.put("titulo", "Datos " + ("MAD".equals(tipoParticipante.getCodigo())? "de la " : "del ") + tipoParticipante.getNombre());
		modelo.put(codigoDeclarante, mDeclaracion.get(codigoDeclarante));
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", modelo);
		respuesta.put("vista", vista.getJSONArray("fields"));
		return respuesta.toString();
	}

	public JSONObject generarFormularioDeclaracion(String nroSolicitud, String codigoDeclarante) throws JRException, JSONException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		//numero de testigos procesados
		List<DeclaracionJurada> listaDeclaraciones = clienteParticipante.consultarDeclaracionJurada(nroSolicitud, DroolsControlador.tokenAutenticar);
		DeclaracionJurada declaracionJurada = listaDeclaraciones.get(0);
		//Busca los participantes presentes en la declaracion jurada
		List<Participante> listaParticipantes = declaracionJurada.getParticipantes();
		Participante declarante = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, codigoDeclarante);
		
        Date fecha = declaracionJurada.getFechaDeclaracion();
        int indice =-1;
        for(String codigo : POSIBLES_DECLARANTES){
        	if(codigo.equals(codigoDeclarante))
        		indice++;
        }
        String formulario = Util.leerArchivo(context.getRealPath( indice == -1 ? PLANTILLA_TESTIGOS : PLANTILLA_DECLARANTES));
        formulario = formulario.replace("ROL", codigoDeclarante);
		log.info("***************FORMULARIO ORIGINAL**"+formulario);
        
		TipoParticipante tipoParticipante = catalogoServicioCliente.consultarTipoParticipantePorCodigo(codigoDeclarante, false, DroolsControlador.tokenAutenticar);
        formulario = formulario.replace("PARENTESCO", tipoParticipante.getNombre());
        formulario = formulario.replace("GENERO", "MAD".equals(codigoDeclarante) ? "de la" : "del");
        
		JSONObject objetoFormly = new JSONObject(formulario);		        
        Map<String,String> datosDecla = cargarDatosDeclarante(declarante);
        JSONObject datosDeclarante = new JSONObject(mapper.writeValueAsString(datosDecla));
        datosDeclarante.put("dia", GenerarActas.obtenerParametroDeFecha("DIA", fecha));
        datosDeclarante.put("mes", GenerarActas.obtenerParametroDeFecha("NUM_MES", fecha));
        datosDeclarante.put("ano", GenerarActas.obtenerParametroDeFecha("ANIO", fecha));
        JSONObject respuesta = new JSONObject();
        respuesta.put("formulario", objetoFormly);
        respuesta.put(codigoDeclarante, datosDeclarante);
		return respuesta;
	}

	public Map<String,String> cargarDatosDeclarante(Participante padre) throws JSONException{
		Map<String,String> parameters = new HashMap<String,String>();
        parameters.put("primerNombre", padre.getPrimerNombre());
        parameters.put("sgundoNombre", padre.getSegundoNombre());
        parameters.put("primerApellido", padre.getPrimerApellido());
        parameters.put("segundoApellido", padre.getSegundoApellido());
        if(padre.getDireccion() != null && padre.getDireccion().size()>1){
        	parameters.put("Direccion", padre.getDireccion().get(1).getUbicacion());
        	log.info("**************Direccion del declarante: " + padre.getDireccion().get(1).getUbicacion());
        	parameters.put("Estado", padre.getDireccion().get(1).getEstado().getCodigo());
        	parameters.put("Municipio", padre.getDireccion().get(1).getMunicipio().getCodigo());
        	parameters.put("Parroquia", padre.getDireccion().get(1).getParroquia().getCodigo());
        	parameters.put("Pais", padre.getDireccion().get(1).getPais().getCodigo());
        }
        if(padre.getDocumentoIdentidad()!=null)
        	parameters.put("documentoIdentidad",padre.getDocumentoIdentidad().get(0).getNumero());
        return parameters;
	}
	@RequestMapping(value="/enviarAverificacion", method = RequestMethod.POST)
	public @ResponseBody String  enviarAverificacion(@RequestBody String data) throws Exception {
		JSONObject modelo = new JSONObject(data);
		String sParticipantes = modelo.getString("declarantes");
		sParticipantes = sParticipantes.replace("\"", "");
		sParticipantes = sParticipantes.replace("[", "");
		sParticipantes = sParticipantes.replace("]", "");
		String[] codigosDeclarantes = sParticipantes.split(",");
		solicitud.setNumeroSolicitud(modelo.getString("id"));
		solicitud.setEstadoSolicitud("PVA");
		solicitud.setMotivoCambioEstado("Autenticacion de participantes");
		Solicitud sol = clienteSolicitud.actualizarEstadoSolicitud(solicitud, DroolsControlador.tokenAutenticar);
		String ruta = "/resources/js/json/satisfactorio/satisfactorio.json";
		String vista = Util.leerArchivo(context.getRealPath(ruta));
		JSONObject objetoFormly = new JSONObject(vista);		        
		modelo.put("mensaje", "Ha finalizado el proceso de verificacion");
		JSONObject respuesta = new JSONObject();
		respuesta.put("vista", objetoFormly.getJSONArray("fields"));
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
}
