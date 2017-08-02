package ve.gob.cne.sarc.test.client;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.LoggerFactory;

import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;

public class test {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) throws GenericException {

        try {
            SeguridadServicioCliente se = new SeguridadServicioCliente();
            JSONObject json;
            String token, refresh;

            LOG.info("");
            LOG.info("********LOGIN**********");
            json = se.obtenerAcceso("func1@cne.com", "LACLAVE");
            token=(String) json.get("access_token");
            refresh=(String) json.get("refresh_token");
            LOG.info("refresh_token " + json.get("refresh_token"));
            LOG.info("access_token " + json.get("access_token"));
            LOG.info("access_token " + json.get("expires_in"));
            LOG.info("primerNombre " + json.get("primerNombre"));
            LOG.info("segundoNombre " + json.get("segundoNombre"));
            LOG.info("primerApellido " + json.get("primerApellido"));
            LOG.info("segundoApellido " + json.get("segundoApellido"));
            LOG.info("oficina " + json.get("oficina"));
            LOG.info("rol " + json.get("rol"));

            LOG.info("");
            LOG.info("********ODTENER DATA**********");
            LOG.info("access token "+token);
            json = se.getUsernameCliente(token);
            LOG.info("username " + json.get("username"));
            LOG.info("fecha " + json.get("fecha"));
            LOG.info("expira " + json.get("expira"));
            LOG.info("primerNombre " + json.get("primerNombre"));
            LOG.info("segundoNombre " + json.get("segundoNombre"));
            LOG.info("primerApellido " + json.get("primerApellido"));
            LOG.info("segundoApellido " + json.get("segundoApellido"));
            LOG.info("oficina " + json.get("oficina"));
            LOG.info("rol " + json.get("rol"));

            LOG.info("");
            LOG.info("********REFRESH TOKEN**********");
            LOG.info("refresh token "+refresh);
            json = se.regenerarAcceso(refresh);
            LOG.info("refresh_token " + json.get("refresh_token"));
            LOG.info("access_token " + json.get("access_token"));
            LOG.info("access_token " + json.get("expires_in"));
            LOG.info("primerNombre " + json.get("primerNombre"));
            LOG.info("segundoNombre " + json.get("segundoNombre"));
            LOG.info("primerApellido " + json.get("primerApellido"));
            LOG.info("segundoApellido " + json.get("segundoApellido"));
            LOG.info("oficina " + json.get("oficina"));
            LOG.info("rol " + json.get("rol"));
        } catch (JSONException ex) {
            LOG.error("error", ex);
        }

    }

}
