package ve.gob.cne.sarc.persistencia.repositorios.verificaracta;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.TramitePaqueteEntidad;

/**
 * TramitePaqueteRepository.java
 * @descripcion Interfaz que Contiene todos los metodos utilizados que se comunican con el Repositorio
 * @version 
 * 23 de jun. de 2016
 * @author Domingo Rondon
 */
public interface TramitePaqueteRepository extends CrudRepository<TramitePaqueteEntidad, Long>{
    
    /** 
     * Traza de historias Jira SST-225.
     * @see org.springframework.data.repository.CrudRepository#findAll()
     */
    public List<TramitePaqueteEntidad> findAll();

}
