package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.ModuloBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.ModuloMapper;
import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaModuloEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoOficinaModuloRepository;
 /**
 * ModuloBFImpl.java
 * @descripcion Implementacion del catalogo Modulo
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */  
 
@Component
public class ModuloBFImpl implements ModuloBF {

    private static final Logger LOG = LoggerFactory.getLogger(ModuloBFImpl.class);

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private TipoOficinaModuloRepository tipoOficinaModuloRepository;

    @Autowired
    private ModuloMapper moduloMapper;

    /**
     * 
     * Metodo responsable de listar los Modulos dada un Tipo de Oficina
     *
     * @param codigo String codigo de tipo de oficina
     * @return Lista de {@link Modulo}
     */
    @Override
    public List<Modulo> consultarModuloPorOficina(String codigo) {

        LOG.info("=====INICIANDO ModuloBFImpl.ConsultarModuloPorOficina==========");

        List<TipoOficinaModuloEntidad> listTipoOficinaEntidad = null;
        OficinaEntidad oficEntidad = oficinaRepository.findByCodigo(codigo);

        Long idTipoOfc;

        if (oficEntidad != null) {
            idTipoOfc = oficEntidad.getTipoOficina().getId();
            listTipoOficinaEntidad = tipoOficinaModuloRepository.findByTipoOficina_IdOrderByModulo_IdAsc(idTipoOfc);
        }

        return moduloMapper.entitiesToBOs(listTipoOficinaEntidad);
    }

}
