package ve.gob.cne.sarc.participante.core.participante.business.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ve.gob.cne.sarc.cliente.libro.LibroServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;

import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.participante.CartaConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.participante.core.participante.business.ParticipanteBF;
import ve.gob.cne.sarc.participante.core.participante.mapper.ActaMapper;
import ve.gob.cne.sarc.participante.core.participante.mapper.CartaConsejoComunalMapper;
import ve.gob.cne.sarc.participante.core.participante.mapper.DeclaracionJuradaMapper;
import ve.gob.cne.sarc.participante.core.participante.mapper.IntegranteCartaCComunalMapper;
import ve.gob.cne.sarc.participante.core.participante.mapper.ParticipanteMapper;
import ve.gob.cne.sarc.participante.core.participante.mapper.SolicitudMapper;
import ve.gob.cne.sarc.participante.core.participante.mapper.TipoParticipanteMapper;
import ve.gob.cne.sarc.participante.core.util.Constantes;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.CartaConsejoComunalEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DeclaracionJuradaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DireccionParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.IntegranteCartaConsejoComunalEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ActaEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DiarioEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OcupacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramiteTipoParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DiarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuParticipanteActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.ActaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.CartaConsejoComunalRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DeclaracionJuradaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DireccionParticipanteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ParticipanteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ActaEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ComunidadIndigenaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DiarioEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.MunicipioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.NacionalidadRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OcupacionRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ParroquiaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoLibroRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoParticipanteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TramiteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DiarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.PaisRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudRepository;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * ParticipanteBFImpl.java
 *
 * @descripcion Implementacion del BusinessFacade con la logica de negocio del
 * manejo de Participantes.
 * @version 1.0 24/11/2015
 * @author Anabell De Faria
 */
@Component
public class ParticipanteBFImpl implements ParticipanteBF {

    private static final Logger LOG = LoggerFactory.getLogger(ParticipanteBFImpl.class);

    private static final String NUMEROSOLICITUDNOENCONTRADO = "Numero de solicitud no encontrado";

    @Autowired
    public AdministradorPropiedades properties;

    @Autowired
    private ActaRepository actaRepository;
    @Autowired
    private ActaEstatusRepository actaEstatusRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private ActaMapper actaMapper;
    @Autowired
    private TramiteRepository tramiteRepository;
    @Autowired
    private TipoParticipanteMapper tipoParticipanteMapper;
    @Autowired
    private DeclaracionJuradaMapper declaracionJuradaMapper;
    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private ParticipanteMapper participanteMapper;
    @Autowired
    private SolicitudEstatusRepository solicitudEstatusRepository;
    @Autowired
    private ParroquiaRepository parroquiaRepository;
    @Autowired
    private NacionalidadRepository nacionalidadRepository;
    @Autowired
    private OcupacionRepository ocupacionRepository;
    @Autowired
    private SolicitudMapper solicitudMapper;
    @Autowired
    private DireccionParticipanteRepository direccionParticipanteRepository;
    @Autowired
    private MunicipioRepository municipioRepository;
    @Autowired
    private TipoParticipanteRepository tipoParticipanteRepository;
    @Autowired
    private DeclaracionJuradaRepository declaracionJuradaRepository;
    @Autowired
    private CartaConsejoComunalMapper cartaConsejoComunalMapper;
    @Autowired
    private IntegranteCartaCComunalMapper integranteCComunalMapper;
    @Autowired
    private CartaConsejoComunalRepository cartaCComunalRepository;
    @Autowired
    private TipoLibroRepository tipoLibroRepository;
    @Autowired
    private DiarioRepository diarioRepository;
    @Autowired
    private DiarioEstatusRepository diarioEstatusRepository;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private ComunidadIndigenaRepository comunidadIndigenaRepository;

    /**
     * Metodo registrarParticipantes que permite registrar los Participantes a
     * una solicitud. Cambia el estado de la solicitud a abierta si los
     * participantes son insertados de manera satisfactoria.
     *
     * @param solicitud objeto del modelo ontologico
     * @return Solicitud objeto del modelo ontologico
     * @throws java.lang.Exception
     */
    @Override
    @Transactional
    public Solicitud registrarParticipantes(Solicitud solicitud, String token) throws Exception {
        LOG.info("=====INICIANDO ParticipanteNegocio.registrarParticipantes==========");

        List<ParticipanteEntidad> participantesEntidadBD, participantesEntidadV, partDel = new ArrayList<>();
        SolicitudEntidad solicitudEntidad;
        SolicitudEstatusEntidad solicitudEstatusEntidad;
        ActaEntidad actaEntidad;
        List<ActaEntidad> actasSolicitud;
        TipoLibroEntidad tipoLibroEntidad;
        Solicitud solicResponse;
        SolicitudEntidad resultado;
        String numero;
        String folio;
        String numeroActa;
        List<Acta> listaActas;
        List<ActaEntidad> listaActaEntidad = new ArrayList<>();
        boolean flag = false;
        int cont = 0;

        imprimirObjeto(solicitud);

        try {
            LOG.info("=====================Solicitud==============================");
            solicitudEntidad = solicitudRepository.findByNumero(solicitud.getNumeroSolicitud());

            //Verificar si el status de la solicitud es Pendiente Por Autenticar (PA)
            if (solicitudEntidad != null && solicitudEntidad.getEstatus().getCodigo().equals(Constantes.PENDIENTE_POR_AUTENTICAR_SOL)) {

                LOG.info("====Solicitud encontrada==========");

                //Buscar si existe un numero de Acta para la solicitud
                actasSolicitud = buscaNumeroActa(solicitud.getNumeroSolicitud());

                if (!actasSolicitud.isEmpty()) {

                    for (ActaEntidad actSol : actasSolicitud) {

                        numeroActa = actasSolicitud.get(0).getNumeroActa();
                        LOG.info("autenticando participantes - solicitud: " + solicitudEntidad.getNumero()
                                + " est: " + solicitudEntidad.getEstatus().getCodigo() + " acta: " + numeroActa);

                        participantesEntidadBD = actSol.getParticipantes();
                        LOG.info("tamaño lista participante de la solicitud buscada " + participantesEntidadBD.size());

                        participantesEntidadV = buscarListaParticipantes(solicitud.getActas().get(0).getParticipantes(), actSol);
                        LOG.info("tamaño lista participante de la peticion de la web " + participantesEntidadV.size());
                        
                        for (ParticipanteEntidad pev : participantesEntidadV) {
                            for (ParticipanteEntidad pebd : participantesEntidadBD) {
                                LOG.info("comparando participante (BD) " + pev.getPrimerNombre()
                                        + " con participante (V) " + pebd.getPrimerNombre());
                                if (pev.getPrimerNombre().equalsIgnoreCase(pebd.getPrimerNombre())) {
                                    partDel.add(pev);
                                }
                            }
                        }
                        
                        for (ParticipanteEntidad ped : partDel) {
                            for (int i = 0; i <= participantesEntidadV.size(); i++) {
                                ParticipanteEntidad pe = participantesEntidadV.get(i);
                                if(ped.getPrimerNombre().equalsIgnoreCase(pe.getPrimerNombre())){
                                    LOG.info("eliminando el participante repetido "+pe.getPrimerNombre());
                                    participantesEntidadV.remove(i);
                                    break;
                                }
                            }
                        }

                        actSol.getParticipantes().clear();
                        actaEntidad = actSol;
                        actaEntidad.setParticipantes(participantesEntidadV);
                        listaActaEntidad.add(actaEntidad);
                        solicitudEntidad.setActa(listaActaEntidad);
                    }

                } else {

                    //Lee lista de Actas
                    listaActas = solicitud.getActas();

                    LOG.info("==========Determinar Libro donde se insertará el Acta==========");

                    LOG.info("=====================Acta==============================");
                    for (Acta acta : listaActas) {

                        actaEntidad = new ActaEntidad();
                        actaEntidad.setOficinaFuncionario(solicitudEntidad.getOficinaFuncionario());

                        long idOficina = solicitudEntidad.getOficinaFuncionario().getOficina().getId();

                        tipoLibroEntidad = buscarTipoLibro(solicitud.getTramite().getCodigo(), token);

                        String idTipoLibro = "0";
                        if (tipoLibroEntidad != null) {
                            idTipoLibro = String.valueOf(tipoLibroEntidad.getId());
                        }

                        String[] valoresActa = generarNroActa(String.valueOf(idOficina), String.valueOf(idTipoLibro));

                        numero = valoresActa[0];
                        folio = valoresActa[1];

                        //lectura de properties 
                        String estatusActa = buscarValorProperties(Constantes.ESTATUS_ACTA_PUBLICA);

                        actaEntidad.setNumeroActa(numero);
                        actaEntidad.setNumeroFolio(Integer.parseInt(folio));
                        actaEntidad.setFechaCreacion(new Date());
                        actaEntidad.setEstatus(consultarEstatusActa(estatusActa));

                        participantesEntidadV = buscarListaParticipantes(acta.getParticipantes(), actaEntidad);

                        actaEntidad.setParticipantes(participantesEntidadV);
                        actaEntidad.setSolicitud(solicitudEntidad);
                        listaActaEntidad.add(actaEntidad);
                        solicitudEntidad.setActa(listaActaEntidad);
                    }
                    List<ActaEntidad> actas = (List<ActaEntidad>) actaRepository.save(listaActaEntidad);
                    solicitud.setActas(actaMapper.entitiesToBOs(actas));

                }
            } else {
                LOG.error("ERROR: consultando la solicitud asociada a los Participantes - solicitud no existe");
                throw new Exception("Solicitud no encontrada");
            }

            solicitudEstatusEntidad = solicitudEstatusRepository.findByCodigo("AB");
            solicitudEntidad.setEstatus(solicitudEstatusEntidad);
            resultado = solicitudRepository.save(solicitudEntidad);
            solicResponse = solicitudMapper.entityToBO(resultado);
            LOG.info("Va actualizar libro diario --> ");

            //Actualizar libro diario
            actualizarLibroDiario(solicResponse.getNumeroSolicitud(), "AB");

        } catch (Exception e) {
            LOG.error("error al registrar los participante", e);
            throw new Exception("Error registrando Participantes: " + e);
        }

        LOG.info("Return --> " + solicResponse.getNumeroSolicitud());

        return solicResponse;
    }

