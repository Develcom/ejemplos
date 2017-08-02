package ve.gob.cne.sarc.proteger.protegerrecursos.core.protegerrecursos;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.context.WebApplicationContext;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * AppConfig.java
 *
 * @descripcion Configuracion general de proteger recursos
 * @author Erick Escalona
 * @version 1.0
 */
@Configuration
@EnableAsync
@EnableScheduling
@EnableWebSecurity
@ComponentScan("ve.gob.cne.sarc")
@ImportResource("classpath:/META-INF/spring/*.xml")
public class AppConfig implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);
    
    
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdministradorPropiedades properties;

    private String port, clientId, clientSecret;

    @PostConstruct
    public void iniciar() throws GenericException {
        properties.registrarEscucha(this);
        init();
    }

    @Bean(name = "properties")
    public AdministradorPropiedades getProperties() throws GenericException {
        return new AdministradorPropiedadesImplementacionApache("SARC_HOME", "conf/general/config-Manage.properties");
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    @Bean
    public RemoteTokenServices remoteTokenServices() {

        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        String url;
        RestTemplateHelper rth = new RestTemplateHelper();

        try {
            url = port + "/oauth/check_token";

            LOG.info("url para chequeo de token " + url);
            LOG.info("clientId " + clientId);
            LOG.info("clientSecret " + clientSecret);

            remoteTokenServices.setCheckTokenEndpointUrl(url);
            remoteTokenServices.setClientId(clientId);
            remoteTokenServices.setClientSecret(clientSecret);
            remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
            remoteTokenServices.setRestTemplate(rth.getRt());

        } catch (Exception ex) {
            LOG.error("problemas archivo de propiedades para el servicio de seguridad", ex);
        }
        return remoteTokenServices;
    }

    @Bean
    public WebApplicationContext webApplicationContextBean() {
        return webApplicationContext;
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
        this.port = properties.getProperty("port.check");
        this.clientId = properties.getProperty("client.id");
        this.clientSecret = properties.getProperty("client.secret");
    }

    
    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory() {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(
                dataSource);
        sessionBuilder.scanPackages("ve.gob.cne.sarc.persistencia.entidades");
        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {
        HibernateTransactionManager tm = new HibernateTransactionManager(
                sessionFactory);
        return tm;
    }

}
