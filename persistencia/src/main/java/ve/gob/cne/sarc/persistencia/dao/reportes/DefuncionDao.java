package ve.gob.cne.sarc.persistencia.dao.reportes;

import java.util.List;
import java.util.Map;

/**
 * @author HiSoft
 */

public interface DefuncionDao {
     /**
     * Obtiene las defunciones agrupados por anio de registro y entidad federal
     * @param geografico
     * @param anio 
     * @return 
     */

    List<Map<String, Object>> defuncionPorEntidad(int anio, long geografico);
    
}
