package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEstatusEntidad;

/**
 * CiudadanoEstatusRepository
 *
 * @descripcion Clase Repositorio de la entidad CiudadanoEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface CiudadanoEstatusRepository extends CrudRepository<CiudadanoEstatusEntidad, Long> {

}
