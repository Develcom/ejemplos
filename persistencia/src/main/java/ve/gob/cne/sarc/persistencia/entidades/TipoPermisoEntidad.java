package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;

/**
 * The persistent class for the I012T_TIPO_PERMISO database table.
 * 
 */
@Entity
@Table(name="I012T_TIPO_PERMISO")
@NamedQuery(name="TipoPermisoEntidad.findAll", query="SELECT t FROM TipoPermisoEntidad t")
@ClassEspecification(name = "Tipo de Permiso", identifier = "NbPermiso", generatesTask = SincronizationPolicy.BROADCAST)
public class TipoPermisoEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_PERMISO")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    @SequenceGenerator(name = "TIPO_PERMISO_SEQ", sequenceName = "I012S_CO_PERMISO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_PERMISO_SEQ")
    private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="FE_FIN")
    private Date fechaFin;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INICIO")
    private Date fechaInicio;

	@Column(name="NB_PERMISO")
    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 1, step = 0)
	private String nbPermiso;

	@Column(name="TX_DESCRIPCION")
    @FieldEspecification(required = true, input = FieldEspecification.InputType.TEXTAREAINPUT, label = "Descripci&oacute;n", size = 2000)
	private String txDescripcion;
	
	//bi-directional many-to-one association to PermisologiaEntidad
	@OneToMany(mappedBy="coTipoPermiso")
	private List<PermisologiaEntidad> coPermisologias;

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

	public TipoPermisoEntidad() {
		// Metodo Constructor
	}

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date feFin) {
        this.fechaFin = feFin;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date feInicio) {
        this.fechaInicio = feInicio;
    }

	public String getNbPermiso() {
		return this.nbPermiso;
	}

	public void setNbPermiso(String nbPermiso) {
		this.nbPermiso = nbPermiso;
	}

	public String getTxDescripcion() {
		return this.txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }
	public List<PermisologiaEntidad> getCoPermisologias() {
		return this.coPermisologias;
	}

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

	public void setCoPermisologias(List<PermisologiaEntidad> coPermisologias) {
		this.coPermisologias = coPermisologias;
	}

	public PermisologiaEntidad addCoPermisologia(PermisologiaEntidad coPermisologia) {
		getCoPermisologias().add(coPermisologia);
		coPermisologia.setCoTipoPermiso(this);

		return coPermisologia;
	}

	public PermisologiaEntidad removeCoPermisologia(PermisologiaEntidad coPermisologia) {
		getCoPermisologias().remove(coPermisologia);
		coPermisologia.setCoTipoPermiso(null);

		return coPermisologia;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoPermisoEntidad that = (TipoPermisoEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}