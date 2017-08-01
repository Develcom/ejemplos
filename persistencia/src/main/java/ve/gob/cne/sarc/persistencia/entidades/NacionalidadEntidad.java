package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.MethodEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.disparadores.NacionalidadDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * NacionalidadEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0 11/08/2016
 * @descripcion Se crea la clase NacionalidadEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C008T_NACIONALIDAD")
@EntityListeners({ NacionalidadDisparador.class })
@NamedQueries({
    @NamedQuery(name = NacionalidadEntidad.BUSCAR_POR_NOMBRE, query = "SELECT nac "
            + "FROM   NacionalidadEntidad nac " + "WHERE  UPPER(nac.nombre) = :nombre")
})
@ClassEspecification(name = "Nacionalidad", identifier = "Nombre", canBeListed = true,
        generatesTask = SincronizationPolicy.BROADCAST)
public class NacionalidadEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_NOMBRE = "NacionalidadEntidad.buscarPorNombre";

    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;
    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @FieldEspecification(size = 200, input = FieldEspecification.InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    private Date fechaInicio;

    private Date fechaFin;
    
    private List<FuncionarioEntidad> funcionarios = new ArrayList<>();
    
    private List<ParticipanteEntidad> participantes = new ArrayList<>();
    
    private List<CiudadanoEntidad> ciudadano = new ArrayList<>();

    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public NacionalidadEntidad() {
        // Metodo Constructor
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CO_NACIONALIDAD", nullable = false, length = 22)
    @SequenceGenerator(name = "NACIONALIDAD_SEQ", sequenceName = "C008S_CO_NACIONALIDAD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NACIONALIDAD_SEQ")
    @MethodEspecification(targetedAttribute="id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "NB_NACIONALIDAD", nullable = false, length = 50)
    @MethodEspecification(targetedAttribute="nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    @MethodEspecification(targetedAttribute="fechaInicio")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    @MethodEspecification(targetedAttribute="fechaFin")
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @MethodEspecification(targetedAttribute="descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nacionalidad", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE })
    @MethodEspecification(targetedAttribute="funcionarios")
    public List<FuncionarioEntidad> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<FuncionarioEntidad> funcionarios) {
        this.funcionarios = funcionarios;
    }
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @MethodEspecification(targetedAttribute="codigoSincronizacion")
    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nacionalidad", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<ParticipanteEntidad> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteEntidad> participantes) {
        this.participantes = participantes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nacionalidad", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<CiudadanoEntidad> getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(List<CiudadanoEntidad> ciudadano) {
        this.ciudadano = ciudadano;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NacionalidadEntidad that = (NacionalidadEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
