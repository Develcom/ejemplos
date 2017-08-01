package ve.gob.cne.sarc.libro.core.libro.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.catalogo.TipoLibro;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.oficina.OficinaFuncionario;
import ve.gob.cne.sarc.comunes.oficina.Tomo;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.libro.core.libro.business.LibroBF;

/**
 * LibroController.java
 *
 * @descripcion Controlador REST de Libro
 * @version 1.0 10/10/2015
 * @author Oscar Eubieda
 */
@RestController
@RequestMapping("/libro")
public class LibroController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibroController.class);

    @Autowired
    private LibroBF libroBF;

    /**
     * Obtiene la colección de libros dado un codigo de oficina
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @return Colección de objetos <tt>Libro</tt> en formato <tt>JSON</tt>.
     */
    @RequestMapping(value = "/consultarTodos/{codigoOfic}", method = RequestMethod.GET)
    @ResponseBody
    public List<Libro> consultarTodos(@PathVariable("codigoOfic") String codigoOfic) {

        LOGGER.info("Ejecutando LibroController.consultarTodos(" + codigoOfic + ")");

        return libroBF.consultarTodos(codigoOfic);

    }

    /**
     * Obtiene la colección de libros disponibles en base a los parametros dados
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @param estatus el id del estatus de libros a consultar.
     * @param anio el año de libros a consultar.
     * @return Colección de objetos <tt>Libro</tt> en formato <tt>JSON</tt>.
     */
    @RequestMapping(value = "/buscarLibrosEstatusPorTipoOficina/{codigoOfic}/{estatus}/{anio}",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Libro> buscarLibrosEstatusPorTipoOficina(@PathVariable("codigoOfic") String codigoOfic,
            @PathVariable("estatus") String estatus, @PathVariable("anio") int anio) {

        LOGGER.info("Ejecutando LibroController.buscarLibrosEstatusPorTipoOficina(" + codigoOfic + "," + estatus + ","
                + anio + ")");

        return libroBF.buscarLibrosEstatusPorTipoOficina(codigoOfic, Integer.valueOf(estatus), anio);

    }

    /**
     * Valida la existencia de un libro activo para una oficina y un tipo de libro dato.
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @param estatus el estatus de libros a consultar.
     * @param anio el año de libros a consultar.
     * @return <tt>boolean</tt> que indica la validez del libro, en formato <tt>JSON</tt>.
     */
    @RequestMapping(value = "/validarLibrosActivosPorOficina/{codigoOfic}/{estatus}/{anio}",
            method = RequestMethod.GET)
    @ResponseBody
    public boolean validarLibrosActivosPorOficina(@PathVariable("codigoOfic") String codigoOfic,
            @PathVariable("estatus") String estatus, @PathVariable("anio") int anio) {

        LOGGER.info("Ejecutando LibroController.validarLibrosActivosPorOficina(" + codigoOfic + "," + estatus + ","
                + anio + ")");

        return libroBF.validarLibrosActivosPorOficina(codigoOfic, Integer.valueOf(estatus), anio);

    }
    
    /**
     * Valida la existencia de un libro diario activo para una oficina en una fecha.
     *
     * @param codOficina el codigo de oficina a consultar.
     * @param fecha fecha del libro diario a consultar.
     * @param estatus estatus del libro diario a consultar.
     * @return <tt>boolean</tt> que indica la validez del libro, en formato <tt>JSON</tt>.
     */
    @RequestMapping(value = "/validarLibroDiarioActivoPorOficina/{codOficina}/{fecha}/{estatus}",
            method = RequestMethod.GET)
    @ResponseBody
    public boolean validarLibroDiarioActivoPorOficina(@PathVariable("codOficina") String codOficina,
            @PathVariable("fecha") String fecha, @PathVariable("estatus") long estatus) {

        LOGGER.info("Ejecutando LibroController.validarLibroDiarioActivoPorOficina(" + codOficina + "," + fecha + ","
                + estatus + ")");

        return libroBF.validarLibroDiarioActivoPorOficina(codOficina, fecha, estatus);

    }

    /**
     * Crea un nuevo libro en base a la información dada.
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @return <tt>boolean</tt> que indica si la operación de ha completado con éxito, en formato <tt>JSON</tt>.
     */
    @RequestMapping(value = "/crearLibro", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public boolean crearLibro(@RequestBody Libro libro) {

        LOGGER.info("Ejecutando LibroController.gestionarLibro()");

        return libroBF.gestionarLibro(libro);
    }

    /**
     * Actualiza la información de un libro en base a la información dada.
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @return <tt>boolean</tt> que indica si la operación de ha completado con éxito, en formato <tt>JSON</tt>.
     */
    @RequestMapping(value = "/actualizarLibro", method = RequestMethod.PUT, produces = {"application/json"})
    @ResponseBody
    public boolean actualizarLibro(@RequestBody Libro libro) {

        LOGGER.info("Ejecutando LibroController.gestionarLibro()");

        return libroBF.gestionarLibro(libro);
    }

    /**
     * Metodo para un objeto Libro en formato JSON.
     *
     * @return Objeto<tt>Libro</tt> en formato <tt>JSON</tt>.
     */
    @RequestMapping(value = "/crearJSON", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Libro crearJSON() {

        Libro libro = new Libro();

        Oficina oficina = new Oficina();
        oficina.setCodigo("OBM");
        oficina.setDescripcion("CENTRO MEDICO PROVESALUD");

        TipoLibro tipoLibro = new TipoLibro();
        tipoLibro.setCodigo("2");
        tipoLibro.setNombre("NACIMIENTO");

        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo("1");

        DocumentoIdentidad documento = new DocumentoIdentidad();
        documento.setTipoDocumento(tipoDocumento);
        documento.setNumero("1");

        List<DocumentoIdentidad> documentos = new ArrayList<>();
        documentos.add(documento);

        Funcionario funcApertura = new Funcionario();
        funcApertura.setDocumentoIdentidad(documentos);

        Funcionario funcCierre = new Funcionario();
        funcCierre.setDocumentoIdentidad(documentos);

        libro.setNumeroAnio(2016);
        libro.setNumeroTomo("2");
        libro.setNumeroActa("2");
        libro.setNumerofolio(1);
        libro.setEstatus("2");

        libro.setOficina(oficina);
        libro.setTipoLibro(tipoLibro);
        libro.setFuncionarioApertura(funcApertura);
        libro.setFuncionarioCierre(funcCierre);

        return libro;

    }

    /**
     * Actualiza el estatus de un libro en base a la información dada.
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @return <tt>boolean</tt> que indica si la operación de ha completado con éxito, en formato <tt>JSON</tt>.
     */
    @RequestMapping(value = "/aperturaCierreLibro/", method = RequestMethod.PUT, produces = {"application/json"})
    @ResponseBody
    public boolean aperturaCierreLibro(@RequestBody Libro libro) {

        LOGGER.info("Ejecutando LibroController.aperturaCierreLibro()");

        return libroBF.aperturaCierreLibro(libro);
    }

    /**
     * Actualiza la informacion deel libro civil
     *
     * @param acta objeto Acta en formato JSON.
     * @return boolean que indica si la operación de ha completado con éxito, en formato JSON.
     */
    @RequestMapping(value = "/actualizarLibroCivil", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public boolean actualizarLibroCivil(@RequestBody Acta acta) {
        LOGGER.info("Actualizando el libro Civil (controlador)" + acta.getNumeroActa());
        LOGGER.info("Libro con numero de acta: " + acta.getNumeroActa() + " actualizado ");
        return libroBF.actualizarLibroCivil(acta);
    }

    /**
     * Valida el tope del numero del Tomo para un tipo libro activo en un año y oficina dada.
     *
     * @param codigoOfic String codigo de oficina.
     * @param tipoLibro String codigo de tipo de libro.
     * @param anio integer año del libro.
     * @return Verdadero si el Numero del Tomo llego al tope establecido.
     */
    @RequestMapping(value = "/validarTopeTomo/{codigoOfic}/{tipoLibro}/{anio}",
            method = RequestMethod.GET)
    @ResponseBody
    public boolean validarTopeTomo(@PathVariable("codigoOfic") String codigoOfic,
            @PathVariable("tipoLibro") String tipoLibro, @PathVariable("anio") int anio) {

        LOGGER.info("Ejecutando LibroController.validarTopeTomo(" + codigoOfic + "," + tipoLibro + ","
                + anio + ")");

        return libroBF.validarTopeTomo(codigoOfic, Long.valueOf(tipoLibro), anio);
    }

    /**
     * Busca el codigo del tipo libro dado un codigo de tramite
     *
     * @param codigoTramite objeto String que contiene el codigo del tramite.
     * @return Objeto String con el codigo del Tipo Libro
     */
    @RequestMapping(value = "/obtenerTipoLibro/{codigoTramite}", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public String obtenerTipoLibro(@PathVariable("codigoTramite") String codigoTramite) {
        LOGGER.info("Ejecutando LibroController.obtenerTipoLibro(" + codigoTramite + ")");

        return libroBF.obtenerTipoLibro(codigoTramite);
    }

    /**
     * Busca el tomo actual dado una oficina, año, tipo libro y estatus.
     *
     * @param codigoOfic String codigo de la oficina a consultar
     * @param estatus String codigo de estatus a consultar
     * @param anio int año del libro a consultar
     * @param codTipoLibro String codigo del tipo libro a consultar
     * @return Objeto tipo Tomo que contiene la informacion del tomo consultado
     */
    @RequestMapping(value = "/buscarTomoActual/{codigoOfic}/{estatus}/{anio}/{codTipoLibro}",
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Tomo buscarTomoActual(@PathVariable("codigoOfic") String codigoOfic,
            @PathVariable("estatus") String estatus, @PathVariable("anio") int anio,
            @PathVariable("codTipoLibro") String codTipoLibro) {

        LOGGER.info("Ejecutando LibroController.buscarTomoActual(" + codigoOfic + "," + estatus + ","
                + anio + "," + codTipoLibro + ")");

        return libroBF.buscarTomoActual(codigoOfic, Long.valueOf(estatus), anio, Long.valueOf(codTipoLibro));
    }

    /**
     * Valida el tope de numeros de acta por tomo dado un codigo de oficina, un codigo de tipo libro, un año y un tomo.
     *
     * @param codOficina String codigo de la oficina
     * @param codTipoLibro String codigo del tipo libro
     * @param anio int año del libro
     * @param numeroTomo String numero de tomo a validar
     * @return Verdadero si el numero de actas por tomo dado una oficina, tipo libro, año y tomo
     */
    @RequestMapping(value = "/validarActasPorTomo/{codOficina}/{codTipoLibro}/{anio}/{numeroTomo}",
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public boolean validarActasPorTomo(@PathVariable("codOficina") String codOficina,
            @PathVariable("codTipoLibro") String codTipoLibro, @PathVariable("anio") int anio,
            @PathVariable("numeroTomo") String numeroTomo) {

        LOGGER.info("Ejecutando LibroController.validarActasPorTomo(" + codOficina
                + " " + codTipoLibro + " " + anio + " " + numeroTomo + ")");

        return libroBF.validarActasPorTomo(codOficina, Long.valueOf(codTipoLibro), anio, numeroTomo);
    }

    /**
     * Metodo para crear libro diario dado un numero de solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @return Verdadero si creo; satisfactoriamente el libro diario, en caso contrario devuelve falso
     */
    @RequestMapping(value = "/crearLibroDiario/{numeroSolicitud}", method = RequestMethod.POST,
            produces = {"application/json"})
    @ResponseBody
    public boolean crearLibroDiario(@PathVariable("numeroSolicitud") String numeroSolicitud) {
        LOGGER.info("Ejecutando LibroController.crearLibroDiario(" + numeroSolicitud + ")");

        return libroBF.crearLibroDiario(numeroSolicitud);
    }

    /**
     * Metodo que permite actualizar el libro diario dado un n&uacute;mero de solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @param codigoEstatus String Codigo del estatus del libro diario
     * @return Verdadero si hizo la actualizacion satisfactoriamente, en caso contrario devuelve falso
     */
    @RequestMapping(value = "/actualizarLibroDiario/{numeroSolicitud}/{codigoEstatus}", method = RequestMethod.POST,
            produces = {"application/json"})
    @ResponseBody
    public boolean actualizarLibroDiario(@PathVariable("numeroSolicitud") String numeroSolicitud,
            @PathVariable("codigoEstatus") String codigoEstatus) {
        LOGGER.info("Ejecutando LibroController.actualizarLibroDiario(" + numeroSolicitud + " - "+ codigoEstatus + ")");

        return libroBF.actualizarLibroDiario(numeroSolicitud, codigoEstatus);
    }
    /**
     * Metodo para un objeto Libro en formato JSON.
     *
     * @return Objeto Acta en formato JSON.
     */
    @RequestMapping(value = "/crearJSONActa", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Acta crearJSONActa() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date fecha;
        Acta acta = new Acta();
        Oficina oficina = new Oficina();
        OficinaFuncionario oficinaFuncionario = new OficinaFuncionario();
        Solicitud solicitud = new Solicitud();
        Tramite tramite = new Tramite();

        try {

            tramite.setCodigo("RDEFU");
            tramite.setDescripcion("REGISTRO DE DEFUNCION");
            tramite.setNombre("REGISTRAR DEFUNCION");

            solicitud.setNumeroSolicitud("201615900001713");
            solicitud.setTramite(tramite);

            oficina.setCodigo("OBM");
            oficina.setNombre("OFICINA ACC. BELLO MONTE");
            oficinaFuncionario.setOficina(oficina);

            fecha = sdf.parse(sdf.format(new Date()));

            acta.setNumeroActa("1234566");
            acta.setFechaCreacion(fecha);
            acta.setNumeroFolio(1.0);
            acta.setActaEstatus("P");
            acta.setParticipantes(new ArrayList<Participante>());
            acta.setSolicitud(solicitud);
            acta.setOficinaFuncionario(oficinaFuncionario);
        } catch (ParseException ex) {
            LOGGER.error("error fecha " + ex);
        }

        return acta;
    }
}
