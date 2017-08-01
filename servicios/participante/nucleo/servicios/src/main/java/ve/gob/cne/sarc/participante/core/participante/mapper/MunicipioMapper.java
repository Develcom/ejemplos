package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;

/**
 * MunicipioMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {EstadoMapper.class, ParroquiaMapper.class})
public interface MunicipioMapper {

    /**
     * Metodo de mapeo de entidad MunicipioEntidad al pojo Municipio
     *
     * @param municipioEntidad Objeto entidad de tipo MunicipioEntidad
     * @return Objeto de tipo Municipio
     */
    @Mappings({
        @Mapping(target = "parroquias", ignore = true)
    })
    public Municipio entityToBO(MunicipioEntidad municipioEntidad);

    /**
     * Metodo de mapeo de lista de entidad MunicipioEntidad a una lista de pojo Municipio
     *
     * @param municipioList Lista de objetos de tipo MunicipioEntidad
     * @return Lista de objetos de tipo Municipio
     */
    public List<Municipio> entitiesToBOs(List<MunicipioEntidad> municipioList);
}
