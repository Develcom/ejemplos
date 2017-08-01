package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.participante.IntegranteConsejoComunal;
import ve.gob.cne.sarc.persistencia.entidades.IntegranteCartaConsejoComunalEntidad;

/**
 * IntegranteCartaCComunalMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {TipoDocumentoMapper.class})
public interface IntegranteCartaCComunalMapper {

    /**
     * Metodo de mapeo de entidad IntegranteCartaConsejoComunalEntidad a pojo IntegranteConsejoComunal
     *
     * @param integranteCartaConsejoComunalEntidad Objeto entidad de tipo IntegranteCartaConsejoComunalEntidad
     * @return Objeto de tipo IntegranteConsejoComunal
     */
    @Mappings({
        @Mapping(source = "integranteCartaConsejoComunalEntidad.nombreIntegrante", target = "primerNombre"),
        @Mapping(source = "integranteCartaConsejoComunalEntidad.apellidoIntegrante", target = "primerApellido"),
        @Mapping(source = "integranteCartaConsejoComunalEntidad.indicadorIntegrante", target = "tipoIntegrante"),
        @Mapping(source = "integranteCartaConsejoComunalEntidad.numeroDocumento", target = "numeroDocumento"),
        @Mapping(source = "integranteCartaConsejoComunalEntidad.cargo", target = "cargo"),
        @Mapping(source = "integranteCartaConsejoComunalEntidad.tipoDocumento", target = "tipoDocumento")
    })
    IntegranteConsejoComunal entityToBo(IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunalEntidad);

    /**
     * Metodo de mapeo de pojo IntegranteConsejoComunal a entidad IntegranteCartaConsejoComunalEntidad
     *
     * @param integranteConsejoComunal Objeto con la informacion del integrante del consejo comunal
     * @return Entidad IntegranteCartaConsejoComunalEntidad
     */
    @Mappings({
        @Mapping(source = "integranteConsejoComunal.primerNombre", target = "nombreIntegrante"),
        @Mapping(source = "integranteConsejoComunal.primerApellido", target = "apellidoIntegrante"),
        @Mapping(source = "integranteConsejoComunal.tipoIntegrante", target = "indicadorIntegrante"),
        @Mapping(source = "integranteConsejoComunal.tipoDocumento", target = "tipoDocumento"),
        @Mapping(source = "integranteConsejoComunal.numeroDocumento", target = "numeroDocumento"),
        @Mapping(source = "integranteConsejoComunal.cargo", target = "cargo")
    })
    IntegranteCartaConsejoComunalEntidad boToEntity(IntegranteConsejoComunal integranteConsejoComunal);

    /**
     * Metodo de mapeo de una lista de pojo IntegranteConsejoComunal a una lista de entidad
     * IntegranteCartaConsejoComunalEntidad
     *
     * @param list Lista de objeto de tipo IntegranteConsejoComunal
     * @return Lista de objeto de tipo IntegranteCartaConsejoComunalEntidad
     */
    List<IntegranteCartaConsejoComunalEntidad> boToEntities(List<IntegranteConsejoComunal> list);

    /**
     * Metodo de mapeo de una lista de entidad IntegranteCartaConsejoComunalEntidad a una lista de pojo
     * IntegranteConsejoComunal
     *
     * @param list Lista de objeto de tipo IntegranteCartaConsejoComunalEntidad
     * @return Lista de objeto de tipo IntegranteConsejoComunal
     */
    List<IntegranteConsejoComunal> entitiesToBo(List<IntegranteCartaConsejoComunalEntidad> list);
}
