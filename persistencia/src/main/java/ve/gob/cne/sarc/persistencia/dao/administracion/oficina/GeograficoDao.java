package ve.gob.cne.sarc.persistencia.dao.administracion.oficina;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.GeograficoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.GeograficoPaisEntidad;

import java.util.Collection;
import java.util.List;

/**
 * @author HiSoft
 */

public interface GeograficoDao extends GenericDao {
    /**
     * Cargar Colección de GeograficoEntidad por Estado y
     * Dependecias Federales
     *
     * @return collection
     */
    Collection<GeograficoEntidad> cargarPorEstadoDependencias();

    /**
     * Cargar colección de GeograficoEntidad hijos
     * @param geograficoEntidad Padre
     * @return collection
     */
    Collection<GeograficoEntidad> cargarHijos(GeograficoEntidad geograficoEntidad);
    
    /**
     * Cargar lista de paises no eliminados
     * @param columnName
     * @param p
     * @return List
     */
    <P> List<GeograficoEntidad> cargarGeograficosOrdenados(String columnName, P p);
    
    /**
     * Cargar lista de municipios no eliminados hijos de un estado
     * @param geograficoEntidad
     * @return Collection
     */
    Collection<GeograficoEntidad> cargarMunicipiosHijos(GeograficoEntidad geograficoEntidad);
    
    /**
     * Cargar lista de estados no eliminados hijos de un pais
     * @param geograficoEntidad
     * @return Collection
     */
    Collection<GeograficoEntidad> cargarEstadosHijos(GeograficoEntidad geograficoEntidad);
    
    /**
     * Cargar lista de paises no eliminados
     * @param columnName    
     * @param p
     * @return List
     */
    <P> List<GeograficoPaisEntidad> cargarPaisesOrdenados(String columnName, P p);
}
