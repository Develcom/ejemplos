package ve.gob.cne.sarc.persistencia.repositorios.verificaracta;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ArchivoActaEntidad;

/**
 * ArchivoActaRepository.java
 * @descripcion Interfaz que Contiene todos los metodos utilizados que se comunican con el Repositorio
 * @version 1.0
 * 21 de jun. de 2016
 * @author Domingo Rondon
 */
public interface ArchivoActaRepository extends CrudRepository<ArchivoActaEntidad, Long>{


}
