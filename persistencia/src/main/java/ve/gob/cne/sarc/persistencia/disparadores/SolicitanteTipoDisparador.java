package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipoEntidad;

/**
 * SolicitanteTipoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase SolicitanteTipoEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class SolicitanteTipoDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad SolicitanteTipoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param solicitanteTipo objeto del modelo ontologico de Solicitante tipo
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesSolicitanteTipo(SolicitanteTipoEntidad solicitanteTipo) throws ExceptionDisparador {

        validarNombreNulo(solicitanteTipo);
        validarNombreVacio(solicitanteTipo);
        validarFechaInicioNulo(solicitanteTipo);
    }

    private void validarNombreNulo(SolicitanteTipoEntidad solicitanteTipo) throws ExceptionDisparador {
        if (solicitanteTipo.getNombreSolicitanteTipo() == null) {
            throw new ExceptionDisparador("Debe introducir el nombre que identifique al estado");
        }
    }

    private void validarNombreVacio(SolicitanteTipoEntidad solicitanteTipo) throws ExceptionDisparador {
        if ("".equals(solicitanteTipo.getNombreSolicitanteTipo().trim())) {
            throw new ExceptionDisparador("Debe introducir el nombre que identifique al estado");
        }
    }

    private void validarFechaInicioNulo(SolicitanteTipoEntidad solicitanteTipo) {
        if (solicitanteTipo.getFechaInicio() == null) {
            solicitanteTipo.setFechaInicio(new Date());
        }
    }
}
