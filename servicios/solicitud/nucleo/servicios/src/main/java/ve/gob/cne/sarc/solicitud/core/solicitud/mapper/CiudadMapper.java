package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Ciudad;
import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;

/**
 * CiudadMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = MunicipioMapper.class)
public interface CiudadMapper {

    /**
     * Convierte una entidad a un pojo
     * @param ciudadEntidad Objeto entidad de tipo ciudadEntidad
     * @return objeto de tipo Ciudad
     */
    public Ciudad entityToBO(CiudadanoEntidad ciudadEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     * @param ciudadesList Lista de objetos de tipo Ciudad
     * @return Lista de objetos de tipo Ciudad
     */
    public List<Ciudad> entitiesToBOs(List<Ciudad> ciudadesList);
}
