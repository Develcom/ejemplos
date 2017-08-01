package ve.gob.cne.sarc.persistencia.dao.administracion.oficina.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.administracion.oficina.OficinaDao;
import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.entidades.GeograficoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SincronizadorEntidad;

import java.util.Collection;
import java.util.Date;

/**
 * @author HiSoft
 */
@Repository("oficinaDao")
public class OficinaDaoImpl extends GenericDaoImpl implements OficinaDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(OficinaDaoImpl.class);

    @Override
    public void actualizarStatus(OficinaEntidad oficinaEntidad, OficinaEstatusEntidad oficinaEstatusEntidad) {
        if (oficinaEntidad.getOficinaEstatus().equals(oficinaEstatusEntidad)) 
            return;
        
        try {
            StringBuilder hqlUpdate = new StringBuilder();
            hqlUpdate.append("update OficinaEntidad e ");
            hqlUpdate.append("set e.oficinaEstatus = :oficinaEstatus ");
            hqlUpdate.append("where e =:objs");


            final Session session = sessionFactory.getCurrentSession();
            int updatedEntities = session.createQuery(hqlUpdate.toString())
                    .setParameter("oficinaEstatus", oficinaEstatusEntidad)
                    .setParameter("objs", oficinaEntidad)
                    .executeUpdate();
            LOGGER.info("updatedEntities = " + updatedEntities);
        } catch (Exception e) {
            LOGGER.error("Error en actualizarStatus: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void abrirCerrarOficina(OficinaEntidad oficinaEntidad, Date date, boolean b) {
        try {
            StringBuilder hqlUpdate = new StringBuilder();
            hqlUpdate.append("update OficinaEntidad e ");
            hqlUpdate.append("set e.fechaFin=:dateEnd ");
            if (!b)
                hqlUpdate.append(", e.fechaInicio=:dateIni ");

            hqlUpdate.append("where e =:objs");

            final Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(hqlUpdate.toString());
            if (b) 
                query.setParameter("dateEnd", date);
            else {
                query.setParameter("dateIni", date);
                query.setParameter("dateEnd", null);
            }
            query.setParameter("objs", oficinaEntidad);
            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error en abrirCerrarOficina: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<SincronizadorEntidad> cargarSynPorGeo(GeograficoEntidad geografico) {
        Criteria criteria = getCriteria(SincronizadorEntidad.class);
        criteria.createCriteria("oficina", "oficina");
        criteria.add(Restrictions.eq("oficina.geografico", geografico.getId()));

        return criteria.list();
    }

    @Override
    public SincronizadorEntidad obtenerUltimoSincronizador() {
        Criteria criteria = getCriteria(SincronizadorEntidad.class);
        criteria.addOrder(Order.desc("nombre"));
        criteria.setMaxResults(1);
        
        return (SincronizadorEntidad) criteria.uniqueResult();
    }
}
