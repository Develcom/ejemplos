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
 * PaqueteNuiEstatusEntidad.java
 *
 * @descripcion Se crea la clase PaqueteNuiEstatusEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "C028T_PAQUETE_NUI_ESTATUS")

public class PaqueteNuiEstatusEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PAQUETE_NUI_ESTATUS", nullable = false, length = 22)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_PAQUETE_ESTATUS", length = 50, nullable = false)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200, nullable = true)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paqueteNuiEstatus",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PaqueteNuiEntidad> paqueteNui = new ArrayList<>();

    public PaqueteNuiEstatusEntidad() {
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

    public List<PaqueteNuiEntidad> getPaqueteNui() {
        return paqueteNui;
    }

    public void setPaqueteNui(List<PaqueteNuiEntidad> paqueteNui) {
        this.paqueteNui = paqueteNui;
    }

}
