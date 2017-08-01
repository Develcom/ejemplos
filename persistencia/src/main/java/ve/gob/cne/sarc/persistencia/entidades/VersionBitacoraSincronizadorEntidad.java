package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;

/**
 * Relacion entre Version de un paquete de sincronizacion, Sincronizador y Bitacora
 */
@Entity
@Table(name = "C076T_VERSION_SYNC_BITACORA")
public class VersionBitacoraSincronizadorEntidad implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private BitacoraEntidad bitacora;
    private SincronizadorEntidad sincronizador;
    private VersionSincronizacionEntidad version;

    public VersionBitacoraSincronizadorEntidad() {
        //Constructor
    }

    /**
     * Constructor
     *
     * @param bitacora
     * @param sincronizador
     * @param version
     */
    public VersionBitacoraSincronizadorEntidad(BitacoraEntidad bitacora, SincronizadorEntidad sincronizador,
                                               VersionSincronizacionEntidad version) {
        setBitacora(bitacora);
        setSincronizador(sincronizador);
        setVersion(version);
    }

    @Id
    @Column(name = "id_version_sync_bitacora", unique = true, nullable = false)
    @SequenceGenerator(name = "VERSION_SYNC_BITACORA_SEQ", sequenceName = "C076S_CO_VERSION_SYNC_BITACORA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERSION_SYNC_BITACORA_SEQ")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarea", nullable = false)
    public BitacoraEntidad getBitacora() {
        return this.bitacora;
    }

    public void setBitacora(BitacoraEntidad bitacora) {
        this.bitacora = bitacora;
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
        VersionBitacoraSincronizadorEntidad other = (VersionBitacoraSincronizadorEntidad) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "VersionBitacoraSync [id=" + id + ", bitacora=" + bitacora.getIdTarea()
                + ", sincronizador=" + sincronizador.getNombre() + ", version=" + version.getCodigo()
                + "]";
    }

}
