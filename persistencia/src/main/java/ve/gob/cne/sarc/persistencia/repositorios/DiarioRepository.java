package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;

import ve.gob.cne.sarc.persistencia.entidades.DiarioEntidad;

/**
 * Diariorepository.java
 *
 * @descripcion Clase Repositorio de la entidad DiarioEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface DiarioRepository extends
        CrudRepository<DiarioEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudNumero que permite consultar todos los datos de un Diario
     * @param numero String Numero de solicitud
     * @return DiarioEntidad - Objeto del modelo ontologico que tiene la informacion de Diario
     */
    DiarioEntidad findBySolicitudNumero(@Param("numero") String numero);

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudTramiteIn que permite consultar todos los datos de un Diario por
     * medio de una lista de Tramite
     * @param listaTramite Lista de objetos del modelo ontologico que contiene la informacion de Estatus.
     * @return DiarioEntidad - Lista de Objetos del modelo ontologico que tiene la informacion de Diario
     */
    public List<DiarioEntidad> findBySolicitudTramiteIn(@Param("listaTramite") List<TramiteEntidad> listaTramite);

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudEstatusIn que permite consultar todos los datos de un Diario por
     * medio de una lista de Estatus
     * @param listaEstatus Lista de objetos del modelo ontologico que contiene la informacion de Estatus.
     * @return DiarioEntidad - Lista de Objetos del modelo ontologico que tiene la informacion de Diario
     */
    public List<DiarioEntidad> findBySolicitudEstatusIn(
            @Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus);

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudTramiteInAndSolicitudEstatusIn que permite consultar todos los
     * datos de un Diario por medio de una lista de Estatus y una lista de tramite
     * @param listaTramite Lista de objetos del modelo ontologico que contiene la informacion de Estatus.
     * @param listaEstatus Lista de objetos del modelo ontologico que contiene la informacion de Estatus.
     * @return DiarioEntidad - Lista de Objetos del modelo ontologico que tiene la informacion de Diario
     */
    public List<DiarioEntidad> findBySolicitudTramiteInAndSolicitudEstatusIn(
            @Param("listaTramite") List<TramiteEntidad> listaTramite,
            @Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus);

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudTramiteInAndFechaRegistroBetween que permite consultar todos los
     * datos de un Diario por medio de una lista de Tramite y un rango de fecha.
     * @param listaTramite Lista de objetos del modelo ontologico que contiene la informacion de Tramite.
     * @param fechaInicio Date fechaInicio Para evualuar un rango de fecha en FechaRegistro que pertenece a Diario.
     * @param fechaFin Date fechafin Para evualuar un rango de fecha en FechaRegistro que pertenece a Diario.
     * @return DiarioEntidad - Lista de Objetos del modelo ontologico que tiene la informacion de Diario
     */
    public List<DiarioEntidad> findBySolicitudTramiteInAndFechaRegistroBetween(
            @Param("listaTramite") List<SolicitudEstatusEntidad> listaTramite,
            @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudEstatusInAndFechaRegistroBetween que permite consultar todos los
     * datos de un Diario por medio de una lista de Estatus y un rango de fecha.
     * @param listaEstatus Lista de objetos del modelo ontologico que contiene la informacion de Estatus.
     * @param fechaInicio Date fechaInicio Para evualuar un rango de fecha en FechaRegistro que pertenece a Diario.
     * @param fechaFin Date fechafin Para evualuar un rango de fecha en FechaRegistro que pertenece a Diario.
     * @return DiarioEntidad - Lista de Objetos del modelo ontologico que tiene la informacion de Diario
     */
    public List<DiarioEntidad> findBySolicitudEstatusInAndFechaRegistroBetween(
            @Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus,
            @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudEstatusInAndSolicitudTramiteAndFechaRegistroBetween que permite
     * consultar todos los datos de un Diario por medio de una lista de Estatus, una lista de tramite y un rango de
     * fecha.
     * @param listaEstatus Lista de objetos del modelo ontologico que contiene la informacion de Estatus.
     * @param listaTramite Lista de objetos del modelo ontologico que contiene la informacion de Tramite.
     * @param fechaInicio Date fechaInicio Para evualuar un rango de fecha en FechaRegistro que pertenece a Diario.
     * @param fechaFin Date fechafin Para evualuar un rango de fecha en FechaRegistro que pertenece a Diario.
     * @return DiarioEntidad - Lista de Objetos del modelo ontologico que tiene la informacion de Diario
     */
    public List<DiarioEntidad> findBySolicitudEstatusInAndSolicitudTramiteAndFechaRegistroBetween(
            @Param("listaEstatus") List<SolicitudEstatusEntidad> listaEstatus,
            @Param("listaTramite") List<SolicitudEstatusEntidad> listaTramite,
            @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    /**
     *
     * @metodo Metodo de acceso a datos findByFechaRegistroBetween que permite consultar todos los datos de un Diario
     * por medio un rango de fecha.
     * @param fechaInicio Date fechafin Para evualuar un rango de fecha en FechaRegistro que pertenece a Diario.
     * @param fechaFin Date fechafin Para evualuar un rango de fecha en FechaRegistro que pertenece a Diario.
     * @return DiarioEntidad - Lista de Objetos del modelo ontologico que tiene la informacion de Diario
     */
    public List<DiarioEntidad> findByFechaRegistroBetween(
            @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

}
