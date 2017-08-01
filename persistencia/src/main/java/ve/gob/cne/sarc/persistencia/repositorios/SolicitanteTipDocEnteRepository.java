package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocEnteEntidad;

/**
 * SolicitanteTipDocEnteRepository.java
 *
 * @descripcion Clase Repositorio de la entidad SolicitanteTipDocEnteEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface SolicitanteTipDocEnteRepository extends
        CrudRepository<SolicitanteTipDocEnteEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar todos los de un SolicitanteTipDocEnteEntidad
     * @param id Long Identificador de SolicitanteTipDoc.
     * @return SolicitanteTipDocEnteEntidad - Objeto del modelo ontologico que contiene la informacion de
     * SolicitanteTipDoc.
     *
     */
    SolicitanteTipDocEnteEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar todos los datos de SolicitanteTipDocEnte por
     * nombre
     * @param nombre String Nombre de SolicitanteTipDocEnte
     * @return SolicitanteTipDocEnteEntidad - Objeto del modelo ontologico que contiene la informacion de
     * SolicitanteTipDoc.
     */
    SolicitanteTipDocEnteEntidad findByNombre(@Param("nombre") String nombre);

}
