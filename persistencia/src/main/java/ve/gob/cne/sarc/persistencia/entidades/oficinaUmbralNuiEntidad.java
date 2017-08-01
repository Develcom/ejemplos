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
 * oficinaUmbralNuiEntidad.java
 *
 * @descripcion Se crea la clase oficinaUmbralNuiEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 19/12/2016
 */
@Entity
@Table(name = "C039T_OFICINA_UMBRAL_NUI")

public class oficinaUmbralNuiEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_OFICINA_UMBRAL_NUI", nullable = false, length = 22)
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_OFICINA", referencedColumnName = "CO_OFICINA", nullable = true)
    private OficinaEntidad oficina;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 500)
    private String descripcion;

    @Basic(optional = false)
    @Column(name = "CA_NUI_OFICINA", length = 50, nullable = false)
    private String cantidadNUI;

    public oficinaUmbralNuiEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

    public String getCantidadNUI() {
        return cantidadNUI;
    }

    public void setCantidadNUI(String cantidadNUI) {
        this.cantidadNUI = cantidadNUI;
    }

}
