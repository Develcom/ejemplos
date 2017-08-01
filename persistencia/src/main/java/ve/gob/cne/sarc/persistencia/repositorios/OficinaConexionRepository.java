package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.OficinaConexionEntidad;

/**
 * OficinaConexionRepository.java
 *
 * @descripcion Clase Repositorio de la entidad OficinaConexionEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface OficinaConexionRepository extends
        CrudRepository<OficinaConexionEntidad, Long> {

}
