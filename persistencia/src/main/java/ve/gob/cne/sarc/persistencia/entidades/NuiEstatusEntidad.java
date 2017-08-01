package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

/**
 * NuiEstatusEntidad.java
 *
 * @descripcion Se crea la clase NuiEstatusEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "C056T_NUI_ESTATUS")
@ClassEspecification(name = "Estatus de NUI", identifier = "Nombre", canBeListed = true,
generatesTask=SincronizationPolicy.BROADCAST)
public class NuiEstatusEntidad implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_NUI_ESTATUS", nullable = false, length = 22)
    @SequenceGenerator(name = "NUI_ESTATUS_SEQ", sequenceName = "C056S_CO_NUI_ESTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NUI_ESTATUS_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_NUI_ESTATUS", length = 50, nullable = false)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200, nullable = true)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nuiEstatus",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<NuiEntidad> nui = new ArrayList<>();
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public NuiEstatusEntidad() {
        //Metodo Constructor 
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<NuiEntidad> getNui() {
        return nui;
    }

    public void setNui(List<NuiEntidad> nui) {
        this.nui = nui;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NuiEstatusEntidad that = (NuiEstatusEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }
}
