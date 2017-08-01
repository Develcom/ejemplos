package ve.gob.cne.sarc.recaudo.servicio.cliente;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.comunes.catalogo.Recaudo;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * RecaudoServicioCliente.java
 *
 * @descripcion [Cliente para la ejecucion de los servicios de Recaudo]
 * @version 1.0 22/7/2016
 * @author Anabell De Faria
 */
@Component
@Configuration
@EnableAsync
@EnableScheduling
public class RecaudoServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(RecaudoServicioCliente.class);

    private String endPointConsultarRecaudo;
    private String endPointRegistrarRecaudos;
    private String endPointConsultarRecaudos;
    private AdministradorPropiedades properties;

    public RecaudoServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.registrarEscucha(this);
        init();
    }

    /**
     * Cliente de la operacion consultar recaudo por codigo
     *
     * @param codigo String codigo de recaudo
     * @param token permiso de acceso
     * @return Objeto de tipo Recaudo
     */
    public Recaudo consultarRecaudoPorCodigo(String codigo, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();

        return rt.procesarPeticionSeguridad(endPointConsultarRecaudo + codigo,
                token, null, Recaudo.class, HttpMethod.GET);
    }

    /**
     * Registrar Recaudos
     *
     * @param numeroSolicitud String que describe el numero de solicitud
     * @param recaudos String codigo de Recaudo
     * @param token permiso de acceso
     * @return Verdadero si se registro el recaudo, falso en caso contrario
     */
    public Boolean registrarRecaudos(String numeroSolicitud, String[] recaudos, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        
        return rt.procesarPeticionSeguridad(endPointRegistrarRecaudos
                + numeroSolicitud + "/" + Arrays.deepToString(recaudos),
                token, null, Boolean.class, HttpMethod.POST);
    }

    /**
     * Cliente de la operacion consultar recaudos por numero de solicitud
     *
     * @param numSolicitud String numero de solicitud
     * @param token permiso de acceso
     * @return Lista de objetos de tipo Recaudo
     */
    public List<Recaudo> consultarRecaudo(String numSolicitud, String token) {
        RestTemplateHelper rt = new RestTemplateHelper();
      
        return rt.procesarPeticionSeguridadLista(endPointConsultarRecaudos
                + numSolicitud, token, null, Recaudo[].class, HttpMethod.GET);

     }

    @Override
    public String changeProperties(String archivo) {
        try {
            if ("SeguridadServicioCliente.properties".equals(archivo)) {
                init();
            } 
            
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointConsultarRecaudo = properties.getProperty("endPointConsultarRecaudo");
        endPointRegistrarRecaudos = properties.getProperty("endPointRegistrarRecaudos");
        endPointConsultarRecaudos = properties.getProperty("endPointConsultarRecaudos");
    }
}
