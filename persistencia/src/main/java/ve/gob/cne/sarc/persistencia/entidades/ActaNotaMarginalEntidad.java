package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ve.gob.cne.sarc.persistencia.disparadores.ActaNotaMarginalDisparador;

/**
 * ActaNotaMarginalEntidad.java
 *
 * @descripcion Se crea la clase ActaNotaMarginalEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
@Entity
@Table(name = "X044T_ACTA_NOTA_MARGINAL")
@EntityListeners({ActaNotaMarginalDisparador.class})

public class ActaNotaMarginalEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ACTA_NOTA_MARGINAL", nullable = false, length = 22)
    @SequenceGenerator(name = "ACTA_NOTA_MARGINAL_SEQ", sequenceName = "X044S_CO_ACTA_NOTA_MARGINAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTA_NOTA_MARGINAL_SEQ")
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 100)
    private String descripcion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA", nullable = false)
    private ActaEntidad acta;

    public ActaNotaMarginalEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }
}
