package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.SolicitudEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * SolicitudRepositor.java
 *
 * @descripcion Clase Repositorio de la entidad SolicitudEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface SolicitudRepository extends
        CrudRepository<SolicitudEntidad, Long> {

    /**
     *
     * @metodo Buscar Solicitud por Número (Usando CrudRepository directamente);
     * @param numero String numero de Solicitud.
     * @return SolicitudEntidad - Objeto del modelo ontologico que contiene la informacion de solicitud.
     *
     */
    public SolicitudEntidad findByNumero(@Param("numero") String numero);

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorDocSolicitante que permite consultar las solicitudes existentes
     * asociadas a un documento de solicitante declarantre
     * @param nombre String Nombre de solicitanteTipDocEnte.
     * @param numeroDocumento String numeroDocumento de solicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> buscarPorDocSolicitanteDec(
            @Param("nombre") String nombre,
            @Param("numeroDocumento") String numeroDocumento);

    /**
     * @metodo Metodo de acceso a datos buscarPorDocSolicitante que permite consultar las solicitudes existentes
     * asociadas a un documento de solicitante ente publico
     * @param id Long id de entidad solicitanteTipDocDec.
     * @param numeroDocumento String numeroDocumento de solicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> buscarPorDocSolicitanteEnte(
            @Param("id") Long id,
            @Param("numeroDocumento") String numeroDocumento);

    /**
     *
     * @metodo Metodo de acceso a datos buscarSolicitudPorUsuario que permite consultar las solicitudes existentes por
     * un Nombre de usuario.
     * @author Oscar Montilla
     * @version 8.5 - 12 de may. de 2016
     * @param nombre String nombre de solicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> buscarSolicitudPorUsuario(
            @Param("nombre") String nombre);

    /**
     *
     * @metodo Metodo de acceso a datos buscarSolicitudPorUsuario que permite consultar las solicitudes existentes por
     * un Nombre de usuario.
     * @author Oscar Montilla
     * @version 8.5 - 12 de may. de 2016
     * @param nombre String nombre de solicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> buscarSolicitudPorUsuarioYcodigo(
            @Param("nombre") String nombre);

    /**
     *
     * @metodo Metodo de acceso a datos findByTramiteIn que permite consultar las solicitudes existentes por una
     * listaTramite
     * @author Oscar Montilla
     * @version 8.5 - 12 de may. de 2016
     * @param listaTramite Objeto listaTramite del modelo ontologico de Tramite.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> findByTramiteIn(@Param("listaTramite") List<TramiteEntidad> listaTramite);

    /**
     *
     * @metodo Metodo de acceso a datos findByTramiteIn que permite consultar las solicitudes existentes por una
     * listaEstatus.
     * @author Oscar Montilla
     * @version 8.5 - 7 de Jun. de 2016
     * @param listaEstatus Objeto listaTramite del modelo ontologico de EstatusSolicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> findByEstatusIn(@Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus);

    /**
     *
     * @metodo Metodo de acceso a datos findByFechaInicioBetweenFechaFin que permite consultar las solicitudes
     * existentes entre fechaInicio y FechaFin.
     * @author Oscar Montilla
     * @version 8.5 - 7 de Jun. de 2016
     * @param fechaInicio Date fechaInicio de solicitud.
     * @param fechaFin Date fechaFin de solicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> findByFechaInicioBetween(@Param("fechaInicio") DateTime fechaInicio,
            @Param("fechaInicio") DateTime fechaFin);

    /**
     *
     * @metodo Metodo de acceso a datos findByTramiteInAndFechaInicioBetween que permite consultar las solicitudes
     * existentes entre fechaInicio, FechaFin y Una lista de Tramite.
     * @author Oscar Montilla
     * @version 8.5 - 7 de Jun. de 2016
     * @param listaTramite Objeto listaTramite del modelo ontologico de Tramite
     * @param fechaInicio Date fechaInicio de solicitud.
     * @param fechaFin Date fechaFin de solicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad>
            findByTramiteInAndFechaInicioBetween(@Param("listaTramite") List<TramiteEntidad> listaTramite,
                    @Param("fechaInicio") DateTime fechaInicio, @Param("fechaFin") DateTime fechaFin);

    /**
     *
     * @metodo Metodo de acceso a datos findByTramiteInAndFechaInicioBetween que permite consultar las solicitudes
     * existentes entre fechaInicio, FechaFin y Una lista de Estatus.
     * @author Oscar Montilla
     * @version 8.5 - 7 de Jun. de 2016
     * @param listaEstatus Objeto listaTramite del modelo ontologico de EstatusSolicitud
     * @param fechaInicio Date fechaInicio de solicitud.
     * @param fechaFin Date fechaFin de solicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad>
            findByEstatusInAndFechaInicioBetween(@Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus,
                    @Param("fechaInicio") DateTime fechaInicio, @Param("fechaFin") DateTime fechaFin);

    /**
     *
     * @metodo Metodo de acceso a datos findByTramiteInAndFechaInicioBetween que permite consultar las solicitudes
     * existentes entre fechaInicio, FechaFin, Una lista de Estatus y una lista de tramite.
     * @author Oscar Montilla
     * @version 8.5 - 7 de Jun. de 2016
     * @param listaTramite Objeto listaTramite del modelo ontologico de Tramite.
     * @param listaEstatus Objeto listaTramite del modelo ontologico de EstatusSolicitud.
     * @param fechaInicio Date fechaInicio de solicitud.
     * @param fechaFin Date fechaFin de solicitud.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad>
            findByTramiteInAndEstatusInAndFechaInicioBetween(@Param("listaTramite") List<TramiteEntidad> listaTramite,
                    @Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus,
                    @Param("fechaInicio") DateTime fechaInicio, @Param("fechaFin") DateTime fechaFin);

    /**
     *
     * @metodo Metodo de acceso a datos findByTramiteInAndFechaInicioBetween que permite consultar las solicitudes
     * existentes entre una lista de Estatus y una lista de tramite.
     * @author Oscar Montilla
     * @version 8.5 - 7 de Jun. de 2016
     * @param listaTramite Objeto listaTramite del modelo ontologico de EstatusSolicitud.
     * @param listaEstatus Objeto listaTramite del modelo ontologico de Tramite.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> findByTramiteInAndEstatusIn(@Param("listaTramite") List<TramiteEntidad> listaTramite,
            @Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus);

    /**
     * @metodo Metodo de acceso a datos findByOficinaFuncionarioOficinaId que permite consultar las solicitudes por id
     * de oficina
     * @param id Long id de Oficina.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> findByOficinaFuncionarioOficinaId(@Param("id") long id);

    /**
     * @metodo Metodo de acceso a datos findByEstatusInAndOficinaFuncionarioOficinaId que permite consultar una lista de
     * solicitudes por una lista de Estatus y id de oficina
     * @param listaEstatus Objeto listaTramite del modelo ontologico de Tramite.
     * @param id Long id de Oficina.
     * @return List<SolicitudEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion de
     * solicitud.
     */
    public List<SolicitudEntidad> findByEstatusInAndOficinaFuncionarioOficinaId(
            @Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus,
            @Param("id") long id);

    /**
     *
     * @metodo Buscar Solicitud por ID (Usando CrudRepository directamente);
     * @param id de Solicitud.
     * @return SolicitudEntidad - Objeto del modelo ontologico que contiene la informacion de solicitud.
     *
     */
    public SolicitudEntidad findById(@Param("id") long id);

}
