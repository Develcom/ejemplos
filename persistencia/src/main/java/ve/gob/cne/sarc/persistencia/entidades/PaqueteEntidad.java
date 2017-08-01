package ve.gob.cne.sarc.persistencia.entidades;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entidad de PaqueteEntidad.
 * @author Posmagroup
 */
@Entity
@Table(name="C066T_PAQUETE")
public class PaqueteEntidad implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name="CO_PAQUETE")
    @SequenceGenerator(name = "PAQUETE_SEQ", sequenceName = "C066S_CO_PAQUETE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAQUETE_SEQ")
    private Long id;
    
    @Column(name = "NB_PAQUETE", nullable=false)
    private String nombre;
    
    @Column(name = "TX_ORIGEN" , nullable=false)
    private String origen;

    @Column(name = "TX_PAYLOAD")
    @Lob
    private String payload;

    @Column(name = "TX_FLOW_SCOPE")
    @Lob
    private String flowScope;

    @Column(name = "TX_TRANSITION", nullable=true)
    private String transition;
    
    @ManyToOne(targetEntity=TareaEntidad.class)
    @JoinColumn(name = "CO_TAREA")
    private TareaEntidad tareaEntidad;
    
    @Column(name = "TX_NEXTSTEP", nullable = false)
    private String nextStep;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_SOLICITUD", name = "CO_SOLICITUD",unique= true, nullable = false)
    private SolicitudEntidad solicitudEntidad;

    @ManyToOne(targetEntity=PaqueteEstatusEntidad.class)
    @JoinColumn(name = "CO_PAQUETE_ESTATUS")
    private PaqueteEstatusEntidad paqueteEstatusEntidad;

    @ManyToOne(targetEntity=OficinaEntidad.class)
    @JoinColumn(name = "CO_OFICINA", nullable = false)
    private OficinaEntidad oficina;

    @ManyToOne(targetEntity=UsuarioEntidad.class)
    @JoinColumn(referencedColumnName = "CO_USUARIO",name = "CO_USUARIO")
    private UsuarioEntidad usuario;

    @ManyToOne(targetEntity = RolEntidad.class)
    @JoinColumn(referencedColumnName = "IDEN_ROL",name = "IDEN_ROL", nullable = true)
    private RolEntidad rol;

    public PaqueteEntidad() {
        //constructor por defecto
    }

    /**
     * Contructor
     * @param nombre
     * @param origen
     * @param tareaEntidad
     * @param payload
     * @param nextStep
     */
    public PaqueteEntidad(String nombre, String origen, TareaEntidad tareaEntidad,
                          String payload, String flowScope,  String nextStep) {
        this.nombre = nombre;
        this.origen = origen;
        this.payload = payload;
        this.flowScope = flowScope;
        this.nextStep = nextStep;


        this.tareaEntidad = tareaEntidad;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TareaEntidad getTareaEntidad() {
        return tareaEntidad;
    }

    public void setTareaEntidad(TareaEntidad tareaEntidad) {
        this.tareaEntidad = tareaEntidad;
    }

    public PaqueteEstatusEntidad getPaqueteEstatusEntidad() {
        return paqueteEstatusEntidad;
    }

    public void setPaqueteEstatusEntidad(PaqueteEstatusEntidad paqueteEstatusEntidad) {
        this.paqueteEstatusEntidad = paqueteEstatusEntidad;
    }

    @Override
    public String toString() {
        return "PaqueteEntidad{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    /*paqueteEstatusEntidad=" + paqueteEstatusEntidad + ", tareaEntidad=" + tareaEntidad + ",*/
    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getFlowScope() {
        return flowScope;
    }

    public void setFlowScope(String flowScope) {
        this.flowScope = flowScope;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    public SolicitudEntidad getSolicitudEntidad() {
        return solicitudEntidad;
    }

    public void setSolicitudEntidad(SolicitudEntidad solicitudEntidad) {
        this.solicitudEntidad = solicitudEntidad;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

    public UsuarioEntidad getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntidad usuario) {
        this.usuario = usuario;
    }

    public RolEntidad getRol() {
        return rol;
    }

    public void setRol(RolEntidad rol) {
        this.rol = rol;
    }
}
