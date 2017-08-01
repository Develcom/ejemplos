package ve.gob.cne.sarc.nacimiento.web.nacimiento.controllers.RNACI;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.bind.annotation.RestController;

import orgsarcreglas.nacimiento.Nacimiento;
import orgsarcreglas.rgdefuncion.RgDefuncion;
import orgsarcreglas.rgnacimiento.RgNacimiento;
import ve.gob.cne.sarc.acta.servicio.cliente.ActaServicioCliente;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.oficina.Ore;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.defuncion.servicio.cliente.DefuncionServicioCliente;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.funcionario.FuncionarioServicio;
import ve.gob.cne.sarc.generales.solicitud.SolicitudServicio;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.generales.util.formatDate;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.recaudo.servicio.cliente.RecaudoServicioCliente;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.Constantes;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;

@RestController
public class RgNacimientoController {

	@Autowired
	ServletContext context;

	Logger log = Logger.getLogger(getClass().getName());
	
	private final String rutaHtml = "/resources/site/RNACI/page/templates/";
	private final String rutaPDF = "/resources/site/RNACI/page/plantillas/";
	private final String oficioORE = "Oficio_ORE_nacimiento.jrxml";
	private final String RUTA_PLANTILLA = "/resources/site/RNACI/page/plantillas/";
	private final String PLANTILLA_NOTIFICACION = "notificacionRegistradorAdopcion.jrxml";
	private final String PLANTILLA_DJ = "Formato_ONRC_N_014.jrxml";
	private final String PLANTILLA_ACTA = "registro_de_nacimiento.jrxml";
	private final String RUTA_VISTA = "/resources/site/RADOP/page/templates/imprimir_notificacion.html";
	private final String RUTA_VISTA_ACTA = "/resources/site/RADOP/page/templates/imprimir_ActaValida.html";
	private final String RUTA_VISTA_DJ = "/resources/site/RNACI/page/templates/imprimir_acta.html";
	private final String RUTA_VISTA1 = "/resources/site/RNACI/page/templates/imprimir_acta.html";
	private final String RUTA_PV = "/resources/site/RADOP/page/templates/vista_notificacion.html";
	private final String RUTA_PV1 = "/resources/site/RADOP/page/templates/vista_previa.html";
	public static final String RUTA_PDF = "C:/tmp/"; // /local
	public static final String RUTA_PDF2 = "C:\\tmp\\"; // /local

	// public static final String RUTA_PDF = "/home/jboss/tmp/"; ///servidor
	private final String extfile = ".pdf";

	SolicitudServicio solicitud = new SolicitudServicio();
	RecaudoServicioCliente recaudo = new RecaudoServicioCliente();
	FuncionarioServicio funcionario = new FuncionarioServicio();
	SeguridadServicioCliente seguridadCliente = new SeguridadServicioCliente();
	FuncionarioServicioCliente funcionarioServicioCliente = new FuncionarioServicioCliente();

