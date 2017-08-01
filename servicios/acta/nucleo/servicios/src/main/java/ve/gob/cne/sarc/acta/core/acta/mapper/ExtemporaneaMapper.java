package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.acta.Extemporanea;
import ve.gob.cne.sarc.persistencia.entidades.ExtemporaneaEntidad;

/**
 * ExtemporaneaMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface ExtemporaneaMapper {

    /**
     * Metodo de mapeo de los objetos ExtemporaneaEntidad y Extemporanea
     *
     * @param extemporaneaEntidad Objeto ExtemporaneaEntidad
     * @return Extemporanea, instancia de objeto que contiene la informacion de
     * extemporanea
     */
    @Mappings({
        @Mapping(source = "extemporaneaEntidad.procedencia.textoExtracto", target = "extractoProcedencia"),
        @Mapping(source = "extemporaneaEntidad.procedencia.acta.numeroActa", target = "numeroActa")
    })
    Extemporanea entityToBo(ExtemporaneaEntidad extemporaneaEntidad);

    /**
     * Metodo de mapeo de los objetos Extemporanea y ExtemporaneaEntidad
     *
     * @param extemporanea instancia de objeto que contiene la informacion de
     * extemporanea
     * @return extemporaneaEntidad Objeto ExtemporaneaEntidad
     */
    ExtemporaneaEntidad boToEntity(Extemporanea extemporanea);

}
