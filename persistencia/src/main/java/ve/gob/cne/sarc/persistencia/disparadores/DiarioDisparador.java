package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.DiarioEntidad;

/**
 * DiarioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase DiarioEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @version 1.1 12/08/2016
 * @author Oscar Montilla
 *
 */
public class DiarioDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad DiarioEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param diario Objeto del modelo ontologico que contiene la informacion de Diario
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesDiario(DiarioEntidad diario) throws ExceptionDisparador {
        validarSolicitudNulo(diario);
        validarFechaRegistroNulo(diario);
        validarEstatusNulo(diario);
    }

    private void validarSolicitudNulo(DiarioEntidad diario) throws ExceptionDisparador {
        if (diario.getSolicitud() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la solicitud correspondiente al diario");
        }
    }

    private void validarFechaRegistroNulo(DiarioEntidad diario) {
        if (diario.getFechaRegistro() == null) {
            diario.setFechaRegistro(new Date());
        }
    }

    private void validarEstatusNulo(DiarioEntidad diario) throws ExceptionDisparador {
        if (diario.getEstatus() == null) {
            throw new ExceptionDisparador("2#Debe introducir el estatus correspondiente al diario");
        }
    }
}
