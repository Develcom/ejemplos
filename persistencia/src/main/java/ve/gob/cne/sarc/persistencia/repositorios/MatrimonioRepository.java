package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.MatrimonioEntidad;

/**
 * MatrimonioRepository.java
 *
 * @descripcion Clase Repositorio de la entidad MatrimonioEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 */
public interface MatrimonioRepository extends CrudRepository<MatrimonioEntidad, Long> {

    /**
     *
     * @descripcion Busca Matrimonio por su Id
     * @param id Log id de Matrimonio
     * @return MatrimonioEntidad - Objeto del modelo ontologico que contiene la informacion de Matrimonio.
     */
    public MatrimonioEntidad findById(@Param("id") Long id);

}
