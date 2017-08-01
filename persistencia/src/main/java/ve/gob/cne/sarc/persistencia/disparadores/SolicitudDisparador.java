package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.joda.time.DateTime;

import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * SolicitudDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitudEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class SolicitudDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitudEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param solicitud Objeto del modelo ontologico que contiene la inforamcion de Solicitud
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesSolicitud(SolicitudEntidad solicitud) throws ExceptionDisparador {
        validarTramiteNulo(solicitud);
        validarFechaInicioNulo(solicitud);
        validarSolicitudEstatusNulo(solicitud);
        validarEstatusVacio(solicitud);
        validarOficinaFuncionarioNulo(solicitud);
        validarStatusTramiteNulo(solicitud);
        validarNumeroNulo(solicitud);
        validarNumeroVacio(solicitud);

    }

    private void validarTramiteNulo(SolicitudEntidad solicitud)
            throws ExceptionDisparador {
        if (solicitud.getTramite() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el tramite correspondiente a la solicitud");
        }
    }

    private void validarSolicitudEstatusNulo(SolicitudEntidad solicitud) throws ExceptionDisparador {
        if (solicitud.getEstatus() == null) {
            throw new ExceptionDisparador("2#Debe introducir el estatus correspondiente a la solicitud");
        }
    }

    private void validarEstatusVacio(SolicitudEntidad solicitud) throws ExceptionDisparador {
        if ("".equals(solicitud.getEstatus().getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el estatus correspondiente a la solicitud");
        }
    }

    private void validarOficinaFuncionarioNulo(SolicitudEntidad solicitud)
            throws ExceptionDisparador {
        if (solicitud.getOficinaFuncionario() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la oficina del funcionario correspondiente a la solicitud");
        }
    }

    private void validarStatusTramiteNulo(SolicitudEntidad solicitud)
            throws ExceptionDisparador {
        if (solicitud.getEstatusTramite() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el estatus del tramite");
        }
    }

    private void validarFechaInicioNulo(SolicitudEntidad solicitud) {
        if (solicitud.getFechaInicio() == null) {
            solicitud.setFechaInicio(new DateTime());
        }
    }

    private void validarNumeroNulo(SolicitudEntidad solicitud) throws ExceptionDisparador {
        if (solicitud.getNumero() == null) {
            throw new ExceptionDisparador("2#Debe introducir un número de solicitud válido");
        }
    }

    private void validarNumeroVacio(SolicitudEntidad solicitud) throws ExceptionDisparador {
        if ("".equals(solicitud.getNumero().trim())) {
            throw new ExceptionDisparador("2#Debe introducir un número de solicitud válido");
        }
    }
}
