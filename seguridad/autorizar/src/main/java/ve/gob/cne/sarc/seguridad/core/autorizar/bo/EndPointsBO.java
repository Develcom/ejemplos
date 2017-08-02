package ve.gob.cne.sarc.seguridad.core.autorizar.bo;

import java.io.Serializable;

/**
 * Descripcion de la clase: BO que devuelve los endsPoinst
 * @author Ismayer Hernandez
 * 16 de ago. de 2016
 */
public class EndPointsBO implements Serializable {
    
    /**
     * Variables a utilizar
     */
    private static final long serialVersionUID = 1L;
    private String endPoints;
    private int nivelRestriccion;
    private String tipoPermiso;
    private String rol;
    
    public EndPointsBO() {
        super();
    }

    public EndPointsBO(String endPoints, int nivelRestriccion, String tipoPermiso, String rol) {
        super();
        this.endPoints = endPoints;
        this.nivelRestriccion = nivelRestriccion;
        this.tipoPermiso = tipoPermiso;
        this.rol = rol;
    }

    public String getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(String endPoints) {
        this.endPoints = endPoints;
    }

    public int getNivelRestriccion() {
        return nivelRestriccion;
    }

    public void setNivelRestriccion(int nivelRestriccion) {
        this.nivelRestriccion = nivelRestriccion;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }    

}
