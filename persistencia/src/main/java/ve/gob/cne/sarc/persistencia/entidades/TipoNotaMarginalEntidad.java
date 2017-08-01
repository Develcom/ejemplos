package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: jorge Date: 29/04/15 Time: 11:12 AM To
 * change this template use File | Settings | File Templates.
 */

/**
 *  Entidad que persiste los tipos de notas marginales
 */
@Entity
@Table(name = "I016T_TIPO_NOTA_MARGINAL")
public class TipoNotaMarginalEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="CO_TIPO_NOTA_MARGINAL")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(name = "TIPO_NOTA_SEQ", sequenceName = "I016S_TIPO_NOTA_MARGINAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_NOTA_SEQ")
    private Long id;

    @Column(name = "NB_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "CO_CODIGO", nullable = false)
    private String codigo;

    @Column(name = "TX_DESCRIPCION", nullable = false)
    private String descripcion;

    /**
     * Relaciones con otras tablas
     */

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name ="CO_TIPO_ACTO")
    private TipoActoEntidad tipoActoEntidad;

    public TipoNotaMarginalEntidad() {
        //metodo vacio
    }

   public TipoNotaMarginalEntidad(String nombre, String codigo,
                                  String descripcion) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }



    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoActoEntidad getTipoActoEntidad() {
        return tipoActoEntidad;
    }

    public void setTipoActoEntidad(TipoActoEntidad tipoActoEntidad) {
        this.tipoActoEntidad = tipoActoEntidad;
    }


    @Override
    public String toString() {
        return "TipoNotaMarginalEntidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
