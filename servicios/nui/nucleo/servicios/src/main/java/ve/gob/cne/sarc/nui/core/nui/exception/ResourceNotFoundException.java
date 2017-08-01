package ve.gob.cne.sarc.nui.core.nui.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * ResourceNotFoundException.java
 * @descripcion [Excepcion que representa una recurso no encontrado (estatus HTTP 404 Not
 * Found)]
 * @version 1.0 14/7/2016
 * @author Elvin.Gonzalez
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 4858935792201042673L;

    /**
     * Constructor de la clase ResourceNotFoundException
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Metodo para mostrar mensaje de error por recurso no encontrado.
     *
     * @param message
     */
    public ResourceNotFoundException(String message) {
        super.getMessage();
    }

    /**
     * Metodo para mostrar la causa de error por recurso no encontrado a partir
     * del mensaje de error.
     *
     * @param message string que contiene el mensaje
     * @param cause Throwable
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super.getCause();
    }

    /**
     * Metodo para mostrar la causa de error por recurso no encontrado.
     *
     * @param cause Throwable
     */
    public ResourceNotFoundException(Throwable cause) {
        super.getCause();
    }

}
