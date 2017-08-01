package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;

/**
 * TomoEntidad.java
 *
 * @descripcion Se crea la clase TomoEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 12/12/2016
 */
@Entity
@Table(name = "X043T_TOMO")
@ClassEspecification(name = "Tomo", identifier = "NumeroTomo", canBeListed = false,
generatesTask=SincronizationPolicy.NONE)
public class TomoEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TOMO", nullable = false, length = 22)
    @SequenceGenerator(name = "TOMO_SEQ", sequenceName = "X043S_CO_TOMO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOMO_SEQ")
    private long id;

    @Basic(optional = false)
    @Column(name = "NU_TOMO", nullable = false, length = 2)
    private Integer numeroTomo;

    @Basic(optional = false)
    @Column(name = "TOMO_FISICO", nullable = false)
    private boolean esFisico;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_TOMO_ESTATUS", referencedColumnName = "CO_TOMO_ESTATUS", nullable = true)
    private TomoEstatusEntidad tomoEstatus;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_LIBRO", referencedColumnName = "CO_LIBRO", nullable = true)
    private LibroEntidad libro;

    public TomoEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEsFisico() {
        return esFisico;
    }

    public void setEsFisico(boolean esFisico) {
        this.esFisico = esFisico;
    }

    public TomoEstatusEntidad getTomoEstatus() {
        return tomoEstatus;
    }

    public void setTomoEstatus(TomoEstatusEntidad tomoEstatus) {
        this.tomoEstatus = tomoEstatus;
    }

    public Integer getNumeroTomo() {
        return numeroTomo;
    }

    public void setNumeroTomo(Integer numeroTomo) {
        this.numeroTomo = numeroTomo;
    }

    public LibroEntidad getLibro() {
        return libro;
    }

    public void setLibro(LibroEntidad libro) {
        this.libro = libro;
    }

}
