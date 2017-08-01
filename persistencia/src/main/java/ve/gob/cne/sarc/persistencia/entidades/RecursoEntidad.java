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
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;


/**
 * The persistent class for the I014T_RECURSO database table.
 * 
 */
@Entity
@Table(name="I014T_RECURSO")
@NamedQuery(name="RecursoEntidad.findAll", query="SELECT r FROM RecursoEntidad r")
@ClassEspecification(name = "Recurso", identifier = "NbRecurso", generatesTask=SincronizationPolicy.BROADCAST)
public class RecursoEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_RECURSO")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    @SequenceGenerator(name = "RECURSO_SEQ", sequenceName = "I014S_CO_RECURSO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECURSO_SEQ")
    private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="FE_FIN")
    private Date fechaFin;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INICIO")
    private Date fechaInicio;

	@Column(name="NB_RECURSO")
    @FieldEspecification(required = true, size = 50, label = "Acceso a Recurso", order = 1, step = 0, placeHolder = "/xxxx/xxxx")
	private String nbRecurso;

	@Column(name="TX_DESCRIPCION")
    @FieldEspecification(required = true, input = FieldEspecification.InputType.TEXTAREAINPUT, label = "Descripci&oacute;n", size = 2000)
	private String txDescripcion;

	//bi-directional many-to-one association to PermisoRecursoEntidad
	@OneToMany(mappedBy="coRecurso")
	@FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
	private List<PermisoRecursoEntidad> coPermisoRecursos;
	
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;
    
    public RecursoEntidad() {
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

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNbRecurso() {
        return this.nbRecurso;
    }

    public void setNbRecurso(String nbRecurso) {
        this.nbRecurso = nbRecurso;
    }
    
    public String getTxDescripcion() {
		return this.txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

	public List<PermisoRecursoEntidad> getCoPermisoRecursos() {
		return coPermisoRecursos;
	}

	public void setCoPermisoRecursos(List<PermisoRecursoEntidad> coPermisoRecursos) {
		this.coPermisoRecursos = coPermisoRecursos;
	}

	/**
	 * Método para agregar un PermisoRecurso
	 * @param coPermisoRecurso
	 * @return
	 */
	public PermisoRecursoEntidad addCoPermisoRecurso(PermisoRecursoEntidad coPermisoRecurso) {
		getCoPermisoRecursos().add(coPermisoRecurso);
		coPermisoRecurso.setCoRecurso(this);

		return coPermisoRecurso;
	}

	/**
	 * Método para eliminar un PermisoRecurso
	 * @param coPermisoRecurso
	 * @return
	 */
	public PermisoRecursoEntidad removeCoPermisoRecurso(PermisoRecursoEntidad coPermisoRecurso) {
		getCoPermisoRecursos().remove(coPermisoRecurso);
		coPermisoRecurso.setCoRecurso(null);

		return coPermisoRecurso;
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
        RecursoEntidad that = (RecursoEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}