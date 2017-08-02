package ve.gob.cne.sarc.seguridad.core.autorizar.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ve.gob.cne.sarc.seguridad.core.autorizar.oauth.Usuario;

/**
 * AdminController.java
 *
 * @descripcion Controlador para obtener el usuario por el token y revocar el
 * token al cerar la sesion
 * @version 1.0
 * @author Erick Escalona
 *
 */
@RestController
public class AdminController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private ConsumerTokenServices tokenServices;

    @Autowired
    @Qualifier("tokenStore")
    private TokenStore tokenStore;

    /**
     * Para obtener el usuario basado en su token
     *
     * @param request {@link HttpServletRequest} para obtener el token por el
     * Header
     * @return Un {@link Map} con la informacion del usuario
     */
    @RequestMapping(value = "/oauth/credentials", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> userClient(HttpServletRequest request) {

        Authentication auth;
        OAuth2Authentication authentication;
        OAuth2AccessToken accessToken;
        Map<String, String> userClient = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Date date;
        int expira;
        String token;
        Usuario usuario;

        LOG.info("obteniendo informacion del usuario por el token");

        token = request.getHeader("Authorization");

        if (token != null) {

            if (token.contains("Bearer") || token.contains("bearer")) {
                token = token.substring(7);
            }

            accessToken = tokenStore.readAccessToken(token);

            if (accessToken != null) {
                date = accessToken.getExpiration();
                expira = accessToken.getExpiresIn();

                authentication = tokenStore.readAuthentication(token);
                auth = authentication.getUserAuthentication();

                usuario = (Usuario) auth.getPrincipal();

                userClient.put("username", auth.getName());
                userClient.put("fecha", sdf.format(date));
                userClient.put("expira", String.valueOf(expira));
                userClient.put("primerNombre", usuario.getPrimerNombre());
                userClient.put("segundoNombre", usuario.getSegundoNombre());
                userClient.put("primerApellido", usuario.getPrimerApellido());
                userClient.put("segundoApellido", usuario.getSegundoApellido());
                userClient.put("oficina", usuario.getOficina());
                userClient.put("rol", usuario.getRol());

            } else {
                LOG.error("token invalido");
                userClient.put("error", "invalid_token");
                userClient.put("error_description", "Token was not recognised");
            }
        } else {
            LOG.error("token no presente");
            userClient.put("token", "token is not present");
        }
        LOG.info("resultado " + userClient);
        return userClient;
    }

    /**
     * Para revocar el token al finalizar la sesion del usuario
     *
     * @param token el token a revocar
     * @return El estatus de la revocacion
     */
    @RequestMapping(value = "/oauth/revoketoken/tokens/{token}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> revokeToken(@PathVariable String token) {

        LOG.info("revocando el token");

        if (tokenServices.revokeToken(token)) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            tokenStore.removeAccessToken(accessToken);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void setTokenServices(ConsumerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

}
