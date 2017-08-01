package ve.gob.cne.sarc.recaudo.core.recaudo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Recaudo;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudRecaudoEntidad;

/**
 * SolicitudRecaudoMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 28/5/2016
 * @author Gabriela Palmar
 */
@Mapper(componentModel = "spring")
public interface SolicitudRecaudoMapper {

    
    /**
     * Metodo de mapeo de los objetos SolicitudRecaudoEntidad y Recaudo
     *
     * @param solicitudRecaudoEntidad Objeto SolicitudRecaudoEntidad
     * @return Recaudo, instancia de objeto que contiene la informacion del Recaudo
     */
    @Mappings({
        @Mapping(source = "solicitudRecaudoEntidad.recaudo.codigo", target = "codigo"),
        @Mapping(source = "solicitudRecaudoEntidad.recaudo.nombre", target = "nombre"),
        @Mapping(source = "solicitudRecaudoEntidad.recaudo.fechaInicio", target = "fechaInicio"),
        @Mapping(source = "solicitudRecaudoEntidad.recaudo.fechaFin", target = "fechaFin"),
        @Mapping(source = "solicitudRecaudoEntidad.recaudo.observacion", target = "observacion"),
        @Mapping(source = "solicitudRecaudoEntidad.recaudo.estatus", target = "estatus"),
        @Mapping(source = "solicitudRecaudoEntidad.recaudo.tipo", target = "tipo"),
        @Mapping(source = "solicitudRecaudoEntidad.fechaActualizando", target = "fechaActualizacion"),
        @Mapping(source = "solicitudRecaudoEntidad.obligatorio", target = "obligatorio")
    })
    Recaudo entityToBO(SolicitudRecaudoEntidad solicitudRecaudoEntidad);

    /**
     * Metodo de mapeo listas de objetos SolicitudRecaudoEntidad y Recaudo
     *
     * @param listaSolicitudRecaudoEntidad Lista de objetos SolicitudRecaudoEntidad
     * @return objeto tipo Lista de Recaudo
     */
    List<Recaudo> entitiesToBOs(List<SolicitudRecaudoEntidad> listaSolicitudRecaudoEntidad);
}
