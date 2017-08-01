package ve.gob.cne.sarc.nacimiento.web.nacimiento.controllers.RADOP;

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
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.StdDateFormat;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import orgsarcreglas.rgadopcion.RgAdopcion;
import ve.gob.cne.sarc.acta.servicio.cliente.ActaServicioCliente;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.acta.ActaPrimigenia;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.comunes.catalogo.ComunidadIndigena;
import ve.gob.cne.sarc.comunes.catalogo.Nacionalidad;
import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.catalogo.Recaudo;
import ve.gob.cne.sarc.comunes.catalogo.TipoDireccion;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.nacimiento.Nacimiento;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.recaudo.servicio.cliente.RecaudoServicioCliente;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.Constantes;
import ve.gob.cne.sarc.generales.catalogo.Catalogo;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.funcionario.FuncionarioServicio;
import ve.gob.cne.sarc.generales.participante.Participantes;
import ve.gob.cne.sarc.generales.reporte.GenerarActas;
import ve.gob.cne.sarc.generales.solicitud.SolicitudServicio;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.generales.util.formatDate;

@RestController
public class RgAdopcionController {
	// private static final String String = null;

	@Autowired
	ServletContext context;

	Logger log = Logger.getLogger(getClass().getName());
	private final String rutaHtml = "/resources/site/RADOP/page/templates/";
	private final String RUTA_PLANTILLA = "/resources/site/RADOP/page/plantillas/";
	private final String PLANTILLA_NOTIFICACION = "notificacionRegistradorAdopcion.jrxml";
	private final String PLANTILLA_DJ = "Formato_ONRC_N_014.jrxml";
	private final String PLANTILLA_ACTA= "registro_de_nacimiento.jrxml";
	private final String RUTA_VISTA = "/resources/site/RADOP/page/templates/imprimir_notificacion.html";
	private final String RUTA_VISTA_ACTA = "/resources/site/RADOP/page/templates/imprimir_ActaValida.html";
	private final String RUTA_VISTA_DJ = "/resources/site/RADOP/page/templates/DJ_imprimir.html";
	private final String RUTA_VISTA1 = "/resources/site/RADOP/page/templates/imprimir_acta.html";
	private final String RUTA_PV = "/resources/site/RADOP/page/templates/vista_notificacion.html";
	private final String RUTA_PV_ACT = "/resources/site/RADOP/page/templates/vista_acta.html";
	private final String RUTA_PV1 = "/resources/site/RADOP/page/templates/vista_previa.html";
	private final String rutaLogo="resources/img/";
//	public static final String RUTA_PDF = "C:/tmp/";  ///local
     public static final String RUTA_PDF = "/home/jboss/tmp/";  ///servidor
	private final String extfile=".pdf";
	RecaudoServicioCliente recaudo = new RecaudoServicioCliente();
	   
	   SolicitudServicio solicitud = new SolicitudServicio();

	    SeguridadServicioCliente seguridadCliente = new SeguridadServicioCliente();
	    Solicitud sol = new Solicitud();


	    FuncionarioServicio funcionario = new FuncionarioServicio();
	    CatalogoServicioCliente catalogoServicio = new CatalogoServicioCliente();
	    FuncionarioServicioCliente funcionarioServicioCliente = new FuncionarioServicioCliente();

	/**
	 * Funcion que devuelve una vista y modelo de cada inicio del proceso
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/iniciarTramiteRADOP", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String iniciarTramite(@RequestBody String data, HttpSession sesion, HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException {

		// public @ResponseBody String iniciarTramite(@RequestBody String data,
		// HttpServletRequest request,
		// HttpSession session, HttpServletResponse response) throws Exception {
		// log.info("*****************************token"+session.getAttribute("access_token"));
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		//
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		// session.setAttribute("token", token);
		// TODO REMOVER EL COMENTARIO DE LAS SIGUIENTES 3 LINEAS CUANDO EXISTA
		// INTEGRACION CON GPT
		// PaqueteBO paqueteBO = obtenerPaquete(Long.valueOf(data.get("id")));
		// Solicitud result = new
		// ObjectMapper().readValue(paqueteBO.getPayload()
		// .toJSONString(), Solicitud.class);
		// TODO ELIMINAR LAS SIGUIENTES LINEAS CUANDO EXISTA INTEGRACION CON GPT
		/*
		 * SolicitudServicioCliente cSol= new SolicitudServicioCliente();
		 * log.info("********Consultando el ID: "+data.get("id")); Solicitud
		 * result = cSol.consultarDetalleSolicitud(data.get("id"));
		 */
 
		String token=request.getHeader("Authorization");
		log.info("**********************request:  "+request.toString());
	    log.info("**********************Token:  "+token);
		JSONObject modelo = new JSONObject(data);
		String numSolicitud = modelo.getString("id");
		String htmlInicio = null;
		
/////////////////////////////////////////////////////////
		Object objeto = new RgAdopcion();
		ObjectMapper mapper = new ObjectMapper();
		//JSONObject respuesta = new JSONObject();
		JSONObject pojo = new JSONObject(mapper.writeValueAsString(objeto));
		pojo.put("escenario", "GATE 0");
		//pojo.remove("vista");
		((RgAdopcion)objeto).setEscenario("GATE 0");
		String rutaBase = "/resources/pages/templates/";
		modelo.put("rutaBase", rutaBase);
		pojo.put("inscripOInserc", true);
		modelo.put("claseModulo", objeto.getClass().getName());
		modelo.put("pojo", pojo);
////////////////////////////////////////////////////////

