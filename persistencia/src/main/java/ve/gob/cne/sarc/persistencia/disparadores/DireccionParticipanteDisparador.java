package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.DireccionParticipanteEntidad;

/**
 * DireccionParticipanteDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase DireccionParticipanteEntidad sean
 * correctos en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class DireccionParticipanteDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad DireccionParticipanteEntidad y llama a otros métodos
     * privados para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param direccionParticipante Objeto del modelo ontologico de Direccion Participante
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesDireccionParticipante(
            DireccionParticipanteEntidad direccionParticipante)
            throws ExceptionDisparador {
        validarParroquiaNulo(direccionParticipante);
        validarParticipanteNulo(direccionParticipante);
        validarDireccionUbicacionNulo(direccionParticipante);
        validarDireccionUbicacionVacio(direccionParticipante);
    }

    private void validarParroquiaNulo(
            DireccionParticipanteEntidad direccionParticipante)
            throws ExceptionDisparador {
        if (direccionParticipante.getParroquia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la parroquia correspondiente a la direcciónParticipante");
        }
    }

    private void validarParticipanteNulo(
            DireccionParticipanteEntidad direccionParticipante)
            throws ExceptionDisparador {
        if (direccionParticipante.getParticipante() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el participante correspondiente a la direcciónParticipante");
        }
    }

    private void validarDireccionUbicacionNulo(
            DireccionParticipanteEntidad direccionParticipante)
            throws ExceptionDisparador {
        if (direccionParticipante.getDireccionUbicacion() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor direcciónUbicación en la entidad participante");
        }
    }

    private void validarDireccionUbicacionVacio(
            DireccionParticipanteEntidad direccionParticipante)
            throws ExceptionDisparador {
        if ("".equals(direccionParticipante.getDireccionUbicacion().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor direcciónUbicación en la entidad participante");
        }
    }
}
