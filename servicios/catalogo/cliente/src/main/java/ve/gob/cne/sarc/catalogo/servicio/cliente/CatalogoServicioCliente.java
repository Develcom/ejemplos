package ve.gob.cne.sarc.catalogo.servicio.cliente;

import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.comunes.catalogo.Ciudad;
import ve.gob.cne.sarc.comunes.catalogo.ComunidadIndigena;
import ve.gob.cne.sarc.comunes.catalogo.EntePublico;
import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Nacionalidad;
import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;
import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * CatalogoServicioCliente.java
 *
 * @descripcion [Servicio Catalogo Descripcion: API Cliente para el acceso a los
 * servicios de Catalogo Consumida por: Pantallas]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Component
@Configuration
@EnableAsync
@EnableScheduling
public class CatalogoServicioCliente implements EscuchaAdministradorPropiedades {

    private static String endPointTipoParticipantePorCodigo;
    private static String endPointEntePublicoTodos;
    private static String endPointTipoDocumentoTodos;
    private static String endPointModuloPorOficina;
    private static String endPointTramitePorModulo;
    private static String endPointParroquiaTodas;
    private static String endPointEstadoTodos;
    private static String endPointPaisTodos;
    private static String endPointCiudad;
    private static String endPointMunicipio;
    private static String endPointNacionalidad;
    private static String endPointOcupacion;
    private static String endPointEstadoPorPais;
    private static String endPointMunicipioPorEstado;
    private static String endPointParroquiaPorMunicipio;
    private static String endPointComunidadIndigena;
    private static String endPointDocAutenticados;
    private static String endPointOficinaTodos;
    private static String endPointProximoNroConsecutivo;

    private AdministradorPropiedades properties;

    private static final Logger LOG = LoggerFactory.getLogger(CatalogoServicioCliente.class);