		// Funcionario datoFuncionario =Util.buscarFuncionarioPorLogin(token);
		// String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
		Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);

		ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();

		List<Participante> particiSolicitud;
		particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");

		List<Participante> participante;
		String[] codigoTipo = { "ADO" };
		participante = participanteCliente.consultarParticPorTipo(numSolicitud, codigoTipo);
		log.info("********participante.consultarParticPorTipo***" + participante.size());
		htmlInicio = buscarHtml(sol.getEstadoSolicitud(), participante.size());

		/* paara traer los datos del participante */

		Participantes.consultarParticPorTipo(sol.getNumeroSolicitud(), codigoTipo);
		log.info("*****************entro: " + sol.getNumeroSolicitud());
		log.info("ROOOOOOOOOO0OL: " + codigoTipo);

		// session.setAttribute("solicitud", sol);
		// session.setAttribute("rol", codRol);
		// session.setAttribute("status",sol.getEstadoSolicitud() );
		String htmlCompila = Util.leerArchivo(context.getRealPath(rutaHtml + htmlInicio + ".html"));
		log.info("******Contenido Html************" + htmlCompila);
		// String htmlValido = null;
		// JSONArray jsonA= new JSONArray();

		// JSONObject jsonObj= new JSONObject();

		ObjectMapper mapper1 = new ObjectMapper();
		for (Participante participante1 : particiSolicitud) {
			JSONObject data1 = new JSONObject(mapper1.writeValueAsString(participante1));
			modelo.put(participante1.getRol(), data1);
		}
		JSONObject json = new JSONObject();
		if ("PV".equals(modelo.getString("estatu"))) {
			JSONObject modeloReporte = generarReporte((String) modelo.get("id"), (String) modelo.get("estatu"), token);

			//String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "imprimir_notificacion.html"));
			//log.info("******Contenido Html************" + vista);
			json.put("vista", modeloReporte.get("vista"));
			modelo.put("titulo", "Solicitud de registro");
			json.put("modelo", modelo);
			
		} else if ("NC".equals(modelo.getString("estatu"))) {
			modelo.put("titulo", "Ver Observaciones");
			modelo.put("observacion", sol.getMotivoCambioEstado());
			json.put("vista", htmlCompila);
			json.put("modelo", modelo);
			
		}else if ("PCN".equals(modelo.getString("estatu"))) {
			modelo.put("titulo", "Cargar documento");
			json.put("vista", htmlCompila);
			json.put("modelo", modelo);
			
		}else if ("PEA".equals(modelo.getString("estatu"))) {
			modelo.put("titulo", "Datos del(de la) adoptado(a)");
			json.put("vista", htmlCompila);
			json.put("modelo", modelo);
			
		}  else if ("PVR".equals(modelo.getString("estatu"))) {
			JSONObject modeloReporte = generarReporteActa(data, (String) modelo.get("id"), (String) modelo.get("estatu"), token);

			//String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "imprimir_notificacion.html"));
			//log.info("******Contenido Html************" + vista);
			json.put("vista", modeloReporte.get("vista"));
			modelo.put("titulo", "Revisi\u00f3n del acta");
			json.put("modelo", modelo);
			
//			modelo.put("titulo", "Datos del(la) adoptado(a)");
//			json.put("vista", htmlCompila);
//			json.put("modelo", modelo);
		
		} else if ("NCA".equals(modelo.getString("estatu"))) {
			modelo.put("titulo", "Ver Observaciones");
			modelo.put("observacionActa", sol.getMotivoCambioEstado());
			json.put("vista", htmlCompila);
			json.put("modelo", modelo);
			
		} else if ("PPD".equals(modelo.getString("estatu"))) {
			modelo.put("titulo", "Cargar documento");
			json.put("vista", htmlCompila);
			json.put("modelo", modelo);
		
		}  else if ("CDJ".equals(modelo.getString("estatu"))) {
			modelo.put("titulo", "Cargar documento");
			json.put("vista", htmlCompila);
			json.put("modelo", modelo);
		
		}else {
			modelo.put("titulo", "Datos para la solicitud");
			json.put("vista", htmlCompila);
			json.put("modelo", modelo);
		}
		// htmlValido =
		// "{\"datosAPintar\":"+particiSolicitud+",\"html\":"+htmlCompila+"}";
		log.info("******JSON************" + json.toString());
		return json.toString();
	}

	/**
	 * Funcion que devuelve el nombre de un html para mostrar segun el estado
	 * del proceso
	 * 
	 * @author Elly Estaba
	 * @param String:
	 *            estaus
	 * @return String
	 */
	public String buscarHtml(String estatus, int participante) {
		log.info("*************participante html:" + participante);
		log.info("estat" + estatus);
		String html = null;
		// int uno= 1;
		// int cero= 0;
		// if(rol.equals(Constantes.ROL_INSCRIPCION)){
		if (("ABIERTA").equals(estatus)) {
			if (participante == 1) {
				html = "adoptado_ueh";
				log.info("Adoptadooooooo_ueh");
			} else if (participante == 0) {
				html = "adoptado_hijo";
				log.info("Adoptadooooooo_hijoooo");
			}
		}
		// else {
		// log.info("************* datos errados **************");
		//
		// }

		// }else if(rol.equals(Constantes.ROL_REGISTRADOR)){

		else if (("PENDIENTE POR VERIFICAR R.C").equals(estatus)) {
			html = "vista_notificacion";
		} else if(("NO CONFORME POR REGISTRADOR CIVIL").equals(estatus)){
            html="ver_observaciones";
        }else if (("PENDIENTE POR ELABORAR ACTA").equals(estatus)) {
			html = "datos_adoptado";
			log.info("Adoptadooooooo ACTA");
			
		}else if (("PENDIENTE POR VERIFICACION R.C").equals(estatus)) {
			log.info("PENDIENTE POR VERIFICAR RC ACT******!!!");
			html = "vista_acta";
			log.info("Adoptadooooooo ACTA en VErificacion!!!");
		
		}else if(("NO CONFORME POR REGISTRADOR CIVIL ACTA").equals(estatus)){
            html="ver_observacionesActa";
        }else if (("PENDIENTE POR CARGAR DOCUMENTO").equals(estatus)) {
			log.info("CARGAR DOCUMENTO******!!!");
			html = "CargarDoc";
			log.info("Adoptadooooooo ACTA en VErificacion!!!");
			
		}else if (("PENDIENTE POR CARGAR DOCUMENTO DJ").equals(estatus)) {
			log.info("CARGAR DOCUMENTO DJ******!!!");
			html = "DJ_CargarDoc";
			log.info("Adoptadooooooo ACTA en VErificacion!!!");
		}else if (("PENDIENTE POR CARGAR NOTIFICACION").equals(estatus)) {
			log.info("CARGAR DOCUMENTO NOT******!!!");
			html = "NT_CargarDoc";
			log.info("Adoptadooooooo NOTIFICACION!!!");
		}

		log.info("********************************************");
		log.info("*******busca html: " + html);
		return html;

	}

	// ------------------------ Listado de Vistas--------------------------- //
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_adoptado_hijo", method = RequestMethod.POST)
	public @ResponseBody String consultarPreg2(@RequestBody String data, HttpSession sesion, HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		// log.info("*************RADOP************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "adoptado_hijo.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Datos para la solicitud");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_adoptado", method = RequestMethod.POST)
	public @ResponseBody String consultarAdoptado(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		// log.info("*************RADOP ADOPTADO************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "datos_adoptado_solicitud_adopcion.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Datos para la solicitud");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una lista con datos documento identidad
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	private List<DocumentoIdentidad> consultarDocID(String codigo, String numero, String nombre){
		  TipoDocumento tipoDocumento = new TipoDocumento();
          tipoDocumento.setCodigo(codigo);
          tipoDocumento.setNombre(nombre);

          DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
          documentoIdentidad.setTipoDocumento(tipoDocumento);
          documentoIdentidad.setNumero(numero);

          List<DocumentoIdentidad> documentosIdentidad = new ArrayList<>();
          documentosIdentidad.add(documentoIdentidad);
          return documentosIdentidad;
		
	}
	
	


	/**
	 * Funcion que devuelve una vista y modelo a presentar 
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_acta_prim", method = RequestMethod.POST)
	public @ResponseBody String consultarActaPrim(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		// log.info("*************RADOP Acta
		// PRIMIGENIA************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }		
		
		Solicitud solicitudCompleta= new Solicitud();
	    solicitudCompleta = solicitud.consultarDetalleSolicitud(modelo.getString("id"));
	    log.info("********acta primigenia***");
		ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();
		List<Participante> participante;
		String[] codigoTipo = { "ADO" };
		participante = participanteCliente.consultarParticPorTipo(modelo.getString("id"), codigoTipo);
		log.info("********ID...SOLICITUD///***" + modelo.getString("id"));
		log.info("********consultarParticPorTipo///***" + participante.size());

		if (participante.size()==0){
			 log.info("********entro al if***");
			    Participante ADO = new Participante();
				JSONObject JADO = modelo.getJSONObject("ADO");
              
              List<DocumentoIdentidad> listDoc = new ArrayList<>();
			   DocumentoIdentidad doc = new DocumentoIdentidad(); 
			   String Nom= null;
			   String Num= null;
			   String Cod= null;
              try{
			       if(JADO.getJSONArray("documentoIdentidad")!= null)  {
			       Num= JADO.getJSONArray("documentoIdentidad").getJSONObject(0).getString("numero");   
				   doc.setNumero(Num);
				   TipoDocumento tipoDocumento=new TipoDocumento();
				   doc.setTipoDocumento(tipoDocumento);
				   Nom =JADO.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("nombre");
				   tipoDocumento.setNombre(Nom);
					   if(JADO.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("nombre")=="CEDULA"){
						  Cod = "CED";  
					   }else{
						  Cod = "PAS";
					   }   
				   tipoDocumento.setCodigo(Cod);
				   listDoc.add(doc);
				   ADO.setDocumentoIdentidad(listDoc); 
			       log.info(JADO.getJSONArray("documentoIdentidad").getJSONObject(0).getString("nombre"));
			  }
		  } catch(Exception e){
			   Num= null;   
			   doc.setNumero(Num);
			   TipoDocumento tipoDocumento=new TipoDocumento();
			   doc.setTipoDocumento(tipoDocumento);
			   Nom =null;
			   tipoDocumento.setNombre(Nom);
			   Cod = null;  
			   tipoDocumento.setCodigo(Cod);
			   listDoc.add(doc);
			   ADO.setDocumentoIdentidad(listDoc);  
		  }
             String TD = null;
             try{
             if(JADO.getString("tipoDocumento")!= null){
            	TD = JADO.getString("tipoDocumento");}
             else if(JADO.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").getString("nombre")=="PASAPORTE"){
	    		TD="P";
	    	}
             }catch(Exception e){
            	 TD= null;
             }

	       ADO.setTipoDocumento(TD);
		   ADO.setRol("ADO");
		   ADO.setPrimerNombre(JADO.getString("primerNombre"));   
		   ADO.setSegundoNombre(JADO.getString("segundoNombre"));
		   ADO.setPrimerApellido(JADO.getString("primerApellido"));
		   ADO.setSegundoApellido(JADO.getString("segundoApellido"));
		   ADO.setSexo(JADO.getString("sexo"));
		   String Nac = null;
		   try{
			JADO.getJSONObject("nacionalidad").remove("$$hashKey");
			Nac = JADO.getJSONObject("nacionalidad").getString("nombre");
			JADO.remove("nacionalidad");
	    	JADO.put("nacionalidad", Nac);
		   }catch(Exception e){
		   Nac = "VENEZOLANA";
		   }
		   ADO.setNacionalidad(Nac);

		   log.info("ver ADOOOOOOOOO");
		   log.info("ver ADOOOOOOOOO"+JADO.getString("primerNombre"));

	     Participante participanteNew = participanteCliente.registrarParticPorSolicitud(modelo.getString("id"),ADO);
	     if (participanteNew == null){
	    	  log.info("********Es Nulooooooo***");
	     }else{
	     log.info("********guardooooooooooooooooooo***");
	     }
		}
		else {
			log.info("paso al else");
//			Participante ADO = new Participante();
			JSONObject DADO = modelo.getJSONObject("ADO");

			ObjectMapper mapper = new ObjectMapper();
			Participante  ADO = mapper.readValue(DADO.toString(), Participante.class);
			log.info("ver el modeloooo"+DADO);
		ParticipanteServicioCliente serv = new ParticipanteServicioCliente();
		Participante ADOPTADO = serv.actualizarParticipante(modelo.getString("id"), ADO);

		}
		
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "datos_acta_solicitud_adopcion.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Datos para la solicitud");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar 
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_recaudos", method = RequestMethod.POST)
	public @ResponseBody String consultarrecaudos(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		// log.info("*************RADOP Acta
		// PRIMIGENIA************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "recaudos.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Validar recaudos");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_buscarActa", method = RequestMethod.POST)
	public @ResponseBody String consultarBuscarActa(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "buscarActa.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Datos para la solicitud");
		modelo.put("mensaje", "Buscando datos del acta...");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que actualiza el estatu de una solicitud
	 * @author Elly Estaba
	 * @param id de la solicitud String status
	 * @return Solicitud soli
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/actualizarEstado", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Solicitud actualizarEstatuSolicitud(@RequestBody String data, HttpSession session, HttpServletResponse response) throws Exception {
		log.info("DATA MODELO ID********" + data);
		JSONObject dataModelo = new JSONObject(data);

		Solicitud solicitudSesion = new  Solicitud();  
		// String parametro=dataModelo.getString("estatu");
		solicitudSesion = solicitud.consultarDetalleSolicitud(dataModelo.getString("id"));
	  
//	  	solicitudSesion		sera reemplazado por solTest..   
		///@RequestMapping por resources
		
		Solicitud solTest= new Solicitud();
		
		if (("AB").equals(dataModelo.get("estatu"))) {
			log.info("**************Enviar datos de la solicitud AB");
			log.info("**************SOLICITUD ---> "+dataModelo.getString("id"));
			log.info("**************ESTADO ---> "+Constantes.PENDIENTE_POR_VERIFICAR_RC);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");
			/*solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
			solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
			solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");*/

		} else if (("NC").equals(dataModelo.get("estatu"))) {
			log.info("**************ACTUALIZAR NO CONFORME");
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");
			
		} else if (("PV").equals(dataModelo.get("estatu")) && ("conforme").equals(dataModelo.get("permiso"))) {
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR);
			solTest.setMotivoCambioEstado("CAMBIO DE ESTADO");
			solTest.setNumeroSolicitud(dataModelo.getString("id"));

		} else if (("PV").equals(dataModelo.get("estatu")) && ("noConforme").equals(dataModelo.get("permiso"))) {
			solTest.setEstadoSolicitud(Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL);
			solTest.setMotivoCambioEstado(dataModelo.getString("observacion"));
			solTest.setNumeroSolicitud(dataModelo.getString("id"));

		} else if (("PI").equals(dataModelo.get("estatu"))) {
			log.info("**************Cambiar estatus a ****** PNC");
			log.info("**************id" + dataModelo.getString("id") );
			log.info("**************Constantes.PENDIENTE_POR_CARGAR_NOTIFICAION " + Constantes.PENDIENTE_POR_CARGAR_NOTIFICAION);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_NOTIFICAION);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_NOTIFICAION");
			
		} else if (("PCN").equals(dataModelo.get("estatu"))) {
			log.info("**************Cambiar estatus a ****** PEA");
			log.info("**************id" + dataModelo.getString("id") );
			log.info("**************Constantes.PENDIENTE_POR_ELABORAR_ACTA " + Constantes.PENDIENTE_POR_ELABORAR_ACTA);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");
			
		}else if (("PEA").equals(dataModelo.get("estatu")) && ("conforme").equals(dataModelo.get("permiso1"))) {
			log.info("**************Cambiar estatus a ****** PVR");
			log.info("**************id" + dataModelo.getString("id") );
			log.info("**************Constantes.PENDIENTE_POR_ELABORAR_ACTA " + Constantes.PENDIENTE_POR_VERIFICAR_RC_ACTA);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC_ACTA);
			solTest.setMotivoCambioEstado("NO_CONFORME_POR_REGISTRADOR_CIVIL");
			
		}else if (("PEA").equals(dataModelo.get("estatu")) && ("noConforme").equals(dataModelo.get("permiso1"))) {
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL);
			solTest.setMotivoCambioEstado("NO_CONFORME_POR_REGISTRADOR_CIVIL");
			
			
		}else if (("PVR").equals(dataModelo.get("estatu")) && ("conforme").equals(dataModelo.get("permisoRC"))) {
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR_ACTA);
			solTest.setMotivoCambioEstado("CAMBIO DE ESTADO");
			

		} else if (("PVR").equals(dataModelo.get("estatu")) && ("noConforme").equals(dataModelo.get("permisoRC"))) {
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL_ACTA);
			solTest.setMotivoCambioEstado(dataModelo.getString("observacionActa"));	
		
		}else if (("NCA").equals(dataModelo.get("estatu"))) {
			log.info("**************ACTUALIZAR NO CONFORME ACTA");
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC_ACTA);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC_ACTA II");
			
		}else if (("PPI").equals(dataModelo.get("estatu"))) {
			log.info("**************Cambiar estatus a ****** PPD");
			log.info("**************id" + dataModelo.getString("id") );
			log.info("**************Constantes.PENDIENTE_POR_ELABORAR_ACTA " + Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOCUMENTO");
			
		}else if (("PPD").equals(dataModelo.get("estatu")) && ("true").equals(dataModelo.get("DJ"))) {
			log.info("**************Cambiar estatus a ****** IDJ");
			log.info("**************id" + dataModelo.getString("id") );
			log.info("**************Constantes.PENDIENTE_POR_imprimir " + Constantes.PENDIENTE_POR_IMPRIMIR_DJ);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR_DJ);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_IMPRIMIR_DJ");
			
		}else if (("PPD").equals(dataModelo.get("estatu")) && ("false").equals(dataModelo.get("DJ"))) {
			log.info("**************Cambiar estatus a ****** PRNM");
			log.info("**************id" + dataModelo.getString("id") );
			log.info("**************Constantes.PENDIENTE_POR_registrar nota " + Constantes.PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL");
			
		}else if (("IDJ").equals(dataModelo.get("estatu"))) {
			log.info("**************Cambiar estatus a ****** CDJ");
			log.info("**************id" + dataModelo.getString("id") );
			log.info("**************Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO_DJ" + Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO_DJ);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO_DJ);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOCUMENTO_DJ");
			
		}else if (("CDJ").equals(dataModelo.get("estatu"))) {
			log.info("**************Cambiar estatus a ****** PRM");
			log.info("**************id" + dataModelo.getString("id") );
			log.info("**************Constantes.PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL" + Constantes.PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL);
			solTest.setNumeroSolicitud(dataModelo.getString("id"));
			solTest.setEstadoSolicitud(Constantes.PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL);
			solTest.setMotivoCambioEstado("PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL");		
		}
		
		
		
		
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	SolicitudServicio solicitudActualizar = new SolicitudServicio();
		Solicitud soli = SolicitudServicio.actualizaEstadoSolicitud(solTest);
		///System.out.println("****Actualizar*****" + soli);
		
		//Solicitud soli = null;
		return soli;
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_verificacionRC", method = RequestMethod.POST)
	public @ResponseBody String consultarVerificacionRC(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "verificacion_registrador.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Verificaci\u00f3n del(de la) R.C.");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve el modelo y la vista a mostrar en el controlador js
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	@RequestMapping(value = "/RADOP_presentarImprimir", method = RequestMethod.POST)
	public @ResponseBody String consultarPresentarImprimir(@RequestBody String data, HttpSession sesion, HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException {
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
		String token=request.getHeader("Authorization");
		JSONObject modeloReporte = generarReporte((String) modelo.get("id"), (String) modelo.get("estatu"), token);

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "imprimir_notificacion.html"));
		log.info("******Contenido Html************" + vista);
		respuesta.put("vista", modeloReporte.get("vista"));
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	
	/**
	 * Funcion que genera el reporte de notificacion al registrador civil
	 * 
	 * @author Elly E. Estaba M
	 * @param data:
	 * contiene los datos obtenidos del formulario para generar el
	 * reporte
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	public JSONObject generarReporte(String modelo, String estatu, String token)throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException {
		// TODO Auto-generated method stub
		log.info("******modelo reporte*****" + modelo);
		log.info("******statu reporte*****" + estatu);
		log.info("******token reporte*****" + token);
		String rutaFin = RUTA_PDF + modelo+ ".pdf";
		String RutaIMAGE=context.getRealPath(rutaLogo);
		//String rutaPlantilla = context.getRealPath(RUTA_PLANTILLA + PLANTILLA_NOTIFICACION);
		//rutaFin = rutaPlantilla.replace(PLANTILLA_NOTIFICACION, modelo + ".pdf");
		
		///String rutaReportes = obtenerEndPointConfig("endPointRutaReportes");
		String vista = null; 
		String rutaPlantilla = null;
		
		 	String login = seguridadCliente.getUsernameCliente(token);
	        FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
	        Funcionario obtenerDatosFuncionario = servFuncionario.buscarPorLogin(login);

	        String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
	        log.info("codoficina!!!!!!!!!"+obtenerDatosFuncionario.getOficina().getCodigo());
         
	      //  String tipoPermisoData = null;

	        List<Participante> part = new ArrayList<>();
	        String codTipoFuncionario = "RP";
	        
	       // String parentesco ="";
	        String codEstatusFuncionario = "ACT";
	        Funcionario dataServicio = new Funcionario();
            
	        dataServicio = funcionarioServicioCliente.buscarFuncionarioPorOficina(codOficina, codTipoFuncionario, codEstatusFuncionario);
	        Oficina datosOficina = new Oficina();
	        datosOficina = obtenerDatosFuncionario.getOficina();
	        
	        CatalogoServicioCliente nConsecutivo = new  CatalogoServicioCliente();
	        Long obtenerNconsecutivo = nConsecutivo.proximoNroConsecutivo(codOficina);
	        log.info("--------obtenerNconsecutivo----- " + obtenerNconsecutivo);

		List<Participante> obtenerDatos = new ArrayList<Participante>();
		List<Participante> obtenerDatosADO = new ArrayList<Participante>();
	
			//JSONObject modeloD = new JSONObject(modelo);
			//JSONObject RJson = new JSONObject(modelo);
			//String numSolicitud = modeloD.getString("id");
			String[] codTipo = {"ADO"};
				
			Solicitud sol = solicitud.consultarDetalleSolicitud(modelo);
			ParticipanteServicioCliente participante = new ParticipanteServicioCliente();
				
			obtenerDatos = participante.consultarParticPorSolicitud(modelo, "T");		
			obtenerDatosADO = participante.consultarParticPorTipo(modelo, codTipo);

			String PrimerNombreADO= null;
			PrimerNombreADO = obtenerDatosADO.get(0).getPrimerNombre();
			String SegundoNombreADO = null;
			SegundoNombreADO = obtenerDatosADO.get(0).getSegundoNombre()==null?" ": obtenerDatosADO.get(0).getSegundoNombre();

			String ApellidoADO = null;
			ApellidoADO = obtenerDatosADO.get(0).getPrimerApellido();
			String SegundoApellidoADO = null;
			SegundoApellidoADO = obtenerDatosADO.get(0).getSegundoApellido()==null?" ": obtenerDatosADO.get(0).getSegundoApellido();

			
			ActaPrimigenia datosActa = this.consultarDatosActa(modelo);

	       
		
		
		HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
		
		String fechaResolucion = formatDate.convertirDateAString(dataServicio.getFechaResolucion());
        String fechaGaceta = formatDate.convertirDateAString(dataServicio.getFechaGaceta());
		
	    // Adopcion dataConsulta =this.consultarDatos(modelo); 
		// consulta para los datos.
        datosAPintar.put("rutaImg",RutaIMAGE);
		datosAPintar.put("nombreRegistrador", StringUtils.capitalize(obtenerDatosFuncionario.getPrimerNombre().toLowerCase())+' '+StringUtils.capitalize(obtenerDatosFuncionario.getPrimerApellido().toLowerCase()));
		datosAPintar.put("nombreOficinaRegistro", (datosOficina.getNombre().toUpperCase()));
		datosAPintar.put("parroquia", StringUtils.capitalize(datosOficina.getDireccion().getParroquia().getNombre().toLowerCase()));
		
		datosAPintar.put("municipio", StringUtils.capitalize(datosOficina.getDireccion().getMunicipio().getNombre().toLowerCase()));
	  
		datosAPintar.put("estado", StringUtils.capitalize(datosOficina.getDireccion().getEstado().getNombre().toLowerCase()));
		
		datosAPintar.put("oficinaNacimiento", datosActa.getNombreOficina());
		
		
		datosAPintar.put("nConsecutivo", String.valueOf(datosActa.getNumeroConsecutivo()));
		datosAPintar.put("nResolucion", String.valueOf(dataServicio.getNumeroResolucion())); 
		
		datosAPintar.put("fResolucion",  fechaGaceta); 
		
     	datosAPintar.put("nGaceta",  String.valueOf(dataServicio.getNumeroGaceta())); 
     	
		datosAPintar.put("fGaceta", fechaResolucion); 
		
		datosAPintar.put("numActa", datosActa.getNumeroActa());
		
		datosAPintar.put("fechaActa", datosActa.getFechaIncripcion());
		
		datosAPintar.put("fechaActual", obtenerDatosFuncionario.getOficina().getDireccion().getEstado().getNombre()+", " +GenerarActas.obtenerFechaOrHoraActual("DIA")+" de "+GenerarActas.obtenerFechaOrHoraActual("STRING_MES")+" del "+GenerarActas.obtenerFechaOrHoraActual("ANIO"));
		 
		datosAPintar.put("nombreApellidoAdoptado", PrimerNombreADO+' '+SegundoNombreADO+' '+ApellidoADO+' '+SegundoApellidoADO);
		
		
// Datos decision Judicial...
		datosAPintar.put("numero", String.valueOf(datosActa.getNumeroConsecutivo()));
		
		
		//datosAPintar.put("nombreRegistrador", StringUtils.capitalize(obtenerDatosFuncionario.getPrimerNombre().toLowerCase())+' '+StringUtils.capitalize(obtenerDatosFuncionario.getPrimerApellido().toLowerCase()));
		datosAPintar.put("oficinaRegistro", datosActa.getNombreOficina());
		

        if("PV".equals(estatu) || "PI".equals(estatu)){
        	
            rutaPlantilla = context.getRealPath(RUTA_PLANTILLA + PLANTILLA_NOTIFICACION);
            rutaFin = RUTA_PDF+""+modelo+""+extfile;

        }else if("IDJ".equals(estatu)){
        	
        	ActaServicioCliente servActa = new ActaServicioCliente();
        	DecisionJudicial obtenerDatosDJ = servActa.consultarDecisionJudicial(modelo);
        	
        	datosAPintar.put("nombreCiudadano", obtenerDatosDJ.getPrimerNombreJuez()+" "+obtenerDatosDJ.getSegundoNombreJuez()+" "+obtenerDatosDJ.getPrimerApellidoJuez()+" "+obtenerDatosDJ.getSegundoApellidoJuez());
    		datosAPintar.put("juezTribunal", obtenerDatosDJ.getNombreTribunal());
    		
    		//obtenerDatosDJ.getFechaSentencia().toString();
    		log.info("fechaSentencia---> "+ formatDate.convertirDateAString(obtenerDatosDJ.getFechaSentencia()));
    		datosAPintar.put("fechaSentencia", formatDate.convertirDateAString(obtenerDatosDJ.getFechaSentencia()));
    		datosAPintar.put("fecha", GenerarActas.obtenerFechaOrHoraActual("DIA")+"/"+GenerarActas.obtenerFechaOrHoraActual("NUM_MES")+"/"+GenerarActas.obtenerFechaOrHoraActual("ANIO"));
    		datosAPintar.put("nombreNacimiento", PrimerNombreADO+' '+SegundoNombreADO+' '+ApellidoADO+' '+SegundoApellidoADO);
    		datosAPintar.put("nActa", "----");
    		datosAPintar.put("nFolio", "---");
    		datosAPintar.put("annoLibro", GenerarActas.obtenerFechaOrHoraActual("ANIO"));
    		
        	
         ///log.info("entrooo al elseeee "+RUTA_PLANTILLA+PLANTILLA_DJ);
            rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_DJ);
            rutaFin = RUTA_PDF+""+modelo+""+extfile;
            
        }
        
        JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla); 
        log.info("rutaPlantilla "+rutaPlantilla);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);

		//String template = "<embed width='850' height='550' src='" + rutaReportes + modelo
		//String template="<embed width='850' height='550' src='"+ "/web-generales/tmp/" +"XXXX"+ ".pdf' type='application/pdf'></embed>";
		//+ ".pdf' type='application/pdf'></embed>";
		
		
//		*********************************************************************************************
		String template="<iframe width='800' id='plugin' height='800' src='"+ "/web-nacimiento/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
//		String template="<iframe width='800' id='plugin' height='800' src='"+ "/web-generales/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
			   if ("PI".equals(estatu)) {
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA));
		} else if ("PV".equals(estatu)) {
			vista = Util.leerArchivo(context.getRealPath(RUTA_PV));
		}else if ("IDJ".equals(estatu)){
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA_DJ));
		}else{
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA1));
		}
//			if ("PEA".equals(estatu)){
//			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA1));
//		}else if ("PPI".equals(estatu)){
//			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA_ACTA));
//		}else	
         
		vista = vista.replace("ARCHIVOPDF", template);
		JSONObject formulario = new JSONObject();
		formulario.put("vista", vista);
		return formulario;
	}

	/**
	 * Funcion que genera el acta de adopcion
	 * @author Elly E. Estaba M
	 * @param data:
	 * contiene los datos obtenidos del formulario para generar el
	 * reporte
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws ParseException 
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	@SuppressWarnings("deprecation")
	public JSONObject generarReporteActa(String data, String modelo, String estatu, String token)throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException, ParseException {
	//	public JSONObject generarReporteActa(String modelo, String estatu, String token, JSONObject data)throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException {
		// TODO Auto-generated method stub
		log.info("******modelo reporte*****" + modelo);
		log.info("******statu reporte*****" + estatu);
		log.info("******token reporte*****" + token);
		log.info("******dataaaaaaa*****" + data);
	
//		String vista = null; 
//      String rutaPlantilla = null;
		
		 	String login = seguridadCliente.getUsernameCliente(token);
	        FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
	        Funcionario obtenerDatosFuncionario = servFuncionario.buscarPorLogin(login);

	        String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
	        log.info("codoficina!!!!!!!!!"+obtenerDatosFuncionario.getOficina().getCodigo());
         
	        List<Participante> part = new ArrayList<>();
	        String codTipoFuncionario = "RP";
	        
	        String codEstatusFuncionario = "ACT";
	        Funcionario dataServicio = new Funcionario();
            
	        dataServicio = funcionarioServicioCliente.buscarFuncionarioPorOficina(codOficina, codTipoFuncionario, codEstatusFuncionario);
	        Oficina datosOficina = new Oficina();
	        datosOficina = obtenerDatosFuncionario.getOficina();
	        
	        CatalogoServicioCliente nConsecutivo = new  CatalogoServicioCliente();
	        Long obtenerNconsecutivo = nConsecutivo.proximoNroConsecutivo(codOficina);
	        log.info("--------obtenerNconsecutivo----- " + obtenerNconsecutivo);
			
			Solicitud sol = solicitud.consultarDetalleSolicitud(modelo);
			ParticipanteServicioCliente participante = new ParticipanteServicioCliente();
			/////DATOS ADO
//			obtenerDatosADO = participante.consultarParticPorTipo(modelo, codTipo);
//			String PrimerNombreADO= null;
//			PrimerNombreADO = obtenerDatosADO.get(0).getPrimerNombre();
//			String SegundoNombreADO = null;
//			SegundoNombreADO = obtenerDatosADO.get(0).getSegundoNombre()==null?" ": obtenerDatosADO.get(0).getSegundoNombre();
//			String ApellidoADO = null;
//			ApellidoADO = obtenerDatosADO.get(0).getPrimerApellido();
//			String SegundoApellidoADO = null;
//			SegundoApellidoADO = obtenerDatosADO.get(0).getSegundoApellido()==null?" ": obtenerDatosADO.get(0).getSegundoApellido();
			
	
		//HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
		
		String fechaResolucion = formatDate.convertirDateAString(dataServicio.getFechaResolucion());
        String fechaGaceta = formatDate.convertirDateAString(dataServicio.getFechaGaceta());
		
	    // Adopcion dataConsulta =this.consultarDatos(modelo); 
		// Datos del Acta.
			
		
		HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
		String rutaPlantilla = context.getRealPath(RUTA_PLANTILLA + PLANTILLA_ACTA);
        String rutaFin = RUTA_PDF+""+modelo+""+extfile;
        String vista = null; 
        String RutaIMAGE=context.getRealPath(rutaLogo);
        
        datosAPintar.put("rutaImg",RutaIMAGE);
    	datosAPintar.put("estadoDocumento" ,  StringUtils.capitalize(datosOficina.getDireccion().getEstado().getNombre().toLowerCase()));
		datosAPintar.put("municipioDocumento" ,  StringUtils.capitalize(datosOficina.getDireccion().getMunicipio().getNombre().toLowerCase()));
		datosAPintar.put("parroquiaDocumento" ,  StringUtils.capitalize(datosOficina.getDireccion().getParroquia().getNombre().toLowerCase()));
		datosAPintar.put("actaDocumento" , "------"); 
		datosAPintar.put("diaDocumento" , GenerarActas.obtenerFechaOrHoraActual("DIA"));
		datosAPintar.put("mesDocumento" , GenerarActas.obtenerFechaOrHoraActual("STRING_MES"));
		datosAPintar.put("annoDocumento" , GenerarActas.obtenerFechaOrHoraActual("ANIO"));
		datosAPintar.put("tipoRegistro" , "Registro de adopcion");
		datosAPintar.put("primerNombreRegistrador" , StringUtils.capitalize(dataServicio.getPrimerNombre().toLowerCase())+' '); 
		datosAPintar.put("segundoNombreRegistrador" , StringUtils.capitalize(dataServicio.getSegundoNombre().toLowerCase()));
		datosAPintar.put("primerApellidoRegistrador" , StringUtils.capitalize(dataServicio.getPrimerApellido().toLowerCase()));
		datosAPintar.put("segundoApellidoRegistrador" , StringUtils.capitalize(dataServicio.getSegundoApellido().toLowerCase()));
		datosAPintar.put("documentoIdentidadRegistrador" , " ");
//	   log.info("......cedulaFUN......"+dataServicio.getDocumentoIdentidad().get(0).getNumero());
		datosAPintar.put("oficinaRegistroCivil" , (datosOficina.getNombre().toUpperCase())); 
		datosAPintar.put("numeroResolucion" , String.valueOf(dataServicio.getNumeroResolucion()));
		datosAPintar.put("fechaResolucion" , fechaResolucion);
		datosAPintar.put("numeroGaceta" , String.valueOf(dataServicio.getNumeroGaceta()));
		datosAPintar.put("tipoRegistrador" , true);
		////////////////////////////////
		datosAPintar.put("primerNombreOtro" , " N/A ");
		datosAPintar.put("segundoNombreOtro" , " ");
		datosAPintar.put("primerApellidoOtro" , " "); 
		datosAPintar.put("segundoApellidoOtro" , " ");
		datosAPintar.put("identidadOtro" , " N/A ");
		datosAPintar.put("tipoDocumentoOtro" , " N/A "); 
		datosAPintar.put("edadOtro" , " N/A "); 
		datosAPintar.put("nacionalidadOtro" , " N/A "); 
		datosAPintar.put("profesionOtro" , " N/A "); 
		datosAPintar.put("indigenaOtro" , " N/A "); 
		datosAPintar.put("residenciaOtro" , " N/A "); 
		datosAPintar.put("declaranteOtro" , " N/A "); 
		datosAPintar.put("caracterActua" , " N/A "); 
		////////////////////////////////////
		datosAPintar.put("insercionActa" , "N/A "); 
		datosAPintar.put("diaInsercion" , "N/A "); 
		datosAPintar.put("insercionAutoridad" , "N/A "); 
		datosAPintar.put("consejoProteccion" , " N/A "); 
		datosAPintar.put("medidaN" , " N/A "); 
		datosAPintar.put("diaProteccion" , "N/A "); 
		datosAPintar.put("mesProteccion" , "N/A "); 
		datosAPintar.put("annoProteccion" , "N/A "); 
		datosAPintar.put("nombreConsejero" , "N/A "); 
		///////////////////////////////////////////////
		datosAPintar.put("datosInforme" , "N/A "); 
		datosAPintar.put("autoridadExpide" , "N/A "); 
		datosAPintar.put("diaExtemporanea" , "N/A "); 
		datosAPintar.put("mesExtemporanea" , "N/A "); 
		datosAPintar.put("annoExtemporanea" , "N/A ");
		
		
        if("PEA".equals(estatu)){
	        	
	          rutaPlantilla = context.getRealPath(RUTA_PLANTILLA + PLANTILLA_ACTA);
	          rutaFin = RUTA_PDF+""+modelo+""+extfile;
	    
	  
		JSONObject datos = new JSONObject(data);
		////Datos ADO...
				String ado = new String(datos.getString("ADO")) ;
				JSONObject ADO =  new JSONObject(ado);	
				String PrimerNombreADO= null;
				PrimerNombreADO = String.valueOf(ADO.get("primerNombre"));
				String SegundoNombreADO = null;
				SegundoNombreADO = String.valueOf(ADO.get("segundoNombre"))==null?"  " : String.valueOf(ADO.get("segundoNombre"));
				String ApellidoADO = null;
				ApellidoADO = String.valueOf(ADO.get("primerApellido"));
				String SegundoApellidoADO = null;
				SegundoApellidoADO = String.valueOf(ADO.get("segundoApellido"))==null?"  " : String.valueOf(ADO.get("segundoApellido"));
				String sexoADO = null;
				sexoADO = String.valueOf(ADO.get("sexo"));
				String DocIdentidadADO = null;
				String tipoDocADO = null;
				try
		        {
					try{
						
					DocIdentidadADO = String.valueOf(ADO.getJSONArray("documentoIdentidad").getJSONObject(0).get("numero"));
		            tipoDocADO = String.valueOf(ADO.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").get("nombre"));   
					}catch (Exception e){
		            DocIdentidadADO = String.valueOf(ADO.getJSONObject("documentoIdentidad").getJSONObject("0").get("numero"));
		            tipoDocADO = String.valueOf(ADO.getJSONObject("documentoIdentidad").getJSONObject("0").getJSONObject("tipoDocumento").get("nombre"));          
		        } 
		        }
		        catch(Exception e){
		        	DocIdentidadADO= "  ";
		        	tipoDocADO= "  ";
		        }
				///String fecha 
				 Date fecha = new Date();
				 DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				 try{
	             DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	            fecha = inputFormat.parse( ADO.getString("fechaNacimiento"));
				 }
				 catch(Exception e) {
				  long val = Long.parseLong(ADO.getString("fechaNacimiento"));
			      Date fecha1 = new  Date(ADO.getLong("fechaNacimiento")*1000);		
//		        DateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
//			    fecha = inputFormat.parse(fecha1);
//			    fecha = fecha1;
	               log.info("/8//////////////"+fecha);
		          }
				 String outputText = outputFormat.format(fecha);
		             String[] arrayFecha = outputText.split("/");
					 String[] anno = arrayFecha[2].split(" ");
		         String diaADO = null;
		         diaADO = arrayFecha[0];
		         String mesADO = null;
		         mesADO = arrayFecha[1];
		         String annoADO = null;
		         annoADO = anno[0];
		         String horaADO = null;
				 horaADO = String.valueOf(ADO.getJSONObject("horaNacimiento").get("hora"))+":"+String.valueOf(ADO.getJSONObject("horaNacimiento").get("minuto"));     
				
				String direccionADO = null;
				direccionADO = String.valueOf(ADO.getJSONArray("direccion").getJSONObject(0).getString("ubicacion"));
				String paisADO = null;
			//	paisADO = String.valueOf(ADO.getJSONArray("direccion").getJSONObject(0).getJSONObject("pais").getString("nombre"));		
				paisADO = String.valueOf(ADO.getJSONArray("direccion").getJSONObject(0).getString("pais"));		
				log.info("-------------------->PAIS PAIS PAIS "+paisADO);
			    String edoADO = null;	
				try{
					edoADO=String.valueOf(ADO.getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").getString("nombre"));
					}
					catch (Exception e)
					{
						try {
							if (!ADO.getJSONArray("direccion").getJSONObject(0).getString("estado").equalsIgnoreCase(null)){
								edoADO = ADO.getJSONArray("direccion").getJSONObject(0).getString("estado");
							}
							
						}
						catch (Exception ex) {
							edoADO = "N/A";
						}
						
						
						
//						if (!ADO.getJSONArray("direccion").getJSONObject(0).getString("estado").equalsIgnoreCase(null)){
//							log.info("A VER SI ENTRO EN EL IF DE PONERLE EL STRING");
//							edoADO = ADO.getJSONArray("direccion").getJSONObject(0).getString("estado");
//						} else {
//							edoADO = "N/A";
//						}
//						ADO.getJSONArray("direccion").getJSONObject(0).getString("estado");
					
					}
				String muniADO = null;
				try{
					muniADO=String.valueOf(ADO.getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").getString("nombre"));
					}
					catch (Exception e)
					{
						if (!ADO.getJSONArray("direccion").getJSONObject(0).getString("municipio").equalsIgnoreCase(null)){
							
							muniADO = ADO.getJSONArray("direccion").getJSONObject(0).getString("municipio");
						}else{
							muniADO = "N/A";
						}
//						ADO.getJSONArray("direccion").getJSONObject(0).getString("municipio");
//					muniADO = "N/A";
					}
				String parroADO = null;
				try{
					parroADO=String.valueOf(ADO.getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").getString("nombre"));
					}
					catch (Exception e)
					{
						if (!ADO.getJSONArray("direccion").getJSONObject(0).getString("parroquia").equalsIgnoreCase(null)){
							
							parroADO = ADO.getJSONArray("direccion").getJSONObject(0).getString("parroquia");
						}else{
							parroADO = "N/A";
						}
						
//						ADO.getJSONArray("direccion").getJSONObject(0).getString("parroquia");
//					parroADO = "N/A";
					}
	boolean mama = true;
	String PrimerNombreMAM= null;
	String SegundoNombreMAM = null;
	String ApellidoMAM = null;
	String SegundoApellidoMAM = null;
	String DocIdentidadMAM = null;
	String tipoDocMAM = null;
	String edadMAM = null;
	String profesionMAM = null;
	String NacMAM = null;
	String direccionMAM = null;
	String ComIndMAM = null;
	try{
		///Datos Madre
		String mam = new String(datos.getString("MAM")) ;
		JSONObject MAM =  new JSONObject(mam);	

		PrimerNombreMAM = String.valueOf(MAM.get("primerNombre"));

		SegundoNombreMAM = String.valueOf(MAM.get("segundoNombre"))==null?"  ":String.valueOf(MAM.get("segundoNombre"));

		ApellidoMAM = String.valueOf(MAM.get("primerApellido"));

		SegundoApellidoMAM = String.valueOf(MAM.get("segundoApellido"))==null?"  ":String.valueOf(MAM.get("segundoApellido"));
		
		try
        {
			DocIdentidadMAM = String.valueOf(MAM.getJSONArray("documentoIdentidad").getJSONObject(0).get("numero"));
            tipoDocMAM = String.valueOf(MAM.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").get("nombre"));       
        }
        catch(Exception e)
        {
            DocIdentidadMAM = String.valueOf(MAM.getJSONObject("documentoIdentidad").getJSONObject("0").get("numero"));
            tipoDocMAM = String.valueOf(MAM.getJSONObject("documentoIdentidad").getJSONObject("0").getJSONObject("tipoDocumento").get("nombre"));          
        }

		edadMAM = String.valueOf(datos.getString("MAMEdad"));

		try{
			profesionMAM = String.valueOf(MAM.getJSONObject("ocupacion").getString("nombre"));
			}
			catch (Exception e)
			{
				profesionMAM = MAM.getString("ocupacion");	 
			}
	    String tipD = MAM.getString("tipoDocumento");
		if ( tipD == "V"){
			NacMAM = "VENEZOLANO";
			log.info("entro al if");
		}else{
			log.info("entro al else");
			NacMAM = "EXTRANJERO";
		}
		direccionMAM = String.valueOf(MAM.getJSONArray("direccion").getJSONObject(1).getString("ubicacion"));
		try{
			ComIndMAM=String.valueOf(MAM.getJSONObject("comunidadIndigena").getString("nombre"));
			}
			catch (Exception e)
			{
			MAM.getString("comunidadIndigena");
			ComIndMAM = "N/A";
			}
	mama=true;
	}
	catch(Exception e)
	{
     mama = false;
	}if (mama==false){
		PrimerNombreMAM = "N/A";
		SegundoNombreMAM = "  ";
		ApellidoMAM = "N/A";
		SegundoApellidoMAM = "N/A";
		DocIdentidadMAM = "N/A";
        tipoDocMAM = "N/A";     
		edadMAM = "N/A";
		profesionMAM = "N/A";
		NacMAM = "N/A";
		direccionMAM = "N/A";
		ComIndMAM = "N/A";
	}
	
		///Datos Padre
       boolean papa=true;	
       String PrimerNombrePAP= null;
       String SegundoNombrePAP = null;
       String ApellidoPAP = null;
       String SegundoApellidoPAP = null;
       String DocIdentidadPAP = null;
       String tipoDocPAP = null;
       String edadPAP = null;
       String direccionPAP = null;		
       String profesionPAP = null; 
       String ComIndPAP = null; 
       String NacPAP = null;
       
       try{
		String pap = new String(datos.getString("PAP")) ;
		JSONObject PAP =  new JSONObject(pap);
		
		PrimerNombrePAP = String.valueOf(PAP.get("primerNombre"));
		
		SegundoNombrePAP = String.valueOf(PAP.get("segundoNombre"))==null?"  ":String.valueOf(PAP.get("segundoNombre"));
		
		ApellidoPAP = String.valueOf(PAP.get("primerApellido"));
	
		SegundoApellidoPAP = String.valueOf(PAP.get("segundoApellido"))==null?"  ":String.valueOf(PAP.get("segundoApellido"));
		
		try
        {
			DocIdentidadPAP = String.valueOf(PAP.getJSONArray("documentoIdentidad").getJSONObject(0).get("numero"));
            tipoDocPAP = String.valueOf(PAP.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").get("nombre"));       
        }
        catch(Exception e)
        {
            DocIdentidadPAP = String.valueOf(PAP.getJSONObject("documentoIdentidad").getJSONObject("0").get("numero"));
            tipoDocPAP = String.valueOf(PAP.getJSONObject("documentoIdentidad").getJSONObject("0").getJSONObject("tipoDocumento").get("nombre"));          
        }
		
		edadPAP = String.valueOf(datos.getString("PAPEdad"));
		
		try{
			profesionPAP = String.valueOf(PAP.getJSONObject("ocupacion").getString("nombre"));
			}
			catch (Exception e)
			{
			profesionPAP = PAP.getString("ocupacion");	 
			}
		//profesionPAP = String.valueOf(PAP.getJSONObject("ocupacion").getString("nombre"));
		
		if (PAP.get("tipoDocumento")=="V"){
			NacPAP = "VENEZOLANO";
		}else{
			NacPAP = "EXTRANJERO";
		}
		
		direccionPAP = String.valueOf(PAP.getJSONArray("direccion").getJSONObject(1).getString("ubicacion"));
		
		try{
		ComIndPAP=String.valueOf(PAP.getJSONObject("comunidadIndigena").getString("nombre"));
		}
		catch (Exception e)
		{
		PAP.getString("comunidadIndigena");	 
		ComIndPAP = "N/A";
		}
		log.info(ComIndPAP);
	
		papa=true;
      }
	catch(Exception e)
	{
     papa = false;
	}if (papa==false){
		
		 PrimerNombrePAP= "N/A";
	     SegundoNombrePAP = "  ";
	     ApellidoPAP = "N/A";
	     SegundoApellidoPAP = "N/A";
	     DocIdentidadPAP = "N/A";
	     tipoDocPAP = "N/A";
	     edadPAP = "N/A";
	     direccionPAP = "N/A";		
	     profesionPAP = "N/A";
	     ComIndPAP = "N/A";
	     NacPAP = "N/A";
	}
		
		///Datos Testigo1
		String ta1 = new String(datos.getString("TA1")) ;
		JSONObject TA1 =  new JSONObject(ta1);
		String PrimerNombreTA1= null;
		PrimerNombreTA1 = String.valueOf(TA1.get("primerNombre"));
		String SegundoNombreTA1 = null;
		SegundoNombreTA1 = String.valueOf(TA1.get("segundoNombre"))==null?"  ":String.valueOf(TA1.get("segundoNombre"));
		String ApellidoTA1 = null;
		ApellidoTA1 = String.valueOf(TA1.get("primerApellido"));
		String SegundoApellidoTA1 = null;
		SegundoApellidoTA1 = String.valueOf(TA1.get("segundoApellido"))==null?"  ":String.valueOf(TA1.get("segundoApellido"));
		String DocIdentidadTA1 = null;
		DocIdentidadTA1 = String.valueOf(TA1.getJSONArray("documentoIdentidad").getJSONObject(0).get("numero"));
		String NacTA1 = null;
		if (TA1.get("tipoDocumento")!="V"){
			NacTA1 = "EXTRANJERO";
		}else{
			NacTA1 = "VENEZOLANO";
		}
//		String tipoDocTA1 = null;
//		tipoDocTA1 = String.valueOf(TA1.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").get("nombre"));       
		String edadTA1 = null;
		edadTA1 = String.valueOf(datos.getString("TA1Edad"));
		String profesionTA1 = null; 
		try{
			profesionTA1 = String.valueOf(TA1.getJSONObject("ocupacion").getString("nombre"));
			}
			catch (Exception e)
			{
			profesionTA1 = TA1.getString("ocupacion");	 
			}
//		profesionTA1 = String.valueOf(TA1.getJSONObject("ocupacion").getString("nombre"));
		String direccionTA1 = null;
		direccionTA1 = String.valueOf(TA1.getJSONArray("direccion").getJSONObject(1).getString("ubicacion"));
		String ComIndTA1 = null;
		//ComIndTA1 = String.valueOf(TA1.getJSONObject("comunidadIndigena").getString("nombre"))==null?"  " : String.valueOf(TA1.getJSONObject("comunidadIndigena").getString("nombre"));
		try{
			ComIndTA1=String.valueOf(TA1.getJSONObject("comunidadIndigena").getString("nombre"));
			}
			catch (Exception e)
			{
				TA1.getString("comunidadIndigena");
			ComIndTA1 = "N/A";
			}
		
		///Datos Testigo2
		String ta2 = new String(datos.getString("TA2")) ;
		JSONObject TA2 =  new JSONObject(ta2);
		String PrimerNombreTA2= null;
		PrimerNombreTA2 = String.valueOf(TA2.get("primerNombre"));
		String SegundoNombreTA2 = null;
		SegundoNombreTA2 = String.valueOf(TA2.get("segundoNombre"))==null?"  ":String.valueOf(TA2.get("segundoNombre"));
		String ApellidoTA2 = null;
		ApellidoTA2 = String.valueOf(TA2.get("primerApellido"));
		String SegundoApellidoTA2 = null;
		SegundoApellidoTA2 = String.valueOf(TA2.get("segundoApellido"))==null?"  ":String.valueOf(TA2.get("segundoApellido"));
		String DocIdentidadTA2 = null;
		DocIdentidadTA2 = String.valueOf(TA2.getJSONArray("documentoIdentidad").getJSONObject(0).get("numero"));
//		String tipoDocTA2 = null;
//		tipoDocTA2 = String.valueOf(TA2.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").get("nombre"));       
		String edadTA2 = null;
		edadTA2 = String.valueOf(datos.getString("TA2Edad"));
		String profesionTA2 = null; 
		try{
			profesionTA2 = String.valueOf(TA2.getJSONObject("ocupacion").getString("nombre"));
			}
			catch (Exception e)
			{
			profesionTA2 = TA2.getString("ocupacion");	 
			}
//		profesionTA2 = String.valueOf(TA2.getJSONObject("ocupacion").getString("nombre"));
		String NacTA2 = null;
		if (TA2.get("tipoDocumento")!="V"){
			NacTA2 = "EXTRANJERO";
		}else{
			NacTA2 = "VENEZOLANO";
		}
		String direccionTA2 = null;
		direccionTA2 = String.valueOf(TA2.getJSONArray("direccion").getJSONObject(1).getString("ubicacion"));
		String ComIndTA2 = null;
		try{
			ComIndTA2=String.valueOf(TA2.getJSONObject("comunidadIndigena").getString("nombre"));
			}
			catch (Exception e)
			{
				TA2.getString("comunidadIndigena");
			ComIndTA2 = "N/A";
			}
		
		///datos CM
		String cm = new String(datos.getString("CM")) ;
		JSONObject CM =  new JSONObject(cm);
		String PrimerNombreCM= null;
		PrimerNombreCM = String.valueOf(CM.get("primerNombre"));
		String SegundoNombreCM = null;
		String mm = String.valueOf(CM.get("segundoNombre"));
		try{
		 if(mm != "null"){
			 SegundoNombreCM = String.valueOf(CM.get("segundoNombre")); 
			    log.info(String.valueOf(CM.get("segundoNombre")));
			  }
			else
			{
				SegundoNombreCM = " ";
			}
			 }catch(Exception e){
			   
			}
		String ApellidoCM = null;
		ApellidoCM = String.valueOf(CM.get("primerApellido"));
		String SegundoApellidoCM = null;
		String nn = String.valueOf(CM.get("segundoApellido"));
		try{
		 if(nn != "null"){  
			 SegundoApellidoCM = String.valueOf(CM.get("segundoApellido")); 
			    log.info(String.valueOf(CM.get("segundoApellido")));
			  }
			else
			{
				SegundoApellidoCM = " ";
			}
			 }catch(Exception e){
			   
			}
		String NumDCM = null;
		NumDCM = String.valueOf(CM.get("numCert"));
		String NumMPPS = null;
		NumMPPS = String.valueOf(CM.get("numMPPS"));
		String centroCM= null;
		centroCM = String.valueOf(CM.get("centroSalud"));
		String DocIdentidadCM = null;
		DocIdentidadCM = String.valueOf(CM.getJSONObject("documentoIdentidad").getJSONObject("0").get("numero"));
		String DoctipoCM = null;
		DoctipoCM = String.valueOf(CM.getJSONObject("documentoIdentidad").getJSONObject("0").getJSONObject("tipoDocumento").get("nombre"));          
		String NacCM = null;
		try{
		if (CM.get("tipoDocumento")!="V"){
			NacCM = "EXTRANJERO";
		}else{
			NacCM = "VENEZOLANO";
		} }catch(Exception c){
			
		}
		
	
		
		 Date fecha0 = new Date();
         DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
         
         try{
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            fecha0 = inputFormat.parse(CM.getString("fechaCert"));
         }catch(Exception e) {
            long val = Long.parseLong(CM.getString("fechaCert"));
             fecha0 = new Date(val);
         }
         log.info("fechaaa11111-----------> "+fecha0);
//         JSONObject horaMin = ado.getJSONObject("horaNacimiento");
//         String hora = horaMin.getString("hora");
//         String minuto = horaMin.getString("minuto");
//         
//         log.info("horaMin-----------> "+horaMin);
//         String outputText = outFormat.format(fecha1);
         String outText = outFormat.format(fecha0);
         log.info("outputText-----------> "+outText);
         String FechaCM[] = outText.split("/");
         String FechaCM1[] = FechaCM[2].split(" ");

		String diaCM = null;
		diaCM = FechaCM[0];   // String.valueOf(CM.getJSONObject("fechaCert").get("dia2"));
		String mesCM = null;
		mesCM = FechaCM[1];//String.valueOf(CM.getJSONObject("fechaCert").getJSONObject("mes2").get("nombre"));
		String annoCM = null;
		annoCM = FechaCM1[0]; ///String.valueOf(CM.getJSONObject("fechaCert").get("ano2"));
		
		///Datos IDJ
		String idj = new String(datos.getString("IDJ")) ;
		JSONObject IDJ =  new JSONObject(idj);
		String PrimerNombreIDJ= null;
		PrimerNombreIDJ = String.valueOf(IDJ.get("primerNombre"));
		String SegundoNombreIDJ = null;
		String kk = String.valueOf(IDJ.get("segundoNombre"));
		try{
		 if(kk != "null"){    ////|| IDJ.get("segundoNombre") != null
			  SegundoNombreIDJ = String.valueOf(IDJ.get("segundoNombre")); 
			    log.info(String.valueOf(IDJ.get("segundoNombre")));
			  }
			else
			{
				SegundoNombreIDJ = " ";
			}
			 }catch(Exception e){
			   
			}
		String ApellidoIDJ = null;
		ApellidoIDJ = String.valueOf(IDJ.get("primerApellido"));
		String SegundoApellidoIDJ = null;
		String pp = String.valueOf(IDJ.get("segundoApellido"));
		try{
		  if(pp != "null"){ 
			 SegundoApellidoIDJ = String.valueOf(IDJ.get("segundoApellido")); 
			    log.info(String.valueOf(IDJ.get("segundoApellido")));
			  }
			else
			{
				SegundoApellidoIDJ = " ";
			}
			 }catch(Exception e){
			   
			}
		String Sentencia = null;
		Sentencia = String.valueOf(IDJ.get("sentencia"));
		String tribunal = null;
		tribunal = String.valueOf(IDJ.get("tribunal"));
		String diaIDJ = null;
		diaIDJ = String.valueOf(IDJ.getJSONObject("fecha").get("dia2"));
		String mesIDJ = null;
		mesIDJ = String.valueOf(IDJ.getJSONObject("fecha").getJSONObject("mes2").get("nombre"));
		String annoIDJ = null;
		annoIDJ = String.valueOf(IDJ.getJSONObject("fecha").get("ano2"));
		
		
/////////////////////////////////////////////////////////////////////
        

		datosAPintar.put("primerNombrePresentado" ,  PrimerNombreADO+' ');
		datosAPintar.put("segundoNombrePresentado" , SegundoNombreADO+' ');
		datosAPintar.put("primerApellidoPresentado" , ApellidoADO+' ');
		datosAPintar.put("segundoApellidoPresentado" , SegundoApellidoADO);
		datosAPintar.put("diaNacimientoPresentado" , diaADO);
		datosAPintar.put("mesNacimientoPresentado" , mesADO);
		datosAPintar.put("annoNacimientoPresentado" , annoADO);
		datosAPintar.put("sexoPresentado" , sexoADO);
		datosAPintar.put("horaPresentado" , horaADO);
		datosAPintar.put("am_pm" , true);
		datosAPintar.put("paisPresentado" , paisADO);
		datosAPintar.put("estadoPresentado" , edoADO);
		datosAPintar.put("municipioPresentado" , muniADO);
		datosAPintar.put("parroquiaPresentado" , parroADO);
		datosAPintar.put("direccionPresentado" , direccionADO); 
		datosAPintar.put("diaCertificado" , diaCM);
		datosAPintar.put("mesCertificado" , mesCM);
		datosAPintar.put("annoCertificado" , annoCM);
		datosAPintar.put("nCertificado" , NumDCM);
		datosAPintar.put("Autoridad" , PrimerNombreCM+' '+SegundoNombreCM+' '+ApellidoCM+' '+SegundoApellidoCM);
		datosAPintar.put("primerNombreMadre" , PrimerNombreMAM+' '); 
		datosAPintar.put("segundoNombreMadre" , SegundoNombreMAM+' ');
		datosAPintar.put("primerApellidoMadre" , ApellidoMAM+' ');
		datosAPintar.put("segundoApellidoMadre" , SegundoApellidoMAM);
		datosAPintar.put("nacionalidadMadre" , NacMAM);
		datosAPintar.put("edadMadre" ,  edadMAM);
		datosAPintar.put("identidadMadre" , DocIdentidadMAM);
		datosAPintar.put("tipoDocumentoMadre" , tipoDocMAM); 
		datosAPintar.put("profesionMadre" , profesionMAM); 
		datosAPintar.put("indigenaMadre" , ComIndMAM); 
		datosAPintar.put("residenciaMadre" ,  direccionMAM);
		datosAPintar.put("declaranteMadre" , true);
		datosAPintar.put("documentoMadre" , " ");
		datosAPintar.put("primerNombrePadre" , PrimerNombrePAP+' ');
		datosAPintar.put("segundoNombrePadre" , SegundoNombrePAP+' '); 
		datosAPintar.put("primerApellidoPadre" , ApellidoPAP+' ');
		datosAPintar.put("segundoApellidoPadre" , SegundoApellidoPAP);
		datosAPintar.put("nacionalidadPadre" , NacPAP); 
		datosAPintar.put("edadPadre" ,  edadPAP);
		datosAPintar.put("identidadPadre" , DocIdentidadPAP);
		datosAPintar.put("tipoDocumentoPadre" , tipoDocPAP);
		datosAPintar.put("profesionPadre" , profesionPAP);
		datosAPintar.put("indigenaPadre" , ComIndPAP); 
		datosAPintar.put("residenciaPadre" ,  direccionPAP); 
		datosAPintar.put("declarantePadre" , true); 
		datosAPintar.put("documentoPadre" , " ");

		datosAPintar.put("primerNombreTestigo1" , PrimerNombreTA1+' '); 
		datosAPintar.put("segundoNombreTestigo1" , SegundoNombreTA1+' '); 
		datosAPintar.put("primerApellidoTestigo1" , ApellidoTA1+' '); 
		datosAPintar.put("segundoApellidoTestigo1" , SegundoApellidoTA1); 
		datosAPintar.put("identidadTestigo1" , DocIdentidadTA1); 
		datosAPintar.put("edadTestigo1" ,  edadTA1);
		datosAPintar.put("profesionTestigo1" , profesionTA1); 
		datosAPintar.put("nacionalidadTestigo1" , NacTA1); 
		datosAPintar.put("indigenaTestigo1" , ComIndTA1); 
		datosAPintar.put("residenciaTestigo1" ,  direccionTA1); 
		datosAPintar.put("primerNombreTestigo2" , PrimerNombreTA2+' '); 
		datosAPintar.put("segundoNombreTestigo2" , SegundoNombreTA2+' '); 
		datosAPintar.put("primerApellidoTestigo2" , ApellidoTA2+' '); 
		datosAPintar.put("segundoApellidoTestigo2" , SegundoApellidoTA2); 
		datosAPintar.put("identidadTestigo2" ,  DocIdentidadTA2);
		datosAPintar.put("edadTestigo2" ,  edadTA2);
		datosAPintar.put("profesionTestigo2" , profesionTA2); 
		datosAPintar.put("nacionalidadTestigo2" , NacTA2); 
		datosAPintar.put("indigenaTestigo2" , ComIndTA2); 
		datosAPintar.put("residenciaTestigo2" ,  direccionTA2); 
		datosAPintar.put("nombreCentroSalud" , centroCM); 
		datosAPintar.put("numeroMPPS" , NumMPPS); 

		datosAPintar.put("tribunalJuzgado" , tribunal); 
		datosAPintar.put("sentencia" , Sentencia); 
		datosAPintar.put("nombreJuez" , PrimerNombreIDJ+' '+SegundoNombreIDJ+' '+ApellidoIDJ+' '+SegundoApellidoIDJ); 
		datosAPintar.put("diaJuzgado" , diaIDJ); 
		datosAPintar.put("mesJuzgado" , mesIDJ); 
		datosAPintar.put("annoJuzgado" , annoIDJ); 

		
		
//		      if("PEA".equals(estatu)){
//        	
//            rutaPlantilla = context.getRealPath(RUTA_PLANTILLA + PLANTILLA_ACTA);
//            rutaFin = RUTA_PDF+""+modelo+""+extfile;

        }else if("PVR".equals(estatu) || "PPI".equals(estatu)){
//        	ParticipanteServicioCliente participante = new ParticipanteServicioCliente();
        	
        	List<Participante> obtenerDatosADO = new ArrayList<Participante>();
        	List<Participante> obtenerDatosMAM = new ArrayList<Participante>();
        	List<Participante> obtenerDatosPAP = new ArrayList<Participante>();
        	List<Participante> obtenerDatosTA1 = new ArrayList<Participante>();
        	List<Participante> obtenerDatosTA2 = new ArrayList<Participante>();
        	
			String[] codTipo  = {"ADO"};
			String[] codTipo1 = {"MAM"};
			String[] codTipo2 = {"PAP"};
			String[] codTipo3 = {"TA1"};
			String[] codTipo4 = {"TA2"};
			
			obtenerDatosADO = participante.consultarParticPorTipo(modelo, codTipo);
			obtenerDatosMAM = participante.consultarParticPorTipo(modelo, codTipo1);
			obtenerDatosPAP = participante.consultarParticPorTipo(modelo, codTipo2);
			obtenerDatosTA1 = participante.consultarParticPorTipo(modelo, codTipo3);
			obtenerDatosTA2 = participante.consultarParticPorTipo(modelo, codTipo4);
			ParticipanteServicioCliente participante1 = new ParticipanteServicioCliente();
			Solicitud sol1 = solicitud.consultarDetalleSolicitud(modelo);
			
			/////DATOS ADO
			String PNombreADO= null;
			PNombreADO = obtenerDatosADO.get(0).getPrimerNombre();
			String SNombreADO = null;
			SNombreADO = obtenerDatosADO.get(0).getSegundoNombre()==null?" ": obtenerDatosADO.get(0).getSegundoNombre();
			String PApellidoADO = null;
			PApellidoADO = obtenerDatosADO.get(0).getPrimerApellido();
			String SApellidoADO = null;
			SApellidoADO = obtenerDatosADO.get(0).getSegundoApellido()==null?" ": obtenerDatosADO.get(0).getSegundoApellido();
			String SExO = null;
			SExO = obtenerDatosADO.get(0).getSexo();
			String DocIdentADO = null;
			String tipDocADO = null;
	
			try
	        {
				DocIdentADO = String.valueOf(obtenerDatosADO.get(0).getDocumentoIdentidad().get(0).getNumero());
				tipDocADO =	String.valueOf(obtenerDatosADO.get(0).getDocumentoIdentidad().get(0).getTipoDocumento().getNombre());	
	        }
	        catch(Exception e){
	        	DocIdentADO = "N/A";
	            tipDocADO = "N/A";   
	        }

			 String dADO = null;
	         String mADO = null;
	         String anADO = null;
	         String hADO = null;
			
			try{
			///String fecha 
			 Date Fecha = new Date();
			 DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            // DateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
             Fecha = obtenerDatosADO.get(0).getFechaNacimiento();

			 String outText = outFormat.format(Fecha);
			 
			 log.info("********************"+obtenerDatosADO.get(0).getFechaNacimiento());
			 log.info("********************///////////"+outText);
	             String[] aFecha = outText.split("/");
				 String[] ano2 = aFecha[2].split(" ");
