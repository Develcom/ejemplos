package ve.gob.cne.sarc.autenticarCiudadano.web.autenticarCiudadano.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ve.gob.cne.sarc.autenticarCiudadano.utils.ProcesadorListas;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.reporte.GenerarActas;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.autenticarCiudadano.web.autenticarCiudadano.controllers.DroolsControlador;

@Controller
public class VerificacionControlador {
	
	@Autowired
	ServletContext context;
	Logger log=Logger.getLogger(getClass().getName());
	
	@Autowired
	private AdministradorPropiedades properties;
	
	@Autowired
    private FuncionarioServicioCliente funcionarioServicioCliente;
    
    @Autowired
    private SeguridadServicioCliente seguridadServicioCliente;
	
	private String DIRECCION_PLANTILLA = "";
	private static String tokenAutenticar;
	//public static final String DIRECCION_MODAL = "/resources/pages/template_general.html";
	public static final String DIRECCION_MODAL = "/resources/pages/templates/template_nacimiento1.html";
	public static final String DIRECCION_REVISION_REGISTRADOR = "/resources/pages/templates/verificacion_registrador.html";
	public static final String[] POSIBLES_DECLARANTES = new String[]{"MAD","PAD"};
	public static final String[] PREFIJOS = new String[]{"TDM","TDP"};
	//public static final String RUTA_PDF = "C:/tmp/Declaracion_jurada_";
	public static final String RUTA_PDF = "Declaracion_jurada_";
	@Autowired 
    private ParticipanteServicioCliente clienteParticipante;
    
    @Autowired 
    private CatalogoServicioCliente catalogoServicioCliente;
    
    @Autowired 
    private SolicitudServicioCliente cliente;
    
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
	
    @RequestMapping(value="/iniciarVerificacion", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody
	String  iniciarVerificacion(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		log.info("*****************************Iniciando Verificacion de registrador civil*****");
		String token;
		token=request.getHeader("Authorization");
		/*if(token==null){
			throw new GeneralException("seguridad_no_token");
		}*/	
		log.info("*******************Token recibido: "+token);
		if (token.contains("Bearer") || token.contains("bearer")) {
            token = token.substring(7);
        }
		log.info("*******************Token -Bearer: "+token);
		session.setAttribute("access_token", token);
		tokenAutenticar = (String) session.getAttribute("access_token");
		String htmlValido = Util.leerArchivo(context.getRealPath(DIRECCION_MODAL));
		return htmlValido;
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
	@RequestMapping(value="/procesarVerificacion", method = RequestMethod.POST)
	public @ResponseBody String  procesarVerificacion(@RequestBody String datos, HttpServletRequest request) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException, Exception{
		
		ObjectMapper mapper= new ObjectMapper();
		JSONObject data = new JSONObject(datos);
		//numero de testigos procesados
		int numTestigo = data.has("numTestigo") ? data.getInt("numTestigo") : 1 ;
		//numero de testigo local a procesar
		int lNumTestigo = numTestigo > 2 ? numTestigo-2 : numTestigo;
		log.info("*************Numero de solicitud: " + data.get("id"));
		log.info("*************Numero de Testigo:  " + numTestigo);
		log.info("*******token: "+tokenAutenticar);
		//log.info("*******token: "+DroolsControlador.tokenAutenticar);
		List<DeclaracionJurada> listaDeclaraciones = clienteParticipante.consultarDeclaracionJurada(data.getString("id"), tokenAutenticar);
		DeclaracionJurada declaracionJurada = listaDeclaraciones.get(0);
		//Busca los participantes presentes en la declaracion jurada
		List<Participante> listaParticipantes = declaracionJurada.getParticipantes();
		//Busca lista de posibles declarantes ********
		String codigoDeclarante = null;
		if(!data.has("codigoDeclarante")){
			List<String> declarantes = new ArrayList<>();
			for(String codDeclarante : POSIBLES_DECLARANTES){
				Participante p = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, codDeclarante);
				if(p != null){
					declarantes.add(codDeclarante);
				}
			}
			codigoDeclarante = declarantes.get(0);
			data.put("codigoDeclarante", codigoDeclarante);
			data.put("declarantes", mapper.writeValueAsString(declarantes));
		}else{
			codigoDeclarante = data.getString("codigoDeclarante");
		}
		log.info("*******Codigo del declarante: " + codigoDeclarante);
		JSONObject mDeclaracion = generarPDFDeclaracion(declaracionJurada, codigoDeclarante, lNumTestigo);
		data.put("numTestigo", (++numTestigo)+"");
		TipoParticipante tipoParticipante = catalogoServicioCliente.consultarTipoParticipantePorCodigo(mDeclaracion.getString("rol"), false, tokenAutenticar);
		data.put("titulo", tipoParticipante.getNombre());
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", data);
		respuesta.put("vista", mDeclaracion.getString("vista"));
		return respuesta.toString();
	}

	@RequestMapping(value="/conformidad", method = RequestMethod.POST)
	public @ResponseBody String  procesarConformidad(@RequestBody String datos, HttpServletRequest request) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		JSONObject data = new JSONObject(datos);
		int nConformaciones = data.getInt("nConformaciones");
		data.put("nConformaciones", (++nConformaciones) + "");
		String htmlRegistrador = Util.leerArchivo(context.getRealPath(DIRECCION_REVISION_REGISTRADOR));
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", data);
		respuesta.put("vista", htmlRegistrador);
		return respuesta.toString();
	}
	
