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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.CompletationStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

/**
 * The persistent class for the C068T_PERMISOLOGIA database table.
 * 
 */
@Entity
@Table(name = "C068T_PERMISOLOGIA")

@NamedQueries({
    @NamedQuery(name = "PermisologiaEntidad.findAll", query = "SELECT p FROM PermisologiaEntidad p")})
@ClassEspecification(name = "Permisolog&iacute;a", identifier = "Id",
generatesTask = ClassEspecification.SincronizationPolicy.NONE)
public class PermisologiaEntidad implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CO_PERMISOLOGIA")
    @SequenceGenerator(name = "PERMISOLOGIA_SEQ", sequenceName = "C068S_CO_PERMISOLOGIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISOLOGIA_SEQ")
    @FieldEspecification(hide=true, type= "java.lang.Long", order=0, step=0)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_FIN")
    private Date fechaFin;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INICIO")
    private Date fechaInicio;

    // bi-directional many-to-one association to TipoPermisoEntidad
    @ManyToOne
    @JoinColumn(name = "CO_PERMISO")
    @FieldEspecification(required= true, input = InputType.SELECT, 
        options=CompletationStrategy.DB_OPTIONS, 
        type= "java.lang.Long",  
        label="Tipo de Permiso", 
        order=2, 
        step=0)
	private TipoPermisoEntidad coTipoPermiso;

    // bi-directional many-to-one association to ModuloEntidad
    @ManyToOne
    @JoinColumn(name = "CO_MODULO")
    @FieldEspecification(required= true, input = InputType.SELECT, 
    options=CompletationStrategy.DB_OPTIONS, 
    type= "java.lang.Long",  
    label="M&oacute;dulo", 
    order=1, 
    step=0)
	private ModuloSeguridadEntidad coModulo;
    
    //bi-directional many-to-one association to PermisoRecursoSeguridadEntidad
  	@OneToMany(mappedBy="coPermisologia")
  	@FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
  	private List<PermisoRecursoEntidad> coPermisoRecursos;

  	//bi-directional many-to-one association to RolOperadorSeguridadEntidad
  	@OneToMany(mappedBy="coPermisologia")
  	@FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
  	private List<RolOperadorEntidad> coRolOperadors;

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

	public PermisologiaEntidad() {
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

	public TipoPermisoEntidad getCoTipoPermiso() {
		return coTipoPermiso;
	}

	public void setCoTipoPermiso(TipoPermisoEntidad coTipoPermiso) {
		this.coTipoPermiso = coTipoPermiso;
	}

	public ModuloSeguridadEntidad getCoModulo() {
		return coModulo;
	}

	public void setCoModulo(ModuloSeguridadEntidad coModulo) {
		this.coModulo = coModulo;
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
		coPermisoRecurso.setCoPermisologia(this);

		return coPermisoRecurso;
	}

	/**
	 * Método para eliminar un PermisoRecurso
	 * @param coPermisoRecurso
	 * @return
	 */
	public PermisoRecursoEntidad removeCoPermisoRecurso(PermisoRecursoEntidad coPermisoRecurso) {
		getCoPermisoRecursos().remove(coPermisoRecurso);
		coPermisoRecurso.setCoPermisologia(null);

		return coPermisoRecurso;
	}

	public List<RolOperadorEntidad> getCoRolOperadors() {
		return this.coRolOperadors;
	}

	public void setCoRolOperadors(List<RolOperadorEntidad> coRolOperadors) {
		this.coRolOperadors = coRolOperadors;
	}

	/**
	 * Método para agregar un RolOperador
	 * @param coRolOperador
	 * @return
	 */
	public RolOperadorEntidad addCoRolOperador(RolOperadorEntidad coRolOperador) {
		getCoRolOperadors().add(coRolOperador);
		coRolOperador.setCoPermisologia(this);

		return coRolOperador;
	}

	/**
	 * Método para elimnar un RolOperador
	 * @param coRolOperador
	 * @return
	 */
	public RolOperadorEntidad removeCoRolOperador(RolOperadorEntidad coRolOperador) {
		getCoRolOperadors().remove(coRolOperador);
		coRolOperador.setCoPermisologia(null);

		return coRolOperador;
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

        PermisologiaEntidad that = (PermisologiaEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}