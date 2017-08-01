package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;

/**
 * ParticipanteMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ActaMapper.class)
public interface ParticipanteMapper {

	/**
     * Convertir una entidad a un String
     * @param comunidadIndigenaEntidad entidad de tipo ComunidadIndigenaEntidad
     * @return objeto de tipo ComunidadIndigena
     */
	String comIndigenaEntidadToString(ComunidadIndigenaEntidad comunidadIndigenaEntidad);
	/**
     * Convertir una String a una entidad
     * @param comunidadIndigena String que describe la comunidad Indigena
     * @return entidad de tipo ComunidadIndigena
     */
	ComunidadIndigenaEntidad comIndigenaStringToEntidad(String comunidadIndigena);
    /**
     * Convertir una entidad a un pojo
     * @param participanteEntidad entidad de tipo ParticipanteEntidad
     * @return objeto de tipo Participante
     */
    @Mappings({
        @Mapping(source = "participanteEntidad.nacionalidad.nombre", target = "nacionalidad"),
        @Mapping(target = "ocupacion", source = "participanteEntidad.ocupacion.nombre")
    })
    public Participante entityToBO(ParticipanteEntidad participanteEntidad);

    /**
     * Convertir una lista entidad a una lista pojo
     * @param participanteEntidadList lista de objetos ParticipanteEntidad
     * @return lista de objetos de tipo Participante
     */
    public List<Participante> entitiesToBOs(List<ParticipanteEntidad> participanteEntidadList);

    /**
     * Convertir un pojo a una entidad
     * @param participante objeto de tipo Participante
     * @return objeto entidad de tipo ParticipanteEntidad
     */
    @Mappings({
        @Mapping(target = "nacionalidad", ignore = true),
        @Mapping(target = "ocupacion", ignore = true)
    })
    ParticipanteEntidad boToEntity(Participante participante);
}
