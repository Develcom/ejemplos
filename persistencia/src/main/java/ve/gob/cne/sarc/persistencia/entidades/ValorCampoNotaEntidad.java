package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Posma-edwyn
 * Date:  08/05/2015
 * Time: 11:05:11
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="C088T_VALOR_CAMPO_NOTA")
public class ValorCampoNotaEntidad implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue (strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "VALOR_CAMPO_NOTA_SEQ", sequenceName = "C088S_VALOR_CAMPO_NOTA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VALOR_CAMPO_NOTA_SEQ")
    @Column (name="CO_VALOR_CAMPO")
    private Long id;
    
    @Column (name="TX_VALOR",nullable = false, length = 255)
    private String valor ;
    
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "CO_CAMPO_TIPO")
    private CampoTipoNotaMarginalEntidad campoTipoNotaMarginalEntidad;
    
    
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "CO_NOTA_MARGINAL")
    private NotaMarginalEntidad notaMarginalEntidad;


    public ValorCampoNotaEntidad(){
        //constructor por defecto
    }

    public ValorCampoNotaEntidad(String valor, CampoTipoNotaMarginalEntidad campoTipoNotaMarginalEntidad,
                                 NotaMarginalEntidad notaMarginalEntidad){
        this.valor = valor;
        this.notaMarginalEntidad = notaMarginalEntidad;
        this.campoTipoNotaMarginalEntidad = campoTipoNotaMarginalEntidad;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CampoTipoNotaMarginalEntidad getCampoTipoNotaMarginalEntidad() {
        return campoTipoNotaMarginalEntidad;
    }

    public void setCampoTipoNotaMarginalEntidad(CampoTipoNotaMarginalEntidad campoTipoNotaMarginalEntidad) {
        this.campoTipoNotaMarginalEntidad = campoTipoNotaMarginalEntidad;
    }

    public NotaMarginalEntidad getNotaMarginalEntidad() {
        return notaMarginalEntidad;
    }

    public void setNotaMarginalEntidad(NotaMarginalEntidad notaMarginalEntidad) {
        this.notaMarginalEntidad = notaMarginalEntidad;
    }

    @Override
    public String toString() {
        return "ValorCampoNotaEntidad{" +
                "id=" + id +
                ", valor='" + valor + '\'' +
                ", campoTipoNotaMarginalEntidad=" + campoTipoNotaMarginalEntidad +
                ", notaMarginalEntidad=" + notaMarginalEntidad +
                '}';
    }
}


