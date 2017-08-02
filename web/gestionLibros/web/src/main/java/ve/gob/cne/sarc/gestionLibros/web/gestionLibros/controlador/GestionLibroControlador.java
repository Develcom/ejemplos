package ve.gob.cne.sarc.gestionLibros.web.gestionLibros.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ve.gob.cne.sarc.cliente.libro.LibroServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.catalogo.TipoLibro;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.reporte.GenerarActas;
import ve.gob.cne.sarc.gestionLibros.web.gestionLibros.constantes.ConstantesLibro;
import ve.gob.cne.sarc.gestionLibros.web.gestionLibros.excepcion.GestionLibroException;
import ve.gob.cne.sarc.gestionLibros.web.gestionLibros.formulario.LibroCivilFormulario;
import ve.gob.cne.sarc.gestionLibros.web.gestionLibros.utilitario.Utilitario;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * GestionLibroControlador.java
 * 
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version Dec 21, 2016
 * @author Anderson Delgado
 */
@Controller
public class GestionLibroControlador {

    @Autowired
    private LibroServicioCliente libroServicioCliente;

    @Autowired
    private FuncionarioServicioCliente funcionarioServicioCliente;

    @Autowired
    private SeguridadServicioCliente seguridadServicioCliente;

    @Autowired
    private AdministradorPropiedades properties;

    /**
     * 
     */
    public static final String NOMBRE_MODULO = "Gestión de Libro Civil";

    public static final String DESCRIPCION_MODULO = "Gestión de Libro Civil";

    private String token;

    private String rqMapper = "operacion";

