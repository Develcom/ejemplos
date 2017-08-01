
package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Posma-edwyn
 * Date:  07/05/2015
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="I018T_PLANTILLA_NOTA_MARGINAL")
public class PlantillaNotaMarginalEntidad implements Serializable{

    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name= "CO_PLANTILLA_NOTA_MARGINAL")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(name = "PLANTILLA_NOTA_SEQ", sequenceName = "I018S_PLANTILLA_NOTA_MARGINAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLANTILLA_NOTA_SEQ")
    private Long id;
    
    @Column(name = "TX_CONTENIDO", nullable = false)
    private String contenido;
    
    @Column(name = "FE_CREACION", nullable = true)
    private Date fechaCreacion;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "CO_TIPO_NOTA_MARGINAL")
    private TipoNotaMarginalEntidad tipoNotaMarginalEntidad;


    public PlantillaNotaMarginalEntidad() {
        //constructor por defecto
    }

    public PlantillaNotaMarginalEntidad(String contenido, Date fechaCreacion,
                                        TipoNotaMarginalEntidad tipoNotaMarginalEntidad){
        this.contenido =contenido;
        this.fechaCreacion = fechaCreacion;
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoNotaMarginalEntidad getTipoNotaMarginalEntidad() {
        return tipoNotaMarginalEntidad;
    }

    public void setTipoNotaMarginalEntidad(TipoNotaMarginalEntidad tipoNotaMarginalEntidad) {
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
    }

    @Override
    public String toString() {
        return "PlantillaNotaMarginalEntidad{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", tipoNotaMarginalEntidad=" + tipoNotaMarginalEntidad +
                '}';
    }
}


