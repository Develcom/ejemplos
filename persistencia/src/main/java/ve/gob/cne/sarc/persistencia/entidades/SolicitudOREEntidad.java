package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.SolicitudOREDisparador;

/**
 * SolicitudOREEntidad.java
 * @descripcion Entidad que contiene la data de las Solicitudes ORE
 * @version 
 * 23 de nov. de 2016
 * @author Domingo Rondon
 */
@Entity
@Table(name = "C084T_SOLICITUD_ORE")
@EntityListeners({SolicitudOREDisparador.class})
public class SolicitudOREEntidad implements Serializable{

    private static final long serialVersionUID = -2333049591247399654L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "CO_SOLICITUD_ORE",unique=true, nullable = false, length = 22)
    @SequenceGenerator(name = "SOLICITUD_ORE_SEQ", sequenceName = "C084S_CO_SOLICITUD_ORE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOLICITUD_ORE_SEQ")
    private long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA", nullable = false)
    private ActaEntidad acta;
    
    @Basic(optional = false)
    @Column(name = "TX_URL_DOCUMENTO", nullable = false, length = 100)
    private String urlDocumento;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_LUGAR_CELEBRACION", referencedColumnName = "CO_LUGAR_CELEBRACION", nullable = false)
    private LugarCelebracionEntidad lugarCelebracion;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_MOTIVO_TRASLADO", referencedColumnName = "CO_MOTIVO_TRASLADO", nullable = false)
    private MotivoTrasladoEntidad motivoTraslado;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitudORE", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RespuestaOREEntidad> respuestaORE;
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_SOLICITUD_ORE", nullable = false)
    private Date fechaSolicitudORE;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrlDocumento() {
        return urlDocumento;
    }

    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    public List<RespuestaOREEntidad> getRespuestaORE() {
        return respuestaORE;
    }

    public void setRespuestaORE(List<RespuestaOREEntidad> respuestaORE) {
        this.respuestaORE = respuestaORE;
    }

    public LugarCelebracionEntidad getLugarCelebracion() {
        return lugarCelebracion;
    }

    public void setLugarCelebracion(LugarCelebracionEntidad lugarCelebracion) {
        this.lugarCelebracion = lugarCelebracion;
    }

    public MotivoTrasladoEntidad getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(MotivoTrasladoEntidad motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

    public Date getFechaSolicitudORE() {
        return fechaSolicitudORE;
    }

    public void setFechaSolicitudORE(Date fechaSolicitudORE) {
        this.fechaSolicitudORE = fechaSolicitudORE;
    }
  
}
