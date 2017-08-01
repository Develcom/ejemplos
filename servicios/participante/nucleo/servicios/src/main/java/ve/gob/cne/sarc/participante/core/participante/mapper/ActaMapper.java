package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitante.Solicitante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * ActaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ParticipanteMapper.class,
    EstatusActaMapper.class, LibroMapper.class, InsercionMapper.class,
    DefuncionMapper.class, ProcedenciaMapper.class
})
public interface ActaMapper {

    /**
     * Metodo que convierte un pojo en una entidad
     *
     * @param tramite Objeto de tipo tramite
     * @return Objeto entidad de tipo TramiteEntidad
     */
    TramiteEntidad boToEntityTramite(Tramite tramite);

    /**
     * Metodo que convierte un pojo en una entidad
     *
     * @param solicitante Objeto de tipo solicitante
     * @return Objeto entidad de tipo SolicitanteEntidad
     */
    SolicitanteEntidad boToEntitySolicitante(Solicitante solicitante);

    /**
     * Metodo que convierte un pojo en una entidad
     *
     * @param solicitud Objeto de tipo solicitud
     * @return Objeto entidad de tipo SolicitudEntidad
     */
    SolicitudEntidad boToEntitySolicitud(Solicitud solicitud);

    /**
     * Metodo que convierte una entidad en un pojo
     *
     * @param actaEntidad Objeto de entidad de tipo ActaEntidad
     * @return Objeto de tipo Acta
     */
    @Mappings({
        @Mapping(target = "procedencias", ignore = true),
        @Mapping(target = "oficinaFuncionario", ignore = true),
        @Mapping(target = "solicitud", ignore = true)
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
        @Mapping(target = "procedencias", ignore = true),
        @Mapping(target = "oficinaFuncionario", ignore = true),
        @Mapping(target = "solicitud", ignore = true)
    })
    ActaEntidad boToEntity(Acta acta);
}
