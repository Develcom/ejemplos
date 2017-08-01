package ve.gob.cne.sarc.ecu.core.ecu.exception;

/**
 * Clase para manejar las exepciones en el servicio
 *
 * @author Erick Escalona
 * @version 1.0
 */
public class EcuException extends Exception{
    
    private static final long serialVersionUID = 2733409629850933116L;

    /**
     * constructor
     */
    public EcuException() {
        super();
    }

    /**
     * Constructor que recibe un mensaje
     * @param message 
     */
    public EcuException(String message) {
        super(message);
    }

    /**
     * Constructor que recibe un mensaje y la causa
     * @param message
     * @param cause 
     */
    public EcuException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que recibe la causa
     * @param cause 
     */
    public EcuException(Throwable cause) {
        super(cause);
    }
}
