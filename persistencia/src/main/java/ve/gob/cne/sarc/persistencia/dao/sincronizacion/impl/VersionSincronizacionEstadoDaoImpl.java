package ve.gob.cne.sarc.persistencia.dao.sincronizacion.impl;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.sincronizacion.VersionSincronizadorEstadoDao;
import ve.gob.cne.sarc.persistencia.entidades.EstadoSincronizacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.VersionSincronizadorEstadoEntidad;

import java.util.List;

/**
 * @author HiSoft
 */
@Repository("versionSincronizadorEstadoDao")
public class VersionSincronizacionEstadoDaoImpl extends GenericDaoImpl implements VersionSincronizadorEstadoDao {

    @Override
    public List<VersionSincronizadorEstadoEntidad> loadInState(EstadoSincronizacionEntidad estadoSync) {
        final Session session = sessionFactory.getCurrentSession();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM c072t_version_sync_estado as vse");
        sb.append(" WHERE vse.id_estado_sync = ").append(estadoSync.getIdEstado());
        sb.append(" AND ((SELECT count(1) FROM c072t_version_sync_estado as vse2");
        sb.append(" WHERE vse2.id_estado_sync > ").append(estadoSync.getIdEstado());
        sb.append(" AND vse.id_version = vse2.id_version");
        sb.append(" AND vse.id_sincronizador = vse2.id_sincronizador) = 0)");

        SQLQuery query = session.createSQLQuery(sb.toString());

        query.addEntity(VersionSincronizadorEstadoEntidad.class);
        return query.list();
    }
}
