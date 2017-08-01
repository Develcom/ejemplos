package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;

/**
 * PaisMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0  21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = EstadoMapper.class)
public interface PaisMapper {

    /**
     * Convierte una entidad en un pojo
     *
     * @param paisEntidad entidad que contiene al Pais
     * @return Pais pojo que contiene la informacion de Pais
     */
    Pais entityToBO(PaisEntidad paisEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param paiseLists lista de entidades de Paises
     * @return List<Pais> lista de pojos de Paises
     */
    List<Pais> entitiesToBOs(List<PaisEntidad> paiseLists);
}
