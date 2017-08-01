package ve.gob.cne.sarc.participante.servicio.cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.participante.CartaConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;

@Component
@Configuration
@EnableAsync
@EnableScheduling
public class ParticipanteServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(ParticipanteServicioCliente.class);

    private static String endPointRegistrarParticipante;
    private static String endPointBuscarTipoParticipante;
    private static String endPointConsultarParticPorSolTip;
    private static String endPointConsultarParticPorActa;
    private static String endPointConsultarParticPorSolic;
    private static String endPointActualizarParticipante;
    private static String endPointRegistrarDeclaracionJurada;
    private static String endPointRegistrarCartaCComunal;
    private static String endPointConsultarDecJurada;
    private static String endPointRegParticipantePorSol;
    private static String endPointRegParticipantePorDoc;

    private AdministradorPropiedades properties;

    RestTemplateHelper rth = new RestTemplateHelper();

    public ParticipanteServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.registrarEscucha(this);
        init();
    }

    public Solicitud insertarParticipantes(Solicitud solicitud, String token) {

        Solicitud solicitudRespuesta;

        solicitudRespuesta = rth.procesarPeticionSeguridad(endPointRegistrarParticipante,
                token, solicitud, Solicitud.class, HttpMethod.POST);

        return solicitudRespuesta;
    }

    /**
     * Cliente de la operacion buscar tipo participante por tramite, ; y si se
     * desean los declarantes unicamente para dicho tramite.
     *
     * @param codigoTramite String codigo del tramite
     * @param tipoDeclarante String que contiene tipo de declarante
     * @param token permiso de acceso
     * @return Lista de {@link TipoParticipante} lista de objeto
     * TipoParticipante del modelo ontologico
     */
    public List<TipoParticipante> buscarTipoParticipantePorTramite(String codigoTramite,
            String tipoDeclarante, String token) {

        List<TipoParticipante> tipoParticipanteRespuesta;

        tipoParticipanteRespuesta = rth.procesarPeticionSeguridadLista(endPointBuscarTipoParticipante
                + codigoTramite + "/" + tipoDeclarante, token, null,
                TipoParticipante[].class, HttpMethod.GET);

        return tipoParticipanteRespuesta;
    }

    /**
     * Cliente de la operacion consultar participante por tipo
     *
     * @param numeroSolicitud String numero de solicitud
     * @param codigoTipo String codigo tipo de participante
     * @return Participante
     */
    public List<Participante> consultarParticPorTipo(String numeroSolicitud, String[] codigoTipo, String token) {

        List<Participante> participanteRespuesta;

        participanteRespuesta = rth.
                procesarPeticionSeguridadLista(endPointConsultarParticPorSolTip + numeroSolicitud
                        + "/" + Arrays.deepToString(codigoTipo), token, null,
                        Participante[].class, HttpMethod.GET);

        return participanteRespuesta;
    }

    /**
     * Cliente de la operacion consultar participantes por solicitud
     *
     * @param numeroSolicitud String numero de solicitud
     * @param esDeclarante
     * @return Lista de {@link Participante}
     */
    public List<Participante> consultarParticPorSolicitud(String numeroSolicitud, String esDeclarante, String token) {

        List<Participante> participanteRespuesta;

        participanteRespuesta = rth.
                procesarPeticionSeguridadLista(endPointConsultarParticPorSolic
                        + numeroSolicitud + "/" + esDeclarante, token, null,
                        Participante[].class, HttpMethod.GET);

        return participanteRespuesta;
    }

    /**
     * Cliente de la operacion consultar participantes por acta
     *
     * @param numeroActa String numero de acta
     * @param token permiso de acceso
     * @return Lista de {@link Participante}
     */
    public List<Participante> consultarParticPorActa(String numeroActa, String token) {

        List<Participante> participanteRespuesta;

        participanteRespuesta = rth.
                procesarPeticionSeguridadLista(endPointConsultarParticPorActa + numeroActa,
                        token, null, Participante[].class, HttpMethod.GET);

        return participanteRespuesta;
    }

    /**
     * Cliente de la operacion actualizar participante
     *
     * @param participante Objeto que contiene los datos de un participante
     * @param nroSolic String Numero de solicitud
     * @param token permiso de acceso
     * @return Objeto de tipo Participante
     */
    public Participante actualizarParticipante(String nroSolic, Participante participante, String token) {

        Participante participanteRespuesta;

        participanteRespuesta = rth.procesarPeticionSeguridad(endPointActualizarParticipante
                + nroSolic, token, participante, Participante.class, HttpMethod.POST);

        return participanteRespuesta;
    }

    /**
     * Cliente para la operacion registrar declaracion jurada
     *
     * @param declaracionJurada Objeto de tipo DeclaracionJurada que contiene la
     * informacion de una declaracion jurada
     * @return Verdadero si guardo la declaracion jurada
     */
    public boolean registrarDeclaracionJurada(DeclaracionJurada declaracionJurada, String token) {

        boolean response;

        response = rth.procesarPeticionSeguridad(endPointRegistrarDeclaracionJurada, token,
                declaracionJurada, boolean.class, HttpMethod.POST);

        return response;
    }

    /**
     * Cliente para la operacion registrar carta consejo comunal del
     * participante
     *
     * @param cartaConsejoComunal Objeto de tipo CartaConsejoComunal que
     * contiene la informacion de una carta consejo comunal de un participante
     * @return Verdadero si guardo la carta del consejo comunal
     */
    public boolean registrarConsejoComunal(CartaConsejoComunal cartaConsejoComunal, String token) {

        boolean response;

        response = rth.procesarPeticionSeguridad(endPointRegistrarCartaCComunal, token,
                cartaConsejoComunal, boolean.class, HttpMethod.POST);
        		LOG.info("Respuesta de registrarConsejoComunal: "+response);
        return response;
    }

    /**
     * Cliente de la operacion consultar declaracion Jurada
     *
     * @param numSolicitud String numero de solicitud
     * @param token
     * @return Lista de {@link Participante}
     */
    public List<DeclaracionJurada> consultarDeclaracionJurada(String numSolicitud, String token) {

        List<DeclaracionJurada> declaracionRespuesta;

        declaracionRespuesta = rth.procesarPeticionSeguridadLista(endPointConsultarDecJurada + numSolicitud,
                token, null, DeclaracionJurada[].class, HttpMethod.GET);

        return declaracionRespuesta;
    }

    /**
     * Cliente para la operacion registrar un participante por solicitud
     *
     * @param numSolicitud, String que describe el numero de solicitud
     * @param participante
     * @param token permiso de acceso
     * @return Verdadero si guardo la carta del consejo comunal
     */
    public Participante registrarParticPorSolicitud(String numSolicitud, Participante participante, String token) {

        Participante resp;

        resp = rth.procesarPeticionSeguridad(endPointRegParticipantePorSol
                + numSolicitud, token, participante, Participante.class, HttpMethod.GET);

        return resp;
    }

    /**
     * Busca un listado de actas con el participante por su numero de documento
     *
     * @param numeroDoc Documento de identidad
     * @param flag Bandera que indica el tipo de acta, true actas propias, false
     * actas relacionas
     * @param token token de acceso
     * @return Listado de actas
     */
    public List<Acta> buscarParticipantesDocumento(String numeroDoc, boolean flag, String token) {
        List<Acta> actas;

        LOG.info("endPoint " + endPointRegParticipantePorDoc + numeroDoc + "/" + flag);

        actas = rth.procesarPeticionSeguridadLista(endPointRegParticipantePorDoc + numeroDoc + "/" + flag,
                token, null, Acta[].class, HttpMethod.GET);

        return actas;
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if (archivo.equals("ParticipanteServicioCliente.properties")) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointRegistrarParticipante = properties.getProperty("endPointRegistrarParticipante");
        endPointBuscarTipoParticipante = properties.getProperty("endPointBuscarTipoParticipante");
        endPointConsultarParticPorSolTip = properties.getProperty("endPointConsultarParticPorSolTip");
        endPointConsultarParticPorActa = properties.getProperty("endPointConsultarParticPorActa");
        endPointConsultarParticPorSolic = properties.getProperty("endPointConsultarParticPorSolic");
        endPointActualizarParticipante = properties.getProperty("endPointActualizarParticipante");
        endPointRegistrarDeclaracionJurada = properties.getProperty("endPointRegistrarDeclaracionJurada");
        endPointRegistrarCartaCComunal = properties.getProperty("endPointRegistrarCartaCComunal");
        endPointConsultarDecJurada = properties.getProperty("endPointConsultarDecJurada");
        endPointRegParticipantePorSol = properties.getProperty("endPointRegParticipantePorSol");
        endPointRegParticipantePorDoc = properties.getProperty("endPointRegParticipantePorDoc");
    }
}
