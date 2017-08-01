package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;

/**
 * MunicipioMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface MunicipioMapper {

    /**
     * Metodo de mapeo de los objetos MunicipioEntidad y Municipio
     *
     * @param municipioEntidad Objeto con la informacion de la entidad Municipio
     * @return {@link Municipio}
     */
    @Mappings({
        @Mapping(source = "municipioEntidad.id", target = "codigo"),
        @Mapping(target = "parroquias", ignore = true)
    })
    Municipio entityToBo(MunicipioEntidad municipioEntidad);

    /**
     * Metodo de mapeo de las listas de MunicipioEntidad y Municipio
     *
     * @param municipioEntidad Lista de {@link MunicipioEntidad}
     * @return Lista de {@link Municipio}
     */
    List<Municipio> entitiesToBos(List<MunicipioEntidad> municipioEntidad);
}
