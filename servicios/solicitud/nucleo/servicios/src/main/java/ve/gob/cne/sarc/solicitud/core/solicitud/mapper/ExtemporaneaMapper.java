package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Extemporanea;
import ve.gob.cne.sarc.persistencia.entidades.ExtemporaneaEntidad;

/**
 * ExtemporaneaMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface ExtemporaneaMapper {

    /**
     * Convierte una entidad a una pojo
     * @param extemporaneaEntidad objeto de tipo ExtemporaneaEntidad
     * @return objeto de tipo Extemporanea
     */
    Extemporanea entityToBo(ExtemporaneaEntidad extemporaneaEntidad);

    /**
     * Convierte un pojo a una entidad
     * @param extemporanea objeto de tipo Extemporanea
     * @return objeto de tipo ExtemporaneaEntidad
     */
    ExtemporaneaEntidad boToEntity(Extemporanea extemporanea);

}
