package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.direccion.Direccion;


/**
 * DireccionMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class, EstadoMapper.class, ParroquiaMapper.class, 
    MunicipioMapper.class, CiudadanoMapper.class})
public interface DireccionMapper {

    /**
     * Convierte un string a un Pojo
     *
     * @param direccionEntidad String que describe la direccion
     * @return Direccion pojo que contiene la Direccion
     */
    public Direccion entityToBO(String direccionEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param direccionesEntidad lista de String que describe una lista de direcciones
     * @return List<Direccion> lista de pojo de direcciones
     */
    public List<Direccion> entitiesToBOs(List<String> direccionesEntidad);
}
