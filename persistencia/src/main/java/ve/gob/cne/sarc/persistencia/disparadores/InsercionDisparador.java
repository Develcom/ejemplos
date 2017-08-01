package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.InsercionEntidad;

/**
 * InsercionDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase InsercionEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class InsercionDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad Insercion18Entidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param insercion Objeto del modelo ontologico que contiene la informacion de insercion
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesInsercion(InsercionEntidad insercion)
            throws ExceptionDisparador {
        validarParroquiaNulo(insercion);
        validarNumeroActaCero(insercion);
        validarPrimerNombreAutoridadNulo(insercion);
        validarPrimerNombreAutoridadVacio(insercion);
        validarPrimerApellidoAutoridadNulo(insercion);
        validarPrimerApellidoAutoridadVacio(insercion);
        validarFechaInsercionNulo(insercion);
    }

    private void validarParroquiaNulo(InsercionEntidad insercion)
            throws ExceptionDisparador {
        if (insercion.getParroquia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la parroquia correspondiente a la inserción");
        }
    }

    private void validarNumeroActaCero(InsercionEntidad insercion) throws ExceptionDisparador {
        if (insercion.getActa() == null) {
            throw new ExceptionDisparador("2#Debe indicar el número de acta");
        }
    }

    private void validarPrimerNombreAutoridadNulo(InsercionEntidad insercion)
            throws ExceptionDisparador {
        if (insercion.getPrimerNombreAutoridad() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del Autoridad");
        }
    }

    private void validarPrimerNombreAutoridadVacio(InsercionEntidad insercion)
            throws ExceptionDisparador {
        if ("".equals(insercion.getPrimerNombreAutoridad().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del Autoridad");
        }
    }

    private void validarPrimerApellidoAutoridadNulo(InsercionEntidad insercion)
            throws ExceptionDisparador {

        if (insercion.getPrimerApellidoAutoridad() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del Autoridad");
        }
    }

    private void validarPrimerApellidoAutoridadVacio(InsercionEntidad insercion)
            throws ExceptionDisparador {
        if ("".equals(insercion.getPrimerApellidoAutoridad().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del Autoridad");
        }
    }

    private void validarFechaInsercionNulo(InsercionEntidad insercion) {
        if (insercion.getFechaInsercion() == null) {
            insercion.setFechaInsercion(new Date());
        }
    }
}
