/**
 *@LugarCelebracionEntidad.java
 * @version 1.0
 * 22/08/2016
 * Copyright
 */
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

import ve.gob.cne.sarc.persistencia.disparadores.LugarCelebracionDisparador;




/**
 * LugarCelebracionEntidad.java
 * @descripcion clase que indica el lugar de celebración del acto
 * de matrimonio
 * @version 
 * 22/08/2016
 * @author geormancalderon@grupodigifarm.com
 */
@Entity
@Table(name = "C081T_LUGAR_CELEBRACION")
@EntityListeners(LugarCelebracionDisparador.class)
@NamedQueries({

    @NamedQuery(name = "LugarCelebracionEntidad.findByCodigo", query = "select l from LugarCelebracionEntidad l where l.codigo = :codigo")
})
public class LugarCelebracionEntidad implements Serializable {
    
    /**
     * LugarCelebracionEntidad.java
     */
    private static final long serialVersionUID = 1L;



    @Id
    @Basic(optional = false)
    @Column(name = "CO_LUGAR_CELEBRACION",unique=true, nullable = false, length = 22)
    @SequenceGenerator(name = "LUGAR_CELEB_SEQ", sequenceName = "C081S_CO_LUGAR_CELEBRACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LUGAR_CELEB_SEQ")
    private Long id;
    
    
    @Basic(optional = false)
    @Column(name = "CO_ABREVIATURA", unique = true, nullable = false, length = 5)
    private String codigo;
    
    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    private String descripcion;
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lugarCelebracion", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SolicitudOREEntidad> solicitudORE;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<SolicitudOREEntidad> getSolicitudORE() {
        return solicitudORE;
    }

    public void setSolicitudORE(List<SolicitudOREEntidad> solicitudORE) {
        this.solicitudORE = solicitudORE;
    }
    

}
