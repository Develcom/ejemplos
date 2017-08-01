package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.oficina.Usuario;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;

/**
 * UsuarioMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    /**
     * Convierte una entidad a un pojo
     * @param usuarioEntidad objeto de tipo UsuarioEntidad
     * @return objeto de tipo Usuario
     */
    public Usuario entityToBO(UsuarioEntidad usuarioEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     * @param usuarioEntidadList lista de objetos de tipo UsuarioEntidad
     * @return lista de objetos de tipo Usuario
     */
    public List<Usuario> entitiesToBOs(List<UsuarioEntidad> usuarioEntidadList);
}
