package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocDecEntidad;

/**
 * SolicitanteTipDocDecDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitanteEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class SolicitanteTipDocDecDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitanteEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param solicitanteTipDocDec objeto del modelo ontologico Solicitante tipo documento dec
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesSolicitante(SolicitanteTipDocDecEntidad solicitanteTipDocDec)
            throws ExceptionDisparador {

        validarFechaInicio(solicitanteTipDocDec);
        validarNombreNulo(solicitanteTipDocDec);
        validarNombreVacio(solicitanteTipDocDec);
    }

    private void validarFechaInicio(SolicitanteTipDocDecEntidad sol) throws ExceptionDisparador {
        if (sol.getFechaInicio() == null) {
            sol.setFechaInicio(new Date());
        }
    }

    private void validarNombreNulo(SolicitanteTipDocDecEntidad solicitanteTipDodDec) throws ExceptionDisparador {
        if (solicitanteTipDodDec.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del tipo de documento del declarante");
        }
    }

    private void validarNombreVacio(SolicitanteTipDocDecEntidad solicitanteTipDocDec) throws ExceptionDisparador {
        if ("".equals(solicitanteTipDocDec.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del tipo de documento del declarante");
        }
    }
}
