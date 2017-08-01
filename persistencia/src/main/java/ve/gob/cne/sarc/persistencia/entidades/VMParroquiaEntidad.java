package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the J004VM_PARROQUIA database table.
 */
@Entity
@Table(name = "J004VM_PARROQUIA")
@NamedQuery(name = "VMParroquiaEntidad.findAll", query = "SELECT v FROM VMParroquiaEntidad v")
public class VMParroquiaEntidad implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7130429949324630533L;

    @Id
    @Column(name = "CO_GEOGRAFICO")
    private long id;
    @Column(name = "NB_GEOGRAFICO")
    private String nbGeografico;

    @Column(name = "NU_PADRE_GEOGRAFICO")
    private BigDecimal nuPadreGeografico;

    public VMParroquiaEntidad() {
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

    public BigDecimal getNuPadreGeografico() {
        return this.nuPadreGeografico;
    }

    public void setNuPadreGeografico(BigDecimal nuPadreGeografico) {
        this.nuPadreGeografico = nuPadreGeografico;
    }

}