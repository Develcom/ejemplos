package ve.gob.cne.sarc.notificaciones.core.notificaciones.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ve.gob.cne.sarc.comunes.notificacion.Etiquetas;

import ve.gob.cne.sarc.notificaciones.core.notificaciones.business.NotificacionBF;

/**
 * NotificacionController.java
 * 
 * @descripcion Controlador REST del Servicio Notificacion
 * @version 1.0 15/7/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    private static final Logger LOG = LoggerFactory.getLogger(NotificacionController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private NotificacionBF notificacionBF;

    /**
     * Metodo para enviar notificaciones por mail
     * 
     * @param html String que contiene el html a enviar por mail
     * @param mailReceptor String que contiene mail destinatario
     * @param asunto String que contiene el asunto del mail
     * @param etiquetas Objeto de tipo Etiquetas que contiene el valor de las etiquetas
     * @return Verdadero si envia la notificacion por mail
     */
    @RequestMapping(value = "/enviarCorreo/{html}/{mailReceptor}/{asunto}", method = RequestMethod.POST, 
            produces = {"application/json"})
    @ResponseBody
    public boolean enviarCorreo(@PathVariable("html") String html, @PathVariable("mailReceptor") String mailReceptor,
            @PathVariable("asunto") String asunto, @RequestBody Etiquetas etiquetas) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "enviarCorreo");

        return notificacionBF.enviarCorreo(etiquetas, html, mailReceptor, asunto);
    }

}
