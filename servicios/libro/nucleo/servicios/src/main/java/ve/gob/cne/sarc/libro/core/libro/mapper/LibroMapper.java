package ve.gob.cne.sarc.libro.core.libro.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.persistencia.entidades.LibroEntidad;

/**
 * LibroMapper.java
 * @descripcion {@link Mapper} usado para convertir instancias de entidad en instancias de pojo.
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {OficinaMapper.class,
    FuncionarioMapper.class, TipoLibroMapper.class, LibroEstatusMapper.class})
public interface LibroMapper {

    /**
     * Convierte una entidad en un pojo
     * @param libroEntidad Objeto entidad de tipo LibroEntidad
     * @return Objeto de tipo Libro
     */
    @Mapping(target = "estatus", source = "libroEntidad.estatus.nombre")
    Libro entityToBusiness(LibroEntidad libroEntidad);

    /**
     * Convierte una lista entidad en una lista pojo
     * @param librosEntidad Lista de objetos de tipo LibroEntidad
     * @return Lista de objetos de tipo Libro
     */
    List<Libro> entitiesToBusinesses(List<LibroEntidad> librosEntidad);

    /**
     * Convierte un pojo en una entidad
     * @param libro Objeto de tipo Libro
     * @return Objeto entidad de tipo LibroEntidad
     */
    @InheritInverseConfiguration
    LibroEntidad businessToEntity(Libro libro);

    /**
     * Convierte una lista pojo en una lista entidad
     * @param libros Lista de objetos de tipo Libro
     * @return Lista de objetos de tipo LibroEntidad
     */
    @InheritInverseConfiguration
    List<LibroEntidad> businessesToEntities(List<Libro> libros);

}
