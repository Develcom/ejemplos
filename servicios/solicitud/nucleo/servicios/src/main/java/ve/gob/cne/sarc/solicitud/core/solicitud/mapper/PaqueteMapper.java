package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TareaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramitePaqueteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.UrlModuloWebEntidad;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;
import ve.gob.cne.sarc.pmsentinel.bo.OficinaBO;
import ve.gob.cne.sarc.pmsentinel.bo.PaqueteBO;
import ve.gob.cne.sarc.pmsentinel.bo.StatusBO;
import ve.gob.cne.sarc.pmsentinel.bo.TareaBO;
import ve.gob.cne.sarc.pmsentinel.bo.TramiteBO;
import ve.gob.cne.sarc.pmsentinel.bo.UrlModuloWebBO;
import ve.gob.cne.sarc.pmsentinel.bo.UsuarioBO;

/**
 * PaqueteMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 07/10/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring")
public interface PaqueteMapper {

    /**
     * Convierte la entidad oficina a un pojo oficina
     *
     * @param oficinaEntidad La oficina entidad
     * @return El pojo oficina
     */
    @Mappings({
        @Mapping(target = "codigo", ignore = true)
    })
    OficinaBO entityToBo(OficinaEntidad oficinaEntidad);

    /**
     * Convierte la entidad usuario a un pojo usuario
     *
     * @param usuarioEntidad El usuario entidad
     * @return El pojo usuario
     */
    UsuarioBO entityToBo(UsuarioEntidad usuarioEntidad);

    /**
     * Convierte la entidad urlModuloWeb a un pojo urlModuloWeb
     * @param urlModuloWebEntidad La entidad urlModuloWeb
     * @return El pojo urlModuloWeb
     */
    @Mappings({
        @Mapping(target = "angularFlag", ignore = true)
    })
    UrlModuloWebBO entityToBo(UrlModuloWebEntidad urlModuloWebEntidad);

    /**
     * Convierte la entidad tramite a un pojo tramite
     * @param tramitePaqueteEntidad La entidad tramite
     * @return El pojo tramite
     */
    @Mappings({
        @Mapping(target = "tramiteInicial", ignore = true),
        @Mapping(target = "modulo", ignore = true),
        @Mapping(target = "urlModuloWeb", source = "urlModuloWebEntidad")
    })
    TramiteBO entityToBo(TramitePaqueteEntidad tramitePaqueteEntidad);

    /**
     * Convierte la entidad tarea a un pojo tarea
     * @param tareaEntidad La entidad tarea
     * @return El pojo tarea
     */
    @Mappings({
        @Mapping(target = "tareaInicial", ignore = true),
        @Mapping(target = "tramite", source = "tramitePaqueteEntidad")
    })
    TareaBO entityToBo(TareaEntidad tareaEntidad);

    /**
     * Convierte la estatus tarea a un pojo estatus
     * @param paqueteEstatusEntidad La entidad estatus
     * @return El pojo estatus
     */
    StatusBO entityToBo(PaqueteEstatusEntidad paqueteEstatusEntidad);

    /**
     * Convierte la paquete tarea a un pojo paquete
     * @param paqueteEntidad La entidad paquete
     * @return El pojo paquete
     */
    @Mappings({
        @Mapping(target = "rol", ignore = true),
        @Mapping(target = "solicitud", ignore = true),
        @Mapping(target = "payload", ignore = true),
        @Mapping(target = "flowScope", ignore = true),
        @Mapping(target = "tarea", source = "tareaEntidad"),
        @Mapping(target = "estatus", source = "paqueteEstatusEntidad")
    })
    PaqueteBO entityToBo(PaqueteEntidad paqueteEntidad);

}
