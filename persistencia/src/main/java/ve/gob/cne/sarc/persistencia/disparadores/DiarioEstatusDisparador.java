package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.DiarioEstatusEntidad;

/**
 * DiarioEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase DiarioEstatusEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.1
 * @author Oscar Montilla
 *
 */
public class DiarioEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad DiarioEstatusEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param diarioEstatus objeto del modelo ontologico de Diario Estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesDiarioEstatus(DiarioEstatusEntidad diarioEstatus) throws ExceptionDisparador {

        validarNombreNulo(diarioEstatus);
        validarNombreVacio(diarioEstatus);
        validarFechaInicioNulo(diarioEstatus);
    }

    private void validarNombreNulo(DiarioEstatusEntidad diarioEstatus) throws ExceptionDisparador {
        if (diarioEstatus.getNombreDiarioEstatus() == null) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique al DiarioEstatus");
        }
    }

    private void validarNombreVacio(DiarioEstatusEntidad diarioEstatus) throws ExceptionDisparador {
        if ("".equals(diarioEstatus.getNombreDiarioEstatus().trim())) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique al DiarioEstatus");
        }
    }

    private void validarFechaInicioNulo(DiarioEstatusEntidad diarioEstatus) {
        if (diarioEstatus.getFechaInicio() == null) {
            diarioEstatus.setFechaInicio(new Date());
        }
    }
}
