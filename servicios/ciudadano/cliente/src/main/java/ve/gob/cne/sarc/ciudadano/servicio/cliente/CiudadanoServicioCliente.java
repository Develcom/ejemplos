package ve.gob.cne.sarc.ciudadano.servicio.cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;

import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * CiudadanoServicioCliente.java
 *
 * @descripcion [Servicio Ciudadano Descripcion: API Cliente para el acceso a los servicios de Ciudadano]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Component
public class CiudadanoServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(CiudadanoServicioCliente.class);
    private String endPointConsultarPorNumeroDocumento;
    private AdministradorPropiedades properties;

    public CiudadanoServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.desRegistrarEscucha(this);
        LOG.info("Cargando propiedades de Ciudadano");
        init();
    }

    /**
     * Metodo de acceso a los Servicios de Ciudadano de consulta de Número de Documento de Identidad
     *
     * @param numeroDocIdentidad, String número de documento de identidad
     * @param token permiso de acceso
     * @return Objeto de tipo Ciudadano
     *
     */
    public Ciudadano consultarPorNumeroDocumento(String numeroDocIdentidad, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Ciudadano respuesta;

        respuesta = rt.procesarPeticionSeguridad(
                endPointConsultarPorNumeroDocumento + numeroDocIdentidad,
                token, null, Ciudadano.class, HttpMethod.GET);

        return respuesta;
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if ("CiudadanoServicioCliente.properties".equals(archivo)) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointConsultarPorNumeroDocumento = properties.getProperty("endPointConsultarPorNumeroDocumento");
    }
}
