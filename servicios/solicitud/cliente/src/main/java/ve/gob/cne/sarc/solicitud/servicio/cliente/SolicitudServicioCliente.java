package ve.gob.cne.sarc.solicitud.servicio.cliente;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.pmsentinel.bo.PaqueteBO;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * Clase cliente para el servicio de solicitud
 *
 * @author Anabell De Faria
 * @version 1.0
 */
@Component
@Configuration
@EnableAsync
@EnableScheduling
public class SolicitudServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitudServicioCliente.class);

    private String endPointPorNumeroDocSolicitante;
    private String endPointDetalleSolicitud;
    private String endPointGenerar;
    private String endPointActualizarEdoSolicitud;
    private String endPointSolicitudTodos;
    private String endPointPorUsuario;
    private String endPointConsultarPorFecha;
    private String endPointConsPorTramiteOEstatus;
    private String endPointConsPorTramiteYEstatus;
    private String endPointConsPorTramiteOEstatusYFecha;
    private String endPointConsPorTramiteYEstatusYFecha;
    private String endPointBuscarPaquete;
    private RestTemplateHelper rth = new RestTemplateHelper();
    private List<Solicitud> solicitudList;
    private AdministradorPropiedades properties;

    public SolicitudServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.registrarEscucha(this);
        init();
    }

    /**
     * Buscar detalle solicitud por el numero
     *
     * @param numero String que contiene el numero de solicitud
     * @param token token de acceso para seguridad
     * @return Objeto de tipo Solicitud
     */
    public Solicitud consultarDetalleSolicitud(String numero, String token) {

        Solicitud solicitudResponde;

        solicitudResponde = rth.procesarPeticionSeguridad(endPointDetalleSolicitud + numero,
                token, null, Solicitud.class, HttpMethod.GET);

        return solicitudResponde;
    }

    /**
     * Crea un paquete por url
     *
     * @param solicitud Solicitud que contiene los datos de una solicitud
     * @param token permiso de acceso
     * @return Objeto de tipo Solicitud
     */
    public Solicitud crearSolicitud(Solicitud solicitud, String token) {

        Solicitud respPaq;

        respPaq = rth.procesarPeticionSeguridad(endPointGenerar, token, solicitud,
                Solicitud.class, HttpMethod.POST);

        return respPaq;
    }

    /**
     * Busca todas las solicitudes
     *
     * @param token permiso de acceso
     * @return Lista de objetos de tipo Solicitud
     */
    public List<Solicitud> consultarSolicitudesTodas(String token) {

        solicitudList = rth.procesarPeticionSeguridadLista(
                endPointSolicitudTodos, token, null,
                Solicitud[].class, HttpMethod.GET);

        return solicitudList;
    }

    /**
     * Busca las solicitudes existentes asociadas a un documento de solicitante
     *
     * @param tipoSolicitante String tipo de solicitante
     * @param tipoDocumento String tipo de documento del solicitante
     * @param numeroDocumento String numero documento del solicitante
     * @param token permiso de acceso
     * @return Lista de objetos de tipo Solicitud
     */
    public List<Solicitud> consultarPorNumDocAtendido(String tipoSolicitante,
            String tipoDocumento, String numeroDocumento, String token) {

        solicitudList = rth.procesarPeticionSeguridadLista(endPointPorNumeroDocSolicitante
                + tipoSolicitante + "/" + tipoDocumento + "/"
                + numeroDocumento, token, null, Solicitud[].class, HttpMethod.GET);

        return solicitudList;
    }

    /**
     * Busca solicitudes por usuario y rol
     *
     * @param loginUsuario String login del usuario
     * @param codigoRol String codigo rol de usuario
     * @return Lista de Objeto Solicitud
     */
    public List<Solicitud> consultarPorUsuario(String loginUsuario, String codigoRol, String token) {

        solicitudList = rth.procesarPeticionSeguridadLista(endPointPorUsuario + loginUsuario
                + "/" + codigoRol, token, null, Solicitud[].class, HttpMethod.GET);

        return solicitudList;
    }

    /**
     * Actualiza un estado de la solicitud existente
     *
     * @param solicitud Objeto que contiene la solicitud
     * @param token permiso de acceso
     * @return Solicitud objeto de tipo Solicitud
     */
    public Solicitud actualizarEstadoSolicitud(Solicitud solicitud, String token) {

        Solicitud resp;

        resp = rth.procesarPeticionSeguridad(endPointActualizarEdoSolicitud, token,
                solicitud, Solicitud.class, HttpMethod.POST);

        return resp;

    }

    /**
     * <p>
     * Consulta las solicitudes dado un rango de fechas
     * </p>
     *
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @param token permiso de acceso
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesRangoFechas(String fechaDesde, String fechaHasta, String token) {

        solicitudList = rth.procesarPeticionSeguridadLista(endPointConsultarPorFecha + fechaDesde
                + "/" + fechaHasta, token, null, Solicitud[].class, HttpMethod.GET);

        return solicitudList;
    }

    /**
     * <p>
     * Consulta las solicitudes dada una cadena de codigos de tramites o estatus de solicitud(separados por coma) y el
     * tipo de codigo (T:Tramite o E:Estatus)</p>
     *
     * @param codigos Arreglo de tipo String con los codigos de tramites o estatus de solicitud
     * @param tipoCodigo String indica el tipo de codigo (T:Tramite o E:Estatus)
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesTramiteOEstatus(String[] codigos, String tipoCodigo, String token) {

        solicitudList = rth.procesarPeticionSeguridadLista(endPointConsPorTramiteOEstatus
                + Arrays.deepToString(codigos) + "/" + tipoCodigo, token, null,
                Solicitud[].class, HttpMethod.GET);

        return solicitudList;
    }

    /**
     * <p>
     * Consultar las solicitudes dada una cadena de codigos de tramites y una cadena de codigos de estatus.</p>
     *
     * @param codigosTramite Arreglo de tipo String con los codigos de tramites
     * @param codigosEstatus Arreglo de tipo String con los codigos de estatus de solicitud
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesTramiteYEstatus(String[] codigosTramite, String[] codigosEstatus, String token) {

        solicitudList = rth.procesarPeticionSeguridadLista(endPointConsPorTramiteYEstatus
                + Arrays.deepToString(codigosTramite) + "/" + Arrays.deepToString(codigosEstatus),
                token, null, Solicitud[].class, HttpMethod.GET);

        return solicitudList;
    }

    /**
     * <p>
     * Consultarlas solicitudes dado una cadena de codigos de tramites o estatus de solicitud(separados por coma), el
     * tipo de codigo (T:Tramite o E:Estatus) y un rango de fechas.</p>
     *
     * @param codigos Arreglo de tipo String con los codigos de tramites o estatus de solicitud
     * @param tipoCodigo String indica el tipo de codigo (T:Tramite o E:Estatus)
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @param token
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesTramiteOEstatusRangoFechas(String[] codigos, String tipoCodigo, String fechaDesde,
            String fechaHasta, String token) {

        solicitudList = rth.procesarPeticionSeguridadLista(endPointConsPorTramiteOEstatusYFecha
                + Arrays.deepToString(codigos) + "/" + tipoCodigo + "/" + fechaDesde + "/" + fechaHasta,
                token, null, Solicitud[].class, HttpMethod.GET);

        return solicitudList;
    }

    /**
     * <p>
     * Consultar las solicitudes existentes dado una cadena de codigos de tramites, una cadena de estatus y una cadena
     * de codigos de estatus y un rango de fechas.</p>
     *
     * @param codigosTramite Arreglo de tipo String con los codigos de tramites
     * @param codigosEstatus Arreglo de tipo String con los codigos de estatus de solicitud
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @param token
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesTramiteYEstatusYRangoFechas(String[] codigosTramite, String[] codigosEstatus, String fechaDesde,
            String fechaHasta, String token) {

        solicitudList = rth.procesarPeticionSeguridadLista(endPointConsPorTramiteYEstatusYFecha
                + Arrays.deepToString(codigosTramite) + "/" + Arrays.deepToString(codigosEstatus) + "/"
                + fechaDesde + "/" + fechaHasta, token, null, Solicitud[].class, HttpMethod.GET);

        return solicitudList;
    }

    /**
     * Busca un paquete asociado a su solicitud
     *
     * @param numeroSolicitu El numero de la solicitud
     * @return El objeto paquete
     */
    public PaqueteBO buscarPaquete(String numeroSolicitu) {

        PaqueteBO paqueteBO;

        paqueteBO = rth.procesarPeticion(endPointBuscarPaquete + numeroSolicitu,
                new HashMap(), PaqueteBO.class);

        return paqueteBO;
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if (archivo.equals("SolicitudServicioCliente.properties")) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointPorNumeroDocSolicitante = properties.getProperty("endPointPorNumeroDocSolicitante");
        endPointDetalleSolicitud = properties.getProperty("endPointDetalleSolicitud");
        endPointGenerar = properties.getProperty("endPointGenerar");
        endPointActualizarEdoSolicitud = properties.getProperty("endPointActualizarEdoSolicitud");
        endPointSolicitudTodos = properties.getProperty("endPointSolicitudTodos");
        endPointPorUsuario = properties.getProperty("endPointPorUsuario");
        endPointConsultarPorFecha = properties.getProperty("endPointConsultarPorFecha");
        endPointConsPorTramiteOEstatus = properties.getProperty("endPointConsPorTramiteOEstatus");
        endPointConsPorTramiteYEstatus = properties.getProperty("endPointConsPorTramiteYEstatus");
        endPointConsPorTramiteOEstatusYFecha = properties.getProperty("endPointConsPorTramiteOEstatusYFecha");
        endPointConsPorTramiteYEstatusYFecha = properties.getProperty("endPointConsPorTramiteYEstatusYFecha");
        endPointBuscarPaquete = properties.getProperty("endPointBuscarPaquete");
    }
}
