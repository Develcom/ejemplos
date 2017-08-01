package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Nacionalidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;

/**
 * NacionalidadMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface NacionalidadMapper {

    /**
     * Metodo de mapeo de los objetos NacionalidadEntidad y Nacionalidad
     *
     * @param nacionalidadEntidad Objeto con la informacion de la entidad
     * Nacionalidad
     * @return {@link Estado}
     */
    @Mappings({
        @Mapping(source = "nacionalidadEntidad.id", target = "codigo")})
    Nacionalidad entityToBo(NacionalidadEntidad nacionalidadEntidad);

    /**
     * Metodo de mapeo de las listas de NacionalidadEntidad y Nacionalidad
     *
     * @param nacionalidadEntidad Lista de {@link NacionalidadEntidad}
     * @return Lista de {@link Nacionalidad}
     */
    List<Nacionalidad> entitiesToBOs(List<NacionalidadEntidad> nacionalidadEntidad);
}
