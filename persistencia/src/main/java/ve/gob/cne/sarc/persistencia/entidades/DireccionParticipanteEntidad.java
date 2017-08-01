package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ve.gob.cne.sarc.persistencia.disparadores.DireccionParticipanteDisparador;

/**
 * DireccionParticipanteEntidad.java
 *
 * @descripcion Se crea la clase ActaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "Y002T_DIREC_PARTICIPANTE")
@EntityListeners({DireccionParticipanteDisparador.class})

public class DireccionParticipanteEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_DIREC_PARTICIPACION", nullable = false, length = 22)
    @SequenceGenerator(name = "DIREC_PARTICIPANTE_SEQ", sequenceName = "Y002S_CO_DIREC_PARTICIPANTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIREC_PARTICIPANTE_SEQ")
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_PARROQUIA", name = "CO_PARROQUIA", nullable = true)
    private ParroquiaEntidad parroquia;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PARTICIPANTE", name = "CO_PARTICIPANTE")
    private ParticipanteEntidad participante;

    @Basic(optional = true)
    @Column(name = "NB_APARTAMENTO", length = 50)
    private String nombreApartamento;

    @Basic(optional = true)
    @Column(name = "TX_APTO", length = 50)
    private String textoApartamento;

    @Basic(optional = true)
    @Column(name = "IN_NUM_PISO", length = 1)
    private String numeroCasaPiso;

    @Basic(optional = true)
    @Column(name = "IN_EDIF_CASA", length = 1)
    private String indicadorEdificioCasa;

    @Basic(optional = true)
    @Column(name = "TX_VIVIENDA", length = 50)
    private String textoVivienda;

    @Basic(optional = true)
    @Column(name = "IN_VIA", length = 1)
    private String indicadorVia;

    @Basic(optional = true)
    @Column(name = "TX_VIA", length = 50)
    private String textoVia;

    @Basic(optional = false)
    @Column(name = "DI_UBICACION", nullable = false, length = 200)
    private String direccionUbicacion;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_PAIS", name = "CO_PAIS", nullable = true)
    private PaisEntidad pais;

    public DireccionParticipanteEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ParroquiaEntidad getParroquia() {
        return this.parroquia;
    }

    public void setParroquia(ParroquiaEntidad parroquia) {
        this.parroquia = parroquia;
    }

    public ParticipanteEntidad getParticipante() {
        return this.participante;
    }

    public void setParticipante(ParticipanteEntidad participante) {
        this.participante = participante;
    }

    public String getNombreApartamento() {
        return this.nombreApartamento;
    }

    public void setNombreApartamento(String nombreApartamento) {
        this.nombreApartamento = nombreApartamento;
    }

    public String getTextoApartamento() {
        return this.textoApartamento;
    }

    public void setTextoApartamento(String textoApartamento) {
        this.textoApartamento = textoApartamento;
    }

    public String getNumeroCasaPiso() {
        return this.numeroCasaPiso;
    }

    public void setNumeroCasaPiso(String numeroCasaPiso) {
        this.numeroCasaPiso = numeroCasaPiso;
    }

    public String getIndicadorEdificioCasa() {
        return this.indicadorEdificioCasa;
    }

    public void setIndicadorEdificioCasa(String indicadorEdificioCasa) {
        this.indicadorEdificioCasa = indicadorEdificioCasa;
    }

    public String getTextoVivienda() {
        return this.textoVivienda;
    }

    public void setTextoVivienda(String textoVivienda) {
        this.textoVivienda = textoVivienda;
    }

    public String getIndicadorVia() {
        return this.indicadorVia;
    }

    public void setIndicadorVia(String indicadorVia) {
        this.indicadorVia = indicadorVia;
    }

    public String getTextoVia() {
        return this.textoVia;
    }

    public void setTextoVia(String textoVia) {
        this.textoVia = textoVia;
    }

    public String getDireccionUbicacion() {
        return this.direccionUbicacion;
    }

    public void setDireccionUbicacion(String direccionUbicacion) {
        this.direccionUbicacion = direccionUbicacion;
    }

    public PaisEntidad getPais() {
        return pais;
    }

    public void setPais(PaisEntidad pais) {
        this.pais = pais;
    }

}