//	         String dADO = null;
	         dADO = aFecha[0];
//	         String mADO = null;
	         mADO = aFecha[1];
//	         String anADO = null;
	         anADO = ano2[0];
//	         String hADO = null;
			 hADO = ano2[1]; }
			catch(Exception e){
				dADO = " ";
//		         String mADO = null;
		         mADO = " ";
//		         String anADO = null;
		         anADO = " ";
//		         String hADO = null;
				 hADO = " ";
			}
			String direccionADO = null;
			direccionADO = String.valueOf(obtenerDatosADO.get(0).getDireccion().get(0).getUbicacion());
			String paisADO = null;
			try{
			paisADO = String.valueOf(obtenerDatosADO.get(0).getDireccion().get(0).getPais().getNombre());	
			}catch (Exception e)
			{
				paisADO = " ";
			}
			log.info("===========pais============"+String.valueOf(obtenerDatosADO.get(0).getDireccion().get(0).getPais())); 
		    String edoADO = null;	
			try{
				edoADO=String.valueOf(obtenerDatosADO.get(0).getDireccion().get(0).getEstado().getNombre());
				log.info("edoADO"+ String.valueOf(obtenerDatosADO.get(0).getDireccion().get(0).getEstado().getNombre()));
			}
				catch (Exception e)
				{
				//	ObtenerDatosADO.getJSONArray("direccion").getJSONObject(0).getString("estado");
				edoADO = "N/A";
				}
			String muniADO = null;
			try{
				muniADO=String.valueOf(obtenerDatosADO.get(0).getDireccion().get(0).getMunicipio().getNombre());
				}
				catch (Exception e)
				{
					//ADO.getJSONArray("direccion").getJSONObject(0).getString("municipio");
				muniADO = "N/A";
				}
			String parroADO = null;
			try{
				parroADO=String.valueOf(obtenerDatosADO.get(0).getDireccion().get(0).getParroquia().getNombre());
				}
				catch (Exception e)
				{
					//ADO.getJSONArray("direccion").getJSONObject(0).getString("parroquia");
				parroADO = "N/A";
				}
			
		/////DATOS MAM
		    boolean MAM = true;	
			String PNombreMAM= null;
			String SNombreMAM = null;
			String PApellidoMAM = null;
			String SApellidoMAM = null;
			String DocIdentidadMAM = null;
		    String tipoDocMAM = null;
		    String edadMAM = null;
		    String direccionMAM = null;		
		    String profesionMAM = null; 
		    String ComIndMAM = null; 
		    String NacMAM = null;
		    try{
			PNombreMAM = obtenerDatosMAM.get(0).getPrimerNombre();
			SNombreMAM = obtenerDatosMAM.get(0).getSegundoNombre()==null?" ": obtenerDatosMAM.get(0).getSegundoNombre();
			PApellidoMAM = obtenerDatosMAM.get(0).getPrimerApellido();
			SApellidoMAM = obtenerDatosMAM.get(0).getSegundoApellido()==null?" ": obtenerDatosMAM.get(0).getSegundoApellido();	
			DocIdentidadMAM = obtenerDatosMAM.get(0).getDocumentoIdentidad().get(0).getNumero();
		    tipoDocMAM = obtenerDatosMAM.get(0).getDocumentoIdentidad().get(0).getTipoDocumento().getNombre();
		    String fech = null;
		    fech =  formatDate.convertirDateAString(obtenerDatosMAM.get(0).getFechaNacimiento());
		    edadMAM = String.valueOf(this.calcularEdad(fech, "A"));
		    direccionMAM = obtenerDatosMAM.get(0).getDireccion().get(0).getUbicacion();		
		    profesionMAM = obtenerDatosMAM.get(0).getOcupacion()==null?" ": obtenerDatosMAM.get(0).getOcupacion(); 
		    ComIndMAM =  obtenerDatosMAM.get(0).getComunidadIndigena()==null?" ": obtenerDatosMAM.get(0).getComunidadIndigena(); 
		    NacMAM = null;
			if(obtenerDatosMAM.get(0).getTipoDocumento()=="V"){
				NacMAM ="VENEZOLANA";
			}else{
				NacMAM ="EXTRANJERO";
			}
			MAM=true;
		      }
			catch(Exception e)
			{
		     MAM = false;
			}if (MAM==false){
				PNombreMAM= "N/A";
				SNombreMAM = "N/A";
				PApellidoMAM = "N/A";
				SApellidoMAM = "N/A";
				DocIdentidadMAM = "N/A";
			    tipoDocMAM = "N/A";
			    edadMAM = "N/A" ;
			    direccionMAM = "N/A";		
			    profesionMAM = "N/A";
			    ComIndMAM = "N/A";
			    NacMAM = "N/A";
			}
			
			
	    /////DATOS PAP
			 boolean PAP = true;	
			String PNombrePAP= null;
			String SNombrePAP = null;
			String PApellidoPAP = null;
			String SApellidoPAP = null;
			String DocIdentidadPAP = null;
		    String tipoDocPAP = null;
		    String edadPAP = null;
		    String direccionPAP = null;		
		    String profesionPAP = null; 
		    String ComIndPAP = null; 
		    String NacPAP = null;
			try{
			PNombrePAP = obtenerDatosPAP.get(0).getPrimerNombre();
			SNombrePAP = obtenerDatosPAP.get(0).getSegundoNombre()==null?" ": obtenerDatosPAP.get(0).getSegundoNombre();
			PApellidoPAP = obtenerDatosPAP.get(0).getPrimerApellido();
			SApellidoPAP = obtenerDatosPAP.get(0).getSegundoApellido()==null?" ": obtenerDatosPAP.get(0).getSegundoApellido();
			DocIdentidadPAP = obtenerDatosPAP.get(0).getDocumentoIdentidad().get(0).getNumero();
		    tipoDocPAP = obtenerDatosPAP.get(0).getDocumentoIdentidad().get(0).getTipoDocumento().getNombre();
		    String fech = null;
		    fech =  formatDate.convertirDateAString(obtenerDatosPAP.get(0).getFechaNacimiento());
		    edadPAP = String.valueOf(this.calcularEdad(fech, "A"));
		    direccionPAP = obtenerDatosPAP.get(0).getDireccion().get(0).getUbicacion();		
		    profesionPAP = obtenerDatosPAP.get(0).getOcupacion()==null?" ": obtenerDatosPAP.get(0).getOcupacion(); 
		    ComIndPAP =  obtenerDatosPAP.get(0).getComunidadIndigena()==null?" ": obtenerDatosPAP.get(0).getComunidadIndigena(); 
		    NacPAP = null;
			if(obtenerDatosPAP.get(0).getTipoDocumento()=="V"){
				NacPAP ="VENEZOLANO";
			}else{
				NacPAP ="EXTRANJERO";
			}
			PAP = true;	
			}
			catch(Exception e)
			{
		     PAP = false;
			}if (PAP==false){
				PNombrePAP= "N/A";
				SNombrePAP = "N/A";
				PApellidoPAP = "N/A";
				SApellidoPAP = "N/A";
				DocIdentidadPAP = "N/A";
			    tipoDocPAP = "N/A";
			    edadPAP = "N/A" ;
			    direccionPAP = "N/A";		
			    profesionPAP = "N/A";
			    ComIndPAP = "N/A";
			    NacPAP = "N/A";
			}
		 /////DATOS TA1
			String PNombreTA1= null;
			PNombreTA1 = obtenerDatosTA1.get(0).getPrimerNombre();
			String SNombreTA1 = null;
			SNombreTA1 = obtenerDatosTA1.get(0).getSegundoNombre()==null?" ": obtenerDatosTA1.get(0).getSegundoNombre();
			String PApellidoTA1 = null;
			PApellidoTA1 = obtenerDatosTA1.get(0).getPrimerApellido();
			String SApellidoTA1 = null;
			SApellidoTA1 = obtenerDatosTA1.get(0).getSegundoApellido()==null?" ": obtenerDatosTA1.get(0).getSegundoApellido();
			String DocIdentTA1 = null;
			String tipDocTA1 = null;
			DocIdentTA1 = String.valueOf(obtenerDatosTA1.get(0).getDocumentoIdentidad().get(0).getNumero());
			tipDocTA1 =	String.valueOf(obtenerDatosTA1.get(0).getDocumentoIdentidad().get(0).getTipoDocumento().getNombre());					
			String fech = null;
		    fech =  formatDate.convertirDateAString(obtenerDatosTA1.get(0).getFechaNacimiento());
		    String edadTA1 = null;
		    edadTA1 = String.valueOf(this.calcularEdad(fech, "A"));
		    String direccionTA1 = null;
		    direccionTA1 = obtenerDatosTA1.get(0).getDireccion().get(0).getUbicacion();		
		    String profesionTA1 = null;
		    profesionTA1 = obtenerDatosTA1.get(0).getOcupacion()==null?" ": obtenerDatosTA1.get(0).getOcupacion(); 
		    String ComIndTA1 = null;
		    ComIndTA1 = obtenerDatosTA1.get(0).getComunidadIndigena()==null?" ": obtenerDatosTA1.get(0).getComunidadIndigena(); 
		    String NacTA1 = null;
			if(obtenerDatosTA1.get(0).getTipoDocumento()=="V"){
				NacTA1 ="VENEZOLANO";
			}else{
				NacTA1 ="EXTRANJERO";
			}
		/////DATOS TA2
			String PNombreTA2= null;
			PNombreTA2 = obtenerDatosTA2.get(0).getPrimerNombre();
			String SNombreTA2 = null;
			SNombreTA2 = obtenerDatosTA2.get(0).getSegundoNombre()==null?" ": obtenerDatosTA2.get(0).getSegundoNombre();
			String PApellidoTA2 = null;
			PApellidoTA2 = obtenerDatosTA2.get(0).getPrimerApellido();
			String SApellidoTA2 = null;
			SApellidoTA2 = obtenerDatosTA2.get(0).getSegundoApellido()==null?" ": obtenerDatosTA2.get(0).getSegundoApellido();
			String DocIdentTA2 = null;
			String tipDocTA2 = null;
			DocIdentTA2 = String.valueOf(obtenerDatosTA2.get(0).getDocumentoIdentidad().get(0).getNumero());
			tipDocTA2 =	String.valueOf(obtenerDatosTA2.get(0).getDocumentoIdentidad().get(0).getTipoDocumento().getNombre());	
			String fech1 = null;
		    fech1 =  formatDate.convertirDateAString(obtenerDatosTA2.get(0).getFechaNacimiento());
		    String edadTA2 = null;
		    edadTA2 = String.valueOf(this.calcularEdad(fech, "A"));
		    String direccionTA2 = null;
		    direccionTA2 = obtenerDatosTA2.get(0).getDireccion().get(0).getUbicacion();		
		    String profesionTA2 = null;
		    profesionTA2 = obtenerDatosTA2.get(0).getOcupacion()==null?" ": obtenerDatosTA2.get(0).getOcupacion(); 
		    String ComIndTA2 = null;
		    ComIndTA2 = obtenerDatosTA2.get(0).getComunidadIndigena()==null?" ": obtenerDatosTA2.get(0).getComunidadIndigena(); 
		    String NacTA2 = null;
			if(obtenerDatosTA2.get(0).getTipoDocumento()=="V"){
				NacTA2 ="VENEZOLANO";
			}else{
				NacTA2 ="EXTRANJERO";
			}
			
			
        	ActaServicioCliente servActa = new ActaServicioCliente();
        	DecisionJudicial obtenerDatosDJ = servActa.consultarDecisionJudicial(modelo);
        	log.info("\n\n\n\n\n\n\n obtenerDatosDJ------------->"); 	
        	log.info("\n\n\n\n\n\n\n obtenerDatosDJ------------->"+obtenerDatosDJ); 	
        	 
        	
       if(obtenerDatosDJ!=null){
     	
        	String FechaIDJ = formatDate.convertirDateAString(obtenerDatosDJ.getFechaSentencia());
        	String[] arrayFecha = FechaIDJ.split("/"); 
            String diaDJ = arrayFecha[0];
            String mesDJ = arrayFecha[1];
            String annoDJ =arrayFecha[2];
            String SegundoNomIDJ = null;
            String n2 = String.valueOf(obtenerDatosDJ.getSegundoNombreJuez());
    		try{
    				if(n2 != "null"){   
    						SegundoNomIDJ  = String.valueOf(obtenerDatosDJ.getSegundoNombreJuez()); 
    				 }else
    				 	{
    					 	SegundoNomIDJ  = " ";
    				 	}
    		  }catch(Exception e){
    			   
    		}
    		
    		String SegundoApeIDJ = null;
            String ap2 = String.valueOf(obtenerDatosDJ.getSegundoApellidoJuez());
    		try{
    				if(ap2 != "null"){   
    					SegundoApeIDJ  = String.valueOf(obtenerDatosDJ.getSegundoApellidoJuez()); 
    				}
    				else
    				{
    					SegundoApeIDJ  = " ";
    				}
    			 }catch(Exception e){
    			   
    			}
    		
        
    		
       	Nacimiento obtenerDatosCM = servActa.consultarNacimiento(modelo);
        	
        if(obtenerDatosCM!=null){
        	
        	
        	
        	String FechaCM = formatDate.convertirDateAString(obtenerDatosCM.getFechaCertificado());
        	String[] arrayFechaC = FechaCM.split("/"); 
            String diaCM = arrayFechaC[0];
            String mesCM = arrayFechaC[1];
            String annoCM =arrayFechaC[2];
            String PrimerNombreCM = obtenerDatosCM.getPrimerNombreMedico();
            String SegundoNomCM =null;
            String n1 = String.valueOf(obtenerDatosCM.getSegundoNombreMedico());
    		try{
    		 if(n1 != "null"){   
    			 SegundoNomCM = String.valueOf(obtenerDatosCM.getSegundoNombreMedico()); 
    			    log.info(n1);
    			  }
    			else
    			{
    				SegundoNomCM = " ";
    			}
    			 }catch(Exception e){
    			   
    			}
            String ApellidoCM =obtenerDatosCM.getPrimerApellidoMedico();
            String SegundoApeCM = null;
            String ap = String.valueOf(obtenerDatosCM.getSegundoNombreMedico());
    		try{
    		 if(ap != "null"){   
    			 SegundoApeCM  = String.valueOf(obtenerDatosCM.getSegundoApellidoMedico()); 
    			  }
    			else
    			{
    				SegundoApeCM  = " ";
    			}
    			 }catch(Exception e){
    			   
    			}
            
    		datosAPintar.put("primerNombrePresentado" ,  PNombreADO+' ');
    		datosAPintar.put("segundoNombrePresentado" , SNombreADO+' ');
    		datosAPintar.put("primerApellidoPresentado" , PApellidoADO+' ');
    		datosAPintar.put("segundoApellidoPresentado" , SApellidoADO);
    		datosAPintar.put("diaNacimientoPresentado" , dADO);
    		datosAPintar.put("mesNacimientoPresentado" , mADO);
    		datosAPintar.put("annoNacimientoPresentado" , anADO);
    		datosAPintar.put("sexoPresentado" , SExO);
    		datosAPintar.put("horaPresentado" , hADO);
    		datosAPintar.put("am_pm" , true);
    		datosAPintar.put("paisPresentado" , paisADO);
    		datosAPintar.put("estadoPresentado" , edoADO);
    		datosAPintar.put("municipioPresentado" , muniADO);
    		datosAPintar.put("parroquiaPresentado" , parroADO);
    		datosAPintar.put("direccionPresentado" , direccionADO); 
    		datosAPintar.put("diaCertificado" , diaCM);
    		datosAPintar.put("mesCertificado" , mesCM);
    		datosAPintar.put("annoCertificado" , annoCM);
    		datosAPintar.put("nCertificado" ,String.valueOf(obtenerDatosCM.getNumeroCertificado()));
    		datosAPintar.put("Autoridad" , PrimerNombreCM+' '+SegundoNomCM+' '+ApellidoCM+' '+SegundoApeCM);
    		datosAPintar.put("primerNombreMadre" , PNombreMAM+' '); 
    		datosAPintar.put("segundoNombreMadre" , SNombreMAM+' ');
    		datosAPintar.put("primerApellidoMadre" , PApellidoMAM+' ');
    		datosAPintar.put("segundoApellidoMadre" , SApellidoMAM);
    		datosAPintar.put("nacionalidadMadre" , NacMAM);
    		datosAPintar.put("edadMadre" ,  edadMAM);
    		datosAPintar.put("identidadMadre" , DocIdentidadMAM);
    		datosAPintar.put("tipoDocumentoMadre" , tipoDocMAM); 
    		datosAPintar.put("profesionMadre" , profesionMAM); 
    		datosAPintar.put("indigenaMadre" , ComIndMAM); 
    		datosAPintar.put("residenciaMadre" , direccionMAM);
    		datosAPintar.put("declaranteMadre" , true);
    		datosAPintar.put("documentoMadre" , " ");
    		datosAPintar.put("primerNombrePadre" , PNombrePAP+' ');
    		datosAPintar.put("segundoNombrePadre" , SNombrePAP+' '); 
    		datosAPintar.put("primerApellidoPadre" , PApellidoPAP+' ');
    		datosAPintar.put("segundoApellidoPadre" , SApellidoPAP);
    		datosAPintar.put("nacionalidadPadre" , NacPAP); 
    		datosAPintar.put("edadPadre" , edadPAP);
    		datosAPintar.put("identidadPadre" , DocIdentidadPAP);
    		datosAPintar.put("tipoDocumentoPadre" , tipoDocPAP);
    		datosAPintar.put("profesionPadre" , profesionPAP);
    		datosAPintar.put("indigenaPadre" , ComIndPAP); 
    		datosAPintar.put("residenciaPadre" ,  direccionPAP); 
    		datosAPintar.put("declarantePadre" , true); 
    		datosAPintar.put("documentoPadre" , " ");

    		datosAPintar.put("primerNombreTestigo1" , PNombreTA1+' '); 
    		datosAPintar.put("segundoNombreTestigo1" , SNombreTA1+' '); 
    		datosAPintar.put("primerApellidoTestigo1" ,PApellidoTA1+' '); 
    		datosAPintar.put("segundoApellidoTestigo1" , SApellidoTA1); 
    		datosAPintar.put("identidadTestigo1" , DocIdentTA1); 
    		datosAPintar.put("edadTestigo1" ,  edadTA1);
    		datosAPintar.put("profesionTestigo1" , profesionTA1); 
    		datosAPintar.put("nacionalidadTestigo1" , NacTA1); 
    		datosAPintar.put("indigenaTestigo1" , ComIndTA1); 
    		datosAPintar.put("residenciaTestigo1" ,  direccionTA1); 
    		datosAPintar.put("primerNombreTestigo2" , PNombreTA2+' '); 
    		datosAPintar.put("segundoNombreTestigo2" , SNombreTA2+' '); 
    		datosAPintar.put("primerApellidoTestigo2" , PApellidoTA2+' '); 
    		datosAPintar.put("segundoApellidoTestigo2" , SApellidoTA2); 
    		datosAPintar.put("identidadTestigo2" ,  DocIdentTA2);
    		datosAPintar.put("edadTestigo2" ,  edadTA2);
    		datosAPintar.put("profesionTestigo2" , profesionTA2); 
    		datosAPintar.put("nacionalidadTestigo2" , NacTA2); 
    		datosAPintar.put("indigenaTestigo2" , ComIndTA2); 
    		datosAPintar.put("residenciaTestigo2" ,  direccionTA2); 
    		datosAPintar.put("nombreCentroSalud" , obtenerDatosCM.getCentroSalud());///centroCM); 
    		datosAPintar.put("numeroMPPS" , String.valueOf(obtenerDatosCM.getNuMPPS()));/// NumMPPS); 
//    
    		datosAPintar.put("tribunalJuzgado" , obtenerDatosDJ.getNombreTribunal()); 
    		datosAPintar.put("sentencia" , obtenerDatosDJ.getNumeroSentencia()); 
    		datosAPintar.put("nombreJuez" , obtenerDatosDJ.getPrimerNombreJuez()+" "+SegundoNomIDJ+" "+obtenerDatosDJ.getPrimerApellidoJuez()+" "+SegundoApeIDJ); 
    		datosAPintar.put("diaJuzgado" ,diaDJ); 
    		datosAPintar.put("mesJuzgado" , mesDJ); 
    		datosAPintar.put("annoJuzgado" , annoDJ); 
    		
    		
        }
    		
    		
       }	
    		
           
          log.info("\n\n\n\n\n\n\n RUTAAAAAA PLANTILLA------------->"+rutaPlantilla);
       
            rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_ACTA);
            rutaFin = RUTA_PDF+""+modelo+""+extfile;
            
            log.info("\n\n\n\n\n\n\n RUTAAAAAA fin------------->"+rutaFin);
            
        }
		

        
        
        log.info("\n\n\n\n\n\n\n antes del jasper ------------->");
        
        JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla); 
        log.info("rutaPlantilla "+rutaPlantilla);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);
		String template="<iframe width='800' id='plugin' height='800' src='"+ "/web-nacimiento/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
