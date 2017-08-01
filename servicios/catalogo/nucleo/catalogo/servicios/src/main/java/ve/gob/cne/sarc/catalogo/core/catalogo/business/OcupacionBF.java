package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;
import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;

/**
 * OcupacionBF.java
 * @descripcion Interfaz del catalogo Ocupacion
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
public interface OcupacionBF {

    /**
     * Interfaz del metodo responsable de listar las Ocupaciones
     *
     * @return Lista de {@link Ocupacion}
     */
    List<Ocupacion> consultarTodos();
}
