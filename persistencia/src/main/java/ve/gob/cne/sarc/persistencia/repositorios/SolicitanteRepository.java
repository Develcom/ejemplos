package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.SolicitanteEntidad;

/**
 * SolicitanteRepository.java
 *
 * @descripcion Clase Repositorio de la entidad SolicitanteEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface SolicitanteRepository extends CrudRepository<SolicitanteEntidad, Long> {

    /**
     *
     * @metodo Buscar Solicitante por Número (Usando CrudRepository directamente)
     * @param numeroDocumentoOficio String numeroDocumentoOficio de Solicitante.
     * @return SolicitanteEntidad - Objeto del modelo ontologico que contiene la informacion de Solicitante.
     */
    public SolicitanteEntidad findByNumeroDocumentoOficio(@Param("numeroDocumentoOficio") String numeroDocumentoOficio);

}
