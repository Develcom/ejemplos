package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
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

import ve.gob.cne.sarc.persistencia.disparadores.TipoOficinaModuloDisparador;

/**
 * TipoOficinaModuloEntidad.java
 *
 * @descripcion Se crea la clase TipoOficinaModuloEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C032T_TIPO_OFICINA_MODULO")
@EntityListeners({TipoOficinaModuloDisparador.class})
@NamedQueries({
    @NamedQuery(name = TipoOficinaModuloEntidad.BUSCAR_TODOS, query = "SELECT tom "
            + "FROM   TipoOficinaModuloEntidad tom"),
    @NamedQuery(name = TipoOficinaModuloEntidad.BUSCAR_POR_ID, query = "SELECT tom "
            + "FROM   TipoOficinaModuloEntidad tom "
            + "WHERE  tom.id = :id"),
    @NamedQuery(name = TipoOficinaModuloEntidad.BUSCAR_POR_ID_TIPO_OFICINA_MODULO, query = "SELECT tom "
            + "FROM   TipoOficinaModuloEntidad tom "
            + "WHERE  tom.tipoOficina.id = :id ")
})
public class TipoOficinaModuloEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "TipoOficinaModuloEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "TipoOficinaModuloEntidad.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_ID_TIPO_OFICINA_MODULO
            = "TipoOficinaModuloEntidad.buscarPorIdTipoOficinaModulo";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TIPO_OFICINA_MODULO", nullable = false, length = 22)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_MODULO", referencedColumnName = "CO_MODULO", nullable = false)
    private ModuloEntidad modulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TIPO_OFICINA", referencedColumnName = "CO_TIPO_OFICINA", nullable = false)
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
    @Column(name = "TX_OBSERVACION", nullable = true, length = 200)
    private String observacion;

    public TipoOficinaModuloEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ModuloEntidad getModulo() {
        return modulo;
    }

    public void setModulo(ModuloEntidad modulo) {
        this.modulo = modulo;
    }

    public TipoOficinaEntidad getTipoOficina() {
        return tipoOficina;
    }

    public void setTipoOficina(TipoOficinaEntidad tipoOficina) {
        this.tipoOficina = tipoOficina;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
