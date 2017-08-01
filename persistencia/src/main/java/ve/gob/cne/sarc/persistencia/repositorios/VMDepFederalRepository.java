package ve.gob.cne.sarc.persistencia.repositorios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.VMDepFederalEntidad;

public interface VMDepFederalRepository extends CrudRepository<VMDepFederalEntidad, Long> {

	 public VMDepFederalEntidad findById(@Param("Id") long id);

    public List<VMDepFederalEntidad> findByNuPadreGeografico(@Param("nuPadreGeografico") BigDecimal nuPadreGeografico);
}
