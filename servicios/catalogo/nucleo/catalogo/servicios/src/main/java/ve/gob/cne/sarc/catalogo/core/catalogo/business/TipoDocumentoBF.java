package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;

/**
 * TipoDocumentoBF.java
 * @descripcion Interfaz del catalogo Tipo de Documento
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface TipoDocumentoBF {

    /**
     * Interfaz del metodo responsable de listar los tipos de documentos de acuerdo al tipo de solicitante
     * 
     *
     * @param tipoSolicitante (E= ente publico) o (D = declarante)
     * @return Lista de {@link TipoDocumento}
     */
    List<TipoDocumento> consultarTipoDocumento(String tipoSolicitante);
}
