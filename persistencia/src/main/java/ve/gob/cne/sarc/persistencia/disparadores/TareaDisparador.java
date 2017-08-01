package ve.gob.cne.sarc.persistencia.disparadores;


import ve.gob.cne.sarc.persistencia.entidades.TareaEntidad;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created with IntelliJ IDEA.
 * User: Jorge Maguiña
 * Date: 18/05/16
 * Time: 01:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class TareaDisparador {

    /**
     * Validaciones de las tareas de un flujo.
     * @param tarea
     * @throws ve.gob.cne.sarc.persistencia.disparadores.ExceptionDisparador
     */
    @PrePersist
    @PreUpdate
    public void validacionesTarea(TareaEntidad tarea) throws ExceptionDisparador {
        validacionesTramite(tarea);
    }

    private void validacionesTramite(TareaEntidad estatus) throws ExceptionDisparador {
        if (estatus.getTramitePaqueteEntidad() == null) {
            throw new ExceptionDisparador("2#Debe introducir el tramite que identifique al " +
                    "la tarea de un paquete");
        }
    }

}
