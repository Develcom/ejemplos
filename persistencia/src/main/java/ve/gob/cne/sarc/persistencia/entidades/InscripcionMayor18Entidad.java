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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.InscripcionMayor18Disparador;

/**
 * InscripcionMayor18Entidad.java
 *
 * @descripcion Se crea la clase ActaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 */
@Entity
@Table(name = "X015T_INSCRIPCION_MAYOR_18")
@EntityListeners({InscripcionMayor18Disparador.class})

public class InscripcionMayor18Entidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "InscripcionMayor18Entidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "InscripcionMayor18Entidad.BUSCAR_POR_ID";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_INSCRIPCION_MAYOR_18", nullable = false, length = 22)
    @SequenceGenerator(name = "INSCRIPCION_MAYOR_18_SEQ", sequenceName = "X015S_CO_INSCRIPCION_MAYOR_18",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSCRIPCION_MAYOR_18_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA_FUNCIONARIO",
            name = "CO_OFICINA_FUNCIONARIO", nullable = false)
    private OficinaFuncionarioEntidad oficinaFuncionario;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_SOLICITUD", name = "CO_SOLICITUD", nullable = false)
    private SolicitudEntidad solicitud;

    @Basic(optional = true)
    @Column(name = "TX_EXPOSICION_MOTIVO", length = 200)
    private String textoExposicionMotivo;

    @Basic(optional = true)
    @Column(name = "NU_TELEFONO", nullable = true, length = 22)
    private Integer numeroTelefono;

    @Basic(optional = true)
    @Column(name = "IN_PUBLICACION_NOTIFICACION", length = 1)
    private String indicadorPublicacionNotificacion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_ENVIO")
    private Date fechaEnvio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_RECEPCION")
    private Date fechaRecepcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_NOTIFICACION")
    private Date fechaNotificacion;

    @Basic(optional = true)
    @Column(name = "IN_RECONSIDERACION", nullable = true, length = 1)
    private String indicadorReconsideracion;

    @Basic(optional = true)
    @Column(name = "TX_CORREO_ELECTRONICO", nullable = true, length = 100)
    private String correoElectronico;

    @Basic(optional = true)
    @Column(name = "TX_OBSERVACION", nullable = true, length = 500)
    private String observacion;

    public InscripcionMayor18Entidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OficinaFuncionarioEntidad getOficinaFuncionario() {
        return this.oficinaFuncionario;
    }

    public void setOficinaFuncionario(
            OficinaFuncionarioEntidad oficinaFuncionario) {
        this.oficinaFuncionario = oficinaFuncionario;
    }

    public SolicitudEntidad getSolicitud() {
        return this.solicitud;
    }

    public void setSolicitud(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public String getTextoExposicionMotivo() {
        return this.textoExposicionMotivo;
    }

    public void setTextoExposicionMotivo(String textoExposicionMotivo) {
        this.textoExposicionMotivo = textoExposicionMotivo;
    }

    public Integer getNumeroTelefono() {
        return this.numeroTelefono;
    }

    public void setNumeroTelefono(Integer numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getIndicadorPublicacionNotificacion() {
        return this.indicadorPublicacionNotificacion;
    }

    public void setIndicadorPublicacionNotificacion(
            String indicadorPublicacionNotificacion) {
        this.indicadorPublicacionNotificacion = indicadorPublicacionNotificacion;
    }

    public Date getFechaEnvio() {
        return this.fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaRecepcion() {
        return this.fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Date getFechaNotificacion() {
        return this.fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getIndicadorReconsideracion() {
        return this.indicadorReconsideracion;
    }

    public void setIndicadorReconsideracion(String indicadorReconsideracion) {
        this.indicadorReconsideracion = indicadorReconsideracion;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
