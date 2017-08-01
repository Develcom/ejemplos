package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.PoderMandatorioEntidad;

/**
 * PoderMandatorioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ActaEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class PoderMandatorioDisparador {

    /**
     *
     * Este método recibe como parámetro la entidad PoderMandatorioEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     *
     *
     * @param poderMandatorio Objeto del modelo ontologico que contiene la informacion de Poder Mandatorio
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesPoderMandatorio(PoderMandatorioEntidad poderMandatorio) throws ExceptionDisparador {
        validarActaNulo(poderMandatorio);
        validarNotariaNulo(poderMandatorio);
        validarNotariaVacio(poderMandatorio);
    }

    private void validarActaNulo(PoderMandatorioEntidad poderMandatorio)
            throws ExceptionDisparador {
        if (poderMandatorio.getActa() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el acta correspondiente al poder mandatorio");
        }
    }

    private void validarNotariaNulo(PoderMandatorioEntidad poderMandatorio) throws ExceptionDisparador {
        if (poderMandatorio.getNotaria() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre "
                    + "de la notaría correspondiente al poder mandatorio");
        }
    }

    private void validarNotariaVacio(PoderMandatorioEntidad poderMandatorio) throws ExceptionDisparador {
        if ("".equals(poderMandatorio.getNotaria().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre "
                    + "de la notaría correspondiente al poder mandatorio");
        }
    }
}
