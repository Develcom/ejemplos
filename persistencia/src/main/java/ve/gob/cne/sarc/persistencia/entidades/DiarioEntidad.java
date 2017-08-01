package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.DiarioDisparador;

/**
 * DiarioEntidad.java
 *
 * @descripcion Se crea la clase DiarioEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 01/08/2016
 * @author Oscar Montilla
 *
 */
@Entity
@Table(name = "X041T_DIARIO")
@EntityListeners({DiarioDisparador.class})

public class DiarioEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_DIARIO", nullable = false, length = 22)
    @SequenceGenerator(name = "DIARIO_SEQ", sequenceName = "X041S_CO_DIARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIARIO_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_SOLICITUD", name = "CO_SOLICITUD")
    private SolicitudEntidad solicitud;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_REGISTRO", nullable = false)
    private Date fechaRegistro;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_DIARIO_ESTATUS", name = "CO_DIARIO_ESTATUS")
    private DiarioEstatusEntidad diarioEstatus;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_LIBRO_DIARIO", name = "CO_LIBRO_DIARIO", nullable = false)
    private LibroDiarioEntidad libroDiario;

    public DiarioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public SolicitudEntidad getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public DiarioEstatusEntidad getEstatus() {
        return diarioEstatus;
    }

    public void setEstatus(DiarioEstatusEntidad diarioEstatus) {
        this.diarioEstatus = diarioEstatus;
    }

    public DiarioEstatusEntidad getDiarioEstatus() {
        return diarioEstatus;
    }

    public void setDiarioEstatus(DiarioEstatusEntidad diarioEstatus) {
        this.diarioEstatus = diarioEstatus;
    }

    public LibroDiarioEntidad getLibroDiario() {
        return libroDiario;
    }

    public void setLibroDiario(LibroDiarioEntidad libroDiario) {
        this.libroDiario = libroDiario;
    }

}
