package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocDecEntidad;


/**
 * TipoDocumentoDeclaranteMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoDocumentoDeclaranteMapper {

    /**
     * Metodo de mapeo de los objetos SolicitanteTipDocDecEntidad y TipoDocumento
     *
     * @param solicitanteTipDocDecEntidad Objeto con la informacion de la entidad SolicitanteTipDocDec
     * @return {@link TipoDocumento}
     */
    @Mappings({
        @Mapping(source = "solicitanteTipDocDecEntidad.id", target = "codigo"),
        @Mapping(source = "solicitanteTipDocDecEntidad.nombre", target = "nombre"),
        @Mapping(source = "solicitanteTipDocDecEntidad.descripcion", target = "descripcion"),})
    TipoDocumento entityToBOs(SolicitanteTipDocDecEntidad solicitanteTipDocDecEntidad);

    /**
     * Metodo de mapeo de las listas de SolicitanteTipDocDecEntidad y TipoDocumento
     *
     * @param solicitanteTipDocDecEntidadList Lista de {@link SolicitanteTipDocDecEntidad}
     * @return Lista de {@link TipoDocumento}
     */
    List<TipoDocumento> entitiesToBOs(List<SolicitanteTipDocDecEntidad> solicitanteTipDocDecEntidadList);
}
