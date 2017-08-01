package ve.gob.cne.sarc.persistencia.dao.reportes.impl;

import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import ve.gob.cne.sarc.persistencia.dao.generic.impl.GenericDaoImpl;
import ve.gob.cne.sarc.persistencia.dao.reportes.NacimientoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author HiSoft
 */
@Repository("nacimientoDao")
public class NacimientoDaoImpl extends GenericDaoImpl implements NacimientoDao {
    private static final String GEOGRAFICO = "geografico";
    private static final String TIPOFECHA = "anio";
    private static final String TIPOFECHAMES = "mes";
    private static final String SEXO ="sexo";
    private static final String SELECTMES="select extract(year from acta.fechaCreacion), extract(month from acta.fechaCreacion), estado.nbGeografico, ofi.nombre, nac.sexo,nac.centroSalud";
    private static final String SELECTANIO="select extract(year from acta.fechaCreacion), estado.nbGeografico, ofi.nombre, nac.sexo,nac.centroSalud ";
    private static final String SELECTSEXO=" select extract(year from acta.fechaCreacion), estado.nbGeografico, ofi.nombre, nac.sexo,nac.centroSalud ";
    private static final String ENTIDADESREF=" from OficinaEntidad as ofi, LibroEntidad lib, ActaEntidad acta, NacimientoEntidad as nac, ";
    private static final String WHEREOFI=" and ofi.geografico=geo.id  ";
    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String, Object>> nacimientosPorEntidadAnio(int anio,
            long geografico) {
        
        StringBuilder query = new StringBuilder();
        query.append(" select extract(year from acta.fechaCreacion), geo.nbGeografico, ofi.nombre, nac.sexo,nac.centroSalud ");
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as geo,TipoGeograficoEntidad as tipo  ");
        query.append(" where geo.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Estado') and extract(year from acta.fechaCreacion)=:anio and geo.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(WHEREOFI);

        Query s = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico);

        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        query.setLength(0);

        query.append(SELECTANIO);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip,TipoGeograficoEntidad as tipo  ");
        query.append(" where municip.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Municipio') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and ofi.geografico=municip.id and municip.nuPadreGeografico=estado.id  ");
        
        Query s1 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico);

        s1.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        
        query.setLength(0);
        
        query.append(SELECTANIO);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip, GeograficoEntidad as paq,TipoGeograficoEntidad as tipo ");
        query.append(" where paq.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Parroquia') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and ofi.geografico=paq.id and paq.nuPadreGeografico=municip.id and municip.nuPadreGeografico=estado.id  ");
        
