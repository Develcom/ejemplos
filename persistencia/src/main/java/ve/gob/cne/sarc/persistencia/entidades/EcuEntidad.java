package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.EcuDisparador;

/**
 * EcuEntidad.java
 *
 * @descripcion Se crea la clase EcuEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 063/09/2016
 */
@Entity
@Table(name = "X049T_ECU")
@EntityListeners({EcuDisparador.class})
@NamedQueries({
    @NamedQuery(name = EcuEntidad.BUSCAR_POR_NUMERO_DOCUMENTO, query = "SELECT ecu "
            + " FROM   EcuEntidad ecu WHERE ecu.ciudadano.numeroDocIdentidad = :numeroDocIdentidad"),
    @NamedQuery(name = EcuEntidad.BUSCAR_POR_CIUDADANO_CEDULA, query = "SELECT CASE WHEN COUNT(ecu) > 0 "
            + "THEN true ELSE false END FROM EcuEntidad ecu "
            + "WHERE ecu.ciudadano.numeroDocIdentidad = :numeroDocIdentidad")

})

public class EcuEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_NUMERO_DOCUMENTO = "EcuEntidad.findByNumeroDocumento";
    public static final String BUSCAR_POR_CIUDADANO_CEDULA = "EcuEntidad.existsEcuByCiudadanoNumeroDocIdentidad";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ECU", nullable = false, length = 22)
    @SequenceGenerator(name = "ECU_SEQ", sequenceName = "X049S_CO_ECU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECU_SEQ")
    private long id;

    @Basic(optional = false)
    @Column(name = "FE_CREACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_CIUDADANO", name = "CO_CIUDADANO")
    private CiudadanoEntidad ciudadano;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ecu", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<EcuParticipanteActaEntidad> ecuParticipanteActaEntidad = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ecu", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<AuditoriaEcuEntidad> auditoriaEcu = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ecu", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<EcuNuiEntidad> ecuNui = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ESTADO_ECU", referencedColumnName = "CO_ESTADO_ECU", nullable = false)
    private EstadoEcuEntidad estadoEcu;

    public EcuEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CiudadanoEntidad getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(CiudadanoEntidad ciudadano) {
        this.ciudadano = ciudadano;
    }

    public List<EcuParticipanteActaEntidad> getEcuParticipanteActaEntidad() {
        return ecuParticipanteActaEntidad;
    }

    public void setEcuParticipanteActaEntidad(List<EcuParticipanteActaEntidad> ecuParticipanteActaEntidad) {
        this.ecuParticipanteActaEntidad = ecuParticipanteActaEntidad;
    }

    public EstadoEcuEntidad getEstadoEcu() {
        return estadoEcu;
    }

    public void setEstadoEcu(EstadoEcuEntidad estadoEcu) {
        this.estadoEcu = estadoEcu;
    }

    public List<AuditoriaEcuEntidad> getAuditoriaEcu() {
        return auditoriaEcu;
    }

    public void setAuditoriaEcu(List<AuditoriaEcuEntidad> auditoriaEcu) {
        this.auditoriaEcu = auditoriaEcu;
    }

    public List<EcuNuiEntidad> getEcuNui() {
        return ecuNui;
    }

    public void setEcuNui(List<EcuNuiEntidad> ecuNui) {
        this.ecuNui = ecuNui;
    }

}
