package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoLibro;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;

/**
 * TipoLibroMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {LibroMapper.class})
public interface TipoLibroMapper {

    /**
     * Metodo de mapeo de entidad TipoLibroEntidad a pojo TipoLibro
     *
     * @param tipoLibroEntidad Objeto entidad de tipo TipoLibroEntidad
     * @return Objeto de tipo TipoLibro
     */
    public TipoLibro entityToBO(TipoLibroEntidad tipoLibroEntidad);

    /**
     * Metodo de mapeo de lista de entidad TipoLibroEntidad a lista de pojo TipoLibro
     *
     * @param tipoLibrosEntidad Lista de objetos de tipo TipoLibroEntidad
     * @return Lista de objetos de tipo TipoLibro
     */
    public List<TipoLibro> entitiesToBOs(List<TipoLibroEntidad> tipoLibrosEntidad);
}
