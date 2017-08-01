package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.OficinaCitaEntidad;

/**
 * OficinaCitaDisparador.java
 * @descripcion Clase que valida los Campos Nulos
 * @version 
 * 13 de oct. de 2016
 * @author Domingo Rondon
 */
public class OficinaCitaDisparador {
    
    /**
     * validacionesOficinaCita
     * Metodo que Contiene la validacion de todos los campos
     * @since 13 de oct. de 2016
     * @param oficinaCita
     * @throws ExceptionDisparador
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-312
     */
    @PrePersist
    @PreUpdate
    public void validacionesOficinaCita(OficinaCitaEntidad oficinaCita) throws ExceptionDisparador {
        validarOficinaNulo(oficinaCita);
        validarCantidadFuncionarioNulo(oficinaCita);
        validarCantidadCitasPermitidasNulo(oficinaCita);
        validarDiasHabilesNulo(oficinaCita);
        validarDiasHabilesVacio(oficinaCita);
        validarPeriodicidadNulo(oficinaCita);
        validarPeriodicidadVacio(oficinaCita);
    }
    
    private void validarOficinaNulo(OficinaCitaEntidad oficinaCita) throws ExceptionDisparador {
        if (oficinaCita.getOficina() == null) {
            throw new ExceptionDisparador("2#Debe introducir el código que identifique a la oficina");
        }
    }
    
    private void validarCantidadFuncionarioNulo(OficinaCitaEntidad oficinaCita) throws ExceptionDisparador {
        if (oficinaCita.getCantidadFuncionario()== null) {
            throw new ExceptionDisparador("3#Debe introducir la cantidad de Funcionarios de la Oficina");
        }
    }
    
    private void validarCantidadCitasPermitidasNulo(OficinaCitaEntidad oficinaCita) throws ExceptionDisparador {
        if (oficinaCita.getCantidadCitasPermitidas()== null) {
            throw new ExceptionDisparador("4#Debe introducir la cantidad de Citas Permitidas para esa Oficina");
        }
    }
    
    private void validarDiasHabilesNulo(OficinaCitaEntidad oficinaCita) throws ExceptionDisparador {
        if (oficinaCita.getDiasHabilesDisponibles() == null) {
            throw new ExceptionDisparador("6#Debe introducir los Dias Habiles Disponibles");
        }
    }

    private void validarDiasHabilesVacio(OficinaCitaEntidad oficinaCita) throws ExceptionDisparador {
        if ("".equals(oficinaCita.getDiasHabilesDisponibles().trim())) {
            throw new ExceptionDisparador("6#Debe introducir los Dias Habiles Disponibles");
        }
    }
    
    private void validarPeriodicidadNulo(OficinaCitaEntidad oficinaCita) throws ExceptionDisparador {
        if (oficinaCita.getPeriodicidad() == null) {
            throw new ExceptionDisparador("7#Debe introducir la Periodicidad");
        }
    }

    private void validarPeriodicidadVacio(OficinaCitaEntidad oficinaCita) throws ExceptionDisparador {
        if ("".equals(oficinaCita.getPeriodicidad().trim())) {
            throw new ExceptionDisparador("7#Debe introducir la Periodicidad");
        }
    }
    
    

}
