package ve.gob.cne.sarc.persistencia.repositorios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.VMMunicipioEntidad;

public interface VMMunicipioRepository extends CrudRepository<VMMunicipioEntidad, Long> {

    public VMMunicipioEntidad findById(@Param("id") long id);

    public List<VMMunicipioEntidad> findByNuPadreGeografico(@Param("nuPadreGeografico") BigDecimal nuPadreGeografico);
}
