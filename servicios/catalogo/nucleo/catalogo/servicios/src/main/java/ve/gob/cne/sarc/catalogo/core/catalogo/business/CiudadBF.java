package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Ciudad;

/**
 * CiudadBF.java
 * @descripcion Interfaz del catalogo Ciudades
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface CiudadBF {

    /**
     * Interfaz del metodo responsable de listar las Ciudades
     *
     * @return Lista de {@link Ciudad}
     */
    List<Ciudad> consultarTodos();
}
