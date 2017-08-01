package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.ParroquiaBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.ParroquiaMapper;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.ParroquiaRepository;

 /**
 * ParroquiaBFImpl.java
 * @descripcion Implementacion del catalogo Parroquia
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
@Component
public class ParroquiaBFImpl implements ParroquiaBF {

    private static final Logger LOG = LoggerFactory.getLogger(ParroquiaBFImpl.class);

    @Autowired
    private ParroquiaRepository parroquiaRepository;

    @Autowired
    private ParroquiaMapper parroquiaMapper;

    /**
     * Metodo responsable de listar las Parroquias
     *
     * @return Lista de {@link Parroquia}
     */
    @Override
    public List<Parroquia> consultarTodos() {

        LOG.debug("=====INICIANDO ParroquiaBFImpl.consultarTodos==========");
        List<ParroquiaEntidad> listaParroquiaEntidad;
        listaParroquiaEntidad = (List<ParroquiaEntidad>) 
                parroquiaRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return parroquiaMapper.entitiesToBOs(listaParroquiaEntidad);
    }

    /**
     * Metodo responsable de listar las Parroquias dado un codigo de Municipio
     *
     * @param codigo String codigo de municipio
     * @return Lista de {@link Parroquia}
     */
    @Override
    public List<Parroquia> consultarPorMunicipio(Long codigo) {
        LOG.debug("=====INICIANDO ParroquiaBFImpl.consultarPorMunicipio==========");
        List<ParroquiaEntidad> listaParroquiaEntidad;
        listaParroquiaEntidad = parroquiaRepository.findByMunicipioOrderByNombreAsc(codigo);
        return parroquiaMapper.entitiesToBOs(listaParroquiaEntidad);
    }
}
