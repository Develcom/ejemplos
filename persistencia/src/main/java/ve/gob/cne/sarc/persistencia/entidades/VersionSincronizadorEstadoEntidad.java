package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;

import java.util.Date;

/**
 * Relacion existente entre una version de un paquete sincronizable,
 * su sincronizador y el estado de procesamiento del mismo
 */
@Entity
@Table(name = "C077T_VERSION_SYNC_ESTADO")
public class VersionSincronizadorEstadoEntidad implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private EstadoSincronizacionEntidad estadoSync;
    private SincronizadorEntidad sincronizador;
    private VersionSincronizacionEntidad version;
    private Date fecha;

    public VersionSincronizadorEstadoEntidad() {
        //Constructor
    }

    /**
     * Constructor
     *
     * @param estadoSync
     * @param sincronizador
     * @param version
     * @param fecha
     */
    public VersionSincronizadorEstadoEntidad(EstadoSincronizacionEntidad estadoSync, SincronizadorEntidad sincronizador,
                                             VersionSincronizacionEntidad version, Date fecha) {
        setEstadoSync(estadoSync);
        setSincronizador(sincronizador);
        setVersion(version);
        this.fecha = fecha;
    }

    @Id
    @Column(name = "id_version_sync_estado", nullable = false)
    @SequenceGenerator(name = "VERSION_SYNC_ESTADO_SEQ", sequenceName = "C077S_CO_VERSION_SYNC_ESTADO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERSION_SYNC_ESTADO_SEQ")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_sync", nullable = false)
    public EstadoSincronizacionEntidad getEstadoSync() {
        return this.estadoSync;
    }

    public void setEstadoSync(EstadoSincronizacionEntidad estadoSync) {
        this.estadoSync = estadoSync;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sincronizador", nullable = false)
    public SincronizadorEntidad getSincronizador() {
        return this.sincronizador;
    }

    public void setSincronizador(SincronizadorEntidad sincronizador) {
        this.sincronizador = sincronizador;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_version", nullable = false)
    public VersionSincronizacionEntidad getVersion() {
        return this.version;
    }

    public void setVersion(VersionSincronizacionEntidad version) {
        this.version = version;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fe_fecha", nullable = false, length = 13)
    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VersionSincronizadorEstadoEntidad other = (VersionSincronizadorEstadoEntidad) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "VersionSyncEstado [id=" + id + ", estadoSync=" + estadoSync.getIdEstado()
                + ", sincronizador=" + sincronizador.getIdSincronizador() + ", version=" + version.getIdVersion()
                + ", fecha=" + fecha + "]";
    }

}
