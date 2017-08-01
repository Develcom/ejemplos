package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TramiteTipoParticipanteEntidad;

/**
 * TramiteTipoParticipanteDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TramiteTipoParticipanteEntidad sean
 * correctos en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
public class TramiteTipoParticipanteDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TramiteTipoParticipanteEntidad y llama a otros métodos
     * privados para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tramiteTipoParticipante Objeto del modelo ontologico de Tramite Tipo Participante
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTramiteTipoParticipante(
            TramiteTipoParticipanteEntidad tramiteTipoParticipante)
            throws ExceptionDisparador {
        validarTramiteNulo(tramiteTipoParticipante);
        validarTipoParticipanteNulo(tramiteTipoParticipante);
        validarFechaInicioNulo(tramiteTipoParticipante);
    }

    private void validarTramiteNulo(
            TramiteTipoParticipanteEntidad tramiteTipoParticipante)
            throws ExceptionDisparador {
        if (tramiteTipoParticipante.getTramite() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el trámite correspondiente al tramiteTipoParticipante");
        }
    }

    private void validarTipoParticipanteNulo(
            TramiteTipoParticipanteEntidad tramiteTipoParticipante)
            throws ExceptionDisparador {
        if (tramiteTipoParticipante.getTipoParticipante() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el tipoParticipante correspondiente al tramiteTipoParticipante");
        }
    }

    private void validarFechaInicioNulo(
            TramiteTipoParticipanteEntidad tramiteTipoParticipante) {
        if (tramiteTipoParticipante.getFechaInicio() == null) {
            tramiteTipoParticipante.setFechaInicio(new Date());
        }
    }

}
