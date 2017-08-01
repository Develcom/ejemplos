package ve.gob.cne.sarc.participante.core.participante.mapper;

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
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {OficinaMapper.class,
    FuncionarioMapper.class, TipoLibroMapper.class, LibroEstatusMapper.class})
public interface LibroMapper {

    /**
     * Metodo que mapea la entidad LibroEntidad a pojo Libro
     *
     * @param libroEntidad Objeto de tipo LibroEntidad
     * @return Objeto de tipo Libro
     */
    @Mappings({
        @Mapping(source = "libroEntidad.estatus.nombre", target = "estatus"),
        @Mapping(source = "libroEntidad.oficina", target = "oficina"),
        @Mapping(source = "libroEntidad.tipoLibro", target = "tipoLibro"),
        @Mapping(source = "libroEntidad.funcionarioApertura", target = "funcionarioApertura")
    })
    Libro entityToBO(LibroEntidad libroEntidad);

    /**
     * Metodo que mapea una lista de entidad LibroEntidad a una lista de pojo Libro
     *
     * @param librosEntidad Lista de objeto de tipo LibroEntidad
     * @return Lista de objeto de tipo Libro
     */
    List<Libro> entitiesToBOs(List<LibroEntidad> librosEntidad);

    /**
     * Metodo que mapea el pojo TipoLibro a la entidad TipoLibroEntidad
     *
     * @param tipoLibro Objeto de tipo TipoLibro
     * @return Objeto entidad de tipo TipoLibroEntidad
     */
    TipoLibroEntidad map(TipoLibro tipoLibro);

    /**
     * Metodo que mapea el pojo Libro a la entidad LibroEntidad
     *
     * @param libro Objeto de tipo Libro
     * @return Objeto entidad de tipo LibroEntidad
     */
    @Mapping(target = "estatus", ignore = true)
    LibroEntidad boToEntity(Libro libro);
}
