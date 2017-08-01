package ve.gob.cne.sarc.persistencia.dao.sincronizacion.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.sincronizacion.VersionBitacoraSincronizadorDao;
import ve.gob.cne.sarc.persistencia.entidades.BitacoraEntidad;

import java.util.*;

/**
 * @author HiSoft
 */
@Repository("versionBitacoraSincronizadorDao")
public class VersionBitacoraSincronizadorDaoImpl extends GenericDaoImpl implements VersionBitacoraSincronizadorDao {

    private static final String IDTAREA = "idTarea";
    private static final String DIFUSION = "difusion";

    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String, Object>> buscarPaquetes() {

        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> mapList = consultaInterna();
        Set<Long> idsTarea = new HashSet<>();
        for (Map<String, Object> map : mapList) {
            idsTarea.add(Long.valueOf(String.valueOf(map.get(IDTAREA))));
        }
        Collection<BitacoraEntidad> bitacoraEntidads = null;
        if (!idsTarea.isEmpty()) {
            Criteria crit = getCriteria(BitacoraEntidad.class);
            crit.add(Restrictions.in(IDTAREA, idsTarea));
            bitacoraEntidads = crit.list();
        }
        if (bitacoraEntidads != null && !bitacoraEntidads.isEmpty()) {
            for (BitacoraEntidad bitacoraEntidad : bitacoraEntidads) {
                long idTarea = bitacoraEntidad.getIdTarea();
                loadMap(mapList, bitacoraEntidad, idTarea, result);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Collection<Map<String, Object>> buscarPaquetesDifusion() {

        Map<Long, Map<String, Object>> result = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct vbse.version.idVersion, vbse.version.codigo, vbse.bitacora.data, vbse.bitacora.executor, vbse.bitacora.hash, vbse.bitacora.idTarea, vbse.bitacora.fechaInicio ");
        sb.append("from VersionBitacoraSincronizadorEntidad as vbse ");
        sb.append("where vbse.version.difusion = true and vbse.version.idVersion not in ( select distinct paq.version.idVersion from PaqueteSincronizacionEntidad as paq) ");
        sb.append("order by vbse.bitacora.fechaInicio ASC");
        Query s = sessionFactory.getCurrentSession().createQuery(sb.toString());
        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        Collection<Map<String, Object>> lista = s.list();
        
        Map<String, Boolean> keeper = new HashMap<>();

        if (!lista.isEmpty()) {
            
            Long cont=0L;
            
            for (Map<String, Object> map : lista) {
            	
            	if( !keeper.containsKey(map.get("5").toString()) ){
            		keeper.put(map.get("5").toString(), true);
            		result.put(++cont, map);
            	} 
            }
        }

        return result.values();
    }

    @SuppressWarnings("unchecked")
	private List<Map<String, Object>> consultaInterna() {
        StringBuilder sb = new StringBuilder();

        sb.append("select vbse.sincronizador.idSincronizador as idSincronizador, ");
        sb.append("vbse.version.idVersion as idVersion, vbse.bitacora.idTarea as idTarea, vbse.bitacora.fechaInicio as fechaInicio, se.nombre as nombre, ");
        sb.append("vse.codigo as codigo, vse.difusion as difusion from SincronizadorEntidad as se, ");
        sb.append("VersionBitacoraSincronizadorEntidad as vbse, VersionSincronizacionEntidad as vse ");
        sb.append("where se = vbse.sincronizador and vbse.version = vse ");
        sb.append("and (vbse.sincronizador, ");
        sb.append("vbse.version) not in (");
        sb.append("select pse.sincronizador, pse.version ");
        sb.append("from SincronizadorEntidad as se1, ");
        sb.append("PaqueteSincronizacionEntidad as pse, VersionSincronizacionEntidad as vse1 ");
        sb.append("where se1 = pse.sincronizador and ");
        sb.append("pse.version = vse1");
        sb.append(") and vse.difusion = :difusion ");
        sb.append("order by fechaInicio ASC");

        Query s = sessionFactory.getCurrentSession().createQuery(sb.toString()).setParameter(DIFUSION, false);
        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return s.list();

    }

    private static void loadMap(List<Map<String, Object>> mapList, BitacoraEntidad bitacoraEntidad,
                                long idTarea, List<Map<String, Object>> result) {
        for (Map<String, Object> map : mapList) {
            long idTareaInner = Long.parseLong(String.valueOf(map.get(IDTAREA)));
            if (idTarea == idTareaInner) {
                Map<String, Object> stringObjectMap = new HashMap<>();
                stringObjectMap.put("idSincronizador", map.get("idSincronizador"));
                stringObjectMap.put("idVersion", map.get("idVersion"));
                stringObjectMap.put("data", bitacoraEntidad.getData());
                stringObjectMap.put("executor", bitacoraEntidad.getExecutor());
                stringObjectMap.put("hash", bitacoraEntidad.getHash());
                stringObjectMap.put("nombre", map.get("nombre"));
                stringObjectMap.put("codigo", map.get("codigo"));
                stringObjectMap.put(DIFUSION, map.get(DIFUSION));
                result.add(stringObjectMap);
            }
        }

    }
}
