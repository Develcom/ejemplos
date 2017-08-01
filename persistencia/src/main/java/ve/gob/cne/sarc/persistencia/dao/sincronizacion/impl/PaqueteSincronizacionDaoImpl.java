package ve.gob.cne.sarc.persistencia.dao.sincronizacion.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.sincronizacion.PaqueteSincronizacionDao;
import ve.gob.cne.sarc.persistencia.entidades.EstadoSincronizacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteSincronizacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SincronizadorEntidad;
import ve.gob.cne.sarc.persistencia.entidades.VersionSincronizacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.VersionSincronizadorEstadoEntidad;

/**
 * @author HiSoft<
 */
@Repository("paqueteSincronizacionDao")
@Transactional
public class PaqueteSincronizacionDaoImpl extends GenericDaoImpl implements PaqueteSincronizacionDao {
    
    private static final String KEY_ID_SINC = "idSincronizador";

    @Override
    public void actualizarHash(String hash) {
        sessionFactory.getCurrentSession().createSQLQuery("").setParameter("hash", hash);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaqueteSincronizacionEntidad> getPaquetesBySincronizador(
            List<SincronizadorEntidad> sincronizadores) {

        Criteria criteria = getCriteria(PaqueteSincronizacionEntidad.class);
        criteria.add(Restrictions.in("sincronizador", sincronizadores));

        List<PaqueteSincronizacionEntidad> paquetes = criteria.list();

        if (paquetes != null) {
            for (PaqueteSincronizacionEntidad paq : paquetes) {
                Hibernate.initialize(paq.getSincronizador());
                Hibernate.initialize(paq.getVersion());
            }
        }

        return paquetes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaqueteSincronizacionEntidad> loadByName(List<String> paquetes) {
        Criteria criteriaPaquete = getCriteria(PaqueteSincronizacionEntidad.class);
        criteriaPaquete.add(Restrictions.in("nombre", paquetes.toArray()));
        return criteriaPaquete.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaqueteSincronizacionEntidad> loadByEstadoToPrincipal(List<String> paquetes,
                                                                      EstadoSincronizacionEntidad estadoSync) {

        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append("select p ");
        hqlQuery.append("from PaqueteSincronizacionEntidad as p, VersionSincronizadorEstadoEntidad as vse ");
        hqlQuery.append("where p.sincronizador = vse.sincronizador ");
        hqlQuery.append("and p.version = vse.version ");
        hqlQuery.append("and vse.estadoSync = :estado ");
        hqlQuery.append("and p.nombre in (:paquetes)");
        hqlQuery.append("and p.desdePrincipal = false");

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameter("estado", estadoSync);
        query.setParameterList("paquetes", paquetes);
        List<PaqueteSincronizacionEntidad> lazy = (List<PaqueteSincronizacionEntidad>) query.list();
        for (PaqueteSincronizacionEntidad p : lazy) {
            Hibernate.initialize(p.getSincronizador());
            Hibernate.initialize(p.getVersion());
        }
        return lazy;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> loadPaqueteNoEnDB(List<String> paquetes) {
        StringBuilder query = new StringBuilder();
        query.append("select p.nombre from PaqueteSincronizacionEntidad p where p.nombre in (:paquetes)");
        Query q = sessionFactory.getCurrentSession().createQuery(query.toString());
        q.setParameterList("paquetes", paquetes);
        List<String> listResult = (List<String>) q.list();
        paquetes.removeAll(listResult);
        return paquetes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaqueteSincronizacionEntidad> getPaquetesDifusionPendientes(
            String nombrePaquete) {
        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append(" select pqs.id,pqs.desdePrincipal,pqs.hash,pqs.nombre,pqs.sincronizador.idSincronizador,pqs.version.idVersion ");
        hqlQuery.append(" from SincronizadorEntidad as sync,VersionSincronizadorEstadoEntidad as vse2, PaqueteSincronizacionEntidad as pqs,EstadoSincronizacionEntidad as es ");
        hqlQuery.append(" where vse2.sincronizador.idSincronizador=sync.idSincronizador and ");
        hqlQuery.append(" pqs.sincronizador.idSincronizador = sync.idSincronizador and ");
        hqlQuery.append(" vse2.sincronizador.idSincronizador=pqs.sincronizador.idSincronizador and ");
        hqlQuery.append(" vse2.version.idVersion=pqs.version.idVersion and ");
        hqlQuery.append(" pqs.nombre=:nombrePaquete");
        hqlQuery.append(" and vse2.estadoSync.idEstado=es.idEstado and es.codigo='P' ");
        hqlQuery.append(" and (vse2.sincronizador.idSincronizador, vse2.version.idVersion, vse2.fecha) in ( ");
        hqlQuery.append(" select vse1.sincronizador.idSincronizador,");
        hqlQuery.append(" vse1.version.idVersion, max(vse1.fecha) as fecha ");
        hqlQuery.append(" from VersionSincronizadorEstadoEntidad as vse1 ");
        hqlQuery.append(" group by vse1.sincronizador.idSincronizador, vse1.version.idVersion) ");

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameter("nombrePaquete", nombrePaquete);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PaqueteSincronizacionEntidad> getOrderedUnprocessesPackages(
            List<String> nombresPaquetes, long idSincronizador) {
        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append(" select pqs ");
        hqlQuery.append(" from SincronizadorEntidad as sync, VersionSincronizadorEstadoEntidad as vse2, PaqueteSincronizacionEntidad as pqs ");
        hqlQuery.append(" where vse2.sincronizador.idSincronizador = sync.idSincronizador and ");
        hqlQuery.append(" pqs.sincronizador.idSincronizador = :idSincronizador and ");
        hqlQuery.append(" vse2.sincronizador.idSincronizador = pqs.sincronizador.idSincronizador and ");
        hqlQuery.append(" vse2.version.idVersion = pqs.version.idVersion and ");
        hqlQuery.append(" pqs.nombre in (:nombresPaquetes)");
        hqlQuery.append(" and vse2.estadoSync.idEstado not in (5,4) ");
        hqlQuery.append(" and (vse2.sincronizador.idSincronizador, vse2.version.idVersion, vse2.fecha) in ( ");
        hqlQuery.append(" select vse1.sincronizador.idSincronizador,");
        hqlQuery.append(" vse1.version.idVersion, max(vse1.fecha) as fecha ");
        hqlQuery.append(" from VersionSincronizadorEstadoEntidad as vse1 ");
        hqlQuery.append(" group by vse1.sincronizador.idSincronizador, vse1.version.idVersion) ");
        hqlQuery.append(" order by pqs.fechaInicio ");

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameterList("nombresPaquetes", nombresPaquetes);
        query.setParameter(KEY_ID_SINC, idSincronizador);
        
        List<PaqueteSincronizacionEntidad> lazy = (List<PaqueteSincronizacionEntidad>) query.list();
        for (PaqueteSincronizacionEntidad p : lazy) {
            Hibernate.initialize(p.getSincronizador());
            Hibernate.initialize(p.getVersion());
        }
        return lazy;
    }
    
    @Override
    public PaqueteSincronizacionEntidad loadPaqByNameAndNameSync(String nombrePaquete, Long idSincronizador) {
        
        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append(" select pqs ");
        hqlQuery.append(" from PaqueteSincronizacionEntidad as pqs ");
        hqlQuery.append(" where pqs.nombre = :nombrePaquete ");
        hqlQuery.append(" and pqs.sincronizador.idSincronizador = :idSincronizador ");
        
        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameter("nombrePaquete", nombrePaquete);
        query.setParameter(KEY_ID_SINC, idSincronizador);
        
        return (PaqueteSincronizacionEntidad) query.uniqueResult();
    }

    @Override
    public List<PaqueteSincronizacionEntidad> loadPaqByNamesAndIdSinc(
            List<String> nombresPaquetes, Long idSincronizador) {
        
        StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append(" select paqs ");
        hqlQuery.append(" from PaqueteSincronizacionEntidad as paqs ");
        hqlQuery.append(" where paqs.nombre in (:nombresPaquetes) ");
        hqlQuery.append(" and paqs.sincronizador.idSincronizador = :idSincronizador ");
        hqlQuery.append(" and paqs.desdePrincipal = true");

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameterList("nombresPaquetes", nombresPaquetes);
        query.setParameter(KEY_ID_SINC, idSincronizador);
        
        @SuppressWarnings("unchecked")
        List<PaqueteSincronizacionEntidad> lazy = (List<PaqueteSincronizacionEntidad>) query.list();
        for (PaqueteSincronizacionEntidad p : lazy) {
            Hibernate.initialize(p.getSincronizador());
            Hibernate.initialize(p.getVersion());
        }
        return lazy;
    }
    
    @Override
    public void saveArmarPaquete(VersionSincronizacionEntidad version, EstadoSincronizacionEntidad status, String key, String hash, String mySync){
    	
    	StringBuilder hqlQuery = new StringBuilder();
    	hqlQuery.append(" select sincs.idSincronizador ");
    	hqlQuery.append(" from SincronizadorEntidad as sincs ");
    	hqlQuery.append(" where sincs.nombre != :mySync");

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameter("mySync", mySync);
        
        int cont = 0;
        for (Long object : (List<Long>) query.list()){
        	SincronizadorEntidad sync = new SincronizadorEntidad();
        	sync.setIdSincronizador(object);
        	PaqueteSincronizacionEntidad paq = new PaqueteSincronizacionEntidad(sync, version, key, hash, false);
        	sessionFactory.getCurrentSession().save(paq);
        	
        	VersionSincronizadorEstadoEntidad versynces = new VersionSincronizadorEstadoEntidad();
            Date today = new Date();
            versynces.setFecha(today);
            versynces.setSincronizador(sync);
            versynces.setVersion(version);
            versynces.setEstadoSync(status);
            sessionFactory.getCurrentSession().save(versynces);
            
            /*if ( cont % 5 == 0 ) { //5, same as the JDBC batch size
                //flush a batch of inserts and release memory:
            	sessionFactory.getCurrentSession().flush();
            	sessionFactory.getCurrentSession().clear();
            }*/
            cont++;
        }
    }

	@Override
    public List<PaqueteSincronizacionEntidad> loadPaqOrderTime(List<String> hashes) {
        
    	Criteria criteria = getCriteria(PaqueteSincronizacionEntidad.class);
        criteria.add(Restrictions.in("hash", hashes));
        criteria.addOrder(Order.asc("fechaInicio"));
        List<PaqueteSincronizacionEntidad> paquetes = criteria.list();

        if (paquetes != null) {
            for (PaqueteSincronizacionEntidad paq : paquetes) {
                Hibernate.initialize(paq.getSincronizador());
                Hibernate.initialize(paq.getVersion());
            }
        }

        return paquetes;
    }
}
