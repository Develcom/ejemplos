package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * TipoOficinaMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoOficinaMapper {

    /**
     * Convierte una entidad a un pojo
     * @param tipoOficinaEntidad objeto de tipo TipoOficinaEntidad
     * @return objeto de tipo TipoOficina
     */
    public TipoOficina entityToBO(TipoOficinaEntidad tipoOficinaEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     * @param tipoOficinasEntidad lista de objetos de tipo TipoOficinaEntidad
     * @return lista de objetos de tipo TipoOficina
     */
    public List<TipoOficina> entitiesToBOs(List<TipoOficinaEntidad> tipoOficinasEntidad);
}
