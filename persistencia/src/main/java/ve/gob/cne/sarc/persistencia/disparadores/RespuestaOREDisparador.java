package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.RespuestaOREEntidad;

/**
 * RespuestaOREDisparador.java
 * @descripcion Clase que contiene las validaciones de los campos que no pueden estar nulos
 * @version 
 * 23 de nov. de 2016
 * @author Domingo Rondon
 */
public class RespuestaOREDisparador {

    /**
     * validacionesRespuestaORE
     * Metodo que contiene las validaciones de los campos que no pueden ser nulos
     * @since 23 de nov. de 2016
     * @param respuestaORE
     * @throws ExceptionDisparador
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-211
     */
    @PrePersist
    @PreUpdate
    public void validacionesRespuestaORE(RespuestaOREEntidad respuestaORE) throws ExceptionDisparador {
        validarDesicionORENulo(respuestaORE);
        validarMotivoDesicionORENulo(respuestaORE);
        validarUsuarioRolNulo(respuestaORE);
        validarSolicitudNulo(respuestaORE);
    }
    
    
    private void validarDesicionORENulo(RespuestaOREEntidad respuestaORE) throws ExceptionDisparador {
        if (respuestaORE.getDecisionORE() == null) {
            throw new ExceptionDisparador("2#Debe introducir la Desicion ORE correspondiente");
        }
    }
    
    private void validarMotivoDesicionORENulo(RespuestaOREEntidad respuestaORE) throws ExceptionDisparador {
        if (respuestaORE.getMotivoDecisionORE() == null) {
            throw new ExceptionDisparador("2#Debe introducir Motivo a Desicion ORE correspondiente");
        }
    }
    
    private void validarUsuarioRolNulo(RespuestaOREEntidad respuestaORE) throws ExceptionDisparador {
        if (respuestaORE.getRolUsuario() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Usuario Rol correspondiente");
        }
    }
    
    private void validarSolicitudNulo(RespuestaOREEntidad respuestaORE) throws ExceptionDisparador {
        if (respuestaORE.getSolicitudORE() == null) {
            throw new ExceptionDisparador("2#Debe introducir la Solicitud correspondiente");
        }
    }
   
}
