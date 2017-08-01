package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ActaEstatusEntidad;

/**
 * ActaEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitudEstatus sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class ActaEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitudEstatus y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param actaEstatus Objeto del modelo ontologico de Acta estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesActaEstatus(ActaEstatusEntidad actaEstatus) throws ExceptionDisparador {
        validarFechaInicioNulo(actaEstatus);
        validarNombreNulo(actaEstatus);
        validarNombreVacio(actaEstatus);
    }

    private void validarFechaInicioNulo(ActaEstatusEntidad actaEstatus) {
        if (actaEstatus.getFechaInicio() == null) {
            actaEstatus.setFechaInicio(new Date());
        }
    }

    private void validarNombreNulo(ActaEstatusEntidad actaEstatus) throws ExceptionDisparador {
        if (actaEstatus.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir un nombre válido para el estatus");
        }
    }

    private void validarNombreVacio(ActaEstatusEntidad actaEstatus) throws ExceptionDisparador {
        if ("".equals(actaEstatus.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir un nombre válido para el estatus");
        }
    }
}
