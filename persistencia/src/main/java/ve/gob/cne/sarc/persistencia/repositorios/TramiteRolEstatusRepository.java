package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.TramiteRolEstatusEntidad;

/**
 * TramiteRolEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TramiteRolEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TramiteRolEstatusRepository
        extends CrudRepository<TramiteRolEstatusEntidad, Long> {

}
