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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import ve.gob.cne.sarc.persistencia.disparadores.ActaDisparador;

/**
 * ActaEntidad.java
 *
 * @descripcion Se crea la clase ActaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 *
 */
@Entity
@Table(name = "K004T_ACTA")
@EntityListeners({ActaDisparador.class})

public class ActaEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ACTA", nullable = false, length = 22)
    @SequenceGenerator(name = "ACTA_SEQ", sequenceName = "K004S_CO_ACTA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTA_SEQ")
    private Long id;

    @Basic(optional = false)
    @Column(name = "NU_ACTA", unique = true, nullable = false, length = 20)
    private String numeroActa;

    @Basic(optional = false)
    @Column(name = "NU_FOLIO", nullable = false, length = 8)
    private Integer numeroFolio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_CREACION", nullable = false)
    private Date fechaCreacion;

    @Basic(optional = true)
    @Column(name = "NB_PAIS", nullable = true, length = 100)
    private String pais;

    @Basic(optional = true)
    @Column(name = "NB_ESTADO", nullable = true, length = 100)
    private String estado;

    @Basic(optional = true)
    @Column(name = "NB_MUNICIPIO", nullable = true, length = 100)
    private String municipio;

    @Basic(optional = true)
    @Column(name = "NB_PARROQUIA", nullable = true, length = 100)
    private String parroquia;

    @Basic(optional = true)
    @Column(name = "NB_LOCALIDAD", nullable = true, length = 100)
    private String localidad;

    @Basic(optional = true)
    @Column(name = "NB_COMUNIDAD_INDIGENA", nullable = true, length = 100)
    private String comunidadIndigena;

    @Basic(optional = true)
    @Column(name = "TX_CIRCUNSTANCIAS_ESPECIALES", nullable = true, length = 500)
    private String circunstancias;

    @Basic(optional = true)
    @OneToOne(mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DefuncionEntidad defuncion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ParticipanteEntidad> participantes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProcedenciaEntidad> procedencias = new ArrayList<>();

    @Basic(optional = true)
    @OneToOne(mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private InsercionEntidad insercion;

    @Basic(optional = true)
    @OneToOne(mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NacimientoEntidad nacimiento;

    @Basic(optional = true)
    @OneToMany(mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActaNotaMarginalEntidad> notaMarginal = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MatrimonioEntidad> matrimonio = new ArrayList<>();

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_LIBRO", name = "CO_LIBRO", nullable = true)
    private LibroEntidad libro;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PoderMandatorioEntidad> poderesMandatarios = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA_FUNCIONARIO",
            name = "CO_OFICINA_FUNCIONARIO", nullable = false)
    private OficinaFuncionarioEntidad oficinaFuncionario;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_SOLICITUD", name = "CO_SOLICITUD", nullable = true)
    private SolicitudEntidad solicitud;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ACTA_ESTATUS", referencedColumnName = "CO_ACTA_ESTATUS", nullable = false)
    private ActaEstatusEntidad estatus;

    @OneToMany(mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EcuParticipanteActaEntidad> ecuParticipanteActaEntidad = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SolicitudOREEntidad> solicitudORE;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActaRecaudoEntidad> actaRecaudo;

    public ActaEntidad() {
        // Metodo Constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OficinaFuncionarioEntidad getOficinaFuncionario() {
        return oficinaFuncionario;
    }

    public void setOficinaFuncionario(OficinaFuncionarioEntidad oficinaFuncionario) {
        this.oficinaFuncionario = oficinaFuncionario;
    }

    public SolicitudEntidad getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public LibroEntidad getLibro() {
        return libro;
    }

    public void setLibro(LibroEntidad libro) {
        this.libro = libro;
    }

    public String getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(String numeroActa) {
        this.numeroActa = numeroActa;
    }

    public Integer getNumeroFolio() {
        return numeroFolio;
    }

    public void setNumeroFolio(Integer numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public ActaEstatusEntidad getEstatus() {
        return estatus;
    }

    public void setEstatus(ActaEstatusEntidad estatus) {
        this.estatus = estatus;
    }

    public DefuncionEntidad getDefuncion() {
        return defuncion;
    }

    public void setDefuncion(DefuncionEntidad defuncion) {
        this.defuncion = defuncion;
    }

    public List<ParticipanteEntidad> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteEntidad> participantes) {
        this.participantes = participantes;
    }

    public List<ProcedenciaEntidad> getProcedencias() {
        return procedencias;
    }

    public void setProcedencias(List<ProcedenciaEntidad> procedencias) {
        this.procedencias = procedencias;
    }

    public InsercionEntidad getInsercion() {
        return insercion;
    }

    public void setInsercion(InsercionEntidad insercion) {
        this.insercion = insercion;
    }

    public NacimientoEntidad getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(NacimientoEntidad nacimiento) {
        this.nacimiento = nacimiento;
    }

    public List<MatrimonioEntidad> getMatrimonio() {
        return matrimonio;
    }

    public void setMatrimonio(List<MatrimonioEntidad> matrimonio) {
        this.matrimonio = matrimonio;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getComunidadIndigena() {
        return comunidadIndigena;
    }

    public void setComunidadIndigena(String comunidadIndigena) {
        this.comunidadIndigena = comunidadIndigena;
    }

    public String getCircunstancias() {
        return circunstancias;
    }

    public void setCircunstancias(String circunstancias) {
        this.circunstancias = circunstancias;
    }

    public List<ActaNotaMarginalEntidad> getNotaMarginal() {
        return notaMarginal;
    }

    public void setNotaMarginal(List<ActaNotaMarginalEntidad> notaMarginal) {
        this.notaMarginal = notaMarginal;
    }

    @XmlTransient
    public List<EcuParticipanteActaEntidad> getEcuParticipanteActaEntidad() {
        return ecuParticipanteActaEntidad;
    }

    public void setEcuParticipanteActaEntidad(List<EcuParticipanteActaEntidad> ecuParticipanteActaEntidad) {
        this.ecuParticipanteActaEntidad = ecuParticipanteActaEntidad;
    }
	

    public List<SolicitudOREEntidad> getSolicitudORE() {
        return solicitudORE;
    }

    public void setSolicitudORE(List<SolicitudOREEntidad> solicitudORE) {
        this.solicitudORE = solicitudORE;
    }
	
	    public List<PoderMandatorioEntidad> getPoderesMandatarios() {
        return poderesMandatarios;
    }

    public void setPoderesMandatarios(List<PoderMandatorioEntidad> poderesMandatarios) {
        this.poderesMandatarios = poderesMandatarios;
    }

    public List<ActaRecaudoEntidad> getActaRecaudo() {
        return actaRecaudo;
    }

    public void setActaRecaudo(List<ActaRecaudoEntidad> actaRecaudo) {
        this.actaRecaudo = actaRecaudo;
    }

}
