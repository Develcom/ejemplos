package ve.gob.cne.sarc.persistencia.dao.sincronizacion;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.EstadoSincronizacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.VersionSincronizadorEstadoEntidad;

import java.util.List;

/**
 * @author HiSoft
 */
public interface VersionSincronizadorEstadoDao extends GenericDao {
    /**
     * @param estadoSync
     * @return
     */
    List<VersionSincronizadorEstadoEntidad> loadInState(EstadoSincronizacionEntidad estadoSync);
}
