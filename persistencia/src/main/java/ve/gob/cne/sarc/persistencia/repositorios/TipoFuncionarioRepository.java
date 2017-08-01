package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TipoFuncionarioEntidad;

/**
 * TipoFuncionarioRepository.java
 *
 * @Descripcion Clase Repositorio de la entidad TipoFuncionarioEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TipoFuncionarioRepository extends CrudRepository<TipoFuncionarioEntidad, Long> {

    /**
     * @metodo Metodo de acceso findById para buscar un objeto TipoFuncionario por codigo
     * @param id Long Identificador de tipo Funcionario
     * @return TipoFuncionarioEntidad - objeto del modelo ontologico que contiene la informacion de
     * TipoFuncionarioEntidad.
     */
    TipoFuncionarioEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso findByNombre para buscar un objeto de Tipo Funcionario por el Nombre
     * @param nombre String Nombre de tipo Funcionario
     * @return TipoFuncionarioEntidad - objeto del modelo ontologico que contiene la informacion de
     * TipoFuncionarioEntidad.
     */
    TipoFuncionarioEntidad findByNombre(@Param("nombre") String nombre);

}
