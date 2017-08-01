package ve.gob.cne.sarc.ecu.servicio.cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * EcuServicioCliente.java
 *
 * @descripcion [Clase cliente para los servicio de ECU]
 * @version 1.0 14/7/2016
 * @author Erick Escalona
 */
@Component
@Configuration
@EnableAsync
@EnableScheduling
public class EcuServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(EcuServicioCliente.class);
    private static String endPointValidarEcu;
    private static String endPointVinculaEcuActaParti;
    private static String endPointActualizarEcu;
    private static String endPointBuscarExistenciaECU;
    private static String endPointConsultarECU;
    private AdministradorPropiedades properties;

    public EcuServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.registrarEscucha(this);
        init();
    }

    /**
     * Valida que exista un ECU para el ciudadano
     *
     * @param ciudadano Ciudadano pojo con la informacion del ciudadano
     * @param token permiso de acceso
     * @return Lista de Objetos de tipo Participante
     */
    public List<Participante> validarEcu(Ciudadano ciudadano, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        List<Participante> participantes;

        participantes = rth.procesarPeticionSeguridadLista(endPointValidarEcu,
                token, ciudadano, Participante[].class, HttpMethod.POST);

        return participantes;
    }

    /**
     * Cliente que vincula el acta con los participante y su ecu
     *
     * @param solicitud el numero de solicito par la vinculacion
     * @param token permiso de acceso
     * @return Verdadero si se vinculo falso en caso contrario
     */
    public Boolean vincularActaEcuParticipante(String solicitud, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        Boolean response;

        response = rth.procesarPeticionSeguridad(endPointVinculaEcuActaParti + solicitud,
                token, null, Boolean.class, HttpMethod.GET);

        return response;
    }

    /**
     * Cliente que actualiza el ecu
     *
     * @param codigoEstadoEcu codigo del estado del Ecu
     * @param participante pojo de Participante
     * @param token permiso de acceso
     * @return ecu pojo de Ecu
     */
    public Ecu actualizarEcu(String codigoEstadoEcu, Participante participante, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        Ecu ecuRespuesta;

        ecuRespuesta = rth.procesarPeticionSeguridad(endPointActualizarEcu + codigoEstadoEcu,
                token, participante, Ecu.class, HttpMethod.POST);

        return ecuRespuesta;

    }

    /**
     * Metodo de acceso a los Servicios de Ecu de consulta de existencia de ECU
     *
     * @param numeroDocIdentidad, String numero de documento de identidad
     * @param token permiso de acceso
     * @return true si existe el ecu, en caso contrario false
     *
     */
    public Boolean buscarExistenciaECU(String numeroDocIdentidad, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        Boolean resp;

        resp = rth.procesarPeticionSeguridad(endPointBuscarExistenciaECU + numeroDocIdentidad,
                token, null, Boolean.class, HttpMethod.GET);

        return resp;
    }

    /**
     * Metodo que permite consultar el Ecu de un ciudadano dado el numero de
     * documento y el tipo de documento de identificacion (CED:Cedula,
     * PAS:Pasaporte, NUI: Nui)
     *
     * @param numeroDocumento String Numero de documento de identidad
     * @param tipoDocumento String Tipo de documento de identificacion
     * (CED:Cedula, PAS:Pasaporte, NUI: Nui)
     * @param token permiso de acceso
     * @return Pojo Ecu con los datos del Ecu del ciudadano
     */
    public Ecu consultarEcu(String numeroDocumento, String tipoDocumento, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        Ecu respEcu;

        respEcu = rth.procesarPeticionSeguridad(endPointConsultarECU + numeroDocumento
                + "/" + tipoDocumento, token, null, Ecu.class, HttpMethod.GET);

        return respEcu;
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if (archivo.equals("EcuServicioCliente.properties")) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {

        endPointValidarEcu = properties.getProperty("endPointValidarEcu");
        endPointVinculaEcuActaParti = properties.getProperty("endPointVinculaEcuActaParti");
        endPointActualizarEcu = properties.getProperty("endPointActualizarEcu");
        endPointBuscarExistenciaECU = properties.getProperty("endPointBuscarExistenciaECU");
        endPointConsultarECU = properties.getProperty("endPointConsultarECU");
    }    
}
