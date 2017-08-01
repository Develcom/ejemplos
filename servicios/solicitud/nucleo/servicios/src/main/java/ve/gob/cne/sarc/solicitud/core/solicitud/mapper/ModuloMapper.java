package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.persistencia.entidades.ModuloEntidad;

/**
 * ModuloMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface ModuloMapper {

    /**
     * Convierte una entidad a un pojo
     * @param moduloEntidad objeto de tipo ModuloEntidad
     * @return objeto de tipo Modulo
     */
    @Mappings({
        @Mapping(source = "moduloEntidad.codigo", target = "codigo"),})
    Modulo entityToBO(ModuloEntidad moduloEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     * @param moduloEntidadList lista de modulo de Entidades
     * @return lista de objetos de tipo Modulo
     */
    List<Modulo> entitiesToBOs(List<ModuloEntidad> moduloEntidadList);
}
