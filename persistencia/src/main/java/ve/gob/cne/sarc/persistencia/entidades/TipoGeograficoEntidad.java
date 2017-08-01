package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the I033T_TIPO_GEOGRAFICO database table.
 */
@Entity
@Table(name = "I033T_TIPO_GEOGRAFICO")
@NamedQuery(name = "TipoGeograficoEntity.findAll", query = "SELECT t FROM TipoGeograficoEntidad t")
public class TipoGeograficoEntidad implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CO_TIPO_GEOGRAFICO", unique = true, nullable = false)
    private long id;

    @Column(name = "TX_DESCRIPCION", length = 2000)
    private String txDescripcion;

    // bi-directional many-to-one association to GeograficoEntity
    @OneToMany(mappedBy = "tipoGeografico")
    private List<GeograficoEntidad> geograficosTipoGeografico;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    private String codigoSincronizacion;

    public TipoGeograficoEntidad() {
        // Constructor vacío
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTxDescripcion() {
        return this.txDescripcion;
    }

    public void setTxDescripcion(String txDescripcion) {
        this.txDescripcion = txDescripcion;
    }

    public List<GeograficoEntidad> getGeograficosTipoGeografico() {
        return this.geograficosTipoGeografico;
    }

    public void setGeograficosTipoGeografico(List<GeograficoEntidad> geograficosTipoGeografico) {
        this.geograficosTipoGeografico = geograficosTipoGeografico;
    }
    
    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

    /**
     * Constructor con parámetros
     * @param geograficosTipoGeografico
     * @return
     */
    public GeograficoEntidad addGeograficosTipoGeografico(GeograficoEntidad geograficosTipoGeografico) {
        getGeograficosTipoGeografico().add(geograficosTipoGeografico);
        geograficosTipoGeografico.setTipoGeografico(this);

        return geograficosTipoGeografico;
    }

    /**
     * Remover Geografico de TipoGeografico
     * @param geograficosTipoGeografico
     * @return
     */
    public GeograficoEntidad removeGeograficosTipoGeografico(GeograficoEntidad geograficosTipoGeografico) {
        getGeograficosTipoGeografico().remove(geograficosTipoGeografico);
        geograficosTipoGeografico.setTipoGeografico(null);

        return geograficosTipoGeografico;
    }

}
