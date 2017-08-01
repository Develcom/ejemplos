package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Nacionalidad;


/**
 * NacionalidadBF.java
 * @descripcion Interfaz del catalogo Nacionalidad
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
public interface NacionalidadBF {

    /**
     * Interfaz del metodo responsable de listar las Nacionalidades
     *
     * @return Lista de {@link Nacionalidad}
     */
    List<Nacionalidad> consultarTodos();
}
