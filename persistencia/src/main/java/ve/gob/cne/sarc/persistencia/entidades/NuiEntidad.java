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
 * NuiEntidad.java
 *
 * @descripcion Se crea la clase EnteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "X018T_NUI")

public class NuiEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_NUI", nullable = false, length = 22)
    @SequenceGenerator(name = "PAQUETE_NUI_SEQ", sequenceName = "X018S_CO_NUI", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAQUETE_NUI_SEQ")
    private long id;

    @Basic(optional = false)
    @Column(name = "IDENTIFICADOR_NUI", length = 50, nullable = false, unique = true)
    private String identificadorNUI;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PAQUETE_NUI", name = "CO_PAQUETE_NUI", nullable = false)
    private PaqueteNuiEntidad paqueteNui;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA", name = "CO_OFICINA", nullable = false)
    private OficinaEntidad oficina;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_NUI_ESTATUS", name = "CO_NUI_ESTATUS", nullable = false)
    private NuiEstatusEntidad nuiEstatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nui", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<EcuNuiEntidad> ecuNui = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_CREACION", length = 50, nullable = false)
    private Date fechaInicio;

    public NuiEntidad() {
        //Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentificadorNUI() {
        return identificadorNUI;
    }

    public void setIdentificadorNUI(String identificadorNUI) {
        this.identificadorNUI = identificadorNUI;
    }

    public PaqueteNuiEntidad getPaqueteNUI() {
        return paqueteNui;
    }

    public void setPaqueteNUI(PaqueteNuiEntidad paqueteNui) {
        this.paqueteNui = paqueteNui;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

    public NuiEstatusEntidad getNuiEstatus() {
        return nuiEstatus;
    }

    public void setNuiEstatus(NuiEstatusEntidad nuiEstatus) {
        this.nuiEstatus = nuiEstatus;
    }

    public List<EcuNuiEntidad> getEcuNui() {
        return ecuNui;
    }

    public void setEcuNui(List<EcuNuiEntidad> ecuNui) {
        this.ecuNui = ecuNui;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
