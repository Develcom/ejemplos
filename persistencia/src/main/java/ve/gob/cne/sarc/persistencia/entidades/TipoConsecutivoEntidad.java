package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TipoConsecutivoEntidad.java
 *
 * @descripcion Clase Repositorio de la entidad TipoConsecutivoEntidad
 * @version 1.0 01/08/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "I030T_TIPO_CONSECUTIVO")

public class TipoConsecutivoEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TIPO_CONSECUTIVO", nullable = false, length = 22)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_CONSECUTIVO", nullable = false, length = 50)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 50)
    private String descripcion;

    public long getId() {
        return id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
