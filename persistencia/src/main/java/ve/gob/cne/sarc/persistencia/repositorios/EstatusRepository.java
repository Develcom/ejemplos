package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.EstatusEntidad;

/**
 * EstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad EstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface EstatusRepository extends
        CrudRepository<EstatusEntidad, Long> {

}
