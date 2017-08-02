package ve.gob.cne.sarc.generales.controladores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.util.Util;
@Controller

public class RADOPControlador {
	
	final static Logger logger = Logger.getLogger(RADOPControlador.class.getName());

	
	@RequestMapping(value = "/iniciarRADOP", method = RequestMethod.POST)
	public @ResponseBody String  iniciarRADOP(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException{
	String token=(String) sesion.getAttribute("access_token");
	if(token==null){
	GeneralException ge = new GeneralException("seguridad_no_token");
	ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		return Util.obtenerRecurso(token, data, "https://localhost:8443/web-nacimiento/iniciarTramite");
	}

	
	@RequestMapping(value = "/RADOP_adoptado_hijo", method = RequestMethod.POST)
	public @ResponseBody String  RADOP_adoptado_hijo(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException{
	String token=(String) sesion.getAttribute("access_token");
	if(token==null){
	GeneralException ge = new GeneralException("seguridad_no_token");
	ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		return Util.obtenerRecurso(token, data, "http://localhost:8080/web-nacimiento/RADOP_adoptado_hijo");
	}
	
	
	@RequestMapping(value = "/RADOP_adoptado", method = RequestMethod.POST)
	public @ResponseBody String  RADOP_adoptado(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException{
	String token=(String) sesion.getAttribute("access_token");
	if(token==null){
	GeneralException ge = new GeneralException("seguridad_no_token");
	ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		return Util.obtenerRecurso(token, data, "http://localhost:8080/web-nacimiento/RADOP_adoptado");
	}
	
	@RequestMapping(value = "/RADOP_acta_prim", method = RequestMethod.POST)
	public @ResponseBody String  RADOP_acta_prim(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException{
	String token=(String) sesion.getAttribute("access_token");
	if(token==null){
	GeneralException ge = new GeneralException("seguridad_no_token");
	ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		return Util.obtenerRecurso(token, data, "http://localhost:8080/web-nacimiento/RADOP_acta_prim");
	}
	
	@RequestMapping(value = "/RADOP_datos_adop", method = RequestMethod.POST)
	public @ResponseBody String  RADOP_datos_adop(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException{
	String token=(String) sesion.getAttribute("access_token");
	if(token==null){
	GeneralException ge = new GeneralException("seguridad_no_token");
	ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		return Util.obtenerRecurso(token, data, "http://localhost:8080/web-nacimiento/RADOP_datos_adop");
	}
	
	@RequestMapping(value = "/RADOP_cert_med", method = RequestMethod.POST)
	public @ResponseBody String  RADOP_cert_med(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException{
	String token=(String) sesion.getAttribute("access_token");
	if(token==null){
	GeneralException ge = new GeneralException("seguridad_no_token");
	ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		return Util.obtenerRecurso(token, data, "http://localhost:8080/web-nacimiento/RADOP_cert_med");
	}
	
	@RequestMapping(value = "/RADOP_datos_madre", method = RequestMethod.POST)
	public @ResponseBody String  RADOP_datos_madre(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException{
	String token=(String) sesion.getAttribute("access_token");
	if(token==null){
	GeneralException ge = new GeneralException("seguridad_no_token");
	ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		return Util.obtenerRecurso(token, data, "http://localhost:8080/web-nacimiento/RADOP_datos_madre");
	}
	
	@RequestMapping(value = "/RADOP_datos_padre", method = RequestMethod.POST)
	public @ResponseBody String  RADOP_datos_padre(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException{
	String token=(String) sesion.getAttribute("access_token");
	if(token==null){
	GeneralException ge = new GeneralException("seguridad_no_token");
	ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		return Util.obtenerRecurso(token, data, "http://localhost:8080/web-nacimiento/RADOP_datos_padre");
	}

}
