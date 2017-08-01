package ve.gob.cne.sarc.persistencia.entidades;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

/**
 * Entidad destinada a representar plugins de ejecucion para tareas de trabajo
 * 
 * @author HiSoft
 *
 */
@Entity
@Table(name = "K008T_PLUGIN")
@ClassEspecification(name = "Plugin", identifier = "Nombre", canBeListed = false, generatesTask = SincronizationPolicy.BROADCAST)
public class PluginEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_plugin", nullable = false)
    @GeneratedValue(generator = "generador_plugin_hibernate_increment")
    @org.hibernate.annotations.GenericGenerator(name = "generador_plugin_hibernate_increment", strategy = "increment")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private Long id;

    @Column(name = "nb_completo_clase", unique = true, nullable = false, length = 255)
    @FieldEspecification(required = true, size = 255, label = "FQDN", order = 1, step = 0, placeHolder = "ve.gob.cne.sarc.plugin")
    private String nombre;

    @Column(name = "tx_descripcion", nullable = true, length = 255)
    @FieldEspecification(size = 255, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n", order = 3, step = 0)
    private String descripcion;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fe_inicio", nullable = true)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fe_fin", nullable = true)
    private Date fechaFin;
    
    @Column(name = "tx_ruta", nullable = false, length = 255)
    @FieldEspecification(input = InputType.FILEINPUT, type = "java.util.File", label = "Archivo Ejecutable",
            required=true, order = 2, step = 0)
    private String path;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255)
    private String codigoSincronizacion;
    
    public PluginEntidad() {
        /* Constructor */
    }

    /**
     * 
     * @param nombre
     * @param descripcion
     */
    public PluginEntidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public String getCodigoSincronizacion() {
		return codigoSincronizacion;
	}

	public void setCodigoSincronizacion(String codigoSincronizacion) {
		this.codigoSincronizacion = codigoSincronizacion;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (this.id ^ (this.id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PluginEntidad other = (PluginEntidad) obj;
        if (this.id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Plugin [idPlugins=" + this.id + ", nombre=" + nombre
                + ", descripcion=" + descripcion + "]";
    }

}
