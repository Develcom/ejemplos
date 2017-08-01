package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Pais;

/**
 * PaisBF.java
 * @descripcion Interfaz del catalogo Pais
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface PaisBF {

    /**
     * Interfaz del metodo responsable de listar los Paises
     *
     * @return Lista de {@link Pais}
     */
    List<Pais> consultarTodos();
}
