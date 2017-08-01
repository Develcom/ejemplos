package ve.gob.cne.sarc.acta.core.acta.business.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.acta.core.acta.business.ActaBF;
import ve.gob.cne.sarc.acta.core.acta.mapper.ActaMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.ActaOfMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.ActaPrimigeniaMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.DecisionJudicialMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.DefuncionMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.ExtemporaneaMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.InsercionMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.MedidaProteccionMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.NacimientoMapper;
import ve.gob.cne.sarc.acta.core.acta.mapper.OREMapper;
import ve.gob.cne.sarc.acta.core.acta.util.Constantes;
import ve.gob.cne.sarc.comunes.acta.ActaPrimigenia;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.comunes.acta.Extemporanea;
import ve.gob.cne.sarc.comunes.acta.Insercion;
import ve.gob.cne.sarc.comunes.acta.MedidaProteccion;
import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.comunes.nacimiento.Nacimiento;
import ve.gob.cne.sarc.comunes.oficina.Ore;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.MedidaProteccionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ProcedenciaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DecisionJudicialEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ActaEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ActaPrimigeniaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoProcedenciaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DefuncionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ExtemporaneaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.InsercionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacimientoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PoderMandatorioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.ActaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.MedidaProteccionRepository;
import ve.gob.cne.sarc.persistencia.repositorios.FuncionarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaFuncionarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ProcedenciaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DecisionJudicialRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ActaEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ActaPrimigeniaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ParroquiaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoProcedenciaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TramiteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DefuncionRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ExtemporaneaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.InsercionRepository;
import ve.gob.cne.sarc.persistencia.repositorios.NacimientoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudRepository;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * ActaBFImpl.java
 *
 * @descripcion [Implementacion por defecto de la interfaz ActaBF]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 *
 */
@Component
public class ActaBFImpl implements ActaBF {

    private static final Logger LOG = LoggerFactory.getLogger(ActaBFImpl.class);
    private static final String NO_ENCONTRO_ACTA = "======No Encontro el acta====";
    private static final String BUSCAR_PROCEDENCIA = "============Buscando la procedencia=============";

    @Autowired
    public AdministradorPropiedades properties;

    @Autowired
    private ActaMapper actaMapper;

    @Autowired
    private ActaOfMapper actaOfMapper;

    @Autowired
    private NacimientoMapper nacimientoMapper;

    @Autowired
    private DefuncionMapper defuncionMapper;

    @Autowired
    private InsercionMapper insercionMapper;

    @Autowired
    private OREMapper oreMapper;

    @Autowired
    private DecisionJudicialMapper decisionJudicialMapper;

    @Autowired
    private MedidaProteccionMapper medidaProteccionMapper;

    @Autowired
    private ExtemporaneaMapper extemporaneaMapper;

    @Autowired
    private ActaPrimigeniaMapper actaPrimigeniaMapper;

    @Autowired
    private ActaRepository actaRepository;

    @Autowired
    private ActaPrimigeniaRepository actaPrimigeniaRepository;

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private NacimientoRepository nacimientoRepository;

    @Autowired
    private DefuncionRepository defuncionRepository;

    @Autowired
    private ActaEstatusRepository actaEstatusRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private OficinaFuncionarioRepository oficinaFuncionarioRepository;

    @Autowired
    private InsercionRepository insercionRepository;

    @Autowired
    private ParroquiaRepository parroquiaRepository;

    @Autowired
    private ProcedenciaRepository procedenciaRepository;

    @Autowired
    private TipoProcedenciaRepository tipoProcedenciaRepository;

    @Autowired
    private DecisionJudicialRepository decisionJudicialRepository;

    @Autowired
    private MedidaProteccionRepository medidaProteccionRepository;

    @Autowired
    private ExtemporaneaRepository extemporaneaRepository;

    /**
     * Busca un Acta segun la informacion suministrada
     *
     * @param acta Objecto con la informacion requeridad para la busqueda
     * @return Un acta si existe en caso contrario null
     */
    @Override
    @Transactional
    public Acta consultarActa(Acta acta) {
        LOG.info("=====INICIANDO ActaBFImpl.consultarActa==========");

        imprimirObjeto(acta);

        ActaEntidad actaEntidad = null;

        if (acta.getNumeroActa() != null) {

            LOG.info("buscando el acta por su numero (bussine facade) "
                    + acta.getNumeroActa());

            actaEntidad = actaRepository.findByNumeroActa(acta.getNumeroActa());
        }

        if (acta.getFechaCreacion() != null) {

            LOG.info("buscando el acta por la fecha de creacion (bussine facade) "
                    + acta.getFechaCreacion());

            actaEntidad = actaRepository.findByFechaCreacion(acta.getFechaCreacion());
        }

        if (acta.getNumeroFolio() != null) {

            LOG.info("buscando el acta por el numero de folio (bussine facade) "
                    + acta.getNumeroFolio());

            actaEntidad = actaRepository.findByNumeroFolio(acta.getNumeroFolio().intValue());
        }

        if (actaEntidad != null) {
            LOG.info("acta " + actaEntidad);
            return actaMapper.entityToBo(actaEntidad);
        }
        return null;
    }

    /**
     * Busca un listado de actas segun la informacion suministrada
     *
     * @param acta Objecto con la informacion requeridad para la busqueda
     * @return Un listado de Actas
     */
    @Override
    @Transactional
    public List<Acta> consultarActaLista(Acta acta) {

        LOG.info("=====INICIANDO ActaBFImpl.consultarActaLista==========");

        List<ActaEntidad> actas = new ArrayList<>();
        List<Acta> actasResult;

        imprimirObjeto(acta);

        actas = buscarActas(acta, actas);

        if (acta.getOficinaFuncionario() != null && acta.getOficinaFuncionario().getOficina().getCodigo() != null) {
            LOG.info("buscando el acta por la oficina (bussine facade) "
                    + acta.getOficinaFuncionario().getOficina().getCodigo());

            //anabell
            actas = actaRepository.findByOficinaFuncionarioOficinaCodigo(
                    acta.getOficinaFuncionario().getOficina().getCodigo());
            actasResult = tamActaOf(actas);

        }

        actasResult = tamActa(actas);

        return actasResult;
    }

    /**
     * Metodo que permite buscas las actas dado el acta
     *
     * @param acta Pojo con los datos del Acta
     * @param actas Lista de objetos entidad de tipo ActaEntidad
     * @return Lista de objetos entidad de tipo ActaEntidad
     */
    private List<ActaEntidad> buscarActas(Acta acta, List<ActaEntidad> actas) {
        List<ActaEntidad> actasReturn;
        actasReturn = actas;

        if (acta.getSolicitud() != null) {
            if (acta.getSolicitud().getNumeroSolicitud() != null) {

                LOG.info("buscando actas por numero de solicitud (bussine facade) "
                        + acta.getSolicitud().getNumeroSolicitud());

                actasReturn = actaRepository.findBySolicitudNumero(acta.getSolicitud().
                        getNumeroSolicitud());
            }

            if (acta.getSolicitud().getTramite() != null && acta.getSolicitud().getTramite().getCodigo() != null) {

                LOG.info("buscando actas por codigo del tramite (bussine facade) "
                        + acta.getSolicitud().getTramite().getCodigo());

                actasReturn = actaRepository.findBySolicitudTramiteCodigo(acta.
                        getSolicitud().getTramite().getCodigo());
            }
        }

        return actasReturn;
    }

