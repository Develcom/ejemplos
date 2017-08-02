package ve.gob.cne.sarc.seguridad.core.autorizar.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * SecurityConfiguration.java
 *
 * @descripcion Clase para verificar credenciales del usuario y agregar
 * configuracion adiccional de seguridad
 * @version 1.0
 * @author Erick Escalona
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        auth.userDetailsService(userDetailsService).
                passwordEncoder(encoder).getUserDetailsService();

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() {
        return userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .antMatcher("/oauth/**")
                .userDetailsService(userDetailsService)
                .csrf().disable();
    }

}
