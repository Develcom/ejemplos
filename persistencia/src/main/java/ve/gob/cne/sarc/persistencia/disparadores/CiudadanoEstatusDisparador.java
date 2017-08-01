package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEstatusEntidad;

/**
 * CiudadanoEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitudEstatus sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class CiudadanoEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitudEstatus y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param ciudadanoEstatus objeto del modelo ontologico de Ciudadano Estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesCiudadanoEstatus(CiudadanoEstatusEntidad ciudadanoEstatus)
            throws ExceptionDisparador {

        validarFechaInicioNulo(ciudadanoEstatus);
        validarNombreNulo(ciudadanoEstatus);
        validarNombreVacio(ciudadanoEstatus);
    }

    private void validarFechaInicioNulo(CiudadanoEstatusEntidad ciudadanoEstatus) {
        if (ciudadanoEstatus.getFechaInicio() == null) {
            ciudadanoEstatus.setFechaInicio(new Date());
        }
    }

    private void validarNombreNulo(CiudadanoEstatusEntidad ciudadanoEstatus) throws ExceptionDisparador {
        if (ciudadanoEstatus.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir un nombre válido para el estatus");
        }
    }

    private void validarNombreVacio(CiudadanoEstatusEntidad ciudadanoEstatus) throws ExceptionDisparador {
        if ("".equals(ciudadanoEstatus.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir un nombre válido para el estatus");
        }
    }
}
