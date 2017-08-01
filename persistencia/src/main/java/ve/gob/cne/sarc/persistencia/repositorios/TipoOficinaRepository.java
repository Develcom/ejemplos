package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * TipoOficinaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TipoOficinaEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 */
public interface TipoOficinaRepository extends CrudRepository<TipoOficinaEntidad, Long> {

    /**
     * @metodo Metodo para buscar un tipo de oficina por su ID
     * @param id ID del tipo de oficina
     * @return TipoOficinaEntidad - Objeto del modelo ontologico que contiene la informacion de TipoOficina.
     *
     */
    public TipoOficinaEntidad buscarPorId(@Param("id") String id);

    /**
     * @metodo Metodo para buscar todas los tipos oficinas
     * @param sort La variable para ordenar una Lista de Ciudad.
     * @return List<TipoOficinaEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de
     * TipoOficina.
     */
    List<TipoOficinaEntidad> findAll(Sort sort);
}
