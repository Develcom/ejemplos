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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OficinaPeticionPaqueteNuiEstatusEntidad.java
 *
 * @descripcion Se crea la clase OficinaPeticionPaqueteNuiEstatusEntidad donde se Realizan los Query de consulta de cada
 * metodo
 * @author Oscar Montilla
 * @version 1.0 20/12/2016
 */
@Entity
@Table(name = "C027T_OFICINA_PETICION_ESTATUS")

public class OficinaPeticionPaqueteNuiEstatusEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_OFICINA_PETICION_ESTATUS", nullable = false, length = 22)
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false, length = 10)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true, length = 10)
    private Date fechaFin;

    @Basic(optional = false)
    @Column(name = "NB_OFICINA_PETICION_ESTATUS", nullable = false, length = 50)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "OficinaPeticionPaqueteNuiEstatus",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaPeticionPaqueteNuiEntidad> OficinaPeticionPaqueteNui = new ArrayList<>();

    public OficinaPeticionPaqueteNuiEstatusEntidad() {
        //Metodo Constructor
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

    public List<OficinaPeticionPaqueteNuiEntidad> getOficinaPeticionPaqueteNui() {
        return OficinaPeticionPaqueteNui;
    }

    public void setOficinaPeticionPaqueteNui(List<OficinaPeticionPaqueteNuiEntidad> OficinaPeticionPaqueteNui) {
        this.OficinaPeticionPaqueteNui = OficinaPeticionPaqueteNui;
    }

}
