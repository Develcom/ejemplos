package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TramitePaqueteEntidad;

/**
 * TramitePaqueteDisparador.java
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TramitePaqueteDisparador
 * @version 1.0
 * 23 de jun. de 2016
 * @author Domingo Rondon
 */
public class TramitePaqueteDisparador {
    
    /**
     * validacionesTramitePaquete
     * Este método recibe como parámetro la entidad Tramite Paquete y llama a otros
     * métodos privados para validar los atributos que no puedan ser nulos,
     * vacios o deban cumplir con un formato específico.
     * @since 23 de jun. de 2016
     * @param tramitePaquete
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-225
     */
    @PrePersist
    @PreUpdate
    public void validacionesTramitePaquete(TramitePaqueteEntidad tramitePaquete) throws Exception {
        
        //validarTramiteNulo(tramitePaquete);
        validarDescripcionNulo(tramitePaquete);
        
    }
    
    /**
     * validarTramiteNulo
     * Metodo que valida que el Tramite no se encuentre Nulo
     * @since 23 de jun. de 2016
     * @param tramitePaquete
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-225
     */
    /*private void validarTramiteNulo(TramitePaqueteEntidad tramitePaquete) throws Exception {
        if (tramitePaquete.getTramite() == null) {
            throw new Exception(
                    "Debe introducir el tramite correspondiente al tramite paquete");
        }
    }*/
    
    /**
     * validarDescripcionNulo
     * Metodo que valida que la descripción no se encuentre Nulo
     * @since 23 de jun. de 2016
     * @param tramitePaquete
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-225
     */
    private void validarDescripcionNulo(TramitePaqueteEntidad tramitePaquete) throws Exception {
        if (tramitePaquete.getDescripcion() == null) {
            throw new Exception(
                    "Debe introducir la descripcion correspondiente al tramite paquete");
        }
    }

}
