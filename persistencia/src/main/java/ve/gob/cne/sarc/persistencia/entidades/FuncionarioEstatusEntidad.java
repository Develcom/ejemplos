package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;
import ve.gob.cne.sarc.persistencia.disparadores.FuncionarioEstatusDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * FuncionarioEstatusEntidad.java
 *
 * @descripcion Se crea la clase ActaEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 06/09/2016
 * @author Oscar Montilla
 * @version 1.0 11/08/2016
 * @descripcion Se crea la clase ActaEntidad donde se Realizan los Query de consulta de cada metodo
 */
@Entity
@Table(name = "C047T_FUNCIONARIO_ESTATUS")
@EntityListeners({FuncionarioEstatusDisparador.class})
@ClassEspecification(name = "Estatus de Funcionario", identifier = "NombreFuncEstatus",
        canBeListed = true, generatesTask = SincronizationPolicy.BROADCAST)
public class FuncionarioEstatusEntidad implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Basic(optional = false)
    @Column(name = "CO_FUNCIONARIO_ESTATUS", nullable = false, length = 22)
    @SequenceGenerator(name = "FUNCIONARIO_ESTATUS_SEQ", sequenceName = "C047S_CO_FUNCIONARIO_ESTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUNCIONARIO_ESTATUS_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = false)
    @Column(name = "NB_FUNCIONARIO_ESTATUS", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombreFuncEstatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionarioEstatus", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE})
    private List<FuncionarioEntidad> funcionarios;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public FuncionarioEstatusEntidad() {
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

    public String getNombreFuncEstatus() {
        return nombreFuncEstatus;
    }

    public void setNombreFuncEstatus(String nombreFuncEstatus) {
        this.nombreFuncEstatus = nombreFuncEstatus;
    }

    public List<FuncionarioEntidad> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<FuncionarioEntidad> funcionarios) {
        this.funcionarios = funcionarios;
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
        FuncionarioEstatusEntidad that = (FuncionarioEstatusEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
