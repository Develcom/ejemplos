/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entidad que corresponde a TramiteBO.
 * @author Posmagroup
 */
@Entity
@Table(name="I003T_TRAMITE_PAQUETE")

public class TramitePaqueteEntidad implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "CO_TRAMITE_PAQUETE", nullable = false, length = 19)
    @SequenceGenerator(name = "TRAMITE_PAQUETE_SEQ", sequenceName = "I003S_CO_TRAMITE_PAQUETE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAMITE_PAQUETE_SEQ")
    private Long id;

    @Column(name = "NB_TRAMITE", nullable = false)
    private String nombre;

    @Column(name = "TX_DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "NU_TRAMITE_INICIAL_FLAG", nullable = false)
    private long tramiteInicial;

    @ManyToOne(targetEntity=UrlModuloWebEntidad.class)
    @JoinColumn(name = "CO_URL_MODULO_WEB", nullable = true)
    private UrlModuloWebEntidad urlModuloWebEntidad;

    @ManyToOne(targetEntity=ModuloEntidad.class)
    @JoinColumn(referencedColumnName = "IDEN_MODULO", name = "IDEN_MODULO", nullable = true)
    private ModuloEntidad moduloEntidad;

    public TramitePaqueteEntidad() {
        //constructor por defecto
    }

    /**
     * Constructor con parámetros
     * @param nombre
     * @param descripcion
     */
    public TramitePaqueteEntidad(String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public long getTramiteInicial() {
        return tramiteInicial;
    }

    public void setTramiteInicial(long tramiteInicial) {
        this.tramiteInicial = tramiteInicial;
    }

    public UrlModuloWebEntidad getUrlModuloWebEntidad() {
        return urlModuloWebEntidad;
    }

    public void setUrlModuloWebEntidad(UrlModuloWebEntidad urlModuloWebEntidad) {
        this.urlModuloWebEntidad = urlModuloWebEntidad;
    }

    public ModuloEntidad getModuloEntidad() {
        return moduloEntidad;
    }

    public void setModuloEntidad(ModuloEntidad moduloEntidad) {
        this.moduloEntidad = moduloEntidad;
    }

    @Override
    public String toString() {
        return "TramiteBO{" + "id=" + id + ", nombre=" + nombre
                + ", descripcion=" + descripcion + ", codigo_urlModuloWeb="
                + urlModuloWebEntidad.getId() + '}';
    }
    
    
    
}