package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;

/**
 * EstadoMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class, MunicipioMapper.class})
public interface EstadoMapper {

    /**
     * Convierte una entidad a un pojo
     * @param estado objeto de tipo EstadoEntidad
     * @return objeto de tipo Estado
     */
    public Estado entityToBO(EstadoEntidad estado);

    /**
     * Convierte una lista entidad a una lista pojo
     * @param estado lista de objetos de tipo EstadoEntidad
     * @return lista de Objetos de tipo Estado
     */
    public List<Estado> entitiesToBOs(List<EstadoEntidad> estado);
}
