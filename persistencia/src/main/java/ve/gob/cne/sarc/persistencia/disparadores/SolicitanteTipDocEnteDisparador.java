package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocEnteEntidad;

/**
 * SolicitanteTipDocEnteDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitanteEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class SolicitanteTipDocEnteDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitanteEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param solicitanteTipDocEnte Objeto del modelo ontologico de Solicitante tipo documento ente.
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesSolicitanteTipDocEnte(SolicitanteTipDocEnteEntidad solicitanteTipDocEnte)
            throws ExceptionDisparador {

        validarFechaInicio(solicitanteTipDocEnte);
        validarNombreNulo(solicitanteTipDocEnte);
        validarNombreVacio(solicitanteTipDocEnte);
    }

    private void validarFechaInicio(SolicitanteTipDocEnteEntidad solEnte) throws ExceptionDisparador {
        if (solEnte.getFechaInicio() == null) {
            solEnte.setFechaInicio(new Date());
        }
    }

    private void validarNombreNulo(SolicitanteTipDocEnteEntidad solicitanteTipDocEnte)
            throws ExceptionDisparador {
        if (solicitanteTipDocEnte.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre de tipo de documento");
        }
    }

    private void validarNombreVacio(SolicitanteTipDocEnteEntidad solicitanteTipDocEnte)
            throws ExceptionDisparador {
        if ("".equals(solicitanteTipDocEnte.getNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre de tipo de documento");
        }
    }
}