	@RequestMapping(value = "/iniciarTramite", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String iniciarAutenticar(@RequestBody String datos,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject modelo = new JSONObject(datos);
		JSONObject jsonSol = new JSONObject();
		JSONObject respuesta = new JSONObject();
		String token = request.getHeader("Authorization");
		log.info(token);
		String htmlInicio = null;
		Solicitud soli = new Solicitud();
		
		soli = this.solicitud.consultarDetalleSolicitud(modelo.getJSONObject("solicitud").getString("numeroSolicitud"));
		htmlInicio = buscarHtml(soli.getCodigoEstadoSolicitud());
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml
				+ htmlInicio + ".html"));

		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		// Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
		if (soli.getCodigoEstadoSolicitud().equals("AB")) {
			jsonSol = modelo.getJSONObject("solicitud");
			ObjectMapper mapper = new ObjectMapper();
			Solicitud result = mapper.readValue(jsonSol.toString(),
					Solicitud.class);
			Object objeto = null;
			String codigoTramite = result.getTramite().getCodigo();
			if (codigoTramite == null)
				return null;

			/**
			 * Determina el codigo del tramite
			 */
			if ("RNACI".equalsIgnoreCase(codigoTramite)) {
				objeto = new RgNacimiento();
				((RgNacimiento) objeto).setEscenario("GATE 0");
			} else {
				return null;
			}
			String rutaBase = "/resources/site/RNACI/page/templates/";

			modelo.put("claseModulo", objeto.getClass().getName());
			JSONObject mPojo = new JSONObject(mapper.writeValueAsString(objeto));
			modelo.put("titulo", "Registrar nacimiento");
			modelo.put("pojo", mPojo);
			modelo.put("rutaBase", rutaBase);
			String sol = jsonSol.toString();
			List<Participante> particiSolicitud;
			particiSolicitud = this.consultarParticPorSolicitud(
					jsonSol.getString("numeroSolicitud"), "T");
			ObjectMapper mapper1 = new ObjectMapper();
			modelo.put("valorMandatario",false);
			for (Participante participante1 : particiSolicitud) {
				JSONObject data1 = new JSONObject(
						mapper1.writeValueAsString(participante1));
				modelo.put(participante1.getRol(), data1);
				if (participante1.getRol()=="RMT"){
				modelo.put("valorMandatario", true);
			}else{
				modelo.put("valorMandatario", false);
			}
				
			}

			respuesta.put("modelo", modelo);
			// respuesta.put("vista", jsonFormly.getJSONArray("fields"));
			respuesta.put(
					"vista",
					Util.leerArchivo(context.getRealPath(rutaBase
							+ "vista_ciudadano_u_organo.html")));

		} else if (soli.getCodigoEstadoSolicitud().equals("PEA")) {
			htmlInicio = buscarHtml(soli.getCodigoEstadoSolicitud());
			modelo.put("titulo", "Datos del presentado(a)");
			respuesta.put("vista", htmlValido);
			respuesta.put("modelo", modelo);
		}
		return respuesta.toString();
	}

	@RequestMapping(value = "/consultarDrools", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String consultarDrools(@RequestBody String datos,
			HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws Exception {
		JSONObject modelo = new JSONObject(datos);// Util.getDataFromRequest(request);
		JSONObject pojoJson = modelo.getJSONObject("pojo");
		log.info("el pojoJson tiene: " + pojoJson.toString());
		ObjectMapper mapper = new ObjectMapper();
		Object pojo = mapper.readValue(pojoJson.toString(),
				Class.forName(modelo.getString("claseModulo")));
		log.info(pojo.toString());
		String nombreClase1 = pojo.getClass().getName()
				.replace(pojo.getClass().getPackage().getName() + ".", "");
		// crea una instancia del cliente de reglas de negocio para el tramite
		// especifico
		Object kie = Class.forName(
				"ve.gob.cne.sarc.reglas.servicio.cliente.ReglasServicioCliente"
						+ nombreClase1).newInstance();
		log.info(kie.toString());
		// realiza la consulta al motor de reglas de negocio
		log.info("******************" + pojo.toString());
		Object pojo2 = validarKie(kie, pojo);
		log.info("el pojo2 tiene: " + pojo2.toString());
		// Recupera el nombre de la proxima vista a ser presentada
		log.info("vamos a ver que pasa aca");
		log.info(pojo2.toString());
		JSONObject jsonPojo = new JSONObject(mapper.writeValueAsString(pojo2));
		log.info(jsonPojo.toString());
		String rutaBase = modelo.getString("rutaBase");
		modelo.put("pojo", jsonPojo);
		JSONObject respuesta = new JSONObject();
		log.info(jsonPojo.getString("vista"));
		String vistaDrools = jsonPojo.getString("vista");
		if (!"No hay mas vista que mostrar por ser un Nodo Terminal"
				.equals(vistaDrools)) {
			// JSONObject jsonFormly = new
			// JSONObject(Util.leerArchivo(context.getRealPath(rutaBase +
			// vistaDrools + ".html")));
			// JSONObject modeloFormly = jsonFormly.getJSONObject("modelo");
			respuesta.put(
					"vista",
					Util.leerArchivo(context.getRealPath(rutaBase + vistaDrools
							+ ".html")));
		}
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	// ------------------------ Acta Controller--------------------------- //
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Jose Leonardo Dos Ramos
	 * @param data
	 *            : contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/actaControlador", method = RequestMethod.POST)
	public @ResponseBody String consultarPreg2(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException,
			IOException, JSONException {
		log.info("*************hoooooolaaa   RNACI_ACTA************"
				+ sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String rutaBase = "/resources/site/RNACI/page/templates/";
		String vista = null;

		vista = rutaBase + this.buscarHtml(modelo.getString("statu"));
		respuesta.put("vista",
				Util.leerArchivo(context.getRealPath(vista + ".html")));
		log.info("******Contenido Html************"
				+ Util.leerArchivo(context.getRealPath(vista + ".html")));
		modelo.put("titulo", "Datos del(la) Presentado(a)");
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
	
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Jose Leonardo Dos Ramos
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/datosCertificadoNacimiento", method = RequestMethod.POST)
    public @ResponseBody String datosCertificadoNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos certificado--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_certificado_medico_presentado.html"));     
        modelo.put("titulo","Datos del certificado");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/datosMadreNacimiento", method = RequestMethod.POST)
    public @ResponseBody String datosMadreNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos madre--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_madre.html"));     
        modelo.put("titulo","Datos de la madre");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/datosPadreNacimiento", method = RequestMethod.POST)
    public @ResponseBody String datosPadre(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos padre--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_padre.html"));     
        modelo.put("titulo","Datos del padre");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/datosDeclaranteNacimiento", method = RequestMethod.POST)
    public @ResponseBody String datosDeclaranteNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos declarante--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_declarante.html"));     
        modelo.put("titulo","Datos del declarante");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/datosTestigosNacimiento", method = RequestMethod.POST)
    public @ResponseBody String datosTestigoNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos testigos--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_testigos.html"));     
        modelo.put("titulo","Datos de los testigos");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/datosActaInsertarNacimiento", method = RequestMethod.POST)
    public @ResponseBody String datosActaInsertarNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos acta a insertar--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_acta_insertar.html"));     
        modelo.put("titulo","Datos del acta a insertar");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/inscripcionMedidaProteccionNacimiento", method = RequestMethod.POST)
    public @ResponseBody String inscripcionMedidaProteccionNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------inscripcionMedidaProteccion--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"inscripcion_medida_proteccion.html"));     
        modelo.put("titulo","Medida de Proteccion");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/inscripcionDecisionJudicialNacimiento", method = RequestMethod.POST)
    public @ResponseBody String inscripcionDecisionJudicialNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------inscripcionDecisionJudicial--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"inscripcion_decision_judicial.html"));     
        modelo.put("titulo","Decision Judicial");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }

    @RequestMapping(value="/inscripcionExtemporaneaNacimiento", method = RequestMethod.POST)
    public @ResponseBody String inscripcionExtemporaneaNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
    	log.info("--------inscripcionExtemporanea--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"inscripcion_extemporanea.html"));     
        modelo.put("titulo","Inscripcion Extemporanea");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/circunstanciaEspecialNacimiento", method = RequestMethod.POST)
    public @ResponseBody String circunstanciaEspecialNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------circunstanciaEspecial--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"circunstancias_especiales_acto.html"));     
        modelo.put("titulo","Circunstancias Especiales/Obs..");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    
    @RequestMapping(value="/documentosPresentadosNacimiento", method = RequestMethod.POST)
    public @ResponseBody String documentosPresentadosNacimiento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------documentosPresentados--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml
				+ "documentos_presentados.html"));
		modelo.put("titulo", "Documentos presentados");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	@RequestMapping(value="/fueraDrools", method = RequestMethod.POST)
    public @ResponseBody String fueraDrools(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------entrando a fuera de drools--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_determinar_partos_multiples.html"));     
        modelo.put("titulo","Solicitud de Registro");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        
        log.info(respuesta.getString("vista"));
        log.info(respuesta.getString("modelo"));
        return respuesta.toString();
    }
    
    @RequestMapping(value="/cantidadHijos", method = RequestMethod.POST)
    public @ResponseBody String cantidadHijos(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------entrando a fuera de drools--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_determinar_cantidad_hijos.html"));     
        modelo.put("titulo","Solicitud de Registro");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        
        log.info(respuesta.getString("vista"));
        log.info(respuesta.getString("modelo"));
        return respuesta.toString();
    }

    @RequestMapping(value="/determinarApellido", method = RequestMethod.POST)
    public @ResponseBody String determinarApellido(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_determinar_apellido.html"));     
        modelo.put("titulo","Casos Especiales");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        
        log.info(respuesta.getString("vista"));
        log.info(respuesta.getString("modelo"));
        return respuesta.toString();
    }
    
    @RequestMapping(value="/decJurada", method = RequestMethod.POST)
    public @ResponseBody String decJurada(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_declaracion_jurada.html"));     
        modelo.put("titulo","Declaracion jurada");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        
        log.info(respuesta.getString("vista"));
        log.info(respuesta.getString("modelo"));
        return respuesta.toString();
    }
	/**
	 * Funcion que devuelve el modelo y la vista a mostrar en el controlador js
	 * 
	 * @author Elly Estaba
	 * @param data
	 *            : contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */

	@RequestMapping(value = "/imprimirActaNacimiento", method = RequestMethod.POST)
	public @ResponseBody String imprimirActaNacimiento(
			@RequestBody String data, HttpSession sesion,
			HttpServletRequest request) throws GeneralException,
			JsonParseException, JsonMappingException, IOException,
			JSONException, JRException {
		log.info("MODELO RRFILL ACTA");
		// log.info("*************RADOP ADOPTADOOO************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		log.info("MODELO RRFILL ACTA" + modelo);
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		// //////////acabo de modificar esooo....
		String token = request.getHeader("Authorization");

		// JSONObject modeloReporte = generarReporteActa((String)
		// modelo.get("id"), (String) modelo.get("estatu"), token,
		// modelo.getJSONObject("data"));
		JSONObject modeloReporte = generarReporte(
				(String) modelo.getString("id"),
				(String) modelo.getString("estatu"), token);
		// String vista = Util.leerArchivo(context.getRealPath(rutaHtml +
		// "imprimir_acta.html"));
		// log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Vista previa del acta de nacimiento");
		respuesta.put("vista", modeloReporte.get("vista"));
		// respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	@RequestMapping(value = "/verificacionPadresNacimiento", method = RequestMethod.POST)
	public @ResponseBody String verificacionPadresNacimiento(
			@RequestBody String data, HttpSession sesion,
			HttpServletRequest request) throws GeneralException,
			JsonParseException, JsonMappingException, IOException,
			JSONException {

		log.info("--------verificaciondeclarante--------");
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml
				+ "verificacion_padres.html"));
		modelo.put("titulo", "Verificacion del declarante");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}

	@RequestMapping(value = "/finalizarTramiteNacimiento", method = RequestMethod.POST)
	public @ResponseBody String finalizarTramiteNacimiento(
			@RequestBody String data, HttpSession sesion,
			HttpServletRequest request) throws GeneralException,
			JsonParseException, JsonMappingException, IOException,
			JSONException {
		// log.info("*************RADOP ADOPTADOOO************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml
				+ "satisfactorio.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("mensaje", "Impresi�n exitosa.");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * validarReporteConformeORE.java
	 * 
	 * @descripcion Metodo que actualiza el cambio de estatus
	 * 
	 * @version 1.0 11/6/2016
	 * 
	 * @author Dairene Ramirez /*
	 * 
	 * @param numCertificado
	 *            String numero de certificado
	 * 
	 * @return Solicitud soli
	 */
	@RequestMapping(value = "/actualizarEstadoNacimiento", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Solicitud actualizarEstadoNacimiento(@RequestBody String data,
			HttpSession session, HttpServletResponse response) throws Exception {
		JSONObject dataModelo = new JSONObject(data);
		// JSONObject pojoJson = dataModelo.getJSONObject("pojo");
		DefuncionServicioCliente servicioCliente = new DefuncionServicioCliente();
		String numSolicitud = dataModelo.getString("id");
		String estatus = dataModelo.getString("estatu");
		String permiso = dataModelo.getString("permiso1");
		String parametro = estatus;
		Solicitud solicitudSesion = new Solicitud();

		if (("PEA").equals(estatus) && ("conforme").equals(permiso)) {
			solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
			solicitudSesion
					.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC_ACTA);
			solicitudSesion
					.setMotivoCambioEstado("NO_CONFORME_POR_REGISTRADOR_CIVIL");
		} else if (("PEA").equals(estatus) && ("noConforme").equals(permiso)) {
			solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
			solicitudSesion
					.setEstadoSolicitud(Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL);
			solicitudSesion
					.setMotivoCambioEstado("NO_CONFORME_POR_REGISTRADOR_CIVIL_ACTA");
		}
		SolicitudServicio solicitudActualizar = new SolicitudServicio();
		Solicitud soli = new Solicitud();
		soli = solicitudActualizar.actualizaEstadoSolicitud(solicitudSesion);
		log.info("Solicitud modificada: " + soli.getEstadoSolicitud().toString());
		solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
		solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
		solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");
		soli = solicitudActualizar.actualizaEstadoSolicitud(solicitudSesion);
		log.info("Solicitud de vuelta a la normalidad: " + soli.getEstadoSolicitud().toString());
		return soli;
	}

	/**
	 * Funcion que genera el reporte de notificacion al registrador civil
	 * 
	 * @author Elly E. Estaba M
	 * @param data
	 *            : contiene los datos obtenidos del formulario para generar el
	 *            reporte
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	public JSONObject generarReporte(String modelo, String estatu, String token)
			throws JRException, JsonGenerationException, JsonMappingException,
			IOException, JSONException {
		// TODO Auto-generated method stub
		log.info("******modelo reporte*****" + modelo);
		log.info("******statu reporte*****" + estatu);
		log.info("******token reporte*****" + token);
		String rutaFin = RUTA_PDF + modelo + ".pdf";
		// String rutaPlantilla = context.getRealPath(RUTA_PLANTILLA +
		// PLANTILLA_NOTIFICACION);
		// rutaFin = rutaPlantilla.replace(PLANTILLA_NOTIFICACION, modelo +
		// ".pdf");

		// /String rutaReportes = obtenerEndPointConfig("endPointRutaReportes");
		String vista = null;
		String rutaPlantilla = null;

		// String login = seguridadCliente.getUsernameCliente(token);
		// FuncionarioServicioCliente servFuncionario = new
		// FuncionarioServicioCliente();
		// Funcionario obtenerDatosFuncionario = servFuncionario
		// .buscarPorLogin(login);
		//
		// String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
		// log.info("codoficina!!!!!!!!!"
		// + obtenerDatosFuncionario.getOficina().getCodigo());
		//
		// // String tipoPermisoData = null;
		//
		// List<Participante> part = new ArrayList<>();
		// String codTipoFuncionario = "RP";
		//
		// // String parentesco ="";
		// String codEstatusFuncionario = "ACT";
		// Funcionario dataServicio = new Funcionario();
		//
		// /*dataServicio =
		// funcionarioServicioCliente.buscarFuncionarioPorOficina(
		// codOficina, codTipoFuncionario, codEstatusFuncionario);*/
		// Oficina datosOficina = new Oficina();
		// //datosOficina = obtenerDatosFuncionario.getOficina();
		//
		// CatalogoServicioCliente nConsecutivo = new CatalogoServicioCliente();
		// /*Long obtenerNconsecutivo = nConsecutivo
		// .proximoNroConsecutivo(codOficina);*/
		// //log.info("--------obtenerNconsecutivo----- " +
		// obtenerNconsecutivo);
		//
		// List<Participante> obtenerDatos = new ArrayList<Participante>();
		// List<Participante> obtenerDatosADO = new ArrayList<Participante>();
		//
		// // JSONObject modeloD = new JSONObject(modelo);
		// // JSONObject RJson = new JSONObject(modelo);
		// // String numSolicitud = modeloD.getString("id");
		// String[] codTipo = { "ADO" };

		Solicitud sol = solicitud.consultarDetalleSolicitud(modelo);
		ParticipanteServicioCliente participante = new ParticipanteServicioCliente();

		// obtenerDatos = participante.consultarParticPorSolicitud(modelo, "T");
		// obtenerDatosADO = participante.consultarParticPorTipo(modelo,
		// codTipo);

		// String PrimerNombreADO = null;
		// PrimerNombreADO = obtenerDatosADO.get(0).getPrimerNombre();
		// String SegundoNombreADO = null;
		// SegundoNombreADO = obtenerDatosADO.get(0).getSegundoNombre() == null
		// ? " "
		// : obtenerDatosADO.get(0).getSegundoNombre();
		//
		// String ApellidoADO = null;
		// ApellidoADO = obtenerDatosADO.get(0).getPrimerApellido();
		// String SegundoApellidoADO = null;
		// SegundoApellidoADO = obtenerDatosADO.get(0).getSegundoApellido() ==
		// null ? " "
		// : obtenerDatosADO.get(0).getSegundoApellido();
		//
		HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
		//
		// String fechaResolucion = formatDate.convertirDateAString(dataServicio
		// .getFechaResolucion());
		// String fechaGaceta = formatDate.convertirDateAString(dataServicio
		// .getFechaGaceta());

		// Adopcion dataConsulta =this.consultarDatos(modelo);
		// consulta para los datos.
		// datosAPintar.put(
		// "nombreRegistrador",
		// StringUtils.capitalize(obtenerDatosFuncionario
		// .getPrimerNombre().toLowerCase())
		// + ' '
		// + StringUtils.capitalize(obtenerDatosFuncionario
		// .getPrimerApellido().toLowerCase()));
		// datosAPintar.put("nombreOficinaRegistro",
		// (datosOficina.getNombre().toUpperCase()));
		// datosAPintar.put(
		// "parroquia",
		// StringUtils.capitalize(datosOficina.getDireccion()
		// .getParroquia().getNombre().toLowerCase()));
		// datosAPintar.put(
		// "municipio",
		// StringUtils.capitalize(datosOficina.getDireccion()
		// .getMunicipio().getNombre().toLowerCase()));
		// datosAPintar.put(
		// "estado",
		// StringUtils.capitalize(datosOficina.getDireccion().getEstado()
		// .getNombre().toLowerCase()));
		// datosAPintar.put("oficinaNacimiento", "OFICINA");
		// datosAPintar.put("nConsecutivo", "obtenerNconsecutivo.toString()");
		// datosAPintar.put("nResolucion",
		// String.valueOf(dataServicio.getNumeroResolucion())); // /"45632");//
		// datosAPintar.put("fResolucion", fechaGaceta); // "10/01/2016");
		// datosAPintar.put("nGaceta",
		// String.valueOf(dataServicio.getNumeroGaceta())); // /
		// // "G-00632");
		// datosAPintar.put("fGaceta", fechaResolucion); // "10/01/2016" ); //
		// datosAPintar.put("numActa", "----");
		// datosAPintar.put("fechaActa", "10/01/2016");
		// datosAPintar.put("nombreApellidoAdoptado", "PrimerNombreADO" + ' '
		// + "SegundoNombreADO" + ' ' + "ApellidoADO" + ' '
		// + "SegundoApellidoADO");
		//
		// // Datos decision Judicial...
		// datosAPintar.put("numero", "obtenerNconsecutivo.toString()");
		// datosAPintar.put("nombreCiudadano", "Juan Pablo Martinez");
		// datosAPintar.put("juezTribunal", "Tribunal Superior");
		// datosAPintar.put("fecha", "15/09/2016"); // fechaGaceta);
		// datosAPintar.put("nombreNacimiento", "PrimerNombreADO" + ' '
		// + "SegundoNombreADO" + ' ' + "ApellidoADO" + ' '
		// + "SegundoApellidoADO");
		// datosAPintar.put("nActa", "----");
		// datosAPintar.put("nFolio", "---");
		// datosAPintar.put("annoLibro", "YYYY");
		// datosAPintar.put("fechaSentencia", "10/01/2016");
		// // datosAPintar.put("nombreRegistrador",
		// //
		// StringUtils.capitalize(obtenerDatosFuncionario.getPrimerNombre().toLowerCase())+' '+StringUtils.capitalize(obtenerDatosFuncionario.getPrimerApellido().toLowerCase()));
		// datosAPintar.put("oficinaRegistro",
		// (datosOficina.getNombre().toUpperCase()));

		datosAPintar.put("numero", "----");
		datosAPintar.put("nombreCiudadano", "----");
		datosAPintar.put("juezTribunal", "----");
		datosAPintar.put("fecha", "----");
		datosAPintar.put("nombreNacimiento", "----");
		datosAPintar.put("nActa", "----");
		datosAPintar.put("nFolio", "----");
		datosAPintar.put("annoLibro", "----");
		datosAPintar.put("fechaSentencia", "----");
		datosAPintar.put("oficinaRegistro", "----");
		datosAPintar.put("nResolucion", "----");
		datosAPintar.put("fResolucion", "----");
		datosAPintar.put("nGaceta", "----");
		datosAPintar.put("fGaceta", "----");

		if ("PV".equals(estatu) || "PI".equals(estatu)) {

			rutaPlantilla = context.getRealPath(RUTA_PLANTILLA
					+ PLANTILLA_NOTIFICACION);
			rutaFin = RUTA_PDF + "" + modelo + "" + extfile;

		} else if ("IDJ".equals(estatu)) {
			log.info("entrooo al elseeee " + RUTA_PLANTILLA + PLANTILLA_DJ);
			rutaPlantilla = context.getRealPath(RUTA_PLANTILLA + PLANTILLA_DJ);
			rutaFin = RUTA_PDF + "" + modelo + "" + extfile;
			log.info("ruta pdf " + rutaFin);
		} else if ("PEA".equals(estatu)) {
			log.info("PLANTILLA HECTOR ALEJANDRO " + RUTA_PLANTILLA
					+ PLANTILLA_DJ);
			rutaPlantilla = context.getRealPath(RUTA_PLANTILLA + PLANTILLA_DJ);
			rutaFin = RUTA_PDF + "" + modelo + "" + extfile;
			log.info("ruta pdf " + rutaFin);
		}

		JasperReport jasperReport = JasperCompileManager
				.compileReport(rutaPlantilla);
		log.info("rutaPlantilla " + rutaPlantilla);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				datosAPintar, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);

		// String template = "<embed width='850' height='550' src='" +
		// rutaReportes + modelo
		// String template="<embed width='850' height='550' src='"+
		// "/web-generales/tmp/" +"XXXX"+
		// ".pdf' type='application/pdf'></embed>";
		// + ".pdf' type='application/pdf'></embed>";
		// String template="<iframe width='800' id='plugin' height='800' src='"+
		// "/web-nacimiento/tmp/" + modelo +
		// ".pdf#toolbar=0' type='application/pdf'></iframe>";
		String template = "<iframe width='800' id='plugin' height='800' src='"
				+ "/web-generales/tmp/" + modelo
				+ ".pdf#toolbar=0' type='application/pdf'></iframe>";
		if ("PI".equals(estatu)) {
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA));
		} else if ("PV".equals(estatu)) {
			vista = Util.leerArchivo(context.getRealPath(RUTA_PV));
		} else if ("IDJ".equals(estatu)) {
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA_DJ));
		} else if ("PEA".equals(estatu)) {
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA_DJ));
		} else {
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA1));
		}
		// if ("PEA".equals(estatu)){
		// vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA1));
		// }else if ("PPI".equals(estatu)){
		// vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA_ACTA));
		// }else

		vista = vista.replace("ARCHIVOPDF", template);
		JSONObject formulario = new JSONObject();
		formulario.put("vista", vista);
		return formulario;
	}

	public String buscarHtml(String estatus) {

		// log.info("ROLLLL:"+rol);
		// log.info("constante: "+Constantes.ROL_REGISTRADOR);
		// log.info("constanteEstatu: "+Constantes.ABIERTA);
		log.info("estat dentro de buscarHTML" + estatus);
		String html = null;

		// if((Constantes.ROL_REGISTRADOR).equals(rol)){
		if ((Constantes.ROL_REGISTRADOR).equals("R_REG")) { // *******
			// cableado***///
			if (("AB").equals(estatus)) {
				html = "vista_ciudadano_u_organo";
			} else if (("PEA").equals(estatus)) {
				html = "vista_datos_presentado";
			} else if (("PENDIENTE POR VERIFICAR R.C").equals(estatus)) {
				html = "datos_fallecido";
			} else if (("NO CONFORME POR REGISTRADOR CIVIL").equals(estatus)) {
				html = "ver_observaciones";
			} else if (("NO CONFORME POR REGISTRADOR CIVIL ACTA")
					.equals(estatus)) {
				html = "datos_fallecido";
			} else if (("PENDIENTE POR CARGAR DOCUMENTO").equals(estatus)) {
				html = "cargar_documento";
			}

		}

		log.info("busca html: " + html);
		return html;
	}

    @RequestMapping(value="/validarActa", method = RequestMethod.POST)
    public @ResponseBody boolean validarActa(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------entrando a fuera de drools--------");
        JSONObject modelo = new JSONObject(data);
        log.info(modelo.toString());
        log.info(modelo.getJSONObject("solicitud").getJSONObject("tramite").getString("codigo"));
        boolean respuesta = this.consultarEV25(modelo.getJSONObject("solicitud").getJSONObject("tramite").getString("codigo"),modelo.getLong("numeroEV25"));
        log.info("la respuesta---->"+respuesta);
        return respuesta;
    }
    
    @RequestMapping(value="/finalizar1", method = RequestMethod.POST)
    public @ResponseBody String finalizar1(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------entrando en finalizar1--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_finalizar1.html"));     
        modelo.put("titulo","Solicitud de Registro");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        log.info("mostrar la vista----------------------------------");
        log.info(respuesta.getString("vista"));
        log.info("mostrar el modelo---------------------------------");
        log.info(respuesta.getString("modelo"));
        return respuesta.toString();
    }
    
    @RequestMapping(value="/motivosNoEv25", method = RequestMethod.POST)
    public @ResponseBody String motivoNoEv25(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------entrando en finalizar1--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_motivos_no_ev25.html"));     
        modelo.put("titulo","Solicitud de Registro");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        log.info("mostrar la vista----------------------------------");
        log.info(respuesta.getString("vista"));
        log.info("mostrar el modelo---------------------------------");
        log.info(respuesta.getString("modelo"));
        return respuesta.toString();
    }
    @RequestMapping(value="/oficioORE1", method = RequestMethod.POST)
    public @ResponseBody String oficioORE1(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------entrando en finalizar1--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_ore1.html"));     
        modelo.put("titulo","Oficio a la ORE");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        log.info("mostrar la vista----------------------------------");
        log.info(respuesta.getString("vista"));
        log.info("mostrar el modelo---------------------------------");
        log.info(respuesta.getString("modelo"));
        return respuesta.toString();
    }
    
    @RequestMapping(value="/oficioORE2", method = RequestMethod.POST)
    public @ResponseBody String oficioORE2(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------en este momento grabaremos lo que se lleva en especial planilla ore--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_ore1.html"));     
        modelo.put("titulo","Oficio a la ORE");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        log.info("mostrar la vista----------------------------------");
        log.info(respuesta.getString("vista"));
        log.info("mostrar el modelo---------------------------------");
        log.info(respuesta.getString("modelo"));
        
        Solicitud sol = new Solicitud();
        Solicitud solFinal = new Solicitud();
        sol.setNumeroSolicitud(modelo.getJSONObject("solicitud").getString("numeroSolicitud"));
        sol.setMotivoCambioEstado("PV");
        sol.setEstadoSolicitud("PV");
        
        SolicitudServicioCliente claseServicioSolicitud = new SolicitudServicioCliente();
        solFinal = claseServicioSolicitud.actualizarEstadoSolicitud(sol);
        log.info(solFinal.toString());
        
        
        
        
//        esto es para grabar el ore, por ahora no funciona bien
        
//        ActaServicioCliente claseActa = new ActaServicioCliente();
//        Ore ore = new Ore();
//        String numeroSol = modelo.getJSONObject("solicitud").getString("numeroSolicitud");
//        ore = claseActa.guardarORE(numeroSol);
//        log.info(ore.toString());
        return respuesta.toString();
    }
    
    @RequestMapping(value = "/vistaPreviaORE", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String vistaPreviaORE(@RequestBody String datos, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{

    	log.info("--------entrando en vista Previa ORE--------");
        JSONObject modelo = new JSONObject(datos);
        JSONObject respuesta = new JSONObject();
        log.info(modelo.toString());
        Enumeration<String> lista = request.getAttributeNames();
        log.info(lista.toString());
//        String token=(String) session.getAttribute("access_token");
        String token = request.getHeader("Authorization");
        log.info("token------>"+token);
        String estatusReporte = "";
        String numeroSolicitud = modelo.getJSONObject("estado").getString("numeroSolicitud");
        JSONObject reportePrevio = generarReporte2(numeroSolicitud,estatusReporte,token);
        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_previa_ore1.html"));     
        modelo.put("titulo","Oficio a la ORE");
        log.info(reportePrevio.getString("vista"));
        respuesta.put("vista", reportePrevio.getString("vista"));
        respuesta.put("modelo", modelo);
        
        return respuesta.toString();
    }
    
    public JSONObject generarReporte2(String modelo, String estatu, String token)throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException {
    	JSONObject respuesta = new JSONObject();
    	log.info("******modelo reporte*****" + modelo);
		log.info("******statu reporte*****" + estatu);
		log.info("******token reporte*****" + token);
		String vista = null;
		String rutaFin = RUTA_PDF + modelo+ ".pdf";
		log.info(rutaFin);
		HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
		String login = seguridadCliente.getUsernameCliente(token);
		FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
        Funcionario datosSer = servFuncionario.buscarPorLogin(login);
        
        String codOficina = datosSer.getOficina().getCodigo();
        Oficina ofi = datosSer.getOficina();
        log.info("funcionario de tipo ----->" + datosSer.getTipoFuncionario());
        try {
        String nombreOficina = datosSer.getOficina().getDescripcion();
        String nResolucion = Integer.toString(datosSer.getNumeroResolucion());
        String fResolucion = datosSer.getFechaResolucion().toString();
        String nGaceta = Integer.toString(datosSer.getNumeroGaceta());
        String fGaceta = datosSer.getFechaGaceta().toString();
        
        
		
		
		datosAPintar.put("oficinaRegistro", nombreOficina);
		datosAPintar.put("nResolucion", nResolucion);
		datosAPintar.put("fResolucion", fResolucion);
		datosAPintar.put("nGaceta", nGaceta);
		datosAPintar.put("fGaceta", fGaceta);
		datosAPintar.put("nombreDirectorOficina", "");
		datosAPintar.put("estadoOficina", "");
		datosAPintar.put("nombreCiudadano", "");
		datosAPintar.put("cedulaOficina", "");
		datosAPintar.put("nombreNacimiento", "");
		datosAPintar.put("lugarNacimiento", "");
		datosAPintar.put("parroquiaNacimiento", "");
		datosAPintar.put("municipioNacimiento", "");
		datosAPintar.put("estadoMunicipio", "");
		datosAPintar.put("fechaNacimiento", "");
        }catch(Exception e){
        	log.info(e.toString());
        	datosAPintar.put("oficinaRegistro", "");
    		datosAPintar.put("nResolucion", "");
    		datosAPintar.put("fResolucion", "");
    		datosAPintar.put("nGaceta", "");
    		datosAPintar.put("fGaceta", "");
    		datosAPintar.put("nombreDirectorOficina", "");
    		datosAPintar.put("estadoOficina", "");
    		datosAPintar.put("nombreCiudadano", "");
    		datosAPintar.put("cedulaOficina", "");
    		datosAPintar.put("nombreNacimiento", "");
    		datosAPintar.put("lugarNacimiento", "");
    		datosAPintar.put("parroquiaNacimiento", "");
    		datosAPintar.put("municipioNacimiento", "");
    		datosAPintar.put("estadoMunicipio", "");
    		datosAPintar.put("fechaNacimiento", "");
        }
		
		////////////////////
//		JasperReport jasperReport = JasperCompileManager.compileReport(rutaPDF+oficioORE);
		JasperReport j = JasperCompileManager.compileReport(context.getRealPath(rutaPDF+oficioORE));
		JasperPrint jasperPrint = JasperFillManager.fillReport(j, datosAPintar, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);
		log.info("modelo------------>" + modelo);
		String template="<iframe width='800' id='plugin' height='800' src='"+ "/web-generales/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
		vista = Util.leerArchivo(context.getRealPath("/resources/site/RNACI/page/templates/vista_previa_ore1.html"));
		log.info(vista);
		
		vista = vista.replace("ARCHIVOPDF", template);
		log.info(vista);
		respuesta.put("vista", vista);
    	return respuesta;
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
				log.info("**************Escenario: " + escenario);
				break;
			}
		}
		Method[] metodos2 = kie.getClass().getMethods();
		for (Method metodo : metodos2) {
			log.info("Nombre del metodo: " + metodo.getName());
			if (("validar" + nombreClase).equals(metodo.getName())) {
				log.info("************evaluando defuncion ******************");
				try {
					objeto2 = metodo.invoke(kie, objeto);
				} catch (Exception e) {
					log.info("************ERROR: " + e.getMessage()
							+ "*************");
					e.printStackTrace();
					throw e;
				}
			}
		}
		return objeto2;
	}

	public List<Participante> consultarParticPorSolicitud(String numSolicitud,
			String variable) throws JsonGenerationException,
			JsonMappingException, IOException {
		// / ObjectMapper mapper= new ObjectMapper();
		// JSONObject objetoJs= new JSONObject();
		// Map<String, String> data =
		// / mapper.readValue(request.getHeader("datos"), Map.class);
		log.info("*********entrando al metodo consultarParticpante*******");
		log.info("*******" + numSolicitud + "*********" + variable
				+ "*********");
		ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();
		List<Participante> participante;
		participante = participanteCliente.consultarParticPorSolicitud(
				numSolicitud, variable);

		return participante;
	}
	
	public boolean consultarEV25(String codigoTramite, long numeroCertificado)throws JsonGenerationException,
	JsonMappingException, IOException{
		ActaServicioCliente actaServCli = new ActaServicioCliente();
		
		boolean respuesta = actaServCli.validarCertificadoMedico(codigoTramite, numeroCertificado);
		return respuesta;
	}
}