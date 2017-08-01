/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entidad que corresponde a TareaBO .
 * @author Posmagroup
 */
@Entity
@Table(name="I002T_TAREA")
public class TareaEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name="CO_TAREA")
    @SequenceGenerator(name = "TAREA_SEQ", sequenceName = "I002S_CO_TAREA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAREA_SEQ")
    private Long id;

    @Column(name = "NB_TAREA", nullable = false)
    private String nombre;
    
    @Column(name = "TX_DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "NU_TAREA_INICIAL_FLAG", nullable = false)
    private long tareaInicial;

    @ManyToOne(targetEntity=TramitePaqueteEntidad.class)
    @JoinColumn(name = "CO_TRAMITE_PAQUETE", nullable = false)
    private TramitePaqueteEntidad tramitePaqueteEntidad;
    


    public TareaEntidad() {
        //constructor por defecto
    }

    public TareaEntidad(String nombre, String descripcion, TramitePaqueteEntidad tramitePaqueteEntidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tramitePaqueteEntidad = tramitePaqueteEntidad;

    }


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

    public long getTareaInicial() {
        return tareaInicial;
    }

    public void setTareaInicial(long tareaInicial) {
        this.tareaInicial = tareaInicial;
    }

    public TramitePaqueteEntidad getTramitePaqueteEntidad() {
        return tramitePaqueteEntidad;
    }

    public void setTramitePaqueteEntidad(TramitePaqueteEntidad tramitePaqueteEntidad) {
        this.tramitePaqueteEntidad = tramitePaqueteEntidad;
    }

    @Override
    public String toString() {
        return "TareaBO{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", tramitePaqueteEntidad=" + tramitePaqueteEntidad + '}';
    }
    
    
}
