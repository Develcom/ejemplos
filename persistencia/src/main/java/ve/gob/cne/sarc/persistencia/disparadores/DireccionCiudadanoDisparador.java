package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.DireccionCiudadanoEntidad;

/**
 * DireccionCiudadanoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase DireccionCiudadanoEntidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
public class DireccionCiudadanoDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad DireccionCiudadanoEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param direccionCiudadano objeto del modelo ontologico de Direcion Ciudadano
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesDireccionCiudadano(
            DireccionCiudadanoEntidad direccionCiudadano) throws ExceptionDisparador {
        validarCiudadNulo(direccionCiudadano);
        validarParroquiaNulo(direccionCiudadano);
        validarCiudadanoNulo(direccionCiudadano);
        validarTipoNulo(direccionCiudadano);
        validarTipoVacio(direccionCiudadano);
        validarEstatusNulo(direccionCiudadano);
        validarEstatusVacio(direccionCiudadano);
        validarDiDetalleNulo(direccionCiudadano);
        validarDiDetalleVacio(direccionCiudadano);
    }

    private void validarCiudadNulo(DireccionCiudadanoEntidad direccionCiudadano)
            throws ExceptionDisparador {
        if (direccionCiudadano.getCiudad() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la ciudad correspondiente a la direccionCiudadano");
        }
    }

    private void validarParroquiaNulo(
            DireccionCiudadanoEntidad direccionCiudadano) throws ExceptionDisparador {
        if (direccionCiudadano.getParroquia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la parroquia correspondiente a la direccionCiudadano");
        }
    }

    private void validarCiudadanoNulo(
            DireccionCiudadanoEntidad direccionCiudadano) throws ExceptionDisparador {
        if (direccionCiudadano.getParroquia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el ciudadano correspondiente a la direccionCiudadano");
        }
    }

    private void validarTipoNulo(DireccionCiudadanoEntidad direccionCiudadano)
            throws ExceptionDisparador {
        if (direccionCiudadano.getTipo() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el tipo correspondiente a la direccionCiudadano");
        }
    }

    private void validarTipoVacio(DireccionCiudadanoEntidad direccionCiudadano)
            throws ExceptionDisparador {
        if ("".equals(direccionCiudadano.getTipo().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el tipo  correspondiente a la direccionCiudadano");
        }
    }

    private void validarEstatusNulo(DireccionCiudadanoEntidad direccionCiudadano)
            throws ExceptionDisparador {
        if (direccionCiudadano.getEstatus() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el estatus correspondiente a la direccionCiudadano");
        }
    }

    private void validarEstatusVacio(
            DireccionCiudadanoEntidad direccionCiudadano) throws ExceptionDisparador {
        if ("".equals(direccionCiudadano.getEstatus().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el estatus correspondiente a la direccionCiudadano");
        }
    }

    private void validarDiDetalleNulo(DireccionCiudadanoEntidad direccionCiudadano) throws ExceptionDisparador {
        if (direccionCiudadano.getDetalle() == null) {
            throw new ExceptionDisparador("2#Debe introducir la dirección detallada del ciudadano");
        }
    }

    private void validarDiDetalleVacio(DireccionCiudadanoEntidad direccionCiudadano) throws ExceptionDisparador {
        if ("".equals(direccionCiudadano.getDetalle().trim())) {
            throw new ExceptionDisparador("2#Debe introducir la dirección detallada del ciudadano");
        }
    }
}
