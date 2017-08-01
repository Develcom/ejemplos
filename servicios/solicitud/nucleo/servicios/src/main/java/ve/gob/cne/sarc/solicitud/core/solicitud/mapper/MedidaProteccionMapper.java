package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.MedidaProteccion;
import ve.gob.cne.sarc.persistencia.entidades.MedidaProteccionEntidad;

/**
 * MedidaProteccionMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface MedidaProteccionMapper {
    
    /**
     * Convierte una entidad a un pojo
     * @param medidaProteccionEntidad objeto de tipo MedidaProteccionEntidad
     * @return objeto de Tipo MedidaProteccion
     */
    MedidaProteccion entityToBo(MedidaProteccionEntidad medidaProteccionEntidad);
    
    /**
     * Convierte un pojo a una entidad
     * @param medidaProteccion objeto de tipo MedidaProteccion
     * @return objeto de tipo MedidaProteccionEntidad
     */
    MedidaProteccionEntidad boToEntity(MedidaProteccion medidaProteccion);
    
}
