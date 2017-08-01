package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

/**
 * Se crea la clase EscenarioEntidad donde se Realizan los Query de consulta de cada metodo
 *
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C016T_ESCENARIO")
@NamedQueries({
        @NamedQuery(name = EscenarioEntidad.BUSCAR_TODOS,
                query = "SELECT esc FROM EscenarioEntidad esc"),
        @NamedQuery(name = EscenarioEntidad.BUSCAR_POR_ID,
                query = "SELECT esc FROM EscenarioEntidad esc WHERE esc.id = :id"),
        @NamedQuery(name = EscenarioEntidad.BUSCAR_POR_NOMBRE,
                query = "SELECT esc FROM EscenarioEntidad esc WHERE UPPER(esc.nombre) = :nombre"),
        @NamedQuery(name = EscenarioEntidad.BUSCAR_POR_NOMBRE_PATRON,
                query = "SELECT esc FROM EscenarioEntidad esc WHERE UPPER(esc.nombre) LIKE :nombre")})
@ClassEspecification(name = "Escenario", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class EscenarioEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "EscenarioEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "EscenarioEntidad.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_NOMBRE = "EscenarioEntidad.BUSCAR_POR_NOMBRE";
    public static final String BUSCAR_POR_NOMBRE_PATRON = "EscenarioEntidad.BUSCAR_POR_NOMBRE_PATRON";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ESCENARIO", nullable = false, length = 4)
    @SequenceGenerator(name = "ESCENARIO_SEQ", sequenceName = "C016S_CO_ESCENARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESCENARIO_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_ESCENARIO", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Basic(optional = false)
    @Column(name = "NU_ORDEN", nullable = false, length = 3)
    @FieldEspecification(size = 3, required = true, type = "java.lang.Integer", constraints = "/[0-1]/", label = "N&uacute;mero", order = 3, step = 0)
    private int numero;

    @Basic(optional = false)
    @Column(name = "IN_CONTINUA", nullable = false, length = 2)
    @FieldEspecification(size = 2, required = true, type = "java.lang.Integer", constraints = "/[0-1]/", label = "Continua", order = 4, step = 0)
    private int continua;

    @Basic(optional = false)
    @Column(name = "IN_AUTOMATICO", nullable = false, length = 5)
    @FieldEspecification(size = 5, required = true, type = "java.lang.Integer", constraints = "/[0-1]/", label = "Autom&aacute;tico", order = 5, step = 0)
    private int automatico;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false, length = 10)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true, length = 10)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 100)
    @FieldEspecification(size = 100, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;
    

    public EscenarioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getContinua() {
        return continua;
    }

    public void setContinua(int indicadorContinua) {
        this.continua = indicadorContinua;
    }

    public int getAutomatico() {
        return automatico;
    }

    public void setAutomatico(int indicadorAutomatico) {
        this.automatico = indicadorAutomatico;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EscenarioEntidad that = (EscenarioEntidad) o;
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