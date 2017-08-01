package ve.gob.cne.sarc.persistencia.dao.sincronizacion.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.sincronizacion.VersionSincronizacionDao;
import ve.gob.cne.sarc.persistencia.entidades.VersionSincronizacionEntidad;

/**
 * @author HiSoft
 */
@Repository("versionSincronizacionDao")
public class VersionSincronizacionDaoImpl extends GenericDaoImpl implements VersionSincronizacionDao {

    @Override
    public VersionSincronizacionEntidad getLastVersion() {
        Criteria criteria = getCriteria(VersionSincronizacionEntidad.class);

        criteria.addOrder(Order.desc("codigo"));
        criteria.setMaxResults(1);

        return (VersionSincronizacionEntidad) criteria.uniqueResult();
    }

    @Override
    public VersionSincronizacionEntidad loadByCode(Integer code) {

        Criteria criteria = getCriteria(VersionSincronizacionEntidad.class);
        criteria.add(Restrictions.eq("codigo", code));
        return (VersionSincronizacionEntidad) criteria.uniqueResult();
    }

}
