package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.DecisionJudicialEntidad;

/**
 * DecisionJudicialDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase DecisionJudicialEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1
 */
public class DecisionJudicialDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad DecisionJudicialEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param decisionJudicial objeto del modelo ontologico de Decision Judicial
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesDecisionJudicial(
            DecisionJudicialEntidad decisionJudicial) throws ExceptionDisparador {
        validarProcedenciaNulo(decisionJudicial);
        validarNombreTribunalNulo(decisionJudicial);
        validarNombreTribunalVacio(decisionJudicial);
        validarNumeroSentenciaCero(decisionJudicial);
        validarNumeroSentenciaNulo(decisionJudicial);
        validarPrimerNombreJuezNulo(decisionJudicial);
        validarPrimerNombreJuezVacio(decisionJudicial);
        validarPrimerApellidoJuezNulo(decisionJudicial);
        validarPrimerApellidoJuezVacio(decisionJudicial);
        validarFechaSentenciaNulo(decisionJudicial);
    }

    private void validarProcedenciaNulo(DecisionJudicialEntidad decisionJudicial)
            throws ExceptionDisparador {
        if (decisionJudicial.getProcedencia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la procedencia correspondiente a la decisiónJudicial");
        }
    }

    private void validarNombreTribunalNulo(
            DecisionJudicialEntidad decisionJudicial) throws ExceptionDisparador {
        if (decisionJudicial.getNombreTribunal() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor nombreTribunal en la entidad decisiónJudicial");
        }
    }

    private void validarNombreTribunalVacio(
            DecisionJudicialEntidad decisionJudicial) throws ExceptionDisparador {
        if ("".equals(decisionJudicial.getNombreTribunal().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor nombreTribunal en la entidad decisiónJudicial");
        }
    }

    private void validarNumeroSentenciaCero(
            DecisionJudicialEntidad decisionJudicial) throws ExceptionDisparador {
        if ("".equals(decisionJudicial.getNumeroSentencia().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor númeroSentencia en la entidad decisiónJudicial");
        }
    }

    private void validarNumeroSentenciaNulo(
            DecisionJudicialEntidad decisionJudicial) throws ExceptionDisparador {
        if (decisionJudicial.getNumeroSentencia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor númeroSentencia en la entidad decisiónJudicial");
        }
    }

    private void validarPrimerNombreJuezNulo(DecisionJudicialEntidad decisionJudicial)
            throws ExceptionDisparador {
        if (decisionJudicial.getPrimerNombreJuez() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del juez");
        }
    }

    private void validarPrimerNombreJuezVacio(DecisionJudicialEntidad decisionJudicial)
            throws ExceptionDisparador {
        if ("".equals(decisionJudicial.getPrimerNombreJuez().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del juez");
        }
    }

    private void validarPrimerApellidoJuezNulo(DecisionJudicialEntidad decisionJudicial)
            throws ExceptionDisparador {

        if (decisionJudicial.getPrimerApellidoJuez() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del juez");
        }
    }

    private void validarPrimerApellidoJuezVacio(DecisionJudicialEntidad decisionJudicial)
            throws ExceptionDisparador {
        if ("".equals(decisionJudicial.getPrimerApellidoJuez().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del juez");
        }
    }

    private void validarFechaSentenciaNulo(DecisionJudicialEntidad decisionJudicial) {

        if (decisionJudicial.getFechaSentencia() == null) {
            decisionJudicial.setFechaSentencia(new Date());
        }
    }
}
