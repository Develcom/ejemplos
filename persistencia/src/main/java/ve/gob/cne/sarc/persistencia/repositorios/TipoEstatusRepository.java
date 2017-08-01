package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.TipoEstatusEntidad;

/**
 * TipoEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TipoEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TipoEstatusRepository extends
        CrudRepository<TipoEstatusEntidad, Long> {

}
