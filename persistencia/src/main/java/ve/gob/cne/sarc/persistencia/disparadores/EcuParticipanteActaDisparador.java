package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EcuParticipanteActaEntidad;

/**
 * EcuParticipanteActaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase EcuParticipanteActaEntidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 06/09/2016
 */
public class EcuParticipanteActaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad EcuParticipanteActaEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param ecuParticipanteActaEntidad objeto del modelo ontologico que tiene la informacion de Participante Acta
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesEcuParticipanteActaDisparador(
            EcuParticipanteActaEntidad ecuParticipanteActaEntidad) throws ExceptionDisparador {
        validarParticipanteNulo(ecuParticipanteActaEntidad);
        validarEcuNulo(ecuParticipanteActaEntidad);
        validarActaNulo(ecuParticipanteActaEntidad);
    }

    private void validarParticipanteNulo(EcuParticipanteActaEntidad ecuParticipanteActaEntidad)
            throws ExceptionDisparador {
        if (ecuParticipanteActaEntidad.getParticipante() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir un identificador para el participante");

        }
    }

    private void validarEcuNulo(EcuParticipanteActaEntidad ecuParticipanteActaEntidad)
            throws ExceptionDisparador {
        if (ecuParticipanteActaEntidad.getEcu() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir un identificador para el ecu");

        }
    }

    private void validarActaNulo(EcuParticipanteActaEntidad ecuParticipanteActaEntidad)
            throws ExceptionDisparador {
        if (ecuParticipanteActaEntidad.getActa() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir un identificador para el acta");

        }
    }
}
