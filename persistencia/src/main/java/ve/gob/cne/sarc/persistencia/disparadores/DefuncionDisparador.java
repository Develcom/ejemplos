package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.DefuncionEntidad;

/**
 * DefuncionDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase DefuncionEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1
 */
public class DefuncionDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad DefuncionEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param defuncion objeto del modelo ontologico de Denfuncion
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesDefuncion(DefuncionEntidad defuncion)
            throws ExceptionDisparador {
        validarParroquiaNulo(defuncion);
        validarActaNulo(defuncion);
        validarFechaDefuncionNulo(defuncion);
        validarTextoCausaNulo(defuncion);
        validarTextoCausaVacio(defuncion);
        validarSexoPresentadoNulo(defuncion);
        validarSexoPresentadoVacio(defuncion);
        validarEstadoCivilNulo(defuncion);
        validarEstadoCivilVacio(defuncion);
        validarEstadoNulo(defuncion);
        validarEstadoVacio(defuncion);
        validarMunicipioNulo(defuncion);
        validarMunicipioVacio(defuncion);
        validarNumeroCertificadoDefuncionCero(defuncion);
        validarFechaCertificadoDefuncionNulo(defuncion);
        validarPrimerNombreMedicoNulo(defuncion);
        validarPrimerNombreMedicoVacio(defuncion);
        validarPrimerApellidoMedicoNulo(defuncion);
        validarPrimerApellidoMedicoVacio(defuncion);
        validarDocumentoIdentidadMedicoNulo(defuncion);
        validarDocumentoIdentidadMedicoVacio(defuncion);
        validarNumeroMPPSCero(defuncion);
    }

    private void validarNumeroMPPSCero(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if (defuncion.getNuMPPS() == 0) {
            throw new ExceptionDisparador("2#Debe indicar el número de MPPS del médico");
        }
    }

    private void validarDocumentoIdentidadMedicoVacio(DefuncionEntidad defuncion)
            throws ExceptionDisparador {
        if ("".equals(defuncion.getDocumentoIdentidadMedico().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el número de documento de identidad del médico");
        }
    }

    private void validarDocumentoIdentidadMedicoNulo(DefuncionEntidad defuncion)
            throws ExceptionDisparador {
        if (defuncion.getDocumentoIdentidadMedico() == null) {
            throw new ExceptionDisparador("2#Debe introducir el número de documento de identidad del médico");
        }
    }

    private void validarPrimerNombreMedicoVacio(DefuncionEntidad defuncion)
            throws ExceptionDisparador {
        if ("".equals(defuncion.getPrimerNombreMedico().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre "
                    + "del médico que emite el certificado.");
        }
    }

    private void validarPrimerNombreMedicoNulo(DefuncionEntidad defuncion)
            throws ExceptionDisparador {
        if (defuncion.getPrimerNombreMedico() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre "
                    + "del médico que emite el certificado..");
        }
    }

    private void validarPrimerApellidoMedicoVacio(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if ("".equals(defuncion.getPrimerApellidoMedico().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido "
                    + "del médico que emite el certificado");
        }
    }

    private void validarPrimerApellidoMedicoNulo(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if (defuncion.getPrimerApellidoMedico() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido "
                    + "del médico que emite el certificado");
        }
    }

    private void validarMunicipioVacio(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if ("".equals(defuncion.getMunicipioDefuncion().trim())) {
            throw new ExceptionDisparador("2#Debe indicar el municipio correspondiente a la defunción");
        }
    }

    private void validarMunicipioNulo(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if (defuncion.getMunicipioDefuncion() == null) {
            throw new ExceptionDisparador("2#Debe indicar el municipio correspondiente a la defunción");
        }
    }

    private void validarEstadoVacio(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if ("".equals(defuncion.getEstadoDefuncion().trim())) {
            throw new ExceptionDisparador("2#Debe indicar el estado correspondiente a la defunción");
        }
    }

    private void validarEstadoNulo(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if (defuncion.getEstadoDefuncion() == null) {
            throw new ExceptionDisparador("2#Debe indicar el estado correspondiente a la defunción");
        }
    }

    private void validarEstadoCivilNulo(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if (defuncion.getEstadoCivil() == null) {
            throw new ExceptionDisparador("2#Debe indicar el estado civil del difunto");
        }
    }

    private void validarEstadoCivilVacio(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if ("".equals(defuncion.getEstadoCivil().trim())) {
            throw new ExceptionDisparador("2#Debe indicar el estado civil del difunto");
        }
    }

    private void validarNumeroCertificadoDefuncionCero(
            DefuncionEntidad defuncion) throws ExceptionDisparador {
        if (defuncion.getNumeroCertificado() == 0) {
            throw new ExceptionDisparador("2#Debe indicar el número del certificado de defunción");
        }
    }

    private void validarFechaCertificadoDefuncionNulo(DefuncionEntidad defuncion) {
        if (defuncion.getFechaCertificado() == null) {
            defuncion.setFechaCertificado(new Date());
        }
    }

    private void validarSexoPresentadoNulo(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if (defuncion.getSexo() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Sexo del difunto");
        }
    }

    private void validarSexoPresentadoVacio(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if ("".equals(defuncion.getSexo().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el Sexo del difunto");
        }
    }

    private void validarParroquiaNulo(DefuncionEntidad defuncion)
            throws ExceptionDisparador {
        if (defuncion.getParroquia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la parroquia correspondiente a la defunción");
        }
    }

    private void validarActaNulo(DefuncionEntidad defuncion) throws ExceptionDisparador {
        if (defuncion.getActa() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la acta correspondiente a la defunción");
        }
    }

    private void validarFechaDefuncionNulo(DefuncionEntidad defuncion) {
        if (defuncion.getFechaDefuncion() == null) {
            defuncion.setFechaDefuncion(new Date());
        }
    }

    private void validarTextoCausaNulo(DefuncionEntidad defuncion)
            throws ExceptionDisparador {
        if (defuncion.getTextoCausa() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor textoCausa en la entidad defunción");
        }
    }

    private void validarTextoCausaVacio(DefuncionEntidad defuncion)
            throws ExceptionDisparador {
        if ("".equals(defuncion.getTextoCausa().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor textoCausa en la entidad defunción");
        }
    }
}
