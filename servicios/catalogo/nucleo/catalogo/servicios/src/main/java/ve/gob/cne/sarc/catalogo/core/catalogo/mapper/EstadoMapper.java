package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;
/**
 * EstadoMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class})
public interface EstadoMapper {

    /**
     * Metodo de mapeo de los objetos EstadoEntidad y Estado
     * @param estadoEntidad
     * @return {@link Estado}
     */     
    @Mappings({
        @Mapping(source = "estadoEntidad.id", target = "codigo"),
        @Mapping(target = "municipios", ignore = true)
    })    
    Estado entityToBO(EstadoEntidad estadoEntidad);

    /**
     * Metodo de mapeo de las listas de EstadoEntidad y Estado
     *
     * @param estadoEntidad Lista de {@link EstadoEntidad}
     * @return Lista de {@link Estado}
     */
    List<Estado> entitiesToBOs(List<EstadoEntidad> estadoEntidad);
}
