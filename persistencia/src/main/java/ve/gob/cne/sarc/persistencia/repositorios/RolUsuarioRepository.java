package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.RolUsuarioEntidad;

/**
 * RolUsuarioRepository.java
 *
 * @descripcion Clase Repositorio de la entidad RolUsuarioEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface RolUsuarioRepository extends
        CrudRepository<RolUsuarioEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos buscarIdRolFunc que permite consultar todos los de un RolUsuarioEntidad
     * @param nombre String nombre del RolUsuario.ss
     * @return RolUsuarioEntidad - Objeto del modelo ontologico que contiene la informacion de RolUsuario.
     */
    RolUsuarioEntidad buscarIdRolFunc(@Param("nombre") String nombre);

}
