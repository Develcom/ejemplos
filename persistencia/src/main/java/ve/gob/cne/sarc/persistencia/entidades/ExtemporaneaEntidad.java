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

import ve.gob.cne.sarc.persistencia.disparadores.ExtemporaneaListener;

/**
 * ExtemporaneaEntidad.java
 *
 * @descripcion Se crea la clase ExtemporaneaEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 06/09/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "X021T_EXTEMPORANEA")
@EntityListeners({ExtemporaneaListener.class})

public class ExtemporaneaEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_EXTEMPORANEA", nullable = false, length = 22)
    @SequenceGenerator(name = "EXTEMPORANEA_SEQ", sequenceName = "X021S_CO_EXTEMPORANEA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXTEMPORANEA_SEQ")
    private long id;

    @Basic(optional = false)
    @Column(name = "NU_PROVIDENCIA", nullable = false, length = 50)
    private String numeroProvidencia;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE_AUTORIDAD", nullable = false, length = 50)
    private String primerNombreAutoridad;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_AUTORIDAD", nullable = true, length = 50)
    private String segundoNombreAutoridad;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO_AUTORIDAD", nullable = false, length = 50)
    private String primerApellidoAutoridad;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_AUTORIDAD", nullable = true, length = 50)
    private String segundoApellidoAutoridad;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_PROVIDENCIA", nullable = false)
    private Date fechaProvidencia;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PROCEDENCIA", name = "CO_PROCEDENCIA", nullable = false)
    private ProcedenciaEntidad procedencia;

    public ExtemporaneaEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public ProcedenciaEntidad getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(ProcedenciaEntidad procedencia) {
        this.procedencia = procedencia;
    }

    public String getNumeroProvidencia() {
        return numeroProvidencia;
    }

    public void setNumeroProvidencia(String numeroProvidencia) {
        this.numeroProvidencia = numeroProvidencia;
    }

    public String getPrimerNombreAutoridad() {
        return primerNombreAutoridad;
    }

    public void setPrimerNombreAutoridad(String primerNombreAutoridad) {
        this.primerNombreAutoridad = primerNombreAutoridad;
    }

    public String getSegundoNombreAutoridad() {
        return segundoNombreAutoridad;
    }

    public void setSegundoNombreAutoridad(String segundoNombreAutoridad) {
        this.segundoNombreAutoridad = segundoNombreAutoridad;
    }

    public String getPrimerApellidoAutoridad() {
        return primerApellidoAutoridad;
    }

    public void setPrimerApellidoAutoridad(String primerApellidoAutoridad) {
        this.primerApellidoAutoridad = primerApellidoAutoridad;
    }

    public String getSegundoApellidoAutoridad() {
        return segundoApellidoAutoridad;
    }

    public void setSegundoApellidoAutoridad(String segundoApellidoAutoridad) {
        this.segundoApellidoAutoridad = segundoApellidoAutoridad;
    }

    public Date getFechaProvidencia() {
        return fechaProvidencia;
    }

    public void setFechaProvidencia(Date fechaProvidencia) {
        this.fechaProvidencia = fechaProvidencia;
    }

}
