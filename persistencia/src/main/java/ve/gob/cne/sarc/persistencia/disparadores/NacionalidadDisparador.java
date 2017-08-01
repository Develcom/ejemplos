package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;

/**
 * NacionalidadDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase NacionalidadEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 12/08/2016
 */
public class NacionalidadDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad NacionalidadEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param nacionalidad objeto del modelo ontologico de Nacionalidad
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesNacionalidad(NacionalidadEntidad nacionalidad)
            throws ExceptionDisparador {

        validarNombreNulo(nacionalidad);
        validarNombreVacio(nacionalidad);
        validarFechaInicioNulo(nacionalidad);
    }

    private void validarNombreNulo(NacionalidadEntidad nacionalidad)
            throws ExceptionDisparador {
        if (nacionalidad.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique la nacionalidad");
        }
    }

    private void validarNombreVacio(NacionalidadEntidad nacionalidad)
            throws ExceptionDisparador {
        if ("".equals(nacionalidad.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el código que identifique la nacionalidad");
        }
    }

    private void validarFechaInicioNulo(NacionalidadEntidad nacionalidad) {
        if (nacionalidad.getFechaInicio() == null) {
            nacionalidad.setFechaInicio(new Date());
        }
    }
}
