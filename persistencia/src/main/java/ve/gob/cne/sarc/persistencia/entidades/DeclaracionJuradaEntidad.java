package ve.gob.cne.sarc.persistencia.entidades;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import ve.gob.cne.sarc.persistencia.disparadores.DeclaracionJuradaDisparador;

/**
 * DeclaracionJuradaEntidad.java
 *
 * @descripcion Se crea la clase DeclaracionJuradaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "X013T_DECLARACION_JURADA")
@EntityListeners({DeclaracionJuradaDisparador.class})

public class DeclaracionJuradaEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 62781130114156394L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_DECLARACION_JURADA", nullable = false, length = 22)
    @SequenceGenerator(name = "DECLARACION_JURADA_SEQ", sequenceName = "X013S_CO_DECLARACION_JURADA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DECLARACION_JURADA_SEQ")
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_SOLICITUD", name = "CO_SOLICITUD")
    private SolicitudEntidad solicitud;

    @Basic(optional = false)
    @Column(name = "IN_TIPO", nullable = false, length = 5)
    private String indicadorTipo;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_DECLARACION_JURADA", length = 7)
    private Date fechaDeclaracionJurada;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "declaracionJurada",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ParticipanteEntidad> participantes = new ArrayList<>();

    public DeclaracionJuradaEntidad() {
        // Metodo Constructor
    }

    /**
     *
     * DeclaracionJuradaEntidad
     * </P>
     *
     * @param solicitud
     */
    public DeclaracionJuradaEntidad(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SolicitudEntidad getSolicitud() {
        return this.solicitud;
    }

    public void setSolicitud(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public String getIndicadorTipo() {
        return this.indicadorTipo;
    }

    public void setIndicadorTipo(String indicadorTipo) {
        this.indicadorTipo = indicadorTipo;
    }

    public Date getFechaDeclaracionJurada() {
        return this.fechaDeclaracionJurada;
    }

    public void setFechaDeclaracionJurada(Date fechaDeclaracionJurada) {
        this.fechaDeclaracionJurada = fechaDeclaracionJurada;
    }

    public List<ParticipanteEntidad> getParticipantes() {
        return this.participantes;
    }

    public void setParticipantes(List<ParticipanteEntidad> participantes) {
        this.participantes = participantes;
    }

}
