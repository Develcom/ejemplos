package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ve.gob.cne.sarc.persistencia.disparadores.OficinaCitaDisparador;

/**
 * OficinaCitaEntidad.java
 * @descripcion Entidad que contendrá las configuraciones a realizar a las Citas
 * @version 
 * 13 de oct. de 2016
 * @author Domingo Rondon
 */
@Entity
@Table(name = "I033T_OFICINA_CITA")
@EntityListeners({OficinaCitaDisparador.class})
@NamedQueries({
    @NamedQuery(name = OficinaCitaEntidad.BUSCAR_POR_OFICINA, query = "SELECT oficita "
            + "FROM OficinaCitaEntidad oficita " + "WHERE  oficita.oficina = :oficina")
})
public class OficinaCitaEntidad implements Serializable{
    
    private static final long serialVersionUID = -2333049591247399654L;
    
    public static final String BUSCAR_POR_OFICINA = "OficinaCitaEntidad.buscarPorCodigoOficina";
    
    @Id
    @Basic(optional = false)
    @Column(name = "CO_OFICINA_CITA", nullable = false, length = 22)
    @SequenceGenerator(name = "OFICINA_CITA_SEQ", sequenceName = "I033S_CO_OFICINA_CITA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFICINA_CITA_SEQ")
    private long id;
    
    @Basic(optional = false)
    @Column(name = "CO_OFICINA", nullable = false, length = 10)
    private String oficina;
    
    @Basic(optional = false)
    @Column(name = "CA_FUNCIONARIO", nullable = false, length = 22)
    private Integer cantidadFuncionario;
    
    @Basic(optional = false)
    @Column(name = "CA_CITAS_PERMITIDAS", nullable = false, length = 22)
    private Integer cantidadCitasPermitidas;
    
    @Basic(optional = false)
    @Column(name = "TX_DIAS_HABILES_CITAS", nullable = false, length = 15)
    private String diasHabilesDisponibles;
    
    @Basic(optional = false)
    @Column(name = "TX_PERIODICIDAD", nullable = false, length = 15)
    private String periodicidad;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficinaCita", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaCitaDetalleEntidad> oficinasCitasDetalles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public Integer getCantidadFuncionario() {
        return cantidadFuncionario;
    }

    public void setCantidadFuncionario(Integer cantidadFuncionario) {
        this.cantidadFuncionario = cantidadFuncionario;
    }

    public Integer getCantidadCitasPermitidas() {
        return cantidadCitasPermitidas;
    }

    public void setCantidadCitasPermitidas(Integer cantidadCitasPermitidas) {
        this.cantidadCitasPermitidas = cantidadCitasPermitidas;
    }

    public String getDiasHabilesDisponibles() {
        return diasHabilesDisponibles;
    }

    public void setDiasHabilesDisponibles(String diasHabilesDisponibles) {
        this.diasHabilesDisponibles = diasHabilesDisponibles;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public List<OficinaCitaDetalleEntidad> getOficinasCitasDetalles() {
        return oficinasCitasDetalles;
    }

    public void setOficinasCitasDetalles(List<OficinaCitaDetalleEntidad> oficinasCitasDetalles) {
        this.oficinasCitasDetalles = oficinasCitasDetalles;
    }
    
    

}
