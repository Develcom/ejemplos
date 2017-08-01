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

import ve.gob.cne.sarc.persistencia.disparadores.IntegranteCartaConsejoComunalDisparador;

/**
 * IntegranteCartaConsejoComunalEntidad.java
 *
 * @descripcion Se crea la clase IntegranteCartaConsejoComunalEntidad donde se Realizan los Query de consulta de cada
 * metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "X003T_INTEGRANTE_CARTA_CC")
@EntityListeners({IntegranteCartaConsejoComunalDisparador.class})

public class IntegranteCartaConsejoComunalEntidad implements
        java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_INTEGRANTE_CARTA_CC", nullable = false, length = 22)
    @SequenceGenerator(name = "INTEGRANTE_CARTA_CC_SEQ", sequenceName = "X003S_CO_INTEGRANTE_CARTA_CC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INTEGRANTE_CARTA_CC_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_CARTA_CONSEJO_COMUNAL",
            name = "CO_CARTA_CONSEJO_COMUNAL", nullable = false)
    private CartaConsejoComunalEntidad cartaConsejoComunal;

    @Basic(optional = false)
    @Column(name = "NB_SEGUNDO_NOMBRE", nullable = true, length = 50)
    private String segundoNombre;

    @Basic(optional = false)
    @Column(name = "NB_SEGUNDO_APELLIDO", nullable = true, length = 50)
    private String segundoApellido;

    @Basic(optional = false)
    @Column(name = "NB_CARGO", nullable = false, length = 100)
    private String cargo;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE", nullable = false, length = 100)
    private String nombreIntegrante;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO", nullable = false, length = 100)
    private String apellidoIntegrante;

    @Basic(optional = false)
    @Column(name = "TIPO_DOCUMENTO", nullable = false, length = 1)
    private String tipoDocumento;

    @Basic(optional = false)
    @Column(name = "NU_DOCUMENTO", nullable = false, length = 15)
    private String numeroDocumento;

    @Basic(optional = false)
    @Column(name = "IN_INTEGRANTE", nullable = false, length = 1)
    private String indicadorIntegrante;

    public IntegranteCartaConsejoComunalEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CartaConsejoComunalEntidad getCartaConsejoComunal() {
        return cartaConsejoComunal;
    }

    public void setCartaConsejoComunal(
            CartaConsejoComunalEntidad cartaConsejoComunal) {
        this.cartaConsejoComunal = cartaConsejoComunal;
    }

    public String getNombreIntegrante() {
        return nombreIntegrante;
    }

    public void setNombreIntegrante(String nombreIntegrante) {
        this.nombreIntegrante = nombreIntegrante;
    }

    public String getApellidoIntegrante() {
        return apellidoIntegrante;
    }

    public void setApellidoIntegrante(String apellidoIntegrante) {
        this.apellidoIntegrante = apellidoIntegrante;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getIndicadorIntegrante() {
        return indicadorIntegrante;
    }

    public void setIndicadorIntegrante(String indicadorIntegrante) {
        this.indicadorIntegrante = indicadorIntegrante;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
