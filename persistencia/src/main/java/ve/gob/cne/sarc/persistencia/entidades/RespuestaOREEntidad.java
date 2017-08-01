package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ve.gob.cne.sarc.persistencia.disparadores.RespuestaOREDisparador;

/**
 * RespuestaOREEntidad.java
 * @descripcion Entidad que contiene la data de las respuestas de los funcionarios realizados en la ORE
 * @version 
 * 23 de nov. de 2016
 * @author Domingo Rondon
 */
@Entity
@Table(name = "C083T_RESPUESTA_ORE")
@EntityListeners({RespuestaOREDisparador.class})
@NamedQueries({
    @NamedQuery(name = RespuestaOREEntidad.BUSCAR_RESPUESTA_POR_SOLICITUD, query = "SELECT resp "
            + "FROM  RespuestaOREEntidad resp "
            + "WHERE resp.solicitudORE.id = :idSolicitud")
    })
public class RespuestaOREEntidad implements Serializable{

    private static final long serialVersionUID = -2333049591247399654L;
    
    public static final String BUSCAR_RESPUESTA_POR_SOLICITUD = "RespuestaOREEntidad.buscarPorSolicitudORE";
    
    @Id
    @Basic(optional = false)
    @Column(name = "CO_RESPUESTA_ORE", unique=true,nullable = false, length = 22)
    @SequenceGenerator(name = "RESPUESTA_ORE_SEQ", sequenceName = "C083S_CO_RESPUESTA_ORE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESPUESTA_ORE_SEQ")
    private long id;
    
    @Basic(optional = false)
    @Column(name = "IN_RESPUESTA", nullable = false)
    private boolean respuesta;
    
    @Basic(optional = false)
    @Column(name = "TX_DECISION_ORE", nullable = false)
    private String decisionORE;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ROL_USUARIO", referencedColumnName = "CO_ROL_USUARIO", nullable = false)
    private RolUsuarioEntidad rolUsuario;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_SOLICITUD_ORE", referencedColumnName = "CO_SOLICITUD_ORE", nullable = false)
    private SolicitudOREEntidad solicitudORE;
    
    @Basic(optional = false)
    @Column(name = "TX_MOTIVO_DECISION_ORE", nullable = false)
    private String motivoDecisionORE;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public String getDecisionORE() {
        return decisionORE;
    }

    public void setDecisionORE(String decisionORE) {
        this.decisionORE = decisionORE;
    }

    public RolUsuarioEntidad getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuarioEntidad rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getMotivoDecisionORE() {
        return motivoDecisionORE;
    }

    public void setMotivoDecisionORE(String motivoDecisionORE) {
        this.motivoDecisionORE = motivoDecisionORE;
    }

    public SolicitudOREEntidad getSolicitudORE() {
        return solicitudORE;
    }

    public void setSolicitudORE(SolicitudOREEntidad solicitudORE) {
        this.solicitudORE = solicitudORE;
    }

    
    

    
}
