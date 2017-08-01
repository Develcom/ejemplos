package ve.gob.cne.sarc.persistencia.dao.sincronizacion;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.SincronizadorEntidad;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author HiSoft
 */
public interface SincronizadorDao extends GenericDao {

    /**
     * Metodo para obtner una entidad de la bd
     *
     * @param nombre
     * @return
     */
    SincronizadorEntidad load(String nombre);

    /**
     * Metodo para obtener una entidad de la bd por nombre
     *
     * @param nombres
     * @return
     */
    List<SincronizadorEntidad> findByNombres(List<String> nombres);

    /**
     * Cargar la colección de Sincronizadores Asociados a la oficina por
     * Lista de id de GeograficoEntidad
     *
     * @param idGeograficos Lista de id de GeograficoEntidad
     * @return Sincronizadores Asociados a la oficina
     */
    Collection<SincronizadorEntidad> cargarSynPorGeo(Collection<Long> idGeograficos);

    /**
     * cargar los sincronizadores padres
     *
     * @return Colección de sincronizadores padre
     */
    Collection<SincronizadorEntidad> cargarPadres();

    /**
     * Elimina todas las relaciones padre-hijo
     */
    void deleteAllRelation();
    
    /**
     * 
     * @param cod
     * @return
     */
    public List<Map<String, Object>> getOfficesDependant(String cod);
    
    /**
     * @param cod
     * @return
     */
    public List<Map<String, Object>> loadConectedOffices(String cod);
    
    /**
     * 
     * @param id
     * @return
     */
    public List<Map<String, Object>> loadDependantByIdConected(long id);
}
