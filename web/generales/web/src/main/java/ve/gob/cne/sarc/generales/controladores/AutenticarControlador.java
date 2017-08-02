package ve.gob.cne.sarc.generales.controladores;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.clienteWeb.servicio.cliente.ClienteWebServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.Constantes;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

@Controller
public class AutenticarControlador {
	
	@Autowired
    public AdministradorPropiedades properties;
	
	@Autowired
	ParticipanteServicioCliente participanteCliente;
	
	@Autowired
	CatalogoServicioCliente catalogoCliente;
	
	 @Autowired
	ClienteWebServicioCliente clienteWeb;
	
	public static String tokenAutenticar;
	
	final static Logger logger = Logger.getLogger(PrincipalControlador.class.getName());
	
	@Autowired
	ServletContext context;

	/**
	 * <p>Controlador de acceso al modulo autenticar EPDIC</p> 
	 * @param data
	 * @param sesion
	 * @return La ruta a iniciar 
	 * @throws RestClientException
	 * @throws URISyntaxException
	 * @throws GeneralException
	 */
	@RequestMapping(value = "/iniciarVerificacion", method = RequestMethod.POST)
	public @ResponseBody String  iniciarEPDIC(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException, RestClientException, URISyntaxException{
		logger.info("**************Iniciando VERIFICACION AUTENTICACION**************"+sesion.getId());
		String token=(String) sesion.getAttribute("access_token");
		String html;
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		html =clienteWeb.consultarEndPointHtmlIniciarVerificacion(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
	/**
	 * Consulta el servicio de participantes por tramite implementando el servicio de seguridad
	 * @param data : Informaci&oacute;n del tr&aacute;mite enviada desde el controlador javascript
	 * @param sesion HttpSession 
	 * @return La lista de participantes para el tr&aacute;mite
	 * @throws JSONException 
	 * @throws GeneralException 
	 */
	@RequestMapping(value = "/consultarParticipantesPorTramite", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String consultarParticipantesPorTramite(@RequestBody Map<String, String> data, HttpSession sesion) throws JSONException, GeneralException{
		logger.info("******************Control de acceso a consultar participante por tramite*********************");
		String idDeclarante = buscarValorProperties("servicios.solicitud.tipo.solicitante.declarante");
	    String idEntePublico = buscarValorProperties("servicios.solicitud.tipo.solicitante.ente");
	    String valorClave;
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		logger.info("***********Tramite: "+data.get("tramite"));
		logger.info("***********Tipo de declarante IMPRESO: "+data.get("tipoDeclarante"));
		logger.info("***********idDeclarante IMPRESO: "+idDeclarante);
		logger.info("***********idEntePublico IMPRESO: "+idEntePublico);
		if(data.get("tipoDeclarante")=="Declarante"){
			valorClave=idDeclarante;
		}else{
			valorClave=idEntePublico;
		}
		logger.info("***********valorClave IMPRESO: "+valorClave);
		List<TipoParticipante> listaParticipantes = participanteCliente.buscarTipoParticipantePorTramite(data.get("tramite"),valorClave,token);
		//List<TipoParticipante> listaParticipantes = participanteCliente.buscarTipoParticipantePorTramite(data.get("tramite"),data.get("tipoDeclarante"),token);
		//List<TipoParticipante> listaParticipantes = participanteCliente.buscarTipoParticipantePorTramite(data.get("tramite"),"1",token);
		logger.info("**********Encontrados: "+listaParticipantes.size());
		JSONArray opciones= new JSONArray();
		for(TipoParticipante lista : listaParticipantes) {
			JSONObject objCombo= new JSONObject();
			objCombo.put("name", lista.getNombre());
			objCombo.put("value", lista.getCodigo());
			logger.info("***********Elemento: : "+objCombo.toString());
			opciones.put(objCombo);
		}
		return opciones.toString();
	}
	
    /**
     * Metodo que permite buscar el valor de una propiedad en los archivos properties
     *
     * @param clave String Propiedad a buscar en ela rchivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) throws GeneralException {

        String valor = "";
        //lee properties
        System.out.println("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
        	logger.info("Error leyendo properties: " + ex.getMessage());
        }
        System.out.println("buscarValorProperties --> valor --> " + valor);

        return valor;
    }


	/**
	 * <p>Controlador para consultar documentos de identificacion en BD</p> 
	 * @param data
	 * @param sesion
	 * @return La ruta a iniciar 
	 * @throws JSONException
	 * @throws GeneralException
	 */
	@RequestMapping(value = "/consultarDocumentoAutenticacion", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String consultarDocumentoAutenticacion(@RequestBody Map<String, String> data, HttpSession sesion) throws JSONException, GeneralException{
		logger.info("******************Control de acceso a consultar Documentos de autenticacion*********************");
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		
		LinkedHashMap<String,String> mapa = catalogoCliente.consultarDocumentosAutenticados(token);
		
		Set<String> keys = mapa.keySet();
		logger.info("**********Encontrados: " + mapa.size());
		JSONArray opciones= new JSONArray();
		for(String key : keys) {
			JSONObject objCombo= new JSONObject();
			objCombo.put("name", mapa.get(key));
			objCombo.put("value", key);
			logger.info("***********Elemento: : "+objCombo.toString());
			opciones.put(objCombo);
		}
		return opciones.toString();
	}
	
	/**
	 * <p>Consulta los datos de la declaraci&oacute;n jurada sociada a la solicitud</p>
	 * @param data N&uacute;mero de solicitud
	 * @param sesion
	 * @return
	 * @throws GeneralException 
	 * @throws JSONException 
	 * @throws JRException 
	 * @throws URISyntaxException 
	 * @throws RestClientException 
	 */
	@RequestMapping(value="/procesarVerificacion", method = RequestMethod.POST)
	public @ResponseBody String  procesarVerificacion(@RequestBody Map<String,String> data, HttpSession sesion) throws GeneralException, JRException, JSONException, RestClientException, URISyntaxException{
		logger.info("******************************Iniciando proceso de verificacion  de declaracion jurada: ");
		String html;
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlProcesarVerificacion(token);
		return Util.obtenerRecurso(token, data, html);
	}

	@RequestMapping(value="/conformidad", method = RequestMethod.POST)
	public @ResponseBody String  procesarConformidad(@RequestBody Map<String,String> data, HttpSession sesion) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException, RestClientException, URISyntaxException{
		logger.info("***********************************Iniciando proceso Conformacion: ");
		String html;
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlConformidad(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
	/**
	 * <p>Controlador que redirecciona a otro contexto</p> 
	 * @param data
	 * @param sesion
	 * @return La ruta a iniciar 
	 * @throws JSONException
	 * @throws GeneralException
	 * @throws JRException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws RestClientException
	 * @throws URISyntaxException
	 * 
	 */
	@RequestMapping(value="/actualizarSolicitud", method = RequestMethod.POST)
	public @ResponseBody String  actualizarSolicitud(@RequestBody Map<String,String> data, HttpSession sesion) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException, RestClientException, URISyntaxException{
		logger.info("***********************************Iniciando proceso Conformacion: ");
		String html;
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlActualizarSolicitud(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
	@RequestMapping(value="/iniciarCorreccion", method = RequestMethod.POST)
	public @ResponseBody String  iniciarCorreccion(@RequestBody Map<String,String> data, HttpSession sesion) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException, RestClientException, URISyntaxException{
		logger.info("***********************************Iniciando proceso Conformacion: ");
		String html;
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlIniciarCorreccion(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
	@RequestMapping(value="/procesarCorreccion", method = RequestMethod.POST)
	public @ResponseBody String  procesarCorreccion(@RequestBody String data, HttpSession sesion) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException, RestClientException, URISyntaxException{
		logger.info("***********************************Iniciando proceso Conformacion: ");
		String html;
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlProcesarCorreccion(token);
		return Util.obtenerRecurso(token, data,html);
	}
	
	@RequestMapping(value="/infoDeclaracion", method = RequestMethod.POST)
	public @ResponseBody String  infoDeclaracion(@RequestBody Map<String,String> data, HttpSession sesion) throws Exception {
		String token=(String) sesion.getAttribute("access_token");
		String html;
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlInfoDeclaracion(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
	@RequestMapping(value="/procesarImpresion", method = RequestMethod.POST)
	public @ResponseBody String  procesarImpresion(@RequestBody String data, HttpSession sesion) throws GeneralException, JRException, JSONException, JsonParseException, JsonMappingException, IOException, RestClientException, URISyntaxException{
		logger.info("***********************************Iniciando proceso Conformacion: ");
		String html;
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlProcesarImpresion(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
	@RequestMapping(value="/infoImpresion", method = RequestMethod.POST)
	public @ResponseBody String  infoImpresion(@RequestBody Map<String,String> data, HttpSession sesion) throws Exception {
		String token=(String) sesion.getAttribute("access_token");
		String html;
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlInfoImpresion(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
	@RequestMapping(value="/actualizarAbierta", method = RequestMethod.POST)
	public @ResponseBody String  actualizarAbierta(@RequestBody Map<String,String> data, HttpSession sesion) throws Exception {
		String token=(String) sesion.getAttribute("access_token");
		String html;
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlActualizarAbierta(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
	@RequestMapping(value="/enviarAverificacion", method = RequestMethod.POST)
	public @ResponseBody String  enviarAverificacion(@RequestBody String data, HttpSession sesion, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String token=(String) sesion.getAttribute("access_token");
		String html;
		if(token==null){
			throw new GeneralException("seguridad_no_token");
		}	
		html =clienteWeb.consultarEndPointHtmlEnviarAverificacion(token);
		return Util.obtenerRecurso(token, data, html);
	}
	
}
