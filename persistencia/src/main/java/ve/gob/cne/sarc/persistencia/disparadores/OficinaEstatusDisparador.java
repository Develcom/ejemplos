package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.OficinaEstatusEntidad;

/**
 * OficinaEstatusDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase OficinaEstatusEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class OficinaEstatusDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad OficinaEstatussEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param oficinaEstatus objeto del modelo ontologico de Oficina estatus
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesOficinaEstatus(OficinaEstatusEntidad oficinaEstatus) throws ExceptionDisparador {
        validarNombreNulo(oficinaEstatus);
        validarNombreVacio(oficinaEstatus);
        validarFechaInicioNulo(oficinaEstatus);
    }

    private void validarNombreNulo(OficinaEstatusEntidad oficinaEstatus) throws ExceptionDisparador {
        if (oficinaEstatus.getNombreOficEstatus() == null) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique al FuncionarioEstatus");
        }
    }

    private void validarNombreVacio(OficinaEstatusEntidad oficinaEstatus) throws ExceptionDisparador {
        if ("".equals(oficinaEstatus.getNombreOficEstatus().trim())) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique al FuncionarioEstatus");
        }
    }

    private void validarFechaInicioNulo(OficinaEstatusEntidad oficinaEstatus) {
        if (oficinaEstatus.getFechaInicio() == null) {
            oficinaEstatus.setFechaInicio(new Date());
        }
    }

}
