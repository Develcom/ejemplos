package ve.gob.cne.sarc.persistencia.dao.administracion.rol.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.administracion.rol.RolDao;
import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.entidades.PermisologiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.RolEntidad;
import ve.gob.cne.sarc.persistencia.entidades.RolOperadorEntidad;
import ve.gob.cne.sarc.persistencia.entidades.RolUsuarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;

/**
 * @author HiSoft
 */
@Repository("rolDao")
public class RolDaoImpl extends GenericDaoImpl implements RolDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<RolEntidad> loadRolsView(List<Long> rolesId) {
    	
    	StringBuilder hqlQuery = new StringBuilder();
		
        hqlQuery.append(" select rol ");
        hqlQuery.append(" from RolEntidad as rol ");
        hqlQuery.append(" where rol.id in (:rolesId) ");
        hqlQuery.append(" order by rol.id ");

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameterList("rolesId", rolesId);
        
        return (List<RolEntidad>)query.list();
    }
    
    @Override
	public List<RolEntidad> loadRolsFromBD(UsuarioEntidad usuario) {
    	
    	List<RolEntidad> listaRol = listaRolUsuario(usuario);
    	
    	if(!listaRol.isEmpty()){
    		StringBuilder hqlQuery = new StringBuilder();
    		
    		hqlQuery.append(" select distinct rol ");
    		hqlQuery.append(" from RolEntidad as rol ");
    		hqlQuery.append(" where rol in (:listaRol) ");
    		hqlQuery.append(" order by rol.id ");
    		
    		Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
    		query.setParameterList("listaRol", listaRol);
    	}
    	
		return listaRol;
	}
    
    @SuppressWarnings("unchecked")
	private List<RolEntidad> listaRolUsuario (UsuarioEntidad usuario){
    	
    	StringBuilder hqlQuery = new StringBuilder();
		
        hqlQuery.append(" select rolU.rol ");
        hqlQuery.append(" from RolUsuarioEntidad as rolU ");
        hqlQuery.append(" where rolU.usuario = :usuario ");
        hqlQuery.append(" and rolU.fechaFin is null ");

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameter("usuario", usuario);
        
        return (List<RolEntidad>)query.list();
    }
    
    @Override
	public void deleteRols(Collection<RolEntidad> rolsToDelete, UsuarioEntidad usuario){
		
		Date currentDate = new Date();
		
		StringBuilder hqlQuery = new StringBuilder();
		
        hqlQuery.append(" update RolUsuarioEntidad ");
        hqlQuery.append(" set fechaFin = :currentDate ");
        hqlQuery.append(" where rol in (:rolsToDelete) ");
        hqlQuery.append(" and usuario = :usuario ");

        Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
        query.setParameter("currentDate", currentDate);
        query.setParameter("usuario", usuario);
        query.setParameterList("rolsToDelete", rolsToDelete);
        
        query.executeUpdate();
	}
    
    @Override
	public void insertRols(Collection<RolEntidad> rolsToInsert, UsuarioEntidad usuario) {

		for(RolEntidad rol : rolsToInsert){
			
			RolUsuarioEntidad rolUsuario = new RolUsuarioEntidad();
			
			rolUsuario.setFechaInicio(new Date());
			rolUsuario.setRol(rol);
			rolUsuario.setUsuario(usuario);
			
			sessionFactory.getCurrentSession().save(rolUsuario);
		}
	}

    @Override
    @SuppressWarnings("unchecked")
    public Collection<RolOperadorEntidad> load(RolEntidad rol, PermisologiaEntidad permisologia,
                                               String operador) {
        Criteria criteria = getCriteria(RolOperadorEntidad.class);
        Criterion c1 = Restrictions.eq("rolOperador", rol);
        Criterion c2 = Restrictions.eq("coPermisologia", permisologia);
        Criterion c1c2 = Restrictions.and(c1, c2);
        Criterion c3 = Restrictions.eq("operadorLogico", operador);
        Criterion c1c2c3 = Restrictions.and(c1c2, c3);
        criteria.add(c1c2c3);

        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<RolOperadorEntidad> load(RolEntidad rol) {
        Criteria criteria = getCriteria(RolOperadorEntidad.class);
        criteria.add(Restrictions.eq("rolOperador", rol));

        return criteria.list();
    }
    
}
