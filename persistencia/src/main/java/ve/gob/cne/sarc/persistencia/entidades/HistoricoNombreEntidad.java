package ve.gob.cne.sarc.persistencia.entidades;

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
 * Se crea la clase HistoricoNombreEntidad donde se Realizan los Query de consulta de cada metodo
 * @author carlos.castillo
 *
 */

@Entity
@Table(name="X055T_HISTORICO_NOMBRE")
@EntityListeners({HistoricoNombreEntidad.class})
@NamedQueries({
    @NamedQuery(name = HistoricoNombreEntidad.BUSCAR_TODOS, query = "SELECT historicoNombre "
            + "FROM HistoricoNombreEntidad historicoNombre"),
    @NamedQuery(name = HistoricoNombreEntidad.BUSCAR_POR_NOMBRE, query = "SELECT historicoNombre "
            + "FROM HistoricoNombreEntidad historicoNombre WHERE  historicoNombre.nbViejo = :nbViejo "
            + "AND historicoNombre.oficina.tipoOficina = :tipoOficina AND historicoNombre.oficina.fechaFin IS NULL"),
})

public class HistoricoNombreEntidad  implements java.io.Serializable {

	 public static final String BUSCAR_TODOS = "HistoricoNombreEntidad.findAll";
	 public static final String BUSCAR_POR_NOMBRE = "HistoricoNombreEntidad.buscarPorNombre";
	    
	@Id
    @Column(name="CO_HISTORICO_NOMBRE", unique=true, nullable=false, precision=22, scale=0)
	@SequenceGenerator(name = "HISTORICO_NOMBRE_SEQ", sequenceName = "X055S_CO_HISTORICO_NOMBRE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISTORICO_NOMBRE_SEQ")
     private long id;
	
	@ManyToOne (optional = false)
    @JoinColumn(name="CO_OFICINA", referencedColumnName = "CO_OFICINA", nullable=false)
     private OficinaEntidad oficina;
	
	 @Temporal(TemporalType.DATE)
	    @Column(name="FE_CAMBIO_NOMBRE", nullable=false, length=7)
     private Date feCambioNombre;
    
	  @Column(name="NB_VIEJO", nullable=false, length=50)
	 private String nbViejo;

    public HistoricoNombreEntidad() {
    	// Metodo Constructor
    }

    public HistoricoNombreEntidad(long id, OficinaEntidad oficina, Date feCambioNombre, String nbViejo) {
       this.id = id;
       this.oficina = oficina;
       this.feCambioNombre = feCambioNombre;
       this.nbViejo = nbViejo;
    }
   
     
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public OficinaEntidad getOficina() {
        return this.oficina;
    }
    
    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

   
    public Date getFeCambioNombre() {
        return this.feCambioNombre;
    }
    
    public void setFeCambioNombre(Date feCambioNombre) {
        this.feCambioNombre = feCambioNombre;
    }

    
    public String getNbViejo() {
        return this.nbViejo;
    }
    
    public void setNbViejo(String nbViejo) {
        this.nbViejo = nbViejo;
    }
}
