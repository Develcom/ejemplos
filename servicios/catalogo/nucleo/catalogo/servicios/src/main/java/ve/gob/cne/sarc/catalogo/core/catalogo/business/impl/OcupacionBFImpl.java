package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.OcupacionBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.OcupacionMapper;
import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;
import ve.gob.cne.sarc.persistencia.entidades.OcupacionEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.OcupacionRepository;

  /**
 * OcupacionBFImpl.java
 * @descripcion Implementacion del catalogo Ocupacion
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */   
@Component
public class OcupacionBFImpl implements OcupacionBF {

    private static final Logger LOG = LoggerFactory.getLogger(NacionalidadBFImpl.class);

    @Autowired
    OcupacionRepository ocupacionRepository;

    @Autowired
    OcupacionMapper ocupacionMapper;

    /**
     * Metodo responsable de listar las Ocupaciones
     *
     * @return Lista de {@link Ocupacion}
     */
    @Override
    public List<Ocupacion> consultarTodos() {
        LOG.debug("=====INICIANDO OcupacionBFImpl.ConsultarTodos==========");
        List<OcupacionEntidad> listaOcupacionEntidad;
        listaOcupacionEntidad = (List<OcupacionEntidad>) 
                ocupacionRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return ocupacionMapper.entitiesToBOs(listaOcupacionEntidad);
    }
}
