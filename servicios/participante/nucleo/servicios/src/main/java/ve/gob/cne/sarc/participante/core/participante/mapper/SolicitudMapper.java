package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * SolicitudMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ActaMapper.class, OficinaMapper.class, 
    FuncionarioMapper.class, SolicitanteMapper.class, TramiteMapper.class, ModuloMapper.class})
public interface SolicitudMapper {

    /**
     * Metodo de mapeo de la entidad SolicitudEntidad al pojo Solicitud
     *
     * @param solicitudEntidad Objeto entidad de tipo SolicitudEntidad
     * @return Solicitud Objeto de tipo Solicitud
     */
    @Mappings({
        @Mapping(source = "solicitudEntidad.numero", target = "numeroSolicitud"),
        @Mapping(source = "solicitudEntidad.tramite", target = "tramite"),
        @Mapping(source = "solicitudEntidad.tramite.modulo", target = "modulo"),
        @Mapping(source = "solicitudEntidad.oficinaFuncionario.oficina", target = "oficina"),
        @Mapping(source = "solicitudEntidad.oficinaFuncionario.funcionario", target = "funcionario"),
        @Mapping(source = "solicitudEntidad.acta", target = "actas")
    })
    public Solicitud entityToBO(SolicitudEntidad solicitudEntidad);

    /**
     * Metodo de mapeo de una lista de objetos entidad SolicitudEntidad a una lista de pojo Solicitud
     *
     * @param solicitudesList Lista de objetos de tipo SolicitudEntidad
     * @return Lista de objetos de tipo Solicitud
     */
    public List<Solicitud> entitiesToBOs(List<SolicitudEntidad> solicitudesList);

}
