package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;

/**
 * UsuarioRepository.java
 *
 * @descripcion Clase Repositorio de la entidad UsuarioEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface UsuarioRepository extends CrudRepository<UsuarioEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorNombre que permite consultar los datos de un usuario por el nombre
     * @param nombre String Nombre de usuario.
     * @return UsuarioEntidad - Objeto del modelo ontologico que contiene la informacion de Usuario.
     *
     */
    public UsuarioEntidad buscarPorNombre(@Param("nombre") String nombre);

}
