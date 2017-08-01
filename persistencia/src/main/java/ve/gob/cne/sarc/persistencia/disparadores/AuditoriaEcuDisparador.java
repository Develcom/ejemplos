package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.AuditoriaEcuEntidad;

/**
 * AuditoriaEcuDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase AuditoriaEcuEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.0 06/09/2016
 * @author Anabell De Faria
 */
public class AuditoriaEcuDisparador {

    /**
     *
     * @descripcion Este método recibe como parámetro la entidad AuditoriaEcuEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param aee Objeto del modelo ontologico de Auditoria
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesAuditoriaEcu(AuditoriaEcuEntidad aee) throws ExceptionDisparador {
        validarFechaEstadoNull(aee);
        validarFechaRegistroNull(aee);
        validarEcuNull(aee);
    }

    private void validarFechaEstadoNull(AuditoriaEcuEntidad aee) throws ExceptionDisparador {
        if (aee.getFechaEstadoEcu() == null) {
            aee.setFechaEstadoEcu(new Date());
        }
    }

    private void validarFechaRegistroNull(AuditoriaEcuEntidad aee) throws ExceptionDisparador {
        if (aee.getFechaRegistro() == null) {
            aee.setFechaRegistro(new Date());
        }
    }

    private void validarEcuNull(AuditoriaEcuEntidad aee) throws ExceptionDisparador {
        if (aee.getEcu() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el identificador del ECU");
        }
    }

}
