package ve.gob.cne.sarc.seguridad.core.autorizar.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ve.gob.sarc.seguridad.bo.EndPointsBO;
import ve.gob.cne.sarc.persistencia.repositorios.PermisologiaRepository;
import ve.gob.cne.sarc.seguridad.core.autorizar.business.PermisologiaBF;
import ve.gob.cne.sarc.seguridad.core.autorizar.controllers.AdminController;
import ve.gob.cne.sarc.seguridad.core.autorizar.mapper.PermisologiaMapper;
import ve.gob.sarc.seguridad.bo.GrupoPermisoBO;
import ve.gob.sarc.seguridad.bo.GrupoRecurosBO;

/**
 * Descripcion de la clase: Implementacion que se encarga de buscar el recurso a
 * proteger dependiendo del parametro del modulo
 *
 * @author Ismayer Hernandez 16 de ago. de 2016
 */
@Component
public class PermisologiaBFImpl implements PermisologiaBF {

    private static final Logger LOG = LoggerFactory.getLogger(PermisologiaBFImpl.class);

    @Autowired
    private PermisologiaRepository permisologiaRepositoryBFI;

    @Autowired
    private PermisologiaMapper permisologiaMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean findByCoPermisologiaBF(String coPermisologia) {
        return null;
    }

    /**
     * Traza de historias Jira " S2RC-554".
     *
     * @see
     * ve.gob.cne.sarc.seguridad.core.autorizar.business.PermisologiaBF#buscaRecurso(java.lang.String)
     */
    @Override
    public List<EndPointsBO> buscaRecurso(String coModulo) {

        Long codigoModulo = Long.parseLong(coModulo);
        List<EndPointsBO> listEndPoints = null;

//        String sql = "SELECT e.NB_RECURSO, p.NIVEL_RESTRICCION, i.NB_PERMISO, r.IDEN_ROL "
//                + "FROM C068T_PERMISOLOGIA p, I013T_MODULO m, C033T_ROL r, I014T_RECURSO e, I012T_TIPO_PERMISO i "
//                + "WHERE p.CO_MODULO = m.CO_MODULO " + "AND p.CO_ROL = r.CO_ROL "
//                + "AND p.CO_RECURSO = e.CO_RECURSO " + "AND p.CO_PERMISO = i.CO_PERMISO " + "AND p.CO_MODULO = "
//                + codigoModulo;
        String sql = "SELECT e.NB_RECURSO, i.NB_PERMISO, r.IDEN_ROL \n"
                + "FROM C068T_PERMISOLOGIA p, \n"
                + "I013T_MODULO m, \n"
                + "C033T_ROL r, \n"
                + "I014T_RECURSO e, \n"
                + "I012T_TIPO_PERMISO i, \n"
                + "C073T_PERMISO_RECURSO pr,\n"
                + "C074T_ROL_OPERADOR ro\n"
                + "WHERE p.CO_MODULO = m.CO_MODULO \n"
                + "and pr.co_permisologia=p.co_permisologia\n"
                + "and pr.co_recurso=e.co_recurso\n"
                + "and ro.co_permisologia = p.co_permisologia\n"
                + "AND ro.c033pk_rol = r.co_rol \n"
                + "AND p.CO_PERMISO = i.CO_PERMISO \n"
                + "AND p.CO_MODULO = "
                + codigoModulo;

        List<Object[]> results = em.createNativeQuery(sql).getResultList();
        LOG.info("tamaño de la consulta " + results.size());
        if (!results.isEmpty() && results.size() >= 1) {
            listEndPoints = new ArrayList<>();
            String endPoints;
            int nivelRestriccion = 0;
            String tipoPermiso;
            String rol;

            for (Object[] result : results) {

                endPoints = result[0].toString();
//                nivelRestriccion = Integer.parseInt(result[1].toString());
                tipoPermiso = result[1].toString();
                if (result[2] != null) {
                    rol = result[2].toString();
                } else {
                    rol = null;
                }

                listEndPoints.add(new EndPointsBO(endPoints, nivelRestriccion, tipoPermiso, rol));

            }
        }
        return listEndPoints;
    }

    @Override
    public List<GrupoRecurosBO> buscarRecurso(String coModulo) {

        List<GrupoRecurosBO> grupoRecurosBOs = new ArrayList<>();
        GrupoRecurosBO gr = new GrupoRecurosBO();
        List<GrupoPermisoBO> grupoPermisoBO = new ArrayList<>();
        GrupoPermisoBO gp;
        List<String> endPoints = new ArrayList<>();
        String sql, tmp = "";
        List<Object[]> results;
        boolean flag = true;

        sql = "select r.NB_RECURSO, t.NB_PERMISO, rol.IDEN_ROL, ro.operador_logico, ro.nu_orden  "
                + "from i013t_modulo m "
                + "inner join c068t_permisologia p on m.co_modulo=p.co_modulo "
                + "inner join c073t_permiso_recurso pr on p.co_permisologia=pr.co_permisologia "
                + "inner join i012t_tipo_permiso t on t.co_permiso=p.co_permiso "
                + "inner join i014t_recurso r on r.co_recurso=pr.co_recurso "
                + "inner join c074t_rol_operador ro on ro.co_permisologia=p.co_permisologia "
                + "inner join c033t_rol rol on rol.co_rol=ro.co_rol "
                + "where m.co_modulo=" + coModulo + " order by ro.nu_orden";

        results = em.createNativeQuery(sql).getResultList();

        LOG.info("tamaño de la consulta " + results.size());

        if (!results.isEmpty() && results.size() >= 1) {

            for (Object[] result : results) {
                if (!"/acta/**".equalsIgnoreCase(result[0].toString())) {
                    if (!tmp.equalsIgnoreCase(result[0].toString())) {
                        tmp = result[0].toString();
                        if (endPoints.isEmpty()) {
                            endPoints.add(result[0].toString());
                        } else {
                            for (int i = 1; i <= endPoints.size(); i++) {
                                if (endPoints.get(i - 1).equalsIgnoreCase(tmp)) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                endPoints.add(result[0].toString());
                            }
                        }
                    }
                }
            }

            LOG.info("tamaño lista endPoint " + endPoints.size());

            gr.setEndPoint(endPoints);

            tmp = "";
            for (Object[] result : results) {
                if (!tmp.equalsIgnoreCase(result[2].toString())) {
                    tmp = result[2].toString();

                    gp = new GrupoPermisoBO();
                    gp.setPermiso(result[1].toString());
                    gp.setRol(result[2].toString());
                    gp.setOperadorLogico(result[3].toString());
                    gp.setNumeroPos(Integer.parseInt(result[4].toString()));
                    grupoPermisoBO.add(gp);

                }
            }

            LOG.info("tamaño lista grupoPermisoBO " + grupoPermisoBO.size());

            gr.setGrupoPermisoBO(grupoPermisoBO);
        }

        grupoRecurosBOs.add(gr);

        LOG.info("tamaño lista grupoRecurosBOs " + grupoRecurosBOs.size());

        return grupoRecurosBOs;
    }

}
