package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;

/**
 * TipoParticipanteBF.java
 * @descripcion Interfaz del catalogo Tipo Participante
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface TipoParticipanteBF {

    /**
     * 
     * Interfaz del metodo responsable de buscar Tipo Participante dado un
     * codigo
     * 
     * @param codigo String codigo de Tipo Participante
     * @param isDeclarante boolean indica si participante es Declarante
     * @return {@link TipoParticipante}
     */
    TipoParticipante buscarPorCodigo(String codigo, boolean isDeclarante);
}
