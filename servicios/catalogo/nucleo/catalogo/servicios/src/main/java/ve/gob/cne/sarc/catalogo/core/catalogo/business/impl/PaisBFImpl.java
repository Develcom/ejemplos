package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.PaisBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.PaisMapper;
import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.PaisRepository;


 /**
 * PaisBFImpl.java
 * @descripcion Implementacion del catalogo Pais
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
@Component
public class PaisBFImpl implements PaisBF {

    private static final Logger LOG = LoggerFactory.getLogger(PaisBFImpl.class);

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PaisMapper paisMapper;

    /**
     * Metodo responsable de listar los Paises
     *
     * @return Lista de {@link Pais}
     */
    @Transactional
    @Override
    public List<Pais> consultarTodos() {
        LOG.debug("====INICIANDO PaisBFImpl.ConsultarTodos======");
        List<PaisEntidad> listaPaisesEntidad;
        listaPaisesEntidad = (List<PaisEntidad>) paisRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return paisMapper.entitiesToBOs(listaPaisesEntidad);
    }

}
