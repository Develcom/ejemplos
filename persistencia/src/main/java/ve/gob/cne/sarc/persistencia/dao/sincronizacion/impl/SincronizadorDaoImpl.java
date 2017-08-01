package ve.gob.cne.sarc.persistencia.dao.sincronizacion.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.sincronizacion.SincronizadorDao;
import ve.gob.cne.sarc.persistencia.entidades.SincronizadorEntidad;

/**
 * @author HiSoft
 */
@Repository("sincronizadorDao")
public class SincronizadorDaoImpl extends GenericDaoImpl implements SincronizadorDao {

    @Override
    public SincronizadorEntidad load(String nombre) {

        Criteria criteria = getCriteria(SincronizadorEntidad.class);
        criteria.add(Restrictions.eq("nombre", nombre));
        return (SincronizadorEntidad) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SincronizadorEntidad> findByNombres(List<String> nombres) {
        Criteria criteria = getCriteria(SincronizadorEntidad.class);
        criteria.add(Restrictions.in("nombre", nombres));
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<SincronizadorEntidad> cargarSynPorGeo(Collection<Long> idGeograficos) {
        Criteria criteria = getCriteria(SincronizadorEntidad.class);
        criteria.createCriteria("oficina", "oficina");
        criteria.add(Restrictions.in("oficina.geografico", idGeograficos));

        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<SincronizadorEntidad> cargarPadres() {
        Criteria criteria = getCriteria(SincronizadorEntidad.class);
        criteria.add(Restrictions.isNotEmpty("dependientes"));

        return criteria.list();
    }

    @Override
    public void deleteAllRelation() {
        final Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery("delete from C078T_PASARELA").executeUpdate();
    }
    
    /**
     * 
     * @param cod
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getOfficesDependant(String cod) {
    	
        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append(" select sync.idSincronizador, sync.oficina.nombre, sync.oficina.codigo");
        hqlQuery.append(" from SincronizadorEntidad as sync");
        
        hqlQuery.append(" where sync.pasarela is null ");
        
        hqlQuery.append(" and sync.oficina.id in ");
        
        hqlQuery.append(" ( select ofi.id ");
        hqlQuery.append(" from OficinaEntidad as ofi ");
        hqlQuery.append(" where ofi.codigo like :cod ");
        hqlQuery.append(" and (ofi.oficinaConexion.id is null ");
        hqlQuery.append(" or ofi.oficinaConexion.nombreOficConexion = 'FUERA DE LINEA' ) ) ");  

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameter("cod", '%'+cod+'%');
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
       
        return query.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> loadConectedOffices(String cod) {

        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append(" select sinc.idSincronizador, sinc.oficina.nombre, sinc.oficina.codigo ");
        hqlQuery.append(" from SincronizadorEntidad as sinc ");
        hqlQuery.append(" where ( sinc.oficina.oficinaConexion.id is null ");
        hqlQuery.append(" or sinc.oficina.oficinaConexion.nombreOficConexion = 'EN LINEA' ) ");
        hqlQuery.append(" and  sinc.oficina.codigo like :cod");
        
        Query query = sessionFactory.getCurrentSession().createQuery(
                hqlQuery.toString());
        query.setParameter("cod", '%'+cod+'%');
        
        return query.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> loadDependantByIdConected(long id){
        
        if(id <= 0 )
            return new ArrayList<>();
        
        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append(" select sinc.dependientes ");
        hqlQuery.append(" from SincronizadorEntidad as sinc ");
        hqlQuery.append(" where sinc.idSincronizador = :id ");
        
        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameter("id", id);
        
        if(query.list().isEmpty())
            return new ArrayList<>();
        
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> data;
        
        for(SincronizadorEntidad sinc : (List<SincronizadorEntidad>)query.list()){
            data  = new HashMap<>();
            data.put("2", sinc.getOficina().getCodigo());
            data.put("1", sinc.getOficina().getNombre());
            data.put("0", sinc.getIdSincronizador());
            result.add(data);
        }
        return result;
    }
}
