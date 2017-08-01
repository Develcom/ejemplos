package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.direccion.Direccion;

/**
 * DireccionMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class, EstadoMapper.class, 
    ParroquiaMapper.class, MunicipioMapper.class, CiudadanoMapper.class})
public interface DireccionMapper {

    /**
     * Convierte una entidad a un pojo
     * @param direccionEntidad objeto de tipo direccionEntidad
     * @return objeto de tipo Direccion
     */
    public Direccion entityToBO(String direccionEntidad);

    /**
     * Convierte una lista String a una lista pojo
     * @param direccionesEntidad lista de direcciones de tipo String
     * @return  lista de direcciones de tipo String 
     */
    public List<Direccion> entitiesToBOs(List<String> direccionesEntidad);
}
