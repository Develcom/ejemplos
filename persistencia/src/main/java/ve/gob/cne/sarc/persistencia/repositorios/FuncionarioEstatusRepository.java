package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEstatusEntidad;

/**
 * FuncionarioEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad FuncionarioEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface FuncionarioEstatusRepository extends
        CrudRepository<FuncionarioEstatusEntidad, Long> {

}