    /**
     * Metodo registrarParticPorSolicitud que permite registrar los
     * Participantes Por Solicitud.
     *
     * @param numSolicitud String que describe el numero de solicitud
     * @return objeto del modelo ontologico de tipo Participante
     * @throws java.lang.Exception
     */
    @Override
    @Transactional
    public Participante registrarParticPorSolicitud(String numSolicitud, Participante participante) throws Exception {
        LOG.info("=====INICIANDO Participante.registrarParticPorSolicitud==========");
        ActaEntidad actaEntidad;
        SolicitudEntidad solicitudEntidad;
        List<ParticipanteEntidad> listaParticipantesEnt;
        ParticipanteEntidad partEn;
        ParticipanteEntidad participanteAgregado;
        Participante resultado;

        LOG.info("=====================Solicitud==============================");
        solicitudEntidad = solicitudRepository.findByNumero(numSolicitud);

        if (solicitudEntidad == null) {
            LOG.error("ERROR: consultando la solicitud asociada a los Participantes - solicitud no existe");

            throw new Exception("Solicitud no encontrada");
        }

        LOG.info("=====================Acta==============================");
        actaEntidad = actaRepository.findByNumeroActa(solicitudEntidad.getActa().get(0).getNumeroActa());

        //Si se hace desde Participante
        listaParticipantesEnt = participanteRepository.buscarPorNumeroActa(actaEntidad.getNumeroActa());
        LOG.info("OJOOOO " + listaParticipantesEnt.get(0).getPrimerNombre());
        String[] partRoles = {participante.getRol()};
        List<TipoParticipanteEntidad> listaTipoParticipanteEntidad = buscarListaCodigo(partRoles);
        LOG.info("TIPOOOOO PARTICIPANTE " + listaTipoParticipanteEntidad.get(0).getCodigo());
        List<ParticipanteEntidad> participanteEntidad;

        participanteEntidad = participanteRepository.findByActaSolicitudNumeroAndTipoParticipanteIn(numSolicitud,
                listaTipoParticipanteEntidad);

        //asociando al acta
        if (participanteEntidad.isEmpty()) {
            partEn = participanteMapper.boToEntity(participante);
            listaParticipantesEnt.add(partEn);
            actaEntidad.setParticipantes(listaParticipantesEnt);
            partEn.setActa(actaEntidad);
            participanteAgregado = participanteRepository.save(partEn);
            resultado = participanteMapper.entityToBO(participanteAgregado);
        } else {
            LOG.info("INFO: El tipo de Participante ya existe");
            throw new Exception("Encontrado Tipo de Participante: "
                    + "Con Numero de Solicitud: "
                    + numSolicitud);
        }

        return resultado;

    }

    /**
     * Metodo que permite consulta el Estatus de un Acta segun el codigo de
     * estatus.
     *
     * @param codigoEstatus String con el id del estatus del acta.
     * @return ActaEstatusEntidad objeto con la informacion de la entidad
     * ActaEstatus.
     */
    private ActaEstatusEntidad consultarEstatusActa(String codigoEstatus) {
        return actaEstatusRepository.findOne(Long.valueOf(codigoEstatus));
    }

    /**
     * Este metodo busca los tipos de participante por un codigo de tramite; y
     * tipo de declarante.
     *
     * @param codigoTramite String que contiene el codigo del tramite
     * @param tipoDeclarante String que contiene el tipo de dedlarante
     * declarantes
     * @return Lista de {@link TipoParticipante}
     * @throws java.lang.Exception
     */
    @Transactional
    @Override
    public List<TipoParticipante> buscarTipoParticipantePorTramite(String codigoTramite, String tipoDeclarante)
            throws Exception {
        LOG.info("buscando los tramites por el codigo: " + codigoTramite + "- tipoDeclarante: " + tipoDeclarante);

        List<TipoParticipante> listaTipoParticipante = new ArrayList<>();
        List<TipoParticipante> listaOrdenadaCriterio;
        TramiteEntidad tramiteEntidad;

        String idDeclarante = buscarValorProperties(Constantes.TIPO_SOLICITANTE_DECLARANTE);
        String idEntePublico = buscarValorProperties(Constantes.TIPO_SOLICITANTE_ENTE_PUBLICO);
        LOG.info("idDeclarante: " + idDeclarante + " - idEntePublico: " + idEntePublico);

        if (!tipoDeclarante.equals(idDeclarante)
                && !tipoDeclarante.equals(idEntePublico)) {
            LOG.error("ERROR: buscarTipoParticipantePorTramite - valor invalido tipo declarante: " + tipoDeclarante);
            throw new Exception("Tipo Declarante no es valido");
        }

        tramiteEntidad = tramiteRepository.buscarPorCodigo(codigoTramite);
        if (tramiteEntidad == null) {
            LOG.error("ERROR: buscarTipoParticipantePorTramite - codigo de tramite no existe: " + codigoTramite);
            throw new Exception("Codigo de Tramite no encontrado");
        }

        if (tipoDeclarante.equals(idDeclarante)) {
            listaTipoParticipante = consultarTramitePorTipoParticipante(
                    tramiteEntidad.getTramitesTiposParticipantes());
        } else if (tipoDeclarante.equals(idEntePublico)) {
            if (codigoTramite.equals(Constantes.REGISTRO_DE_NACIMIENTO)) {
                listaTipoParticipante = consultarTramitePorTipoParticipante(
                        tramiteEntidad.getTramitesTiposParticipantes());
            } else {
                listaTipoParticipante = consultarParticEntePublico(codigoTramite, tipoDeclarante);
            }
        }

        LOG.info("tamaño lista tipo participante " + listaTipoParticipante.size());

        //Ordenar listaTipoParticipante 
        if (codigoTramite.equals(Constantes.REGISTRO_DE_NACIMIENTO)) {
            //Ordenar segun criterio particular
            LOG.info("Orden Criterio para Tramite RNACI-Declarante");
            listaOrdenadaCriterio = participantesNacimientoD(listaTipoParticipante);
        } else {
            //Ordenar alfabeticamente
            LOG.info("Ordenar Alfabeticamente");
            ordenarLista(listaTipoParticipante);
            listaOrdenadaCriterio = listaTipoParticipante;
        }

        return listaOrdenadaCriterio;
    }

