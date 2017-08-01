package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the J002VM_ESTADO database table.
 */
@Entity
@Table(name = "J002VM_ESTADO")
@NamedQuery(name = "VMEstadoEntidad.findAll", query = "SELECT v FROM VMEstadoEntidad v")
public class VMEstadoEntidad implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5439331758351710012L;

    @Id
    @Column(name = "CO_GEOGRAFICO")
    private long id;

    @Column(name = "NB_GEOGRAFICO")
    private String nbGeografico;

    @Column(name = "NU_PADRE_GEOGRAFICO")
    private BigDecimal nuPadreGeografico;

    public VMEstadoEntidad() {
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