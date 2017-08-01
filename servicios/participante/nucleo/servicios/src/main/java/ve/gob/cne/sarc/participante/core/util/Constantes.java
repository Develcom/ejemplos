package ve.gob.cne.sarc.participante.core.util;

/**
 * Constantes.java
 *
 * @descripcion Clase que contiene las constantes a usarse en Servicio Participante.
 * @version 1.0 24/11/2015
 * @author Anabell De Faria
 */
public class Constantes {

    /*TIPOS DE SOLICITANTE**/
    public static final String TIPO_SOLICITANTE_DECLARANTE = "servicios.solicitud.tipo.solicitante.declarante";
    public static final String TIPO_SOLICITANTE_ENTE_PUBLICO = "servicios.solicitud.tipo.solicitante.ente";
    
    /*Estado Solicitud*/
    public static final String ABIERTA = "AB";
    public static final String PENDIENTE_POR_AUTENTICAR_SOL="PA";

    /*Estatus Acta*/
    public static final String ESTATUS_ACTA_PUBLICA = "servicios.acta.estatus.publica";
    public static final String ESTATUS_ACTA_RESTRINGIDA = "servicios.acta.estatus.restringida";

    /*Tramite*/
    public static final String REGISTRO_DE_DEFUNCION = "RDEFU";
    public static final String REGISTRO_DE_NACIMIENTO = "RNACI";
    public static final String PERMISO_INHUMACION_CREMACION = "EPDIC";
    
    /*Estatus Solicitud al registrar Declaracion Jurada*/
    public static final String EST_SOL_DECLARACION_JURADA = "servicio.solicitud.estatus.solicitud.declaracion.jurada";
    
    /*Actas ciudadano*/
    public static final String ACTAS_PROPIAS="servicios.participante.personal";
    public static final String ACTAS_RELACIONADA="servicios.participante.relacionado";
    public static final String ACTAS_HIJOS="participante.hijo";
    public static final String ACTAS_MATRIMONIO="participante.acta.matrimonio";
            
    private Constantes() {
    }
}