    private static final Logger LOGGER = Logger.getLogger(GestionLibroControlador.class.getName());

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
        return properties.getProperty("sarc.web.libro.consultarLibro.url");

    }

    /**
     * @param request
     * @return
     * @throws GestionLibroException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping(value = "/consultarLibro", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String consultarLibro(HttpServletRequest request) throws JsonParseException, JsonMappingException,
            IOException, JSONException, GestionLibroException {
        this.usuarioPorRol();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject data = new JSONObject(request.getHeader(rqMapper));
        String operacion = data.getString(rqMapper);
        LibroCivilFormulario libroCivil;
        String usuario = this.obtenerLogin(token);
        String codOficina = this.obtenerCodOficina(usuario);

        libroCivil = this.cargarLibro(operacion, codOficina);
        return mapper.writeValueAsString(libroCivil);

    }

    /**
     * @param data
     * @param sesion
     * @return
     * @throws GenericException
     * @throws GestionLibroException
     * @throws JSONException
     */
    @RequestMapping(value = "/generarActaLibro", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String generarActaLibro(@RequestBody Map<String, String> data, HttpSession sesion) throws GenericException,
            GestionLibroException, JSONException {
        String operacion = data.get(rqMapper).toUpperCase();
        String nombreLibro;
        String constServProp = "servicios.libro.estatus.";
        String st = ("ABIERTO").equals(data.get("estatus")) ? constServProp + "abierto" : constServProp + "cerrado";
        String status = this.buscarValorProperties(st);
        Libro libro = new Libro();
        Funcionario funcionarioApertura = new Funcionario();
        funcionarioApertura.setPrimerNombre(data.get("funcionarioApertura"));

        Oficina oficina = new Oficina();
        oficina.setCodigo(data.get("codigo_oficina"));
        oficina.setDescripcion(data.get("descripcion_oficina"));
        oficina.setNombre(data.get("nombre_oficina"));

        TipoLibro tipoLibro = new TipoLibro();
        tipoLibro.setNombre(data.get("tipoLibro"));
        tipoLibro.setCodigo(data.get("tipoLibro_codigo"));
        tipoLibro.setDescripcion(data.get("tipoLibro_descripcion"));

        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo(data.get("tipoDocumento_codigo"));

        DocumentoIdentidad documento = new DocumentoIdentidad();
        documento.setTipoDocumento(tipoDocumento);
        documento.setNumero(data.get("documentoIdentidad_numero"));

        List<DocumentoIdentidad> documentos = new ArrayList<>();
        documentos.add(documento);

        Funcionario funcApertura = new Funcionario();
        funcApertura.setDocumentoIdentidad(documentos);

        Funcionario funcCierre = new Funcionario();
        funcCierre.setDocumentoIdentidad(documentos);

        libro.setOficina(oficina);
        libro.setTipoLibro(tipoLibro);
        libro.setFuncionarioApertura(funcApertura);

        libro.setNumeroAnio(Integer.parseInt(data.get("numeroAnio")));
        libro.setEstatus(status);
        libro.setNumeroTomo(data.get("numeroTomo"));
        libro.setNumeroActa(data.get("numeroActa"));
        libro.setNumerofolio(Integer.parseInt(data.get("numeroFolio")));

        libro.setFuncionarioCierre(funcCierre);
        sesion.setAttribute("libro", libro);
        nombreLibro = data.get("tipoLibro");

        JasperReport jasperReport;
        JasperPrint jasperPrint;
        String rutaArchivoPlantilla;
        String rutaSalidaDoc;
        HashMap<String, Object> hasMapData;
        String pa = properties.getProperty("plantilla.jasper.report.generico.plantillaApertura");
        String pc = properties.getProperty("plantilla.jasper.report.generico.plantillaCierre");
        String tmp = properties.getProperty("sarc.file.tmp");
        // se obtiene la ruta donde esta guardada mi plantilla
        rutaArchivoPlantilla = operacion.equals(ConstantesLibro.OPERACION_APERTURA) ? pa : pc;
        hasMapData = this.cargarData(operacion.equals(ConstantesLibro.OPERACION_APERTURA) ? true : false, data);
        GenerarActas reporteActa = new GenerarActas(rutaArchivoPlantilla);
        rutaSalidaDoc = tmp + nombreLibro.replace(" ", "") + "_" + operacion;
        try {
            jasperReport = reporteActa.compilarReporte(rutaArchivoPlantilla);
            jasperPrint = reporteActa.sustituirTag(jasperReport, hasMapData);
            reporteActa.exportarDocumento(jasperPrint, "HTML", rutaSalidaDoc);
            reporteActa.exportarDocumento(jasperPrint, "PDF", rutaSalidaDoc);
        } catch (JRException e) {
            LOGGER.error("Error al generar el Acta:" + e);
            throw new GestionLibroException(ConstantesLibro.ERROR_ACTA);
        }
        String protocol = properties.getProperty("sarc.web.servidor.atc");
        String rutaContexto = protocol + "/web-gestionLibros/tmp/" + nombreLibro.replace(" ", "") + "_" + operacion
                + ".pdf#toolbar=1";
        JSONObject obj = new JSONObject();
        obj.put("ruta", rutaContexto);
        return obj.toString();
    }

    /**
     * @param sesion
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "/operacionLibro_AC", method = RequestMethod.POST)
    @ResponseBody
    public String operacionLibroAC(HttpSession sesion) throws JSONException {
        Libro libro = (Libro) sesion.getAttribute("libro");
        boolean abierto = libroServicioCliente.aperturaCierreLibro(libro, token);
        JSONObject obj = new JSONObject();
        String st = "servicios.libro.estatus.cerrado";
        String status = this.buscarValorProperties(st);
        if (abierto) {
            if (status.equals(libro.getEstatus())) {
                obj.put("satisfactorioMsg", "Se realizo la apertura del libro de manera exitosa.");
            } else {
                obj.put("satisfactorioMsg", "El libro fue cerrado exitosamente");
            }
        } else {
            obj.put("errCode", "00");
            obj.put("errMsg", "Error en la operación Sobre el libro");
        }
        return obj.toString();
    }

    /**
     * @param ex
     * @param request
     * @return
     * @throws IOException
     */
    @ExceptionHandler(GestionLibroException.class)
    @ResponseBody
    public GestionLibroException handleCustomException(GestionLibroException ex, HttpServletRequest request)
            throws IOException {
        // valida exception del modulo completo
        ServletContext context = request.getSession().getServletContext();
        String contexto = context.getRealPath("/resources/site/properties");
        LOGGER.info("error: " + ex.getErrCode());
        ex.getMessage(contexto + "//messages.properties", ex.getErrCode());
        return ex;
    }

    /**
     * @param operacion
     * @param codOficina
     * @return
     * @throws GestionLibroException
     */
    private LibroCivilFormulario cargarLibro(String operacion, String codOficina) throws GestionLibroException {
        LibroCivilFormulario libroCivil = new LibroCivilFormulario();
        List<Libro> respuestaServicio = libroServicioCliente.consultarTodos(codOficina, token);
        if (respuestaServicio.isEmpty()) {
            LOGGER.info("****" + ConstantesLibro.ERROR_NOT_FOUND_DATA);
            throw new GestionLibroException(ConstantesLibro.ERROR_NOT_FOUND_DATA);
        } else {
            for (Libro libro : respuestaServicio) {
                boolean validacionCorrecta = this.validarCamposReqActa(libro, operacion.toUpperCase());
                if (!validacionCorrecta) {
                    libroCivil.getLibrosList().add(libro);
                } else {
                    LOGGER.info("****" + ConstantesLibro.ERROR_NOT_FOUND_DATA);
                    throw new GestionLibroException(ConstantesLibro.ERROR_NOT_FOUND_DATA);
                }
            }
        }

        return libroCivil;
    }

    /**
     * @param libro
     * @param operacion
     * @return
     */
    private boolean validarCamposReqActa(Libro libro, String operacion) {

        boolean datosCorrecto = true;

        // ///////////////////////////////////////// Validaciones Genericas
        // tanto para la Acta de Apertura como d Cierre
        // ///////////////////////////////////////////
        // Validacion Parroquia
        // Es necesario esta validacion ya que debe aparecer el nombre de la
        // parroquia en el Acta(Aplica tanto para apertura como cierre)
        if (libro.getOficina().getDireccion() == null
                || libro.getOficina().getDireccion().getParroquia() == null
                || (libro.getOficina().getDireccion().getParroquia().getNombre() == null || !libro.getOficina()
                        .getDireccion().getParroquia().getNombre().isEmpty())) {
            datosCorrecto = false;
        }
        // Validacion Nombre Registrador
        // Es necesario esta validacion ya que debe aparecer el nombre del
        // registrador en el Acta(Aplica tanto para apertura como cierre)
        if (libro.getFuncionarioCierre() == null
                || Utilitario.esCampoVacio(libro.getFuncionarioCierre().getPrimerNombre())
                || Utilitario.esCampoVacio(libro.getFuncionarioCierre().getPrimerApellido())) {
            datosCorrecto = false;
        }
        // ///////////////////////////////////////////////// Validaciones para
        // Apertura
        // ////////////////////////////////////////////////////////////////////////////////
        // Validacion necesaria para validar que el servicio te traigas los
        // valores requeridos para la posible generacion de Acta
        if (!Utilitario.esIgualCampos(operacion, ConstantesLibro.OPERACION_APERTURA)
                || Utilitario.esCampoVacio(libro.getNumeroTomo())) {
            datosCorrecto = false;
        }

        return datosCorrecto;

    }

    /**
     * @param login
     * @return
     * @throws GestionLibroException
     */
    private String obtenerCodOficina(String login) throws GestionLibroException {

        Funcionario funcionario = funcionarioServicioCliente.buscarPorLogin(login, token);

        if (funcionario == null || funcionario.getOficina() == null || funcionario.getOficina().getCodigo() == null
                || funcionario.getOficina().getCodigo().isEmpty()) {
            LOGGER.error("ERROR: Problema en el servicio buscarPorLogin ");
            throw new GestionLibroException(ConstantesLibro.ERROR_NOT_FOUND_DATA);
        }

        return funcionario.getOficina().getCodigo();

    }

    /**
     * @param accessToken
     * @return
     * @throws JSONException
     */
    private String obtenerLogin(String accessToken) throws JSONException {
        JSONObject usuario;
        usuario = seguridadServicioCliente.getUsernameCliente(accessToken);

        return usuario.getString("username");
    }

    /**
     * @param isApertura
     * @param data
     * @return
     * @throws GenericException
     */
    private HashMap<String, Object> cargarData(boolean isApertura, Map<String, String> data) throws GenericException {
        Map<String, Object> dataActa = new HashMap<>();

        // Datos Genericos que necesitan las Dos(2) Actas: Apertura y Cierre
        String urlFile = properties.getProperty("plantilla.jasper.report.imagen.logo");
        dataActa.put("urlFile", urlFile);
        dataActa.put("dia", Utilitario.obtenerFechaOrHoraActual("DIA"));
        dataActa.put("mes", Utilitario.obtenerFechaOrHoraActual("STRING_MES"));
        dataActa.put("anio", Utilitario.obtenerFechaOrHoraActual("ANIO"));
        dataActa.put("nombreCompletoRg", data.get("funcionarioApertura"));
        dataActa.put("parroquia", data.get("parroquia"));
        dataActa.put("nombreLibro", data.get("tipoLibro"));
        dataActa.put("html", "true");
        dataActa.put("pdf", "true");
        if (isApertura) {
            dataActa.put("numTomo", data.get("numeroTomo"));
        } else {
            dataActa.put("numActaInicio", data.get("numeroActa"));
            dataActa.put("numActaFin", data.get("numeroActa"));
            dataActa.put("numLetra", data.get("numeroActa"));
            dataActa.put("num", data.get("numeroActa"));
        }
        return (HashMap<String, Object>) dataActa;
    }

    /**
     * @throws JSONException
     * @throws GestionLibroException
     */
    private void usuarioPorRol() throws GestionLibroException {
        String rol = this.buscarValorProperties("roles.reg");
        JSONObject login = null;
        try {
            login = seguridadServicioCliente.getUsernameCliente(token);
        } catch (JSONException e) {
            LOGGER.error(e);
        }
        Funcionario obtenerDatosFuncionario = null;
        try {
            obtenerDatosFuncionario = funcionarioServicioCliente.buscarPorLogin(login.getString("username"), token);
        } catch (JSONException e) {
            LOGGER.error(e);
        }

        String codRol = obtenerDatosFuncionario.getUsuarios().get(0).getCodigoRol();
        if (rol.equalsIgnoreCase(codRol)) {
            LOGGER.info("rol Autorizado: " + codRol);
        } else {
            throw new GestionLibroException(ConstantesLibro.ERROR_ROL_USUARIO);
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
