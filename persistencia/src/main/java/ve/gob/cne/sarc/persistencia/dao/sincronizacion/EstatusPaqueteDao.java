package ve.gob.cne.sarc.persistencia.dao.sincronizacion;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author HiSoft
 *
 */
public interface EstatusPaqueteDao {
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> estatusPaquetes(Map<String, Object> params);

}
