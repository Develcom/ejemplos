package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;

/**
 * MunicipioMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(
        componentModel = "spring",
        uses = {
            EstadoMapper.class,
            ParroquiaMapper.class
        }
)
public interface MunicipioMapper {

    /**
     * Convierte una entidad a un pojo
     *
     * @param municipioEntidad objeto de tipo MunicipioEntidad
     * @return objeto de tipo Municipio
     */
    public Municipio entityToBO(MunicipioEntidad municipioEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     *
     * @param municipioList lista de objetos de tipo MunicipioEntidad
     * @return lista de objetos de tipo Municipio
     */
    public List<Municipio> entitiesToBOs(List<MunicipioEntidad> municipioList);
}
