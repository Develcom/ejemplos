package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.CiudadEntidad;

/**
 * CiudadRepository.java
 *
 * @descripcion Clase Repositorio de la entidad CiudadEntidad
 * @author Anabell De Faria
 * @version: 1.0 11/08/2016
 *
 */
public interface CiudadRepository extends CrudRepository<CiudadEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un CiudadEntidad
     *
     * @author Anabell De Faria
     * @param sort La variable para ordenar una Lista de Ciudad.
     * @return List<CiudadEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Ciudad.
     */
    List<CiudadEntidad> findAll(Sort sort);
}
