package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ExtemporaneaEntidad;

/**
 * ExtemporaneaListener.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ExtemporaneaEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Anabell De Faria
 * @version 1.1
 */
public class ExtemporaneaListener {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad ExtemporaneaEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param extemporanea Objeto del modelo ontologico que contiene la informacion de ex temporanea
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesExtemporanea(ExtemporaneaEntidad extemporanea)
            throws ExceptionDisparador {
        validarNumeroNulo(extemporanea);
        validarNumeroVacio(extemporanea);
        validarPrimerNombreAutoridadNulo(extemporanea);
        validarPrimerNombreAutoridadVacio(extemporanea);
        validarPrimerApellidoAutoridadNulo(extemporanea);
        validarPrimerApellidoAutoridadVacio(extemporanea);
        validarFechaProvidenciaNulo(extemporanea);
    }

    private void validarNumeroNulo(ExtemporaneaEntidad extemporanea) throws ExceptionDisparador {
        if (extemporanea.getNumeroProvidencia() == null) {
            throw new ExceptionDisparador("2#Debe colocar el númeo de providencia correspondiente");
        }
    }

    private void validarNumeroVacio(ExtemporaneaEntidad extemporanea) throws ExceptionDisparador {
        if ("".equals(extemporanea.getNumeroProvidencia())) {
            throw new ExceptionDisparador("2#Debe colocar el númeo de providencia correspondiente");
        }
    }

    private void validarPrimerNombreAutoridadNulo(ExtemporaneaEntidad extemporanea)
            throws ExceptionDisparador {
        if (extemporanea.getPrimerNombreAutoridad() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del Autoridad");
        }
    }

    private void validarPrimerNombreAutoridadVacio(ExtemporaneaEntidad extemporanea)
            throws ExceptionDisparador {
        if ("".equals(extemporanea.getPrimerNombreAutoridad().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del Autoridad");
        }
    }

    private void validarPrimerApellidoAutoridadNulo(ExtemporaneaEntidad extemporanea)
            throws ExceptionDisparador {

        if (extemporanea.getPrimerApellidoAutoridad() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del Autoridad");
        }
    }

    private void validarPrimerApellidoAutoridadVacio(ExtemporaneaEntidad extemporanea)
            throws ExceptionDisparador {
        if ("".equals(extemporanea.getPrimerApellidoAutoridad().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del Autoridad");
        }
    }

    private void validarFechaProvidenciaNulo(ExtemporaneaEntidad extemporanea) {
        if (extemporanea.getFechaProvidencia() == null) {
            extemporanea.setFechaProvidencia(new Date());
        }
    }

}
