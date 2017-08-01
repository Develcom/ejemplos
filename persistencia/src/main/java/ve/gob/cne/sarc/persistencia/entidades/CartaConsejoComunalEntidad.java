package ve.gob.cne.sarc.persistencia.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.CartaConsejoComunalDisparador;

/**
 * CartaConsejoComunalEntidad.java
 *
 * @descripcion Se crea la clase CartaConsejoComunalEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
@Entity
@Table(name = "X002T_CARTA_CONSEJO_COMUNAL")
@EntityListeners({CartaConsejoComunalDisparador.class})

public class CartaConsejoComunalEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_CARTA_CONSEJO_COMUNAL", nullable = false, length = 22)
    @SequenceGenerator(name = "CARTA_CONSEJO_COMUNAL_SEQ", sequenceName = "X002S_CO_CARTA_CONSEJO_COMUNAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARTA_CONSEJO_COMUNAL_SEQ")
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_CONSEJO_COMUNAL", nullable = false, length = 100)
    private String nombreConsejoComunal;

    @Basic(optional = true)
    @Column(name = "TX_RIF_CONSEJO_COMUNAL", nullable = true, length = 20)
    private String rifConsejoComunal;

    @Basic(optional = true)
    @Column(name = "DI_CONSEJO_COMUNAL", nullable = true, length = 200)
    private String direccion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_CARTA", nullable = false)
    private Date fechaCarta;

    @Basic(optional = true)
    @Column(name = "NU_TELEFONO_LOCAL", nullable = true, length = 20)
    private String numeroTelefonico;

    @OneToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_PARTICIPANTE", name = "CO_PARTICIPANTE", nullable = true)
    private ParticipanteEntidad participante;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cartaConsejoComunal",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<IntegranteCartaConsejoComunalEntidad> integrantesCartaConsejoComunal = new ArrayList<>();

    public CartaConsejoComunalEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreConsejoComunal() {
        return this.nombreConsejoComunal;
    }

    public void setNombreConsejoComunal(String nombreConsejoComunal) {
        this.nombreConsejoComunal = nombreConsejoComunal;
    }

    public String getRifConsejoComunal() {
        return this.rifConsejoComunal;
    }

    public void setRifConsejoComunal(String rifConsejoComunal) {
        this.rifConsejoComunal = rifConsejoComunal;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ParticipanteEntidad getParticipante() {
        return this.participante;
    }

    public void setParticipante(ParticipanteEntidad participante) {
        this.participante = participante;
    }

    public List<IntegranteCartaConsejoComunalEntidad> getIntegrantesCartaConsejoComunal() {
        return integrantesCartaConsejoComunal;
    }

    public void setIntegrantesCartaConsejoComunal(
            List<IntegranteCartaConsejoComunalEntidad> integrantesCartaConsejoComunal) {
        this.integrantesCartaConsejoComunal = integrantesCartaConsejoComunal;
    }

    public Date getFechaCarta() {
        return fechaCarta;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setFechaCarta(Date fechaCarta) {
        this.fechaCarta = fechaCarta;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

}