        Query s2 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico);

        s2.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        
        query.setLength(0);
        query.append(SELECTANIO);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as inospi,TipoGeograficoEntidad as tipo ");
        query.append(" where inospi.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Inhóspito') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and ofi.geografico=inospi.id  and inospi.nuPadreGeografico=estado.id ");
        
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
    public List<Map<String, Object>> nacimientosPorEntidadSexo(int anio,
            String sexo, long geografico) {
        
        StringBuilder query = new StringBuilder();
        query.append(" select extract(year from acta.fechaCreacion), geo.nbGeografico, ofi.nombre, nac.sexo,nac.centroSalud");
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as geo,TipoGeograficoEntidad as tipo   ");
        query.append(" where geo.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Estado') and extract(year from acta.fechaCreacion)=:anio and geo.id=:geografico and nac.sexo in (:sexo) and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(WHEREOFI);

        Query s = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico)
                .setParameter(SEXO, sexo);

        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        
        query.setLength(0);
        query.append(SELECTSEXO);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip,TipoGeograficoEntidad as tipo   ");
        query.append(" where municip.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Municipio') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and nac.sexo in (:sexo) and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and estado.id=municip.nuPadreGeografico and ofi.geografico=municip.id  ");
        
        Query s1 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico)
                .setParameter(SEXO, sexo);

        s1.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        
        query.setLength(0);
        query.append(SELECTSEXO);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip, GeograficoEntidad as paq,TipoGeograficoEntidad as tipo  ");
        query.append(" where paq.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Parroquia') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and nac.sexo in (:sexo) and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and estado.id=municip.nuPadreGeografico and municip.id=paq.nuPadreGeografico and ofi.geografico=paq.id  ");
        
        Query s2 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico)
                .setParameter(SEXO, sexo);
        
        s2.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        
        query.setLength(0);
        query.append(SELECTSEXO);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as inosp,TipoGeograficoEntidad as tipo  ");
        query.append(" where inosp.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Inhóspito') and extract(year from acta.fechaCreacion)=:anio and estado.id=:geografico and nac.sexo in (:sexo) and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and estado.id=inosp.nuPadreGeografico and ofi.geografico=inosp.id  ");
        
        Query s3 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico)
                .setParameter(SEXO, sexo);
        
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
    public List<Map<String, Object>> nacimientosPorEntidadAnioMes(int anio,
            int mes, long geografico) {
        
        StringBuilder query = new StringBuilder();
        
        query.append(" select extract(year from acta.fechaCreacion), extract(month from acta.fechaCreacion), geo.nbGeografico, ofi.nombre, nac.sexo,nac.centroSalud");
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as geo,TipoGeograficoEntidad as tipo  ");
        query.append(" where geo.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Estado') and extract(year from acta.fechaCreacion)=:anio and extract(month from acta.fechaCreacion)=:mes and geo.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(WHEREOFI);

        Query s = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico)
                .setParameter(TIPOFECHAMES, mes);

        s.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        
        query.setLength(0);
        query.append(SELECTMES);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip,TipoGeograficoEntidad as tipo  ");
        query.append(" where municip.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Municipio') and extract(year from acta.fechaCreacion)=:anio and extract(month from acta.fechaCreacion)=:mes and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and ofi.geografico=municip.id  and municip.nuPadreGeografico=estado.id");
        
        Query s1 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico)
                .setParameter(TIPOFECHAMES, mes);
        
        s1.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
    
        query.setLength(0);
        
        query.append(SELECTMES);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as municip, GeograficoEntidad as paq,TipoGeograficoEntidad as tipo ");
        query.append(" where paq.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Parroquia') and extract(year from acta.fechaCreacion)=:anio and extract(month from acta.fechaCreacion)=:mes and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and ofi.geografico=paq.id  and municip.nuPadreGeografico=estado.id and paq.nuPadreGeografico=municip.id");
        
        Query s2 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico)
                .setParameter(TIPOFECHAMES, mes);
        
        s2.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        
        query.setLength(0);
        
        query.append(SELECTMES);
        query.append(ENTIDADESREF);
        query.append(" GeograficoEntidad as estado, GeograficoEntidad as inospi,TipoGeograficoEntidad as tipo ");
        query.append(" where  inospi.tipoGeografico.id=tipo.id and upper(tipo.txDescripcion)=upper('Inhóspito') and extract(year from acta.fechaCreacion)=:anio and extract(month from acta.fechaCreacion)=:mes and estado.id=:geografico and ofi.id=lib.oficina.id and lib.id=acta.libro.id and nac.acta.id=acta.id  ");
        query.append(" and ofi.geografico=inospi.id  and inospi.nuPadreGeografico=estado.id ");
        
        Query s3 = sessionFactory.getCurrentSession()
                .createQuery(query.toString()).setParameter(TIPOFECHA, anio)
                .setParameter(GEOGRAFICO, geografico)
                .setParameter(TIPOFECHAMES, mes);
        s3.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        
        List<Map<String, Object>> result = new ArrayList<>();
        result.addAll(s.list());
        result.addAll(s1.list());
        result.addAll(s2.list());
        result.addAll(s3.list());
        
        return result;
    }
    
    
    
}
