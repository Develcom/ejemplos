package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;
import ve.gob.cne.sarc.persistencia.disparadores.OficinaFuncionarioEstatusDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * OficinaFuncionarioEstatusEntidad.java
 *
 * @descripcion Se crea la clase OficinaFuncionarioEstatusEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C050T_OFICINA_FUNC_ESTATUS")
@EntityListeners({OficinaFuncionarioEstatusDisparador.class})
@ClassEspecification(name = "Estatus de Asignación de Funcionario", identifier = "NomOficFuncEstatus",
        canBeListed = true, generatesTask = SincronizationPolicy.BROADCAST)
public class OficinaFuncionarioEstatusEntidad implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Basic(optional = false)
    @Column(name = "CO_OFICINA_FUNC_ESTATUS", nullable = false, length = 22)
    @SequenceGenerator(name = "OFICINA_FUNC_ESTATUS_SEQ", sequenceName = "C050S_CO_OFICINA_FUNC_ESTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFICINA_FUNC_ESTATUS_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200
    )
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false, length = 10
    )
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true, length = 10
    )
    private Date fechaFin;

    @Basic(optional = false)
    @Column(name = "NB_OFICINA_FUNC_ESTATUS", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nomOficFuncEstatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficFuncEstatus", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaFuncionarioEntidad> oficFuncEntidad;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public OficinaFuncionarioEstatusEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getNomOficFuncEstatus() {
        return nomOficFuncEstatus;
    }

    public void setNomOficFuncEstatus(String nomOficFuncEstatus) {
        this.nomOficFuncEstatus = nomOficFuncEstatus;
    }

    public List<OficinaFuncionarioEntidad> getOficFuncEntidad() {
        return oficFuncEntidad;
    }

    public void setOficFuncEntidad(List<OficinaFuncionarioEntidad> oficFuncEntidad) {
        this.oficFuncEntidad = oficFuncEntidad;
    }

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OficinaFuncionarioEstatusEntidad that = (OficinaFuncionarioEstatusEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
