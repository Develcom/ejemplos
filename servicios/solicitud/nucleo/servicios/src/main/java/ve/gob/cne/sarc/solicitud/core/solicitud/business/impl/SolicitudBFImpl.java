package ve.gob.cne.sarc.solicitud.core.solicitud.business.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ve.gob.cne.sarc.cliente.libro.LibroServicioCliente;

import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.persistencia.entidades.DiarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DiarioEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EstatusTramiteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.LibroDiarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.RolUsuarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.DiarioEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DiarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.UsuarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.EstatusTramiteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.PaqueteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.RolUsuarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TramiteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitanteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudRepository;
import ve.gob.cne.sarc.persistencia.repositorios.LibroDiarioRepository;
import ve.gob.cne.sarc.pmsentinel.bo.PaqueteBO;
import ve.gob.cne.sarc.solicitud.core.solicitud.business.SolicitudBF;
import ve.gob.cne.sarc.solicitud.core.solicitud.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.solicitud.core.solicitud.mapper.PaqueteMapper;
import ve.gob.cne.sarc.solicitud.core.solicitud.mapper.SolicitanteMapper;
import ve.gob.cne.sarc.solicitud.core.solicitud.mapper.SolicitudMapper;
import ve.gob.cne.sarc.solicitud.core.util.Constantes;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * SolicitudBFImpl.java
 *
 * @descripcion [Implementacion del BusinessFacade con la logica de negocio de manejo de solicitudes]
 * @version 1.0 22/7/2016
 * @author Anabell De Faria
 */
@Component
public class SolicitudBFImpl implements SolicitudBF {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitudBFImpl.class);

    @Autowired
    public AdministradorPropiedades properties;

    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private SolicitudEstatusRepository solicitudEstatusRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TramiteRepository tramiteRepository;
    @Autowired
    private SolicitanteRepository solicitanteRepository;
    @Autowired
    private EstatusTramiteRepository estatusTramiteRepository;
    @Autowired
    private SolicitudMapper solicitudMapper;
    @Autowired
    private SolicitanteMapper solicitanteMapper;
    @Autowired
    private DiarioRepository diarioRepository;
    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    private PaqueteRepository paqueteRepository;
    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;
    @Autowired
    private DiarioEstatusRepository diarioEstatusRepository;
    @Autowired
    private LibroDiarioRepository libroDiarioRepository;

    @Autowired
    private PaqueteMapper paqueteMapper;

    Date fecDesde = new Date();
    Date fecHasta = new Date();

    /**
     *
     * Metodo consultarDetalleSolicitud que permite consultar un solicitud segun el numero de esta
     *
     * @param numero String con el numero de la solicitud
     * @return Objeto de tipo Solicitud
     */
    @Override
    public Solicitud consultarDetalleSolicitud(String numero) {
        LOG.debug("=====INICIANDO SolicitudNegocio.consultarDetalleSolicitud==========");
        Solicitud solicitud = new Solicitud();
        SolicitudEntidad solicitudEntidad;
        solicitudEntidad = solicitudRepository.findByNumero(numero);

        if (solicitudEntidad != null) {
            solicitud = solicitudMapper.entityToBO(solicitudEntidad);
        }

        return solicitud;
    }

    /**
     *
     * Metodo consultarPorNumeroDocSolicitante que permite consultar las solicitudes existentes asociadas a un documento
     * de solicitante
     *
     * @param tipoSolicitante String con el tipo de Solicitante (D:Declarante Ciudadano/E:Ente publico)
     * @param tipoDocumento String con el tipo de documento del solicitante
     * @param numeroDocumento String con el numero de documento del solicitante
     * @return Lista de objetos de tipo Solicitud
     */
    @Override
    public List<Solicitud> consultarPorNumeroDocSolicitante(String tipoSolicitante,
            String tipoDocumento, String numeroDocumento) {

        LOG.debug("=====INICIANDO SolicitudNegocio.consultarPorNumeroDocSolicitante==========");
        List<SolicitudEntidad> listaSolicitudesEntidad;
        String tipoDoc;
        
        if (tipoSolicitante.equalsIgnoreCase(Constantes.TIPO_SOLICITANTE_DECLARANTE)) {
            tipoDoc = buscarValorProperties(tipoDocumento);
            LOG.info("ciudadano - tipoDoc --> "+tipoDoc);
            listaSolicitudesEntidad = solicitudRepository.
                    buscarPorDocSolicitanteDec(tipoDoc, numeroDocumento);
        } else {
            LOG.info("ente - tipoDocumento --> "+tipoDocumento);
            listaSolicitudesEntidad = solicitudRepository.
                    buscarPorDocSolicitanteEnte(Long.valueOf(tipoDocumento), numeroDocumento);
        }

        return solicitudMapper.entitiesToBOs(listaSolicitudesEntidad);
    }

    /**
     *
     * Metodo consultarTodos que permite consultar todas las solicitudes existentes
     *
     * @return Lista de objetos de tipo Solicitud
     */
    @Override
    public List<Solicitud> consultarTodos() {
        LOG.debug("=====INICIANDO SolicitudNegocio.ConsultarTodos==========");
        List<SolicitudEntidad> listaSolicitudesEntidad = (List<SolicitudEntidad>) solicitudRepository.findAll();
        return solicitudMapper.entitiesToBOs(listaSolicitudesEntidad);
    }

    /**
     *
     * Metodo actualizarEstadoSolicitud que permite actualizar el estado de una solicitud existente
     *
     * @param solicitud Objeto de tipo solicitud
     * @return Objeto de tipo Solicitud
     * @throws java.lang.Exception
     */
    @Override
    @Transactional
    public Solicitud actualizarEstadoSolicitud(Solicitud solicitud, String token) throws Exception {

        LOG.debug("=====INICIANDO actualizarEstadoSolicitud==========");

        SolicitudEntidad solicitudEntidad;
        Solicitud sol = new Solicitud();

        if (solicitud.getNumeroSolicitud() != null) {
            solicitudEntidad = solicitudRepository.findByNumero(solicitud.getNumeroSolicitud());

            LOG.debug("====Solicitud encontrada==========");
            if (solicitudEntidad == null) {
                LOG.error("ERROR: consultando la solicitusd para actualizar - solicitud no existe");

                throw new ResourceNotFoundException("Solicitud no encontrada");
            } else if (solicitud.getEstadoSolicitud() != null && solicitud.getMotivoCambioEstado() != null) {
                LOG.debug("Datos validos para la actualizacion");
                sol = actualizarEstado(solicitudEntidad, solicitud, sol, token);

            } else {
                LOG.error("ERROR: Datos invalidos para la actualizacion - Datos incompletos");
                throw new ResourceNotFoundException("Datos invalidos para la actualizacion");
            }
        }
        return sol;
    }

    /**
     *
     * Metodo privado actualizarEstado que permite actualizar el estado de una solicitud
     *
     * @param solicitudEntidad objeto entidad de tisolicitudEntidad
     * @param solicitud objeto de tipo Solicitud
     * @param sol objeto de tipo Solicitud
     * @return Objeto de tipo Solicitud
     * @throws Exception
     */
    private Solicitud actualizarEstado(SolicitudEntidad solicitudEntidad, Solicitud solicitud, Solicitud sol, String token) throws Exception {
        boolean existLibro;
        Solicitud solReturn;
        SolicitudEntidad se;
        solReturn = sol;
        SolicitudEstatusEntidad estatusEntidad;

        try {
//            solicitudEntidad.setNumero(solicitud.getNumeroSolicitud());
            estatusEntidad = this.buscarEstatus(solicitud.getEstadoSolicitud());
            solicitudEntidad.setEstatus(estatusEntidad);
            solicitudEntidad.setObservacion(solicitud.getMotivoCambioEstado());
            se = solicitudRepository.save(solicitudEntidad);
            solReturn = solicitudMapper.entityToBO(se);

            //Actualizar libro diario
            actualizarLibroDiario(solicitud.getNumeroSolicitud(), solicitud.getEstadoSolicitud(), token);

            existLibro = existeLibroDiario(solReturn.getNumeroSolicitud());

            if (!existLibro) {
                LOG.error("ERROR: Libro Diario no fue actualizado, solicitud: " + solReturn.getNumeroSolicitud());
                throw new ResourceNotFoundException("Libro Diario no encontrado");
            }

        } catch (Exception e) {
            throw new Exception("Error actualizando Estatus de la Solicitud Nro "
                    + solicitud.getNumeroSolicitud() + ": " + e);

        }
        return solReturn;
    }
