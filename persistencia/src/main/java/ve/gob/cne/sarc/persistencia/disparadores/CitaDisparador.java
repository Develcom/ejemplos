package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.CitaEntidad;

/**
 * CitaDisparador.java
 * @descripcion Clase de validacion de las columnas de la entidad Cita
 * @version 
 * 13 de oct. de 2016
 * @author Domingo Rondon
 */
public class CitaDisparador {
    
    /**
     * validacionesCita
     * Metodo de Validaciones de las Cita
     * @since 13 de oct. de 2016
     * @param cita
     * @throws ExceptionDisparador
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-312
     */
    @PrePersist
    @PreUpdate
    public void validacionesCita(CitaEntidad cita) throws ExceptionDisparador {
        validarLugarCelebracionNulo(cita);
        validarFechaCelebracionNulo(cita);
    }
    
    private void validarLugarCelebracionNulo(CitaEntidad cita) throws ExceptionDisparador {
        if (cita.getLugarCelebracion() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Lugar de Celebración de la Cita");
        }
    }
    
    private void validarFechaCelebracionNulo(CitaEntidad cita) {
        if (cita.getFechaCelebracion() == null) {
            cita.setFechaCelebracion(new Date());
        }
    }

}
