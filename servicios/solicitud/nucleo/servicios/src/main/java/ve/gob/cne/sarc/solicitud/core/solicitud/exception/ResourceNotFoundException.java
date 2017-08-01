package ve.gob.cne.sarc.solicitud.core.solicitud.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResourceNotFoundException.java
 * @descripcion [Excepcion que representa una recurso no encontrado (estatus HTTP 404 Not Found)]
 * @version 1.0 22/7/2016
 * @author  Elvin.Gonzalez
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 4858935792201042673L;

    /**
     * Constructor que invoca al padre
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructor que invoca el mensaje del padre
     * @param message 
     */
    public ResourceNotFoundException(String message) {
        super.getMessage();
    }

    /**
     * Constructor que invoca la causa del padre
     * @param message
     * @param cause 
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super.getCause();
    }

    /**
     * Constructor que invoca la causa del padre
     * @param cause 
     */
    public ResourceNotFoundException(Throwable cause) {
        super.getCause();
    }

}
