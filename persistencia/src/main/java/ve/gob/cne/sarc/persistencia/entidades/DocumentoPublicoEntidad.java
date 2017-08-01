package ve.gob.cne.sarc.persistencia.entidades;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.DocumentoPublicoDisparador;

/**
 * DocumentoPublicoEntidad.java
 *
 * @descripcion Se crea la clase DocumentoPublicoEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 */
@Entity
@Table(name = "X004T_DOCUMENTO_PUBLICO")
@EntityListeners({DocumentoPublicoDisparador.class})

public class DocumentoPublicoEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_DOCUMENTO_PUBLICO", nullable = false, length = 22)
    @SequenceGenerator(name = "DOCUMENTO_PUBLICO_SEQ", sequenceName = "X004S_CO_DOCUMENTO_PUBLICO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENTO_PUBLICO_SEQ")
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_ENTE_PUBLICO", nullable = false, length = 100)
    private String nombreEntePublico;

    @Basic(optional = false)
    @Column(name = "NU_OFICIO", nullable = false, length = 20)
    private String numeroOficio;

    @Basic(optional = false)
    @Column(name = "NB_DECLARANTE", nullable = false, length = 50)
    private String nombreDeclarante;

    @Basic(optional = false)
    @Column(name = "NB_APELLIDO_DECLARANTE", nullable = false, length = 50)
    private String apellidoDeclarante;

    @Basic(optional = false)
    @Column(name = "NB_JUEZ", nullable = false, length = 50)
    private String nombreJuez;

    @Basic(optional = false)
    @Column(name = "NB_APELLIDO_JUEZ", nullable = false, length = 50)
    private String apelidoJuez;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_ELABORACION", nullable = false)
    private Date fechaElaboracion;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PARTICIPANTE", name = "CO_PARTICIPANTE")
    private ParticipanteEntidad participante;

    public DocumentoPublicoEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreEntePublico() {
        return nombreEntePublico;
    }

    public void setNombreEntePublico(String nombreEntePublico) {
        this.nombreEntePublico = nombreEntePublico;
    }

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public String getNombreDeclarante() {
        return nombreDeclarante;
    }

    public void setNombreDeclarante(String nombreDeclarante) {
        this.nombreDeclarante = nombreDeclarante;
    }

    public String getApellidoDeclarante() {
        return apellidoDeclarante;
    }

    public void setApellidoDeclarante(String apellidoDeclarante) {
        this.apellidoDeclarante = apellidoDeclarante;
    }

    public String getNombreJuez() {
        return nombreJuez;
    }

    public void setNombreJuez(String nombreJuez) {
        this.nombreJuez = nombreJuez;
    }

    public String getApelidoJuez() {
        return apelidoJuez;
    }

    public void setApelidoJuez(String apelidoJuez) {
        this.apelidoJuez = apelidoJuez;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public ParticipanteEntidad getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteEntidad participante) {
        this.participante = participante;
    }
}
