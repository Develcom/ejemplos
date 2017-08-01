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

import ve.gob.cne.sarc.persistencia.disparadores.CiudadanoDisparador;

/**
 * CiudadanoEntidad.java
 *
 * @descripcion Se crea la clase CiudadanoEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "K001T_CIUDADANO")
@EntityListeners({CiudadanoDisparador.class})

public class CiudadanoEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_CIUDADANO", nullable = false, length = 22)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_NACIONALIDAD", name = "CO_NACIONALIDAD", nullable = false)
    private NacionalidadEntidad nacionalidad;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_COMUNIDAD_INDIGENA", name = "CO_COMUNIDAD_INDIGENA", nullable = true)
    private ComunidadIndigenaEntidad comunidadIndigena;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_CIUDADANO_ESTATUS", referencedColumnName = "CO_CIUDADANO_ESTATUS", nullable = true)
    private CiudadanoEstatusEntidad estatusCiudadano;

    @Basic(optional = true)
    @Column(name = "IN_TIPO_DOC_IDENTIFICACION", nullable = true, length = 5)
    private String tipoDocIdentificacion;

    @Basic(optional = true)
    @Column(name = "NU_DOCUMENTO_IDENTIDAD", nullable = true, length = 10)
    private String numeroDocIdentidad;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO", nullable = false, length = 50)
    private String primerApellido;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO", nullable = true, length = 50)
    private String segundoApellido;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE", nullable = false, length = 50)
    private String primerNombre;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE", nullable = true, length = 50)
    private String segundoNombre;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_NACIMIENTO", nullable = false)
    private Date fechaNacimiento;

    @Basic(optional = false)
    @Column(name = "IN_SEXO", nullable = false, length = 1)
    private String sexo;

    @Basic(optional = false)
    @Column(name = "IN_ESTADO_CIVIL", nullable = false, length = 10)
    private String estatusCivil;

    @Basic(optional = true)
    @Column(name = "IN_CONDICION_NACIONALIDAD", nullable = true, length = 5)
    private String condicionNacionalidad;

    @Basic(optional = true)
    @Column(name = "IN_UEH", nullable = true, length = 1)
    private String indicadorUEH;

    @Basic(optional = true)
    @Column(name = "NU_HIJOS", nullable = true, length = 2)
    private Integer numeroHijos;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_RESIDENCIA", nullable = true)
    private Date fechaResidencia;

    @Basic(optional = false)
    @Column(name = "DI_NACIMIENTO", nullable = false, length = 20)
    private String direccionNacimiento;

    @Basic(optional = true)
    @Column(name = "CO_NUI", nullable = true, length = 20)
    private String codigoNUI;

    @Basic(optional = true)
    @Column(name = "IN_ESTATUS_NUI", nullable = true, length = 5)
    private String estatusNUI;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ciudadano", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<DireccionCiudadanoEntidad> direccionesCiudadanos = new ArrayList<>();

    @Basic(optional = true)
    @OneToOne(mappedBy = "ciudadano", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private EcuEntidad ecu;

    public CiudadanoEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NacionalidadEntidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(NacionalidadEntidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public ComunidadIndigenaEntidad getComunidadIndigena() {
        return comunidadIndigena;
    }

    public void setComunidadIndigena(ComunidadIndigenaEntidad comunidadIndigena) {
        this.comunidadIndigena = comunidadIndigena;
    }

    public String getTipoDocIdentificacion() {
        return tipoDocIdentificacion;
    }

    public void setTipoDocIdentificacion(String tipoDocIdentificacion) {
        this.tipoDocIdentificacion = tipoDocIdentificacion;
    }

    public String getNumeroDocIdentidad() {
        return numeroDocIdentidad;
    }

    public void setNumeroDocIdentidad(String numeroDocIdentidad) {
        this.numeroDocIdentidad = numeroDocIdentidad;
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

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstatusCivil() {
        return estatusCivil;
    }

    public void setEstatusCivil(String estatusCivil) {
        this.estatusCivil = estatusCivil;
    }

    public String getCondicionNacionalidad() {
        return condicionNacionalidad;
    }

    public void setCondicionNacionalidad(String condicionNacionalidad) {
        this.condicionNacionalidad = condicionNacionalidad;
    }

    public String getIndicadorUEH() {
        return indicadorUEH;
    }

    public void setIndicadorUEH(String indicadorUEH) {
        this.indicadorUEH = indicadorUEH;
    }

    public Integer getNumeroHijos() {
        return numeroHijos;
    }

    public void setNumeroHijos(int numeroHijos) {
        this.numeroHijos = numeroHijos;
    }

    public Date getFechaResidencia() {
        return fechaResidencia;
    }

    public void setFechaResidencia(Date fechaResidencia) {
        this.fechaResidencia = fechaResidencia;
    }

    public String getDireccionNacimiento() {
        return direccionNacimiento;
    }

    public void setDireccionNacimiento(String direccionNacimiento) {
        this.direccionNacimiento = direccionNacimiento;
    }

    public String getCodigoNUI() {
        return codigoNUI;
    }

    public void setCodigoNUI(String codigoNUI) {
        this.codigoNUI = codigoNUI;
    }

    public String getEstatusNUI() {
        return estatusNUI;
    }

    public void setEstatusNUI(String estatusNUI) {
        this.estatusNUI = estatusNUI;
    }

    public CiudadanoEstatusEntidad getEstatusCiudadano() {
        return estatusCiudadano;
    }

    public void setEstatusCiudadano(CiudadanoEstatusEntidad estatusCiudadano) {
        this.estatusCiudadano = estatusCiudadano;
    }

    public List<DireccionCiudadanoEntidad> getDireccionesCiudadanos() {
        return direccionesCiudadanos;
    }

    public void setDireccionesCiudadanos(
            List<DireccionCiudadanoEntidad> direccionesCiudadanos) {
        this.direccionesCiudadanos = direccionesCiudadanos;
    }

    public EcuEntidad getEcu() {
        return ecu;
    }

    public void setEcu(EcuEntidad ecu) {
        this.ecu = ecu;
    }

}
