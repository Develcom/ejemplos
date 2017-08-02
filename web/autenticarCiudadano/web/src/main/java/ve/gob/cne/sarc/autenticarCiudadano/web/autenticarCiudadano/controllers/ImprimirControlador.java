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

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ve.gob.cne.sarc.autenticarCiudadano.utils.ProcesadorListas;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
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
public class ImprimirControlador {
	@Autowired
	ServletContext context;
	Logger log=Logger.getLogger(getClass().getName());
	
	@Autowired
	private AdministradorPropiedades properties;

	private String DIRECCION_PLANTILLA = "";

	public static final String DIRECCION_MODAL = "/resources/pages/templates/template_nacimiento.html";
	public static final String DIRECCION_REVISION_REGISTRADOR = "/resources/pages/templates/imprimir/ficha_tecnica.html";
	public static final String[] POSIBLES_DECLARANTES = new String[]{"MAD","PAD"};
	public static final String[] PREFIJOS = new String[]{"TDM","TDP"};
	public static final String RUTA_PDF = "C:/tmp/Declaracion_jurada_";
	
	@Autowired 
    private ParticipanteServicioCliente clienteParticipante;
    
    @Autowired 
    private CatalogoServicioCliente clienteCatalogo;
    
    @Autowired 
    private SolicitudServicioCliente cliente;
    
    @Autowired
    private SolicitudServicioCliente solicitudServicioCliente;
	
