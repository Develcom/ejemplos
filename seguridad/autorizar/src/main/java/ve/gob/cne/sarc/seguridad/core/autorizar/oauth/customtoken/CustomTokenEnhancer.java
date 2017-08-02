package ve.gob.cne.sarc.seguridad.core.autorizar.oauth.customtoken;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import ve.gob.cne.sarc.seguridad.core.autorizar.oauth.Usuario;

/**
 * CustomTokenEnhancer.java
 *
 * @descripcion Clase para agregar informacion adicional al token de acceso
 * @version 1.0
 * @author Erick Escalona
 *
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    private static final Logger LOG = LoggerFactory.getLogger(CustomTokenEnhancer.class);

    /**
     * Proporciona una oportunidad para que la personalización de un token de
     * acceso (por ejemplo, a través de su mapa de información adicional)
     * durante el proceso de creación de un nuevo token para su uso por un
     * cliente.
     *
     * @param accessToken El token de acceso actual con su vencimiento y el
     * token de actualizacion
     * @param authentication La autenticación actual incluyendo clientes y
     * detalles de usuarios
     * @return Un nuevo token mejorado con información adicional
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        LOG.info("agregando informacion adicional al token");

        final Map<String, Object> additionalInfo = new HashMap<>();
        final Usuario user = (Usuario) authentication.getPrincipal();
        
        additionalInfo.put("primerNombre", user.getPrimerNombre());
        additionalInfo.put("segundoNombre", user.getSegundoNombre());
        additionalInfo.put("primerApellido", user.getPrimerApellido());
        additionalInfo.put("segundoApellido", user.getSegundoApellido());
        additionalInfo.put("oficina", user.getOficina());
        additionalInfo.put("rol", user.getRol());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        
        LOG.info("token de acceso con informacion adicional "+accessToken.getAdditionalInformation());

        return accessToken;
    }

}
