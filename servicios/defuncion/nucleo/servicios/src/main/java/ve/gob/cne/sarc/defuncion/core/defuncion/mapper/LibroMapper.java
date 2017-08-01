package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.gob.cne.sarc.comunes.catalogo.TipoLibro;
import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;
import ve.gob.cne.sarc.persistencia.entidades.LibroEntidad;

/**
 * LibroMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring",
        uses = {OficinaMapper.class, FuncionarioMapper.class})
public interface LibroMapper {
    
    /**
     * Convertir una entidad a un pojo
     * @param tipoLibroEntidad TipoLibroEntidad entidad con la informacion de tipo libro
     * @return Pojo TipoLibro
     */
    TipoLibro map(TipoLibroEntidad tipoLibroEntidad);
    
    /**
     * Convertir una entidad a un pojo
     * @param libroEntidad LibroEntidad entidad con la informacion de un libro
     * @return Pojo Libro
     */
    @Mapping(target = "estatus", source = "libroEntidad.estatus.nombre")
    Libro entityToBo(LibroEntidad libroEntidad);
    
    /**
     * Convertir un pojo a una entidad
     * @param tipoLibro TipoLibro pojo con la informacion de un tipo libro
     * @return Objeto de tipo entidad
     */
    TipoLibroEntidad map(TipoLibro tipoLibro);
    
    /**
     * Convertir un pojo a una entidad
     * @param libro Libro pojo con la informacion de un libro
     * @return Objeto de tipo entidad
     */
    @Mapping(target = "estatus", ignore = true)
    LibroEntidad boToEntity(Libro libro);
}
