package ve.gob.cne.sarc.seguridad.core.autorizar.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * RecusroBO.java
 * 
 * Descripcion de la clase: Business Obj. para el mapeo y acceso a la tabla recurso de la BD
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */
public class RecursoBO implements Serializable {

    /**
     * Variable manejadas en la tabla I014T_RECURSO
     */
    private static final long serialVersionUID = -2971808371876162204L;
    private long coRecurso;
    private String nbRecurso;
    private String txDescripcion;
    private Date feInicio;
    private Date feFin;
    
    public RecursoBO() {
        super();
    }

    public RecursoBO(long coRecurso, String nbRecurso, String txDescripcion, Date feInicio, Date feFin) {
        super();
        this.coRecurso = coRecurso;
        this.nbRecurso = nbRecurso;
        this.txDescripcion = txDescripcion;
        this.feInicio = feInicio;
        this.feFin = feFin;
    }

    public long getCoRecurso() {
        return coRecurso;
    }

    public void setCoRecurso(long coRecurso) {
        this.coRecurso = coRecurso;
    }

    public String getNbRecurso() {
        return nbRecurso;
    }

    public void setNbRecurso(String nbRecurso) {
        this.nbRecurso = nbRecurso;
    }

    public String getTxDescripcion() {
        return txDescripcion;
    }

    public void setTxDescripcion(String txDescripcion) {
        this.txDescripcion = txDescripcion;
    }

    public Date getFeInicio() {
        return feInicio;
    }

    public void setFeInicio(Date feInicio) {
        this.feInicio = feInicio;
    }

    public Date getFeFin() {
        return feFin;
    }

    public void setFeFin(Date feFin) {
        this.feFin = feFin;
    }

}