    public CatalogoServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME",
                "conf/general/config-Manage.properties");
        properties.registrarEscucha(this);
        LOG.info("Cargando propiedades de Catalogo");
        init();
    }

    /**
     * Metodo de acceso a los Servicios de Catalogo de consulta de Ente Publico
     *
     * @param token permiso de acceso
     * @return List<EntePublico> Lista de EntePublico
     *
     */
    public List<EntePublico> consultarEntePublicoTodos(String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        List<EntePublico> entePublicos;
        
        entePublicos = rth.procesarPeticionSeguridadLista(endPointEntePublicoTodos, token,
                null, EntePublico[].class, HttpMethod.GET);

        return entePublicos;
    }

    /**
     * Consulta un tipo de documento por su tipo
     *
     * @param tipo String que describe al tipo de documento
     * @param token permiso de acceso
     * @return List<TipoDocumento> lista de Documentos
     */
    public List<TipoDocumento> consultarTipoDocumentoTodos(String tipo, String token) {

        RestTemplateHelper rtDoc = new RestTemplateHelper();
        List<TipoDocumento> tipoDocumentoRespuesta;      
        
        tipoDocumentoRespuesta = rtDoc.procesarPeticionSeguridadLista(
                endPointTipoDocumentoTodos + tipo, token,
                null, TipoDocumento[].class, HttpMethod.GET);

        return tipoDocumentoRespuesta;
    }

    /**
     * Busca el modulo por el codigo de la oficina
     *
     * @param codigo String de codigo de oficina
     * @param token permiso de acceso
     * @return List<Modulo> Lista de Modulos
     */
    public List<Modulo> consultarModuloPorOficina(String codigo, String token) {

        RestTemplateHelper rtModulo = new RestTemplateHelper();
        List<Modulo> moduloRespuesta;
        
        moduloRespuesta = rtModulo.procesarPeticionSeguridadLista(
                endPointModuloPorOficina + codigo, token, null,
                Modulo[].class, HttpMethod.GET);
        return moduloRespuesta;
    }

    /**
     * Buscar el tramite por el codigo del modulo
     *
     * @param codigoModulo String codigo del modulo del tramite
     * @param codTipoSolicitante String codigo del tipo de Solicitante
     * @param token permiso de accceso
     * @return List<Tramite> Lista de Tramites
     */
    public List<Tramite> consultarTramitePorModulo(String codigoModulo,
            String codTipoSolicitante, String token) {

        RestTemplateHelper rtTra = new RestTemplateHelper();
        List<Tramite> tramiteRespuesta;
        
        tramiteRespuesta = rtTra.procesarPeticionSeguridadLista(
                endPointTramitePorModulo + codigoModulo + "/" + codTipoSolicitante, token,
                null, Tramite[].class, HttpMethod.GET);

        return tramiteRespuesta;
    }

    /**
     * Busca un tipo de participante por el codigo
     *
     * @param codigo String codigo de Tipo Participante
     * @param isDeclarante boolean que determina si es tipo declarante
     * @param token permiso de acceso
     * @return TipoParticipante contiene la informacion de TipoParticipante
     */
    public TipoParticipante consultarTipoParticipantePorCodigo(String codigo,
            boolean isDeclarante, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        TipoParticipante tipoParticipanteRespuesta;
        
        tipoParticipanteRespuesta = rt.procesarPeticionSeguridad(
                endPointTipoParticipantePorCodigo + codigo + "/" + isDeclarante,
                token, null, TipoParticipante.class, HttpMethod.GET);

        return tipoParticipanteRespuesta;
    }

    /**
     * Busca todas las parroquias
     *
     * @param token permiso de acceso
     * @return List<Parroquia> Lista de Parroquias
     */
    public List<Parroquia> consultarParroquiasTodas(String token) {

        RestTemplateHelper rtP = new RestTemplateHelper();
        List<Parroquia> parroquiasRespuesta;
        
        parroquiasRespuesta = rtP.procesarPeticionSeguridadLista(endPointParroquiaTodas, token,
                null, Parroquia[].class, HttpMethod.GET);
        return parroquiasRespuesta;
    }

    /**
     * Busca todos los estados
     *
     * @param token permiso de acceso
     * @return List<Estado> lista de estados
     */
    public List<Estado> consultarEstadoTodos(String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        List<Estado> estadoRespuesta;
        
        estadoRespuesta = rt.procesarPeticionSeguridadLista(endPointEstadoTodos,
                token, null, Estado[].class, HttpMethod.GET);
        return estadoRespuesta;
    }

    /**
     * Busca todos los paises
     *
     * @param token permiso de acceso
     * @return List<Pais> lista de Paises
     */
    public List<Pais> consultarPaisTodos(String token) {

        RestTemplateHelper rtPais = new RestTemplateHelper();
        List<Pais> paisRespuesta;
        
        paisRespuesta = rtPais.procesarPeticionSeguridadLista(endPointPaisTodos,
                token, null, Pais[].class, HttpMethod.GET);
        return paisRespuesta;
    }

    /**
     * Busca todas las ciudades
     *
     * @param token permiso de acceso
     * @return List<Ciudad> lista de Ciudades
     */
    public List<Ciudad> consultarCiudadTodos(String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        List<Ciudad> ciudadRespuesta;
        
        ciudadRespuesta = rt.procesarPeticionSeguridadLista(endPointCiudad,
                token, null, Ciudad[].class, HttpMethod.GET);
        return ciudadRespuesta;
    }

    /**
     * Busca todos los municipios
     *
     * @param token permiso de acceso
     * @return List<Municipio> lista de municipios
     */
    public List<Municipio> consultarMunicipioTodos(String token) {

        RestTemplateHelper rtMun = new RestTemplateHelper();
        List<Municipio> municipioRespuesta;

        municipioRespuesta = rtMun.procesarPeticionSeguridadLista(endPointMunicipio,
                token, null, Municipio[].class, HttpMethod.GET);

        return municipioRespuesta;
    }

    /**
     * Busca todas las nacionalidades
     *
     * @param token permiso de acceso
     * @return List<Nacionalidad> lista de las nacionalidades
     */
    public List<Nacionalidad> consultarNacionalidadTodos(String token) {

        RestTemplateHelper rtNac = new RestTemplateHelper();
        List<Nacionalidad> nacionalidadRespuesta;

        nacionalidadRespuesta = rtNac.procesarPeticionSeguridadLista(endPointNacionalidad,
                token, null, Nacionalidad[].class, HttpMethod.GET);

        return nacionalidadRespuesta;
    }

    /**
     * Busca las ocupaciones
     *
     * @param token permiso de acceso
     * @return List<Ocupacion> lista de ocupacion
     */
    public List<Ocupacion> consultarOcupacionTodos(String token) {

        RestTemplateHelper rtOcu = new RestTemplateHelper();
        List<Ocupacion> ocupacionRespuesta;

        ocupacionRespuesta = rtOcu.procesarPeticionSeguridadLista(endPointOcupacion,
                token, null, Ocupacion[].class, HttpMethod.GET);

        return ocupacionRespuesta;
    }

    /**
     * Busca todos los estados de un pais por el codigo de un pais
     *
     * @param codigo String de codigo de un pais
     * @param token permiso de acceso
     * @return List<Estado> lista de estados
     */
    public List<Estado> consultarEstadoPorPais(String codigo, String token) {

        RestTemplateHelper rtEstado = new RestTemplateHelper();
        List<Estado> estadoPorPaisRespuesta;

        estadoPorPaisRespuesta = rtEstado.procesarPeticionSeguridadLista(endPointEstadoPorPais
                + codigo, token, null, Estado[].class, HttpMethod.GET);

        return estadoPorPaisRespuesta;
    }

    /**
     * Busca los municipios de un estado por el codigo
     *
     * @param codigo String que describe el codigo de Estado
     * @param token permiso de acceso
     * @return List<Municipio> lista de municipios
     */
    public List<Municipio> consultarMunicipioPorEstado(String codigo, String token) {

        RestTemplateHelper rth = new RestTemplateHelper();
        List<Municipio> municipioPorEstadoRespuesta;

        municipioPorEstadoRespuesta = rth.procesarPeticionSeguridadLista(endPointMunicipioPorEstado
                + codigo, token, null, Municipio[].class, HttpMethod.GET);

        return municipioPorEstadoRespuesta;
    }

    /**
     * Busca las parroquia de un municipio por el codigo
     *
     * @param codigo String de codigo de Municipio
     * @param token permiso de acceso
     * @return List<Parroquia> lista de Parroquias
     */
    public List<Parroquia> consultarParroquiaPorMunicipio(String codigo, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        List<Parroquia> parroquiaPorMunicipioRespuesta;

        parroquiaPorMunicipioRespuesta = rt.procesarPeticionSeguridadLista(endPointParroquiaPorMunicipio
                + codigo, token, null, Parroquia[].class, HttpMethod.GET);

        return parroquiaPorMunicipioRespuesta;
    }

    /**
     * Busca todas la comunidades infigenas
     *
     * @param token permiso de acceso
     * @return List<ComunidadIndigena> lista de comunidad indigena
     */
    public List<ComunidadIndigena> consultarComunidadIndigenaTodos(String token) {

        RestTemplateHelper rtCI = new RestTemplateHelper();
        List<ComunidadIndigena> comunidadIndigenaRespuesta;

        comunidadIndigenaRespuesta = rtCI.procesarPeticionSeguridadLista(endPointComunidadIndigena,
                token, null, ComunidadIndigena[].class, HttpMethod.GET);

        return comunidadIndigenaRespuesta;
    }

    /**
     * Consulta todos los documentos autenticados
     *
     * @param token permiso de acceso
     * @return LinkedHashMap<String, String> map que permite almacenar valores
     *
     */
    public LinkedHashMap consultarDocumentosAutenticados(String token) {

        RestTemplateHelper rtDA = new RestTemplateHelper();
        LinkedHashMap documentosRespuesta;

        documentosRespuesta = rtDA.procesarPeticionSeguridad(endPointDocAutenticados,
                token, null, LinkedHashMap.class, HttpMethod.GET);

        return documentosRespuesta;
    }

    /**
     * Busca todos las oficinas
     *
     * @param token permiso de acceso
     * @return List<Oficina> lista de Oficinas
     */
    public List<Oficina> consultarOficinasTodos(String token) {

        RestTemplateHelper rtOfic = new RestTemplateHelper();
        List<Oficina> oficinaRespuesta;

        oficinaRespuesta = rtOfic.procesarPeticionSeguridadLista(endPointOficinaTodos,
                token, null, Oficina[].class, HttpMethod.GET);

        return oficinaRespuesta;
    }

    /**
     * Obtiene el proximo nro consecutivo
     *
     * @param codigoOficina el codigo de la oficina
     * @param token permiso de acceso
     * @return long proximo nro consecutivo
     */
    public long proximoNroConsecutivo(String codigoOficina, String token) {

        RestTemplateHelper rtProx = new RestTemplateHelper();
        Long nro;

        nro = rtProx.procesarPeticionSeguridad(endPointProximoNroConsecutivo + codigoOficina,
                token, null, Long.class, HttpMethod.GET);

        return nro;
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if (archivo.equals("CatalogoServicioCliente.properties")) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointTipoParticipantePorCodigo = properties.getProperty("endPointTipoParticipantePorCodigo");
        endPointEntePublicoTodos = properties.getProperty("endPointEntePublicoTodos");
        endPointTipoDocumentoTodos = properties.getProperty("endPointTipoDocumentoTodos");
        endPointModuloPorOficina = properties.getProperty("endPointModuloPorOficina");
        endPointTramitePorModulo = properties.getProperty("endPointTramitePorModulo");
        endPointParroquiaTodas = properties.getProperty("endPointParroquiaTodas");
        endPointEstadoTodos = properties.getProperty("endPointEstadoTodos");
        endPointPaisTodos = properties.getProperty("endPointPaisTodos");
        endPointCiudad = properties.getProperty("endPointCiudad");
        endPointMunicipio = properties.getProperty("endPointMunicipio");
        endPointNacionalidad = properties.getProperty("endPointNacionalidad");
        endPointOcupacion = properties.getProperty("endPointOcupacion");
        endPointEstadoPorPais = properties.getProperty("endPointEstadoPorPais");
        endPointMunicipioPorEstado = properties.getProperty("endPointMunicipioPorEstado");
        endPointParroquiaPorMunicipio = properties.getProperty("endPointParroquiaPorMunicipio");
        endPointComunidadIndigena = properties.getProperty("endPointComunidadIndigena");
        endPointDocAutenticados = properties.getProperty("endPointDocAutenticados");
        endPointOficinaTodos = properties.getProperty("endPointOficinaTodos");
        endPointProximoNroConsecutivo = properties.getProperty("endPointProximoNroConsecutivo");
    }
    
}
