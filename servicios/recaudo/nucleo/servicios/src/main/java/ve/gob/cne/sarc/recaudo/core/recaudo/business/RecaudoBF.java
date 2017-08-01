package ve.gob.cne.sarc.recaudo.core.recaudo.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.Recaudo;

/**
 * RecaudoBF.java
 * @descripcion BusinessFacade con la logica de negocio de manejo de Recaudo
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
public interface RecaudoBF {

    /**
     * Metodo responsable de buscar Recaudo dado un codigo
     *
     * @param codigo String que contiene el codigo de Recaudo
     * @return Recaudo, instancia de objeto que contiene la informacion del Recaudo
     */
    Recaudo consultarPorCodigo(String codigo);
    
    /**
     * Metodo que permite registrar los recaudos solicitados en una solicitud
     * 
     * @param numeroSolicitud String que contiene el numero de la solicitud
     * @param recaudos String[] que contiene los recaudos solicitados y la obligatoriedad, su formato es 
     *                 {codRecaudo1:true, codRecaudo2:false, codRecaudo3:true}
     * @return Verdadero si actualiza satisfactoriamente, en caso contrario falso
     */
    boolean registrarRecaudos(String numeroSolicitud, String[]  recaudos);
    
    /**
    *
    * Metodo que permite consultar los recaudos en una solicitud
    *
    * @param numSolicitud String numero de solicitud
    * @return lista de objetos de tipo Recaudo
    *
    */    
    List<Recaudo> consultarRecaudos(String numSolicitud);
}
