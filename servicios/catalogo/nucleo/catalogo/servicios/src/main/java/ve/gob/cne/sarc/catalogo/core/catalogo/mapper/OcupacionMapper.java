package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;
import ve.gob.cne.sarc.persistencia.entidades.OcupacionEntidad;


/**
 * OcupacionMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface OcupacionMapper {

    /**
     * Metodo de mapeo de los objetos OcupacionEntidad y Ocupacion
     *
     * @param ocupacionEntidad Objeto con la informacion de la entidad Ocupacion
     * @return {@link Ocupacion}
     */
    @Mappings({
        @Mapping(source = "ocupacionEntidad.id", target = "codigo")})
    Ocupacion entitiesToBOs(OcupacionEntidad ocupacionEntidad);

    /**
     * Metodo de mapeo de las listas de OcupacionEntidad y Ocupacion
     *
     * @param ocupacionEntidad Lista de {@link OcupacionEntidad}
     * @return Lista de {@link Ocupacion}
     */
    List<Ocupacion> entitiesToBOs(List<OcupacionEntidad> ocupacionEntidad);
}
