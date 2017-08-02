/**
 * 
 */
package ve.gob.cne.sarc.seguridad.core.autorizar.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * TipoPermisoMapper.java
 * 
 * Descripcion de la clase: Mapea de entities a BO para acceder a las variables. 
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */

@Mapper(componentModel = "spring", uses = { ModuloMapper.class, RecursoMapper.class, RolMapper.class, TipoPermisoMapper.class })
public interface PermisologiaMapper {

    PermisologiaMapper INSTANCE = Mappers.getMapper(PermisologiaMapper.class);

//    PermisologiaBO permisologiaEntidadToBO(PermisologiaEntidad permisologia);
//    List<PermisologiaBO> listPermisologiaEntidadToBO(List<PermisologiaEntidad> permisologia);

}
