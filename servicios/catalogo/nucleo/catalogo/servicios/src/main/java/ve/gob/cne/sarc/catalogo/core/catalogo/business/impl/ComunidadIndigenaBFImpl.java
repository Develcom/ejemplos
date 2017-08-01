package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.ComunidadIndigenaBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.ComunidadIndigenaMapper;
import ve.gob.cne.sarc.comunes.catalogo.ComunidadIndigena;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.ComunidadIndigenaRepository;

  /**
 * ComunidadIndigenaBFImpl.java
 * @descripcion Implementacion del catalogo Comunidad Indigena
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Component
public class ComunidadIndigenaBFImpl implements ComunidadIndigenaBF {

    private static final Logger LOG = LoggerFactory.getLogger(ComunidadIndigenaBFImpl.class);

    @Autowired
    private ComunidadIndigenaRepository comunidadIndigenaRepository;

    @Autowired
    private ComunidadIndigenaMapper comunidadIndigenaMapper;

    /**
     * Metodo responsable de listar las Comunidades Indigenas
     *
     * @return Lista de {@link ComunidadIndigena}
     */
    @Override
    public List<ComunidadIndigena> consultarTodos() {
        LOG.debug("=====INICIANDO ComunidadIndigenaBFImpl.ConsultarTodos==========");
        List<ComunidadIndigenaEntidad> listaComunidadIndigenaEntidad;
        listaComunidadIndigenaEntidad = comunidadIndigenaRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return comunidadIndigenaMapper.entitiesToBos(listaComunidadIndigenaEntidad);
    }
}
