package ve.gob.cne.sarc.persistencia.repositorios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.VMParroquiaEntidad;

public interface VMParroquiaRepository extends CrudRepository<VMParroquiaEntidad, Long> {

    public VMParroquiaEntidad findById(@Param("id") long id);

    public List<VMParroquiaEntidad> findAll();

    public List<VMParroquiaEntidad> findByNuPadreGeografico(@Param("nuPadreGeografico") BigDecimal nuPadreGeografico);

}
