/* AuditoriaEcuRepository 
 * 
 * Version 1.0  06/04/2016
 * 
 * Copyright
 */
package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import ve.gob.cne.sarc.persistencia.entidades.TramitePaqueteEntidad;

/**
 * <p>
 * Clase Repositorio de la entidad TramitePaqueteEntidad
 * </p>
 * @version 1.0  20 Mayo 2016
 * @author Jorge Maguina
 *
 */
public interface TramitePaqueteRepository extends CrudRepository<TramitePaqueteEntidad, Long> {
}
