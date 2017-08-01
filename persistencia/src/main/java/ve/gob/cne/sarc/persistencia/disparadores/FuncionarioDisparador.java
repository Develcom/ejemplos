package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;

/**
 * FuncionarioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase FuncionarioEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.1 12/08/2016
 * @author Oscar Montilla
 *
 */
public class FuncionarioDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad FuncionarioEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param funcionario objeto del modelo ontologico de Funcionario
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesFuncionario(FuncionarioEntidad funcionario)
            throws ExceptionDisparador {
        validarNacionalidadNulo(funcionario);
        validarPrimerApellidoNulo(funcionario);
        validarPrimerApellidoVacio(funcionario);
        validarPrimerNombreNulo(funcionario);
        validarPrimerNombreVacio(funcionario);
        validarIndicadorTipoDocumentoNulo(funcionario);
        validarCedulaNulo(funcionario);
        validarCedulaVacio(funcionario);
        validarCampoCedula(funcionario);
        validarEstatusNulo(funcionario);
    }

    private void validarNacionalidadNulo(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if (funcionario.getNacionalidad() == null) {
            throw new ExceptionDisparador("2#Debe introducir la nacionalidad correspondiente al funcionario");
        }
    }

    private void validarPrimerApellidoNulo(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if (funcionario.getPrimerApellido() == null) {
            throw new ExceptionDisparador("2#Debe introducir el valor primerApellido en la entidad funcionario");
        }
    }

    private void validarPrimerNombreNulo(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if (funcionario.getPrimerNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir el valor primerApellido en la entidad funcionario");
        }
    }

    private void validarPrimerApellidoVacio(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if ("".equals(funcionario.getPrimerApellido().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del funcionario");
        }
    }

    private void validarPrimerNombreVacio(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if ("".equals(funcionario.getPrimerNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el valor primerNombre en la entidad funcionario");
        }
    }

    private void validarIndicadorTipoDocumentoNulo(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if (funcionario.getIndicadorTipoDocumento() == null) {
            throw new ExceptionDisparador("2#Debe introducir el valor indicadorTipo en la entidad funcionario");
        }
    }

    private void validarCedulaNulo(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if (funcionario.getCedula() == null) {
            throw new ExceptionDisparador("2#Debe introducir el valor cedula en la entidad funcionario");
        }
    }

    private void validarCedulaVacio(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if ("".equals(funcionario.getCedula().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el valor cedula en la entidad funcionario");
        }
    }

    private void validarCampoCedula(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        try {
            Pattern pat = Pattern.compile("[0-9]{0,15}");
            Matcher mat = pat.matcher(funcionario.getCedula());
        } catch (Exception ex) {
            throw new ExceptionDisparador("2#Debe introducir solo números para el campo cédula", ex);
        }
    }

    private void validarEstatusNulo(FuncionarioEntidad funcionario) throws ExceptionDisparador {
        if (funcionario.getFuncionarioEstatus() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Estatus correspondiente al funcionario");
        }
    }

}
