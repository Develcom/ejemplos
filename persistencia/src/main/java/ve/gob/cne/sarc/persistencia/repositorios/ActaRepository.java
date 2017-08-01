package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;

/**
 * ActaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ActaEntidad
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
public interface ActaRepository extends CrudRepository<ActaEntidad, Long> {

    /**
     * @metodo Busca la lista de actas para una solicitud por su numero de solicitud
     * @param numeroSolicitud String Numero de la solicitud.
     * @return List<ActaEntidad> - Una lista de Objetos del modelo ontologico que contiene la informacion de Acta.
     */
    List<ActaEntidad> findBySolicitudNumero(@Param("numeroSolicitud") String numeroSolicitud);

    /**
     * @metodo Busca un Acta segun su numero
     * @param numeroActa String Numero de la acta.
     * @return ActaEntidad - Objeto del modelo ontologico que contiene la informacion de Acta.
     */
    ActaEntidad findByNumeroActa(@Param("numeroActa") String numeroActa);

    /**
     *
     * @metodo Metodo de acceso a datos findByOficinaFuncionarioOficinaId que permite consultar los datos de un
     * ActaRepository
     * @param id Long codigo de Oficina.
     * @return ActaEntidad - Objeto del modelo ontologico que contiene la informacion de Acta.
     */
    List<ActaEntidad> findByOficinaFuncionarioOficinaId(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByFechaCreacion que permite consultar los datos de un ActaRepository
     * @param fechaCreacion - Date fecha de creacion del Acta.
     * @return ActaEntidad - Objeto del modelo ontologico que contiene la informacion de Acta.
     */
    ActaEntidad findByFechaCreacion(@Param("fechaCreacion") Date fechaCreacion);

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudTramiteCodigo que permite consultar los datos de un
     * ActaRepository
     * @param codigo String codigo de Tramite.
     * @return ActaEntidad - Objeto del modelo ontologico que contiene la informacion de Acta.
     */
    List<ActaEntidad> findBySolicitudTramiteCodigo(@Param("codigo") String codigo);

    /**
     *
     * @metodo Metodo de acceso a datos findByNumeroFolio que permite consultar los datos de un Acta por el numero de
     * Folio
     * @param numeroFolio Integer codigo de numero de folio.
     * @return ActaEntidad - Objeto del modelo ontologico que contiene la informacion de Acta.
     */
    ActaEntidad findByNumeroFolio(@Param("numeroFolio") Integer numeroFolio);

    /**
     * @metodo Metodo de acceso a datos findByOficinaFuncionarioOficinaNombre que permite consultar los datos de un Acta
     * por nombre de Oficina
     * @param nombre String nombre de Oficina
     * @return ActaEntidad - Objeto del modelo ontologico que contiene la informacion de Acta.
     */
    List<ActaEntidad> findByOficinaFuncionarioOficinaNombre(@Param("nombre") String nombre);

    /**
     *
     * @metodo Metodo de acceso a datos findByOficinaFuncionarioOficinaCodigo que permite consultar los datos de un
     * ActaRepository por el codigo de oficina.
     * @author Oscar Montilla
     * @param codigo String codigo de Oficina.
     * @return ActaEntidad - Objeto del modelo ontologico que contiene la informacion de Acta.
     */
    List<ActaEntidad> findByOficinaFuncionarioOficinaCodigo(@Param("codigo") String codigo);
}
