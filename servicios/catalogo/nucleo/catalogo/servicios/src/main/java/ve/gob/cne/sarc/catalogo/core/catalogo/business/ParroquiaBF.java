package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Parroquia;

/**
 * <p>
 * Interfaz del catalogo Parroquia</p>
 *
 * @author Soaint
 * @version Version 2.0
 */
/**
 * ParroquiaBF.java
 * @descripcion Interfaz del catalogo Parroquia
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface ParroquiaBF {

    /**
     * Interfaz del metodo responsable de listar las Parroquias
     *
     * @return Lista de {@link Parroquia}
     */
    List<Parroquia> consultarTodos();

    /**
     * Interfaz del metodo responsable de buscar las Parroquias dado un codigo
     * de Municipio
     *
     * @param codigo String codigo de municipio
     * @return Lista de {@link Parroquia}
     */
    List<Parroquia> consultarPorMunicipio(Long codigo);
}