	@RequestMapping(value="/actualizarSolicitud", method = RequestMethod.POST)
	public @ResponseBody String  actualizarSolicitud(@RequestBody String datos, HttpServletRequest request) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException{
		JSONObject data = new JSONObject(datos);
		ObjectMapper mapper= new ObjectMapper();
		int conforme = data.getInt("conforme");
        solicitud.setNumeroSolicitud(data.getString("id"));
        solicitud.setMotivoCambioEstado("REABIERTA");
		
		if (conforme == 1){
			solicitud.setEstadoSolicitud("PI");
		}
		else
			solicitud.setEstadoSolicitud("NC");
		if(data.has("observaciones")){
			solicitud.setMotivoCambioEstado(data.getString("observaciones"));
		}
		Solicitud sol = cliente.actualizarEstadoSolicitud(solicitud, tokenAutenticar);
		String ruta = "/resources/pages/success/satisfactorio_modal.html";
		String vista = Util.leerArchivo(context.getRealPath(ruta));
		data.put("mensaje", "Se ha realizado la operaci\u00f3n satisfactoriamente");
		JSONObject respuesta = new JSONObject();
		respuesta.put("vista", vista);
		respuesta.put("modelo", data);
		return respuesta.toString();
	}
	
	
	public JSONObject generarPDFDeclaracion(DeclaracionJurada declaracion, String codigoDeclarante, int numTestigo) throws JRException, JSONException, IOException, Exception{
		String dj = properties.getProperty("plantilla.jasper.report.ciudadano.declaracionJurada");
		String rutaLocal=properties.getProperty("sarc.file.tmp");
		JSONObject login = seguridadServicioCliente.getUsernameCliente(tokenAutenticar);
	    Funcionario funcionario = funcionarioServicioCliente.buscarPorLogin(login.getString("username"), tokenAutenticar);
	    String nombreOficina = funcionario.getOficina().getNombre();
		List<Participante> participantes = declaracion.getParticipantes();
		String prefijo = PREFIJOS[Arrays.binarySearch(POSIBLES_DECLARANTES, codigoDeclarante)];
		Participante testigo = ProcesadorListas.encontrarParticipantePorCodigo(participantes, prefijo+numTestigo);
		Participante declarante = ProcesadorListas.encontrarParticipantePorCodigo(participantes, codigoDeclarante);
		int nSolicitud = (int) (10000*Math.random());
		String realPathFdf = rutaLocal+RUTA_PDF + testigo.getRol() + nSolicitud + ".pdf";
		log.info("Path: "+realPathFdf);
		log.info("Ruta del contexto: "+context.getRealPath("/"));
		log.info("***leer archivo: "+dj);
        JasperReport jasperReport = JasperCompileManager.compileReport(dj);
        Map<String,Object> parameters = new HashMap<String,Object>();
        Date fecha = declaracion.getFechaDeclaracion();
        log.info("***Fecha: "+fecha);
        String urlFile=properties.getProperty("plantilla.jasper.report.imagen.logo");

        parameters.put("dia", GenerarActas.obtenerParametroDeFecha("DIA", fecha));        
        parameters.put("mes", GenerarActas.obtenerParametroDeFecha("STRING_MES", fecha));
        parameters.put("ano", GenerarActas.obtenerParametroDeFecha("ANIO", fecha));
        parameters.put("urlFile", urlFile);
        String funcionarioFullName=StringUtils.capitalize(funcionario.getPrimerNombre().toLowerCase())
        		+' '+ StringUtils.capitalize(funcionario.getPrimerApellido().toLowerCase());
		parameters.put("registrador", funcionarioFullName);
		parameters.put("oficina",nombreOficina);
        
        cargarDatosDeclarante(parameters, declarante);
        cargarDatosParticipante(parameters, testigo);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,  new JREmptyDataSource());      
        JasperExportManager.exportReportToPdfFile(jasperPrint, realPathFdf);
        String server=properties.getProperty("sarc.web.servidor.atc");
		//String template="<embed width='900' height='450' src='"+ "C:/tmp/" + "Declaracion_jurada_" + testigo.getRol() + nSolicitud + ".pdf' type='application/pdf'></embed>";
		String template="<embed width='900' height='450' src='"+server+"/web-autenticarCiudadano/tmp/" + "Declaracion_jurada_" + testigo.getRol() + nSolicitud + ".pdf' type='application/pdf'></embed>";
		
