package ve.gob.cne.sarc.acta.core.acta.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.acta.core.acta.business.ActaBF;
import ve.gob.cne.sarc.comunes.acta.ActaPrimigenia;
import ve.gob.cne.sarc.comunes.nacimiento.Nacimiento;
import ve.gob.cne.sarc.comunes.oficina.Ore;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.comunes.acta.Extemporanea;
import ve.gob.cne.sarc.comunes.acta.Insercion;
import ve.gob.cne.sarc.comunes.acta.MedidaProteccion;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.oficina.OficinaFuncionario;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitante.DocumentoEntePublico;
import ve.gob.cne.sarc.comunes.solicitante.Solicitante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;

/**
 * ActaController.java
 *
 * @descripcion Controlador REST del Servicio Acta
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 *
 */
@RestController
@RequestMapping("/acta")
public class ActaController {

    private static final Logger LOG = LoggerFactory.getLogger(ActaController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String ERROR_FECHA = "error en fecha ";

    @Autowired
    private ActaBF actaBF;

    /**
     * Busca un Acta segun la informacion suministrada
     *
     * @param acta Objecto con la informacion requeridad para la busqueda
     * @return Un acta si se existe en caso contrario null
     */
    @RequestMapping(value = "/consultarActa",
            method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Acta consultarActa(@RequestBody Acta acta) {

        LOG.info("buscando informacion del acta (controlador) " + acta);

        Acta actaResult;

        actaResult = actaBF.consultarActa(acta);

        LOG.info("acta " + actaResult);

        return actaResult;
    }

    /**
     * Busca un listado de actas segun la informacion suministrada
     *
     * @param acta Objecto con la informacion requeridad para la busqueda
     * @return Un listado de Actas
     */
    @RequestMapping(value = "/consultarActa/lista",
            method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public List<Acta> consultarActaLista(@RequestBody Acta acta) {

        LOG.info("buscando informacion del acta (list) " + acta);

        List<Acta> actaResult;

        actaResult = actaBF.consultarActaLista(acta);

        LOG.info("tamano resultado busqueda acta " + actaResult.size());

        return actaResult;
    }

    /**
     * Actualiza un acta segun la informacion suministrada
     *
     * @param acta pojo con la infomacion del acta
     * @return verdadero si se actualizo falso en caso contrario
     */
    @RequestMapping(value = "/actualizarActa", method = RequestMethod.PUT,
            produces = {"application/json"})
    @ResponseBody
    public boolean actualizarActa(@RequestBody Acta acta) {

        LOG.info("actualizando el acta (controlador) " + acta.getNumeroActa());

        boolean resp;

        resp = actaBF.actualizarActa(acta);

        LOG.info("acta " + acta.getNumeroActa() + " actualizada " + resp);

        return resp;
    }

    /**
     * Metodo para buscar el acta
     *
     * @param numeroActa String que describe el numero de acta
     * @return Map<String, String> que permite almacenar como clave el esquema y
     * el valor si se encontro o no el numero del acta
     */
    @RequestMapping(value = "/existeActa/{numeroActa}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> existeActa(@PathVariable("numeroActa") String numeroActa) {
        LOG.info("Consultando si existe el Acta");
        return actaBF.existeActa(numeroActa);
    }

    /**
     * Metodo que permite buscar el Numero de Acta dado el numero de la
     * solicitud
     *
     * @param nroSolicitud String que contiene el numero de solicitud
     * @return String que contiene el Numero de Acta
     */
    @RequestMapping(value = "/buscarNumeroActaPorSolic/{nroSolicitud}", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public String buscarNumeroActaPorSolic(@PathVariable("nroSolicitud") String nroSolicitud) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "buscarNumeroActaPorSolic");

        return actaBF.buscarNumeroActaPorSolic(nroSolicitud);
    }

    /**
     * Valida si el certificado medico existe.
     *
     * @param codigoTramite String describe el codigo del tramite,
     * @param numeroCertificado long describe el numero de certificado
     * @return Verdadero si existe el certificado medico, en caso contrario
     * falso
     */
    @RequestMapping(value
            = "/validarCertificadoMedico/{codigoTramite}/{numeroCertificado}",
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public boolean validarCertificadoMedico(@PathVariable("codigoTramite") String codigoTramite,
            @PathVariable("numeroCertificado") long numeroCertificado) {

        return actaBF.validarCertificadoMedico(codigoTramite,
                numeroCertificado);
    }

    /**
     * Metodo que permite guardar el ORE
     *
     * @param numSolicitud String que describe el numero de la Solicitud
     * @return objeto de tipo Ore
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/guardarORE/{numSolicitud}", method
            = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Ore guardarORE(@PathVariable("numSolicitud") String numSolicitud) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " guardarORE -");

        return actaBF.guardarORE(numSolicitud);
    }

    /**
     *
     * Metodo que permite consultar un Ore dado un numero de Solicitud
     *
     * @param numSolicitud String numero de solicitud
     * @return Ore pojo que contiene la informacion de Ore
     *
     */
    @RequestMapping(value = "/consultarOre/{numSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public Ore consultarOre(@PathVariable("numSolicitud") String numSolicitud) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " consultaOre");

        return actaBF.consultarOre(numSolicitud);
    }

    /**
     * Actualiza el estatus de un acta
     *
     * @param codigoEstatusActa String que contiene el estatus del Acta
     * @param acta Acta pojo con la informacion del acta
     * @return acta pojo con la informacion del acta
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/actualizarEstatusActa/{codigoEstatusActa}",
            method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Acta actualizarEstatusActa(@PathVariable("codigoEstatusActa") String codigoEstatusActa,
            @RequestBody Acta acta) throws Exception {
        return actaBF.actualizarEstatusActa(Long.valueOf(codigoEstatusActa), acta);

    }

    /**
     * Metodo que permite guardar la insercion
     *
     * @param insercion pojo con la informacion de Insercion
     * @return objeto de tipo Insercion
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/guardarInsercion", method
            = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public boolean guardarInsercion(@RequestBody Insercion insercion) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " guardarInsercion -");

        return actaBF.guardarInsercion(insercion);
    }

    /**
     *
     * Metodo que permite consultar una Insercion
     *
     * @param numSolicitud String numero de solicitud
     * @return Insercion pojo que contiene la informacion de Insercion
     * @throws java.lang.Exception
     *
     */
    @RequestMapping(value = "/consultarInsercion/{numSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public Insercion consultarInsercion(@PathVariable("numSolicitud") String numSolicitud) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " consultarInsercion");

        return actaBF.consultarInsercion(numSolicitud);
    }

    /**
     * Metodo que permite guardar la Decision Judicial
     *
     * @param decisionJudicial pojo con la informacion de Decision Judicial
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     */
    @RequestMapping(value = "/guardarDecisionJudicial", method
            = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public boolean guardarDecisionJudicial(@RequestBody DecisionJudicial decisionJudicial) {

        LOG.info(METODO_EJECUCION_CONTROLLER + " guardarDecisionJudicial -");

        return actaBF.guardarDecisionJudicial(decisionJudicial);
    }

    /**
     *
     * Metodo que permite consultar una Insercion
     *
     * @param numSolicitud String numero de solicitud
     * @return Insercion pojo que contiene la informacion de Insercion
     * @throws java.lang.Exception
     *
     */
    @RequestMapping(value = "/consultarDecisionJudicial/{numSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public DecisionJudicial consultarDecisionJudicial(@PathVariable("numSolicitud") String numSolicitud) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " consultarDecisionJudicial");

        return actaBF.consultarDecisionJudicial(numSolicitud);
    }

    /**
     * Metodo que permite guardar la Medida de Proteccion
     *
     * @param medidaProteccion pojo con la informacion de Medida de Proteccion
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     */
    @RequestMapping(value = "/guardarMedidaProteccion", method
            = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public boolean guardarMedidaProteccion(@RequestBody MedidaProteccion medidaProteccion) {

        LOG.info(METODO_EJECUCION_CONTROLLER + " guardarMedidaProteccion -");

        return actaBF.guardarMedidaProteccion(medidaProteccion);
    }

    /**
     *
     * Metodo que permite consultar una Medida de Proteccion
     *
     * @param numSolicitud String numero de solicitud
     * @return MedidaProteccion pojo que contiene la informacion de Medida de
     * Proteccion
     * @throws java.lang.Exception
     *
     */
    @RequestMapping(value = "/consultarMedidaProteccion/{numSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public MedidaProteccion consultarMedidaProteccion(@PathVariable("numSolicitud") String numSolicitud) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " consultarMedidaProteccion");

        return actaBF.consultarMedidaProteccion(numSolicitud);
    }

    /**
     * Metodo que permite guardar Extemporanea
     *
     * @param extemporanea pojo con la informacion de Extemporanea
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/guardarExtemporanea", method
            = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public boolean guardarExtemporanea(@RequestBody Extemporanea extemporanea) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " guardarExtemporanea -");

        return actaBF.guardarExtemporanea(extemporanea);
    }

    /**
     *
     * Metodo que permite consultar Extemporanea
     *
     * @param numSolicitud String numero de solicitud
     * @return Extemporanea pojo que contiene la informacion de Extemporanea
     * @throws java.lang.Exception
     *
     */
    @RequestMapping(value = "/consultarExtemporanea/{numSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public Extemporanea consultarExtemporanea(@PathVariable("numSolicitud") String numSolicitud) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " consultarExtemporanea");

        return actaBF.consultarExtemporanea(numSolicitud);
    }

    /**
     * Metodo que permite guardar Nacimiento
     *
     * @param nacimiento pojo con la informacion de Nacimiento
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/guardarNacimiento", method
            = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public boolean guardarNacimiento(@RequestBody Nacimiento nacimiento) throws Exception {

        LOG.info(METODO_EJECUCION_CONTROLLER + " guardarDefuncion -");

        return actaBF.guardarNacimiento(nacimiento);
    }

    /**
     *
     * Metodo que permite consultar Nacimiento
     *
     * @param numSolicitud String numero de solicitud
     * @return pojo que contiene la informacion de Nacimiento
     *
     */
    @RequestMapping(value = "/consultarNacimiento/{numSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public Nacimiento consultarNacimiento(@PathVariable("numSolicitud") String numSolicitud) {

        LOG.info(METODO_EJECUCION_CONTROLLER + " consultarNacimiento");

        return actaBF.consultarNacimiento(numSolicitud);
    }

    /**
     * Metodo que permite guardar la informacion del acta primigenia solicitada
     * por pantalla
     *
     * @param actaPrimigenia ActaPrimigenia Pojo con la informacion del acta
     * primigenia
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/guardarActaPrimigenia", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public ActaPrimigenia guardarActaPrimigenia(@RequestBody ActaPrimigenia actaPrimigenia) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " guardarActaPrimigenia");

        return actaBF.guardarActaPrimigenia(actaPrimigenia);
    }

    /**
     * Metodo que permite consultar los datos del acta primigenia
     *
     * @param numeroSolicitud String Numero de solicitud
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/consultaActaPrimigenia/{numeroSolicitud}", method = RequestMethod.GET)
    @ResponseBody
    public ActaPrimigenia consultaActaPrimigenia(@PathVariable("numeroSolicitud") String numeroSolicitud) throws Exception {

        LOG.debug(METODO_EJECUCION_CONTROLLER + " consultaActaPrimigenia");

        return actaBF.consultaActaPrimigenia(numeroSolicitud);
    }

    /**
     * metodo para pruebas
     *
     * @return
     */
    @RequestMapping(value = "/crearJson", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public Acta crearJson() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date fecha;
        Acta acta = new Acta();
        Oficina oficina = new Oficina();
        OficinaFuncionario oficinaFuncionario = new OficinaFuncionario();
        Solicitud solicitud = new Solicitud();
        Solicitante solicitante = new Solicitante();
        Tramite tramite = new Tramite();
        Ciudadano ciudadano = new Ciudadano();
        try {

            tramite.setCodigo("RMATR");
            tramite.setDescripcion("REGISTRO DE MATRIMONIO");
            tramite.setNombre("REGISTRAR MATRIMONIO");

            ciudadano.setPrimerNombre("rusber");
            ciudadano.setPrimerApellido("rebolledo");
            DocumentoIdentidad documentoIden = new DocumentoIdentidad();
            documentoIden.setNumero("V-9594959");

            TipoDocumento tipoDoc = new TipoDocumento();
            tipoDoc.setCodigo("CED");
            documentoIden.setTipoDocumento(tipoDoc);
            ArrayList<DocumentoIdentidad> listDoc = new ArrayList<>();
            listDoc.add(documentoIden);
            ciudadano.setDocumentoIdentidad(listDoc);
            solicitante.setCiudadano(ciudadano);
            solicitante.setTipo("D");
            DocumentoEntePublico documentoEntePublico = new DocumentoEntePublico();
            documentoEntePublico.setTipoDocumentoEntePublico("CED");
            solicitante.setDocumentoEntePublico(documentoEntePublico);
            solicitud.setNumeroSolicitud("201628000001460");
            solicitud.setTramite(tramite);
            solicitud.setSolicitante(solicitante);

            oficina.setCodigo("OBM");
            oficina.setNombre("OFICINA ACC. BELLO MONTE");
            oficinaFuncionario.setOficina(oficina);

            fecha = sdf.parse(sdf.format(new Date()));

            acta.setNumeroActa("2016000014012806720");
            acta.setFechaCreacion(fecha);
            acta.setNumeroFolio(1.0);
            acta.setActaEstatus("R");
            acta.setParticipantes(new ArrayList<Participante>());
            acta.setSolicitud(solicitud);
            acta.setOficinaFuncionario(oficinaFuncionario);
        } catch (ParseException ex) {
            LOG.error("error fecha " + ex);
        }

        return acta;
    }

    /**
     * metodo para pruebas para crear Insercion
     *
     * @return
     */
    @RequestMapping(value = "/crearInsercion", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public Insercion crearInsercion() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Date fechaInsercion;
        Insercion insercion = new Insercion();

        try {
            fechaInsercion = sdf.parse(sdf.format(new Date()));
            insercion.setFechaInsercion(fechaInsercion);
            insercion.setIndicadorVivo("S");
            insercion.setPrimerApellidoAutoridad("Montes");
            insercion.setPrimerNombreAutoridad("Luis");

            Parroquia parroquia = new Parroquia();
            parroquia.setCodigoMunicipio("CEL");
            parroquia.setNombre("ACEQUIAS");
            parroquia.setCodigo("ACEQU");
            parroquia.setFechaInicio(fechaInsercion);
            insercion.setParroquia(parroquia);
            insercion.setNumeroActa("2016183OBMNA1001873");

        } catch (ParseException ex) {
            LOG.error(ERROR_FECHA + ex);
        }

        return insercion;
    }

    /**
     * metodo para pruebas para crear Insercion
     *
     * @return
     */
    @RequestMapping(value = "/crearDecisionJudicial", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public DecisionJudicial crearDecisionJudicial() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Date fechaSentencia;
        DecisionJudicial decJudicial = new DecisionJudicial();

        try {
            decJudicial.setCedulaJuez("11113333");
            decJudicial.setExtractoProcedencia("Prueba");
            fechaSentencia = sdf.parse(sdf.format(new Date()));
            decJudicial.setFechaSentencia(fechaSentencia);
            decJudicial.setNombreTribunal("TSJ Caracas");
            decJudicial.setNumeroActa("2016000012012469650");
            decJudicial.setNumeroSentencia("A7777");
            decJudicial.setPrimerApellidoJuez("Mora");
            decJudicial.setPrimerNombreJuez("Luis");
            decJudicial.setSegundoApellidoJuez("Nieves");
            decJudicial.setSegundoNombreJuez("Fabio");

        } catch (ParseException ex) {
            LOG.error(ERROR_FECHA + ex);
        }

        return decJudicial;
    }

    /**
     * metodo para pruebas para crear Medida de Proteccion
     *
     * @return
     */
    @RequestMapping(value = "/crearMedidaProteccion", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public MedidaProteccion crearMedidaProteccion() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Date fechaMedida;
        MedidaProteccion medidaPro = new MedidaProteccion();

        try {
            medidaPro.setExtractoProcedencia("Por Medida de Proteccion");
            fechaMedida = sdf.parse(sdf.format(new Date()));
            medidaPro.setFechaMedida(fechaMedida);
            medidaPro.setNombreConsejoProteccion("De Caracas");
            medidaPro.setNumeroActa("20160000120122385245");
            medidaPro.setPrimerApellidoConsejero("Salazar");
            medidaPro.setPrimerNombreConsejero("Marleen");
            medidaPro.setSegundoApellidoConsejero("Castillo");
            medidaPro.setSegundoNombreConsejero("Nitsuga");

        } catch (ParseException ex) {
            LOG.error(ERROR_FECHA + ex);
        }

        return medidaPro;
    }

    /**
     * metodo para pruebas para crear Insercion
     *
     * @return
     */
    @RequestMapping(value = "/crearExtemporanea", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public Extemporanea crearExtemporanea() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Date fechaExtemporanea;
        Extemporanea extemporanea = new Extemporanea();

        try {
            extemporanea.setExtractoProcedencia("Por Extemporanea");

            fechaExtemporanea = sdf.parse(sdf.format(new Date()));
            extemporanea.setFechaProvidencia(fechaExtemporanea);
            extemporanea.setNumeroActa("20160000300122318252");
            extemporanea.setNumeroProvidencia("7");
            extemporanea.setPrimerApellidoAutoridad("Marin");
            extemporanea.setPrimerNombreAutoridad("Pablo");
            extemporanea.setSegundoApellidoAutoridad("Marcos");
            extemporanea.setSegundoNombreAutoridad("Raul");

        } catch (ParseException ex) {
            LOG.error(ERROR_FECHA + ex);
        }

        return extemporanea;
    }

    /**
     * metodo para pruebas para crear Nacimiento
     *
     * @return
     */
    @RequestMapping(value = "/crearNacimiento", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public Nacimiento crearNacimiento() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Date fechaCertificado;
        Nacimiento nacimiento = new Nacimiento();

        try {
            Acta acta = new Acta();
            acta.setNumeroActa("2016000012012469650");
            fechaCertificado = sdf.parse(sdf.format(new Date()));
            nacimiento.setActa(acta);
//        	nacimiento.setCaracterActuaDeclarante("gdgd");
            nacimiento.setCentroSalud("hospital jm");
//        	nacimiento.setDiExtrahospitalario("Estacion de Maternidad");
            nacimiento.setDocumentoIdentidadMedico("4589655");
            nacimiento.setFechaCertificado(fechaCertificado);
            nacimiento.setNumeroCertificado(78965412);
            nacimiento.setNuMPPS(317678);
            nacimiento.setPrimerApellidoMedico("Lopez");
            nacimiento.setPrimerNombreMedico("Baldemaro");
            nacimiento.setSegundoApellidoMedico("Gonzalez");
            nacimiento.setSegundoNombreMedico("Jose");
            nacimiento.setSexo("M");

        } catch (ParseException ex) {
            LOG.error(ERROR_FECHA + ex);
        }

        return nacimiento;
    }

    /**
     * Metodo que crea el objeto acta primigenia para pruebas
     *
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     */
    @RequestMapping(value = "/crearActaPrimigenia", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public ActaPrimigenia crearActaPrimigenia() {

        ActaPrimigenia ap = new ActaPrimigenia();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        String fecInscripcion;
        String fecCreacion;

        fecInscripcion = sdf.format(new Date());
        fecCreacion = sdf.format(new Date());

        ap.setNumeroActa("ABC12345");
        ap.setNumeroTomo("1");
        ap.setNumeroFolio("1");
        ap.setNombreOficina("La Candelaria");
        ap.setNumeroConsecutivo(10001);
        ap.setNumeroSolic("201615900001952");
        ap.setFechaIncripcion(fecInscripcion);

        return ap;
    }
}
