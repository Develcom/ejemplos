package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EstadoEcuEntidad;

/**
 * EstadoEcuDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase EstadoEcuEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class EstadoEcuDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad CiudadanoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param eee Objeto del modelo ontologico de Tramite Tipo EstadoEcu
     * @throws ExceptionDisparador
     */
    @PrePersist
    @PreUpdate
    public void validacionesEstadoEcu(EstadoEcuEntidad eee) throws ExceptionDisparador {
        validarDescripcionNull(eee);
        validarDescripcionVacio(eee);
        validarFechaIncioNull(eee);
        validarFechaFinNull(eee);
        validarNombreEstadoEcuNull(eee);
        validarNombreEstadoEcuVacio(eee);
    }

    private void validarDescripcionNull(EstadoEcuEntidad eee) throws ExceptionDisparador {
        if (eee.getDescripcion() == null) {
            throw new ExceptionDisparador("2#Debe introducir la descripcion del estado del ECU");
        }
    }

    private void validarDescripcionVacio(EstadoEcuEntidad eee) throws ExceptionDisparador {
        if ("".equalsIgnoreCase(eee.getDescripcion())) {
            throw new ExceptionDisparador("2#Debe introducir la descripcion del estado del ECU");
        }
    }

    private void validarFechaIncioNull(EstadoEcuEntidad eee) throws ExceptionDisparador {
        if (eee.getFechaInicio() == null) {
            eee.setFechaInicio(new Date());
        }
    }

    private void validarFechaFinNull(EstadoEcuEntidad eee) throws ExceptionDisparador {
        if (eee.getFechaFin() == null) {
            eee.setFechaFin(new Date());
        }
    }

    private void validarNombreEstadoEcuNull(EstadoEcuEntidad eee) throws ExceptionDisparador {
        if (eee.getNombreEstadoEcu() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del estado del ECU");
        }
    }

    private void validarNombreEstadoEcuVacio(EstadoEcuEntidad eee) throws ExceptionDisparador {
        if ("".equalsIgnoreCase(eee.getNombreEstadoEcu())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del estado del ECU");
        }
    }

}
