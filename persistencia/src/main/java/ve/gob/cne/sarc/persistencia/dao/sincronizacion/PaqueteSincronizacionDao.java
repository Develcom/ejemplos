package ve.gob.cne.sarc.persistencia.dao.sincronizacion;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.EstadoSincronizacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteSincronizacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SincronizadorEntidad;
import ve.gob.cne.sarc.persistencia.entidades.VersionSincronizacionEntidad;

import java.util.List;

/**
 * @author HiSoft
 */
public interface PaqueteSincronizacionDao extends GenericDao {

    /**
     * Metodo para actualizar el hash
     *
     * @param hash
     */
    void actualizarHash(String hash);

    /**
     * Obtener paquetes por sincronizador
     *
     * @param sincronizadores
     * @return
     */
    List<PaqueteSincronizacionEntidad> getPaquetesBySincronizador(List<SincronizadorEntidad> sincronizadores);

    /**
     * Obtener por nombre
     *
     * @param paquetes
     * @return retorna una lista de PaqueteSincronizacionEntidad
     */
    List<PaqueteSincronizacionEntidad> loadByName(List<String> paquetes);

    /**
     * Obtener por estado a principal
     *
     * @param paquetes
     * @param estadoSync
     * @return retorna una lista de PaqueteSincronizacionEntidad
     */
    List<PaqueteSincronizacionEntidad> loadByEstadoToPrincipal(List<String> paquetes,
                                                               EstadoSincronizacionEntidad estadoSync);

    /**
     * Obtener por estado a principal
     *
     * @param paquetes original
     * @return subconjunto de paquetes
     */
    List<String> loadPaqueteNoEnDB(List<String> paquetes);
    
    /**
     * Obtener paquetes de difusion pendientes 
     * @param nombrePaquete nombre del paquete
     * @return subconjuntos de paquetes
     */
    List<PaqueteSincronizacionEntidad> getPaquetesDifusionPendientes(String nombrePaquete);
    
    /**
     * @description: obtener los paquetes ubicados en el in sin procesar y ordenados por pk
     * @param nombresPaquetes
     * @param idSincronizador
     * @return retorna una lista de PaqueteSincronizacionEntidad
     */
    List<PaqueteSincronizacionEntidad> getOrderedUnprocessesPackages(List<String> nombresPaquetes, long idSincronizador);
    
    /**
     * 
     * @param nombrePaquete
     * @param idSincronizador
     * @return retorna una lista de PaqueteSincronizacionEntidad
     */
    PaqueteSincronizacionEntidad loadPaqByNameAndNameSync(String nombrePaquete, Long idSincronizador);
    
    /**
     * @description: obtener paquetes filtrado por nombre y id sincronizador
     * @param nombresPaquetes
     * @param idSincronizador
     * @return retorna una lista de PaqueteSincronizacionEntidad
     */
    List<PaqueteSincronizacionEntidad> loadPaqByNamesAndIdSinc(List<String> nombresPaquetes, Long idSincronizador);
    
    /**
     * @description: obtener paquetes filtrado por nombre y id sincronizador
     * @param nombresPaquetes
     * @param idSincronizador
     * @return retorna una lista de PaqueteSincronizacionEntidad
     */
	List<PaqueteSincronizacionEntidad> loadPaqOrderTime(
			List<String> hashes);
	
	void saveArmarPaquete(VersionSincronizacionEntidad version, EstadoSincronizacionEntidad status, String key, String hash, String mySync);

}
