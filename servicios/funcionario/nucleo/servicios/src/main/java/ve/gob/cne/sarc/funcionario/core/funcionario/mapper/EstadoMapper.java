package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;

/**
 * EstadoMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class, MunicipioMapper.class})
public interface EstadoMapper {

    /**
     * Convierte una entidad en un pojo
     *
     * @param estado entidad que contiene la informacion de estado
     * @return Estado pojo que contiene la informacion de Estado
     */
    public Estado entityToBO(EstadoEntidad estado);

    /**
     * Convierte una entidad en un pojo
     *
     * @param estado lista de entidades de Estado
     * @return List<Estado> pojo que contiene la Lista de Estados
     */
    public List<Estado> entitiesToBOs(List<EstadoEntidad> estado);
}
