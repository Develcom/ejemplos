package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaTranscritaDetalleEntidad;

/**
 * ActaTranscritaDetalleDisparador.java
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ActaTranscritaDetalleEntidad
 * @version 1.0
 * 21 de jun. de 2016
 * @author Domingo Rondon
 */
public class ActaTranscritaDetalleDisparador {
    
    /**
     * validacionesActaTranscritaDetalle
     * Este método recibe como parámetro la entidad ActaTranscritaDetalleEntidad y llama a otros
     * métodos privados para validar los atributos que no puedan ser nulos,
     * vacios o deban cumplir con un formato específico.
     * @since 21 de jun. de 2016
     * @param actaTranscritaDetalle
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-220
     */
    @PrePersist
    @PreUpdate
    public void validacionesActaTranscritaDetalle(ActaTranscritaDetalleEntidad actaTranscritaDetalle) throws Exception {
        
        validarValorNulo(actaTranscritaDetalle);
        validarActaTranscritaNulo(actaTranscritaDetalle);
        
        
    }
    
    /**
     * validarValorNulo
     * Método que Valida si el valor del Detalle del Acta llega Nulo
     * @since 21 de jun. de 2016
     * @param actaTranscritaDetalle
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-220
     */
    private void validarValorNulo(ActaTranscritaDetalleEntidad actaTranscritaDetalle) throws Exception {
        if (actaTranscritaDetalle.getTxValor() == null) {
            throw new Exception(
                    "Debe introducir el valor correspondiente al acta detalle");
        }
    }
    
    /**
     * validarActaTranscritaNulo
     * Método que Valida que Cada Detalle contenga su referencia con un Acta
     * @since 21 de jun. de 2016
     * @param actaTranscritaDetalle
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-247
     */
    private void validarActaTranscritaNulo(ActaTranscritaDetalleEntidad actaTranscritaDetalle)
            throws Exception {
        if (actaTranscritaDetalle.getActaTranscrita() == null) {
            throw new Exception(
                    "Debe introducir el acta correspondiente al detalle");
        }
    }

}