//		String template="<iframe width='800' id='plugin' height='800' src='"+ "/web-generales/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
//			   if ("PI".equals(estatu)) {
//			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA));
//		} else
			  if ("PVR".equals(estatu)) {
			vista = Util.leerArchivo(context.getRealPath(RUTA_PV_ACT));
			
			log.info("\n\n\n\n\n\n\n ESTADO PVR------------->"+vista);
			
			
		}
//			 else if ("IDJ".equals(estatu)){
//			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA_DJ));
//		}
		else if ("PEA".equals(estatu)){
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA1));
		}else if ("PPI".equals(estatu)){
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA_ACTA));
		}else	{
			vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA1));
		}
			
         
		vista = vista.replace("ARCHIVOPDF", template);
		JSONObject formulario = new JSONObject();
		formulario.put("vista", vista);
		log.info("\n\n\n\n\n\n\n saliod el metodo------------->"+formulario);
		
		return formulario;
	}
	
	
	/**
	 * Funcion que devuelve la ruta del war
	 * 
	 * @author Elly Estaba
	 * @param String
	 * @return String
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	public String obtenerEndPointConfig(String endPointClave) {
		String rutaReportes = (System.getProperty("jboss.home.dir") == null ? "C:/jboss-as-7.1.0.Final"
				: System.getProperty("jboss.home.dir")) + "/modules/ve/gob/cne/sarc/main/";
		log.info("EndPoint a reportes: " + rutaReportes);
		File configFile = new File(rutaReportes + "Reporte.properties");
		String host = "";
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			host = props.getProperty(endPointClave);
			reader.close();
		} catch (FileNotFoundException ex) {
			log.info("ERROR Config file No Encontrado");
		} catch (IOException ex) {
			log.info("ERROR I/O accediendo a Archivo Config");
		}
		log.info("direccion del host: " + host);
		return host;
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_presentarSatisfactorio", method = RequestMethod.POST)
	public @ResponseBody String consultarPresentarSatisfactorio(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "satisfactorio.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("mensaje", "Impresi\u00f3n exitosa.");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_cert_med", method = RequestMethod.POST)
	public @ResponseBody String consultarCertMed(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "certificado_medico_nacimiento_adopcion.html"));
		modelo.put("titulo", "Datos del certificado");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		log.info("*****Vista Certficado Medico*Contenido Html************" + vista);
		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_datos_madre", method = RequestMethod.POST)
	public @ResponseBody String consultarDatosMadre(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "datos_madre.html"));
		modelo.put("titulo", "Datos de la madre");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_datos_padre", method = RequestMethod.POST)
	public @ResponseBody String consultarDatosPadre(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "datos_padre.html"));
		modelo.put("titulo", "Datos del padre");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Jean Millan
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NoConformePadres", method = RequestMethod.POST)
	public @ResponseBody String NoConformePadres(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "datos_adoptado.html"));
		modelo.put("titulo", "Datos del(de la) adoptado(a)");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
    
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_datos_testigos", method = RequestMethod.POST)
	public @ResponseBody String consultarDatosTestigos(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "datos_testigos_adopcion.html"));
		modelo.put("titulo", "Datos de los testigos");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_inscrip_judicial", method = RequestMethod.POST)
	public @ResponseBody String consultarInscripJudicial(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "inscripcion_decision_judicial_adopcion.html"));
		modelo.put("titulo", "Decisi\u00f3n judicial");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_especiales", method = RequestMethod.POST)
	public @ResponseBody String consultarEspeciales(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "circunstancias_especiales_acto_adopcion.html"));
		modelo.put("titulo", "Circunstancias especiales");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_documentos", method = RequestMethod.POST)
	public @ResponseBody String consultarDocumentos(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "documentos_adopcion.html"));
		List <Recaudo> Recaudos;
		  List <String> nombreRecaudo;
		  String soli = sol.getNumeroSolicitud();
		  Recaudos = recaudo.consultarRecaudo(soli);

		  JSONObject objeto = new JSONObject();
		  for (Recaudo r : Recaudos){
		   objeto.put(r.getCodigo(),r.getNombre()); 
		  }
		modelo.put("recaudos", objeto);	
		modelo.put("titulo", "Documentos presentados");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/RADOP_vistaPrevia", method = RequestMethod.POST)
	public @ResponseBody String consultarVistaPrevia(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String token=request.getHeader("Authorization");
		JSONObject modeloReporte = generarReporteActa(data,(String) modelo.get("id"), (String) modelo.get("estatu"), token);

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "vista_previa.html"));
		modelo.put("titulo", "Vista previa del acta");
		respuesta.put("vista", modeloReporte.get("vista"));
		///respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve el modelo y la vista a mostrar en el controlador js
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/RADOP_imprimirActa", method = RequestMethod.POST)
	public @ResponseBody String consultarImprimirActa(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException {
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
		////////////acabo de modificar esooo....
		String token=request.getHeader("Authorization");
		
		log.info("------------token------------***** " +token);
		
		//JSONObject modeloReporte = generarReporteActa((String) modelo.get("id"), (String) modelo.get("estatu"), token, modelo.getJSONObject("data"));
		JSONObject modeloReporte = generarReporteActa(data, (String) modelo.get("id"), (String) modelo.get("estatu"), token);
	//	String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "imprimir_acta.html"));
		//log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Imprimir borrador");
		respuesta.put("vista", modeloReporte.get("vista"));
		//respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve el modelo y la vista a mostrar en el controlador js
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	@RequestMapping(value = "/RADOP_vrfPadres", method = RequestMethod.POST)
	public @ResponseBody String consultarVrfPadres(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException {
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
		//JSONObject modeloReporte = generarReporte((String) modelo.get("id"), (String) modelo.get("estatu"));

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "verificacion_padres.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Verificaci\u00f3n de los padres");
		//respuesta.put("vista", modeloReporte.get("vista"));
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	///////-------------------
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_RV_datos_adop", method = RequestMethod.POST)
	public @ResponseBody String consultarRVDatosAdop(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "RV_datos_adoptado.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Acta");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_RV_cert_med", method = RequestMethod.POST)
	public @ResponseBody String consultarRV_CertMed(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "RV_certificado_medico_nacimiento_adopcion.html"));
		modelo.put("titulo", "Datos del certificado");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		log.info("*****Vista Certficado Medico*Contenido Html************" + vista);
		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_RV_datos_madre", method = RequestMethod.POST)
	public @ResponseBody String consultarRV_DatosMadre(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "RV_datos_madre.html"));
		modelo.put("titulo", "Datos de la madre");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_RV_datos_padre", method = RequestMethod.POST)
	public @ResponseBody String consultarRV_DatosPadre(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "RV_datos_padre.html"));
		modelo.put("titulo", "Datos del padre");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
   
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_RV_datos_testigos", method = RequestMethod.POST)
	public @ResponseBody String consultarRV_DatosTestigos(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "RV_datos_testigos_adopcion.html"));
		modelo.put("titulo", "Datos de los testigos");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_RV_inscrip_judicial", method = RequestMethod.POST)
	public @ResponseBody String consultarRV_InscripJudicial(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "RV_inscripcion_decision_judicial_adopcion.html"));
		modelo.put("titulo", "Decisi\u00f3n judicial");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_RV_especiales", method = RequestMethod.POST)
	public @ResponseBody String consultarRV_Especiales(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "RV_circunstancias_especiales_acto_adopcion.html"));
		modelo.put("titulo", "Circunstancias Especiales");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_RV_verificacion", method = RequestMethod.POST)
	public @ResponseBody String consultarRV_verificaciones(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "RV_verificacion_RC.html"));
		modelo.put("titulo", "Verificaci\u00f3n del(de la) R.C.");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve el modelo y la vista a mostrar en el controlador js
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/RADOP_presentarImprimirActa", method = RequestMethod.POST)
	public @ResponseBody String consultarPresentarImprimirActa(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException {
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
		String token=request.getHeader("Authorization");
		/////cambie aqui el reporte
		///JSONObject modeloReporte = generarReporteActa((String) modelo.get("id"), (String) modelo.get("estatu"), token, modelo.getJSONObject("data"));
		JSONObject modeloReporte = generarReporteActa(data, (String) modelo.get("id"), (String) modelo.get("estatu"), token);

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "imprimir_ActaValida.html"));
		log.info("******Contenido Html************" + vista);
		respuesta.put("vista", modeloReporte.get("vista"));
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_presentarSatisfactorioActa", method = RequestMethod.POST)
	public @ResponseBody String consultarPresentarSatisfactorioActa(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "satisfactorioActa.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("mensaje", "El acta fue impresa satisfactoriamente.");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/////-----------------------------Cargar Documento
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_CargarDocSatisf", method = RequestMethod.POST)
	public @ResponseBody String consultarCargarDocSatisf(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "CargarDocSatisf.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("mensaje", "El acta fue impresa satisfactoriamente");
		modelo.put("titulo", "Cargar documento");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_DecisionJudicial", method = RequestMethod.POST)
	public @ResponseBody String consultarDJ(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "decision_judicial.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Oficio al tribunal");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	///-----------------------------------imprimir oficio al tribunal
	/**
	 * Funcion que devuelve el modelo y la vista a mostrar en el controlador js
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	@RequestMapping(value = "/RADOP_presentarImprimirDJ", method = RequestMethod.POST)
	public @ResponseBody String consultarPresentarImprimirDJ(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException {
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
		String token=request.getHeader("Authorization");
		JSONObject modeloReporte = generarReporte ((String) modelo.get("id"), (String) modelo.get("estatu"), token);

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "DJ_imprimir.html"));
		log.info("******Contenido Html************" + vista);
		respuesta.put("vista", modeloReporte.get("vista"));
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_presentarSatisfactorioDJ", method = RequestMethod.POST)
	public @ResponseBody String consultarPresentarSatisfactorioDJ(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "DJ_satisfactorio.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("mensaje", "Impresi\u00f3n exitosa.");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	//////////--------------------Cargar DJ
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_DJ_CargarDocSatisf", method = RequestMethod.POST)
	public @ResponseBody String consultarDJCargarDocSatisf(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "DJ_CargarDocSatisf.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("mensaje", "Carga de documento exitosa");
		modelo.put("titulo", "Cargar documento");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}	
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NT_CargarDocSatisf", method = RequestMethod.POST)
	public @ResponseBody String consultarNTCargarDocSatisf(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NT_CargarDocSatisf.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("mensaje", "Carga de documento exitosa");
		modelo.put("titulo", "Cargar documento");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NC_datos_adop", method = RequestMethod.POST)
	public @ResponseBody String consultarNCDatosAdop(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_datos_adoptado.html"));
		log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Datos del(de la) adoptado(a)");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NC_cert_med", method = RequestMethod.POST)
	public @ResponseBody String consultarNC_CertMed(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_certificado_medico_nacimiento_adopcion.html"));
		modelo.put("titulo", "Datos del certificado");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		log.info("*****Vista Certficado Medico*Contenido Html************" + vista);
		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NC_datos_madre", method = RequestMethod.POST)
	public @ResponseBody String consultarNC_DatosMadre(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_datos_madre.html"));
		modelo.put("titulo", "Datos de la madre");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}

	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NC_datos_padre", method = RequestMethod.POST)
	public @ResponseBody String consultarNC_DatosPadre(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_datos_padre.html"));
		modelo.put("titulo", "Datos del padre");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
   
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NC_datos_testigos", method = RequestMethod.POST)
	public @ResponseBody String consultarNC_DatosTestigos(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_datos_testigos_adopcion.html"));
		modelo.put("titulo", "Datos de los testigos");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NC_inscrip_judicial", method = RequestMethod.POST)
	public @ResponseBody String consultarNC_InscripJudicial(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_inscripcion_decision_judicial_adopcion.html"));
		modelo.put("titulo", "Decisi\u00f3n judicial");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NC_especiales", method = RequestMethod.POST)
	public @ResponseBody String consultarNC_Especiales(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_circunstancias_especiales_acto_adopcion.html"));
		modelo.put("titulo", "Circunstancias Especiales");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
	@RequestMapping(value = "/RADOP_NC_verificacion", method = RequestMethod.POST)
	public @ResponseBody String consultarNC_verificaciones(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_verificacion_RC.html"));
		modelo.put("titulo", "Verificaci\u00f3n del (de la) R.C.");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
	
	
	
	
	// ------------------------Consultas a Servicios---------------------------
	/**
	 * Funcion que devuelve una lista de ocupaciones desde catalogo
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            
	 * @return Lista de objetos tipo Catalogo
	 */
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
	 * 
	 * @author Elly Estaba
	 * @param data:
	 *            
	 * @return Lista de objetos tipo Catalogo
	 */
	@RequestMapping(value = "/consultarOficinasTodos", method = RequestMethod.GET)
	public List<Oficina> consultarOficinasTodos() {
		Catalogo catalogo = new Catalogo();
		return catalogo.consultarOficinasTodos();

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
	
	
///////////////////////////////////////////No conforme AB////////////////////////////////////////////////
	 /**
	   * Funcion que devuelve una vista y modelo a presentar
	   * 
	   * @author Elly Estaba
	   * @param data:
	   *            contiene los datos obtenidos del rootScope
	   * @return JsonObject
	   */
	@RequestMapping(value = "/RADOP_NC_adoptado", method = RequestMethod.POST)
	  public @ResponseBody String consultarNC_Adoptado(@RequestBody String data, HttpSession sesion,
	      HttpServletRequest request)
	      throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
	    // log.info("*************RADOP ADOPTADO************"+sesion.getId());
	    JSONObject modelo = new JSONObject(data);
	    JSONObject respuesta = new JSONObject();
	    // ObjectMapper mapper= new ObjectMapper();
	    // Map<String, String> data =
	    // mapper.readValue(request.getHeader("datos"), Map.class);
	    // String token=request.getHeader("Authorization");
	    // if(token==null){
	    // throw new GeneralException("seguridad_no_token");
	    // }
	    String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_datos_adoptado_solicitud_adopcion.html"));
	    log.info("******Contenido Html************" + vista);
	    modelo.put("titulo", "Datos para la solicitud");
	    respuesta.put("vista", vista);
	    respuesta.put("modelo", modelo);

	    return respuesta.toString();
	  }

	/**
	   * Funcion que devuelve una vista y modelo a presentar 
	   * @author Elly Estaba
	   * @param data:
	   * contiene los datos obtenidos del rootScope
	   * @return JsonObject
	 * @throws ParseException 
	   */
	  @RequestMapping(value = "/RADOP_NC_acta_prim", method = RequestMethod.POST)
	  public @ResponseBody String consultar_NcActaPrim(@RequestBody String data, HttpSession sesion,
	      HttpServletRequest request)
	      throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, ParseException {
	    // log.info("*************RADOP Acta
	    // PRIMIGENIA************"+sesion.getId());
	    JSONObject modelo = new JSONObject(data);
	    JSONObject respuesta = new JSONObject();
	    // ObjectMapper mapper= new ObjectMapper();
	    // Map<String, String> data =
	    // mapper.readValue(request.getHeader("datos"), Map.class);
	    // String token=request.getHeader("Authorization");
	    // if(token==null){
	    // throw new GeneralException("seguridad_no_token");
	    // }    

	    ActaPrimigenia datosActa = this.consultarDatosActa(modelo.getString("id"));
	    ObjectMapper mapper = new ObjectMapper();
		JSONObject dActa = new JSONObject(mapper.writeValueAsString(datosActa));
	
        Date fecha2 = new Date();
        DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fecha2 = outFormat.parse(dActa.getString("fechaIncripcion")+" 00:00:00");
        log.info("---------->fechaGuardarHijo "+fecha2);
        long millis = (fecha2.getTime());
        log.info("---------->val2 "+millis);

		dActa.put("fechaIncripcion", millis);
	    modelo.put("AP", dActa);   
	    
	    String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "NC_datos_acta_solicitud_adopcion.html"));
	    log.info("******Contenido Html************" + vista);
	    modelo.put("titulo", "Datos para la solicitud");
	    respuesta.put("vista", vista);
	    respuesta.put("modelo", modelo);

	    return respuesta.toString();
	  }

