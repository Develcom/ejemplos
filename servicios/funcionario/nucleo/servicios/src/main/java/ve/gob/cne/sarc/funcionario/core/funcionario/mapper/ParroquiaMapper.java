package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * ParroquiaMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = MunicipioMapper.class)
public interface ParroquiaMapper {

    /**
     * Convierte una entidad en un pojo
     *
     * @param parroquiaEntidad entidad que contiene a la Parroquia
     * @return Parroquia pojo que contiene a la Parroquia
     */
    Parroquia entityToBO(ParroquiaEntidad parroquiaEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param parroquiasList lista de entidades de parroquias
     * @return List<Parroquia> lista de pojos de Parroquias
     */
    List<Parroquia> entitiesToBOs(List<ParroquiaEntidad> parroquiasList);
}
