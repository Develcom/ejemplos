package ve.gob.cne.sarc.nacimiento.controllers.NacimientoGeneral;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.util.Util;

@RestController public class NacimientoGeneral {
	@Autowired
	ServletContext context;
	
    Logger log = Logger.getLogger(getClass().getName());
    private final String rutaHtml="/resources/site/RADOP/page/templates/";
   /// private final String rutaHtml="/resources/site/RADOP/page/templates/"; cada proyecto debe crear su ruta...
    
    
    
	/**
	 * Funcion que imprime el pdf en pantalla
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
    @RequestMapping(value="/imprimir_pdf", method = RequestMethod.POST)	
	public @ResponseBody String imprimir_pdf(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
		//log.info("*************RADOP************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
//		ObjectMapper mapper= new ObjectMapper();
//		Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class); 
//		String token=request.getHeader("Authorization");
//		if(token==null){
//			throw new GeneralException("seguridad_no_token");
//		}	
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"adoptado_hijo.html"));
		log.info("******Contenido Html************"+vista);
		modelo.put("titulo", "Datos para la Solicitud");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		
		return respuesta.toString();
	}
    
	/**
	 * funcion para verificacion de conforme no conforme levanta pantalla
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
    ///Metodo para imprimir
    @RequestMapping(value="/conforme_no", method = RequestMethod.POST)	
	public @ResponseBody String conforme_no(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
		//log.info("*************RADOP************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
//		ObjectMapper mapper= new ObjectMapper();
//		Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class); 
//		String token=request.getHeader("Authorization");
//		if(token==null){
//			throw new GeneralException("seguridad_no_token");
//		}	
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"verificacion.html"));
		log.info("******Contenido Html************"+vista);
		modelo.put("titulo", "Datos para la Solicitud");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		
		return respuesta.toString();
	}
    
    
	/**
	 * previsualiza el pdf-
	 * @author Elly Estaba
	 * @param data:
	 * contiene los datos obtenidos del rootScope
	 * @return JsonObject
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws GeneralException
	 */
    @RequestMapping(value="/vista_pdf", method = RequestMethod.POST)	
	public @ResponseBody String vista_pdf(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{
		//log.info("*************RADOP************"+sesion.getId());
		JSONObject modelo = new JSONObject(data);
		JSONObject respuesta = new JSONObject();
//		ObjectMapper mapper= new ObjectMapper();
//		Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class); 
//		String token=request.getHeader("Authorization");
//		if(token==null){
//			throw new GeneralException("seguridad_no_token");
//		}	
		String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"vista_pdf.html"));
		log.info("******Contenido Html************"+vista);
		modelo.put("titulo", "Datos para la Solicitud");
		respuesta.put("vista", vista);
		respuesta.put("modelo", modelo);
		
		return respuesta.toString();
	}
	
}
