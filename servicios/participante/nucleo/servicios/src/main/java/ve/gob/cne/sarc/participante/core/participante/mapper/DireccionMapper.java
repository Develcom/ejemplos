package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.direccion.Direccion;

/**
 * DireccionMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class, EstadoMapper.class, 
    ParroquiaMapper.class, MunicipioMapper.class, CiudadanoMapper.class})
public interface DireccionMapper {

    /**
     * Metodo que mapea un String a un pojo
     *
     * @param direccionEntidad String con la direccion
     * @return Objeto de tipo Direccion
     */
    public Direccion entityToBO(String direccionEntidad);

    /**
     * Metodo que mapea una lista de String a una Lista de pojo del tipo Direccion
     *
     * @param direccionesEntidad Lista de String
     * @return Lista de objetos de tipo Direccion
     */
    public List<Direccion> entitiesToBOs(List<String> direccionesEntidad);
}
