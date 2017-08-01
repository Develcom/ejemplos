package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;
import ve.gob.cne.sarc.persistencia.disparadores.DiarioEstatusDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * DiarioEstatusEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0
 * @descripcion Se crea la clase DiarioEstatusEntidad donde se Realizan los Query de consulta de cada metodo
 */
@Entity
@Table(name = "C060T_DIARIO_ESTATUS")
@EntityListeners({DiarioEstatusDisparador.class})
@ClassEspecification(name = "Estatus de Libro Diario", identifier = "NombreDiarioEstatus",
        canBeListed = true, generatesTask=SincronizationPolicy.BROADCAST)
public class DiarioEstatusEntidad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "CO_DIARIO_ESTATUS", nullable = false, length = 22)
    @SequenceGenerator(name = "DIARIO_ESTATUS_SEQ", sequenceName = "C060S_CO_DIARIO_ESTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIARIO_ESTATUS_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "IDEN_DIARIO_ESTATUS", unique = true, nullable = false, length = 5
    )
    @FieldEspecification(size = 5, required = true, label = "C&oacute;digo", order = 1, step = 0, placeHolder = "Ejemplo: AAA00")
    private String codigoDiarioEstatus;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false, length = 10)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true, length = 10)
    private Date fechaFin;

    @Basic(optional = false)
    @Column(name = "NB_DIARIO_ESTATUS", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombreDiarioEstatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "diarioEstatus", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DiarioEntidad> diarioEstatus;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public DiarioEstatusEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getNombreDiarioEstatus() {
        return nombreDiarioEstatus;
    }

    public void setNombreDiarioEstatus(String nombreDiarioEstatus) {
        this.nombreDiarioEstatus = nombreDiarioEstatus;
    }

    public List<DiarioEntidad> getDiarioEstatus() {
        return diarioEstatus;
    }

    public void setDiarioEstatus(List<DiarioEntidad> diarioEstatus) {
        this.diarioEstatus = diarioEstatus;
    }

    public String getCodigoDiarioEstatus() {
        return codigoDiarioEstatus;
    }

    public void setCodigoDiarioEstatus(String codigoDiarioEstatus) {
        this.codigoDiarioEstatus = codigoDiarioEstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiarioEstatusEntidad that = (DiarioEstatusEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }
}
