package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * ParroquiaMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = MunicipioMapper.class)
public interface ParroquiaMapper {

    /**
     * Convertir una entida a un pojo
     * @param parroquiaEntidad objeto de tipo ParroquiaEntidad
     * @return objeto de tipo Parroquia
     */
    Parroquia entityToBO(ParroquiaEntidad parroquiaEntidad);

    /**
     * Convertir una lista entidad a una lista pojo
     * @param parroquiasList lista de objetos de tipo ParroquiaEntidad
     * @return lista de objetos de tipo Parroquia 
     */
    List<Parroquia> entitiesToBOs(List<ParroquiaEntidad> parroquiasList);

    /**
     * Convertir un pojo a una entidad
     * @param parroquia objeto de tipo Parroquia
     * @return objeto de tipo ParroquiaEntidad
     */
    ParroquiaEntidad boToEntity(Parroquia parroquia);
}
