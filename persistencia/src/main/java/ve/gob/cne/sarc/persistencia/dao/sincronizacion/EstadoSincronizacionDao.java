package ve.gob.cne.sarc.persistencia.dao.sincronizacion;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.EstadoSincronizacionEntidad;

/**
 * Created by HiSoft
 */

public interface EstadoSincronizacionDao extends GenericDao {

    /**
     * Obtener de bd por codigo
     *
     * @param codigo
     * @return
     */
    EstadoSincronizacionEntidad loadByCode(String codigo);
}