    /**
     * Metodo que para ordenar lista de tipo participante para el Tramite:
     * Nacimiento y Tipo declarante: D: Ciudadano
     *
     * @param listaTipoParticipante Lista de objetos de tipo TipoParticipante
     * @return Lista de objetos de tipo TipoParticipante ordenada segun orden
     * establecido
     */
    private List<TipoParticipante> participantesNacimientoD(List<TipoParticipante> listaTipoParticipante) {

        LOG.info("participantesNacimientoD -->" + listaTipoParticipante.size());
        List<TipoParticipante> listaOrdenadaCriterio = new ArrayList<>();
        TipoParticipante tipoParticipante;

        String[] arrayOrden = {"TIO", "ABU", "BISA", "TTBO", "HRM", "SOB", "PRI", "MED", "PART", "RMT", "CPNA"};

        for (String codigo : arrayOrden) {
            tipoParticipante = buscarParticipanteOrden(codigo, listaTipoParticipante);
            LOG.info("tipoParticipante -->" + tipoParticipante.getCodigo() + " - " + tipoParticipante.getNombre());

            listaOrdenadaCriterio.add(tipoParticipante);
        }

        return listaOrdenadaCriterio;
    }

    /**
     * Metodo que busca un codigo de TipoParticipante en una lista de objetos de
     * tipo TipoParticipante
     *
     * @param codigo String codigo de tipo participante a buscar
     * @param participantes Lista de objetos de tipo TipoParticipante
     * @return Objeto TipoParticipante encontrado
     */
    private TipoParticipante buscarParticipanteOrden(String codigo, List<TipoParticipante> participantes) {

        LOG.info("buscarParticipanteOrden --> " + codigo + "-" + participantes.size());
        TipoParticipante tipoParticipante;
        int resultado = -1;
        int pos = -1;

        for (TipoParticipante participante : participantes) {
            resultado = participante.getCodigo().indexOf(codigo);
            pos++;
            if (resultado > -1) {
                break;
            }
        }

        LOG.info("resultado --> " + resultado + " pos-" + pos);
        if (resultado > -1) {
            tipoParticipante = participantes.get(pos);
        } else {
            tipoParticipante = null;
        }

        return tipoParticipante;
    }

    /**
     * Metodo que ordena la lista de tipo participante por el atributo nombre
     *
     * @param listaTipoParticipante Objeto de tipo Lista con la informacion de
     * tipo participante
     */
    private void ordenarLista(List<TipoParticipante> listaTipoParticipante) {

        Collections.sort(listaTipoParticipante, new Comparator<TipoParticipante>() {

            @Override
            public int compare(TipoParticipante o1, TipoParticipante o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }

        });
    }

    /**
     * Metodo que consulta los Tipo de Participantes por Tramite para
     * Declarantes (Ciudadanos).
     *
     * @param listaTipoParticipante Lista de Objetos
     * TramiteTipoParticipanteEntidad
     * @return Lista de objetos de tipo TipoParticipante
     */
    private List<TipoParticipante> consultarTramitePorTipoParticipante(
            List<TramiteTipoParticipanteEntidad> listaTipoParticipante) {
        TipoParticipanteEntidad tipoParticipanteEntidad;
        List<TipoParticipante> listaParticipantes = new ArrayList<>();

        for (TramiteTipoParticipanteEntidad ttp : listaTipoParticipante) {
            tipoParticipanteEntidad = ttp.getTipoParticipante();
            LOG.info("tipo participante " + tipoParticipanteEntidad.getCodigo() + " - "
                    + tipoParticipanteEntidad.getNombre());
            listaParticipantes.add(mapearTipoParticipante(tipoParticipanteEntidad));
        }
        validarTipoParticipantes(listaParticipantes);

        return listaParticipantes;
    }

    /**
     * Metodo que consulta los Tipo de Participantes por Tramite para Ente
     * Publico.
     *
     * @param codigoTramite String codigo del tramite
     * @param tipoDeclarante String tipo de declarante
     * @return Lista de objetos de tipo TipoParticipante
     */
    private List<TipoParticipante> consultarParticEntePublico(String codigoTramite, String tipoDeclarante) {

        List participantes = new ArrayList();
        List<TipoParticipante> listPartic = new ArrayList<>();
        TipoParticipanteEntidad tipoParticipanteEntidad;

        if (codigoTramite.equalsIgnoreCase(Constantes.REGISTRO_DE_DEFUNCION)) {
            //Registro de Defuncion
            participantes.add("COUND");
            participantes.add("HIJD");
            participantes.add("MAD");
            participantes.add("PAD");
        }

        for (Object participante : participantes) {
            tipoParticipanteEntidad = tipoParticipanteRepository.findByCodigo(participante.toString());
            listPartic.add(mapearTipoParticipante(tipoParticipanteEntidad));
        }

        if (listPartic.isEmpty()) {
            LOG.info("***** Lista TipoParticipantes vacia tramite: " + codigoTramite
                    + " tipoDeclarante: " + tipoDeclarante);
        } else {
            validarTipoParticipantes(listPartic);
        }
        return listPartic;
    }

    /**
     * Metodo para validar el nombre del Tipo Participante
     *
     * @param listaTipPartic Lista de objeto TipoParticipante
     * @return Lista de objetos de tipo TipoParticipante
     */
    private List<TipoParticipante> validarTipoParticipantes(List<TipoParticipante> listaTipPartic) {

        int resultado;
        for (TipoParticipante ttp : listaTipPartic) {
            if (!"DECLARANTE".equalsIgnoreCase(ttp.getNombre())) {
                resultado = ttp.getNombre().indexOf("DECLARANTE");
                ttp.setNombre((resultado == -1) ? ttp.getNombre() : ttp.getNombre().substring(0, resultado - 1));
            }
        }

        return listaTipPartic;
    }

    /**
     * Este metodo mapea un objeto TipoParticipanteEntidad a TipoParticipante
     *
     * @param tipoParticEntidad Objeto TipoParticipanteEntidad
     * @return TipoParticipante Objeto TipoParticipante del modelo ontologico
     */
    private TipoParticipante mapearTipoParticipante(TipoParticipanteEntidad tipoParticEntidad) {
        return tipoParticipanteMapper.entityToBO(tipoParticEntidad);
    }

    /**
     * Metodo que permite construir una lista de objetos de la entidad
     * ParticipanteEntidad
     *
     * @param listaParticipante
     * @param actaEntidad
     * @return Lista de objetos de tipo ParticipanteEntidad
     */
    private List<ParticipanteEntidad> buscarListaParticipantes(List<Participante> listaParticipante,
            ActaEntidad actaEntidad) {

        LOG.info("=====================Participante==============================");
        List<ParticipanteEntidad> listaParticipanteEnt = new ArrayList<>();

        for (Participante participante : listaParticipante) {
            ParticipanteEntidad participanteEntidad;

            participanteEntidad = participanteMapper.boToEntity(participante);
            participanteEntidad.setActa(actaEntidad);
            listaParticipanteEnt.add(participanteEntidad);
        }

        LOG.info("=====================Participante=====================Salida");
        return listaParticipanteEnt;
    }