		JSONObject formulario=new JSONObject();
		formulario.put("vista", template);
		formulario.put("rol",testigo.getRol());
		return formulario;
	}
		
	public void cargarDatosDeclarante(Map<String,Object>parameters, Participante padre){
        String nombreDeclarado=padre.getPrimerNombre()+(padre.getSegundoNombre()==null ? " " :" "+padre.getSegundoNombre()+" ")
				+padre.getPrimerApellido()+(padre.getSegundoApellido()==null ? " " :" "+padre.getSegundoApellido());
        parameters.put("declarado", nombreDeclarado);
        Direccion mDireccion = ProcesadorListas.getDireccionPorTipo(padre, "RES");
        if(mDireccion == null)
        	return;
        parameters.put("direcciondeclarado", mDireccion.getUbicacion());
        log.info("**************Direccion del declarante: " + mDireccion.getUbicacion());
        parameters.put("estadodeclarado", mDireccion.getEstado().getNombre());
        parameters.put("municipiodeclarado", mDireccion.getMunicipio().getNombre());
        parameters.put("parroquiadeclarado", mDireccion.getParroquia().getNombre());
        if(padre.getDocumentoIdentidad() != null)
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
        parameters.put("direcciondec", mDireccion.getUbicacion());
        log.info("**************Direccion del testigo: " + mDireccion.getUbicacion());
        parameters.put("estadodec", mDireccion.getEstado().getNombre());
        parameters.put("municipiodec", mDireccion.getMunicipio().getNombre());
        parameters.put("parroquiadec", mDireccion.getParroquia().getNombre());
	}
	
	  public  String obtenerEndPointConfig(String endPointClave) {
		String rutaReportes=(System.getProperty("jboss.home.dir") == null ? "C:/jboss-as-7.1.1.Final" : System.getProperty("jboss.home.dir"))+"/modules/ve/gob/cne/sarc/main/";
		log.info("EndPoint a reportes: " + rutaReportes);
	    File configFile = new File(rutaReportes+ "Reportes.properties");    
	    String host = "";
	    try{
	    	FileReader reader = new FileReader(configFile);
	    	Properties props = new Properties();
	    	props.load(reader);
	    	host = props.getProperty(endPointClave);
	    	reader.close();
	    } catch (FileNotFoundException ex) {
	    	log.debug("ERROR Config file No Encontrado");
	    } catch (IOException ex){
	    	log.debug("ERROR I/O accediendo a Archivo Config");
	    }
	    log.info("direccion del host: "+host);
	    return host;
	  }
	  
		@ExceptionHandler(GeneralException.class)
		public @ResponseBody String handleCustomException(GeneralException ex, HttpServletRequest request ) {
			ServletContext context = request.getSession().getServletContext();
			String contexto = context.getRealPath("/resources/site/properties");
			ex.getMessage(contexto + "//messages.properties", ex.getErrCode());
			JSONObject json = new JSONObject();
			try {
				json.append("error", ex.getErrCode());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return 	json.toString();
		}
	  
}
