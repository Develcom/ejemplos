package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.TramiteRolEntidad;

/**
 * TramiteRolRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TramiteRolEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TramiteRolRepository
        extends CrudRepository<TramiteRolEntidad, Long> {

}
