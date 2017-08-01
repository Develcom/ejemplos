package ve.gob.cne.sarc.persistencia.dao.administracion.rol;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.ModuloSeguridadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PermisoRecursoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PermisologiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoPermisoEntidad;

import java.util.Collection;

/**
 * @author HiSoft
 */

public interface PermisologiaDao extends GenericDao {
    /**
     * Cargar Colección de PermisologiaEntidad
     * @param moduloSeguridad ModuloSeguridadEntidad Asociado a PermisologiaEntidad
     * @param tipoPermisoEntidad TipoPermisoEntidad Asociado a PermisologiaEntidad
     * @return Colección de PermisologiaEntidad
     */
    Collection<PermisologiaEntidad> load(ModuloSeguridadEntidad moduloSeguridad,
                                         TipoPermisoEntidad tipoPermisoEntidad);

    /**
     * Cargar PermisoRecurso por Permisologia
     * @param permisologia
     * @return
     */
    Collection<PermisoRecursoEntidad> load(PermisologiaEntidad permisologia);

    /**
     * Obtener el siguiente Grupo
     * @return
     */
    int getNextGroup();
}
