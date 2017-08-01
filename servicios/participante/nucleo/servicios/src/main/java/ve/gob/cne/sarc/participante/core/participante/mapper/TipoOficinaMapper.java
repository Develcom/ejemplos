package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * TipoOficinaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoOficinaMapper {

    /**
     * Metodo de mapeo de entidad TipoOficinaEntidad a pojo TipoOficina
     *
     * @param tipoOficinaEntidad Objeto entidad de tipo TipoOficinaEntidad
     * @return Objeto de tipo TipoOficina
     */
    public TipoOficina entityToBO(TipoOficinaEntidad tipoOficinaEntidad);

    /**
     * Metodo de mapeo de una lista de entidad TipoOficinaEntidad a una lista de pojo TipoOficina
     *
     * @param tipoOficinasEntidad Lista de objetos de tipo TipoOficinaEntidad
     * @return Lista de objetos de tipo TipoOficinam
     */
    public List<TipoOficina> entitiesToBOs(List<TipoOficinaEntidad> tipoOficinasEntidad);
}
