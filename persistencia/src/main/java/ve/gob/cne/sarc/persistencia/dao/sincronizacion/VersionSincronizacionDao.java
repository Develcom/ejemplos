package ve.gob.cne.sarc.persistencia.dao.sincronizacion;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.VersionSincronizacionEntidad;

/**
 * @author HiSoft
 */
public interface VersionSincronizacionDao extends GenericDao {

    /**
     * obtener entidad de la bd ppor codigo
     *
     * @param code
     * @return
     */
    public VersionSincronizacionEntidad loadByCode(Integer code);

    /**
     * Obtener la ultima versión
     *
     * @return
     */
    public VersionSincronizacionEntidad getLastVersion();
}
