package ve.gob.cne.sarc.catalogo.core.catalogo.util;

/**
 * Constantes.java
 * @descripcion Clase que contiene todas las constantes necesarias para los servicios catalogo
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
public class Constantes {

    /*TIPOS DE SOLICITANTE**/
    public static final String TIPO_SOLICITANTE_DECLARANTE = "D";
    public static final String TIPO_SOLICITANTE_ENTE_PUBLICO = "E";
    /*Estado Solicitud*/
    public static final String ABIERTA = "AB";
    /*Tipos de Participantes */
    public static final String CONST_HIJO_DECLARANTE = "HIJD";
    public static final String CONST_COUNYUGE_DECLARANTE = "COUND";
    public static final String CONST_PADRE_DECLARANTE = "PAD";
    public static final String CONST_MADRE_DECLARANTE = "MAD";
    public static final String CONST_DECLARANTE = "DEC";
    public static final String CONST_MADRE_PARTICIPANTE = "MAM";
    public static final String CONST_PADRE_PARTICIPANTE = "PAP";
    public static final String CONST_FALLECIDO = "FAL";

    public static final String CONST_ABUELA_MATERNA = "ABAM";
    public static final String CONST_ABUELO_MATERNO = "ABOM";
    public static final String CONST_ABUELA_PATERNA = "ABAP";
    public static final String CONST_ABUELO_PATERNO = "ABOP";
    public static final String CONST_BISABUELO_MATERNO = "BIAOM";
    public static final String CONST_TATARABUELO_PATERNO = "TTBOP";
    public static final String CONST_TATARABUELA_MATERNA = "TTBAM";
    public static final String CONST_TATARABUELA_PATERNA = "TTBAP";
    public static final String CONST_BISABUELO_PATERNO = "BIAOP";
    public static final String CONST_BISABUELA_MATERNA = "BIAAM";
    public static final String CONST_BISABUELA_PATERNA = "BIAAP";
    public static final String CONST_TATARABUELO_MATERNO = "TTBOM";

    /*Modulos*/
    public static final String CONST_NACIMIENTO = "NACIM";
    public static final String CONST_DEFUNCION = "DEFUN";
    /*Codigos de Tramites*/
    public static final String CONST_REG_ADOPCION = "RADOP";
    public static final String CONST_PER_INHUMACION_CREMACION = "EPDIC";
    
    /*Propiedad tipo de documento en servicio-participante.properties*/
    public static final String TIPO_DOCUMENTO = "servicios.participante.tipo.documento.";
    
    /*Tipo de documentos para autenticar*/
    public static final String CARTA_CONSEJO_COMUNAL = TIPO_DOCUMENTO+"carta_consejo_comunal";
    public static final String DECLARACION_JURADA = TIPO_DOCUMENTO+"declaracion_jurada";
    public static final String DOCUMENTO_PUBLICO = TIPO_DOCUMENTO+"documento_publico";
    
    private Constantes() {
        //Metodo constructor
    }

}
