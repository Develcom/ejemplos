package ve.gob.cne.sarc.participante.core.participante.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.catalogo.TipoDireccion;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.oficina.Usuario;
import ve.gob.cne.sarc.comunes.participante.CartaConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.comunes.participante.IntegranteConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.participante.core.participante.business.ParticipanteBF;

/**
 * ParticipanteController.java
 *
 * @descripcion Controlador del servicio REST participante.
 * @version 1.0 24/11/2015
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/participante")
public class ParticipanteController {

    private static final Logger LOG = LoggerFactory.getLogger(ParticipanteController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private ParticipanteBF participanteBF;

    /**
     * Metodo registrarParticipantes que permite registrar una lista de
     * participantes a una solicitud dada.
     *
     * @param solicitud objeto del modelo ontologico que contiene la informacion
     * de una Solicitud
     * @param request Objeto HttpServletRequest peticion a la operacion registrarParticipante
     * @return Solicitud objeto del modelo ontologico que contiene la
     * informacion de una Solicitud
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/registrarParticipantes", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Solicitud registrarParticipantes(@RequestBody Solicitud solicitud, HttpServletRequest request) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " registrarParticipantes");
        
        String token = request.getHeader("Authorization");
        
        if (token != null) {
            if (token.contains("Bearer") || token.contains("bearer")) {
                token = token.substring(7);
            }
        }
        
        return participanteBF.registrarParticipantes(solicitud, token);
    }

    /**
     * Metodo crearJSON que permite generar un objeto Solicitud.
     *
     * @return Solicitud objeto del modelo ontologico que contiene la
     * informacion de una Solicitud
     */
    @RequestMapping(value = "/crearJSON", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Solicitud crearJSON() {

        LOG.debug(METODO_EJECUCION_CONTROLLER + " crearJSON");

        LOG.debug("=====INICIANDO ParticipanteNegocio.crearJSON==========");

        Solicitud solicitud = new Solicitud();

        Acta acta = new Acta();

        List<Participante> listaParticipantes = new ArrayList<>();

        Participante participante = new Participante();

        participante.setPrimerApellido("Ferreira");
        participante.setPrimerNombre("Ana");
        participante.setSegundoApellido("Martinez");
        participante.setTipoDocumento("V");
        participante.setEstadoCivil("CASADO");
        participante.setLugarNacimiento("Caracas");
        participante.setFechaNacimiento(new GregorianCalendar(1976, 11, 8).getTime());
        participante.setRol("MAD");

        List<DocumentoIdentidad> listaDocIden = new ArrayList<>();

        DocumentoIdentidad docIden = new DocumentoIdentidad();

        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo("1");

        docIden.setNumero("V-12001059");
        docIden.setTipoDocumento(tipoDocumento);

        listaDocIden.add(docIden);
        participante.setDocumentoIdentidad(listaDocIden);

        listaParticipantes.add(participante);

        participante = new Participante();

        participante.setPrimerApellido("Perez");
        participante.setPrimerNombre("Carlos");
        participante.setSegundoApellido("Lopez");
        participante.setTipoDocumento("V");
        participante.setEstadoCivil("CASADO");
        participante.setLugarNacimiento("Caracas");
        participante.setFechaNacimiento(new GregorianCalendar(1976, 11, 8).getTime());
        participante.setRol("PAD");

        List<DocumentoIdentidad> listaDocIden2 = new ArrayList<>();

        DocumentoIdentidad docIden2 = new DocumentoIdentidad();

        TipoDocumento tipoDocumento2 = new TipoDocumento();
        tipoDocumento2.setCodigo("2");

        docIden2.setNumero("48421699");
        docIden2.setTipoDocumento(tipoDocumento2);

        listaDocIden2.add(docIden2);
        participante.setDocumentoIdentidad(listaDocIden2);

        listaParticipantes.add(participante);

        acta.setParticipantes(listaParticipantes);

        List<Acta> listActas = new ArrayList<>();
        listActas.add(acta);

        solicitud.setFechaInicio(new Date());
        solicitud.setNumeroSolicitud("2016160000031750");
        solicitud.setActas(listActas);

        Usuario usuario = new Usuario();
        usuario.setNombre("func1@cne.com");
        List<Usuario> listUsuario = new ArrayList<>();
        listUsuario.add(usuario);

        Funcionario funcionario = new Funcionario();
        funcionario.setUsuarios(listUsuario);
        funcionario.setActa(acta);
        solicitud.setFuncionario(funcionario);

        Oficina oficina = new Oficina();
        oficina.setCodigo("OBM");
        oficina.setNombre("OFICINA ACC. BELLO MONTE");
        solicitud.setOficina(oficina);

        Tramite tramite = new Tramite();
        tramite.setCodigo("EPDIC");
        solicitud.setTramite(tramite);

        Modulo modulo = new Modulo();
        modulo.setCodigo("DEFUN");
        modulo.setNombre("MODULO DE DEFUNCION");
        solicitud.setModulo(modulo);

        return solicitud;
    }

    /**
     * Metodo buscarTipoParticipantePorTramite que permite retornar los tipos de
     * participantes asociados a un tramite; y si se desean los declarantes
     * unicamente para dicho tramite.
     *
     * @param codigoTramite String que contiene el codigo del tramite
     * @param tipoDeclarante String que contiene tipo de declarante
     * @return Lista de objetos de tipo Participante
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/buscarTipoParticipantePorTramite/{codigoTramite}/{tipoDeclarante}",
            method = RequestMethod.GET)
    @ResponseBody
    public List<TipoParticipante> buscarTipoParticipantePorTramite(
            @PathVariable("codigoTramite") String codigoTramite, 
            @PathVariable("tipoDeclarante") String tipoDeclarante) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " buscarTipoParticipantePorTramite");

        return participanteBF.buscarTipoParticipantePorTramite(codigoTramite, tipoDeclarante);
    }

    /**
     * Metodo que permite consultar una lista de Participantes dado un numero de
     * Acta.
     *
     * @param numeroActa String numero de Acta
     * @return Lista de objetos de tipo Participante
     */
    @RequestMapping(value = "/consultarParticPorActa/{numeroActa}", method = RequestMethod.GET)
    @ResponseBody
    public List<Participante> consultarParticPorActa(@PathVariable("numeroActa") String numeroActa) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " consultarParticPorActa");

        return participanteBF.consultarParticPorActa(numeroActa);
    }

    /**
     * Metodo que permite consultar una lista de Participantes dado un numero de
     * Solicitud y un indicador (D:declarante, T:Todos)
     *
     * @param numeroSolicitud String numero de solicitud
     * @param esDeclarante String que contiene el indicador del tipo de
     * participantes
     * @return Lista de objetos de tipo Participante
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/consultarParticPorSolicitud/{numeroSolicitud}/{esDeclarante}", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public List<Participante> consultarParticPorSolicitud(@PathVariable("numeroSolicitud") String numeroSolicitud,
            @PathVariable("esDeclarante") String esDeclarante) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " consultarParticPorSolicitud");

        return participanteBF.consultarParticPorSolicitud(numeroSolicitud, esDeclarante);
    }

    /**
     * Metodo que permite consultar un participante dado un numero de solicitud
     * y un codigo tipo de participante.
     *
     * @param numeroSolicitud String numero de solicitud
     * @param codigoTipo String codigo tipo de participante
     * @return Objeto de tipo Participante
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/consultarParticPorTipo/{numeroSolicitud}/{codigoTipo}", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public List<Participante> consultarParticPorTipo(@PathVariable("numeroSolicitud") String numeroSolicitud,
            @PathVariable("codigoTipo") String[] codigoTipo) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " consultarParticPorTipo numeroSol - " + numeroSolicitud
                + " codigoTipo - " + Arrays.toString(codigoTipo));

        return participanteBF.consultarParticPorTipo(numeroSolicitud, codigoTipo);
    }

    /**
     * Metodo que permite actualizar un participante dado un participante
     *
     * @param numeroSolicitud String Numero de solicitud
     * @param participante Objeto de tipo Participante con los datos del mismo
     * @return Objeto de tipo Participante
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/actualizarParticipante/{numeroSolicitud}", method = RequestMethod.POST,
            produces = {"application/json"})
    @ResponseBody
    public Participante actualizarParticipante(@PathVariable("numeroSolicitud") String numeroSolicitud,
            @RequestBody Participante participante) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "actualizarParticipante");
        return participanteBF.actualizarParticipante(participante, numeroSolicitud);
    }

    /**
     * Metodo que permite registrar una declaracion jurada de un participante
     *
     * @param declaracionJurada Objeto de tipo DeclaracionJurada
     * @return Verdadero o Falso si registra la declaracion jurada
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/registrarDeclaracionJurada", method = RequestMethod.POST,
            produces = {"application/json"})
    public boolean registrarDeclaracionJurada(@RequestBody DeclaracionJurada declaracionJurada) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "registrarDeclaracionJurada");

        return participanteBF.registrarDeclaracionJurada(declaracionJurada);
    }

    /**
     * Metodo que permite registrar una declaracion jurada de un participante
     *
     * @param cartaConsejoComunal Objeto de tipo CartaConsejoComunal
     * @return Verdadero o Falso si registra la declaracion jurada
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/registrarConsejoComunal", method = RequestMethod.POST,
            produces = {"application/json"})
    public boolean registrarConsejoComunal(@RequestBody CartaConsejoComunal cartaConsejoComunal) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "registrarConsejoComunal");

        return participanteBF.registrarConsejoComunal(cartaConsejoComunal);
    }

    /**
     * Metodo crearJSON que permite generar un objeto Participante.
     *
     * @return Objeto de tipo Participante
     */
    @RequestMapping(value = "/crearJSONParticipante", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Participante crearJSONParticipante() {
        Participante participante;
        participante = crearParticipante();

        return participante;
    }

    /**
     * Metodo crearJSON que permite generar un objeto DeclaracionJurada.
     *
     * @return DeclaracionJurada objeto del modelo ontologico que contiene la
     * informacion de una DeclaracionJurada
     */
    @RequestMapping(value = "/crearJSONDeclaracionJurada", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public DeclaracionJurada crearJSONDeclaracionJurada() {

        DeclaracionJurada declaracionJurada = new DeclaracionJurada();
        Participante participante;
        List<Participante> participantes = new ArrayList<>();

        participante = crearTestigo1();
        participantes.add(participante);
        participante = crearTestigo2();
        participantes.add(participante);

        declaracionJurada.setFechaDeclaracion(new GregorianCalendar(2016, 06, 10).getTime());
        declaracionJurada.setTipoDeclaracion("AUCIU");
        declaracionJurada.setNumeroSolicitud("2016182OBM44");
        declaracionJurada.setParticipantes(participantes);

        return declaracionJurada;
    }

    /**
     * Metodo que crea un Participante
     *
     * @return Objeto de tipo Participante con los datos del mismo
     */
    private static Participante crearParticipante() {
        List<Participante> listaParticip = new ArrayList<>();
        Acta acta = new Acta();
        acta.setNumeroActa("20160000320128670234");

        Participante participante = new Participante();

        participante.setFechaNacimiento(new GregorianCalendar(1976, 11, 8).getTime());
        participante.setLugarNacimiento("");
        participante.setPrimerNombre("Pablo");
        participante.setPrimerApellido("Aguilar");
        participante.setSegundoApellido("Alarcon");
        participante.setSegundoNombre("Hugo");
        participante.setSexo("M");
        List<DocumentoIdentidad> listaDocIden = new ArrayList<>();

        DocumentoIdentidad docIden = new DocumentoIdentidad();
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo("CED");
        docIden.setNumero("V-11112222");
        docIden.setTipoDocumento(tipoDocumento);
        listaDocIden.add(docIden);
        participante.setDocumentoIdentidad(listaDocIden);
        Direccion direccion = new Direccion();
        TipoDireccion tipoDireccion = new TipoDireccion();
        tipoDireccion.setCodigo("NAC");
        List<Direccion> listDireccion = new ArrayList<>();
        Parroquia parroquia = new Parroquia();
        parroquia.setNombre("PANAMERICANO");
        Municipio municipio = new Municipio();
        municipio.setNombre("PANAMERICANO");
        direccion.setTipoDireccion(tipoDireccion);
        direccion.setUbicacion("Calle Bolivar Tovar");
        direccion.setParroquia(parroquia);
        direccion.setMunicipio(municipio);
        listDireccion.add(direccion);
        participante.setDireccion(listDireccion);
//        participante.setEstadoCivil("CAS");
//        participante.setOcupacion("ABOGADO");
//        participante.setNacionalidad("COLOMBIANA");
        listaParticip.add(participante);
        acta.setParticipantes(listaParticip);
        return participante;
    }

    /**
     * Metodo que crea un Participante Testigo1
     *
     * @return Objeto de tipo Participante
     */
    private static Participante crearTestigo1() {
        
        Participante partTestigoOne = new Participante();
        Direccion dir = new Direccion();
        List<Direccion> lstDir = new ArrayList<>();
        TipoDireccion tipoDir = new TipoDireccion();
        tipoDir.setCodigo("RES");

        Parroquia parr = new Parroquia();
        parr.setCodigo("CACUT");
        parr.setNombre("CACUTE");

        dir.setUbicacion("Av PPal Tosta Garcia 1");
        dir.setTipoDireccion(tipoDir);
        dir.setParroquia(parr);
        lstDir.add(dir);

        partTestigoOne.setDireccion(lstDir);
        partTestigoOne.setFechaNacimiento(new GregorianCalendar(2016, 05, 16).getTime());
        partTestigoOne.setLugarNacimiento("Caracas");
        partTestigoOne.setPrimerNombre("Maria");
        partTestigoOne.setPrimerApellido("Castillo");
        partTestigoOne.setSegundoApellido("Gonzalez");
        partTestigoOne.setSegundoNombre("Andrea");
        partTestigoOne.setRol("TA1");
        partTestigoOne.setSexo("M");
        List<DocumentoIdentidad> listaDocIden = new ArrayList<>();

        DocumentoIdentidad docIden = new DocumentoIdentidad();
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo("V");
        docIden.setNumero("V-20003009");
        docIden.setTipoDocumento(tipoDocumento);
        listaDocIden.add(docIden);
        partTestigoOne.setDocumentoIdentidad(listaDocIden);

        return partTestigoOne;
    }

    /**
     * Metodo que crea un Participante Testigo2
     *
     * @return Objeto de tipo Participante
     */
    private static Participante crearTestigo2() {
        Participante partTestigoTwo = new Participante();
        Direccion dire = new Direccion();
        List<Direccion> lstDire = new ArrayList<>();
        TipoDireccion tipoDire = new TipoDireccion();
        tipoDire.setCodigo("RES");

        Parroquia parro = new Parroquia();
        parro.setCodigo("CACUT");
        parro.setNombre("CACUTE");

        dire.setUbicacion("Av PPal del centro 2");
        dire.setTipoDireccion(tipoDire);
        dire.setParroquia(parro);
        lstDire.add(dire);

        partTestigoTwo.setDireccion(lstDire);
        partTestigoTwo.setFechaNacimiento(new GregorianCalendar(2016, 05, 16).getTime());
        partTestigoTwo.setLugarNacimiento("Caracas");
        partTestigoTwo.setPrimerNombre("Juanito");
        partTestigoTwo.setPrimerApellido("Brito");
        partTestigoTwo.setSegundoApellido("Marquez");
        partTestigoTwo.setSegundoNombre("Jose");
        partTestigoTwo.setRol("TA2");
        partTestigoTwo.setSexo("M");
        List<DocumentoIdentidad> listaDocIden = new ArrayList<>();

        DocumentoIdentidad docIden = new DocumentoIdentidad();
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo("CED");
        docIden.setNumero("V-15006006");
        docIden.setTipoDocumento(tipoDocumento);
        listaDocIden.add(docIden);
        partTestigoTwo.setDocumentoIdentidad(listaDocIden);

        return partTestigoTwo;
    }

    /**
     * Metodo crearJSON que permite generar un objeto CartaConsejoComunal.
     *
     * @return Objeto de tipo CartaConsejoComunal que contiene la informacion de
     * una CartaConsejoComunal
     */
    @RequestMapping(value = "/crearJSONCartaConsejoComunal", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public CartaConsejoComunal crearJSONCartaConsejoComunal() {

        CartaConsejoComunal cartaCComunal;
        cartaCComunal = crearCartaConsejoComunal();

        return cartaCComunal;
    }

    /**
     * Metodo que crea integrantes de un consejo comunal
     *
     * @return Lista de Objetos de tipo IntegranteConsejoComunal con los datos
     * de los integrantes que conforman un consejo comunal
     */
    private static List<IntegranteConsejoComunal> crearIntegrantes() {

        List<IntegranteConsejoComunal> listaIntegrantes = new ArrayList<>();
        IntegranteConsejoComunal integrante1 = new IntegranteConsejoComunal();
        IntegranteConsejoComunal integrante2 = new IntegranteConsejoComunal();

        integrante1.setNumeroDocumento("V-12900100");
        integrante1.setPrimerNombre("Armando");
        integrante1.setPrimerApellido("Duarte");
        integrante1.setTipoDocumento("V");
        integrante1.setTipoIntegrante("I");
        integrante1.setCargo("Vocal");

        integrante2.setNumeroDocumento("V-12900102");
        integrante2.setPrimerNombre("Sofia");
        integrante2.setPrimerApellido("Reyes");
        integrante2.setTipoDocumento("V");
        integrante2.setTipoIntegrante("I");
        integrante2.setCargo("Secretaria");

        listaIntegrantes.add(integrante1);
        listaIntegrantes.add(integrante2);

        return listaIntegrantes;
    }

    /**
     * Metodo que crea una carta consejo comunal
     *
     * @return Objeto de tipo CartaConsejoComunal
     */
    private static CartaConsejoComunal crearCartaConsejoComunal() {

        CartaConsejoComunal cartaCComunal = new CartaConsejoComunal();
        Participante participante;

        cartaCComunal.setFechaCarta(new Date());
        cartaCComunal.setNombre("Dona Emilia");
        cartaCComunal.setDireccion("Carapita");
        cartaCComunal.setRif("J-00002100-0");
        cartaCComunal.setNumeroSolicitud("2016181MC157");
        cartaCComunal.setIntegrantes(crearIntegrantes());

        participante = crearTestigo1();
        cartaCComunal.setParticipante(participante);

        return cartaCComunal;
    }

    /**
     * Metodo para consultar las declaraciones Juradas
     *
     * @param numSolicitud String numero de solicitud
     * @return Lista de {@link DeclaracionJurada}
     */
    @RequestMapping(value = "/consultarDeclaracionJurada/{numSolicitud}", method = RequestMethod.GET)
    @ResponseBody
    public List<DeclaracionJurada> consultarDeclaracionJurada(@PathVariable("numSolicitud") String numSolicitud) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarDeclaracionJurada");
        return participanteBF.consultarDeclaracionJurada(numSolicitud);
    }

    /**
     * Metodo registrarParticPorSolicitud que permite registrar una lista de
     * participantes a una solicitud dada.
     *
     * @param numSolicitud numero de la solicitud
     * @param participante objeto del modelo ontologico que contiene la informacion
     * de un Participante
     * @return Solicitud objeto del modelo ontologico que contiene la
     * informacion de una Solicitud
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/registrarParticPorSolicitud/{numSolicitud}", 
            method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Participante registrarParticPorSolicitud(@PathVariable("numSolicitud") String numSolicitud, 
            @RequestBody Participante participante) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " registrarParticPorSolicitud");
        return participanteBF.registrarParticPorSolicitud(numSolicitud, participante);
    }
    
    
    /**
     * Busca una lista de de participantes dado un documento del usuario
     * para mostrar la informacion de las diferentes actas.
     * @param numeroDocumento El documento del ciudadano
     * @param flag Bandera para indicar cual acta buscar, true actas propias, false actas relacionada
     * @return Lista de actas
     * @throws Exception 
     */
    @RequestMapping(value = "/buscarParticipantePorDocumento/{numSolicitud}/{flag}", 
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<Acta> buscarParticipantePorDocumento(@PathVariable("numSolicitud") String numeroDocumento, 
            @PathVariable("flag") boolean flag) throws Exception {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " buscarParticipantePorDocumento");
        return participanteBF.consultarParticipantePorDocumento(numeroDocumento, flag);
    }
}
