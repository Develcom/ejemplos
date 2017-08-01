package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.LibroDiarioEntidad;

/**
 * LibroDiarioRepository.java
 *
 * @descripcion Clase Repositorio de la entidad LibroDiarioEntidad
 * @version 1.0 12/12/2016
 * @author Oscar Montilla
 *
 */
public interface LibroDiarioRepository extends CrudRepository<LibroDiarioEntidad, Long> {

    /**
     * @metodo Metodo de consulta que obtiene un objeto de Libro Diario por medio de la fecha de creacion y el codigo de
     * oficina
     * @param fecha String fecha Creacion del Libro Diario (Se Realiza to_date en el @NameQuery)
     * @param codigo String codigo identificador de oficina
     * @return LibroDiarioEntidad - Objeto del modelo ontologico que contiene la informacion de Ciudadano.
     */
    LibroDiarioEntidad buscarLibroDiarioPorFecha(@Param("fecha") String fecha, @Param("codigo") String codigo);

    /**
     * @metodo Metodo de consulta que obitne un objeto de Libro Diario por medio de la fecha de creacion, el codigo de
     * oficina y el estatus de libro Diario
     * @param Fecha String fecha de creacion del Libro Diario (Se Realiza to_date en el @NameQuery)
     * @param codigo String Codigo de oficina
     * @param id Long id de estatus de Libro Diario
     * @return LibroDiarioEntidad - Objeto del modelo ontologico que contiene la informacion de Ciudadano.
     */
    LibroDiarioEntidad validarLibroDiarioOficina(
            @Param("fecha") String Fecha,
            @Param("codigo") String codigo,
            @Param("id") Long id);
}
