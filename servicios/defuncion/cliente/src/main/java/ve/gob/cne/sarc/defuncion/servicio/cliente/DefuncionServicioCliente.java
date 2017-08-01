package ve.gob.cne.sarc.defuncion.servicio.cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.comunes.defuncion.PermisoInhCre;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * DefuncionServicioCliente.java
 *
 * @descripcion [Cliente para la ejecucion de los servicios de Defuncion]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 * @version 1.0
 */
@Component
@Configuration
@EnableAsync
@EnableScheduling
public class DefuncionServicioCliente implements EscuchaAdministradorPropiedades {

    private AdministradorPropiedades properties;

    private static final Logger LOG = LoggerFactory.getLogger(DefuncionServicioCliente.class);

    public static String endPointValidarCetificadoDefuncion;
    public static String endPointGuardarPermisoInhCre;
    public static String endPointBuscarProxNumPermiso;
    public static String endPointConsultarDefuncion;
    public static String endPointConsultaPermisoInhCre;
    public static String endPointGuardarDefuncion;

    public DefuncionServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.registrarEscucha(this);
        LOG.info("Cargando propiedades de Defuncion");
        init();
    }

    /**
     * Valida que el numero del certificado se encuentre rigistrado
     *
     * @param numeroCertificado long que contiene el numero del certificado
     * medico
     * @param token permiso de acceso
     * @return Verdadero si existe en caso contrario falso
     */
    public Boolean validarCertificadoMedicoDefuncion(long numeroCertificado, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Boolean response;

        response = rt.procesarPeticionSeguridad(endPointValidarCetificadoDefuncion
                + numeroCertificado, token, null, Boolean.class, HttpMethod.GET);

        return response;
    }

    /**
     * Cliente de la operacion guardarPermisoInhCre
     *
     * @param codOficina String codigo de la oficina
     * @param token permiso de acceso
     * @return long con el proximo numero de permiso
     */
    public Long buscarProxNumPermiso(String codOficina, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Long response;

        response = rt.procesarPeticionSeguridad(endPointBuscarProxNumPermiso
                + codOficina, token, null, Long.class, HttpMethod.GET);

        return response;
    }

    /**
     * Cliente de la operacion buscarProxNumPermiso
     *
     * @param permisoInhCre pojo que contiene PermisoInhCre
     * @param token permiso de acceso
     * @return PermisoInhCre pojo que contiene informacion de PermisoInhCre
     */
    public PermisoInhCre guardarPermisoInhCre(PermisoInhCre permisoInhCre, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        PermisoInhCre permisoRespuesta;

        permisoRespuesta = rt.procesarPeticionSeguridad(endPointGuardarPermisoInhCre,
                token, permisoInhCre, PermisoInhCre.class, HttpMethod.POST);

        return permisoRespuesta;
    }

    /**
     * Consulta el permiso de Inhumacion y creamcion por el numero de solicitud
     *
     * @param numeroSolicitud que describe el numero de Solicitud
     * @param token permiso de acceso
     * @return PermisoInhCre pojo que contiene el PermisoInhCre
     */
    public PermisoInhCre consultaPermisoInhCre(String numeroSolicitud, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        PermisoInhCre permisoRespuesta;

        permisoRespuesta = rt.procesarPeticionSeguridad(endPointConsultaPermisoInhCre
                + numeroSolicitud, token, null, PermisoInhCre.class, HttpMethod.GET);

        return permisoRespuesta;
    }

    /**
     * Metodo que permite guardar Defuncion
     *
     * @param defuncion pojo con la informacion de Defuncion
     * @param token permiso de acceso
     * @return verdadero si es éxitosa la transacci�n en caso contrario falso
     */
    public boolean guardarDefuncion(Defuncion defuncion, String token) {

        RestTemplateHelper rtAct = new RestTemplateHelper();
        boolean resp;

        resp = rtAct.procesarPeticionSeguridad(endPointGuardarDefuncion, token,
                defuncion, boolean.class, HttpMethod.POST);

        return resp;

    }

    /**
     * Consulta la defuncion dado el numero de solicitud
     *
     * @param numeroSolicitud que describe el numero de Solicitud
     * @return objeto de tipo Defuncion
     */
    @SuppressWarnings("unchecked")
    public Defuncion consultarDefuncion(String numeroSolicitud, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Defuncion defuncion;

        defuncion = rt.procesarPeticionSeguridad(endPointConsultarDefuncion
                + numeroSolicitud, token, null, Defuncion.class, HttpMethod.GET);

        return defuncion;
    }

    private void init() throws GenericException {
        endPointValidarCetificadoDefuncion = properties.getProperty("endPointValidarCetificadoDefuncion");
        endPointGuardarPermisoInhCre = properties.getProperty("endPointGuardarPermisoInhCre");
        endPointBuscarProxNumPermiso = properties.getProperty("endPointBuscarProxNumPermiso");
        endPointConsultaPermisoInhCre = properties.getProperty("endPointConsultaPermisoInhCre");
        endPointGuardarDefuncion = properties.getProperty("endPointGuardarDefuncion");
        endPointConsultarDefuncion = properties.getProperty("endPointConsultarDefuncion");
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if (archivo.equals("DefuncionServicioCliente.properties")) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }
}
