package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.persistencia.entidades.TipoParticipanteEntidad;

/**
 * TipoParticipanteMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoParticipanteMapper {

    /**
     * Metodo de mapeo de los objetos TipoParticipanteEntidad y TipoParticipante
     *
     * @param tipoParticipanteEntidad Objeto con la informacion de la entidad TipoParticipante
     * @return {@link TipoParticipante}
     */
    TipoParticipante entityToBO(TipoParticipanteEntidad tipoParticipanteEntidad);

    /**
     * Metodo de mapeo listas de objetos TipoParticipanteEntidad y TipoParticipante
     * @param tipoParticipanteEntidad Lista de {@link TipoParticipante}
     * @return Lista de {@link TipoParticipante}
     */
    List<TipoParticipante> entitiesToBOs(List<TipoParticipanteEntidad> tipoParticipanteEntidad);
}
