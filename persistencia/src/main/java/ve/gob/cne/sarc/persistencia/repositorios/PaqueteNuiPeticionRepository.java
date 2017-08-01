package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteNuiPeticionEntidad;

/**
 * PaqueteNuiPeticionRepository.java
 *
 * @descripcion Clase Repositorio de la entidad LoteNuiEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 03/01/2017
 */
public interface PaqueteNuiPeticionRepository extends
        CrudRepository<PaqueteNuiPeticionEntidad, Long> {

}
