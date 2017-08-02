/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.gob.cne.sarc.proteger.protegerrecursos.core.protegerrecursos;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;

/**
 *
 * @author SoaintWin64HP
 */
@Configuration
@EnableAsync
@EnableScheduling
@EnableWebSecurity
@ComponentScan("ve.gob.cne.sarc.proteger.protegerrecursos.core.protegerrecursos")
@ImportResource("classpath:/META-INF/spring/*.xml")
public class AppConfig implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);
    
    @Autowired
    @Qualifier("dataSourceOAUTH")
    private DataSource dataSource;

    @Autowired
    private AdministradorPropiedades properties;

    private String port, ip;

    @Bean(name = "properties")
    public AdministradorPropiedades getProperties() throws GenericException {
        return new AdministradorPropiedadesImplementacionApache("SARC_HOME", "conf/general/config-Manage.properties");
    }

//    @Bean
//    public AccessTokenConverter accessTokenConverter() {
//        return new DefaultAccessTokenConverter();
//    }

//    @Bean
//    public RemoteTokenServices remoteTokenServices() {
//
//        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        String url;
//
//        try {
//            port = properties.getProperty("port");
//            ip = properties.getProperty("ip");
//            url = "http://" + ip + ":" + port + "/autorizar-seguridad/oauth/check_token";
//
//            LOG.info("url para chequeo de token " + url);
//
//            remoteTokenServices.setCheckTokenEndpointUrl(url);
//            remoteTokenServices.setClientId("cne");
//            remoteTokenServices.setClientSecret("cne");
//            remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
//
//        } catch (Exception ex) {
//            LOG.error("problemas archivo de propiedades para el servicio de seguridad", ex);
//        }
//        return remoteTokenServices;
//    }

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
        this.port = properties.getProperty("port");
        this.ip = properties.getProperty("ip");

    }
    
    @Bean
    public TokenStore tokenStore() {
        JdbcTokenStore store = new JdbcTokenStore(dataSource);
        return store;
    }
    
    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
    
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        final OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
//        oAuth2AuthenticationManager.setTokenServices(defaultTokenServices());
//        return oAuth2AuthenticationManager;
//    }
    
}
