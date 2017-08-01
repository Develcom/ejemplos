package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by posma-christian on 10/03/2016.
 */
@Entity
@Table(name="C087T_RECAUDO_FISICO_NOTA")
public class RecaudoFisicoNotaEntidad implements Serializable {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(name = "RECAUDO_FISICO_NOTA_SEQ", sequenceName = "C087S_RECAUDO_FISICO_NOTA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECAUDO_FISICO_NOTA_SEQ")
    @Column( name = "CO_RECAUDO_FISICO_NOTA")
    private Long id;

    @Column(name = "CO_GESTOR_DOCUMENTAL", nullable = false, length = 255)
    private String codigoGestor;

    @Column(name = "NB_NOMBRE", nullable = false, length = 255)
    private String nombre;

    @Column (name ="FE_RECEPCION", nullable = false)
    private Date fechaRecepcion;

    @Column (name ="NB_RUTA_ARCHIVO", nullable = false, length = 255)
    private String  rutaArchivo;

    @Column (name ="TX_COMENTARIOS", nullable = false, length = 255)
    private String  comentarios;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "CO_NOTA_MARGINAL")
    private NotaMarginalEntidad notaMarginalEntidad;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "CO_RECAUDO_NOTA_MARGINAL")
    private RecaudoNotaMarginalEntidad recaudoNotaMarginal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoGestor() {
        return codigoGestor;
    }

    public void setCodigoGestor(String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public NotaMarginalEntidad getNotaMarginalEntidad() {
        return notaMarginalEntidad;
    }

    public void setNotaMarginalEntidad(NotaMarginalEntidad notaMarginalEntidad) {
        this.notaMarginalEntidad = notaMarginalEntidad;
    }

    public RecaudoNotaMarginalEntidad getRecaudoNotaMarcinal() {
        return recaudoNotaMarginal;
    }

    public void setRecaudoNotaMarcinal(RecaudoNotaMarginalEntidad recaudoNotaMarginal) {
        this.recaudoNotaMarginal = recaudoNotaMarginal;
    }

    @Override
    public String toString() {
        return "RecaudoFisicoNotaEntidad{" +
                "id=" + id +
                ", codigoGestor='" + codigoGestor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", rutaArchivo='" + rutaArchivo + '\'' +
                ", fechaRecepcion='" + fechaRecepcion + '\'' +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
}
