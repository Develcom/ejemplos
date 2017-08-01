package ve.gob.cne.sarc.persistencia.anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Anotación utilitaria para generar dinámicamente los formularios para la
 * administración de las entidades
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface ClassEspecification {
    
    /**
     * Politica de sincronizacion para eventos de registro de bitacora
     * 
     * UNICAST:     Registrar bitacora con destino a "principal"
     * BROADCAST:   Registrar bitacora con destino a todas las oficinas
     * NONE:        No registrar bitacora al detectar un cambio
     */
    public enum SincronizationPolicy {
        UNICAST,
        BROADCAST,
        NONE;
    }

    /**
     * Nombre a mostrar de la entidad
     */
    String name() default "";

    /**
     * Atributo(s) identificador(es) de la entidad
     * En caso de ser varios vendrán separados por ','
     * ejp: attr1,attr2,...,attrn
     */
    String identifier() default "";

    /**
     * Indica si el modelo puede ser listado
     * dentro del conjunto de catalogos disponibles
     */
    boolean canBeListed() default false;
    
    /**
     * Define la politica de sincronizacion a ser ejecutada
     * al momento de realizarse operaciones sobre el registro 
     */
    SincronizationPolicy generatesTask() default SincronizationPolicy.NONE;
}
