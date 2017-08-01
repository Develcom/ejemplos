package ve.gob.cne.sarc.persistencia.entidades;

// Generated Jul 27, 2016 10:21:23 AM by Hibernate Tools 4.3.1

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Version de paquetes de sincronizacion
 */
@Entity
@Table(name = "K006T_VERSION")
public class VersionSincronizacionEntidad implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long idVersion;
    private Integer codigo;
    private boolean difusion;
    private Boolean esExterna = false;
    private List<VersionBitacoraSincronizadorEntidad> versionBitacoraSyncs = new ArrayList<>(0);
    private List<VersionSincronizadorEstadoEntidad> versionSyncEstados = new ArrayList<>(0);
    private List<PaqueteSincronizacionEntidad> paquetes = new ArrayList<>(0);

    public VersionSincronizacionEntidad() {
        //Constructor
    }

    /**
     * Constructor
     *
     * @param difusion
     */
    public VersionSincronizacionEntidad(boolean difusion) {
        this.difusion = difusion;
    }

    /**
     * Constructor
     *
     * @param difusion
     * @param esExterna
     * @param versionBitacoraSyncs
     * @param versionSyncEstados
     * @param paquetes
     */
    public VersionSincronizacionEntidad(boolean difusion, Boolean esExterna,
                                        List<VersionBitacoraSincronizadorEntidad> versionBitacoraSyncs,
                                        List<VersionSincronizadorEstadoEntidad> versionSyncEstados,
                                        List<PaqueteSincronizacionEntidad> paquetes) {
        this.difusion = difusion;
        this.esExterna = esExterna;
        this.versionBitacoraSyncs = versionBitacoraSyncs;
        this.versionSyncEstados = versionSyncEstados;
        this.paquetes = paquetes;
    }

    @Id
    @Column(name = "id_version", unique = true, nullable = false)
    @SequenceGenerator(name = "VERSION_SEQ", sequenceName = "K006S_CO_VERSION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERSION_SEQ")
    public Long getIdVersion() {
        return this.idVersion;
    }

    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
    }

    @Column(name = "co_codigo", nullable = false)
    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Column(name = "in_difusion", nullable = false)
    public boolean isDifusion() {
        return this.difusion;
    }

    public void setDifusion(boolean difusion) {
        this.difusion = difusion;
    }

    @Column(name = "in_es_externa")
    public Boolean getEsExterna() {
        return this.esExterna;
    }

    public void setEsExterna(Boolean esExterna) {
        this.esExterna = esExterna;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "version")
    public List<VersionBitacoraSincronizadorEntidad> getVersionBitacoraSyncs() {
        return this.versionBitacoraSyncs;
    }

    public void setVersionBitacoraSyncs(List<VersionBitacoraSincronizadorEntidad> versionBitacoraSyncs) {
        this.versionBitacoraSyncs = versionBitacoraSyncs;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "version")
    public List<VersionSincronizadorEstadoEntidad> getVersionSyncEstados() {
        return this.versionSyncEstados;
    }

    public void setVersionSyncEstados(List<VersionSincronizadorEstadoEntidad> versionSyncEstados) {
        this.versionSyncEstados = versionSyncEstados;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "version")
    public List<PaqueteSincronizacionEntidad> getPaquetes() {
        return this.paquetes;
    }

    public void setPaquetes(List<PaqueteSincronizacionEntidad> paquetes) {
        this.paquetes = paquetes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idVersion ^ (idVersion >>> 32));
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
        VersionSincronizacionEntidad other = (VersionSincronizacionEntidad) obj;
        if (idVersion != other.idVersion)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Version [idVersion=" + idVersion + ", version=" + codigo
                + ", difusion=" + difusion + ", esExterna=" + esExterna
                + ", versionBitacoraSyncs=" + versionBitacoraSyncs
                + ", versionSyncEstados=" + versionSyncEstados + ", paquetes="
                + paquetes + "]";
    }

}
