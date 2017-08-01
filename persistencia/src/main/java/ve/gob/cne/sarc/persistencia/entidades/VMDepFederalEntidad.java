package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the J005VM_DEP_FEDERAL database table.
 */
@Entity
@Table(name = "J005VM_DEPENDENCIA_FEDERAL")
@NamedQuery(name = "VMDepFederalEntidad.findAll", query = "SELECT v FROM VMDepFederalEntidad v")
public class VMDepFederalEntidad implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CO_GEOGRAFICO")
    private long id;

    @Column(name = "NB_GEOGRAFICO")
    private String nbGeografico;

    @Column(name = "NU_PADRE_GEOGRAFICO")
    private BigDecimal nuPadreGeografico;

    public VMDepFederalEntidad() {
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
