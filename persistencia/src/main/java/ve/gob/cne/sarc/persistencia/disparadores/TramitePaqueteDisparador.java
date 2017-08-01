package ve.gob.cne.sarc.persistencia.disparadores;

import ve.gob.cne.sarc.persistencia.disparadores.ExceptionDisparador;
import ve.gob.cne.sarc.persistencia.entidades.TramitePaqueteEntidad;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created with IntelliJ IDEA.
 * User: JORGE
 * Date: 18/05/16
 * Time: 01:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TramitePaqueteDisparador {

    /**
     * Validaciones del tramite de un flujo.
     * @param tramite
     * @throws ve.gob.cne.sarc.persistencia.disparadores.ExceptionDisparador
     */
    @PrePersist
    @PreUpdate
    public void validacionesTramite(TramitePaqueteEntidad tramite) throws ExceptionDisparador {
        validarNombreNulo(tramite);
    }

    private void validarNombreNulo(TramitePaqueteEntidad estatus) throws ExceptionDisparador {
        if (estatus.getNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre tramite que identifique al " +
                    "la tarea de un paquete");
        }
    }

}
