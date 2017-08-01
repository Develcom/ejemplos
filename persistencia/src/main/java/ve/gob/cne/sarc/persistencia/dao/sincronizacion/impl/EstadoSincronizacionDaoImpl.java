package ve.gob.cne.sarc.persistencia.dao.sincronizacion.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.sincronizacion.EstadoSincronizacionDao;
import ve.gob.cne.sarc.persistencia.entidades.EstadoSincronizacionEntidad;

/**
 * @author HiSoft
 */
@Repository("estadoSincronizacionDao")
public class EstadoSincronizacionDaoImpl extends GenericDaoImpl implements EstadoSincronizacionDao {

    @Override
    public EstadoSincronizacionEntidad loadByCode(String codigo) {

        Criteria criteria = getCriteria(EstadoSincronizacionEntidad.class);
        criteria.add(Restrictions.eq("codigo", codigo));
        return (EstadoSincronizacionEntidad) criteria.uniqueResult();
    }
}