    /**
     * Metodo que busca los participantes dado un numero de acta
     *
     * @param numeroActa String que contiene el numero de acta
     * @return Lista de objetos de tipo Participante
     */
    @Override
    public List<Participante> consultarParticPorActa(String numeroActa) {
        LOG.info("=====INICIANDO ParticipanteBF.consultarParticPorActa==========");
        LOG.info("Acta: " + numeroActa);

        List<Participante> participantes;
        List<ParticipanteEntidad> participantesEntidad;

        participantesEntidad = participanteRepository.buscarPorNumeroActa(numeroActa);
        LOG.info("Cant. de Participantes encontrados: " + participantesEntidad.size());
        participantes = participanteMapper.entitiesToBOs(participantesEntidad);

        return participantes;
    }

    /**
     * Metodo que busca los participantes dado un numero de solicitud y un
     * indicador (D:declarante, T:Todos)
     *
     * @param numeroSolicitud String que contiene el numero de solicitud
     * @param esDeclarante String que contiene el tipo de participantes a buscar
     * @return Lista de objetos de tipo Participante
     * @throws java.lang.Exception
     */
    @Override
    public List<Participante> consultarParticPorSolicitud(String numeroSolicitud, String esDeclarante) throws Exception {
        LOG.info("=====INICIANDO ParticipanteBF.consultarParticPorSolicitud==========");
        LOG.info("Solicitud: " + numeroSolicitud);
        LOG.info("esDeclarante: " + esDeclarante);
        List<Participante> participantes = new ArrayList<>();
        List<ParticipanteEntidad> participantesEntidad;
        ParticipanteEntidad partEntidadSolo;
        Participante participante;

        if (numeroSolicitud != null && ("T").equalsIgnoreCase(esDeclarante.toUpperCase())) {

            LOG.info("=====INICIANDO ParticipanteBF.buscarPorNumeroSolicitud(" + numeroSolicitud + ")==========");
            participantesEntidad = participanteRepository.buscarPorNumeroSolicitud(numeroSolicitud);
            LOG.info("Cant. encontrada: " + participantesEntidad.size());
            participantes = participanteMapper.entitiesToBOs(participantesEntidad);

        } else if (numeroSolicitud != null && ("D").equalsIgnoreCase(esDeclarante.toUpperCase())) {

            LOG.info("=====INICIANDO ParticipanteBF.findByTipoParticipanteCodigoEndingWithAndActaSolicitudNumero("
                    + esDeclarante, numeroSolicitud + ")==========");

            participantesEntidad = participanteRepository.findByTipoParticipanteCodigoEndingWithAndActaSolicitudNumero(
                    esDeclarante, numeroSolicitud);
            if (participantesEntidad.isEmpty()) {
                //Si la lista esta vacia donde la lista de participantes
                LOG.info("=====participantes====");
                participantesEntidad = participanteRepository.buscarPorNumeroSolicitud(numeroSolicitud);
                partEntidadSolo = participantesEntidad.get(0);
                participante = participanteMapper.entityToBO(partEntidadSolo);
                participante.setNombreRol(participante.getNombreRol() + " DECLARANTE");
                participantes.add(participante);
            } else {
                LOG.info("======Lista con participantes=====");
                participantes = participanteMapper.entitiesToBOs(participantesEntidad);

            }
        } else if (numeroSolicitud != null && ("E").equalsIgnoreCase(esDeclarante.toUpperCase())) {

            LOG.info("Error con consultar el Participante");
            throw new Exception("Error con EsDeclarante: " + esDeclarante);

        }

        return participantes;
    }

    /**
     * Metodo que busca un participante dado un numero de solicitud y un codigo
     * de tipo participante
     *
     * @param numeroSolicitud String numero de solicitud
     * @param codigoTipo String[] Un Array de string de codigo tipo de
     * participante
     * @return Lista de {@link Participante}
     * @throws java.lang.Exception
     */
    @Override
    public List<Participante> consultarParticPorTipo(String numeroSolicitud, String[] codigoTipo) throws Exception {
        LOG.info("=====INICIANDO ParticipanteBF.consultarParticPorTipo==========");
        LOG.info("Solicitud: " + numeroSolicitud + " TipoParticipante: " + codigoTipo.length);

        List<TipoParticipanteEntidad> listaTipoParticipanteEntidad = buscarListaCodigo(codigoTipo);

        List<Participante> participantes;
        List<ParticipanteEntidad> participanteEntidad;

        participanteEntidad = participanteRepository.findByActaSolicitudNumeroAndTipoParticipanteIn(numeroSolicitud,
                listaTipoParticipanteEntidad);
        if (participanteEntidad == null) {
            LOG.info("INFO: consultando el participante - participante no encontrado");
            throw new Exception("No encontro Participante: Con Numero de Solicitud: "
                    + numeroSolicitud);
        }
        participantes = participanteMapper.entitiesToBOs(participanteEntidad);
        return participantes;
    }

    /**
     * Metodo que permite obtener la entidad de TipoParticipanteEntidad String[]
     * de codigo.
     *
     * @param codigoTipo String[] que contiene varios tipos de participante.
     * @return Lista de objetos del tipo TipoParticipanteEntidad
     */
    private List<TipoParticipanteEntidad> buscarListaCodigo(String[] codigoTipo) {
        LOG.info("=====INICIANDO SolicitudCodigoTipoParticipante.buscarListaCodigo==========");
        TipoParticipanteEntidad tipoParticipanteEntidad;
        List<TipoParticipanteEntidad> listaTipoParticipanteEntidad = new ArrayList<>();

        for (String est : codigoTipo) {
            LOG.info("=====EL VALOR DE EST==========" + est);
            tipoParticipanteEntidad = buscarCodigo(est.replace("[", "").replace("]", ""));
            if (tipoParticipanteEntidad != null) {
                listaTipoParticipanteEntidad.add(tipoParticipanteEntidad);
            }
        }
        return listaTipoParticipanteEntidad;
    }

    /**
     * Metodo que permite obtener la entidad de TipoParticipanteEntidad dado un
     * codigo
     *
     * @param codigoEstatus String con el codigo del estatus
     * @return Objeto de tipo SolicitudEstatusEntidad
     */
    private TipoParticipanteEntidad buscarCodigo(String codigotipo) {
        LOG.info("=====INICIANDO SolicitudCodigoTipoParticipante.buscarCodigo==========");
        LOG.info("=====el valor del codigo=======" + codigotipo);
        TipoParticipanteEntidad tipoParticipanteEntidad;

        tipoParticipanteEntidad = tipoParticipanteRepository.findByCodigo(codigotipo);

        return tipoParticipanteEntidad;
    }

