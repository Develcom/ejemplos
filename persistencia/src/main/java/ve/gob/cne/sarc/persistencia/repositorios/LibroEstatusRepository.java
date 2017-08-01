package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.LibroEstatusEntidad;

/**
 * LibroEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad LibroEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface LibroEstatusRepository extends CrudRepository<LibroEstatusEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findByNombre que permite consultar los datos de un LibroEstatus por el nombre
     * @param nombre String nombre de libro.
     * @return LibroEstatusEntidad - Objeto del modelo ontologico que contiene la informacion de Libro.
     *
     */
    public LibroEstatusEntidad findByNombreIgnoreCase(@Param("nombre") String nombre);

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar los datos de un LibroEstatus por el codigo
     * @param id Long de libro.
     * @return LibroEstatusEntidad - Objeto del modelo ontologico que contiene la informacion de Libro.
     *
     */
    public LibroEstatusEntidad findById(@Param("id") Long id);
}
