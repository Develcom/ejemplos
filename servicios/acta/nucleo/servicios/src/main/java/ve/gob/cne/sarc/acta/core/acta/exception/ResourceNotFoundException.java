package ve.gob.cne.sarc.acta.core.acta.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResourceNotFoundException.java
 * @descripcion [Excepcion que representa una recurso no encontrado (estatus HTTP 404 Not Found)]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 4858935792201042673L;

    /**
     * Constructor invocando al padre
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructor invocando al mensaje del padre
     * @param message String que describe el mensaje
     */
    public ResourceNotFoundException(String message) {
        super.getMessage();
    }

    
    /**
     * Constructor invocando a la causa del padre
     * @param message String que describe el message
     * @param cause Throwable que describe la causa
     * 
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super.getCause();
    }

    /**
     * Constructor invocando a la causa del padre
     * @param cause Throwable que describe la causa 
     */
    public ResourceNotFoundException(Throwable cause) {
        super.getCause();
    }

}
