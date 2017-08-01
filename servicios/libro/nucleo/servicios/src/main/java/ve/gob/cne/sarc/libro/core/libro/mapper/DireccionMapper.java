package ve.gob.cne.sarc.libro.core.libro.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.direccion.Direccion;

/**
 * DireccionMapper.java
 * @descripcion {@link Mapper} usado para convertir instancias de entidad en instancias de pojo.
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface DireccionMapper {

    /**
     * Convierte una entidad en un pojo
     * @param direccionEntidad String que contiene la direccion entidad
     * @return Objeto de tipo Direccion 
     */
    Direccion entityToBusiness(String direccionEntidad);

    /**
     * Convierte una lista entidad en una lista pojo
     * @param direccionesEntidad Lista de String que contiene las direcciones entidad
     * @return Lista de objetos de tipo Direccion
     */
    List<Direccion> entitiesToBusinesses(List<String> direccionesEntidad);

    /**
     * Convierte un pojo en un String
     * @param direccion Objeto de tipo Direccion
     * @return String con la direccion
     */
    @InheritInverseConfiguration
    String businessToEntity(Direccion direccion);

    /**
     * Convierte una lista pojo en una lista de String
     * @param direcciones Lista de objetos de tipo Direccion
     * @return Lista de String que contiene las direcciones
     */
    @InheritInverseConfiguration
    List<String> businessesToEntities(List<Direccion> direcciones);

}