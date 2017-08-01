package ve.gob.cne.sarc.persistencia.repositorios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.VMEstadoEntidad;

public interface VMEstadoRepository extends CrudRepository<VMEstadoEntidad, Long> {

    public VMEstadoEntidad findById(@Param("id") long id);

    public List<VMEstadoEntidad> findByNuPadreGeografico(@Param("nuPadreGeografico") BigDecimal nuPadreGeografico);
}
