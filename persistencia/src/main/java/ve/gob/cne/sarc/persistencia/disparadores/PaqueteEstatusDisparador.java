package ve.gob.cne.sarc.persistencia.disparadores;

import ve.gob.cne.sarc.persistencia.entidades.PaqueteEstatusEntidad;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created with IntelliJ IDEA. User: JORGE Date: 18/05/16 Time: 01:28 PM To change this template use File | Settings |
 * File Templates.
 */
public class PaqueteEstatusDisparador {

    /**
     * Validaciones relacioandas al estatus de un paquete.
     *
     * @param estatus
     * @throws ExceptionDisparador
     */
    @PrePersist
    @PreUpdate
    public void validacionesEstatus(PaqueteEstatusEntidad estatus) throws ExceptionDisparador {
        validarCodigoNulo(estatus);
        validarCodigoVacio(estatus);
    }

    private void validarCodigoNulo(PaqueteEstatusEntidad estatus) throws ExceptionDisparador {
        if (estatus.getCodigo() == null) {
            throw new ExceptionDisparador("2#Debe introducir el código que identifique al "
                    + "estatus de un paquete");
        }
    }

    private void validarCodigoVacio(PaqueteEstatusEntidad estatus) throws ExceptionDisparador {
        if ("".equals(estatus.getCodigo().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el código que identifique al "
                    + "estatus de un paquete");
        }
    }

}
