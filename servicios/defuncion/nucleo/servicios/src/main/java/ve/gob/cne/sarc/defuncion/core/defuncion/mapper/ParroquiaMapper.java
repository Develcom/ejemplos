package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * ParroquiaMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring", uses = EstadoMapper.class)
public interface ParroquiaMapper {
    
    /**
     * Convertir una entidad a un pojo
     * @param parroquiaEntidad ParroquiaEntidad entidad con la informacion de la parroquia
     * @return Pojo Parroquia
     */
    Parroquia entityToBo(ParroquiaEntidad parroquiaEntidad);
    
    /**
     * Convertir un pojo a una entidad
     * @param parroquia Parroquia pojo con la informacion de la parroquia
     * @return Objeto de tipo entidad
     */
    ParroquiaEntidad boToEntity(Parroquia parroquia);
}
