package ve.gob.cne.sarc.persistencia.dao.administracion.oficina.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.administracion.oficina.GeograficoDao;
import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.entidades.GeograficoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.GeograficoPaisEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoGeograficoEntidad;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author HiSoft
 */
@Repository("geograficoDao")
public class GeograficoDaoImpl extends GenericDaoImpl implements GeograficoDao {
    
    private static final String TIPOGEOGRAFICO = "tipoGeografico";
    private static final String NUPADRE = "nuPadreGeografico";
    private static final String FECHAFIN = "fechaFin";
    private static final String DESCRIPCIONTIPOGEOGRAFICO = "tipoGeografico.txDescripcion";

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GeograficoEntidad> cargarPorEstadoDependencias() {
        Criteria criteria = getCriteria(GeograficoEntidad.class);
        criteria.createCriteria(TIPOGEOGRAFICO, TIPOGEOGRAFICO);
        Criterion c1 = Restrictions.eq(DESCRIPCIONTIPOGEOGRAFICO, "Estado");
        Criterion c2 = Restrictions.eq(DESCRIPCIONTIPOGEOGRAFICO, "Dependencia Federal");
        criteria.add(Restrictions.or(c1, c2));

        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GeograficoEntidad> cargarHijos(GeograficoEntidad geograficoEntidad) {
        Criteria criteria = getCriteria(GeograficoEntidad.class);
        criteria.add(Restrictions.eq(NUPADRE, geograficoEntidad.getId()));
        criteria.add(Restrictions.or(Restrictions.isNull(FECHAFIN),
                Restrictions.gt(FECHAFIN, new Date())));
        return criteria.list();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<GeograficoEntidad> cargarEstadosHijos(GeograficoEntidad geograficoEntidad) {
        Criteria criteria = getCriteria(GeograficoEntidad.class);
        TipoGeograficoEntidad tP = new TipoGeograficoEntidad();
        tP.setId(2);
        criteria.add(Restrictions.and(Restrictions.eq(NUPADRE, geograficoEntidad.getId()),
                Restrictions.eq(TIPOGEOGRAFICO, tP),Restrictions.or(Restrictions.isNull(FECHAFIN),
                Restrictions.gt(FECHAFIN, new Date()))));
        return criteria.list();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<GeograficoEntidad> cargarMunicipiosHijos(GeograficoEntidad geograficoEntidad) {
        Criteria criteria = getCriteria(GeograficoEntidad.class);
        TipoGeograficoEntidad tP = new TipoGeograficoEntidad();
        tP.setId(3);
        criteria.add(Restrictions.and(Restrictions.eq(NUPADRE, geograficoEntidad.getId()),
                Restrictions.eq(TIPOGEOGRAFICO, tP),Restrictions.or(Restrictions.isNull(FECHAFIN),
                Restrictions.gt(FECHAFIN, new Date()))));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <P> List<GeograficoEntidad> cargarGeograficosOrdenados(String columnName, P p) {
        Criteria criteria = getCriteria(GeograficoEntidad.class);
        criteria.add(Restrictions.eq(columnName, p));
        criteria.add(Restrictions.or(Restrictions.isNull(FECHAFIN),
                Restrictions.gt(FECHAFIN, new Date())));
        return (List<GeograficoEntidad>) criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <P> List<GeograficoPaisEntidad> cargarPaisesOrdenados(String columnName, P p) {
        Criteria criteria = getCriteria(GeograficoPaisEntidad.class);
        criteria.add(Restrictions.eq(columnName, p));
        criteria.add(Restrictions.or(Restrictions.isNull(FECHAFIN),
                Restrictions.gt(FECHAFIN, new Date())));
        return (List<GeograficoPaisEntidad>) criteria.list();
    }
}
