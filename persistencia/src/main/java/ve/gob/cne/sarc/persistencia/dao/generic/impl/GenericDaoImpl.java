package ve.gob.cne.sarc.persistencia.dao.generic.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.exceptions.CustomException;

import javax.annotation.Resource;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author HiSoft
 */

@Repository("genericDao")
@Transactional
public class GenericDaoImpl implements GenericDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDaoImpl.class);

    @Resource(name = "sessionFactory")
    protected SessionFactory sessionFactory;

    @Override
    public <T> T save(final T o) {
        sessionFactory.getCurrentSession().save(o);
        return o;
    }

    @Override
    public <T> Boolean delete(final T object) {
        Boolean result = null;
        try {
            sessionFactory.getCurrentSession().delete(object);
            result = true;
        } catch (Exception e) {
            LOGGER.error("Error " + e.getMessage(), e);
            result = false;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final Class<T> type, final Serializable id) {
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }

    @Override
    public <T> void saveOrUpdate(final T o) {
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> all(final Class<T> type) {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria criteria = session.createCriteria(type);
        return criteria.list();
    }

    protected <T> Criteria getCriteria(final Class<T> type) {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(type);
    }

    @Override
    public <T> List<T> batchInsert(Class<T> type, List<T> entities) {
        final Session session = sessionFactory.getCurrentSession();

        for (T entity : entities) {
            session.save(entity);
        }

        return entities;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, P> T load(final Class<T> type, String columnName, P p) {
        Criteria criteria = getCriteria(type);
        criteria.add(Restrictions.eq(columnName, p));
        criteria.setMaxResults(1);
        return (T) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, P> List<T> loadList(final Class<T> type, String columnName, P p) {
        Criteria criteria = getCriteria(type);
        criteria.add(Restrictions.eq(columnName, p));
        return (List<T>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, P> List<T> loadByList(final Class<T> type, String columnName, List<P> listOfValues) {
        Criteria criteria = getCriteria(type);
        criteria.add(Restrictions.in(columnName, listOfValues.toArray()));
        return (List<T>) criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T, P> List<T> loadByList(final Class<T> type, String columnName, P[] listOfValues) {
        Criteria criteria = getCriteria(type);
        criteria.add(Restrictions.in(columnName, listOfValues));
        return (List<T>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> allOrdered(Class<T> type, String column) {
        Criteria criteria = getCriteria(type);
        criteria.add(Restrictions.or(Restrictions.isNull("fechaFin"),
                Restrictions.gt("fechaFin", new Date())));
        criteria.addOrder(Order.asc(column));

        return criteria.list();
    }

    @Override
    public <T> void bulkUpdate(List<T> objects, String className) throws CustomException{

        try {
            StringBuilder hqlUpdate = new StringBuilder();
            hqlUpdate.append("update ");
            hqlUpdate.append(className);
            hqlUpdate.append(" e set e.fechaFin=:today where e.id in (:objs)");

            List<Long> ids = new ArrayList<>();

            for (Object object : objects) {
                Method method = object.getClass().getMethod("getId");

                ids.add((Long) method.invoke(object));
            }

            Date today = new Timestamp(Calendar.getInstance().getTimeInMillis());

            final Session session = sessionFactory.getCurrentSession();
            session.createQuery(hqlUpdate.toString())
                    .setParameter("today", today).setParameterList("objs", ids)
                    .executeUpdate();

        } catch (Exception e) {
            throw new CustomException(e);
        }

    }

    @Override
    public <T, P> void deleteFrom(Class<T> type, String columnName,
                                  List<P> objects) {
        StringBuilder hqlDelete = new StringBuilder("delete from ");
        hqlDelete.append(type.getName());
        hqlDelete.append(" where ");
        hqlDelete.append(columnName);
        hqlDelete.append(" in :objs");

        final Session session = sessionFactory.getCurrentSession();
        session.createQuery(hqlDelete.toString())
                .setParameterList("objs", objects).executeUpdate();
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
