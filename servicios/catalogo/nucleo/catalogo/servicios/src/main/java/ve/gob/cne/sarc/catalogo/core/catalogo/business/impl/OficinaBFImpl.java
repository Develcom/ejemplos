package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.OficinaBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.OficinaMapper;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ConsecutivoOficinaRepository;

/**
 * OficinaBFImpl.java
 *
 * @descripcion Implementacion del catalogo Ocupacion
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Component
public class OficinaBFImpl implements OficinaBF {

    private static final Logger LOG = LoggerFactory.getLogger(OficinaBFImpl.class);

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private OficinaMapper oficinaMapper;

    @Autowired
    private ConsecutivoOficinaRepository consecutivoOficinaRepository;

    /**
     *
     * Metodo responsable de listar las Oficinas
     *
     * @return Lista de {@link Oficinas}
     */
    @Transactional
    @Override
    public List<Oficina> consultarTodos() {
        LOG.debug("====INICIANDO OficinaBFImpl.ConsultarTodos======");
        List<OficinaEntidad> listaOficinasEntidad;
        listaOficinasEntidad = (List<OficinaEntidad>) oficinaRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return oficinaMapper.entitiesToBOs(listaOficinasEntidad);
    }

    /**
     * Metodo responsable de proximo nro consecutivo
     *
     * @return long proximo nro consecutivo
     */
    @Override
    public long proximoNroConsecutivo(String codigoOficina) {
        LOG.debug("====INICIANDO OficinaBFImpl.proximoNroConsecutivo======");
        OficinaEntidad oficinaEntidad;
        long nroConsecutivo;

        oficinaEntidad = oficinaRepository.findByCodigo(codigoOficina);
        if (oficinaEntidad == null) {
            LOG.error("ERROR: consultando oficina- codigo '" + codigoOficina + "' no existe");
            throw new ResourceNotFoundException("Oficina no encontrada");
        }

        nroConsecutivo = new Random().nextInt(90000) + 1000L;
        LOG.info("Proximo nro consecutivo" + nroConsecutivo);
        return nroConsecutivo;
    }

}
