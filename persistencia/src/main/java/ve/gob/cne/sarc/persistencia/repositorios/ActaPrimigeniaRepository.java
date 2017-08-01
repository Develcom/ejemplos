package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.ActaPrimigeniaEntidad;

/**
 * ActaPrimigeniaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ActaPrimigeniaEntidad
 * @version 1.0 11/10/2016
 * @author Oscar Montilla
 */
public interface ActaPrimigeniaRepository extends CrudRepository<ActaPrimigeniaEntidad, Long> {

    /**
     * @Metodo Metodo de acceso a datos findBySolicitudNumero que obtiene la información de una Acta Primigenia por el
     * numero de solicitud
     * @param numero String Numero de solicitud
     * @return ActaPrimigeniaEntidad - Objeto del modelo ontologico que contiene la informacion de Acta Primigenia.
     */
    ActaPrimigeniaEntidad findBySolicitudNumero(@Param("numero") String numero);

}
