package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
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

import ve.gob.cne.sarc.persistencia.disparadores.NacimientoDisparador;

/**
 * NacimientoEntidad.java
 *
 * @descripcionSe crea la clase NacimientoEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X045T_NACIMIENTO")
@EntityListeners({NacimientoDisparador.class})

public class NacimientoEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_NACIMIENTO", nullable = false, length = 22)
    @SequenceGenerator(name = "NACIMIENTO_SEQ", sequenceName = "X045S_CO_NACIMIENTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NACIMIENTO_SEQ")
    private int id;

    @Basic(optional = false)
    @Column(name = "IN_SEXO_PRESENTADO", nullable = false, length = 9)
    private String sexo;

    @Basic(optional = true)
    @Column(name = "DI_EXTRAHOSPITALARIO", nullable = true, length = 500)
    private String extrahospitalario;

    @Basic(optional = false)
    @Column(name = "NU_CERTIFICADO", nullable = false)
    private int certificado;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_CERTIFICADO", nullable = false)
    private Date fechaCertificado;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE_MEDICO", nullable = false, length = 50)
    private String primerNombreMedico;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_MEDICO", nullable = true, length = 50)
    private String segundoNombreMedico;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO_MEDICO", nullable = false, length = 50)
    private String primerApellidoMedico;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_MEDICO", nullable = true, length = 50)
    private String segundoApellidoMedico;

    @Basic(optional = false)
    @Column(name = "NU_DOC_IDENTIDAD_MEDICO", nullable = false, length = 15)
    private String numDocMedico;

    @Basic(optional = false)
    @Column(name = "NU_MPPS", nullable = false)
    private int mpps;

    @Basic(optional = false)
    @Column(name = "NB_CENTRO_SALUD", nullable = true, length = 200)
    private String centroSalud;

    @Basic(optional = true)
    @Column(name = "TX_CARACTER_ACTUA_DECLARANTE", nullable = true, length = 100)
    private String caracterDeclarante;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_ACTA", name = "CO_ACTA", nullable = false)
    private ActaEntidad acta;

    public NacimientoEntidad() {
        // Metodo Constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getExtrahospitalario() {
        return extrahospitalario;
    }

    public void setExtrahospitalario(String extrahospitalario) {
        this.extrahospitalario = extrahospitalario;
    }

    public int getCertificado() {
        return certificado;
    }

    public void setCertificado(int certificado) {
        this.certificado = certificado;
    }

    public Date getFechaCertificado() {
        return fechaCertificado;
    }

    public void setFechaCertificado(Date fechaCertificado) {
        this.fechaCertificado = fechaCertificado;
    }

    public String getPrimerNombreMedico() {
        return primerNombreMedico;
    }

    public void setPrimerNombreMedico(String primerNombreMedico) {
        this.primerNombreMedico = primerNombreMedico;
    }

    public String getSegundoNombreMedico() {
        return segundoNombreMedico;
    }

    public void setSegundoNombreMedico(String segundoNombreMedico) {
        this.segundoNombreMedico = segundoNombreMedico;
    }

    public String getPrimerApellidoMedico() {
        return primerApellidoMedico;
    }

    public void setPrimerApellidoMedico(String primerApellidoMedico) {
        this.primerApellidoMedico = primerApellidoMedico;
    }

    public String getSegundoApellidoMedico() {
        return segundoApellidoMedico;
    }

    public void setSegundoApellidoMedico(String segundoApellidoMedico) {
        this.segundoApellidoMedico = segundoApellidoMedico;
    }

    public String getNumDocMedico() {
        return numDocMedico;
    }

    public void setNumDocMedico(String numDocMedico) {
        this.numDocMedico = numDocMedico;
    }

    public int getMpps() {
        return mpps;
    }

    public void setMpps(int mpps) {
        this.mpps = mpps;
    }

    public String getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(String centroSalud) {
        this.centroSalud = centroSalud;
    }

    public String getCaracterDeclarante() {
        return caracterDeclarante;
    }

    public void setCaracterDeclarante(String caracterDeclarante) {
        this.caracterDeclarante = caracterDeclarante;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

}
