package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.MunicipioBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.MunicipioMapper;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.MunicipioRepository;

 /**
 * MunicipioBFImpl.java
 * @descripcion Implementacion del catalogo Municipio
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */  
 
@Component
public class MunicipioBFImpl implements MunicipioBF {

    private static final Logger LOG = LoggerFactory.getLogger(MunicipioBFImpl.class);

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private MunicipioMapper municipioMapper;

    /**
     * 
     * Metodo responsable de listar los Municipios
     *
     * @return Lista de {@link Municipio}
     */
    @Override
    @Transactional
    public List<Municipio> consultarTodos() {
        LOG.debug("=====INICIANDO MunicipioBFImpl.ConsultarTodos==========");
        List<MunicipioEntidad> listaMunicpioEntidad;
        listaMunicpioEntidad = municipioRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return municipioMapper.entitiesToBos(listaMunicpioEntidad);
    }

    /**
     * 
     * Metodo responsable de listar los Municipios dado un codigo de Estado
     *
     * @param codigo String codigo de estado
     * @return Lista de {@link Municipio}
     */
    @Override
    public List<Municipio> consultarPorEstado(Long codigo) {
        LOG.debug("=====INICIANDO EstadoBFImpl.consultarPorPais==========");
        List<MunicipioEntidad> listaMunicpioEntidad;
        listaMunicpioEntidad = municipioRepository.findByEstadoId(codigo);
        return municipioMapper.entitiesToBos(listaMunicpioEntidad);
    }

}
