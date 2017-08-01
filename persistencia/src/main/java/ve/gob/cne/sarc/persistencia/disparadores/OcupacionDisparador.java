package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.OcupacionEntidad;

/**
 * OcupacionDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase OcupacionEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class OcupacionDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad OcupacionEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param ocupacion objeto del modelo ontologico de Ocupacion
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesOcupacion(OcupacionEntidad ocupacion)
            throws ExceptionDisparador {
        validarNombreNulo(ocupacion);
        validarNombreVacio(ocupacion);
        validarFechaInicioNulo(ocupacion);
    }

    private void validarNombreNulo(OcupacionEntidad ocupacion) throws ExceptionDisparador {
        if (ocupacion.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique la ocupación");
        }
    }

    private void validarNombreVacio(OcupacionEntidad ocupacion)
            throws ExceptionDisparador {
        if ("".equals(ocupacion.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique la ocupación");
        }
    }

    private void validarFechaInicioNulo(OcupacionEntidad ocupacion) {
        if (ocupacion.getFechaInicio() == null) {
            ocupacion.setFechaInicio(new Date());
        }
    }
}
