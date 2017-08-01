package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the C073T_PERMISO_RECURSO database table.
 * 
 */
@Entity
@Table(name="C073T_PERMISO_RECURSO")
@NamedQuery(name="PermisoRecursoEntidad.findAll", query="SELECT p FROM PermisoRecursoEntidad p")
@ClassEspecification(name = "Permiso Recurso", identifier = "Id",
generatesTask = ClassEspecification.SincronizationPolicy.NONE)
public class PermisoRecursoEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_PERMISO_RECURSO")
    @SequenceGenerator(name = "PERMISO_RECURSO_SEQ", sequenceName = "C073S_CO_PERMISO_RECURSO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISO_RECURSO_SEQ")
	private long id;

	@Column(name="GRUPO_RECURSO")
	private BigDecimal grupoRecurso;

	//bi-directional many-to-one association to PermisologiaSeguridadEntidad
	@ManyToOne
	@JoinColumn(name="CO_PERMISOLOGIA", referencedColumnName = "CO_PERMISOLOGIA", nullable = false)
	private PermisologiaEntidad coPermisologia;

	//bi-directional many-to-one association to RecursoSeguridadEntidad
	@ManyToOne
	@JoinColumn(name="CO_RECURSO", referencedColumnName = "CO_RECURSO", nullable = false)
	private RecursoEntidad coRecurso;

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
    
    public PermisoRecursoEntidad() {
    	// Metodo Constructor
    }
    
    public long getId() {
		return this.id;
	}
    
    public void setId(long id) {
		this.id = id;
	}
    
	public BigDecimal getGrupoRecurso() {
		return this.grupoRecurso;
	}

	public void setGrupoRecurso(BigDecimal grupoRecurso) {
		this.grupoRecurso = grupoRecurso;
	}
	
	public PermisologiaEntidad getCoPermisologia() {
		return coPermisologia;
	}

	public void setCoPermisologia(PermisologiaEntidad coPermisologia) {
		this.coPermisologia = coPermisologia;
	}

	public RecursoEntidad getCoRecurso() {
		return coRecurso;
	}

	public void setCoRecurso(RecursoEntidad coRecurso) {
		this.coRecurso = coRecurso;
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

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PermisoRecursoEntidad that = (PermisoRecursoEntidad) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}