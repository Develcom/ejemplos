package ve.gob.cne.sarc.proteger.protegerrecursos.core.protegerrecursos.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;
import ve.gob.sarc.seguridad.bo.GrupoPermisoBO;
import ve.gob.sarc.seguridad.bo.GrupoRecurosBO;

/**
 * CustomMatcher.java
 *
 * @descripcion Configura los diferentes endpoint para la proteccion de los recursos
 * en diferentes war
 * @author Erick Escalona
 * @version 1.0
 */
@Component
public class CustomMatcher {

    @Autowired
    @Qualifier("webApplicationContextBean")
    private WebApplicationContext webApplicationContext;

    @Autowired
    @Qualifier("properties")
    private AdministradorPropiedades properties;

    private static final Logger LOG = LoggerFactory.getLogger(CustomMatcher.class);
    private String[] endPoint;
    private String roles;

    public String[] getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String[] endPoint) {
        this.endPoint = endPoint;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    /**
     * Extrae una lista de recursos dada una url
     *
     * @return Lista con los recursos a proteger
     * @throws ve.gob.cne.sarc.utilitarios.excepciones.GenericException captura
     * todas las excepciones incognitas para el desarrollador en las
     * implementaciones.
     */
    public List<CustomMatcher> cargarRecursos() throws GenericException {

        RestTemplateHelper helper = new RestTemplateHelper();
        List<CustomMatcher> cms = new ArrayList<>();
        CustomMatcher cm;
        Map<String, String> parametros = new HashMap<>();
        String[] recursos;
        List<GrupoRecurosBO> grupoRecurosBO;
        String endPointRecursos, codRecurso, url, codigoModulo;
        int cont = 1;

        codigoModulo = webApplicationContext.getApplicationName().substring(1);

        LOG.info("codigoModulo " + codigoModulo);

        endPointRecursos = properties.getProperty("endPointRecursos");
        codRecurso = properties.getProperty(codigoModulo);
        url = endPointRecursos + codRecurso;

        LOG.info("url a consultar " + url);

        grupoRecurosBO = helper.procesarPeticionComoLista(
                url, parametros, GrupoRecurosBO[].class);

        LOG.info("tamaño lista endPoint " + grupoRecurosBO.size());

        for (GrupoRecurosBO gr : grupoRecurosBO) {

            String endPoints = gr.getEndPoint().toString().replaceAll("[\\s\\[\\]]", "");
            recursos = endPoints.split(",");

            String rols = "";
            int size = gr.getGrupoPermisoBO().size();
            for (GrupoPermisoBO gp : gr.getGrupoPermisoBO()) {

                if (cont != size) {
                    rols = rols + "hasAnyAuthority('" + gp.getRol() + "') " + gp.getOperadorLogico() + " ";
                } else {
                    rols = rols + "hasAnyAuthority('" + gp.getRol() + "') ";
                }
                cont++;
            }

            cm = new CustomMatcher();

            cm.setRoles(rols);
            cm.setEndPoint(recursos);
            cms.add(cm);

        }
        return cms;
    }
}
