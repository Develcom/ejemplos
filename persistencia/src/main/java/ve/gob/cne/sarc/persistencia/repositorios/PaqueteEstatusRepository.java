/* AuditoriaEcuRepository 
 * 
 * Version 1.0  06/04/2016
 * 
 * Copyright
 */
package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteEstatusEntidad;


/**
 * <p>
 * Clase Repositorio de la entidad PaqueteEstatusEntidad
 * </p>
 * @version 1.0  20 Mayo 2016
 * @author Jorge Maguina
 *
 */
public interface PaqueteEstatusRepository extends CrudRepository<PaqueteEstatusEntidad, Long> {

}
