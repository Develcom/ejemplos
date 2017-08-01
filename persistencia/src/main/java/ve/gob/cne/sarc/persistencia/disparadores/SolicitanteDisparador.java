package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * SolicitanteDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitanteEntidad sean correctos en
 * cuanto a obligatoriedad y formato
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class SolicitanteDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitanteEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param solicitante Objeto del modelo ontologico que contiene la informacio de Solicitante
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesSolicitante(SolicitanteEntidad solicitante) throws ExceptionDisparador {
        validarSolicitudNulo(solicitante);
        validarSolicitanteTipoNulo(solicitante);

    }

    private void validarSolicitanteTipoNulo(SolicitanteEntidad solicitante) throws ExceptionDisparador {
        if (solicitante.getSolicitanteTipo() == null) {
            throw new ExceptionDisparador("2#Debe introducir el solicitante tipo correspondiente al solicitante");
        }

    }

    private void validarSolicitudNulo(SolicitanteEntidad solicitante) {
        if (solicitante.getSolicitud() == null) {
            solicitante.setSolicitud(new ArrayList<SolicitudEntidad>());
        }
    }

}
