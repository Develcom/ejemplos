package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;


import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.DocumentosAutenticacionBF;

 /**
 * DocumentosAutenticacionBFImpl.java
 * @descripcion Implementacion del catalogo de Documentos de Autenticacion
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Component
public class DocumentosAutenticacionBFImpl implements DocumentosAutenticacionBF {

    /**
     * 
     * Metodo responsable de listar los Documentos Autenticados
     *
     * @return LinkedHashMap<String, String>
     */
    public LinkedHashMap<String, String> consultarDocumentosAutenticados() {
        LinkedHashMap<String, String> listaDocumentosAutenticados = new LinkedHashMap<>();
        listaDocumentosAutenticados.put("CM", "Carta de Consejo Comunal");
        listaDocumentosAutenticados.put("DJ", "Declaración Jurada");
        listaDocumentosAutenticados.put("DP", "Documento Público");
      
        return listaDocumentosAutenticados;
    }
    
}
