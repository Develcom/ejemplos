package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEstatusEntidad;

/**
 * FuncionarioEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase FuncionarioEstatusEntidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 */
public class FuncionarioEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad FuncionarioEstatusEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param funcionarioEstatus objeto del modelo ontologico de funcionario Estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesFuncionarioEstatus(FuncionarioEstatusEntidad funcionarioEstatus)
            throws ExceptionDisparador {
        validarNombreNulo(funcionarioEstatus);
        validarNombreVacio(funcionarioEstatus);
        validarFechaInicioNulo(funcionarioEstatus);

    }

    private void validarNombreNulo(FuncionarioEstatusEntidad funcionarioEstatus) throws ExceptionDisparador {
        if (funcionarioEstatus.getNombreFuncEstatus() == null) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique al FuncionarioEstatus");
        }
    }

    private void validarNombreVacio(FuncionarioEstatusEntidad funcionarioEstatus) throws ExceptionDisparador {
        if ("".equals(funcionarioEstatus.getNombreFuncEstatus().trim())) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique al FuncionarioEstatus");
        }
    }

    private void validarFechaInicioNulo(FuncionarioEstatusEntidad funcionarioEstatus) {
        if (funcionarioEstatus.getFechaInicio() == null) {
            funcionarioEstatus.setFechaInicio(new Date());
        }
    }
}
