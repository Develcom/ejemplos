package ve.gob.cne.sarc.persistencia.entidades;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.PoderMandatorioDisparador;

/**
 * PoderMandatorioEntidad.java
 *
 * @descripcion Se crea la clase PoderMandatorioEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.1 05/12/2016
 *
 */
@Entity
@Table(name = "X030T_PODER_MANDATARIO")
@EntityListeners({PoderMandatorioDisparador.class})

public class PoderMandatorioEntidad implements java.io.Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PODER_MANDATARIO", nullable = false, length = 22)
    @SequenceGenerator(name = "PODER_MANDATARIO_SEQ", sequenceName = "X030S_CO_PODER_MANDATARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PODER_MANDATARIO_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA", unique = true, nullable = false)
    private ActaEntidad acta;

    @Basic(optional = false)
    @Column(name = "NB_NOTARIA", nullable = false, length = 50)
    private String notaria;

    @Basic(optional = true)
    @Column(name = "TX_DATOS", length = 500)
    private String datos;

    @Basic(optional = true)
    @Column(name = "NU_PODER", length = 22)
    private long numeroPoderNotaria;

    @Basic(optional = true)
    @Column(name = "TX_TOMO", length = 200)
    private String textoTomo;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_PODER", length = 10)
    private Date fechaPoder;

    public PoderMandatorioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ActaEntidad getActa() {
        return this.acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

    public String getDatos() {
        return this.datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public long getNumeroPoderNotaria() {
        return this.numeroPoderNotaria;
    }

    public void setNumeroPoderNotaria(long numeroPoderNotaria) {
        this.numeroPoderNotaria = numeroPoderNotaria;
    }

    public String getTextoTomo() {
        return this.textoTomo;
    }

    public void setTextoTomo(String textoTomo) {
        this.textoTomo = textoTomo;
    }

    public Date getFechaPoder() {
        return this.fechaPoder;
    }

    public void setFechaPoder(Date fechaPoder) {
        this.fechaPoder = fechaPoder;
    }

    public String getNotaria() {
        return notaria;
    }

    public void setNotaria(String notaria) {
        this.notaria = notaria;
    }
}
