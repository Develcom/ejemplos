package ve.gob.cne.sarc.ecu.web.ecu.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ve.gob.cne.sarc.ciudadano.servicio.cliente.CiudadanoServicioCliente;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.ecu.web.ecu.Exceptions.ECUComunException;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * EcuController.java
 * 
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version Dec 22, 2016
 * @author Anderson Delgado
 */
@Controller
@RequestMapping(value = "/ecu")
public class EcuController {

    @Autowired
    private CiudadanoServicioCliente cliente;

    @Autowired
    private ParticipanteServicioCliente clienteParticipante;

    @Autowired
    private AdministradorPropiedades properties;

    private String token;

    private String tipoDocu = "tipoDocumento";

    private String ciudadanoNoEncontrado = "No existen datos del ciudadano";

    private String ciudadanoNoExiste = "ciudadano_no_existe";
    @Autowired
    private FuncionarioServicioCliente funcionarioServicioCliente;

    @Autowired
    private SeguridadServicioCliente seguridadServicioCliente;

    private static final Logger LOGGER = Logger.getLogger(EcuController.class.getName());

    /**
     * @param request
     * @param sesion
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/entrada", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public String entrada(HttpServletRequest request, HttpSession sesion) throws GenericException {

        token = request.getHeader("Authorization");
        if (token.contains("Bearer") || token.contains("bearer")) {
            token = token.substring(7);
        }
        sesion.setAttribute("access_token", token);
        token = (String) sesion.getAttribute("access_token");
        return properties.getProperty("sarc.web.ecu.consultarFicha.url");
    }

    /**
     * @param request
     * @return
     * @throws GenericException
     * @throws JSONException
     * @throws ECUComunException
     */
    @RequestMapping(value = "/consultarFicha", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public String consultarFicha(HttpServletRequest request) throws GenericException, JSONException, ECUComunException {
        this.usuarioPorRol();
        cliente = new CiudadanoServicioCliente();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject data = new JSONObject(request.getHeader("datos"));
        String tipoDoc = "";
        String nroDoc = "";
        String numero;
        if ("CED".equalsIgnoreCase(data.getString(tipoDocu))) {
            tipoDoc = data.getString("tipoDocCdano");
            nroDoc = data.getString("nroDocCed");
        } else if ("NUI".equalsIgnoreCase(data.getString(tipoDocu))) {
            nroDoc = data.getString("nroDoc");
        }

        if (!"".equalsIgnoreCase(tipoDoc.trim())) {
            numero = tipoDoc + "-" + nroDoc;
        } else {
            numero = nroDoc;
        }
        Ciudadano ciudadano = null;
        String dataJson = null;
        JSONObject modelo = null;
        try {
            ciudadano = cliente.consultarPorNumeroDocumento(numero, token);
            modelo = new JSONObject(mapper.writeValueAsString(ciudadano));
            // Busca las actas propias del participante
            List<Acta> actas0 = clienteParticipante.buscarParticipantesDocumento(numero, true, token);
            ArrayList<Acta> aActa0 = new ArrayList<>();
            for (Acta acta0 : actas0) {
                aActa0.add(acta0);
            }
            JSONArray jsonArr0 = new JSONArray(mapper.writeValueAsString(aActa0));
            modelo.put("aParticipante", jsonArr0);
            // Busca las actas vinculadas del participante
            List<Acta> actas = clienteParticipante.buscarParticipantesDocumento(numero, false, token);
            ArrayList<Acta> aActa = new ArrayList<>();
            for (Acta acta : actas) {
                aActa.add(acta);
            }
            JSONArray jsonArr = new JSONArray(mapper.writeValueAsString(aActa));
            modelo.put("aVinculadas", jsonArr);
            if (modelo.getString("documentoIdentidad") == null) {
                LOGGER.info("nulo");
                throw new ECUComunException(ciudadanoNoExiste, numero, ciudadanoNoEncontrado);

            }
            dataJson = modelo.toString();
        } catch (Exception e) {
            // error al encontrar numero
            LOGGER.info("Error al encontrar el numero: " + e);
            throw new ECUComunException(ciudadanoNoExiste, ciudadanoNoEncontrado, numero);
        }

        if (ciudadano == null) {
            LOGGER.info("No existe ciudadano: " + numero);
            throw new ECUComunException(ciudadanoNoExiste, ciudadanoNoEncontrado, numero);
        }
        LOGGER.info("data json: " + dataJson);
        return dataJson;
    }

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ECUComunException.class)
    @ResponseBody
    public ECUComunException handleCustomException(ECUComunException ex, HttpServletRequest request) {
        ServletContext context = request.getSession().getServletContext();
        String contexto = context.getRealPath("/resources/site/properties");
        LOGGER.info("\t ERROR MODULO: GESTION ECU");
        LOGGER.info("\t\t CODIGO: " + ex.getErrCode());
        LOGGER.info("\t\t MENSAJE: " + ex.getErrMsg());
        LOGGER.info("\t\t CLASE: " + ex.getClass());
        LOGGER.info("\t\t CAUSA: " + ex.getCause());
        ex.getMessage(contexto + "//messages.properties", ex.getErrCode());
        return ex;
    }

    /**
     * @throws ECUComunException
     */
    private void usuarioPorRol() throws ECUComunException {
        String rol = this.buscarValorProperties("roles.ecu.list");
        String[] roles = rol.split(",");

        JSONObject login = null;
        try {
            login = seguridadServicioCliente.getUsernameCliente(token);
        } catch (JSONException e) {
            LOGGER.info("error: " + e);
        }
        Funcionario obtenerDatosFuncionario = null;
        try {
            obtenerDatosFuncionario = funcionarioServicioCliente.buscarPorLogin(login.getString("username"), token);
        } catch (JSONException e) {
            LOGGER.info("error: " + e);
        }

        String codRol = obtenerDatosFuncionario.getUsuarios().get(0).getCodigoRol();
        if (Arrays.asList(roles).contains(codRol)) {
            LOGGER.info("rol Autorizado: " + codRol);
        } else {
            throw new ECUComunException("error_rol_usuario", "Usuario no autorizado", "");
        }
    }

    /**
     * @param clave
     * @return
     */
    private String buscarValorProperties(String clave) {
        String valor = null;
        LOGGER.info("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            LOGGER.info("Error leyendo properties: " + ex);
        }
        LOGGER.info("buscarValorProperties --> valor --> " + valor);

        return valor;
    }
}