    private List<Acta> tamActaOf(List<ActaEntidad> actas) {
        List<Acta> actasResult = null;
        if (!actas.isEmpty()) {
            actasResult = actaOfMapper.entitysToBos(actas);
            LOG.info("tamaño lista " + actasResult.size());
        }
        return actasResult;
    }

    private List<Acta> tamActa(List<ActaEntidad> actas) {
        List<Acta> actasResult = new ArrayList<>();
        if (!actas.isEmpty()) {
            actasResult = actaMapper.entitysToBos(actas);
            LOG.info("tamaño lista " + actasResult.size());
        }
        return actasResult;
    }

    /**
     * Actualiza un acta segun la informacion suministrada
     *
     * @param acta pojo que contiene la informacion del acta
     * @return verdadero si se actualizo falso en caso contrario
     */
    @Override
    public boolean actualizarActa(Acta acta) {

        LOG.info("=====INICIANDO ActaBFImpl.actualizarActa==========");

        LOG.info("actualizando el acta (bussine facade) "
                + acta.getNumeroActa());

        boolean resp = false;
        ActaEntidad actaEntBusc;
        ActaEntidad actaEntidad;
        ActaEntidad ae;
        OficinaFuncionarioEntidad oficinaFuncionarioEntidad;
        SolicitudEntidad solicitudEntidad;
        ActaEstatusEntidad actaEstatusEntidad;

        imprimirObjeto(acta);

        try {

            actaEntBusc = actaRepository.findByNumeroActa(acta.getNumeroActa());
            actaEstatusEntidad = actaEstatusRepository.findOne(Long.valueOf(acta.getActaEstatus()));
//                    findByNombre(acta.getActaEstatus());
            oficinaFuncionarioEntidad = actaEntBusc.getOficinaFuncionario();
            solicitudEntidad = actaEntBusc.getSolicitud();

            buscarActaOficinaFuncionarioSolicitud(actaEstatusEntidad, oficinaFuncionarioEntidad, solicitudEntidad);

            actaEntidad = actaMapper.boToEntity(acta);
            actaEntidad.setId(actaEntBusc.getId());
            actaEntidad.setEstatus(actaEstatusEntidad);
            actaEntidad.setOficinaFuncionario(oficinaFuncionarioEntidad);
            actaEntidad.setSolicitud(solicitudEntidad);

            actaEntidad = creandoActaParticipantesProcedenciasPoderes(actaEntidad);

            ae = actaRepository.save(actaEntidad);

            if (ae != null) {
                resp = true;
            }

        } catch (Exception e) {
            LOG.error("error al actualizar el acta", e);
        }
        return resp;
    }

    private void buscarActaOficinaFuncionarioSolicitud(ActaEstatusEntidad actaEstatusEntidad,
            OficinaFuncionarioEntidad oficinaFuncionarioEntidad, SolicitudEntidad solicitudEntidad) throws Exception {
        if (actaEstatusEntidad == null) {
            throw new Exception("no se encontro "
                    + "el estatus del acta");
        }

        if (oficinaFuncionarioEntidad == null) {
            throw new Exception("no se encontro la "
                    + "oficina - funcionario del acta");
        }

        if (solicitudEntidad == null) {
            throw new Exception("no se encontro la"
                    + " solicitud del acta");
        }

    }

    private ActaEntidad creandoActaParticipantesProcedenciasPoderes(ActaEntidad actaEntidad) {
        ActaEntidad actaReturn;
        actaReturn = actaEntidad;
        if (actaEntidad.getParticipantes() == null) {
            actaReturn.setParticipantes(new ArrayList<ParticipanteEntidad>());
        }
        if (actaEntidad.getProcedencias() == null) {
            actaReturn.setProcedencias(new ArrayList<ProcedenciaEntidad>());
        }
        if (actaEntidad.getPoderesMandatarios() == null) {
            actaReturn.setPoderesMandatarios(new ArrayList<PoderMandatorioEntidad>());
        }
        return actaReturn;
    }

    /**
     * Valida el acta de defuncion
     *
     * @param numeroCertificado long que contiene el numero del certificado
     * @return verdadero si se valido falso en caso contrario
     */
    private boolean validarDefuncion(long numeroCertificado) {
        boolean resp = false;
        DefuncionEntidad defuncionEntidad;
        Defuncion defuncion;
        defuncionEntidad = defuncionRepository.
                findNumeroCertificado(numeroCertificado);
        defuncion = defuncionMapper.entityToBo(defuncionEntidad);

        if (defuncion != null) {
            resp = defuncion.getNumeroCertificado() == numeroCertificado;
        }
        return resp;

    }

    /**
     * Valida el certificado medico segun el tramite
     *
     * @param numeroCertificado long que contiene el numero del certificado
     * @return verdadero si consigue el numero de certificado falso en caso
     * contrario
     */
    private boolean validarNacimiento(long numeroCertificado) {
        boolean resp = false;
        NacimientoEntidad nacimientoEntidad;
        Nacimiento nacimiento;
        nacimientoEntidad = nacimientoRepository.
                findByCertificado((int) numeroCertificado);
        nacimiento = nacimientoMapper.entityToBo(nacimientoEntidad);
        if (nacimiento != null) {
            resp = nacimiento.getNumeroCertificado() == numeroCertificado;
        }
        return resp;
    }

    /**
     * Valida si el certificado medico existe.
     *
     * @param codigoTramite String describe el codigo del tramite,
     * @param numeroCertificado long que describe el numero de certificado
     * @return Verdadero si existe en caso contrario falso
     */
    @Override
    @Transactional
    public boolean validarCertificadoMedico(String codigoTramite,
            long numeroCertificado) {
        LOG.info("=====INICIANDO ActaBFImpl.validarCertificadoMedico==========");

        boolean respuesta = false;
        switch (codigoTramite) {
            case "RDEFU":
                respuesta = validarDefuncion(numeroCertificado);
                break;
            case "RNACI":
                respuesta = validarNacimiento(numeroCertificado);
                break;
            default:
                LOG.info("Error al momento de ingresar el codigo del tramite.");
                break;
        }
        return respuesta;
    }

    /**
     * Metodo para buscar el acta
     *
     * @param numeroActa String que describe el numero de acta
     * @return Map<String, String> que permite almacenar como clave el esquema y
     * el valor si se encontro o no el numero del acta.
     */
    @Override
    public Map<String, String> existeActa(String numeroActa) {
        LOG.info("=====INICIANDO ActaBFImpl.existeActa==========");

        Map<String, String> listaAux = new HashMap<>();
        listaAux.put("null", "false");
        return listaAux;
    }

    /**
     * Metodo que permite buscar el numero de acta dado un numero de solicitud
     *
     * @param nroSolicitud String que contiene el numero de solicitud
     * @return String que contiene el Numero de Acta
     */
    @Override
    public String buscarNumeroActaPorSolic(String nroSolicitud) {
        LOG.info("=====INICIANDO ActaBFImpl.buscarNumeroActaPorSolic==========");

        String numeroActa;

        ActaEntidad actaEntidad;
        actaEntidad = actaRepository.findBySolicitudNumero(nroSolicitud).get(0);
        numeroActa = actaEntidad.getNumeroActa();

        return numeroActa;
    }

