package ve.gob.cne.sarc.persistencia.dao.administracion.rol.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.administracion.rol.PermisologiaDao;
import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.entidades.ModuloSeguridadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PermisoRecursoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PermisologiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoPermisoEntidad;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author HiSoft
 */
@Repository("permisologiaDao")
public class PermisologiaDaoImpl extends GenericDaoImpl implements PermisologiaDao {
    

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final Class<T> type, final Serializable id) {
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }
    @Override
    @SuppressWarnings("unchecked")
    public Collection<PermisologiaEntidad> load(ModuloSeguridadEntidad moduloSeguridad,
                                                TipoPermisoEntidad tipoPermisoEntidad) {
        Criteria criteria = getCriteria(PermisologiaEntidad.class);
        Criterion c1 = Restrictions.eq("coTipoPermiso", tipoPermisoEntidad);
        Criterion c2 = Restrictions.eq("coModulo", moduloSeguridad);
        criteria.add(Restrictions.and(c1, c2));

        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<PermisoRecursoEntidad> load(PermisologiaEntidad permisologia) {
        Criteria criteria = getCriteria(PermisoRecursoEntidad.class);
        criteria.add(Restrictions.eq("coPermisologia", permisologia));

        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getNextGroup() {
        long i = 0;
        Criteria criteria = getCriteria(PermisoRecursoEntidad.class);
        criteria.addOrder(Order.desc("grupoRecurso")); 
        List<PermisoRecursoEntidad> list = criteria.list();
        if (!list.isEmpty()) {
            i = list.iterator().next().getGrupoRecurso().longValue();
        }
        return (int) (i + 1);
    }
}
