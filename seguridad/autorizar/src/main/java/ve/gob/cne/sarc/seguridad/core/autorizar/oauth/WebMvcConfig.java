package ve.gob.cne.sarc.seguridad.core.autorizar.oauth;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ve.gob.cne.sarc.seguridad.core.autorizar.controllers.AdminController;

/**
 * WebMvcConfig.java
 *
 * @descripcion Configuracion para el MVC de Spring
 * @version 1.0
 * @author Erick Escalona
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan("ve.gob.cne.sarc.seguridad.core.autorizar")
@EnableJpaRepositories("ve.gob.cne.sarc.persistencia.repositorios")
@ImportResource("classpath:/META-INF/spring/infrastructure.xml")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Bean
    public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        MappingJackson2JsonView defaultView = new MappingJackson2JsonView();
        defaultView.setExtractValueFromSingleKeyModel(true);

        ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
        contentViewResolver.setViewResolvers(Arrays.<ViewResolver>asList(viewResolver));
        contentViewResolver.setDefaultViews(Arrays.<View>asList(defaultView));
        return contentViewResolver;
    }

    @Bean
    public AdminController adminController(@Qualifier("consumerTokenServices") ConsumerTokenServices tokenServices) {
        AdminController adminController = new AdminController();
        adminController.setTokenServices(tokenServices);
        return adminController;
    }
}
