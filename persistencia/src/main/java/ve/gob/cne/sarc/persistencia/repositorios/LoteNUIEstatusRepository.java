package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import ve.gob.cne.sarc.persistencia.entidades.LoteNuiEstatusEntidad;

/**
 * LoteNUIEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad LoteNuiEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 03/01/2017
 */
public interface LoteNUIEstatusRepository extends
        CrudRepository<LoteNuiEstatusEntidad, Long> {

}
