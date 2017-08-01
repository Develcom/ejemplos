package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ActaEstatusEntidad;

/**
 * ActaEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ActaEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public interface ActaEstatusRepository extends
        CrudRepository<ActaEstatusEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findByIdOrderByIdAsc que permite consultar los datos de un ActaEstatus por el id
     * @author Oscar Montilla
     * @param id String id de estatus acta.
     * @return ActaEstatusEntidad - Objeto del modelo ontologico que contiene la informacion de Acta Estatus.
     */
    public ActaEstatusEntidad findByIdOrderByIdAsc(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar los datos de un ActaEstatus por el Nombre
     * @param nombre String nombre de Estatus de acta
     * @return ActaEstatusEntidad - Objeto del modelo ontologico que contiene la informacion de Acta Estatus.
     */
    ActaEstatusEntidad findByNombre(@Param("nombre") String nombre);

}
