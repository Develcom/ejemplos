package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.GeograficoEntidad;

public interface GeograficoRepository extends CrudRepository<GeograficoEntidad, Long> {

    public GeograficoEntidad findById(@Param("id") Long id);
}
