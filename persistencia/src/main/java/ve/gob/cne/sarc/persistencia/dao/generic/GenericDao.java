package ve.gob.cne.sarc.persistencia.dao.generic;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import ve.gob.cne.sarc.persistencia.exceptions.CustomException;

/**
 * Template factory for Generic CRUD Operations
 *
 * @author HiSoft
 */

public interface GenericDao {

    /**
     * Metodo para guardar en bd
     *
     * @param o
     * @return
     */
    public <T> T save(final T o);

    /**
     * Metodo para eliminar en bd
     *
     * @param object
     * @return
     */
    public <T> Boolean delete(final T object);

    /**
     * Metodo para obtener de bd
     *
     * @param type
     * @param id
     * @return
     */
    public <T> T get(final Class<T> type, final Serializable id);

    /**
     * Metodo para actualizar o guardar en bd
     *
     * @param o
     */
    public <T> void saveOrUpdate(final T o);

    /**
     * Metodo para obtener una lista de la bd
     *
     * @param type
     * @return
     */
    public <T> List<T> all(final Class<T> type);

    /**
     * metodo para incertar por lotes
     *
     * @param type
     * @param entities
     * @return
     */
    public <T> List<T> batchInsert(final Class<T> type, List<T> entities);

    /**
     * @param type       the type of class to load
     * @param columnName the column to consult in the query
     * @param p          the value to consult in the query
     * @return a objetc (the queried object).
     */
    public <T, P> T load(final Class<T> type, String columnName, P p);

    /**
     * @param type
     * @param columnName
     * @param p
     * @return
     */
    public <T, P> List<T> loadList(final Class<T> type, String columnName, P p);

    /**
     * @param type
     * @param column
     * @return
     */
    public <T> List<T> allOrdered(final Class<T> type, String column);

    /**
     * @param objects
     * @param className
     * @throws Exception
     */
    public <T> void bulkUpdate(List<T> objects, String className) throws CustomException;

    /**
     * Método para eliminar una o más entidades
     *
     * @param type
     * @param columnName
     * @param objects
     */
    public <T, P> void deleteFrom(final Class<T> type, String columnName, List<P> objects);

    /**
     * Metodo para consultar una lista dada una lista
     *
     * @param type
     * @param columnName
     * @param listOfValues
     * @return
     */
    public <T, P> List<T> loadByList(Class<T> type, String columnName, List<P> listOfValues);
    
    /**
     * Metodo para consultar una lista dada un arreglo
     *
     * @param type
     * @param columnName
     * @param listOfValues
     * @return
     */
    public <T, P> List<T> loadByList(Class<T> type, String columnName, P[] listOfValues);
    
    /**
     * Retorna la Session de Hibernate
     * @return
     */
    Session getSession();
}
