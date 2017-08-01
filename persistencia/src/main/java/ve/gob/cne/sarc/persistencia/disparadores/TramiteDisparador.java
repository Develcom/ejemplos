package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;

/**
 * TramiteDisparador.java
 *
 * @descripcion FEsta clase se encarga de validar que los atributos de la clase TramiteEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class TramiteDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TramiteEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tramite Objeto del modelo ontologico de Tramite
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTramite(TramiteEntidad tramite) throws ExceptionDisparador {
        validarModuloNulo(tramite);
        validarNombreNulo(tramite);
        validarNombreVacio(tramite);
        validarFechaInicioNulo(tramite);
    }

    private void validarModuloNulo(TramiteEntidad tramite) throws ExceptionDisparador {
        if (tramite.getModulo() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el modulo correspondiente al trámite");
        }
    }

    private void validarNombreNulo(TramiteEntidad tramite) throws ExceptionDisparador {
        if (tramite.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique el trámite");
        }
    }

    private void validarNombreVacio(TramiteEntidad tramite) throws ExceptionDisparador {
        if ("".equals(tramite.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique el trámite");
        }
    }

    private void validarFechaInicioNulo(TramiteEntidad tramite) {
        if (tramite.getFechaInicio() == null) {
            tramite.setFechaInicio(new Date());
        }
    }
}
