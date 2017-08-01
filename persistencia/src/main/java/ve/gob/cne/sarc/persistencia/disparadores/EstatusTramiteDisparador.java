package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EstatusTramiteEntidad;

/**
 * EstatusTramiteDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase EntePublicoEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.1
 * @author Oscar Montilla
 *
 */
public class EstatusTramiteDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad EntePublicoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     *
     *
     * @param estatusTramite objeto del modelo ontologico de Estatus tramite
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesEstatus(EstatusTramiteEntidad estatusTramite) throws ExceptionDisparador {
        validarEstatusNulo(estatusTramite);
        validarTramiteNulo(estatusTramite);
    }

    private void validarEstatusNulo(EstatusTramiteEntidad estatusTramite) throws ExceptionDisparador {
        if (estatusTramite.getEstatus() == null) {
            throw new ExceptionDisparador("2#Debe indicar el estatus asociado");
        }
    }

    private void validarTramiteNulo(EstatusTramiteEntidad estatusTramite) throws ExceptionDisparador {
        if (estatusTramite.getTramite() == null) {
            throw new ExceptionDisparador("2#Debe indicar el trámite asociado");
        }
    }
}
