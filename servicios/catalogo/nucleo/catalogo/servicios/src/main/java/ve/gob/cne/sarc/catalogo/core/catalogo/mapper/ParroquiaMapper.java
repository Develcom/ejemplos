package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * ParroquiaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface ParroquiaMapper {

    /**
     * Metodo de mapeo de los objetos ParroquiaEntidad y Parroquia
     *
     * @param parroquiaEntidad Objeto con la informacion de la entidad Parroquia
     * @return {@link Parroquia}
     */
    @Mappings({
        @Mapping(source = "parroquiaEntidad.id", target = "codigo"),
        @Mapping(target = "ciudades", ignore = true)
    })            
    Parroquia entityToBO(ParroquiaEntidad parroquiaEntidad);

    /**
     * Metodo de mapeo de las listas de ParroquiaEntidad y Parroquia
     *
     * @param parroquiaEntidad Lista de {@link ParroquiaEntidad}
     * @return Lista de {@link Parroquia}
     */
    List<Parroquia> entitiesToBOs(List<ParroquiaEntidad> parroquiaEntidad);
}