    /**
     * Metodo que actualiza un participante dado un participante
     *
     * @param participante objeto del modelo ontologico
     * @return Objeto de tipo Participante
     * @throws java.lang.Exception
     */
    @Override
    @Transactional
    public Participante actualizarParticipante(Participante participante, String numeroSolic) throws Exception {
        LOG.info("=====INICIANDO actualizarParticipante==========");

        List<DireccionParticipanteEntidad> listDirecciones = new ArrayList<>();
        DireccionParticipanteEntidad direccionParticipanteEntidad;
        List<Direccion> direcciones;
        SolicitudEntidad solicitudEntidad;
        List<TipoParticipanteEntidad> listaTipoParticipanteEntidad = new ArrayList<>();
        TipoParticipanteEntidad tipoParticipanteEntidad;
        Participante partiResult;

        imprimirObjeto(participante);

        solicitudEntidad = solicitudRepository.findByNumero(numeroSolic);
        if (solicitudEntidad == null) {
            LOG.error("Error validando numero de solicitud - solicitud no existe");
            throw new Exception(NUMEROSOLICITUDNOENCONTRADO);
        }

        //Buscar Participante para la solicitud
        tipoParticipanteEntidad = tipoParticipanteRepository.findByCodigo(participante.getRol());
        if (tipoParticipanteEntidad == null) {
            LOG.info("INFO: consultando el tipo participante - tipo participante no encontrado");
            throw new Exception("No encontro Tipo Participante: " + participante.getRol());
        }
        listaTipoParticipanteEntidad.add(tipoParticipanteEntidad);
        List<ParticipanteEntidad> listaParticipantes;
        ParticipanteEntidad participanteEntidad;

        listaParticipantes = participanteRepository.findByActaSolicitudNumeroAndTipoParticipanteIn(numeroSolic,
                listaTipoParticipanteEntidad);

        if (listaParticipantes.isEmpty()) {
            LOG.info("INFO: consultando el participante - participante no encontrado");
            throw new Exception("No encontro Participante: " + " Con Numero de Solicitud: "
                    + numeroSolic + " tipo " + participante.getRol());
        }

        participanteEntidad = listaParticipantes.get(0);

        LOG.info("Datos encontrados para la actualizacion");

        NacionalidadEntidad nacionalidadEntidad = consultarNacionalidad(participante.getNacionalidad());

        participanteEntidad.setSexo(buscarIdentificadorSexo(participante.getSexo()));

        participanteEntidad.setEstadoCivil(buscarIdentificadorEstadoCivil(participante.getEstadoCivil()));
        participanteEntidad.setNacionalidad(nacionalidadEntidad);
        participanteEntidad.setSegundoApellido(participante.getSegundoApellido());
        OcupacionEntidad ocupacionEntidad = consultarOcupacion(participante.getOcupacion());
        participanteEntidad.setOcupacion(ocupacionEntidad);

        ComunidadIndigenaEntidad comunidadIndigenaEntidad = consultarComunidadIndigena(participante.getComunidadIndigena());
        participanteEntidad.setComunidadIndigena(comunidadIndigenaEntidad);

        direcciones = participante.getDireccion();
        for (Direccion direc : direcciones) {
            LOG.info("direc --> " + direc.getTipoDireccion().getCodigo());
            if ("NAC".equalsIgnoreCase(direc.getTipoDireccion().getCodigo())) {
                participanteEntidad.setLugarNacimiento(direc.getUbicacion());
                if (direc.getUbicacion() != null) {
                    LOG.info("direc NAC --> " + direc.getUbicacion());
                }
            }

            if (("RES".equalsIgnoreCase(direc.getTipoDireccion().getCodigo()))
                    || ("NAC".equalsIgnoreCase(direc.getTipoDireccion().getCodigo())
                    && "ADO".equalsIgnoreCase(participante.getRol())
                    && direc.getParroquia() != null
                    && direc.getMunicipio() != null)) {

                LOG.info("Tipo direccion -->" + direc.getTipoDireccion().getCodigo() + " rol-" + participante.getRol());
                /* Construir direccionEntidad */
                direccionParticipanteEntidad = construirDireccionEntidad(participanteEntidad.getId(), direc);
                direccionParticipanteEntidad.setParticipante(participanteEntidad);
                listDirecciones.add(direccionParticipanteEntidad);
            }
        }

        participanteEntidad.setDireccionesParticipantes(listDirecciones);

        ParticipanteEntidad resultado = participanteRepository.save(participanteEntidad);
        partiResult = participanteMapper.entityToBO(resultado);

        return partiResult;
    }

    /**
     * Metodo que consulta una Nacionalidad dado un nombre
     *
     * @param participante objeto del modelo ontologico
     * @return Objeto de tipo Participante
     */
    private NacionalidadEntidad consultarNacionalidad(String nombre) {
        return nacionalidadRepository.buscarPorNombre(nombre);

    }

    /**
     * Metodo que busca una Ocupacion dado un nombre
     *
     * @param nombreOcupacion String nombre de la Ocupacion
     * @return OcupacionEntidad objeto del modelo de Datos
     */
    private OcupacionEntidad consultarOcupacion(String nombreOcupacion) {
        return ocupacionRepository.findByNombreIgnoreCase(nombreOcupacion);
    }

    /**
     * Metodo que busca una Comunidad indigena dado un nombre
     *
     * @param comunidadIndigena String nombre de la Comunidad Indigena
     * @return ComunidadIndigenaEntidad objeto del modelo de Datos
     */
    private ComunidadIndigenaEntidad consultarComunidadIndigena(String comunidadIndigena) {
        return comunidadIndigenaRepository.findByNombre(comunidadIndigena);
    }

    /**
     * Metodo que busca la parroquia dado el codigo de la parroquia o el nombre
     * de la parroquia y municipio
     *
     * @param parroquia Parroquia pojo que contiene la informacion de la
     * parroquia
     * @param municipio Municipio pojo que contiene la informacion del municipio
     * @return ParroquiaEntidad objeto del modelo de Datos
     */
    private ParroquiaEntidad buscarParroquia(Parroquia parroquia, Municipio municipio) {

        ParroquiaEntidad parroquiaEnt = null;

        if (parroquia.getNombre() != null && municipio.getNombre() != null) {
            LOG.info("Parroquia nombre: " + parroquia.getNombre() + "-" + municipio.getNombre());
            parroquiaEnt = consultarParroquia(parroquia.getNombre(), municipio.getNombre());
        } else if (parroquia.getCodigo() != null) {
            LOG.info("Parroquia codigo: " + parroquia.getCodigo());
            parroquiaEnt = consultarParroquia(parroquia.getCodigo());
        }

        return parroquiaEnt;
    }

    /**
     * Metodo que busca los datos de Pais dado el nombre del mismo
     *
     * @param nombre String Nombre del pais
     * @return PaisEntidad objeto del modelo de Datos
     */
    private PaisEntidad buscarPaisPorNombre(String nombre) {
        return paisRepository.findByNombre(nombre);
    }

    /**
     * Metodo que busca una Parroquia dado un codigo de parroquia
     *
     * @param codigoParroquia String codigo de la Parroquia
     * @return ParroquiaEntidad objeto del modelo de Datos
     */
    private ParroquiaEntidad consultarParroquia(String codigoParroquia) {
        return parroquiaRepository.buscarPorNombre(codigoParroquia);
    }

    /**
     * Metodo que busca una Parroquia dado el nombre de la parroquia y el nombre
     * del municipio
     *
     * @param nombreParroquia String Nombre de la parroquia
     * @param nombreMunicipio String Nombre del municipio
     * @return ParroquiaEntidad objeto del modelo de Datos
     */
    private ParroquiaEntidad consultarParroquia(String nombreParroquia, String nombreMunicipio) {

        return parroquiaRepository.findByNombreAndMunicipioNombre(nombreParroquia, nombreMunicipio);
    }

    /**
     * Metodo que busca una Direccion dado un numero de Documento de Identidad
     *
     * @param numero String numero de Documento de Identidad
     * @return DireccionParticipanteEntidad objeto del modelo de Datos
     */
    private DireccionParticipanteEntidad consultarDireccion(long numeroId) {

        return direccionParticipanteRepository.findByParticipanteId(numeroId);
    }

