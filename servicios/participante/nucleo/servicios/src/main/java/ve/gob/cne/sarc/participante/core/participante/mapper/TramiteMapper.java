package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;

/**
 * TramiteMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ModuloMapper.class, TipoOficinaMapper.class})
public interface TramiteMapper {

    /**
     * Metodo de mapeo de entidad TramiteEntidad a pojo Tramite
     *
     * @param tramiteEntidad Objeto entidad de tipo TramiteEntidad
     * @return Objeto de tipo Tramite
     */
    @Mappings({
        @Mapping(source = "tramiteEntidad.descripcion", target = "descripcion"),})
    public Tramite entityToBO(TramiteEntidad tramiteEntidad);

    /**
     * Metodo de mapeo de una lista de entidad TramiteEntidad a una lista de pojo Tramite
     *
     * @param tramiteEntidadlist Lista de objetos de tipo TramiteEntidad
     * @return Lista de objetos de tipo Tramite
     */
    public List<Tramite> entitiesToBOs(List<TramiteEntidad> tramiteEntidadlist);
}
