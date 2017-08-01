package ve.gob.cne.sarc.acta.servicio.cliente;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import ve.gob.cne.sarc.comunes.acta.ActaPrimigenia;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.comunes.acta.Extemporanea;
import ve.gob.cne.sarc.comunes.acta.Insercion;
import ve.gob.cne.sarc.comunes.acta.MedidaProteccion;
import ve.gob.cne.sarc.comunes.nacimiento.Nacimiento;
import ve.gob.cne.sarc.comunes.oficina.Ore;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;

/**
 * ActaServicioCliente.java
 *
 * @descripcion [Cliente para la ejecucion de los servicios de Acta]
 * @version 1.0 14/7/2016
 * @author Anabell De Faria
 */
@Component
@Configuration
@EnableAsync
@EnableScheduling
public class ActaServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.
            getLogger(ActaServicioCliente.class);

    private static String endPointBuscarActa;
    private static String endPointActualizarActa;
    private static String endPointBuscarActalista;
    private static String endPointValidarCertificado;
    private static String endPointExisteActa;
    private static String endPointBuscarNroActaPorSolic;
    private static String endPointGuardarOre;
    private static String endPointConsultarOre;
    private static String endPointActualizarEstatusActa;
    private static String endPointGuardarInsercion;
    private static String endPointConsultarInsercion;
    private static String endPointGuardarDecisionJud;
    private static String endPointConsultarDecisionJud;
    private static String endPointGuardarMedidaPro;
    private static String endPointConsultarMedidaPro;
    private static String endPointGuardarExtemporanea;
    private static String endPointConsultarExtemporanea;
    private static String endPointGuardarNacimiento;
    private static String endPointConsultarNacimiento;
    private static String endPointGuardarActaPrimigenia;
    private static String endPointConsultarActaPrimigenia;
    private AdministradorPropiedades properties;

    public ActaServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.registrarEscucha(this);
        LOG.info("Cargando propiedades de Acta");
        init();
    }

    /**
     * Consulta un Acta por su numero, tomo, fecha registro, folio
     *
     * @param acta Objecto con la informacion requeridad para la busqueda
     * @param token permiso de acceso
     * @return Toda la informacion relacionada al numero del Acta
     */
    public Acta consultarActa(Acta acta, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        Acta resp;

        resp = rth.procesarPeticionSeguridad(endPointBuscarActa, token, acta, Acta.class, HttpMethod.POST);

        return resp;
    }

    /**
     * Consulta un Acta por el numero de la solicitud, tramite
     *
     * @param acta Objecto con la informacion requeridad para la busqueda
     * @param token permiso de acceso
     * @return Toda la informacion relacionada al numero del Acta
     */
    public List<Acta> consultarActaSolicitud(Acta acta, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        List<Acta> participantes;

        participantes = rth.procesarPeticionSeguridadLista(endPointBuscarActalista, 
            token, null, Acta[].class, HttpMethod.POST);

        return participantes;
    }

    /**
     * Buscar numero de Acta dado un numero de solicitud
     *
     * @param numeroSolic String que contiene el numero de la solicitud
     * @param token permiso de acceso
     * @return String que contiene el numero del Acta
     */
    public String buscarNumeroActaPorSolic(String numeroSolic, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        String respuesta;

        respuesta = rth.procesarPeticionSeguridad(endPointBuscarNroActaPorSolic
                + numeroSolic, token, null, String.class, HttpMethod.POST);

        return respuesta;
    }

    /**
     * Actualiza un Acta
     *
     * @param acta Acta a actualizar
     * @param token permiso de acceso
     * @return Verdadero si se actualizo el Acta, falso en caso contrario
     */
    public boolean actualizarActa(Acta acta, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        boolean result;

        result = rth.procesarPeticionSeguridad(endPointActualizarActa, token, acta, boolean.class, HttpMethod.PUT);

        return result;
    }

    /**
     * Valida si el certificado medico existe.
     *
     * @param codigoTramite String describe el codigo del tramite,
     * @param numeroCertificado long describe el numero de certificado medico
     * @param token permiso de acceso
     * @return Verdadero si existe el certificado medico de acuerdo al codigo
     * del tramite, falso en caso contrario
     */
    public Boolean validarCertificadoMedico(String codigoTramite, long numeroCertificado, String token) {

        Boolean respuesta;
        RestTemplateHelper rth = new RestTemplateHelper();

        respuesta = rth.procesarPeticionSeguridad(endPointValidarCertificado
                + codigoTramite + "/" + numeroCertificado, token, null, Boolean.class, HttpMethod.POST);

        return respuesta;
    }

    /**
     * Consulta todos los documentos autenticados
     *
     * @param numeroActa, String describe el numero de Acta
     * @param token permiso de acceso
     * @return Map<String, String> map que permite almacenar valores
     *
     */
    public Map existeActa(String numeroActa, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        Map respuesta;

        respuesta = rth.procesarPeticionSeguridad(endPointExisteActa + numeroActa,
                token, null, Map.class, HttpMethod.GET);

        return respuesta;
    }

    /**
     * Actualiza el estatus de un acta
     *
     * @param codigoEstatusActa String que contiene el estatus del Acta
     * @param acta Acta pojo con la informacion del acta
     * @param token permiso de acceso
     * @return acta pojo con la informacion del acta
     */
    public Acta actualizarEstatusActa(String codigoEstatusActa, Acta acta, String token) {

        Acta respuesta;
        RestTemplateHelper rth = new RestTemplateHelper();

        respuesta = rth.procesarPeticionSeguridad(endPointActualizarEstatusActa
                + codigoEstatusActa, token, acta, Acta.class, HttpMethod.POST);

        return respuesta;
    }

    /**
     * Metodo que permite guardar el ORE
     *
     * @param numeroSolicitud String que describe el numero de la Solicitud
     * @param token permiso de acceso
     * @return objeto de tipo Ore
     */
    public Ore guardarORE(String numeroSolicitud, String token) {

        Ore respuesta;
        RestTemplateHelper rth = new RestTemplateHelper();

        respuesta = rth.procesarPeticionSeguridad(endPointGuardarOre
                + numeroSolicitud, token, null, Ore.class, HttpMethod.POST);

        return respuesta;
    }

    /**
     *
     * Metodo que permite consultar un Ore dado un numero de Solicitud
     *
     * @param numSolicitud String numero de solicitud
     * @param token permiso de acceso
     * @return Ore pojo que contiene la informacion de Ore
     *
     */
    public Ore consultarOre(String numSolicitud, String token) {

        Ore respuesta;
        RestTemplateHelper rth = new RestTemplateHelper();

        respuesta = rth.procesarPeticionSeguridad(endPointConsultarOre
                + numSolicitud, token, null, Ore.class, HttpMethod.GET);

        return respuesta;
    }

    /**
     * Metodo que permite guardar la insercion
     *
     * @param insercion pojo con la informacion de Insercion
     * @param token permiso de acceso
     * @return objeto de tipo Insercion
     */
    public boolean guardarInsercion(Insercion insercion, String token) {

        boolean respuesta;
        RestTemplateHelper rth = new RestTemplateHelper();

        respuesta = rth.procesarPeticionSeguridad(endPointGuardarInsercion,
                token, insercion, boolean.class, HttpMethod.POST);

        return respuesta;
    }

    /**
     *
     * Metodo que permite consultar un Ore dado un numero de Solicitud
     *
     * @param numSolicitud String numero de solicitud
     * @param token permiso de acceso
     * @return Ore pojo que contiene la informacion de Ore
     *
     */
    public Insercion consultarInsercion(String numSolicitud, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        Insercion respuesta;

        respuesta = rth.procesarPeticionSeguridad(endPointConsultarInsercion + numSolicitud,
                token, null, Insercion.class, HttpMethod.GET);

        return respuesta;
    }

    /**
     * Metodo que permite guardar la decision judicial
     *
     * @param decisionJudicial pojo con la informacion de Decision Judicial
     * @param token permiso de acceso
     * @return verdadero si es éxitosa la transacción en caso contrario falso
     */
    public boolean guardarDecisionJudicial(DecisionJudicial decisionJudicial, String token) {

        RestTemplateHelper rtAct = new RestTemplateHelper();
        boolean resp;

        resp = rtAct.procesarPeticionSeguridad(endPointGuardarDecisionJud, token,
                decisionJudicial, boolean.class, HttpMethod.POST);

        return resp;
    }

    /**
     *
     * Metodo que permite consultar una Decision Judicial dado un numero de
     * Solicitud
     *
     * @param numSolicitud String numero de solicitud
     * @param token permiso de acceso
     * @return pojo que contiene la informacion de Decision Judicial
     *
     */
    public DecisionJudicial consultarDecisionJudicial(String numSolicitud, String token) {

        RestTemplateHelper rtDoc = new RestTemplateHelper();
        DecisionJudicial decisionJudicialRespuesta;

        decisionJudicialRespuesta = rtDoc.procesarPeticionSeguridad(endPointConsultarDecisionJud
                + numSolicitud, token, null, DecisionJudicial.class, HttpMethod.POST);

        return decisionJudicialRespuesta;
    }

    /**
     * Metodo que permite guardar la medida de proteccion
     *
     * @param medidaProteccion pojo con la informacion de Medida de Proteccion
     * @param token permiso de acceso
     * @return verdadero si es �xitosa la transacci�n en caso contrario falso
     */
    public boolean guardarMedidaProteccion(MedidaProteccion medidaProteccion, String token) {

        RestTemplateHelper rtAct = new RestTemplateHelper();
        boolean resp;

        resp = rtAct.procesarPeticionSeguridad(endPointGuardarMedidaPro,
                token, medidaProteccion, boolean.class, HttpMethod.POST);

        return resp;
    }

    /**
     *
     * Metodo que permite consultar una Medida de Proteccion dado un numero de
     * Solicitud
     *
     * @param numSolicitud String numero de solicitud
     * @param token permiso de acceso
     * @return pojo que contiene la informacion de Medida de Proteccion
     *
     */
    public MedidaProteccion consultarMedidaProteccion(String numSolicitud, String token) {

        RestTemplateHelper rtDoc = new RestTemplateHelper();
        MedidaProteccion medidaProteccionRespuesta;

        medidaProteccionRespuesta = rtDoc.procesarPeticionSeguridad(endPointConsultarMedidaPro + numSolicitud,
                token, null, MedidaProteccion.class, HttpMethod.GET);

        return medidaProteccionRespuesta;
    }

    /**
     * Metodo que permite guardar Extemporanea
     *
     * @param extemporanea pojo con la informacion de Extemporanea
     * @param token permiso de acceso
     * @return verdadero si es �xitosa la transacci�n en caso contrario falso
     */
    public boolean guardarExtemporanea(Extemporanea extemporanea, String token) {

        RestTemplateHelper rtAct = new RestTemplateHelper();
        boolean resp;

        resp = rtAct.procesarPeticionSeguridad(endPointGuardarExtemporanea, token,
                extemporanea, boolean.class, HttpMethod.POST);

        return resp;

    }

    /**
     * Metodo que permite consultar extemporanea
     *
     * @param numSolicitud String numero de solicitud
     * @param token permiso de acceso
     * @return pojo que contiene la informacion de Extemporanea
     */
    public Extemporanea consultarExtemporanea(String numSolicitud, String token) {

        RestTemplateHelper rtDoc = new RestTemplateHelper();
        Extemporanea extemporaneaRespuesta;

        extemporaneaRespuesta = rtDoc.procesarPeticionSeguridad(endPointConsultarExtemporanea + numSolicitud,
                token, null, Extemporanea.class, HttpMethod.GET);

        return extemporaneaRespuesta;
    }

    /**
     * Metodo que permite guardar Nacimiento
     *
     * @param nacimiento pojo con la informacion de Nacimiento
     * @param token permiso de acceso
     * @return verdadero si es éxitosa la transacci�n en caso contrario falso
     */
    public boolean guardarNacimiento(Nacimiento nacimiento, String token) {

        RestTemplateHelper rtAct = new RestTemplateHelper();
        boolean resp;

        resp = rtAct.procesarPeticionSeguridad(endPointGuardarNacimiento, token,
                nacimiento, boolean.class, HttpMethod.POST);

        return resp;

    }
    
    /**
     * Metodo que permite consultar nacimiento
     *
     * @param numSolicitud String numero de solicitud
     * @param token permiso de acceso
     * @return pojo que contiene la informacion de Nacimiento
     */
    public Nacimiento consultarNacimiento(String numSolicitud, String token) {

        RestTemplateHelper rtDoc = new RestTemplateHelper();
        Nacimiento nacimientoRespuesta;

        nacimientoRespuesta = rtDoc.procesarPeticionSeguridad(endPointConsultarNacimiento + numSolicitud,
                token, null, Nacimiento.class, HttpMethod.GET);

        return nacimientoRespuesta;
    }

    /**
     * Metodo que permite guardar el acta primigenia 
     * 
     * @param actaPrimigenia ActaPrimigenia Pojo con la informacion del acta primigenia
     * @param token permiso de acceso
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     */
    public ActaPrimigenia guardarActaPrimigenia(ActaPrimigenia actaPrimigenia, String token) {

        RestTemplateHelper rsActa = new RestTemplateHelper();
        ActaPrimigenia resp;

        resp = rsActa.procesarPeticionSeguridad(endPointGuardarActaPrimigenia, token,
                actaPrimigenia, ActaPrimigenia.class, HttpMethod.POST);

        return resp;
    }

    /**
     * Metodo que permite consultar el acta primigenia
     * 
     * @param numeroSolicitud String Numero de la solicitud
     * @param token permiso de acceso
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     */
    public ActaPrimigenia consultaActaPrimigenia(String numeroSolicitud, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        ActaPrimigenia permisoRespuesta;

        permisoRespuesta = rt.procesarPeticionSeguridad(endPointConsultarActaPrimigenia + numeroSolicitud,
                token, null, ActaPrimigenia.class, HttpMethod.GET);

        return permisoRespuesta;
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if (archivo.equals("ActaServicioCliente.properties")) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointBuscarActa = properties.getProperty("endPointBuscarActa");
        endPointActualizarActa = properties.getProperty("endPointActualizarActa");
        endPointBuscarActalista = properties.getProperty("endPointBuscarActalista");
        endPointValidarCertificado = properties.getProperty("endPointValidarCertificado");
        endPointExisteActa = properties.getProperty("endPointExisteActa");
        endPointBuscarNroActaPorSolic = properties.getProperty("endPointBuscarNroActaPorSolic");
        endPointGuardarOre = properties.getProperty("endPointGuardarOre");
        endPointConsultarOre = properties.getProperty("endPointConsultarOre");
        endPointActualizarEstatusActa = properties.getProperty("endPointActualizarEstatusActa");
        endPointGuardarInsercion = properties.getProperty("endPointGuardarInsercion");
        endPointConsultarInsercion = properties.getProperty("endPointConsultarInsercion");
        endPointGuardarDecisionJud = properties.getProperty("endPointGuardarDecisionJud");
        endPointConsultarDecisionJud = properties.getProperty("endPointConsultarDecisionJud");
        endPointGuardarMedidaPro = properties.getProperty("endPointGuardarMedidaPro");
        endPointConsultarMedidaPro = properties.getProperty("endPointConsultarMedidaPro");
        endPointGuardarExtemporanea = properties.getProperty("endPointGuardarExtemporanea");
        endPointConsultarExtemporanea = properties.getProperty("endPointConsultarExtemporanea");
        endPointGuardarNacimiento = properties.getProperty("endPointGuardarNacimiento");
        endPointConsultarNacimiento = properties.getProperty("endPointConsultarNacimiento");
        endPointGuardarActaPrimigenia = properties.getProperty("endPointGuardarActaPrimigenia");
        endPointConsultarActaPrimigenia = properties.getProperty("endPointConsultarActaPrimigenia");        
    } 
}
