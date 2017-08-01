package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;

/**
 * ComunidadIndigenaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ComunidadIndigenaEntidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class ComunidadIndigenaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad ComunidadIndigenaEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param comunidadIndigena objeto del modelo ontologico de comunidad Indigine
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesComunidadIndigena(
            ComunidadIndigenaEntidad comunidadIndigena) throws ExceptionDisparador {
        validarNombreNulo(comunidadIndigena);
        validarNombreVacio(comunidadIndigena);
        validarFechaInicioNulo(comunidadIndigena);
    }

    private void validarNombreNulo(ComunidadIndigenaEntidad comunidadIndigena)
            throws ExceptionDisparador {
        if (comunidadIndigena.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique la comunidad indígena");
        }
    }

    private void validarNombreVacio(ComunidadIndigenaEntidad comunidadIndigena)
            throws ExceptionDisparador {
        if ("".equals(comunidadIndigena.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique la comunidad indígena");
        }
    }

    private void validarFechaInicioNulo(
            ComunidadIndigenaEntidad comunidadIndigena) {
        if (comunidadIndigena.getFechaInicio() == null) {
            comunidadIndigena.setFechaInicio(new Date());
        }
    }
}
