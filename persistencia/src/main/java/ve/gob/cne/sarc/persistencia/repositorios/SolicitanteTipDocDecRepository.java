package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocDecEntidad;

/**
 * SolicitanteTipDocDecRepository.java
 *
 * @descripcion Clase Repositorio de la entidad SolicitanteTipDocDecEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface SolicitanteTipDocDecRepository extends
        CrudRepository<SolicitanteTipDocDecEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar los datos de un SolicitanteTipDocDecEntidad
     * @param id Long Identificador de de SolicitanteTipDocDec
     * @return SolicitanteTipDocDecEntidad - Objeto del modelo ontologico que contiene la informacion de
     * SolicitanteTipDo.
     */
    SolicitanteTipDocDecEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar los datos por el nombre de
     * SolicitanteTipDocDec
     * @param nombre String Nombre de SolicitanteTipDocDec
     * @return SolicitanteTipDocDecEntidad - Objeto del modelo ontologico que contiene la informacion de
     * SolicitanteTipDo
     */
    SolicitanteTipDocDecEntidad findByNombre(@Param("nombre") String nombre);

}
