package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.EstadoBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.EstadoMapper;
import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.EstadoRepository;
 /**
 * EstadoBFImpl.java
 * @descripcion Implementacion del catalogo Estado
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
@Component
public class EstadoBFImpl implements EstadoBF {

    private static final Logger LOG = LoggerFactory.getLogger(EntePublicoBFImpl.class);

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoMapper estadoMapper;

    /**
     *
     * Metodo responsable de listar los Estados
     *
     * @return Lista de {@link Estado}
     */
    @Transactional
    @Override
    public List<Estado> consultarTodos() {
        LOG.debug("=====INICIANDO EstadoBFImpl.consultarTodos==========");
        List<EstadoEntidad> listaEstadoEntidad;
        listaEstadoEntidad = (List<EstadoEntidad>) estadoRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return estadoMapper.entitiesToBOs(listaEstadoEntidad);
    }

    /**
     * 
     * Metodo responsable de listar los Estados dado un codigo de Pais
     *
     * @param codigo String codigo de pais
     * @return Lista de {@link Estado}
     */
    @Override
    public List<Estado> consultarPorPais(Long codigo) {
        LOG.debug("=====INICIANDO EstadoBFImpl.consultarPorPais==========");
        List<EstadoEntidad> listaEstadoEntidad;
        listaEstadoEntidad = estadoRepository.findByPaisIdOrderByNombreAsc(codigo);
        return estadoMapper.entitiesToBOs(listaEstadoEntidad);
    }
}
