/**
 * 
 */
package ve.gob.cne.sarc.seguridad.core.autorizar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ve.gob.sarc.seguridad.bo.RolBO;

/**
 * TipoPermisoMapper.java
 * Descripcion de la clase: Mapea de entities a BO para acceder a las variables.
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */

@Mapper(componentModel = "spring")
public interface RolMapper {
    
    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);

    public RolBO RolEntidadToBO (RolBO moduloBO);
    
    //public RolEntidad RolBOToEntidad(RolBO rolBO);
    
}
