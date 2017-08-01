package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ve.gob.cne.sarc.persistencia.disparadores.DireccionCiudadanoDisparador;

/**
 * DireccionCiudadanoEntidad.java
 *
 * @descripcion Se crea la clase DireccionCiudadanoEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "X042T_DIRECCION_CIUDADANO")
@EntityListeners({DireccionCiudadanoDisparador.class})
@NamedQueries({
    @NamedQuery(name = DireccionCiudadanoEntidad.BUSCAR_TODOS, query = "SELECT  dci "
            + "FROM    DireccionCiudadanoEntidad dci"),
    @NamedQuery(name = DireccionCiudadanoEntidad.BUSCAR_POR_ID, query = "SELECT dci "
            + "FROM   DireccionCiudadanoEntidad dci "
            + "WHERE  dci.id = :id")})
public class DireccionCiudadanoEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "DireccionCiudadanoEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "DireccionCiudadanoEntidad.BUSCAR_POR_ID";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_DIRECCION_CIUDADANO", nullable = false, length = 22)
    @SequenceGenerator(name = "DIRECCION_CIUDADANO_SEQ", sequenceName = "X042S_CO_DIRECCION_CIUDADANO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIRECCION_CIUDADANO_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_CIUDADANO", name = "CO_CIUDADANO", nullable = false)
    private CiudadanoEntidad ciudadano;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_CIUDAD", name = "CO_CIUDAD", nullable = false)
    private CiudadEntidad ciudad;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PARROQUIA", name = "CO_PARROQUIA", nullable = false)
    private ParroquiaEntidad parroquia;

    @Basic(optional = false)
    @Column(name = "IN_TIPO", nullable = false, length = 5)
    private String tipo;

    @Basic(optional = false)
    @Column(name = "IN_ESTATUS", nullable = false, length = 5)
    private String estatus;

    @Basic(optional = false)
    @Column(name = "DI_DETALLE", nullable = false, length = 100)
    private String detalle;

    public DireccionCiudadanoEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CiudadanoEntidad getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(CiudadanoEntidad ciudadano) {
        this.ciudadano = ciudadano;
    }

    public CiudadEntidad getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadEntidad ciudad) {
        this.ciudad = ciudad;
    }

    public ParroquiaEntidad getParroquia() {
        return parroquia;
    }

    public void setParroquia(ParroquiaEntidad parroquia) {
        this.parroquia = parroquia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

}
