package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;

/**
 * PaisMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = EstadoMapper.class)
public interface PaisMapper {

    /**
     * Metodo de mapeo de entidad PaisEntidad a pojo Pais
     *
     * @param paisEntidad Objeto entidad de tipo PaisEntidad
     * @return Objeto de tipo Pais
     */
    @Mappings({
        @Mapping(target = "estados", ignore = true)
    })
    Pais entityToBO(PaisEntidad paisEntidad);

    /**
     * Metodo de mapeo de una lista de entidad PaisEntidad a uns lista de pojo Pais
     *
     * @param paiseLists Lista de objetos de tipo PaisEntidad
     * @return Lista de objetos de tipo Pais
     */
    List<Pais> entitiesToBOs(List<PaisEntidad> paiseLists);
}
