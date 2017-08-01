package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEstatusEntidad;

/**
 * SolicitudEstatusDisparador.java
 *
 * @descripcin Esta clase se encarga de validar que los atributos de la clase SolicitudEstatus sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class SolicitudEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitudEstatus y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param solicitud objeto del modelo ontologico de Solicitud
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesSolicitud(SolicitudEstatusEntidad solicitud) throws ExceptionDisparador {

        validarFechaInicioNulo(solicitud);
        validarNombreEstatusNulo(solicitud);
        validarNombreEstatusVacio(solicitud);
    }

    private void validarFechaInicioNulo(SolicitudEstatusEntidad solicitud) {
        if (solicitud.getFechaInicio() == null) {
            solicitud.setFechaInicio(new Date());
        }
    }

    private void validarNombreEstatusNulo(SolicitudEstatusEntidad solicitud) throws ExceptionDisparador {
        if (solicitud.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del estatus");
        }
    }

    private void validarNombreEstatusVacio(SolicitudEstatusEntidad solicitud) throws ExceptionDisparador {
        if ("".equals(solicitud.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del estatus");
        }
    }
}
