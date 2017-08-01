package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.EscenarioEntidad;



/**
 * Repositorio de la entidad Escenario
 * 
 * @author Elvin.Gonzalez
 *
 */
public interface EscenarioRepository extends
        CrudRepository<EscenarioEntidad, Long> {

    /**
     * Busca los recaudos de un Escenario
     * 
     * @param idEscenario
     * @return
     */
    EscenarioEntidad findById(
            @Param("id") long id);    
    
}
