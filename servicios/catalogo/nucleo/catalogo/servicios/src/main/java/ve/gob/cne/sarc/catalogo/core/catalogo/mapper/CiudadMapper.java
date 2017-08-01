package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Ciudad;
import ve.gob.cne.sarc.persistencia.entidades.CiudadEntidad;

/**
 * CiudadMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface CiudadMapper {


    /**
     * Metodo de mapeo de los objetos CiudadEntidad y Ciudad
     *
     * @param ciudadEntidad Objeto con la informacion de la entidad Ciudad
     * @return {@link Ciudad}
     */
    @Mappings({
        @Mapping(source = "ciudadEntidad.id", target = "codigo")})
    Ciudad entityToBo(CiudadEntidad ciudadEntidad);

    /**
     * Metodo de mapeo de las listas de CiudadEntidad y Ciudad
     *
     * @param ciudadEntidad Lista de {@link CiudadEntidad}
     * @return Lista de {@link Ciudad}
     */
    List<Ciudad> entitiesToBos(List<CiudadEntidad> ciudadEntidad);

}
