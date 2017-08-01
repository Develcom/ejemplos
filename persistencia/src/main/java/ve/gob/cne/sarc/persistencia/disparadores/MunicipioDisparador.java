package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;

/**
 * MunicipioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase MunicipioEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class MunicipioDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad MunucipioEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param municipio objeto del modelo ontologico de Municipio
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesMunicipio(MunicipioEntidad municipio)
            throws ExceptionDisparador {
        validarEstadoNulo(municipio);
        validarNombreNulo(municipio);
        validarNombreVacio(municipio);
        validarFechaInicioNulo(municipio);
    }

    private void validarEstadoNulo(MunicipioEntidad municipio) throws ExceptionDisparador {
        if (municipio.getEstado() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el municipio correspondiente al municipio");
        }
    }

    private void validarNombreNulo(MunicipioEntidad municipio) throws ExceptionDisparador {
        if (municipio.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al municipio");
        }
    }

    private void validarNombreVacio(MunicipioEntidad municipio)
            throws ExceptionDisparador {
        if ("".equals(municipio.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al municipio");
        }
    }

    private void validarFechaInicioNulo(MunicipioEntidad municipio) {
        if (municipio.getFechaInicio() == null) {
            municipio.setFechaInicio(new Date());
        }
    }
}
