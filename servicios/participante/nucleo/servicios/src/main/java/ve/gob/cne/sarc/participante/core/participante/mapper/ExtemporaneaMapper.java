package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Extemporanea;
import ve.gob.cne.sarc.persistencia.entidades.ExtemporaneaEntidad;

/**
 * ExtemporaneaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface ExtemporaneaMapper {

    /**
     * Metodo que mapea la entidad ExtemporaneaEntidad a un pojo Extemporanea
     *
     * @param extemporaneaEntidad Objeto entidad de tipo ExtemporaneaEntidad
     * @return Objeto de tipo Extemporanea
     */
    Extemporanea entityToBo(ExtemporaneaEntidad extemporaneaEntidad);

    /**
     * Metodo que mapea el pojo Extemporanea a la entidad ExtemporaneaEntidad
     *
     * @param extemporanea Objeto de tipo Extemporanea
     * @return Objeto entidad de tipo ExtemporaneaEntidad
     */
    ExtemporaneaEntidad boToEntity(Extemporanea extemporanea);

}
