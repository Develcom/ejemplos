package ve.gob.cne.sarc.persistencia.dao.reportes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.reportes.DivorcioDao;

/**
 * @author HiSoft
 */
@Repository("divorcioDao")
public class DivorcioDaoImpl extends GenericDaoImpl implements DivorcioDao {
    private static final String GEOGRAFICO = "geografico";
    private static final String SELECT = " select estado.nbGeografico, ofi.nombre,extract(year from current_date)-extract(year from ciu.fechaNacimiento),nota.descripcion ";
    private static final String ENTIDADESREF = " from CiudadanoEntidad as ciu,EcuEntidad as ecu, ";
    private static final String ENTIDADESREF1 = " EcuParticipanteActaEntidad as epc, ActaEntidad as act,MatrimonioEntidad as mat, ";
    private static final String ENTIDADESREF2 = " ActaNotaMarginalEntidad as nota, LibroEntidad as lib,OficinaEntidad as ofi, ";
    private static final String COND1 = " and act.id=epc.acta.id and mat.acta.id=act.id ";
    private static final String COND2 = " and nota.acta.id=act.id and  lib.id=act.libro.id and ofi.id=lib.oficina.id ";

    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String, Object>> divorciosPorEntidad(long geografico) {

        StringBuilder query = new StringBuilder();
        query.append(" select geo.nbGeografico, ofi.nombre,extract(year from current_date)-extract(year from ciu.fechaNacimiento),nota.descripcion");
        query.append(ENTIDADESREF);
        query.append(ENTIDADESREF1);
        query.append(ENTIDADESREF2);
        query.append(" GeograficoEntidad as geo,TipoGeograficoEntidad as tipo ");
        query.append(" where geo.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Estado') and ciu.estatusCivil='D' and geo.id=:geografico and ciu.id=ecu.ciudadano.id and epc.ecu.id=ecu.id ");
        query.append(COND1);
        query.append(COND2);
        query.append(" and ofi.geografico=geo.id ");

        Query s = sessionFactory.getCurrentSession()
                .createQuery(query.toString())
                .setParameter(GEOGRAFICO, geografico);
        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECT);
        query.append(ENTIDADESREF);
        query.append(ENTIDADESREF1);
        query.append(ENTIDADESREF2);
        query.append(" GeograficoEntidad as estado,GeograficoEntidad as municip,TipoGeograficoEntidad as tipo");
        query.append(" where municip.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Municipio') and ciu.estatusCivil='D' and estado.id=:geografico and ciu.id=ecu.ciudadano.id and epc.ecu.id=ecu.id ");
        query.append(COND1);
        query.append(COND2);
        query.append(" and estado.id=municip.nuPadreGeografico and ofi.geografico=municip.id ");

        Query s1 = sessionFactory.getCurrentSession()
                .createQuery(query.toString())
                .setParameter(GEOGRAFICO, geografico);
        s1.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECT);
        query.append(ENTIDADESREF);
        query.append(ENTIDADESREF1);
        query.append(ENTIDADESREF2);
        query.append(" GeograficoEntidad as estado,GeograficoEntidad as municip,GeograficoEntidad as paq,TipoGeograficoEntidad as tipo ");
        query.append(" where paq.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Parroquia') and ciu.estatusCivil='D' and estado.id=:geografico and ciu.id=ecu.ciudadano.id and epc.ecu.id=ecu.id ");
        query.append(COND1);
        query.append(COND2);
        query.append(" and estado.id=municip.nuPadreGeografico and paq.nuPadreGeografico=municip.id and ofi.geografico=paq.id ");

        Query s2 = sessionFactory.getCurrentSession()
                .createQuery(query.toString())
                .setParameter(GEOGRAFICO, geografico);
        s2.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECT);
        query.append(ENTIDADESREF);
        query.append(ENTIDADESREF1);
        query.append(ENTIDADESREF2);
        query.append(" GeograficoEntidad as estado,GeograficoEntidad as inosp,TipoGeograficoEntidad as tipo");
        query.append(" where inosp.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Inhóspito') and ciu.estatusCivil='D' and estado.id=:geografico and ciu.id=ecu.ciudadano.id and epc.ecu.id=ecu.id ");
        query.append(COND1);
        query.append(COND2);
        query.append(" and estado.id=inosp.nuPadreGeografico and ofi.geografico=inosp.id ");

        Query s3 = sessionFactory.getCurrentSession()
                .createQuery(query.toString())
                .setParameter(GEOGRAFICO, geografico);
        s3.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> result = new ArrayList<>();
        result.addAll(s.list());
        result.addAll(s1.list());
        result.addAll(s2.list());
        result.addAll(s3.list());

        return result;
    }
}
