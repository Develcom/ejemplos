package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;
import ve.gob.cne.sarc.persistencia.disparadores.EntePublicoDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * EntePublicoEntidad.java
 *
 * @author Oscar Montilla
 * @descripcion Se crea la clase EntePublicoEntidad donde se Realizan los Query de consulta de cada metodo
 * @versionm 1.0 11/08/2016
 */
@Entity
@Table(name = "C031T_ENTE_PUBLICO")
@EntityListeners({EntePublicoDisparador.class})
@NamedQueries({
    @NamedQuery(name = EntePublicoEntidad.BUSCAR_POR_CODIGO, query = "SELECT epu "
            + "FROM   EntePublicoEntidad epu WHERE  epu.id = :id")
})
@ClassEspecification(name = "Ente Público", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class EntePublicoEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_CODIGO = "EntePublicoEntidad.buscarPorCodigo";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ENTE_PUBLICO", nullable = false, length = 22)
    @SequenceGenerator(name = "ENTE_PUBLICO_SEQ", sequenceName = "C031S_CO_ENTE_PUBLICO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTE_PUBLICO_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_ENTE_PUBLICO", nullable = false, length = 200)
    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @FieldEspecification(input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n", size = 200)
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entePublico", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SolicitanteEntidad> solicitante = new ArrayList<>();

    public EntePublicoEntidad() {
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

    public List<SolicitanteEntidad> getSolicitantes() {
        return solicitante;
    }

    /**
     *
     * setsolicitantes
     *
     *
     * @param solicitante
     */
    public void setsolicitantes(List<SolicitanteEntidad> solicitante) {
        this.solicitante = solicitante;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntePublicoEntidad that = (EntePublicoEntidad) o;
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
