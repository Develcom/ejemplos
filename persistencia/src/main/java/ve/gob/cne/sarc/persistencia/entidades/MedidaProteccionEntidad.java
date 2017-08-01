package ve.gob.cne.sarc.persistencia.entidades;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.MedidaProteccionDisparador;

/**
 * MedidaProteccionEntidad.java
 *
 * @descripcion Se crea la clase MedidaProteccionEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "X028T_MEDIDA_PROTECCION")
@EntityListeners({MedidaProteccionDisparador.class})

public class MedidaProteccionEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_MEDIDA_PROTECCION", nullable = false, length = 22)
    @SequenceGenerator(name = "MEDIDA_PROTECCION_SEQ", sequenceName = "X028S_CO_MEDIDA_PROTECCION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDIDA_PROTECCION_SEQ")
    private long id;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PROCEDENCIA", name = "CO_PROCEDENCIA", nullable = false)
    private ProcedenciaEntidad procedencia;

    @Basic(optional = false)
    @Column(name = "NB_CONSEJO_PROTECCION", nullable = false, length = 100)
    private String nombreConsejoProteccion;

    @Basic(optional = false)
    @Column(name = "NU_MEDIDA", nullable = false, length = 20)
    private int numeroMedida;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_MEDIDA", nullable = false)
    private Date fechaMedida;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE_CONSEJERO", nullable = false, length = 50)
    private String primerNombreConsejero;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_CONSEJERO", nullable = false, length = 50)
    private String segundoNombreConsejero;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO_CONSEJERO", nullable = false, length = 50)
    private String primerApellidoConsejero;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_CONSEJERO", nullable = false, length = 50)
    private String segundoApellidoConsejero;

    public MedidaProteccionEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProcedenciaEntidad getProcedencia() {
        return this.procedencia;
    }

    public void setProcedencia(ProcedenciaEntidad procedencia) {
        this.procedencia = procedencia;
    }

    public String getNombreConsejoProteccion() {
        return this.nombreConsejoProteccion;
    }

    public void setNombreConsejoProteccion(String nombreConsejoProteccion) {
        this.nombreConsejoProteccion = nombreConsejoProteccion;
    }

    public int getNumeroMedida() {
        return this.numeroMedida;
    }

    public void setNumeroMedida(int numeroMedida) {
        this.numeroMedida = numeroMedida;
    }

    public Date getFechaMedida() {
        return fechaMedida;
    }

    public void setFechaMedida(Date fechaMedida) {
        this.fechaMedida = fechaMedida;
    }

    public String getPrimerNombreConsejero() {
        return primerNombreConsejero;
    }

    public void setPrimerNombreConsejero(String primerNombreConsejero) {
        this.primerNombreConsejero = primerNombreConsejero;
    }

    public String getSegundoNombreConsejero() {
        return segundoNombreConsejero;
    }

    public void setSegundoNombreConsejero(String segundoNombreConsejero) {
        this.segundoNombreConsejero = segundoNombreConsejero;
    }

    public String getPrimerApellidoConsejero() {
        return primerApellidoConsejero;
    }

    public void setPrimerApellidoConsejero(String primerApellidoConsejero) {
        this.primerApellidoConsejero = primerApellidoConsejero;
    }

    public String getSegundoApellidoConsejero() {
        return segundoApellidoConsejero;
    }

    public void setSegundoApellidoConsejero(String segundoApellidoConsejero) {
        this.segundoApellidoConsejero = segundoApellidoConsejero;
    }
}
