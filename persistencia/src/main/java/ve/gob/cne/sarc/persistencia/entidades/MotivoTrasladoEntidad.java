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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.MotivoTrasladoDisparador;


@Entity
@Table(name = "C082T_MOTIVO_TRASLADO")
@EntityListeners(MotivoTrasladoDisparador.class)
@NamedQueries({

    @NamedQuery(name = "MotivoTrasladoEntidad.findByCodigo", query = "select m from MotivoTrasladoEntidad m where m.codigo = :codigo")
})
public class MotivoTrasladoEntidad  implements Serializable{
    
    /**
     * MotivoCelebracionEntidad.java
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CO_MOTIVO_TRASLADO", unique=true,nullable = false, length = 22)
    @SequenceGenerator(name = "MOTIVO_TRAS_SEQ", sequenceName = "C082S_CO_MOTIVO_TRASLADO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOTIVO_TRAS_SEQ")
    private Long id;
    
    
    @Basic(optional = false)
    @Column(name = "CO_ABREVIATURA", unique = true, nullable = false, length = 5)
    private String codigo;
    
    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    private String descripcion;
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "motivoTraslado", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SolicitudOREEntidad> solicitudORE;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public List<SolicitudOREEntidad> getSolicitudORE() {
        return solicitudORE;
    }

    public void setSolicitudORE(List<SolicitudOREEntidad> solicitudORE) {
        this.solicitudORE = solicitudORE;
    }

}
