package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import ve.gob.cne.sarc.persistencia.disparadores.CitaDisparador;

/**
 * CitaEntidad.java
 * @descripcion Clase que contiene las entidades para el objeto de Cita Matrimonio
 * @version 
 * 13 de oct. de 2016
 * @author Domingo Rondon
 */
@Entity
@Table(name = "C079T_CITA")
@EntityListeners({CitaDisparador.class})
@NamedQueries({
    @NamedQuery(name = CitaEntidad.BUSCAR_POR_OFICINA, query = "SELECT cita "
            + "FROM CitaEntidad cita " + "WHERE  cita.lugarCelebracion = :oficina"),
    @NamedQuery(name = CitaEntidad.BUSCAR_CITAS_OFICINA_FECHA, query = "SELECT cita "
            + "FROM CitaEntidad cita " + "WHERE  cita.lugarCelebracion = :oficina and "
                    + " cita.horaCelebracion between :fechaInicio and :fechaFin"),
    @NamedQuery(name = CitaEntidad.COUNT_CITAS_OFICINA_FECHA, query = "SELECT count(cita.id) "
                            + "FROM CitaEntidad cita"+" WHERE  cita.lugarCelebracion = :oficina "
                            + " and cita.estatusCita.codigo = :estatus"
                            + " and cita.horaCelebracion between :fechaInicio and :fechaFin")
})
public class CitaEntidad implements Serializable{
    
    private static final long serialVersionUID = -2333049591247399654L;
    
    public static final String BUSCAR_POR_OFICINA = "CitaEntidad.buscarPorCodigoOficina";
    public static final String BUSCAR_CITAS_OFICINA_FECHA = "CitaEntidad.buscarCitas";
    public static final String COUNT_CITAS_OFICINA_FECHA = "CitaEntidad.contarCitarPorPeriodo";
    
    @Id
    @Basic(optional = false)
    @Column(name = "CO_CITA", nullable = false, length = 22)
    @SequenceGenerator(name = "CITA_SEQ", sequenceName = "C079S_CO_CITA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CITA_SEQ")
    private long id;
    
    @Basic(optional = false)
    @Column(name = "CO_LUGAR_CELEBRACION", nullable = false, length = 22)
    private String lugarCelebracion;
    
    @Basic(optional = false)
    @Column(name = "HH_CELEBRACION", nullable = false)
    private Timestamp horaCelebracion;
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_CELEBRACION", nullable = false)
    private Date fechaCelebracion;
    
    @Basic(optional = false)
    @Column(name = "IN_MULTIPLE", nullable = false)
    private Boolean multiple;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ESTATUS_CITA", referencedColumnName = "CO_ESTATUS_CITA", nullable = false)
    private EstatusCitaEntidad estatusCita;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_RESERVA_OFICINA_CITA", referencedColumnName = "CO_RESERVA_OFICINA_CITA", nullable = false)
    private OficinaCitaDetalleEntidad oficinaCitaDetalle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLugarCelebracion() {
        return lugarCelebracion;
    }

    public void setLugarCelebracion(String lugarCelebracion) {
        this.lugarCelebracion = lugarCelebracion;
    }

    public Timestamp getHoraCelebracion() {
        return horaCelebracion;
    }

    public void setHoraCelebracion(Timestamp horaCelebracion) {
        this.horaCelebracion = horaCelebracion;
    }

    public Date getFechaCelebracion() {
        return fechaCelebracion;
    }

    public void setFechaCelebracion(Date fechaCelebracion) {
        this.fechaCelebracion = fechaCelebracion;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public EstatusCitaEntidad getEstatusCita() {
        return estatusCita;
    }

    public void setEstatusCita(EstatusCitaEntidad estatusCita) {
        this.estatusCita = estatusCita;
    }

    public OficinaCitaDetalleEntidad getOficinaCitaDetalle() {
        return oficinaCitaDetalle;
    }

    public void setOficinaCitaDetalle(OficinaCitaDetalleEntidad oficinaCitaDetalle) {
        this.oficinaCitaDetalle = oficinaCitaDetalle;
    }
    
    

}
