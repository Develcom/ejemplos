package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;

/**
 * PaisMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {EstadoMapper.class})
public interface PaisMapper {

    /**
     * Metodo de mapeo de los objetos PaisEntidad y Pais
     *
     * @param paisEntidad Objeto con la informacion de la entidad Pais
     * @return {@link Pais}
     */
    @Mappings({
        @Mapping(source = "paisEntidad.id", target = "codigo"),
        @Mapping(target = "estados", ignore = true)
    })
    Pais entityToBo(PaisEntidad paisEntidad);

    /**
     * Metodo de mapeo de las listas de PaisEntidad y Pais
     *
     * @param paisEntidad Lista de {@link PaisEntidad}
     * @return Lista de {@link Pais}
     */
    List<Pais> entitiesToBOs(List<PaisEntidad> paisEntidad);

}
