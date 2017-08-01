package ve.gob.cne.sarc.persistencia.anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Anotación utilitaria para generar dinámicamente los formularios para la
 * administración de las entidades
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface FieldEspecification {
    /**
     * Enumerado para los tipos de campos HTML
     */
    public enum InputType {
        COMMONINPUT("commonInput"), EMAILINPUT("emailInput"), PASSWORDINPUT(
                "passwordInput"), TEXTAREAINPUT("textareaInput"), CHECKBOX(
                "checkBox"), SELECT("select"), BUTTON("button"), RADIOBUTTON(
                "radioButton"), DATEINPUT("dateInput"), FILEINPUT("fileInput");

        private String inputName;

        private InputType(String name) {
            this.inputName = name;
        }

        public String getInputName() {
            return this.inputName;
        }

    }

    /**
     * Enumerado para la obtencion de opciones dentro de un select
     */
    public enum CompletationStrategy {
        DB_OPTIONS,
        MANUAL_OPTIONS,
        NONE_OPTIONS;
    }

    /**
     * Campo HTML
     */
    InputType input() default InputType.COMMONINPUT;

    /**
     * Tipo de dato del atributo de la clase entidad
     */
    String type() default "java.lang.String";

    /**
     * Cantidad de caracteres permitidos en el campo HTML
     */
    int size() default 200;

    /**
     * Conjunto de reglas del campo HTML
     * La ',' es la separación entre las reglas
     * El '|' representa el OR
     * El '&' representa el AND
     * Admite operadores relacionales(>,<,>=,<=,==,~=)
     * El String dentro de una regla va con ""
     * Acepta enteros, flotantes y hexadecimales enteros
     *
     * @Ejemplos: "<3"
     * ""Hola"|"Chao""
     * "/[a-z]+/|/[0-9]+/"   "/([a-z]+|[0-9]+)/"
     * "/[0-9.]+/&~=8.5"
     */
    String constraints() default "";

    /**
     * Indica si el campo es requerido
     */
    boolean required() default false;

    /**
     * Indica si el campo estará escondido
     */
    boolean hide() default false;

    /**
     * El valor almacenado en la BD
     * Si el campo es checkbox, combo box o radio button los valores vendrán
     * en una lista de la siguiente forma ["<valor1>","<valor2>",...]
     */
    String value() default "";

    /**
     * Nombre de la etiqueta del campo HTML. Ortográficamente correcto
     */
    String label() default "";

    /**
     * Atributo que indica si el cambo no se puede editar
     */
    boolean readOnly() default false;

    /**
     * Atributo que indica que si el campo está deshabilitado
     */
    boolean disabled() default false;

    /**
     * Estrategia de completación del campo
     */
    CompletationStrategy options() default CompletationStrategy.NONE_OPTIONS;

    /**
     * Valores manuales colocados para el campo
     */
    String manualOptions() default "";

    /**
     * Orden de prioridad del campo dentro del formulario
     */
    int order() default Integer.MAX_VALUE;

    /**
     * Paso en el cual se guardará el campo
     */
    int step() default 0;

    /**
     * Alias para nombre de campo, en caso de incompatibilidad del lado del cliente AngularJS
     */
    String alias() default "";
    
    /**
     * Placeholder que se mostrará en el input
     */
    String placeHolder() default "Ejemplo: Pérez1234*";
}
