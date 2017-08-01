package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TramiteRolEntidad;

/**
 * TramiteRolDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TramiteRolEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class TramiteRolDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TramiteRolEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tramiteRol Objeto del modelo ontologico de Tramite Rol
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTramiteRol(TramiteRolEntidad tramiteRol)
            throws ExceptionDisparador {
        validarTramiteNulo(tramiteRol);
        validarRolNulo(tramiteRol);
    }

    private void validarTramiteNulo(TramiteRolEntidad tramiteRol)
            throws ExceptionDisparador {
        if (tramiteRol.getTramite() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el Tramite correspondiente al TramiteRol");
        }
    }

    private void validarRolNulo(TramiteRolEntidad tramiteRol) throws ExceptionDisparador {
        if (tramiteRol.getRol() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el Rol correspondiente al TramiteRol");
        }
    }
}
