package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ParticipanteSinDocEntidad;

/**
 * ParticipanteSinDocDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitudEstatus sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class ParticipanteSinDocDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitudEstatus y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param participanteSinDoc objeto del modelo ontologico de Participante sin documento
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesParticipanteSinDoc(ParticipanteSinDocEntidad participanteSinDoc)
            throws ExceptionDisparador {

        validarNombreNulo(participanteSinDoc);
        validarNombreVacio(participanteSinDoc);
        validarFechaInicioNulo(participanteSinDoc);
        validarTramite(participanteSinDoc);
    }

    private void validarFechaInicioNulo(ParticipanteSinDocEntidad participanteSinDoc) {
        if (participanteSinDoc.getFechaInicio() == null) {
            participanteSinDoc.setFechaInicio(new Date());
        }
    }

    private void validarTramite(ParticipanteSinDocEntidad participanteSinDoc) throws ExceptionDisparador {
        if (participanteSinDoc.getTramite() == null) {
            throw new ExceptionDisparador("2#El participante debe estar asociado a un trámite");
        }
    }

    private void validarNombreNulo(ParticipanteSinDocEntidad participanteSinDoc) throws ExceptionDisparador {
        if (participanteSinDoc.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir un nombre válido");
        }
    }

    private void validarNombreVacio(ParticipanteSinDocEntidad participanteSinDoc) throws ExceptionDisparador {
        if ("".equals(participanteSinDoc.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir un nombre válido");
        }
    }
}
