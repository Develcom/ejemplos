package ve.gob.cne.sarc.proteger.protegerrecursos.core.protegerrecursos;

import java.util.Arrays;
import ve.gob.cne.sarc.proteger.protegerrecursos.core.protegerrecursos.util.CustomMatcher;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * ResourceServerConfiguration.java
 *
 * @descripcion Clase para la proteccion de recursos de manera dinamica
 * @author Erick Escalona
 * @version 1.0
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceServerConfiguration.class);

    @Autowired
    @Qualifier("remoteTokenServices")
    private RemoteTokenServices remoteTokenServices;
    
    @Autowired
    private CustomMatcher matcher;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("cnesarc").stateless(true).tokenServices(remoteTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        List<CustomMatcher> cms;

        http
//                .authorizeRequests()
//                .antMatchers("/catalogo/Pais/consultarTodos",
//                        "/catalogo/Ciudad/consultarTodos")
//                .access("hasAnyAuthority('R_REG') or hasAnyAuthority('R_PRO')");
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .requestMatchers().antMatchers("/**");
//
        cms = matcher.cargarRecursos();

        for (CustomMatcher m : cms) {
            LOG.info("endPoint protegido " + Arrays.toString(m.getEndPoint()) + " sus roles " + m.getRoles());
//            http.authorizeRequests()
//                    .antMatchers(m.getEndPoint())
//                    .access("hasAnyAuthority(" + m.getRoles() + ")");
            http.authorizeRequests()
                    .antMatchers(m.getEndPoint())
                    .access(m.getRoles());
        }

        http.formLogin().disable()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
                .and()
                .securityContext().securityContextRepository(securityContextRepository());
    }

    @Bean
    protected AuthenticationEntryPoint authenticationEntryPoint() {
        OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
        entryPoint.setRealmName("cnesarc");
        return entryPoint;
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}