/////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	/**
	 * Funcion que actualiza los participantes de una solicitud
	 * @author Elly Estaba
	 * @param id de la solicitud String status
	 * @return Solicitud soli
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/actualizarParticipanteActa", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public String actualizarParticipante(@RequestBody String data, HttpSession session, HttpServletResponse response) throws Exception {
		//log.info("DATA MODELO ID********" + data);
		log.info("****************************/actualizarParticipante**************************************");
		boolean decJSatisfactorio=false;
		boolean guardo=false;
		JSONObject datos = new JSONObject(data);
		log.info("DATA MODELO*******" + datos);
		
		Solicitud objSolicitud = new  Solicitud();  
		objSolicitud = solicitud.consultarDetalleSolicitud(datos.getString("id"));
		log.info("num solicitud*******" + datos.getString("id"));
			
	////Guardando Nacimiento
					Nacimiento Nac = new Nacimiento();
					JSONObject CM = datos.getJSONObject("CM");
					Acta Act = new Acta();
					String Nsoli = datos.getString("id");
					String numeroActa = new String();
					ActaServicioCliente servicioActa = new ActaServicioCliente();
					numeroActa = servicioActa.buscarNumeroActaPorSolic(Nsoli);
					Act.setNumeroActa(numeroActa);
//				Nac.setActa(Act);
				log.info("CM "+CM);
