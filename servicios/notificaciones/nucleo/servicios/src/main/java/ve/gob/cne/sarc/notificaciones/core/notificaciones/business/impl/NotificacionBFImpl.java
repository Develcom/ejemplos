package ve.gob.cne.sarc.notificaciones.core.notificaciones.business.impl;

import java.util.Iterator;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.notificaciones.core.notificaciones.business.NotificacionBF;
import ve.gob.cne.sarc.comunes.notificacion.Etiquetas;
import ve.gob.cne.sarc.notificaciones.core.notificaciones.exception.ResourceNotFoundException;

/**
 * NotificacionBFImpl.java
 *
 * @descripcion Implementacion por defacto de {@link NotificacionBF}
 * @version 1.0 15/7/2016
 * @author Anabell De Faria
 */
@Component
public class NotificacionBFImpl implements NotificacionBF {

    private static final Logger LOG = LoggerFactory.getLogger(NotificacionBFImpl.class);

    private static final long serialVersionUID = 1L;

    private static String address;
    private static String passwd;
    private static Properties props = new Properties();

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    private static final String CONST_STARTTLS = "mail.smtp.starttls.enable";
    private static final String CONST_HOST = "mail.smtp.host";
    private static final String CONST_PORT = "mail.smtp.port";
    private static final String CONST_USER = "mail.smtp.user";
    private static final String CONST_PASS = "mail.smtp.pass";
    private static final String CONST_AUTH = "mail.smtp.auth";
    private static final String CONST_CHARSET = "mail.mime.charset";

    private static Authenticator authenticator;
    private Session session;
    private Transport transport;

    /**
     * Constructor de la clase
     */
    public NotificacionBFImpl() {
        validarParametros();
    }

    /**
     * Metodo para enviar notificaciones por mail
     *
     * @param etiquetas Objeto de tipo Etiquetas que contiene el valor de las
     * etiquetas
     * @param html String que contiene el html a enviar por mail
     * @param mailReceptor String que contiene mail destinatario
     * @param asunto String que contiene el asunto del mail
     * @return Verdadero si envia la notificacion por mail
     */
    @Override
    public boolean enviarCorreo(Etiquetas etiquetas, String html, String mailReceptor, String asunto) {

        LOG.info("=====INICIANDO enviarCorreo==========");
        boolean resp = false;

        try {
            // *** Este codigo es temporal, debe construirse en la capa Vista
            Etiquetas eti;
            eti = etiquetas;

            LOG.info("dest --> " + mailReceptor + "cant etiquetas --> " + eti.itemAgregado());
            //Para la implementacion completa sustituir la variable eti por parametro de entrada etiquetas
            //y sustituir crearHtml por parametro de entrada html
            String cuerpo = this.reemplazarTag(eti, html);
            resp = this.enviarNotificacion(asunto, cuerpo, mailReceptor);
            //
        } catch (Exception e) {
            LOG.error("Error enviando notificacion: " + mailReceptor, e);
        }

        return resp;
    }

    private String crearHtml() {

        return "<html lang=es>"
                + "<head> <title>Template</title> <meta charset=utf-8 /> </head>"
                + "<body>   <br><br>"
                + "   <b>Señor(a)</b>"
                + " <br>"
                + "     <b>Registrador(a) Civil </b>"
                + " <br><br>"
                + " El acta N° <b>  <tag:numActa> </b>, de fecha <b> <tag:fechaNacimiento> </b>, inscrita en <b> "
                + " <tag:oficina></b>, perteneciente a <b><tag:nombreCompleto> </b>, queda sin efecto, en virtud del "
                + " procedimiento de adopci&oacute;n realizado, por lo cual no es susceptible a certificaci&oacute;n."
                + " <br><br>" + "   <div style='text-align: center;'>" + "        <b> Registrador (a) Civil </b> </br>"
                + "   </div>  </br>                   " + "    <div style='text-align: center;'>"
                + "          <tag:nombreOficinaRC>" + "     </div> " + "    </br>"
                + "<div style='text-align: center;'>" + "    <b>Resoluci&oacute;n N° </b> "
                + "   <tag:numResolucion> , " + "<b>fecha</b>  <tg:fechaResolucion> </br>" + "</div>"
                + "<div style='text-align: center;'>"
                + "    <b>Gaceta </b> <tag:numeroGaceta> , <b>fecha </b>  <tag:fechaGaceta>" + " </div> " + "</body>"
                + "</html>";
    }

