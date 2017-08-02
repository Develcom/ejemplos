package ve.gob.cne.sarc.seguridad.core.autorizar.oauth;

import javax.servlet.Filter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * MvcWebApplicationInitializer.java
 *
 * @descripcion Inicializa el MVC de spring
 * @version 1.0
 * @author Erick Escalona
 *
 */
public class MvcWebApplicationInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
            SecurityConfiguration.class,
            WebMvcConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new HiddenHttpMethodFilter()};
    }

}
