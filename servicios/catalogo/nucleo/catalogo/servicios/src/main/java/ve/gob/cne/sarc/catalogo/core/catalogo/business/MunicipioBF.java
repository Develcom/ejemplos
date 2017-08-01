package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Municipio;

/**
 * MunicipioBF.java
 * @descripcion Interfaz del catalogo Municipio
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
public interface MunicipioBF {

    /**
     * Interfaz del metodo responsable de listar los Municipios
     *
     * @return Lista de {@link Municipio}
     */
    List<Municipio> consultarTodos();

    /**
     * Interfaz del metodo responsable de buscar los Municipios dado un codigo
     * de Estado
     *
     * @param codigo String codigo de estado
     * @return Lista de {@link Municipio}
     */
    List<Municipio> consultarPorEstado(Long codigo);
}
