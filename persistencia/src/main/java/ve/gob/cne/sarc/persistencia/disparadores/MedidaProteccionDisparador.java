package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.MedidaProteccionEntidad;

/**
 * validacionesMedidaProteccion.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase MedidaProteccionEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class MedidaProteccionDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad MedidaProteccionEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param medidaProteccion objeto del modelo ontologico de Medida Proteccion
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesMedidaProteccion(
            MedidaProteccionEntidad medidaProteccion) throws ExceptionDisparador {
        validarProcedenciaNulo(medidaProteccion);
        validarNombreConsejoProteccionNulo(medidaProteccion);
        validarNombreConsejoProteccionVacio(medidaProteccion);
        validarNumeroMedidaCero(medidaProteccion);
        validarPrimerNombreConsejeroNulo(medidaProteccion);
        validarPrimerNombreConsejeroVacio(medidaProteccion);
        validarPrimerApellidoConsejeroNulo(medidaProteccion);
        validarPrimerApellidoConsejeroVacio(medidaProteccion);
        validarFechaMedidaNulo(medidaProteccion);
    }

    private void validarProcedenciaNulo(MedidaProteccionEntidad medidaProteccion)
            throws ExceptionDisparador {
        if (medidaProteccion.getProcedencia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la procedencia correspondiente a la medida de protección");
        }
    }

    private void validarNombreConsejoProteccionNulo(
            MedidaProteccionEntidad medidaProteccion) throws ExceptionDisparador {
        if (medidaProteccion.getNombreConsejoProteccion() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre del consejo de protección");
        }
    }

    private void validarNombreConsejoProteccionVacio(
            MedidaProteccionEntidad medidaProteccion) throws ExceptionDisparador {
        if ("".equals(medidaProteccion.getNombreConsejoProteccion().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre del consejo de protección");
        }
    }

    private void validarNumeroMedidaCero(
            MedidaProteccionEntidad medidaProteccion) throws ExceptionDisparador {
        if (medidaProteccion.getNumeroMedida() == 0) {
            throw new ExceptionDisparador("2#Debe indicar el número de medida");
        }
    }

    private void validarPrimerNombreConsejeroNulo(MedidaProteccionEntidad medidaProteccion)
            throws ExceptionDisparador {
        if (medidaProteccion.getPrimerNombreConsejero() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del consejero");
        }
    }

    private void validarPrimerNombreConsejeroVacio(MedidaProteccionEntidad medidaProteccion)
            throws ExceptionDisparador {
        if ("".equals(medidaProteccion.getPrimerNombreConsejero().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del consejero");
        }
    }

    private void validarPrimerApellidoConsejeroNulo(MedidaProteccionEntidad medidaProteccion)
            throws ExceptionDisparador {

        if (medidaProteccion.getPrimerApellidoConsejero() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del consejero");
        }
    }

    private void validarPrimerApellidoConsejeroVacio(MedidaProteccionEntidad medidaProteccion)
            throws ExceptionDisparador {
        if ("".equals(medidaProteccion.getPrimerApellidoConsejero().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del consejero");
        }
    }

    private void validarFechaMedidaNulo(MedidaProteccionEntidad medidaProteccion) {

        if (medidaProteccion.getFechaMedida() == null) {
            medidaProteccion.setFechaMedida(new Date());
        }
    }
}
