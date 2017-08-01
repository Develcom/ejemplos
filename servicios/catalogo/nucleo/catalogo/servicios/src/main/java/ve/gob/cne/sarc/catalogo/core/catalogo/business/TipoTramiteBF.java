package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Tramite;

/**
 * TipoTramiteBF.java
 * @descripcion Interfaz del catalogo para los tramites
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface TipoTramiteBF {

    /**
     *
     * Interfaz del metodo responsable de listar los tramites de acuerdo al
     * codigo del modulo
     *
     *
     * @param codigoModulo String codigo de modulo, 
     * @param codTipoSolicitante String codigo del tipo de Solicitante
     * @return Lista de {@link Tramite}
     */
    List<Tramite> consultarTramitePorModulo(String codigoModulo, String codTipoSolicitante);
}
