package ve.gob.cne.sarc.persistencia.dao.sincronizacion;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.VersionBitacoraSincronizadorEntidad;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author HiSoft
 */
public interface VersionBitacoraSincronizadorDao extends GenericDao {

    /**
     * Metodo para buscar paquetes
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    List<Map<String, Object>> buscarPaquetes();

    /**
     * Buscar paquetes difusion
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    Collection<Map<String, Object>> buscarPaquetesDifusion();
}
