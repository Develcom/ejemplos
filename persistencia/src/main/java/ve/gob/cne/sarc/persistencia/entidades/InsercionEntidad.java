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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.InsercionDisparador;

/**
 * InsercionEntidad.java
 *
 * @descripcion Se crea la clase InsercionEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 06/09/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "X029T_INSERCION")
@EntityListeners({InsercionDisparador.class})

public class InsercionEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_INSERCION", nullable = false, length = 22)
    @SequenceGenerator(name = "INSERCION_SEQ", sequenceName = "X029S_CO_INSERCION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSERCION_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PARROQUIA", name = "CO_PARROQUIA", nullable = false)
    private ParroquiaEntidad parroquia;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INSERCION", nullable = false)
    private Date fechaInsercion;

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

    @Basic(optional = true)
    @Column(name = "IN_VIVE", length = 1, nullable = true)
    private String indicadorVivo;

    @OneToOne(optional = false)
    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA")
    private ActaEntidad acta;

    public InsercionEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ParroquiaEntidad getParroquia() {
        return this.parroquia;
    }

    public void setParroquia(ParroquiaEntidad parroquia) {
        this.parroquia = parroquia;
    }

    public Date getFechaInsercion() {
        return this.fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
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

    public String getIndicadorVivo() {
        return this.indicadorVivo;
    }

    public void setIndicadorVivo(String indicadorVivo) {
        this.indicadorVivo = indicadorVivo;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }
}
