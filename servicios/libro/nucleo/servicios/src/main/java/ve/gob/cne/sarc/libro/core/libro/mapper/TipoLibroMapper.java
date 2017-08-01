package ve.gob.cne.sarc.libro.core.libro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.TipoLibro;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;

/**
 * TipoLibroMapper.java
 * @descripcion {@link Mapper} usado para convertir instancias de entidad en instancias de pojo.
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {LibroMapper.class})
public interface TipoLibroMapper {

    /**
     * Convierte una entidad en un pojo
     * @param tipoLibroEntidad Objeto entidad de tipo TipoLibroEntidad
     * @return Objeto de tipo TipoLibro
     */
    
    @Mappings({
        @Mapping(source = "tipoLibroEntidad.id", target = "codigo")
    })
    TipoLibro entityToBusiness(TipoLibroEntidad tipoLibroEntidad);

    /**
     * Convierte una lista entidad en una lista pojo
     * @param tipoLibrosEntidad Lista de objetos de tipo TipoLibroEntidad
     * @return Objeto de tipo TipoLibro
     */
    List<TipoLibro> entitiesToBusinesses(List<TipoLibroEntidad> tipoLibrosEntidad);
    
    /**
     * Convierte un pojo en una entidad
     * @param tipoLibro Objeto de tipo TipoLibro
     * @return Objeto entidad de tipo TipoLibroEntidad
     */
  
    TipoLibroEntidad businessToEntity(TipoLibro tipoLibro);

    /**
     * Convierte una lista pojo en una lista entidad
     * @param tipoLibros Lista de objetos TipoLibro
     * @return Lista de objetos de tipo TipoLibroEntidad
     */
  
    List<TipoLibroEntidad> businessesToEntities(List<TipoLibro> tipoLibros);

}
