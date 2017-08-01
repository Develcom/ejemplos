package ve.gob.cne.sarc.persistencia.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TomoEstatusEntidad.java
 *
 * @descripcion Se crea la clase LibroEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.1 06/09/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C053T_TOMO_ESTATUS")

public class TomoEstatusEntidad implements java.io.Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TOMO_ESTATUS", nullable = false, length = 22)
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = false)
    @Column(name = "NB_TOMO_ESTATUS", nullable = false, length = 50)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tomoEstatus", cascade = {CascadeType.PERSIST})
    private List<TomoEntidad> tomo = new ArrayList<>();

    public TomoEstatusEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TomoEntidad> getTomo() {
        return tomo;
    }

    public void setTomo(List<TomoEntidad> tomo) {
        this.tomo = tomo;
    }

}
