package ve.gob.cne.sarc.solicitud.core.util;

/**
 * Constantes.java
 * @descripcion [Clase utilitaria de constantes del servicio]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
public class Constantes {

    /*TIPOS DE SOLICITANTE**/
    public static final String TIPO_SOLICITANTE_DECLARANTE = "D";
    public static final String TIPO_SOLICITANTE_ENTE_PUBLICO = "E";
    public static final String TIPO_SOLICITANTE_DECLARANTE_B = "Declarante";
    public static final String TIPO_SOLICITANTE_ENTE_PUBLICO_B = "Ente Publico";
    
    /*Estados Tramite*/
    public static final String TRAMITE_ABIERTO = "ABIERTO";
    public static final String TRAMITE_CERRADO = "CERRADA";
   
   /*Estado Solicitud*/
    public static final String ABIERTA = "AB";
    public static final String CERRADA= "CE";
    public static final String CANCELADA = "CA";
    public static final String PARCIALMENTE_CERRADA ="PC";
    public static final String NO_CONFORME_POR_REGISTRADOR_CIVIL="NC";
    public static final String CANCELADA_POR_EXPIRACION_DE_TIEMPO="CP";
    public static final String PENDIENTE_POR_VERIFICAR_RC="PV";
    public static final String PENDIENTE_POR_IMPRIMIR="PI";
    public static final String PENDIENTE_POR_AUTENTICAR_SOL="PA";
    
    /*Estatus Tramite **/
    public static final String INICIADA = "INIC0";
    public static final String PENDIENTE_POR_AUTENTICAR = "PA";
    
    /*Rol de Usuarios **/
    /*Profesional de Registrador*/
    public static final String ROL_REGISTRADOR = "R_REG";
    /*Profesional de Area de Inscripcion*/
    public static final String ROL_INSCRIPCION = "R_PRO";
    /*Profesional de Atencion Comunitaria*/
    public static final String ROL_ATENCION = "R_AST";
    
    public static final String FORMATO_DDMMYYYY = "dd-MM-yyyy";
    public static final String FORMATO_DDMMYYYY_SLASH = "dd/MM/yyyy";
    
    public static final String ESTATUS_INICIAL = "sarc.atencion.comunitaria.estatus.inicial.";
    public static final String ESTATUS_ABIERTO_LIBRO_DIARIO = "servicios.libro.diario.estatus.abierto";
    public static final String ESTATUS_CERRADO_LIBRO_DIARIO = "servicios.libro.diario.estatus.cerrado";
    
    private Constantes() {
        //Metodo Constructor
    }

}
