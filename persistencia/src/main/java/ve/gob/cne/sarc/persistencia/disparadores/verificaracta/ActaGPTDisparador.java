package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaGPTEntidad;

/**
 * ActaGPTDisparador.java
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ActaGPT Entidad
 * @version 1.0
 * 23 de jun. de 2016
 * @author Domingo Rondon
 */
public class ActaGPTDisparador {
    
    /**
     * validacionesActaGPT
     * Este método recibe como parámetro la entidad ActaGPT y llama a otros
     * métodos privados para validar los atributos que no puedan ser nulos,
     * vacios o deban cumplir con un formato específico. 
     * NOTA::: En este caso como esta acta viene de un proceso
     * no se tiene certeza si la mayoria de los campos vendran con data asi sean obligatorios
     * es por ello que este proceso queda hasta ahora vacio
     * @since 23 de jun. de 2016
     * @param actraGPT
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-217
     */
    @PrePersist
    @PreUpdate
    public void validacionesActaGPT(ActaGPTEntidad actraGPT) throws Exception {
        
       
    }

}
