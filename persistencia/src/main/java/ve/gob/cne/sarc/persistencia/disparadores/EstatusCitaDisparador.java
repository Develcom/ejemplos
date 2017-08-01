package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EstatusCitaEntidad;

/**
 * EstatusCitaDisparador.java
 * @descripcion Disparador de la entidad de Estatus Cita
 * @version 
 * 11 de oct. de 2016
 * @author Domingo Rondon
 */
public class EstatusCitaDisparador {
    
    /**
     * validacionesEstatusCita
     * Metodo que valida si existen campos nulos en la Entidad de Estatus Cita
     * @since 11 de oct. de 2016
     * @param estatusCita
     * @throws ExceptionDisparador
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-312
     */
    @PrePersist
    @PreUpdate
    public void validacionesEstatusCita(EstatusCitaEntidad estatusCita) throws ExceptionDisparador {
        validarCodigoNulo(estatusCita);
        validarCodigoVacio(estatusCita);
        validarDescripcionNulo(estatusCita);
        validarDescripcionVacio(estatusCita);
        validarEstadoNulo(estatusCita);
    }
    
    private void validarCodigoNulo(EstatusCitaEntidad estatusCita) throws ExceptionDisparador {
        if (estatusCita.getCodigo() == null) {
            throw new ExceptionDisparador("2#Debe introducir el código que identifique al estatus");
        }
    }
    
    private void validarCodigoVacio(EstatusCitaEntidad estatusCita) throws ExceptionDisparador {
        if ("".equals(estatusCita.getCodigo().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el código que identifique al estatus");
        }
    }
    
    private void validarDescripcionNulo(EstatusCitaEntidad estatusCita) throws ExceptionDisparador {
        if (estatusCita.getDescripcion() == null) {
            throw new ExceptionDisparador("3#Debe introducir la descripcion que identifique al estatus");
        }
    }

    private void validarDescripcionVacio(EstatusCitaEntidad estatusCita) throws ExceptionDisparador {
        if ("".equals(estatusCita.getDescripcion().trim())) {
            throw new ExceptionDisparador("4#Debe introducir la descripcion nombre que identifique al estatus");
        }
    }
    
    private void validarEstadoNulo(EstatusCitaEntidad estatusCita) throws ExceptionDisparador {
        if (estatusCita.getEstatus() == null) {
            throw new ExceptionDisparador("5#Debe introducir el Estado que identifique al estatus");
        }
    }

}
