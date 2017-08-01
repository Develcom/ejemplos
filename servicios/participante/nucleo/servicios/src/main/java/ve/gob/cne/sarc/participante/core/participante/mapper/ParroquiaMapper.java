package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * ParroquiaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = MunicipioMapper.class)
public interface ParroquiaMapper {

    /**
     * Metodo de mapeo de entidad ParroquiaEntidad a pojo Parroquia
     *
     * @param parroquiaEntidad Objeto entidad de tipo ParroquiaEntidad
     * @return Objeto de tipo Parroquia
     */
    Parroquia entityToBO(ParroquiaEntidad parroquiaEntidad);

    /**
     * Metodo de mapeo de lista de entidad ParroquiaEntidad a una lista de pojo Parroquia
     *
     * @param parroquiasList Lista de objetos de tipo ParroquiaEntidad
     * @return Lista de objetos de tipo Parroquia
     */
    List<Parroquia> entitiesToBOs(List<ParroquiaEntidad> parroquiasList);

    /**
     * Metodo de mapeo de pojo Parroquia a entidad ParroquiaEntidad
     *
     * @param parroquia Objeto de tipo Parroquia
     * @return Objeto entidad de tipo ParroquiaEntidad
     */
    ParroquiaEntidad boToEntity(Parroquia parroquia);
}
