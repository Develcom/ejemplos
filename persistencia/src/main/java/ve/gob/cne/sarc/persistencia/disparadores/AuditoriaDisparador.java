package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * AuditoriaDisparador.java
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version 
 * 31 de oct. de 2016
 * @author Domingo Rondon
 */
public class AuditoriaDisparador {
    @PrePersist
    public void insertandoRegistro(Object obj) {
        try {
        } catch (NullPointerException ex) {
            throw (ex);
        }
    }

    @PostPersist
    public void registroInsertado(Object obj) {

    }

    @PreUpdate
    public void modificandoRegistro(Object obj) {
    }

    @PostUpdate
    public void registroModificado(Object obj) {

    }
}
