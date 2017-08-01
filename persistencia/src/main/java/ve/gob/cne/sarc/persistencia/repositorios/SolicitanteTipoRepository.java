package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipoEntidad;

/**
 * SolicitanteTipoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad SolicitanteTipoEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 *
 */
public interface SolicitanteTipoRepository extends
        CrudRepository<SolicitanteTipoEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar todos los de un SolicitanteTipoEntidad
     * @param id Long Identificador de de SolicitanteTipo.
     * @return SolicitanteTipoEntidad - Objeto del modelo ontologico que contiene la informacion de SolicitanteTipo.
     */
    SolicitanteTipoEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar todos los Tipo de solicitante por Nombre
     * @param nombreSolicitanteTipo String Nombre de Solicitante Tipo
     * @return SolicitanteTipoEntidad - Objeto del modelo ontologico que contiene la informacion de SolicitanteTipo.
     */
    SolicitanteTipoEntidad findByNombreSolicitanteTipo(@Param("nombreSolicitanteTipo") String nombreSolicitanteTipo);

}
