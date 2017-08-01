package ve.gob.cne.sarc.ecu.core.ecu.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.ecu.core.ecu.business.EcuBF;
import ve.gob.cne.sarc.ecu.core.ecu.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.ecu.core.ecu.mapper.EcuMapper;
import ve.gob.cne.sarc.persistencia.entidades.AuditoriaEcuEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEcuEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.AuditoriaEcuRepository;
import ve.gob.cne.sarc.persistencia.repositorios.EstadoEcuRepository;
import ve.gob.cne.sarc.ecu.core.ecu.exception.EcuException;
import ve.gob.cne.sarc.ecu.core.ecu.mapper.ParticipanteMapper;
import ve.gob.cne.sarc.ecu.core.util.Constantes;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuParticipanteActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.CiudadanoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.EcuParticipanteActaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.EcuRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudRepository;

/**
 * EcuBFimpl.java
 *
 * @descripcion [Clase con las logicas de implemantacion para la interfaz EcuBF]
 * @version 1.0 14/7/2016
 * @author Erick Escalona
 */
@Component
public class EcuBFimpl implements EcuBF {

    private static final Logger LOG = LoggerFactory.getLogger(EcuBFimpl.class);

    @Autowired
    private EcuRepository ecuRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private EcuParticipanteActaRepository ecuParticipanteActaRepository;

    @Autowired
    private CiudadanoRepository ciudadanoRepository;

    @Autowired
    private EstadoEcuRepository estadoEcuRepository;

    @Autowired
    private AuditoriaEcuRepository auditoriaEcuRepository;

    @Autowired
    private ParticipanteMapper participanteMapper;

    @Autowired
    private EcuMapper ecuMapper;

    /**
     * Valida un ecu y devuelve una lista de participante
     *
     * @param ciudadano Ciudadano pojo con la informacion del ciudadano
     * @return Lista de Objetos de tipo Participante
     */
    @Override
    public List<Participante> validarEcu(Ciudadano ciudadano) {
        LOG.debug("=====INICIANDO EcuBFImpl.validarEcu==========");

        LOG.info("validando el ecu del ciudadano " + ciudadano.getPrimerNombre()
                + " " + ciudadano.getPrimerApellido());

        EcuEntidad ecuEntidad;
        List<EcuParticipanteActaEntidad> epaeList = new ArrayList<>();
        List<ParticipanteEntidad> listParticipanteEntidad = new ArrayList<>();
        List<Participante> listParticipante = new ArrayList<>();

        if (ciudadano.getDocumentoIdentidad().isEmpty()) {
            listParticipante = new ArrayList<>();
        } else {

            for (DocumentoIdentidad td : ciudadano.getDocumentoIdentidad()) {
                if (td != null) {

                    LOG.info("documento de identidad " + td.getNumero());
                    ecuEntidad = ecuRepository.findByNumeroDocumento(td.getNumero());

                    LOG.info("buscando lista participantes por el id del ECU " + ecuEntidad.getId());
                    epaeList = ecuParticipanteActaRepository.findByEcuId(ecuEntidad.getId());
                    break;
                }
            }

            if (!epaeList.isEmpty()) {
                for (EcuParticipanteActaEntidad epae : epaeList) {
                    listParticipanteEntidad.add(epae.getParticipante());
                }

                if (!listParticipanteEntidad.isEmpty()) {
                    listParticipante = participanteMapper.entitysToBos(listParticipanteEntidad);
                } else {
                    listParticipante = new ArrayList<>();
                }
            }
            LOG.info("tamaño lista participante " + listParticipante.size());
        }
        return listParticipante;
    }

    /**
     * Vincula el acta con el ecu y los participantes
     *
     * @param solicitud solicitud a vincular
     * @return Verdadero si se vinculo falso en caso contrario
     * @throws EcuException
     */
    @Override
    @Transactional
    public boolean vincularActaEcuParticipante(String solicitud) throws EcuException {
        LOG.debug("=====INICIANDO EcuBFImpl.vincularActaEcuParticipante==========");

        boolean resp = false;
        TramiteEntidad tramiteEntidad;
        SolicitudEntidad solicitudEntidad;
        List<EcuParticipanteActaEntidad> epaesResult;
        List<EcuParticipanteActaEntidad> epaes = new ArrayList<>();

        solicitudEntidad = solicitudRepository.findByNumero(solicitud);

        if (solicitudEntidad != null) {

            tramiteEntidad = solicitudEntidad.getTramite();
            LOG.info("tipo de tramite " + tramiteEntidad.getCodigo());

            if ("RDEFU".equalsIgnoreCase(tramiteEntidad.getCodigo())) {

                epaes = defuncion(solicitudEntidad, solicitud);

            } else if ("RNACI".equalsIgnoreCase(tramiteEntidad.getCodigo())
                    || "RADOP".equalsIgnoreCase(tramiteEntidad.getCodigo())
                    || "RRECO".equalsIgnoreCase(tramiteEntidad.getCodigo())
                    || "RRFIL".equalsIgnoreCase(tramiteEntidad.getCodigo())) {

                epaes = generarActaEntidadNacimiento(solicitudEntidad, solicitud);
            }

            epaesResult = (List<EcuParticipanteActaEntidad>) ecuParticipanteActaRepository.save(epaes);

            if (!epaesResult.isEmpty()) {
                resp = true;
            }

        } else {
            throw new EcuException("Solicitud " + solicitud + " no encontrada");
        }

        return resp;
    }

