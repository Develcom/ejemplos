package ve.gob.cne.sarc.nacimiento.web.nacimiento.controllers.RRFIL;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
//import java.util.Map;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
import java.util.HashMap;

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
//import org.apache.commons.collections.set.CompositeSet.SetMutator;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jettison.json.JSONArray;
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
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.acta.servicio.cliente.ActaServicioCliente;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.acta.ActaPrimigenia;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.defuncion.PermisoInhCre;
import ve.gob.cne.sarc.comunes.nacimiento.Nacimiento;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.generales.catalogo.Catalogo;
import ve.gob.cne.sarc.generales.defuncion.Defuncion;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.funcionario.FuncionarioServicio;
import ve.gob.cne.sarc.generales.reporte.GenerarActas;
import ve.gob.cne.sarc.generales.solicitud.SolicitudServicio;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.generales.util.formatDate;
import ve.gob.cne.sarc.solicitud.servicio.cliente.Constantes;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;

@RestController public class RgRecomposicionFiliacionController {

	@Autowired
	ServletContext context;
	JSONObject json = new JSONObject();
    Logger log = Logger.getLogger(getClass().getName());
    private final String rutaHtml="/resources/site/RRFIL/page/templates/";
    
    SolicitudServicio solicitud = new SolicitudServicio();
    Solicitud sol = new Solicitud();
    FuncionarioServicio funcionario = new FuncionarioServicio();
    FuncionarioServicioCliente clientefuncionario = new FuncionarioServicioCliente();
    private final String extfile=".pdf";
    private final String RUTA_PLANTILLA = "resources/site/RRFIL/page/plantillas/";
    private final String PLANTILLA_NOTIFICACION = "notificacionRegistradorFiliacion.jrxml";
    private final String PLANTILLA_ACTA="registro_de_nacimiento.jrxml";
    private final String rutaNF="/resources/site/RRFIL/page/templates/vista_notificacion_filiacion.html";
    private final String rutaImprimirActa="/resources/site/RRFIL/page/templates/imprimir_acta.html";
    private final String rutaImprimir="/resources/site/RRFIL/page/templates/imprimir_documentoPPIO.html";
    private final String rutaImprimirPV="/resources/site/RRFIL/page/templates/imprimir_documento.html";
    private final String rutaPPI="/resources/site/RRFIL/page/templates/imprimir_documentoPIA.html";
    private final String PLANTILLA_OFICIO = "Formato_ONRC_N_016.jrxml";
    private final String  rutaImp="/home/jboss/tmp/";
    private final String rutaLogo="resources/img/";
		
		SeguridadServicioCliente seguridadCliente = new SeguridadServicioCliente();
		Participante participante = new Participante();
		ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();
		Nacimiento Nac = new Nacimiento();
		ActaServicioCliente servicioActa = new ActaServicioCliente();
    
    @RequestMapping(value="/consultarParticipantes/{solicitud}", method = RequestMethod.POST)
    public List<Participante> consultarParticipantesSolicitud(@PathVariable("solicitud") String solicitud){
            
            List<Participante> participantes = new ArrayList<>();
            ParticipanteServicioCliente participanteServicioCliente = new ParticipanteServicioCliente();
            return participantes;
    }
    
    
    /**
     * Funcion que devuelve una vista y modelo de cada inicio del proceso
     * 
     * @author Ernesto Jimenez
     * @param data:
     *            contiene los datos obtenidos del rootScope
     * @return JsonObject
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/iniciarRRFIL", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody String iniciarRRFIL(@RequestBody String data, HttpServletRequest request,
          HttpSession session, HttpServletResponse response) throws Exception {
       log.info("********************Iniciando tr\u00e1amitede autenticaci\u00f3n desde generales************"+session.getId());
//        log.info("*****************************token"+request.getHeader("Authorization"));
      // ObjectMapper mapper= new ObjectMapper();
//        Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class); 
        String token=request.getHeader("Authorization");
        List<Participante> particiSolicitud;
        ObjectMapper mapper1 = new ObjectMapper();
//        if(token==null){
//           throw new GeneralException("seguridad_no_token");
//        }  
//        session.setAttribute("token", token);
       //TODO REMOVER EL COMENTARIO DE LAS SIGUIENTES 3 LINEAS CUANDO EXISTA INTEGRACION CON GPT
//        PaqueteBO paqueteBO = obtenerPaquete(Long.valueOf(data.get("id")));
//        Solicitud result = new ObjectMapper().readValue(paqueteBO.getPayload()
//              .toJSONString(), Solicitud.class);
       //TODO ELIMINAR LAS SIGUIENTES LINEAS CUANDO EXISTA INTEGRACION CON GPT
       /*SolicitudServicioCliente cSol= new SolicitudServicioCliente();
       log.info("********Consultando el ID: "+data.get("id"));
       Solicitud result = cSol.consultarDetalleSolicitud(data.get("id"));*/
       
       JSONObject modelo = new JSONObject(data);
           log.info("model: "+modelo);
       String numSolicitud = modelo.getString("id");
       
       log.info("------numSolicitud--------" + numSolicitud);
       String htmlInicio=null;
       // Funcionario datoFuncionario=clientefuncionario.buscarPorLogin(token);
       // String codRol=datoFuncionario.getUsuarios().get(0).getCodigoRol();
        
        //log.info("-----------codRol------ " +codRol);
           //Solicitud sol = solicitud.consultarDetalleSolicitud(numSolicitud);
           SolicitudServicioCliente cSol = new SolicitudServicioCliente();
           Solicitud result = cSol.consultarDetalleSolicitud(numSolicitud);
           log.info("---------> result---- " +result);


           htmlInicio = buscarHtml(result.getCodigoEstadoSolicitud());


           String htmlCompila = Util.leerArchivo(context.getRealPath(rutaHtml + htmlInicio + ".html"));
           log.info("******Contenido Html***********" + htmlCompila);

           JSONObject respuesta = new JSONObject();
           JSONObject modeloReporte =  new JSONObject();
 
           if ("PPD".equals(result.getCodigoEstadoSolicitud())) {
               // jsonA.put("titulo", "Cargar documento");
               modelo.put("titulo", "Cargar documento");
               respuesta.put("vista", htmlCompila);
               respuesta.put("modelo", modelo);
           } else if ("AB".equals(result.getCodigoEstadoSolicitud())) {
             
              particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");             
              
              for (Participante participante1 : particiSolicitud) {
                 JSONObject data1 = new JSONObject(mapper1.writeValueAsString(participante1));
                 String[] cedula = participante1.getDocumentoIdentidad().get(0).getNumero().split("-");
                              
                 log.info("----------participante1.getRol()------ " + cedula[1]);
                 
                 modelo.put(participante1.getRol(), data1);
              }
               //jsonA.put("titulo", "Cargar documento");
               modelo.put("titulo", "Solicitud de registro");
               respuesta.put("vista", htmlCompila);
               respuesta.put("modelo", modelo);
           }else if ("PEA".equals(result.getCodigoEstadoSolicitud())) {
                 // jsonA.put("titulo", "Datos del presentado");
              particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");   
              for (Participante p : particiSolicitud) {
                 JSONObject data1 = new JSONObject(mapper1.writeValueAsString(p));
                 log.info("----------p-**- " + p.getRol());

                 modelo.put(p.getRol(), data1);
             }
             modelo.put("titulo", "Datos del(de la) presentado(a)");
             respuesta.put("vista", htmlCompila);
             respuesta.put("modelo", modelo);
           } else if ("PCA".equals(result.getCodigoEstadoSolicitud())) {
               // jsonA.put(modelo);
               // jsonA.put("titulo", "Cargar documento");
               modelo.put("titulo", "Cargar documento");
               respuesta.put("vista", htmlCompila);
               respuesta.put("modelo", modelo);
           } else if ("PCDO".equals(result.getCodigoEstadoSolicitud())) {
             // jsonA.put(modelo);
             // jsonA.put("titulo", "Cargar documento");
             modelo.put("titulo", "Cargar documento");
             respuesta.put("vista", htmlCompila);
             respuesta.put("modelo", modelo);
           } else if ("PVR".equals(result.getCodigoEstadoSolicitud())) {
              log.info("--------PVRRRR----- " +result.getCodigoEstadoSolicitud());
              // jsonA.put(modelo);
              // jsonA.put("titulo", "Cargar documento");
              //modeloReporte = generarReporte(modelo.getString("id"),modelo.getString("statu"), token);
              modeloReporte = generarReporte(data, modelo.getString("id"),modelo.getString("statu"),token);

             modelo.put("titulo", modeloReporte.get("titulo"));
             respuesta.put("modelo", modelo);
             respuesta.put("vista", modeloReporte.get("vista"));
            
           }else if ("PV".equals(result.getCodigoEstadoSolicitud())) {
              log.info("--------PV INICIAR TRAMITE");
             // jsonA.put(modelo);
             // jsonA.put("titulo", "Cargar documento");
                //modelo.put("titulo", "TITULO");
                //respuesta.put("vista", htmlCompila);
                //respuesta.put("modelo", modelo);
                modeloReporte = generarReporte(data, modelo.getString("id"),modelo.getString("statu"),token);

               modelo.put("titulo", modeloReporte.get("titulo"));
               respuesta.put("modelo", modelo);
               respuesta.put("vista", modeloReporte.get("vista"));
         
          }else if ("PVO".equals(result.getCodigoEstadoSolicitud())) {
             
             // jsonA.put(modelo);
             // jsonA.put("titulo", "Cargar documento");
             //modelo.put("titulo", "TITULO");
             //respuesta.put("vista", htmlCompila);
             //respuesta.put("modelo", modelo);
             modeloReporte = generarReporte(data, modelo.getString("id"),modelo.getString("statu"),token);
      
             modelo.put("titulo", modeloReporte.get("titulo"));
             respuesta.put("modelo", modelo);
             respuesta.put("vista", modeloReporte.get("vista"));
          } else if ("NC".equals(result.getCodigoEstadoSolicitud())) {
             // jsonA.put(modelo);
             // jsonA.put("titulo", "Cargar documento");
             //modeloReporte = generarReporte(modelo.getString("id"),modelo.getString("statu"), token);
             
          //METODO POR LYNN MENESES


             modelo.put("titulo", "Ver observaciones");
             modelo.put("observaciones", result.getMotivoCambioEstado());
             respuesta.put("vista", htmlCompila);
             respuesta.put("modelo", modelo);
          } else if ("NCA".equals(result.getCodigoEstadoSolicitud())) {
             particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");   
             for (Participante p : particiSolicitud) {
                JSONObject data1 = new JSONObject(mapper1.writeValueAsString(p));
                log.info("----------p-**- " + p.getRol());

                modelo.put(p.getRol(), data1);
            }


             modelo.put("titulo", "Datos del presentado");
             modelo.put("observaciones", result.getMotivoCambioEstado());
             respuesta.put("vista", htmlCompila);
             respuesta.put("modelo", modelo);
             
             
          }


