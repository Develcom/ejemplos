package ve.gob.cne.sarc.persistencia.entidades;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Entidad de UrlModuloWebEntidad que contiene los datos de conexion hacia los modulos web de SARC.
 * @author Posmagroup
 */
@Entity
@Table(name="I034T_URL_MODULO_WEB")
public class UrlModuloWebEntidad implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name="CO_URL_MODULO_WEB")
    @SequenceGenerator(name = "URL_MODULO_WEB_SEQ", sequenceName = "I034S_CO_URL_MODULO_WEB", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "URL_MODULO_WEB_SEQ")
    private Long id;

    @Column(name = "TX_HOST" , nullable=false)
    private String host;

    @Column(name = "TX_CONTEXTO" , nullable=false)
    private String contexto;

    @Column(name = "TX_PUNTO_ENTRADA" , nullable=false)
    private String puntoEntrada;

    @Column(name = "NU_ANGULAR_FLAG" , nullable=false)
    private long angularFlag;

    public UrlModuloWebEntidad() {
        // Constructor vacío
    }

    public UrlModuloWebEntidad(String host, String contexto, String puntoEntrada, long angularFlag) {
        this.host = host;
        this.contexto = contexto;
        this.puntoEntrada = puntoEntrada;
        this.angularFlag = angularFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public String getPuntoEntrada() {
        return puntoEntrada;
    }

    public void setPuntoEntrada(String puntoEntrada) {
        this.puntoEntrada = puntoEntrada;
    }

    public long getAngularFlag() {
        return angularFlag;
    }

    public void setAngularFlag(long angularFlag) {
        this.angularFlag = angularFlag;
    }
}
