package ve.gob.cne.sarc.ecu.core.ecu.mapper;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.ecu.core.ecu.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuParticipanteActaEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.ActaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ParticipanteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.EcuParticipanteActaRepository;

/**
 * EcuMapper.java
 *
 * @descripcion [Clase para el mapeo de la Entidad ecu con la clase ecu y viceversa]
 * @version 1.0 14/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring", uses = {ActaMapper.class})
public abstract class EcuMapper {

    private static final Logger LOG = LoggerFactory.getLogger(EcuMapper.class);

    @Autowired
    private EcuParticipanteActaRepository ecuParticipanteActaRepository;

    @Autowired
    private ActaRepository actaRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private ActaMapper actaMapper;

    /**
     * Convierte una entidad en un pojo
     *
     * @param ecuEntidad EcuEntidad entidad con la informacion del Ecu
     * @return Pojo Ecu con la informacion del Ecu
     */
    public Ecu entityToBO(EcuEntidad ecuEntidad) {

        Ecu ecu = new Ecu();
        List<EcuParticipanteActaEntidad> epaeList;
        List<ActaEntidad> listaActas = new ArrayList<>();
        List<Acta> actas;

        LOG.info("ecuEntidad --> " + ecuEntidad.getId());

        epaeList = ecuParticipanteActaRepository.findByEcuId(ecuEntidad.getId());

        if (!epaeList.isEmpty()) {
            listaActas = construirListaActas(epaeList);
        }

        ecu.setFechaCreacion(ecuEntidad.getFechaCreacion());
        ecu.setEstatus(ecuEntidad.getEstadoEcu().getNombreEstadoEcu());
        actas = actaMapper.entitiesToBOs(listaActas);
        ecu.setListaActas(actas);

        return ecu;
    }

    /**
     * Metodo que construye la lista de acta entidad relacionadas con ecu y participante
     *
     * @param listaEpa Lista de objetos de tipo EcuParticipanteActaEntidad
     * @return Lista de objetos entidad de tipo ActaEntidad
     */
    private List<ActaEntidad> construirListaActas(List<EcuParticipanteActaEntidad> listaEpa) {

        List<ActaEntidad> listaActas = new ArrayList<>();

        ActaEntidad actaEntidad;
        String numero = "";
        LOG.info("Numero: " + numero);

        for (EcuParticipanteActaEntidad epa : listaEpa) {
            if (!numero.equals(epa.getActa().getNumeroActa())) {

                LOG.info("acta: " + epa.getActa().getId() + " - " + epa.getActa().getNumeroActa());
                actaEntidad = buscarActa(epa.getActa().getId());
                LOG.info("Acta encontrada: " + actaEntidad.getNumeroActa());
                actaEntidad.setParticipantes(buscarParticipantesActaEcu(listaEpa, epa.getActa().getId()));

                listaActas.add(actaEntidad);
            }
            numero = epa.getActa().getNumeroActa();
            LOG.info("Numero: " + numero);
        }

        return listaActas;
    }

    /**
     * Metodo que busca el acta dado el id
     *
     * @param idActa long id de acta
     * @return Objeto entidad de tipo ActaEntidad
     */
    private ActaEntidad buscarActa(long idActa) {

        ActaEntidad actaEntidad;

        LOG.info("buscarActa --> " + idActa);

        actaEntidad = actaRepository.findOne(idActa);
        if (actaEntidad == null) {
            LOG.error("ERROR: buscarActa - Acta no encontrada: " + idActa);
            throw new ResourceNotFoundException("Acta no encontrada");
        }

        return actaEntidad;
    }

    /**
     * Metodo que busca el participante dado el id del participante
     *
     * @param idP long id del participante
     * @return Objeto entidad de tipo ParticipanteEntidad
     */
    private ParticipanteEntidad buscarParticipante(long idP) {

        ParticipanteEntidad participanteEntidad;

        LOG.info("buscarParticipante --> " + idP);

        participanteEntidad = participanteRepository.findOne(idP);
        if (participanteEntidad == null) {
            LOG.error("ERROR: buscarParticipante - Participante no encontrado: " + idP);
            throw new ResourceNotFoundException("Participante no encontrado");
        }

        return participanteEntidad;
    }

    /**
     * Metodo que permite buscar los participantes vinculados el id del acta
     *
     * @param listaEpa Lista de objetos entidad de tipo EcuParticipanteActaEntidad
     * @param idActa long id del acta
     * @return Lista de objetos entidad de tipo ParticipanteEntidad
     */
    private List<ParticipanteEntidad> buscarParticipantesActaEcu(List<EcuParticipanteActaEntidad> listaEpa,
            long idActa) {

        List<ParticipanteEntidad> participantes = new ArrayList<>();
        ParticipanteEntidad participanteEntidad;

        for (EcuParticipanteActaEntidad epa : listaEpa) {
            if (idActa == epa.getActa().getId()) {
                participanteEntidad = buscarParticipante(epa.getParticipante().getId());
                participantes.add(participanteEntidad);
            }
        }

        return participantes;
    }

    /**
     * Convierte un pojo en una entidad
     *
     * @param ecu Ecu pojo con la informacion del Ecu
     * @return Entidad con la informacion del Ecu
     */
    abstract EcuEntidad boToEntity(Ecu ecu);

}
