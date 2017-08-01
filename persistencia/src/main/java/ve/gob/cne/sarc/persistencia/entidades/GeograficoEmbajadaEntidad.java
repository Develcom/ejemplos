package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.MethodEspecification;

/**
 * @author HiSoft
 */
@Entity
@Table(name = "I032T_GEOGRAFICO")
@ClassEspecification(name = "Embajada", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class GeograficoEmbajadaEntidad implements Serializable {
    private static final long serialVersionUID = 1L;

    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 1, step = 0)
    private String nombre;

    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            type = "java.lang.Long", label = "Pa&iacute;s", order = 2, step = 0)
    private GeograficoPaisEntidad geograficoPaisEntidad;
    
    private Date fechaInicio;

    private Date fechaFin;

    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;
    
    public GeograficoEmbajadaEntidad() {
        // Constructor vacío
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CO_GEOGRAFICO", nullable = false, length = 22)
    @GeneratedValue(generator = "generador_geografico_hibernate_increment")
    @GenericGenerator(name = "generador_geografico_hibernate_increment", strategy = "increment")
    @MethodEspecification(targetedAttribute="id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "NB_GEOGRAFICO", nullable = false, length = 50)
    @MethodEspecification(targetedAttribute="nombre")
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // bi-directional many-to-one association to TipoGeograficoEntity
    @ManyToOne
    @JoinColumn(name = "CO_TIPO_GEOGRAFICO", nullable = false)
    @MethodEspecification(targetedAttribute="tipoGeografico")
    public TipoGeograficoEntidad getTipoGeografico() {
        TipoGeograficoEntidad tipoGeograficoEntidad =
                new TipoGeograficoEntidad();
        tipoGeograficoEntidad.setId(7);
        tipoGeograficoEntidad.setTxDescripcion("Embajada");
        return tipoGeograficoEntidad;
    }

    public void setTipoGeografico(TipoGeograficoEntidad tipoGeografico) {
        // Empty method
    }

    @ManyToOne
    @JoinColumn(name = "NU_PADRE_GEOGRAFICO")
    @MethodEspecification(targetedAttribute="geograficoPaisEntidad")
    public GeograficoPaisEntidad getGeograficoPaisEntidad() {
        return geograficoPaisEntidad;
    }

    public void setGeograficoPaisEntidad(GeograficoPaisEntidad geograficoPaisEntidad) {
        this.geograficoPaisEntidad = geograficoPaisEntidad;
    }
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    @MethodEspecification(targetedAttribute="fechaInicio")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    @MethodEspecification(targetedAttribute="fechaFin")
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @MethodEspecification(targetedAttribute="codigoSincronizacion")
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

        GeograficoEmbajadaEntidad that = (GeograficoEmbajadaEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "GeograficoEmbajadaEntidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
