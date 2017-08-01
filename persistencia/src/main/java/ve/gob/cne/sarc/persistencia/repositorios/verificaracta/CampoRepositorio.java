package ve.gob.cne.sarc.persistencia.repositorios.verificaracta;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.CampoEntidad;

public interface CampoRepositorio extends CrudRepository<CampoEntidad, Long> {
	
	public CampoEntidad findById(@Param("id") long id);
	
	public List<CampoEntidad> findAll();
	
	public CampoEntidad findByTxNombre(@Param("txNombre") String txNombre);

}