    /**
     * Metodo que permite guardar el ORE
     *
     * @param numSolicitud String que contiene el numero de la solicitud
     * @return objeto de tipo Ore
     * @throws java.lang.Exception
     */
    @Transactional
    @Override
    public Ore guardarORE(String numSolicitud) throws Exception {
        LOG.info("=====INICIANDO ActaBFImpl.guardarORE==========");
        SolicitudEntidad solicitudEntidad;
        OficinaEntidad oficinaEntidadSol;
        FuncionarioEntidad funcionarioEntidad;
        OficinaEntidad oficinaEntidadOre;
        OficinaFuncionarioEntidad oficinaFuncionarioEntidad;
        long tipoDirector = 2;
        Ore ore;
        LOG.info("=====================Solicitud==============================");
        solicitudEntidad = solicitudRepository.findByNumero(numSolicitud);

        if (solicitudEntidad == null) {
            LOG.error("ERROR: consultando la solicitud - solicitud no existe");
            throw new Exception("Solicitud no encontrada");
        }

        try {
            LOG.info("=====Buscando SolicitudEntidadSol===== "
                    + solicitudEntidad.getNumero());
            oficinaEntidadSol = buscandoOficina(numSolicitud);

            LOG.info("=====Buscando Ore===== " + oficinaEntidadSol.getCodigo());
            oficinaEntidadOre = buscandoOre(oficinaEntidadSol.getCodigo());

            LOG.info("=====Buscando Funcionario===== " + oficinaEntidadOre.getCodigo());
            funcionarioEntidad = buscandoFuncionario(oficinaEntidadOre.getId(), tipoDirector);

            LOG.info("=====Buscando OficinaFuncionario=====" + solicitudEntidad.getNumero());
            oficinaFuncionarioEntidad = buscandoOficinaFuncionario(funcionarioEntidad.getCedula());

            solicitudEntidad.setDirectorOre(oficinaFuncionarioEntidad);
            solicitudRepository.save(solicitudEntidad);
            ore = consultarOre(solicitudEntidad.getNumero());

            LOG.info("=====OreEntidad Guardado=====");

        } catch (Exception e) {
            LOG.error("ERROR: El director no pudo ser guardado", e);
            throw new Exception(e);

        }

        return ore;

    }

    /**
     *
     * Metodo buscandoOficina que permite buscar la Oficina
     *
     * @param numSolicitud String que describe el numero de Solicitud
     * @return Objeto de tipo OficinaEntidad
     *
     */
    private OficinaEntidad buscandoOficina(String numSolicitud) throws Exception {
        //buscando la oficina 
        OficinaEntidad oficinaEntidadSol;

        oficinaEntidadSol = oficinaRepository
                .findByOficinasFuncionariosSolicitudesNumero(numSolicitud);

        //buscando la oficina 
        if (oficinaEntidadSol == null) {
            LOG.error("ERROR: consultando la oficina - oficina no existe");
            throw new Exception("Oficina no encontrada");
        }
        return oficinaEntidadSol;
    }

    /**
     *
     * Metodo buscandoOre que permite buscar la Oficina ORE
     *
     * @param codOficinaEntidadSol String que describe el codigo de la Oficina
     * ORE
     * @return Objeto de tipo OficinaEntidad
     *
     */
    private OficinaEntidad buscandoOre(String codOficinaEntidadSol) throws Exception {
        //buscando la oficina 
        OficinaEntidad oficinaEntidadOre;
        oficinaEntidadOre = oficinaRepository.findByCodigo(codOficinaEntidadSol);
        //buscando la Oficina Ore por ser recursivo
        if (oficinaEntidadOre == null) {
            LOG.error("ERROR: consultando la oficina - oficina ORE no existe");
            throw new Exception("Oficina no encontrada");
        }
        return oficinaEntidadOre;
    }

    /**
     *
     * Metodo buscandoFuncionario que permite buscar el funcionario
     *
     * @param idOficinaOre String que describe el codigo de la Oficina ORE,
     * @param idTipoDirector String que describe si es Director
     * @return Objeto de tipo FuncionarioEntidad
     *
     */
    private FuncionarioEntidad buscandoFuncionario(long idOficinaOre, long idTipoDirector) throws Exception {
        //buscando la oficina 
        FuncionarioEntidad funcionarioEntidad;
        //anabell
        funcionarioEntidad = funcionarioRepository.findByOficinasFuncionariosOficinaIdAndOficinasFuncionariosTipoFuncionarioId(idOficinaOre, idTipoDirector);

        if (funcionarioEntidad == null) {
            LOG.error("ERROR: consultando el funcionario - funcionario no existe");
            throw new Exception("Funcionario no encontrado");
        }
        return funcionarioEntidad;
    }

    /**
     *
     * Metodo buscandoOficinaFuncionario que permite buscar la Oficina ORE
     *
     * @param cedula String que describe la cedula del funcionario
     * @return objeto de tipo OficinaFuncionarioEntidad
     *
     */
    private OficinaFuncionarioEntidad buscandoOficinaFuncionario(String cedula) throws Exception {
        //buscando la oficina 
        OficinaFuncionarioEntidad oficinaFuncionarioEntidad;
        oficinaFuncionarioEntidad = oficinaFuncionarioRepository
                .findByFuncionarioCedula(cedula);

        if (oficinaFuncionarioEntidad == null) {
            LOG.error("ERROR: consultando la OficinaFuncionario - Oficinafuncionario no existe");
            throw new Exception("Oficinafuncionario no encontrado");
        }
        return oficinaFuncionarioEntidad;
    }

    /**
     * Metodo responsable de buscar Ore dado un numero de solicitud
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return Ore, instancia de objeto que contiene la informacion del Ore
     * @throws java.lang.Exception
     */
    @Override
    public Ore consultarOre(String numSolicitud) throws Exception {

        SolicitudEntidad se;
        Ore ore = new Ore();
        try {
            se = solicitudRepository.findByNumero(numSolicitud);

            if (se == null) {
                throw new Exception("Solicitud " + numSolicitud + " no encontrada");
            } else if (se.getDirectorOre() == null) {
                throw new Exception("Solicitud " + numSolicitud + " no tiene ORE registrado");
            } else {
                ore = oreMapper.entityToBusiness(se);
            }

        } catch (Exception e) {
            LOG.error("problemas al consultar el ORE", e);
            throw new Exception(e);
        }

        LOG.info("=====INICIANDO ActaBFImpl.consultarOre==========");
        return ore;
    }

