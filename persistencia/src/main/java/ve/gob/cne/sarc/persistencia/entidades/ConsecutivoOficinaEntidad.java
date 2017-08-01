package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

/**
 * ConsecutivoOficinaEntidad.java
 *
 * @descripcion Clase Repositorio de la entidad ConsecutivoOficinaEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 */
@Entity
@Table(name = "C069T_CONSECUTIVO_OFICINA")
//
//@NamedStoredProcedureQuery(name = ConsecutivoOficinaEntidad.CONSECUTIVO_OFICINA,
//        procedureName = "PROX_CONSECUTIVO_OFICINA",
//        parameters = {
//            @StoredProcedureParameter(mode = ParameterMode.IN, name = "oficina", type = Long.class),
//            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "proximoNumero", type = Long.class)})

public class ConsecutivoOficinaEntidad implements Serializable {

    public static final String CONSECUTIVO_OFICINA = "ConsecutivoOficinaEntidad.obtenerConsecutivoPorOficina";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_CONSECUTIVOS_OFICINA", nullable = false, length = 22)
    private long id;

    @Basic(optional = false)
    @Column(name = "NUMERO_INICIO", nullable = false, length = 50)
    private long numeroInicio;

    @Basic(optional = false)
    @Column(name = "NUMERO_SIGUIENTE", nullable = false, length = 15)
    private long numeroSiguiente;

    @Basic(optional = false)
    @Column(name = "NUMERO_FINAL", nullable = false, length = 15)
    private long numeroFinal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_OFICINA", referencedColumnName = "CO_OFICINA")
    private OficinaEntidad oficina;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TIPO_CONSECUTIVO", referencedColumnName = "CO_TIPO_CONSECUTIVO")
    private TipoConsecutivoEntidad TipoConsecutivo;

    public ConsecutivoOficinaEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumeroInicio() {
        return numeroInicio;
    }

    public void setNumeroInicio(long numeroInicio) {
        this.numeroInicio = numeroInicio;
    }

    public long getNumeroSiguiente() {
        return numeroSiguiente;
    }

    public void setNumeroSiguiente(long numeroSiguiente) {
        this.numeroSiguiente = numeroSiguiente;
    }

    public long getNumeroFinal() {
        return numeroFinal;
    }

    public void setNumeroFinal(long numeroFinal) {
        this.numeroFinal = numeroFinal;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

    public TipoConsecutivoEntidad getTipoConsecutivo() {
        return TipoConsecutivo;
    }

    public void setTipoConsecutivo(TipoConsecutivoEntidad TipoConsecutivo) {
        this.TipoConsecutivo = TipoConsecutivo;
    }

}
