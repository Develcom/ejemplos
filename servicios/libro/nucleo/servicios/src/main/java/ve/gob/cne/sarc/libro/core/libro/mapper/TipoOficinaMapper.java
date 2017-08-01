package ve.gob.cne.sarc.libro.core.libro.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * TipoOficinaMapper.java
 * @descripcion {@link Mapper} usado para convertir instancias de entidad en instancias de pojo.
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoOficinaMapper {

    /**
     * Convierte una entidad en un pojo
     * @param tipoOficinaEntidad Objeto entidad de tipo TipoOficinaEntidad
     * @return Objeto de tipo TipoOficina
     */
    TipoOficina entityToBusiness(TipoOficinaEntidad tipoOficinaEntidad);

    /**
     * Convierte una lista entidad en una lista pojo
     * @param tipoOficinasEntidad Lista de objetos de tipo TipoOficinaEntidad
     * @return Lista de objetos de tipo TipoOficina
     */
    List<TipoOficina> entitiesToBusinesses(List<TipoOficinaEntidad> tipoOficinasEntidad);

    /**
     * Convierte un pojo en una entidad
     * @param tipoOficina Objeto de tipo TipoOficina
     * @return Objeto entidad de tipo TipoOficinaEntidad
     */
    @InheritInverseConfiguration
    TipoOficinaEntidad businessToEntity(TipoOficina tipoOficina);

    /**
     * Convierte una lista pojo en una lista entidada
     * @param tipoOficinas Lista de objetos de tipo TipoOficina
     * @return Lista de objetos de tipo TipoOficinaEntidad
     */
    @InheritInverseConfiguration
    List<TipoOficinaEntidad> businessesToEntities(List<TipoOficina> tipoOficinas);

}
