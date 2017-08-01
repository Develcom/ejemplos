package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.OficinaEstatusEntidad;

/**
 * OficinaEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad OficinaEstatusEntidad
 * @version 1.0 12/08/2016
 * @author Anabell De Faria
 *
 */
public interface OficinaEstatusRepository extends
        CrudRepository<OficinaEstatusEntidad, Long> {

}
