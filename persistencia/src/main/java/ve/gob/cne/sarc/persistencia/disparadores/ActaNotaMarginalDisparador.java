package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ActaNotaMarginalEntidad;

/**
 * ActaNotaMarginalDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ActaNotaMarginalEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class ActaNotaMarginalDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad ActaNotaMarginalEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param notaMarginal objeto del modelo ontologico de Nota Marginal
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     *
     */
    @PrePersist
    @PreUpdate
    public void validacionesActaNotaMarginal(ActaNotaMarginalEntidad notaMarginal) throws ExceptionDisparador {
        validarActa(notaMarginal);
    }

    private void validarActa(ActaNotaMarginalEntidad notaMarginal) throws ExceptionDisparador {
        if (notaMarginal.getActa() == null) {
            throw new ExceptionDisparador("2#Debe indicar el acta a la que corresponde la nota marginal");
        }
    }
}
