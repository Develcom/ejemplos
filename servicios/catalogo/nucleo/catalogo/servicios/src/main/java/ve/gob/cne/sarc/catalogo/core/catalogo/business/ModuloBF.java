package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Modulo;

/**
 * ModuloBF.java
 * @descripcion Interfaz del catalogo Modulo
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
public interface ModuloBF {

    /**
     * Interfaz del metodo responsable de buscar los Modulos dado un codigo de
     * Oficina
     *
     * @param codigo String codigo de oficina
     * @return Lista de {@link Modulo}
     */
    public List<Modulo> consultarModuloPorOficina(String codigo);
}
