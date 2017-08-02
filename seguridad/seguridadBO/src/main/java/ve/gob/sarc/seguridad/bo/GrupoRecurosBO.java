package ve.gob.sarc.seguridad.bo;

import java.util.List;


public class GrupoRecurosBO {
    
    private List<String> endPoint;
    private List<GrupoPermisoBO> grupoPermisoBO;

    public List<String> getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(List<String> endPoint) {
        this.endPoint = endPoint;
    }

    public List<GrupoPermisoBO> getGrupoPermisoBO() {
        return grupoPermisoBO;
    }

    public void setGrupoPermisoBO(List<GrupoPermisoBO> grupoPermisoBO) {
        this.grupoPermisoBO = grupoPermisoBO;
    }
    
}
