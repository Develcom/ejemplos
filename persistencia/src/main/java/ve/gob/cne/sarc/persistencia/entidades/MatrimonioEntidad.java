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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.MatrimonioDisparador;

/**
 * MatrimonioEntidad.java
 *
 * @descripcion Se crea la clase MatrimonioEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 24/08/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "X048T_MATRIMONIO")
@EntityListeners({MatrimonioDisparador.class})

public class MatrimonioEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_MATRIMONIO", nullable = false, length = 22)
    @SequenceGenerator(name = "MATRIMONIO_SEQ", sequenceName = "X048S_CO_MATRIMONIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MATRIMONIO_SEQ")
    private long id;

    @Basic(optional = false)
    @Column(name = "DI_MATRIMONIO", nullable = false, length = 500)
    private String direccion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_MATRIMONIO", nullable = false)
    private Date fecha;

    @Basic(optional = true)
    @Column(name = "NU_CAPITULACION", nullable = true, length = 15)
    private long numCapitulacion;

    @Basic(optional = true)
    @Column(name = "NU_PROTOCOLO", nullable = true, length = 15)
    private long numProtocolo;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_CAPITULACION", nullable = true)
    private Date fechaCapitulacion;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_NOMBRE_AUT_CAP", nullable = true, length = 50)
    private String primerNombreAutoridadCap;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_AUT_CAP", nullable = true, length = 50)
    private String segundoNombreAutoridadCap;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_APELLIDO_AUT_CAP", nullable = true, length = 50)
    private String primerApellidoAutoridadCap;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_AUT_CAP", nullable = true, length = 50)
    private String segundoApellidoAutoridadCap;

    @Basic(optional = true)
    @Column(name = "NB_NOTARIA", nullable = true, length = 50)
    private String notaria;

    @Basic(optional = true)
    @Column(name = "NU_DOCUMENTO_INSERTAR", nullable = true, length = 15)
    private long numDocInsertar;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_DOCUMENTO_INSERTAR", nullable = true)
    private Date fechaDocInsertar;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_NOMBRE_AUT_INS", nullable = true, length = 50)
    private String primerNombreAutIns;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_AUT_INS", nullable = true, length = 50)
    private String segundoNombreAutIns;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_APELLIDO_AUT_INS", nullable = true, length = 50)
    private String primerApellidoAutIns;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_AUT_INS", nullable = true, length = 50)
    private String segundoApellidoAutIns;

    @Basic(optional = true)
    @Column(name = "TX_EXTRACTO_DOCUMENTO", nullable = true, length = 100)
    private String extracto;

    @Basic(optional = true)
    @Column(name = "TX_NOTA_MARGINAL", nullable = false, length = 500)
    private String notaMarginal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA", nullable = false)
    private ActaEntidad acta;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_ACTA_UEH", referencedColumnName = "CO_ACTA", nullable = true)
    private ActaEntidad actaUEH;

    public MatrimonioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getNumCapitulacion() {
        return numCapitulacion;
    }

    public void setNumCapitulacion(long numCapitulacion) {
        this.numCapitulacion = numCapitulacion;
    }

    public long getNumProtocolo() {
        return numProtocolo;
    }

    public Date getFechaCapitulacion() {
        return fechaCapitulacion;
    }

    public void setFechaCapitulacion(Date fechaCapitulacion) {
        this.fechaCapitulacion = fechaCapitulacion;
    }

    public String getPrimerNombreAutoridadCap() {
        return primerNombreAutoridadCap;
    }

    public void setPrimerNombreAutoridadCap(String primerNombreAutoridadCap) {
        this.primerNombreAutoridadCap = primerNombreAutoridadCap;
    }

    public String getSegundoNombreAutoridadCap() {
        return segundoNombreAutoridadCap;
    }

    public void setSegundoNombreAutoridadCap(String segundoNombreAutoridadCap) {
        this.segundoNombreAutoridadCap = segundoNombreAutoridadCap;
    }

    public String getPrimerApellidoAutoridadCap() {
        return primerApellidoAutoridadCap;
    }

    public void setPrimerApellidoAutoridadCap(String primerApellidoAutoridadCap) {
        this.primerApellidoAutoridadCap = primerApellidoAutoridadCap;
    }

    public String getSegundoApellidoAutoridadCap() {
        return segundoApellidoAutoridadCap;
    }

    public void setSegundoApellidoAutoridadCap(String segundoApellidoAutoridadCap) {
        this.segundoApellidoAutoridadCap = segundoApellidoAutoridadCap;
    }

    public String getNotaria() {
        return notaria;
    }

    public void setNotaria(String notaria) {
        this.notaria = notaria;
    }

    public long getNumDocInsertar() {
        return numDocInsertar;
    }

    public void setNumDocInsertar(long numDocInsertar) {
        this.numDocInsertar = numDocInsertar;
    }

    public Date getFechaDocInsertar() {
        return fechaDocInsertar;
    }

    public void setFechaDocInsertar(Date fechaDocInsertar) {
        this.fechaDocInsertar = fechaDocInsertar;
    }

    public String getPrimerNombreAutIns() {
        return primerNombreAutIns;
    }

    public void setPrimerNombreAutIns(String primerNombreAutIns) {
        this.primerNombreAutIns = primerNombreAutIns;
    }

    public String getSegundoNombreAutIns() {
        return segundoNombreAutIns;
    }

    public void setSegundoNombreAutIns(String segundoNombreAutIns) {
        this.segundoNombreAutIns = segundoNombreAutIns;
    }

    public String getPrimerApellidoAutIns() {
        return primerApellidoAutIns;
    }

    public void setPrimerApellidoAutIns(String primerApellidoAutIns) {
        this.primerApellidoAutIns = primerApellidoAutIns;
    }

    public String getSegundoApellidoAutIns() {
        return segundoApellidoAutIns;
    }

    public void setSegundoApellidoAutIns(String segundoApellidoAutIns) {
        this.segundoApellidoAutIns = segundoApellidoAutIns;
    }

    public String getExtracto() {
        return extracto;
    }

    public void setExtracto(String extracto) {
        this.extracto = extracto;
    }

    public String getNotaMarginal() {
        return notaMarginal;
    }

    public void setNotaMarginal(String notaMarginal) {
        this.notaMarginal = notaMarginal;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

    public ActaEntidad getActaUEH() {
        return actaUEH;
    }

    public void setActaUEH(ActaEntidad actaUEH) {
        this.actaUEH = actaUEH;
    }

    public void setNumProtocolo(long numProtocolo) {
        this.numProtocolo = numProtocolo;
    }

}
