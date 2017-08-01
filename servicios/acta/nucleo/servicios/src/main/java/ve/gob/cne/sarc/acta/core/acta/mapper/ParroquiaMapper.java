package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * ParroquiaMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring")
public interface ParroquiaMapper {
    
    /**
     * Metodo de mapeo de los objetos ParroquiaEntidad y Parroquia
     *
     * @param parroquiaEntidad Objeto ParroquiaEntidad
     * @return parroquia, instancia de objeto que contiene la informacion de
     * parroquia
     */
    Parroquia entityToBo(ParroquiaEntidad parroquiaEntidad);
    
    /**
     * Metodo de mapeo de los objetos Parroquia y ParroquiaEntidad
     *
     * @param parroquia instancia de objeto que contiene la informacion de
     * Parroquia
     * @return ParroquiaEntidad Objeto ParroquiaEntidad
     */
    ParroquiaEntidad boToEntity(Parroquia parroquia);
}
