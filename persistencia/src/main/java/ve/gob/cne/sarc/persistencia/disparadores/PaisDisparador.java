package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;

/**
 * PaisDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase PaisEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
public class PaisDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad PaisEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param pais objeto del modelo ontologico de Pais
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesPais(PaisEntidad pais) throws ExceptionDisparador {

        validarNombreNulo(pais);
        validarNombreVacio(pais);
        validarFechaInicioNulo(pais);
    }

    private void validarNombreNulo(PaisEntidad pais) throws ExceptionDisparador {
        if (pais.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al país");
        }
    }

    private void validarNombreVacio(PaisEntidad pais) throws ExceptionDisparador {
        if ("".equals(pais.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al país");
        }
    }

    private void validarFechaInicioNulo(PaisEntidad pais) {
        if (pais.getFechaInicio() == null) {
            pais.setFechaInicio(new Date());
        }
    }
}
