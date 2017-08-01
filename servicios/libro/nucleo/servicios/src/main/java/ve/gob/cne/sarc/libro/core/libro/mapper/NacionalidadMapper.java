package ve.gob.cne.sarc.libro.core.libro.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;

/**
 * NacionalidadMapper.java
 * @descripcion {@link Mapper} usado para convertir instancias de entidad en instancias de pojo.
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface NacionalidadMapper {

    /**
     * Convierte una entidad en un String
     * @param nacionalidad Objeto entidad de tipo NacionalidadEntidad
     * @return String con la nacionalidad
     */
    String entityToBusiness(NacionalidadEntidad nacionalidad);

    /**
     * Convierte una lista entidad en una lista String
     * @param nacionalidadesEntidad Lista de objetos de tipo NacionalidadEntidad
     * @return Lista de String con las nacionalidades
     */
    List<String> entitiesToBusinesses(List<NacionalidadEntidad> nacionalidadesEntidad);

    /**
     * Convierte un String en una entidad
     * @param nacionalidad String con la nacionalidad
     * @return Objeto entidad de tipo NacionalidadEntidad
     */
    @InheritInverseConfiguration
    NacionalidadEntidad businessToEntity(String nacionalidad);

    /**
     * Convierte una lista String en una lista entidad
     * @param nacionalidades Lista de String con las nacionalidades
     * @return Lista de objetos de tipo NacionalidadEntidad
     */
    @InheritInverseConfiguration
    List<NacionalidadEntidad> businessesToEntities(List<String> nacionalidades);

}