    /**
     * Actualiza el estatus de un acta
     *
     * @param idEstatusActa String que contiene el id del estatus del Acta
     * @param acta Acta pojo con la informacion del acta
     * @return acta pojo con la informacion del acta
     */
    @Transactional
    @Override
    public Acta actualizarEstatusActa(Long idEstatusActa, Acta acta) throws Exception {
        LOG.info("=====INICIANDO ActaBFImpl.actualizarEstatusActa==========");

        ActaEntidad actaEntidad;
        Acta actaResultado;

        imprimirObjeto(acta);

        actaEntidad = actaRepository.findByNumeroActa(acta.getNumeroActa());

        if (actaEntidad == null) {
            LOG.info("===========no consiguio el acta ==============");
            throw new Exception("Acta tiene valor nulo");
        }

        LOG.info("===========consiguio el acta ==============");

        if (acta.getActaEstatus() != null || ("").equals(acta.getActaEstatus())) {
            LOG.debug("Datos validos para la actualizacion");
            actaEntidad.setEstatus(consultarEstadoActa(idEstatusActa));
            actaResultado = actaMapper.entityToBo(actaRepository.save(actaEntidad));
        } else {
            LOG.error("ERROR: Datos invalidos para la actualizacion - Datos incompletos");
            throw new Exception("Datos invalidos para la actualizacion");
        }

        return actaResultado;

    }

    /**
     *
     * Metodo consultarEstadoActa que permite consultar el estado del Acta
     *
     * @param idEstatusActa Long que describe el codigo
     * @return Objeto de tipo ActaEstatusEntidad
     *
     */
    private ActaEstatusEntidad consultarEstadoActa(Long idEstatusActa) {
        return actaEstatusRepository.findOne(idEstatusActa);
    }

    /**
     * Metodo que permite guardar la Insercion
     *
     * @param insercion objeto de tipo Insercion
     * @return verdadero si se guardo con éxito en caso contrario false
     */
    @Transactional
    @Override
    public boolean guardarInsercion(Insercion insercion) throws Exception {
        LOG.info("=====INICIANDO ActaBFImpl.guardarInsercion==========");
        InsercionEntidad insercionEntidad;
        ParroquiaEntidad parroquiaEntidad;
        ActaEntidad actaEntidad;
        boolean resp = false;

        imprimirObjeto(insercion);

        try {
            LOG.info("============Buscando la parroquia======================");
            parroquiaEntidad = buscandoParroquiaInsercion(insercion
                    .getParroquia().getNombre());

            LOG.info("================Buscando el acta=======================");

            actaEntidad = buscandoActaInsercion(insercion.getNumeroActa());

            LOG.info("=====================Insercion==============================");
            insercionEntidad = insercionRepository
                    .findByActaNumeroActa(insercion.getNumeroActa());

            if (insercionEntidad == null) {
                LOG.info("=====Guardando InsercionEntidad=====");
                insercionEntidad = new InsercionEntidad();
                insercionEntidad.setActa(actaEntidad);
                insercionEntidad.setParroquia(parroquiaEntidad);
            }
            insercionEntidad.setFechaInsercion(insercion.getFechaInsercion());
            insercionEntidad.setPrimerNombreAutoridad(insercion.getPrimerNombreAutoridad());
            insercionEntidad.setPrimerApellidoAutoridad(insercion.getPrimerApellidoAutoridad());
            insercionEntidad.setIndicadorVivo(insercion.getIndicadorVivo());

            insercionEntidad.setSegundoNombreAutoridad(insercion
                    .getSegundoNombreAutoridad() == null ? "" : insercion.getSegundoNombreAutoridad());
            insercionEntidad.setSegundoApellidoAutoridad(insercion
                    .getSegundoApellidoAutoridad() == null ? "" : insercion.getSegundoApellidoAutoridad());

            InsercionEntidad resultado = insercionRepository.save(insercionEntidad);

            LOG.info("=====InsercionEntidad Guardado=====");
            if (resultado != null) {
                resp = true;
            }

        } catch (Exception e) {
            LOG.error("ERROR: La insercion no pudo ser guardado", e);
            throw new Exception(e);

        }
        return resp;
    }

    /**
     *
     * Metodo buscandoParroquiaInsercion que permite buscar la Parroquia
     *
     * @param nombre String que describe el nombre de la Parroquia
     * @return objeto de tipo ParroquiaEntidad
     *
     */
    private ParroquiaEntidad buscandoParroquiaInsercion(String nombre) throws Exception {
        //buscando la oficina 
        ParroquiaEntidad parroquiaEntidad;

        parroquiaEntidad = parroquiaRepository.buscarPorNombre(nombre);

        if (parroquiaEntidad == null) {
            LOG.error("ERROR: consultando la parroquia - parroquia no existe");
            throw new Exception("Parroquia no encontrada");
        }

        return parroquiaEntidad;
    }

    /**
     *
     * Metodo buscandoActaInsercion que permite buscar el Acta
     *
     * @param numActa String que describe el numero del Acta
     * @return objeto de tipo ActaEntidad
     *
     */
    private ActaEntidad buscandoActaInsercion(String numActa) throws Exception {
        //buscando la oficina 
        ActaEntidad actaEntidad;
        LOG.info("Num de Acta ++++++++++++++++++" + numActa);
        actaEntidad = actaRepository.findByNumeroActa(numActa);

        if (actaEntidad == null) {
            LOG.error("ERROR: consultando el Acta - Acta no existe");
            throw new Exception("Acta no encontrada");
        }

        return actaEntidad;
    }

    /**
     * Metodo responsable de consultar la insercion
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de la Insercion
     */
    @Override
    public Insercion consultarInsercion(String numSolicitud) throws Exception {
        LOG.info("=====INICIANDO ActaBFImpl.consultarInsercion==========");

        InsercionEntidad insercionEntidad;
        List<ActaEntidad> listActaEntidad;
        LOG.info("=====INICIANDO ActaBFImpl.consultarInsercion==========");
        listActaEntidad = actaRepository.findBySolicitudNumero(numSolicitud);

        if (!listActaEntidad.isEmpty()) {
            insercionEntidad = insercionRepository
                    .findByActaNumeroActa(listActaEntidad.get(0).getNumeroActa());
        } else {
            throw new Exception("Acta no encontrada por el numero de solicitud " + numSolicitud);
        }
        return insercionMapper.entityToBo(insercionEntidad);
    }

