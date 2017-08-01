package ve.gob.cne.sarc.recaudo.core.recaudo.business.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.comunes.catalogo.Recaudo;
import ve.gob.cne.sarc.persistencia.entidades.RecaudoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudRecaudoEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.RecaudoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudRecaudoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudRepository;
import ve.gob.cne.sarc.recaudo.core.recaudo.business.RecaudoBF;
import ve.gob.cne.sarc.recaudo.core.recaudo.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.recaudo.core.recaudo.mapper.RecaudoMapper;
import ve.gob.cne.sarc.recaudo.core.recaudo.mapper.SolicitudRecaudoMapper;

/**
 * RecaudoBFImpl.java
 *
 * @descripcion Implementacion del BusinessFacade con la logica de negocio de
 * manejo de Recaudo
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Component
public class RecaudoBFImpl implements RecaudoBF {

    private static final Logger LOG = LoggerFactory.getLogger(RecaudoBFImpl.class);

    @Autowired
    private RecaudoRepository recaudoRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudRecaudoRepository solicitudRecaudoRepository;

    @Autowired
    private RecaudoMapper recaudoMapper;

    @Autowired
    private SolicitudRecaudoMapper solicitudRecaudoMapper;

    /**
     * Metodo responsable de buscar Recaudo dado un codigo
     *
     * @param codigo String que contiene el codigo de Recaudo
     * @return Recaudo, instancia de objeto que contiene la informacion del
     * Recaudo
     */
    @Override
    public Recaudo consultarPorCodigo(String codigo) {
        LOG.info("=====INICIANDO RecaudoBFImpl.consultarPorCodigo==========");
        return recaudoMapper.entityToBO(recaudoRepository.findByCodigo(codigo));
    }

    /**
     * Metodo que permite registrar los recaudos solicitados en una solicitud
     *
     * @param numeroSolicitud String que contiene el numero de la solicitud
     * @param recaudos String[] que contiene los recaudos solicitados y la
     * obligatoriedad, su formato es {codRecaudo1:true, codRecaudo2:false,
     * codRecaudo3:true}
     * @return Verdadero si actualizó satisfactoriamente, en caso contrario
     * falso
     */
    @Override
    public boolean registrarRecaudos(String numeroSolicitud, String[] recaudos) {
        LOG.info("=====INICIANDO RecaudoBFImpl.registrarRecaudos==========");
        boolean respuesta = false;
        SolicitudEntidad solicitudEntidad;
        RecaudoEntidad recaudoEntidad;
        SolicitudRecaudoEntidad solicitudRecaudoEntidad;
        String rec;
        LOG.info("=====================Solicitud==============================");
        solicitudEntidad = solicitudRepository.findByNumero(numeroSolicitud);

        if (solicitudEntidad == null) {
            LOG.error("ERROR: consultando la solicitud - solicitud no existe");
            throw new ResourceNotFoundException("Solicitud no encontrada");
        }

        try {
            for (String recaudo : recaudos) {
                rec = recaudo.replace("[", "").replace("]", "");

                String[] valorRecaudo = rec.split(":");
                LOG.info("valorRecaudo --> recaudo: " + valorRecaudo[0]
                        + " obligatorio: " + valorRecaudo[1]);

                recaudoEntidad = recaudoRepository.findByCodigo(valorRecaudo[0]);

                if (recaudoEntidad == null) {
                    LOG.error("ERROR: consultando el recaudo - recaudo no existe");
                    throw new ResourceNotFoundException("Recaudo no encontrado");
                } else {
                    solicitudRecaudoEntidad = guardarRecaudo(solicitudEntidad, recaudoEntidad, valorRecaudo);
                }
                respuesta = true;
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException(e);

        }

        return respuesta;
    }

    /**
     * Metodo que permite guardar recaudos solicitados en una solicitud
     *
     * @param solicitudEntidad objeto entidad de tipo SolicitudEntidad
     * @param recaudoEntidad objeto entidad de tipo Recaudo
     * @param valorRecaudo String[] que contiene los recaudos solicitados y la
     * obligatoriedad, su formato es {codRecaudo1:true, codRecaudo2:false,
     * codRecaudo3:true}
     * @return Verdadero si el valor de String es True en caso contrario False
     */
    private SolicitudRecaudoEntidad guardarRecaudo(SolicitudEntidad solicitudEntidad, RecaudoEntidad recaudoEntidad,
            String[] valorRecaudo) {

        SolicitudRecaudoEntidad solicitudRecaudoEntidad;
        LOG.info("=====Buscando SolicitudRecaudoEntidad=====");
        solicitudRecaudoEntidad = solicitudRecaudoRepository
                .findBySolicitudNumeroAndRecaudoCodigo(solicitudEntidad.getNumero(),
                        recaudoEntidad.getCodigo());
        if (solicitudRecaudoEntidad == null) {
            LOG.info("=====Guardando SolicitudRecaudoEntidad=====");
            solicitudRecaudoEntidad = new SolicitudRecaudoEntidad();
            solicitudRecaudoEntidad.setSolicitud(solicitudEntidad);
            solicitudRecaudoEntidad.setRecaudo(recaudoEntidad);
            solicitudRecaudoEntidad.setFechaInicio(new Date());
        }
        solicitudRecaudoEntidad.setObligatorio(convertirObligatoriedad(valorRecaudo));
        solicitudRecaudoEntidad.setFechaActualizando(new Date());
        solicitudRecaudoRepository.save(solicitudRecaudoEntidad);
        LOG.info("=====SolicitudRecaudoEntidad Guardado=====");

        return solicitudRecaudoEntidad;
    }

    /**
     * Metodo que permite registrar los recaudos solicitados en una solicitud
     *
     * @param valorRecaudo String[] que contiene los recaudos solicitados y la
     * obligatoriedad, su formato es {codRecaudo1:true, codRecaudo2:false,
     * codRecaudo3:true}
     * @return Verdadero si el valor de String es True en caso contrario False
     */
    private boolean convertirObligatoriedad(String[] valorRecaudo) {
        boolean res;
        boolean valor = "true".equals(valorRecaudo[1]);
        if (valor) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }

    /**
     *
     * Metodo que permite consultar los recaudos en una solicitud
     *
     * @param numSolicitud String numero de solicitud
     * @return lista de objeto de tipo Recaudo
     *
     */
    @Override
    public List<Recaudo> consultarRecaudos(String numSolicitud) {
        LOG.info("=====INICIANDO DefuncionBFImpl.consultarOre==========");
        List<SolicitudRecaudoEntidad> listaSolicitudRecaudoEntidad;
        listaSolicitudRecaudoEntidad = solicitudRecaudoRepository.findBySolicitudNumero(numSolicitud);
        return solicitudRecaudoMapper.entitiesToBOs(listaSolicitudRecaudoEntidad);
    }

}
