package ve.gob.cne.sarc.acta.core.acta.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * SolicitudMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {OficinaMapper.class, FuncionarioMapper.class, 
    TramiteMapper.class, ModuloMapper.class, SolicitanteMapper.class, ActaMapper.class})
public interface SolicitudMapper {

    /**
     *
     * Metodo de mapeo de los objetos SolicitudEntidad y Solicitud
     *
     * @param solicitudEntidad objeto de tipo SolicitudEntidad
     * @return objeto de tipo Solicitud
     */
    @Mappings({
        @Mapping(source = "solicitudEntidad.numero", target = "numeroSolicitud"),
        @Mapping(source = "solicitudEntidad.oficinaFuncionario.oficina", target = "oficina"),
        @Mapping(source = "solicitudEntidad.oficinaFuncionario.funcionario", target = "funcionario"),
        @Mapping(source = "solicitudEntidad.tramite", target = "tramite"),
        @Mapping(source = "solicitudEntidad.tramite.modulo", target = "modulo"),
        @Mapping(source = "solicitudEntidad.solicitante", target = "solicitante"),
        @Mapping(source = "solicitudEntidad.observacion", target = "motivoCambioEstado"),
        @Mapping(source = "solicitudEntidad.estatus.nombre", target = "estadoSolicitud"),
        @Mapping(source = "solicitudEntidad.estatus.nombre", target = "codigoEstadoSolicitud")
    })
    public Solicitud entityToBO(SolicitudEntidad solicitudEntidad);

    /**
     *
     * Metodo de mapeo listas de objetos SolicitudEntidad y Solicitud
     *
     * @param solicitudesList lista de objetos de tipo SolicitudEntidad
     * @return Lista de objetos de tipo Solicitud
     */
    public List<Solicitud> entitiesToBOs(List<SolicitudEntidad> solicitudesList);
    /**
     *
     * Metodo de mapeo de objetos SolicitudEntidad y Solicitud
     *
     * @param solicitud objeto de tipo Solicitud
     * @return objeto de tipo SolicitudEntidad
     */
    public SolicitudEntidad boToEntity(Solicitud solicitud);

}
