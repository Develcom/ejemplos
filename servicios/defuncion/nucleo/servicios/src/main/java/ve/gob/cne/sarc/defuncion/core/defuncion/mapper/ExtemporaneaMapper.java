package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Extemporanea;
import ve.gob.cne.sarc.persistencia.entidades.ExtemporaneaEntidad;

/**
 * ExtemporaneaMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface ExtemporaneaMapper {
    
    /**
     * Convertir una entidad a un pojo
     * @param extemporaneaEntidad ExtemporaneaEntidad entidad con la informacion extemporanea
     * @return Pojo Extemporanea
     */
    Extemporanea entityToBo(ExtemporaneaEntidad extemporaneaEntidad);
    
    /**
     * Convertir un pojo a un a entidad
     * @param extemporanea Extemporanea pojo con la informacion de la misma
     * @return Objeto de tipo Entidad
     */
    ExtemporaneaEntidad boToEntity(Extemporanea extemporanea);
    
}
