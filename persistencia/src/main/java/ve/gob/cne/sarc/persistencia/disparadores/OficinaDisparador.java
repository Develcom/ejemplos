package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;

/**
 * OficinaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase OficinaEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class OficinaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad OficinaEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param oficina objeto del modelo ontologico de oficina
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesOficina(OficinaEntidad oficina) throws ExceptionDisparador {
        validarParroquiaNulo(oficina);
        validarTipoOficinaNulo(oficina);
        validarNombreNulo(oficina);
        validarNombreVacio(oficina);
        validarFechaInicioNulo(oficina);
        validarDireccionNulo(oficina);
        validarDireccionVacio(oficina);

    }

    private void validarParroquiaNulo(OficinaEntidad oficina) throws ExceptionDisparador {
        if (oficina.getParroquia() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la parroquia correspondiente a la oficina");
        }
    }

    private void validarTipoOficinaNulo(OficinaEntidad oficina)
            throws ExceptionDisparador {
        if (oficina.getTipoOficina() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el tipo de oficina correspondiente a la oficina");
        }
    }

    private void validarNombreNulo(OficinaEntidad oficina) throws ExceptionDisparador {
        if (oficina.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique a la oficina");
        }
    }

    private void validarNombreVacio(OficinaEntidad oficina) throws ExceptionDisparador {
        if ("".equals(oficina.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique a la oficina");
        }
    }

    private void validarFechaInicioNulo(OficinaEntidad oficina) {
        if (oficina.getFechaInicio() == null) {
            oficina.setFechaInicio(new Date());
        }
    }

    private void validarDireccionNulo(OficinaEntidad oficina) throws ExceptionDisparador {
        if (oficina.getDireccion() == null) {
            throw new ExceptionDisparador("2#Debe introducir la dirección de la oficina");
        }
    }

    private void validarDireccionVacio(OficinaEntidad oficina) throws ExceptionDisparador {
        if ("".equals(oficina.getDireccion().trim())) {
            throw new ExceptionDisparador("2#Debe introducir la dirección de la oficina");
        }
    }
}
