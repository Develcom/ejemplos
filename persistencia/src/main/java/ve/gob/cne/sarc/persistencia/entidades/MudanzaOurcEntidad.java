package ve.gob.cne.sarc.persistencia.entidades;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Se crea la clase MudanzaOurcEntidad donde se Realizan los Query de consulta de cada metodo
 * @author carlos.castillo
 *
 */
@Entity
@Table(name="X054T_MUDANZA_OURC")
@EntityListeners({MudanzaOurcEntidad.class})
@NamedQueries({
    @NamedQuery(name = MudanzaOurcEntidad.BUSCAR_TODOS, query = "SELECT mudanzaOurc "
            + "FROM   MudanzaOurcEntidad mudanzaOurc"),
    @NamedQuery(name = MudanzaOurcEntidad.BUSCAR_POR_ID, query = "SELECT mudanzaOurc "
            + "FROM   MudanzaOurcEntidad mudanzaOurc WHERE  mudanzaOurc.id = :id"),
    @NamedQuery(name = MudanzaOurcEntidad.BUSCAR_OFCINA_NUEVA, query = "SELECT ourc "
            + "FROM MudanzaOurcEntidad ourc, OficinaEntidad o WHERE  ourc.codigoOurcVieja = o.id "
            + "AND TRIM(UPPER(o.nombre)) = TRIM(UPPER(:nombre)) AND ourc.feFin IS NULL")
})

public class MudanzaOurcEntidad  implements java.io.Serializable {

	public static final String BUSCAR_TODOS = "MudanzaOurcEntidad.findAll";
	public static final String BUSCAR_POR_ID = "MudanzaOurcEntidad.buscarPorCodigo";
	public static final String BUSCAR_OFCINA_NUEVA = "MudanzaOurcEntidad.buscarOurcNueva";
	 
	 @Id 
	 @Column(name="CO_MUDANZA_OURC", unique=true, nullable=false, precision=22, scale=0)
	 @SequenceGenerator(name = "MUDANZA_OURC_SEQ", sequenceName = "X054S_CO_MUDANZA_OURC", allocationSize = 1)
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUDANZA_OURC_SEQ")
     private long id;
     
	 @ManyToOne(optional = false)
	    @JoinColumn(name="CO_OURC_VIEJA", nullable=false)
	 private OficinaEntidad codigoOurcVieja;
	 
	 @ManyToOne(optional = false)
	    @JoinColumn(name="CO_OURC_NUEVA", nullable=false)
     private OficinaEntidad codigoOurcNueva;
	 
	  @Temporal(TemporalType.DATE)
	    @Column(name="FE_INICIO", nullable=false, length=7)
     private Date feInicio;
	  
	  @Temporal(TemporalType.DATE)
	    @Column(name="FE_FIN", length=7)
     private Date feFin;
	  
	  @Column(name="MOTIVO", precision=22, scale=0)
     private BigDecimal motivo;

    public MudanzaOurcEntidad() {
    	// Metodo Constructor
    }

    public MudanzaOurcEntidad(long id,OficinaEntidad codigoOurcVieja, OficinaEntidad codigoOurcNueva, Date feInicio) {
        this.id = id;
        this.codigoOurcVieja = codigoOurcVieja;
        this.codigoOurcNueva = codigoOurcNueva;
        this.feInicio = feInicio;
    }
    public MudanzaOurcEntidad(long id,OficinaEntidad codigoOurcVieja,OficinaEntidad codigoOurcNueva, Date feInicio, Date feFin, BigDecimal motivo) {
       this.id = id;
       this.codigoOurcVieja = codigoOurcVieja;
       this.codigoOurcNueva = codigoOurcNueva;
       this.feInicio = feInicio;
       this.feFin = feFin;
       this.motivo = motivo;
    }
   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public OficinaEntidad getCodigoOurcVieja() {
        return this.codigoOurcVieja;
    }
    
    public void setCodigoOurcVieja (OficinaEntidad codigoOurcVieja) {
        this.codigoOurcVieja = codigoOurcVieja;
    }

    public OficinaEntidad getCodigoOurcNueva() {
        return this.codigoOurcNueva;
    }
    
    public void setCodigoOurcNueva (OficinaEntidad codigoOurcNueva) {
        this.codigoOurcNueva = codigoOurcNueva;
    }
  
    public Date getFeInicio() {
        return this.feInicio;
    }
    
    public void setFeInicio(Date feInicio) {
        this.feInicio = feInicio;
    }
  
    public Date getFeFin() {
        return this.feFin;
    }
    
    public void setFeFin(Date feFin) {
        this.feFin = feFin;
    }  
   
    public BigDecimal getMotivo() {
        return this.motivo;
    }
    
    public void setMotivo(BigDecimal motivo) {
        this.motivo = motivo;
    }
}
