package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaGPTDetalleEntidad;

/**
 * ActaGPTDetalleDisparador.java
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ActaGPTDetalleEntidad
 * @version 1.0
 * 23 de jun. de 2016
 * @author Domingo Rondon
 */
public class ActaGPTDetalleDisparador {
    
    /**
     * validacionesActaGPTDetalle
     * Este método recibe como parámetro la entidad ActaGPTDetalleEntidad y llama a otros
     * métodos privados para validar los atributos que no puedan ser nulos,
     * vacios o deban cumplir con un formato específico.
     * @since 23 de jun. de 2016
     * @param actaGPTDetalle
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-219
     */
    @PrePersist
    @PreUpdate
    public void validacionesActaGPTDetalle(ActaGPTDetalleEntidad actaGPTDetalle) throws Exception {
        
        validarValorNulo(actaGPTDetalle);      
    }
    
    /**
     * validarValorNulo
     * Método que Valida si el valor del Detalle del Acta llega Nulo
     * @since 23 de jun. de 2016
     * @param actaGPTDetalle
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-219
     */
    private void validarValorNulo(ActaGPTDetalleEntidad actaGPTDetalle) throws Exception {
        if (actaGPTDetalle.getTxValor() == null) {
            throw new Exception(
                    "Debe introducir el valor correspondiente al acta detalle");
        }
    }

}
