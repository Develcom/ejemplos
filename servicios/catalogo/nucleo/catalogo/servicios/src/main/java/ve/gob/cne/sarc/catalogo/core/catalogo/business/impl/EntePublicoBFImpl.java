package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.EntePublicoBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.EntePublicoMapper;
import ve.gob.cne.sarc.comunes.catalogo.EntePublico;
import ve.gob.cne.sarc.persistencia.entidades.EntePublicoEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.EntePublicoRepository;

 /**
 * EntePublicoBFImpl.java
 * @descripcion Implementacion del catalogo Ente Publico
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
@Component
public class EntePublicoBFImpl implements EntePublicoBF {

    private static final Logger LOG = LoggerFactory.getLogger(EntePublicoBFImpl.class);

    @Autowired
    private EntePublicoRepository entePublicoRepository;

    @Autowired
    private EntePublicoMapper entePublicoMapper;

    /**
     * Metodo responsable de listar los Entes Publicos
     *
     * @return Lista de {@link EntePublico}
     */
    @Override
    public List<EntePublico> consultarTodos() {
        LOG.debug("=====INICIANDO EntePublicoBFImpl.consultarTodos==========");
        List<EntePublicoEntidad> listaEntePublicoEntidad;
        listaEntePublicoEntidad = (List<EntePublicoEntidad>) 
                entePublicoRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return entePublicoMapper.entitiesToBOs(listaEntePublicoEntidad);
    }
}
