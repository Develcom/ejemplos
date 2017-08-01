package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.ActaPrimigeniaEntidad;

/**
 * ActaPrimigeniaEntidad.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ActaPrimigeniaEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.0 11/10/2016
 * @author Oscar Montilla
 */
public class ActaPrimigeniaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad ActaPrimigeniaEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param actaP objeto del modelo ontologico de Acta Primigenia
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     *
     */
    @PrePersist
    @PreUpdate
    public void validacionesActa(ActaPrimigeniaEntidad actaP) throws ExceptionDisparador {
        validarNumeroActa(actaP);
        validarNumeroActaVacio(actaP);
        validarnumeroTomo(actaP);
        validarnumeroTomoVacio(actaP);
        validarFechaIncripcion(actaP);
    }

    private void validarNumeroActa(ActaPrimigeniaEntidad actaP) throws ExceptionDisparador {
        if (actaP.getNumeroActa() == null) {
            throw new ExceptionDisparador("#Debe introducir el Numero de acta Primigenia,numeroActa = null");
        }
    }

    private void validarNumeroActaVacio(ActaPrimigeniaEntidad actaP)
            throws ExceptionDisparador {
        if ("".equals(actaP.getNumeroActa().trim())) {
            throw new ExceptionDisparador(
                    "#Debe introducir el Numero de acta Primigenia, numeroActa = ''");
        }
    }

    private void validarnumeroTomo(ActaPrimigeniaEntidad actaP) throws ExceptionDisparador {
        if (actaP.getNumeroTomo() == null) {
            throw new ExceptionDisparador(
                    "#Debe introducir el numero de tomo, numeroTomo = null");
        }
    }

    private void validarnumeroTomoVacio(ActaPrimigeniaEntidad actaP) throws ExceptionDisparador {
        if ("".equals(actaP.getNumeroTomo().trim())) {
            throw new ExceptionDisparador(
                    "#Debe introducir el numero de tomo, numeroTomo = ''");
        }
    }

    private void validarFechaIncripcion(ActaPrimigeniaEntidad actaP) throws ExceptionDisparador {
        if (actaP.getFechaIncripcion() == null) {
            throw new ExceptionDisparador(
                    "#Debe introducir la fecha de inscripcion correspondiente "
                    + "al acta Primigenia, nombreOficina = null");
        }
    }

}