    /**
     * Metodo registrarDeclaracionJurada que permite registrar una declaracion
     * jurada del participante.
     *
     * @param declaracionJurada Objeto de tipo DeclaracionJurada que contiene
     * los datos de la misma
     * @return Verdadero si registra la declaracion jurada
     * @throws java.lang.Exception
     */
    @Transactional
    @Override
    public boolean registrarDeclaracionJurada(DeclaracionJurada declaracionJurada) throws Exception {
        LOG.info("=====INICIANDO registrarDeclaracionJurada==========");

        DeclaracionJuradaEntidad declaracionJuradaEntidad = new DeclaracionJuradaEntidad();
        DeclaracionJuradaEntidad dj;
        boolean guardo = false;
        boolean cambioEstatus;
        String documento;
        SolicitudEntidad solicitudEntidad;
        ParticipanteEntidad participanteEntidad;
        List<ParticipanteEntidad> participantesEntidad = new ArrayList<>();
        List<Participante> participantes;

        imprimirObjeto(declaracionJurada);

        String estatusSolDJ = buscarValorProperties(Constantes.EST_SOL_DECLARACION_JURADA);
        LOG.info("estatusSolDJ --> " + estatusSolDJ);

        solicitudEntidad = solicitudRepository.findByNumero(declaracionJurada.getNumeroSolicitud());
        if (solicitudEntidad == null) {

            LOG.error("ERROR: validando numero de solicitud - solicitud no existe");
            throw new Exception(NUMEROSOLICITUDNOENCONTRADO);
        }
        if (estatusSolDJ == null) {
            cambioEstatus = cambiarEstatusSolicitud(solicitudEntidad, "PA");
            LOG.error("Cambio de Estatus de la solicitud a pendiente por autenticar " + cambioEstatus);
            throw new Exception("Valor no encontrado para: " + Constantes.EST_SOL_DECLARACION_JURADA);
        }

        if (declaracionJurada == null) {
            LOG.error("ERROR: parametro declaracion jurada nulo");
            cambioEstatus = cambiarEstatusSolicitud(solicitudEntidad, "PA");
            LOG.error("Cambio de Estatus de la solicitud a pendiente por autenticar " + cambioEstatus);
            throw new Exception("Parametro Declaracion Jurada es nulo");
        }

        declaracionJuradaEntidad.setSolicitud(solicitudEntidad);

        //Guarda la Declaracion Jurada
        declaracionJuradaEntidad.setFechaDeclaracionJurada(declaracionJurada.getFechaDeclaracion());
        declaracionJuradaEntidad.setIndicadorTipo(declaracionJurada.getTipoDeclaracion());

        try {
            declaracionJuradaRepository.save(declaracionJuradaEntidad);
        } catch (Exception e) {
            LOG.error("ERROR: Guardando declaracion jurada");
            cambioEstatus = cambiarEstatusSolicitud(solicitudEntidad, "PA");
            LOG.error("Cambio de Estatus de la solicitud a pendiente por autenticar " + cambioEstatus);
            throw new Exception("Error al guardar declaracion jurada: " + e.getMessage());
        }

        //Vincula la declaracion jurada a los participantes
        participantes = declaracionJurada.getParticipantes();
        for (Participante participante : participantes) {
            documento = participante.getDocumentoIdentidad().get(0).getNumero();
            participanteEntidad = buscarParticipante(documento, participante.getRol(),
                    declaracionJurada.getNumeroSolicitud());

            if (participanteEntidad != null) {
                participanteEntidad.setDeclaracionJurada(declaracionJuradaEntidad);
                participantesEntidad.add(participanteEntidad);
            }

        }

        //Actualizar direccion de los participantes de la declaracion jurada
        actualizarDireccionParticipante(declaracionJurada.getParticipantes(),
                declaracionJurada.getNumeroSolicitud());

        try {
            //Actualizar estatus de la solicitud
            SolicitudEstatusEntidad solicitudEstatusEntidad;
            solicitudEstatusEntidad = solicitudEstatusRepository.findByCodigo("PVA");

            solicitudEntidad.setEstatus(solicitudEstatusEntidad);
            solicitudRepository.save(solicitudEntidad);
        } catch (Exception e) {
            LOG.error("ERROR: Actualizando Estatus de la Solicitud");
            throw new Exception("Error al actualizar el estatus de la solicitud: "
                    + e.getMessage());
        }

        try {
            //Actualizar libro diario
            actualizarLibroDiario(solicitudEntidad.getNumero(), "PVA");
            guardo = true;
        } catch (Exception e) {
            LOG.error("ERROR: Actualizando Estatus del Libro Diario");
            throw new Exception("Error al actualizar el estatus del Libro Diario: "
                    + e.getMessage());
        }

        return guardo;
    }

    private ParticipanteEntidad buscarParticipante(String documento, String rol, String numeroSolic) {

        ParticipanteEntidad participanteEntidad;

        participanteEntidad
                = participanteRepository.findByNumeroDocIdentidadAndTipoParticipanteCodigoAndActaSolicitudNumero(
                        documento, rol, numeroSolic);
        LOG.info("Nombre del Part.: " + participanteEntidad.getPrimerNombre() + " "
                + participanteEntidad.getPrimerApellido());

        return participanteEntidad;
    }

    /**
     * Metodo para actualizar la direccion residencia de los integrantes de una
     * declaracion jurada
     *
     * @param participantes Lista de objetos de tipo Participante que
     * intervienen en una declaracion jurada
     * @param numeroSolic String que contiene el numero de solicitud
     * @return Verdadero si registra las direcciones de los participantes
     */
    @Transactional
    public boolean actualizarDireccionParticipante(List<Participante> participantes, String numeroSolic) {

        boolean guardo = false;
        String documento;
        ParticipanteEntidad participanteEntidad;
        List<DireccionParticipanteEntidad> listaDirecciones = new ArrayList<>();
        DireccionParticipanteEntidad direccionParticipanteEntidad;

        for (Participante parti : participantes) {
            //Se lee la primera posicion porque desde Autenticar Ciudadano con Declaracion Jurada se carga en
            //dicha posicion
            if ("RES".equalsIgnoreCase(parti.getDireccion().get(0).getTipoDireccion().getCodigo())
                    && parti.getDireccion().get(0).getUbicacion() != null) {

                documento = parti.getDocumentoIdentidad().get(0).getNumero();

                participanteEntidad
                        = participanteRepository.
                                findByNumeroDocIdentidadAndTipoParticipanteCodigoAndActaSolicitudNumero(
                                        documento, parti.getRol(), numeroSolic);

                direccionParticipanteEntidad = construirDireccionEntidad(participanteEntidad.getId(),
                        parti.getDireccion().get(0));
                direccionParticipanteEntidad.setParticipante(participanteEntidad);
                listaDirecciones.add(direccionParticipanteEntidad);
                participanteEntidad.setDireccionesParticipantes(listaDirecciones);

                participanteRepository.save(participanteEntidad);
                guardo = true;
            }
        }

        return guardo;
    }

