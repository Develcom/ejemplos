package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.disparadores.RolDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * RolUsuarioEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0 08 de jun. de 2016
 * @Descripcion Se crea la clase RolEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C033T_ROL")
@EntityListeners({RolDisparador.class})

@ClassEspecification(name = "Rol", identifier = "Nombre", canBeListed=false,
        generatesTask=SincronizationPolicy.NONE)
public class RolEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ROL", nullable = false, length = 22)
    @SequenceGenerator(name = "ROL_SEQ", sequenceName = "C033S_CO_ROL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "IDEN_ROL", unique = true, nullable = false, length = 5)
    @FieldEspecification(required = true, size = 5, label = "C&oacute;digo", order = 1, step = 0, placeHolder = "AAA00")
    private String codigo;

    @Basic(optional = false)
    @Column(name = "NB_ROL", nullable = false, length = 50)
    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    @FieldEspecification(input = FieldEspecification.InputType.TEXTAREAINPUT, label = "Descripci&oacute;n", size = 200)
    private String descripcion;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rol", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    @FieldDeletionStrategy(options = DeletionStrategy.ALONE_OPTIONS)
    private List<RolUsuarioEntidad> rolUsuarios = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rol", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<TramiteRolEntidad> tramiteRol = new ArrayList<>();
    
    //bi-directional many-to-one association to RolOperadorSeguridadEntidad
  	@OneToMany(mappedBy="rolOperador")
  	@FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
  	private List<RolOperadorEntidad> coRolOperadors;

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    private String codigoSincronizacion;

    public RolEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<RolUsuarioEntidad> getRolUsuarios() {
        return rolUsuarios;
    }

    public void setRolUsuarios(List<RolUsuarioEntidad> rolUsuarios) {
        this.rolUsuarios = rolUsuarios;
    }

    public List<TramiteRolEntidad> getTramiteRol() {
        return tramiteRol;
    }

    public void setTramiteRol(List<TramiteRolEntidad> tramiteRol) {
        this.tramiteRol = tramiteRol;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
		coRolOperador.setRolOperador(this);

		return coRolOperador;
	}

	/**
	 * Método para eliminar un RolOperador
	 * @param coRolOperador
	 * @return
	 */
	public RolOperadorEntidad removeCoRolOperador(RolOperadorEntidad coRolOperador) {
		getCoRolOperadors().remove(coRolOperador);
		coRolOperador.setRolOperador(null);

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
        RolEntidad that = (RolEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}