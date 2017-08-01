package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.PermisologiaEntidad;

public interface PermisologiaRepository extends CrudRepository<PermisologiaEntidad, Long>{
    	
	
}
