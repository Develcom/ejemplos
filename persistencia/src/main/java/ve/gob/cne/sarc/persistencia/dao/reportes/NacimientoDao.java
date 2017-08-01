package ve.gob.cne.sarc.persistencia.dao.reportes;

import java.util.List;
import java.util.Map;

/**
 * @author HiSoft
 */

public interface NacimientoDao {

    /**
     * Cargar lista de Nacimientos por Dependencia Federal y anio
     * @param geografico
     * @param anio
     * @return
     */
    List<Map<String, Object>> nacimientosPorEntidadAnio(int anio,long geografico);
    
    /**
     * Cargar lista de Nacimientos filtrados por el anio, sexo y la Dependencia Federal
     * @param anio
     * @param sexo
     * @param geografico
     * @return
     */
    List<Map<String, Object>> nacimientosPorEntidadSexo(int anio, String sexo,long geografico);
    
    /**
     * Cargar lista de Nacimientos por Dependencia Federal, anio y mes
     * @param geografico
     * @param anio
     * @param mes
     * @return
     */
    List<Map<String, Object>> nacimientosPorEntidadAnioMes(int anio,int mes, long geografico);
    
}
