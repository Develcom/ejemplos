package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the C037T_ESCENARIO_RECAUDO database table.
 * 
 */
@Entity
@Table(name="C037T_ESCENARIO_RECAUDO")
@NamedQueries({
    @NamedQuery(name = "EscenarioRecaudoEntidad.findPendientes", query = "SELECT er FROM EscenarioRecaudoEntidad er WHERE er.escenario.id = :idEscenario AND er.id NOT IN (SELECT ar.escenarioRecaudo.id FROM ActaRecaudoEntidad ar WHERE ar.acta.solicitud.numero = :numeroSolicitud)"),
    @NamedQuery(name = "EscenarioRecaudoEntidad.findAll", query = "SELECT i FROM EscenarioRecaudoEntidad i")})
public class EscenarioRecaudoEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_ESCENARIO_RECAUDO")
	private long id;

	@Column(name="CO_ESTATUS_TRAMITE")
	private long estatusTramite;

	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName ="CO_RECAUDO" , name = "CO_RECAUDO")
	private RecaudoEntidad recaudo;

	@Temporal(TemporalType.DATE)
	@Column(name="FE_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="FE_INICIO")
	private Date fechaInicio;

	@Column(name="IN_OBLIGATORIO")
	private long obligatorio;

	@Column(name="NU_PLAZO_DIAS")
	private int nuPlazoDias;

	//bi-directional many-to-one association to EscenarioEntidad
	@ManyToOne
	@JoinColumn(name="CO_ESCENARIO")
	private EscenarioEntidad escenario;
	
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "escenarioRecaudo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActaRecaudoEntidad> actaRecaudo;

	public EscenarioRecaudoEntidad() {
	}



    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }



    public long getEstatusTramite() {
        return estatusTramite;
    }



    public void setEstatusTramite(long estatusTramite) {
        this.estatusTramite = estatusTramite;
    }



    public RecaudoEntidad getRecaudo() {
        return recaudo;
    }



    public void setRecaudo(RecaudoEntidad recaudo) {
        this.recaudo = recaudo;
    }



    public Date getFechaFin() {
        return fechaFin;
    }



    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }



    public Date getFechaInicio() {
        return fechaInicio;
    }



    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }



    public long getObligatorio() {
        return obligatorio;
    }



    public void setObligatorio(long obligatorio) {
        this.obligatorio = obligatorio;
    }



    public int getNuPlazoDias() {
        return nuPlazoDias;
    }



    public void setNuPlazoDias(int nuPlazoDias) {
        this.nuPlazoDias = nuPlazoDias;
    }



    public EscenarioEntidad getEscenario() {
        return escenario;
    }

    public void setEscenario(EscenarioEntidad escenario) {
        this.escenario = escenario;
    }



    public List<ActaRecaudoEntidad> getActaRecaudo() {
        return actaRecaudo;
    }



    public void setActaRecaudo(List<ActaRecaudoEntidad> actaRecaudo) {
        this.actaRecaudo = actaRecaudo;
    }
    
    

}