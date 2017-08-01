package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;

/**
 * EstadoMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class, MunicipioMapper.class})
public interface EstadoMapper {

    /**
     * Metodo que mapea una entidad a un pojo
     *
     * @param estado Objeto entidad de tipo EstadoEntidad
     * @return Objeto de tipo Estado
     */
    @Mappings({
        @Mapping(target = "municipios", ignore = true)
    })
    public Estado entityToBO(EstadoEntidad estado);

    /**
     * Metodo que mapea unalista de entidad a una lista de pojo
     *
     * @param estado Lista de objeto de tipo EstadoEntidad
     * @return Lista de objetos de tipo estado
     */
    public List<Estado> entitiesToBOs(List<EstadoEntidad> estado);
}
