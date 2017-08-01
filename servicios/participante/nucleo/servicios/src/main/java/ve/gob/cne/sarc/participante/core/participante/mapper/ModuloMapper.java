package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.persistencia.entidades.ModuloEntidad;

/**
 * ModuloMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface ModuloMapper {

    /**
     * Metodo de mapeo de la entidad ModuloEntidad al pojo Modulo
     *
     * @param moduloEntidad Objeto entidad de tipo ModuloEntidad
     * @return Objeto de tipo Modulo
     */
    @Mappings({
        @Mapping(source = "moduloEntidad.codigo", target = "codigo"),})
    Modulo entityToBO(ModuloEntidad moduloEntidad);

    /**
     * Metodo de mapeo de una lista de entidad de ModuloEntidad a una lista de pojo de Modulo
     *
     * @param moduloEntidadList Lista de objeto de tipo ModuloEntidad
     * @return Lista de objeto de tipo Modulo
     */
    List<Modulo> entitiesToBOs(List<ModuloEntidad> moduloEntidadList);
}
