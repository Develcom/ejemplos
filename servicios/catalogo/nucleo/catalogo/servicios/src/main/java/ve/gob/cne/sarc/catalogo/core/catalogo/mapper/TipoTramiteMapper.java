package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;


/**
 * TipoTramiteMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoTramiteMapper {

    /**
     * Metodo de mapeo de los objetos TramiteEntidad y Tramite
     *
     * @param tramiteEntidad Objeto con la informacion de la entidad Tramite
     * @return {@link Tramite}
     */
    Tramite entitiesToBOs(TramiteEntidad tramiteEntidad);

    /**
     * Metodo de mapeo de las listas de TramiteEntidad y Tramite
     *
     * @param tramitesEntidadLista Lista de {@link TramiteEntidad}
     * @return Lista de {@link Tramite}
     */
    List<Tramite> entitiesToBOs(List<TramiteEntidad> tramitesEntidadLista);
}
