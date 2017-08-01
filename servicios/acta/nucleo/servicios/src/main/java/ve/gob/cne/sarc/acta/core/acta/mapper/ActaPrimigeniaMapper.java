package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.acta.ActaPrimigenia;
import ve.gob.cne.sarc.persistencia.entidades.ActaPrimigeniaEntidad;

/**
 * ActaPrimigeniaMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y las clase del modelo de
 * datos correspondiente al Acta Primigenia]
 * @version 1.0 12/10/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface ActaPrimigeniaMapper {

    /**
     *
     * Metodo de mapeo de los objetos SolicitudEntidad y Solicitud
     *
     * @param actaPrimigeniaEntidad objeto de tipo ActaPrimigeniaEntidad
     * @return pojo ActaPrimigenia con la informacion del acta primigenia
     */
    @Mappings({
        @Mapping(source = "actaPrimigeniaEntidad.solicitud.numero", target = "numeroSolic"),
        @Mapping(source = "actaPrimigeniaEntidad.fechaIncripcion", target = "fechaIncripcion"),
        @Mapping(source = "actaPrimigeniaEntidad.oficina.nombre", target = "nombreOficina")
    })
    public ActaPrimigenia entityToBO(ActaPrimigeniaEntidad actaPrimigeniaEntidad);

    /**
     *
     * Metodo de mapeo de objetos SolicitudEntidad y Solicitud
     *
     * @param actaPrimigenia pojo ActaPrimigenia con los datos del acta primigenia
     * @return objeto de tipo ActaPrimigeniaEntidad
     */
    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    public ActaPrimigeniaEntidad boToEntity(ActaPrimigenia actaPrimigenia);

}
