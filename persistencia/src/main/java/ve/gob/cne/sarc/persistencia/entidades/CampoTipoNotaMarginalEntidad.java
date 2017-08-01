package ve.gob.cne.sarc.persistencia.entidades;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Posma-edwyn
 * Date:  07/05/2015
 * Time: 15:31:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="I021T_CAMPO_TIPO_NOTA_MARGINAL")
public class CampoTipoNotaMarginalEntidad implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    //@GeneratedValue (strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "CAMPO_TIPO_NOTA_SEQ", sequenceName = "I021S_CAMPO_TIPO_NOTA_MARGINAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPO_TIPO_NOTA_SEQ")
    @Column( name = "CO_CAMPO_TIPO")
    private Long id;
    
    @Column(name="NB_NOMBRE", nullable=false, length = 255)
    private String nombre;
    
    @Column (name="NB_TIPO", nullable=true, length = 255)
    private String  tipoDato;

    @Column (name="NB_TIPO_CAMPO", nullable=false, length = 255)
    private String  tipoCampo;

    @Column(name="TX_DESCRIPCION", nullable=false, length = 255)
    private String descripcion;

    @Column(name="IN_REQUERIDO", nullable=false)
    private long requerido;

    @Column(name="NB_GRUPO", nullable=false)
    private String grupo;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name="CO_TIPO_NOTA_MARGINAL")
    private TipoNotaMarginalEntidad tipoNotaMarginalEntidad;

    public CampoTipoNotaMarginalEntidad(){
        //constructor por defecto
    }
    @Autowired
    public CampoTipoNotaMarginalEntidad(String nombre, String tipoCampo, String descripcion,
                                        TipoNotaMarginalEntidad tipoNotaMarginalEntidad){
        this.nombre = nombre;
        this.tipoCampo = tipoCampo;
        this.descripcion= descripcion;
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
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

    public String getTipoCampo() {
        return tipoCampo;
    }

    public void setTipoCampo(String tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getRequerido() {
        return requerido;
    }

    public void setRequerido(long requerido) {
        this.requerido = requerido;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setTipoNotaMarginalEntidad(TipoNotaMarginalEntidad tipoNotaMarginalEntidad) {
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
    }

    public TipoNotaMarginalEntidad getTipoNotaMarginalEntidad() {
        return tipoNotaMarginalEntidad;
    }

    public void setCampoTipoNotaMarginal(TipoNotaMarginalEntidad tipoNotaMarginalEntidad) {
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
    }

    @Override
    public String toString() {
        return "CampoTipoNotaMarginalEntidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipoCampo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipoNotaMarginalEntidad=" + tipoNotaMarginalEntidad +
                '}';
    }
}