    static {
        props.setProperty(CONST_STARTTLS, "false");
        props.setProperty(CONST_HOST, "smtp.soaint.com");
        props.setProperty(CONST_PORT, "smtp");
        props.setProperty(CONST_USER, "jsubero@soaint.com");
        props.setProperty(CONST_PASS, "18755922");
        props.setProperty(CONST_AUTH, "true");
        props.setProperty(CONST_CHARSET, "utf8");

        address = props.getProperty(CONST_USER);
        passwd = props.getProperty(CONST_PASS);

        validarParametros();

        // Se crea el autenticador
        authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, passwd);
            }
        };
    }

    /**
     * Metodo encargado de validar los parametros que son necesarios para
     * conectarse con el servidor de correo
     *
     * @throws IllegalArgumentException
     */
    private static void validarParametros() throws ResourceNotFoundException {
        if (props.getProperty(CONST_USER) == null) {
            throw new ResourceNotFoundException(NotificacionBFImpl.class.getCanonicalName()
                    + " El parametro smtp.user no puede ser nulo");
        } else if (props.getProperty(CONST_PASS) == null) {
            throw new ResourceNotFoundException(NotificacionBFImpl.class.getCanonicalName()
                    + " El parametro smtp.pass no puede ser nulo");
        } else if (props.getProperty(CONST_HOST) == null) {
            throw new ResourceNotFoundException(NotificacionBFImpl.class.getCanonicalName()
                    + " El parametro smtp.host no puede ser nulo");
        } else if (props.getProperty(CONST_PORT) == null) {
            throw new ResourceNotFoundException(NotificacionBFImpl.class.getCanonicalName()
                    + " El parametro smtp.port no puede ser nulo");
        } else if (props.getProperty(CONST_STARTTLS) == null) {
            throw new ResourceNotFoundException(NotificacionBFImpl.class.getCanonicalName()
                    + " El parametro smtp.ttls no puede ser nulo");
        }
    }

    /**
     * Metodo que se encarga de abrir la conexion SMTP
     *
     * @return
     * @throws MessagingException
     */
    private Transport abrir() throws MessagingException {
        // Se abre la session
        Session session2 = Session.getInstance(props, authenticator);
        transport = session2.getTransport("smtp");
        transport.connect(address, passwd);
        return transport;
    }

    /**
     * Metodo que se encarga de enviar correo
     *
     * @param asunto contiene el asunto del correo
     * @param cuerpo contiene el cuerpo del correo
     * @param cuentaDestino contiene la cuenta del destinatareo
     */
    private boolean enviarNotificacion(String asunto, String cuerpo, String cuentaDestino) {

        boolean enviado = true;

        try {
            abrir();
            procesoEnviarCorreo(asunto, cuerpo, cuentaDestino, false);
        } catch (MessagingException | ResourceNotFoundException e) {
            LOG.error("Error enviando notificacion: " + cuentaDestino, e);
            enviado = false;
        } finally {
            close();
        }
        return enviado;
    }

    /**
     * Metodo que se encarga de enviar correo
     *
     * @param asunto contiene el nombre del asunto del correo
     * @param cuerpo contiene el cuerpo del correo
     * @param direccionDestino contiene la direccion del destinatario
     * @param throwe contiene si emitir mensaje en caso de error
     */
    private void procesoEnviarCorreo(String asunto, String cuerpo, String destinatareo, boolean throwe)
            throws ResourceNotFoundException {

        String[] correos;

        try {

            // Validacion de entrada
            if ((asunto == null) && (cuerpo == null) && (destinatareo == null)) {
                throw new ResourceNotFoundException("Los parametro asunto, cuerpo, y destinatareo no puede ser nulo");
            }

            if (session == null) {
                abrir();
            } else {
                throw new ResourceNotFoundException("Error abriendo conexion: " + destinatareo);
            }

            BodyPart texto = new MimeBodyPart();

            MimeMultipart multiParte = new MimeMultipart();
            texto.setContent(cuerpo, CONTENT_TYPE);
            multiParte.addBodyPart(texto);

            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", CONTENT_TYPE);
            message.setFrom(new InternetAddress(address));

            correos = destinatareo.split(",");

            for (String correo : correos) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            }
            message.setSubject(asunto);
            message.setContent(multiParte);

            transport.sendMessage(message, message.getAllRecipients());

        } catch (ResourceNotFoundException | MessagingException e) {
            if (throwe) {
                LOG.error("Error enviando Notificaciones: " + destinatareo, e);
            }
        }
    }

    /**
     * Metodo que se encarga de cerrar la conexion
     *
     */
    private void close() {
        if (transport != null && transport.isConnected()) {
            try {
                transport.close();
            } catch (Exception e) {
                LOG.error("Error cerrando conexion: ", e);
            }
        }
        transport = null;
        session = null;
    }

    /**
     * Metodo encargado de reemplazar todos las etiquetas que se encuentra en
     * las plantillas html
     *
     * @param eti objeto de la clase etiqueta
     * @param html contiene el html que se va reemplazar
     * @return String con el html con las etiquetas cargadas
     */
    private String reemplazarTag(Etiquetas eti, String html) {

        if (eti.itemAgregado() == 0 || html.isEmpty()) {
            LOG.info("Algunos datos que son necesario para reemplazar los tag en la plantilla son Nulo, por favor "
                    + " verificar");
            return null;
        }
        String htmlFinal = null;
        Iterator<String> it = eti.getClave();
        int cont = 1;
        while (it.hasNext()) {
            String clave;
            clave = it.next();

            String valor = eti.obtenerItem(clave);

            if (cont == 1) {
                htmlFinal = html.replaceAll(clave, valor);
            } else {
                htmlFinal = htmlFinal.replaceAll(clave, valor);
            }

            cont++;
        }
        LOG.info(" html generado  " + htmlFinal);
        return htmlFinal;

    }

}
