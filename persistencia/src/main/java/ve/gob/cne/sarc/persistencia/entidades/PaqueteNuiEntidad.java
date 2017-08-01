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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PaqueteNuiEntidad.java
 *
 * @descripcion Se crea la clase PaqueteNuiEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "X016T_PAQUETE_NUI")

@NamedQueries({
    @NamedQuery(name = PaqueteNuiEntidad.BUSCAR_LISTA_PAQUETE_Y_LISTA_NUI, query = "SELECT DISTINCT  PN "
            + "FROM PaqueteNuiEntidad PN, IN(PN.nui) nui "
            + "WHERE nui.oficina.codigo = :codigo "
            + "and PN.paqueteNuiEstatus.id = :paqueteEstatus "
            + "and nui.nuiEstatus.id = :estatusNui "
            + "order by PN.fechaInicio,PN.id")
})
public class PaqueteNuiEntidad implements Serializable {

    public static final String BUSCAR_LISTA_PAQUETE_Y_LISTA_NUI = "PaqueteNuiEntidad.buscarListaPaqueteYListaNui";
    @Id
    @Basic(optional = false)
    @Column(name = "CO_PAQUETE_NUI", nullable = false, length = 22)
    @SequenceGenerator(name = "PAQUETE_NUI_SEQ", sequenceName = "X016S_CO_PAQUETE_NUI", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAQUETE_NUI_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_LOTE_NUI", name = "CO_LOTE_NUI", nullable = false)
    private LoteNUIEntidad loteNui;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PAQUETE_NUI_ESTATUS", name = "CO_PAQUETE_NUI_ESTATUS", nullable = false)
    private PaqueteNuiEstatusEntidad paqueteNuiEstatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paqueteNui",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<NuiEntidad> nui = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paqueteNui",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PaqueteNuiPeticionEntidad> paqueteNuiPeticion = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    public PaqueteNuiEntidad() {
        //Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LoteNUIEntidad getLoteNui() {
        return loteNui;
    }

    public void setLoteNui(LoteNUIEntidad loteNui) {
        this.loteNui = loteNui;
    }

    public PaqueteNuiEstatusEntidad getPaqueteNuiEstatus() {
        return paqueteNuiEstatus;
    }

    public void setPaqueteNuiEstatus(PaqueteNuiEstatusEntidad paqueteNuiEstatus) {
        this.paqueteNuiEstatus = paqueteNuiEstatus;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<NuiEntidad> getNui() {
        return nui;
    }

    public void setNui(List<NuiEntidad> nui) {
        this.nui = nui;
    }

    public List<PaqueteNuiPeticionEntidad> getPaqueteNuiPeticion() {
        return paqueteNuiPeticion;
    }

    public void setPaqueteNuiPeticion(List<PaqueteNuiPeticionEntidad> paqueteNuiPeticion) {
        this.paqueteNuiPeticion = paqueteNuiPeticion;
    }

}