           //htmlValido = "{\"datosAPintar\":"+particiSolicitud+",\"html\":"+htmlCompila+"}";
           log.info("******JSON********" + respuesta.toString());
           return respuesta.toString();
       }
 
    
    /**
     * Funcion que devuelve el nombre de un html para mostrar 
     * 
     * @author Ernesto Jimenez
     * @param String:
     *            estaus
     * @return String
     */
    public String buscarHtml(String estatus){

       String html=null;    //PCA

           switch (estatus){
               case "AB":
                   html="datos_madre";
                   break;
               case "PPD":
                   html="cargar_documento";
                   break;
               case "PEA":
                   html="datos_presentado";
                   break;
               case "PCA":
                   html="cargar_documento";
                   break;
               case "PCDO":
                  html="cargar_documento";
                  break;
               case "PV":
                  html="vista_notificacion_filiacion";
                  break;
               case "PVO":
                   html="vista_notificacion_filiacion";
                   break;
               case "PVR":
                  html="vista_notificacion_filiacion";
                  break;
               case "NC":
                  html="ver_observaciones"; // PARA NO CONFORME REGISTRADOR CIVIL  LYNN MENESES
                  break;
               case "NCA":
                  html="datos_presentado"; // PARA NO CONFORME REGISTRADOR CIVIL  LYNN MENESES
                  break;
           }
          
       return html;
       
     }
      	
  	
    
    @RequestMapping(value="/presentarSatisfactorioRRFIL", method = RequestMethod.POST)
    public @ResponseBody String presentarSatisfactorio(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"satisfactorio.html"));

        modelo.put("mensaje", "Impresi&oacute;n exitosa");

        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    @RequestMapping(value="/cargarDocumentoRRFIL", method = RequestMethod.POST)
    public @ResponseBody String cargarDocumento(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{


        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"cargar_documento.html"));

        modelo.put("titulo", "Cargar documento");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    // PRESENTAR IMPRIMIR POR OFICIO
    @RequestMapping(value="/presentarSatisfactorioRRFILPPIO", method = RequestMethod.POST)
    public @ResponseBody String presentarSatisfactorioPPIO(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"satisfactorioPPIO.html"));

        modelo.put("mensaje", "Impresi&oacute;n exitosa");

        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    // PRESENTAR IMPRIMIR POR OFICIO
    @RequestMapping(value="/presentarImprimirRRFILPPIO", method = RequestMethod.POST)
    public @ResponseBody String presentarImprimirRRFILPPIO(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException{
      log.info("---------------------presentarImprimir -------------");
      String token=request.getHeader("Authorization");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();
        JSONObject modeloReporte = new JSONObject();

       // String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"imprimir_documentoPPIO.html"));
        sol = solicitud.consultarDetalleSolicitud((String) modelo.get("id"));
        String fechaRegistro = formatDate.convertirDateAString(sol.getFechaInicio());
        
        modeloReporte = generarReporte(data, modelo.getString("id"),modelo.getString("statu"),token);
       
        modelo.put("mensaje", "Impresi&oacute;n exitosa");
        modelo.put("fechaRegistro", fechaRegistro);
        respuesta.put("vista", modeloReporte.get("vista"));
       
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    
    
    //meTODO DE PUREBA
    @RequestMapping(value="/carga_exitosaRRFIL", method = RequestMethod.POST)
    public @ResponseBody String carga_exitosaRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{


        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"carga_exitosa.html"));
        
        //modelo.put("mensaje", "sadasdasdsa");
       // modelo.put("titulo", "Carga de documento exitosa");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
  	
  	
    @RequestMapping(value="/presentarImprimirRRFIL", method = RequestMethod.POST)
    public @ResponseBody String presentarImprimirRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException{
log.info("---------------------presentarImprimir -------------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();
        JSONObject modeloReporte =  new JSONObject();

       
        sol = solicitud.consultarDetalleSolicitud((String) modelo.get("id"));
        String fechaRegistro = formatDate.convertirDateAString(sol.getFechaInicio());
        String token=request.getHeader("Authorization");
        
        modeloReporte = generarReporte(data, modelo.getString("id"),modelo.getString("statu"),token);
       
        modelo.put("mensaje", "Impresi&oacute;n exitosa");
        modelo.put("fechaRegistro", fechaRegistro);
        respuesta.put("modelo", modelo);
        respuesta.put("vista", modeloReporte.get("vista"));
        return respuesta.toString();
    }
    
    
    
    
  	
  	////////////// a partir de aca se colocan los mapping que se encargan de colocar la ruta 
    ////////////// absoluta para las plantillas del proceso

 /**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Ernesto Jimenez
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
  	@RequestMapping(value="/RRFIL_hijo", method = RequestMethod.POST)	
	public @ResponseBody String consultarHijo(@RequestBody String data, HttpServletRequest request,HttpSession sesion, HttpServletResponse response) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
		log.info("*************RRFIL_hijo************"+sesion.getId());
		ObjectMapper mapper= new ObjectMapper();
//		Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class); 
//		String token=request.getHeader("Authorization");
//		if(token==null){
//			throw new GeneralException("seguridad_no_token");
//		}	
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_hijo_recomposicion.html"));
		log.info("******Contenido Html************"+htmlValido);
		JSONObject modelo = new JSONObject(data);
		String numSolicitud = modelo.getString("id");
//  		String htmlInicio=null;
		List<Participante> particiSolicitud;
		  particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");
//		  log.info("********participante.consultarPartiC***"+particiSolicitud);
//		JSONObject jsonA= new JSONObject();
		  //ObjectMapper mapper1= new ObjectMapper();
		  for(Participante participante1: particiSolicitud){
		     JSONObject data1 = new JSONObject(mapper.writeValueAsString(participante1)); 
		     log.info("participante1.getRol()------ " + participante1.getRol());
		   modelo.put(participante1.getRol(),data1);
		  }
		  modelo.put("titulo", "Solicitud de registro");
		  JSONObject respuesta = new JSONObject();
		  respuesta.put("vista", htmlValido);
		  respuesta.put("modelo", modelo);

		  return respuesta.toString();
	}
  	
  	
 /**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Ernesto Jimenez
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 */
  	@RequestMapping(value="/RRFIL_datos_acta", method = RequestMethod.POST)	
	public @ResponseBody String consultarDatosActa(@RequestBody String data, HttpServletRequest request,HttpSession sesion, HttpServletResponse response) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
		log.info("*************RRFIL_datos_acta************"+sesion.getId());
		//ObjectMapper mapper= new ObjectMapper();
//		Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class); 
//		String token=request.getHeader("Authorization");
//		if(token==null){
//			throw new GeneralException("seguridad_no_token");
//		}	
		String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_acta_recomposicion.html"));
		log.info("******Contenido Html************"+htmlValido);
		JSONObject modelo = new JSONObject(data);
//		String numSolicitud = modelo.getString("id");
//  		String htmlInicio=null;
//		List<Participante> particiSolicitud;
//		  particiSolicitud = this.consultarParticPorSolicitud(numSolicitud, "T");
//		  log.info("********participante.consultarPartiC***"+particiSolicitud);
//		JSONObject jsonA= new JSONObject();
//		  //ObjectMapper mapper1= new ObjectMapper();
//		  for(Participante participante1: particiSolicitud){
//		     JSONObject data1 = new JSONObject(mapper.writeValueAsString(participante1)); 
//		   jsonA.put(participante1.getRol(),data1);
//		  }
		  modelo.put("titulo", "Solicitud de registro");
		  JSONObject respuesta = new JSONObject();
		  respuesta.put("vista", htmlValido);
		  respuesta.put("modelo", modelo);
		  //htmlValido = "{\"datosAPintar\":"+particiSolicitud+",\"html\":"+htmlCompila+"}";
		  log.info("******JSON********"+respuesta.toString());
		  return respuesta.toString();
	}
  	

 /**
	 * Funcion que devuelve una lista de participantes
	 * 
	 * @author Ernesto Jimenez
	 * @param data:
	 *            contiene los datos obtenidos del rootScope
	 * @return Lista
	 */
  	public List<Participante> consultarParticPorSolicitud (String numSolicitud, String variable) throws JsonGenerationException, JsonMappingException, IOException{
      log.info("*********entrando al metodo consultarParticpante*******");

      ParticipanteServicioCliente participanteCliente = new ParticipanteServicioCliente();
      List<Participante> participante;
   	  participante=participanteCliente.consultarParticPorSolicitud(numSolicitud, variable);
      
         return participante;
     }
  	
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Hector Zea
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/datosCertificadoRRFIL", method = RequestMethod.POST)
    public @ResponseBody String datosCertificadoRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos certificado--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();
        
        if("NCA".equals(modelo.getString("statu"))){
           JSONObject CMN = new JSONObject();
           DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
           
           log.info("------------->ESNTRO A CONSULTAR CERTIFICADO PA PINTAR");
           Nacimiento datosCertNac = this.consultarNacimientoRRFIL(modelo.getString("id"));
           CMN.put("numCert", datosCertNac.getNumeroCertificado());
           CMN.put("primerNombre", datosCertNac.getPrimerNombreMedico());
           CMN.put("segundoNombre", datosCertNac.getSegundoNombreMedico());
           CMN.put("primerApellido", datosCertNac.getPrimerApellidoMedico());
           CMN.put("segundoApellido", datosCertNac.getSegundoApellidoMedico());
           CMN.put("numMPPS", datosCertNac.getNuMPPS());
           CMN.put("centroSalud", datosCertNac.getCentroSalud());

           Date fechaCert = datosCertNac.getFechaCertificado();
           String outputText = outputFormat.format(fechaCert);

           CMN.put("fechaCert", outputText);           
           
           modelo.put("CMN", CMN);
           respuesta.put("modelo", modelo);
           log.info("--------->JSON RESPUESTA "+respuesta.toString());
        }

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_certificado_medico.html"));     
        modelo.put("titulo","Datos del certificado");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Hector Zea
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/datosMadreRRFIL", method = RequestMethod.POST)
    public @ResponseBody String datosMadreRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos madre--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_madre2.html"));     
        modelo.put("titulo","Datos de la madre");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Hector Zea
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/datosDeclaranteRRFIL", method = RequestMethod.POST)
    public @ResponseBody String datosDeclaranteRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos declarante--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_declarante.html"));     
        modelo.put("titulo","Datos del declarante");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Hector Zea
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/datosTestigoRRFIL", method = RequestMethod.POST)
    public @ResponseBody String datosTestigoRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos declarante--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_testigos.html"));     
        modelo.put("titulo","Datos de los testigos");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Hector Zea
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/decisionJudicialRRFIL", method = RequestMethod.POST)
    public @ResponseBody String decisionJudicialRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos decision judicial--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"inscripcion_decision_judicial.html"));     
        modelo.put("titulo","Decision judicial");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Hector Zea
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/circunstanciaEspecialRRFIL", method = RequestMethod.POST)
    public @ResponseBody String circunstanciaEspecialRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos decision judicial--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"circunstancias_especiales_acto.html"));     
        modelo.put("titulo","Circunstancias especiales");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
    
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Hector Zea
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/documentosPresentadosRRFIL", method = RequestMethod.POST)
    public @ResponseBody String documentosPresentadosRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

    	log.info("--------datos decision judicial--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"documentos_presentados.html"));     
        modelo.put("titulo","Documentos presentados");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
	
	    @RequestMapping(value="/verificacionDeclarante", method = RequestMethod.POST)
    public @ResponseBody String verificacionDeclarante(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
    	
    	log.info("--------datos --------verificacion del declarante-------");
    	JSONObject modelo = new JSONObject(data);
    	JSONObject respuesta = new JSONObject();
    	
    	String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"verificacion_declarante.html"));     
    	modelo.put("titulo","Certificacion del registrador");
    	respuesta.put("vista", vista);
    	respuesta.put("modelo", modelo);
    	return respuesta.toString();
    }
    
    
    //ACTUALIZA EL ESTADO
   @SuppressWarnings("static-access")
    @RequestMapping(value="/actualizarEstadoRRFIL", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Solicitud actualizarEstatuSolicitud(@RequestBody String data, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{

          log.info("data"+data);
          log.info("dataXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        JSONObject dataModelo = new JSONObject(data); //Util.getDataFromRequest(request);
         // log.info("modelo"+dataModelo.modelo);
          log.info("ID SOLICITUD"+dataModelo.getString("id"));

        String parametro=dataModelo.getString("statu");
        log.info("capturando estatus"+parametro);
        log.info("----dataModelo.getString()--- "+dataModelo.getString("id"));

        Solicitud sol = solicitud.consultarDetalleSolicitud(dataModelo.getString("id"));
        Solicitud  solicitudSesion= new Solicitud();

        if(("Pendiente por imprimir").equals(parametro)){
         log.info("----dataModelo.getString()--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX- ");
         solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO);
            solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOCUMENTO");
       
        }else if(("PPD").equals(parametro)){
           log.info("----dataModelo.getString()-------------------cambiar a PENDIENTE_POR_ELABORAR_ACTA------------- ");
           solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
           solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
        //    solicitudSesion.setEstadoSolicitud("PPIO");
           solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");      
         
          
          }else if(("Pendiente por imprimir oficio").equals(parametro)){
         log.info("----dataModelo.getString()-------------------cambiar a PENDIENTE CARGAR DOCUMENTO OFICIO------------- ");
         solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
         solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOC_OFICIO);
        //  solicitudSesion.setEstadoSolicitud("PCDO");
         solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOC_OFICIO");
              
           
        }else if(("PCA").equals(parametro)){
            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_OFICIO);
            solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_OFICIO");
            
        }else if(("PCDO").equals(parametro)){
         log.info("----dataModelo.getString()-----------------cambiar a PENDIENTE POR REGISTRAR NOTA MAGISTRAL-------------------- ");
         solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
         solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL);
         solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL");
         
        }else if(("PVR").equals(parametro) && ("conforme").equals(dataModelo.get("permiso1"))){
    	log.info("----dataModelo.getString()-----------------cambiar a Pendiente por imprimir-------------------- ");
    	solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
    	solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR_ACTA);
    	solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_IMPRIMIR_ACTA");
        
        }else if(("PVR").equals(parametro) && ("noConforme").equals(dataModelo.get("permiso1"))){
    	log.info("----dataModelo.getString()-----------------cambiar a Pendiente POR ELABORAR ACTA-------------------- ");
    	solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
    	solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_ELABORAR_ACTA);
    	solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_ELABORAR_ACTA");
    	
		}else if(("PV").equals(parametro)){
              String verifica=dataModelo.getString("permiso"); String estadoSoli=null;String cambioEst=null;
            switch (verifica){
                case "conforme":
                    estadoSoli=Constantes.PENDIENTE_POR_IMPRIMIR;
                    cambioEst="PENDIENTE_POR_IMPRIMIR";
                    break;
                case "noConforme":
                    estadoSoli=Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL;
                    cambioEst=dataModelo.getString("observaciones");
                    break;
            }
            log.info("****estado: "+estadoSoli);
            log.info("****cambio: "+cambioEst);
            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(estadoSoli);
            solicitudSesion.setMotivoCambioEstado(cambioEst);
		}else if (("PEA").equals(parametro)) {

		   String verificar = dataModelo.getString("permiso1");
		   String estadoSolic = null;
		   String cambioEsta = null;
		   switch (verificar) {
		   case "conforme":
		      estadoSolic = Constantes.PENDIENTE_POR_VERIFICAR_RC_ACTA;
		      cambioEsta = "CAMBIO DE ESTADO";
		      break;
		   case "noConforme":
		      estadoSolic = Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL;
		      cambioEsta = dataModelo.getString("observaciones");
		      break;
		   }
		   log.info("****estado: " + estadoSolic);
		   log.info("****cambio: " + cambioEsta);
		   solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
		   solicitudSesion.setEstadoSolicitud(estadoSolic);
		   solicitudSesion.setMotivoCambioEstado(cambioEsta);
		} else if (("NC").equals(parametro)) {
            log.info("capturando id y status"+parametro+"**"+dataModelo.getString("id"));
            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
            solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");
        } else if(("PVO").equals(parametro) && ("conforme").equals(dataModelo.get("permiso"))){
            log.info("-------cambiar a Pendiente por imprimir: "+dataModelo.get("permiso"));
            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR_OFICIO);
            solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_IMPRIMIR_OFICIO");

        } else if(("AB").equals(parametro)){
           solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
           solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
           solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");

        }else if(("PPI").equals(parametro)){
           log.info("----dataModelo.getString()-------------------cambiar a PENDIENTE CARGAR DOCUMENTO OFICIO------------- ");
           solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
           solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOC_ACTA); 
           solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOC_ACTA");

       } else if (("NCA").equals(parametro)) {
          log.info("capturando id y status"+parametro+"**"+dataModelo.getString("id"));
          solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
          solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR_ACTA);
          solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_IMPRIMIR_ACTA");
          
       }
        
        SolicitudServicio solicitudActualizar = new SolicitudServicio();
        Solicitud soli = solicitudActualizar.actualizaEstadoSolicitud(solicitudSesion);
        return soli;
}
   
  	
   @RequestMapping(value = "/existeActaRRFIL/{numActa}", method = RequestMethod.GET)
   public @ResponseBody Map<String, String> validarActa(@RequestParam(value = "numActa", required = false) String numActa) {
      log.info("PRUEBAAAAAA*******//// ");
      ActaServicioCliente servicioCliente = new ActaServicioCliente();
      Map<String, String> resp = servicioCliente.existeActa(numActa);
      log.info("PRUEBAAAAAA******* "+resp);
      return resp;
   }

   
  	
    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Hector Zea
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/RRFIL_validarRecaudo", method = RequestMethod.POST)
    public @ResponseBody String RRFIL_validarRecaudo(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

      log.info("--------datos decision judicial--------");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"validar_recaudos.html"));     
        modelo.put("titulo","Validar recaudos");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
  	
	@RequestMapping(value = "presentarPdf", method = RequestMethod.POST)
    public @ResponseBody String presentarPdf(@RequestBody String data,HttpSession session,HttpServletRequest request)throws JRException,GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
        JSONObject respuesta = new JSONObject();
        JSONObject modelo = new JSONObject(data);
        JSONObject modeloReporte = new JSONObject();
        log.info("id: "+modelo.getString("id"));
        log.info(" statu: "+modelo.getString("statu"));
      //  modeloReporte=generarReporte(modelo.getString("id"),modelo.getString("statu"));

      //  modelo.put("titulo",reporte.get("tituloDoc"));
        respuesta.put("modelo",modelo);
      //  respuesta.put("vista",reporte.get("vista"));
        return respuesta.toString();
    }
  
	
	    @RequestMapping(value="/validarReporteConforme", method = RequestMethod.POST)
    public @ResponseBody String validarReporteConforme(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException{

        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"verificacion_registrador.html"));


        modelo.put("titulo", "Certificacion del registrador");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
  	
  		@RequestMapping(value = "/vistaPreviaActaRRFIL", method = RequestMethod.POST)
	public @ResponseBody String vistaPreviaActaRRFIL(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException {
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
		// ObjectMapper mapper= new ObjectMapper();
		// Map<String, String> data =
		// mapper.readValue(request.getHeader("datos"), Map.class);
		// String token=request.getHeader("Authorization");
		// if(token==null){
		// throw new GeneralException("seguridad_no_token");
		// }
		//String token=request.getHeader("Authorization");
		//JSONObject modeloReporte = generarReporteActa((String) modelo.get("id"), (String) modelo.get("estatu"), token);

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "vista_previa.html"));
		modelo.put("titulo", "Vista previa acta");
		//respuesta.put("vista", modeloReporte.get("vista"));
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
		@RequestMapping(value = "/RRFIL_imprimirActa", method = RequestMethod.POST)
	public @ResponseBody String RRFIL_imprimirActa(@RequestBody String data, HttpSession sesion,
			HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException {
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
		////////////acabo de modificar esooo....
		String token=request.getHeader("Authorization");
		
		//JSONObject modeloReporte = generarReporteActa((String) modelo.get("id"), (String) modelo.get("estatu"), token, modelo.getJSONObject("data"));
		JSONObject modeloReporte = generarReporte(data, (String) modelo.getString("id"), (String) modelo.getString("statu"), token);
	//	String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "imprimir_acta.html"));
		//log.info("******Contenido Html************" + vista);
		modelo.put("titulo", "Vista previa");
		respuesta.put("vista", modeloReporte.get("vista"));
		//respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);

		return respuesta.toString();
	}
	
		/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Hector Zea
	 * @param request
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	@RequestMapping(value = "/verificacionConformeRRFIL", method = RequestMethod.POST)
	public @ResponseBody String verificacionConformeRRFIL(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException,
			IOException, JSONException {

		log.info("--------datos conforme verificacion--------");
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml
				+ "verificacion_declarante.html"));
		modelo.put("titulo", "Verificacion del declarante");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
  	
	
		/**
	 * Funcion que devuelve una vista y modelo a presentar
	 * 
	 * @author Hector Zea
	 * @param request
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
	@RequestMapping(value = "/finalizarTramiteRRFIL", method = RequestMethod.POST)
	public @ResponseBody String finalizarTramiteRRFIL(@RequestBody String data,
			HttpSession sesion, HttpServletRequest request)
			throws GeneralException, JsonParseException, JsonMappingException,
			IOException, JSONException {

		log.info("--------datos conforme verificacion--------");
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();

		String vista = Util.leerArchivo(context.getRealPath(rutaHtml
				+ "satisfactorioActaRRFIL.html"));
		modelo.put("titulo", "");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
	
	
	
	@SuppressWarnings("null")
   public JSONObject generarReporte(String data, String modelo, String estatus, String token)throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException, ParseException {

	   log.info("----------------> generarReporte" +estatus);
	   HashMap<String, Object> datosAPintar = new HashMap<String, Object>();

     JSONObject formulario=new JSONObject();

      String rutaFin = null;
      String rutaPlantilla = null;
      String rutaVista = null;

      String vista = null;
      
      switch (estatus) {
     
      case "AB":
         datosAPintar = this.datosPDFActa(data, modelo, token);
         break;
         
      case "PV":
         datosAPintar = this.datosPDFNotificacionAlRegistrador(modelo, token);
         break;
         
      case "Pendiente por imprimir":
         datosAPintar = this.datosPDFNotificacionAlRegistrador(modelo, token);
         break;
         
      case "PEA":
         datosAPintar = this.datosPDFActa(data, modelo, token);
         break;
         
      case "PVR":
         datosAPintar = this.datosPDFActa(data, modelo, token);
         break;
         
      case "PPI":
         datosAPintar = this.datosPDFActa(data, modelo, token);
         break;
         
      case "PVO":
         datosAPintar = this.datosPDFOficioMinisterio(modelo, token);
         break;
         
      case "Pendiente por imprimir oficio":
         datosAPintar = this.datosPDFOficioMinisterio(modelo, token);
         break;
      
      case "NCA":
         datosAPintar = this.datosPDFActa(data, modelo, token);
         break;

      }      
     
      
      rutaPlantilla = this.obtenerRutaPlantilla(estatus);
      
      log.info("-rutaPlantilla--------> "+rutaPlantilla);
     
      rutaFin = rutaImp+""+modelo+""+extfile;

      JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla);

      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar,  new JREmptyDataSource());

      JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);

       String template="<iframe   id='plugin' width='800'  height='800' src='"+ "/web-generales/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
       
       rutaVista = this.obtenerRutavista(estatus);

      vista = Util.leerArchivo(context.getRealPath(rutaVista));
      log.info("-------vista------> "+vista);
      
       vista = vista.replace("ARCHIVOPDF", template);
       
       String tituloDoc = this.obtenerTituloDocumentoPDF(estatus);
       
      
      formulario.put("vista", vista);
      formulario.put("titulo", tituloDoc);
      return formulario;
  }
	
	private String obtenerTituloDocumentoPDF(String estatus){
	   String tituloDoc = null;
	   switch (estatus) {
      case "PV":
         tituloDoc = "Notificacion al registrador(a) civil";
         break;
      case "PVO":
         tituloDoc = "Oficio al Ministerio Publico";
         break;
      case "PVR":
         tituloDoc = "Acta de nacimiento";
         break;
  
      }
	   
	   
	   return tituloDoc;
	}
	
	
	public String obtenerRutavista(String estatu){
	   String rutaVista = null;
	   switch (estatu) {
      case "AB":
         rutaVista = rutaNF;//FALSTA VISTA
         break;
      case "PV":
         rutaVista = rutaNF;
         break;
      case "Pendiente por imprimir":
         rutaVista = rutaImprimirPV;
         break;
      case "PEA":
         rutaVista = rutaImprimirActa;
         break;
      case "PVR":
         rutaVista = rutaNF;
         log.info("---------rutaPVR----> "+rutaVista);
         break;
      case "PVO":
         rutaVista = rutaNF;
         break;
      case "Pendiente por imprimir oficio":
         rutaVista = rutaImprimir;
         break;
      case "NCA":
         rutaVista = rutaImprimirActa;
         break;
      case "PPI":
         rutaVista = rutaPPI;
         break;

      }
	   
	   return rutaVista;	   
	}
	
	public String obtenerRutaPlantilla(String estatu){
	   String rutaPlantilla = null;
	   
	   
	   switch (estatu) {
      case "AB" :
         rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_ACTA);
         break;
      case "PV":
         rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_NOTIFICACION);
         break;
      case "Pendiente por imprimir":
         rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_NOTIFICACION);
         break;
      case "PEA":
         rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_ACTA);
         break;
      case "PVR":
         rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_ACTA);
         log.info("---rutaPlantilla---> "+rutaPlantilla);
         break;
      case "PVO":
         rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_OFICIO);
         break;
      case "Pendiente por imprimir oficio":
         rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_OFICIO);
         break;
      case "NCA":
         rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_ACTA);
         break;
      }
	   
	   return rutaPlantilla;
	}
	
	public HashMap<String, Object> datosPDFOficioMinisterio(String modelo, String token) throws JsonGenerationException, JsonMappingException, IOException{
	   HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
	   
	   String RutaIMAGE=context.getRealPath(rutaLogo);

      String login = seguridadCliente.getUsernameCliente(token);
      FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
      Funcionario obtenerDatosFuncionario = servFuncionario.buscarPorLogin(login);
      String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
      String codTipoFuncionario = "RP";   
      String codEstatusFuncionario = "ACT";
      Funcionario dataServicio = new Funcionario();
      dataServicio = clientefuncionario.buscarFuncionarioPorOficina(codOficina, codTipoFuncionario, codEstatusFuncionario);
      Oficina datosOficina = new Oficina();
      datosOficina = obtenerDatosFuncionario.getOficina();
      String fechaResolucion = formatDate.convertirDateAString(dataServicio.getFechaResolucion());
      String fechaGaceta = formatDate.convertirDateAString(dataServicio.getFechaGaceta());
      List<Participante> obtenerDatosRF = new ArrayList<Participante>();
      String[] codTipo = {"HIJ"};
      obtenerDatosRF = participanteCliente.consultarParticPorTipo(modelo, codTipo);
      String PrimerNombreHIJ= null;
      PrimerNombreHIJ = obtenerDatosRF.get(0).getPrimerNombre();
      String SegundoNombreHIJ = null;
      SegundoNombreHIJ = obtenerDatosRF.get(0).getSegundoNombre()==null?" ": obtenerDatosRF.get(0).getSegundoNombre();

      String ApellidoHIJ = null;
      ApellidoHIJ = obtenerDatosRF.get(0).getPrimerApellido();
      String SegundoApellidoHIJ = null;
      SegundoApellidoHIJ = obtenerDatosRF.get(0).getSegundoApellido()==null?" ": obtenerDatosRF.get(0).getSegundoApellido();
      
      ActaPrimigenia datosActa = this.consultarDatosActaRRFIL(modelo);
      String FInscripcion = datosActa.getFechaIncripcion();
      
      //datosAPintar.put("nombreRegistrador", StringUtils.capitalize(obtenerDatosFuncionario.getPrimerNombre().toLowerCase())+' '+StringUtils.capitalize(obtenerDatosFuncionario.getPrimerApellido().toLowerCase()));
      datosAPintar.put("oficinaRegistro", datosOficina.getNombre().toUpperCase());
      
      datosAPintar.put("nResolucion", String.valueOf(dataServicio.getNumeroResolucion()));
     
      datosAPintar.put("fResolucion", fechaResolucion);
      
      datosAPintar.put("nGaceta", String.valueOf(dataServicio.getNumeroGaceta()));
     
      datosAPintar.put("fGaceta", fechaGaceta);
	   
	   datosAPintar.put("nActa",datosActa.getNumeroActa());
      datosAPintar.put("numero",String.valueOf(datosActa.getNumeroConsecutivo()));
      datosAPintar.put("fechaActaNacimiento",FInscripcion);
      datosAPintar.put("nombreNacimiento",PrimerNombreHIJ+' '+SegundoNombreHIJ+' '+ApellidoHIJ+' '+SegundoApellidoHIJ);
      datosAPintar.put("rutaImg",RutaIMAGE);
      datosAPintar.put("fechaActual",obtenerDatosFuncionario.getOficina().getDireccion().getEstado().getNombre()+", " +GenerarActas.obtenerFechaOrHoraActual("DIA")+" de "+GenerarActas.obtenerFechaOrHoraActual("STRING_MES")+" del "+GenerarActas.obtenerFechaOrHoraActual("ANIO"));
      
      
	   return datosAPintar;
	}
	
	public HashMap<String, Object> datosPDFNotificacionAlRegistrador(String modelo, String token) throws JsonGenerationException, JsonMappingException, IOException{
	   log.info("-----pintar datos----");
	   String RutaIMAGE=context.getRealPath(rutaLogo);

	   String login = seguridadCliente.getUsernameCliente(token);
      FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
      Funcionario obtenerDatosFuncionario = servFuncionario.buscarPorLogin(login);
      String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
      String codTipoFuncionario = "RP";   
      String codEstatusFuncionario = "ACT";
      Funcionario dataServicio = new Funcionario();
      dataServicio = clientefuncionario.buscarFuncionarioPorOficina(codOficina, codTipoFuncionario, codEstatusFuncionario);
      Oficina datosOficina = new Oficina();
      datosOficina = obtenerDatosFuncionario.getOficina();
      String fechaResolucion = formatDate.convertirDateAString(dataServicio.getFechaResolucion());
      String fechaGaceta = formatDate.convertirDateAString(dataServicio.getFechaGaceta());
      List<Participante> obtenerDatosRF = new ArrayList<Participante>();
      String[] codTipo = {"HIJ"};
      obtenerDatosRF = participanteCliente.consultarParticPorTipo(modelo, codTipo);
      String PrimerNombreHIJ= null;
      PrimerNombreHIJ = obtenerDatosRF.get(0).getPrimerNombre();
      String SegundoNombreHIJ = null;
      SegundoNombreHIJ = obtenerDatosRF.get(0).getSegundoNombre()==null?" ": obtenerDatosRF.get(0).getSegundoNombre();

      String ApellidoHIJ = null;
      ApellidoHIJ = obtenerDatosRF.get(0).getPrimerApellido();
      String SegundoApellidoHIJ = null;
      SegundoApellidoHIJ = obtenerDatosRF.get(0).getSegundoApellido()==null?" ": obtenerDatosRF.get(0).getSegundoApellido();
      
      ActaPrimigenia datosActa = this.consultarDatosActaRRFIL(modelo);
      String FInscripcion = datosActa.getFechaIncripcion();
      log.info("-----FECHA CONSULTA---- " + FInscripcion);
      
      HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
      
      datosAPintar.put("nombreRegistrador", StringUtils.capitalize(obtenerDatosFuncionario.getPrimerNombre().toLowerCase())+' '+StringUtils.capitalize(obtenerDatosFuncionario.getPrimerApellido().toLowerCase()));
      log.info("-----nombreRegistrador---- " + StringUtils.capitalize(obtenerDatosFuncionario.getPrimerNombre().toLowerCase())+' '+StringUtils.capitalize(obtenerDatosFuncionario.getPrimerApellido().toLowerCase()));
      datosAPintar.put("oficinaRegistradora", datosOficina.getNombre().toUpperCase());
      log.info("-----oficinaRegistradora---- " + datosOficina.getNombre().toUpperCase());
      datosAPintar.put("nResolucion", String.valueOf(dataServicio.getNumeroResolucion()));
      log.info("-----nResolucion---- " + String.valueOf(dataServicio.getNumeroResolucion()));
      datosAPintar.put("fResolucion", fechaResolucion);
      log.info("-----fResolucion---- " + fechaResolucion);
      datosAPintar.put("nGaceta", String.valueOf(dataServicio.getNumeroGaceta()));
      log.info("-----nGaceta---- " + String.valueOf(dataServicio.getNumeroGaceta()));
      datosAPintar.put("fGaceta", fechaGaceta);
      log.info("-----fGaceta---- " + fechaGaceta);
      datosAPintar.put("nConsecutivo", String.valueOf(datosActa.getNumeroConsecutivo()));
      log.info("-----nConsecutivo---- " + datosActa.getNumeroConsecutivo());
      datosAPintar.put("numActa", datosActa.getNumeroActa());
      log.info("-----numActa---- " + datosActa.getNumeroActa());
      datosAPintar.put("fechaActa", FInscripcion);
      log.info("-----fechaActa---- " + FInscripcion);
      datosAPintar.put("nombreApellidoAdoptado", PrimerNombreHIJ+' '+SegundoNombreHIJ+' '+ApellidoHIJ+' '+SegundoApellidoHIJ);
      log.info("-----nombreApellidoAdoptado---- " + PrimerNombreHIJ+' '+SegundoNombreHIJ+' '+ApellidoHIJ+' '+SegundoApellidoHIJ);
      datosAPintar.put("oficinaNacimiento", datosActa.getNombreOficina());
      log.info("-----oficinaNacimiento---- " + datosActa.getNombreOficina());
      datosAPintar.put("rutaImg",RutaIMAGE);
      datosAPintar.put("fechaActual",obtenerDatosFuncionario.getOficina().getDireccion().getEstado().getNombre()+", " +GenerarActas.obtenerFechaOrHoraActual("DIA")+" de "+GenerarActas.obtenerFechaOrHoraActual("STRING_MES")+" del "+GenerarActas.obtenerFechaOrHoraActual("ANIO"));
      
      
      return datosAPintar;
	}
	
	public HashMap<String, Object> datosPDFActa(String data, String modelo, String token) throws JsonGenerationException, JsonMappingException, IOException, JSONException, ParseException{
	   log.info("------------> datosPDFActa");
      HashMap<String, Object> datosAPintar = this.datosPDFNotificacionAlRegistrador(modelo, token);
      JSONObject obj = new JSONObject(data);

      String hijo = new String(obj.getString("HIJ"));
      JSONObject HIJO = new JSONObject(hijo);
      //--------
      String pnombreHijo = String.valueOf(HIJO.get("primerNombre"));
      String snombreHijo = (String.valueOf(HIJO.get("segundoNombre")) == null) ? " " : String.valueOf(HIJO.get("segundoNombre"));
      String papeHijo = String.valueOf(HIJO.get("primerApellido"));
      String sapeHijo = (String.valueOf(HIJO.get("segundoApellido")) == null) ? " " : String.valueOf(HIJO.get("segundoApellido"));
      //String nombreCompletoHijo = pnombreHijo + " " + snombreHijo + ", " + papeHijo + " " + sapeHijo;
      String sexoHijo=String.valueOf(HIJO.get("sexo"));
      String direccionHijo=String.valueOf(HIJO.getJSONArray("direccion").getJSONObject(0).get("ubicacion"));
      //-------------------
      String juez = new String(obj.getString("IDJ"));
      JSONObject JUEZ = new JSONObject(juez);
      String pnombreJuez = String.valueOf(JUEZ.get("primerNombre"));
      String papeJuez = String.valueOf(JUEZ.get("primerApellido"));
      String nomCompJuez = StringUtils.capitalize(pnombreJuez) + " " + StringUtils.capitalize(papeJuez);

      String tribunal=String.valueOf(JUEZ.get("tribunal"));
      String sentencia=String.valueOf(JUEZ.get("sentencia"));
      //-------------------------------------------------
      String madre = new String(obj.getString("MAD"));
      JSONObject MAD = new JSONObject(madre);
      String pnombreMadre = String.valueOf(MAD.get("primerNombre"));
      String snombreMadre = (String.valueOf(MAD.get("segundoNombre")) == null) ? " " : String.valueOf(MAD.get("segundoNombre"));
      String papeMadre = String.valueOf(MAD.get("primerApellido"));
      String sapeMadre = (String.valueOf(MAD.get("segundoApellido")) == null) ? " " : String.valueOf(MAD.get("segundoApellido"));
      String profesionMadre=String.valueOf(MAD.get("profesion"));
      String edadMadre=String.valueOf(MAD.get("Edad"));
      String tipoDocMadre=String.valueOf(MAD.get("tipoDocumento"));
      String cedulaMad=String.valueOf(MAD.getJSONArray("documentoIdentidad").getJSONObject(0).get("numero"));
      String tipoDocuMadre = String.valueOf(MAD.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").get("nombre"));
      String cedulaMadre=cedulaMad.substring(2);
      String nacionalidadMadre=null;
      String residenciaMadre=String.valueOf(MAD.getJSONArray("direccion").getJSONObject(0).get("ubicacion"));
      //-------------------------------------------------
      String cert = new String(obj.getString("CMN"));
      JSONObject CERT = new JSONObject(cert);
      String numCert=String.valueOf(CERT.get("numCert"));
      String centroSaludCert=String.valueOf(CERT.get("centroSalud"));
      String mppsCert=String.valueOf(CERT.get("numMPPS"));
      String pnomCert=String.valueOf(CERT.get("primerNombre"));
      String papeCert=String.valueOf(CERT.get("primerApellido"));
      String autoridadCert=StringUtils.capitalize(pnomCert)+" "+StringUtils.capitalize(papeCert);
      //-------------------------------------------------
      if(tipoDocMadre.equals("V")){
          nacionalidadMadre="Venezolana";
      }else{
          nacionalidadMadre="Extranjera";
      }
      //-----------------------testtigo 1 y 2------------
      String t1 = new String(obj.getString("TA1"));
      JSONObject T1 = new JSONObject(t1);
      String pnombreT1 = String.valueOf(T1.get("primerNombre"));
      String snombreT1 = (String.valueOf(T1.get("segundoNombre")) == null) ? " " : String.valueOf(T1.get("segundoNombre"));
      String papeT1 = String.valueOf(T1.get("primerApellido"));
      String sapeT1 = (String.valueOf(T1.get("segundoApellido")) == null) ? " " : String.valueOf(T1.get("segundoApellido"));
      String profesionT1=String.valueOf(T1.get("profesion"));
      String edadT1=String.valueOf(T1.get("edad"));
      String tipoDocT1=String.valueOf(T1.get("tipoDocumento"));
      String cedulaT1=String.valueOf(T1.getJSONArray("documentoIdentidad").getJSONObject(0).get("numero"));
      //String tipoDocuMadre = String.valueOf(MAD.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").get("nombre"));
      //String cedulaMadre=cedulaMad.substring(2);
      String nacionalidadT1=null;
      String residenciaT1=String.valueOf(T1.getJSONArray("direccion").getJSONObject(0).get("ubicacion"));
      if(tipoDocT1.equals("V")){
          nacionalidadT1="Venezolana";
      }else{
          nacionalidadT1="Extranjera";
      }
      //--------------------------------------------------------
      String t2 = new String(obj.getString("TA2"));
      JSONObject T2 = new JSONObject(t2);
      String pnombreT2 = String.valueOf(T2.get("primerNombre"));
      String snombreT2 = (String.valueOf(T2.get("segundoNombre")) == null) ? " " : String.valueOf(T2.get("segundoNombre"));
      String papeT2 = String.valueOf(T2.get("primerApellido"));
      String sapeT2 = (String.valueOf(T2.get("segundoApellido")) == null) ? " " : String.valueOf(T2.get("segundoApellido"));
      String profesionT2=String.valueOf(T2.get("profesion"));
      String edadT2=String.valueOf(T2.get("edad"));
      String tipoDocT2=String.valueOf(T2.get("tipoDocumento"));
      String cedulaT2=String.valueOf(T2.getJSONArray("documentoIdentidad").getJSONObject(0).get("numero"));
      //String tipoDocuMadre = String.valueOf(MAD.getJSONArray("documentoIdentidad").getJSONObject(0).getJSONObject("tipoDocumento").get("nombre"));
      //String cedulaMadre=cedulaMad.substring(2);
      String nacionalidadT2=null;
      String residenciaT2=String.valueOf(T2.getJSONArray("direccion").getJSONObject(0).get("ubicacion"));
      if(tipoDocT2.equals("V")){
          nacionalidadT2="Venezolana";
      }else{
          nacionalidadT2="Extranjera";
      }
      //-------------------------------------------------
      ///String fecha
      String diaHIJO = null;
      String mesHIJO = null;
      String annoHIJO = null;
      String horaHIJO = null;
      String fechanac=HIJO.getString("fechaNacimiento");
      String fechaJuez=JUEZ.getString("fecha");
      String formato = null;
      try{
          formato = obj.getString("formato2");
      }catch (Exception e)  {}

      //------------------------
      String diaJuez=null;
      String mesJuez=null;
      String annoJuez=null;

     // String diaJuez=String.valueOf(JUEZ.getJSONObject("fecha").get("dia2"));
      //String mesJuez=String.valueOf(JUEZ.getJSONObject("fecha").getJSONObject("mes2").get("nombre"));
      //String annoJuez=String.valueOf(JUEZ.getJSONObject("fecha").get("ano2"));
      //------------------------

      DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");


      DateFormat outputFormat1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
      DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
      //String  hor=String.valueOf(HIJO.getJSONArray("horaNacimiento").get(0));
      //String  min=String.valueOf(HIJO.getJSONArray("horaNacimiento").get(1));
      String  hor=String.valueOf(HIJO.getJSONObject("horaNacimiento").get("hora"));
      String  min=String.valueOf(HIJO.getJSONObject("horaNacimiento").get("minuto"));


            horaHIJO=hor+":"+min;
      //horaHijo=HIJO.getJSONArray();
      log.info("----hour : "+horaHIJO);
      log.info("----date : "+fechanac);
      Date date = new Date();
      try{
          DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
          date = inputFormat.parse(fechanac);
          //Date date = inputFormat.parse(fechanac);

          //horaHIJO = anno[1];
          log.info("-----anio: "+annoHIJO);
      }catch(Exception p){
          try{
              long val = Long.parseLong(fechanac);
              DateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
              date = inputFormat.parse(fechanac);
          }catch (Exception e){}
      }
      String outputText = outputFormat.format(date);
      String[] arrayFecha = outputText.split("/");
      String[] anno = arrayFecha[2].split(" ");

      diaHIJO = arrayFecha[0];
      mesHIJO = arrayFecha[1];
      annoHIJO = anno[0];

      try{

          Date date1 = inputFormat1.parse(fechaJuez);
          String outputText1 = outputFormat1.format(date1);
          String[] arrayFecha1 = outputText1.split("/");
          String[] anno1 = arrayFecha1[2].split(" ");

          diaJuez = arrayFecha1[0];
          mesJuez = arrayFecha1[1];
          annoJuez = anno1[0];

          log.info("-----anio: "+annoJuez);
      }catch(Exception p){
          //}catch(Exception p){
      }

        String circunstancia=null;
        String observaciones=null;
        String docPresentados=null;
        //--------------nuevos cambios anderson delgado--fecha 27-10-2016---inicio--
        try{
            circunstancia=StringUtils.capitalize(obj.getString("observaciones"));
        }catch (Exception e){

        }
        try{
            observaciones=StringUtils.capitalize(obj.getString("observacionesEsp"));
        }catch (Exception e){

        }
        try{
            docPresentados=StringUtils.capitalize(obj.getString("observacionesDoc"));
        }catch (Exception e){

      }
      String paisHijo=String.valueOf(HIJO.getJSONArray("direccion").getJSONObject(0).get("pais"));
      String estadoHijo="";
      String municipioHijo="";
      String parroquiaHijo="";
      try{

          estadoHijo=String.valueOf(HIJO.getJSONArray("direccion").getJSONObject(0).get("estado"));
          municipioHijo=String.valueOf(HIJO.getJSONArray("direccion").getJSONObject(0).get("municipio"));
          parroquiaHijo=String.valueOf(HIJO.getJSONArray("direccion").getJSONObject(0).get("parroquia"));
      }catch (Exception e){

      }

      String direccionEMPHijo=estadoHijo+" "+municipioHijo+" "+parroquiaHijo;
      log.info("validando si el dato es lleno o vacio direccion: "+direccionEMPHijo);
      //-------------------------------------------------
      //------fecha juez ----
      //String diaj=JUEZ.getJSONObject("fecha").getString("dia2");
      //String mesj=JUEZ.getJSONObject("fecha").getJSONObject("mes2").getString("nombre");
      //String annoj=JUEZ.getJSONObject("fecha").getString("ano2");
      String fechaj=String.valueOf(diaJuez+"/"+mesJuez+"/"+annoJuez);

      //-------------------
      String RutaIMAGE = context.getRealPath(rutaLogo);
      datosAPintar.put("numero", "----");
      datosAPintar.put("nombreCiudadano", "----");
      //---------------------hijo--------------------------
      datosAPintar.put("diaNacimientoPresentado",diaHIJO);
      datosAPintar.put("mesNacimientoPresentado",mesHIJO);
      datosAPintar.put("annoNacimientoPresentado",annoHIJO);
      datosAPintar.put("horaPresentado",horaHIJO);
      datosAPintar.put("direccionPresentado",StringUtils.capitalize(direccionHijo));
      datosAPintar.put("primerNombrePresentado",pnombreHijo);
      datosAPintar.put("segundoNombrePresentado",snombreHijo);
      datosAPintar.put("primerApellidoPresentado",papeHijo);
      datosAPintar.put("segundoApellidoPresentado",sapeHijo);
      datosAPintar.put("sexoPresentado",sexoHijo);
      datosAPintar.put("paisPresentado",paisHijo);
      datosAPintar.put("estadoPresentado",estadoHijo);
      datosAPintar.put("municipioPresentado",municipioHijo);
      datosAPintar.put("parroquiaPresentado",parroquiaHijo);
      datosAPintar.put("am_pm",formato);
      //--------------------madre--------------------------
      datosAPintar.put("primerNombreMadre",pnombreMadre);
      datosAPintar.put("segundoNombreMadre",snombreMadre);
      datosAPintar.put("primerApellidoMadre",papeMadre);
      datosAPintar.put("segundoApellidoMadre",sapeMadre);
      datosAPintar.put("edadMadre",edadMadre);
      datosAPintar.put("profesionMadre",profesionMadre);
      datosAPintar.put("nacionalidadMadre",nacionalidadMadre);
      datosAPintar.put("declaranteMadre",true);
      datosAPintar.put("residenciaMadre",StringUtils.capitalize(residenciaMadre));
      datosAPintar.put("documentoMadre",tipoDocuMadre);
      datosAPintar.put("tipoDocumentoMadre",tipoDocMadre);
      datosAPintar.put("identidadMadre",cedulaMadre);
      //--------------------padre--------------------------
      datosAPintar.put("primerNombrePadre","N/A");
      datosAPintar.put("segundoNombrePadre","N/A");
      datosAPintar.put("primerApellidoPadre","N/A");
      datosAPintar.put("segundoApellidoPadre","N/A");
      datosAPintar.put("edadPadre","N/A");
      datosAPintar.put("profesionPadre","N/A");
      datosAPintar.put("nacionalidadPadre","N/A");
      datosAPintar.put("declarantePadre",false);
      datosAPintar.put("residenciaPadre","N/A");
      datosAPintar.put("documentoPadre","N/A");
      datosAPintar.put("tipoDocumentoPadre","N/A");
      datosAPintar.put("identidadPadre","N/A");
      //-------------------declarante------------------------
      datosAPintar.put("primerNombreOtro",pnombreMadre);
      datosAPintar.put("segundoNombreOtro",snombreMadre);
      datosAPintar.put("primerApellidoOtro",papeMadre);
      datosAPintar.put("segundoApellidoOtro",sapeMadre);
      datosAPintar.put("edadOtro",edadMadre);
      datosAPintar.put("profesionOtro",profesionMadre);
      datosAPintar.put("nacionalidadOtro",nacionalidadMadre);
      datosAPintar.put("declaranteOtro",true);
      datosAPintar.put("residenciaOtro",StringUtils.capitalize(residenciaMadre));
      datosAPintar.put("documentoOtro",tipoDocuMadre);
      datosAPintar.put("tipoDocumentoOtro",tipoDocMadre);
      datosAPintar.put("identidadOtro",cedulaMadre);
      //---------------------certificado salud--------------------
      datosAPintar.put("nCertificado",numCert);
      datosAPintar.put("numeroMPPS",mppsCert);
      datosAPintar.put("nombreCentroSalud",StringUtils.capitalize(centroSaludCert));
      datosAPintar.put("Autoridad",autoridadCert);
      //--------------------testigos 1 y2 -------------------------
      datosAPintar.put("primerNombreTestigo1",pnombreT1);
      datosAPintar.put("segundoNombreTestigo1",snombreT1);
      datosAPintar.put("primerApellidoTestigo1",papeT1);
      datosAPintar.put("segundoApellidoTestigo1",sapeT1);
      datosAPintar.put("edadTestigo1",edadT1);
      datosAPintar.put("profesionTestigo1",profesionT1);
      datosAPintar.put("nacionalidadTestigo1",nacionalidadT1);
      datosAPintar.put("residenciaTestigo1",StringUtils.capitalize(residenciaT1));
      datosAPintar.put("identidadTestigo1",cedulaT1);
      //****
      datosAPintar.put("primerNombreTestigo2",pnombreT2);
      datosAPintar.put("segundoNombreTestigo2",snombreT2);
      datosAPintar.put("primerApellidoTestigo2",papeT2);
      datosAPintar.put("segundoApellidoTestigo2",sapeT2);
      datosAPintar.put("edadTestigo2",edadT2);
      datosAPintar.put("profesionTestigo2",profesionT2);
      datosAPintar.put("nacionalidadTestigo2",nacionalidadT2);
      datosAPintar.put("residenciaTestigo2",StringUtils.capitalize(residenciaT2));
      datosAPintar.put("identidadTestigo2",cedulaT2);
      //-------------------------insercion acta-------------------------
      datosAPintar.put("insercionActa","N/A");
      datosAPintar.put("diaInsercion","N/A");
      datosAPintar.put("mesInsercion","N/A");
      datosAPintar.put("annoInsercion","N/A");
      datosAPintar.put("insercionAutoridad","N/A");
      //---------------------------medida proteccion---------------------
      datosAPintar.put("insercionActa","N/A");
      datosAPintar.put("diaProteccion","N/A");
      datosAPintar.put("mesProteccion","N/A");
      datosAPintar.put("annoProteccion","N/A");
      datosAPintar.put("consejoProteccion","N/A");
      datosAPintar.put("medidaN","N/A");
      datosAPintar.put("nombreConsejero","N/A");
      //---------------------------tribunal juzgado----------------------
      datosAPintar.put("tribunalJuzgado",StringUtils.capitalize(tribunal));
      datosAPintar.put("diaJuzgado",diaJuez);
      datosAPintar.put("mesJuzgado",mesJuez);
      datosAPintar.put("annoJuzgado",annoJuez);
      datosAPintar.put("sentencia",sentencia);
      datosAPintar.put("nombreJuez",nomCompJuez);
      //----------------------------temporanea------------------------------------
      datosAPintar.put("datosInforme","N/A");
      datosAPintar.put("diaExtemporanea","N/A");
      datosAPintar.put("mesExtemporanea","N/A");
      datosAPintar.put("annoExtemporanea","N/A");
      datosAPintar.put("autoridadExpide","N/A");
      //----------------------------circunstancia / observaciones------------------
      datosAPintar.put("circunstacia",circunstancia);
      datosAPintar.put("observaciones",observaciones);
      //--------------------------documentos presentados---------------------------
      datosAPintar.put("docPresentados",docPresentados);
      //---------------------------------------------------------------------------

      datosAPintar.put("juezTribunal", nomCompJuez);
      //datosAPintar.put("fecha", "----");
      //datosAPintar.put("nombreNacimiento", nombreCompleto);
      datosAPintar.put("nActa", "----");
      datosAPintar.put("nFolio", "----");
      datosAPintar.put("annoLibro", "----");
      datosAPintar.put("fechaSentencia", fechaj);
      datosAPintar.put("rutaImg", RutaIMAGE);
      return datosAPintar;
	}
	
	    @RequestMapping(value="/cargaExitosaRRFIL", method = RequestMethod.POST)
    public @ResponseBody String cargaExitosa(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{


        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"carga_exitosa.html"));

        //modelo.put("mensaje", "Carga de documento exitosa.");
        modelo.put("titulo","Carga de documento exitosa");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }
	    
	    
	     @RequestMapping(value="/guardarDatosActaRRFIL", method = RequestMethod.POST)
	     public @ResponseBody ActaPrimigenia guardarDatosActaRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, ParseException{
	        String token=request.getHeader("Authorization");
	        String login = seguridadCliente.getUsernameCliente(token);
	        FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
	        Funcionario obtenerDatosFuncionario = servFuncionario.buscarPorLogin(login);
	        String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
	 
	        //Funcionario dataServicio = new Funcionario();
	        CatalogoServicioCliente nConsecutivo = new  CatalogoServicioCliente();
	        Long obtenerNconsecutivo = nConsecutivo.proximoNroConsecutivo(codOficina);

	        JSONObject modelo = new JSONObject(data);
	        //JSONObject oficina = modelo.getJSONObject("oficina");
	        //JSONObject fecha = modelo.getJSONObject("fecha");
	        JSONObject respuesta = new JSONObject();
	        ObjectMapper mapper = new ObjectMapper();
	        String outputText = null;
	        Date date = null;
	        DateFormat outputFormat =  null;
	        DateFormat inputFormat = null;
	        
	        log.info("-----------oficina----->"+modelo.getString("oficina"));
	         
	         String numSolic = modelo.getString("id");
	         String nombreOficina = modelo.getString("oficina");
	         String numeroActa = modelo.getString("numeroActa");
	         String numeroTomo =  modelo.getString("numeroTomo");
	         String numeroFolio = modelo.getString("numeroFolio");
	         String fecha = modelo.getString("fecha");
	         
	         if("AB".equals(modelo.getString("statu"))){
	         
	         outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
            date = inputFormat.parse(fecha);
            outputText = outputFormat.format(date);
            
	         }else{
	            
	            outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	            inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
	           
	            try{
	            date = inputFormat.parse(fecha);
	            outputText = outputFormat.format(date);
	           
	            //respuesta.put("fechaDefuncion", outputText+' '+modelo.getString("formato2").toUpperCase());
	            
	            }catch(Exception e){
	               
	               outputText = fecha;
	                           
	            
	         }
	         }

	         
	         respuesta.put("numeroActa", numeroActa);
	         respuesta.put("numeroTomo", numeroTomo);
	         respuesta.put("numeroFolio", numeroFolio);
	         respuesta.put("nombreOficina", nombreOficina);
	         respuesta.put("numeroConsecutivo", obtenerNconsecutivo);
	         respuesta.put("numeroSolic", numSolic);
	         respuesta.put("fechaIncripcion", outputText);
	         
	         ActaPrimigenia AP = mapper.readValue(respuesta.toString(), ActaPrimigenia.class);
	         ActaServicioCliente servicioCliente = new ActaServicioCliente();
	         ActaPrimigenia actaprim = servicioCliente.guardarActaPrimigenia(AP);
	         
	         return actaprim;
	     }
	     
	     public @ResponseBody ActaPrimigenia consultarDatosActaRRFIL (String numSolicitud) throws JsonGenerationException, JsonMappingException, IOException{

	        ActaServicioCliente actaCliente = new ActaServicioCliente();
	           ActaPrimigenia consulta =  null;         
	           consulta = actaCliente.consultaActaPrimigenia(numSolicitud);
	        return consulta;
	    }
	     
	     @RequestMapping(value="/RRFIL_datosActa", method = RequestMethod.POST)
	     public @ResponseBody String RRFIL_datosActa(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{


	         JSONObject modelo = new JSONObject(data);
	         JSONObject respuesta = new JSONObject();

	         String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"datos_acta_recomposicion.html"));
	         
	         ActaPrimigenia datosActa = this.consultarDatosActaRRFIL(modelo.getString("id"));

	         modelo.put("numeroActa", datosActa.getNumeroActa());
	         modelo.put("numeroTomo", datosActa.getNumeroTomo());
	         modelo.put("numeroFolio", datosActa.getNumeroFolio());
	         modelo.put("oficina", datosActa.getNombreOficina());
	         modelo.put("fecha", datosActa.getFechaIncripcion());
      

	         modelo.put("titulo", "Solicitud de registro");
	         respuesta.put("vista", vista);
	         respuesta.put("modelo", modelo);
	         return respuesta.toString();
	     }
	     
	     
	     @RequestMapping(value = "/consultarPais", method = RequestMethod.GET)
	     public List<Pais> consultarPaisTodos() {
	         Catalogo catalogo = new Catalogo();
	         return catalogo.consultarPaisTodos();
	     }

	     @RequestMapping(value = "/consultarMunicipios/{codigoEstado}", method = RequestMethod.GET)
	     public List<Municipio> consultarMunicipiosEstado(@PathVariable("codigoEstado") String codigoEstado) {

	         Catalogo catalogo = new Catalogo();
	         return catalogo.consultarMunicipiosEstado(codigoEstado);
	     }
  	
	     /**
	      * Funcion que cunsulta parroquias por estado
	      *
	      * @param codigoMunicipio String codigo del municipio
	      * @return List<Parroquia> lista de objetos Parroquia
	      * @author Maria Marsicano
	      */
	     @RequestMapping(value = "/consultarParroquias/{codigoMunicipio}", method = RequestMethod.GET)
	     public List<Parroquia> consultarParroquiasMunicipio(@PathVariable("codigoMunicipio") String codigoMunicipio) {
	         //String codEstado ="APU";
	         Catalogo catalogo = new Catalogo();
	         return catalogo.consultarParroquiasMunicipio(codigoMunicipio);
	     }
	     @RequestMapping(value = "/verificacionNoConformeRRFIL", method = RequestMethod.POST)
	     public
	     @ResponseBody
	     String verificacionNoConformeRRFIL(@RequestBody String data,
	                                 HttpSession sesion, HttpServletRequest request)
	             throws GeneralException, JsonParseException, JsonMappingException,
	             IOException, JSONException {

	         log.info("--------datos no conforme verificacion--------");
	         JSONObject modelo = new JSONObject(data);
	         JSONObject respuesta = new JSONObject();

	         String vista = Util.leerArchivo(context.getRealPath(rutaHtml
	                 + "ver_observaciones.html"));
	         modelo.put("titulo", "Ver Observaciones");
	         respuesta.put("vista", vista);
	         respuesta.put("modelo", modelo);
	         return respuesta.toString();
	     }
	     
	     
	     @RequestMapping(value="/presentarImprimirRRFILPIA", method = RequestMethod.POST)
	     public @ResponseBody String presentarImprimirPIA(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException{
	 log.info("---------------------presentarImprimir -------------");
	 String token=request.getHeader("Authorization");
	 JSONObject modelo = new JSONObject(data);
	 JSONObject respuesta = new JSONObject();
	 JSONObject modeloReporte = new JSONObject();

	// String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"imprimir_documentoPIA.html"));
	 sol = solicitud.consultarDetalleSolicitud((String) modelo.get("id"));
	 String fechaRegistro = formatDate.convertirDateAString(sol.getFechaInicio());

	         modeloReporte = generarReporte(data, modelo.getString("id"),modelo.getString("statu"),token);
	         
	         modelo.put("mensaje", "Impresi&oacute;n exitosa");
	         modelo.put("fechaRegistro", fechaRegistro);
	         respuesta.put("vista", modeloReporte.get("vista"));
	        
	         respuesta.put("modelo", modelo);
	         return respuesta.toString();
	     }
	     
	     @RequestMapping(value="/presentarSatisfactorioRRFILPIA", method = RequestMethod.POST)
	     public @ResponseBody String presentarSatisfactorioPIA(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

	         JSONObject modelo = new JSONObject(data);
	         JSONObject respuesta = new JSONObject();

	         String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"satisfactorioPIA.html"));

	         modelo.put("mensaje", "Impresi&oacute;n exitosa");

	         respuesta.put("vista", vista);
	         respuesta.put("modelo", modelo);
	         return respuesta.toString();
	     }
	     
	     
	     public String obtenerArrayFechaIDJ(String fecha) throws ParseException{
           DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
           DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
           
           Date date = inputFormat.parse(fecha);
           String outputText = outputFormat.format(date);
           
         //  log.info("outputText-----> "+outputText);
           
           String[] arrayFecha = outputText.split(" ");
           String fechaSalida = arrayFecha[0];
           //log.info("fechaSalida-----> "+fechaSalida);
	        
	        return fechaSalida;
	     }

	     
        @RequestMapping(value="/actualizarParticipanteRRFIL", method = RequestMethod.POST)
        public @ResponseBody Participante actualizarParticipanteRRFIL(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, ParseException{

           log.info("---------->ENTRO A ACTUALIZAR");
           
           JSONObject datos = new JSONObject(data);
           JSONObject HIJ = datos.getJSONObject("HIJ");
   
           
           log.info("---------->HIJO "+HIJ);
           
           Date fecha = new Date();
           Date fecha2 = new Date();
           DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
           
           try{
              DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
              fecha = inputFormat.parse(HIJ.getString("fechaNacimiento"));
           }catch(Exception e) {
              long val = Long.parseLong(HIJ.getString("fechaNacimiento"));
               fecha = new Date(val);
           }
           JSONObject horaMin = HIJ.getJSONObject("horaNacimiento");
           String hora = horaMin.getString("hora");
           String minuto = horaMin.getString("minuto");
           
           
           log.info("horaMin-----------> "+horaMin);
           String outputText = outputFormat.format(fecha);
           
           log.info("outputText-----------> "+outputText);
           String arrayFechahijo[] = outputText.split(" ");
           
           fecha2 = outputFormat.parse(arrayFechahijo[0]+" "+hora+":"+minuto+":00");
           
           log.info("---------->fechaGuardarHijo "+fecha2);
           
           long millis = (fecha2.getTime());
           
           log.info("---------->val2 "+millis);
           
//           String fNac = millis ;

           HIJ.remove("horaNacimiento");
           HIJ.remove("fechaNacimiento");
           JSONObject paisHijo =  new JSONObject();
           JSONObject estadoHijo =  new JSONObject();
           JSONObject municipioHijo =  new JSONObject();
           JSONObject parroquiaHijo =  new JSONObject();
           
           String valorPais = HIJ.getJSONArray("direccion").getJSONObject(0).getString("pais");
           String valorEstado = HIJ.getJSONArray("direccion").getJSONObject(0).getString("estado");
           String valorMunicipio = HIJ.getJSONArray("direccion").getJSONObject(0).getString("municipio");
           String valorParroquia = HIJ.getJSONArray("direccion").getJSONObject(0).getString("parroquia");
           
           log.info("---------------->ARRAY DIRECCION " + HIJ.getJSONArray("direccion").getJSONObject(0));
           HIJ.getJSONArray("direccion").getJSONObject(0).put("pais", paisHijo);
           HIJ.getJSONArray("direccion").getJSONObject(0).put("estado", estadoHijo);
           HIJ.getJSONArray("direccion").getJSONObject(0).put("municipio", municipioHijo);
           HIJ.getJSONArray("direccion").getJSONObject(0).put("parroquia", parroquiaHijo);
           
           HIJ.getJSONArray("direccion").getJSONObject(0).getJSONObject("pais").put("nombre", valorPais);
           HIJ.getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").put("nombre", valorEstado);
           HIJ.getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").put("nombre", valorMunicipio);
           HIJ.getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").put("nombre", valorParroquia);
           
           JSONObject tipoDireccionHijo = new JSONObject();
           tipoDireccionHijo.put("codigo", "RES");
           tipoDireccionHijo.put("descripcion", "RESIDENCIA");
           tipoDireccionHijo.put("nombre", "DIRECCION DE NACIMIENTO");
           HIJ.getJSONArray("direccion").getJSONObject(0).put("tipoDireccion", tipoDireccionHijo);
           
           log.info("---------------->ARRAY  " +  HIJ.getJSONArray("direccion").getJSONObject(0));
           
   
           HIJ.put("fechaNacimiento", millis);
           String hijoString = HIJ.toString();

           
           ObjectMapper mapper = new ObjectMapper();
           Participante HIJO = mapper.readValue(hijoString, Participante.class);      
        ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
        
        log.info("---------->ANTES DE GUARDAR ");

        Participante dataHijo = servicios.actualizarParticipante(datos.getString("id"), HIJO);
        log.info("---------->GUARDO PERFECTO "+hijoString);
        
          
  //--------------------DATOS DE LA MADRE PARA GUARDAR------------------//         
           JSONObject MAD = datos.getJSONObject("MAD");           
           
           log.info("---------->MADRE "+MAD);
           
           JSONObject paisMadre =  new JSONObject();
           JSONObject estadoMadre =  new JSONObject();
           JSONObject municipioMadre =  new JSONObject();
           JSONObject parroquiaMadre =  new JSONObject();
           
           String valorPaisMadre = MAD.getJSONArray("direccion").getJSONObject(1).getString("pais");
           String valorEstadoMadre = MAD.getJSONArray("direccion").getJSONObject(1).getString("estado");
           String valorMunicipioMadre = MAD.getJSONArray("direccion").getJSONObject(1).getString("municipio");
           String valorParroquiaMadre = MAD.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
           
           log.info("---------------->ARRAY DIRECCION " + MAD.getJSONArray("direccion").getJSONObject(0));
           MAD.getJSONArray("direccion").getJSONObject(1).put("pais", paisMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).put("estado", estadoMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaMadre);
           
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaMadre);
           JSONObject tipoDireccion = new JSONObject();
           tipoDireccion.put("codigo", "RES");
           tipoDireccion.put("descripcion", "RESIDENCIA");
           tipoDireccion.put("nombre", "DIRECCION DE RESIDENCIA");
           MAD.getJSONArray("direccion").getJSONObject(1).put("tipoDireccion", tipoDireccion);
           MAD.remove("Edad");
           
           try{
              String ComInd = MAD.getJSONObject("comunidadIndigena").getString("nombre");
              MAD.remove("comunidadIndigena");
              MAD.put("comunidadIndigena", ComInd);
              }
              catch (Exception e)
              {
                 MAD.getString("comunidadIndigena");
              }
           
           String Ocup = MAD.getString("profesion");
           log.info("---------> OCUPACION MADRE "+Ocup);
           MAD.remove("profesion");
           MAD.remove("ocupacion");
           MAD.put("ocupacion", Ocup);
           if (MAD.getString("sexo")!=null){
              MAD.put("sexo", "F");
           }else{
              MAD.put("sexo", "F");
           }
           
           String madreString = MAD.toString();
           
           ObjectMapper mapper2 = new ObjectMapper();
           Participante MADRE = mapper2.readValue(madreString, Participante.class);      
       
        log.info("---------->ANTES DE GUARDAR ");

        Participante dataMadre = servicios.actualizarParticipante(datos.getString("id"), MADRE);
        
        log.info("---------->GUARDO PERFECTO MADRE"+dataMadre);
        
        
        //------------------ data certificado medico----------------
        
        JSONObject CMN = datos.getJSONObject("CMN");
        
        Acta Act = new Acta();
        
        log.info("---------->CMN "+CMN);
        
        String fechaCMN = CMN.getString("fechaCert");
        DateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        DateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");

        Date date = formatoEntrada.parse(fechaCMN);
        String formatoFechaFin = formatoSalida.format(date);
        
        Date fechaCertificadoFin = formatoSalida.parse(formatoFechaFin);
        
        log.info("------------>FECHA FIN CERTIFICADO "+fechaCertificadoFin);
        

        
        
        String numeroActaRRFIL = servicioActa.buscarNumeroActaPorSolic(datos.getString("id"));
        Act.setNumeroActa(numeroActaRRFIL);
        Nac.setActa(Act);
        Nac.setNumeroCertificado(CMN.getInt("numCert"));
        Nac.setFechaCertificado(fechaCertificadoFin);
        Nac.setDocumentoIdentidadMedico(CMN.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero"));
        Nac.setPrimerApellidoMedico(CMN.getString("primerApellido"));
        Nac.setSegundoApellidoMedico(CMN.getString("segundoApellido"));
        Nac.setPrimerNombreMedico(CMN.getString("primerNombre"));
        Nac.setSegundoNombreMedico(CMN.getString("segundoNombre"));
        Nac.setNuMPPS(CMN.getInt("numMPPS"));
        Nac.setCentroSalud(CMN.getString("centroSalud"));
        Nac.setSexo("M");
        boolean NacSatisfactorio = servicioActa.guardarNacimiento(Nac); 
        log.info("------------>GUARDO PERFECTO CERTIFICADO MEDICO "+NacSatisfactorio);
        
   //------------------------------ DATOS DECISION JUDICIAL------------------------------
        String juez = new String(datos.getString("IDJ"));
        JSONObject JUEZ = new JSONObject(juez);
        String pnombreJuez = String.valueOf(JUEZ.get("primerNombre"));
        String papeJuez = String.valueOf(JUEZ.get("primerApellido"));
        String fechaJuez=JUEZ.getString("fecha");
        Date fJuez=formatoEntrada.parse(fechaJuez);
        String tribunal=String.valueOf(JUEZ.get("tribunal"));
        String sentencia=String.valueOf(JUEZ.get("sentencia"));
        String snombreJuez=" ";String sapeJuez=" ";
        try{
            snombreJuez=JUEZ.getString("segundoNombre")==null?" ":JUEZ.getString("segundoNombre");
        }catch (Exception e){}
        try{
            sapeJuez=JUEZ.getString("segundoApellido")==null?" ":JUEZ.getString("segundoApellido");
        }catch (Exception e){}
        //------
        DecisionJudicial decJ = new DecisionJudicial();
        decJ.setNumeroActa(numeroActaRRFIL);
        decJ.setNombreTribunal(tribunal);
        decJ.setNumeroSentencia(sentencia);
        decJ.setPrimerNombreJuez(pnombreJuez);
        decJ.setSegundoNombreJuez(snombreJuez);
        decJ.setPrimerApellidoJuez(papeJuez);
        decJ.setSegundoApellidoJuez(sapeJuez);
        decJ.setFechaSentencia(fJuez);
        boolean DecJSatisfactorio = servicioActa.guardarDecisionJudicial(decJ);
        log.info("guardar data IDJ "+DecJSatisfactorio);
           
  //------------------------------DATOS TESTIGO 1-------------------------         
           JSONObject TA1 = datos.getJSONObject("TA1");
           TA1.remove("edad");
           
           log.info("---------->TA1 "+TA1);
           
           String OcupTA1 = TA1.getString("profesion");
           log.info("---------> OCUPACION MADRE "+OcupTA1);
           TA1.remove("profesion");
           TA1.remove("ocupacion");
           TA1.put("ocupacion", OcupTA1);
           
           JSONObject paisTA1 =  new JSONObject();
           JSONObject estadoTA1 =  new JSONObject();
           JSONObject municipioTA1 =  new JSONObject();
           JSONObject parroquiaTA1 =  new JSONObject();
           
           String valorPaisTA1 = MAD.getJSONArray("direccion").getJSONObject(1).getString("pais");
           String valorEstadoTA1 = MAD.getJSONArray("direccion").getJSONObject(1).getString("estado");
           String valorMunicipioTA1 = MAD.getJSONArray("direccion").getJSONObject(1).getString("municipio");
           String valorParroquiaTA1 = MAD.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
           
           log.info("---------------->ARRAY DIRECCION " + MAD.getJSONArray("direccion").getJSONObject(0));
           MAD.getJSONArray("direccion").getJSONObject(1).put("pais", paisTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).put("estado", estadoTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaTA1);
           
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaTA1);
           
           
           
           try{
              String ComIndTA1 = TA1.getJSONObject("comunidadIndigena").getString("nombre");
              TA1.remove("comunidadIndigena");
              TA1.put("comunidadIndigena", ComIndTA1);
              }
              catch (Exception e)
              {
                 TA1.getString("comunidadIndigena");
              }
           String testigo1String = TA1.toString();
           
           ObjectMapper mapper3 = new ObjectMapper();
           Participante TESTIGO = mapper3.readValue(testigo1String, Participante.class);  
           Participante dataTestigo = servicios.actualizarParticipante(datos.getString("id"), TESTIGO);
           log.info("---------->GUARDO PERFECTO TESTIGO1 "+dataTestigo);
           
           
        //---------------------------data TESTIGO2---------------------   
           JSONObject TA2 = datos.getJSONObject("TA2");
           TA2.remove("edad");
           
           log.info("---------->TA2 "+TA2);
           
           String OcupTA2 = TA2.getString("profesion");
           log.info("---------> OCUPACION MADRE "+OcupTA2);
           TA2.remove("profesion");
           TA2.remove("ocupacion");
           TA2.put("ocupacion", OcupTA2);
           
           JSONObject paisTA2 =  new JSONObject();
           JSONObject estadoTA2 =  new JSONObject();
           JSONObject municipioTA2 =  new JSONObject();
           JSONObject parroquiaTA2 =  new JSONObject();
           
           String valorPaisTA2 = MAD.getJSONArray("direccion").getJSONObject(1).getString("pais");
           String valorEstadoTA2 = MAD.getJSONArray("direccion").getJSONObject(1).getString("estado");
           String valorMunicipioTA2 = MAD.getJSONArray("direccion").getJSONObject(1).getString("municipio");
           String valorParroquiaTA2 = MAD.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
           
           log.info("---------------->ARRAY DIRECCION " + MAD.getJSONArray("direccion").getJSONObject(0));
           MAD.getJSONArray("direccion").getJSONObject(1).put("pais", paisTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).put("estado", estadoTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaTA2);
           
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaTA2);
           
           try{
              String ComIndTA2 = TA2.getJSONObject("comunidadIndigena").getString("nombre");
              TA2.remove("comunidadIndigena");
              TA2.put("comunidadIndigena", ComIndTA2);
              }
              catch (Exception e)
              {
                 TA2.getString("comunidadIndigena");
              }
           String testigo2String = TA2.toString();
           
           ObjectMapper mapper4 = new ObjectMapper();
           Participante TESTIGO2 = mapper4.readValue(testigo2String, Participante.class);  
           Participante dataTestigo2 = servicios.actualizarParticipante(datos.getString("id"), TESTIGO2);
           log.info("---------->GUARDO PERFECTO TESTIGO2 "+dataTestigo2);

           

            return null;
        }
        
        @RequestMapping(value = "/verificacionTramiteRRFIL", method = RequestMethod.POST)
        public
        @ResponseBody
        String verificacionTramiteRRFIL(@RequestBody String data,
                                      HttpSession sesion, HttpServletRequest request)
                throws GeneralException, JsonParseException, JsonMappingException,
                IOException, JSONException {

            //String token = request.getHeader("Authorization");
            log.info("--------datos no conforme verificacion--------");
            JSONObject modelo = new JSONObject(data);
            JSONObject respuesta = new JSONObject();

            String vista = Util.leerArchivo(context.getRealPath(rutaHtml
                    + "datos_presentado.html"));
            modelo.put("titulo", "Datos del presentado");
            respuesta.put("vista", vista);
            respuesta.put("modelo", modelo);
            return respuesta.toString();
        }
        
        
        @RequestMapping(value="/actualizarParticipanteRRFILNCA", method = RequestMethod.POST)
        public @ResponseBody Participante actualizarParticipanteRRFILNCA(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, ParseException{

           log.info("---------->ENTRO A ACTUALIZAR PARTICIPANTE NCA");
           
           JSONObject datos = new JSONObject(data);
           JSONObject HIJ = datos.getJSONObject("HIJ");
   
           
           log.info("---------->HIJO "+HIJ);
           
           Date fecha = new Date();
           Date fecha2 = new Date();
           DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
           
           try{
              DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
              fecha = inputFormat.parse(HIJ.getString("fechaNacimiento"));
           }catch(Exception e) {
              long val = Long.parseLong(HIJ.getString("fechaNacimiento"));
               fecha = new Date(val);
           }
           JSONObject horaMin = HIJ.getJSONObject("horaNacimiento");
           String hora = horaMin.getString("hora");
           String minuto = horaMin.getString("minuto");
           
           
           log.info("horaMin-----------> "+horaMin);
           String outputText = outputFormat.format(fecha);
           
           log.info("outputText-----------> "+outputText);
           String arrayFechahijo[] = outputText.split(" ");
           
           fecha2 = outputFormat.parse(arrayFechahijo[0]+" "+hora+":"+minuto+":00");
           
           log.info("---------->fechaGuardarHijo "+fecha2);
           
           long millis = (fecha2.getTime());
           
           log.info("---------->val2 "+millis);
           
//           String fNac = millis ;

           HIJ.remove("horaNacimiento");
           HIJ.remove("fechaNacimiento");
           JSONObject paisHijo =  new JSONObject();
           JSONObject estadoHijo =  new JSONObject();
           JSONObject municipioHijo =  new JSONObject();
           JSONObject parroquiaHijo =  new JSONObject();
           
           String valorPais = HIJ.getJSONArray("direccion").getJSONObject(0).getString("pais");
           String valorEstado = HIJ.getJSONArray("direccion").getJSONObject(0).getString("estado");
           String valorMunicipio = HIJ.getJSONArray("direccion").getJSONObject(0).getString("municipio");
           String valorParroquia = HIJ.getJSONArray("direccion").getJSONObject(0).getString("parroquia");
           
           log.info("---------------->ARRAY DIRECCION " + HIJ.getJSONArray("direccion").getJSONObject(0));
           HIJ.getJSONArray("direccion").getJSONObject(0).put("pais", paisHijo);
           HIJ.getJSONArray("direccion").getJSONObject(0).put("estado", estadoHijo);
           HIJ.getJSONArray("direccion").getJSONObject(0).put("municipio", municipioHijo);
           HIJ.getJSONArray("direccion").getJSONObject(0).put("parroquia", parroquiaHijo);
           
           HIJ.getJSONArray("direccion").getJSONObject(0).getJSONObject("pais").put("nombre", valorPais);
           HIJ.getJSONArray("direccion").getJSONObject(0).getJSONObject("estado").put("nombre", valorEstado);
           HIJ.getJSONArray("direccion").getJSONObject(0).getJSONObject("municipio").put("nombre", valorMunicipio);
           HIJ.getJSONArray("direccion").getJSONObject(0).getJSONObject("parroquia").put("nombre", valorParroquia);
           
           JSONObject tipoDireccionHijo = new JSONObject();
           tipoDireccionHijo.put("codigo", "RES");
           tipoDireccionHijo.put("descripcion", "RESIDENCIA");
           tipoDireccionHijo.put("nombre", "DIRECCION DE NACIMIENTO");
           HIJ.getJSONArray("direccion").getJSONObject(0).put("tipoDireccion", tipoDireccionHijo);
           
           log.info("---------------->ARRAY  " +  HIJ.getJSONArray("direccion").getJSONObject(0));
           
   
           HIJ.put("fechaNacimiento", millis);
           String hijoString = HIJ.toString();

           
           ObjectMapper mapper = new ObjectMapper();
           Participante HIJO = mapper.readValue(hijoString, Participante.class);      
        ParticipanteServicioCliente servicios = new ParticipanteServicioCliente();
        
        log.info("---------->ANTES DE GUARDAR ");

        Participante dataHijo = servicios.actualizarParticipante(datos.getString("id"), HIJO);
        log.info("---------->GUARDO PERFECTO "+hijoString);
        
          
  //--------------------DATOS DE LA MADRE PARA GUARDAR------------------//         
           JSONObject MAD = datos.getJSONObject("MAD");           
           
           log.info("---------->MADRE "+MAD);
           
           JSONObject paisMadre =  new JSONObject();
           JSONObject estadoMadre =  new JSONObject();
           JSONObject municipioMadre =  new JSONObject();
           JSONObject parroquiaMadre =  new JSONObject();
           
           String valorPaisMadre = MAD.getJSONArray("direccion").getJSONObject(1).getString("pais");
           String valorEstadoMadre = MAD.getJSONArray("direccion").getJSONObject(1).getString("estado");
           String valorMunicipioMadre = MAD.getJSONArray("direccion").getJSONObject(1).getString("municipio");
           String valorParroquiaMadre = MAD.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
           
           log.info("---------------->ARRAY DIRECCION " + MAD.getJSONArray("direccion").getJSONObject(0));
           MAD.getJSONArray("direccion").getJSONObject(1).put("pais", paisMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).put("estado", estadoMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaMadre);
           
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioMadre);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaMadre);
           JSONObject tipoDireccion = new JSONObject();
           tipoDireccion.put("codigo", "RES");
           tipoDireccion.put("descripcion", "RESIDENCIA");
           tipoDireccion.put("nombre", "DIRECCION DE RESIDENCIA");
           MAD.getJSONArray("direccion").getJSONObject(1).put("tipoDireccion", tipoDireccion);
           MAD.remove("Edad");
           
           try{
              String ComInd = MAD.getJSONObject("comunidadIndigena").getString("nombre");
              MAD.remove("comunidadIndigena");
              MAD.put("comunidadIndigena", ComInd);
              }
              catch (Exception e)
              {
                 MAD.getString("comunidadIndigena");
              }
           
           String Ocup = MAD.getString("profesion");
           log.info("---------> OCUPACION MADRE "+Ocup);
           MAD.remove("profesion");
           MAD.remove("ocupacion");
           MAD.put("ocupacion", Ocup);
           if (MAD.getString("sexo")!=null){
              MAD.put("sexo", "F");
           }else{
              MAD.put("sexo", "F");
           }
           
           String madreString = MAD.toString();
           
           ObjectMapper mapper2 = new ObjectMapper();
           Participante MADRE = mapper2.readValue(madreString, Participante.class);      
       
        log.info("---------->ANTES DE GUARDAR ");

        Participante dataMadre = servicios.actualizarParticipante(datos.getString("id"), MADRE);
        
        log.info("---------->GUARDO PERFECTO MADRE"+dataMadre);
        
        //------------------ data certificado medico----------------
        
        JSONObject CMN = datos.getJSONObject("CMN");
        
        Acta Act = new Acta();
        
        log.info("---------->CMN "+CMN);
        
        String fechaCMN = CMN.getString("fechaCert");
        DateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        DateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");

        Date date = formatoEntrada.parse(fechaCMN);
        String formatoFechaFin = formatoSalida.format(date);
        
        Date fechaCertificadoFin = formatoSalida.parse(formatoFechaFin);
        
        log.info("------------>FECHA FIN CERTIFICADO "+fechaCertificadoFin);
        

        
        
        String numeroActaRRFIL = servicioActa.buscarNumeroActaPorSolic(datos.getString("id"));
        Act.setNumeroActa(numeroActaRRFIL);
        Nac.setActa(Act);
        Nac.setNumeroCertificado(CMN.getInt("numCert"));
        Nac.setFechaCertificado(fechaCertificadoFin);
        Nac.setDocumentoIdentidadMedico(CMN.getJSONObject("documentoIdentidad").getJSONObject("0").getString("numero"));
        Nac.setPrimerApellidoMedico(CMN.getString("primerApellido"));
        Nac.setSegundoApellidoMedico(CMN.getString("segundoApellido"));
        Nac.setPrimerNombreMedico(CMN.getString("primerNombre"));
        Nac.setSegundoNombreMedico(CMN.getString("segundoNombre"));
        Nac.setNuMPPS(CMN.getInt("numMPPS"));
        Nac.setCentroSalud(CMN.getString("centroSalud"));
        Nac.setSexo("M");
        boolean NacSatisfactorio = servicioActa.guardarNacimiento(Nac); 
        log.info("------------>GUARDO PERFECTO CERTIFICADO MEDICO "+NacSatisfactorio);
        
   //------------------------------ DATOS DECISION JUDICIAL------------------------------
        String juez = new String(datos.getString("IDJ"));
        JSONObject JUEZ = new JSONObject(juez);
        String pnombreJuez = String.valueOf(JUEZ.get("primerNombre"));
        String papeJuez = String.valueOf(JUEZ.get("primerApellido"));
        String fechaJuez=JUEZ.getString("fecha");
        Date fJuez=formatoEntrada.parse(fechaJuez);
        String tribunal=String.valueOf(JUEZ.get("tribunal"));
        String sentencia=String.valueOf(JUEZ.get("sentencia"));
        String snombreJuez=" ";String sapeJuez=" ";
        try{
            snombreJuez=JUEZ.getString("segundoNombre")==null?" ":JUEZ.getString("segundoNombre");
        }catch (Exception e){}
        try{
            sapeJuez=JUEZ.getString("segundoApellido")==null?" ":JUEZ.getString("segundoApellido");
        }catch (Exception e){}
        //------
        DecisionJudicial decJ = new DecisionJudicial();
        decJ.setNumeroActa(numeroActaRRFIL);
        decJ.setNombreTribunal(tribunal);
        decJ.setNumeroSentencia(sentencia);
        decJ.setPrimerNombreJuez(pnombreJuez);
        decJ.setSegundoNombreJuez(snombreJuez);
        decJ.setPrimerApellidoJuez(papeJuez);
        decJ.setSegundoApellidoJuez(sapeJuez);
        decJ.setFechaSentencia(fJuez);
        boolean DecJSatisfactorio = servicioActa.guardarDecisionJudicial(decJ);
        log.info("guardar data IDJ "+DecJSatisfactorio);
           
  //------------------------------DATOS TESTIGO 1-------------------------  
        
           JSONObject TA1 = datos.getJSONObject("TA1");
           TA1.remove("edad");
           
           log.info("---------->TA1 "+TA1);
           
           String OcupTA1 = TA1.getString("profesion");
           log.info("---------> OCUPACION MADRE "+OcupTA1);
           TA1.remove("profesion");
           TA1.remove("ocupacion");
           TA1.put("ocupacion", OcupTA1);
           
           JSONObject paisTA1 =  new JSONObject();
           JSONObject estadoTA1 =  new JSONObject();
           JSONObject municipioTA1 =  new JSONObject();
           JSONObject parroquiaTA1 =  new JSONObject();
           
           String valorPaisTA1 = MAD.getJSONArray("direccion").getJSONObject(1).getString("pais");
           String valorEstadoTA1 = MAD.getJSONArray("direccion").getJSONObject(1).getString("estado");
           String valorMunicipioTA1 = MAD.getJSONArray("direccion").getJSONObject(1).getString("municipio");
           String valorParroquiaTA1 = MAD.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
           
           log.info("---------------->ARRAY DIRECCION " + MAD.getJSONArray("direccion").getJSONObject(0));
           MAD.getJSONArray("direccion").getJSONObject(1).put("pais", paisTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).put("estado", estadoTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaTA1);
           
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioTA1);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaTA1);
           
           
           
           try{
              String ComIndTA1 = TA1.getJSONObject("comunidadIndigena").getString("nombre");
              TA1.remove("comunidadIndigena");
              TA1.put("comunidadIndigena", ComIndTA1);
              }
              catch (Exception e)
              {
                 TA1.getString("comunidadIndigena");
              }
           String testigo1String = TA1.toString();
           
           ObjectMapper mapper3 = new ObjectMapper();
           Participante TESTIGO = mapper3.readValue(testigo1String, Participante.class);  
           Participante dataTestigo = servicios.actualizarParticipante(datos.getString("id"), TESTIGO);
           log.info("---------->GUARDO PERFECTO TESTIGO1 "+dataTestigo);

        //---------------------------data TESTIGO2---------------------   
           JSONObject TA2 = datos.getJSONObject("TA2");
           TA2.remove("edad");
           
           log.info("---------->TA2 "+TA2);
           
           String OcupTA2 = TA2.getString("profesion");
           log.info("---------> OCUPACION MADRE "+OcupTA2);
           TA2.remove("profesion");
           TA2.remove("ocupacion");
           TA2.put("ocupacion", OcupTA2);
           
           JSONObject paisTA2 =  new JSONObject();
           JSONObject estadoTA2 =  new JSONObject();
           JSONObject municipioTA2 =  new JSONObject();
           JSONObject parroquiaTA2 =  new JSONObject();
           
           String valorPaisTA2 = MAD.getJSONArray("direccion").getJSONObject(1).getString("pais");
           String valorEstadoTA2 = MAD.getJSONArray("direccion").getJSONObject(1).getString("estado");
           String valorMunicipioTA2 = MAD.getJSONArray("direccion").getJSONObject(1).getString("municipio");
           String valorParroquiaTA2 = MAD.getJSONArray("direccion").getJSONObject(1).getString("parroquia");
           
           log.info("---------------->ARRAY DIRECCION " + MAD.getJSONArray("direccion").getJSONObject(0));
           MAD.getJSONArray("direccion").getJSONObject(1).put("pais", paisTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).put("estado", estadoTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).put("municipio", municipioTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).put("parroquia", parroquiaTA2);
           
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("pais").put("nombre", valorPaisTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("estado").put("nombre", valorEstadoTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("municipio").put("nombre", valorMunicipioTA2);
           MAD.getJSONArray("direccion").getJSONObject(1).getJSONObject("parroquia").put("nombre", valorParroquiaTA2);
           
           try{
              String ComIndTA2 = TA2.getJSONObject("comunidadIndigena").getString("nombre");
              TA2.remove("comunidadIndigena");
              TA2.put("comunidadIndigena", ComIndTA2);
              }
              catch (Exception e)
              {
                 TA2.getString("comunidadIndigena");
              }
           String testigo2String = TA2.toString();
           
           ObjectMapper mapper4 = new ObjectMapper();
           Participante TESTIGO2 = mapper4.readValue(testigo2String, Participante.class);  
           Participante dataTestigo2 = servicios.actualizarParticipante(datos.getString("id"), TESTIGO2);
           log.info("---------->GUARDO PERFECTO TESTIGO2 "+dataTestigo2);
 
           
           
           

            return null;
        }
        
        public @ResponseBody Nacimiento consultarNacimientoRRFIL (String numSolicitud) throws JsonGenerationException, JsonMappingException, IOException{


           Nacimiento consulta= null;
           consulta =servicioActa.consultarNacimiento(numSolicitud);


           return consulta;
       }
  	
}