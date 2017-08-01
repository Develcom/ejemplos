package ve.gob.cne.sarc.solicitud.servicio.cliente;

/**
 * Clase de constante con las claves de las url
 *
 * @author Anabell De Faria
 * @version 1.0
 */
public class Constantes {

    /*TIPOS DE SOLICITANTE**/
    public static final String TIPO_SOLICITANTE_DECLARANTE = "D";
    public static final String TIPO_SOLICITANTE_ENTE_PUBLICO = "E";

    /*Estado Solicitud*/
    public static final String ABIERTA = "AB";
    public static final String CERRADA = "CE";
    public static final String CANCELADA = "CA";
    public static final String PARCIALMENTE_CERRADA = "PC";
    public static final String NO_CONFORME_POR_REGISTRADOR_CIVIL = "NC";
    public static final String CANCELADA_POR_EXPIRACION_DE_TIEMPO = "CP";
    public static final String PENDIENTE_POR_VERIFICAR_RC = "PV";
    public static final String PENDIENTE_POR_IMPRIMIR = "PI";
    public static final String PENDIENTE_POR_AUTENTICAR_SOL = "PA";
    public static final String PENDIENTE_POR_ELABORAR_ACTA = "PEA";
    public static final String PENDIENTE_POR_IMPRIMIR_ACTA = "PPI";
    public static final String PENDIENTE_POR_VERIFICAR_RC_ACTA = "PVR";
    public static final String PENDIENTE_POR_IMPRIMIR_DJ = "IDJ";
    public static final String PENDIENTE_POR_CARGAR_DOCUMENTO_DJ = "CDJ";
    public static final String PENDIENTE_POR_REG_NOTA_MARGINAL = "PRM";
    public static final String PENDIENTE_POR_CARGAR_DOCUMENTO = "PPD";
    public static final String PENDIENTE_POR_REGISTRAR_NOTA_MARGINAL = "PRNM";
    public static final String NO_CONFORME_POR_REGISTRADOR_CIVIL_ACTA = "NCA";
    public static final String PENDIENTE_POR_CARGAR_NOTIFICAION = "PCN";
    public static final String PENDIENTE_POR_VERIFICAR_OFICIO = "PVO";
    public static final String PENDIENTE_POR_CARGAR_DOC_ACTA = "PCA";
    public static final String PENDIENTE_POR_IMPRIMIR_OFICIO = "PPIO";
    public static final String PENDIENTE_POR_CARGAR_DOC_OFICIO = "PCDO";

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

    private Constantes() {
        //Metodo constructor
    }
}
