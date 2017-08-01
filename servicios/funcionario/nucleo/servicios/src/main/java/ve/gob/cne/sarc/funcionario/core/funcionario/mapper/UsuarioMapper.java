package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ve.gob.cne.sarc.comunes.oficina.Usuario;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;

/**
 * UsuarioMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    /**
     * Convierte una entidad en un pojo
     *
     * @param usuarioEntidad describe la entidad Usuario
     * @return Usuario pojo que describe al Usuario
     */
    @Mapping(target = "claveUsuario", ignore = true)
    public Usuario entityToBO(UsuarioEntidad usuarioEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param usuarioEntidadList describe una lista de entidades de Usuario
     * @return List<Usuario> lista de pojos de Usuarios
     */
    public List<Usuario> entitiesToBOs(List<UsuarioEntidad> usuarioEntidadList);
}
