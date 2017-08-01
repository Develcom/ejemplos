package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.VMPaisEntidad;

public interface VMPaisRepository extends CrudRepository<VMPaisEntidad, Long> {

    public VMPaisEntidad findById(@Param("id") long id);
    
    @Override
    @Query
    public List<VMPaisEntidad> findAll();
}
