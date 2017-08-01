package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.CiudadEntidad;

/**
 * CiudadDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase CiudadEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class CiudadDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad CiudadEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param ciudad Objeto del modelo ontologico de ciudad
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesCiudad(CiudadEntidad ciudad) throws ExceptionDisparador {
        validarPaisNulo(ciudad);
        validarNombreNulo(ciudad);
        validarNombreVacio(ciudad);
        validarFechaInicioNulo(ciudad);
    }

    private void validarPaisNulo(CiudadEntidad ciudad) throws ExceptionDisparador {
        if (ciudad.getPais() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el pais correspondiente a la ciudad");
        }
    }

    private void validarNombreNulo(CiudadEntidad ciudad) throws ExceptionDisparador {
        if (ciudad.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique a la ciudad");
        }
    }

    private void validarNombreVacio(CiudadEntidad ciudad) throws ExceptionDisparador {
        if ("".equals(ciudad.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique a la ciudad");
        }
    }

    private void validarFechaInicioNulo(CiudadEntidad ciudad) {
        if (ciudad.getFechaInicio() == null) {
            ciudad.setFechaInicio(new Date());
        }
    }
}
