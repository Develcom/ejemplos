package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;

/**
 * TramiteRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TramiteEntidad
 * @version: 1.0 12/08/2016
 * @author: Anabell De Faria
 *
 */
public interface TramiteRepository
        extends CrudRepository<TramiteEntidad, Long> {

    /**
     *
     * @metodoMetodo de acceso a datos buscarPorCodigo que permite consultar los datos de un tramite por el codigo
     * @param codigo String codigo de Tramite
     * @return TramiteEntidad - Objeto del modelo ontologico que contiene la informacion de Tramite.
     */
    public TramiteEntidad buscarPorCodigo(@Param("codigo") String codigo);

    /**
     *
     * Metodo de acceso a datos buscarPorCodigo que permite consultar todos los tramites de un modulo
     *
     *
     * @param codigo String codigo de Tramite.
     * @return List<TramiteEntidad> - Objeto del modelo ontologico que contiene la informacion de Tramite.
     */
    public List<TramiteEntidad>
            buscarPorCodigoModulo(@Param("codigoModulo") String codigo);

}
