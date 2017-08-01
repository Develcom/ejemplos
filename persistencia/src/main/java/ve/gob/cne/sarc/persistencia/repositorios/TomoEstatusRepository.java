package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.TomoEstatusEntidad;

/**
 * TomoEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TomoEstatusEntidad
 * @version 1.0 12/12/2016
 * @author Oscar Montilla
 *
 */
public interface TomoEstatusRepository extends CrudRepository<TomoEstatusEntidad, Long> {

}
