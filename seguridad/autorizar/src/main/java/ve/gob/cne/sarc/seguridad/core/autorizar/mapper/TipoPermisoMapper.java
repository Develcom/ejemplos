/**
 * 
 */
package ve.gob.cne.sarc.seguridad.core.autorizar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ve.gob.cne.sarc.persistencia.entidades.TipoPermisoEntidad;
import ve.gob.sarc.seguridad.bo.TipoPermisoBO;
/**
 * TipoPermisoMapper.java
 * Descripcion de la clase: Mapea de entities a BO para acceder a las variables.
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */

@Mapper(componentModel = "spring")
public interface TipoPermisoMapper {
    
    TipoPermisoMapper INSTANCE = Mappers.getMapper(TipoPermisoMapper.class);

    TipoPermisoBO TipoPermisoEntidadToBO (TipoPermisoBO TipoPermisoBO);
    
    TipoPermisoEntidad TipoPermisoBOToEntidad(TipoPermisoBO TipoPermisoBO);
    
}