    /**
     * Metodo que permite guardar la Decision Judicial
     *
     * @param decisionJudicial objeto de tipo DecisionJudicial
     * @return verdadero si se guardo con éxito en caso contrario false
     */
    @Transactional
    @Override
    public boolean guardarDecisionJudicial(DecisionJudicial decisionJudicial) {

        LOG.info("=====INICIANDO ActaBFImpl.guardarDecisionJudicial==========");
        DecisionJudicialEntidad decisionJudicialEntidad;
        ProcedenciaEntidad procedenciaEntidad;
        ProcedenciaEntidad procedenciaGuardada;
        boolean resp = true;

        imprimirObjeto(decisionJudicial);

        try {
            procedenciaEntidad = buscarProcedenciaPorDecJudicial(decisionJudicial);

            if (procedenciaEntidad != null) {
                LOG.info("=================Buscando Decision Judicial=============");
                decisionJudicialEntidad
                        = decisionJudicialRepository.findByProcedenciaActaNumeroActaAndProcedenciaTipoProcedenciaId(
                                decisionJudicial.getNumeroActa(), procedenciaEntidad.getTipoProcedencia().getId());

                if (decisionJudicialEntidad == null) {
                    LOG.info("=====Guardando DecisionJudicialEntidad=====");
                    decisionJudicialEntidad = new DecisionJudicialEntidad();
                    decisionJudicialEntidad.setProcedencia(procedenciaEntidad);
                }

                decisionJudicialEntidad.setCedulaJuez(decisionJudicial.getCedulaJuez());
                decisionJudicialEntidad.setFechaSentencia(decisionJudicial.getFechaSentencia());
                decisionJudicialEntidad.setNombreTribunal(decisionJudicial.getNombreTribunal());
                decisionJudicialEntidad.setNumeroSentencia(decisionJudicial.getNumeroSentencia());
                decisionJudicialEntidad.setPrimerApellidoJuez(decisionJudicial.getPrimerApellidoJuez());
                decisionJudicialEntidad.setPrimerNombreJuez(decisionJudicial.getPrimerNombreJuez());
                decisionJudicialEntidad.setSegundoApellidoJuez(decisionJudicial.getSegundoApellidoJuez());
                decisionJudicialEntidad.setSegundoNombreJuez(decisionJudicial.getSegundoNombreJuez());
                procedenciaEntidad.setDecisionJudicial(decisionJudicialEntidad);

                procedenciaGuardada = procedenciaRepository.save(procedenciaEntidad);
                LOG.info("procedencia Guardada --> " + procedenciaGuardada.getId() + " - "
                        + procedenciaGuardada.getDecisionJudicial().getNumeroSentencia());

            } else {
                resp = false;
                LOG.error("ERROR: " + NO_ENCONTRO_ACTA);
            }

        } catch (Exception e) {
            resp = false;
            LOG.error("ERROR: La decision Judicial no pudo ser guardado: " + e.getMessage());
        }
        return resp;
    }

    /**
     * Metodo que permite buscar la procedencia de la Decision Judicial
     *
     * @param decisionJudicial objeto de tipo DecisionJudicial
     * @return objeto entidad de tipo Procedencia
     */
    private ProcedenciaEntidad buscarProcedenciaPorDecJudicial(DecisionJudicial decisionJudicial) {

        ProcedenciaEntidad procedenciaEntidad = null;
        ActaEntidad actaEntidad;
        TipoProcedenciaEntidad tipoProcedenciaEntidad;
        Long idProcedencia;

        actaEntidad = actaRepository.findByNumeroActa(decisionJudicial.getNumeroActa());

        //lee properties
        idProcedencia = Long.valueOf(buscarValorProperties(Constantes.PROCEDENCIA + Constantes.DECISION_JUDICIAL));

        if (actaEntidad != null) {
            tipoProcedenciaEntidad = tipoProcedenciaRepository.findById(idProcedencia);
            procedenciaEntidad = procedenciaRepository
                    .findByTipoProcedenciaIdAndActaNumeroActa(tipoProcedenciaEntidad.getId(),
                            decisionJudicial.getNumeroActa());

            if (procedenciaEntidad != null) {

                procedenciaEntidad.setTextoExtracto(decisionJudicial.getExtractoProcedencia());
                LOG.info("============ProcedenciaPorDecJudicial Encontrada======================");
            } else {
                procedenciaEntidad = new ProcedenciaEntidad();
                procedenciaEntidad.setActa(actaEntidad);
                procedenciaEntidad.setTipoProcedencia(tipoProcedenciaEntidad);
                procedenciaEntidad.setTextoExtracto(decisionJudicial.getExtractoProcedencia());

            }

        } else {
            LOG.info(NO_ENCONTRO_ACTA);
        }
        return procedenciaEntidad;
    }

    /**
     * Metodo responsable de consultar la decision judicial
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de la decision judicial
     */
    @Transactional
    @Override
    public DecisionJudicial consultarDecisionJudicial(String numSolicitud) throws Exception {
        LOG.info("=====INICIANDO ActaBFImpl.consultarDecisionJudicial==========");

        DecisionJudicialEntidad decisionJudicialEntidad;
        List<ActaEntidad> listActaEntidad;
        List<ProcedenciaEntidad> listProcedenciaEntidad = new ArrayList<>();
        DecisionJudicial resultado = null;
        String idProcedencia;

        try {
            LOG.info("=====INICIANDO ActaBFImpl.consultarDecisionJudicial==========");
            listActaEntidad = actaRepository.findBySolicitudNumero(numSolicitud);

            if (!listActaEntidad.isEmpty()) {
                LOG.info("============Buscando la procedencia=============");
                listProcedenciaEntidad = procedenciaRepository
                        .findByActaNumeroActa(listActaEntidad.get(0).getNumeroActa());
            }

            //lee properties
            idProcedencia = buscarValorProperties(Constantes.PROCEDENCIA + Constantes.DECISION_JUDICIAL);

            //anabell
            if (!listProcedenciaEntidad.isEmpty()
                    && String.valueOf(listProcedenciaEntidad.get(0).getTipoProcedencia().getId()).equals(idProcedencia)) {

                LOG.info("Id - " + listProcedenciaEntidad.get(0).getId());
                LOG.info("============Buscando la decision judicial=============");
                decisionJudicialEntidad = decisionJudicialRepository
                        .findByProcedenciaId(listProcedenciaEntidad.get(0).getId());

                if (decisionJudicialEntidad != null) {
                    LOG.info("============Encontro la Decision Judicial========");
                    resultado = decisionJudicialMapper.entityToBo(decisionJudicialEntidad);
                }
            }
        } catch (Exception e) {
            LOG.error("ERROR: Consultando la decision judicial: " + e.getMessage());
            throw new Exception(e);
        }

        LOG.info("return: " + resultado);
        return resultado;
    }

    /**
     * Metodo que permite guardar la Medida de Proteccion
     *
     * @param medidaProteccion objeto de tipo MedidaProteccion
     * @return verdadero si se guardo con �xito en caso contrario false
     */
    @Transactional
    @Override
    public boolean guardarMedidaProteccion(MedidaProteccion medidaProteccion) {
        LOG.info("=====INICIANDO ActaBFImpl.guardarMedidaProteccion==========");
        MedidaProteccionEntidad medidaProteccionEntidad;
        ProcedenciaEntidad procedenciaEntidad;
        ProcedenciaEntidad procedenciaGuardada;
        boolean resp = true;

        imprimirObjeto(medidaProteccion);

        try {
            procedenciaEntidad = buscarProcedenciaMedidaProteccion(medidaProteccion);

            if (procedenciaEntidad != null) {
                LOG.info("=================Buscando Medida de Proteccion=============");
                medidaProteccionEntidad
                        = medidaProteccionRepository.findByProcedenciaActaNumeroActaAndProcedenciaTipoProcedenciaId(
                                medidaProteccion.getNumeroActa(), procedenciaEntidad.getTipoProcedencia().getId());

                if (medidaProteccionEntidad == null) {
                    LOG.info("=====Guardando MedidaProteccionEntidad=====");
                    medidaProteccionEntidad = new MedidaProteccionEntidad();
                    medidaProteccionEntidad.setNumeroMedida(medidaProteccion.getNumeroMedida());
                    medidaProteccionEntidad.setProcedencia(procedenciaEntidad);
                    medidaProteccionEntidad.setFechaMedida(medidaProteccion.getFechaMedida());

                }

                medidaProteccionEntidad
                        .setNombreConsejoProteccion(medidaProteccion.getNombreConsejoProteccion());
                medidaProteccionEntidad
                        .setPrimerApellidoConsejero(medidaProteccion.getPrimerApellidoConsejero());
                medidaProteccionEntidad
                        .setPrimerNombreConsejero(medidaProteccion.getPrimerNombreConsejero());
                medidaProteccionEntidad
                        .setSegundoApellidoConsejero(medidaProteccion.getSegundoApellidoConsejero());
                medidaProteccionEntidad
                        .setSegundoNombreConsejero(medidaProteccion.getSegundoNombreConsejero());
                procedenciaEntidad.setMedidaProteccion(medidaProteccionEntidad);

                procedenciaGuardada = procedenciaRepository.save(procedenciaEntidad);
                LOG.info("procedencia Guardada --> " + procedenciaGuardada.getId() + " - "
                        + procedenciaGuardada.getDecisionJudicial().getNumeroSentencia());

            } else {
                resp = false;
                LOG.error("ERROR: " + NO_ENCONTRO_ACTA);
            }

        } catch (Exception e) {
            resp = false;
            LOG.error("ERROR: La medida de proteccion no pudo ser guardado: " + e.getMessage());
        }

        return resp;
    }