    /**
     * Crea una lista de entidad ecuParticipanteActaEntidad segun el tramite
     *
     * @param solicitudEntidad entidad de solicitud,
     * @param solicitud pojo de Solicitud
     * @return Lista de tipo ecuParticipanteActaEntidad
     */
    private List<EcuParticipanteActaEntidad> defuncion(
            SolicitudEntidad solicitudEntidad, String solicitud) throws EcuException {

        List<EcuParticipanteActaEntidad> epaesDef;

        epaesDef = generarActaEntidadDefuncion(solicitudEntidad, solicitud);

        return epaesDef;
    }

    private List<EcuParticipanteActaEntidad> generarActaEntidadDefuncion(SolicitudEntidad solicitudEntidad,
            String solicitud) {

        List<EcuParticipanteActaEntidad> epaesDef = null;
        ActaEntidad entidad = null;

        LOG.info("tamaño lista acta " + solicitudEntidad.getActa().size());

        for (ActaEntidad ae : solicitudEntidad.getActa()) {

            if (ae.getSolicitud().getNumero().equalsIgnoreCase(solicitud)) {
                LOG.info("tamaño lista participantes " + ae.getParticipantes().size());
                entidad = ae;
                break;
            }
        }

        if (entidad != null) {
            for (ParticipanteEntidad participanteEntidad : entidad.getParticipantes()) {
                if ("FAL".equalsIgnoreCase(participanteEntidad.getTipoParticipante().getCodigo())) {
                    epaesDef = generarListadoEPADefuncion(entidad);
                    break;
                }
            }
        }
        return epaesDef;
    }

    private List<EcuParticipanteActaEntidad> generarListadoEPADefuncion(ActaEntidad actaEntidadDef) {

        List<EcuParticipanteActaEntidad> epaesDef = new ArrayList<>();
        EcuParticipanteActaEntidad epaeDef;
        List<String> documentosIdenDef = new ArrayList<>();
        CiudadanoEntidad ciudadanoEntidadDef;

        for (ParticipanteEntidad pe : actaEntidadDef.getParticipantes()) {

            LOG.info("tipo de participante " + pe.getTipoParticipante().getCodigo());

            epaeDef = new EcuParticipanteActaEntidad();
            epaeDef.setActa(actaEntidadDef);

            if ("MAD".equalsIgnoreCase(pe.getTipoParticipante().getCodigo())
                    || "MAM".equalsIgnoreCase(pe.getTipoParticipante().getCodigo())) {
                LOG.info("documento de identidad de mama del fallecido "
                        + pe.getNumeroDocIdentidad());
                documentosIdenDef.add(pe.getNumeroDocIdentidad());
                epaeDef.setParticipante(pe);
                epaesDef.add(epaeDef);
            } else if ("PAD".equalsIgnoreCase(pe.getTipoParticipante().getCodigo())
                    || "PAP".equalsIgnoreCase(pe.getTipoParticipante().getCodigo())) {
                LOG.info("documento de identidad de papa del fallecido "
                        + pe.getNumeroDocIdentidad());
                documentosIdenDef.add(pe.getNumeroDocIdentidad());
                epaeDef.setParticipante(pe);
                epaesDef.add(epaeDef);
            } else if ("FAL".equalsIgnoreCase(pe.getTipoParticipante().getCodigo())) {
                LOG.info("documento de identidad del fallecido "
                        + pe.getNumeroDocIdentidad());
                documentosIdenDef.add(pe.getNumeroDocIdentidad());
                epaeDef.setParticipante(pe);
                epaesDef.add(epaeDef);
            } else if ("CON".equalsIgnoreCase(pe.getTipoParticipante().getCodigo())
                    || "COUNI".equalsIgnoreCase(pe.getTipoParticipante().getCodigo())
                    || "COUND".equalsIgnoreCase(pe.getTipoParticipante().getCodigo())) {
                LOG.info("documento de identidad del conyuge del fallecido "
                        + pe.getNumeroDocIdentidad());
                documentosIdenDef.add(pe.getNumeroDocIdentidad());
                epaeDef.setParticipante(pe);
                epaesDef.add(epaeDef);
            }

        }

        for (String doc : documentosIdenDef) {
            ciudadanoEntidadDef = ciudadanoRepository.findByNumeroDocIdentidad(doc);
            for (int i = 0; i < epaesDef.size(); i++) {
                EcuParticipanteActaEntidad epa = epaesDef.get(i);
                if (epa.getParticipante().getNumeroDocIdentidad().equalsIgnoreCase(doc)) {
                    epaesDef.get(i).setEcu(ciudadanoEntidadDef.getEcu());
                }
            }
        }
        return epaesDef;
    }

