package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.persistencia.entidades.TipoParticipanteEntidad;

/**
 * TipoParticipanteMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public abstract class TipoParticipanteMapper {

    /**
     * Metodo de mapeo de entidad TipoParticipanteEntidad a String
     *
     * @param tipoParticipanteEntidad Objeto entidad de tipo
     * TipoParticipanteEntidad
     * @return String con la descripcion del tipo de participante
     */
    public String tipoParticipanteTOrol(TipoParticipanteEntidad tipoParticipanteEntidad) {
        return tipoParticipanteEntidad.getCodigo();
    }

    /**
     * Metodo de mapeo de entidad TipoParticipanteEntidad a pojo
     * TipoParticipante
     *
     * @param tipoParticipanteEntidad Objeto entidad de tipo
     * TipoParticipanteEntidad
     * @return Objeto de tipo TipoParticipante
     */
    public abstract TipoParticipante entityToBO(TipoParticipanteEntidad tipoParticipanteEntidad);

    /**
     * Metodo de mapeo de entidad TipoParticipanteEntidad a String
     *
     * @param tipoParticipanteEntidad Objeto entidad de tipo
     * TipoParticipanteEntidad
     * @return String con el nombre del tipo de participante
     */
    public String tipoParticipanteToNombreRol(TipoParticipanteEntidad tipoParticipanteEntidad) {
        return tipoParticipanteEntidad.getNombre();
    }

}
