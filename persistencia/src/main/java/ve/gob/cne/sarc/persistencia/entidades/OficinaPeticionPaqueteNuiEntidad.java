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
 * EnteEntidad.java
 *
 * @descripcion Se crea la clase EnteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "X058T_OFICINA_PETICION")

public class OficinaPeticionPaqueteNuiEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_OFICINA_PETICION", nullable = false, length = 22)
    @SequenceGenerator(name = "OFICINA_PETICION_SEQ",
            sequenceName = "X058S_CO_OFICINA_PETICION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFICINA_PETICION_SEQ")
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200, nullable = true)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficinaPeticionPaquete",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PaqueteNuiPeticionEntidad> PaqueteNuiPeticion = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA", name = "CO_OFICINA", nullable = false)
    private OficinaEntidad oficina;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA_PETICION_ESTATUS",
            name = "CO_OFICINA_PETICION_ESTATUS", nullable = false)
    private OficinaPeticionPaqueteNuiEstatusEntidad OficinaPeticionPaqueteNuiEstatus;

    public OficinaPeticionPaqueteNuiEntidad() {
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

    public List<PaqueteNuiPeticionEntidad> getPaqueteNuiPeticion() {
        return PaqueteNuiPeticion;
    }

    public void setPaqueteNuiPeticion(List<PaqueteNuiPeticionEntidad> PaqueteNuiPeticion) {
        this.PaqueteNuiPeticion = PaqueteNuiPeticion;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

    public OficinaPeticionPaqueteNuiEstatusEntidad getOficinaPeticionPaqueteNuiEstatus() {
        return OficinaPeticionPaqueteNuiEstatus;
    }

    public void setOficinaPeticionPaqueteNuiEstatus(OficinaPeticionPaqueteNuiEstatusEntidad OficinaPeticionPaqueteNuiEstatus) {
        this.OficinaPeticionPaqueteNuiEstatus = OficinaPeticionPaqueteNuiEstatus;
    }

}
