package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.OficinaCitaDetalleEntidad;

/**
 * OficinaCitaDetalleDisparador.java
 * @descripcion Clase que valida que no existan campos nulos en Oficina Cita Detalle
 * @version 
 * 13 de oct. de 2016
 * @author Domingo Rondon
 */
public class OficinaCitaDetalleDisparador {
    
    /**
     * validacionesOficinaCitaDetalle
     * Metodo que contiene la validacion de Campos Nulos
     * @since 13 de oct. de 2016
     * @param oficinaCitaDetalle
     * @throws ExceptionDisparador
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-312
     */
    @PrePersist
    @PreUpdate
    public void validacionesOficinaCitaDetalle(OficinaCitaDetalleEntidad oficinaCitaDetalle) throws ExceptionDisparador {
        validarFechaCelebracionNulo(oficinaCitaDetalle);
        validarCantidadCitasDispoiniblesNulo(oficinaCitaDetalle);
    }
    
    private void validarFechaCelebracionNulo(OficinaCitaDetalleEntidad oficinaCitaDetalle) {
        if (oficinaCitaDetalle.getFechaCelebracion() == null) {
            oficinaCitaDetalle.setFechaCelebracion(new Date());
        }
    }
    
    private void validarCantidadCitasDispoiniblesNulo(OficinaCitaDetalleEntidad oficinaCitaDetalle) throws ExceptionDisparador {
        if (oficinaCitaDetalle.getCantidadCitasDisponibles()== null) {
            throw new ExceptionDisparador("3#Debe introducir la cantidad de Citas Disponibles para esa Oficina");
        }
    }

}
