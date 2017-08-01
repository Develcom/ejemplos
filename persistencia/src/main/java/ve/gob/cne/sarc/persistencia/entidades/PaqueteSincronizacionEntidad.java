package ve.gob.cne.sarc.persistencia.entidades;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;


/**
 * Entidad representativa de paquetes de sincronizacion
 */
@Entity
@Table(name = "C075T_PAQUETE_SINCRONIZACION")
public class PaqueteSincronizacionEntidad implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_paquete_sincronizacion", nullable = false)
    @SequenceGenerator(name = "PAQUETE_SINCRONIZACION_SEQ", sequenceName = "C075S_CO_PAQ_SINCRONIZACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAQUETE_SINCRONIZACION_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sincronizador", nullable = false)
    private SincronizadorEntidad sincronizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_version", nullable = false)
    private VersionSincronizacionEntidad version;

    @Column(name = "nb_nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "co_hash", nullable = false, length = 100)
    private String hash;

    @Column(name = "in_desde_principal", nullable = false)
    private boolean desdePrincipal;
    
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Timestamp fechaInicio;
    

    public PaqueteSincronizacionEntidad() {
        // Constructor
    }

    /**
     * Constructor
     *
     * @param sincronizador
     * @param version
     * @param nombre
     * @param hash
     * @param desdePrincipal
     */
    public PaqueteSincronizacionEntidad(SincronizadorEntidad sincronizador,
                                        VersionSincronizacionEntidad version, String nombre, String hash,
                                        boolean desdePrincipal) {
        setVersion(version);
        setSincronizador(sincronizador);
        this.nombre = nombre;
        this.hash = hash;
        this.desdePrincipal = desdePrincipal;
        Date date = new Date();
        this.fechaInicio=new Timestamp(date.getTime());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SincronizadorEntidad getSincronizador() {
        return this.sincronizador;
    }

    public void setSincronizador(SincronizadorEntidad sincronizador) {
        this.sincronizador = sincronizador;
    }

    public VersionSincronizacionEntidad getVersion() {
        return this.version;
    }

    public void setVersion(VersionSincronizacionEntidad version) {
        this.version = version;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean getDesdePrincipal() {
        return this.desdePrincipal;
    }

    public boolean isDesdePrincipal() {
        return this.desdePrincipal;
    }

    public void setDesdePrincipal(boolean desdePrincipal) {
        this.desdePrincipal = desdePrincipal;
    }

    public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
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
        PaqueteSincronizacionEntidad other = (PaqueteSincronizacionEntidad) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Paquete [id=" + id + ", sincronizador="
                + sincronizador.getIdSincronizador() + ", version="
                + version.getIdVersion() + ", nombre=" + nombre + ", hash="
                + hash + ", desdePrincipal=" + desdePrincipal + "]";
    }

}
