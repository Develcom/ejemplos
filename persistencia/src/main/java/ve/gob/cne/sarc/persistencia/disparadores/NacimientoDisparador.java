package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.NacimientoEntidad;

/**
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase AusenciaPadreEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class NacimientoDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad NacimientoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param nacimiento Objeto del modelo ontologico que contiene la informacion de nacimiento
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesAusenciaPadre(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        validarSexoNulo(nacimiento);
        validarSexoVacio(nacimiento);
        validarNumeroCertificado(nacimiento);
        validarFechaCertificado(nacimiento);
        validarNombreMedicoNulo(nacimiento);
        validarNombreMedicoVacio(nacimiento);
        validarApellidoMedicoNulo(nacimiento);
        validarApellidoMedicoVacio(nacimiento);
        validarDocumentoMedicoNulo(nacimiento);
        validarDocumentoMedicoVacio(nacimiento);
        validarMpps(nacimiento);
        validarCentroSaludNulo(nacimiento);
        validarCentroSaludVacio(nacimiento);
        validarActaNulo(nacimiento);
    }

    private void validarSexoNulo(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getSexo() == null) {
            throw new ExceptionDisparador("2#Debe introducir el género del presentado");
        }
    }

    private void validarSexoVacio(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if ("".equals(nacimiento.getSexo().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el género del presentado");
        }
    }

    private void validarNumeroCertificado(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getCertificado() <= 0) {
            throw new ExceptionDisparador("2#Debe introducir el número del "
                    + "certificado de nacimiento del presentado");
        }
    }

    private void validarFechaCertificado(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getFechaCertificado() == null) {
            throw new ExceptionDisparador("2#Debe introducir la fecha de expedición del "
                    + "certificado de nacimiento del presentado");
        }
    }

    private void validarNombreMedicoVacio(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getPrimerNombreMedico() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del "
                    + "médico que expide el certificado de nacimiento del presentado");
        }
    }

    private void validarNombreMedicoNulo(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if ("".equals(nacimiento.getPrimerNombreMedico().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre "
                    + "del médico que expide el certificado de nacimiento del presentado..");
        }
    }

    private void validarApellidoMedicoNulo(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getPrimerApellidoMedico() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido "
                    + "del médico que expide el certificado de nacimiento del presentado");
        }
    }

    private void validarApellidoMedicoVacio(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if ("".equals(nacimiento.getPrimerApellidoMedico().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido "
                    + "del médico que expide el certificado de nacimiento del presentado");
        }
    }

    private void validarDocumentoMedicoNulo(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getNumDocMedico() == null) {
            throw new ExceptionDisparador("2#Debe introducir el número de documento "
                    + "de identidad del médico que expide el certificado de nacimiento");
        }
    }

    private void validarDocumentoMedicoVacio(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if ("".equals(nacimiento.getNumDocMedico().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el número de documento "
                    + "de identidad del médico que expide el certificado de nacimiento");
        }
    }

    private void validarMpps(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getMpps() <= 0) {
            throw new ExceptionDisparador("2#Debe introducir el mpps del "
                    + "médico que expide el certificado de nacimiento");
        }
    }

    private void validarCentroSaludNulo(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getCentroSalud() == null) {
            throw new ExceptionDisparador("2#Debe introducir el "
                    + "nombre del centro de salud correspondiente");
        }
    }

    private void validarCentroSaludVacio(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if ("".equals(nacimiento.getCentroSalud().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el "
                    + "nombre del centro de salud correspondiente");
        }
    }

    private void validarActaNulo(NacimientoEntidad nacimiento) throws ExceptionDisparador {
        if (nacimiento.getActa() == null) {
            throw new ExceptionDisparador("2#Debe indicar el"
                    + " acta asociada al registro");
        }
    }
}
