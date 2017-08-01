package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.TipoParticipanteBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.TipoParticipanteMapper;
import ve.gob.cne.sarc.catalogo.core.catalogo.util.Constantes;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.persistencia.entidades.TipoParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.TipoParticipanteRepository;

/**
 * TipoParticipanteBFImpl.java
 *
 * @descripcion Implementacion del catalogo Tipo Participante
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Component
public class TipoParticipanteBFImpl implements TipoParticipanteBF {

    private static final Logger LOG = LoggerFactory.getLogger(TipoParticipanteBFImpl.class);

    @Autowired
    private TipoParticipanteRepository tipoParticipanteRepository;

    @Autowired
    private TipoParticipanteMapper tipoParticipanteMapper;

    /**
     * Metodo responsable de buscar un Tipo de Participante dado el codigo
     *
     * @param codigo String codigo de tipo de participante
     * @param isDeclarante boolean indica si el participante es Declarante
     * @return {@link TipoParticipante}
     */
    @Override
    public TipoParticipante buscarPorCodigo(String codigo, boolean isDeclarante) {

        LOG.debug("=====INICIANDO TipoParticipanteBFImpl.BuscarPorCodigo==========");
        TipoParticipanteEntidad tipoParticipanteEntidad;
        tipoParticipanteEntidad = tipoParticipanteRepository.findByCodigo(codigo);
        if ((tipoParticipanteEntidad != null)
                && (((((((!tipoParticipanteEntidad.getCodigo().equals(Constantes.CONST_HIJO_DECLARANTE)
                && !tipoParticipanteEntidad.getCodigo().equals(Constantes.CONST_COUNYUGE_DECLARANTE))
                && !tipoParticipanteEntidad.getCodigo().equals(Constantes.CONST_PADRE_DECLARANTE))
                && !tipoParticipanteEntidad.getCodigo().equals(Constantes.CONST_MADRE_DECLARANTE))
                && !tipoParticipanteEntidad.getCodigo().equals(Constantes.CONST_DECLARANTE))
                && !tipoParticipanteEntidad.getCodigo().equals(Constantes.CONST_MADRE_PARTICIPANTE))
                && !tipoParticipanteEntidad.getCodigo().equals(Constantes.CONST_PADRE_PARTICIPANTE))
                && !tipoParticipanteEntidad.getCodigo().equals(Constantes.CONST_FALLECIDO))) {

            /**
             * Agrega la palabra DECLARANTE al nombre de TipoParticipante cuando sean diferente a la condicion
             */
            tipoParticipanteEntidad = agregandoDecOPart(isDeclarante, tipoParticipanteEntidad);

        }
        return tipoParticipanteMapper.entityToBO(tipoParticipanteEntidad);
    }

    private TipoParticipanteEntidad agregandoDecOPart(boolean isDeclarante,
            TipoParticipanteEntidad tipoParticipanteEntidad) {

        if (isDeclarante) {
            tipoParticipanteEntidad.setNombre(tipoParticipanteEntidad.getNombre() + " DECLARANTE");
        } else if (agregarPalabraParticipante(tipoParticipanteEntidad.getCodigo())) {
            tipoParticipanteEntidad.setNombre(tipoParticipanteEntidad.getNombre() + " PARTICIPANTE");
        }

        return tipoParticipanteEntidad;
    }

    /**
     * Metodo que verifica si el codigo del participante es del grupo de participantes al que debe agregarse la palabra
     * Participante al nombre. Eso para efectos de verlo asi en las pantallas de autenticar.
     *
     * @param codigoParticipante String con el codigo del participante
     * @return true o false
     */
    private boolean agregarPalabraParticipante(String codigoParticipante) {

        if (codigoParticipante.equals(Constantes.CONST_ABUELA_MATERNA)
                || codigoParticipante.equals(Constantes.CONST_ABUELO_MATERNO)
                || codigoParticipante.equals(Constantes.CONST_ABUELA_PATERNA)
                || codigoParticipante.equals(Constantes.CONST_ABUELO_PATERNO)
                || codigoParticipante.equals(Constantes.CONST_BISABUELO_MATERNO)
                || codigoParticipante.equals(Constantes.CONST_TATARABUELO_PATERNO)
                || codigoParticipante.equals(Constantes.CONST_TATARABUELA_MATERNA)
                || codigoParticipante.equals(Constantes.CONST_TATARABUELA_PATERNA)
                || codigoParticipante.equals(Constantes.CONST_BISABUELO_PATERNO)
                || codigoParticipante.equals(Constantes.CONST_BISABUELA_MATERNA)
                || codigoParticipante.equals(Constantes.CONST_BISABUELA_PATERNA)
                || codigoParticipante.equals(Constantes.CONST_TATARABUELO_MATERNO)) {

            return true;
        }

        return false;
    }
}
