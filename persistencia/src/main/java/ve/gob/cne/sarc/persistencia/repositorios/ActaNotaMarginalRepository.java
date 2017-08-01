package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.ActaNotaMarginalEntidad;

/**
 * ActaNotaMarginalRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ActaNotaMarginalEntidad
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
public interface ActaNotaMarginalRepository extends CrudRepository<ActaNotaMarginalEntidad, Long> {

}
