package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TomoEntidad;

/**
 * TomoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TomoEntidad
 * @version 1.0 12/12/2016
 * @author Oscar Montilla
 *
 */
public interface TomoRepository extends CrudRepository<TomoEntidad, Long> {

    /**
     * @metodo Metodo de Consulta que obtiene la informacion de Tomo mediante un libro en especifico
     * @param id Long id Identificador de libro
     * @return List<TomoEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de PaqueteNui.
     */
    List<TomoEntidad> findByLibroId(@Param("id") Long id);

}
