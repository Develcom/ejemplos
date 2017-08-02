package ve.gob.cne.sarc.seguridad.core.autorizar.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * RolBO.java
 * 
 * Descripcion de la clase: Business Obj. para el mapeo y acceso a la tabla rol de la BD
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */
public class RolBO implements Serializable {

    /**
     * Variable manejadas en la tabla I014T_rol
     */
    private static final long serialVersionUID = -2971808371876162204L;
    private long coRol;
    private String nbRol;
    private String txDescripcion;
    private Date feInicio;
    private Date feFin;
    
    public RolBO() {
        super();
    }

    public RolBO(long coRol, String nbRol, String txDescripcion, Date feInicio, Date feFin) {
        super();
        this.coRol = coRol;
        this.nbRol = nbRol;
        this.txDescripcion = txDescripcion;
        this.feInicio = feInicio;
        this.feFin = feFin;
    }

    public long getCoRol() {
        return coRol;
    }

    public void setCoRol(long coRol) {
        this.coRol = coRol;
    }

    public String getNbRol() {
        return nbRol;
    }

    public void setNbRol(String nbRol) {
        this.nbRol = nbRol;
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
