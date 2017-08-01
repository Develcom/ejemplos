package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.HuellasRegistradaEntidad;

/**
 * Interfaz de operaciones CRUD para la entidad de huellas registradas
 * 
 * @author Ismayer Hernandez
 *
 */
public interface HuellasRegistradasRepository  extends CrudRepository<HuellasRegistradaEntidad, Long> {

	public HuellasRegistradaEntidad findByIdSolicitud(String id);
	public HuellasRegistradaEntidad findByCoHuellas(long id);
	public HuellasRegistradaEntidad findByIdCiudadano(String id);
	public List<HuellasRegistradaEntidad> findAll();	
}
