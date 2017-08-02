package ve.gob.cne.sarc.seguridad.core.autorizar.business;

import java.util.List;
import ve.gob.sarc.seguridad.bo.EndPointsBO;
import ve.gob.sarc.seguridad.bo.GrupoRecurosBO;


/**
 * Descripcion de la clase: Interface para la implementacion de consulta de los recursos
 * @author Ismayer Hernandez
 *
 */
public interface PermisologiaBF {
    
    public Boolean findByCoPermisologiaBF(String coPermisologia);
    public List<EndPointsBO> buscaRecurso(String coModulo);
    public List<GrupoRecurosBO> buscarRecurso(String coModulo);

}
