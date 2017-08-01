package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.InscripcionMayor18Entidad;

/**
 * InscripcionMayor18Repository.java
 *
 * @descripcion Clase Repositorio de la entidad InscripcionMayor18Entidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface InscripcionMayor18Repository extends
        CrudRepository<InscripcionMayor18Entidad, Long> {

}
