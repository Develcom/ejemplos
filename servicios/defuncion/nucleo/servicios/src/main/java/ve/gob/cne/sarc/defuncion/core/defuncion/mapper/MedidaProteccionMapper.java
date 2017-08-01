package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.MedidaProteccion;
import ve.gob.cne.sarc.persistencia.entidades.MedidaProteccionEntidad;

/**
 * MedidaProteccionMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface MedidaProteccionMapper {
    
    /**
     * Convertir una entidad a un pojo
     * @param medidaProteccionEntidad MedidaProteccionEntidad entidad con la informacion de una medida de proteccion
     * @return Pojo MedidaProteccion
     */
    MedidaProteccion entityToBo(MedidaProteccionEntidad medidaProteccionEntidad);
    
    /**
     * Converitr un pojo a una entidad
     * @param medidaProteccion MedidaProteccion pojo con la informacion de una medida de proteccion
     * @return Objeto de tipo entidad
     */
    MedidaProteccionEntidad boToEntity(MedidaProteccion medidaProteccion);
    
}
