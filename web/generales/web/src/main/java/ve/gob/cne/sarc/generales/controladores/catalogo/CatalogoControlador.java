package ve.gob.cne.sarc.generales.controladores.catalogo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;

/**
 * <p>Controlador para realizar consulta a los servicios cat&aacute;logo a trav&eacute;s del servicio de seguridad</p>
 * @author dgonzalez
 *
 */

@Controller
public class CatalogoControlador {

	@Autowired
	public CatalogoServicioCliente catalogoCliente; 

	@Autowired
	public FuncionarioServicioCliente funcionarioCliente; 
	
	@Autowired
	public SeguridadServicioCliente seguridadCliente;
	
	
	/**
	 * 
	 * @param data
	 * @param sesion
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/consultarPaisTodos", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody List<Pais> consultarPaises(@RequestBody Map<String,String> data, HttpSession sesion) throws Exception{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		List<Pais> paises = catalogoCliente.consultarPaisTodos(token);
		return paises;
	}
	
	@RequestMapping(value="/consultarEstadoPorPais", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody List<Estado> consultarEstadoPorPais(@RequestBody Map<String,String> data, HttpSession sesion) throws Exception{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		List<Estado> ciudades = catalogoCliente.consultarEstadoPorPais(data.get("codigo"), token);
		return ciudades;
	}
	
	@RequestMapping(value="/consultarMunicipioPorEstado", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody List<Municipio> consultarMunicipiosPorEstado(@RequestBody Map<String,String> data, HttpSession sesion) throws Exception{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		List<Municipio> ciudades = catalogoCliente.consultarMunicipioPorEstado(data.get("codigo"), token);
		return ciudades;
	}
	
	@RequestMapping(value="/consultarParroquiaPorMunicipio", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody List<Parroquia> consultarParroquiaPorMunicipio(@RequestBody Map<String,String> data, HttpSession sesion) throws Exception{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		List<Parroquia> ciudades = catalogoCliente.consultarParroquiaPorMunicipio(data.get("codigo"), token);
		return ciudades;
	}
    
	@RequestMapping(value="/consultarOficinasTodos", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody List<Oficina> consultarOficina(@RequestBody Map<String,String> data, HttpSession sesion) throws Exception{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		List<Oficina> oficinas = catalogoCliente.consultarOficinasTodos(token);
		return oficinas;
	}

	@RequestMapping(value="/detallesOficina", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String consultarOficina(HttpSession sesion) throws Exception{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		JSONObject jsonUser = seguridadCliente.getUsernameCliente(token);
		Funcionario funcionario=funcionarioCliente.buscarPorLogin(jsonUser.getString("username"), token);
		JSONObject modelo = new JSONObject();
		modelo.put("nombreUsuario",seguridadCliente.getUsernameCliente(token));
		modelo.put("oficina",funcionario.getOficina().getNombre());
		modelo.put("rolUsuario",funcionario.getUsuarios().get(0).getNombreRol());
		JSONObject respuesta = new JSONObject();
		respuesta.put("modelo", modelo);
		return respuesta.toString();
	}
}
