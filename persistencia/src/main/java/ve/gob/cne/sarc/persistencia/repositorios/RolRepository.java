package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.RolEntidad;

/**
 * RolRepository.java
 *
 * @descripcion Clase Repositorio de la entidad RolEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface RolRepository extends CrudRepository<RolEntidad, Long> {

}
