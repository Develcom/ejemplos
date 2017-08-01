package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TipoLibroTipoOficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * TipoLibroTipoOficinaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TipoOficinaEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TipoLibroTipoOficinaRepository extends CrudRepository<TipoLibroTipoOficinaEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorIdTipoOficina que permite consultar los datos de un
     * TipoLibroTipoOficinaEntidad
     * @param id Long id de TipoLibroTipoOficina.
     * @return List<TipoLibroTipoOficinaEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de
     * TipoLibroTipoOficina.
     */
    List<TipoLibroTipoOficinaEntidad> buscarPorIdTipoOficina(@Param("id") long id);

    /**
     *
     * @metodo de acceso a datos findByTipoOficina que permite consultar los datos de un TipoLibroTipoOficinaEntidadS
     * @param tipoOficina Objeto del modelo ontologico que contiene la informacion de Oficina.
     * @return List<TipoLibroTipoOficinaEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de
     * TipoLibroTipoOficina.
     */
    List<TipoLibroTipoOficinaEntidad> findByTipoOficina(@Param("tipoOficina") TipoOficinaEntidad tipoOficina);

}
