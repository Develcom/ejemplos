package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.AuditoriaEcuEntidad;

/**
 * AuditoriaEcuRepository.java
 *
 * @descripcion Clase Repositorio de la entidad AuditoriaEcuEntidad
 * @author Oscar Montilla
 * @version 07/08/2016
 *
 */
public interface AuditoriaEcuRepository
        extends CrudRepository<AuditoriaEcuEntidad, Long> {

    /**
     * @metodo Metodo que busca una lista de AuditoriaEcuEntidad basado en el identificador del ecu
     * @author Anabell De Faria
     * @param id long con el identificador del nui
     * @return List<AuditoriaEcuEntidad> - lista de AuditoriaEcuEntidad
     */
    List<AuditoriaEcuEntidad> findByEcu(@Param("id") long id);
}