    /**
     * Metodo que permite buscar la procedencia de la Medida de Proteccion
     *
     * @param medidaProteccion objeto de tipo Medida de Proteccion
     * @return objeto entidad de tipo Procedencia
     */
    private ProcedenciaEntidad buscarProcedenciaMedidaProteccion(MedidaProteccion medidaProteccion) {

        ProcedenciaEntidad procedenciaEntidad = null;
        ActaEntidad actaEntidad;
        TipoProcedenciaEntidad tipoProcedenciaEntidad;
        Long idProcedencia;

        actaEntidad = actaRepository.findByNumeroActa(medidaProteccion.getNumeroActa());

        //lee properties
        idProcedencia = Long.valueOf(buscarValorProperties(Constantes.PROCEDENCIA + Constantes.MEDIDA_PROTECCION));

        if (actaEntidad != null) {
            tipoProcedenciaEntidad = tipoProcedenciaRepository.findById(idProcedencia);
            procedenciaEntidad = procedenciaRepository
                    .findByTipoProcedenciaIdAndActaNumeroActa(tipoProcedenciaEntidad.getId(),
                            medidaProteccion.getNumeroActa());

            if (procedenciaEntidad != null) {
                procedenciaEntidad.setTextoExtracto(medidaProteccion.getExtractoProcedencia());
                LOG.info("============ProcedenciaMediaProteccion Encontrada======================");
            } else {
                procedenciaEntidad = new ProcedenciaEntidad();
                procedenciaEntidad.setActa(actaEntidad);
                procedenciaEntidad.setTipoProcedencia(tipoProcedenciaEntidad);
                procedenciaEntidad.setTextoExtracto(medidaProteccion.getExtractoProcedencia());

            }

        } else {
            LOG.info(NO_ENCONTRO_ACTA);

        }
        return procedenciaEntidad;
    }

    /**
     * Metodo responsable de consultar la medida de Proteccion
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de la medida de proteccion
     */
    @Transactional
    @Override
    public MedidaProteccion consultarMedidaProteccion(String numSolicitud) throws Exception {
        LOG.info("=====INICIANDO ActaBFImpl.consultarMedidaProteccion==========");

        MedidaProteccionEntidad medidaProteccionEntidad;
        List<ActaEntidad> listActaEntidad;
        List<ProcedenciaEntidad> listProcedenciaEntidad;
        MedidaProteccion resultado = null;
        String idProcedencia;

        try {
            LOG.info("=====INICIANDO ActaBFImpl.consultarMedidaProteccion==========");
            listActaEntidad = actaRepository.findBySolicitudNumero(numSolicitud);

            LOG.info(BUSCAR_PROCEDENCIA);
            listProcedenciaEntidad = procedenciaRepository
                    .findByActaNumeroActa(listActaEntidad.get(0).getNumeroActa());

            //lee properties
            idProcedencia = buscarValorProperties(Constantes.PROCEDENCIA + Constantes.MEDIDA_PROTECCION);

            //anabell
            if (!listProcedenciaEntidad.isEmpty()
                    && String.valueOf(listProcedenciaEntidad.get(0).getTipoProcedencia().getId()).equals(idProcedencia)) {

                LOG.info("Id - " + listProcedenciaEntidad.get(0).getId());
                LOG.info("============Buscando medida de proteccion=============");
                medidaProteccionEntidad = medidaProteccionRepository
                        .findByProcedenciaId(listProcedenciaEntidad.get(0).getId());

                if (medidaProteccionEntidad != null) {
                    LOG.info("============Encontro la Medida de proteccion========");
                    resultado = medidaProteccionMapper.entityToBo(medidaProteccionEntidad);
                }
            }

        } catch (Exception e) {
            LOG.error("ERROR: Consultando la medida de proteccion: " + e.getMessage());
            throw new Exception(e);
        }
        return resultado;
    }

    /**
     * Metodo que permite guardar Extemporanea
     *
     * @param extemporanea objeto de tipo Extemporanea
     * @return verdadero si se guardo con �xito en caso contrario false
     */
    @Transactional
    @Override
    public boolean guardarExtemporanea(Extemporanea extemporanea) throws Exception {
        LOG.info("=====INICIANDO ActaBFImpl.guardarExtemporanea==========");
        ExtemporaneaEntidad extemporaneaEntidad;
        ProcedenciaEntidad procedenciaEntidad;
        boolean resp = false;

        imprimirObjeto(extemporanea);

        try {
            procedenciaEntidad = buscarProcedenciaExtemporanea(extemporanea);
            procedenciaRepository.save(procedenciaEntidad);
            LOG.info("============Guardando la procedencia===================");

            LOG.info("==============Buscando Medida Proteccion===============");
            extemporaneaEntidad = extemporaneaRepository
                    .findByNumeroProvidencia(extemporanea.getNumeroProvidencia());

            if (extemporaneaEntidad == null) {
                LOG.info("=====Guardando ExtemporaneaEntidad=====");
                extemporaneaEntidad = new ExtemporaneaEntidad();
                extemporaneaEntidad.setNumeroProvidencia(extemporanea.getNumeroProvidencia());
                extemporaneaEntidad.setProcedencia(procedenciaEntidad);
                extemporaneaEntidad.setFechaProvidencia(extemporanea.getFechaProvidencia());

            }

            extemporaneaEntidad.
                    setPrimerApellidoAutoridad(extemporanea.getPrimerApellidoAutoridad());
            extemporaneaEntidad
                    .setPrimerNombreAutoridad(extemporanea.getPrimerNombreAutoridad());
            extemporaneaEntidad
                    .setSegundoApellidoAutoridad(extemporanea.getSegundoApellidoAutoridad());
            extemporaneaEntidad
                    .setSegundoNombreAutoridad(extemporanea.getSegundoNombreAutoridad());
            procedenciaEntidad
                    .setExtemporanea(extemporaneaEntidad);
            ExtemporaneaEntidad resultado = extemporaneaRepository.save(extemporaneaEntidad);

            if (resultado != null) {
                resp = true;
                LOG.info("=====ExtemporaneaEntidad Guardado=====");
            } else {
                LOG.info("=====ExtemporaneaEntidad NO Guardado=====");
            }
        } catch (Exception e) {
            LOG.error("ERROR: Extemporanea no pudo ser guardado");
            throw new Exception(e);

        }
        return resp;
    }

