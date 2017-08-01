package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.ModuloEntidad;

/**
 * ModuloRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ModuloEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface ModuloRepository extends CrudRepository<ModuloEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un ModuloEntidad
     * @return List<ModuloEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Modulo.
     */
    @Override
    public List<ModuloEntidad> findAll();
}
