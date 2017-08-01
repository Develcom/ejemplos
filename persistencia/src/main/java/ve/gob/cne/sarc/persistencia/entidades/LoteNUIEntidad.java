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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * LoteNUIEntidad.java
 *
 * @descripcion Se crea la clase LoteNUIEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "X017T_LOTE_NUI")

public class LoteNUIEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_LOTE_NUI", nullable = false, length = 22)
    @SequenceGenerator(name = "LOTE_NUI_SEQ", sequenceName = "X017S_CO_LOTE_NUI", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOTE_NUI_SEQ")
    private long id;

    @Basic(optional = true)
    @Column(name = "CA_LOTE_NUI")
    private String cantidadLoteNUI;

    @Basic(optional = false)
    @Column(name = "CODIGO_DISTRIBUCION")
    private String codigoDistribucion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_CONSUMO", nullable = true)
    private Date fechaConsumo;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_CREACION", nullable = false)
    private Date fechaCreacion;

    @Basic(optional = false)
    @Column(name = "NU_MINIMO", nullable = false)
    private Long nuMinimo;

    @Basic(optional = true)
    @Column(name = "NU_MAXIMO", nullable = true)
    private Long nuMaximo;

    @Basic(optional = true)
    @Column(name = "CODIGO_ERROR", nullable = true)
    private String codigoError;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_LOTE_NUI_ESTATUS", name = "CO_LOTE_NUI_ESTATUS", nullable = false)
    private LoteNuiEstatusEntidad loteNuiEstatus;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_ENTE", name = "CO_ENTE", nullable = false)
    private EnteEntidad ente;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loteNui",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PaqueteNuiEntidad> paqueteNui = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCantidadLoteNUI() {
        return cantidadLoteNUI;
    }

    public void setCantidadLoteNUI(String cantidadLoteNUI) {
        this.cantidadLoteNUI = cantidadLoteNUI;
    }

    public String getCodigoDistribucion() {
        return codigoDistribucion;
    }

    public void setCodigoDistribucion(String codigoDistribucion) {
        this.codigoDistribucion = codigoDistribucion;
    }

    public Date getFechaConsumo() {
        return fechaConsumo;
    }

    public void setFechaConsumo(Date fechaConsumo) {
        this.fechaConsumo = fechaConsumo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getNuMinimo() {
        return nuMinimo;
    }

    public void setNuMinimo(Long nuMinimo) {
        this.nuMinimo = nuMinimo;
    }

    public Long getNuMaximo() {
        return nuMaximo;
    }

    public void setNuMaximo(Long nuMaximo) {
        this.nuMaximo = nuMaximo;
    }

    public LoteNuiEstatusEntidad getLoteNuiEstatus() {
        return loteNuiEstatus;
    }

    public void setLoteNuiEstatus(LoteNuiEstatusEntidad loteNuiEstatus) {
        this.loteNuiEstatus = loteNuiEstatus;
    }

    public EnteEntidad getEnte() {
        return ente;
    }

    public void setEnte(EnteEntidad ente) {
        this.ente = ente;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public List<PaqueteNuiEntidad> getPaqueteNui() {
        return paqueteNui;
    }

    public void setPaqueteNui(List<PaqueteNuiEntidad> paqueteNui) {
        this.paqueteNui = paqueteNui;
    }

}
