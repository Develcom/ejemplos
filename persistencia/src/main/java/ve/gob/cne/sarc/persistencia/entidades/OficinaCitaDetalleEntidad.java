package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.OficinaCitaDetalleDisparador;

/**
 * OficinaCitaDetalleEntidad.java
 * @descripcion Entidad que contiene el detalle de las citas Configuradas
 * @version 
 * 13 de oct. de 2016
 * @author Domingo Rondon
 */
@Entity
@Table(name = "X056T_RESERVA_OFICINA_CITA")
@EntityListeners({OficinaCitaDetalleDisparador.class})
@NamedQueries({
    @NamedQuery(name = OficinaCitaDetalleEntidad.BUSCAR_POR_OFICINA_FECHA, query = "SELECT oficita "
            + "FROM OficinaCitaDetalleEntidad oficita " + "WHERE  oficita.oficinaCita.oficina = :oficina and "
                    + "oficita.fechaCelebracion between :fechaInicio and :fechaFin"),
    @NamedQuery(name = OficinaCitaDetalleEntidad.BUSCAR_POR_OFICINA, query = "SELECT oficita "
            + "FROM OficinaCitaDetalleEntidad oficita " + "WHERE  oficita.oficinaCita.oficina = :oficina ")
})
public class OficinaCitaDetalleEntidad implements Serializable{
    
    private static final long serialVersionUID = -2333049591247399654L;
    
    public static final String BUSCAR_POR_OFICINA_FECHA = "OficinaCitaDetalleEntidad.buscarPorCodigoOficinaFecha";
    public static final String BUSCAR_POR_OFICINA = "OficinaCitaDetalleEntidad.buscarPorCodigoOficina";
    
    @Id
    @Basic(optional = false)
    @Column(name = "CO_RESERVA_OFICINA_CITA", nullable = false, length = 22)
    @SequenceGenerator(name = "RESERVA_OC_SEQ", sequenceName = "X056S_CO_RESV_OFIC_CITA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVA_OC_SEQ")
    private long id;
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_CELEBRACION", nullable = false)
    private Date fechaCelebracion;
    
    @Basic(optional = false)
    @Column(name = "CA_CITAS_DISPONIBLES", nullable = false, length = 22)
    private Integer cantidadCitasDisponibles;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficinaCitaDetalle", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CitaEntidad> citas;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_OFICINA_CITA", referencedColumnName = "CO_OFICINA_CITA", nullable = false)
    private OficinaCitaEntidad oficinaCita;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCelebracion() {
        return fechaCelebracion;
    }

    public void setFechaCelebracion(Date fechaCelebracion) {
        this.fechaCelebracion = fechaCelebracion;
    }

    public Integer getCantidadCitasDisponibles() {
        return cantidadCitasDisponibles;
    }

    public void setCantidadCitasDisponibles(Integer cantidadCitasDisponibles) {
        this.cantidadCitasDisponibles = cantidadCitasDisponibles;
    }

    public List<CitaEntidad> getCitas() {
        return citas;
    }

    public void setCitas(List<CitaEntidad> citas) {
        this.citas = citas;
    }

    public OficinaCitaEntidad getOficinaCita() {
        return oficinaCita;
    }

    public void setOficinaCita(OficinaCitaEntidad oficinaCita) {
        this.oficinaCita = oficinaCita;
    }
    
    

}
