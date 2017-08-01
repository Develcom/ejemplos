package ve.gob.cne.sarc.persistencia.dao.sincronizacion.impl;

import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;
import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.sincronizacion.EstatusPaqueteDao;

import java.util.List;
import java.util.Map;

/**
 * @author Hisoft
 */
@Repository("estatusPaqueteDao")
public class EstatusPaqueteDaoImpl extends GenericDaoImpl implements EstatusPaqueteDao {

    private static final String ESTATUS = "idEstatus";
    private static final String OFICINA = "nombreOficina";
    private static final String PAQUETE = "nombrePaquete";

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> estatusPaquetes(Map<String, Object> params) {
        /**
         * QUERY Original para comparar
         *
         select ofc.nb_oficina,pqs.nb_nombre,edo_sync.tx_descripcion
         from synci.c077t_version_sync_estado vse2
         join synci.k005t_sincronizador as sync
         on(sync.id_sincronizador=vse2.id_sincronizador)
         join synci.c075t_paquete_sincronizacion as pqs
         on(pqs.id_sincronizador=sync.id_sincronizador
         and vse2.id_sincronizador=pqs.id_sincronizador
         and vse2.id_version=pqs.id_version)
         join synci.i031t_estado_sincronizacion as edo_sync
         on(edo_sync.id_estado_sincronizacion=vse2.id_estado_sync)
         join synci.c067t_oficina as ofc
         on (ofc.co_oficina = sync.id_oficina)
         where (vse2.id_sincronizador, vse2.id_version,fe_fecha) in (
         select vse1.id_sincronizador, vse1.id_version, max(vse1.fe_fecha) as fe_fecha
         from synci.c077t_version_sync_estado as vse1
         group by vse1.id_sincronizador, vse1.id_version)

         and ofc.nb_oficina LIKE '%?%'
         and pqs.nb_nombre LIKE '%?%'
         and edo_sync.id_estado_sincronizacion = ?
         */

        Long idEstatus = (Long) params.get(ESTATUS);
        String nombreOficina = (String) params.get(OFICINA);
        String nombrePaquete = (String) params.get(PAQUETE);

        StringBuilder sb = new StringBuilder();
        sb.append("select ofc.nombre, pqs.nombre, edo_sync.descripcion ");
        sb.append("from VersionSincronizadorEstadoEntidad as vse2, SincronizadorEntidad as sync, ");
        sb.append("PaqueteSincronizacionEntidad as pqs, EstadoSincronizacionEntidad as edo_sync, ");
        sb.append("OficinaEntidad as ofc ");
        sb.append("where sync.idSincronizador = vse2.sincronizador.idSincronizador ");
        sb.append("and pqs.sincronizador.idSincronizador = sync.idSincronizador ");
        sb.append("and vse2.sincronizador.idSincronizador = pqs.sincronizador.idSincronizador ");
        sb.append("and vse2.version.idVersion = pqs.version.idVersion ");
        sb.append("and edo_sync.idEstado = vse2.estadoSync.idEstado ");
        sb.append("and ofc.id = sync.oficina.id ");
        sb.append("and (vse2.sincronizador.idSincronizador, vse2.version.idVersion, vse2.fecha) in (");
        sb.append("select vse1.sincronizador.idSincronizador, vse1.version.idVersion, max(vse1.fecha) as fecha ");
        sb.append("from VersionSincronizadorEstadoEntidad as vse1 ");
        sb.append("group by vse1.sincronizador.idSincronizador, vse1.version.idVersion) ");
        sb.append(" and ofc.nombre LIKE '%").append(nombreOficina).append("%' ");
        sb.append(" and pqs.nombre LIKE '%").append(nombrePaquete).append("%' ");

        if (idEstatus > 0) {
            sb.append(" and edo_sync.idEstado = ").append(idEstatus);
        }

        Query s = sessionFactory.getCurrentSession().createQuery(sb.toString());

        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return s.list();
    }
}
