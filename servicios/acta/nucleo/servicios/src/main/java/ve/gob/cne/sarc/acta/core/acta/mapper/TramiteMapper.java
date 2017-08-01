package ve.gob.cne.sarc.acta.core.acta.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;

/**
 * TramiteMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ModuloMapper.class, TipoOficinaMapper.class})
public interface TramiteMapper {

    /**
     * Convierte una entidad a un pojo
     *
     * @param tramiteEntidad objeto de tipo TramiteEntidad
     * @return objeto de tipo Tramite
     */
    @Mappings({
        @Mapping(source = "tramiteEntidad.descripcion", target = "descripcion"),})
    public Tramite entityToBO(TramiteEntidad tramiteEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     *
     * @param tramiteEntidadlist lista de tipo TramiteEntidad
     * @return lista de objetos de tipo Tramite
     */
    public List<Tramite> entitiesToBOs(List<TramiteEntidad> tramiteEntidadlist);
    /**
     * Metodo que permite guardar la insercion
     *
     * @param tramite pojo con la informacion de Tramite
     * @return objeto entidad de tipo TramiteEntidad
     */
    public TramiteEntidad boToEntity(Tramite tramite);
}
