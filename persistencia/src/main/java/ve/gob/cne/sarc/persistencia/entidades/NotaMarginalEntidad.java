package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jorge
 * Date: 29/04/15
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */


@Entity
@Table(name="C086T_NOTA_MARGINAL")
public class NotaMarginalEntidad implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue (strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "NOTA_MARGINAL_SEQ", sequenceName = "C086S_NOTA_MARGINAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTA_MARGINAL_SEQ")
    @Column(name = "CO_NOTA_MARGINAL")
    private long id;

    @Column(name = "TX_TEXTO", nullable = true, length = 4096)
    private String texto;

    @Column(name = "FE_REGISTRO", nullable = true)
    private Date fechaRegistro;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CO_TIPO_NOTA_MARGINAL", nullable = false)
    private TipoNotaMarginalEntidad tipoNotaMarginalEntidad;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA", nullable = true)
    private ActaEntidad acta;



    public NotaMarginalEntidad(String texto, Date fechaRegistro, TipoNotaMarginalEntidad tipoNotaMarginalEntidad, ActaEntidad acta) {
        this.texto = texto;
        this.fechaRegistro = fechaRegistro;
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
        this.acta = acta;
    }

    public NotaMarginalEntidad() {
        //constructor por defecto
    }

    public NotaMarginalEntidad(Date fechaRegistro, String texto,
                               TipoNotaMarginalEntidad tipoNotaMarginalEntidad, ActaEntidad acta) {
        this.fechaRegistro = fechaRegistro;
        this.texto = texto;
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
        this.acta = acta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public TipoNotaMarginalEntidad getTipoNotaMarginalEntidad() {
        return tipoNotaMarginalEntidad;
    }

    public void setTipoNotaMarginalEntidad(TipoNotaMarginalEntidad tipoNotaMarginalEntidad) {
        this.tipoNotaMarginalEntidad = tipoNotaMarginalEntidad;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }


    @Override
    public String toString() {
        return "NotaMarginalEntidad{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", tipoNotaMarginalEntidad=" + tipoNotaMarginalEntidad +
                ", acta=" + acta +
                '}';
    }
}
