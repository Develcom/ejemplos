package ve.gob.cne.sarc.atencionComunitaria.web.atencionComunitaria.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import ve.gob.cne.sarc.atencionComunitaria.web.atencionComunitaria.Exceptions.AtencionComunException;
import ve.gob.cne.sarc.atencionComunitaria.web.atencionComunitaria.form.atencionCiudadano.AtencionFormulario;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;

import ve.gob.cne.sarc.comunes.catalogo.EntePublico;
import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Usuario;
import ve.gob.cne.sarc.comunes.solicitante.DocumentoEntePublico;
import ve.gob.cne.sarc.comunes.solicitante.Solicitante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.gpt.paquete.ManagePackage;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * <p>
 * Controlador del modulo atencion comunitaria
 * </p>
 */
@Controller
@RequestMapping(value = "/atComun")
public class AtencionCiudadanoControlador {

    @Autowired
    private ServletContext context;

    @Autowired
    private ManagePackage managePackage;

    @Autowired
    private AdministradorPropiedades properties;

    @Autowired
    private CatalogoServicioCliente catalogoServicioCliente;

    @Autowired
    private FuncionarioServicioCliente funcionarioServicioCliente;

    @Autowired
    private SolicitudServicioCliente solicitudServicioCliente;

    @Autowired
    private SeguridadServicioCliente seguridadServicioCliente;

    private String variableToken;

    Funcionario funcionario = new Funcionario();

    Solicitud solicitud = new Solicitud();

