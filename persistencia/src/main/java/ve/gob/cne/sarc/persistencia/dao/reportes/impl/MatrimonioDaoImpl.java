package ve.gob.cne.sarc.persistencia.dao.reportes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.reportes.MatrimonioDao;
import ve.gob.cne.sarc.persistencia.entidades.GeograficoEntidad;

/**
 * @author HiSoft
 */
@Repository("matrimonioDao")
public class MatrimonioDaoImpl extends GenericDaoImpl implements MatrimonioDao {

    private static final String GEOGRAFICO = "geografico";
    private static final String TIPOFECHA = "anio";
    private static final String TIPOFECHAMES = "mes";
    private static final String ENTIDADESREF = " from OficinaEntidad as ofi, LibroEntidad lib, ActaEntidad acta, MatrimonioEntidad as mat, ";
    private static final String SELECT = " select extract(year from acta.fechaCreacion), estado.nbGeografico, ofi.nombre ";
    private static final String SELECTMES = " select extract(year from acta.fechaCreacion), estado.nbGeografico, ofi.nombre,extract(month from acta.fechaCreacion) ";
    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String, Object>> matrimoniosPorEntidad(
            GeograficoEntidad geografico) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("select matrimonio.direccion as direccion,");
        sb.append("matrimonio.fecha as fecha,");
        sb.append("matrimonio.primerNombreAutoridadCap as primerNombreAutoridadCap,");
        sb.append("matrimonio.primerApellidoAutoridadCap as primerApellidoAutoridadCap,");
        sb.append("matrimonio.notaria as notaria ");
        sb.append("from MatrimonioEntidad as matrimonio ");
        sb.append("join matrimonio.acta as acta ");
        sb.append("join matrimonio.acta.libro as libro ");
        sb.append("join matrimonio.acta.libro.oficina as oficina ");
        sb.append("where oficina.geografico=:geografico");
        
        Query s = sessionFactory.getCurrentSession().createQuery(sb.toString()).setParameter("geografico", geografico);
        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return s.list();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String, Object>> matrimoniosPorEntidad(int anio,
            long geografico) {
        
        StringBuilder query = new StringBuilder();
        query.append(" select extract(year from acta.fechaCreacion), geo.nbGeografico, ofi.nombre");
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as geo,TipoGeograficoEntidad as tipo  ");
        query.append(" where geo.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Estado') and extract(year from acta.fechaCreacion)=:anio and geo.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and mat.acta.id=acta.id  ");
        query.append(" and  ofi.geografico=geo.id  ");

        Query s = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico);

        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECT);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip,TipoGeograficoEntidad as tipo ");
        query.append(" where municip.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Municipio') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and mat.acta.id=acta.id  ");
        query.append(" and  estado.id=municip.nuPadreGeografico and ofi.geografico=municip.id  ");

        Query s1 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico);

        s1.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECT);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip, GeograficoEntidad as paq,TipoGeograficoEntidad as tipo");
        query.append(" where paq.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Parroquia') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and mat.acta.id=acta.id  ");
        query.append(" and  estado.id=municip.nuPadreGeografico and paq.nuPadreGeografico=municip.id and ofi.geografico=paq.id  ");

        Query s2 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico);

        s2.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECT);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as inosp,TipoGeograficoEntidad as tipo ");
        query.append(" where  inosp.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Inhóspito') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and mat.acta.id=acta.id  ");
        query.append(" and  estado.id=inosp.nuPadreGeografico and ofi.geografico=inosp.id  ");

        Query s3 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico);

        s3.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> result = new ArrayList<>();
        result.addAll(s.list());
        result.addAll(s1.list());
        result.addAll(s2.list());
        result.addAll(s3.list());
        
        return result;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String, Object>> matrimoniosPorEntidad(int anio, int mes,
            long geografico) {
        
        StringBuilder query = new StringBuilder();
        query.append(" select extract(year from acta.fechaCreacion), geo.nbGeografico, ofi.nombre ");
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as geo,TipoGeograficoEntidad as tipo  ");
        query.append(" where geo.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Estado') and extract(year from acta.fechaCreacion)=:anio and extract(month from acta.fechaCreacion)=:mes and geo.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and mat.acta.id=acta.id  ");
        query.append(" and  ofi.geografico=geo.id  ");

        Query s = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico).setParameter(TIPOFECHAMES, mes);

        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECTMES);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip,TipoGeograficoEntidad as tipo ");
        query.append(" where municip.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Municipio') and extract(year from acta.fechaCreacion)=:anio and extract(month from acta.fechaCreacion)=:mes and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and mat.acta.id=acta.id  ");
        query.append(" and  estado.id=municip.nuPadreGeografico and ofi.geografico=municip.id  ");

        Query s1 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico).setParameter(GEOGRAFICO, geografico).setParameter(TIPOFECHAMES, mes);

        s1.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECTMES);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip, GeograficoEntidad as paq,TipoGeograficoEntidad as tipo ");
        query.append(" where paq.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Parroquia') and extract(year from acta.fechaCreacion)=:anio and extract(month from acta.fechaCreacion)=:mes and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and mat.acta.id=acta.id  ");
        query.append(" and  estado.id=municip.nuPadreGeografico and paq.nuPadreGeografico=municip.id and ofi.geografico=paq.id  ");

        Query s2 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico).setParameter(GEOGRAFICO, geografico).setParameter(TIPOFECHAMES, mes);

        s2.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);
        query.append(SELECTMES);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as inosp,TipoGeograficoEntidad as tipo ");
        query.append(" where inosp.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Inhóspito') and extract(year from acta.fechaCreacion)=:anio and extract(month from acta.fechaCreacion)=:mes and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and mat.acta.id=acta.id  ");
        query.append(" and  estado.id=inosp.nuPadreGeografico and ofi.geografico=inosp.id  ");

        Query s3 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico).setParameter(GEOGRAFICO, geografico).setParameter(TIPOFECHAMES, mes);

        s3.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> result = new ArrayList<>();
        result.addAll(s.list());
        result.addAll(s1.list());
        result.addAll(s2.list());
        result.addAll(s3.list());

        return result;
    }
    
}
