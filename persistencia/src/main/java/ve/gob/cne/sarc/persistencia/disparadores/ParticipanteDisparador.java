package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;

/**
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ParticipanteEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class ParticipanteDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad ParticipanteEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param participante - objeto del modelo ontologico de participante
     * @throws ExceptionDisparador Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesParticipante(ParticipanteEntidad participante)
            throws ExceptionDisparador {
        validarActaNulo(participante);
        validarPrimerApellidoNulo(participante);
        validarSegundoApellidoNulo(participante);
        validarPrimerNombreNulo(participante);
        validarTipoDocumentoNulo(participante);
        validarFechaNacimientoNulo(participante);

    }

    private void validarActaNulo(ParticipanteEntidad participante)
            throws ExceptionDisparador {
        if (participante.getActa() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el acta correspondiente al participante");
        }
    }

    private void validarPrimerApellidoNulo(ParticipanteEntidad participante)
            throws ExceptionDisparador {
        if (participante.getPrimerApellido() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el primer apellido del participante");
        }
    }

    private void validarSegundoApellidoNulo(ParticipanteEntidad participante)
            throws ExceptionDisparador {
        if (participante.getSegundoApellido() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el segundo apellido del participante");
        }
    }

    private void validarPrimerNombreNulo(ParticipanteEntidad participante)
            throws ExceptionDisparador {
        if (participante.getPrimerNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el primer nombre del participante");
        }
    }

    private void validarTipoDocumentoNulo(ParticipanteEntidad participante)
            throws ExceptionDisparador {
        if (participante.getTipoDocumento() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir alguna de las siguientes letras para el tipo de documento: V o E o P");
        }
    }

    private void validarFechaNacimientoNulo(ParticipanteEntidad participante) {
        if (participante.getFechaNacimiento() == null) {
            participante.setFechaNacimiento(new Date());
        }
    }
}
