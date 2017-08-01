package ve.gob.cne.sarc.nui.core.nui.business.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.nui.core.nui.business.NuiBF;
import ve.gob.cne.sarc.nui.core.nui.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NuiEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NuiEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteNuiEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteNuiEstatusEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.CiudadanoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.NuiEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.NuiRepository;
import ve.gob.cne.sarc.persistencia.repositorios.PaqueteNuiEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.PaqueteNuiRepository;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * NuiBFImpl.java
 *
 * @descripcion [Clase con las logicas de negocion, de implemantacion para la
 * interfaz NuiBF]
 * @version 1.0 14/7/2016
 * @author Anabell De Faria
 */
@Component
public class NuiBFImpl implements NuiBF {

    private static final Logger LOG = LoggerFactory.getLogger(NuiBFImpl.class);
    private static final String ESTATUS_PAQUETE_RECIBIDO = "servicios.nui.estatus.paquete.recibido";
    private static final String ESTATUS_PAQUETE_CERRADO = "servicios.nui.estatus.paquete.cerrado";
    private static final String ESTATUS_NUI_DISPONIBLE = "servicios.nui.estatus.nui.disponible";
    private static final String ESTATUS_NUI_ASIGNADO = "servicios.nui.estatus.nui.asignado";

    @Autowired
    public AdministradorPropiedades properties;

    @Autowired
    private CiudadanoRepository ciudadanoRepository;

    @Autowired
    private PaqueteNuiRepository paqueteNuiRepository;

    @Autowired
    private NuiRepository nuiRepository;

    @Autowired
    private PaqueteNuiEstatusRepository paqueteNuiEstatusRepository;

    @Autowired
    private NuiEstatusRepository nuiEstatusRepository;

    /**
     * Busca el proximo NUI disponible segun el codigoOficina
     *
     * @param codigoOficina String codigo de la Oficina
     * @return string que describe el numero proximo del nui
     */
    @Override
    @Transactional
    public String buscarProximoNui(String codigoOficina) {
        LOG.info("Buscando proximo Nui de la oficina========== " + codigoOficina);
        Long paqueteEstatus = Long.parseLong(buscarValorProperties(ESTATUS_PAQUETE_RECIBIDO));
        Long nuiEstatus = Long.parseLong(buscarValorProperties(ESTATUS_NUI_DISPONIBLE));

        List<PaqueteNuiEntidad> paquetesEntidadesActivos = null;
        LOG.info("buscarListaPaqueteYListaNui==========oficina " + codigoOficina + "paquete estatus "
                + paqueteEstatus + " nui estatus " + nuiEstatus);
        try {
            paquetesEntidadesActivos = paqueteNuiRepository.buscarListaPaqueteYListaNui
                (codigoOficina, paqueteEstatus, nuiEstatus);

        } catch (Exception e) {
            LOG.error("ERROR: No se ha encontrado paquete recibido para esta oficina", e);
            throw new ResourceNotFoundException("No se ha encontrado paquete recibido para esta oficina");

        }
        LOG.info("Tamaño de lista paquetes " + paquetesEntidadesActivos.size());
        PaqueteNuiEntidad primerPaquete = paquetesEntidadesActivos.get(0);
        long primerpaqueteid = primerPaquete.getId();
        List<NuiEntidad> nuiDisponibles = null;
        try {
            nuiDisponibles = nuiRepository.findByPaqueteNuiIdAndNuiEstatusIdOrderByFechaInicioAscIdAsc
            (primerpaqueteid, nuiEstatus);

        } catch (Exception e) {
            LOG.error("ERROR: No se han encontrado Nui disponibles para este paquete", e);
            throw new ResourceNotFoundException("No se han encontrado Nui disponibles para este paquete");

        }
        if (primerPaquete.getNui().size() <= 1) {
            boolean paqueteCerrado = cerrarPaquete(primerPaquete);
            LOG.info("paquete cerrado " + paqueteCerrado);
        }
        LOG.info("primer Nui disponible " + nuiDisponibles.get(0).getIdentificadorNUI() 
                + " fecha " + nuiDisponibles.get(0).getFechaInicio());

        boolean nuiAsignado = cambiarEstatusNui(nuiDisponibles.get(0));
        LOG.info("Nui Asignado " + nuiAsignado);
       

        return nuiDisponibles.get(0).getIdentificadorNUI();
    }

    /**
     * Metodo que permite cambiar el estatus de un paquete cuando este ya no
     * posee Nui disponibles
     *
     * @param PaqueteNui Objeto de tipo PaqueteNui que contiene la informacion
     * de un paquete Nui
     * @return true si el estatus del paquete fue actualizado y en caso
     * contrario false
     */
    @Transactional
    private boolean cerrarPaquete(PaqueteNuiEntidad paqueteNui) {
        LOG.info("Cerrando paquete========== " + paqueteNui.getId());
        boolean resp;
        Long paqueteEstatus = Long.parseLong(buscarValorProperties(ESTATUS_PAQUETE_CERRADO));
        PaqueteNuiEstatusEntidad estatusEntidad = paqueteNuiEstatusRepository.findOne(paqueteEstatus);
        paqueteNui.setPaqueteNuiEstatus(estatusEntidad);
        try {
            paqueteNuiRepository.save(paqueteNui);
            resp = true;
        } catch (Exception e) {
            LOG.error("Error actualizando estatus del paquete Nui.", e);
            return false;
        }
        return resp;

    }

    /**
     * Metodo que permite cambiar el estatus de un Nui cuando este va a ser
     * asignado
     *
     * @param nui Objeto de tipo Nui que contiene la informacion de un Nui
     * @return true si el estatus del Nui fue actualizado y en caso contrario
     * false
     */
    @Transactional
    private boolean cambiarEstatusNui(NuiEntidad nui) {
        LOG.info("Actualizando estatus de Nui ========== " + nui.getId());
        boolean resp;
        Long nuiEstatus = Long.parseLong(buscarValorProperties(ESTATUS_NUI_ASIGNADO));
        NuiEstatusEntidad estatusEntidad = nuiEstatusRepository.findOne(nuiEstatus);
        nui.setNuiEstatus(estatusEntidad);
        try {
            nuiRepository.save(nui);
            resp = true;
        } catch (Exception e) {
            LOG.error("Error actualizando estatus del Nui.", e);
            return false;
        }
        return resp;

    }

    /**
     * Metodo que permite buscar el valor de una propiedad en el archivo
     * properties del servicio libro
     *
     * @param clave String Propiedad a buscar en ela rchivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) {

        String valor = "";
        //lee properties
        LOG.info("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            LOG.info("Error leyendo properties: " + ex);
        }
        LOG.info("buscarValorProperties --> valor --> " + valor);

        return valor;
    }

    /**
     * Busca un NUI segun el numero de documento de identidad
     *
     * @param numeroDocIdentidad String numero del documento de identidad
     * @return true si existe el ecu y en caso contrario false
     */
    @Override
    public boolean buscarExistenciaNUI(String numeroDocIdentidad) {
        boolean resp = false;
        String nui;

        LOG.info("Documento de identidad " + numeroDocIdentidad);
        CiudadanoEntidad ciudadanoEntidad = ciudadanoRepository.findByNumeroDocIdentidad(numeroDocIdentidad);
        if (ciudadanoEntidad == null) {
            LOG.error("ERROR: consultando el numero de documento de identidad- no existe");
            throw new ResourceNotFoundException("Ciudadano no encontrado");
        }
        nui = ciudadanoEntidad.getCodigoNUI();
        if (nui != null) {
            resp = true;
        }
        return resp;
    }

}
