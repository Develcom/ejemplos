package ve.gob.cne.sarc.nui.servicio.cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * NuiServicioCliente.java
 *
 * @descripcion [API Cliente para el acceso a los servicios de Nui ]
 * @version 1.0 14/7/2016
 * @author Anabell De Faria
 */
@Component
public class NuiServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(NuiServicioCliente.class);

    private String endPointBuscarProximoNui;
    private String endPointBuscarExistenciaNUI;

    @Autowired
    private AdministradorPropiedades properties;

    /**
     * Constructor
     * @throws ve.gob.cne.sarc.utilitarios.excepciones.GenericException
     */
    public NuiServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
        "conf/general/config-Manage.properties");
        
        properties.desRegistrarEscucha(this);
        init();
    }

    @Bean(name = "properties")
    public AdministradorPropiedades getProperties() throws GenericException {
        AdministradorPropiedades ap = new AdministradorPropiedadesImplementacionApache("SARC_HOME", 
        "conf/general/config-Manage.properties");
        
        init();
        return ap;
    }

    /**
     * Metodo de acceso a los Servicios de Nui de consulta de Proximo Numero de
     * Nui
     *
     * @param codigoOficina, String codigo de la oficina
     * @param token permiso de acceso
     * @return Proximo numero de Nui
     *
     */
    public String buscarProximoNui(String codigoOficina, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        String nui;

        nui = rt.procesarPeticionSeguridad(endPointBuscarProximoNui
                + codigoOficina, token, null, String.class, HttpMethod.GET);

        return nui;
    }

    /**
     * Metodo de acceso a los Servicios de Nui de consulta de existencia de Nui
     *
     * @param numeroDocIdentidad, String numero de documento de identidad
     * @param token permiso de acceso
     * @return true si existe el nui, en caso contrario false
     *
     */
    public Boolean buscarExistenciaNUI(String numeroDocIdentidad, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Boolean resp;

        resp = rt.procesarPeticionSeguridad(endPointBuscarExistenciaNUI
                + numeroDocIdentidad, token, null, Boolean.class, HttpMethod.GET);

        return resp;
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if ("NuiServicioCliente.properties".equals(archivo)) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointBuscarProximoNui = properties.getProperty("endPointBuscarProximoNui");
        endPointBuscarExistenciaNUI = properties.getProperty("endPointBuscarExistenciaNUI");
    }
}
