package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocEnteEntidad;

/**
 * TipoDocumentoEnteMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoDocumentoEnteMapper {

    /**
     * Metodo de mapeo de los objetos SolicitanteTipDocEnteEntidad y TipoDocumento
     *
     * @param solicitanteTipDocEnteEntidad Objeto con la informacion de la entidad SolicitanteTipDocEnte
     * @return {@link TipoDocumento}
     */
    @Mappings({
        @Mapping(source = "solicitanteTipDocEnteEntidad.id", target = "codigo"),
        @Mapping(source = "solicitanteTipDocEnteEntidad.nombre", target = "nombre"),
        @Mapping(source = "solicitanteTipDocEnteEntidad.descripcion", target = "descripcion"),})
    TipoDocumento entityToBOs(SolicitanteTipDocEnteEntidad solicitanteTipDocEnteEntidad);

    /**
     * Metodo de mapeo de las listas de SolicitanteTipDocEnteEntidad y TipoDocumento
     *
     * @param solicitanteTipDocEnteEntidadList Lista de {@link SolicitanteTipDocEnteEntidad}
     * @return Lista de {@link TipoDocumento}
     */
    List<TipoDocumento> entitiesToBOs(List<SolicitanteTipDocEnteEntidad> solicitanteTipDocEnteEntidadList);
}
