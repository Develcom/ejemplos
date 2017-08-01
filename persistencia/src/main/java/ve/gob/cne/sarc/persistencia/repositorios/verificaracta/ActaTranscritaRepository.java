package ve.gob.cne.sarc.persistencia.repositorios.verificaracta;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaTranscritaEntidad;

/**
 * ActaTranscritaRepository.java
 * Descripcion de la clase: Interfaz que Contiene todos los metodos utilizados que se comunican con el Repositorio
 * @version 1.0
 * 20 de jun. de 2016
 * @author Domingo Rondon
 */

public interface ActaTranscritaRepository extends CrudRepository<ActaTranscritaEntidad, Long> {	
	
	/**
	 * findByNumeroActa
	 * Busca el Acta Transcrita del Ciudadano por Numero de Acta 
	 * @since 20 de jun. de 2016
	 * @param numeroActa
	 * @return ActaTranscritaEntidad
	 * @author Domingo Rondon
	 * @version 1.0
	 * Jira SST-216
	 */
	public ActaTranscritaEntidad findByNumeroActa(@Param("numeroActa") long numeroActa);
	
	/**
	 * findById
	 * Busca el Acta Transcrita del Ciudadano por Id del Acta 
	 * @since 17 de oct. de 2016
	 * @param id
	 * @return ActaTranscritaEntidad
	 * @author Jennifer Alvarez
	 * @version 1.0
	 * Jira S2RC-514
	 */
	public ActaTranscritaEntidad findById(@Param("id") long id);
	
	
}
