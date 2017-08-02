package ve.gob.cne.sarc.gestionLibros.web.gestionLibros.constantes;

/**
 * ConstantesLibro.java
 * 
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version Dec 21, 2016
 * @author Anderson Delgado
 */
public class ConstantesLibro {

    // Tipos de Oficinas
    public static final String MUNICIPALES = "MUNICIPALES";
    public static final String PARROQUIALES = "PARROQUIALES";
    public static final String URCES = "URCES";
    public static final String MEDICATURAS_FORENCES = "MEDICATURAS_FORENCES";
    public static final String ACCIDENTALES = "ACCIDENTALES";
    // Libros
    public static final String LIBRO_CIVIL_REG_NACICIMIENTO = "Registrar Nacimiento";
    public static final String LIBRO_CIVIL_REG_DEFUNCION = "Registrar Defunción";
    public static final String LIBRO_CIVIL_REG_MATRIMONIO = "Registrar Matrimonio";
    public static final String LIBRO_CIVIL_NACIONALIDAD_CAPACIDAD = "Nacionalidad y Capacidad";
    public static final String LIBRO_CIVIL_RESIDENCIA_EXTRANJERIA = "Residencia y Extranjería";
    public static final String LIBRO_CIVIL_UNION_ESTABLE_HECHO = "Unión Estable de Hecho";
    // Operaciones a realizar
    public static final String OPERACION_APERTURA = "APERTURA";
    public static final String OPERACION_APERTURA_SERVICIO = "APERTURA";
    public static final String OPERACION_CIERRE = "CIERRE";
    public static final String OPERACION_CIERRE_SERVICIO = "CIERRE";
    // Plantillas creadas para la generacion de Acta
    public static final String NOMBRE_PLANTILLA_APERTURA = "plantilla_apertura.jrxml";
    public static final String NOMBRE_PLANTILLA_CIERRE = "plantilla_cierre.jrxml";
    public static final String PARAMETRO_FECHA_ACTUAL = "FECHACOMPLETA";
    public static final String PARAMETRO_ANIO_ACTUAL = "ANIO";
    public static final String PARAMETRO_DIAS_ACTUAL = "DIA";
    public static final String PARAMETRO_NUM_MES_ACTUAL = "NUM_MES";
    public static final String PARAMETRO_STRING_MES_ACTUAL = "STRING_MES";
    // Archivo de configuracion
    public static final String RUTA_ARCHIVO_INPUT = "ruta_archivo_input";
    // Variables de Errores
    public static final String ERROR_NOT_FOUND_DATA = "error_notFound_data";
    public static final String ERROR_TIEMPO_EXPIRADO = "error_tiempo_expirado";
    public static final String ERROR_ACTA = "error_acta";
    public static final String ERROR_ROL_USUARIO="error_rol_usuario";

    private ConstantesLibro() {
    }
}