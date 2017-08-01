package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.catalogo.TipoLibro;

import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;
import ve.gob.cne.sarc.persistencia.entidades.LibroEntidad;

/**
 * LibroMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {OficinaMapper.class,
    FuncionarioMapper.class, TipoLibroMapper.class
})
public interface LibroMapper {

    /**
     * Convierte una entidad a un pojo
     * @param libroEntidad objeto de tipo LibroEntidad
     * @return objeto de tipo Libro
     */
    @Mappings({
        @Mapping(source = "libroEntidad.estatus.nombre", target = "estatus"),})
    Libro entityToBO(LibroEntidad libroEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     * @param librosEntidad lista objetos de tipo LibroEntidad
     * @return lista de objetos de tipo Libro
     */
    List<Libro> entitiesToBOs(List<LibroEntidad> librosEntidad);

    /**
     * Convierte un poko a una entidad
     * @param tipoLibro objeto de tipo TipoLibro
     * @return objeto de TipoLibroEntidad
     */
    TipoLibroEntidad map(TipoLibro tipoLibro);

    /**
     * Convierte un pojo a una entidad
     * @param libro objeto de tipo Libro
     * @return objeto de tipo LibroEntidad
     */
    @Mapping(target = "estatus", ignore = true)
    LibroEntidad boToEntity(Libro libro);
}
