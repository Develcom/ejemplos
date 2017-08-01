package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;


/**
 * The persistent class for the I032T_GEOGRAFICO database table.
 */
@Entity
@Table(name = "I032T_GEOGRAFICO")
@NamedQuery(name = "GeograficoEntity.findAll", query = "SELECT g FROM GeograficoEntidad g")
@ClassEspecification(name = "Geográfico", identifier = "NbGeografico")
public class GeograficoEntidad implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CO_GEOGRAFICO", unique = true, nullable = false)
    @SequenceGenerator(name = "GEOGRAFICO_SEQ", sequenceName = "I032S_CO_GEOGRAFICO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEOGRAFICO_SEQ")
    private long id;

    @Column(name = "NB_GEOGRAFICO", length = 50)
    private String nbGeografico;

    @Column(name = "NU_PADRE_GEOGRAFICO")
    private Long nuPadreGeografico;

    // bi-directional many-to-one association to TipoGeograficoEntity
    @ManyToOne
    @JoinColumn(name = "CO_TIPO_GEOGRAFICO", nullable = false)
    private TipoGeograficoEntidad tipoGeografico;
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    private String codigoSincronizacion;

    public GeograficoEntidad() {
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

    public Long getNuPadreGeografico() {
        return this.nuPadreGeografico;
    }

    public void setNuPadreGeografico(Long nuPadreGeografico) {
        this.nuPadreGeografico = nuPadreGeografico;
    }


    public TipoGeograficoEntidad getTipoGeografico() {
        return this.tipoGeografico;
    }

    /**
     * @param tipoGeografico the tipoGeografico to set
     */
    public void setTipoGeografico(TipoGeograficoEntidad tipoGeografico) {
        this.tipoGeografico = tipoGeografico;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeograficoEntidad that = (GeograficoEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }
}
