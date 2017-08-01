package ve.gob.cne.sarc.persistencia.entidades;

/**
 * Created by posma-christian on 19/04/2016.
 */

import javax.persistence.*;
import java.io.Serializable;

/**
 *  Entidad que persiste los tipos actos
 */
@Entity
@Table(name = "I017T_TIPO_ACTO")
public class TipoActoEntidad implements Serializable {

    @Id
    @Column(name= "CO_TIPO_ACTO")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(name = "TIPO_ACTO_SEQ", sequenceName = "I017S_TIPO_ACTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_ACTO_SEQ")
    private Long id;

    @Column(name = "NB_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "CO_CODIGO", nullable = false)
    private String codigo;

    @Column(name = "TX_DESCRIPCION", nullable = false)
    private String descripcion;

    public TipoActoEntidad() {
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
}
