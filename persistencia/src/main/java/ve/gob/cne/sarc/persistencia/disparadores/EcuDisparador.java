package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EcuEntidad;

/**
 * EcuDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase EcuEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class EcuDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad CiudadanoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param ecu Objeto del modelo ontologico de Ecu
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesEcu(EcuEntidad ecu) throws ExceptionDisparador {
        validarFechaCreacionNulo(ecu);
    }

    public void validarFechaCreacionNulo(EcuEntidad ecu) throws ExceptionDisparador {
        if (ecu.getFechaCreacion() == null) {
            ecu.setFechaCreacion(new Date());
        }
    }
}
