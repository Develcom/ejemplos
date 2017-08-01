package ve.gob.cne.sarc.persistencia.repositorios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.VMEmbajadaEntidad;

public interface VMEmbajadaRepository extends CrudRepository<VMEmbajadaEntidad, Long> {

    public VMEmbajadaEntidad findById(@Param("Id") long id);

    public List<VMEmbajadaEntidad> findByNuPadreGeografico(@Param("nuPadreGeografico") BigDecimal nuPadreGeografico);
}