	@RequestMapping(value="/infoImpresion", method = RequestMethod.POST)
	public @ResponseBody String  infoDeclaracion(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String token=request.getHeader("Authorization");
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		ObjectMapper mapper= new ObjectMapper();
		Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class);
		log.info("*************Numero de solicitud: " + data.get("id"));
		List<DeclaracionJurada> listaDeclaraciones = clienteParticipante.consultarDeclaracionJurada(data.get("id"), DroolsControlador.tokenAutenticar);
		DeclaracionJurada declaracionJurada = listaDeclaraciones.get(0);
		//Busca los participantes presentes en la declaracion jurada
		List<Participante> listaParticipantes = declaracionJurada.getParticipantes();
		//Buscala lista de posibles declarantes ********Hasta el momento de desarrollar este codigo
		//solo se connocen dos posibles: MAD y PAD
		List<String> declarantes = new ArrayList<>();
		for (int i = 0; i < POSIBLES_DECLARANTES.length; i++) {
			Participante p = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, POSIBLES_DECLARANTES[i]);
			if (p != null) {
				declarantes.add(PREFIJOS[i]+1);
				declarantes.add(PREFIJOS[i]+2);
			}
		}
		data.put("declarantes", mapper.writeValueAsString(declarantes));
		JSONObject modelo = new JSONObject(mapper.writeValueAsString(data));
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", modelo);
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
	 * @throws GenericException 
	 */
	@RequestMapping(value="/procesarImpresion", method = RequestMethod.POST)
	public @ResponseBody String  procesarVerificacion(HttpServletRequest request) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException, GenericException{
		String token=request.getHeader("Authorization");
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		String dsData = request.getHeader("datos");
		JSONObject modelo = new JSONObject(dsData);
		String codigoDeclarante =modelo.getString("codigoDeclarante");
		JSONObject mDeclaracion = generarPdfDeclaracion(modelo.getString("id"), codigoDeclarante);	
		
		TipoParticipante tipoParticipante = clienteCatalogo.consultarTipoParticipantePorCodigo(modelo.getString("codigoDeclarante"), false, DroolsControlador.tokenAutenticar); 
		modelo.put("titulo", tipoParticipante.getNombre());
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", modelo);
		respuesta.put("vista",  mDeclaracion.get("vista"));
			return respuesta.toString();
	}
	


	public JSONObject generarPdfDeclaracion(String nroSolicitud, String codigoTestigo) throws JRException, JSONException, IOException, GenericException{
		String dp = properties.getProperty("plantilla.jasper.report.generico.declaracionJurada");		
		//String realPath = context.getRealPath(dp);
		//numero de testigos procesados
		List<DeclaracionJurada> listaDeclaraciones = clienteParticipante.consultarDeclaracionJurada(nroSolicitud, DroolsControlador.tokenAutenticar);
		DeclaracionJurada declaracionJurada = listaDeclaraciones.get(0);
		//Busca los participantes presentes en la declaracion jurada
		List<Participante> listaParticipantes = declaracionJurada.getParticipantes();
		String codigoDeclarante = "TDM1".equals(codigoTestigo) || "TDM2".equals(codigoTestigo) ? "MAD": "PAD";
		Participante testigo = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, codigoTestigo);
		Participante declarante = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, codigoDeclarante);
		
		int nSolicitud = (int) (10000*Math.random());
		//windows
		String realPathFdf = RUTA_PDF + testigo.getRol() + nSolicitud + ".pdf";
		log.info("Path: "+realPathFdf);
		log.info("Ruta del contexto: "+context.getRealPath("/"));
        JasperReport jasperReport = JasperCompileManager.compileReport(dp);
        Map<String,Object> parameters = new HashMap<String,Object>();
        Date fecha = declaracionJurada.getFechaDeclaracion();

        parameters.put("dia", GenerarActas.obtenerParametroDeFecha("DIA", fecha));
        parameters.put("mes", GenerarActas.obtenerParametroDeFecha("NUM_MES", fecha));
        parameters.put("ano", GenerarActas.obtenerParametroDeFecha("ANIO", fecha));
        
        cargarDatosDeclarante(parameters, declarante);
        cargarDatosParticipante(parameters, testigo);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,  new JREmptyDataSource());      
        JasperExportManager.exportReportToPdfFile(jasperPrint, realPathFdf);
		String template="<embed width='850' height='550' src='"+ "/web-generales/tmp/" + "Declaracion_jurada_" + testigo.getRol() + nSolicitud + ".pdf' type='application/pdf'></embed>";

		String vista = Util.leerArchivo(context.getRealPath(DIRECCION_REVISION_REGISTRADOR));
		vista = vista.replace("ARCHIVOPDF", template);
		JSONObject formulario=new JSONObject();
		formulario.put("vista", vista);
		return formulario;
	}

	public void cargarDatosDeclarante(Map<String,Object>parameters, Participante padre){
        String nombreDeclarado=padre.getPrimerNombre()+(padre.getSegundoNombre()==null ? " " :" "+padre.getSegundoNombre()+" ")
				+padre.getPrimerApellido()+(padre.getSegundoApellido()==null ? " " :" "+padre.getSegundoApellido());
        parameters.put("declarado", nombreDeclarado);
        Direccion mDireccion = ProcesadorListas.getDireccionPorTipo(padre, "RES");
        if(mDireccion == null)
        	return;
        parameters.put("direcciondeclarado", padre.getDireccion().get(1).getUbicacion());
        log.info("**************Direccion del declarante: " + padre.getDireccion().get(0).getUbicacion());
        parameters.put("estadodeclarado", padre.getDireccion().get(1).getEstado().getNombre());
        parameters.put("municipiodeclarado", padre.getDireccion().get(1).getMunicipio().getNombre());
        parameters.put("parroquiadeclarado", padre.getDireccion().get(1).getParroquia().getNombre());
        if(padre.getDocumentoIdentidad()!=null)
        	parameters.put("numdoc",padre.getDocumentoIdentidad().get(0).getNumero());
	}
	public void cargarDatosParticipante(Map<String,Object>parameters, Participante participante){
        String nombreDeclarante = participante.getPrimerNombre() + (participante.getSegundoNombre()==null ? " " :" "+participante.getSegundoNombre()+" ")
				+participante.getPrimerApellido()+(participante.getSegundoApellido()==null ? " " :" "+participante.getSegundoApellido());
        parameters.put("declarante", nombreDeclarante);
        parameters.put("ceduladec", participante.getDocumentoIdentidad().get(0).getNumero());
        Direccion mDireccion = ProcesadorListas.getDireccionPorTipo(participante, "RES");
        if(mDireccion == null)
        	return;
        parameters.put("direcciondec", participante.getDireccion().get(1).getUbicacion());
        log.info("**************Direccion del testigo: " + participante.getDireccion().get(1).getUbicacion());
        parameters.put("estadodec", participante.getDireccion().get(1).getEstado().getNombre());
        parameters.put("municipiodec", participante.getDireccion().get(1).getMunicipio().getNombre());
        parameters.put("parroquiadec", participante.getDireccion().get(1).getParroquia().getNombre());
	} 
	  
		@RequestMapping(value="/actualizarAbierta", method = RequestMethod.POST)
		public @ResponseBody String  actualizarSolicitud(HttpServletRequest request) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException{
			ObjectMapper mapper= new ObjectMapper();
			String token=request.getHeader("Authorization");
			if(token==null){
				throw new GeneralException("seguridad_no_token");
			}	
			Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class);
			String ruta = "/resources/pages/success/satisfactorio.html";
			Solicitud solicitud = cliente.consultarDetalleSolicitud(data.get("id"), DroolsControlador.tokenAutenticar);
			solicitud.setEstadoSolicitud("AB");
			solicitud.setMotivoCambioEstado("Autenticacion de participantes");
			Solicitud sol = cliente.actualizarEstadoSolicitud(solicitud, DroolsControlador.tokenAutenticar);
			String vista = Util.leerArchivo(context.getRealPath(ruta));
			JSONObject modelo = new JSONObject(mapper.writeValueAsString(data));
			modelo.put("mensaje", "Se ha autenticado a los ciudadanos satisfactoriamente");
			JSONObject respuesta = new JSONObject();
			respuesta.put("modelo", modelo);
			respuesta.put("vista", vista);
			return respuesta.toString();
		}	  
}