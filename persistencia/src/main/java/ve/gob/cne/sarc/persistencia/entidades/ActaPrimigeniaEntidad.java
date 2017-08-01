package ve.gob.cne.sarc.persistencia.entidades;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import ve.gob.cne.sarc.persistencia.disparadores.ActaPrimigeniaDisparador;

@Entity
@Table(name = "X055T_ACTA_PRIMIGENIA")
@EntityListeners({ActaPrimigeniaDisparador.class})
/**
 * ActaPrimigeniaEntidad.java
 *
 * @descripcion Entidad donde se declara las propiedades de una Acta Primigenia
 * @version 1.0 11/10/2016
 * @author Oscar Montilla
 */
public class ActaPrimigeniaEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ACTA_PRIMIGENIA", nullable = false, length = 22)
    @SequenceGenerator(name = "ACTA_PRIMIGENIA_SEQ", sequenceName = "X055S_CO_ACTA_PRIMIGENIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTA_PRIMIGENIA_SEQ")
    private Long id;

    @Basic(optional = false)
    @Column(name = "NU_ACTA", nullable = false, length = 50)
    private String numeroActa;

    @Basic(optional = false)
    @Column(name = "NU_TOMO", nullable = false, length = 50)
    private String numeroTomo;

    @Basic(optional = false)
    @Column(name = "NU_FOLIO", nullable = false, length = 50)
    private String numeroFolio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INCRIPCION", nullable = false)
    private Date fechaIncripcion;

    @Basic(optional = true)
    @Column(name = "NU_CONSECUTIVO_PRIMIGENIA", nullable = true, length = 50)
    private int numeroConsecutivo;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA", name = "CO_OFICINA", nullable = false)
    private OficinaEntidad oficina;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_SOLICITUD", name = "CO_SOLICITUD", nullable = false)
    private SolicitudEntidad solicitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(String numeroActa) {
        this.numeroActa = numeroActa;
    }

    public String getNumeroTomo() {
        return numeroTomo;
    }

    public void setNumeroTomo(String numeroTomo) {
        this.numeroTomo = numeroTomo;
    }

    public String getNumeroFolio() {
        return numeroFolio;
    }

    public void setNumeroFolio(String numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    public Date getFechaIncripcion() {
        return fechaIncripcion;
    }

    public void setFechaIncripcion(Date fechaIncripcion) {
        this.fechaIncripcion = fechaIncripcion;
    }

    public SolicitudEntidad getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public int getNumeroConsecutivo() {
        return numeroConsecutivo;
    }

    public void setNumeroConsecutivo(int numeroConsecutivo) {
        this.numeroConsecutivo = numeroConsecutivo;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad Oficina) {
        this.oficina = Oficina;
    }

}
