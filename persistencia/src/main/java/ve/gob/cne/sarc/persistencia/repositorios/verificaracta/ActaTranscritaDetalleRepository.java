package ve.gob.cne.sarc.persistencia.repositorios.verificaracta;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaTranscritaDetalleEntidad;

/**
 * Descripcion de la clase: Interfaz que Contiene todos los metodos utilizados que se comunican con el Repositorio
 * 21 de jun. de 2016
 * @author Domingo Rondon
 *
 */
public interface ActaTranscritaDetalleRepository extends CrudRepository<ActaTranscritaDetalleEntidad, Long>{
    
    /** 
     * Traza de historias Jira SST-220.
     * @see org.springframework.data.repository.CrudRepository#findAll()
     */
    public List<ActaTranscritaDetalleEntidad> findAll();
    
    /**
	 * findById
	 * Busca el Acta Transcrita Detalle del Ciudadano por Id del Acta 
	 * @since 17 de oct. de 2016
	 * @param id
	 * @return ActaTranscritaDetalleEntidad
	 * @author Jennifer Alvarez
	 * @version 1.0
	 * Jira S2RC-514
	 */
    public ActaTranscritaDetalleEntidad findById(@Param("id") long id);
    
    /**
   	 * findByCampo
   	 * Busca el Acta Transcrita Detalle del Ciudadano por Id del Campo
   	 * @since 17 de oct. de 2016
   	 * @param id
   	 * @return ActaTranscritaDetalleEntidad
   	 * @author Jennifer Alvarez
   	 * @version 1.0
   	 * Jira S2RC-514
   	 */
    public ActaTranscritaDetalleEntidad findByCampoId(@Param("id") long id);
    
    /**
   	 * findByCampo
   	 * Busca el Acta Transcrita por el Acta Transcrita Detalle
   	 * @since 17 de oct. de 2016
   	 * @param id ActaTrascrita
   	 * @return ActaTranscritaDetalleEntidad
   	 * @author Jennifer Alvarez
   	 * @version 1.0
   	 * Jira S2RC-514
   	 */
    public List<ActaTranscritaDetalleEntidad> findByActaTranscritaId(@Param("id") long id);
    

}