    /**
     * <p>
     * Controlador que redirecciona un html
     * </p>
     * 
     * @param modelo
     * @param request
     * @param sesion
     * @return La ruta a iniciar
     * @throws GenericException
     */
    @RequestMapping(value = "/entrada", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public String entrada(HttpServletRequest request, HttpSession sesion) throws GenericException {
        String token;
        token = request.getHeader("Authorization");
        if (token.contains("Bearer") || token.contains("bearer")) {
            token = token.substring(7);
        }
        sesion.setAttribute("access_token", token);
        variableToken = (String) sesion.getAttribute("access_token");
        String html;
        html = properties.getProperty("endPointFormAtencionInicio");
        return html;
    }

    /**
     * <p>
     * Controlador que redirecciona un html
     * </p>
     * 
     * @param modelo
     * @param request
     * @param sesion
     * @return La ruta a iniciar
     * @throws GenericException
     */
    @RequestMapping(value = "/scrmDetalle", method = RequestMethod.POST)
    public String scrmDetalle() throws GenericException {
        String html;
        html = properties.getProperty("endPointFormCrearSolicitud");
        return html;
    }

    /**
     * <p>
     * Controlador que redirecciona un html
     * </p>
     * 
     * @return objeto formulario
     * @throws AtencionComunException
     */
    @RequestMapping(value = "/ciudadanoDiv", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public AtencionFormulario loadCiudadanoAcordeon() throws AtencionComunException {
        AtencionFormulario formulario = new AtencionFormulario();
        List<TipoDocumento> list = null;
        try {

            list = catalogoServicioCliente.consultarTipoDocumentoTodos("D", variableToken);
        } catch (HttpClientErrorException htpEx) {
            throw new AtencionComunException("error_notFound_service" + htpEx);
        } catch (Exception e) {
            throw new AtencionComunException("generalEx" + e);
        }
        if (list == null || list.isEmpty()) {
            throw new AtencionComunException("documento_c001");
        }
        formulario.setDocumentos(list);
        return formulario;
    }

    /**
     * <p>
     * Controlador que retorne mensaje de error
     * </p>
     * 
     * @return AtencionComunException ex
     * @param AtencionComunException
     *            ex
     * @param HttpServletRequest
     *            request
     */
    @ExceptionHandler(AtencionComunException.class)
    @ResponseBody
    public AtencionComunException handleCustomException(AtencionComunException ex, HttpServletRequest request) {
        context = request.getSession().getServletContext();
        String contexto = context.getRealPath("/resources/site/properties");
        ex.getMessage(contexto + "//messages.properties", ex.getErrCode());
        return ex;
    }

    /**
     * <p>
     * Controlador que redirecciona un html
     * </p>
     * 
     * @return objeto formulario
     * @throws AtencionComunException
     * @throws GenericException
     */
    @RequestMapping(value = "/enteDiv", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public AtencionFormulario loadEnteAcordeon() throws GeneralException, AtencionComunException {
        if (variableToken == null) {
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n" + ge);
            throw ge;
        }
        AtencionFormulario formulario = new AtencionFormulario();
        EntePublico entePublico = new EntePublico();
        entePublico.setNombre("ewewq");
        List<EntePublico> listaEntesPublicos = null;
        List<TipoDocumento> list = null;
        try {
            listaEntesPublicos = catalogoServicioCliente.consultarEntePublicoTodos(variableToken);
            list = catalogoServicioCliente.consultarTipoDocumentoTodos("E", variableToken);
        } catch (HttpClientErrorException htpEx) {
            throw new AtencionComunException("error_notFound_service" + htpEx);
        } catch (Exception e) {
            throw new AtencionComunException("generalEx" + e);
        }
        if (listaEntesPublicos.isEmpty() || list.isEmpty()) {
            throw new AtencionComunException("documento_c001");
        }
        formulario.setEntes(listaEntesPublicos);
        formulario.setDocumentosEnte(list);
        return formulario;
    }

    /**
     * <p>
     * Controlador que redirecciona un html
     * </p>
     * 
     * @return objeto formulario
     * @throws GeneralException
     * @throws JSONException
     * @throws AtencionComunException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/nuevaSolicitud", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public String nuevaSolicitud() throws GeneralException, JSONException, AtencionComunException,
            JsonGenerationException, JsonMappingException, IOException {
        if (variableToken == null) {
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
        AtencionFormulario formulario = new AtencionFormulario();
        JSONObject jsonUser = seguridadServicioCliente.getUsernameCliente(variableToken);
        List<Modulo> listModulo = null;
        try {
            funcionario = funcionarioServicioCliente.buscarPorLogin(jsonUser.getString("username"), variableToken);
            if (funcionario == null) {
                throw new AtencionComunException("funcionario_001");
            }
            listModulo = catalogoServicioCliente.consultarModuloPorOficina(funcionario.getOficina().getCodigo(),
                    variableToken);
        } catch (HttpClientErrorException htpEx) {
            throw new AtencionComunException("error_notFound_service" + htpEx);
        } catch (Exception e) {
            throw new AtencionComunException("generalEx" + e);
        }
        if (listModulo == null || listModulo.isEmpty()) {
            throw new AtencionComunException("modulo_001");
        }
        formulario.setModulos(listModulo);

        ObjectMapper mapper = new ObjectMapper();
        mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

        String json = mapper.writeValueAsString(formulario);
        return json;

    }

    /**
     * <p>
     * Controlador que redirecciona un html
     * </p>
     * 
     * @return objeto formulario
     * @param String
     *            codigo
     * @param String
     *            data
     * @throws GeneralException
     * @throws AtencionComunException
     */
    @RequestMapping(value = "/consultTramite/{codigo}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public AtencionFormulario consultTramite(@PathVariable(value = "codigo") String codigo, @RequestBody String data)
            throws GeneralException, AtencionComunException {
        if (variableToken == null) {
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
        AtencionFormulario formulario = new AtencionFormulario();
        List<Tramite> listTramite = null;
        try {
            listTramite = catalogoServicioCliente.consultarTramitePorModulo(codigo, data, variableToken);
        } catch (HttpClientErrorException htpEx) {
            throw new AtencionComunException("error_notFound_service" + htpEx);
        } catch (Exception e) {
            throw new AtencionComunException("generalEx" + e);
        }
        if (listTramite.isEmpty()) {
            throw new AtencionComunException("tramite_001");
        }
        formulario.setTramites(listTramite);
        return formulario;
    }

    /**
     * <p>
     * Controlador que redirecciona un html
     * </p>
     * 
     * @return objeto formulario
     * @param String
     *            datos
     * @throws GeneralException
     * @throws JSONException
     * @throws AtencionComunException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/finCreacionSolicitud", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public String finCreacionSolicitud(@RequestBody String datos) throws GeneralException, JSONException,
            AtencionComunException, JsonGenerationException, JsonMappingException, IOException {
        if (variableToken == null) {
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> list = new HashMap<>();
        JSONObject data = new JSONObject(datos);
        JSONObject jsonUser = seguridadServicioCliente.getUsernameCliente(variableToken);
        if (data.has("name")) {
            Solicitante solicitante = new Solicitante();
            if (data.has("tipo")) {
                solicitante.setTipo(data.getString("tipo").trim());
            }
            if ("D".equalsIgnoreCase(solicitante.getTipo())) {
                TipoDocumento tipoDocumento = new TipoDocumento();
                if (data.has("tipoDocumento")) {
                    tipoDocumento.setCodigo(data.getString("tipoDocumento").trim());
                }
                List<DocumentoIdentidad> listDoc = new ArrayList();
                DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
                if (data.has("numeroDocumento")) {
                    documentoIdentidad.setNumero(data.getString("numeroDocumento").trim());
                }
                documentoIdentidad.setTipoDocumento(tipoDocumento);
                listDoc.add(documentoIdentidad);
                Ciudadano ciudadano = new Ciudadano();
                if (data.has("primerNombre")) {
                    ciudadano.setPrimerNombre(data.getString("primerNombre").trim());
                }
                if (data.has("segundoNombre")) {
                    ciudadano.setSegundoNombre(data.getString("segundoNombre").trim());
                }
                if (data.has("primerApellido")) {
                    ciudadano.setPrimerApellido(data.getString("primerApellido").trim());
                }
                if (data.has("segundoApellido")) {
                    ciudadano.setSegundoApellido(data.getString("segundoApellido").trim());
                }
                ciudadano.setDocumentoIdentidad(listDoc);
                solicitante.setCiudadano(ciudadano);
            }
            if ("E".equalsIgnoreCase(solicitante.getTipo())) {
                DocumentoEntePublico docEnte = new DocumentoEntePublico();
                if (data.has("numeroOficio")) {
                    docEnte.setNumero(data.getString("numeroOficio").trim());
                }
                if (data.has("tipoDocEnte")) {
                    docEnte.setTipoDocumentoEntePublico(data.getString("tipoDocEnte").trim());
                }
                EntePublico entepublico = new EntePublico();
                if (data.has("tipoEnte")) {
                    entepublico.setCodigo(data.getString("tipoEnte").trim());
                }
                docEnte.setEntePublico(entepublico);
                solicitante.setDocumentoEntePublico(docEnte);
            }

            if ("nuevaSolicitud".equalsIgnoreCase(data.getString("name").trim())) {

                Modulo modulo = new Modulo();
                if (data.has("modulo")) {
                    modulo.setCodigo(data.getString("modulo").trim());
                }

                solicitud.setModulo(modulo);
                solicitud.setSolicitante(solicitante);
                Tramite tramite = new Tramite();
                if (data.has("tramite")) {
                    tramite.setCodigo(data.getString("tramite").trim());
                }
                solicitud.setTramite(tramite);

                List<Usuario> usuarios = new ArrayList<Usuario>();
                Usuario user = new Usuario();
                user.setNombre(jsonUser.getString("primerNombre"));
                usuarios.add(user);

                funcionario = funcionarioServicioCliente.buscarPorLogin(jsonUser.getString("username"), variableToken);
                solicitud.setFuncionario(funcionario);
                Solicitud result;

                result = solicitudServicioCliente.crearSolicitud(solicitud, variableToken);

                if (result.getNumeroSolicitud() != null) {
                    list.put("state", "detalleSolicitud");
                    list.put("id", result.getNumeroSolicitud());

                    managePackage.generarPaquete(result, modulo.getCodigo(), "Crear Solicitud " + modulo.getCodigo(),
                            funcionario);

                } else {
                    throw new AtencionComunException("crearSol_001");
                }
            }
            if ("consultaSolicitud".equalsIgnoreCase(data.getString("name").trim())) {
                list.put("bln", "0");
                String tipoConsulta = "";
                if (data.has("tipoConsulta")) {
                    tipoConsulta = data.getString("tipoConsulta").trim();
                }
                if ("nroOficioSolicitud".equalsIgnoreCase(tipoConsulta)) {
                    list.put("tipoSolicitante", "E");
                    if (data.has("documentoOrSolicitud")) {
                        if ("numeroSolicitud".equalsIgnoreCase(data.getString("documentoOrSolicitud").trim())) {
                            if (data.has("numeroSolicitud")) {
                                list.put("state", "detalleSolicitud");
                                list.put("id", data.getString("numeroSolicitud").trim());
                            }
                        }
                        if ("numeroDocumento".equalsIgnoreCase(data.getString("documentoOrSolicitud").trim())) {
                            if (data.has("numeroConsultDocumento")) {
                                list.put("state", "listSolicitud");
                                list.put("tipo", data.getString("tipoDocEnte").trim());
                                list.put("nro", data.getString("numeroConsultDocumento").trim());
                            }
                        }
                    }
                }
                if ("documentoIdent".equalsIgnoreCase(tipoConsulta)) {
                    list.put("state", "listSolicitud");
                    list.put("tipoSolicitante", "D");
                    if (data.has("tipoDocumentoSolicitud")) {
                        list.put("tipo", data.getString("tipoDocumentoSolicitud").trim());

                    }
                    if (data.has("nroDocumentoSolicitud")) {
                        list.put("nro", data.getString("nroDocumentoSolicitud").trim());
                    }
                }
                if (list.size() == 1) {
                    throw new AtencionComunException("atComunFail_001");
                }
            }
        }
        return mapper.writeValueAsString(list);
    }

    /**
     * <p>
     * Controlador que redirecciona un html
     * </p>
     * 
     * @return objeto formulario
     * @param String
     *            tipoSolicitante @PathVariable tipoSolicitante
     * @param String
     *            tipo @PathVariable tipo
     * @param String
     *            nro @PathVariable nro
     * @throws GeneralException
     * @throws JSONException
     * @throws AtencionComunException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/listSolicitudes/{tipoSolicitante}/{tipo}/{nro}", method = RequestMethod.POST)
    @ResponseBody
    public List<Solicitud> listaSolicitudes(@PathVariable(value = "tipoSolicitante") String tipoSolicitante,
            @PathVariable(value = "tipo") String tipo, @PathVariable(value = "nro") String nro) throws JSONException,
            AtencionComunException, GeneralException {
        if (variableToken == null) {
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
        List<Solicitud> list = null;
        try {
            if (tipoSolicitante != null && tipo != null && nro != null) {
                list = solicitudServicioCliente.consultarPorNumDocAtendido(tipoSolicitante, tipo, nro, variableToken);
            }
        } catch (HttpClientErrorException htpEx) {
            throw new AtencionComunException("error_notFound_service" + htpEx);
        } catch (Exception e) {
            throw new AtencionComunException("generalEx" + e);
        }
        if (list.isEmpty()) {
            throw new AtencionComunException("solicitud_001");
        }
        return list;
    }

    /**
     * <p>
     * Controlador que devuelve una lista
     * </p>
     * 
     * @param String nro
     * @param sesion
     * @return list
     * @throws Exception
     */
    @RequestMapping(value = "/listSolicitudes/{nro}", method = RequestMethod.POST)
    @ResponseBody
    public List<Solicitud> listaSolicitudes2(@PathVariable(value = "nro") String nro) throws AtencionComunException,
            GeneralException {
        if (variableToken == null) {
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
        List<Solicitud> list = null;
        try {
            if (nro != null) {
                list = solicitudServicioCliente.consultarPorNumDocAtendido("", "", nro.replace(":", ""), variableToken);
            }
            for (Solicitud sol : list) {
                sol.getFechaInicio();
            }
        } catch (HttpClientErrorException htpEx) {
            throw new AtencionComunException("error_notFound_service" + htpEx);
        } catch (Exception e) {
            throw new AtencionComunException("generalEx" + e);
        }
        if (list.isEmpty()) {
            throw new AtencionComunException("solicitud_001");
        }
        return list;
    }

    /**
     * <p>
     * Consulta el listado de solicitudes directamente al servicio Solicitud
     * </p>
     *
     * @return
     * @throws AtencionComunException
     * @throws GeneralException
     */
    @RequestMapping(value = "/listSolicitudesTotal", method = RequestMethod.POST)
    @ResponseBody
    public List<Solicitud> listSolicitudesTotal() throws AtencionComunException, GeneralException {
        if (variableToken == null) {
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }
        List<Solicitud> list = null;
        try {
            list = solicitudServicioCliente.consultarSolicitudesTodas(variableToken);
        } catch (HttpClientErrorException htpEx) {
            throw new AtencionComunException("error_notFound_service" + htpEx);
        } catch (Exception e) {
            throw new AtencionComunException("generalEx" + e);
        }
        if (list.isEmpty()) {
            throw new AtencionComunException("documento_c001");
        }
        return list;
    }

    /**
     * <p>
     * Controlador que devuelve un objeto
     * </p>
     * 
     * @param String id
     * @param sesion
     * @return objeto solicitud
     * @throws AtencionComunException
     * @throws GeneralException
     */
    @RequestMapping(value = "/consultDetalle/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Solicitud consultDetalle(@PathVariable(value = "id") String id) throws AtencionComunException,
            GeneralException {
        if (variableToken == null) {
            GeneralException ge = new GeneralException("seguridad_no_token");
            ge.setErrMsg("Error de Autorizaci&oacute;n");
            throw ge;
        }

        try {
            if (id != null && !"".equalsIgnoreCase(id.trim())) {
                solicitud = solicitudServicioCliente.consultarDetalleSolicitud(id.replace(":", ""), variableToken);
            }
        } catch (HttpClientErrorException htpEx) {
            throw new AtencionComunException("error_notFound_service" + htpEx);
        } catch (Exception e) {
            throw new AtencionComunException("generalEx" + e);
        }
        if (solicitud.getNumeroSolicitud() == null || "".equalsIgnoreCase(solicitud.getNumeroSolicitud())) {
            throw new AtencionComunException("documento_c002");
        }
        return solicitud;
    }

}
