package ve.gob.cne.sarc.generales.controladores;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.cliente.libro.LibroServicioCliente;
import ve.gob.cne.sarc.clienteWeb.servicio.cliente.ClienteWebServicioCliente;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.clienteWeb.servicio.cliente.ClienteWebServicioCliente;


@Controller
public class PrincipalControlador {
	final static Logger logger = Logger.getLogger(PrincipalControlador.class.getName());
	
	@Autowired
	ServletContext context;
	
	@Autowired
	FuncionarioServicioCliente funcionarioCliente;
	
	@Autowired
	SolicitudServicioCliente solicitudCliente;
		
	@Autowired
	LibroServicioCliente libroCliente;
	
	@Autowired
	SeguridadServicioCliente seguridadCliente;
	
	@Autowired
	CatalogoServicioCliente catalogoCliente;
	
    @Autowired
    AdministradorPropiedades properties;
    
    @Autowired
    ClienteWebServicioCliente clienteWeb;
    
	
	/**
	 * <p>Controlador de acceso a trav&eacute;s del login</p> 
	 * @param usr: Nombre de usuario
	 * @param pass: Clave de acceso
	 * @param response: response Http
	 * @param sesion
	 * @return La ruta a iniciar o la palabra 'error'en caso de un intento fallido
	 * @throws JSONException
	 */
	@RequestMapping(value= { "/ini/{usr}/{pass}" }, method=RequestMethod.POST)
	public @ResponseBody ArrayList<String> iniciarPost(@PathVariable(value="usr") String usr, @PathVariable(value="pass") String pass, HttpServletResponse response, HttpSession sesion) throws JSONException{
		JSONObject arr = seguridadCliente.obtenerAcceso(usr,pass);
		
		sesion.setAttribute("access_token", arr.get("access_token"));
		response.setHeader("Authorization", "Bearer "+arr.get("access_token"));
		ArrayList<String> list = new ArrayList<>();
		list.add(arr.get("access_token")==null ? "error" : "/web-generales/iniciar");
		return list;
	}
	
	
	@RequestMapping(value= { "/error" }, method=RequestMethod.GET)
	public String presentarError(Model modelo, HttpServletRequest request, HttpServletResponse response, HttpSession sesion) throws RestClientException, MalformedURLException, Exception{
		return "errorSeguridad";
	}

