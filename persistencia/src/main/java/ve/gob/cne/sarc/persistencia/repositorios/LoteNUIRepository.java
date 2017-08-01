package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import ve.gob.cne.sarc.persistencia.entidades.LoteNUIEntidad;

/**
 * LoteNUIRepository.java
 *
 * @descripcion Clase Repositorio de la entidad LoteNUIRepository
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
public interface LoteNUIRepository extends
        CrudRepository<LoteNUIEntidad, Long> {

}
