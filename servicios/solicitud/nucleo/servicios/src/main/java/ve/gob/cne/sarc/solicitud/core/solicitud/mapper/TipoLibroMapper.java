package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoLibro;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;

/**
 * TipoLibroMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {LibroMapper.class})
public interface TipoLibroMapper {

    /**
     * Convierte una entidad en un pojo
     * @param tipoLibroEntidad objeto de tipo TipoLibroEntidad
     * @return objeto de TipoLibroEntidad
     */
    public TipoLibro entityToBO(TipoLibroEntidad tipoLibroEntidad);

    /**
     * Convierte una lista entidad en una lista pojo
     * @param tipoLibrosEntidad lista de objetos entidad de tipo TipoLibroEntidad
     * @return lista de objetos de TipoLibroEntidad
     */
    public List<TipoLibro> entitiesToBOs(List<TipoLibroEntidad> tipoLibrosEntidad);
}