    /**
     * Metodo que permite buscar la procedencia Extemporanea
     *
     * @param extemporanea objeto de tipo Extemporanea
     * @return objeto entidad de tipo Procedencia
     */
    private ProcedenciaEntidad buscarProcedenciaExtemporanea(Extemporanea extemporanea) {

        ProcedenciaEntidad procedenciaEntidad = null;
        ActaEntidad actaEntidad;
        TipoProcedenciaEntidad tipoProcedenciaEntidad;

        actaEntidad = actaRepository
                .findByNumeroActa(extemporanea.getNumeroActa());

        if (actaEntidad != null) {

            //anabell
            tipoProcedenciaEntidad = tipoProcedenciaRepository.findByNombre(Constantes.EXTEMPORANEA);

            procedenciaEntidad = procedenciaRepository
                    .findByTipoProcedenciaNombreAndActaNumeroActa(tipoProcedenciaEntidad
                            .getNombre(), extemporanea.getNumeroActa());

            if (procedenciaEntidad != null) {
                procedenciaEntidad.setTextoExtracto(extemporanea.getExtractoProcedencia());
                LOG.info("============ProcedenciaExtemporanea  Encontrada======================");
            } else {
                procedenciaEntidad = new ProcedenciaEntidad();
                procedenciaEntidad.setActa(actaEntidad);
                procedenciaEntidad.setTipoProcedencia(tipoProcedenciaEntidad);
                procedenciaEntidad.setTextoExtracto(extemporanea.getExtractoProcedencia());

            }

        } else {
            LOG.info(NO_ENCONTRO_ACTA);

        }
        return procedenciaEntidad;
    }

    /**
     * Metodo responsable de consultar extemporanea
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de extemporanea
     */
    @Transactional
    @Override
    public Extemporanea consultarExtemporanea(String numSolicitud) throws Exception {

        LOG.info("=====INICIANDO ActaBFImpl.consultarExtemporanea==========");

        ExtemporaneaEntidad extemporaneaEntidad;
        List<ActaEntidad> listActaEntidad;
        List<ProcedenciaEntidad> listProcedenciaEntidad;
        Extemporanea resultado = null;

        try {

            LOG.info("=====INICIANDO ActaBFImpl.consultarExtemporanea==========");
            listActaEntidad = actaRepository.findBySolicitudNumero(numSolicitud);

            LOG.info(BUSCAR_PROCEDENCIA);
            listProcedenciaEntidad = procedenciaRepository
                    .findByActaNumeroActa(listActaEntidad.get(0).getNumeroActa());

            //anabell
            if (listProcedenciaEntidad.get(0).getTipoProcedencia().getNombre().equals(Constantes.EXTEMPORANEA)) {
                LOG.info("============Buscando Extemporanea========");
                extemporaneaEntidad = extemporaneaRepository
                        .findByProcedenciaId(listProcedenciaEntidad.get(0).getId());

                if (extemporaneaEntidad != null) {
                    resultado = extemporaneaMapper.entityToBo(extemporaneaEntidad);
                    LOG.info("============Encontrooooo Extemporanea========");
                } else {
                    LOG.info("============No encontrooooo Extemporanea========");
                }

            } else {
                LOG.info("El tipo de procedencia no es Extemporanea");
                return resultado;

            }

        } catch (Exception e) {
            LOG.error("ERROR: Extemporanea no pudo ser consultada", e);
            throw new Exception(e);

        }
        return resultado;
    }

    /**
     * Metodo que permite guardar Nacimiento
     *
     * @param nacimiento objeto de tipo Nacimiento
     * @return verdadero si se guardo con exito en caso contrario false
     */
    @Transactional
    @Override
    public boolean guardarNacimiento(Nacimiento nacimiento) throws Exception {
        LOG.info("=====INICIANDO ActaBFImpl.guardarNacimiento==========");
        NacimientoEntidad nacimientoEntidad;
        ActaEntidad actaEntidad;
        boolean resp = true;

        imprimirObjeto(nacimiento);

        try {
            LOG.info("===================== Buscando Acta =======================");
            actaEntidad = buscarActa(nacimiento.getActa().getNumeroActa());
            if (actaEntidad == null) {
                LOG.info("=============no consiguio el acta ============= " + nacimiento.getActa().getNumeroActa());
                return false;
            }

            LOG.info("===================== Validar que no exista el certificado de Nacimiento =======================");
            boolean existeCertificado = validarCertificadoMedico(Constantes.NACIMIENTO, nacimiento.getNumeroCertificado());
            if (existeCertificado) {
                LOG.info("Error: Certificado de Nacimiento existe en Base de Datos");
                return false;
            }

            LOG.info("==============Buscando Nacimiento===============");
            nacimientoEntidad = nacimientoRepository.findByActaNumeroActa(actaEntidad.getNumeroActa());

            if (nacimientoEntidad == null) {
                LOG.info("=====Insertando NacimientoEntidad=====");
                nacimientoEntidad = new NacimientoEntidad();
                nacimientoEntidad.setActa(actaEntidad);
                nacimientoEntidad.setFechaCertificado(nacimiento.getFechaCertificado());
                nacimientoEntidad.setMpps(nacimiento.getNuMPPS());
                nacimientoEntidad.setCertificado(nacimiento.getNumeroCertificado());
                nacimientoEntidad.setPrimerNombreMedico(nacimiento.getPrimerNombreMedico());
                nacimientoEntidad.setPrimerApellidoMedico(nacimiento.getPrimerApellidoMedico());
            }
            nacimientoEntidad.setSexo(nacimiento.getSexo());
            nacimientoEntidad.setCaracterDeclarante(nacimiento.getCaracterActuaDeclarante());
            nacimientoEntidad.setCentroSalud(nacimiento.getCentroSalud());
            nacimientoEntidad.setExtrahospitalario(nacimiento.getDiExtrahospitalario());
            nacimientoEntidad.setNumDocMedico(nacimiento.getDocumentoIdentidadMedico());
            nacimientoEntidad.setSegundoNombreMedico(nacimiento.getSegundoNombreMedico());
            nacimientoEntidad.setSegundoApellidoMedico(nacimiento.getSegundoApellidoMedico());
            NacimientoEntidad resultado = nacimientoRepository.save(nacimientoEntidad);

            LOG.info("Nacimiento --> " + resultado.getCentroSalud());
        } catch (Exception e) {
            resp = false;
            LOG.error("ERROR: Nacimiento no pudo ser guardado", e);
            throw new Exception(e);
        }
        return resp;
    }

    /**
     *
     * Metodo privado buscarActa que permite buscar el estado
     *
     * @param numeroActa String numero del Acta
     * @return ActaEntidad entidad que contiene la informacion de Acta
     */
    private ActaEntidad buscarActa(String numeroActa) {
        ActaEntidad actaBuscar;
        actaBuscar = actaRepository.findByNumeroActa(numeroActa);

        return actaBuscar;
    }

