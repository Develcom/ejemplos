package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ExtemporaneaEntidad;

/**
 * ExtemporaneaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ExtemporaneaEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface ExtemporaneaRepository
        extends CrudRepository<ExtemporaneaEntidad, Long> {

    /**
     * @metodo acceso a datos findByNumeroProvidencia que permite consultar los datos de un Extemporanea por el Numero
     * de providencia
     * @param numeroProvidencia - String Numero de providencia de Extemporanea
     * @return ExtemporaneaEntidad - Objeto del modelo ontologico que contiene la inforamcion de Extemporanea
     */
    ExtemporaneaEntidad findByNumeroProvidencia(@Param("numeroProvidencia") String numeroProvidencia);

    /**
     * @metodo acceso a datos findByProcedenciaId que permite consultar los datos de un Extemporanea por id de
     * Providencia de providencia
     * @param id Long id de Procedencia
     * @return ExtemporaneaEntidad - Objeto del modelo ontologico que contiene la inforamcion de Extemporanea
     */
    ExtemporaneaEntidad findByProcedenciaId(@Param("id") long id);

}
