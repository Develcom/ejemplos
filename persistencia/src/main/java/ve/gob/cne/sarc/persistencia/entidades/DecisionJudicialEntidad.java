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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.DecisionJudicialDisparador;

/**
 * DecisionJudicialEntidad.java
 *
 * @descripcion Se crea la clase DecisionJudicialEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "X024T_DECISION_JUDICIAL")
@EntityListeners({DecisionJudicialDisparador.class})

public class DecisionJudicialEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_DECISION_JUDICIAL", nullable = false, length = 22)
    @SequenceGenerator(name = "ECISION_JUDICIAL_SEQ", sequenceName = "X024S_CO_DECISION_JUDICIAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECISION_JUDICIAL_SEQ")
    private long id;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PROCEDENCIA", name = "CO_PROCEDENCIA", nullable = false)
    private ProcedenciaEntidad procedencia;

    @Basic(optional = false)
    @Column(name = "NB_TRIBUNAL", nullable = false, length = 100)
    private String nombreTribunal;

    @Basic(optional = false)
    @Column(name = "NU_SENTENCIA", nullable = false, length = 20)
    private String numeroSentencia;

    @Basic(optional = true)
    @Column(name = "NU_DOCUMENTO_IDENTIDAD", nullable = true, length = 20)
    private String cedulaJuez;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE_JUEZ", nullable = false, length = 50)
    private String primerNombreJuez;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_JUEZ", nullable = true, length = 50)
    private String segundoNombreJuez;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO_JUEZ", nullable = false, length = 50)
    private String primerApellidoJuez;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_JUEZ", nullable = true, length = 50)
    private String segundoApellidoJuez;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_SENTENCIA", nullable = false)
    private Date fechaSentencia;

    public DecisionJudicialEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProcedenciaEntidad getProcedencia() {
        return this.procedencia;
    }

    public void setProcedencia(ProcedenciaEntidad procedencia) {
        this.procedencia = procedencia;
    }

    public String getNombreTribunal() {
        return this.nombreTribunal;
    }

    public void setNombreTribunal(String nombreTribunal) {
        this.nombreTribunal = nombreTribunal;
    }

    public String getNumeroSentencia() {
        return this.numeroSentencia;
    }

    public void setNumeroSentencia(String numeroSentencia) {
        this.numeroSentencia = numeroSentencia;
    }

    public String getCedulaJuez() {
        return this.cedulaJuez;
    }

    public void setCedulaJuez(String cedulaJuez) {
        this.cedulaJuez = cedulaJuez;
    }

    public String getPrimerNombreJuez() {
        return primerNombreJuez;
    }

    public void setPrimerNombreJuez(String primerNombreJuez) {
        this.primerNombreJuez = primerNombreJuez;
    }

    public String getSegundoNombreJuez() {
        return segundoNombreJuez;
    }

    public void setSegundoNombreJuez(String segundoNombreJuez) {
        this.segundoNombreJuez = segundoNombreJuez;
    }

    public String getPrimerApellidoJuez() {
        return primerApellidoJuez;
    }

    public void setPrimerApellidoJuez(String primerApellidoJuez) {
        this.primerApellidoJuez = primerApellidoJuez;
    }

    public String getSegundoApellidoJuez() {
        return segundoApellidoJuez;
    }

    public void setSegundoApellidoJuez(String segundoApellidoJuez) {
        this.segundoApellidoJuez = segundoApellidoJuez;
    }

    public Date getFechaSentencia() {
        return fechaSentencia;
    }

    public void setFechaSentencia(Date fechaSentencia) {
        this.fechaSentencia = fechaSentencia;
    }

}
