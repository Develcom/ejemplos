package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.persistencia.entidades.DeclaracionJuradaEntidad;

/**
 * DeclaracionJuradaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ParticipanteMapper.class})
public interface DeclaracionJuradaMapper {

    /**
     * Metodo que mapea la entidad DeclaracionJuradaEntidad a un pojo DeclaracionJurada
     *
     * @param declaracionJuradaEntidad Objeto entidad de tipo DeclaracionJuradaEntidad
     * @return Objeto de tipo DeclaracionJurada
     */
    @Mappings({
        @Mapping(source = "declaracionJuradaEntidad.solicitud.numero", target = "numeroSolicitud"),
        @Mapping(source = "declaracionJuradaEntidad.indicadorTipo", target = "tipoDeclaracion"),
        @Mapping(source = "declaracionJuradaEntidad.fechaDeclaracionJurada", target = "fechaDeclaracion"),
        @Mapping(source = "declaracionJuradaEntidad.participantes", target = "participantes") 
    })
    public DeclaracionJurada entityToBo(DeclaracionJuradaEntidad declaracionJuradaEntidad);

    /**
     * Metodo que mapea una lista de entidad DeclaracionJuradaEntidad a una lista pojo DeclaracionJurada
     *
     * @param declaracionJuradaEntidad Lista de objetos de tipo DeclaracionJuradaEntidad
     * @return Lista de objetos de tipo DeclaracionJurada
     */
    public List<DeclaracionJurada> entitiesToBOs(List<DeclaracionJuradaEntidad> declaracionJuradaEntidad);

}