    /**
     * Crea una lista de entidad ecuParticipanteActaEntidad segun el tramite
     *
     * @param solicitudEntidad entidad de solicitud,
     * @param solicitud pojo de Solicitud
     * @return Lista de tipo ecuParticipanteActaEntidad
     */
    private List<EcuParticipanteActaEntidad> generarActaEntidadNacimiento(
            SolicitudEntidad solicitudEntidad, String solicitud) throws EcuException {

        ActaEntidad actaEntidadNac = new ActaEntidad();
        List<ActaEntidad> actasEntidadNac;
        List<EcuParticipanteActaEntidad> epaesNac = null;

        actasEntidadNac = solicitudEntidad.getActa();
        LOG.info("tamaño lista acta " + actasEntidadNac.size());

        for (ActaEntidad ae : actasEntidadNac) {

            if (ae.getSolicitud().getNumero().equalsIgnoreCase(solicitud)) {
                actaEntidadNac = ae;
                LOG.info("tamaño lista participantes nacimiento " + ae.getParticipantes().size());
                break;
            }
        }

        for (ParticipanteEntidad participanteEntidad : actaEntidadNac.getParticipantes()) {
            if ("HIJ".equalsIgnoreCase(participanteEntidad.getTipoParticipante().getCodigo())) {
                epaesNac = generarListadoEPANacimiento(actaEntidadNac);
                break;
            }
        }

        return epaesNac;
    }

    private List<EcuParticipanteActaEntidad> generarListadoEPANacimiento(ActaEntidad actaEntidadNac) {

        EcuParticipanteActaEntidad epaeNac;
        List<String> documentosIdenNac = new ArrayList<>();
        List<EcuParticipanteActaEntidad> epaesNac = new ArrayList<>();
        CiudadanoEntidad ciudadanoEntidadNac;

        for (ParticipanteEntidad participanteEntidad : actaEntidadNac.getParticipantes()) {

            LOG.info("tipo de participante " + participanteEntidad.getTipoParticipante().getCodigo());
            epaeNac = new EcuParticipanteActaEntidad();

            if ("MAD".equalsIgnoreCase(participanteEntidad.getTipoParticipante().getCodigo())
                    || "MAM".equalsIgnoreCase(participanteEntidad.getTipoParticipante().getCodigo())) {
                LOG.info("documento de identidad de mama del nacido "
                        + participanteEntidad.getNumeroDocIdentidad());
                documentosIdenNac.add(participanteEntidad.getNumeroDocIdentidad());
                epaeNac.setParticipante(participanteEntidad);
                epaeNac.setActa(actaEntidadNac);
                epaesNac.add(epaeNac);
            }

            if ("PAD".equalsIgnoreCase(participanteEntidad.getTipoParticipante().getCodigo())
                    || "PAP".equalsIgnoreCase(participanteEntidad.getTipoParticipante().getCodigo())) {
                LOG.info("documento de identidad de papa del nacido "
                        + participanteEntidad.getNumeroDocIdentidad());
                documentosIdenNac.add(participanteEntidad.getNumeroDocIdentidad());
                epaeNac.setParticipante(participanteEntidad);
                epaeNac.setActa(actaEntidadNac);
                epaesNac.add(epaeNac);
            }

            if ("HIJ".equalsIgnoreCase(participanteEntidad.getTipoParticipante().getCodigo())) {
                LOG.info("documento de identidad del fallecido "
                        + participanteEntidad.getNumeroDocIdentidad());
                documentosIdenNac.add(participanteEntidad.getNumeroDocIdentidad());
                epaeNac.setParticipante(participanteEntidad);
                epaeNac.setActa(actaEntidadNac);
                epaesNac.add(epaeNac);
            }
        }

        for (String doc : documentosIdenNac) {
            ciudadanoEntidadNac = ciudadanoRepository.findByNumeroDocIdentidad(doc);
            for (int i = 0; i < epaesNac.size(); i++) {
                EcuParticipanteActaEntidad epa = epaesNac.get(i);
                if (epa.getParticipante().getNumeroDocIdentidad().equalsIgnoreCase(doc)) {
                    epaesNac.get(i).setEcu(ciudadanoEntidadNac.getEcu());
                }
            }
        }
        return epaesNac;
    }

