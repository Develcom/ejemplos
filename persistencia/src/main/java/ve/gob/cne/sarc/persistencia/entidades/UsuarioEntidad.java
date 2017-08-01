package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.disparadores.UsuarioDisparador;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * UsuarioEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0 08 de jun. de 2016
 * @descripcion Se crea la clase UsuarioEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X037T_USUARIO")
@EntityListeners({UsuarioDisparador.class})
@NamedQueries({
    @NamedQuery(name = UsuarioEntidad.BUSCAR_POR_NOMBRE, query = "SELECT usu "
            + "FROM   UsuarioEntidad usu " + "WHERE  usu.nombre = :nombre")

})

public class UsuarioEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_NOMBRE = "UsuarioEntidad.buscarPorNombre";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_USUARIO", nullable = false, length = 22)
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "X037S_CO_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_USUARIO", nullable = false, length = 20)
    @FieldEspecification(required = true, size = 20, label = "Nombre", order = 1, step = 0, placeHolder = "Pedro Pérez")
    private String nombre;

    @Basic(optional = false)
    @Column(name = "TX_ClAVE", nullable = false, length = 100)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.PASSWORDINPUT, size = 100,
            label = "Clave", order = 2, step = 0, placeHolder = "")
    private String claveUsuario;

    @Basic(optional = false)
    @Column(name = "TX_CORREO", nullable = false, length = 100)
    @FieldEspecification(required = true, size = 100, label = "Correo Electr&oacute;nico", order = 3, step = 0,
            input = FieldEspecification.InputType.COMMONINPUT, constraints = "/[a-zA-Z0-9]+@[a-zA-Z0-9]+([.][a-zA-Z0-9]+)?/",
            placeHolder = "pedroperez@dominio")
    private String correoElectronico;

    @Basic(optional = false)
    @Column(name = "IN_ESTATUS", nullable = false, length = 3)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.MANUAL_OPTIONS,
            manualOptions = "ACT:Activo,INA:Inactivo",
            label = "Estatus",
            order = 4,
            step = 0)
    private String estatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_OFICINA_FUNCIONARIO", referencedColumnName = "CO_OFICINA_FUNCIONARIO",
            nullable = false)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS,
            label = "Oficina - Funcionario",
            type = "java.lang.Long",
            order = 5,
            step = 0
    )
    private OficinaFuncionarioEntidad oficinaFuncionario;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RolUsuarioEntidad> rolUsuario = new ArrayList<>();
    
    /*
     * 
     * Conjunto de templates enumerados de acuerdo a la
     * Clasificacion de Huellas Digitales Henry
     * 
     * */

    /* Minucia para del pulgar derecho */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE1", nullable = true)
    @Lob
    private byte[] template1;

    /* Minucia para indice derecho */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE2", nullable = true)
    @Lob
    private byte[] template2;

    /* Minucia para medio derecho */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE3", nullable = true)
    @Lob
    private byte[] template3;

    /* Minucia para anular derecho */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE4", nullable = true)
    @Lob
    private byte[] template4;

    /* Minucia para minique derecho */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE5", nullable = true)
    @Lob
    private byte[] template5;

    /* Minucia para pulgar izquierdo */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE6", nullable = true)
    @Lob
    private byte[] template6;

    /* Minucia para indice izquierdo */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE7", nullable = true)
    @Lob
    private byte[] template7;

    /* Minucia para medio izquierdo */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE8", nullable = true)
    @Lob
    private byte[] template8;

    /* Minucia para anular izquierdo */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE9", nullable = true)
    @Lob
    private byte[] template9;

    /* Minucia para meñique izquierdo */
    @Basic(optional = true)
    @Column(name = "GR_TEMPLATE10", nullable = true)
    @Lob
    private byte[] template10;

    public UsuarioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OficinaFuncionarioEntidad getOficinaFuncionario() {
        return oficinaFuncionario;
    }

    public void setOficinaFuncionario(
            OficinaFuncionarioEntidad oficinaFuncionario) {
        this.oficinaFuncionario = oficinaFuncionario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public List<RolUsuarioEntidad> getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(List<RolUsuarioEntidad> rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public byte[] getTemplate1() {
        return template1;
    }

    public void setTemplate1(byte[] template1) {
        this.template1 = template1;
    }

    public byte[] getTemplate2() {
        return template2;
    }

    public void setTemplate2(byte[] template2) {
        this.template2 = template2;
    }

    public byte[] getTemplate3() {
        return template3;
    }

    public void setTemplate3(byte[] template3) {
        this.template3 = template3;
    }

    public byte[] getTemplate4() {
        return template4;
    }

    public void setTemplate4(byte[] template4) {
        this.template4 = template4;
    }

    public byte[] getTemplate5() {
        return template5;
    }

    public void setTemplate5(byte[] template5) {
        this.template5 = template5;
    }

    public byte[] getTemplate6() {
        return template6;
    }

    public void setTemplate6(byte[] template6) {
        this.template6 = template6;
    }

    public byte[] getTemplate7() {
        return template7;
    }

    public void setTemplate7(byte[] template7) {
        this.template7 = template7;
    }

    public byte[] getTemplate8() {
        return template8;
    }

    public void setTemplate8(byte[] template8) {
        this.template8 = template8;
    }

    public byte[] getTemplate9() {
        return template9;
    }

    public void setTemplate9(byte[] template9) {
        this.template9 = template9;
    }

    public byte[] getTemplate10() {
        return template10;
    }

    public void setTemplate10(byte[] template10) {
        this.template10 = template10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuarioEntidad that = (UsuarioEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
