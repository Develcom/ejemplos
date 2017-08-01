package ve.gob.cne.sarc.solicitud.core.solicitud.controllers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.comunes.catalogo.EntePublico;
import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.direccion.DireccionElectronica;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Usuario;
import ve.gob.cne.sarc.comunes.solicitante.DocumentoEntePublico;
import ve.gob.cne.sarc.comunes.solicitante.Solicitante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.pmsentinel.bo.PaqueteBO;
import ve.gob.cne.sarc.solicitud.core.solicitud.business.SolicitudBF;

/**
 * SolicitudController.java
 *
 * @descripcion [Controlador del servicio REST Solicitud]
 * @version 1.0 22/7/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/solicitud")
public class SolicitudController {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitudController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    @Autowired
    private SolicitudBF solicitudBF;

    /**
     *
     * Metodo consultarDetalleSolicitud que permite consultar un solicitud segun
     * el numero de esta
     *
     * @param numero String Numero de la Solicitud
     * @return Solicitud objeto de tipo Solicitud
     */
    @RequestMapping(value = "/consultarDetalleSolicitud/{numero}", method = RequestMethod.GET)
    public Solicitud consultarDetalleSolicitud(@PathVariable("numero") String numero) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarDetalleSolicitud");
        return solicitudBF.consultarDetalleSolicitud(numero);

    }

    /**
     *
     * Metodo consultarPorNumeroDocSolicitante que permite consultar las
     * solicitudes existentes asociadas a un documento de solicitante
     *
     * @param tipoSolicitante String tipo de solicitante
     * @param tipoDocumento String tipo de documento del solicitante
     * @param numeroDocumento String numero documento del solicitante
     * @return Lista de objetos de tipo Solicitud
     */
    @RequestMapping(
            value = "/consultarPorNumeroDocSolicitante/{tipoSolicitante}/{tipoDocumento}/{numeroDocumento}",
            method = RequestMethod.GET)
    public List<Solicitud> consultarPorNumeroDocSolicitante(
            @PathVariable("tipoSolicitante") String tipoSolicitante,
            @PathVariable("tipoDocumento") String tipoDocumento,
            @PathVariable("numeroDocumento") String numeroDocumento) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarPorNumeroDocSolicitante");
        return solicitudBF.consultarPorNumeroDocSolicitante(tipoSolicitante, tipoDocumento, numeroDocumento);
    }

    /**
     *
     * Metodo consultarTodos que permite consultar todas las solicitudes
     * existentes
     *
     *
     * @return Lista de Objetos de tipo Solicitud
     */
    @RequestMapping(value = "/consultarTodos", method = RequestMethod.GET)
    public List<Solicitud> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarTodos");
        return solicitudBF.consultarTodos();
    }

    /**
     *
     * Metodo actualizarEstadoSolicitud que permite actualizar el estado de una
     * solicitud existente
     *
     * @param solicitud Objeto que contiene la solicitud
     * @return Solicitud objeto de tipo Solicitud
     */
    @RequestMapping(value = "/actualizarEstadoSolicitud", method = RequestMethod.POST, produces = {"application/json"})
    public Solicitud actualizarEstadoSolicitud(@RequestBody Solicitud solicitud, HttpServletRequest request) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "actualizarEstadoSolicitud");

        imprimirObjeto(solicitud);
        
        String token = request.getHeader("Authorization");

        if (token != null) {

            if (token.contains("Bearer") || token.contains("bearer")) {
                token = token.substring(7);
            }
        }
        return solicitudBF.actualizarEstadoSolicitud(solicitud, token);

    }

    /**
     *
     * Metodo generarSolicitud que permite crear una solicitud nueva
     *
     * @param solicitud Objeto que contiene la Solicitud
     * @param request
     * @return Lista de Objeto Solicitud
     */
    @RequestMapping(value = "/generarSolicitud", method = RequestMethod.POST,
            produces = {"application/json;**charset=UTF-8**"})
    public Solicitud generarSolicitud(@RequestBody Solicitud solicitud, HttpServletRequest request) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "generarSolicitud");

        imprimirObjeto(solicitud);

        String token = request.getHeader("Authorization");

        if (token != null) {

            if (token.contains("Bearer") || token.contains("bearer")) {
                token = token.substring(7);
            }
        }
        return solicitudBF.generarSolicitud(solicitud, token);
    }

    /**
     *
     * Metodo consultarPorUsuario que permite consultar las solicitudes
     * existentes asociadas a un usuario y rol
     *
     * @param loginUsuario String login del usuario
     * @param codigoRol String codigo rol de usuario
     * @return Lista de Objeto Solicitud
     */
    @RequestMapping(value = "/consultarPorUsuario/{loginUsuario}/{codigoRol}", method = RequestMethod.GET)
    public List<Solicitud> consultarPorUsuario(@PathVariable("loginUsuario") String loginUsuario,
            @PathVariable("codigoRol") String codigoRol) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarPorUsuario");
        return solicitudBF.consultarPorUsuario(loginUsuario, codigoRol);
    }

    /**
     *
     * Metodo generarSolicitud que permite crear una solicitud nueva
     *
     * @return Solicitud objeto de tipo Solicitud
     */
    @RequestMapping(value = "/generarjson", method = RequestMethod.GET, produces = {"application/json"})
    public Solicitud generarjson() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "generarSolicitud");
        Solicitud solicitud = new Solicitud();

        solicitud.setEstadoSolicitud("ab");
        Solicitante solicitante = new Solicitante();
        solicitante.setTipo("D");
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setPrimerNombre("Joelvin");
        ciudadano.setPrimerApellido("Chirino");
        ciudadano.setSegundoNombre("Enrique");
        ciudadano.setSegundoApellido("Urbano");

        DireccionElectronica direccionElectronica = new DireccionElectronica();
        direccionElectronica.setTipoEmail("Personal");
        direccionElectronica.setEmail("joelvinchirinox14@gmail.com");
        List<DireccionElectronica> direccionElectronicas = new ArrayList<>();
        direccionElectronicas.add(direccionElectronica);
        ciudadano.setDireccionesElectronica(direccionElectronicas);

        solicitante.setCiudadano(ciudadano);

        DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
        documentoIdentidad.setNumero("16022375");
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo("CED");
        tipoDocumento.setNombre("Cedula");
        documentoIdentidad.setTipoDocumento(tipoDocumento);
        List<DocumentoIdentidad> documentoIdentidads = new ArrayList<>();
        documentoIdentidads.add(documentoIdentidad);
        ciudadano.setDocumentoIdentidad(documentoIdentidads);
        Funcionario funcionario = new Funcionario();
        funcionario.setPrimerNombre("Nombre Funcionario");
        Usuario usuario = new Usuario();
        usuario.setNombre("func1");

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        funcionario.setUsuarios(usuarios);
        solicitud.setFuncionario(funcionario);
        Tramite tramite = new Tramite();
        tramite.setCodigo("RNACI");

        Modulo modulo = new Modulo();
        modulo.setCodigo("RNACI");

        solicitud.setTramite(tramite);

        solicitud.setModulo(modulo);

        /*ente pulico*/
        DocumentoEntePublico documentoEntePublico = new DocumentoEntePublico();

        documentoEntePublico.setEnteOrigen("Ministerio de interior y justicia");
        documentoEntePublico.setFechaDocumento("2015-05-05");
        documentoEntePublico.setNumero("2015-5959");
        documentoEntePublico.setTipoDocumentoEntePublico("DJU");
        EntePublico entePublico = new EntePublico();
        entePublico.setCodigo("MIJ");

        documentoEntePublico.setEntePublico(entePublico);

        solicitante.setDocumentoEntePublico(documentoEntePublico);
        solicitud.setSolicitante(solicitante);

        return solicitud;
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un rango de fechas
     *
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    @RequestMapping(value = "/consultarPorFecha/{fechaDesde}/{fechaHasta}", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public List<Solicitud> consultarSolicitudesRangoFecha(@PathVariable("fechaDesde") String fechaDesde,
            @PathVariable("fechaHasta") String fechaHasta) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarSolicitudes - FecDesde/FecHasta -- "
                + fechaDesde + "/" + fechaHasta);
        return solicitudBF.consultarSolicitudesRangoFecha(fechaDesde, fechaHasta);
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos
     * (tramites o estatus de solicitud) y el tipo de codigo (T:Tramite o
     * E:Estatus)
     *
     * @param codigos Arreglo de tipo String con los codigos de tramites o
     * estatus de solicitud
     * @param tipoCodigo String indica el tipo de codigo (T:Tramite o E:Estatus)
     * @return Lista de objetos de tipo solicitud
     */
    @RequestMapping(value = "/consultarPorTramiteOEstatus/{codigos}/{tipoCodigo}", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public List<Solicitud> consultarSolicitudesTramiteOEstatus(@PathVariable("codigos") String[] codigos,
            @PathVariable("tipoCodigo") String tipoCodigo) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarSolicitudes - codigos/tipoCodigo -- "
                + codigos + "/" + tipoCodigo);
        return solicitudBF.consultarSolicitudesTramiteOEstatus(codigos, tipoCodigo);
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos
     * de tramites y dado un arreglo de codigos de estatus
     *
     * @param codigosTramite Arreglo de tipo String con los codigos de tramites
     * @param codigosEstatus Arreglo de tipo String con los codigos de estatus
     * de solicitud
     * @return Lista de objetos de tipo solicitud
     */
    @RequestMapping(value = "/consultarPorTramiteYEstatus/{codigosTramite}/{codigosEstatus}",
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<Solicitud> consultarSolicitudesTramiteYEstatus(@PathVariable("codigosTramite") String[] codigosTramite,
            @PathVariable("codigosEstatus") String[] codigosEstatus) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarSolicitudes - codigosTramite/codigosEstatus -- "
                + codigosTramite.length + "/" + codigosEstatus.length);
        return solicitudBF.consultarSolicitudesTramiteYEstatus(codigosTramite, codigosEstatus);
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos
     * (tramites o estatus de solicitud),el tipo de codigo (T:Tramite o
     * E:Estatus) y un rango de fechas
     *
     * @param codigos Arreglo de tipo String con los codigos de tramites o
     * estatus de solicitud
     * @param tipoCodigo String indica el tipo de codigo (T:Tramite o E:Estatus)
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    @RequestMapping(value = "/consultarPorTramiteOEstatusYFecha/{codigos}/{tipoCodigo}/{fechaDesde}/{fechaHasta}",
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<Solicitud> consultarSolicitudesTramiteOEstatusRangoFechas(@PathVariable("codigos") String[] codigos,
            @PathVariable("tipoCodigo") String tipoCodigo,
            @PathVariable("fechaDesde") String fechaDesde,
            @PathVariable("fechaHasta") String fechaHasta) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarSolicitudes - codigos/tipoCodigo/fechaDesde/fechaHasta -- "
                + codigos.length + "/" + tipoCodigo + "/" + fechaDesde + "/" + fechaHasta);
        return solicitudBF.consultarSolicitudesTramiteOEstatusRangoFechas(codigos, tipoCodigo, fechaDesde, fechaHasta);
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos
     * de tramites, un arreglo de estatus y dado un arreglo de codigos de
     * estatus y un rango de fechas
     *
     * @param codigosTramite Arreglo de tipo String con los codigos de tramites
     * @param codigosEstatus Arreglo de tipo String con los codigos de estatus
     * de solicitud
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    @RequestMapping(
            value = "/consultarPorTramiteYEstatusYFecha/{codigosTramite}/{codigosEstatus}/{fechaDesde}/{fechaHasta}",
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<Solicitud> consultarSolicitudesTramiteYEstatusRangoFechas(@PathVariable("codigosTramite") String[] codigosTramite,
            @PathVariable("codigosEstatus") String[] codigosEstatus,
            @PathVariable("fechaDesde") String fechaDesde,
            @PathVariable("fechaHasta") String fechaHasta) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarSolicitudes - codTra/codEst/fechaDesde/fechaHasta -- "
                + codigosTramite.length + "/" + codigosEstatus.length);
        return solicitudBF.consultarSolicitudesTramiteYEstatusRangoFechas(codigosTramite, codigosEstatus, fechaDesde, fechaHasta);
    }

    /**
     * Busca un el paquete asociado a la solicitud
     *
     * @param numeroSolicitud numero de la solicitud
     * @return Objecto paquete
     */
    @RequestMapping(value = "/buscarPaquete/{numeroSolicitud}")
    @ResponseBody
    public PaqueteBO buscarPaquete(@PathVariable("numeroSolicitud") String numeroSolicitud) {
        return solicitudBF.buscarPaqueteBO(numeroSolicitud);
    }

    /**
     * Metodo que imprime el objeto de entrada de cualquier método que lo
     * implemente
     *
     * @param obj Objeto que es entrada de cualquier metodo
     */
    private void imprimirObjeto(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOG.error("problemas al mostra el json ", ex);
        }
        LOG.info("Imprimir Json de Entrada: " + obj.getClass().getName() + " - " + json);

    }

}
