package ve.gob.cne.sarc.persistencia.entidades;


import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.CompletationStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * The persistent class for the C074T_ROL_OPERADOR database table.
 * 
 */
@Entity
@Table(name="C074T_ROL_OPERADOR")
@NamedQuery(name="RolOperadorEntidad.findAll", query="SELECT r FROM RolOperadorEntidad r")
@ClassEspecification(name = "Rol Operador", identifier = "Id",
generatesTask = ClassEspecification.SincronizationPolicy.NONE)
public class RolOperadorEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_ROL_OPERADOR")
    @SequenceGenerator(name = "ROL_OPERADOR_SEQ", sequenceName = "C074S_CO_ROL_OPERADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_OPERADOR_SEQ")
    @FieldEspecification(hide=true, type= "java.lang.Long", order=0, step=0)
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "FE_FIN")
	private Date fechaFin;
	
    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INICIO")
	private Date fechaInicio;

	@Column(name="NU_ORDEN")
	@FieldEspecification(required = true, type = "java.lang.Integer", size = 19, label = "Orden", order = 4, step = 0, constraints = ">=0")
	private BigDecimal nuOrden;

    @Basic(optional = true)
    @Column(name = "OPERADOR_LOGICO", nullable = true, length = 20)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
        options = FieldEspecification.CompletationStrategy.MANUAL_OPTIONS,
        manualOptions = "AND:And,OR:Or",
        label = "Operador",
        order = 3,
        step = 0)
	private String operadorLogico;

	//bi-directional many-to-one association to RolSeguridadEntidad
	@ManyToOne
	@JoinColumn(name="CO_ROL")
    @FieldEspecification(required= true, input = InputType.SELECT, 
        options=CompletationStrategy.DB_OPTIONS, 
        type= "java.lang.Long",  
        label="Rol", 
        order=1, 
        step=0)
	private RolEntidad rolOperador;

	//bi-directional many-to-one association to PermisologiaSeguridadEntidad
	@ManyToOne
	@JoinColumn(name="CO_PERMISOLOGIA")
	@FieldEspecification(required= true, input = InputType.SELECT, 
    options=CompletationStrategy.DB_OPTIONS, 
    type= "java.lang.Long",  
    label="Permisolog&iacute;a", 
    order=2, 
    step=0)
	private PermisologiaEntidad coPermisologia;

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

	public RolOperadorEntidad() {
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

	public BigDecimal getNuOrden() {
		return this.nuOrden;
	}

	public void setNuOrden(BigDecimal nuOrden) {
		this.nuOrden = nuOrden;
	}

	public String getOperadorLogico() {
		return this.operadorLogico;
	}

	public void setOperadorLogico(String operadorLogico) {
		this.operadorLogico = operadorLogico;
	}

	public RolEntidad getRolOperador() {
		return rolOperador;
	}

	public void setRolOperador(RolEntidad rolOperador) {
		this.rolOperador = rolOperador;
	}

	public PermisologiaEntidad getCoPermisologia() {
		return coPermisologia;
	}

	public void setCoPermisologia(PermisologiaEntidad coPermisologia) {
		this.coPermisologia = coPermisologia;
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
        RolOperadorEntidad that = (RolOperadorEntidad) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}