package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
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

import ve.gob.cne.sarc.persistencia.disparadores.SolicitanteDisparador;

/**
 * SolicitanteEntidad.java
 *
 * @descripcion Se crea la clase SolicitanteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X035T_SOLICITANTE")
@EntityListeners({SolicitanteDisparador.class})

public class SolicitanteEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_SOLICITANTE", nullable = false, length = 22)
    @SequenceGenerator(name = "SOLICITANTE_SEQ", sequenceName = "X035S_CO_SOLICITANTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOLICITANTE_SEQ")
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_ENTE_PUBLICO", name = "CO_ENTE_PUBLICO", nullable = true)
    private EntePublicoEntidad entePublico;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_NOMBRE", nullable = true, length = 50)
    private String primerNombre;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE", nullable = true, length = 50)
    private String segundoNombre;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_APELLIDO", nullable = true, length = 50)
    private String primerApellido;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO", nullable = true, length = 50)
    private String segundoApellido;

    @Basic(optional = true)
    @Column(name = "NU_DOCUMENTO_OFICIO", nullable = true, length = 15)
    private String numeroDocumentoOficio;

    @Basic(optional = false)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitante", cascade = {CascadeType.PERSIST})
    private List<SolicitudEntidad> solicitud = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_SOLICITANTE_TIPO", referencedColumnName = "CO_SOLICITANTE_TIPO", nullable = false)
    private SolicitanteTipoEntidad solicitanteTipo;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_SOLICITANTE_TIP_DOC_DEC", referencedColumnName
            = "CO_SOLICITANTE_TIP_DOC_DEC", nullable = true)
    private SolicitanteTipDocDecEntidad solicitanteTipDocDec;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_SOLICITANTE_TIP_DOC_ENTE", referencedColumnName
            = "CO_SOLICITANTE_TIP_DOC_ENTE", nullable = true)
    private SolicitanteTipDocEnteEntidad solicitanteTipDocEnte;

    public SolicitanteEntidad() {
        // Metodo Constructor
    }

    public SolicitanteTipDocEnteEntidad getSolicitanteTipDocEnte() {
        return solicitanteTipDocEnte;
    }

    public void setSolicitanteTipDocEnte(
            SolicitanteTipDocEnteEntidad solicitanteTipDocEnte) {
        this.solicitanteTipDocEnte = solicitanteTipDocEnte;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String primerNombre) {
        this.segundoNombre = primerNombre;
    }

    public String getNombreCompleto() {
        return this.primerNombre + " " + this.segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getApellidoCompleto() {
        return this.primerApellido + " " + this.segundoApellido;
    }

    public String getNumeroDocumentoOficio() {
        return numeroDocumentoOficio;
    }

    public void setNumeroDocumentoOficio(String numeroDocumentoOficio) {
        this.numeroDocumentoOficio = numeroDocumentoOficio;
    }

    public EntePublicoEntidad getEntePublico() {
        return entePublico;
    }

    public void setEntePublico(EntePublicoEntidad entePublico) {
        this.entePublico = entePublico;
    }

    public List<SolicitudEntidad> getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(List<SolicitudEntidad> solicitud) {
        this.solicitud = solicitud;
    }

    public SolicitanteTipoEntidad getSolicitanteTipo() {
        return solicitanteTipo;
    }

    public void setSolicitanteTipo(SolicitanteTipoEntidad solicitanteTipo) {
        this.solicitanteTipo = solicitanteTipo;
    }

    public SolicitanteTipDocDecEntidad getSolicitanteTipDocDec() {
        return solicitanteTipDocDec;
    }

    public void setSolicitanteTipDocDec(SolicitanteTipDocDecEntidad solicitanteTipDocDec) {
        this.solicitanteTipDocDec = solicitanteTipDocDec;
    }

}
