package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;

/**
 * ComunidadIndigenaRepository.java
 *
 * @descipcion Clase Repositorio de la entidad ComunidadIndigenaEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface ComunidadIndigenaRepository extends CrudRepository<ComunidadIndigenaEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un ComunidadIndigenaRepository
     * @param sort La variable para ordenar una Lista de Comunidad Indigena.
     * @return List<ComunidadIndigena> - Lista de Objetos del modelo ontologico que contiene la informacion de Comudidad
     * Indigena.
     */
    List<ComunidadIndigenaEntidad> findAll(Sort sort);

    /**
     * @metodo Metodo de acceso findByNombre que pemite consultar todos los datos de una Comunidad Indigena por medio
     * del nombre
     * @param nombre String Nombre de Comunidad Indigena
     * @return ComunidadIndigenaEntidad - Objeto del modelo ontologico que contiene la informacion de Comudidad
     * Indigena.
     */
    ComunidadIndigenaEntidad findByNombre(@Param("nombre") String nombre);

}
