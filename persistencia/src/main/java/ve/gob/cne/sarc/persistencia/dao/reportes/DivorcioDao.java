package ve.gob.cne.sarc.persistencia.dao.reportes;

import java.util.List;
import java.util.Map;

/**
 * @author HiSoft
 */

public interface DivorcioDao {
     /**
     * Obtiene los divorcios agrupados por entidad federal
     * @param geografico
     * @return 
     */

    List<Map<String, Object>> divorciosPorEntidad(long geografico);
    
}
