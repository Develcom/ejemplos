package ve.gob.cne.sarc.persistencia.repositorios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.VMInhospitoEntidad;

public interface VMInhospitoRepository extends CrudRepository<VMInhospitoEntidad, Long> {

    public VMInhospitoEntidad findById(@Param("id") long id);

    public List<VMInhospitoEntidad> findByNuPadreGeografico(@Param("nuPadreGeografico") BigDecimal nuPadreGeografico);
}
