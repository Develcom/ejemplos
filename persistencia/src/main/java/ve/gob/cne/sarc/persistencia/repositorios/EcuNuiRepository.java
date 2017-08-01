package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.EcuNuiEntidad;

/**
 * EcuNuiRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TomoEntidad
 * @version 1.0 20/12/2016
 * @author Oscar Montilla
 *
 */
public interface EcuNuiRepository extends CrudRepository<EcuNuiEntidad, Long> {

    /**
     *
     * @metodo Metodo que busca una lista de EcuNuiEntidad basado en la letra de la nacionalidad
     * @param letraNacionalidad String letraNacionalidad de EcuNui
     * @return List<EcuNuiEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de EcuNui.
     */
    List<EcuNuiEntidad> findByLetraNacionalidad(@Param("letraNacionalidad") String letraNacionalidad);

}
