package ve.gob.sarc.seguridad.bo;


public class GrupoPermisoBO {
    
    private String permiso;
    private String rol;
    private String operadorLogico;
    private int numeroPos;

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getOperadorLogico() {
        return operadorLogico;
    }

    public void setOperadorLogico(String operadorLogico) {
        this.operadorLogico = operadorLogico;
    }

    public int getNumeroPos() {
        return numeroPos;
    }

    public void setNumeroPos(int numeroPos) {
        this.numeroPos = numeroPos;
    }
    
}
