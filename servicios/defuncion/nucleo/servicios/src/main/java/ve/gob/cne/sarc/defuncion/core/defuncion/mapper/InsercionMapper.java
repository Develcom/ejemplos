package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Insercion;
import ve.gob.cne.sarc.persistencia.entidades.InsercionEntidad;

/**
 * InsercionMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring", uses = ParroquiaMapper.class)
public interface InsercionMapper {
    
    /**
     * Convertir una entidad a un pojo
     * @param insercionEntidad InsercionEntidad entidad con la informacion de una insercion
     * @return Pojo Insercion
     */
    Insercion entityToBo(InsercionEntidad insercionEntidad);
    
    /**
     * Convertir un pojo a una entidad
     * @param insercion Insercion pojo con la informacion de insercion
     * @return Objeto de tipo Entidad
     */
    InsercionEntidad boToEntity(Insercion insercion);
    
}
