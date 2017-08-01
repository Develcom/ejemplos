package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaModuloEntidad;

/**
 * ModuloMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface ModuloMapper {

    /**
     * Metodo de mapeo de los objetos TipoOficinaModuloEntidad y Modulo
     *
     * @param tipoOficinaModuloEntidad Objeto con la informacion de la entidad
     * Modulo
     * @return {@link Modulo}
     */
    @Mappings({
        @Mapping(source = "tipoOficinaModuloEntidad.modulo.nombre", target = "nombre"),
        @Mapping(source = "tipoOficinaModuloEntidad.modulo.codigo", target = "codigo")
    })
    Modulo entitiesToBOs(TipoOficinaModuloEntidad tipoOficinaModuloEntidad);

    /**
     * Metodo de mapeo de las listas de TipoOficinaModuloEntidad y Modulo
     *
     * @param tipoOficinaModuloEntidad Lista de {@link TipoOficinaModuloEntidad}
     * @return Lista de {@link Modulo}
     */
    List<Modulo> entitiesToBOs(List<TipoOficinaModuloEntidad> tipoOficinaModuloEntidad);
}
