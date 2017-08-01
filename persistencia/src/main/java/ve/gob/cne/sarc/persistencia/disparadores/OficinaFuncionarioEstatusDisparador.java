package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEstatusEntidad;

/**
 * OficinaFuncionarioEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase OficinaFuncionarioEstatusEntidad sean
 * correctos en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
public class OficinaFuncionarioEstatusDisparador {

    /**
     * @metodo Este método recibe como parámetro la entidad OficinaFuncionarioEstatusEntidad y llama a otros métodos
     * privados para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param oficFuncEstatus Objeto del modelo ontologico de Oficina funcionario Estatus
     * @throws ExceptionDisparador- Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesOficinaFuncionarioEstatus(OficinaFuncionarioEstatusEntidad oficFuncEstatus)
            throws ExceptionDisparador {

        validarNombreNulo(oficFuncEstatus);
        validarNombreVacio(oficFuncEstatus);
        validarFechaInicioNulo(oficFuncEstatus);
    }

    private void validarNombreNulo(OficinaFuncionarioEstatusEntidad oficFuncEstatus) throws ExceptionDisparador {
        if (oficFuncEstatus.getNomOficFuncEstatus() == null) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique a la OficinaFuncionarioEstatus");
        }
    }

    private void validarNombreVacio(OficinaFuncionarioEstatusEntidad oficFuncEstatus) throws ExceptionDisparador {
        if ("".equals(oficFuncEstatus.getNomOficFuncEstatus().trim())) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique a la OficinaFuncionarioEstatus");
        }
    }

    private void validarFechaInicioNulo(OficinaFuncionarioEstatusEntidad oficFuncEstatus) {
        if (oficFuncEstatus.getFechaInicio() == null) {
            oficFuncEstatus.setFechaInicio(new Date());
        }
    }
}