//				Nac.setNumeroCertificado(CM.getInt("numCert"));
				String fechaCMN = CM.getString("fechaCert");
		        DateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		        DateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
		        Date date = formatoEntrada.parse(fechaCMN);
		        String formatoFechaFin = formatoSalida.format(date);
		        Date fechaCertificadoFin = formatoSalida.parse(formatoFechaFin);
		        log.info("------------>FECHA"+fechaCMN);
		        log.info("==========="+date+"============");
		        log.info("=====formafecha======"+formatoFechaFin+"============");
		        log.info("------------>FECHA FIN CERTIFICADO "+fechaCertificadoFin);
//					Date F;
//					String dia = CM.getJSONObject("fechaCert").getString("dia2");
//					String mes = CM.getJSONObject("fechaCert").getJSONObject("mes2").getString("nombre");
//					String anno = CM.getJSONObject("fechaCert").getString("ano2");
//					String fecha = dia+"/"+mes+"/"+anno;
////		             fechaCertificadoNac = sdf4.parse(sdf4.format(new Date()));
//					SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
//					F =  outputFormat.parse(fecha);
//					log.info(F.toString());
		        String snomCMN=" "; String sapeCMN=" ";
	            try{
	                snomCMN=CM.getString("segundoNombre");
	            }catch (Exception e){}
	            try{
	                sapeCMN=CM.getString("segundoApellido");
	            }catch (Exception e){}
	            log.info("==========seg nom==========="+CM.getString("segundoNombre"));
	            log.info("========Vnom==========="+snomCMN);
	            log.info("==========seg apeee==========="+CM.getString("segundoApellido"));
	            log.info("========Vape==========="+sapeCMN);
		        Nac.setActa(Act);
				Nac.setNumeroCertificado(CM.getInt("numCert"));
				Nac.setFechaCertificado(fechaCertificadoFin);
				Nac.setDocumentoIdentidadMedico(CM.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero"));
				Nac.setPrimerNombreMedico(CM.getString("primerNombre"));
				Nac.setPrimerApellidoMedico(CM.getString("primerApellido"));
//				   String segCM = "   ";
//				   try{
//					   log.info("///////segundo nombre///////////"+datos.getJSONObject("CM").getString("segundoNombre"));
//				    if(datos.getJSONObject("CM").getString("segundoNombre") != null)
//				     segCM = datos.getJSONObject("CM").getString("segundoNombre");
//				   }catch(Exception e){}
				Nac.setSegundoNombreMedico(snomCMN);
//				   String segACM = "   ";
//				   try{
//				    if(datos.getJSONObject("CM").getString("segundoApellido") != null)
//				      segACM = datos.getJSONObject("CM").getString("segundoApellido");
//				   }catch(Exception e){}		  
				Nac.setSegundoApellidoMedico(sapeCMN);
				Nac.setNuMPPS(CM.getInt("numMPPS"));
				Nac.setCentroSalud(CM.getString("centroSalud"));
				String sex = datos.getJSONObject("ADO").getString("sexo");
				String sexo;
				if(sex=="FEMENINO"){
					sexo="F";
				}else{
					sexo="M";
				}
				Nac.setSexo(sexo);
				log.info(Nac.toString());
				//Nac.setSexo(datos.getJSONObject("ADO").getString("sexo"));
				log.info("======SEXO ADO=========="+datos.getJSONObject("ADO").getString("sexo"));

				  ObjectMapper mapper20 = new ObjectMapper();
				  JSONObject prueba = new JSONObject (mapper20.writeValueAsString(Nac));
				  log.info("objedo Certificado " + prueba.toString());
				
				boolean nacSatisfactorio = servicioActa.guardarNacimiento(Nac); 
				log.info("\n\n\n\n\n\n\n\n -------------------------  GUARDAR EL NACIMIENTO WEB-------------------------- ");
				log.info("Numero de solcitud "+Nsoli);
				log.info("Respuesta del servicio "+nacSatisfactorio);
				
				log.info("-------------------------  GUARDAR EL NACIMIENTO WEB -------------------------- \n\n\n\n\n\n\n"); 
	if(nacSatisfactorio==false){
		guardo=false;
		log.info("*******No se guardoooo**********"+guardo);
		
	}else{
		log.info("*******entro a guardar dec**********");
			////Guardando Decision Judicial
				DecisionJudicial DecJ = new DecisionJudicial();
				JSONObject IDJ = datos.getJSONObject("IDJ");
					log.info("DEJ "+IDJ);
					log.info("*********Acta "+numeroActa);
			DecJ.setNombreTribunal(IDJ.getString("tribunal"));
			DecJ.setNumeroSentencia(IDJ.getString("sentencia")); 
			DecJ.setPrimerNombreJuez(IDJ.getString("primerNombre"));
			 String segDJ = "   ";
			   try{
			    if(datos.getJSONObject("IDJ").getString("segundoNombre") != null)
			     segDJ = datos.getJSONObject("IDJ").getString("segundoNombre");
			   }catch(Exception e){
				   segDJ = "   ";
			   }
		    DecJ.setSegundoNombreJuez(segDJ);
			DecJ.setPrimerApellidoJuez(IDJ.getString("primerApellido"));
			 String segADJ = "   ";
			   try{
			    if(datos.getJSONObject("IDJ").getString("segundoApellido") != null)
			      segADJ = datos.getJSONObject("IDJ").getString("segundoApellido");
			   }catch(Exception e){
				   segADJ = "   ";
			   }		
			DecJ.setSegundoApellidoJuez(segADJ);
			 String CedDJ = "   ";
			   try{
			    if(IDJ.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero") != null){
			      String cednum = IDJ.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero");
				  String cedTip = IDJ.getString("tipoDocumento");
			             CedDJ = cedTip+"-"+cednum;
			    }
			   }catch(Exception e){
				   CedDJ = "   ";
			   }	
			   log.info("=========Cedula============="+CedDJ);
			   DecJ.setCedulaJuez(CedDJ);
			   Date FDJ = new Date();
				String diaDJ = IDJ.getJSONObject("fecha").getString("dia2");
				String mesDJ = IDJ.getJSONObject("fecha").getJSONObject("mes2").getString("nombre");
				String annoDJ = IDJ.getJSONObject("fecha").getString("ano2");
				String fechaDJ = diaDJ+"/"+mesDJ+"/"+annoDJ;
				DateFormat outputFormatDJ = new SimpleDateFormat("dd/MM/yyyy");
				FDJ =  outputFormatDJ.parse(fechaDJ);
			DecJ.setFechaSentencia(FDJ);
			DecJ.setNumeroActa(numeroActa);
			 decJSatisfactorio = servicioActa.guardarDecisionJudicial(DecJ); 
					
			
			log.info("\n\n\n\n\n\n\n\n -------------------------  GUARDAR EL IDJ WEB-------------------------- ");
			log.info("Numero de solcitud "+Nsoli);
			log.info("Respuesta del servicio "+decJSatisfactorio);
			
			log.info("-------------------------  GUARDAR EL IDJ WEB -------------------------- \n\n\n\n\n\n\n"); 
			
			
			
	   }
		if(decJSatisfactorio==false){
			guardo=false;
			log.info("*******No se guardoooo**********"+guardo);
//			return false;			
		}else{	
		/////Actualizando Paticipantes
		    JSONObject ado = datos.getJSONObject("ADO");
		    String segNomADO = " ";
			String segApeADO = " ";
					try{
						if(datos.getJSONObject("ADO").getString("segundoNombre") != null){
							segNomADO = datos.getJSONObject("ADO").getString("segundoNombre");
					}
						}catch(Exception e){}
					try{
						if(datos.getJSONObject("ADO").getString("segundoApellido") != null){
							segApeADO = datos.getJSONObject("ADO").getString("segundoApellido");
					}
						}catch(Exception e){}
					
//		    ---------------------------Parseo Fecha--------------------------------	           
	           Date fecha1 = new Date();
	           Date fecha2 = new Date();
	           DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	           
	           try{
	              DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	              fecha1 = inputFormat.parse(ado.getString("fechaNacimiento"));
	           }catch(Exception e) {
	              long val = Long.parseLong(ado.getString("fechaNacimiento"));
	               fecha1 = new Date(val);
	           }
	           JSONObject horaMin = ado.getJSONObject("horaNacimiento");
	           String hora = horaMin.getString("hora");
	           String minuto = horaMin.getString("minuto");
	           
	           log.info("horaMin-----------> "+horaMin);
	           String outputText = outFormat.format(fecha1);
	           
	           log.info("outputText-----------> "+outputText);
	           String arrayFechahijo[] = outputText.split(" ");
	           
	           fecha2 = outFormat.parse(arrayFechahijo[0]+" "+hora+":"+minuto+":00");
	           log.info("---------->fechaGuardarHijo "+fecha2);
	           long millis = (fecha2.getTime());
	           log.info("---------->val2 "+millis);
	           ado.remove("horaNacimiento");
	           ado.remove("fechaNacimiento");
	           ado.put("fechaNacimiento", millis);
//	           String hijoString = ado.toString();

//		    -----------------------------------------------------------
//		    log.info("JSON DEL ADOO********** "+ datos.getJSONObject("ADO"));
//		    ado.getJSONArray("direccion").getJSONObject(0).getJSONObject("pais").remove("$$hashKey");
//		    try{
//			ado.getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").remove("$$hashKey");
//			ado.getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").remove("$$hashKey");
//			ado.getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").remove("$$hashKey");
//		    }catch(Exception e){ }
	           
	           
	           JSONObject paisAdo =  new JSONObject();
	           JSONObject estadoAdo =  new JSONObject();
	           JSONObject municipioAdo =  new JSONObject();
	           JSONObject parroquiaAdo =  new JSONObject();
	           
	           String valorPais = " ";
	           String valorEstado = " ";
	           String valorMunicipio = " ";
	           String valorParroquia = " ";
	            try{
	                valorPais = ado.getJSONArray("direccion").getJSONObject(0).getString("pais")==null?" ":ado.getJSONArray("direccion").getJSONObject(0).getString("pais");
	                valorEstado = ado.getJSONArray("direccion").getJSONObject(0).getString("estado")==null?" ":ado.getJSONArray("direccion").getJSONObject(0).getString("estado");
	                valorMunicipio = ado.getJSONArray("direccion").getJSONObject(0).getString("municipio")==null?" ":ado.getJSONArray("direccion").getJSONObject(0).getString("municipio");
	                valorParroquia = ado.getJSONArray("direccion").getJSONObject(0).getString("parroquia")==null?" ":ado.getJSONArray("direccion").getJSONObject(0).getString("parroquia");
	            }catch (Exception e){}
	           
	          log.info("--------------- DIRECCION pais" + valorPais);
	           ado.getJSONArray("direccion").getJSONObject(0).put("pais", paisAdo);
	           ado.getJSONArray("direccion").getJSONObject(0).put("estado", estadoAdo);
	           ado.getJSONArray("direccion").getJSONObject(0).put("municipio", municipioAdo);
	           ado.getJSONArray("direccion").getJSONObject(0).put("parroquia", parroquiaAdo);
	           
	           ado.getJSONArray("direccion").getJSONObject(0).getJSONObject("pais").put("nombre", valorPais);
	           ado.getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").put("nombre", valorEstado);
	           ado.getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").put("nombre", valorMunicipio);
	           ado.getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").put("nombre", valorParroquia);

	        JSONObject tipoDADO = new JSONObject();
			tipoDADO.put("codigo", "NAC");
		  	tipoDADO.put("descripcion", "NACIMIENTO");
		  	tipoDADO.put("nombre", "DIRECCION DE NACIMIENTO");
	    	ado.getJSONArray("direccion").getJSONObject(0).put("tipoDireccion", tipoDADO);
			ObjectMapper mapper = new ObjectMapper();
			Participante ADO = mapper.readValue(ado.toString(), Participante.class);		
		ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
		log.info("********ID...SOLICITUD///***" + datos.getString("id"));
		log.info("********ANTES DE ACTUALIZAR ADO**********" + ADO);
		Participante ADOPTADO = servicios.actualizarParticipante(datos.getString("id"), ADO);
		log.info("********DESpues.... DE ACTUALIZAR ADO**********" + ADOPTADO);
		
		  JSONObject mam = new JSONObject();
			String segNomMam = " ";
			String segApeMam = " ";
			try{
				  mam = datos.getJSONObject("MAM");
				if(datos.getJSONObject("MAM")!= null){
				log.info("********.........MAMA.........**********" + mam);
					try{
						if(datos.getJSONObject("MAM").getString("segundoNombre") != null){
							segNomMam = datos.getJSONObject("MAM").getString("segundoNombre");
					}
						}catch(Exception e){}
					try{
						if(datos.getJSONObject("MAM").getString("segundoApellido") != null){
							segApeMam = datos.getJSONObject("MAM").getString("segundoApellido");
					}
						}catch(Exception e){}
				
//					    mam.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").remove("$$hashKey");
//					    try{
//						mam.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").remove("$$hashKey");
//						mam.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").remove("$$hashKey");
//						mam.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").remove("$$hashKey");
//					    }catch(Exception e){ }
					
				       JSONObject paisMam =  new JSONObject();
			           JSONObject estadoMam =  new JSONObject();
			           JSONObject municipioMam =  new JSONObject();
			           JSONObject parroquiaMam =  new JSONObject();
			           
			           String valorPaisM = " ";
			           String valorEstadoM = " ";
			           String valorMunicipioM = " ";
			           String valorParroquiaM = " ";
			            try{
			                valorPaisM = mam.getJSONArray("direccion").getJSONObject(1).getString("pais")==null?" ":mam.getJSONArray("direccion").getJSONObject(1).getString("pais");
			                valorEstadoM = mam.getJSONArray("direccion").getJSONObject(1).getString("estado")==null?" ":mam.getJSONArray("direccion").getJSONObject(1).getString("estado");
			                valorMunicipioM = mam.getJSONArray("direccion").getJSONObject(1).getString("municipio")==null?" ":mam.getJSONArray("direccion").getJSONObject(1).getString("municipio");
			                valorParroquiaM = mam.getJSONArray("direccion").getJSONObject(1).getString("parroquia")==null?" ":mam.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
			            }catch (Exception e){}
			           
			          log.info("--------------- DIRECCION pais" + valorPaisM);
			           mam.getJSONArray("direccion").getJSONObject(1).put("pais", paisMam);
			           mam.getJSONArray("direccion").getJSONObject(1).put("estado", estadoMam);
			           mam.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioMam);
			           mam.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaMam);
			           
			           mam.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisM);
			           mam.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoM);
			           mam.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioM);
			           mam.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaM);					
					
					
						JSONObject tipoDM = new JSONObject();
						tipoDM.put("codigo", "RES");
						tipoDM.put("descripcion", "RESIDENCIA");
						tipoDM.put("nombre", "DIRECCION DE RESIDENCIA");
						mam.getJSONArray("direccion").getJSONObject(1).put("tipoDireccion", tipoDM);
						try{
							mam.getJSONObject("comunidadIndigena").remove("$$hashKey");
							String ComInd = mam.getJSONObject("comunidadIndigena").getString("nombre");
							mam.remove("comunidadIndigena");
							mam.put("comunidadIndigena", ComInd);
							}
							catch (Exception e)
							{
							mam.getString("comunidadIndigena");
							}
						try{
							mam.getJSONObject("ocupacion").remove("$$hashKey");
							String Ocup = mam.getJSONObject("ocupacion").getString("nombre");
							mam.remove("ocupacion");
							mam.put("ocupacion", Ocup);
							}
							catch (Exception e)
							{
							mam.getString("ocupacion");
							}
