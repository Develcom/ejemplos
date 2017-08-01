package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;

/**
 * EstadoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase EstadoEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class EstadoDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad EstadoEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param estado objeto del modelo ontologico de estado
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesEstado(EstadoEntidad estado) throws ExceptionDisparador {
        validarPaisNulo(estado);
        validarNombreNulo(estado);
        validarNombreVacio(estado);
        validarFechaInicioNulo(estado);
    }

    private void validarPaisNulo(EstadoEntidad estado) throws ExceptionDisparador {
        if (estado.getPais() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el país correspondiente al estado");
        }
    }

    private void validarNombreNulo(EstadoEntidad estado) throws ExceptionDisparador {
        if (estado.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al estado");
        }
    }

    private void validarNombreVacio(EstadoEntidad estado) throws ExceptionDisparador {
        if ("".equals(estado.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al estado");
        }
    }

    private void validarFechaInicioNulo(EstadoEntidad estado) {
        if (estado.getFechaInicio() == null) {
            estado.setFechaInicio(new Date());
        }
    }
}
