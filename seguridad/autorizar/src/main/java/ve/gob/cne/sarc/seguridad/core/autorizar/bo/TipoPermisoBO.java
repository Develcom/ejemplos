package ve.gob.cne.sarc.seguridad.core.autorizar.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * TipoPermisoBO.java
 * 
 * Descripcion de la clase: Business Obj. para el mapeo y acceso a la tabla tipo permiso de la BD
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */
public class TipoPermisoBO implements Serializable {

    /**
     * Variable manejadas en la tabla I012T_TIPO_PERMISO
     */
    private static final long serialVersionUID = -2971808371876162204L;
    private long coPermiso;
    private String nbPermiso;
    private String txDescripcion;
    private Date feInicio;
    private Date feFin;

    public TipoPermisoBO(long coPermiso, String nbPermiso, String txDescripcion, Date feInicio, Date feFin) {
        super();
        this.coPermiso = coPermiso;
        this.nbPermiso = nbPermiso;
        this.txDescripcion = txDescripcion;
        this.feInicio = feInicio;
        this.feFin = feFin;
    }

    public TipoPermisoBO() {
        super();
    }

    public long getCoPermiso() {
        return coPermiso;
    }

    public void setCoPermiso(long coPermiso) {
        this.coPermiso = coPermiso;
    }

    public String getNbPermiso() {
        return nbPermiso;
    }

    public void setNbPermiso(String nbPermiso) {
        this.nbPermiso = nbPermiso;
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
