package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.NacionalidadBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.NacionalidadMapper;
import ve.gob.cne.sarc.comunes.catalogo.Nacionalidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.NacionalidadRepository;

  /**
 * NacionalidadBFImpl.java
 * @descripcion Implementacion del catalogo Nacionalidad
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */  
 
@Component
public class NacionalidadBFImpl implements NacionalidadBF {

    private static final Logger LOG = LoggerFactory.getLogger(NacionalidadBFImpl.class);

    @Autowired
    NacionalidadRepository nacionalidadRepository;

    @Autowired
    NacionalidadMapper nacionalidadMapper;

    /**
     * Metodo responsable de listar las Nacionalidades
     *
     * @return Lista de {@link Nacionalidad}
     */
    @Transactional
    @Override
    public List<Nacionalidad> consultarTodos() {
        LOG.debug("=====INICIANDO NacionalidadBFImpl.consultarTodos==========");
        List<NacionalidadEntidad> listaNacionalidadEntidad;
        listaNacionalidadEntidad = (List<NacionalidadEntidad>) 
                nacionalidadRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return nacionalidadMapper.entitiesToBOs(listaNacionalidadEntidad);
    }
}
