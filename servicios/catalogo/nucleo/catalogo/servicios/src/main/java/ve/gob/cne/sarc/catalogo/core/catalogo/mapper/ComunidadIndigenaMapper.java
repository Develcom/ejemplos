package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.catalogo.ComunidadIndigena;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;

/**
 * ComunidadIndigenaMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface ComunidadIndigenaMapper {

    /**
     * Metodo de mapeo de los objetos ComunidadIndigenaEntidad y ComunidadIndigena
     *
     * @param comunidadIndigenaEntidad Objeto con la informacion de la entidad ComunidadIndigena
     * @return {@link ComunidadIndigena}
     */
    @Mappings({
        @Mapping(source = "comunidadIndigenaEntidad.id", target = "codigo")})
    ComunidadIndigena entityToBo(ComunidadIndigenaEntidad comunidadIndigenaEntidad);

    /**
     * Metodo de mapeo de las listas de ComunidadIndigenaEntidad y ComunidadIndigena
     *
     * @param comunidadIndigenaEntidad Lista de {@link ComunidadIndigenaEntidad}
     * @return Lista de {@link ComunidadIndigena}
     */
    List<ComunidadIndigena> entitiesToBos(List<ComunidadIndigenaEntidad> comunidadIndigenaEntidad);
}
