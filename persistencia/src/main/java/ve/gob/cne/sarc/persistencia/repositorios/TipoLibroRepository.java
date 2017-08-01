package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;

/**
 * TipoLibroRepository.java
 *
 * @descriocion Clase Repositorio de la entidad CategoriaEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TipoLibroRepository extends
        CrudRepository<TipoLibroEntidad, Long> {

    /**
     *
     * @Metodo Metodo de acceso a datos findById que permite consultar los datos de un TipoLibroEntidad
     * @param id Long Identificador de TipoLibro.
     * @return TipoLibroEntidad - Objeto del modelo ontologico que contiene la informacion de TipoLibro.
     */
    TipoLibroEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar los datos por Nombre de tipo libro
     * @param nombre String nombre de Tipo libro
     * @return TipoLibroEntidad - Objeto del modelo ontologico que contiene la informacion de TipoLibro.
     */
    TipoLibroEntidad findByNombre(@Param("nombre") String nombre);

}