    /**
     * Metodo responsable de consultar nacimiento
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de nacimiento
     */
    @Transactional
    @Override
    public Nacimiento consultarNacimiento(String numSolicitud) {
        LOG.info("=====INICIANDO ActaBFImpl.consultarNacimiento==========");

        NacimientoEntidad nacimientoEntidad;
        List<ActaEntidad> listActaEntidad = null;
        Nacimiento resultado = null;

        try {
            listActaEntidad = actaRepository.findBySolicitudNumero(numSolicitud);
        } catch (Exception e) {
            LOG.error("ERROR: Consultando el Acta de Nacimiento por Solicitud: " + e.getMessage());
        }

        try {
            nacimientoEntidad = nacimientoRepository.findByActaNumeroActa(listActaEntidad.get(0).getNumeroActa());
            if (nacimientoEntidad != null) {
                resultado = nacimientoMapper.entityToBo(nacimientoEntidad);
                LOG.info("Info: Encontrooooo Nacimiento.");
            }
        } catch (Exception e) {
            LOG.error("ERROR: Consultando datos de Nacimiento: " + e.getMessage());
        }

        return resultado;

    }

    /**
     * Metodo que permite guardar los datos del acta primigenia solicitados por
     * pantalla
     *
     * @param actaPrimigenia ActaPrimigenia Pojo con la informacion del acta
     * primigenia
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     */
    @Override
    @Transactional
    public ActaPrimigenia guardarActaPrimigenia(ActaPrimigenia actaPrimigenia) throws Exception {

        LOG.info("=====INICIANDO ActaBFImpl.guardarActaPrimigenia==========");

        ActaPrimigenia ap = new ActaPrimigenia();
        ActaPrimigeniaEntidad apConsulta;
        ActaPrimigeniaEntidad actaPrimigeniaEnt;
        ActaPrimigeniaEntidad apResul;
        SolicitudEntidad solicitudEntidad;
        OficinaEntidad oficinaEntidad;

        imprimirObjeto(actaPrimigenia);

        LOG.info("==================Buscando Solicitud=====================");
        solicitudEntidad = buscarSolicitud(actaPrimigenia.getNumeroSolic());

        LOG.info("==================Buscando Solicitud=====================");
        oficinaEntidad = buscarOficina(actaPrimigenia.getNombreOficina());

        LOG.info("==================Buscando Acta Primigenia=====================");
        apConsulta = buscarActaPrimigenia(actaPrimigenia.getNumeroSolic());

        try {

            /* Setea los valores que vienen de BDs */
            actaPrimigeniaEnt = apConsulta;
            /* Setea los valores que vienen de pantalla */
            actaPrimigeniaEnt = actaPrimigeniaMapper.boToEntity(actaPrimigenia);

            if (apConsulta != null) {
                /* Setea Id del Acta primigenia para hacer update sobre el registro existente en BDs */
                actaPrimigeniaEnt.setId(apConsulta.getId());
            }
            actaPrimigeniaEnt.setSolicitud(solicitudEntidad);
            actaPrimigeniaEnt.setOficina(oficinaEntidad);

            apResul = actaPrimigeniaRepository.save(actaPrimigeniaEnt);

            ap = actaPrimigeniaMapper.entityToBO(apResul);
        } catch (Exception e) {
            LOG.error("ERROR: guardando acta primigenia.");
            throw new Exception(e);
        }

        return ap;
    }

    /**
     * Metodo que permite consulta los datos de una acta primigenia dado el
     * numero de solicitud
     *
     * @param numSolicitud String Numero de la solicitud
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     */
    @Override
    public ActaPrimigenia consultaActaPrimigenia(String numSolicitud) throws Exception {

        LOG.info("=====INICIANDO ActaBFImpl.consultaActaPrimigenia==========");

        ActaPrimigenia ap = null;
        SolicitudEntidad solicitudEntidad;
        ActaPrimigeniaEntidad apConsulta;

        LOG.info("==================Buscando Solicitud=====================");
        solicitudEntidad = buscarSolicitud(numSolicitud);

        if (solicitudEntidad != null) {
            try {
                LOG.info("==================Buscando Acta Primigenia=====================");
                apConsulta = buscarActaPrimigenia(numSolicitud);

                if (apConsulta != null) {
                    ap = actaPrimigeniaMapper.entityToBO(apConsulta);
                }
            } catch (Exception e) {
                LOG.error("ERROR: consultando acta primigenia.", e);
                throw new Exception(e);
            }
        }

        return ap;
    }

    /**
     * Metodo que permite buscar la oficina dado su nombre
     *
     * @param nombreOficina String Nombre de la oficina
     * @return OficinaEntidad entidad que contiene la informacion de la oficina
     */
    private OficinaEntidad buscarOficina(String nombreOficina) throws Exception {

        OficinaEntidad oficinaEntidad;

        try {
            oficinaEntidad = oficinaRepository.findByNombre(nombreOficina);

            if (oficinaEntidad == null) {
                LOG.info("Error: Oficina no encontrada - " + nombreOficina);
                throw new Exception("Oficina no existe");
            }
        } catch (Exception e) {
            LOG.error("ERROR: Consultando la oficina: " + nombreOficina, e);
            throw new Exception(e);
        }

        return oficinaEntidad;
    }

    /**
     *
     * Metodo buscarSolicitud que permite buscar la solicitud dado el numero de
     * solciitud
     *
     * @param numeroSolicitud String nombre de la Solicitud
     * @return SolicitudEntidad entidad que contiene la informacion de Solicitud
     */
    private SolicitudEntidad buscarSolicitud(String numeroSolicitud) throws Exception {

        SolicitudEntidad solicitudBuscar;

        try {
            solicitudBuscar = solicitudRepository.findByNumero(numeroSolicitud);

            if (solicitudBuscar == null) {
                LOG.info("Error: Solicitud no encontrada - " + numeroSolicitud);
                throw new Exception("Solicitud no existe");
            }
        } catch (Exception e) {
            LOG.error("ERROR: Consultando la solicitud: " + numeroSolicitud, e);
            throw new Exception(e);
        }

        return solicitudBuscar;
    }

    /**
     * Metodo que permite buscar el acta primigenia dado el numero de solicitud
     *
     * @param numeroSolicitud String Numero de la solicitud
     * @return ActaPrimigeniaEntidad entidad que contiene la informacion del
     * acta primigenia
     */
    private ActaPrimigeniaEntidad buscarActaPrimigenia(String numeroSolicitud) {

        return actaPrimigeniaRepository.findBySolicitudNumero(numeroSolicitud);
    }

    /**
     * Metodo que imprime el objeto de entrada de cualquier método que lo
     * implemente
     *
     * @param obj Objeto que es entrada de cualquier metodo
     */
    private void imprimirObjeto(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(ActaBFImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOG.info("Imprimir Json de Entrada: " + obj.getClass().getName() + " - " + json);

    }

    /**
     * Metodo que permite buscar el valor de una propiedad en el archivo
     * properties del servicio funcionario
     *
     * @param clave String Propiedad a buscar en ela rchivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) {

        String valor = "";
        //lee properties
        System.out.println("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            LOG.error("Error leyendo properties: " + ex.getMessage());
        }
        System.out.println("buscarValorProperties --> valor --> " + valor);

        return valor;
    }
}
