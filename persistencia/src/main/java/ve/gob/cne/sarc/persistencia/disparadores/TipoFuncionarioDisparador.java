package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.TipoFuncionarioEntidad;

/**
 * TipoFuncionarioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TipoFuncionarioEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class TipoFuncionarioDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TipoFuncionarioEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tipoFuncionario Objeto del modelo ontologico de Tipo Funcionario
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTipoFuncionario(
            TipoFuncionarioEntidad tipoFuncionario) throws ExceptionDisparador {
        validarNombreNulo(tipoFuncionario);
        validarNombreVacio(tipoFuncionario);
        validarFechaInicioNulo(tipoFuncionario);
    }

    private void validarNombreNulo(TipoFuncionarioEntidad tipoFuncionario)
            throws ExceptionDisparador {
        if (tipoFuncionario.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique el tipo de funcionario");
        }
    }

    private void validarNombreVacio(TipoFuncionarioEntidad tipoFuncionario)
            throws ExceptionDisparador {
        if ("".equals(tipoFuncionario.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique el tipo de funcionario");
        }
    }

    private void validarFechaInicioNulo(TipoFuncionarioEntidad tipoFuncionario) {
        if (tipoFuncionario.getFechaInicio() == null) {
            tipoFuncionario.setFechaInicio(new Date());
        }
    }
}
