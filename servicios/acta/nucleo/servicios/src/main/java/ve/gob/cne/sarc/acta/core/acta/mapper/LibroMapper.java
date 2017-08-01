package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.gob.cne.sarc.comunes.catalogo.TipoLibro;
import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;
import ve.gob.cne.sarc.persistencia.entidades.LibroEntidad;

/**
 * LibroMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring",
        uses = {OficinaMapper.class, FuncionarioMapper.class})
public interface LibroMapper {
    
    /**
     * Metodo de mapeo de los objetos de TipoLibroEntidad y TipoLibro
     *
     * @param tipoLibroEntidad Objeto TipoLibroEntidad
     * @return TipoLibro, instancia de objeto que contiene la informacion de
     * TipoLibro
     */
    TipoLibro map(TipoLibroEntidad tipoLibroEntidad);
    
    /**
     * Metodo de mapeo de los objetos de LibroEntidad y Libro
     *
     * @param libroEntidad Objeto LibroEntidad
     * @return Libro, instancia de objeto que contiene la informacion de Libro
     */
    @Mapping(target = "estatus", source = "libroEntidad.estatus.nombre")
    Libro entityToBo(LibroEntidad libroEntidad);
    
    /**
     * Metodo de mapeo de los objetos TipoLibro y TipoLibroEntidad
     *
     * @param tipoLibro instancia de objeto que contiene la informacion de
     * TipoLibro
     * @return tipoLibroEntidad Objeto TipoLibroEntidad
     */
    TipoLibroEntidad map(TipoLibro tipoLibro);
    
    /**
     * Metodo de mapeo de los objetos Libro y LibroEntidad
     *
     * @param libro instancia de objeto que contiene la informacion de Libro
     * @return LibroEntidad Objeto LibroEntidad
     */
    @Mapping(target = "estatus", ignore = true)
    LibroEntidad boToEntity(Libro libro);
}
