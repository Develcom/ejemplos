package ve.gob.cne.sarc.libro.core.libro.util;

/**
 * Constantes.java
 * @descripcion Clase utilitaria de constantes del servicio
 * @version 1.0 11/10/2015
 * @author Anabell De Faria
 */
public class Constantes {

    /*Tramite*/
    public static final String REGISTRO_DE_DEFUNCION = "RDEFU";
    public static final String REGISTRO_DE_NACIMIENTO = "RNACI";
    public static final String PERMISO_INHUMACION_CREMACION = "EPDIC";
    public static final String REGISTRO_DE_ADOPCION= "RADOP";
    public static final String REGISTRO_RECONOCIMIENTO = "RRECO";
    public static final String REGISTRO_RECOMPOSICION_FILIACION = "RRFIL";
    /*Codigos de Tipo Libro*/
    public static final String CONST_NACIMIENTO="NAC";
    public static final String CONST_DEFUNCION="DEF";
    /*Estatus del Libro*/
    public static final String LIBRO_ABIERTO = "A";
    public static final String LIBRO_CERRADO = "C";
    
    /*Constante para buscar el tomo del libro*/
    public static final String LIBRO_TOMO="servicios.libro.tomo.";
    
    /*estatus del tomo*/
    public static final String ESTATUS_TOMO_ABIERTO = "servicios.libro.tomo.estatus.abierto";
    public static final String ESTATUS_TOMO_CERRADO = "servicios.libro.tomo.estatus.cerrado";
    
    private Constantes() {
        //Metodo constructor
    }
}
