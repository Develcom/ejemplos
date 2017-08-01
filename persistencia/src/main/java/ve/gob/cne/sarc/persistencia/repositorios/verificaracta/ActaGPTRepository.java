package ve.gob.cne.sarc.persistencia.repositorios.verificaracta;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaGPTEntidad;

/**
 * ActaGPTRepository.java
 * @descripcion Interfaz que Contiene todos los metodos utilizados que se comunican con el Repositorio
 * @version 1.0
 * 23 de jun. de 2016
 * @author Domingo Rondon
 */
public interface ActaGPTRepository extends CrudRepository<ActaGPTEntidad, Long>{
    
    /** 
     * Traza de historias Jira SST-217.
     * @see org.springframework.data.repository.CrudRepository#findAll()
     */
    public List<ActaGPTEntidad> findAll();

}
