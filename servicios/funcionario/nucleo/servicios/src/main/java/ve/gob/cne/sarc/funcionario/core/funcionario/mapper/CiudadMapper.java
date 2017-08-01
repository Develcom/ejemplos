package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Ciudad;
import ve.gob.cne.sarc.persistencia.entidades.CiudadEntidad;

/**
 * CiudadMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = MunicipioMapper.class)
public interface CiudadMapper {

    /**
     * Convierte una entidad en un pojo
     *
     * @param ciudadEntidad entidad que contiene a Ciudad
     * @return Ciudad pojo que contiene la informacion de Ciudad
     */
    public Ciudad entityToBO(CiudadEntidad ciudadEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     *
     * @param ciudadesList lista de entidad de ciudades
     * @return List<Ciudad> lista de pojo de Ciudades
     */
    public List<Ciudad> entitiesToBOs(List<CiudadEntidad> ciudadesList);
}
