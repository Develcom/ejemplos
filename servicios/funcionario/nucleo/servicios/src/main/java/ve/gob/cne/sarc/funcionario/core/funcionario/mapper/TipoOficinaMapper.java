package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * TipoOficinaMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoOficinaMapper {

    /**
     * Convierte una entidad en un pojo
     *
     * @param tipoOficinaEntidad entidad que contiene el tipoOficinaEntidad
     * @return TipoOficina que contiene el pojo de TipoOficina
     */
    public TipoOficina entityToBO(TipoOficinaEntidad tipoOficinaEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param tipoOficinasEntidad lista de entidades que contiene el tipoOficinaEntidad
     * @return List<TipoOficina> que contiene lista de pojo de TipoOficina
     */
    public List<TipoOficina> entitiesToBOs(List<TipoOficinaEntidad> tipoOficinasEntidad);
}