	/**
	 * <p>Devuelve una ruta creada desde un metodo</p> 
	 * @param data
	 * @param request
	 * @param response: response Http
	 * @param sesion
	 * @return La ruta a iniciar o la palabra 'error'en caso de un intento fallido
	 * @throws JSONException
	 * @throws GeneralException
	 * @throws RestClientException
	 * @throws MalformedURLException
	 */
	@RequestMapping(value= { "/direccionesControlador" }, method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String direccionesControlador(@RequestBody String data, HttpServletRequest request, HttpServletResponse response, HttpSession sesion) throws GeneralException, RestClientException, MalformedURLException, JSONException{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		JSONObject mDatos = new JSONObject(data);
	
	return Util.obtenerRecurso(token, data, mDatos.getString("ruta"));
	}
	

		
	/**
	 * <p>Controlador que inicia el m&oacute;dulo de atenci&oacute;n comunitaria</p>
	 * @param request
	 * @param response
	 * @param sesion
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@RequestMapping(value= { "/iniciarAtencionComunitaria" }, method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody String iniciarAtencionComunitaria(HttpServletRequest request, HttpServletResponse response, HttpSession sesion) throws NumberFormatException, Exception{

		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		
		JSONObject jsonUser = seguridadCliente.getUsernameCliente(token);//se cambio el metodo a jsonObject 
		Funcionario funcionario = funcionarioCliente.buscarPorLogin(jsonUser.getString("username"), token);//este metodo recibe un String, String.
		SimpleDateFormat fd = new SimpleDateFormat("yyyy");
		SimpleDateFormat fdia = new SimpleDateFormat("dd-MM-yyyy");
		String fdFormat = null;
		String fdiaFormat = null;
		fdFormat = fd.format(new Date());
		fdiaFormat = fdia.format(new Date());
		String estatus = "";
		estatus= properties.getProperty("servicios.libro.estatus.abierto");
		boolean libroAbierto = libroCliente.validarLibrosActivosPorOficina(funcionario.getOficina().getCodigo(), estatus, Integer.valueOf(fdFormat), token);
		long estatusDiario = Long.parseLong(estatus);
		boolean libroDiarioAbierto = libroCliente.validarLibroDiarioActivoPorOficina(funcionario.getOficina().getCodigo(), fdiaFormat, estatusDiario, token);

		if(!libroAbierto){
			GeneralException ge=new GeneralException("libroActivo_001");
			ge.setErrMsg("No hay libros abiertos");
			throw ge;
		}else if(!libroDiarioAbierto){
            GeneralException ge=new GeneralException("libroDiarioActivo_001");
            ge.setErrMsg("No hay libros diarios abiertos");
            throw ge;
        }
				
		String url =clienteWeb.consultarEndPointHtml(token);
		return Util.conectarRutaUrl(url);        
	}
	
	/**
	 * <p>Controlador que lista las solicitudes por oficina</p>
	 * @param request
	 * @param response
	 * @param sesion
	 * @return
	 * @throws JSONException
	 * @throws GeneralException
	 * @throws RestClientException
	 * @throws MalformedURLException
	 */
	@RequestMapping(value= { "/listarSolicitudes" }, method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody List<Solicitud> listarSolicitudes(HttpServletRequest request, HttpServletResponse response, HttpSession sesion) throws GeneralException, RestClientException, MalformedURLException, JSONException{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		JSONObject jsonUser = seguridadCliente.getUsernameCliente(token);
		Funcionario funcionario = funcionarioCliente.buscarPorLogin(jsonUser.getString("username"), token);//pasar token
		String codigoRol=funcionario.getUsuarios().get(0).getCodigoRol();
		String loginUsuario=funcionario.getUsuarios().get(0).getNombre();
		List<Solicitud> lista=solicitudCliente.consultarPorUsuario(loginUsuario, codigoRol, token);//se pasa el token al servicio
        return lista;
	}

	/**
	 * <p>Inicia el procesamiento de la solicitud seg&uacuten el estado</p>
	 * @param data : {estado : estadoSolicitud, tramite : codigoTramite, numero : numeroSolicitud}
	 * @param request
	 * @param response
	 * @param sesion
	 * @return
	 * @throws GeneralException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException 
	 */
	@RequestMapping(value= { "/iniciarModuloSegunEstado" }, method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody ArrayList<String> iniciarModuloSegunEstado(@RequestBody Map<String,String> data, HttpServletRequest request, HttpServletResponse response, HttpSession sesion) throws GeneralException, RestClientException, IOException, JSONException, GenericException{
		String token=(String) sesion.getAttribute("access_token");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
		ArrayList<String> respuestaJson= new ArrayList<String>();
		String estadoSolicitud=(String) data.get("estado");
		String tramite=(String) data.get("tramite");
		String recursoValido = null;
		String nombreModulo=tramite;
		String html;
		logger.info("Toda la data: "+data);
		logger.info("Estado Solicitud: "+estadoSolicitud);
		logger.info("Tramite: "+tramite);
		logger.info("Nombre Modulo: "+nombreModulo);
		if("RNACI".equalsIgnoreCase(nombreModulo) && "PVA".equalsIgnoreCase(estadoSolicitud)){
			logger.info("Entro");
			logger.info("Token: "+token);
			html = clienteWeb.consultarEndPointHtmlIniciarVerificacion(token);
			html = html.replace("NOMBRECONTROLADOR", "VerificacionControlador");
			respuestaJson.add(html);
			logger.info("La respuesta Json: "+respuestaJson);
		}else
		if("RNACI".equalsIgnoreCase(nombreModulo) && "PI".equalsIgnoreCase(estadoSolicitud)){
			respuestaJson.add("");
		}else if("RNACI".equalsIgnoreCase(nombreModulo) && "NC".equalsIgnoreCase(estadoSolicitud)){
			html =clienteWeb.consultarEndPointHtmlCorreccion(token);
			respuestaJson.add(Util.obtenerRecurso(token, data, html));
		}
		else if("PA".equalsIgnoreCase(estadoSolicitud)){		
			String url =clienteWeb.consultarEndPointHtmlAut(token);		
			Log.info("************url "+url);
			String retorno = Util.conectarRutaUrl(url);
			respuestaJson.add(retorno);
			
		}else if ( "RDEFU".equals(tramite)){
			String plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general2.html"));
			 if("AB".equalsIgnoreCase(estadoSolicitud)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFU");
			}else if("PV".equalsIgnoreCase(estadoSolicitud)){
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUPV");
			}else if("NC".equalsIgnoreCase(estadoSolicitud)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUNoConforme");
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
			}else if("PI".equalsIgnoreCase(estadoSolicitud)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUPI");
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
			}else if("PEA".equalsIgnoreCase(estadoSolicitud)){
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUPEA");
			}else if("PPD".equalsIgnoreCase(estadoSolicitud)){
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUPPD");
			}else if("PVR".equalsIgnoreCase(estadoSolicitud)){
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUPVR");
			}else if("PPI".equalsIgnoreCase(estadoSolicitud)){
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUPPI");
			}else if("NCA".equalsIgnoreCase(estadoSolicitud)){
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUNoConformeActa");
			}else if("PCA".equalsIgnoreCase(estadoSolicitud)){
				plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRDEFUPCA");
			}
			respuestaJson.add(plantilla);	
		}else {
			

			String plantilla = Util.leerArchivo(context.getRealPath("/resources/pages/template_general.html"));
			if("AB".equalsIgnoreCase(estadoSolicitud) && "EPDIC".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorEPDIC");
			}else if("PV".equalsIgnoreCase(estadoSolicitud) && "EPDIC".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorEPDIC_PV");
			}else if("NC".equalsIgnoreCase(estadoSolicitud) && "EPDIC".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorEPDIC_NC");
			}else if("PPD".equalsIgnoreCase(estadoSolicitud) && "EPDIC".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorEPDIC_PPD");
			}else if("AB".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RA2pControllador");
			}else if("PV".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RADOP_PvController");
			}else if("NC".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RADOP_NoConformeController");
			}else if("PEA".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RADOP_ActaController");
			}else if("PVR".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RADOP_RV_ActaController");
			}else if("NCA".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RADOP_NoConformeActaController");
			}else if("PPD".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RADOP_CargarDocController");
			}else if("CDJ".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RADOP_DJ_CargarDocController");
			}else if("PCN".equalsIgnoreCase(estadoSolicitud) && "RADOP".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RADOP_CargarNotificacionController");
			}else if("AB".equalsIgnoreCase(estadoSolicitud) && "RNACI".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RNACI1_Controller");
			}else if("PV".equalsIgnoreCase(estadoSolicitud) && "RNACI".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RNACI2_Controller");
			}else if("PEA".equalsIgnoreCase(estadoSolicitud) && "RNACI".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "RNACI_Acta_Controller");
			}else if("AB".equalsIgnoreCase(estadoSolicitud) && "RRECO".equals(tramite)){
			   plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRRECO_AB");
			}else if("AB".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
               plantilla = plantilla.replace("NOMBRECONTROLADOR", "controladorRRFIL");
			}else if("PEA".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
			   plantilla = plantilla.replace("NOMBRECONTROLADOR", "PEA_RRFILControlador");
			}else if("PPD".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
				plantilla = plantilla.replace("NOMBRECONTROLADOR", "PDD_REFILController");
			}else if("PCA".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
            plantilla = plantilla.replace("NOMBRECONTROLADOR", "controlador_PCA_RRFIL");
			}else if("PCDO".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
            plantilla = plantilla.replace("NOMBRECONTROLADOR", "controlador_PCDO_RRFIL");
			}else if("PVO".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
            plantilla = plantilla.replace("NOMBRECONTROLADOR", "controlador_PVO_RRFIL");         
			}else if("PVR".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
            plantilla = plantilla.replace("NOMBRECONTROLADOR", "PVR_RRFILControlador");
			}else if("PV".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
            plantilla = plantilla.replace("NOMBRECONTROLADOR", "PV_RRFILControlador");
			}else if("NC".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
            plantilla = plantilla.replace("NOMBRECONTROLADOR", "NC_RRFILControlador");
			}else if("NCA".equalsIgnoreCase(estadoSolicitud) && "RRFIL".equals(tramite)){
            plantilla = plantilla.replace("NOMBRECONTROLADOR", "NCA_RRFILControlador");       
            
         }
			respuestaJson.add(plantilla);	
		}			
			return respuestaJson;
	}
	
	/**
	 * <p>Controlador de acceso al modulo consultar libro</p> 
	 * @param data
	 * @param sesion
	 * @return La ruta a iniciar 
	 * @throws GeneralException
	 */
	@RequestMapping(value = "/consultarLibro", method = RequestMethod.POST)
	public @ResponseBody String  consultarLibro(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException, GenericException{
		String token=(String) sesion.getAttribute("access_token");
		String urli=properties.getProperty("sarc.web.libro.entrada.url");
		if(token==null){
			GeneralException ge = new GeneralException("seguridad_no_token");
			ge.setErrMsg("Error de Autorizaci&oacute;n");
			throw ge;
		}
	        String url =clienteWeb.consultarEndPointHtmlPorProperties(urli,token,"POST");
	        logger.info("****************url consultar "+url);
		return Util.obtenerRecursoPorMetodo(token, "operacion", data, url, "POST");
	}
	
	/**
	 * <p>Controlador de acceso al modulo Gestion ECU</p> 
	 * @param response
	 * @param sesion
	 * @return La ruta a iniciar 
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException 
	 * @throws GeneralException 
	 * @throws GenericException 
	 */
	@RequestMapping(value= { "/iniciarGestionECU" }, method=RequestMethod.GET)
	public @ResponseBody String iniciarGestionECU(HttpServletResponse response, HttpSession sesion) throws RestClientException, IOException, JSONException, GeneralException, GenericException{
	    String token=(String) sesion.getAttribute("access_token");
	    String rol=properties.getProperty("roles.ecu.list");
	    String[] split=rol.split(",");
	    
        JSONObject login = seguridadCliente.getUsernameCliente(token);
        Funcionario obtenerDatosFuncionario = funcionarioCliente.buscarPorLogin(login.getString("username"),
                token);

        String codRol = obtenerDatosFuncionario.getUsuarios().get(0).getCodigoRol();
        if(Arrays.asList(split).contains(codRol)){
            logger.info("rol Autorizado: " + codRol);    
        }else{
            GeneralException ge = new GeneralException("error_autorizar");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }        
                        
        if(token==null){
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
        Map<String, String> ob = null;
        String protocol = properties.getProperty("sarc.web.servidor.atc");
        String url= protocol+"/web-ecu/view/gestionECU.html";
        return Util.obtenerRecursoPorMetodo(token, "",ob , url, "GET");
	}
	
	@ExceptionHandler(GeneralException.class)
	public @ResponseBody String handleCustomException(GeneralException ex, HttpServletRequest request ) {
		ServletContext context = request.getSession().getServletContext();
		String contexto = context.getRealPath("/resources/site/properties");
		ex.getMessage(contexto + "//messages.properties", ex.getErrCode());
		String html=null;
		try {
			html= Util.leerArchivo(context.getRealPath("/resources/pages/error/pagina_error.html"));
			html.replace("MENSAJE_ERROR", ex.getErrMsg());
		} catch (IOException e) {
			
			logger.info("*********************Error en la lectura del archivo de error");
			e.printStackTrace();
		}
		return 	html.replace("MENSAJE_ERROR", ex.getErrMsg());
	}
	
	@ExceptionHandler(Exception.class)
	public String handlerHtml(Exception ex) {
		logger.info("**********ERROR: "+ex.getMessage());
		ex.printStackTrace();
		return "errorGeneral";
	}
	
	public String[] obtenerProperty(String propiedad) throws IOException{
		String ruta=context.getRealPath("/resources/resources.properties");
		logger.info("********************Ruta del archivo de propiedades********"+ruta);
		Properties prop=Util.obtenerPropiedad(ruta);
		return prop.getProperty(propiedad).split(",");
	}
	
	@RequestMapping(value = "/consultarFicha", method = RequestMethod.POST)
    public @ResponseBody String  consultarFicha(@RequestBody Map<String, String> data,HttpSession sesion) throws GeneralException, GenericException{
        String token=(String) sesion.getAttribute("access_token");
        String urli=properties.getProperty("sarc.web.ecu.entrada.url");
        if(token==null){
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
            String url =clienteWeb.consultarEndPointHtmlPorProperties(urli,token,"POST");
            logger.info("****************url consultar "+url);
        return Util.obtenerRecursoPorMetodo(token, "datos", data, url, "POST");
    }
	
	@RequestMapping(value= { "/iniciarGestionLibro" }, method=RequestMethod.GET)
    public @ResponseBody String iniciarGestionLibro(HttpServletResponse response, HttpSession sesion) throws RestClientException, IOException, GenericException, GeneralException, JSONException{
        String token=(String) sesion.getAttribute("access_token");
        String rol=properties.getProperty("roles.reg");
        JSONObject login = seguridadCliente.getUsernameCliente(token);
        Funcionario obtenerDatosFuncionario = funcionarioCliente.buscarPorLogin(login.getString("username"),
                token);

        String codRol = obtenerDatosFuncionario.getUsuarios().get(0).getCodigoRol();
        if (rol.equalsIgnoreCase(codRol)){ logger.info("rol Autorizado: " + codRol);}
        else{
            GeneralException ge = new GeneralException("error_autorizar");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
            }
        
        if(token==null){
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
        Map<String, String> ob = null;
        String protocol = properties.getProperty("sarc.web.servidor.atc");
        String url= protocol+"/web-gestionLibros/view/formularioLibro.html";
        return Util.obtenerRecursoPorMetodo(token, "",ob , url, "GET");
    }

		
}