//
//    /**
//     *
//     * Metodo consultarEstadoSolicitud que permite consultar el detalle del
//     * estado de una solicitud segun el codigo
//     *
//     * @param codigo String que describe el codigo
//     * @return Objeto de tipo SolicitudEstatusEntidad
//     * @throws Exception
//     */
//    private SolicitudEstatusEntidad consultarEstadoSolicitud(String codigo) {
//        return solicitudEstatusRepository.findByCodigo(codigo.toUpperCase());
//    }

    /**
     *
     * Metodo generarSolicitud que permite crear una solicitud nueva
     *
     * @param solicitud Objeto de tipo solicitud
     * @return Objeto de tipo Solicitud
     */
    @Override
    @Transactional
    public Solicitud generarSolicitud(Solicitud solicitud, String token) {
        LOG.debug("=====INICIANDO SolicitudNegocio.generarSolicitud==========");

        SolicitudEntidad solicitudEntidad = new SolicitudEntidad();
        SolicitudEntidad se;
        SolicitudEstatusEntidad solicitudEstatusEntidad;
        Solicitud sol = new Solicitud();
        UsuarioEntidad usuarioEntidad;
        TramiteEntidad tramiteEntidad;
        EstatusTramiteEntidad estatusTramiteEntidad;
        DiarioEntidad diarioEntidad;
        SolicitanteEntidad solicitanteEntidad;
        String idOfic, nroMil, estatusInicial, numero;
        List<SolicitudEntidad> solicitudes = new ArrayList<>();
        LibroDiarioEntidad libroDiarioEntidad;

        try {
            /*
             * incio Busqueda del identificador del usuario en la oficina por el
             * loggin
             */
            usuarioEntidad = usuarioRepository.buscarPorNombre(solicitud.getFuncionario().getUsuarios().get(0).getNombre());
            if (usuarioEntidad == null) {
                LOG.error("ERROR: Consultando Usuario para la Solicitud: "
                        + solicitud.getFuncionario().getUsuarios().get(0).getNombre());
                throw new ResourceNotFoundException("Usuario para la Solicitud no encontrado");
            }

            LOG.debug("=====================Consulto el usuario del funcionario==============================");
            OficinaFuncionarioEntidad oficinaFuncionarioEntidad = usuarioEntidad.getOficinaFuncionario();
            /* Agregando el funcionario a la solicitud */
            solicitudEntidad.setOficinaFuncionario(oficinaFuncionarioEntidad);
            LOG.debug("=====================Funcionario agregado a la Solicitud==============================");
            /* Inicio obtener tramite */

            LOG.debug("=====================Consulto el tramite de la solicitud==============================");
            tramiteEntidad = tramiteRepository.buscarPorCodigo(solicitud.getTramite().getCodigo());
            if (tramiteEntidad == null) {
                LOG.error("ERROR: Consultando Tramite de la Solicitud: " + solicitud.getTramite().getCodigo());
                throw new ResourceNotFoundException("Tramite de la Solicitud no encontrado");
            }

            LOG.debug("=====Lee properties para buscar un estatus inicial dado el codigo del tramite======");
            estatusInicial = buscarValorProperties(Constantes.ESTATUS_INICIAL + solicitud.getTramite().getCodigo());
            LOG.info("estatusInicial --> " + estatusInicial);
            if (estatusInicial == null || estatusInicial.isEmpty()) {
                //Setea estatus por defecto que es Pendiente por Autenticar
                estatusInicial = Constantes.PENDIENTE_POR_AUTENTICAR;
            }

            LOG.debug("=====================Buscar Estatus para la Solicitud en BDs==============================");
            LOG.info("estatusInicial a buscar--> " + estatusInicial);
            solicitudEstatusEntidad = buscarEstatus(estatusInicial);

            LOG.debug("=====================Consultando Estatus del Tramite==============================");
            LOG.info("Estatus -->" + Constantes.TRAMITE_ABIERTO + "-" + solicitud.getTramite().getCodigo());
            estatusTramiteEntidad = consultarEstatusTramite(Constantes.TRAMITE_ABIERTO, solicitud.getTramite().getCodigo());
            LOG.info("estatusTramiteEntidad -->" + estatusTramiteEntidad.getTramite().getCodigo());

            LOG.debug("=====================Buscar Estatus para la Solicitud==============================");
            solicitudEstatusEntidad = buscarEstatus(Constantes.PENDIENTE_POR_AUTENTICAR);

            if (solicitudEstatusEntidad == null) {
                LOG.error("ERROR: Consultando Estatus de la Solicitud: " + Constantes.PENDIENTE_POR_AUTENTICAR);
                throw new ResourceNotFoundException("Estatus de la Solicitud no encontrado");
            }

            LOG.debug("=====================Tramite agregado a la Solicitud==============================");
            /* Agregando el tramite a la solicitud */
            solicitudEntidad.setTramite(tramiteEntidad);
            solicitudEntidad.setFechaInicio(new DateTime());
            solicitudEntidad.setEstatusTramite(estatusTramiteEntidad);
            /* Generar numero de la solicitud */
            numero = generarNumeroSolicitud(oficinaFuncionarioEntidad.getOficina().getId());

            solicitudEntidad.setNumero(numero);
            solicitudEntidad.setEstatus(solicitudEstatusEntidad);

            LOG.debug("=====================Solicitante==============================");
            /* Llenando el objeto Solicitante */
            LOG.info("tipo solicitante " + solicitud.getSolicitante().getTipo());
            solicitanteEntidad = solicitanteMapper.solicitanteBOTOsolicitanteEntidad(solicitud.getSolicitante());
            LOG.info("Solicitante --> " + solicitanteEntidad.getNumeroDocumentoOficio());
            solicitudEntidad.setSolicitante(solicitanteEntidad);

            solicitudes.add(solicitudEntidad);
            solicitanteEntidad.setSolicitud(solicitudes);

            LOG.debug("=====================Guardar el solicitante==============================");
            solicitanteRepository.save(solicitanteEntidad);

            LOG.debug("=====================Guardar la solicitud==============================");
            se = solicitudRepository.save(solicitudEntidad);
            if (se != null) {
                sol = solicitudMapper.entityToBO(se);
                sol.setNumeroSolicitud(solicitudEntidad.getNumero());
                sol.setEstadoSolicitud(solicitudEntidad.getEstatus().getNombre());
                LOG.info("solicitud generada- NUMERO SOLICITUD " + sol.getNumeroSolicitud());

                //Fecha del dia
                Date date = new Date();
                LOG.info("hoy :" + date.toString());

                DateFormat fecha = new SimpleDateFormat(Constantes.FORMATO_DDMMYYYY_SLASH);
                String fecConv = fecha.format(date);
                LOG.info("fecConv: " + fecConv);
                LOG.info("codOfic: " + oficinaFuncionarioEntidad.getOficina().getCodigo());

                //Buscar libro diario del dia
                libroDiarioEntidad = libroDiarioRepository.buscarLibroDiarioPorFecha(fecConv,
                        oficinaFuncionarioEntidad.getOficina().getCodigo());
                LOG.info("libroDiarioEntidad: " + libroDiarioEntidad.getLibroDiarioEstatus().getId()
                        + " " + libroDiarioEntidad.getLibroDiarioEstatus().getNombre());

                //lee properties
                String estAbiertoLibroDiario = buscarValorProperties(Constantes.ESTATUS_ABIERTO_LIBRO_DIARIO);

                LOG.info("id estatus libro : " + libroDiarioEntidad.getLibroDiarioEstatus().getId());
                if (!String.valueOf(libroDiarioEntidad.getLibroDiarioEstatus().getId()).equals(estAbiertoLibroDiario)) {
                    throw new Exception("Libro Diario no esta abierto. Debe abrir un libro diario.");
                }

                //Crea libro diario
                diarioEntidad = crearLibroDiario(solicitudEntidad.getEstatus().getCodigo(), token);
                diarioEntidad.setSolicitud(solicitudEntidad);
                diarioEntidad.setLibroDiario(libroDiarioEntidad);
                diarioRepository.save(diarioEntidad);
            }

        } catch (Exception e) {
            LOG.error("problemas al general la solicitud", e);
            throw new ResourceNotFoundException("Error generarSolicitud: " + e);
        }
        return sol;

    }

    /**
     * Generacion del Numero de solicitud
     *
     * @param idOficina String id de la oficina
     * @return String Numero de la solicitud
     */
    private String generarNumeroSolicitud(long idOficina) {

        String numSolic;
        String idOfic;
        String nroMil;

        String numero = String.valueOf(idOficina);
        idOfic = creandoIdOficina(numero);

        LOG.info("idOfic " + idOfic);
        Calendar calendario = Calendar.getInstance();
        nroMil = String.format("%03d", Integer.parseInt(String.valueOf(calendario.get(
                Calendar.MILLISECOND))));
        LOG.info("nroMill " + nroMil);

        /* Generando el numero de solicitud */
        numSolic = String.valueOf(calendario.get(Calendar.YEAR))
                + String.valueOf(calendario.get(Calendar.DAY_OF_YEAR))
                + idOfic + nroMil;

        return numSolic;

    }

    /**
     *
     * Metodo privado creandoIdOficina que permite crear el Id de una oficina
     *
     * @param numero String que describe el numero generado
     * @return String que retorna el id de la oficina
     */
    private String creandoIdOficina(String numero) {
        String idOficReturn;

        idOficReturn = String.format("%05d", Integer.parseInt(numero));

        if (numero.length() > 5) {
            idOficReturn = numero.substring(numero.length() - 5);
        }

        return idOficReturn;
    }

    /**
     *
     * Metodo privado tipoSolicitantePorSolicitud que permite crear el tipoSolicitante
     *
     * @param solicitud objeto de tipo Solicitud
     * @param solicitudes objeto entidad de tipo lista de tipo Solicitud
     * @return objeto entidad de tipo Solicitante
     */
    private SolicitanteEntidad tipoSolicitantePorSolicitud(Solicitud solicitud,
            List<SolicitudEntidad> solicitudes) throws GenericException {

        SolicitanteEntidad solicitanteReturn;

        LOG.debug("=====================Tipo Solicitante Ente Publico==============================");
        solicitanteReturn = solicitanteMapper.solicitanteBOTOsolicitanteEntidad(solicitud.getSolicitante());

        solicitanteReturn.setSolicitud(solicitudes);
        return solicitanteReturn;
    }

    /**
     *
     * @param loginUsuario String login del usuario
     * @param codigoRol String codigo rol de usuario
     * @return Lista de Objeto Solicitud
     */
    @Override
    public List<Solicitud> consultarPorUsuario(String loginUsuario, String codigoRol) {

        LOG.debug("=====INICIANDO SolicitudNegocio.consultarPorUsuario==========");
        String[] codigos = {"AB", "PA", "PI", "NC", "PEA", "PPI", "PPD", "IDJ", "CDJ", "PRM", "PRNM", "CA", "CE",
            "PCN", "PVO", "PCA", "PPIO", "PCDO", "NCA"};

        List<SolicitudEntidad> listaSolicitudesEntidad = new ArrayList<>();
        List<SolicitudEstatusEntidad> listaSolicitudEstatusEntidad;
        UsuarioEntidad usuarioEntidad;
        OficinaEntidad oficinaEntidad;
        RolUsuarioEntidad rolUsuarioEntidad;

        //Busca el usuario
        usuarioEntidad = usuarioRepository.buscarPorNombre(loginUsuario);
        if (usuarioEntidad == null) {
            LOG.error("ERROR: consultando el  usuario - el usuario no existe");
            throw new ResourceNotFoundException("Usuario no existe");
        }
        //Busca el rol
        rolUsuarioEntidad = rolUsuarioRepository.buscarIdRolFunc(usuarioEntidad.getNombre());
        if (rolUsuarioEntidad == null) {
            LOG.error("ERROR: rol no existe: " + codigoRol);
            throw new ResourceNotFoundException("Rol no existe - " + codigoRol);
        }
        //Valida que el parametro rol sea correcto para el funcionario a consultar
        if (!rolUsuarioEntidad.getRol().getCodigo().equals(codigoRol)) {
            LOG.error("ERROR: parametro rol es incorrecto para funcionario: " + loginUsuario + " - " + codigoRol);
            throw new ResourceNotFoundException("Parametro incorrecto para funcionario - " + loginUsuario + " - "
                    + codigoRol);
        }
        //Busca la oficina
        oficinaEntidad = oficinaRepository.findByCodigo(
                usuarioEntidad.getOficinaFuncionario().getOficina().getCodigo());

        LOG.info("Id Oficina --> " + oficinaEntidad.getId());
        try {
            if (Constantes.ROL_REGISTRADOR.equals(codigoRol)) {
                listaSolicitudesEntidad = solicitudRepository.findByOficinaFuncionarioOficinaId(oficinaEntidad.getId());
            } else if (Constantes.ROL_INSCRIPCION.equals(codigoRol)) {
                listaSolicitudEstatusEntidad = buscarListaEstatus(codigos);
                listaSolicitudesEntidad
                        = solicitudRepository.findByEstatusInAndOficinaFuncionarioOficinaId(
                                listaSolicitudEstatusEntidad, oficinaEntidad.getId());
                LOG.info(codigoRol + " - Solicitudes encontradas Rol Inscripcion: " + listaSolicitudesEntidad.size());
            } else if (Constantes.ROL_ATENCION.equals(codigoRol)) {
                /* No puede ver la lista de las solciitudes en el Inbox */
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error consultarPorUsuario: " + e);
        }

        LOG.info("Solicitudes encontradas: --> " + listaSolicitudesEntidad.size());

        return solicitudMapper.entitiesToBOs(listaSolicitudesEntidad);
    }

    /**
     *
     * Metodo consultarEstadoSolicitud que permite consultar el detalle del estado de una solicitud segun el codigo
     *
     * @param codigoEstatus String que describe el codigo del Estatus
     * @param codigoTramite String que describe el codigo del Tramite
     * @return Objeto de tipo EstatusTramiteEntidad
     * @throws Exception
     */
    private EstatusTramiteEntidad consultarEstatusTramite(String codigoEstatus, String codigoTramite) {
        LOG.info("consultarEstatusTramite: " + codigoEstatus + "-" + codigoTramite);
        return estatusTramiteRepository.findByEstatusNombreAndTramiteCodigo(codigoEstatus, codigoTramite);
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un rango de fechas
     *
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    @Override
    public List<Solicitud> consultarSolicitudesRangoFecha(String fechaDesde, String fechaHasta) {

        LOG.debug("=====INICIANDO SolicitudNegocio.consultarSolicitudes.fechas==========");
        List<Solicitud> solicitudes;
        List<SolicitudEntidad> solicitudesEntidad;
        DateTime dtDesde;
        DateTime dtHasta;

        fecDesde = doFormatearFecha(fechaDesde, Constantes.FORMATO_DDMMYYYY);
        fecHasta = doFormatearFecha(fechaHasta, Constantes.FORMATO_DDMMYYYY);

        dtDesde = new DateTime(fecDesde.getTime());
        dtHasta = new DateTime(fecHasta.getTime());

        solicitudesEntidad = solicitudRepository.findByFechaInicioBetween(dtDesde, dtHasta);
        solicitudes = solicitudMapper.entitiesToBOs(solicitudesEntidad);
        LOG.info("consultarSolicitudes.fechas --> " + solicitudes.size());

        return solicitudes;
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos (tramites o estatus de solicitud) y el
     * tipo de codigo (T:Tramite o E:Estatus)
     *
     * @param codigos Arreglo de tipo String con los codigos de tramites o estatus de solicitud
     * @param tipoCodigo String indica el tipo de codigo (T:Tramite o E:Estatus)
     * @return Lista de objetos de tipo solicitud
     */
    @Override
    public List<Solicitud> consultarSolicitudesTramiteOEstatus(String[] codigos, String tipoCodigo) {
        LOG.debug("=====INICIANDO SolicitudNegocio.consultarSolicitudes.codigos.tipoCodigo==========");

        List<Solicitud> solicitudes = new ArrayList<>();

        if (!"T".equals(tipoCodigo) && !"E".equals(tipoCodigo)) {
            LOG.error("ERROR: tipoCodigo - no es valido --> " + tipoCodigo);
            throw new ResourceNotFoundException("tipoCodigo debe ser T:Tramite o E:Estatus");
        }

        switch (tipoCodigo) {
            case "T":
                solicitudes = consultarPorTramite(codigos);
                break;
            case "E":
                solicitudes = consultarPorEstatus(codigos);
                break;
            default:
                break;
        }
        LOG.info("consultarSolicitudes.codigos.tipoCodigo --> " + solicitudes.size());
        return solicitudes;
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos de tramites y dado un arreglo de codigos
     * de estatus
     *
     * @param codigosTramite Arreglo de tipo String con los codigos de tramites
     * @param codigosEstatus Arreglo de tipo String con los codigos de estatus de solicitud
     * @return Lista de objetos de tipo solicitud
     */
    @Override
    public List<Solicitud> consultarSolicitudesTramiteYEstatus(String[] codigosTramite, String[] codigosEstatus) {
        LOG.debug("=====INICIANDO SolicitudNegocio.consultarSolicitudes.codigosTramite.codigosEstatus==========");
        List<Solicitud> solicitudes = new ArrayList<>();
        List<SolicitudEntidad> solicitudesEntidad;
        List<SolicitudEstatusEntidad> listaSolicitudEstatusEntidad = buscarListaEstatus(codigosEstatus);
        List<TramiteEntidad> listaTramiteEntidad = buscarListaTramite(codigosTramite);

        if (!listaSolicitudEstatusEntidad.isEmpty() && !listaTramiteEntidad.isEmpty()) {
            solicitudesEntidad = solicitudRepository.findByTramiteInAndEstatusIn(listaTramiteEntidad,
                    listaSolicitudEstatusEntidad);
            solicitudes = solicitudMapper.entitiesToBOs(solicitudesEntidad);
        }
        LOG.info("consultarSolicitudes.codigosTramite.codigosEstatus --> " + solicitudes.size());
        return solicitudes;
    }

    /**
     *
     * Metodo que consulta las solicitudes dado un arreglo de codigos de tramite
     *
     * @param codigos Array que contiene los codigos de tramites
     * @return Lista de objetos de tipo Solicitud
     */
    private List<Solicitud> consultarPorTramite(String[] codigos) {
        LOG.debug("=====INICIANDO SolicitudNegocio.consultarSolicitudes.PorTramite.codigos==========");
        List<Solicitud> solicitudes = new ArrayList<>();
        List<SolicitudEntidad> solicitudesEntidad;
        List<TramiteEntidad> listaTramiteEntidad = buscarListaTramite(codigos);

        if (!listaTramiteEntidad.isEmpty()) {
            solicitudesEntidad = solicitudRepository.findByTramiteIn(listaTramiteEntidad);
            solicitudes = solicitudMapper.entitiesToBOs(solicitudesEntidad);
        }
        LOG.info("consultarSolicitudes.PorTramite.codigos --> " + solicitudes.size());
        return solicitudes;
    }

    /**
     *
     * Metodo que consulta las solicitudes dado un arreglo de codigos de estatus de solicitud
     *
     * @param codigos Array que contiene los codigos de estatus de solicitud
     * @return Lista de objetos de tipo Solicitud
     */
    private List<Solicitud> consultarPorEstatus(String[] codigos) {
        LOG.debug("=====INICIANDO SolicitudNegocio.consultarSolicitudes.PorEstatus.codigos==========");
        List<Solicitud> solicitudes = new ArrayList<>();
        List<SolicitudEntidad> solicitudesEntidad;
        List<SolicitudEstatusEntidad> listaSolicitudEstatusEntidad = buscarListaEstatus(codigos);

        if (!listaSolicitudEstatusEntidad.isEmpty()) {
            solicitudesEntidad = solicitudRepository.findByEstatusIn(listaSolicitudEstatusEntidad);
            solicitudes = solicitudMapper.entitiesToBOs(solicitudesEntidad);
        }
        LOG.info("consultarSolicitudes.PorEstatus.codigos --> " + solicitudes.size());
        return solicitudes;
    }

    /**
     *
     * Metodo construye la lista de objetos de tipo TramiteEntidad dado un arreglo codigos de tramites
     *
     * @param tramites Array que contiene los codigos de tramites
     * @return Lista de objetos de tipo TramiteEntidad
     */
    private List<TramiteEntidad> buscarListaTramite(String[] tramites) {
        LOG.debug("=====INICIANDO SolicitudNegocio.buscarListaTramite==========");
        TramiteEntidad tramiteEntidad;
        List<TramiteEntidad> listaTramiteEntidad = new ArrayList<>();

        for (String tramite : tramites) {
            tramiteEntidad = buscarTramite(tramite);
            if (tramiteEntidad != null) {
                listaTramiteEntidad.add(tramiteEntidad);
            }
        }

        return listaTramiteEntidad;
    }

    /**
     *
     * Metodo construye la lista de objetos de tipo SolicitudEstatusEntidad dado un arreglo codigos de estatus de
     * solicitud
     *
     * @param estatus Array que contiene los codigos de estatus de solicitud
     * @return Lista de objetos de tipo SolicitudEstatusEntidad
     */
    private List<SolicitudEstatusEntidad> buscarListaEstatus(String[] estatus) {
        LOG.debug("=====INICIANDO SolicitudNegocio.buscarListaEstatus==========");
        SolicitudEstatusEntidad estatusEntidad;
        List<SolicitudEstatusEntidad> listaEstatusEntidad = new ArrayList<>();

        for (String est : estatus) {
            estatusEntidad = buscarEstatus(est);
            if (estatusEntidad != null) {
                listaEstatusEntidad.add(estatusEntidad);
            }
        }

        return listaEstatusEntidad;
    }

    /**
     *
     * Metodo que permite obtener la entidad de Tramite dado un codigo
     *
     * @param codigoTramite String con el codigo del tramite
     * @return Objeto de tipo TramiteEntidad
     */
    private TramiteEntidad buscarTramite(String codigoTramite) {
        LOG.debug("=====INICIANDO SolicitudNegocio.buscarTramite==========");
        LOG.info("buscando el tramite por el codigo " + codigoTramite);

        if (codigoTramite.startsWith("[")) {
            codigoTramite = codigoTramite.substring(1);
        }

        if (codigoTramite.endsWith("]")) {
            codigoTramite = codigoTramite.substring(0, codigoTramite.length() - 1);
        }
        LOG.info("buscando el tramite por el codigo (procesado) " + codigoTramite);

        TramiteEntidad tramiteEntidad;
        tramiteEntidad = tramiteRepository.buscarPorCodigo(codigoTramite);

        return tramiteEntidad;
    }

    /**
     *
     * Metodo que permite obtener la entidad de SolicitudEstatus dado un codigo
     *
     * @param codigoEstatus String con el codigo del estatus
     * @return Objeto de tipo SolicitudEstatusEntidad
     */
    private SolicitudEstatusEntidad buscarEstatus(String codigoEstatus) {
        LOG.debug("=====INICIANDO SolicitudNegocio.buscarEstatus==========");
        SolicitudEstatusEntidad estatusEntidad;
        LOG.info("buscando el tramite por el codigo " + codigoEstatus);

        if (codigoEstatus.startsWith("[")) {
            codigoEstatus = codigoEstatus.substring(1);
        }

        if (codigoEstatus.endsWith("]")) {
            codigoEstatus = codigoEstatus.substring(0, codigoEstatus.length() - 1);
        }
        LOG.info("buscando el tramite por el codigo (procesado) " + codigoEstatus);

        estatusEntidad = solicitudEstatusRepository.findByCodigo(codigoEstatus);

        return estatusEntidad;
    }

    /**
     *
     * Metodo que formatea un String a Date
     *
     * @param fechaInput String que contiene una fecha
     * @param formato String que contiene el formato usado para formatear
     * @return Objeto de tipo Date
     */
    public static Date doFormatearFecha(String fechaInput, String formato) {

        SimpleDateFormat dateParser = new SimpleDateFormat(formato);
        Date fecha = null;

        try {
            fecha = dateParser.parse(fechaInput);
        } catch (ParseException ex) {
            LOG.error("ERROR: formateando la fecha - formateada--> " + fechaInput, ex);
            throw new ResourceNotFoundException("Fecha inv\\u00e1lida");
        }
        return fecha;
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos (tramites o estatus de solicitud),el tipo
     * de codigo (T:Tramite o E:Estatus) y un rango de fechas
     *
     * @param codigos Arreglo de tipo String con los codigos de tramites o estatus de solicitud
     * @param tipoCodigo String indica el tipo de codigo (T:Tramite o E:Estatus)
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    @Override
    public List<Solicitud> consultarSolicitudesTramiteOEstatusRangoFechas(String[] codigos, String tipoCodigo,
            String fechaDesde, String fechaHasta) {
        LOG.debug("=====INICIANDO consultarSolicitudes.codigos.tipoCodigo.fechaDesde.fechaHasta==========");
        List<Solicitud> solicitudes = new ArrayList<>();

        if (!"T".equals(tipoCodigo) && !"E".equals(tipoCodigo)) {
            LOG.error("ERROR: tipoCodigo - no es valido --> " + tipoCodigo);
            throw new ResourceNotFoundException("tipoCodigo debe ser T:Tramite o E:Estatus");
        }

        switch (tipoCodigo) {
            case "T":
                solicitudes = consultarPorTramiteYFecha(codigos, fechaDesde, fechaHasta);
                break;
            case "E":
                solicitudes = consultarPorEstatusYFecha(codigos, fechaDesde, fechaHasta);
                break;
            default:
                break;
        }

        return solicitudes;
    }

    /**
     *
     * Metodo que consulta las solicitudes dado un arreglo de codigos de tramite y dado un rago de fechas
     *
     * @param codigos Array que contiene los codigos de tramites
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo Solicitud
     */
    private List<Solicitud> consultarPorTramiteYFecha(String[] codigos, String fechaDesde, String fechaHasta) {
        LOG.debug("=====INICIANDO consultarPorTramiteYFecha.codigos.fechaDesde.fechaHasta==========");
        List<Solicitud> solicitudes = new ArrayList<>();
        List<SolicitudEntidad> solicitudesEntidad;
        List<TramiteEntidad> listaTramiteEntidad = buscarListaTramite(codigos);
        DateTime dtDesde;
        DateTime dtHasta;

        if (!listaTramiteEntidad.isEmpty()) {
            fecDesde = doFormatearFecha(fechaDesde, Constantes.FORMATO_DDMMYYYY);
            fecHasta = doFormatearFecha(fechaHasta, Constantes.FORMATO_DDMMYYYY);

            dtDesde = new DateTime(fecDesde.getTime());
            dtHasta = new DateTime(fecHasta.getTime());
            solicitudesEntidad = solicitudRepository.findByTramiteInAndFechaInicioBetween(listaTramiteEntidad,
                    dtDesde, dtHasta);
            solicitudes = solicitudMapper.entitiesToBOs(solicitudesEntidad);
        }
        LOG.info("consultarPorTramiteYFecha.PorEstatus.codigos --> " + solicitudes.size());
        return solicitudes;
    }

    /**
     *
     * Metodo que consulta las solicitudes dado un arreglo de codigos de estatus de solicitud y un rango de fechas
     *
     * @param codigos Array que contiene los codigos de estatus de solicitud
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo Solicitud
     */
    private List<Solicitud> consultarPorEstatusYFecha(String[] codigos, String fechaDesde, String fechaHasta) {
        LOG.debug("=====INICIANDO consultarPorEstatusYFecha.codigos.fechaDesde.fechaHasta==========");
        List<Solicitud> solicitudes = new ArrayList<>();
        List<SolicitudEntidad> solicitudesEntidad;
        List<SolicitudEstatusEntidad> listaSolicitudEstatusEntidad = buscarListaEstatus(codigos);
        DateTime dtDesde;
        DateTime dtHasta;

        if (!listaSolicitudEstatusEntidad.isEmpty()) {
            fecDesde = doFormatearFecha(fechaDesde, Constantes.FORMATO_DDMMYYYY);
            fecHasta = doFormatearFecha(fechaHasta, Constantes.FORMATO_DDMMYYYY);

            dtDesde = new DateTime(fecDesde.getTime());
            dtHasta = new DateTime(fecHasta.getTime());
            solicitudesEntidad = solicitudRepository.findByEstatusInAndFechaInicioBetween(listaSolicitudEstatusEntidad,
                    dtDesde, dtHasta);
            solicitudes = solicitudMapper.entitiesToBOs(solicitudesEntidad);
        }
        LOG.info("consultarPorEstatusYFecha.PorEstatus.codigos --> " + solicitudes.size());
        return solicitudes;
    }

    /**
     *
     * Metodo que permite consultar las solicitudes dado un arreglo de codigos de tramites, un arreglo de estatus y dado
     * un arreglo de codigos de estatus y un rango de fechas
     *
     * @param codigosTramite Arreglo de tipo String con los codigos de tramites
     * @param codigosEstatus Arreglo de tipo String con los codigos de estatus de solicitud
     * @param fechaDesde String que contiene la fecha desde
     * @param fechaHasta String que contiene la fecha hasta
     * @return Lista de objetos de tipo solicitud
     */
    @Override
    public List<Solicitud> consultarSolicitudesTramiteYEstatusRangoFechas(String[] codigosTramite, String[] codigosEstatus, String fechaDesde,
            String fechaHasta) {
        LOG.debug("=====INICIANDO consultarSolicitudes.codigosTramite.codigosEstatus.fechaDesde.fechaHasta==========");
        List<Solicitud> solicitudes = new ArrayList<>();
        List<SolicitudEntidad> solicitudesEntidad;
        List<SolicitudEstatusEntidad> listaSolicitudEstatusEntidad = buscarListaEstatus(codigosEstatus);
        List<TramiteEntidad> listaTramiteEntidad = buscarListaTramite(codigosTramite);
        DateTime dtDesde;
        DateTime dtHasta;

        if (!listaSolicitudEstatusEntidad.isEmpty() && !listaTramiteEntidad.isEmpty()) {
            fecDesde = doFormatearFecha(fechaDesde, Constantes.FORMATO_DDMMYYYY);
            fecHasta = doFormatearFecha(fechaHasta, Constantes.FORMATO_DDMMYYYY);

            dtDesde = new DateTime(fecDesde.getTime());
            dtHasta = new DateTime(fecHasta.getTime());
            solicitudesEntidad = solicitudRepository.findByTramiteInAndEstatusInAndFechaInicioBetween(
                    listaTramiteEntidad, listaSolicitudEstatusEntidad, dtDesde, dtHasta);
            solicitudes = solicitudMapper.entitiesToBOs(solicitudesEntidad);
        }
        LOG.info("consultarSolicitudes.codigosTramite.codigosEstatus.fechaDesde.fechaHasta --> " + solicitudes.size());
        return solicitudes;
    }

    /**
     * Actualiza libro diario dado un numero de solicitud. Se ejecuta en actualizarEstadoSolicitud
     *
     * @param codSolicitud String Numero de solicitud
     */
    private void actualizarLibroDiario(String codSolicitud, String codigoEstatus, String token) throws Exception {

        boolean resp;

        LOG.info("actualizarLibroDiario --> estatus a actualizar: " + codigoEstatus);
        try {
            LibroServicioCliente libroServicioCliente = new LibroServicioCliente();
            resp = libroServicioCliente.actualizarLibroDiario(codSolicitud, codigoEstatus, token);
            if (!resp) {
                throw new ResourceNotFoundException("Error actualizando Libro Diario para Solicitud: "
                        + codSolicitud + "-" + codigoEstatus);
            }
        } catch (GenericException | ResourceNotFoundException e) {
            throw new Exception("Error actualizando Libro Diario: " + e);
        }
    }

    /**
     * Crea libro diario dado un numero de solicitud. Se ejecuta en generarSolicitud
     *
     * @param codSolicitud String Numero de solicitud
     */
    private DiarioEntidad crearLibroDiario(String codigoEstatus, String token) {

        DiarioEntidad diarioEntidad = new DiarioEntidad();
        DiarioEstatusEntidad diarioEstatusEntidad;

        try {
            diarioEstatusEntidad = consultarEstadoLibroDiario(codigoEstatus);
            diarioEntidad.setEstatus(diarioEstatusEntidad);
            LOG.info("Estatus del Libro diario--> " + diarioEntidad.getEstatus().getCodigoDiarioEstatus());
            diarioEntidad.setFechaRegistro(new Date());

        } catch (Exception e) {
            LOG.error("ERROR: registrando en Libro Diario--> " + e);
            throw new ResourceNotFoundException("Error registrando en Libro Diario: " + e.getMessage());
        }

        return diarioEntidad;

    }

    /**
     * Buscar el estatus de un libro diario dado el codigo del estatus de la solicitud
     *
     * @param codigo String codigo del estatus de la solicitud
     * @return Objeto entidad de tipo DiarioEstatusEntidad
     */
    private DiarioEstatusEntidad consultarEstadoLibroDiario(String codigo) {

        DiarioEstatusEntidad diarioEstatusEntidad = null;
        LOG.info("consultarEstadoLibroDiario========== " + codigo);

        try {
            diarioEstatusEntidad = diarioEstatusRepository.findByCodigoDiarioEstatus(codigo.toUpperCase());
            if (diarioEstatusEntidad == null) {
                LOG.error("ERROR: Estatus no encontrado de Libro Diario --> ");
                throw new ResourceNotFoundException("Estatus no encontrado de Libro Diario: ");
            }
        } catch (Exception e) {
            LOG.error("ERROR: consultando Estatus del Libro Diario --> " + e);
            throw new ResourceNotFoundException("Error consultando Estatus del Libro Diario: " + e.getMessage());
        }

        return diarioEstatusEntidad;
    }

    /**
     * Busca un el paquete asociado a la solicitud
     *
     * @param numeroSolicitud numero de la solicitud
     * @return Objecto paquete
     */
    @Override
    public PaqueteBO buscarPaqueteBO(String numeroSolicitud) {

        SolicitudEntidad solicitudEntidad;
        PaqueteEntidad paqueteEntidad;
        PaqueteBO paqueteBO;

        solicitudEntidad = solicitudRepository.findByNumero(numeroSolicitud);

        paqueteEntidad = paqueteRepository.findBySolicitudEntidadId(solicitudEntidad.getId());

        paqueteBO = paqueteMapper.entityToBo(paqueteEntidad);

        return paqueteBO;

    }

    /**
     * Metodo que permite buscar el valor de una propiedad en el archivo properties del servicio solicitud
     *
     * @param clave String Propiedad a buscar en el archivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) {

        String valor = "";
        //lee properties
        LOG.info("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            LOG.info("Error leyendo properties: " + ex.getMessage());
        }
        LOG.info("buscarValorProperties --> valor --> " + valor);

        return valor;
    }

    /**
     * Busca el libro diario dado el numero de la solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @return Devuelve verdadero si encuentra el libro diario, falso en caso contrario
     */
    private boolean existeLibroDiario(String numeroSolicitud) {
        boolean existe = false;
        DiarioEntidad diarioEntidad = new DiarioEntidad();

        try {
            diarioEntidad = diarioRepository.findBySolicitudNumero(numeroSolicitud);
            if (diarioEntidad != null) {
                existe = true;
            }
        } catch (Exception e) {
            LOG.error("ERROR: consultando LibroDiario--> " + e);
            throw new ResourceNotFoundException("Error consultando Libro Diario: " + e.getMessage());
        }

        return existe;
    }
}
