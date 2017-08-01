package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ve.gob.cne.sarc.persistencia.disparadores.ProcedenciaDisparador;

/**
 * ProcedenciaEntidad.java
 *
 * @desccripcion Se crea la clase ProcedenciaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X025T_PROCEDENCIA")
@EntityListeners({ProcedenciaDisparador.class})

public class ProcedenciaEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PROCEDENCIA", nullable = false, length = 22)
    @SequenceGenerator(name = "PROCEDENCIA_SEQ", sequenceName = "X025S_CO_PROCEDENCIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROCEDENCIA_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TIPO_PROCEDENCIA", referencedColumnName = "CO_TIPO_PROCEDENCIA", nullable = false)
    private TipoProcedenciaEntidad tipoProcedencia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA", nullable = false)
    private ActaEntidad acta;

    @Basic(optional = true)
    @Column(name = "TX_EXTRACTO", length = 500)
    private String textoExtracto;

    @OneToOne(mappedBy = "procedencia", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private MedidaProteccionEntidad medidaProteccion;

    @OneToOne(mappedBy = "procedencia", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DecisionJudicialEntidad decisionJudicial;

    @OneToOne(mappedBy = "procedencia", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ExtemporaneaEntidad extemporanea;

    public ProcedenciaEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoProcedenciaEntidad getTipoProcedencia() {
        return this.tipoProcedencia;
    }

    public void setTipoProcedencia(TipoProcedenciaEntidad tipoProcedencia) {
        this.tipoProcedencia = tipoProcedencia;
    }

    public ActaEntidad getActa() {
        return this.acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

    public String getTextoExtracto() {
        return this.textoExtracto;
    }

    public void setTextoExtracto(String textoExtracto) {
        this.textoExtracto = textoExtracto;
    }

    public MedidaProteccionEntidad getMedidaProteccion() {
        return this.medidaProteccion;
    }

    public void setMedidaProteccion(MedidaProteccionEntidad medidaProteccion) {
        this.medidaProteccion = medidaProteccion;
    }

    public DecisionJudicialEntidad getDecisionJudicial() {
        return this.decisionJudicial;
    }

    public void setDecisionJudicial(DecisionJudicialEntidad decisionJudicial) {
        this.decisionJudicial = decisionJudicial;
    }

    public ExtemporaneaEntidad getExtemporanea() {
        return extemporanea;
    }

    public void setExtemporanea(ExtemporaneaEntidad extemporanea) {
        this.extemporanea = extemporanea;
    }
}
