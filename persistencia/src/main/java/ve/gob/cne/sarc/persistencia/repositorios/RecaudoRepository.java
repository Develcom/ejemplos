package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.RecaudoEntidad;

/**
 * RecaudoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad RecaudoEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface RecaudoRepository extends CrudRepository<RecaudoEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar los datos de un RecaudoEntidad
     * @param id Long identificativo de Recaudo.
     * @return RecaudoEntidad - Objeto del modelo ontologico que contiene la informacion de Recaudo.
     */
    public RecaudoEntidad findById(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByCodigo que permite consultar los datos de un RecaudoEntidad
     * @param codigo String identificativo de Recaudo.
     * @return RecaudoEntidad - Objeto del modelo ontologico que contiene la informacion de Recaudo.
     */
    public RecaudoEntidad findByCodigo(@Param("codigo") String codigo);

}
