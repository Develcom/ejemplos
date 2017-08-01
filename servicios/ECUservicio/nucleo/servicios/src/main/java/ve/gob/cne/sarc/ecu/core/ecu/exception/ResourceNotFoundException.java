package ve.gob.cne.sarc.ecu.core.ecu.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepcion que representa una recurso no encontrado (estatus HTTP 404 Not Found)
 * @author Elvin.Gonzalez
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 4858935792201042673L;

    /**
     * Constructo que invoca al padre
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructor que obtiene mensaje del padre
     * @param message 
     */
    public ResourceNotFoundException(String message) {
        super.getMessage();
    }

    /**
     * Constructor que obtiene la causa del padre
     * @param message
     * @param cause 
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super.getCause();
    }

    /**
     * Constructor que obtiene la causa del padre
     * @param cause 
     */
    public ResourceNotFoundException(Throwable cause) {
        super.getCause();
    }

}
