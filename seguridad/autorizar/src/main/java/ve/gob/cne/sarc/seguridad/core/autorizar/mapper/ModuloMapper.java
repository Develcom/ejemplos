/**
 * 
 */
package ve.gob.cne.sarc.seguridad.core.autorizar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ve.gob.sarc.seguridad.bo.ModuloBO;

/**
 * TipoPermisoMapper.java
 * Descripcion de la clase: Mapea de entities a BO para acceder a las variables.
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */

@Mapper(componentModel = "spring")
public interface ModuloMapper {
    
    ModuloMapper INSTANCE = Mappers.getMapper(ModuloMapper.class);
    
    public ModuloBO ModuloEntidadToBO (ModuloBO moduloBO);
    
    //public ModuloEntidad ModuloBOToEntidad(ModuloBO moduloBO);
    
}
