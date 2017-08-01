package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.RecaudoEntidad;

/**
 * RecaudoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase RecaudoEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class RecaudoDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad RecaudoEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param recaudo objeto del modelo ontologico de Recaudo
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesRecaudo(RecaudoEntidad recaudo) throws ExceptionDisparador {

        validarNombreNulo(recaudo);
        validarNombreVacio(recaudo);
        validarFechaInicioNulo(recaudo);
        validarEstatusNulo(recaudo);
        validarEstatusVacio(recaudo);
        validarTipoNulo(recaudo);
        validarTipoVacio(recaudo);
    }

    private void validarNombreNulo(RecaudoEntidad recaudo) throws ExceptionDisparador {
        if (recaudo.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al recaudo");
        }
    }

    private void validarNombreVacio(RecaudoEntidad recaudo) throws ExceptionDisparador {
        if ("".equals(recaudo.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al recaudo");
        }
    }

    private void validarFechaInicioNulo(RecaudoEntidad recaudo) {
        if (recaudo.getFechaInicio() == null) {
            recaudo.setFechaInicio(new Date());
        }
    }

    private void validarEstatusNulo(RecaudoEntidad recaudo) throws ExceptionDisparador {
        if (recaudo.getEstatus() == null) {
            throw new ExceptionDisparador("2#Debe indicar el estatus del recaudo");
        }
    }

    private void validarEstatusVacio(RecaudoEntidad recaudo) throws ExceptionDisparador {
        if ("".equals(recaudo.getEstatus().trim())) {
            throw new ExceptionDisparador("2#Debe indicar el estatus del recaudo");
        }
    }

    private void validarTipoNulo(RecaudoEntidad recaudo) throws ExceptionDisparador {
        if (recaudo.getTipo() == null) {
            throw new ExceptionDisparador("2#Debe indicar el tipo del recaudo");
        }
    }

    private void validarTipoVacio(RecaudoEntidad recaudo) throws ExceptionDisparador {
        if ("".equals(recaudo.getTipo())) {
            throw new ExceptionDisparador("Debe indicar el tipo del recaudo");
        }
    }
}
