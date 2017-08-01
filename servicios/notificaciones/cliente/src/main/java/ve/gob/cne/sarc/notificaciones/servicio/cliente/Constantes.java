package ve.gob.cne.sarc.notificaciones.servicio.cliente;

/**
 * Constantes.java
 * 
 * @descripcion Clase que contiene las claves para las url
 * @version 1.0 18/7/2016
 * @author Anabell De Faria
 */
public class Constantes {

    public static final String CONST_ENDPOINT_ENVIAR_CORREO = "endPointEnviarCorreo";
    public static final String CONST_ARCHIVOCONFIG = "NotificacionServicioCliente.properties";

    public static final String CONST_RUTASERVER = System.getProperty("jboss.home.dir") == null 
            ? "C:/jboss-as-7.1.0.Final"
            : System.getProperty("jboss.home.dir");
    public static final String CONST_RUTACONFIG = CONST_RUTASERVER + "/modules/ve/gob/cne/sarc/main/";

    private Constantes() {

    }
}
