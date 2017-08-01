package ve.gob.cne.sarc.nui.core.nui.business;

/**
 * NuiBF.java
 * @descripcion [Interfaz para la logica de negocio del controlador]
 * @version 1.0 14/7/2016
 * @author Anabell De Faria
 */
public interface NuiBF {

    /**
     * Busca el proximo numero de Nui
     *
     * @param codigoOficina String codigo de la Oficina
     * @return String con el proximo numero de Nui disponible
     */
    public String buscarProximoNui(String codigoOficina);

    /**
     * Busca un NUI segun su numero de documento de identidad
     *
     * @param numeroCedula String que contiene el numero de cedula
     * @return true si existe el nui y en caso contrario false
     */
    boolean buscarExistenciaNUI(String numeroCedula);
}
