package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;

/**
 * MunicipioMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de MunicipioEntidad en
 * instancias de Municipio y viceversa]
 * @version 1.0 20/7/2016
 * @author Gabriela Palmar
 */
@Mapper(componentModel = "spring", uses=ParroquiaMapper.class)
public interface MunicipioMapper {

    /**
     * Convierte una entidad a un pojo
     * @param municipioEntidad MunicipioEntidad entidad con la informacion del municipio
     * @return Pojo municipio
     */
    Municipio entityToBo (MunicipioEntidad municipioEntidad);
    
    /**
     * Convierte un pojo a una entidad
     * @param municipio Municipio pojo con la informacion de municipio
     * @return Objeto de tipo Entidad
     */
    MunicipioEntidad boToEntity(Municipio municipio);
    
}
