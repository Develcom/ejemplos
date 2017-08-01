package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.MethodEspecification;

/**
 * @author HiSoft
 */
@Entity
@Table(name = "I032T_GEOGRAFICO")
@ClassEspecification(name = "País", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class GeograficoPaisEntidad implements Serializable {
    private static final long serialVersionUID = 1L;

    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private Long id;

    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 1, step = 0)
    private String nombre;
    
    @FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
    private List<GeograficoEstadoEntidad> estados;
    
    @FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
    private List<GeograficoEmbajadaEntidad> embajadas;

    @FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
    private List<GeograficoDependenciaFederalEntidad> dependenciaFederales;
    
    private Date fechaInicio;

    private Date fechaFin;

    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public GeograficoPaisEntidad() {
        // Constructor vacío
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CO_GEOGRAFICO", nullable = false, length = 22)
    @GeneratedValue(generator = "generador_geografico_hibernate_increment")
    @GenericGenerator(name = "generador_geografico_hibernate_increment", strategy = "increment")
    @MethodEspecification(targetedAttribute="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Basic(optional = true)
    @Column(name = "NU_PADRE_GEOGRAFICO")
    @MethodEspecification(targetedAttribute="nuPadreGeografico")
    public Long getNuPadreGeografico() {
        return null;
    }

    public void setNuPadreGeografico(Long nuPadreGeografico) {
        // Empty Method
    }


    // bi-directional many-to-one association to TipoGeograficoEntity
    @ManyToOne
    @JoinColumn(name = "CO_TIPO_GEOGRAFICO", nullable = false)
    @MethodEspecification(targetedAttribute="tipoGeografico")
    public TipoGeograficoEntidad getTipoGeografico() {
        TipoGeograficoEntidad tipoGeograficoEntidad =
                new TipoGeograficoEntidad();
        tipoGeograficoEntidad.setId(1);
        tipoGeograficoEntidad.setTxDescripcion("País");
        return tipoGeograficoEntidad;
    }

    public void setTipoGeografico(TipoGeograficoEntidad tipoGeografico) {
        // Empty method
    }

    @OneToMany(mappedBy = "geograficoPaisEntidad")
    @MethodEspecification(targetedAttribute="estados")
    public List<GeograficoEstadoEntidad> getEstados() {
        return estados;
    }

    public void setEstados(List<GeograficoEstadoEntidad> estados) {
        this.estados = estados;
    }

    @OneToMany(mappedBy = "geograficoPaisEntidad")
    @MethodEspecification(targetedAttribute="embajadas")
    public List<GeograficoEmbajadaEntidad> getEmbajadas() {
        return embajadas;
    }

    public void setEmbajadas(List<GeograficoEmbajadaEntidad> embajadas) {
        this.embajadas = embajadas;
    }

    @OneToMany(mappedBy = "geograficoPaisEntidad")
    @MethodEspecification(targetedAttribute="dependenciaFederales")
    public List<GeograficoDependenciaFederalEntidad> getDependenciaFederales() {
        return dependenciaFederales;
    }

    public void setDependenciaFederales(List<GeograficoDependenciaFederalEntidad> dependenciaFederales) {
        this.dependenciaFederales = dependenciaFederales;
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

        GeograficoPaisEntidad that = (GeograficoPaisEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "GeograficoPaisEntidad{" +
                "id=" + id +
                ", nbGeografico='" + nombre + '\'' +
                '}';
    }
}
