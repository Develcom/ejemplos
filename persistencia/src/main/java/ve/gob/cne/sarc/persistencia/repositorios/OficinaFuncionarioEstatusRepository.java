package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEstatusEntidad;

/**
 * OficinaFuncionarioEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad OficinaFuncionarioEstatusEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface OficinaFuncionarioEstatusRepository extends
        CrudRepository<OficinaFuncionarioEstatusEntidad, Long> {

    /**
     * @metodo metodo de acceso findById para obtener un objeto de oficina Funcionario Estatus por medio del codigo
     * @autor Oscar Montilla
     * @param id Long id de Oficina funcionario Estatus
     * @return OficinaFuncionarioEstatusEntidad - objeto del modelo Ontologico que contiene la informacion de Oficina
     * Funcionario Estatus
     */
    OficinaFuncionarioEstatusEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso findByNomOficFuncEstatus para obtener un objeto de Oficina Funcionario Estatus
     * @param nomOficFuncEstatus String Nombre de Oficina Funcionario Estatus
     * @return OficinaFuncionarioEstatusEntidad - objeto del modelo Ontologico que contiene la informacion de Oficina
     * Funcionario Estatus
     */
    OficinaFuncionarioEstatusEntidad findByNomOficFuncEstatus(@Param("nomOficFuncEstatus") String nomOficFuncEstatus);
    
    
}
