package ve.gob.cne.sarc.solicitud.core.solicitud.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.pmsentinel.bo.PaqueteBO;

/**
 * SolicitudBF.java
 * @descripcion [BusinessFacade con la logica de negocio de manejo de solicitudes]
 * @version 1.0 22/7/2016
 * @author Anabell De Faria
 */
public interface SolicitudBF {

    /**
     * 
     * Interfaz del metodo consultarPorNumeroDocSolicitante que permite consultar las solicitudes existentes asociadas a
     * un documento de solicitante
     * 
     * @param tipoSolicitante String con el tipo de Solicitante (D:Declarante Ciudaddano/E:Ente publico)
     * @param tipoDocumento String con el tipo de documento del solicitante
     * @param numeroDocumento String con el numero de documento del solicitante
     * @return Lista de objetos de tipo Solicitud
     */
    public List<Solicitud> consultarPorNumeroDocSolicitante(String tipoSolicitante,
            String tipoDocumento, String numeroDocumento);

    /**
     *
     * Interfaz del metodo consultarDetalleSolicitud que permite consultar un solicitud segun el numero de esta
     * 
     * @param numero String con el numero de la solicitud
     * @return Objeto de tipo Solicitud
     */
    public Solicitud consultarDetalleSolicitud(String numero);

    /**
     * Interfaz del metodo consultarTodos que permite consultar todas las solicitudes existentes
     * 
     * @return Lista de objetos de tipo Solicitud
     */
    public List<Solicitud> consultarTodos();

    /**
     * 
     * Interfaz del metodo actualizarEstadoSolicitud que permite actualizar el estado de una solicitud existente
     *
     * @param solicitud Objeto de tipo solicitud
     * @param token
     * @return Solicitud Obejto de tipo Solicitud
     */
    public Solicitud actualizarEstadoSolicitud(Solicitud solicitud, String token) throws Exception;

    /**
     *
     * Interfaz del metodo generarSolicitud que permite crear una solicitud nueva
     *
     * @param solicitud Objeto de tipo solicitud
     * @param token
     * @return Solicitud Objeto de tipo Solicitud
     */
    public Solicitud generarSolicitud(Solicitud solicitud, String token);

    /**
     *
     * Interfaz del metodo consultarPorUsuario que permite consultar las solicitudes existentes asociadas a un usuario
     * 
     * @param loginUsuario String login del usuario
     * @param codigoRol String codigo rol de usuario
     * @return Lista de Objeto Solicitud
     */
    public List<Solicitud> consultarPorUsuario(String loginUsuario, String codigoRol);

    /**
     *
     * Metodo que permite consultar las solicitudes dado un rango de fechas
     *
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesRangoFecha(String fechaDesde, String fechaHasta);

    /**
     *
     * Interfaz del metodo consultarSolicitudesRangoFecha que permite consultar las solicitudes dado un arreglo de codigos
 (tramites o estatus de solicitud) y el tipo de codigo (T:Tramite o E:Estatus)
     *
     * @param codigos Arreglo de tipo String con los codigos de tramites o estatus de solicitud
     * @param tipoCodigo String indica el tipo de codigo (T:Tramite o E:Estatus)
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesTramiteOEstatus(String[] codigos, String tipoCodigo);

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos de tramites y dado un arreglo de codigos
     * de estatus
     *
     * @param codigosTramite Arreglo de tipo String con los codigos de tramites
     * @param codigosEstatus Arreglo de tipo String con los codigos de estatus de solicitud
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesTramiteYEstatus(String[] codigosTramite, String[] codigosEstatus);

    /**
     * 
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos (tramites o estatus de solicitud),el tipo
     * de codigo (T:Tramite o E:Estatus) y un rango de fechas
     *
     * @param codigos Arreglo de tipo String con los codigos de tramites o estatus de solicitud
     * @param tipoCodigo String indica el tipo de codigo (T:Tramite o E:Estatus)
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesTramiteOEstatusRangoFechas(String[] codigos, String tipoCodigo, String fechaDesde,
            String fechaHasta);

    /**
     * 
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos de tramites, un arreglo de estatus y dado
     * un arreglo de codigos de estatus y un rango de fechas
     *
     * @param codigosTramite Arreglo de tipo String con los codigos de tramites
     * @param codigosEstatus Arreglo de tipo String con los codigos de estatus de solicitud
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    public List<Solicitud> consultarSolicitudesTramiteYEstatusRangoFechas(String[] codigosTramite, String[] codigosEstatus, String fechaDesde,
            String fechaHasta);
    

    /**
     * Busca un el paquete asociado a la solicitud
     * @param numeroSolicitud numero de la solicitud
     * @return Objecto paquete
     */
    public PaqueteBO buscarPaqueteBO(String numeroSolicitud);
}
