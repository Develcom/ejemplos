package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TipoDocIdentidadEntidad;

/**
 * TipoDocIdentidaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TipoDocIdentidadEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class TipoDocIdentidaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad CiudadanoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tdie objeto del modelo ontologico de Tipo documento identidad
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTipoDocIdentida(TipoDocIdentidadEntidad tdie) throws ExceptionDisparador {
        validarDecripcionNull(tdie);
        validarDescripcionVacio(tdie);
        validarFechaInicioNull(tdie);
        validarFechaFinNull(tdie);
        validarNombreTipoDocIdentidadNull(tdie);
        validarNombreTipoDocIdentidadVacio(tdie);
    }

    private void validarDecripcionNull(TipoDocIdentidadEntidad tdie) throws ExceptionDisparador {
        if (tdie.getDescripcion() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la descripcion del tipo de documento de identidad");
        }
    }

    private void validarDescripcionVacio(TipoDocIdentidadEntidad tdie) throws ExceptionDisparador {
        if ("".equalsIgnoreCase(tdie.getDescripcion())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la descripcion del tipo de documento de identidad");
        }
    }

    private void validarFechaInicioNull(TipoDocIdentidadEntidad tdie) throws ExceptionDisparador {
        if (tdie.getFechaInicio() == null) {
            tdie.setFechaInicio(new Date());
        }
    }

    private void validarFechaFinNull(TipoDocIdentidadEntidad tdie) throws ExceptionDisparador {
        if (tdie.getFechaFin() == null) {
            tdie.setFechaFin(new Date());
        }
    }

    private void validarNombreTipoDocIdentidadNull(TipoDocIdentidadEntidad tdie) throws ExceptionDisparador {
        if (tdie.getNombreTipoDocIdentidad() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del tipo de documento de identidad");
        }
    }

    private void validarNombreTipoDocIdentidadVacio(TipoDocIdentidadEntidad tdie) throws ExceptionDisparador {
        if ("".equalsIgnoreCase(tdie.getNombreTipoDocIdentidad())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del tipo de documento de identidad");
        }
    }
}
