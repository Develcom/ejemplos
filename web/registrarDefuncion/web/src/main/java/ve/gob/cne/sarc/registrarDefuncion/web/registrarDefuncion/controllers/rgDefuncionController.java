package ve.gob.cne.sarc.registrarDefuncion.web.registrarDefuncion.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import orgsarcreglas.rgdefuncion.RgDefuncion;
import ve.gob.cne.sarc.acta.servicio.cliente.ActaServicioCliente;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.comunes.catalogo.ComunidadIndigena;
import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Nacionalidad;
import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;
//import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;
import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.catalogo.Recaudo;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.oficina.Ore;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.defuncion.servicio.cliente.DefuncionServicioCliente;
import ve.gob.cne.sarc.ecu.servicio.cliente.EcuServicioCliente;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.catalogo.Catalogo;
import ve.gob.cne.sarc.generales.defuncion.Defuncion;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.participante.Participantes;
import ve.gob.cne.sarc.generales.reporte.GenerarActas;
import ve.gob.cne.sarc.generales.solicitud.SolicitudServicio;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.generales.util.formatDate;
//import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.recaudo.servicio.cliente.RecaudoServicioCliente;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
//import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.solicitud.servicio.cliente.Constantes;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;
import ve.gob.cne.sarc.comunes.defuncion.*;

import com.fasterxml.jackson.core.JsonGenerationException;

@RestController
public class rgDefuncionController {
	private static final String String = null;
	static String tipoDeclarante;

	@Autowired
	ServletContext context;

	Logger log = Logger.getLogger(getClass().getName());
	private final String rutaHtml = "/resources/pages/templates/";
	private final String RUTA_PLANTILLA = "resources/pages/plantillas/";
	private final String PLANTILLA_DEFUNCION = "Oficio_ORE.jrxml";
	private final String PLANTILLA_ACTA = "registro_de_defuncion.jrxml";
	private final String RUTA_VISTA = "/resources/pages/templates/imprimir_documento.html";
	private final String RUTA_BORRADOR = "/resources/pages/templates/imprimirBorrador.html";
	private final String RUTA_PV = "/resources/pages/templates/vista_oficio.html";
	private final String RUTA_ACTA = "/resources/pages/templates/vista_acta.html";
	private final String RUTA_IMPRIMIR_ACTA = "/resources/pages/templates/imprimir_acta.html";
	private final String RUTA_FIN = "/home/jboss/tmp/";
//	private final String RUTA_FIN = "C:/tmp/";
	private final String rutaLogo="resources/img/";
	SolicitudServicioCliente solicitud = new SolicitudServicioCliente();
	// FuncionarioServicio funcionario = new FuncionarioServicio();
	RecaudoServicioCliente recaudo = new RecaudoServicioCliente();
	SeguridadServicioCliente seguridadCliente = new SeguridadServicioCliente();
	FuncionarioServicioCliente funcionarioServicioCliente = new FuncionarioServicioCliente();
	ParticipanteServicioCliente participanteServicioCliente = new ParticipanteServicioCliente();
	ActaServicioCliente servicioActa = new ActaServicioCliente();
	DefuncionServicioCliente servicioDefuncion = new DefuncionServicioCliente();

	// ******************************INICIO
	// RDROOLS**************************************
	/**
	 * <p>
	 * Inicia el tr&aacute;mite de autenticaci&n
	 * </p>
	 * 
	 * @param data
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/iniciarTramite", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String iniciarAutenticar(@RequestBody String data,
			HttpSession session, HttpServletResponse response) throws Exception {
		log.info("********************Iniciando tr\u00e1amitede autenticaci\u00f3n desde generales  ************"
				+ data);
		JSONObject modelo = new JSONObject(data);
		// JSONObject jsonSol = modelo.getJSONObject("solicitud");
		ObjectMapper mapper = new ObjectMapper();
		SolicitudServicioCliente solCliente = new SolicitudServicioCliente();
		Solicitud result = solCliente.consultarDetalleSolicitud(modelo
				.getString("id"));
		log.info("********************Nombre del tramite: "
				+ result.getTramite().getCodigo());
		Object objeto = new RgDefuncion();
		JSONObject respuesta = new JSONObject();
		JSONObject pojo = new JSONObject(mapper.writeValueAsString(objeto));
		pojo.put("escenario", "GATE 0");
		// ((RgDefuncion)objeto).setEscenario("GATE 0");
		String rutaBase = "/resources/pages/templates/";
		modelo.put("rutaBase", rutaBase);
		modelo.put("claseModulo", objeto.getClass().getName());
		modelo.put("pojo", pojo);
		modelo.put("titulo", "Datos del (de la) fallecido(a)");
		// JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", modelo);
		respuesta.put("vista",
				Util.leerArchivo(context.getRealPath(rutaBase + "gate0.html")));
		return respuesta.toString();
	}

	@RequestMapping(value = "/consultarDrools", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String consultarDrools(@RequestBody String data,
			HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws Exception {
		JSONObject modelo = new JSONObject(data);
		JSONObject pojoJson = modelo.getJSONObject("pojo");
		ObjectMapper mapper = new ObjectMapper();
		Object pojo = mapper.readValue(pojoJson.toString(),
				Class.forName(modelo.getString("claseModulo")));
		String nombreClase1 = pojo.getClass().getName()
				.replace(pojo.getClass().getPackage().getName() + ".", "");
		// crea una instancia del cliente de reglas de negocio para el tramite
		// especifico
		Object kie = Class.forName(
				"ve.gob.cne.sarc.reglas.servicio.cliente.ReglasServicioCliente"
						+ nombreClase1).newInstance();
		// realiza la consulta al motor de reglas de negocio
		Object pojo2 = validarKie(kie, pojo);

		String numSolicitud = modelo.getString("id");
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		String[] codigoTipo = { "FAL" };
		
		// Consulta para validar si el fallecido tiene identificacion
		List<Participante> obetenerDatos = new ArrayList<Participante>();
		obetenerDatos = Participantes.consultarParticPorTipo(
				sol.getNumeroSolicitud(), codigoTipo);
		String Identificacion = null;
		if(!obetenerDatos.isEmpty()){
		Participante participante = obetenerDatos.get(0);
			Identificacion = participante.getDocumentoIdentidad().get(0).getNumero();
		} else {
			Identificacion = null;
		}
		modelo.put("Identificacion", Identificacion);
		// /fin consulta

		// Recupera el nombre de la proxima vista a ser presentada
		JSONObject jsonPojo = new JSONObject(mapper.writeValueAsString(pojo2));
		String rutaBase = modelo.getString("rutaBase");
		modelo.put("pojo", jsonPojo);
		modelo.put("titulo", "Solicitud de registro");
		JSONObject respuesta = new JSONObject();
		String vistaDrools = jsonPojo.getString("vista");
		if (!"No hay mas vista que mostrar por ser un Nodo Terminal".equals(vistaDrools)) {
			respuesta.put("vista",Util.leerArchivo(context.getRealPath(rutaBase + vistaDrools+ ".html")));
		}
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	@RequestMapping(value = "/consultarDroolsAtras", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String consultarDroolsAtras(
			HttpServletRequest request, HttpSession session) throws Exception {
		log.info("regresando drools");
		List<Object> pojo = pojo = (List<Object>) session.getAttribute("pojo");
		pojo.remove(pojo.size() - 1);
		return null;
	}

	/**
	 * Realiza la mezcla de dos objetos json
	 * 
	 * @param modelo
	 * @param modelo2
	 * @return objeto mezclado
	 * @throws JSONException
	 */
	public JSONObject agregarModelo(JSONObject modelo, JSONObject modelo2)
			throws JSONException {
		Iterator<String> iterador = modelo2.keys();
		do {
			String key = iterador.next();
			modelo.put(key, modelo2.get(key));
		} while (iterador.hasNext());
		return modelo;
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		ex.printStackTrace();
		log.info("Error: "+ex.getMessage()+" "+ex.getLocalizedMessage());
		return "redirect:/resources/js/json/error/error.json";
	}

	public Object validarKie(Object kie, Object objeto) throws Exception {
		Object objeto2 = null;
		// obtiene el nombre de la clase del objeto tramite, por ejemeplo
		// Nacimiento o Adopcion
		String nombreClase = objeto.getClass().getName()
				.replace(objeto.getClass().getPackage().getName() + ".", "");
		log.info("**************Clase del Objeto a validar: " + nombreClase);
		log.info("**************Clase del Kie: " + kie.getClass().getName());
		Method[] metodosObjeto = objeto.getClass().getMethods();
		for (Method metodo : metodosObjeto) {
			if (metodo.getName().equals("getEscenario")) {
				String escenario = (String) metodo.invoke(objeto, null);
				log.info("**************Escenario: "+escenario);
			}
		}
		Method[] metodos2=kie.getClass().getMethods();
		for (Method metodo : metodos2){
			log.info("Nombre del metodo: "+metodo.getName());
			if(("validar"+nombreClase).equals(metodo.getName())){
				log.info("************evaluando defuncion ******************");
				try{
					objeto2= metodo.invoke(kie, objeto);
				}catch(Exception e){
					log.info("************ERROR: "+e.getMessage()+"*************");
					e.printStackTrace();
					throw e;
				}
			}
		}
		return objeto2;
	}

	public Object getPropiedad(Object adopcion2, String nombreMetodoGeter) throws Exception{
		Object atributo=null;
		Method[] metodos3=adopcion2.getClass().getMethods();
		for (Method metodo: metodos3) {
			if((nombreMetodoGeter).equals(metodo.getName())){
				Object[] args=null;
				atributo=metodo.invoke(adopcion2, args);
			}
		}
		return atributo;
	}
	//	******************************FIN RDROOLS**************************************


