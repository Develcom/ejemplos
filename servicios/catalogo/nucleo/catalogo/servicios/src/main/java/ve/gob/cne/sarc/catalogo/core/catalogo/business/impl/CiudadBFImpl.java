package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.CiudadBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.CiudadMapper;
import ve.gob.cne.sarc.comunes.catalogo.Ciudad;
import ve.gob.cne.sarc.persistencia.entidades.CiudadEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.CiudadRepository;


 /**
 * CiudadBFImpl.java
 * @descripcion Implementacion del catalogo Parroquia
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
 
@Component
public class CiudadBFImpl implements CiudadBF {

    private static final Logger LOG = LoggerFactory.getLogger(CiudadBFImpl.class);

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private CiudadMapper ciudadMapper;

    /**
     *
     * Metodo responsable de listar las Ciudades
     *
     * @return Lista de {@link Ciudad}
     */
    @Override
    public List<Ciudad> consultarTodos() {
        LOG.debug("=====INICIANDO CiudadBFImpl.ConsultarTodos==========");
        List<CiudadEntidad> listaCiudadEntidad;
        listaCiudadEntidad = ciudadRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return ciudadMapper.entitiesToBos(listaCiudadEntidad);
    }

}
