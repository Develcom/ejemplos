package ve.gob.cne.sarc.persistencia.entidades;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jorge
 * Date: 29/04/15
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */


@Entity
@Table(name="I020T_RECAUDO_NOTA_MARGINAL")
public class RecaudoNotaMarginalEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(name = "RECAUDO_NOTA_SEQ", sequenceName = "I020S_RECAUDO_NOTA_MARGINAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECAUDO_NOTA_SEQ")
    @Column( name = "CO_RECAUDO_NOTA_MARGINAL")
    private Long id;

    @Column(name = "NB_NOMBRE", nullable = false, length = 255)
    private String nombre;

    @Column(name = "TX_DESCRIPCION", nullable = false, length = 255)
    private String descripcion;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name ="CO_TIPO_NOTA_MARGINAL")
    private TipoNotaMarginalEntidad tipoNotaMarginalEntidad;

    public RecaudoNotaMarginalEntidad() {
        //constructor por defecto
    }
    public RecaudoNotaMarginalEntidad(String nombre, String descripcion,
                                      TipoNotaMarginalEntidad tipoNotaMarginalEntidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public TipoNotaMarginalEntidad getTipoNotaMarginalEntidad() {
        return tipoNotaMarginalEntidad;
    }

    public void setTipoNotaMarginalEntidad(TipoNotaMarginalEntidad tipoNotaMarginalEntidad) {
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
    }

    @Override
    public String toString() {
        return "Recaudo{" + "id=" + getId() + ", nombre=" + getNombre() + ", descripcion=" + getDescripcion() +"}";
    }
}
