package ve.gob.cne.sarc.catalogo.core.catalogo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.oficina.Oficina;

/**
 * OficinaBF.java
 *
 * @descripcion Interfaz del catalogo Oficina
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
public interface OficinaBF {

    /**
     * Interfaz del metodo responsable de listar los Paises
     *
     * @return Lista de {@link Pais}
     */
    List<Oficina> consultarTodos();

    /**
     * Interfaz del metodo para mostrar proximo nro consecutivo
     *
     * @param codigoOficina String codigo de la oficina
     * @return long proximo nro consecutivo
     */
    long proximoNroConsecutivo(String codigoOficina);
}
