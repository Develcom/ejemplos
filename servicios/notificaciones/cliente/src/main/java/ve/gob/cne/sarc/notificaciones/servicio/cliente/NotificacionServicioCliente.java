package ve.gob.cne.sarc.notificaciones.servicio.cliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import ve.gob.cne.sarc.comunes.notificacion.Etiquetas;

/**
 * NotificacionServicioCliente.java
 *
 * @descripcion Cliente para la ejecucion de los servicios de Notificaciones
 * @version 1.0 18/7/2016
 * @author Anabell De Faria
 */
public class NotificacionServicioCliente {

    private static final Logger LOG = LoggerFactory.getLogger(NotificacionServicioCliente.class);

    /**
     * Constructor
     */
    public NotificacionServicioCliente() {
        // Metodo Constructor
    }

    /**
     * Metodo para obtener url de los servicios por la clave
     *
     * @param endPointClave String que contiene endPoint a buscar
     * @return Objeto de tipo String
     */
    public String obtenerEndPointConfig(String endPointClave) {

        File configFile = new File(Constantes.CONST_RUTACONFIG + Constantes.CONST_ARCHIVOCONFIG);
        String host = "  ";
        LOG.debug("CLIENTE - " + Constantes.CONST_RUTACONFIG + Constantes.CONST_ARCHIVOCONFIG);
        LOG.info("jboss.home.dir " + System.getProperty("jboss.home.dir"));
        LOG.info("endPointClave " + endPointClave);

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            host = props.getProperty(endPointClave);
            LOG.info("url " + host);
            LOG.info("host " + host);
            LOG.info("endPointClave " + endPointClave);
            reader.close();
        } catch (FileNotFoundException ex) {
            LOG.error("ERROR Config file No Encontrado  ", ex);
        } catch (IOException ex) {
            LOG.error("ERROR I/O accediendo a Archivo Config  ", ex);
        }
        return host;
    }

    /**
     * Metodo que envia notificaciones por mail
     *
     * @param html String que contiene el formato html a enviar por mail
     * @param mailReceptor String que contiene el mail destinatario del mail
     * @param asunto String Asunto del correo electronico a enviarse
     * @param eti String que contiene las etiquetas a sustituir en el formato html
     * @return Verdadero si la notificacion fue enviada satisfactoriamente, en caso contrario falso
     */
    @SuppressWarnings("unchecked")
    public boolean enviarCorreo(String html, String mailReceptor, String asunto, Etiquetas eti) {

        RestTemplate restTemplate = new RestTemplate();

        boolean response;

        response = restTemplate.postForObject(obtenerEndPointConfig(Constantes.CONST_ENDPOINT_ENVIAR_CORREO) + html
                + "/" + mailReceptor + "/" + asunto, eti, boolean.class);
        
        return response;
    }

}
