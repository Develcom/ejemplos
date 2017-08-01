package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.oficina.Usuario;

/**
 * UsuarioMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    /**
     * Metodo de mapeo de entidad UsuarioEntidad a un pojo Usuario
     *
     * @param usuarioEntidad Objeto entidad de UsuarioEntidad
     * @return Objeto de tipo Usuario
     */
    public Usuario entityToBO(UsuarioMapper usuarioEntidad);

    /**
     * Metodo de mapeo de una lista de entidad UsuarioEntidad a una lista de pojo Usuario
     *
     * @param usuarioEntidadList Lista de objetos de tipo UsuarioEntidad
     * @return Lista de objetos de tipo Usuario
     */
    public List<Usuario> entitiesToBOs(List<UsuarioMapper> usuarioEntidadList);
}
