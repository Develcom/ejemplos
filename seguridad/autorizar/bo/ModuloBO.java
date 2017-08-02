package ve.gob.cne.sarc.seguridad.core.autorizar.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * ModuloBO.java
 * 
 * Descripcion de la clase: Business Obj. para el mapeo y acceso a la tabla modulo de la BD
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */
public class ModuloBO implements Serializable {

    /**
     * Variable manejadas en la tabla I013T_MODULO
     */
    private static final long serialVersionUID = -2971808371876162204L;
    private long coModulo;
    private String nbModulo;
    private String txDescripcion;
    private Date feInicio;
    private Date feFin;
        
    public ModuloBO() {
        super();
    }

    public ModuloBO(long coModulo, String nbModulo, String txDescripcion, Date feInicio, Date feFin) {
        super();
        this.coModulo = coModulo;
        this.nbModulo = nbModulo;
        this.txDescripcion = txDescripcion;
        this.feInicio = feInicio;
        this.feFin = feFin;
    }
    
    public long getCoModulo() {
        return coModulo;
    }
    public void setCoModulo(long coModulo) {
        this.coModulo = coModulo;
    }
    public String getNbModulo() {
        return nbModulo;
    }
    public void setNbModulo(String nbModulo) {
        this.nbModulo = nbModulo;
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
