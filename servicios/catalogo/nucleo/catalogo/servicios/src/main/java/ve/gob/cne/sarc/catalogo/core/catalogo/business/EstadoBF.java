package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Estado;

/**
 * EstadoBF.java
 * @descripcion Interfaz del catalogo Estado
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface EstadoBF {

    /**
     * Interfaz del metodo responsable de listar los Estados
     *
     * @return Lista de {@link Estado}
     */
    List<Estado> consultarTodos();

    /**
     * Interfaz del metodo responsable de buscar los Estados dado un codigo de
     * Pais
     *
     * @param codigo String codigo de pais
     * @return Lista de {@link Estado}
     */
    List<Estado> consultarPorPais(Long codigo);
}
