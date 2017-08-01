package ve.gob.cne.sarc.persistencia.dao.administracion.rol;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.PermisologiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.RolEntidad;
import ve.gob.cne.sarc.persistencia.entidades.RolOperadorEntidad;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;

import java.util.Collection;
import java.util.List;

/**
 * @author HiSoft
 */
public interface RolDao extends GenericDao {

    /**
     * Busca roles por una lista de id
     *
     * @param rolesId
     * @return
     */
    List<RolEntidad> loadRolsView(List<Long> rolesId);

    /**
     * Cargar RolOperadorEntidad por parametros
     * @param rol
     * @param permisologia
     * @param operador
     * @return
     */
    Collection<RolOperadorEntidad> load(RolEntidad rol, PermisologiaEntidad permisologia,
                                        String operador);

    /**
     * Cargar RolOperadorEntidad por rol
     * @param rol
     * @return
     */
    Collection<RolOperadorEntidad> load(RolEntidad rol);
    
    /**
     * Cargar roles de RolUsuario filtrando por usuario
     * @param usuario
     * @return
     */
    List<RolEntidad> loadRolsFromBD(UsuarioEntidad usuario);
    
    /**
     * 
     * @param rolsToDelete
     * @param usuario
     */
    void deleteRols(Collection<RolEntidad> rolsToDelete, UsuarioEntidad usuario);
    
    /**
     * 
     * @param idRolsToInsert
     * @param usuario
     */
    void insertRols(Collection<RolEntidad> idRolsToInsert, UsuarioEntidad usuario);
}
