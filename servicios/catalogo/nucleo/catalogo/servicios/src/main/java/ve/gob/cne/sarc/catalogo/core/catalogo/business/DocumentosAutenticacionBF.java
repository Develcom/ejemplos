package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.LinkedHashMap;


/**
 * DocumentosAutenticacionBF.java
 * @descripcion Interfaz del catalogo Documentos Autenticados
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface DocumentosAutenticacionBF {

    /**
     * Interfaz del metodo responsable de listar los documentos de autenticacion
     *
     * @return Lista de {@link DocumentosAutenticacion}
     */
    public LinkedHashMap<String, String> consultarDocumentosAutenticados();
}
