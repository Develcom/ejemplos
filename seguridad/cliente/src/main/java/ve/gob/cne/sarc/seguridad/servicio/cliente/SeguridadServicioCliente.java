package ve.gob.cne.sarc.seguridad.servicio.cliente;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

@Component
public class SeguridadServicioCliente implements EscuchaAdministradorPropiedades {

    private static String endPointAccesToken;
    private static String endPointRefresToken;
    private static String endPointGetUser;
    private static String endPointLogout;

    //@Autowired
    private AdministradorPropiedades properties;

    private static final Logger LOG = LoggerFactory.getLogger(SeguridadServicioCliente.class);

    public SeguridadServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.registrarEscucha(this);
        init();
    }

    /**
     * Inicia sesion y genera el token de acceso
     *
     * @param NombreUsuario
     * @param Clave
     * @return Objeto json con la informacion del token y el usuario
     * @throws JSONException
     */
    public JSONObject obtenerAcceso(String NombreUsuario, String Clave) throws JSONException {

        LOG.info("Solicitando Acceso");
        RestTemplateHelper rth = new RestTemplateHelper();
        JSONTokener tokener;
        JSONObject json;
        String url;
        ResponseEntity<String> response;

        url = endPointAccesToken;
        url = url.replace("USUARIO", NombreUsuario);
        url = url.replace("CLAVE", Clave);

        response = rth.procesarPeticionPost(url, null, String.class);
        tokener = new JSONTokener(response.getBody());
        json = new JSONObject(tokener);

        return json;
    }

    /**
     * Refresca el token de acceso cuando expira
     *
     * @param refreshToken
     * @return nuevo token de acceso
     * @throws JSONException
     */
    public JSONObject regenerarAcceso(String refreshToken) throws JSONException {

        LOG.info("refresh token");
        RestTemplateHelper rth = new RestTemplateHelper();
        ResponseEntity re;
        JSONTokener stringer;
        JSONObject json;
        String url;

        url = endPointRefresToken;
        url = url.replace("REFRESH_TOKEN", refreshToken);

        re = rth.procesarPeticionPost(url, null, String.class);
        stringer = new JSONTokener((String) re.getBody());
        json = new JSONObject(stringer);

        return json;
    }

    /**
     * Obtiene informacion del usuario segun su token de acceso
     *
     * @param accessToken
     * @return un objecto json con toda la informacion del usuario
     * @throws JSONException
     */
    public JSONObject getUsernameCliente(String accessToken) throws JSONException {

        RestTemplateHelper rth = new RestTemplateHelper();
        JSONObject json;
        HttpHeaders headers = new HttpHeaders();
        JSONTokener stringer;
        String url;

        url = endPointGetUser;

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        stringer = new JSONTokener(rth.procesarPeticionSeguridad(url,
                accessToken, null, String.class, HttpMethod.POST));
        json = new JSONObject(stringer);

        return json;
    }

    /**
     * Expira el token de acceso
     *
     * @param accessToken
     * @return
     */
    public String cerrarSesion(String accessToken) {

        RestTemplateHelper rth = new RestTemplateHelper();
        String url, resp;

        url = endPointLogout + accessToken;

        resp = rth.procesarPeticionSeguridad(url, accessToken, null, String.class, HttpMethod.DELETE);

        return resp;
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if (archivo.equals("SeguridadServicioCliente.properties")) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointAccesToken = properties.getProperty("endPointAccesToken");
        endPointRefresToken = properties.getProperty("endPointRefresToken");
        endPointGetUser = properties.getProperty("endPointGetUser");
        endPointLogout = properties.getProperty("endPointLogout");
    }

}
