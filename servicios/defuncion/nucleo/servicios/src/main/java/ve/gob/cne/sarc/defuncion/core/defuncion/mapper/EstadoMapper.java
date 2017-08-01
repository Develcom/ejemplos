package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;

/**
 * EstadoMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de EstadoEntidad en
 * instancias de Estado y viceversa]
 * @version 1.0 20/7/2016
 * @author Gabriela Palmar
 */
@Mapper(componentModel = "spring", uses = MunicipioMapper.class)
public interface EstadoMapper {

    /**
     * Convierte una entidad a un pojo
     * @param estadoEntidad estadoEntidad entidad con la informacion de estado
     * @return Pojo Estado
     */
    Estado entityToBo (EstadoEntidad estadoEntidad);
    
    /**
     * Convierte un pojo a una entidad
     * @param estado Estado pojo con la informacion de estado
     * @return Objeto de tipo Entidad
     */
    EstadoEntidad boToEntity(Estado estado);
    
}