//						mam.getJSONObject("ocupacion").remove("$$hashKey");
//						String Ocup = mam.getJSONObject("ocupacion").getString("nombre");
//						mam.remove("ocupacion");
//						mam.put("ocupacion", Ocup);
						if (mam.getString("sexo")!=null){
						mam.put("sexo", "FEMENINO");
						}else{
				  		mam.put("sexo", "FEMENINO");
						}
						log.info("********................**********" + mam);
					    ObjectMapper mapper1 = new ObjectMapper();
						Participante MAM = mapper1.readValue(mam.toString(), Participante.class);
						log.info("********.**********" + mam);
					log.info("********ID...SOLICITUD///***" + datos.getString("id"));
					log.info("********ANTES DE ACTUALIZAR MAMA**********" + MAM);
					Participante MAMA = servicios.actualizarParticipante(datos.getString("id"), MAM);
					
					
					log.info("********DESpues.... DE ACTUALIZAR MAMA**********" + MAMA);
				}
			}catch (Exception e){
			log.info("*******No hay datos de la madre que guardar**********");
			}
		
			
			JSONObject pap = new JSONObject(); ///datos.getJSONObject("PAP");
			String segNomPap = " ";
			String segApePap = " ";
			try{
				 pap = datos.getJSONObject("PAP");
				 
				if(datos.getJSONObject("PAP")!= null){
				log.info("...................."+pap);
					try{
						if(datos.getJSONObject("PAP").getString("segundoNombre") != null){
							segNomPap = datos.getJSONObject("PAP").getString("segundoNombre");
					}
						}catch(Exception e){}
					try{
						if(datos.getJSONObject("PAP").getString("segundoApellido") != null){
							segApePap = datos.getJSONObject("PAP").getString("segundoApellido");
					}
						}catch(Exception e){}
				
//		    pap.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").remove("$$hashKey");
//		    try{
//			pap.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").remove("$$hashKey");
//			pap.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").remove("$$hashKey");
//			pap.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").remove("$$hashKey");
//		    }catch(Exception e){ }
					 JSONObject paisPap =  new JSONObject();
			           JSONObject estadoPap =  new JSONObject();
			           JSONObject municipioPap =  new JSONObject();
			           JSONObject parroquiaPap =  new JSONObject();
			           
			           String valorPaisP = " ";
			           String valorEstadoP = " ";
			           String valorMunicipioP = " ";
			           String valorParroquiaP = " ";
			            try{
			                valorPaisP = pap.getJSONArray("direccion").getJSONObject(1).getString("pais")==null?" ":pap.getJSONArray("direccion").getJSONObject(1).getString("pais");
			                valorEstadoP = pap.getJSONArray("direccion").getJSONObject(1).getString("estado")==null?" ":pap.getJSONArray("direccion").getJSONObject(1).getString("estado");
			                valorMunicipioP = pap.getJSONArray("direccion").getJSONObject(1).getString("municipio")==null?" ":pap.getJSONArray("direccion").getJSONObject(1).getString("municipio");
			                valorParroquiaP = pap.getJSONArray("direccion").getJSONObject(1).getString("parroquia")==null?" ":pap.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
			            }catch (Exception e){}
			           
			          log.info("--------------- DIRECCION pais" + valorPaisP);
			           pap.getJSONArray("direccion").getJSONObject(1).put("pais", paisPap);
			           pap.getJSONArray("direccion").getJSONObject(1).put("estado", estadoPap);
			           pap.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioPap);
			           pap.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaPap);
			           
			           pap.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisP);
			           pap.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoP);
			           pap.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioP);
			           pap.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaP);
					
	     	JSONObject tipoDP = new JSONObject();
	    	tipoDP.put("codigo", "RES");
	    	tipoDP.put("descripcion", "RESIDENCIA");
	    	tipoDP.put("nombre", "DIRECCION DE RESIDENCIA");
	    	pap.getJSONArray("direccion").getJSONObject(1).put("tipoDireccion", tipoDP);
			try{
				pap.getJSONObject("comunidadIndigena").remove("$$hashKey");
			    String ComIndP = pap.getJSONObject("comunidadIndigena").getString("nombre");
			    pap.remove("comunidadIndigena");
			    pap.put("comunidadIndigena", ComIndP);
				}
				catch (Exception e)
				{
				pap.getString("comunidadIndigena");
				}
			try{
				pap.getJSONObject("ocupacion").remove("$$hashKey");
			    String POcup = pap.getJSONObject("ocupacion").getString("nombre");
			    pap.remove("ocupacion");
			    pap.put("ocupacion", POcup);
				}
				catch (Exception e)
				{
				pap.getString("ocupacion");
				}
//		    pap.getJSONObject("ocupacion").remove("$$hashKey");
//		    String POcup = pap.getJSONObject("ocupacion").getString("nombre");
//		    pap.remove("ocupacion");
//		    pap.put("ocupacion", POcup);
			if (pap.getString("sexo")!=null){
			pap.put("sexo", "MASCULINO");
			}else{
			pap.put("sexo", "MASCULINO");
				}
		    ObjectMapper mapper2 = new ObjectMapper();
			Participante PAP = mapper2.readValue(pap.toString(), Participante.class);
		log.info("********ID...SOLICITUD///***" + datos.getString("id"));
		log.info("********ANTES DE ACTUALIZAR PAPA**********" + PAP);
		Participante PAPA = servicios.actualizarParticipante(datos.getString("id"), PAP);
		log.info("\n\n\n\n\n********DESpues.... DE ACTUALIZAR PAPA**********" + PAPA);
				}
			}catch (Exception e){
			log.info("*******No hay datos del padre que guardar**********");
			}
		
		
		   JSONObject ta1 = datos.getJSONObject("TA1");
		  // String nombreTestigo1 = "";
		   String segT1 = " ";
		   String segT1A = " ";
		   try{
		    if(datos.getJSONObject("TA1").getString("segundoNombre") != null)
		     segT1 = datos.getJSONObject("TA1").getString("segundoNombre");
		   }catch(Exception e){
		    
		   }
		   try{
		    if(datos.getJSONObject("TA1").getString("segundoApellido") != null)
		     segT1A = datos.getJSONObject("TA1").getString("segundoApellido");
		   }catch(Exception e){
		    
		   }
//		   nombreTestigo1 = modeloCompleto.getJSONObject("TA1").getString("primerNombre") + " " + segT1 + " " + modeloCompleto.getJSONObject("TA1").getString("primerApellido") + " " + segT1A; 
//		   String nacionalidadTestigo1 = "";
//		   try{
//		    if(datos.getJSONObject("TA1").getString("nacionalidad") != null)
//		     nacionalidadTestigo1 = datos.getJSONObject("TA1").getString("nacionalidad");
//		   }catch(Exception e){
//		    
//		   }	   
//		  	ta1.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").remove("$$hashKey");
//		  	try{
//		  	ta1.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").remove("$$hashKey");
//		  	ta1.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").remove("$$hashKey");
//		  	ta1.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").remove("$$hashKey");
//		  	}catch(Exception e){ }
		   JSONObject paisTa1 =  new JSONObject();
           JSONObject estadoTa1 =  new JSONObject();
           JSONObject municipioTa1 =  new JSONObject();
           JSONObject parroquiaTa1 =  new JSONObject();
           
           String valorPaisT1 = " ";
           String valorEstadoT1 = " ";
           String valorMunicipioT1 = " ";
           String valorParroquiaT1 = " ";
            try{
                valorPaisT1 = ta1.getJSONArray("direccion").getJSONObject(1).getString("pais")==null?" ":ta1.getJSONArray("direccion").getJSONObject(1).getString("pais");
                valorEstadoT1 = ta1.getJSONArray("direccion").getJSONObject(1).getString("estado")==null?" ":ta1.getJSONArray("direccion").getJSONObject(1).getString("estado");
                valorMunicipioT1 = ta1.getJSONArray("direccion").getJSONObject(1).getString("municipio")==null?" ":ta1.getJSONArray("direccion").getJSONObject(1).getString("municipio");
                valorParroquiaT1 = ta1.getJSONArray("direccion").getJSONObject(1).getString("parroquia")==null?" ":ta1.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
            }catch (Exception e){}
           
          log.info("--------------- DIRECCION pais" + valorPaisT1);
           ta1.getJSONArray("direccion").getJSONObject(1).put("pais", paisTa1);
           ta1.getJSONArray("direccion").getJSONObject(1).put("estado", estadoTa1);
           ta1.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioTa1);
           ta1.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaTa1);
           
           ta1.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisT1);
           ta1.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoT1);
           ta1.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioT1);
           ta1.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaT1);
		   
		  	JSONObject tipoDT1 = new JSONObject();
		  	tipoDT1.put("codigo", "RES");
		  	tipoDT1.put("descripcion", "RESIDENCIA");
		  	tipoDT1.put("nombre", "DIRECCION DE RESIDENCIA");
	    	ta1.getJSONArray("direccion").getJSONObject(1).put("tipoDireccion", tipoDT1);
	    	try{
	    		ta1.getJSONObject("comunidadIndigena").remove("$$hashKey");
			    String ComIndT1 = ta1.getJSONObject("comunidadIndigena").getString("nombre");
			    ta1.remove("comunidadIndigena");
			    ta1.put("comunidadIndigena", ComIndT1);
			   
				}
				catch (Exception e)
				{
				ta1.getString("comunidadIndigena");
				}
	    	try{
	    		ta1.getJSONObject("ocupacion").remove("$$hashKey");
	 		    String T1Ocup = ta1.getJSONObject("ocupacion").getString("nombre");
	 		    ta1.remove("ocupacion");
	 		    ta1.put("ocupacion", T1Ocup);
				}
				catch (Exception e)
				{
				ta1.getString("ocupacion");
				}
//	        ta1.getJSONObject("ocupacion").remove("$$hashKey");
//		    String T1Ocup = ta1.getJSONObject("ocupacion").getString("nombre");
//		    ta1.remove("ocupacion");
//		    ta1.put("ocupacion", T1Ocup);
//			if (ta1.getString("sexo")!=null){
//			ta1.put("sexo", "M");
//			}else{
//			ta1.put("sexo", "M");
//				}
		  	ObjectMapper mapper3 = new ObjectMapper();
			Participante TA1 = mapper3.readValue(ta1.toString(), Participante.class);
		///ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
		log.info("********ID...SOLICITUD///***" + datos.getString("id"));
		log.info("********ANTES DE ACTUALIZAR TEST1**********" + TA1);
		Participante TEST1 = servicios.actualizarParticipante(datos.getString("id"), TA1);
		log.info("********DESpues.... DE ACTUALIZAR TEST1**********" + TEST1);
		
		  JSONObject ta2 = datos.getJSONObject("TA2");
		  log.info("********TEST1**********" + datos.getJSONObject("TA2"));
		   String segT2 = "   ";
		   String segT2A = "   ";
		   try{
		    if(datos.getJSONObject("TA2").getString("segundoNombre") != null)
		     segT2 = datos.getJSONObject("TA2").getString("segundoNombre");
		   }catch(Exception e){  
		   }
		   try{
		    if(datos.getJSONObject("TA2").getString("segundoApellido") != null)
		     segT2A = datos.getJSONObject("TA2").getString("segundoApellido");
		   }catch(Exception e){
		   }		  
