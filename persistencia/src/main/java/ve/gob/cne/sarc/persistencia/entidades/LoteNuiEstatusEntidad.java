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
 * LoteNuiEstatusEntidad.java
 *
 * @descripcion Se crea la clase LoteNuiEstatusEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "C058T_LOTE_NUI_ESTATUS")

public class LoteNuiEstatusEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_LOTE_NUI_ESTATUS", nullable = false, length = 22)
    private long id;

    @Basic(optional = true)
    @Column(name = "NB_LOTE_NUI_ESTATUS")
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loteNuiEstatus",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<LoteNUIEntidad> loteNUI = new ArrayList<>();

    public LoteNuiEstatusEntidad() {
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

    public List<LoteNUIEntidad> getLoteNUI() {
        return loteNUI;
    }

    public void setLoteNUI(List<LoteNUIEntidad> loteNUI) {
        this.loteNUI = loteNUI;
    }

}
