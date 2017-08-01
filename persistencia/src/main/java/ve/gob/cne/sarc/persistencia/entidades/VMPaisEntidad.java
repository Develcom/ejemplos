package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the J001VM_PAIS database table.
 */
@Entity
@Table(name = "J001VM_PAIS")
@NamedQuery(name = "VMPaisEntidad.findAll", query = "SELECT v FROM VMPaisEntidad v ORDER BY v.nbGeografico asc")
public class VMPaisEntidad implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5417397955762473676L;

    @Id
    @Column(name = "CO_GEOGRAFICO")
    private long id;

    @Column(name = "NB_GEOGRAFICO")
    private String nbGeografico;

    public VMPaisEntidad() {
        // Constructor vacío
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNbGeografico() {
        return this.nbGeografico;
    }

    public void setNbGeografico(String nbGeografico) {
        this.nbGeografico = nbGeografico;
    }

}