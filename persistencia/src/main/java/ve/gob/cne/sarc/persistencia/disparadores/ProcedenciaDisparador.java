package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ProcedenciaEntidad;

/**
 * ProcedenciaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ProcedenciaEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class ProcedenciaDisparador {

    /**
     *
     * @metodoEste método recibe como parámetro la entidad ProcedenciaEntidad y llama a otros métodos privados para
     * @param procedencia Objeto del modelo ontologico de procedencia
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesProcedencia(ProcedenciaEntidad procedencia)
            throws ExceptionDisparador {
        validarTipoProcedenciaNulo(procedencia);
        validarActaNulo(procedencia);
    }

    private void validarTipoProcedenciaNulo(ProcedenciaEntidad procedencia)
            throws ExceptionDisparador {
        if (procedencia.getTipoProcedencia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el tipo de procedencia correspondiente a la procedencia");
        }
    }

    private void validarActaNulo(ProcedenciaEntidad procedencia)
            throws ExceptionDisparador {
        if (procedencia.getActa() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el acta correspondiente a la procedencia");
        }
    }
}