    /**
     * Metodo registrarConsejoComunal que permite registrar una carta consejo
     * comunal del participante.
     *
     * @param cartaConsejoComunal Objeto de tipo CartaConsejoComunal que
     * contiene los datos de la misma
     * @return Verdadero si registra la carta consejo comunal
     * @throws java.lang.Exception
     */
    @Transactional
    @Override
    public boolean registrarConsejoComunal(CartaConsejoComunal cartaConsejoComunal) throws Exception {

        LOG.info("=====INICIANDO registrarConsejoComunal==========");

        CartaConsejoComunalEntidad cartaConsejoComunalEntidad;
        List<IntegranteCartaConsejoComunalEntidad> listaIntegrantesEntidad;
        SolicitudEntidad solicitudEntidad;
        boolean guardo = false;
        boolean cambioEstatus;

        ParticipanteEntidad participanteEntidad;

        imprimirObjeto(cartaConsejoComunal);

        solicitudEntidad = solicitudRepository.findByNumero(cartaConsejoComunal.getNumeroSolicitud());
        if (solicitudEntidad == null) {
            LOG.error("ERROR: validando numero de solicitud - solicitud no existe");
            throw new Exception(NUMEROSOLICITUDNOENCONTRADO);
        }

        String documento = cartaConsejoComunal.getParticipante().getDocumentoIdentidad().get(0).getNumero();
        participanteEntidad
                = participanteRepository.findByNumeroDocIdentidadAndTipoParticipanteCodigoAndActaSolicitudNumero(
                        documento, cartaConsejoComunal.getParticipante().getRol(),
                        cartaConsejoComunal.getNumeroSolicitud());

        if (participanteEntidad == null) {
            LOG.error("ERROR: validando participante - participante no existe");
            cambioEstatus = cambiarEstatusSolicitud(solicitudEntidad, "PA");
            LOG.error("Cambio de Estatus de la solicitud a pendiente por autenticar " + cambioEstatus);
            throw new Exception("Participante no encontrado");
        }

        listaIntegrantesEntidad = integranteCComunalMapper.boToEntities(cartaConsejoComunal.getIntegrantes());
        cartaConsejoComunalEntidad = cartaConsejoComunalMapper.boToEntity(cartaConsejoComunal);
        cartaConsejoComunalEntidad.setParticipante(participanteEntidad);
        cartaConsejoComunalEntidad.setIntegrantesCartaConsejoComunal(listaIntegrantesEntidad);
        for (IntegranteCartaConsejoComunalEntidad iCarta : listaIntegrantesEntidad) {
            iCarta.setCartaConsejoComunal(cartaConsejoComunalEntidad);
        }

        try {
            cartaCComunalRepository.save(cartaConsejoComunalEntidad);
            guardo = true;
        } catch (Exception e) {
            LOG.error("ERROR: Registrando Carta Consejo Comunal");
            cambioEstatus = cambiarEstatusSolicitud(solicitudEntidad, "PA");
            LOG.error("Cambio de Estatus de la solicitud a pendiente por autenticar " + cambioEstatus);
            throw new Exception("Error al registrar carta consejo comunal" + e.getMessage());
        }

        return guardo;
    }

    /**
     * Metodo para consultar las declaraciones Juradas
     *
     * @param numSolicitud String numero de la Solicitud
     * @return Lista de {@link DeclaracionJurada}
     */
    @Transactional
    @Override
    public List<DeclaracionJurada> consultarDeclaracionJurada(String numSolicitud) {
        LOG.info("=====INICIANDO ParticipanteBFImpl.consultarDeclaracionJurada==========");
        List<DeclaracionJuradaEntidad> listaDeclaracionEntidad;
        listaDeclaracionEntidad = declaracionJuradaRepository.findBySolicitudNumero(numSolicitud);
        return declaracionJuradaMapper.entitiesToBOs(listaDeclaracionEntidad);
    }

    /**
     * Metodo que permite buscar la Direccion de residencia del participante y
     * mapearla con la entidad
     *
     * @param idPart Id del Participante
     * @param direcc Pojo con la direccion del participante
     * @return Objeto de tipo DireccionParticipanteEntidad
     */
    public DireccionParticipanteEntidad construirDireccionEntidad(long idPart,
            Direccion direcc) {

        DireccionParticipanteEntidad direccionParticipanteEntidadBuscar;
        DireccionParticipanteEntidad direccionParticipanteEntidad = new DireccionParticipanteEntidad();
        ParroquiaEntidad parroquiaEntidad;
        PaisEntidad paisEntidad;

        direccionParticipanteEntidadBuscar = consultarDireccion(idPart);

        if (direccionParticipanteEntidadBuscar != null) {
            direccionParticipanteEntidad = direccionParticipanteEntidadBuscar;
        }

        direccionParticipanteEntidad.setDireccionUbicacion(direcc.getUbicacion());
        LOG.info("Ubicacion Resd.: " + direcc.getUbicacion());
        if (direcc.getEstado() != null && direcc.getMunicipio() != null && direcc.getParroquia() != null) {
            parroquiaEntidad = buscarParroquia(direcc.getParroquia(), direcc.getMunicipio());
            direccionParticipanteEntidad.setParroquia(parroquiaEntidad);
        }

        //Busca el pais        
        if (direcc.getPais() != null) {
            paisEntidad = buscarPaisPorNombre(direcc.getPais().getNombre());
            direccionParticipanteEntidad.setPais(paisEntidad);
        }

        return direccionParticipanteEntidad;
    }

    /**
     * Metodo que obtiene el tipo libro dado el codigo del tramite
     *
     * @param codigoTramite String codigo del tramite
     * @return String con el codigo del tipo libro
     */
    private TipoLibroEntidad buscarTipoLibro(String codigoTramite, String valorToken) throws Exception {
        String tipoLibro;
        TipoLibroEntidad tipoLibroEntidad = null;
        LibroServicioCliente libroServicioCliente = new LibroServicioCliente();
        tipoLibro = libroServicioCliente.obtenerTipoLibro(codigoTramite, valorToken);

        if (!"0".equalsIgnoreCase(tipoLibro)) {
            tipoLibroEntidad = tipoLibroRepository.findOne(Long.valueOf(tipoLibro));

            if (tipoLibroEntidad == null) {
                LOG.error("ERROR: consultando tipo libro -  Tipo libro no existe para tramite --> " + codigoTramite);
                throw new Exception("Tipo Libro no encontrado para tramite: " + codigoTramite);
            }
        }

        return tipoLibroEntidad;
    }

    /**
     * Metodo que construye el numero de acta (proceso de autenticación de
     * ciudadano)
     *
     * @param codigoOficina String que contiene el id de la oficina donde se
     * esta creando el acta
     * @param tipoLibro String que contiene el id del tipo libro correspondiente
     * al acta
     * @return String que contiene el numero del acta (proceso de autenticacion
     * de ciudadano)
     */
    private String[] generarNroActa(String codigoOficina, String tipoLibro) {

        Calendar calendario = Calendar.getInstance();
        String numeroActa;
        String[] valores = new String[2];
        String codOficina;
        int secuencia;

        /* Generando el numero de Acta */
        String nroTomo = "01";
        String nroFolio = "001";
        //5 digitos
        secuencia = new Random().nextInt(90000) + 1000;
        codOficina = String.format("%05d", Integer.parseInt(codigoOficina));

        LOG.info("Oficina -> " + codOficina + " tipoLibro -> " + tipoLibro + " seq -> " + secuencia);

        //Anio-Oficina-TipoLibro-Tomo-Dia-Secuencia
        numeroActa = String.valueOf(calendario.get(Calendar.YEAR))
                + codOficina
                + tipoLibro
                + nroTomo + String.valueOf(calendario.get(Calendar.DAY_OF_YEAR))
                + secuencia;

        LOG.info("numero -> " + numeroActa);
        valores[0] = numeroActa;
        valores[1] = nroFolio;

        return valores;
    }

