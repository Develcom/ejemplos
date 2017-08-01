package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;

/**
 * PaisRepository.java
 *
 * @descripcion Clase Repositorio de la entidad PaisEntidad
 * @author: Oscar Montilla
 * @version: 1.0
 */
public interface PaisRepository extends CrudRepository<PaisEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un Pais
     * @param sort La variable para ordenar una Lista de Pais.
     * @return List<PaisEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Pais.
     */
    List<PaisEntidad> findAll(Sort sort);

    /**
     *
     * @metodo Metodo de acceso a datos findByEstadosId que permite consultar todos los datos de un Pais
     * @param id Long consecutivo de Estado
     * @return PaisEntidad - Objeto del modelo ontologico que contiene la informacion de Pais.
     */
    PaisEntidad findByEstadosId(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar todos los datos de un Pais
     * @param id Long consecutivo de pais
     * @return PaisEntidad - Objeto del modelo ontologico que contiene la informacion de pais
     */
    PaisEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar todos los datos de un Pais
     * @param nombre String Nombre de pais
     * @return PaisEntidad - Objeto del modelo ontologico que contiene la informacion de pais
     */
    PaisEntidad findByNombre(@Param("nombre") String nombre);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar todos los datos de un Pais por el nombre de
     * Estado
     * @param nombre String Nombre de pais
     * @return PaisEntidad - Objeto del modelo ontologico que contiene la informacion de pais
     */
    PaisEntidad findByEstadosNombreIn(@Param("nombre") String nombre);

    /**
     * @metodo Metodo de acceso a datos findByEstadosIdIn que permite consultar todos los datos de un Pais por el Id de
     * estado
     * @param id Long consecutivo de pais
     * @return PaisEntidad - Objeto del modelo ontologico que contiene la informacion de pais
     */
    PaisEntidad findByEstadosIdIn(@Param("id") Long id);

}
