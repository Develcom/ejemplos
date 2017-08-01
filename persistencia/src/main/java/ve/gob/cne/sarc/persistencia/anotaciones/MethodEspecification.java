package ve.gob.cne.sarc.persistencia.anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Anotación utilitaria para identificar el atributo al cual hace referencia una
 * anotacion hibernate colocada en un metodo get
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface MethodEspecification {
    
    /**
     * Nombre del atributo al cual hace referencia el metodo dentro de la
     * nomenclatura de hibernate
     * 
     * @return
     */
    String targetedAttribute() default "";

}
