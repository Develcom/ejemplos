package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

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
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y las clase del modelo de
 * datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ParticipanteMapper.class,
    LibroMapper.class, InsercionMapper.class, DefuncionMapper.class,
    ProcedenciaMapper.class
})
public interface ActaMapper {

    /**
     * Convierte un objeto a una entidad
     *
     * @param solicitud Lista de objetos de tipo ActaEntidad
     * @return Lista de objetos de tipo Acta
     */
    SolicitudEntidad boToEntitySolicitud(Solicitud solicitud);

    /**
     * Convierte un pojo a una entidad
     *
     * @param tramite objeto de tipo tramite
     * @return objeto entidad de tipo Tramite
     */
    TramiteEntidad boToEntityTramite(Tramite tramite);

    /**
     * Convierte una pojo a una entidad
     *
     * @param solicitante objeto de tipo Solicitante
     * @return objeto entidad de tipo Solicitante
     */
    SolicitanteEntidad boToEntitySolicitante(Solicitante solicitante);

    /**
     * Convierte una entidad a un pojo
     *
     * @param actaEntidad Objeto entidad de tipo ActaEntidad
     * @return Objeto de tipo Acta
     */
    @Mappings({
        @Mapping(source = "actaEntidad.libro", target = "libro"),
        @Mapping(target = "oficinaFuncionario", ignore = true),
        @Mapping(target = "solicitud", ignore = true)
    })
    public Acta entityToBO(ActaEntidad actaEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     *
     * @param actaEntidadList Lista de objetos de tipo ActaEntidad
     * @return Lista de objetos de tipo Acta
     */
    public List<Acta> entitiesToBOs(List<ActaEntidad> actaEntidadList);

    /**
     * Convierte un pojo a una entidad
     *
     * @param acta objeto de tipo Acta
     * @return Objeto entidad de tipo ActaEntidad
     */
    @Mappings({
        @Mapping(target = "oficinaFuncionario", ignore = true),
        @Mapping(target = "solicitud", ignore = true)
    })
    ActaEntidad boToEntity(Acta acta);
}