    /**
     * Actualiza libro diario dado un numero de solicitud. Se ejecuta en
     * actualizarEstadoSolicitud
     *
     * @param numSolicitud String Numero de solicitud
     * @param codigoEstatus String codigo de estatus
     */
    @Transactional
    private void actualizarLibroDiario(String numSolicitud, String codigoEstatus) throws Exception {

        LOG.info("actualizarLibroDiario --> " + numSolicitud);
        DiarioEntidad diarioEntidad;

        diarioEntidad = diarioRepository.findBySolicitudNumero(numSolicitud);

        if (diarioEntidad == null) {
            LOG.error("ERROR: consultando el Libro Diario - Libro Diario no existe");
            throw new Exception("Libro Diario no encontrado");
        }

        try {
            LOG.info("Estatus Solicitud --> " + codigoEstatus);
            diarioEntidad.setEstatus(consultarEstadoLibroDiario(codigoEstatus));
            diarioRepository.save(diarioEntidad);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Buscar el estatus de un libro diario dado el codigo del estatus de la
     * solicitud
     *
     * @param codigo String codigo del estatus de la solicitud
     * @return Objeto entidad de tipo DiarioEstatusEntidad
     */
    private DiarioEstatusEntidad consultarEstadoLibroDiario(String codigo) {
        DiarioEstatusEntidad diarioEstatusEntidad;
        LOG.info("consultarEstadoLibroDiario========== " + codigo);
        diarioEstatusEntidad = diarioEstatusRepository.findByCodigoDiarioEstatus(codigo.toUpperCase());

        return diarioEstatusEntidad;
    }

    /**
     * Metodo que busca el numero de acta dado el numero de la solicitud
     *
     * @param numero String Numero de la solicitud
     * @return String Numero de acta
     */
    private List<ActaEntidad> buscaNumeroActa(String numero) {

//        ActaEntidad valor = null;
        List<ActaEntidad> actasEntidad;
        actasEntidad = actaRepository.findBySolicitudNumero(numero);

//        if (!actasEntidad.isEmpty()) {
//            valor = actasEntidad.get(0);
//        }
        return actasEntidad;
    }

    /**
     * Metodo que busca el identificador del sexo dado la descripcion del sexo
     *
     * @param sexo String nombre del sexo
     * @return String Identificador del sexo
     */
    private String buscarIdentificadorEstadoCivil(String estadoCivil) {

        String valor;

        Map<String, String> hmEstadoCivil = new HashMap<>();
        hmEstadoCivil.put("SOLTERO", "S");
        hmEstadoCivil.put("CASADO", "C");
        hmEstadoCivil.put("VIUDO", "V");
        hmEstadoCivil.put("DIVORCIADO", "D");
        hmEstadoCivil.put("", " ");
        valor = hmEstadoCivil.get(estadoCivil);

        return valor;
    }

    /**
     * Metodo que busca el identificador del sexo dado la descripcion del sexo
     *
     * @param sexo String nombre del sexo
     * @return String Identificador del sexo
     */
    private String buscarIdentificadorSexo(String sexo) {

        String valor;

        Map<String, String> hmESexo = new HashMap<>();
        hmESexo.put("MASCULINO", "M");
        hmESexo.put("FEMENINO", "F");
        hmESexo.put("", " ");
        valor = hmESexo.get(sexo);

        return valor;
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

    /**
     * Metodo que imprime el objeto de entrada de cualquier metodo que lo
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
            java.util.logging.Logger.getLogger(ParticipanteBFImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOG.info("Imprimir Json de Entrada: " + obj.getClass().getName() + " - " + json);

    }

    /**
     * Busca una lista de de participantes dado un documento del usuario para
     * mostrar la informacion de las diferentes actas.
     *
     * @param numeroDocumento El documento del ciudadano
     * @param flag Bandera para indicar cual acta buscar, true actas propias,
     * false actas relacionada
     * @return Lista de actas
     * @throws Exception
     */
    @Override
    @Transactional
    public List<Acta> consultarParticipantePorDocumento(String numeroDocumento, boolean flag) throws Exception {

        List<ParticipanteEntidad> participantes;
        List<EcuParticipanteActaEntidad> epas;
        List<Participante> ps;
        List<Acta> actas = new ArrayList<>();
        Acta act;
        Participante part;
        String[] actasPropias, actasRelacionada, matrimonio;
        String hijo;

        actasPropias = buscarValorProperties(Constantes.ACTAS_PROPIAS).split(",");
        actasRelacionada = buscarValorProperties(Constantes.ACTAS_RELACIONADA).split(",");
        matrimonio = buscarValorProperties(Constantes.ACTAS_MATRIMONIO).split(",");
        hijo = buscarValorProperties(Constantes.ACTAS_HIJOS);

        participantes = participanteRepository.findByNumeroDocIdentidad(numeroDocumento);

        if (flag) {
            LOG.info("actas propias");
            for (ParticipanteEntidad partici : participantes) {
                for (String ap : actasPropias) {
                    LOG.info("comparando tipo participante (BD) " + partici.getTipoParticipante().getCodigo() + " con (properties) " + ap);
                    if (ap.equalsIgnoreCase(partici.getTipoParticipante().getCodigo())) {
                        epas = partici.getActa().getEcuParticipanteActaEntidad();
                        for (EcuParticipanteActaEntidad epa : epas) {
                            if (epa.getParticipante().getTipoParticipante().getCodigo().equalsIgnoreCase(hijo)) {
                                ps = new ArrayList<>();
                                LOG.info("acta como hijo " + epa.getActa().getNumeroActa());
                                LOG.info("participante " + epa.getParticipante().getPrimerNombre());
                                act = actaMapper.entityToBO(epa.getActa());
                                part = participanteMapper.entityToBO(epa.getParticipante());
                                ps.add(part);
                                act.setParticipantes(ps);
                                actas.add(act);
                            } else {
                                for (String m : matrimonio) {
                                    if (epa.getParticipante().getTipoParticipante().getCodigo().equalsIgnoreCase(m)) {
                                        ps = new ArrayList<>();
                                        LOG.info("acta como matrimonio " + epa.getActa().getNumeroActa());
                                        LOG.info("participante " + epa.getParticipante().getPrimerNombre());
                                        act = actaMapper.entityToBO(epa.getActa());
                                        part = participanteMapper.entityToBO(epa.getParticipante());
                                        ps.add(part);
                                        act.setParticipantes(ps);
                                        actas.add(act);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else {
            LOG.info("actas relacionadas");
            for (ParticipanteEntidad partici : participantes) {
                for (String ar : actasRelacionada) {
                    LOG.info("comparando tipo participante (BD) " + partici.getTipoParticipante().getCodigo() + " con (properties) " + ar);
                    if (ar.equalsIgnoreCase(partici.getTipoParticipante().getCodigo())) {
                        epas = partici.getActa().getEcuParticipanteActaEntidad();
                        for (EcuParticipanteActaEntidad epa : epas) {
                            if (epa.getParticipante().getTipoParticipante().getCodigo().equalsIgnoreCase(hijo)) {
                                ps = new ArrayList<>();
                                LOG.info("participante " + epa.getParticipante().getPrimerNombre());
                                act = actaMapper.entityToBO(epa.getActa());
                                part = participanteMapper.entityToBO(epa.getParticipante());
                                ps.add(part);
                                act.setParticipantes(ps);
                                actas.add(act);
                            }
                        }
                    }
                }
            }
        }

        return actas;
    }

    /**
     * Actualiza el estatus de una solicitud y del libro diario
     *
     * @param solicitudEntidad Objeto Solicitud Entidad
     * @param codigoEstatus String codigo de estatus
     */
    @Transactional
    private boolean cambiarEstatusSolicitud(SolicitudEntidad solicitudEntidad, String codigoEstatus) {

        LOG.info("=====INICIANDO cambiarEstatusSolicitud==========");
        DiarioEntidad diarioEntidad;
        SolicitudEstatusEntidad solicitudEstatusEntidad;

        try {

            solicitudEstatusEntidad = solicitudEstatusRepository.findByCodigo(codigoEstatus);
            solicitudEntidad.setEstatus(solicitudEstatusEntidad);
            solicitudRepository.save(solicitudEntidad);

        } catch (Exception e) {
            LOG.error("Error cambiando estatus de la solicitud" + e);
            return false;
        }
        try {

            diarioEntidad = diarioRepository.findBySolicitudNumero(solicitudEntidad.getNumero());

            if (diarioEntidad == null) {
                LOG.error("ERROR: consultando el Libro Diario - Libro Diario no existe");
                throw new Exception("Libro Diario no encontrado");
            }

            diarioEntidad.setEstatus(consultarEstadoLibroDiario(codigoEstatus));
            diarioRepository.save(diarioEntidad);

        } catch (Exception e) {
            LOG.error("Error cambiando estatus del libro diario " + e);
            return false;

        }

        return true;
    }

}
