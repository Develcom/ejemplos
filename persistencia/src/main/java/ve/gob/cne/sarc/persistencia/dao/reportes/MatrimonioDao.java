package ve.gob.cne.sarc.persistencia.dao.reportes;

import java.util.List;
import java.util.Map;

import ve.gob.cne.sarc.persistencia.entidades.GeograficoEntidad;

/**
 * 
 * @author Hisoft
 *
 */
public interface MatrimonioDao {

    /**
     * Obtiene los matrimonios agrupados por año, mes o día dado una entidad federal
     * @param geografico
     * @return
     */
    List<Map<String, Object>> matrimoniosPorEntidad(GeograficoEntidad geografico);
    
    /**
     * Obtiene los matrimonios agrupados por año  dado una entidad federal
     * @param geografico
     * @param anio 
     * @return
     */
    List<Map<String, Object>> matrimoniosPorEntidad(int anio, long geografico);
    
    /**
     * Obtiene los matrimonios agrupados por año,mes, dado una entidad federal
     * @param geografico
     * @param anio 
     * @param mes 
     * @return
     */
    List<Map<String, Object>> matrimoniosPorEntidad(int anio,int mes, long geografico);
}
