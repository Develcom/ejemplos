package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.disparadores.FuncionarioDisparador;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * FuncionarioEntidad.java
 *
 * @descripcion Se crea la clase FuncionarioEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 06/09/2016
 * @author Oscar Montilla
 * @version 1.0 11/08/2016
 * @descripcion Se crea la clase FuncionarioEntidad donde se Realizan los Query de consulta de cada metodo
 */
@Entity
@Table(name = "X008T_FUNCIONARIO")
@EntityListeners({FuncionarioDisparador.class})
@NamedQueries({
        @NamedQuery(name = FuncionarioEntidad.BUSCAR_TODOS, query = "SELECT fun "
                + "FROM   FuncionarioEntidad fun"),
        @NamedQuery(name = FuncionarioEntidad.BUSCAR_POR_ID, query = "SELECT fun "
                + "FROM   FuncionarioEntidad fun " + "WHERE  fun.id = :id"),
        @NamedQuery(name = FuncionarioEntidad.BUSCAR_POR_CEDULA, query = "SELECT fun "
                + "FROM   FuncionarioEntidad fun "
                + "WHERE  fun.cedula = :cedula"),
        @NamedQuery(name = FuncionarioEntidad.BUSCAR_POR_PRIMER_NOMBRE, query = "SELECT fun "
                + "FROM   FuncionarioEntidad fun "
                + "WHERE  fun.primerNombre = :primerNombre"),
        @NamedQuery(name = FuncionarioEntidad.BUSCAR_POR_PRIMER_NOMBRE_PATRON, query = "SELECT fun "
                + "FROM   FuncionarioEntidad fun "
                + "WHERE  fun.primerNombre LIKE '%:primerNombre%'"),
        @NamedQuery(name = FuncionarioEntidad.BUSCAR_POR_PRIMER_APELLIDO, query = "SELECT fun "
                + "FROM   FuncionarioEntidad fun "
                + "WHERE  fun.primerApellido = :primerApellido"),
        @NamedQuery(name = FuncionarioEntidad.BUSCAR_POR_PRIMER_APELLIDO_PATRON, query = "SELECT fun "
                + "FROM   FuncionarioEntidad fun "
                + "WHERE  fun.primerApellido LIKE '%:primerApellido%'"),
        @NamedQuery(name = FuncionarioEntidad.BUSCAR_POR_CODIGO_CEDULA, query = "SELECT fun FROM   "
                + "FuncionarioEntidad fun WHERE  fun.cedula = :cedula")
})
@ClassEspecification(name = "Funcionario", identifier = "primerNombre,segundoNombre,primerApellido,segundoApellido",
        canBeListed = false,
        generatesTask = SincronizationPolicy.NONE)
public class FuncionarioEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "FuncionarioEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "FuncionarioEntidad.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_CEDULA = "FuncionarioEntidad.buscarPorCedula";
    public static final String BUSCAR_POR_PRIMER_NOMBRE = "FuncionarioEntidad.BUSCAR_POR_PRIMER_NOMBRE";
    public static final String BUSCAR_POR_PRIMER_NOMBRE_PATRON = "FuncionarioEntidad.BUSCAR_POR_PRIMER_NOMBRE_PATRON";
    public static final String BUSCAR_POR_PRIMER_APELLIDO = "FuncionarioEntidad.BUSCAR_POR_PRIMER_APELLIDO";
    public static final String BUSCAR_POR_PRIMER_APELLIDO_PATRON
            = "FuncionarioEntidad.BUSCAR_POR_PRIMER_APELLIDO_PATRON";
    public static final String BUSCAR_POR_CODIGO_CEDULA = "FuncionarioEntidad.BUSCAR_POR_CODIGO_CEDULA";
    public static final String BUSCAR_POR_CODIGO_T_FUNCIONARIO = "FuncionarioEntidad.BUSCAR_POR_CODIGO_T_FUNCIONARIO";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_FUNCIONARIO", nullable = false, length = 22)
    @SequenceGenerator(name = "FUNCIONARIO_SEQ", sequenceName = "X008S_CO_FUNCIONARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUNCIONARIO_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_NACIONALIDAD", referencedColumnName = "CO_NACIONALIDAD", nullable = false)
    private NacionalidadEntidad nacionalidad;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO", nullable = false, length = 50)
    private String primerApellido;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO", nullable = true, length = 50)
    private String segundoApellido;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE", nullable = false, length = 50)
    private String primerNombre;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE", nullable = true, length = 50)
    private String segundoNombre;

    @Basic(optional = false)
    @Column(name = "IN_TIPO_DOCUMENTO", nullable = false, length = 1)
    private String indicadorTipoDocumento;

    @Basic(optional = false)
    @Column(name = "NU_CEDULA", unique = true, nullable = false, length = 15)
    private String cedula;

    @Basic(optional = false)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "funcionario", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaFuncionarioEntidad> oficinasFuncionarios = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionarioApertura", cascade = {CascadeType.PERSIST,})
    private List<LibroEntidad> libros = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionarioCierre", cascade = {CascadeType.PERSIST,})
    private List<LibroEntidad> libros1 = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionarioApertura", cascade = {CascadeType.PERSIST,})
    private List<LibroEntidad> libroDiario = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionarioCierre", cascade = {CascadeType.PERSIST,})
    private List<LibroEntidad> libroDiario1 = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_FUNCIONARIO_ESTATUS", referencedColumnName = "CO_FUNCIONARIO_ESTATUS")
    private FuncionarioEstatusEntidad funcionarioEstatus;

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    private String codigoSincronizacion;

    public FuncionarioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NacionalidadEntidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(NacionalidadEntidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getIndicadorTipoDocumento() {
        return indicadorTipoDocumento;
    }

    public void setIndicadorTipoDocumento(String indicadorTipoDocumento) {
        this.indicadorTipoDocumento = indicadorTipoDocumento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<OficinaFuncionarioEntidad> getOficinasFuncionarios() {
        return oficinasFuncionarios;
    }

    public void setOficinasFuncionarios(
            List<OficinaFuncionarioEntidad> oficinasFuncionarios) {
        this.oficinasFuncionarios = oficinasFuncionarios;
    }

    public List<LibroEntidad> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroEntidad> libros) {
        this.libros = libros;
    }

    public List<LibroEntidad> getLibros1() {
        return libros1;
    }

    public void setLibros1(List<LibroEntidad> libros1) {
        this.libros1 = libros1;
    }

    public FuncionarioEstatusEntidad getFuncionarioEstatus() {
        return funcionarioEstatus;
    }

    public void setFuncionarioEstatus(FuncionarioEstatusEntidad funcionarioEstatus) {
        this.funcionarioEstatus = funcionarioEstatus;
    }

    public List<LibroEntidad> getLibroDiario() {
        return libroDiario;
    }

    public void setLibroDiario(List<LibroEntidad> libroDiario) {
        this.libroDiario = libroDiario;
    }

    public List<LibroEntidad> getLibroDiario1() {
        return libroDiario1;
    }

    public void setLibroDiario1(List<LibroEntidad> libroDiario1) {
        this.libroDiario1 = libroDiario1;
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
        FuncionarioEntidad that = (FuncionarioEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
