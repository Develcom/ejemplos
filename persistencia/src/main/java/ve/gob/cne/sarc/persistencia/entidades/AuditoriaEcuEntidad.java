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

import ve.gob.cne.sarc.persistencia.disparadores.AuditoriaEcuDisparador;

/**
 * AuditoriaEcuDisparador.java
 *
 * @descripcion Clase de Persistencia de la Entidad AuditoriaEcu correspondiente a la tabla X053T_AUDITORIA_ECU.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X053T_AUDITORIA_ECU")
@EntityListeners({AuditoriaEcuDisparador.class})

public class AuditoriaEcuEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_AUDITORIA_ECU", nullable = false, length = 22)
    @SequenceGenerator(name = "AUDITORIA_ECU_SEQ", sequenceName = "X053S_CO_AUDITORIA_ECU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITORIA_ECU_SEQ")
    private long id;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_ESTADO_ECU", nullable = false)
    private Date fechaEstadoEcu;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_REGISTRO", nullable = false)
    private Date fechaRegistro;

    @JoinColumn(name = "CO_ECU", referencedColumnName = "CO_ECU")
    @ManyToOne(optional = false)
    private EcuEntidad ecu;

    @JoinColumn(name = "CO_ESTADO_ECU", referencedColumnName = "CO_ESTADO_ECU")
    @ManyToOne(optional = true)
    private EstadoEcuEntidad estadoEcu;

    public AuditoriaEcuEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaEstadoEcu() {
        return fechaEstadoEcu;
    }

    public void setFechaEstadoEcu(Date fechaEstadoEcu) {
        this.fechaEstadoEcu = fechaEstadoEcu;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EcuEntidad getEcu() {
        return ecu;
    }

    public void setEcu(EcuEntidad ecu) {
        this.ecu = ecu;
    }

    public EstadoEcuEntidad getEstadoEcu() {
        return estadoEcu;
    }

    public void setEstadoEcu(EstadoEcuEntidad estadoEcu) {
        this.estadoEcu = estadoEcu;
    }

}
