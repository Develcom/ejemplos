package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * ParroquiaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ParroquiaEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 */
public interface ParroquiaRepository extends CrudRepository<ParroquiaEntidad, Long> {

    /**
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un ParroquiaEntidad
     * @param sort La variable para ordenar una Lista de Parroquia.
     * @return List<ParroquiaEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Parroquia.
     */
    List<ParroquiaEntidad> findAll(Sort sort);

    /**
     * @metodo Metodo de acceso a datos findByMunicipioOrderByNombreAsc que permite consultar los datos por id de
     * ParroquiaEntidad
     * @param id Long de parroquia.
     * @return List<ParroquiaEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Parroquia.
     */
    public List<ParroquiaEntidad> findByMunicipioOrderByNombreAsc(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos buscarPorNombre que permite consultar todos los datos de un ParroquiaEntidad
     * @param nombre String nombre de parroquia.
     * @return List<ParroquiaEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Parroquia.
     */
    ParroquiaEntidad buscarPorNombre(@Param("nombre") String nombre);

    /**
     *
     * @metodo Metodo de acceso a datos findByCodigo que permite consultar todos los datos de un ParroquiaEntidad
     * @param id Long de Parroquia
     * @return ParroquiaEntidad - Objeto del modelo ontologico que contiene la informacion de Parroquia
     */
    ParroquiaEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombreAndMunicipioNombre que permite consultar todos los datos de un
     * ParroquiaEntidad por el nombre de parroquia y el nombre de Municipio
     *
     * @param nombreParroquia String Nombre de Parroquia
     * @param nombreMunicipio String Nombre de Municipio
     * @return ParroquiaEntidad - Objeto del modelo ontologico que contiene la informacion de Parroquia
     */
    ParroquiaEntidad findByNombreAndMunicipioNombre(
            @Param("nombreParroquia") String nombreParroquia, @Param("nombreMunicipio") String nombreMunicipio);

}
