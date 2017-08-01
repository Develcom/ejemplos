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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import ve.gob.cne.sarc.persistencia.disparadores.EstatusCitaDisparador;

/**
 * EstatusCitaEntidad.java
 * @descripcion Entidad que Contiene los Estatus por los que Pasará la Cita
 * @version 
 * 11 de oct. de 2016
 * @author Domingo Rondon
 */
@Entity
@Table(name = "I032T_ESTATUS_CITA")
@EntityListeners({EstatusCitaDisparador.class})

@NamedQueries({
    @NamedQuery(name = EstatusCitaEntidad.BUSCAR_POR_CODIGO, query = "SELECT estatus "
            + "FROM EstatusCitaEntidad estatus " + "WHERE  estatus.codigo = :codigo")
    
                    
})
public class EstatusCitaEntidad implements Serializable{

    private static final long serialVersionUID = -2333049591247399654L;
    
    public static final String BUSCAR_POR_CODIGO = "EstatusCitaEntidad.findByCode";
    
    @Id
    @Basic(optional = false)
    @Column(name = "CO_ESTATUS_CITA", nullable = false, length = 22)
    @SequenceGenerator(name = "ESTATUS_CITA_SEQ", sequenceName = "I032S_CO_ESTATUS_CITA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTATUS_CITA_SEQ")
    private long id;
    
    @Basic(optional = false)
    @Column(name = "CO_ABREVIATURA", nullable = false, length = 5)
    private String codigo;
    
    @Basic(optional = false)
    @Column(name = "TX_DESCRIPCION", nullable = false, length = 50)
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "IN_ESTATUS", nullable = false)
    private Boolean estatus;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estatusCita", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CitaEntidad> cita;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public List<CitaEntidad> getCita() {
        return cita;
    }

    public void setCita(List<CitaEntidad> cita) {
        this.cita = cita;
    }
    
    
    
    
}
