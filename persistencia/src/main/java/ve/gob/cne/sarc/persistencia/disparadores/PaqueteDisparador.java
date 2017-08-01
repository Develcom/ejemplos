package ve.gob.cne.sarc.persistencia.disparadores;

import ve.gob.cne.sarc.persistencia.disparadores.ExceptionDisparador;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteEntidad;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created with IntelliJ IDEA.
 * User: Jorge Maguiña
 * Date: 18/05/16
 * Time: 01:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaqueteDisparador {

    /**
     * Validacion para que tenga una oficina asociado.
     * @param paquete
     * @throws ExceptionDisparador
     */
    @PrePersist
    @PreUpdate
    public void validacionesPaquete(PaqueteEntidad paquete) throws ExceptionDisparador {
        validarPaqueteOficina(paquete);

    }

    /**
     * Validacion que verifica existencia de un oficina en el paquete.
     * @param paquete
     * @throws ExceptionDisparador
     */
    private void validarPaqueteOficina(PaqueteEntidad paquete) throws ExceptionDisparador {

        if (paquete.getOficina() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la oficina correspondiente a la Tarea delegada");
        }
    }
}
