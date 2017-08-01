package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.TramiteRolEstatusEntidad;

/**
 * TramiteRolEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitudEstatus sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class TramiteRolEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitudEstatus y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tramiteRolEstatus Objeto de modelo ontologico de Tramite Rol Estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTramiteRolEstatus(TramiteRolEstatusEntidad tramiteRolEstatus)
            throws ExceptionDisparador {
        validarNombreNulo(tramiteRolEstatus);
        validarNombreVacio(tramiteRolEstatus);
        validarFechaInicioNulo(tramiteRolEstatus);
    }

    private void validarFechaInicioNulo(TramiteRolEstatusEntidad tramiteRolEstatus) {
        if (tramiteRolEstatus.getFechaInicio() == null) {
            tramiteRolEstatus.setFechaInicio(new Date());
        }
    }

    private void validarNombreNulo(TramiteRolEstatusEntidad tramiteRolEstatus) throws ExceptionDisparador {
        if (tramiteRolEstatus.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir un nombre válido para el registro");
        }
    }

    private void validarNombreVacio(TramiteRolEstatusEntidad tramiteRolEstatus) throws ExceptionDisparador {
        if ("".equals(tramiteRolEstatus.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir un nombre válido para el registro");
        }
    }
}
