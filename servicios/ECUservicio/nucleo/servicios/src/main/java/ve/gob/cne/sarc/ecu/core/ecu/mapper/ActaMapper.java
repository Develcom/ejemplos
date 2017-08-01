package ve.gob.cne.sarc.ecu.core.ecu.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;

/**
 * ActaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ParticipanteMapper.class})
public interface ActaMapper {

    /**
     * Metodo que convierte una entidad en un pojo
     *
     * @param actaEntidad Objeto de entidad de tipo ActaEntidad
     * @return Objeto de tipo Acta
     */
    @Mappings({
        @Mapping(target = "procedencias", ignore = true),
        @Mapping(target = "defuncion", ignore = true),
        @Mapping(target = "insercion", ignore = true),
        @Mapping(target = "solicitud", ignore = true),
        @Mapping(target = "oficinaFuncionario", ignore = true),
        @Mapping(target = "libro", ignore = true)
    })
    public Acta entityToBO(ActaEntidad actaEntidad);

    /**
     * Metodo que convierte una lista de entidad en una lista pojo
     *
     * @param actaEntidadList Lista de objetos de tipo ActaEntidad
     * @return Lista de objetos de tipo Acta
     */
    public List<Acta> entitiesToBOs(List<ActaEntidad> actaEntidadList);

    /**
     * Metodo que convierte un pojo en una entidad
     *
     * @param acta Objeto de tipo Acta
     * @return Objeto entidad de tipo ActaEntidad
     */
    @Mappings({
        @Mapping(target = "oficinaFuncionario", ignore = true),
        @Mapping(target = "solicitud", ignore = true),
        @Mapping(target = "libro", ignore = true),
        @Mapping(target = "defuncion", ignore = true),
        @Mapping(target = "procedencias", ignore = true),
        @Mapping(target = "insercion", ignore = true)
    })
    ActaEntidad boToEntity(Acta acta);
}
