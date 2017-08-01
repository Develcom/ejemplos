package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.EntePublicoEntidad;

/**
 * EntePublicoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad EntePublicoEntidad
 * @version: 1.0 12/08/2016
 * @author: Anabell De Faria
 *
 */
public interface EntePublicoRepository extends
        CrudRepository<EntePublicoEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorCodigo que permite consultar los datos de un ente publico por id
     * @param id Long Id del Ente publico.
     * @return EntePublicoEntidad - Objeto del modelo ontologico que contiene la informacion de Ente publico.
     *
     */
    public EntePublicoEntidad buscarPorCodigo(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un EntePublico
     * @param sort La variable para ordenar una Lista de Ente publico.
     * @return List<EntePublico> - Lista de Objetos del modelo ontologico que contiene la informacion de Ente publico.
     */
    List<EntePublicoEntidad> findAll(Sort sort);

    /**
     * @metodo Metodo de acceso findByNombre que permite consultar todos los datos de un EntePublico por su Nombre
     * @param nombre String Nombre de Ente Publico
     * @return EntePublicoEntidad - Objeto del modelo ontologico que contiene la informacion de Ente publico.
     */
    EntePublicoEntidad findByNombre(@Param("nombre") String nombre);
}
