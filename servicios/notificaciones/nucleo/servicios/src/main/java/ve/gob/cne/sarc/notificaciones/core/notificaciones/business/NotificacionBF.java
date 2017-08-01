package ve.gob.cne.sarc.notificaciones.core.notificaciones.business;

import ve.gob.cne.sarc.comunes.notificacion.Etiquetas;

/**
 * NotificacionBF.java
 * @descripcion BusinessFacade con la logica de negocio de manejo de Notificaciones
 * @version 1.0 15/7/2016
 * @author Anabell De Faria
 */
public interface NotificacionBF {

    /**
     * Metodo para enviar notificaciones por mail
     * 
     * @param etiquetas Objeto de tipo Etiquetas que contiene el valor de las etiquetas
     * @param html String que contiene el html a enviar por mail
     * @param mailReceptor String que contiene mail destinatario
     * @param asunto String que contiene el asunto del mail
     * @return Verdadero si envia la notificacion por mail
     */
    boolean enviarCorreo(Etiquetas etiquetas, String html, String mailReceptor, String asunto);

}
