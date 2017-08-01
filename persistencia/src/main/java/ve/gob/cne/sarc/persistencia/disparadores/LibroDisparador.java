package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.LibroEntidad;

/**
 * LibroDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase LibroEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Anabell De Faria
 *
 */
public class LibroDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad LibroEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico
     * @param libro Objeto del modelo ontologico que tiene la informacion de Libro
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesLibro(LibroEntidad libro) throws ExceptionDisparador {
        validarOficinaNulo(libro);
        validarFuncionarioAperturaNulo(libro);
        validarTipoLibroNulo(libro);
        validarEstatusNulo(libro);
        validarEstatusVacio(libro);
        validarNumeroAnioCero(libro);
        validarFechaCreacionNulo(libro);
        validarFechaActualizacionNulo(libro);
    }

    private void validarOficinaNulo(LibroEntidad libro) throws ExceptionDisparador {
        if (libro.getOficina() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la oficina correspondiente al libro");
        }
    }

    private void validarFuncionarioAperturaNulo(LibroEntidad libro) throws ExceptionDisparador {
        if (libro.getFuncionarioApertura() == null) {
            throw new ExceptionDisparador("2#Debe introducir el funcionarioApertura correspondiente al libro");
        }
    }

    private void validarTipoLibroNulo(LibroEntidad libro) throws ExceptionDisparador {
        if (libro.getTipoLibro() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el TipoLibroTipoOficina correspondiente al libro");
        }
    }

    private void validarEstatusNulo(LibroEntidad libro) throws ExceptionDisparador {
        if (libro.getEstatus() == null) {
            throw new ExceptionDisparador("2#Debe introducir el estatus correspondiente al libro");
        }
    }

    private void validarEstatusVacio(LibroEntidad libro) throws ExceptionDisparador {
        if ("".equals(libro.getEstatus().getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el estatus correspondiente al libro");
        }
    }

    private void validarNumeroAnioCero(LibroEntidad libro) throws ExceptionDisparador {
        if (libro.getNumeroAnio() <= 0) {
            throw new ExceptionDisparador("2#Debe introducir un número de año válido");
        }
    }

    private void validarFechaCreacionNulo(LibroEntidad libro) {
        if (libro.getFechaCreacion() == null) {
            libro.setFechaCreacion(new Date());
        }
    }

    private void validarFechaActualizacionNulo(LibroEntidad libro) {
        if (libro.getFechaActualizacion() == null) {
            libro.setFechaActualizacion(new Date());
        }
    }
}
