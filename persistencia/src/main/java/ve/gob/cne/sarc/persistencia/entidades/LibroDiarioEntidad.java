package ve.gob.cne.sarc.persistencia.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * LibroDiarioEntidad.java
 *
 * @descripcion Se crea la clase LibroDiarioEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.1 12/12/2016
 *
 */
@Entity
@Table(name = "X050T_LIBRO_DIARIO")

@NamedQueries({
    @NamedQuery(name = LibroDiarioEntidad.BUSCAR_LIBRO_DIARIO_POR_FECHA, query = "SELECT li "
            + "FROM LibroDiarioEntidad li "
            + "WHERE li.fechaCreacion = to_date(:fecha,'DD-MM-YYYY') "
            + "AND li.oficina.codigo= :codigo"),
    @NamedQuery(name = LibroDiarioEntidad.VALIDAR_LIBRO_DIARIO_POR_FECHA, query = "SELECT li "
            + "FROM LibroDiarioEntidad li "
            + "WHERE li.fechaCreacion = to_date(:fecha,'DD-MM-YYYY') "
            + "AND li.oficina.codigo= :codigo "
            + "AND li.libroDiarioEstatus.id= :id")
})

public class LibroDiarioEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_LIBRO_DIARIO_POR_FECHA = "LibroDiarioEntidad.buscarLibroDiarioPorFecha";
    public static final String VALIDAR_LIBRO_DIARIO_POR_FECHA = "LibroDiarioEntidad.validarLibroDiarioOficina";
    @Id
    @Basic(optional = false)
    @Column(name = "CO_LIBRO_DIARIO", nullable = false, length = 22)
    @SequenceGenerator(name = "LIBRO_DIARIO_SEQ", sequenceName = "X050S_CO_LIBRO_DIARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIBRO_DIARIO_SEQ")
    private Long id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private DateTime fechaCreacion;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Basic(optional = true)
    @Column(name = "FE_CIERRE", nullable = true)
    private DateTime fechaCierre;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA", name = "CO_OFICINA", nullable = false)
    private OficinaEntidad oficina;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_FUNCIONARIO", name = "CO_FUNCIONARIO_APERTURA", nullable = false)
    private FuncionarioEntidad funcionarioApertura;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_FUNCIONARIO", name = "CO_FUNCIONARIO_CIERRE", nullable = true)
    private FuncionarioEntidad funcionarioCierre;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_LIBRO_DIARIO_ESTATUS", name = "CO_LIBRO_DIARIO_ESTATUS", nullable = false)
    private LibroDiarioEstatusEntidad libroDiarioEstatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "libroDiario", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DiarioEntidad> diario = new ArrayList<>();

    public LibroDiarioEntidad() {
        // Metodo Constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(DateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public DateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(DateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

    public FuncionarioEntidad getFuncionarioApertura() {
        return funcionarioApertura;
    }

    public void setFuncionarioApertura(FuncionarioEntidad funcionarioApertura) {
        this.funcionarioApertura = funcionarioApertura;
    }

    public FuncionarioEntidad getFuncionarioCierre() {
        return funcionarioCierre;
    }

    public void setFuncionarioCierre(FuncionarioEntidad funcionarioCierre) {
        this.funcionarioCierre = funcionarioCierre;
    }

    public LibroDiarioEstatusEntidad getLibroDiarioEstatus() {
        return libroDiarioEstatus;
    }

    public void setLibroDiarioEstatus(LibroDiarioEstatusEntidad libroDiarioEstatus) {
        this.libroDiarioEstatus = libroDiarioEstatus;
    }

    public List<DiarioEntidad> getDiario() {
        return diario;
    }

    public void setDiario(List<DiarioEntidad> diario) {
        this.diario = diario;
    }

}
