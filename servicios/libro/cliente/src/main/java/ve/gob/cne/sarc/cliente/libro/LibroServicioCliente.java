package ve.gob.cne.sarc.cliente.libro;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.comunes.oficina.Tomo;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * Constantes.java
 *
 * @descripcion Clase cliente del servicio de libro
 * @version 1.0 11/10/2015
 * @author Oscar Eubieda
 */
@Component
public class LibroServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(LibroServicioCliente.class);

    private String endPointValidarLibrosActivosPorOficina;
    private String endPointValidarLibroDiarioActivoPorOficina;
    private String endPointBuscarLibrosEstatusPorTipoOficina;
    private String endPointCrearLibro;
    private String endPointActalizarLibro;
    private String endConsultarTodos;
    private String endPointAperturaCierreLibro;
    private String endPointValidarTopeTomo;
    private String endPointObtenerTipoLibro;
    private String endPointBuscarTomoActual;
    private String endPointValidarActasPorTomo;
    private String endPointCrearLibroDiario;
    private String endPointActualizarLibroDiario;
    private String endPointActualizarLibroCivil;
    private AdministradorPropiedades properties;

    public LibroServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME", 
        "conf/general/config-Manage.properties");
        properties.desRegistrarEscucha(this);
        init();
    }

    /**
     * Método buscarLibrosEstatusPorTipoOficina que permite recuperar la lista
     * de libros por oficina, estatus y anio
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @param estatus el estatus de libros a consultar.
     * @param anio el año de libros a consultar.
     * @param token permiso de acceso
     * @return Colección de objetos <tt>Libro</tt> en formato <tt>JSON</tt>.
     */
    public List<Libro> buscarLibrosEstatusPorTipoOficina(String codigoOfic, 
            String estatus, int anio, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        List<Libro> libros;
        
        libros = rt.procesarPeticionSeguridadLista(
                endPointBuscarLibrosEstatusPorTipoOficina + codigoOfic + "/" + estatus + "/" + anio, 
                token, null, Libro[].class, HttpMethod.GET);
        

        return libros;
    }

    /**
     * Método validarLibrosActivosPorOficina que permite validar los libros para
     * una oficina, anio, estatus y tipo de libro dado
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @param estatus el estatus de libros a consultar.
     * @param anio el año de libros a consultar.
     * @param token permiso de acceso
     * @return <tt>boolean</tt> que indica la validez del libro, en formato
     * <tt>JSON</tt>.
     */
    public Boolean validarLibrosActivosPorOficina(String codigoOfic, 
            String estatus, int anio, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Boolean respuesta;

        respuesta = rt.procesarPeticionSeguridad(
                endPointValidarLibrosActivosPorOficina
                + codigoOfic + "/" + estatus + "/" + anio, token,
                null, Boolean.class, HttpMethod.GET);


        return respuesta;
    }
    
     /**
     * Método validarLibroDiarioActivoPorOficina que permite validar el libro diario para una oficina, dia y 
     * estatus 
     * @param codOficina el codigo de oficina a consultar.
     * @param fecha fecha del libro diario a consultar.
     * @param estatus estatus del libro diario a consultar.
     * @param token permiso de acceso
     * @return <tt>boolean</tt> que indica la validez del libro, en formato <tt>JSON</tt>.
     */
 
    public Boolean validarLibroDiarioActivoPorOficina (String codOficina, String fecha, long estatus, String token) {
     
        RestTemplateHelper rt = new RestTemplateHelper();
        Boolean respuesta;

        respuesta = rt.procesarPeticionSeguridad(
                endPointValidarLibroDiarioActivoPorOficina
                + codOficina + "/" + fecha + "/" + estatus, token,
                null, Boolean.class, HttpMethod.GET);


        return respuesta;
    }

    /**
     * Método crearLibro que permite actualizar un nuevo libro
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @param token permiso de acceso
     * @return <tt>boolean</tt> que indica si la operación de ha completado con
     * éxito, en formato <tt>JSON</tt>.
     */
    public Boolean actualizarLibro(Libro libro, String token) {

        RestTemplateHelper rtAct = new RestTemplateHelper();
        Boolean resp;

        resp = rtAct.procesarPeticionSeguridad(endPointActalizarLibro, token, 
                null, Boolean.class, HttpMethod.PUT);


        return resp;
    }

    /**
     * Método crearLibro que permite inicializar un nuevo libro
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @param token permiso de acceso
     * @return <tt>boolean</tt> que indica si la operación de ha completado con
     * éxito, en formato <tt>JSON</tt>.
     */
    public Boolean crearLibro(Libro libro, String token) {

        RestTemplateHelper rtCrearLibro = new RestTemplateHelper();
        Boolean resp;

        resp = rtCrearLibro.procesarPeticionSeguridad(endPointCrearLibro, token, 
                libro, Boolean.class, HttpMethod.POST);

        return resp;

    }

    /**
     * Método que permite obtener todos los libros
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @param token permiso de acceso
     * @return Colección de objetos <tt>Libro</tt> en formato <tt>JSON</tt>.
     */
    public List<Libro> consultarTodos(String codigoOfic, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        List<Libro> libros;

        libros = rt.procesarPeticionSeguridadLista(
                endConsultarTodos + codigoOfic, token, null,
                Libro[].class, HttpMethod.GET);

        return libros;
    }

    /**
     * Método aperturaCierreLibro que permite aperturar o cerrar un libro
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @param token permiso de acceso
     * @return <tt>boolean</tt> que indica si la operación de ha completado con
     * éxito, en formato <tt>JSON</tt>.
     */
    public Boolean aperturaCierreLibro(Libro libro, String token) {

        RestTemplateHelper rtAp = new RestTemplateHelper();
        Boolean resp;

        resp = rtAp.procesarPeticionSeguridad(endPointAperturaCierreLibro, token, 
                libro, Boolean.class, HttpMethod.PUT);

        return resp;

    }

    /**
     * Valida el tope del numero del Tomo para un tipo libro activo en un año y
     * oficina dada
     *
     * @param codigoOfic String codigo de oficina.
     * @param tipoLibro String codigo de tipo de libro.
     * @param anio integer año del libro.
     * @param token permiso de acceso
     * @return Verdadero si el Numero del Tomo llego al tope establecido.
     */
    public Boolean validarTopeTomo(String codigoOfic, String tipoLibro, int anio, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Boolean response;

        response = rt.procesarPeticionSeguridad(
                endPointValidarTopeTomo
                + codigoOfic + "/" + tipoLibro + "/" + anio, token,
                null, Boolean.class, HttpMethod.GET);

        return response;
    }

    /**
     * Busca el codigo del tipo libro dado un codigo de tramite
     *
     * @param codigoTramite objeto String que contiene el codigo del tramite.
     * @param token permiso de acceso
     * @return Objeto String con el codigo del Tipo Libro
     */
    public String obtenerTipoLibro(String codigoTramite, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        String codigo;

        codigo = rt.procesarPeticionSeguridad(endPointObtenerTipoLibro
                + codigoTramite, token, null, String.class, HttpMethod.GET);

        return codigo;
    }

    /**
     * Busca el tomo actual dado el codigo de la oficina, el estatus, el año y
     * el codigo del tipo libro.
     *
     * @param codOficina String codigo de la oficina
     * @param estatus String estatus del libro
     * @param anio int año que se requiere consultar
     * @param codTipoLibro String codigo del tipo libro
     * @param token permso de acceso
     * @return Objeto String con el numero del tomo actual
     */
    public Tomo buscarTomoActual(String codOficina, String estatus, int anio, 
            String codTipoLibro, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Tomo tomo;

        tomo = rt.procesarPeticionSeguridad(
                endPointBuscarTomoActual
                + codOficina + "/" + estatus + "/" + anio + "/" + codTipoLibro,
                token, null, Tomo.class, HttpMethod.GET);

        return tomo;
    }

    /**
     * Valida el tope de numeros de acta por tomo dado un codigo de oficina, un
     * codigo de tipo libro, un año y un tomo.
     *
     * @param codOficina String codigo de la oficina
     * @param codTipoLibro String codigo del tipo libro
     * @param anio int año del libro
     * @param numeroTomo String numero de tomo a validar
     * @param token permiso de acceso
     * @return Verdadero si el numero de actas por tomo dado una oficina, tipo
     * libro, año y tomo
     */
    public Boolean validarActasPorTomo(String codOficina, String codTipoLibro, 
            int anio, String numeroTomo, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Boolean respons;
        
        respons = rt.procesarPeticionSeguridad(
                endPointValidarActasPorTomo
                + codOficina + "/" + codTipoLibro + "/" + anio + "/" + numeroTomo,
                token, null, Boolean.class, HttpMethod.GET);
        
        return respons;

    }

    /**
     * Crea el libro diario dado el numero de una solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @param token permiso de acceso
     * @return Verdadero si cre&oacute; satisfactoriamente el libro diario, en
     * caso contrario devuelve falso
     */
    public Boolean crearLibroDiario(String numeroSolicitud, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();
        Boolean resp;

        resp = rt.procesarPeticionSeguridad(endPointCrearLibroDiario
                + numeroSolicitud, token, null, Boolean.class, HttpMethod.POST);

        return resp;
    }

    /**
     * Actualiza el libro diario dado un numero de solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @param codigoEstatus String Codigo del estatus
     * @param token permiso de acceso
     * @return Verdadero si actualiz&oacute; satisfactoriamente el libro diario,
     * en caso contrario devuelve falso
     */
    public Boolean actualizarLibroDiario(String numeroSolicitud, String codigoEstatus, String token) {

        RestTemplateHelper rtDiario = new RestTemplateHelper();
        Boolean resp;

        resp = rtDiario.procesarPeticionSeguridad(endPointActualizarLibroDiario
                + numeroSolicitud + "/" + codigoEstatus, token, null, Boolean.class, HttpMethod.POST);


        return resp;
    }

    /**
     * Cliente de la operacion actualizar libro Civil
     *
     * @param acta Acta
     * @param token permiso de acceso
     * @return booelan}
     */
    public Boolean actualizarLibroCivil(Acta acta, String token) {

        RestTemplateHelper rtLibro = new RestTemplateHelper();
        Boolean resp;

        resp = rtLibro.procesarPeticionSeguridad(endPointActualizarLibroCivil, token,
                acta, Boolean.class, HttpMethod.POST);

        return resp;

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

        endPointValidarLibrosActivosPorOficina = properties.getProperty("endPointValidarLibrosActivosPorOficina");
        endPointValidarLibroDiarioActivoPorOficina = properties.getProperty("endPointValidarLibroDiarioActivoPorOficina");
        endPointBuscarLibrosEstatusPorTipoOficina = properties.getProperty("endPointBuscarLibrosEstatusPorTipoOficina");
        endPointCrearLibro = properties.getProperty("endPointCrearLibro");
        endPointActalizarLibro = properties.getProperty("endPointActalizarLibro");
        endConsultarTodos = properties.getProperty("endConsultarTodos");
        endPointAperturaCierreLibro = properties.getProperty("endPointAperturaCierreLibro");
        endPointValidarTopeTomo = properties.getProperty("endPointValidarTopeTomo");
        endPointObtenerTipoLibro = properties.getProperty("endPointObtenerTipoLibro");
        endPointBuscarTomoActual = properties.getProperty("endPointBuscarTomoActual");
        endPointValidarActasPorTomo = properties.getProperty("endPointValidarActasPorTomo");
        endPointCrearLibroDiario = properties.getProperty("endPointCrearLibroDiario");
        endPointActualizarLibroDiario = properties.getProperty("endPointActualizarLibroDiario");
        endPointActualizarLibroCivil = properties.getProperty("endPointActualizarLibroCivil");
    }
}
