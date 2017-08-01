package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TipoEstatusEntidad;

/**
 * TipoEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase EntePublicoEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class TipoEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad EntePublicoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tipoEstatus objeto del modelo ontologico de Tipo Estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesEstatus(TipoEstatusEntidad tipoEstatus) throws ExceptionDisparador {
        validarNombreNulo(tipoEstatus);
        validarNombreVacio(tipoEstatus);
        validarFechaInicioNulo(tipoEstatus);
    }

    private void validarNombreNulo(TipoEstatusEntidad tipoEstatus) throws ExceptionDisparador {
        if (tipoEstatus.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre que identifique al tipo de estatus");
        }
    }

    private void validarNombreVacio(TipoEstatusEntidad tipoEstatus) throws ExceptionDisparador {
        if ("".equals(tipoEstatus.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre que identifique al tipo de estatus");
        }
    }

    private void validarFechaInicioNulo(TipoEstatusEntidad tipoEstatus) throws ExceptionDisparador {
        if (tipoEstatus.getFechaInicio() == null) {
            throw new ExceptionDisparador("2#Debe introducir la fecha de inicio de vigencia");
        }
    }
}
