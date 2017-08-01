package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.LibroEstatusEntidad;

/**
 * LibroEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitanteEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 */
public class LibroEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitanteEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param libroEstatusEntidad objeto del modelo ontologico de Libro Estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesLibroEstatus(LibroEstatusEntidad libroEstatusEntidad) throws ExceptionDisparador {
        validarDescripccionNulo(libroEstatusEntidad);
        validarFechaInicio(libroEstatusEntidad);
        validarNombreNulo(libroEstatusEntidad);
        validarNombreVacio(libroEstatusEntidad);
    }

    private void validarDescripccionNulo(LibroEstatusEntidad libroEstatusEntidad) throws ExceptionDisparador {
        if (libroEstatusEntidad.getDescripcion() == null) {
            throw new ExceptionDisparador("2#Debe introducir la descripcion correspondiente.");

        }
    }

    private void validarFechaInicio(LibroEstatusEntidad libroEstatusEntidad) throws ExceptionDisparador {
        if (libroEstatusEntidad.getFechaInicio() == null) {
            libroEstatusEntidad.setFechaInicio(new Date());
        }
    }

    private void validarNombreNulo(LibroEstatusEntidad libroEstatusEntidad) throws ExceptionDisparador {
        if (libroEstatusEntidad.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe asignar un nombre válido al estatus");
        }
    }

    private void validarNombreVacio(LibroEstatusEntidad libroEstatusEntidad) throws ExceptionDisparador {
        if ("".equals(libroEstatusEntidad.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe asignar un nombre válido al estatus");
        }
    }
}
