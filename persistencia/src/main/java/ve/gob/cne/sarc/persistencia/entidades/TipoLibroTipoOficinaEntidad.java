package ve.gob.cne.sarc.persistencia.entidades;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.TipoLibroTipoOficinaDisparador;

/**
 * TipoLibroTipoOficinaEntidad.java
 *
 * @descripcion Se crea la clase ActaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C015T_TLIBRO_TOFICINA")
@EntityListeners({TipoLibroTipoOficinaDisparador.class})
@NamedQueries({
    @NamedQuery(name = TipoLibroTipoOficinaEntidad.BUSCAR_POR_ID_TIPO_OFICINA, query = "SELECT tlt "
            + "FROM   TipoLibroTipoOficinaEntidad tlt "
            + "WHERE  tlt.tipoOficina.id = :id")})
public class TipoLibroTipoOficinaEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_ID_TIPO_OFICINA = "TipoLibroTipoOficinaEntidad.buscarPorIdTipoOficina";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TLIBRO_TOFICINA", nullable = false, length = 22)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_TIPO_LIBRO", name = "CO_TIPO_LIBRO", nullable = false)
    private TipoLibroEntidad tipoLibro;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_TIPO_OFICINA", name = "CO_TIPO_OFICINA", nullable = false)
    private TipoOficinaEntidad tipoOficina;

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
    private String descripcion;

    public TipoLibroTipoOficinaEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoLibroEntidad getTipoLibro() {
        return tipoLibro;
    }

    public void setTipoLibro(TipoLibroEntidad tipoLibro) {
        this.tipoLibro = tipoLibro;
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

    public TipoOficinaEntidad getTipoOficina() {
        return tipoOficina;
    }

    public void setTipoOficina(TipoOficinaEntidad tipoOficina) {
        this.tipoOficina = tipoOficina;
    }

}