	/*
	 * finalizar.java
	 * @descripcion Metodo que muestra mensaje de proceso satisfactorio
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/permisoInhumacion", method = RequestMethod.POST)
	public @ResponseBody String permisoInhumacion(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		log.info("*************ficha_tecnica************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		JSONObject modelLista = modelo.getJSONObject("pojo");
		//		String[] lista = new String[1];
		//		lista[0] = modelLista.getString("lista").trim();	 
		String htmlInicio = null;
		String numSolicitud = modelo.getString("id");
		//		String estatus = modelo.getJSONObject("solicitud").getString("estatus");		
		//		boolean rec = recaudo.registrarRecaudos(numSolicitud,lista);
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "vista_permiso_preg.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("mensaje", "Impresi\u00f3n exitosa");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * escenario.java
	 * @descripcion Metodo que muestra imagen de hisof
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/escenario", method = RequestMethod.POST)
	public @ResponseBody String escenario(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException, ParseException {
		log.info("*************ficha_tecnica************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		JSONObject modelLista = modelo.getJSONObject("pojo");
		String[] lista = new String[1];
		Acta act = new Acta();
		lista[0] = modelLista.getString("lista").trim();	 		
		String htmlInicio = null;
		String numSolicitud = modelo.getString("id");
		String estatus = modelo.getString("estatus");	
		JSONObject respuesta = new JSONObject();
		boolean rec = recaudo.registrarRecaudos(numSolicitud,lista);
		String numeroActa = new String();
		numeroActa = servicioActa.buscarNumeroActaPorSolic(numSolicitud);		
		try{
		if(modelLista.getString("inscripcion") != null) {
		String fechaDefuncion = modelo.getString("fechaDefuncion");
		String numeroCertificadoDef = modelLista.getString("numeroCertificadoDef");
		ve.gob.cne.sarc.comunes.defuncion.Defuncion defuncion = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		Date fd = new Date();
		DateFormat outputFormatDef = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		fd =  outputFormatDef.parse(fechaDefuncion);
		defuncion.setNumeroActa(numeroActa);
		defuncion.setFechaDefuncion(fd);
		defuncion.setNumeroCertificado(Long.parseLong(numeroCertificadoDef));
		boolean defSatisfactorio = servicioDefuncion.guardarDefuncion(defuncion);	
		}}catch(Exception e){}
		
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "tipo_escenario.html"));
		modelo.put("titulo", "Validar recaudos");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * consultarTipoPermiso.java
	 * @descripcion Funcion que devuelve una vista y modelo de cada inicio del proceso
	 * @version 1.0 11/6/2016 
	 * @author Dairene Ramirez
	/*
	 * @param data: contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/iniciarTramiteRDEFU", method = RequestMethod.POST)
	public @ResponseBody String iniciarTramiteRDEFU(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException, JRException, ParseException {
		log.info("*************Tipo de permiso************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data); 
		//	JSONObject modelLista = modelo.getJSONObject("pojo");		
		String htmlInicio = null;
		String numSolicitud = modelo.getString("id");
		String estatus = modelo.getString("estatus");		
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		String[] codigoTipo = { "FAL" };
		ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();
		List<Participante> particiSolicitud;
		particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");
		Participantes.consultarParticPorTipo(sol.getNumeroSolicitud(), codigoTipo);
		ObjectMapper mapper1 = new ObjectMapper();
		int numeroControl = 0;
		for (Participante participante1 : particiSolicitud) {
			JSONObject data1 = new JSONObject(mapper1.writeValueAsString(participante1));
			String valorRol = new String();
			valorRol=participante1.getRol();
			String hij = "HIJ";
			if (valorRol.equals(hij)){
				numeroControl++;
				participante1.setRol("HIJ"+numeroControl);
			}
			modelo.put(participante1.getRol(), data1);
		}
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ htmlInicio + ".html"));
		modelo.put("titulo", "Solicitud de registro");
		JSONObject respuesta = new JSONObject();
		String token=request.getHeader("Authorization");
		if("PV".equals(estatus)){  			
			JSONObject modeloReporte = generarReporte(data, numSolicitud,estatus, token);
			modelo.put("titulo", "Oficio para la oficina");
			respuesta.put("modelo", modelo);
			respuesta.put("vista",modeloReporte.get("vista"));
		}else if("NC".equals(estatus)){  			
			log.info("ENTRO AL NC");
			//JSONObject pojoJson = modelo.getJSONObject("pojo");
			modelo.put("titulo", "Oficio para la ORE");
			modelo.put("observaciones", sol.getMotivoCambioEstado());
			respuesta.put("modelo", modelo);
			respuesta.put("vista", htmlValido);
		}else if("PPD".equals(modelo.getString("estatus"))){
			modelo.put("titulo", "Cargar documento");
			respuesta.put("vista", htmlValido);
			respuesta.put("modelo", modelo);
		}else{		
			log.info("ENTRO TAMBIEN");
			respuesta.put("vista", htmlValido);
			respuesta.put("modelo", modelo);
		}
		return respuesta.toString();
	}

	/*
	 * validarReporteConformeORE.java
	 * @descripcion Metodo que devuelve la vista a presentar en pantalla
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/validarReporteConformeORE2", method = RequestMethod.POST)
	public @ResponseBody String validarReporteConformeORE(
			@RequestBody String data, HttpSession sesion,
			HttpServletRequest request) throws GeneralException,
			JsonParseException, JsonMappingException, IOException,
			JSONException {
		log.info("*************Tipo de permiso************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		String numSolicitud = modelo.getString("id");
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);

		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "conforme_oficio_ORE.html"));
		modelo.put("titulo", "Certificaci\u00f3n del registro");
		JSONObject respuesta = new JSONObject();
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * imprimirORE.java
	 * @descripcion Metodo que devuelve la vista del oficio a imprimir
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data: contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/imprimirORE", method = RequestMethod.POST)
	public @ResponseBody String imprimirORE(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException, JRException, ParseException {
		log.info("*************ficha_tecnica************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		String htmlInicio = null;
		String numSolicitud = modelo.getString("id");
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		String token=request.getHeader("Authorization");
		JSONObject modeloReporte = generarReporte(data, numSolicitud,modelo.getString("estatus"), token);
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", modelo);
		respuesta.put("vista", modeloReporte.get("vista"));
		return respuesta.toString();
	}

	/*
	 * imprimirActa.java
	 * @descripcion Metodo que devuelve la vista del oficio a imprimir
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data: contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/imprimirActa", method = RequestMethod.POST)
	public @ResponseBody String imprimirActa(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException, JRException {
		log.info("*************ficha_tecnica************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		String htmlInicio = null;
		String numSolicitud = modelo.getString("id");
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		//	htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		//String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "ficha_tecnica.html"));
	//	JSONObject modeloReporte = imprimirActa(numSolicitud,modelo.getString("estatus"),modelo);
		JSONObject modeloReporte = vistaPreviaActa1(numSolicitud,modelo.getString("estatus"),modelo);
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", modelo);
		respuesta.put("vista", modeloReporte.get("vista"));
		return respuesta.toString();
	}

	/*
	 * finalizar.java
	 * @descripcion Metodo que muestra mensaje de proceso satisfactorio
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public @ResponseBody String finalizar(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		log.info("*************ficha_tecnica************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		String htmlInicio = null;
		String numSolicitud = modelo.getString("id");
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "satisfactorio.html"));
		JSONObject respuesta = new JSONObject();
		String estatus = modelo.getString("estatus");
		if("PI".equals(estatus)){			
			modelo.put("mensaje", "Impresi\u00f3n exitosa");
		}else if("PPD".equals(estatus) || "PCA".equals(estatus)){
			modelo.put("mensaje", "Carga de documento exitosa");
		}else if("PPI".equals(estatus) || "PCA".equals(estatus)){
			modelo.put("mensaje", "Impresi\u00f3n exitosa");
		}
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * cancelarSolicitud.java
	 * @descripcion Metodo que muestra mensaje de cancelacion de solicitud
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/cancelarSolicitud", method = RequestMethod.POST)
	public @ResponseBody String cancelarSolicitud(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		log.info("*************ficha_tecnica************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "cancelar_solicitud.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("cancelar","La solicitud fue cancelada por no poseer el certificado m\u00e9dico de defunci\u00f3n EV-14.");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * oficio.java
	 * @descripcion Metodo que muestra los datos tipeados del director de ORE
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/oficio", method = RequestMethod.POST)
	public @ResponseBody String oficio(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		//		sesion = request.getSession();
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		String numSolicitud = modelo.getString("id");
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		sesion.getAttribute("PrimerNombre");
		sesion.getAttribute("SegundoNombre");
		sesion.getAttribute("PrimerApellido");
		sesion.getAttribute("SegundoApellido");
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "oficio_ORE.html"));
		modelo.put("titulo", "Oficio a la ORE");
		JSONObject respuesta = new JSONObject();
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	@RequestMapping(value = "/consultarParticPorTipo/{numeroSolicitud}/{codigotipo}", method = RequestMethod.GET)
	public List<Participante> consultarParticPorTipo(
			@PathVariable("numeroSolicitud") String numeroSolicitud,
			@PathVariable("codigotipo") String[] codigotipo) {
		ParticipanteServicioCliente cliente = new ParticipanteServicioCliente();
		return cliente.consultarParticPorTipo(numeroSolicitud, codigotipo);
	}

	/*
	 * validacionEcu.java
	 * @descripcion Metodo que muestra la pantalla de validacio ecu
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/validacionEcu", method = RequestMethod.POST)
	public @ResponseBody String validacionEcu(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		String numSolicitud = modelo.getString("id");
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_validacion_ecu.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Validar ECU");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}


	/*
	 * consultarTipoPermiso.java
	 * @descripcion Funcion que devuelve una vista y modelo de cada inicio del proceso
	 * @version 1.0 11/6/2016 
	 * @author Dairene Ramirez
	/*
	 * @param data: contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/validarActa", method = RequestMethod.POST)
	public @ResponseBody String validarActaDefuncion(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException, JRException, ParseException {
		log.info("*************Tipo de permiso************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		String token=request.getHeader("Authorization");
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		log.info("****************************************" + modelo.getString("id"));
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		String[] codigoTipo = { "FAL" };
		ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();
		//
		List<Participante> particiSolicitud;
		particiSolicitud = this.consultarParticPorSolicitud(modelo.getString("id"), "T");
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		Participantes.consultarParticPorTipo(sol.getNumeroSolicitud(), codigoTipo);

		ObjectMapper mapper1 = new ObjectMapper();
		int numeroControl = 0;
		for (Participante participante1 : particiSolicitud) {
			JSONObject data1 = new JSONObject(mapper1.writeValueAsString(participante1));
			String valorRol = new String();
			valorRol=participante1.getRol();
			String hij = "HIJ";
			if (valorRol.equals(hij)){
				numeroControl++;
				participante1.setRol("HIJ"+numeroControl);
			}
			modelo.put(participante1.getRol(), data1);
		}
		String numSolicitud = sol.getNumeroSolicitud();
		List<Participante> declarante;
		declarante = this.consultarParticPorSolicitud(numSolicitud, "D");
		log.info("declarantessssssssssss" + declarante.toString());
		ObjectMapper mapper2 = new ObjectMapper();
		for (Participante dec : declarante) {
			log.info("estoy pasando por el declarante");
			
			JSONObject data1 = new JSONObject(mapper2.writeValueAsString(dec));			
			modelo.put("DEC", data1);
		}
		try{
			String cedu = modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
			String[] res = cedu.split("-");
			modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).put("numero", res[1]);
		}catch(Exception e){
			String cedu = modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
			String[] res = cedu.split("-");
			modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).put("numero", res[0]);
		}
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml
				+ "datos_fallecido.html"));
		modelo.put("titulo", "Datos del(de la) fallecido(a)");
		JSONObject respuesta = new JSONObject();
		if ("NADA".equals(modelo.get("estatus"))) {
			JSONObject modeloReporte = generarReporte(data,numSolicitud, modelo.getString("estatus"), token);

			log.info("ENTRO AL JSON RETORNO");
			modelo.put("titulo", "Oficio para la ORE");
			respuesta.put("modelo", modelo);
			respuesta.put("vista", modeloReporte.get("vista"));

		} else {
			respuesta.put("vista", htmlValido);
			respuesta.put("modelo", modelo);
		}
		log.info(respuesta.getJSONObject("modelo").toString());
		log.info(respuesta.toString());
		return respuesta.toString();
	}



	/*
	 * validarActa2.java
	 * @descripcion Metodo validacion del acta
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/validarActa2", method = RequestMethod.POST)
	public @ResponseBody String validarActa2(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
	//	ve.gob.cne.sarc.comunes.defuncion.Defuncion DF = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		ve.gob.cne.sarc.comunes.defuncion.Defuncion defInscrip = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		defInscrip = this.consultarDef(sol.getNumeroSolicitud());
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String fechaFallecimiento = "_";
		String fechaString = "";
		try {
			fechaString = defInscrip.getFechaDefuncion().toString();
			log.info("fecha de fallecimiento---->" + fechaString);
		} catch (Exception e) {
		}

		try {
			fechaFallecimiento = inputFormat.format(defInscrip.getFechaDefuncion());
			log.info(fechaFallecimiento);
		} catch (Exception e) {
		}
		if (fechaFallecimiento != "_") {
			log.info("viene una fecha valida ---->" + fechaFallecimiento);
			modelo.put("fechaDefuncion", fechaFallecimiento);
		}else
		{
			try{
				log.info("no viene una fecha valida ---->" + fechaFallecimiento);
				modelo.remove("fechaDefuncion");
			}catch(Exception e ){}
		}
		try{
			String cedu = modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
			String[] res = cedu.split("-");
			log.info(res[0].toString());
			modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).put("numero", res[1]);
			log.info("bandera" + modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		}catch(Exception e){
			String cedu = modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
			String[] res = cedu.split("-");
			modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).put("numero", res[0]);
			log.info("bandera" + modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		}
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_defuncion.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Datos de la defunci\u00f3n");
		log.info("el modelo completo" + modelo.toString());
		log.info(modelo.getString("titulo"));
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
	
	/*
	 * validarActa3.java
	 * @descripcion Metodo que de validacion del acta
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/validarActa3", method = RequestMethod.POST)
	public @ResponseBody String validarActa3(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		JSONObject autoridad = new JSONObject();
		ve.gob.cne.sarc.comunes.defuncion.Defuncion DF = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		 String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		ve.gob.cne.sarc.comunes.defuncion.Defuncion defCertif = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		try{
			defCertif = this.consultarDef(sol.getNumeroSolicitud());
			Long numCertific = defCertif.getNumeroCertificado();
			String numeroCertificado = Long.toString(numCertific);
			autoridad.put("numCert", numeroCertificado);
			modelo.put("AUTORIDAD", autoridad);
			}catch(Exception e){}
		
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_certificado_defuncion.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Datos del certificado");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * validarActa4.java
	 * @descripcion Metodo que de validacion del acta
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/validarActa4", method = RequestMethod.POST)
	public @ResponseBody String validarActa4(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_familiares_conyugue.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Datos familiares");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
	
	/*
	 * datosFamiliar.java
	 * @descripcion Metodo que de valida los datos de familiares
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/datosFamiliar", method = RequestMethod.POST)
	public @ResponseBody String datosFamiliar(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		List<Participante> particiSolicitud;
		particiSolicitud = this.consultarParticPorSolicitud(sol.getNumeroSolicitud(), "T");
		modelo.put("mamaDeclarante", false);
		modelo.put("papaDeclarante", false);
			if (particiSolicitud.get(0).getRol().equals("MAD")) {
				modelo.put("mamaDeclarante", true);
				modelo.put("viveMama", "Si");
			}else if(particiSolicitud.get(0).getRol().equals("PAD")){
				modelo.put("papaDeclarante", true);
				modelo.put("vivePapa", "Si");
			}
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_familiares_2.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Datos familiares");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * datosFamiliar2.java
	 * @descripcion Metodo que de valida los datos de familiares
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/datosFamiliar2", method = RequestMethod.POST)
	public @ResponseBody String datosFamiliar2(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		JSONObject jsonSol = new JSONObject();
		//jsonSol = modelo.getJSONObject("solicitud");
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		log.info(modelo.toString());
		List<Participante> particiSolicitud;
		particiSolicitud = this.consultarParticPorSolicitud(sol.getNumeroSolicitud(), "T");
	//	ObjectMapper mapper1 = new ObjectMapper();
		modelo.put("mamaDeclarante", false);
		modelo.put("papaDeclarante", false);
		//for (Participante participante : particiSolicitud) {
		//	JSONObject data1 = new JSONObject(data);
			//data1 = new JSONObject(mapper1.writeValueAsString(participante1));
		//	modelo.put(participante1.getRol(), data1);
		//	log.info("********************************************Participante: "+ participante1.getRol());
			if (particiSolicitud.get(0).getRol().equals("MAD")) {
				modelo.put("mamaDeclarante", true);
				modelo.put("viveMama", "Si");
			}else if(particiSolicitud.get(0).getRol().equals("PAD")){
				modelo.put("papaDeclarante", true);
				modelo.put("vivePapa", "Si");
			}
	//	}
		log.info("Estoy en datosFamiliar2");
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_familiares_3.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Datos familiares");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		log.info("padres " + modelo.toString());
		return respuesta.toString();
	}
	
	/*
	 * datosDeclarante.java
	 * @descripcion Metodo que de valida los datos del declarante
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/datosDeclarante", method = RequestMethod.POST)
	public @ResponseBody String datosDeclarante(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		
		List <Recaudo> Recaudos;
		List <String> nombreRecaudo;
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		String soli = sol.getNumeroSolicitud();
		Recaudos = recaudo.consultarRecaudo(soli);
		modelo.put("extractoDefuncion", false);
		JSONObject objeto = new JSONObject();
		for (Recaudo r : Recaudos){
			objeto.put(r.getCodigo(),r.getNombre()); 
			if (r.getCodigo().equals("ECD")){
				modelo.put("extractoDefuncion", true);
			} 
		}
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_declarante.html"));
		JSONObject respuesta = new JSONObject();
		try{
			String cedu = modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
			String[] res = cedu.split("-");
			log.info(res[0].toString());
			modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).put("numero", res[1]);
			log.info("bandera" + modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		}catch(Exception e){
			String cedu = modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
			String[] res = cedu.split("-");
			modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).put("numero", res[0]);
			log.info("bandera" + modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		}
		modelo.put("titulo", "Datos del (de la) declarante");
		log.info(modelo.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		log.info("estoy oasando por aca");
		log.info(modelo.toString());
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
	
	/*
	 * inscripcionDecisionJudicial.java
	 * @descripcion Metodo que muestra la Inscripcion de la Decision Judicial
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/inscripcionDecisionJudicial", method = RequestMethod.POST)
	public @ResponseBody String inscripcionDecisionJudicial(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		
		ve.gob.cne.sarc.comunes.defuncion.Defuncion defInscrip = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		defInscrip = this.consultarDef(sol.getNumeroSolicitud());
		modelo.put("incripcionDefuncion", false);
		modelo.put("insercionDefuncion", false);
		if (defInscrip.getFechaDefuncion() == null) {
			modelo.put("incripcionDefuncion", false);
			modelo.put("insercionDefuncion", true);
		} else {
			modelo.put("incripcionDefuncion", true);
			modelo.put("insercionDefuncion", false);
		}

		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "inscripcion_desicion_judicial.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Inscripci\u00f3n por decisi\u00f3n judicial");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * datosTestigos.java
	 * @descripcion Metodo que de validacion del acta
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/datosTestigos", method = RequestMethod.POST)
	public @ResponseBody String datosTestigos(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_testigo.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Datos de los testigos");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
	
	/*
	 * validarReporteConformeORE.java
	 * @descripcion Metodo que de validacion del acta
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/extractoConsular", method = RequestMethod.POST)
	public @ResponseBody String extractoConsular(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "datos_extracto_consular.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Datos del extracto consular");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
	
	/*
	 * validarReporteConformeORE.java
	 * @descripcion Metodo que de validacion del acta
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/observaciones", method = RequestMethod.POST)
	public @ResponseBody String observaciones(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "circunstancias_especiales_acto.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Observaciones");
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();

	}

	//RecaudoServicioCliente recaudo = new RecaudoServicioCliente();	
	@RequestMapping(value = "/documentosPresentados", method = RequestMethod.POST)
	public @ResponseBody String documentosPresentados(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "documentos_presentados.html"));
		JSONObject respuesta = new JSONObject();
		modelo.put("titulo", "Documentos presentados");
		List <Recaudo> Recaudos;
		List <String> nombreRecaudo;
		String soli = sol.getNumeroSolicitud();
		Recaudos = recaudo.consultarRecaudo(soli);

		JSONObject objeto = new JSONObject();
		for (Recaudo r : Recaudos){
			objeto.put(r.getCodigo(),r.getNombre()); 
		}

		modelo.put("recaudos", objeto);
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		log.info(respuesta.getString("modelo"));
		//		JSONObject modelLista = modelo.getJSONObject("pojo");
		//		  String[] lista = new String[1];
		//		  lista[0] = modelLista.getString("lista").trim();    
		//		 
		//		  String numSolicitud = sol.getNumeroSolicitud();
		//		  String estatus = modelo.getJSONObject("solicitud").getString("estatus");  
		//		  boolean rec = recaudo.registrarRecaudos(numSolicitud,lista);


		return respuesta.toString();

	}

	/*
	 * validarConformeActa.java
	 * @descripcion Metodo que devuelve la vista a presentar en pantalla
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/validarConformeActa", method = RequestMethod.POST)
	public @ResponseBody String validarConformeActa(
			@RequestBody String data, HttpSession sesion,
			HttpServletRequest request) throws GeneralException,
			JsonParseException, JsonMappingException, IOException,
			JSONException {
		log.info("*************Tipo de permiso************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "conforme_acta.html"));
		modelo.put("titulo", "Verificaci\u00f3n del (de la) declarante");
		JSONObject respuesta = new JSONObject();
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	/*
	 * conformeRC.java
	 * @descripcion Metodo que devuelve la vista a presentar en pantalla
	 * @version 1.0 11/6/2016
	 * @author Dairene Ramirez
	/*
	 * @param data String que contiene los daos del modelo
	 * @return Html que va a mostrar
	 */
	@RequestMapping(value = "/validarConformeRC", method = RequestMethod.POST)
	public @ResponseBody String validarConformeRC(
			@RequestBody String data, HttpSession sesion,
			HttpServletRequest request) throws GeneralException,
			JsonParseException, JsonMappingException, IOException,
			JSONException {
		log.info("*************Tipo de permiso************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		// String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "conforme_RC.html"));
		modelo.put("titulo", "Verificaci\u00f3n del (de la) R.C");
		JSONObject respuesta = new JSONObject();
		respuesta.put("vista", htmlValido);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	
	/*
	 * validarReporteConformeORE.java
	 * @descripcion Metodo que actualiza el cambio de estatus
	 * @version 1.0 11/6/2016 
	 * @author Dairene Ramirez
	/*
	 * @param numCertificado String numero de certificado
	 * @return Solicitud soli
	 */
	@RequestMapping(value = "/actualizarEstado", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Solicitud actualizarEstatuSolicitud(@RequestBody String data,
			HttpSession session, HttpServletResponse response) throws Exception {
		JSONObject dataModelo = new JSONObject(data);
		// JSONObject pojoJson = dataModelo.getJSONObject("pojo");
		DefuncionServicioCliente servicioCliente = new DefuncionServicioCliente();
		String numSolicitud = dataModelo.getString("id");
		String estatus = dataModelo.getString("estatus");
		String parametro = estatus;
		Solicitud solicitudSesion = new Solicitud();

		if (("AB").equals(parametro)) {
			JSONObject pojoJson = dataModelo.getJSONObject("pojo");
			String insercion = pojoJson.getString("insercion");
			String fallecidoId = pojoJson.getString("estaFallecidoConID");
			String ev14 = pojoJson.getString("estaFallecidoEV14");
			String venezolano = pojoJson.getString("vzlanoOExtrajFallecidoDentroTN");
			Iterator<String> iterador = pojoJson.keys();
			Object obj = null;
			boolean flagNC = false;
			do {
				String campo = iterador.next();
				if ("numeroCertificadoDef".equalsIgnoreCase(campo)) {
					flagNC = true;
				}
			} while (iterador.hasNext());

			if ((venezolano.equals("true")) && (ev14.equals("false"))) {
				solicitudSesion.setNumeroSolicitud(numSolicitud);
				solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
				solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");
			} else if ((insercion.equals("true"))) {
				solicitudSesion.setNumeroSolicitud(numSolicitud);
				solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
				solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");
			} else if ((venezolano.equals("false"))) {
				solicitudSesion.setNumeroSolicitud(numSolicitud);
				solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
				solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");
			}else if ((fallecidoId.equals("false"))) {
				solicitudSesion.setNumeroSolicitud(numSolicitud);
				solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
				solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");
			}if ((ev14.equals("true")) && flagNC == false) {
				solicitudSesion.setNumeroSolicitud(numSolicitud);
				solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
				solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");
			}
			if (flagNC == true) {
				String numeroCertificadoDef = dataModelo.getString("variableCertif");
				if ((ev14.equals("true"))&& (dataModelo.getString("variableCertif").equals("false"))) {
					solicitudSesion.setNumeroSolicitud(numSolicitud);
					solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
					solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");
				} else if ((ev14.equals("true"))&& (dataModelo.getString("variableCertif").equals("true"))) {
					solicitudSesion.setNumeroSolicitud(numSolicitud);
					solicitudSesion.setEstadoSolicitud(Constantes.CANCELADA);
					solicitudSesion.setMotivoCambioEstado("CANCELADA");
				}
			}
		} else if (("EA").equals(parametro)) {
			solicitudSesion.setNumeroSolicitud(numSolicitud);
			solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
			solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");
		}
		if (("PV").equals(parametro)) {
			if (("conforme").equals(dataModelo.getString("permiso"))) {
				solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR);
				solicitudSesion.setMotivoCambioEstado("CAMBIO DE ESTADO");
				solicitudSesion.setNumeroSolicitud(numSolicitud);
			} else if (("noConforme").equals(dataModelo.getString("permiso"))) {
				solicitudSesion.setEstadoSolicitud(Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL);
				solicitudSesion.setMotivoCambioEstado(dataModelo.getString("observaciones"));
				solicitudSesion.setNumeroSolicitud(numSolicitud);
			}
		} else if (("NC").equals(parametro)) {
			solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
			solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");
			solicitudSesion.setNumeroSolicitud(numSolicitud);
		} else if (("PI").equals(parametro.toUpperCase())) {
			solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO);
			solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOCUMENTO");
			solicitudSesion.setNumeroSolicitud(numSolicitud);
		} else if (("PEA").equals(dataModelo.get("estatus"))&& ("conforme").equals(dataModelo.get("acta"))) {
			solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
			solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC_ACTA); 
			solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC_ACTA");
		} else if (("NCA").equals(parametro)) {
			solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
			solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR_ACTA);
			solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_IMPRIMIR_ACTA");
		} else if (("PVR").equals(dataModelo.get("estatus"))&& ("conforme").equals(dataModelo.get("permiso"))) {
			solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
			solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR_ACTA);
			solicitudSesion.setMotivoCambioEstado("CAMBIO DE ESTADO");
		}  else if (("PVR").equals(dataModelo.get("estatus"))&& ("noConforme").equals(dataModelo.get("permiso"))) {
			solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
			solicitudSesion.setEstadoSolicitud(Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL_ACTA);
			solicitudSesion.setMotivoCambioEstado("NO_CONFORME_POR_REGISTRADOR_CIVIL_ACTA");
		} else if (("PPD").equals(parametro.toUpperCase())) {
			solicitudSesion.setEstadoSolicitud(Constantes.CANCELADA);
			solicitudSesion.setMotivoCambioEstado("CANCELADA");
			solicitudSesion.setNumeroSolicitud(numSolicitud);
		}else if (("PPI").equals(parametro.toUpperCase())) {
			solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOC_ACTA);
			solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOC_ACTA");
			solicitudSesion.setNumeroSolicitud(numSolicitud);
		}else if (("PCA").equals(parametro.toUpperCase())){
				solicitudSesion.setEstadoSolicitud(Constantes.CERRADA);
				solicitudSesion.setMotivoCambioEstado("CERRADA");
				solicitudSesion.setNumeroSolicitud(numSolicitud);
		}		
		SolicitudServicio solicitudActualizar = new SolicitudServicio();

		Solicitud soli = solicitudActualizar.actualizaEstadoSolicitud(solicitudSesion);
		return soli;
	}
	
	public String consultarFecDef(@RequestBody String data) throws JsonGenerationException, JsonMappingException, IOException, JSONException {
		List<Participante> obetenerDatos = new ArrayList<Participante>();
		JSONObject dataModelo = new JSONObject(data);
		String numSolicitud = dataModelo.getString("id");
		ve.gob.cne.sarc.comunes.defuncion.Defuncion defInscrip = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		defInscrip = this.consultarDef(sol.getNumeroSolicitud());
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String fechaFallecimiento = "_";
		String fechaString = "";
		try {
			fechaString = defInscrip.getFechaDefuncion().toString();
			log.info("fecha de fallecimiento---->" + fechaString);
		} catch (Exception e) {
		}
		return fechaString;
	}

	public String buscarHtml(String estatus) {
		log.info("estat" + estatus);
		String html = null;
		if ((Constantes.ROL_REGISTRADOR).equals("R_REG")) { // *******
			if (("ABIERTA").equals(estatus)) {
				html = "gate0";
			} else if (("PENDIENTE POR ELABORAR ACTA").equals(estatus)) {
				html = "datos_fallecido";
			} else if (("PENDIENTE POR VERIFICAR R.C").equals(estatus)) {
				html = "vista_oficio";
			} else if (("PENDIENTE_POR_VERIFICAR_RC_ACTA").equals(estatus)) {
				html = "vista_acta";
			}else if (("NO CONFORME POR REGISTRADOR CIVIL").equals(estatus)) {
				html = "ver_observaciones";
			} else if (("PENDIENTE POR CARGAR DOCUMENTO").equals(estatus)) {
				html = "cargar_documento";
			} else if (("PENDIENTE POR CARGAR DOCUMENTO ACTA").equals(estatus)) {
				html = "cargar_documento_acta";
			}else if (("NO CONFORME POR REGISTRADOR CIVIL ACTA").equals(estatus)) {
				html = "ver_observacionesActa";
			}

		}

		log.info("busca html: " + html);
		return html;
	}

	/**
	 *Funcion que genera el reporte de un permiso de inhumacion y/o cremacion
	 * @author Maria Marsicano
	 * @param data: contiene los datos obtenidos del formulario para generar el reporte
	 * @return JsonObject
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws ParseException 
	 * @throws JsonParseException 
	 * @throws GeneralException 
	 */

	public JSONObject generarReporte(@RequestBody String data, String modelo, String estatus, String token) throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException, ParseException {
		// TODO Auto-generated method stub
	
        String rutaImg = context.getRealPath(rutaLogo);
        log.info("imagen=====> "+rutaImg);
		String rutaFin = null;
		String vista = null;

		JSONObject modelo1 = new JSONObject(data);
		List<Participante> obetenerDatos = new ArrayList<Participante>();
		List<Participante> obetenerDatosPart = new ArrayList<Participante>();
		JSONObject dataModelo = new JSONObject(data);
		JSONObject pojoJson = new JSONObject(data);
		String numSolicitud = dataModelo.getString("id");
		String[] codTipo = {"FAL"};
		
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		ParticipanteServicioCliente participante = new ParticipanteServicioCliente();
		//	DefuncionServicioCliente defuncion = new DefuncionServicioCliente();

		obetenerDatos = participante.consultarParticPorSolicitud(numSolicitud, "D");		
		obetenerDatosPart = participante.consultarParticPorTipo(numSolicitud, codTipo);
		//	fecha = defuncion.validarCertificadoMedicoDefuncion(numCertificado);

		String PrimerNombresAutoriza= null;
		PrimerNombresAutoriza = obetenerDatos.get(0).getPrimerNombre();
		String SegundoNombresAutoriza = null;
		SegundoNombresAutoriza = obetenerDatos.get(0).getSegundoNombre()==null?" ": obetenerDatos.get(0).getSegundoNombre();

		String ApellidosAutoriza = null;
		ApellidosAutoriza = obetenerDatos.get(0).getPrimerApellido();
		String SegundoApellidosAutoriza = null;
		SegundoApellidosAutoriza = obetenerDatos.get(0).getSegundoApellido()==null?" ": obetenerDatos.get(0).getSegundoApellido();

		String PrimerNombresPart= null;
		PrimerNombresPart = obetenerDatosPart.get(0).getPrimerNombre();
		String SegundoNombresPart = null;
		SegundoNombresPart = obetenerDatosPart.get(0).getSegundoNombre()==null?" ": obetenerDatosPart.get(0).getSegundoNombre();

		String ApellidosPart = null;
		ApellidosAutoriza = obetenerDatosPart.get(0).getPrimerApellido();
		String SegundoApellidosPart = null;
		SegundoApellidosPart = obetenerDatosPart.get(0).getSegundoApellido()==null?" ": obetenerDatosPart.get(0).getSegundoApellido();

		Funcionario dataServicio = new Funcionario();  
		log.info(token);
		String login = seguridadCliente.getUsernameCliente(token);
		Funcionario obtenerDatosFuncionario = funcionarioServicioCliente.buscarPorLogin(login);
		String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
		List<Participante> part = new ArrayList<>();
		String codTipoFuncionario = "RP";
		String codEstatusFuncionario = "ACT";
		//String login = seguridadCliente.getUsernameCliente(token);
		dataServicio = funcionarioServicioCliente.buscarFuncionarioPorOficina(codOficina, codTipoFuncionario, codEstatusFuncionario);
		Oficina datosOficina = new Oficina();
		
		datosOficina = obtenerDatosFuncionario.getOficina();
		ve.gob.cne.sarc.comunes.defuncion.Defuncion defInscrip = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();

		defInscrip = this.consultarDef(sol.getNumeroSolicitud());
		Date fechaString = defInscrip.getFechaDefuncion();
		try {
		} catch (Exception e) {
		}
		DateFormat outputFormatDef = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String fechaD = outputFormatDef.format(fechaString);

		JSONObject formulario=new JSONObject();
		HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
		Ore dataConsulta =this.consultarDatos(modelo);
		String fechaResolucion = formatDate.convertirDateAString(dataServicio.getFechaResolucion());
        String fechaGaceta = formatDate.convertirDateAString(dataServicio.getFechaGaceta());
	//	ActaPrimigenia datosActa = this.consultarDatosActa(modelo);
	    datosAPintar.put("lugarFecha", obtenerDatosFuncionario.getOficina().getDireccion().getEstado().getNombre()+", " +GenerarActas.obtenerFechaOrHoraActual("DIA")+" de "+GenerarActas.obtenerFechaOrHoraActual("STRING_MES")+" del "+GenerarActas.obtenerFechaOrHoraActual("ANIO"));
	//	datosAPintar.put("numeroConsecutivo", String.valueOf(datosActa.getNumeroConsecutivo()));
	    datosAPintar.put("numeroConsecutivo", "2415");
		datosAPintar.put("nombreDirectorOficina", StringUtils.capitalize(dataServicio.getPrimerNombre().toLowerCase())+' ' +StringUtils.capitalize(dataServicio.getPrimerApellido().toLowerCase()));
		datosAPintar.put("fecha", GenerarActas.obtenerFechaOrHoraActual("DIA")+"/"+GenerarActas.obtenerFechaOrHoraActual("NUM_MES")+"/"+GenerarActas.obtenerFechaOrHoraActual("ANIO"));
		datosAPintar.put("estadoOficina", StringUtils.capitalize(datosOficina.getDireccion().getEstado().getNombre().toLowerCase()));
		datosAPintar.put("nombreCiudadano", PrimerNombresAutoriza+' '+SegundoNombresAutoriza+' '+ApellidosAutoriza+' '+SegundoApellidosAutoriza);
		datosAPintar.put("cedulaCiudadano", obetenerDatos.get(0).getDocumentoIdentidad().get(0).getNumero());
		datosAPintar.put("nombreDifunto", PrimerNombresPart+' '+SegundoNombresPart+' '+ApellidosPart+' '+SegundoApellidosPart);
		datosAPintar.put("cedulaFallecido", obetenerDatosPart.get(0).getDocumentoIdentidad().get(0).getNumero());
		datosAPintar.put("fechaFallecimiento", fechaD);
		datosAPintar.put("oficinaRegistro", (datosOficina.getNombre().toUpperCase()));
		datosAPintar.put("nResolucion", String.valueOf(dataServicio.getNumeroResolucion())); 		
		datosAPintar.put("fResolucion",  fechaGaceta); 		
     	datosAPintar.put("nGaceta",  String.valueOf(dataServicio.getNumeroGaceta()));     	
		datosAPintar.put("fGaceta", fechaResolucion); 
		datosAPintar.put("rutaImg", rutaImg);
				

		String rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_DEFUNCION);
		//rutaFin = rutaPlantilla.replace(PLANTILLA_DEFUNCION,modelo+".pdf");
		rutaFin = RUTA_FIN + modelo+".pdf";
		//String rutaReportes=obtenerEndPointConfig("endPointRutaReportes");

		JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar,  new JREmptyDataSource()); 
		JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);
		String template="<iframe   id='plugin' width='800'  height='800' src='"+ "/web-registrarDefuncion/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
		//String template="<embed width='850' height='550' src='"+ rutaReportes +  modelo + ".pdf' type='application/pdf'></embed>";
		if("PI".equals(estatus)){
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA));
		}else if("PV".equals(estatus)){
			vista = Util.leerArchivo(context.getRealPath(RUTA_PV));
		}		 
        vista = vista.replace("ARCHIVOPDFOFICIO", template);
        
        log.info("------------> " + vista);
        
        formulario.put("vista", vista);
        
        return formulario;
	}

	@RequestMapping(value = "/iniciarActa", method = RequestMethod.POST)
	public @ResponseBody String iniciarActa(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException, JRException {
		log.info("*************Tipo de permiso******4234234234324******" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		
		
		
//		List<Participante> particiSolicitud;
//		particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");
//		ObjectMapper mapper1 = new ObjectMapper();
//		
//		for (Participante participante1 : particiSolicitud) {
//			JSONObject data1 = new JSONObject(mapper1.writeValueAsString(participante1));
//			
//			
//			modelo.put(participante1.getRol(), data1);
//			
//		}
//		log.info("*************Tipo de permiso************" + sesion.getId());
		
		
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "vista_acta.html"));
		modelo.put("titulo", "Vista previa acta");
		JSONObject respuesta = new JSONObject();
		log.info(modelo.toString());
		JSONObject modeloReporte = vistaPreviaActa(numSolicitud,modelo.getString("estatus"),modelo);

		respuesta.put("modelo", modelo);
		respuesta.put("vista", modeloReporte.get("vista"));	
		return respuesta.toString();
	}

	public JSONObject vistaPreviaActa(String modelo, String estatus, JSONObject modeloCompleto ) throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException {
		// TODO Auto-generated method stub
		String rutaImg = context.getRealPath(rutaLogo);
		log.info("******modelo reporte*****"+modelo);
		log.info("******statu reporte*****"+estatus);
		log.info(modeloCompleto.toString());
		String rutaFin = null;
		String rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_ACTA);
		rutaFin = RUTA_FIN + modelo+".pdf"; //rutaFin = rutaPlantilla.replace(PLANTILLA_ACTA,modelo+".pdf");
		//String rutaReportes=obtenerEndPointConfigActa("endPointRutaReportesActa");
		String vista = null;
		
		String estadoDocumento = "";
		String municipioDocumento = "";
		String parroquiaDocumento = "";
		try{
			if (modeloCompleto.get("estado") !=null){
				estadoDocumento = modeloCompleto.getString("estado");
			}}catch(Exception e){}
		try{
			if (modeloCompleto.get("municipio") !=null){
				municipioDocumento = modeloCompleto.getString("municipio");
			}}catch(Exception e){}
		try{
			if (modeloCompleto.get("parroquia") !=null){
				parroquiaDocumento = modeloCompleto.getString("parroquia");
			}}catch(Exception e){}
		JSONObject auto = modeloCompleto.getJSONObject("AUTORIDAD");
		String segundoNombreAutoridad = "";
		try{
			if(auto.getString("segundoNombre") != null){
				segundoNombreAutoridad = auto.getString("segundoNombre"); 
			}
		}catch(Exception e){}
		String segundoApellidoAutoridad = "";
		try{
			if(auto.getString("segundoApellido") != null){
				segundoApellidoAutoridad = auto.getString("segundoApellido"); 
			}
		} catch (Exception e) {
		}
		String fechaResolucion = auto.getJSONObject("fechaCert").getString(
				"dia2")
				+ "-"
				+ auto.getJSONObject("fechaCert").getJSONObject("mes2")
						.getString("numero")
				+ "-"
				+ auto.getJSONObject("fechaCert").getString("ano2");

		JSONObject FAL = modeloCompleto.getJSONObject("FAL");
		String segundoNombreFAL = "";
		try{
			if(FAL.getString("segundoNombre") != null){
				segundoNombreFAL = FAL.getString("segundoNombre"); 
			}
		}catch(Exception e){}
		String segundoApellidoFAL = "";
		try{
			if(FAL.getString("segundoApellido") != null){
				segundoApellidoFAL = FAL.getString("segundoApellido"); 
			}
		}catch(Exception e){}
		long num = FAL.getLong("fechaNacimiento");
		Calendar c = Calendar.getInstance();
		
		c.setTimeInMillis(num);
		int mesNacimientoFal = (c.get(Calendar.MONTH)+1);
		int diaNacimientoFal = c.get(Calendar.DAY_OF_WEEK);
		int annoNacimientoFal = c.get(Calendar.YEAR);
		StringBuilder sbd = new StringBuilder();
		String diaNacimientoFallecido = sbd.append(diaNacimientoFal).toString();
		StringBuilder sbm = new StringBuilder();
		String mesNacimientoFallecido = sbm.append(mesNacimientoFal).toString();
		StringBuilder sba = new StringBuilder();
		String annoNacimientoFallecido = sba.append(annoNacimientoFal).toString();
		
		
		
		boolean tipoDocFal;
		if(FAL.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("codigo") == "CED")
			tipoDocFal = true;
		else
			tipoDocFal = false;
		String comunidadIndigena = "";
		try{
			if(FAL.getJSONObject("comunidadIndigena").getString("nombre") != null){
				comunidadIndigena = FAL.getJSONObject("comunidadIndigena").getString("nombre"); 
			}
		}catch(Exception e){
			comunidadIndigena = "N/A";
		}
		
		JSONObject datosFAL = modeloCompleto.getJSONObject("datosFAL");
		
		String parroquiaFallecido = "";
		try{
			if(datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("parroquia").getString("nombre") != null)
			{
				parroquiaFallecido = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("parroquia").getString("nombre");
			}

		}catch(Exception e){}
		String municipioFallecido = "";
		try{
			if(datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("municipio").getString("nombre") != null)
			{
				municipioFallecido = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("municipio").getString("nombre");
			}

		}catch(Exception e){}
		String estadoFallecido = "";
		try{
			if(datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("estado").getString("nombre") != null)
			{
				estadoFallecido = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("estado").getString("nombre");
			}

		}catch(Exception e){}
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		Date fechaFallecimiento = new Date();
		try {
			fechaFallecimiento = inputFormat.parse(modeloCompleto.getString("fechaDefuncion"));
		} catch (Exception e) {
			}
		Calendar a = Calendar.getInstance();
		a.setTime(fechaFallecimiento);
		int diaDef= a.get(Calendar.DAY_OF_WEEK);
		StringBuilder diaSB = new StringBuilder();
		String diaDefuncion = diaSB.append(diaDef).toString();
		
		int mesDef= a.get(Calendar.MONTH);
		StringBuilder mesSB = new StringBuilder();
		String mesDefuncion = mesSB.append(mesDef).toString();
		
		int annoDef= a.get(Calendar.YEAR);
		StringBuilder annoSB = new StringBuilder();
		String annoDefuncion = annoSB.append(annoDef).toString();
		
		int horDef= a.get(Calendar.HOUR);
		StringBuilder horSB = new StringBuilder();
		String horaDefuncion = horSB.append(horDef).toString();
		
		int minDef= a.get(Calendar.MINUTE);
		StringBuilder minSB = new StringBuilder();
		String minutosDefuncion = minSB.append(minDef).toString();
		
		boolean am_pm;
		StringBuilder am_pmSB = new StringBuilder();
		String am = am_pmSB.append(a.get(Calendar.AM)).toString();
		if(am=="1")
			am_pm = true;
		else
			am_pm = false;
		
		String nombreAutoridadExpide = "";
		String segNom = "";
		String segApe = "";
		try{
			if(modeloCompleto.getJSONObject("AUTORIDAD").getString("segundoNombre") != null)
				segNom = modeloCompleto.getJSONObject("AUTORIDAD").getString("segundoNombre");
		}catch(Exception e){}
		try{
			if(modeloCompleto.getJSONObject("AUTORIDAD").getString("segundoApellido") != null)
				segApe = modeloCompleto.getJSONObject("AUTORIDAD").getString("segundoApellido");
		}catch(Exception e){}
		nombreAutoridadExpide = modeloCompleto.getJSONObject("AUTORIDAD").getString("primerNombre") + " " + segNom + " " + modeloCompleto.getJSONObject("AUTORIDAD").getString("primerApellido") + " " + segApe;
		
		String nombreCompletoPareja = "";
		String segNomPar = "";
		String segApePar = "";
		boolean viveCon = false;
		try{
			if(modeloCompleto.getJSONObject("COUNI") != null){
				try{
					if(modeloCompleto.getJSONObject("COUNI").getString("segundoNombre") != null){
						segNomPar = modeloCompleto.getJSONObject("COUNI").getString("segundoNombre");
				}
					}catch(Exception e){}
				try{
					if(modeloCompleto.getJSONObject("COUNI").getString("segundoApellido") != null){
						segApePar = modeloCompleto.getJSONObject("COUNI").getString("segundoApellido");
				}
					}catch(Exception e){}
				
				nombreCompletoPareja = modeloCompleto.getJSONObject("COUNI").getString("primerNombre") + " " + segNomPar + " " + modeloCompleto.getJSONObject("COUNI").getString("primerApellido")+ " " + segApePar;
			}
		}catch (Exception e){}
		try{
			if(modeloCompleto.getJSONObject("COUNI") != null){
				viveCon = modeloCompleto.getJSONObject("COUNI").getBoolean("vive");
			}
		}catch(Exception e){}
		String documentoIdentidadPareja = "";
		boolean tipoDocumentoPareja = true;
		try{
			if(modeloCompleto.getJSONObject("COUNI") != null){
				documentoIdentidadPareja = modeloCompleto.getJSONObject("COUNI").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
				if(modeloCompleto.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("codigo") == "CED")
					tipoDocumentoPareja = true;
				else
					tipoDocumentoPareja = false;
			}
		}catch(Exception e){}
		String profesionPareja = "";
		try{
			if(modeloCompleto.getJSONObject("COUNI") != null)
				profesionPareja = modeloCompleto.getJSONObject("COUNI").getJSONObject("profesion").getString("nombre");
		}catch(Exception e){}
		String nacionalidadPareja = "";
		try{
			if(modeloCompleto.getJSONObject("COUNI").getString("nacionalidad") != null)
				nacionalidadPareja = modeloCompleto.getJSONObject("COUNI").getString("nacionalidad");
		}catch(Exception e){}
		String residenciaPareja = "";
		try{
			if(modeloCompleto.getJSONObject("COUNI").getString("ubicacion") != null)
				residenciaPareja = modeloCompleto.getJSONObject("COUNI").getString("ubicacion");
			log.info("residencia del conyugue----->"+modeloCompleto.getJSONObject("COUNI").getString("ubicacion"));
		}catch(Exception e){}
		
		String nombreCompletoHijo = "";
		String segNomHij = "";
		String segApeHij = "";
		try{
			if(modeloCompleto.getJSONObject("HIJ1") != null){
				try{
					if(modeloCompleto.getJSONObject("HIJ1").getString("segundoNombre") != null){
						segNomHij = modeloCompleto.getJSONObject("HIJ1").getString("segundoNombre");
				}
					}catch(Exception e){}
				try{
					if(modeloCompleto.getJSONObject("HIJ1").getString("segundoApellido") != null){
						segApeHij = modeloCompleto.getJSONObject("HIJ1").getString("segundoApellido");
				}
					}catch(Exception e){}
				
				nombreCompletoHijo = modeloCompleto.getJSONObject("HIJ1").getString("primerNombre") + " " + segNomHij + " " + modeloCompleto.getJSONObject("HIJ1").getString("primerApellido")+ " " + segApeHij;
			}
		}catch (Exception e){}
		String documentoIdentidadHijo = "";
		boolean viveHijo = true;
		try{
			if(modeloCompleto.getJSONObject("HIJ1") != null){
				documentoIdentidadHijo = modeloCompleto.getJSONObject("HIJ1").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
				viveHijo = modeloCompleto.getJSONObject("HIJ1").getBoolean("vive");
			}
		}catch(Exception e){}
		String edadHijo = "";
		try{
			if(modeloCompleto.getJSONObject("HIJ1") != null){
				edadHijo = modeloCompleto.getJSONObject("HIJ1").getString("edad");
				
			}
		}catch(Exception e){}
		String nombreCompletoDeclarante = "N/A";
		String segundoNombreDeclarante = "";
		String segundoApellidoDeclarante = "";
		try{
			if(modeloCompleto.getJSONObject("DEC").getString("segundoNombre") != null)
				segundoNombreDeclarante = modeloCompleto.getJSONObject("DEC").getString("segundoNombre");
		}catch(Exception e){}
		try{
			if(modeloCompleto.getJSONObject("DEC").getString("segundoApellido") != null)
				segundoApellidoDeclarante = modeloCompleto.getJSONObject("DEC").getString("segundoApellido");
		}catch(Exception e){}
		nombreCompletoDeclarante = modeloCompleto.getJSONObject("DEC").getString("primerNombre") + " " + segundoNombreDeclarante + " " +  modeloCompleto.getJSONObject("DEC").getString("primerApellido") + " " + segundoApellidoDeclarante;
		boolean tipoDocumentoDeclarante = true;
		if(modeloCompleto.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("codigo")=="CED")
			tipoDocumentoDeclarante = true;
		else
			tipoDocumentoDeclarante = false;
		String nacionalidadDeclarante = "";
		try{
			if(modeloCompleto.getJSONObject("DEC").getString("nacionalidad") != "null"){
				nacionalidadDeclarante = modeloCompleto.getJSONObject("DEC").getString("nacionalidad");
					log.info("nacionalidad del declarante----->"+modeloCompleto.getJSONObject("DEC").getString("nacionalidad"));
				}
			else
			{
				log.info("nacionalidad del declarante----->"+modeloCompleto.getJSONObject("DEC").getString("nacionalidad"));
				nacionalidadDeclarante = " ";
			}
			}catch(Exception e){
			
		}
		String autoridadExtracto = "";
		String segEC = "";
		String segECA = "";
		String extractoNumero = "N/A";
		String diaExpedicionExtracto = "N/A";
		String mesExpedicionExtracto = "N/A";
		String annoExpedicionExtracto = "N/A";
		
		try{
			if(modeloCompleto.getJSONObject("EC") != null){
				extractoNumero = modeloCompleto.getJSONObject("EC").getString("numero");
				diaExpedicionExtracto = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getString("dia2");
				mesExpedicionExtracto = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getJSONObject("mes2").getString("numero");
				annoExpedicionExtracto = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getString("ano2");
			}
		}catch(Exception e){
			extractoNumero = "N/A";
			diaExpedicionExtracto = "N/A";
			mesExpedicionExtracto = "N/A";
			annoExpedicionExtracto = "N/A";			
		}
	
		try{
			if(modeloCompleto.getJSONObject("EC").getString("segundoNombre") != null){
				segEC = modeloCompleto.getJSONObject("EC").getString("segundoNombre");
			}			
		}catch(Exception e){
			segEC = "N/A";			
		}
		try{
			if(modeloCompleto.getJSONObject("EC").getString("segundoApellido") != null){				
				segECA = modeloCompleto.getJSONObject("EC").getString("segundoApellido");
			}
		}catch(Exception e){
			segECA = "N/A";			
		}
		
		try{
			if(modeloCompleto.getJSONObject("EC").getString("primerNombre") != null && modeloCompleto.getJSONObject("EC").getString("primerApellido") != null){				
				autoridadExtracto = modeloCompleto.getJSONObject("EC").getString("primerNombre") + " " + segEC + " " + modeloCompleto.getJSONObject("EC").getString("primerApellido") + " " + segECA; 
			}
		}catch(Exception e){
			segECA = "N/A";		
		}
		String nombreJuezSentencia = "N/A";
		String priIDJ = "";
		String priIDJA = "";
		String segIDJ = "";
		String segIDJA = "";
		String tribunalJuzgado = "N/A";
		String sentenciaIDJ = "N/A";
		String fechaDia = "N/A";
		String fechaMes = "N/A";
		String fechaAnno = "N/A";
		try{
			if(modeloCompleto.getJSONObject("IDJ") != null){
				tribunalJuzgado = modeloCompleto.getJSONObject("IDJ").getString("tribunal");
				sentenciaIDJ = modeloCompleto.getJSONObject("IDJ").getString("sentencia");
				fechaDia = modeloCompleto.getJSONObject("IDJ").getJSONObject("fecha").getString("dia2");
				fechaMes = modeloCompleto.getJSONObject("IDJ").getJSONObject("fecha").getJSONObject("mes2").getString("numero");
				fechaAnno = modeloCompleto.getJSONObject("IDJ").getJSONObject("fecha").getString("ano2");
			}
		}catch(Exception e){
			tribunalJuzgado = "N/A";
			sentenciaIDJ = "N/A";
			fechaDia = "N/A";
			fechaMes = "N/A";
			fechaAnno = "N/A";			
		}
		try{
			if(modeloCompleto.getJSONObject("IDJ").getString("primerNombre") != null){				
				priIDJ = modeloCompleto.getJSONObject("IDJ").getString("primerNombre");
			}
		}catch(Exception e){
			priIDJ = "N/A";			
		}
		try{
			if(modeloCompleto.getJSONObject("IDJ").getString("primerApellido") != null){			
				priIDJA = modeloCompleto.getJSONObject("IDJ").getString("primerApellido");
			}
		}catch(Exception e){	
		}
		try{
			if(modeloCompleto.getJSONObject("IDJ").getString("segundoNombre") != null){				
				segIDJ = modeloCompleto.getJSONObject("IDJ").getString("segundoNombre");
			}
		}catch(Exception e){
			
		}
		try{
			if(modeloCompleto.getJSONObject("IDJ").getString("segundoApellido") != null){				
				segIDJA = modeloCompleto.getJSONObject("IDJ").getString("segundoApellido");
			}		
		}catch(Exception e){	
		}
		
		try{
			if(priIDJ != null && priIDJA != null){				
				nombreJuezSentencia = priIDJ + " " + segIDJ + " " + priIDJA + " " + segIDJA; 
			}		
		}catch(Exception e){
			//nombreJuezSentencia = "N/A";
		}

		String nombreTestigo1 = "N/A";
		String segT1 = "";
		String segT1A = "";
		try{
			if(modeloCompleto.getJSONObject("TA1").getString("segundoNombre") != null)
				segT1 = modeloCompleto.getJSONObject("TA1").getString("segundoNombre");
		}catch(Exception e){
			
		}
		try{
			if(modeloCompleto.getJSONObject("TA1").getString("segundoApellido") != null)
				segT1A = modeloCompleto.getJSONObject("TA1").getString("segundoApellido");
		}catch(Exception e){
			
		}
		nombreTestigo1 = modeloCompleto.getJSONObject("TA1").getString("primerNombre") + " " + segT1 + " " + modeloCompleto.getJSONObject("TA1").getString("primerApellido") + " " + segT1A; 
		String nacionalidadTestigo1 = "";
		try{
			if(modeloCompleto.getJSONObject("TA1").getString("nacionalidad") != "null"){
					nacionalidadTestigo1 = modeloCompleto.getJSONObject("TA1").getString("nacionalidad");
					log.info("nacionalidad del TA1----->"+modeloCompleto.getJSONObject("TA1").getString("nacionalidad"));
				}
			else
			{
				log.info("nacionalidad del TA1----->"+modeloCompleto.getJSONObject("TA1").getString("nacionalidad"));
				nacionalidadTestigo1 = " ";
			}
			}catch(Exception e){
			
		}

		String nombreTestigo2 = "N/A";
		String segT2 = "";
		String segT2A = "";
		try{
			if(modeloCompleto.getJSONObject("TA2").getString("segundoNombre") != null)
				segT2 = modeloCompleto.getJSONObject("TA2").getString("segundoNombre");
		}catch(Exception e){
			
		}
		try{
			if(modeloCompleto.getJSONObject("TA2").getString("segundoApellido") != null)
				segT2A = modeloCompleto.getJSONObject("TA2").getString("segundoApellido");
		}catch(Exception e){
			
		}
		nombreTestigo2 = modeloCompleto.getJSONObject("TA2").getString("primerNombre") + " " + segT2 + " " + modeloCompleto.getJSONObject("TA2").getString("primerApellido") + " " + segT2A; 
		String nacionalidadTestigo2 = "";
		try{
			if(modeloCompleto.getJSONObject("TA2").getString("nacionalidad") != "null"){
				nacionalidadTestigo2 = modeloCompleto.getJSONObject("TA2").getString("nacionalidad");
					log.info("nacionalidad del TA2----->"+modeloCompleto.getJSONObject("TA2").getString("nacionalidad"));
				}
			else
			{
				log.info("nacionalidad del TA2----->"+modeloCompleto.getJSONObject("TA2").getString("nacionalidad"));
				nacionalidadTestigo2 = " ";
			}
			}catch(Exception e){
			
		}
		
		Date hoy = new Date();
		log.info(hoy.toString());
		String diaHoy = "";
		String mesHoy = "";
		String annoHoy = "";
		Calendar b = Calendar.getInstance();
		b.setTime(hoy);
		int diaHoyI= b.get(Calendar.DAY_OF_WEEK);
		StringBuilder diaSBI = new StringBuilder();
		diaHoy = diaSBI.append(diaHoyI).toString();
		
		int mesHoyI= b.get(Calendar.MONTH);
		StringBuilder mesSBI = new StringBuilder();
		mesHoy = mesSBI.append(mesHoyI).toString();
		
		int annoHoyI= b.get(Calendar.YEAR);
		StringBuilder annoSBI = new StringBuilder();
		annoHoy = annoSBI.append(annoHoyI).toString();
		
		ve.gob.cne.sarc.comunes.defuncion.Defuncion defInscrip = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		defInscrip = this.consultarDef(modelo);
		String obj1 = defInscrip.getNumeroActa();	
		log.info("objeto en formade string----.>" +  obj1);
		modeloCompleto.put("numeroActaDefuncion", obj1);
		
		String documentosPresentados = "";
		JSONObject docPresentadosCompletos = modeloCompleto.getJSONObject("recaudos");
		Iterator<?> keys = docPresentadosCompletos.keys();
		while(keys.hasNext() ) {
			String key = (String)keys.next();
			String value = docPresentadosCompletos.getString(key);
			log.info(value);
			documentosPresentados += value + ", ";
		}
		try{
			documentosPresentados = documentosPresentados.substring(0, documentosPresentados.length()-2);
		}catch(Exception e){}
		log.info("antes de cargar datos vemos que tiene el modelo------>" + modeloCompleto.toString());
		
		JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla);
		HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
		datosAPintar.put("rutaImg", rutaImg);
		datosAPintar.put("estadoDocumento",estadoDocumento);
		datosAPintar.put("municipioDocumento",municipioDocumento);
		datosAPintar.put("parroquiaDocumento",parroquiaDocumento);
		datosAPintar.put("actaDocumento",modeloCompleto.getString("numeroActaDefuncion"));
		datosAPintar.put("diaDocumento",diaHoy);
		datosAPintar.put("mesDocumento", mesHoy);
		datosAPintar.put("annoDocumento",annoHoy);
		datosAPintar.put("tipoRegistro","no esta en el modelo");
		datosAPintar.put("primerNombreRegistrador",auto.getString("primerNombre"));
		datosAPintar.put("segundoNombreRegistrador",segundoNombreAutoridad);
		datosAPintar.put("primerApellidoRegistrador",auto.getString("primerApellido"));
		datosAPintar.put("segundoApellidoRegistrador",segundoApellidoAutoridad);
		datosAPintar.put("documentoIdentidadRegistrador",auto.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero"));
		datosAPintar.put("oficinaRegistroCivil",auto.getString("centroSalud"));
		datosAPintar.put("numeroResolucion",auto.getString("numCert"));
		datosAPintar.put("fechaResolucion",fechaResolucion);
		datosAPintar.put("numeroGaceta",auto.get("numMPPS"));
		datosAPintar.put("tipoRegistrador",true);
		datosAPintar.put("primerNombreFallecido",FAL.getString("primerNombre"));
		datosAPintar.put("segundoNombreFallecido",segundoNombreFAL);
		datosAPintar.put("primerApellidoFallecido",FAL.getString("primerApellido"));
		datosAPintar.put("segundoApellidoFallecido",segundoApellidoFAL);
		datosAPintar.put("diaNacimientoFallecido",diaNacimientoFallecido);
		datosAPintar.put("mesNacimientoFallecido",mesNacimientoFallecido);
		datosAPintar.put("annoNacimientoFallecido",annoNacimientoFallecido);
		datosAPintar.put("lugarNacimientoFallecido",FAL.getString("lugarNacimiento"));
		datosAPintar.put("estadoCivilFallecido",FAL.getString("estadoCivil"));
		datosAPintar.put("edadFallecido",FAL.getString("edad"));
		datosAPintar.put("sexoFallecido",FAL.getString("sexo"));
		try
		{
			datosAPintar.put("documentoIdentidadFallecido",FAL.getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		}catch(Exception e){datosAPintar.put("documentoIdentidadFallecido","");}
		datosAPintar.put("tipoDocumentoFallecido",tipoDocFal);
		datosAPintar.put("nacionalidadFallecido",FAL.getString("nacionalidad"));
		datosAPintar.put("profesionFallecido",FAL.getJSONObject("profesion").getString("descripcion"));
		datosAPintar.put("comunidadIndigena", comunidadIndigena);
		datosAPintar.put("residenciaFallecido",FAL.getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
		datosAPintar.put("estadoFallecido",estadoFallecido);
		datosAPintar.put("parroquiaFallecido",parroquiaFallecido);
		datosAPintar.put("municipioFallecido",municipioFallecido);
		datosAPintar.put("diaDefuncion",diaDefuncion);
		datosAPintar.put("mesDefuncion",mesDefuncion);
		datosAPintar.put("annoDefuncion",annoDefuncion);
		datosAPintar.put("horaDefuncion",horaDefuncion + ":"+minutosDefuncion);
		datosAPintar.put("am_pm",am_pm);
		datosAPintar.put("paisDefuncion",datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("pais").getString("nombre"));
		datosAPintar.put("estadoDefuncion",estadoFallecido);
		datosAPintar.put("municipioDefuncion",municipioFallecido);
		datosAPintar.put("parroquiaDefuncion",parroquiaFallecido);
		datosAPintar.put("causasDefuncion",modeloCompleto.getString("causas"));
		datosAPintar.put("certficadoDefuncion",modeloCompleto.getJSONObject("AUTORIDAD").getString("numCert"));
		datosAPintar.put("diaExpedicionDefuncion",modeloCompleto.getJSONObject("AUTORIDAD").getJSONObject("fechaCert").getString("dia2"));
		datosAPintar.put("mesExpedicionDefuncion",modeloCompleto.getJSONObject("AUTORIDAD").getJSONObject("fechaCert").getJSONObject("mes2").getString("numero"));
		datosAPintar.put("annoExpedicionDefuncion",modeloCompleto.getJSONObject("AUTORIDAD").getJSONObject("fechaCert").getString("ano2"));
		datosAPintar.put("nombreAutoridadExpide",nombreAutoridadExpide);
		datosAPintar.put("documentoIdentidadAutoridad",modeloCompleto.getJSONObject("AUTORIDAD").getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero"));
		datosAPintar.put("numeroMPPS",modeloCompleto.getJSONObject("AUTORIDAD").getString("numMPPS"));
		datosAPintar.put("denominacionDependenciaSalud",modeloCompleto.getJSONObject("AUTORIDAD").getString("centroSalud"));
		datosAPintar.put("nombreCompletoPareja",nombreCompletoPareja);
		datosAPintar.put("vivePareja",viveCon);
		datosAPintar.put("documentoIdentidadPareja",documentoIdentidadPareja);
		datosAPintar.put("tipoDocumentoPareja",tipoDocumentoPareja);
		datosAPintar.put("profesionPareja",profesionPareja);
		datosAPintar.put("nacionalidadPareja",nacionalidadPareja);
		datosAPintar.put("residenciaPareja",residenciaPareja);
		datosAPintar.put("nombreCompletoHijo",nombreCompletoHijo);
		datosAPintar.put("documentoIdentidadHijo",documentoIdentidadHijo);
		datosAPintar.put("edadHijo",edadHijo);
		datosAPintar.put("viveHijo",viveHijo);
		datosAPintar.put("nombreCompletoDeclarante",nombreCompletoDeclarante);
		datosAPintar.put("documentoIdentidadDeclarante",modeloCompleto.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		datosAPintar.put("tipoDocumentoDeclarante",tipoDocumentoDeclarante);
		datosAPintar.put("edadDeclarante",modeloCompleto.getJSONObject("DEC").getString("edad"));
		datosAPintar.put("profecionDeclarante",modeloCompleto.getJSONObject("DEC").getJSONObject("profesion").getString("nombre"));
		datosAPintar.put("nacionalidadDeclarante",nacionalidadDeclarante);
		datosAPintar.put("residenciaDeclarante",modeloCompleto.getJSONObject("DEC").getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
		datosAPintar.put("extractoNumero", extractoNumero);
		datosAPintar.put("diaExpedicionExtracto", diaExpedicionExtracto);
		datosAPintar.put("mesExpedicionExtracto", mesExpedicionExtracto);
		datosAPintar.put("annoExpedicionExtracto", annoExpedicionExtracto);
		datosAPintar.put("autoridadExtracto",autoridadExtracto);
		datosAPintar.put("tribunalJuzgado",tribunalJuzgado);
		datosAPintar.put("numeroSentenciaJudicial",sentenciaIDJ);
		datosAPintar.put("nombreJuezSentencia",nombreJuezSentencia);
		datosAPintar.put("diaSentenciaJudicial",fechaDia);
		datosAPintar.put("mesSentenciaJudicial",fechaMes);
		datosAPintar.put("annoSentencialJudicial",fechaAnno);
		datosAPintar.put("nombreTestigo1",nombreTestigo1);
		datosAPintar.put("documentoIdentidadTestigo1",modeloCompleto.getJSONObject("TA1").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		datosAPintar.put("edadTestigo1",modeloCompleto.getJSONObject("TA1").getString("edad"));
		datosAPintar.put("profesionTestigo1",modeloCompleto.getJSONObject("TA1").getJSONObject("profesion").getString("nombre"));
		datosAPintar.put("nacionalidadTestigo1",nacionalidadTestigo1);
		datosAPintar.put("residenciaTestigo1", modeloCompleto.getJSONObject("TA1").getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
		datosAPintar.put("nombreTestigo2",nombreTestigo2);
		datosAPintar.put("documentoIdentidadTestigo2",modeloCompleto.getJSONObject("TA2").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		datosAPintar.put("edadTestigo2",modeloCompleto.getJSONObject("TA2").getString("edad"));
		datosAPintar.put("profesionTestigo2",modeloCompleto.getJSONObject("TA2").getJSONObject("profesion").getString("nombre"));
		datosAPintar.put("nacionalidadTestigo2",nacionalidadTestigo2);
		datosAPintar.put("residenciaTestigo2",modeloCompleto.getJSONObject("TA2").getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
		datosAPintar.put("insercionDefuncion",modeloCompleto.getBoolean("insercionDefuncion"));
		datosAPintar.put("documentosPresentados", documentosPresentados);
		
		List<item> listItems =  new ArrayList<item>();
		
			item hijoPrueba = new item();
			hijoPrueba.setNombreHijo("nombrePrueba");
			hijoPrueba.setCedulaHijo("cedulaPrueba");
			hijoPrueba.setEdadHijo("100");
			hijoPrueba.setViveHijo(true);
			listItems.add(hijoPrueba);
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
			datosAPintar.put("itemParameter", itemsJRBean);
		
		
		log.info(datosAPintar.toString());
		// Ore dataConsulta =this.consultarPermiso(modelo);

		//		 datosAPintar.put("numeroConsecutivo", "12345");
		//		 datosAPintar.put("nombreDirectorOficina", "Maria Marsicano");

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar,  new JREmptyDataSource()); 
		JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);
		String template="<iframe   id='plugin' width='800'  height='800' src='"+ "/web-registrarDefuncion/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
		vista = Util.leerArchivo(context.getRealPath(RUTA_ACTA)); 		
		vista = vista.replace("ARCHIVOPDFACTA", template);
		JSONObject formulario=new JSONObject();
		log.info("modelo completo el bueno---->" + modeloCompleto);
		formulario.put("vista", vista);
		formulario.put("modelo", modeloCompleto);
		formulario.put("datosPDF", datosAPintar);
		return formulario;
	}


	

	////////para el segundo estatus (PVR)
	
	@RequestMapping(value = "/iniciarActaPVR", method = RequestMethod.POST)
	public @ResponseBody String iniciarActaPVR(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException, JRException {
		log.info("esta area es impresion en estado PVR session---->" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		
		JSONObject modelo = new JSONObject(data);
		log.info("Veamos que tiene desde un principio-------->" + modelo.toString());
		String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		ObjectMapper mapper4 = new ObjectMapper();
		ve.gob.cne.sarc.comunes.defuncion.Defuncion DF = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		DF = this.consultarDef(numSolicitud);
		JSONObject ytal = new JSONObject(mapper4.writeValueAsString(DF));
		ytal.put("estatus", modelo.get("estatus"));
		log.info("objeto defuncion--->"+ytal.toString());
		JSONObject AUTORIDAD = new JSONObject();
		AUTORIDAD.put("numCert", ytal.get("numeroCertificado"));
		
		JSONObject fechaCert = new JSONObject();
		Long fec = ytal.getLong("fechaCertificado");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(fec);
		int mesNacimientoFal = (c.get(Calendar.MONTH)+1);
		int diaNacimientoFal = c.get(Calendar.DAY_OF_WEEK);
		int annoNacimientoFal = c.get(Calendar.YEAR);
		StringBuilder sbd = new StringBuilder();
		String diaNacimientoFallecido = sbd.append(diaNacimientoFal).toString();
		StringBuilder sbm = new StringBuilder();
		String mesNacimientoFallecido = sbm.append(mesNacimientoFal).toString();
		StringBuilder sba = new StringBuilder();
		String annoNacimientoFallecido = sba.append(annoNacimientoFal).toString();
		JSONObject mes2 = new JSONObject();
		mes2.put("numero", mesNacimientoFallecido);
		fechaCert.put("dia2", diaNacimientoFallecido);
		fechaCert.put("mes2", mes2);
		fechaCert.put("ano2", annoNacimientoFallecido);
		AUTORIDAD.put("fechaCert", fechaCert);
		JSONObject documentoIdentidad = new JSONObject();
		JSONObject cero = new JSONObject();
		cero.put("numero", ytal.getString("docIdenMedico"));
		documentoIdentidad.put("0", cero);
		AUTORIDAD.put("documentoIdentidad", documentoIdentidad);
		try{
			AUTORIDAD.put("primerNombre", ytal.getString("primerNombreMedico"));
		}catch(Exception e){}
		try{
			AUTORIDAD.put("primerApellido", ytal.getString("primerApellidoMedico"));
		}catch(Exception e){}
		try{
			AUTORIDAD.put("segundoNombre", ytal.getString("segundoNombreMedico"));
		}catch(Exception e){}
		try{
			AUTORIDAD.put("segundoApellido", ytal.getString("segundoApellidoMedico"));
		}catch(Exception e){}
		AUTORIDAD.put("numMPPS", ytal.getString("nuMPPS"));
		AUTORIDAD.put("centroSalud", ytal.getString("centroSalud"));
		ytal.put("AUTORIDAD", AUTORIDAD);
		
		ytal.put("causas", ytal.getString("textoCausa"));
		
		JSONObject pais = new JSONObject();
		pais.put("nombre", ytal.getString("paisDefuncion"));
		JSONObject estado = new JSONObject();
		JSONObject municipio = new JSONObject();
		JSONObject parroquia = new JSONObject();
		try{
		estado.put("nombre", ytal.getString("estadoDefuncion"));
		municipio.put("nombre", ytal.getString("municipioDefuncion"));
		parroquia.put("nombre", ytal.getString("parroquiaDefuncion"));
		}catch(Exception e){}
		JSONObject zero =new JSONObject();
		zero.put("pais", pais);
		zero.put("estado", estado);
		zero.put("municipio", municipio);
		zero.put("parroquia", parroquia);
		JSONObject direccion = new JSONObject();
		direccion.put("0", zero);
		JSONObject datosFAL = new JSONObject();
		datosFAL.put("direccion", direccion);
		ytal.put("datosFAL", datosFAL);
		
		JSONObject EC = new JSONObject();
		try{
		EC.put("numero", ytal.getString("numeroExtractoConsular"));
		//////////////////////////////
		JSONObject fechaCertEC = new JSONObject();
		Long fecEC = ytal.getLong("fechaExtractoConsular");
		Calendar a = Calendar.getInstance();
		a.setTimeInMillis(fecEC);
		int mesEC = (c.get(Calendar.MONTH)+1);
		int diaEC = c.get(Calendar.DAY_OF_WEEK);
		int anoEC = c.get(Calendar.YEAR);
		StringBuilder sbdEC = new StringBuilder();
		String diaStringEC = sbdEC.append(diaEC).toString();
		StringBuilder sbmEC = new StringBuilder();
		String mesString = sbmEC.append(mesEC).toString();
		StringBuilder sbaEC = new StringBuilder();
		String anoStringEC = sbaEC.append(anoEC).toString();
		JSONObject mes2EC = new JSONObject();
		mes2EC.put("numero", mesString);
		fechaCertEC.put("dia2", diaStringEC);
		fechaCertEC.put("mes2", mes2EC);
		fechaCertEC.put("ano2", anoStringEC);
		EC.put("fecha", fechaCertEC);
		JSONObject zeroo =new JSONObject();
		zeroo.put("numero",ytal.getString("documentoIdentConsular"));
		JSONObject docEC =new JSONObject();
		docEC.put("0", zeroo);
		EC.put("documentoIdentidad", docEC);
		try{
			EC.put("primerNombre", ytal.getString("primerNombreConsular"));
		}catch(Exception e){}
		try{
			EC.put("primerApellido", ytal.getString("primerApellidoConsular"));
		}catch(Exception e){}
		try{
			EC.put("segundoNombre", ytal.getString("segundoNombreConsular"));
		}catch(Exception e){}
		try{
			EC.put("segundoApellido", ytal.getString("segundoApellidoConsular"));
		}catch(Exception e){}
		
		
		ytal.put("EC", EC);
		
		}catch(Exception e){}
		
		
		
		modelo = ytal;
////////////////////////////**************************************************/////////////////////////*************		
		ObjectMapper mapper2 = new ObjectMapper();
		DecisionJudicial DJ = new DecisionJudicial();
		DJ = this.consultarDJ(numSolicitud);
		JSONObject IDJ = new JSONObject(mapper2.writeValueAsString(DJ));
		IDJ.put("sentencia", IDJ.getString("extractoProcedencia"));
		modelo.put("IDJ", IDJ);
		
		List<Participante> declarante;
		declarante = this.consultarParticPorSolicitud(numSolicitud, "D");
		ObjectMapper mapper3 = new ObjectMapper();
		for (Participante dec : declarante){
			JSONObject data1 = new JSONObject(
					mapper3.writeValueAsString(dec));

			modelo.put("DEC", data1);
			log.info(data1.toString());
		}
		
		
		List<Participante> particiSolicitud;
		particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");
		ObjectMapper mapper1 = new ObjectMapper();

		for (Participante participante1 : particiSolicitud) {
			JSONObject data1 = new JSONObject(
					mapper1.writeValueAsString(participante1));

			modelo.put(participante1.getRol(), data1);
			
		}
	
	

///////////**************************************************//////////////////////////////////////////////
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ "vista_acta.html"));
		modelo.put("titulo", "Vista previa acta");
		JSONObject respuesta = new JSONObject();
		log.info("solicitud--------->"+sol);
		log.info("el modelo que le estoy pasando a imprimir acta--->" + modelo);
		JSONObject modeloReporte = imprimirActa(numSolicitud,modelo.getString("estatus"),modelo);
		modelo.put("id", numSolicitud);
		respuesta.put("modelo", modelo);
		respuesta.put("vista", modeloReporte.get("vista"));	
		return respuesta.toString();
	}
	
	
	
	public JSONObject vistaPreviaActa1(String modelo, String estatus, JSONObject modeloCompleto ) throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException {
		// TODO Auto-generated method stub
		String rutaImg = context.getRealPath(rutaLogo);
		log.info("******modelo reporte*****"+modelo);
		log.info("******statu reporte*****"+estatus);
		log.info(modeloCompleto.toString());
		String rutaFin = null;
		String rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_ACTA);
		rutaFin = RUTA_FIN + modelo+".pdf"; //rutaFin = rutaPlantilla.replace(PLANTILLA_ACTA,modelo+".pdf");
		//String rutaReportes=obtenerEndPointConfigActa("endPointRutaReportesActa");
		String vista = null;
		
		String estadoDocumento = "";
		String municipioDocumento = "";
	
	JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla);
	HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
	datosAPintar.put("rutaImg", rutaImg);
	datosAPintar.put("estadoDocumento","");
	datosAPintar.put("municipioDocumento","");
	datosAPintar.put("parroquiaDocumento"," ");
	datosAPintar.put("actaDocumento"," ");
	datosAPintar.put("diaDocumento","");
	datosAPintar.put("mesDocumento","");
	datosAPintar.put("annoDocumento","");
	datosAPintar.put("tipoRegistro","no esta en el modelo");
	datosAPintar.put("primerNombreRegistrador","");
	datosAPintar.put("segundoNombreRegistrador","");
	datosAPintar.put("primerApellidoRegistrador","");
	datosAPintar.put("segundoApellidoRegistrador","");
	datosAPintar.put("documentoIdentidadRegistrador","");
	datosAPintar.put("oficinaRegistroCivil","");
	datosAPintar.put("numeroResolucion","");
	datosAPintar.put("fechaResolucion","");
	datosAPintar.put("numeroGaceta","");
	datosAPintar.put("tipoRegistrador",true);
	datosAPintar.put("primerNombreFallecido","");
	datosAPintar.put("segundoNombreFallecido","");
	datosAPintar.put("primerApellidoFallecido","");
	datosAPintar.put("segundoApellidoFallecido","");
	datosAPintar.put("diaNacimientoFallecido","");
	datosAPintar.put("mesNacimientoFallecido","");
	datosAPintar.put("annoNacimientoFallecido","");
	datosAPintar.put("lugarNacimientoFallecido","");
	datosAPintar.put("estadoCivilFallecido","");
	datosAPintar.put("edadFallecido","");
	datosAPintar.put("sexoFallecido","");
	datosAPintar.put("documentoIdentidadFallecido","");
	datosAPintar.put("tipoDocumentoFallecido",true);
	datosAPintar.put("nacionalidadFallecido","");
	datosAPintar.put("profesionFallecido","");
	datosAPintar.put("comunidadIndigena", "");
	datosAPintar.put("residenciaFallecido","");
	datosAPintar.put("estadoFallecido","");
	datosAPintar.put("parroquiaFallecido","");
	datosAPintar.put("municipioFallecido","");
	datosAPintar.put("diaDefuncion","");
	datosAPintar.put("mesDefuncion","");
	datosAPintar.put("annoDefuncion","");
	datosAPintar.put("horaDefuncion","" + ":"+"");
	datosAPintar.put("am_pm",true);
	datosAPintar.put("paisDefuncion","");
	datosAPintar.put("estadoDefuncion","");
	datosAPintar.put("municipioDefuncion","");
	datosAPintar.put("parroquiaDefuncion","");
	datosAPintar.put("causasDefuncion","");
	datosAPintar.put("certficadoDefuncion","");
	datosAPintar.put("diaExpedicionDefuncion","");
	datosAPintar.put("mesExpedicionDefuncion","");
	datosAPintar.put("annoExpedicionDefuncion","");
	datosAPintar.put("nombreAutoridadExpide","");
	datosAPintar.put("documentoIdentidadAutoridad","");
	datosAPintar.put("numeroMPPS","");
	datosAPintar.put("denominacionDependenciaSalud","");
	datosAPintar.put("nombreCompletoPareja","");
	datosAPintar.put("vivePareja",true);
	datosAPintar.put("documentoIdentidadPareja","");
	datosAPintar.put("tipoDocumentoPareja",true);
	datosAPintar.put("profesionPareja","");
	datosAPintar.put("nacionalidadPareja","");
	datosAPintar.put("residenciaPareja","");
	datosAPintar.put("nombreCompletoHijo","");
	datosAPintar.put("documentoIdentidadHijo","");
	datosAPintar.put("edadHijo","");
	datosAPintar.put("viveHijo",true);
	datosAPintar.put("nombreCompletoDeclarante","");
	datosAPintar.put("documentoIdentidadDeclarante","");
	datosAPintar.put("tipoDocumentoDeclarante",true);
	datosAPintar.put("edadDeclarante","");
	datosAPintar.put("profecionDeclarante","");
	datosAPintar.put("nacionalidadDeclarante","");
	datosAPintar.put("residenciaDeclarante","");
	datosAPintar.put("extractoNumero","");
	datosAPintar.put("diaExpedicionExtracto", "");
	datosAPintar.put("mesExpedicionExtracto","");
	datosAPintar.put("annoExpedicionExtracto","");
	datosAPintar.put("autoridadExtracto","");
	datosAPintar.put("tribunalJuzgado","");
	datosAPintar.put("numeroSentenciaJudicial","");
	datosAPintar.put("nombreJuezSentencia","");
	datosAPintar.put("diaSentenciaJudicial","");
	datosAPintar.put("mesSentenciaJudicial","");
	datosAPintar.put("annoSentencialJudicial","");
	datosAPintar.put("nombreTestigo1","");
	datosAPintar.put("documentoIdentidadTestigo1","");
	datosAPintar.put("edadTestigo1","");
	datosAPintar.put("profesionTestigo1","");
	datosAPintar.put("nacionalidadTestigo1","");
	datosAPintar.put("residenciaTestigo1", "");
	datosAPintar.put("nombreTestigo2","");
	datosAPintar.put("documentoIdentidadTestigo2","");
	datosAPintar.put("edadTestigo2","");
	datosAPintar.put("profesionTestigo2","");
	datosAPintar.put("nacionalidadTestigo2","");
	datosAPintar.put("residenciaTestigo2","");
	datosAPintar.put("insercionDefuncion",true);
	// Ore dataConsulta =this.consultarPermiso(modelo);

	//		 datosAPintar.put("numeroConsecutivo", "12345");
	//		 datosAPintar.put("nombreDirectorOficina", "Maria Marsicano");

	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar,  new JREmptyDataSource()); 
	JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);
	String template="<iframe   id='plugin' width='800'  height='800' src='"+ "/web-registrarDefuncion/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
	
	if("PEA".equals(estatus) || "PVR".equals(estatus)){
		vista = Util.leerArchivo(context.getRealPath(RUTA_ACTA));
	}else if("PPI".equals(estatus)){		
		vista = Util.leerArchivo(context.getRealPath(RUTA_IMPRIMIR_ACTA)); 		
	}
	vista = vista.replace("ARCHIVOPDFACTA", template);
	JSONObject formulario=new JSONObject();
	formulario.put("vista", vista);
	return formulario;
}

	@RequestMapping(value = "/imprimirActaBorrador", method = RequestMethod.POST)
	public @ResponseBody String imprimirActaBorrador(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
					throws GeneralException, JsonParseException, JsonMappingException,
					IOException, JSONException, JRException {
		log.info("*************Tipo de permiso estoy en imprimir acta borrador************" + sesion.getId());
		ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		JSONObject modelo = new JSONObject(data);
		String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		//htmlInicio = buscarHtml(sol.getEstadoSolicitud());
		//String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+ htmlInicio + ".html"));
		modelo.put("titulo", "Imprimir acta");
		JSONObject respuesta = new JSONObject();
		JSONObject modeloReporte = imprimirActa(numSolicitud,modelo.getString("estatus"),modelo);
		log.info("modelo ---->"+modelo.toString());
		respuesta.put("modelo", modelo);
		respuesta.put("vista", modeloReporte.get("vista"));	
		return respuesta.toString();
	}
	
	public JSONObject imprimirActa(String modelo, String estatus, JSONObject modeloCompleto ) throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException {
		// TODO Auto-generated method stub
		String rutaImg = context.getRealPath(rutaLogo);
		
		String rutaFin = null;
		String rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_ACTA);
		rutaFin = RUTA_FIN + modelo+".pdf"; //rutaFin = rutaPlantilla.replace(PLANTILLA_ACTA,modelo+".pdf");
		//String rutaReportes=obtenerEndPointConfigActa("endPointRutaReportesActa");
		String vista = null;
		
		String estadoDocumento = "";
		String municipioDocumento = "";
		String parroquiaDocumento = "";
		try{
			if (modeloCompleto.get("estado") !=null){
				estadoDocumento = modeloCompleto.getString("estado");
			}}catch(Exception e){}
		try{
			if (modeloCompleto.get("municipio") !=null){
				municipioDocumento = modeloCompleto.getString("municipio");
			}}catch(Exception e){}
		try{
			if (modeloCompleto.get("parroquia") !=null){
				parroquiaDocumento = modeloCompleto.getString("parroquia");
			}}catch(Exception e){}
		JSONObject auto = new JSONObject(); 
	
		try{
			auto = modeloCompleto.getJSONObject("AUTORIDAD");}
		catch(Exception e){}
		String segundoNombreAutoridad = "";
		try{
			if(auto.getString("segundoNombre") != null){
				segundoNombreAutoridad = auto.getString("segundoNombre"); 
			}
		}catch(Exception e){}
		String segundoApellidoAutoridad = "";
		try{
			if(auto.getString("segundoApellido") != null){
				segundoApellidoAutoridad = auto.getString("segundoApellido"); 
			}
		}catch(Exception e){}
		String fechaResolucion = "";
		try{
			fechaResolucion = auto.getJSONObject("fechaCert").getString("dia2") + "-" + auto.getJSONObject("fechaCert").getJSONObject("mes2").getString("numero") + "-" + auto.getJSONObject("fechaCert").getString("ano2");
		}catch(Exception e){}
		
		JSONObject FAL = modeloCompleto.getJSONObject("FAL");
		String segundoNombreFAL = "";
		try{
			if(FAL.getString("segundoNombre") != null){
				segundoNombreFAL = FAL.getString("segundoNombre"); 
			}
		}catch(Exception e){}
		String segundoApellidoFAL = "";
		try{
			if(FAL.getString("segundoApellido") != null){
				segundoApellidoFAL = FAL.getString("segundoApellido"); 
			}
		}catch(Exception e){}
		long num = FAL.getLong("fechaNacimiento");
		Calendar c = Calendar.getInstance();
		
		c.setTimeInMillis(num);
		int mesNacimientoFal = (c.get(Calendar.MONTH)+1);
		int diaNacimientoFal = c.get(Calendar.DAY_OF_WEEK);
		int annoNacimientoFal = c.get(Calendar.YEAR);
		StringBuilder sbd = new StringBuilder();
		String diaNacimientoFallecido = sbd.append(diaNacimientoFal).toString();
		StringBuilder sbm = new StringBuilder();
		String mesNacimientoFallecido = sbm.append(mesNacimientoFal).toString();
		StringBuilder sba = new StringBuilder();
		String annoNacimientoFallecido = sba.append(annoNacimientoFal).toString();
		Date hoy = new Date();
		log.info(hoy.toString());
		String diaHoy = "";
		String mesHoy = "";
		String annoHoy = "";
		Calendar b = Calendar.getInstance();
		b.setTime(hoy);
		int diaHoyI= b.get(Calendar.DAY_OF_WEEK);
		StringBuilder diaSBI = new StringBuilder();
		diaHoy = diaSBI.append(diaHoyI).toString();
		
		int mesHoyI= b.get(Calendar.MONTH);
		StringBuilder mesSBI = new StringBuilder();
		mesHoy = mesSBI.append(mesHoyI).toString();
		
		int annoHoyI= b.get(Calendar.YEAR);
		StringBuilder annoSBI = new StringBuilder();
		annoHoy = annoSBI.append(annoHoyI).toString();
		
		String FALEdad = "";
		boolean tipoDocFal;
		if(FAL.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("codigo") == "CED")
			tipoDocFal = true;
		else
			tipoDocFal = false;
		String comunidadIndigena = "";
		try{
			if(FAL.getJSONObject("comunidadIndigena").getString("nombre") != null){
				comunidadIndigena = FAL.getJSONObject("comunidadIndigena").getString("nombre"); 
			}
		}catch(Exception e){
			comunidadIndigena = "N/A";
		}
		
		JSONObject datosFAL = new JSONObject();
		try{
			datosFAL = modeloCompleto.getJSONObject("datosFAL");
		}catch(Exception e){
			
		}
		
		String parroquiaFallecido = "";
		try{
			if(datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("parroquia").getString("nombre") != null)
			{
				parroquiaFallecido = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("parroquia").getString("nombre");
			}

		}catch(Exception e){}
		String municipioFallecido = "";
		try{
			if(datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("municipio").getString("nombre") != null)
			{
				municipioFallecido = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("municipio").getString("nombre");
			}

		}catch(Exception e){}
		String estadoFallecido = "";
		try{
			if(datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("estado").getString("nombre") != null)
			{
				estadoFallecido = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("estado").getString("nombre");
			}

		}catch(Exception e){}
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		Date fechaFallecimiento = new Date();
		try {
			fechaFallecimiento = inputFormat.parse(modeloCompleto.getString("fechaDefuncion"));
		} catch (Exception e) {
			}
		Calendar a = Calendar.getInstance();
		int diaDef= a.get(Calendar.DAY_OF_WEEK);
		StringBuilder diaSB = new StringBuilder();
		String diaDefuncion = diaSB.append(diaDef).toString();
		
		int mesDef= a.get(Calendar.MONTH);
		StringBuilder mesSB = new StringBuilder();
		String mesDefuncion = mesSB.append(mesDef).toString();
		
		int annoDef= a.get(Calendar.YEAR);
		StringBuilder annoSB = new StringBuilder();
		String annoDefuncion = annoSB.append(annoDef).toString();
		
		int horDef= a.get(Calendar.HOUR);
		StringBuilder horSB = new StringBuilder();
		String horaDefuncion = horSB.append(horDef).toString();
		
		int minDef= a.get(Calendar.MINUTE);
		StringBuilder minSB = new StringBuilder();
		String minutosDefuncion = minSB.append(minDef).toString();
		
		boolean am_pm;
		StringBuilder am_pmSB = new StringBuilder();
		String am = am_pmSB.append(a.get(Calendar.AM)).toString();
		if(am=="1")
			am_pm = true;
		else
			am_pm = false;
		
		String nombreAutoridadExpide = "";
		String primerNombreAutoridad = "";
		String primerApellidoAutoridad = "";
		String segNom = "";
		String segApe = "";
		String certDef = "";
		String diaExpDef = "";
		String mesExpDef = "";
		String annoExpDef = "";
		String documentoIdentidadAutoridad = "";
		String numeroMPPS = "";
		String denoDepSalud = "";
		try{
			if(modeloCompleto.getJSONObject("AUTORIDAD") != null){
				certDef = modeloCompleto.getJSONObject("AUTORIDAD").getString("numCert");
				diaExpDef = modeloCompleto.getJSONObject("AUTORIDAD").getJSONObject("fechaCert").getString("dia2");
				mesExpDef = modeloCompleto.getJSONObject("AUTORIDAD").getJSONObject("fechaCert").getJSONObject("mes2").getString("numero");
				annoExpDef = modeloCompleto.getJSONObject("AUTORIDAD").getJSONObject("fechaCert").getString("ano2");
				documentoIdentidadAutoridad = modeloCompleto.getJSONObject("AUTORIDAD").getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero");
				numeroMPPS = modeloCompleto.getJSONObject("AUTORIDAD").getString("numMPPS");
				denoDepSalud = modeloCompleto.getJSONObject("AUTORIDAD").getString("centroSalud");
				}
			}catch(Exception e){
				
			}
		
		try{
			if(modeloCompleto.getJSONObject("AUTORIDAD").getString("primerNombre") != null)
				primerNombreAutoridad = modeloCompleto.getJSONObject("AUTORIDAD").getString("primerNombre");
		}catch(Exception e){}
		try{
			if(modeloCompleto.getJSONObject("AUTORIDAD").getString("primerApellido") != null)
				primerApellidoAutoridad = modeloCompleto.getJSONObject("AUTORIDAD").getString("primerApellido");
		}catch(Exception e){}
		try{
			if(modeloCompleto.getJSONObject("AUTORIDAD").getString("segundoNombre") != null)
				segNom = modeloCompleto.getJSONObject("AUTORIDAD").getString("segundoNombre");
		}catch(Exception e){}
		try{
			if(modeloCompleto.getJSONObject("AUTORIDAD").getString("segundoApellido") != null)
				segApe = modeloCompleto.getJSONObject("AUTORIDAD").getString("segundoApellido");
		}catch(Exception e){}
		nombreAutoridadExpide = primerNombreAutoridad + " " + segNom + " " + primerApellidoAutoridad + " " + segApe;
		
		String nombreCompletoPareja = "";
		String segNomPar = "";
		String segApePar = "";
		boolean viveCon = false;
		try{
			if(modeloCompleto.getJSONObject("COUNI") != null){
				try{
					if(modeloCompleto.getJSONObject("COUNI").getString("segundoNombre") != null){
						segNomPar = modeloCompleto.getJSONObject("COUNI").getString("segundoNombre");
				}
					}catch(Exception e){}
				try{
					if(modeloCompleto.getJSONObject("COUNI").getString("segundoApellido") != null){
						segApePar = modeloCompleto.getJSONObject("COUNI").getString("segundoApellido");
				}
					}catch(Exception e){}
				
				nombreCompletoPareja = modeloCompleto.getJSONObject("COUNI").getString("primerNombre") + " " + segNomPar + " " + modeloCompleto.getJSONObject("COUNI").getString("primerApellido")+ " " + segApePar;
			}
		}catch (Exception e){}
		try{
			if(modeloCompleto.getJSONObject("COUNI") != null){
				viveCon = modeloCompleto.getJSONObject("COUNI").getBoolean("vive");
			}
		}catch(Exception e){}
		String documentoIdentidadPareja = "";
		boolean tipoDocumentoPareja = true;
		try{
			if(modeloCompleto.getJSONObject("COUNI") != null){
				documentoIdentidadPareja = modeloCompleto.getJSONObject("COUNI").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
				if(modeloCompleto.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("codigo") == "CED")
					tipoDocumentoPareja = true;
				else
					tipoDocumentoPareja = false;
			}
		}catch(Exception e){}
		String profesionPareja = "";
		try{
			if(modeloCompleto.getJSONObject("COUNI") != null)
				profesionPareja = modeloCompleto.getJSONObject("COUNI").getJSONObject("profesion").getString("nombre");
		}catch(Exception e){}
		String nacionalidadPareja = "";
		try{
			if(modeloCompleto.getJSONObject("COUNI").getString("nacionalidad") != null)
				profesionPareja = modeloCompleto.getJSONObject("COUNI").getString("nacionalidad");
		}catch(Exception e){}
		String residenciaPareja = "";
		try{
			if(modeloCompleto.getJSONObject("COUNI").getString("ubicacion") != null)
				profesionPareja = modeloCompleto.getJSONObject("COUNI").getString("ubicacion");
		}catch(Exception e){}
		
		String nombreCompletoHijo = "";
		String segNomHij = "";
		String segApeHij = "";
		try{
			if(modeloCompleto.getJSONObject("HIJ1") != null){
				try{
					if(modeloCompleto.getJSONObject("HIJ1").getString("segundoNombre") != null){
						segNomHij = modeloCompleto.getJSONObject("HIJ1").getString("segundoNombre");
				}
					}catch(Exception e){}
				try{
					if(modeloCompleto.getJSONObject("HIJ1").getString("segundoApellido") != null){
						segApeHij = modeloCompleto.getJSONObject("HIJ1").getString("segundoApellido");
				}
					}catch(Exception e){}
				
				nombreCompletoHijo = modeloCompleto.getJSONObject("HIJ1").getString("primerNombre") + " " + segNomHij + " " + modeloCompleto.getJSONObject("HIJ1").getString("primerApellido")+ " " + segApeHij;
			}
		}catch (Exception e){}
		String documentoIdentidadHijo = "";
		boolean viveHijo = true;
		try{
			if(modeloCompleto.getJSONObject("HIJ1") != null){
				documentoIdentidadHijo = modeloCompleto.getJSONObject("HIJ1").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");
				viveHijo = modeloCompleto.getJSONObject("HIJ1").getBoolean("vive");
			}
		}catch(Exception e){}
		String edadHijo = "";
		try{
			if(modeloCompleto.getJSONObject("HIJ1") != null){
				edadHijo = modeloCompleto.getJSONObject("HIJ1").getString("edad");
				
			}
		}catch(Exception e){}
		String nombreCompletoDeclarante = "";
		String segundoNombreDeclarante = "";
		String segundoApellidoDeclarante = "";
		try{
			if(modeloCompleto.getJSONObject("DEC").getString("segundoNombre") != null)
				segundoNombreDeclarante = modeloCompleto.getJSONObject("DEC").getString("segundoNombre");
		}catch(Exception e){}
		try{
			if(modeloCompleto.getJSONObject("DEC").getString("segundoApellido") != null)
				segundoApellidoDeclarante = modeloCompleto.getJSONObject("DEC").getString("segundoApellido");
		}catch(Exception e){}
		nombreCompletoDeclarante = modeloCompleto.getJSONObject("DEC").getString("primerNombre") + " " + segundoNombreDeclarante + " " +  modeloCompleto.getJSONObject("DEC").getString("primerApellido") + " " + segundoApellidoDeclarante;
		boolean tipoDocumentoDeclarante = true;
		if(modeloCompleto.getJSONObject("DEC").getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("codigo")=="CED")
			tipoDocumentoDeclarante = true;
		else
			tipoDocumentoDeclarante = false;
		String nacionalidadDeclarante = "";
		try{
			if(modeloCompleto.getJSONObject("DEC").getString("nacionalidad") != null)
				nacionalidadDeclarante = modeloCompleto.getJSONObject("DEC").getString("nacionalidad");
		}catch(Exception e){
			
		}
		String autoridadExtracto = "";
		String primerNombreEC = "";
		String primerApellidoEC = "";
		String segEC = "";
		String segECA = "";
		String extractoNumero = "";
		String diaExpedicionExtracto = "";
		String mesExpedicionExtracto = "";
		String annoExpedicionExtracto = "";
		
		try{
			if(modeloCompleto.getJSONObject("EC") != null){
				extractoNumero = modeloCompleto.getJSONObject("EC").getString("numero");
				diaExpedicionExtracto = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getString("dia2");
				mesExpedicionExtracto = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getJSONObject("mes2").getString("numero");
				annoExpedicionExtracto = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getString("ano2");
			}
		}catch(Exception e){
			extractoNumero = "N/A";
			diaExpedicionExtracto = "N/A";
			mesExpedicionExtracto = "N/A";
			annoExpedicionExtracto = "N/A";			
		}
	
		try{
			if(modeloCompleto.getJSONObject("EC").getString("segundoNombre") != null){
				segEC = modeloCompleto.getJSONObject("EC").getString("segundoNombre");
			}			
		}catch(Exception e){
			segEC = "N/A";			
		}
		try{
			if(modeloCompleto.getJSONObject("EC").getString("segundoApellido") != null){				
				segECA = modeloCompleto.getJSONObject("EC").getString("segundoApellido");
			}
		}catch(Exception e){
			segECA = "N/A";			
		}
		
		try{
			if(modeloCompleto.getJSONObject("EC").getString("primerNombre") != null && modeloCompleto.getJSONObject("EC").getString("primerApellido") != null){				
				autoridadExtracto = modeloCompleto.getJSONObject("EC").getString("primerNombre") + " " + segEC + " " + modeloCompleto.getJSONObject("EC").getString("primerApellido") + " " + segECA; 
			}
		}catch(Exception e){
			segECA = "N/A";		
		}
		String nombreJuezSentencia = "";
		String priIDJ = "";
		String priIDJA = "";
		String segIDJ = "";
		String segIDJA = "";
		String tribunalJuzgado = "";
		String sentenciaIDJ = "";
		String fechaDia = "";
		String fechaMes = "";
		String fechaAnno = "";
		try{
			if(modeloCompleto.getJSONObject("IDJ") != null){
				tribunalJuzgado = modeloCompleto.getJSONObject("IDJ").getString("tribunal");
				sentenciaIDJ = modeloCompleto.getJSONObject("IDJ").getString("sentencia");
				fechaDia = modeloCompleto.getJSONObject("IDJ").getJSONObject("fecha").getString("dia2");
				fechaMes = modeloCompleto.getJSONObject("IDJ").getJSONObject("fecha").getJSONObject("mes2").getString("numero");
				fechaAnno = modeloCompleto.getJSONObject("IDJ").getJSONObject("fecha").getString("ano2");
			}
		}catch(Exception e){
			tribunalJuzgado = "N/A";
			sentenciaIDJ = "N/A";
			fechaDia = "N/A";
			fechaMes = "N/A";
			fechaAnno = "N/A";			
		}
		try{
			if(modeloCompleto.getJSONObject("IDJ").getString("primerNombre") != null){				
				priIDJ = modeloCompleto.getJSONObject("IDJ").getString("primerNombre");
			}
		}catch(Exception e){
			priIDJ = "N/A";			
		}
		try{
			if(modeloCompleto.getJSONObject("IDJ").getString("primerApellido") != null){			
				priIDJA = modeloCompleto.getJSONObject("IDJ").getString("primerApellido");
			}
		}catch(Exception e){	
		}
		try{
			if(modeloCompleto.getJSONObject("IDJ").getString("segundoNombre") != null){				
				segIDJ = modeloCompleto.getJSONObject("IDJ").getString("segundoNombre");
			}
		}catch(Exception e){
			
		}
		try{
			if(modeloCompleto.getJSONObject("IDJ").getString("segundoApellido") != null){				
				segIDJA = modeloCompleto.getJSONObject("IDJ").getString("segundoApellido");
			}		
		}catch(Exception e){	
		}
		
		try{
			if(priIDJ != null && priIDJA != null){				
				nombreJuezSentencia = priIDJ + " " + segIDJ + " " + priIDJA + " " + segIDJA; 
			}		
		}catch(Exception e){
			//nombreJuezSentencia = "N/A";
		}
		
		String nombreTestigo1 = "";
		String segT1 = "";
		String segT1A = "";
		try{
			if(modeloCompleto.getJSONObject("TA1").getString("segundoNombre") != null)
				segT1 = modeloCompleto.getJSONObject("TA1").getString("segundoNombre");
		}catch(Exception e){
			
		}
		try{
			if(modeloCompleto.getJSONObject("TA1").getString("segundoApellido") != null)
				segT1A = modeloCompleto.getJSONObject("TA1").getString("segundoApellido");
		}catch(Exception e){
			
		}
		nombreTestigo1 = modeloCompleto.getJSONObject("TA1").getString("primerNombre") + " " + segT1 + " " + modeloCompleto.getJSONObject("TA1").getString("primerApellido") + " " + segT1A; 
		String nacionalidadTestigo1 = "";
		try{
			if(modeloCompleto.getJSONObject("TA1").getString("nacionalidad") != null)
				nacionalidadTestigo1 = modeloCompleto.getJSONObject("TA1").getString("nacionalidad");
		}catch(Exception e){
			
		}
		

		
		String nombreTestigo2 = "";
		String segT2 = "";
		String segT2A = "";
		try{
			if(modeloCompleto.getJSONObject("TA2").getString("segundoNombre") != null)
				segT2 = modeloCompleto.getJSONObject("TA2").getString("segundoNombre");
		}catch(Exception e){
			
		}
		try{
			if(modeloCompleto.getJSONObject("TA2").getString("segundoApellido") != null)
				segT2A = modeloCompleto.getJSONObject("TA2").getString("segundoApellido");
		}catch(Exception e){
			
		}
		nombreTestigo2 = modeloCompleto.getJSONObject("TA2").getString("primerNombre") + " " + segT2 + " " + modeloCompleto.getJSONObject("TA2").getString("primerApellido") + " " + segT2A; 
		String nacionalidadTestigo2 = "";
		try{
			if(modeloCompleto.getJSONObject("TA2").getString("nacionalidad") != null)
				nacionalidadTestigo2 = modeloCompleto.getJSONObject("TA2").getString("nacionalidad");
		}catch(Exception e){
			
		}
		String profesion ="";
		try{
			profesion = FAL.getJSONObject("profesion").getString("descripcion");
		}catch(Exception e){}
		String paisDatosFal = "";
		try {
			paisDatosFal = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("pais").getString("nombre");
		}catch(Exception e){}
		String causasDef = "";
		try{
			causasDef = modeloCompleto.getString("causas");
		}catch(Exception e){}
		String edadDeclarante = "";
		try{
			edadDeclarante = modeloCompleto.getJSONObject("DEC").getString("edad");
		}catch(Exception e){}
		String numeroEC = "";
		try{
			numeroEC = modeloCompleto.getJSONObject("EC").getString("numero");
		}catch(Exception e){}
		String diaEC = "";
		try{
			diaEC = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getString("dia2");
		}catch(Exception e){}
		String mesEC = "";
		try{
			mesEC = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getJSONObject("mes2").getString("numero");
		}catch(Exception e){}
		String anoEC = "";
		try{
			anoEC = modeloCompleto.getJSONObject("EC").getJSONObject("fecha").getString("ano2");
		}catch(Exception e){}
		String edadTA1 = "";
		try{
			edadTA1 = modeloCompleto.getJSONObject("TA1").getString("edad");
		}catch(Exception e){}
		String edadTA2 = "";
		try{
			edadTA2 = modeloCompleto.getJSONObject("TA2").getString("edad");
		}catch(Exception e){}
		
		
				
		JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla);
		HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
		datosAPintar.put("rutaImg", rutaImg);
		datosAPintar.put("estadoDocumento",estadoDocumento);
		datosAPintar.put("municipioDocumento",municipioDocumento);
		datosAPintar.put("parroquiaDocumento"," ");
		datosAPintar.put("actaDocumento"," ");
		datosAPintar.put("diaDocumento",diaHoy);
		datosAPintar.put("mesDocumento", mesHoy);
		datosAPintar.put("annoDocumento",annoHoy);
		datosAPintar.put("tipoRegistro"," ");
		datosAPintar.put("primerNombreRegistrador",primerNombreAutoridad);
		datosAPintar.put("segundoNombreRegistrador",segundoNombreAutoridad);
		datosAPintar.put("primerApellidoRegistrador",primerApellidoAutoridad);
		datosAPintar.put("segundoApellidoRegistrador",segundoApellidoAutoridad);
		datosAPintar.put("documentoIdentidadRegistrador",documentoIdentidadAutoridad);
		datosAPintar.put("oficinaRegistroCivil",denoDepSalud);
		datosAPintar.put("numeroResolucion",certDef);
		datosAPintar.put("fechaResolucion",fechaResolucion);
		datosAPintar.put("numeroGaceta",numeroMPPS);
		datosAPintar.put("tipoRegistrador",true);
		datosAPintar.put("primerNombreFallecido",FAL.getString("primerNombre"));
		datosAPintar.put("segundoNombreFallecido",segundoNombreFAL);
		datosAPintar.put("primerApellidoFallecido",FAL.getString("primerApellido"));
		datosAPintar.put("segundoApellidoFallecido",segundoApellidoFAL);
		datosAPintar.put("diaNacimientoFallecido",diaNacimientoFallecido);
		datosAPintar.put("mesNacimientoFallecido",mesNacimientoFallecido);
		datosAPintar.put("annoNacimientoFallecido",annoNacimientoFallecido);
		datosAPintar.put("lugarNacimientoFallecido",FAL.getString("lugarNacimiento"));
		datosAPintar.put("estadoCivilFallecido",FAL.getString("estadoCivil"));
		datosAPintar.put("edadFallecido",FALEdad);
		datosAPintar.put("sexoFallecido",FAL.getString("sexo"));
		datosAPintar.put("documentoIdentidadFallecido",FAL.getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		datosAPintar.put("tipoDocumentoFallecido",tipoDocFal);
		datosAPintar.put("nacionalidadFallecido",FAL.getString("nacionalidad"));
		datosAPintar.put("profesionFallecido",profesion);
		datosAPintar.put("comunidadIndigena", comunidadIndigena);
		datosAPintar.put("residenciaFallecido",FAL.getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
		datosAPintar.put("estadoFallecido",estadoFallecido);
		datosAPintar.put("parroquiaFallecido",parroquiaFallecido);
		datosAPintar.put("municipioFallecido",municipioFallecido);
		datosAPintar.put("diaDefuncion",diaDefuncion);
		datosAPintar.put("mesDefuncion",mesDefuncion);
		datosAPintar.put("annoDefuncion",annoDefuncion);
		datosAPintar.put("horaDefuncion",horaDefuncion + ":"+minutosDefuncion);
		datosAPintar.put("am_pm",am_pm);
		datosAPintar.put("paisDefuncion",paisDatosFal);
		datosAPintar.put("estadoDefuncion",estadoFallecido);
		datosAPintar.put("municipioDefuncion",municipioFallecido);
		datosAPintar.put("parroquiaDefuncion",parroquiaFallecido);
		datosAPintar.put("causasDefuncion",causasDef);
		datosAPintar.put("certficadoDefuncion",certDef);
		datosAPintar.put("diaExpedicionDefuncion",diaExpDef);
		datosAPintar.put("mesExpedicionDefuncion",mesExpDef);
		datosAPintar.put("annoExpedicionDefuncion",annoExpDef);
		datosAPintar.put("nombreAutoridadExpide",nombreAutoridadExpide);
		datosAPintar.put("documentoIdentidadAutoridad",documentoIdentidadAutoridad);
		datosAPintar.put("numeroMPPS",numeroMPPS);
		datosAPintar.put("denominacionDependenciaSalud",denoDepSalud);
		datosAPintar.put("nombreCompletoPareja",nombreCompletoPareja);
		datosAPintar.put("vivePareja",viveCon);
		datosAPintar.put("documentoIdentidadPareja",documentoIdentidadPareja);
		datosAPintar.put("tipoDocumentoPareja",tipoDocumentoPareja);
		datosAPintar.put("profesionPareja",profesionPareja);
		datosAPintar.put("nacionalidadPareja",nacionalidadPareja);
		datosAPintar.put("residenciaPareja",residenciaPareja);
		datosAPintar.put("nombreCompletoHijo",nombreCompletoHijo);
		datosAPintar.put("documentoIdentidadHijo",documentoIdentidadHijo);
		datosAPintar.put("edadHijo",edadHijo);
		datosAPintar.put("viveHijo",viveHijo);
		datosAPintar.put("nombreCompletoDeclarante",nombreCompletoDeclarante);
		datosAPintar.put("documentoIdentidadDeclarante","12547896");
		datosAPintar.put("tipoDocumentoDeclarante",tipoDocumentoDeclarante);
		datosAPintar.put("edadDeclarante",edadDeclarante);
		datosAPintar.put("profecionDeclarante",modeloCompleto.getJSONObject("DEC").getString("ocupacion"));
		datosAPintar.put("nacionalidadDeclarante",nacionalidadDeclarante);
		datosAPintar.put("residenciaDeclarante",modeloCompleto.getJSONObject("DEC").getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
		datosAPintar.put("extractoNumero",numeroEC);
		datosAPintar.put("diaExpedicionExtracto", diaEC);
		datosAPintar.put("mesExpedicionExtracto", mesEC);
		datosAPintar.put("annoExpedicionExtracto",anoEC);
		datosAPintar.put("autoridadExtracto",autoridadExtracto);
		datosAPintar.put("tribunalJuzgado",tribunalJuzgado);
		datosAPintar.put("numeroSentenciaJudicial",sentenciaIDJ);
		datosAPintar.put("nombreJuezSentencia",nombreJuezSentencia);
		datosAPintar.put("diaSentenciaJudicial",fechaDia);
		datosAPintar.put("mesSentenciaJudicial",fechaMes);
		datosAPintar.put("annoSentencialJudicial",fechaAnno);
		datosAPintar.put("nombreTestigo1",nombreTestigo1);
		datosAPintar.put("documentoIdentidadTestigo1",modeloCompleto.getJSONObject("TA1").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		datosAPintar.put("edadTestigo1",edadTA1);
		datosAPintar.put("profesionTestigo1",modeloCompleto.getJSONObject("TA1").getString("ocupacion"));
		datosAPintar.put("nacionalidadTestigo1",nacionalidadTestigo1);
		datosAPintar.put("residenciaTestigo1", modeloCompleto.getJSONObject("TA1").getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
		datosAPintar.put("nombreTestigo2",nombreTestigo2);
		datosAPintar.put("documentoIdentidadTestigo2",modeloCompleto.getJSONObject("TA2").getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero"));
		datosAPintar.put("edadTestigo2",edadTA2);
		datosAPintar.put("profesionTestigo2",modeloCompleto.getJSONObject("TA2").getString("ocupacion"));
		datosAPintar.put("nacionalidadTestigo2",nacionalidadTestigo2);
		datosAPintar.put("residenciaTestigo2",modeloCompleto.getJSONObject("TA2").getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
		datosAPintar.put("insercionDefuncion",true);
		// Ore dataConsulta =this.consultarPermiso(modelo);

		//		 datosAPintar.put("numeroConsecutivo", "12345");
		//		 datosAPintar.put("nombreDirectorOficina", "Maria Marsicano");
		
		log.info("vamos a ver que tiene el modelo completo----->" + modeloCompleto.toString());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar,  new JREmptyDataSource()); 
		JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);
		String template="<iframe   id='plugin' width='800'  height='800' src='"+ "/web-registrarDefuncion/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
		if("PVR".equals(estatus)){
			vista = Util.leerArchivo(context.getRealPath(RUTA_ACTA));
		}else if("PEA".equals(estatus)){		
			vista = Util.leerArchivo(context.getRealPath(RUTA_BORRADOR)); 		
		}else if("PPI".equals(estatus)){		
			vista = Util.leerArchivo(context.getRealPath(RUTA_IMPRIMIR_ACTA)); 		
		}
		vista = vista.replace("ARCHIVOPDFACTA", template);
		JSONObject formulario=new JSONObject();
		formulario.put("vista", vista);
		return formulario;
	}

	@RequestMapping(value="/cargaExitosaCancelada", method = RequestMethod.POST)  
	public @ResponseBody String cargaExitosaCancelada(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
		log.info("*************validarReporteConforme estoy en carga exitosa cancelada************"+sesion.getId());
		//   ObjectMapper mapper= new ObjectMapper();
		//   Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class); 
		//   String token=request.getHeader("Authorization");
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		//   if(token==null){
		//	       throw new GeneralException("seguridad_no_token");
		//   }  
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"cargar_exitosa_cancelada.html"));
		log.info("******Contenido Html************"+vista);
		modelo.put("mensaje", "La solicitud fue cancelada por no poseer el certificado m\u00e9dico de defunci\u00f3n EV-14.");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	public String obtenerNombrePlantillaRDEFU(String tipoPermiso) {

		String NombrePlantillaRDEFU = null;
		NombrePlantillaRDEFU = "Oficio_ORE.jrxml";
		return NombrePlantillaRDEFU;
	}

	public HashMap<String, Object> obtenerDatosReporte(
			Map<String, String> data, Oficina datosOficina,
			Funcionario obtenerDatosFuncionario) {

		HashMap<String, Object> datosReporte = new HashMap<String, Object>();

		datosReporte.put("numeroConsecutivo", "001");
		datosReporte.put("nombreDirectorOficina",obtenerDatosFuncionario.getPrimerNombre() + ' '+ obtenerDatosFuncionario.getPrimerApellido());
		datosReporte.put("estadoOficina", datosOficina.Direccion.getEstado().getNombre());
		datosReporte.put("nombreCiudadano",data.get("nombre") + ' ' + data.get("apellido"));
		datosReporte.put("cedulaCiudadano", data.get("cedula"));
		datosReporte.put("nombreDifunto",data.get("nombre") + ' ' + data.get("apellido"));
		datosReporte.put("cedulaFallecido", data.get("cedula"));
		datosReporte.put("fechaFallecimiento", "25/10/1982");

		return datosReporte;
	}

	/*
	 * guardarDatos.java
	 * @descripcion Metodo que actualiza el cambio de estatus
	 * @version 1.0 11/6/2016 
	 * @author Dairene Ramirez
	/*
	 * @param numCertificado String numero de certificado
	 * @return Solicitud soli
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/guardarDatos", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String guardarDatos(@RequestBody String data, HttpServletRequest request, HttpSession session) throws JsonParseException, JsonMappingException, IOException, JSONException{
		log.info("****************entrando al metodo guardarDatos*******");
		//ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class);
		JSONObject modelo = new JSONObject(data);     
		log.info("**********DATA GUARDAR FORM******"+modelo.getString("id"));
		return "1";

	}


	/**
	 *Funcion que cunsulta pais  
	 * @author Maria Marsicano
	 * @return List<Pais> lista de objetos Pais
	 */
	@RequestMapping(value="/consultarPais", method = RequestMethod.GET)
	public List<Pais> consultarPaisTodos(){
		//			Catalogo catalogo = new Catalogo();
		CatalogoServicioCliente catalogo =new CatalogoServicioCliente();
		return catalogo.consultarPaisTodos();
	}
	/**
	 *Funcion que cunsulta municipio  
	 * @author Maria Marsicano
	 * @param codigoPais String codigo del pais
	 * @return List<Estado> lista de objetos estado
	 */
	@RequestMapping(value="/consultarEstados/{codigoPais}", method = RequestMethod.GET)	
	public List<Estado> consultarEstadoPais(@PathVariable("codigoPais") String codigoPais){
		log.info("******************Consultando los Estados de "+codigoPais);
		CatalogoServicioCliente catalogo =new CatalogoServicioCliente();
		return catalogo.consultarEstadoPorPais(codigoPais);
	}
	/**
	 *Funcion que cunsulta municipio  
	 * @author Maria Marsicano
	 * @param codigoEstados String codigo del estado
	 * @return List<Municipio> lista de objetos municipio
	 */
	@RequestMapping(value="/consultarMunicipio/{codigoEstado}", method = RequestMethod.GET)	
	public List<Municipio> consultarMunicipiosEstadoTEST(@PathVariable("codigoEstado") String codigoEstado){
		log.info("******************Consultando los municipios de "+codigoEstado);
		CatalogoServicioCliente catalogo =new CatalogoServicioCliente();
		return catalogo.consultarMunicipioPorEstado(codigoEstado);
	}

	/**
	 *Funcion que cunsulta parroquias por estado 
	 * @author Maria Marsicano
	 * @param codigoMunicipio String codigo del municipio
	 * @return List<Parroquia> lista de objetos Parroquia
	 */
	@RequestMapping(value="/consultarParroquia/{codigoMunicipio}", method = RequestMethod.GET)	
	public List<Parroquia> consultarParroquiasMunicipio(@PathVariable("codigoMunicipio") String codigoMunicipio){
		CatalogoServicioCliente catalogo =new CatalogoServicioCliente();

		return catalogo.consultarParroquiaPorMunicipio(codigoMunicipio);
	}

	/**
	 *Funcion que devuelve la ruta del war
	 * @author Maria Marsicano
	 * @param String
	 * @return String
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws GeneralException 
	 */
	public  String obtenerEndPointConfig(String endPointClave) {
		String rutaReportes=(System.getProperty("jboss.home.dir") == null ? "C:/jboss-as-7.1.1.Final" : System.getProperty("jboss.home.dir"))+"/modules/ve/gob/cne/sarc/main/";
		log.info("EndPoint a reportes: " + rutaReportes);
		File configFile = new File(rutaReportes+ "Reporte.properties");    
		String host = "";
		try{
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			host = props.getProperty(endPointClave);
			reader.close();
		} catch (FileNotFoundException ex) {
			log.info("ERROR Config file No Encontrado");
		} catch (IOException ex){
			log.info("ERROR I/O accediendo a Archivo Config");
		}
		log.info("direccion del host: "+host);
		return host;
	}

	/**
	 *Funcion que devuelve la ruta del war
	 * @author Maria Marsicano
	 * @param String
	 * @return String
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws GeneralException 
	 */
	public  String obtenerEndPointConfigActa(String endPointClave) {
		String rutaReportes=(System.getProperty("jboss.home.dir") == null ? "C:/jboss-as-7.1.1.Final" : System.getProperty("jboss.home.dir"))+"/modules/ve/gob/cne/sarc/main/";
		log.info("EndPoint a reportes: " + rutaReportes);
		File configFile = new File(rutaReportes+ "ReporteActa.properties");    
		String host = "";
		try{
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			host = props.getProperty(endPointClave);
			reader.close();
		} catch (FileNotFoundException ex) {
			log.info("ERROR Config file No Encontrado");
		} catch (IOException ex){
			log.info("ERROR I/O accediendo a Archivo Config");
		}
		log.info("direccion del host: "+host);
		return host;
	}

	/**
	 *Funcion que valida la existencia del certificado medico dentro de la base de datos 
	 * @author Maria Marsicano
	 * @param numCertificado String numero de certificado
	 * @return boolean
	 */
	@RequestMapping(value="/validarCertificadoEV/{numeroCertificadoDef}", method = RequestMethod.GET)
	public @ResponseBody Boolean validarCertificadoEV(@PathVariable("numeroCertificadoDef") Long numeroCertificadoDef){
		log.info("-----------------numCertificado-------- " + numeroCertificadoDef);      
		DefuncionServicioCliente servicioCliente = new DefuncionServicioCliente();
		boolean resp = servicioCliente.validarCertificadoMedicoDefuncion(numeroCertificadoDef);

		log.info("--------------resp----- " + resp);
		return resp;

	}

	/**
	 * Funcion que devuelve los datos del director de la ORE
	 * 
	 * @author Dairene Ramirez
	 * @param data: numero de solicitud
	 *            
	 * @return datos del director de la ORE
	 */
	public @ResponseBody Ore consultarDatos (String numSolicitud) throws JsonGenerationException, JsonMappingException, IOException{
		// ObjectMapper mapper= new ObjectMapper();
		//String retorno = null;
		//JSONObject objetoJs= new JSONObject();
		//Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class); 
		log.info("*********entrando al metodo consultarPermiso*******");   	
		Ore consulta= null;    	
		consulta =Defuncion.consultarDatos(numSolicitud);
		log.info("*********LA CONSULTA*******"+consulta);

		return consulta;
	}
	/**
	 * Funcion que devuelve una decision judicial
	 * 
	 * @author Elly Estaba
	 * @param data: numero de solicitud 
	 *            
	 * @return Decision Judicial 
	 */
	public DecisionJudicial consultarDJ(String numSolicitud) throws JsonGenerationException, JsonMappingException, IOException {
		DecisionJudicial IDJ = new DecisionJudicial();
		ActaServicioCliente AC = new ActaServicioCliente();
		IDJ = AC.consultarDecisionJudicial(numSolicitud);
		return IDJ;
	}
	/**
	 * Funcion que devuelve una defuncion
	 * 
	 * @author Elly Estaba
	 * @param data: numero de solicitud 
	 *            
	 * @return DEfuncion
	 */
	public ve.gob.cne.sarc.comunes.defuncion.Defuncion consultarDef(String numSolicitud) throws JsonGenerationException, JsonMappingException, IOException {
		ve.gob.cne.sarc.comunes.defuncion.Defuncion Def = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		DefuncionServicioCliente AC = new DefuncionServicioCliente();
		Def = AC.consultarDefuncion(numSolicitud);
		return Def;
	}
	/**
	 * Funcion que devuelve una lista de participantes
	 * 
	 * @author Elly Estaba
	 * @param data: numero de solicitud y variable (T,E)
	 *            
	 * @return Lista de objetos de participantes 
	 */
	public List<Participante> consultarParticPorSolicitud(String numSolicitud, String variable)
			throws JsonGenerationException, JsonMappingException, IOException {

		ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();
		List<Participante> participante;

		participante = participanteCliente.consultarParticPorSolicitud(numSolicitud, variable);
		return participante;
	}

	@RequestMapping(value = "/consultarOcupacion", method = RequestMethod.GET)
	public List<Ocupacion> consultarOcupacion() {
		Catalogo catalogo = new Catalogo();
		return catalogo.consultarOcupacion();

	}


	/**
	 * Funcion que devuelve una lista de nacionalidades desde catalogo
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            
	 * @return Lista de objetos tipo Catalogo
	 */
	@RequestMapping(value = "/cosultarNacionalidad", method = RequestMethod.GET)
	public List<Nacionalidad> cosultarNacionalidad() {
		Catalogo catalogo = new Catalogo();
		return catalogo.cosultarNacionalidades();

	 }
	
	 /**
		 * Funcion que devuelve una lista de objetos tipo catalogo 
		 * @author Elly Estaba
		 * @param data:        
		 * @return Lista de objetos tipo Catalogo
		 */
		@RequestMapping(value = "/consultarComunidadIndigenaTodos", method = RequestMethod.GET)
		public List<ComunidadIndigena> consultarComunidadIndigenaTodos() {
			Catalogo catalogo = new Catalogo();
			return catalogo.consultarComunidadIndigena();

	}

	/**
	 * Funcion que devuelve una lista de participantes
	 * 
	 * @author Dairene Ramirez
	 * @param data
	 *            :
	 * @return
	 * @return Lista de participantes
	 * @throws JSONException
	 */
	@RequestMapping(value = "/validarEcu", method = RequestMethod.POST)
	public String validarEcu(Ciudadano ciudadano) throws NullPointerException,
			JSONException {
		EcuServicioCliente ecuCliente = new EcuServicioCliente();
		JSONObject modelo = new JSONObject();
		try {
			List<DocumentoIdentidad> listDI = new ArrayList<>();
			// DocumentoIdentidad di = new DocumentoIdentidad();
			// di.setNumero("");
			// listDI.add(di);
			Ciudadano cd = new Ciudadano();
			cd.setDocumentoIdentidad(listDI);
			List<Participante> resp = ecuCliente.validarEcu(cd);
			if (resp == null) {
				Object value = null;
				modelo.put("valorECU", value);
			}
		} catch (Exception e) {
			System.out.println("Error al validar ECU");
		}
		return modelo.toString();
	}

	/**
	 * Funcion que actualiza los participantes de una solicitud
	 * @author Dairene Ramirez
	 * @param id de la solicitud String status
	 * @return Solicitud soli
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/actualizarParticipanteActa", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Solicitud actualizarParticipante(@RequestBody String data, HttpSession session, HttpServletResponse response) throws Exception {
		//log.info("DATA MODELO ID********" + data);
		log.info("****************************/actualizarParticipante**************************************");
		JSONObject datos = new JSONObject(data);
		JSONObject modelo = new JSONObject(data);
		Solicitud objSolicitud = new  Solicitud();  
		String numSolicitud = modelo.getString("id");
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);

		List<Participante> obetenerDatos = new ArrayList<Participante>();
		objSolicitud = solicitud.consultarDetalleSolicitud(datos.getString("id"));
		obetenerDatos = participanteServicioCliente.consultarParticPorSolicitud(sol.getNumeroSolicitud(), "T");
		log.info("participantes: ********** "+ obetenerDatos);
		log.info("****************************/actualizarParticipante FAL**************************************");
		JSONObject fal = datos.getJSONObject("FAL");
		log.info("JSON DEL ADOO********** "+ datos.getJSONObject("FAL"));

		try{
			if(fal.getJSONObject("comunidadIndigena").getString("nombre") != null){
				String ComInd = fal.getJSONObject("comunidadIndigena").getString("nombre");
				fal.remove("comunidadIndigena");
				fal.put("comunidadIndigena", ComInd);
			}
		}catch(Exception e){
		}

		try{
			if(fal.getJSONObject("profesion").getString("nombre") != null){
				String Prof = fal.getJSONObject("profesion").getString("nombre");
				modelo.put("ocupacion", "profesion");
				fal.remove("profesion");
				fal.put("ocupacion", Prof);
			}
		}catch(Exception e){}
		modelo.put("edadFal", "edad"); 
		fal.remove("edad");
		modelo.put("residenciaFal", "residenciaFallecido");
		fal.remove("residencia");	
		fal.remove("vive");
		try{
			if(fal.getString("estadoCivilFallecido") != null){
				String estCivil = fal.getString("estadoCivilFallecido");
				fal.put("estadoCivil", estCivil);
			}
		}catch(Exception e){}

		log.info(fal.getJSONArray("direccion").toString());
		try{
			log.info("JSON DEL FAL********** "+ datos.getJSONObject("FAL").getJSONArray("direccion"));
			if (datos.getJSONObject("FAL").getJSONArray("direccion") !=null){
				String estadoFal = datos.getJSONObject("FAL").getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").getString("nombre");

			}
			if (datos.getJSONObject("FAL").getJSONArray("direccion") !=null){
				String municipioFal = datos.getJSONObject("FAL").getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").getString("nombre");

			}
			if (datos.getJSONObject("FAL").getJSONArray("direccion") !=null){
				String parroquiaFal = datos.getJSONObject("FAL").getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").getString("nombre");

			}
			JSONObject tipoDP = new JSONObject();
			tipoDP.put("codigo", "RES");
			tipoDP.put("descripcion", "RESIDENCIA");
			tipoDP.put("nombre", "DIRECCION DE RESIDENCIA");
			fal.getJSONArray("direccion").getJSONObject(0).put("tipoDireccion", tipoDP);			
			fal.getJSONArray("direccion").getJSONObject(0).put("ubicacion", fal.getString("ubicacion"));
		}catch(Exception e){}
		JSONObject tipoDirNac = new JSONObject();
		tipoDirNac.put("codigo", "NAC");
		tipoDirNac.put("descripcion", "NACIMIENTO");
		tipoDirNac.put("nombre", "DIRECCION DE NACIMIENTO");
		try{
			fal.getJSONArray("direccion").getJSONObject(1).put("tipoDireccion", tipoDirNac);
			fal.getJSONArray("direccion").getJSONObject(1).put("ubicacion", fal.getString("lugarNacimiento"));
		}catch(Exception e){
			JSONObject objeto = new JSONObject();

			objeto.put("tipoDireccion", tipoDirNac);
			objeto.put("ubicacion", fal.getString("lugarNacimiento"));

			fal.getJSONArray("direccion").put(objeto);

		}

		//		try{
		//			if(fal.getString("lugarNacimiento") != null){
		//				String lugarNac = fal.getString("lugarNacimiento");
//				//fal.put("estadoCivil", estCivil);
//			
//		JSONObject tipoDADO = new JSONObject();
//		tipoDADO.put("codigo", "NAC");
//	  	tipoDADO.put("descripcion", "NACIMIENTO");
//	  	tipoDADO.put("nombre", "DIRECCION DE NACIMIENTO");
//	  	fal.getJSONArray("direccion").getJSONObject(0).put("lugarNacimiento", lugarNac);
//			}
//		}catch(Exception e){}
		log.info("objeto del fallecido----->" + fal.toString());
		ObjectMapper mapper = new ObjectMapper();
		Participante FAL = mapper.readValue(fal.toString(), Participante.class);
		ObjectMapper map1 = new ObjectMapper(); String obj1 = new String(); obj1 = map1.writeValueAsString(FAL.toString());	
		
		ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
		Participante FALLECIDO = servicios.actualizarParticipante(datos.getString("id"), FAL);
		try{
			if(datos.getJSONObject("COUNI")!= null){
				JSONObject couni = datos.getJSONObject("COUNI");
				try{
					if(couni.getJSONObject("profesion").getString("nombre") != null){
						String ProfCouni = couni.getJSONObject("profesion").getString("nombre");
						modelo.put("ocupacion", "profesion");
						couni.remove("profesion");
						couni.put("ocupacion", ProfCouni);
					}
				}catch(Exception e){}
				
				try{
					log.info("JSON DEL COUNI********** "+ datos.getJSONObject("COUNI").getJSONArray("direccion"));
					if (datos.getJSONObject("COUNI").getJSONArray("direccion") !=null){
						String estadoFal = datos.getJSONObject("COUNI").getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").getString("nombre");
						
					}
					if (datos.getJSONObject("COUNI").getJSONArray("direccion") !=null){
						String municipioFal = datos.getJSONObject("COUNI").getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").getString("nombre");
						
					}
					if (datos.getJSONObject("COUNI").getJSONArray("direccion") !=null){
						String parroquiaFal = datos.getJSONObject("COUNI").getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").getString("nombre");
						
					}
					JSONObject tipoDP = new JSONObject();
				      tipoDP.put("codigo", "RES");
				      tipoDP.put("descripcion", "RESIDENCIA");
				      tipoDP.put("nombre", "DIRECCION DE RESIDENCIA");
				      couni.getJSONArray("direccion").getJSONObject(0).put("tipoDireccion", tipoDP);			
				      couni.getJSONArray("direccion").getJSONObject(0).put("ubicacion", couni.getString("ubicacion"));
				}catch(Exception e){}

				log.info("JSON DEL COUNI********** "+ datos.getJSONObject("COUNI"));
				modelo.put("residenciaFal", "residencia");
				couni.remove("residencia");
				modelo.put("edadCouni", "edad"); 
				couni.remove("edad");

				ObjectMapper mapper1 = new ObjectMapper();
				Participante COUNI = mapper1.readValue(couni.toString(), Participante.class);

				Participante CONYUGUE = servicios.actualizarParticipante(datos.getString("id"), COUNI);
				log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + CONYUGUE);
			}
		}catch(Exception e){}


		log.info("****************************/actualizarParticipante HIJ**************************************");

		try{
			if(datos.getJSONObject("hij1")!= null){
				JSONObject hij1 = datos.getJSONObject("HIJ1");

				modelo.put("edadHij1", "edad"); 
				hij1.remove("edad");
				modelo.put("viveHij1", "vive"); 
				hij1.remove("vive");

				ObjectMapper mapper2 = new ObjectMapper();
				Participante HIJ1 = mapper2.readValue(hij1.toString(), Participante.class);

				Participante HIJO1 = servicios.actualizarParticipante(datos.getString("id"), HIJ1);
				log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + HIJO1);
			}
		}catch(Exception e){}


		log.info("****************************/actualizarParticipante MAM O MAD**************************************");
		try{
			if(datos.getJSONObject("MAM")!= null){
				JSONObject mam = datos.getJSONObject("MAM");

				modelo.put("edadMAM", "edad"); 
				mam.remove("edad");
				modelo.put("viveMAM", "vive"); 
				mam.remove("vive");

				ObjectMapper mapper3 = new ObjectMapper();
				Participante MAM = mapper3.readValue(mam.toString(), Participante.class);

				///ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
				log.info("********ANTES DE ACTUALIZAR MAMA**********" + MAM);
				Participante MAMA = servicios.actualizarParticipante(datos.getString("id"), MAM);
			} else if(datos.getJSONObject("MAD")!= null){
				JSONObject mad = datos.getJSONObject("MAD");

				modelo.put("edadMAD", "edad"); 
				mad.remove("edad");
				modelo.put("viveMAD", "vive"); 
				mad.remove("vive");
				modelo.put("mamaD", "mamaDeclarante"); 
				mad.remove("mamaDeclarante");	

				ObjectMapper mapper4 = new ObjectMapper();
				Participante MAD = mapper4.readValue(mad.toString(), Participante.class);

				///ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
				log.info("********ANTES DE ACTUALIZAR MAMA**********" + MAD);
				Participante MAMAD = servicios.actualizarParticipante(datos.getString("id"), MAD);
				log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + MAMAD);
			}
		}catch(Exception e){}

		log.info("****************************/actualizarParticipante PAP O PAD**************************************");
		try{
			if(datos.getJSONObject("PAP")!= null){
				JSONObject pap = datos.getJSONObject("PAP");

				modelo.put("edadPAP", "edad"); 
				pap.remove("edad");
				modelo.put("vivePAP", "vive"); 
				pap.remove("vive");	

				ObjectMapper mapper5 = new ObjectMapper();
				Participante PAP = mapper5.readValue(pap.toString(), Participante.class);

				///ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
				log.info("********ANTES DE ACTUALIZAR PAPA**********" + PAP);
				Participante PAPA = servicios.actualizarParticipante(datos.getString("id"), PAP);
			} else if(datos.getJSONObject("PAD")!= null){
				JSONObject pad = datos.getJSONObject("PAD");

				modelo.put("edadPAD", "edad"); 
				pad.remove("edad");
				modelo.put("vivePAD", "vive"); 
				pad.remove("vive");
				modelo.put("papaD", "papaDeclarante"); 
				pad.remove("papaDeclarante");	

				ObjectMapper mapper6= new ObjectMapper();
				Participante PAD = mapper6.readValue(pad.toString(), Participante.class);

				///ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
				log.info("********ANTES DE ACTUALIZAR PAPA**********" + PAD);
				Participante PAPA = servicios.actualizarParticipante(datos.getString("id"), PAD);
				log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + PAD);
			}
		}catch(Exception e){}

		log.info("****************************/actualizarParticipante DECLARANTE**************************************");
		try{
			if(datos.getJSONObject("DEC")!= null){
				JSONObject dec = datos.getJSONObject("DEC");
				log.info("JSON DEL declarante********** "+ datos.getJSONObject("DEC"));
				try{
					if(fal.getJSONObject("profesion").getString("nombre") != null){
						String ProfDecl = dec.getJSONObject("profesion").getString("nombre");
						modelo.put("ocupacion", "profesion");
						dec.remove("profesion");
						dec.put("ocupacion", ProfDecl);
					}
				}catch(Exception e){}

				modelo.put("edadDecl", "edad"); 
				dec.remove("edad");
				modelo.put("residenciaDecl", "residencia");
				dec.remove("residencia");
				try{
					log.info("JSON DEL DEC********** "+ datos.getJSONObject("DEC").getJSONArray("direccion"));
					if (datos.getJSONObject("DEC").getJSONArray("direccion") !=null){
						String estadoFal = datos.getJSONObject("DEC").getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").getString("nombre");
						
					}
					if (datos.getJSONObject("DEC").getJSONArray("direccion") !=null){
						String municipioFal = datos.getJSONObject("DEC").getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").getString("nombre");
						
					}
					if (datos.getJSONObject("DEC").getJSONArray("direccion") !=null){
						String parroquiaFal = datos.getJSONObject("DEC").getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").getString("nombre");
						
					}
					JSONObject tipoDP = new JSONObject();
				      tipoDP.put("codigo", "RES");
				      tipoDP.put("descripcion", "RESIDENCIA");
				      tipoDP.put("nombre", "DIRECCION DE RESIDENCIA");
				      dec.getJSONArray("direccion").getJSONObject(0).put("tipoDireccion", tipoDP);			
				      dec.getJSONArray("direccion").getJSONObject(0).put("ubicacion", dec.getString("ubicacion"));
				}catch(Exception e){}

				ObjectMapper mapper7 = new ObjectMapper();
				Participante DEC = mapper7.readValue(dec.toString(), Participante.class);

				Participante DECLARANTEFAL = servicios.actualizarParticipante(datos.getString("id"), DEC);
				log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + DECLARANTEFAL);
			}
		}catch(Exception e){}

		log.info("****************************/actualizarParticipante TA1**************************************");
		try{
			if(datos.getJSONObject("TA1")!= null){
				JSONObject ta1 = datos.getJSONObject("TA1");

				try{
					if(ta1.getJSONObject("comunidadIndigena").getString("nombre") != null){
						String ComIndTA1 = ta1.getJSONObject("comunidadIndigena").getString("nombre");
						ta1.remove("comunidadIndigena");
						ta1.put("comunidadIndigena", ComIndTA1);
					}
				}catch(Exception e){}

				try{
					if(fal.getJSONObject("profesion").getString("nombre") != null){
						String ProfTA1 = ta1.getJSONObject("profesion").getString("nombre");
						modelo.put("ocupacion", "profesion");
						ta1.remove("profesion");
						ta1.put("ocupacion", ProfTA1);
					}
				}catch(Exception e){}

				modelo.put("edadta1", "edad"); 
				ta1.remove("edad");
				modelo.put("residenciata1", "residencia");
				ta1.remove("residencia");	
				try{
					log.info("JSON DEL TA1********** "+ datos.getJSONObject("TA1").getJSONArray("direccion"));
					if (datos.getJSONObject("TA1").getJSONArray("direccion") !=null){
						String estadoFal = datos.getJSONObject("TA1").getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").getString("nombre");
						
					}
					if (datos.getJSONObject("TA1").getJSONArray("direccion") !=null){
						String municipioFal = datos.getJSONObject("TA1").getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").getString("nombre");
						
					}
					if (datos.getJSONObject("TA1").getJSONArray("direccion") !=null){
						String parroquiaFal = datos.getJSONObject("TA1").getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").getString("nombre");
						
					}
					JSONObject tipoDP = new JSONObject();
				      tipoDP.put("codigo", "RES");
				      tipoDP.put("descripcion", "RESIDENCIA");
				      tipoDP.put("nombre", "DIRECCION DE RESIDENCIA");
				      ta1.getJSONArray("direccion").getJSONObject(0).put("tipoDireccion", tipoDP);			
				      ta1.getJSONArray("direccion").getJSONObject(0).put("ubicacion", ta1.getString("ubicacion"));
				}catch(Exception e){}

				ObjectMapper mapper8 = new ObjectMapper();
				Participante TA1 = mapper8.readValue(ta1.toString(), Participante.class);

				///ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
				Participante TEST1 = servicios.actualizarParticipante(datos.getString("id"), TA1);
				log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + TEST1);
			}
		}catch(Exception e){}


		log.info("****************************/actualizarParticipante TA2**************************************");
		try{
			if(datos.getJSONObject("TA2")!= null){
				JSONObject ta2 = datos.getJSONObject("TA2");

				try{
					if(ta2.getJSONObject("comunidadIndigena").getString("nombre") != null){
						String ComIndTA2 = ta2.getJSONObject("comunidadIndigena").getString("nombre");
						ta2.remove("comunidadIndigena");
						ta2.put("comunidadIndigena", ComIndTA2);
					}
				}catch(Exception e){}
				try{
					if(fal.getJSONObject("profesion").getString("nombre") != null){
						String Profta2 = ta2.getJSONObject("profesion").getString("nombre");
						modelo.put("ocupacion", "profesion");
						ta2.remove("profesion");
						ta2.put("ocupacion", Profta2);
					}
				}catch(Exception e){}

				modelo.put("edadta2", "edad"); 
				ta2.remove("edad");
				modelo.put("residenciata2", "residencia");
				ta2.remove("residencia");
				try{
					log.info("JSON DEL TA2********** "+ datos.getJSONObject("TA2").getJSONArray("direccion"));
					if (datos.getJSONObject("TA2").getJSONArray("direccion") !=null){
						String estadoFal = datos.getJSONObject("TA2").getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").getString("nombre");
						
					}
					if (datos.getJSONObject("TA2").getJSONArray("direccion") !=null){
						String municipioFal = datos.getJSONObject("TA2").getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").getString("nombre");
						
					}
					if (datos.getJSONObject("TA2").getJSONArray("direccion") !=null){
						String parroquiaFal = datos.getJSONObject("TA2").getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").getString("nombre");
						
					}
					JSONObject tipoDP = new JSONObject();
				      tipoDP.put("codigo", "RES");
				      tipoDP.put("descripcion", "RESIDENCIA");
				      tipoDP.put("nombre", "DIRECCION DE RESIDENCIA");
				      ta2.getJSONArray("direccion").getJSONObject(0).put("tipoDireccion", tipoDP);			
				      ta2.getJSONArray("direccion").getJSONObject(0).put("ubicacion", ta2.getString("ubicacion"));
				}catch(Exception e){}

				ObjectMapper mapper9 = new ObjectMapper();
				Participante TA2 = mapper9.readValue(ta2.toString(), Participante.class);;
				Participante TEST2 = servicios.actualizarParticipante(datos.getString("id"), TA2);
				log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + TEST2);
			}
		}catch(Exception e){}


		  ////Guardando Defuncion

		  //Defuncion def = new Defuncion();
		ve.gob.cne.sarc.comunes.defuncion.Defuncion def = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
		Acta act = new Acta();
		JSONObject datosFAL = datos.getJSONObject("datosFAL");	
		log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + datosFAL);
		JSONObject autoridad = datos.getJSONObject("AUTORIDAD");
		String numeroActa = new String();
		String soliN = datos.getString("id");
		numeroActa = servicioActa.buscarNumeroActaPorSolic(soliN);
		def.setNumeroActa(numeroActa);

		////Guardando Datos defuncion
		Date f = new Date();	
		DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		Date date = inputFormat.parse( datos.getString("fechaDefuncion"));
		String outputText = outputFormat.format(date);
		DateFormat outputFormatCert = new SimpleDateFormat("dd/MM/yyyy");

		//	datos.put("fechaDefuncion", outputText);
		String estadoDef = "";
		String municipioDef = "";
		String parroquiaDef = "";
		try {
		estadoDef = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("estado").getString("nombre");
		municipioDef = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("municipio").getString("nombre");
		parroquiaDef = datosFAL.getJSONObject("direccion").getJSONObject("0").getJSONObject("parroquia").getString("nombre");
		}catch(Exception e ){}
		def.setEstadoDefuncion(estadoDef);
		def.setMunicipioDefuncion(municipioDef);
		def.setParroquiaDefuncion(parroquiaDef);
		def.setTextoCausa(datos.getString("causas"));	
		def.setNumeroCertificado(autoridad.getLong("numCert"));				
		Date fd = new Date();		
		JSONObject fechaCertificado = autoridad.getJSONObject("fechaCert");
		String diaDef = fechaCertificado.getString("dia2");
		String mesDef = fechaCertificado.getJSONObject("mes2").getString("nombre");
		String annoDef = fechaCertificado.getString("ano2");
		String fechaDef = diaDef+"/"+mesDef+"/"+annoDef;
		fd =  outputFormatCert.parse(fechaDef);
		def.setFechaCertificado(fd);
		def.setDocIdenMedico(autoridad.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero"));
		def.setPrimerNombreMedico(autoridad.getString("primerNombre"));
		try{
			if (def.getSegundoNombreMedico() !=null){
				def.setSegundoNombreMedico(autoridad.getString("segundoNombre"));
			}}catch(Exception e){}
		def.setPrimerApellidoMedico(autoridad.getString("primerApellido"));
		try{
			if (def.getSegundoApellidoMedico() !=null){
				def.setSegundoApellidoMedico(autoridad.getString("segundoApellido"));
			}}catch(Exception e){}
		def.setNuMPPS(autoridad.getLong("numMPPS"));
		def.setCentroSalud(autoridad.getString("centroSalud"));
		//modelo.put("centroSalud", "centroSalud");	

		////Guardando Extracto consular
		try {
		JSONObject ec = datos.getJSONObject("EC");
		def.setNumeroExtractoConsular(ec.getLong("numero"));	
		Date fde = new Date();		
		String diaCon = ec.getJSONObject("fecha").getString("dia2");
		String mesCon = ec.getJSONObject("fecha").getJSONObject("mes2").getString("nombre");
		String annoCon = ec.getJSONObject("fecha").getString("ano2");
		String fechaCon = diaCon+"/"+mesCon+"/"+annoCon;	
		fde =  outputFormatCert.parse(fechaCon);
		def.setFechaExtractoConsular(fde);
		def.setPrimerNombreConsular(ec.getString("primerNombre"));
		def.setTipoDoc(ec.getJSONObject("documentoIdentidad").getJSONObject("0").getJSONObject("tipoDocumento").getString("nombre"));
		def.setDocumentoIdentConsular(ec.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero"));
		try{
			if (def.getSegundoNombreConsular() !=null){
				def.setSegundoNombreConsular(ec.getString("segundoNombre"));
			}}catch(Exception e){}
		def.setPrimerApellidoConsular(ec.getString("primerApellido"));
		try{
			if (def.getSegundoApellidoConsular() !=null){
				def.setSegundoApellidoConsular(ec.getString("segundoApellido"));
			}}catch(Exception e){}
		}catch(Exception e){}
		ObjectMapper mapper20 = new ObjectMapper();
		JSONObject prueba = new JSONObject (mapper20.writeValueAsString(def));
		log.info("objedo defuncionnnnnnnn " + prueba.toString());
		boolean defSatisfactorio = servicioDefuncion.guardarDefuncion(def); 


		////Guardando Decision Judicial
		try{
		DecisionJudicial decJ = new DecisionJudicial();
		JSONObject extracto = datos.getJSONObject("EXTRACTO");
		JSONObject idj = datos.getJSONObject("IDJ");
		log.info("DEJ "+idj);
		log.info("*********Acta "+numeroActa);
		decJ.setNombreTribunal(idj.getString("tribunal"));
		decJ.setNumeroSentencia(idj.getString("sentencia")); 
		decJ.setPrimerNombreJuez(idj.getString("primerNombre"));
		try{
			if (decJ.getSegundoNombreJuez() !=null){
				decJ.setSegundoNombreJuez(idj.getString("segundoNombre"));
			}}catch(Exception e){}

		decJ.setPrimerApellidoJuez(idj.getString("primerApellido"));
		try{
			if (decJ.getSegundoApellidoJuez() !=null){
				decJ.setSegundoApellidoJuez(idj.getString("segundoApellido"));
			}}catch(Exception e){}
		Date fdj = new Date();
		String diaDJ = idj.getJSONObject("fecha").getString("dia2");
		String mesDJ = idj.getJSONObject("fecha").getJSONObject("mes2").getString("nombre");
		String annoDJ = idj.getJSONObject("fecha").getString("ano2");
		String fechaDJ = diaDJ+"/"+mesDJ+"/"+annoDJ;

		fdj =  outputFormatCert.parse(fechaDJ);
		decJ.setFechaSentencia(fdj);
		decJ.setCedulaJuez(idj.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero"));
		decJ.setNumeroActa(numeroActa);
		decJ.setExtractoProcedencia(extracto.getString("sentencia"));
		boolean decJSatisfactorio = servicioActa.guardarDecisionJudicial(decJ); 	
		}catch(Exception e){}

		////Guardando Observaciones
//		JSONObject ac= datos.getJSONObject("ACT");
//		act.setCircunstancias(ac.getString("circunstancias"));
//		boolean actSatisfactorio = servicioActa.actualizarActa(act);
//		modelo.put("observacionActa", "observacion");

		Solicitud solTest= new Solicitud();
		Solicitud soli = null;
		return soli;
	}

	@RequestMapping(value = "/guardarDef", method = RequestMethod.POST)
	public @ResponseBody String guardarDef(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)throws GeneralException, JsonParseException, JsonMappingException,IOException, JSONException {
		String numeroActa = new String();
		JSONObject modelo = new JSONObject(data);
		JSONObject modelLista = modelo.getJSONObject("pojo");
		String numSolicitud = modelo.getString("id");
		numeroActa = servicioActa.buscarNumeroActaPorSolic(numSolicitud);
	try{
		if(modelLista.getString("inscripcion") != null) {
			String fechaDefuncion = modelo.getString("fechaDefuncion");
			//String numeroCertificadoDef = modelLista.getString("numeroCertificadoDef");
			ve.gob.cne.sarc.comunes.defuncion.Defuncion defuncion = new ve.gob.cne.sarc.comunes.defuncion.Defuncion();
			Date fd = new Date();
			DateFormat outputFormatDef = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			fd =  outputFormatDef.parse(fechaDefuncion);
			defuncion.setNumeroActa(numeroActa);
			defuncion.setFechaDefuncion(fd);
			//defuncion.setNumeroCertificado(Long.parseLong(numeroCertificadoDef));
			boolean defSatisfactorio = servicioDefuncion.guardarDefuncion(defuncion);	
		}}catch(Exception e){}
	return numSolicitud;
}
	
	
}