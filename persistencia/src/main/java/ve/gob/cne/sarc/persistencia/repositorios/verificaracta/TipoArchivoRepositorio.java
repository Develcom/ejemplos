/**
 *@TipoArchivoRepositorio.java
 * @version 1.0
 * 21/6/2016
 * Copyright
 */
package ve.gob.cne.sarc.persistencia.repositorios.verificaracta;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.TipoArchivoEntidad;

/**
 * TipoArchivoRepositorio.java
 * @descripcion Interfaz que Contiene todos los metodos utilizados que se comunican con el Repositorio
 * @version 
 * 21/6/2016
 * @author Maricruz Lista
 */
public interface TipoArchivoRepositorio extends CrudRepository<TipoArchivoEntidad, Long> {

}
