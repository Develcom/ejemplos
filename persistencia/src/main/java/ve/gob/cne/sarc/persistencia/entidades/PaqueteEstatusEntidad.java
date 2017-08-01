/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entidad que corresponde a PaqueteEstatusEntidad.
 * @author Posmagroup
 */
@Entity
@Table(name="I001T_PAQUETE_ESTATUS")
public class PaqueteEstatusEntidad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name="CO_PAQUETE_ESTATUS")
    @SequenceGenerator(name = "PAQUETE_ESTATUS_SEQ", sequenceName = "I001S_CO_PAQUETE_ESTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAQUETE_ESTATUS_SEQ")
    private Long id;

    @Column(name = "CO_ESTATUS", unique = true, nullable=false)
    private String codigo;

    @Column(name = "NB_ESTATUS", nullable=false)
    private String nombre;
    
    @Column(name = "TX_DESCRIPCION", nullable=false)
    private String descripcion;

    public PaqueteEstatusEntidad() {
        //constructor por defecto
    }

    /**
     * Contructor
     * @param codigo
     * @param nombre
     * @param descripcion
     */
    public PaqueteEstatusEntidad(String codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "PaqueteEstatusEntidad{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
