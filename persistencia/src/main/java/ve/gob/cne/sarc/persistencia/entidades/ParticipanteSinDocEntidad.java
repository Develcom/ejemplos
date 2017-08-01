package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.CompletationStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import ve.gob.cne.sarc.persistencia.disparadores.ParticipanteSinDocDisparador;

/**
 * ParticipanteSinDocEntidad.java
 *
 * @descripcion Se crea la clase ParticipanteSinDocEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C054T_PARTICIPANTE_SIN_DOC")
@EntityListeners({ParticipanteSinDocDisparador.class})
@ClassEspecification(name = "Participante sin Documentación", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class ParticipanteSinDocEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PARTICIPANTE_SIN_DOC", nullable = false, length = 22)
    @SequenceGenerator(name = "PARTICIPANTE_SIN_DOC_SEQ", sequenceName = "C054S_CO_PARTICIPANTE_SIN_DOC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTICIPANTE_SIN_DOC_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = false)
    @Column(name = "NB_PARTICIPANTE_SIN_DOC", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participanteSinDoc",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ParticipanteEntidad> participante = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TRAMITE", referencedColumnName = "CO_TRAMITE", nullable = false)
    @FieldEspecification(required = true, input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Trámite", order = 3, step = 0)
    private TramiteEntidad tramite;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public ParticipanteSinDocEntidad() {
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

    public List<ParticipanteEntidad> getParticipante() {
        return participante;
    }

    public void setParticipante(List<ParticipanteEntidad> participante) {
        this.participante = participante;
    }

    public TramiteEntidad getTramite() {
        return tramite;
    }

    public void setTramite(TramiteEntidad tramite) {
        this.tramite = tramite;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipanteSinDocEntidad that = (ParticipanteSinDocEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }
}