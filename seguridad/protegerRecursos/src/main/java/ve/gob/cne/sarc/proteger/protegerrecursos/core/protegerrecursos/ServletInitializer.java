package ve.gob.cne.sarc.proteger.protegerrecursos.core.protegerrecursos;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * ServletInitializer.java
 *
 * @descripcion Inicializador de seguridad de spring.
 * {@link AbstractSecurityWebApplicationInitializer} proporciona la
 * disponibilidad de DelegatingFilterProxy y ContextLoaderListener y son
 * registrados automáticamente.
 * @author Erick Escalona
 * @version 1.0
 */
public class ServletInitializer extends AbstractSecurityWebApplicationInitializer {

    public ServletInitializer() {
        super(AppConfig.class);
    }

}
