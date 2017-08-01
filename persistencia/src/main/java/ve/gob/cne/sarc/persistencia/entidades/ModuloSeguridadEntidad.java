package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the I013T_MODULO database table.
 * 
 */
@Entity
@Table(name="I013T_MODULO")
@NamedQuery(name="ModuloSeguridadEntidad.findAll", query="SELECT m FROM ModuloSeguridadEntidad m")
@ClassEspecification(name = "M&oacute;dulo Seguridad", identifier = "nbModulo",
        generatesTask = SincronizationPolicy.BROADCAST)
public class ModuloSeguridadEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @Basic(optional = false)
    @Column(name = "CO_MODULO", nullable = false, unique = true, length = 5)
    @SequenceGenerator(name = "MODULO_SEQ", sequenceName = "I013S_CO_MODULO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULO_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
	private long id;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "FE_FIN")
	private Date fechaFin;
	
	@Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
	private Date fechaInicio;

	@Basic(optional = false)
    @Column(name = "NB_MODULO", nullable = false, length = 50)
    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 2, step = 0)
	private String nbModulo;

	@Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    @FieldEspecification(required = true, size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
	private String txDescripcion;

	//bi-directional many-to-one association to PermisologiaEntidad
	@OneToMany(mappedBy="coModulo")
  	@FieldDeletionStrategy(options = DeletionStrategy.ALONE_OPTIONS)
	private List<PermisologiaEntidad> coPermisologias;

	@Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public ModuloSeguridadEntidad() {
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
	
	public String getNbModulo() {
		return this.nbModulo;
	}

	public void setNbModulo(String nbModulo) {
		this.nbModulo = nbModulo;
	}

	public String getTxDescripcion() {
		return this.txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

	public List<PermisologiaEntidad> getCoPermisologias() {
		return coPermisologias;
	}

	public void setCoPermisologias(List<PermisologiaEntidad> coPermisologias) {
		this.coPermisologias = coPermisologias;
	}
	
	/**
	 * Método que agrega una Permisología
	 * @param coPermisologia
	 * @return
	 */
	public PermisologiaEntidad addCoPermisologia(PermisologiaEntidad coPermisologia) {
		getCoPermisologias().add(coPermisologia);
		coPermisologia.setCoModulo(this);

		return coPermisologia;
	}

	/**
	 * Método que elimina una Permisología
	 * @param coPermisologia
	 * @return
	 */
	public PermisologiaEntidad removeCoPermisologia(PermisologiaEntidad coPermisologia) {
		getCoPermisologias().remove(coPermisologia);
		coPermisologia.setCoModulo(null);

		return coPermisologia;
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

        ModuloSeguridadEntidad that = (ModuloSeguridadEntidad) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}