package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ve.gob.cne.sarc.persistencia.disparadores.TramiteRolDisparador;

/**
 * TramiteRolEntidad.java
 *
 * @descripcion Se crea la clase TramiteRolEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C030T_TRAMITE_ROL")
@EntityListeners({TramiteRolDisparador.class})
public class TramiteRolEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TRAMITE_ROL", nullable = false, length = 22)
    @GeneratedValue(generator = "generador_tramite_rol_hibernate_increment")
    @org.hibernate.annotations.GenericGenerator(name = "generador_tramite_rol_hibernate_increment",
            strategy = "increment")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TRAMITE", referencedColumnName = "CO_TRAMITE", nullable = false)
    private TramiteEntidad tramite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ROL", referencedColumnName = "CO_ROL", nullable = false)
    private RolEntidad rol;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_TRAMITE_ROL_ESTATUS",
            referencedColumnName = "CO_TRAMITE_ROL_ESTATUS", nullable = true)
    private TramiteRolEstatusEntidad tramiteRolEstatus;

    public TramiteRolEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TramiteEntidad getTramite() {
        return tramite;
    }

    public void setTramite(TramiteEntidad tramite) {
        this.tramite = tramite;
    }

    public RolEntidad getRol() {
        return rol;
    }

    public void setRol(RolEntidad rol) {
        this.rol = rol;
    }

    public TramiteRolEstatusEntidad getEstatus() {
        return tramiteRolEstatus;
    }

    public void setEstatus(TramiteRolEstatusEntidad tramiteRolEstatus) {
        this.tramiteRolEstatus = tramiteRolEstatus;
    }
}
