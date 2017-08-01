package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PaqueteNuiPeticionEntidad.java
 *
 * @descripcion Se crea la clase PaqueteNuiPeticionEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "X052T_PAQUETE_NUI_PETICION")

public class PaqueteNuiPeticionEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PAQUETE_NUI_PETICION", nullable = false, length = 22)
    @SequenceGenerator(name = "PAQUETE_NUI_POR_PETICION_SEQ",
            sequenceName = "X052S_CO_PAQUETE_NUI_PETICION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAQUETE_NUI_POR_PETICION_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PAQUETE_NUI", name = "CO_PAQUETE_NUI", nullable = false)
    private PaqueteNuiEntidad paqueteNui;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA_PETICION",
            name = "CO_OFICINA_PETICION", nullable = false)
    private OficinaPeticionPaqueteNuiEntidad oficinaPeticionPaquete;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_CREACION", nullable = false)
    private Date fechaInicio;

    public PaqueteNuiPeticionEntidad() {
        //Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PaqueteNuiEntidad getPaqueteNUI() {
        return paqueteNui;
    }

    public void setPaqueteNUI(PaqueteNuiEntidad paqueteNui) {
        this.paqueteNui = paqueteNui;
    }

    public OficinaPeticionPaqueteNuiEntidad getOficinaPeticionPaquete() {
        return oficinaPeticionPaquete;
    }

    public void setOficinaPeticionPaquete(OficinaPeticionPaqueteNuiEntidad oficinaPeticionPaquete) {
        this.oficinaPeticionPaquete = oficinaPeticionPaquete;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

}
