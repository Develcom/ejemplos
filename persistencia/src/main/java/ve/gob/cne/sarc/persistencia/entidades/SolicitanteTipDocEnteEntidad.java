package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import ve.gob.cne.sarc.persistencia.disparadores.SolicitanteTipDocEnteDisparador;

/**
 * SolicitanteTipDocEnteEntidad.java
 *
 * @descripcion Se crea la clase SolicitanteTipDocEnteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C045T_SOLICITANTE_TIP_DOC_ENTE")
@EntityListeners({SolicitanteTipDocEnteDisparador.class})
@ClassEspecification(name = "Documento para Entes Gubernamentales", identifier = "Nombre",
        canBeListed=true, generatesTask=SincronizationPolicy.BROADCAST)
public class SolicitanteTipDocEnteEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_SOLICITANTE_TIP_DOC_ENTE", nullable = false, length = 22)
    @SequenceGenerator(name = "SOLICITANTE_TIP_DOC_ENTE_SEQ", sequenceName = "C045S_CO_SOLI_TIP_DOC_ENTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOLICITANTE_TIP_DOC_ENTE_SEQ")
    @FieldEspecification(type="java.lang.Long", hide=true, order=0, step=0)
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @FieldEspecification(size=200, input = InputType.TEXTAREAINPUT, label="Descripci&oacute;n")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "NB_SOLICITANTE_TIP_DOC_ENTE", nullable = true, length = 50)
    @FieldEspecification(size=50, label="Nombre", order=2, step=0)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitanteTipDocEnte",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SolicitanteEntidad> solicitante = new ArrayList<>();
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public SolicitanteTipDocEnteEntidad() {
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

    public List<SolicitanteEntidad> getSolicitantes() {
        return solicitante;
    }

    public void setSolicitantes(List<SolicitanteEntidad> solicitante) {
        this.solicitante = solicitante;
    }

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

}
