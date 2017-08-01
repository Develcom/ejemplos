package ve.gob.cne.sarc.persistencia.anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Anotación utilitaria para la estrategia de eliminación en cascada
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface FieldDeletionStrategy {

    /**
     * Enumerado para los tipos estrategia del eliminado
     */
    public enum DeletionStrategy {
        ALONE_OPTIONS,
        CASCADE_OPTIONS,
        NONE_OPTIONS;
    }

    /**
     * Estrategia de completación del campo
     */
    DeletionStrategy options() default DeletionStrategy.NONE_OPTIONS;

}
