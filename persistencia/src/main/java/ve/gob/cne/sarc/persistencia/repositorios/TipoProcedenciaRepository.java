package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TipoProcedenciaEntidad;

/**
 * TipoProcedenciaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TipoProcedenciaEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TipoProcedenciaRepository
        extends CrudRepository<TipoProcedenciaEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findByNombre que permite consultar los datos de un TipoProcedencia
     * @param nombre String nombre de Tipo de Procedencia
     * @return TipoProcedenciaEntidad - objeto del model ontologico que posee la informacion de Tipo procedencia
     */
    TipoProcedenciaEntidad findByNombre(@Param("nombre") String nombre);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar los datos de un TipoProcedencia
     * @autor Oscar Montilla
     * @param id Long identificador de tipo procedencia
     * @return TipoProcedenciaEntidad - objeto del model ontologico que posee la informacion de Tipo procedencia
     */
    TipoProcedenciaEntidad findById(@Param("id") Long id);

}
