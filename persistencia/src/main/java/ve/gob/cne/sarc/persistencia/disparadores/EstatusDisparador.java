package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EstatusEntidad;

/**
 * EstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase EntePublicoEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.1
 * @author Oscar Montilla
 *
 */
public class EstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad EntePublicoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param estatus objeto del modelo ontologico de estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesEstatus(EstatusEntidad estatus) throws ExceptionDisparador {
        validarNombreNulo(estatus);
        validarNombreVacio(estatus);
        validarFechaInicioNulo(estatus);
        validarTipoEstatusNulo(estatus);
    }

    private void validarNombreNulo(EstatusEntidad estatus) throws ExceptionDisparador {
        if (estatus.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre que identifique al estatus");
        }
    }

    private void validarNombreVacio(EstatusEntidad estatus) throws ExceptionDisparador {
        if ("".equals(estatus.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre que identifique al estatus");
        }
    }

    private void validarFechaInicioNulo(EstatusEntidad estatus) throws ExceptionDisparador {
        if (estatus.getFechaInicio() == null) {
            throw new ExceptionDisparador("2#Debe indicar la fecha de inicio");
        }
    }

    private void validarTipoEstatusNulo(EstatusEntidad estatus) throws ExceptionDisparador {
        if (estatus.getTipoEstatus() == null) {
            throw new ExceptionDisparador("2#Debe indicar el tipo de estatus asociado");
        }
    }
}