//		  	ta2.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").remove("$$hashKey");
//		  	try{
//		  	ta2.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").remove("$$hashKey");
//		  	ta2.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").remove("$$hashKey");
//		  	ta2.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").remove("$$hashKey");
//		  	}catch(Exception e){ 
//		  		log.info("entro al catch sin estado");
		  		
		   JSONObject paisTa2 =  new JSONObject();
           JSONObject estadoTa2 =  new JSONObject();
           JSONObject municipioTa2 =  new JSONObject();
           JSONObject parroquiaTa2 =  new JSONObject();
           
           String valorPaisT2 = " ";
           String valorEstadoT2 = " ";
           String valorMunicipioT2 = " ";
           String valorParroquiaT2 = " ";
            try{
                valorPaisT2 = ta2.getJSONArray("direccion").getJSONObject(1).getString("pais")==null?" ":ta2.getJSONArray("direccion").getJSONObject(1).getString("pais");
                valorEstadoT2 = ta2.getJSONArray("direccion").getJSONObject(1).getString("estado")==null?" ":ta2.getJSONArray("direccion").getJSONObject(1).getString("estado");
                valorMunicipioT2 = ta2.getJSONArray("direccion").getJSONObject(1).getString("municipio")==null?" ":ta2.getJSONArray("direccion").getJSONObject(1).getString("municipio");
                valorParroquiaT2 = ta2.getJSONArray("direccion").getJSONObject(1).getString("parroquia")==null?" ":ta2.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
            }catch (Exception e){}
           
          log.info("--------------- DIRECCION pais" + valorPaisT2);
           ta2.getJSONArray("direccion").getJSONObject(1).put("pais", paisTa2);
           ta2.getJSONArray("direccion").getJSONObject(1).put("estado", estadoTa2);
           ta2.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioTa2);
           ta2.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaTa2);
          
           ta2.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisT2);
           ta2.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoT2);
           ta2.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioT2);
           ta2.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaT2);
		   
//		        ta2.getJSONArray("direccion").getJSONObject(1).append("estado", null);
//		        ta2.getJSONArray("direccion").getJSONObject(1).append("municipio", null);
//		        ta2.getJSONArray("direccion").getJSONObject(1).append("parroquia", null);
//		  	}
		  JSONObject tipoDT2 = new JSONObject();
		  	tipoDT2.put("codigo", "RES");
	  		tipoDT2.put("descripcion", "RESIDENCIA");
	  		tipoDT2.put("nombre", "DIRECCION DE RESIDENCIA");
	  		ta2.getJSONArray("direccion").getJSONObject(1).put("tipoDireccion", tipoDT2);
	  		try{
	  			ta2.getJSONObject("comunidadIndigena").remove("$$hashKey");
		  		String ComIndT2 = ta2.getJSONObject("comunidadIndigena").getString("nombre");
		  		ta2.remove("comunidadIndigena");
		    	ta2.put("comunidadIndigena", ComIndT2);
				}
				catch (Exception e)
				{
				ta2.getString("comunidadIndigena");
				}
	  		try{
	  			 ta2.getJSONObject("ocupacion").remove("$$hashKey");
				 String T2Ocup = ta2.getJSONObject("ocupacion").getString("nombre");
				 ta2.remove("ocupacion");
				 ta2.put("ocupacion", T2Ocup);
				}
				catch (Exception e)
				{
				ta2.getString("ocupacion");
				}
//	  		    ta2.getJSONObject("ocupacion").remove("$$hashKey");
//			    String T2Ocup = ta2.getJSONObject("ocupacion").getString("nombre");
//			    ta2.remove("ocupacion");
//			    ta2.put("ocupacion", T2Ocup);
//	    	if (ta2.getString("sexo")!=null){
//	    		ta2.put("sexo", "M");
//	    	}else{
//	    		ta2.put("sexo", "M");
//			}
		    ObjectMapper mapper4 = new ObjectMapper();
			Participante TA2 = mapper4.readValue(ta2.toString(), Participante.class);;
		log.info("********ID...SOLICITUD///***" + datos.getString("id"));
		log.info("********ANTES DE ACTUALIZAR TEST2**********" + TA2);
		Participante TEST2 = servicios.actualizarParticipante(datos.getString("id"), TA2);
		log.info("********DESpues.... DE ACTUALIZAR TEST2**********" + TEST2);
		guardo=true;
		}
		log.info("Valor de la variable" + guardo);
		
		JSONObject respuesta = new JSONObject();
		datos.put("valorGuardado", guardo);
		respuesta.put("modelo",datos);
			
//		Solicitud solTest= new Solicitud();
		///Solicitud soli = null;
    	return respuesta.toString() ;
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
		/// ObjectMapper mapper= new ObjectMapper();
		// JSONObject objetoJs= new JSONObject();
		// Map<String, String> data =
		/// mapper.readValue(request.getHeader("datos"), Map.class);
		log.info("*********entrando al metodo consultarParticpante*******");

		ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();
		List<Participante> participante;
		participante = participanteCliente.consultarParticPorSolicitud(numSolicitud, variable);
		log.info("*********" + participante + "*******");
		return participante;
	}
	
	
	/**
	 * Funcion que devuelve un mapa que contiene validacion de acta
	 * 
	 * @author Ernesto Jimenez
	 * @param data:
	 *            Numero de acta
	 * @return Mapa
	 */
	@RequestMapping(value = "/actualizarParticipante/{numActa}", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> validarActa(@RequestParam(value = "numActa", required = false) String numActa) {
		log.info("PRUEBAAAAAA*******//// ");
		ActaServicioCliente servicioCliente = new ActaServicioCliente();
		Map<String, String> resp = servicioCliente.existeActa(numActa);
		log.info("PRUEBAAAAAA******* "+resp);
		return resp;
	}
	
	//--------------------METODO DEVUELVE VISTA OBSERVACION. MARIA VICTORIA-----------------------------//
	
	  /**
    * Funcion que devuelve una vista y modelo a presentar
    * @author Elly Estaba
    * @param data:
    * contiene los datos obtenidos del rootScope
    * @return JsonObject
    */
   @RequestMapping(value = "/verObservacionesRADOP", method = RequestMethod.POST)
   public @ResponseBody String verObservacionesRADOP(@RequestBody String data, HttpSession sesion,
         HttpServletRequest request)
         throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
      String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "ver_observaciones.html"));
      log.info("******Contenido Html************" + vista);
      modelo.put("mensaje", "Carga de documento exitosa");
      modelo.put("titulo", "ver observaciones");
      respuesta.put("vista", vista);
      respuesta.put("modelo", modelo);

      return respuesta.toString();
   }
   
   
   //----------------------METODO DEVUELVE HTML DATOS ADOPTADO MARIA VICTORIA 13/10
   
   /**
    * Funcion que devuelve una vista y modelo a presentar
    * @author Elly Estaba
    * @param data:
    * contiene los datos obtenidos del rootScope
    * @return JsonObject
    */
   @RequestMapping(value = "/RADOP_datos_adoptado", method = RequestMethod.POST)
   public @ResponseBody String RADOP_datos_adoptado(@RequestBody String data, HttpSession sesion,
         HttpServletRequest request)
         throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException {
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
      String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "datos_adoptado.html"));
      log.info("******Contenido Html************" + vista);
      
      modelo.put("titulo", "Datos adoptado");
      respuesta.put("vista", vista);
      respuesta.put("modelo", modelo);

      return respuesta.toString();
   }	
	/**
	 * Funcion que devuelve una decision judicial por num de solicitud
	 * @author Elly Estaba
	 * @param data:   num de solicitud     
	 * @return 
	 * @return los datos guardados en un decision Judicial
	 */
	@RequestMapping(value = "/consultarDecisionJudicial", method = RequestMethod.POST)
	public @ResponseBody  String consultarIDJ(@RequestBody String data, HttpSession session,  HttpServletResponse response ) throws Exception
	{  
		
		JSONObject datos = new JSONObject(data);
//		Solicitud objSolicitud = new  Solicitud();  
//		objSolicitud = solicitud.consultarDetalleSolicitud(datos.getString("id"));
		log.info("num solicitud*******" + datos.getString("id"));
		ActaServicioCliente servicioCliente = new ActaServicioCliente();
		DecisionJudicial resp = servicioCliente.consultarDecisionJudicial(datos.getString("id"));
		log.info("PRUEBAAAAAA******* "+resp);
		ObjectMapper mapper9 = new ObjectMapper();
		JSONObject DJ = new JSONObject(mapper9.writeValueAsString(resp));
	    datos.put("IDJ", DJ);
	    
	    
	    log.info("PRUEBAAA IDJ////////******* "+datos);
		return datos.toString();
	}
	
	
	/**
	 * Funcion que devuelve un nacimiento por num de solicitud
	 * @author Elly Estaba
	 * @param data:   num de solicitud     
	 * @return los datos guardados en certificado medico
	 */
//	@RequestMapping(value = "/consultarNacimiento", method = RequestMethod.POST)
//	public @ResponseBody  String consultarCM(@RequestBody String data, HttpSession session, HttpServletResponse response) throws Exception
//	{
//		JSONObject datos = new JSONObject(data);
//		log.info("num solicitud*******" + datos.getString("id"));
//		ActaServicioCliente servicioCliente = new ActaServicioCliente();
//		Nacimiento CMresp = servicioCliente.consultarNacimiento(datos.getString("id"));
//		log.info("PRUEBAAAAAA******* "+CMresp);
//		ObjectMapper mapper8 = new ObjectMapper();
//		JSONObject CMN = new JSONObject(mapper8.writeValueAsString(CMresp));
//		datos.put("CM", CMN);
//		return datos.toString();
//	}
	
	
	/**
	 * Funcion que guarda los datos del acta primi
	 * @author Elly Estaba
	 * @param data:   num de solicitud     
	 * @return 
	 * @return los datos guardados
	 * @throws ParseException 
	 */
	
	@RequestMapping(value = "/guardarActaPrim", method = RequestMethod.POST)
	public ActaPrimigenia guardarActaPrimigenia(@RequestBody String data, HttpSession session, HttpServletResponse response, HttpServletRequest request ) throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException, ParseException 
	{    
		String token=request.getHeader("Authorization");
		 log.info("TOKEN*******"+token);
		 
		String login = seguridadCliente.getUsernameCliente(token);
        FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
        Funcionario obtenerDatosFuncionario = servFuncionario.buscarPorLogin(login);
        String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
 
        Funcionario dataServicio = new Funcionario();
        CatalogoServicioCliente nConsecutivo = new  CatalogoServicioCliente();
        Long obtenerNconsecutivo = nConsecutivo.proximoNroConsecutivo(codOficina);
        log.info("--------obtenerNconsecutivo----- " + obtenerNconsecutivo);		
        
		log.info("****************************/Guardar Acta Primigenia**************************************");
		JSONObject modelo = new JSONObject(data);
		log.info("DATA MODELO*******" + modelo);

		/////guardando datos 
		    JSONObject ap = modelo.getJSONObject("AP");
		    log.info("JSON DEL ACTPRIM********** "+ modelo.getJSONObject("AP"));
		  ObjectMapper mapper = new ObjectMapper();
		  String NumSoli = modelo.getString("id");
		  ap.put("numeroSolic", NumSoli);
        String ofiActa =" ";
		  try{
	       ofiActa = ap.getJSONObject("nombreOficina").getString("nombre");
	  	     ap.remove("nombreOficina");
		  }catch(Exception ex){
			 ofiActa = ap.getString("nombreOficina");
		  }
//	  	  String ofiActa = ap.getJSONObject("nombreOficina").getString("nombre");
//	  	  ap.remove("nombreOficina");
	  	  
	      ap.put("nombreOficina", ofiActa);
	      log.info("nombreOficina "+ap.getString("nombreOficina"));
	      ap.put("numeroConsecutivo", obtenerNconsecutivo);
	      String fecha=" ";
	      try{
			  String dia =ap.getJSONObject("fechaIncripcion").getString("dia2");
			  String anno =ap.getJSONObject("fechaIncripcion").getString("ano2");
			  String mes =ap.getJSONObject("fechaIncripcion").getJSONObject("mes2").getString("nombre");
		            fecha = dia+"/"+mes+"/"+anno;
	      }catch(Exception e){
	    	  try{
	    	  Date f = new Date();  
		      DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		      DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		      Date date = inputFormat.parse(ap.getString("fechaIncripcion"));
		           fecha = outputFormat.format(date);
	    	  }catch(Exception m){
	    		  DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    		  long val = Long.parseLong(ap.getString("fechaIncripcion"));
	              Date fecha1 = new Date(val);
	               fecha = outputFormat.format(fecha1);
	           }
	      }
	      log.info("================"+ap.getString("fechaIncripcion")+"==============");	
          log.info("================"+fecha+"==============");	 

	      ap.put("fechaIncripcion", fecha);
 
		ActaPrimigenia AP = mapper.readValue(ap.toString(), ActaPrimigenia.class);
		ActaServicioCliente servicioCliente = new ActaServicioCliente();
		ActaPrimigenia actaprim = servicioCliente.guardarActaPrimigenia(AP);
		
    	return actaprim;
	}
	
	public Integer calcularEdad(String fecha, String tipo){
	    Date fechaNac=null;
        try {
            /**Se puede cambiar la mascara por el formato de la fecha
            que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
            en este caso es día mes año*/
            fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        //Se crea un objeto con la fecha actual
        Calendar fechaActual = Calendar.getInstance();
        //Se asigna la fecha recibida a la fecha de nacimiento.
        fechaNacimiento.setTime(fechaNac);
        //Se restan la fecha actual y la fecha de nacimiento
        int ano = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
        //Se ajusta el año dependiendo el mes y el día
        if(mes<0 || (mes==0 && dia<0)){
        	ano--;
        }
        //Regresa la edad en base a la fecha de nacimiento
        if(tipo != null && "D".equalsIgnoreCase(tipo.trim())){
        	return dia;
        }else if(tipo != null && "M".equalsIgnoreCase(tipo.trim())){
        	return mes;
        }else if(tipo != null && "A".equalsIgnoreCase(tipo.trim())){
        	return ano;
        }else{
        	return ano;
        }
    }
	
	   //-----------------METODO CONSULTA DATOS ACTA MARIA 14/10
   
   public @ResponseBody ActaPrimigenia consultarDatosActa (String numSolicitud) throws JsonGenerationException, JsonMappingException, IOException{

      log.info("--numSolicitud-------------> "+numSolicitud);
      ActaServicioCliente actaCliente = new ActaServicioCliente();
         ActaPrimigenia consulta =  null;         
         consulta = actaCliente.consultaActaPrimigenia(numSolicitud);
      return consulta;
  }
   
   
   @RequestMapping(value = "/RADOP_consultarDrools", method = RequestMethod.POST)
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
    log.info(kie.toString());
    Object pojo2 = validarKie(kie, pojo);

    String numSolicitud = modelo.getString("id");
    Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
    String[] codigoTipo = { "ADO" };
    
    // Consulta para validar si el fallecido tiene identificacion
    List<Participante> obetenerDatos = new ArrayList<Participante>();
    obetenerDatos = Participantes.consultarParticPorTipo(
      sol.getNumeroSolicitud(), codigoTipo);
//    String Identificacion = null;
//    if(!obetenerDatos.isEmpty()){
//    Participante participante = obetenerDatos.get(0);
//    if(!participante.getDocumentoIdentidad().get(0).getNumero().isEmpty()){
//     Identificacion = participante.getDocumentoIdentidad().get(0).getNumero();}
//    else {
//        Identificacion = null;
//       }
//    } else {
//     Identificacion = null;
//    }
//    modelo.put("Identificacion", Identificacion);
    // /fin consulta

    // Recupera el nombre de la proxima vista a ser presentada
    JSONObject jsonPojo = new JSONObject(mapper.writeValueAsString(pojo2));
//    String rutaBase = modelo.getString("rutaBase");
    jsonPojo.put("inscripOInserc", true);
  //  modelo.remove("vista");
    modelo.put("pojo", jsonPojo);
  
    modelo.put("titulo", "Documentos presentados");
    JSONObject respuesta = new JSONObject();
    String vistaDrools = jsonPojo.getString("vista");

    String lista = modelo.getJSONObject("pojo").getString("lista");
    String[] acronimos = lista.split(",");
    String[] acronimos1 = acronimos[0].split(":");
    String[] acronimos2 = acronimos[1].split(":");
    String[] acronimos3 = acronimos[2].split(":");
    Recaudo recaudos1 = new Recaudo();
    Recaudo recaudos2 = new Recaudo();
    Recaudo recaudos3 = new Recaudo();
    recaudos1 = recaudo.consultarRecaudoPorCodigo(acronimos1[0]);
    recaudos2 = recaudo.consultarRecaudoPorCodigo(acronimos2[0]);
    recaudos3 = recaudo.consultarRecaudoPorCodigo(acronimos3[0]);
    ObjectMapper mapperA = new ObjectMapper();
    JSONObject recau1 = new JSONObject(mapperA.writeValueAsString(recaudos1));
//    recau1.getString("nombre");
    JSONObject recau2 = new JSONObject(mapperA.writeValueAsString(recaudos2));
//    recau2.getString("nombre");
    JSONObject recau3 = new JSONObject(mapperA.writeValueAsString(recaudos3));
//    recau3.getString("nombre");
    List<String> nombreRecaudo = new ArrayList<>();
    nombreRecaudo.add(recau1.getString("nombre"));
    nombreRecaudo.add(recau2.getString("nombre"));
    nombreRecaudo.add(recau3.getString("nombre"));
    
    modelo.put("recaudos", nombreRecaudo);
    respuesta.put("vista", Util.leerArchivo(context.getRealPath(rutaHtml + "documentos_adopcion.html")));
    respuesta.put("modelo", modelo);
    log.info(respuesta.toString());
    return respuesta.toString();
   }

  
   public Object validarKie(Object kie, Object objeto) throws Exception{
	   Object objeto2=null;
	   //obtiene el nombre de la clase del objeto tramite, por ejemeplo Nacimiento o Adopcion
	   String nombreClase=objeto.getClass().getName().replace(objeto.getClass().getPackage().getName()+".", "");
	   log.info("**************Clase del Objeto a validar: "+nombreClase);
	   log.info("**************Clase del Kie: "+kie.getClass().getName());
	   Method[] metodosObjeto=objeto.getClass().getMethods();
	   for(Method metodo : metodosObjeto){
	    if(metodo.getName().equals("getEscenario")){
	     String escenario = (String) metodo.invoke(objeto, null);
	     log.info("**************Escenario: "+escenario);
	    }
	   }
	   Method[] metodos2=kie.getClass().getMethods();
	   for (Method metodo : metodos2){
	    log.info("Nombre del metodo: "+metodo.getName());
	    if(("validar"+nombreClase).equals(metodo.getName())){
	     log.info("************evaluando adopcion ******************");
	     log.info(kie.getClass().toString());
	     log.info(objeto.getClass().toString());
	     try{
	      objeto2= metodo.invoke(kie, objeto);
	     }catch(Exception e){
	      log.info("************ERROR: "+e.getMessage()+"*************");
	      e.printStackTrace();
	      throw e;
	     }
	    }
	   }
	   log.info("************objeto2##########: "+objeto2.getClass()+"*************");
	   return objeto2;
	  }
   
   
   /**
    *Funcion que valida la existencia del certificado medico de nacimiento dentro de la base de datos 
    * @author Elly Estaba
    * @param numCertificado String numero de certificado
    * @return boolean
    */
   @RequestMapping(value="/validarCertificadoMedico/{numeroCertificadoNac}", method = RequestMethod.GET)
   public @ResponseBody Boolean validarCertificadoNac(@PathVariable("numeroCertificadoNac") Long numeroCertificadoNac){
    log.info("-----------------numCertificado-------- " + numeroCertificadoNac);    
    String tramite = "RNACI";
    ActaServicioCliente servicioCliente = new ActaServicioCliente();
    boolean resp = servicioCliente.validarCertificadoMedico(tramite, numeroCertificadoNac);

    log.info("--------------resp----- " + resp);
    return resp;
   }
   
	
}