    /**
     * Actualiza un Ecu
     *
     * @param codigoEstadoEcu String que contiene el estado del Ecu
     * @param participante Participante pojo con la informacion del participante
     * @return ecu pojo con la informacion del ecu
     */
    @Override
    public Ecu actualizarEcu(String codigoEstadoEcu, Participante participante) {
        LOG.debug("=====INICIANDO EcuBFImpl.actualizarEcu==========");

        EcuEntidad ecuEntidad = null;
        EstadoEcuEntidad estadoEcuEntidad;
        AuditoriaEcuEntidad auditoriaEcuEntidad = new AuditoriaEcuEntidad();
        Ecu ecu;
        EcuEntidad resultado = null;
        for (DocumentoIdentidad td : participante.getDocumentoIdentidad()) {
            if (td != null) {

                LOG.info("documento de identidad " + td.getNumero());
                ecuEntidad = ecuRepository.findByNumeroDocumento(td.getNumero());
                break;
            }
        }
        if (ecuEntidad == null) {
            LOG.info("===========no consiguio el ecu ==============");
            throw new ResourceNotFoundException("Ecu tiene valor nulo");

        }
        try {

            estadoEcuEntidad = estadoEcuRepository.findByNombreEstadoEcu(codigoEstadoEcu);
            ecuEntidad.setEstadoEcu(estadoEcuEntidad);
            resultado = ecuRepository.save(ecuEntidad);
            auditoriaEcuEntidad.setEcu(ecuEntidad);
            auditoriaEcuEntidad.setEstadoEcu(estadoEcuEntidad);
            auditoriaEcuEntidad.setFechaEstadoEcu(new Date());
            auditoriaEcuEntidad.setFechaRegistro(new Date());
            auditoriaEcuRepository.save(auditoriaEcuEntidad);

        } catch (ResourceNotFoundException e) {
            LOG.error("Error al actualizar el ecu", e);
        }

        ecu = ecuMapper.entityToBO(resultado);
        return ecu;
    }

    /**
     * Busca un Ecu
     *
     * @param numeroDocIdentidad String numero de documento de identidad
     * @return true si existe el ecu y en caso contrario false
     */
    @Override
    public boolean buscarExistenciaECU(String numeroDocIdentidad) {
        LOG.debug("=====INICIANDO EcuBFImpl.buscarExistenciaECU==========");

        boolean resp;
        LOG.info("Documento de identidad " + numeroDocIdentidad);
        resp = ecuRepository.existsEcuByCiudadanoNumeroDocIdentidad(numeroDocIdentidad);

        return resp;
    }

    /**
     * Consulta el ECU de un ciudadano dado el numero de documento y el tipo de
     * documento de identificacion
     *
     * @param numeroDocumento String Numero de documento de identificacion
     * @param tipoDocumento String Tipo de documento de identificacion
     * (CED:Cedula, PAS:Pasaporte, NUI: Nui)
     * @return Pojo Ecu con los datos del Ecu del ciudadano
     */
    @Override
    @Transactional
    public Ecu consultarECU(String numeroDocumento, String tipoDocumento) {
        LOG.debug("=====INICIANDO EcuBFImpl.consultarECU==========");

        Ecu ecu;
        EcuEntidad ecuEntidad = null;

        //Validar tipo de documento 
        if (!tipoDocumento.equals(Constantes.CEDULA) && !tipoDocumento.equals(Constantes.NUI)
                && !tipoDocumento.equals(Constantes.PASAPORTE)) {
            LOG.error("ERROR: consultarECU - Tipo de documento invalido: " + tipoDocumento);
            throw new ResourceNotFoundException("Datos invalidos para el tipo de documento en consultarECU");
        }

        if (tipoDocumento.equals(Constantes.CEDULA) || tipoDocumento.equals(Constantes.PASAPORTE)) {
            ecuEntidad = ecuRepository.findByNumeroDocumento(numeroDocumento);
        } else if (tipoDocumento.equals(Constantes.NUI)) {
            ecuEntidad = ecuRepository.findByCiudadanoCodigoNUI(numeroDocumento);
        }

        if (ecuEntidad == null) {
            LOG.error("ERROR: consultarECU - Ecu no encontrado para el documento: "
                    + numeroDocumento + " - " + tipoDocumento);
            throw new ResourceNotFoundException("Ecu no encotrado");
        }

        ecu = ecuMapper.entityToBO(ecuEntidad);

        return ecu;
    }

}
