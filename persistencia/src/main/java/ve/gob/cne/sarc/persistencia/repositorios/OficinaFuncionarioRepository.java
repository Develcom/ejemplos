package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;

/**
 * OficinaFuncionarioRepository.java
 *
 * @descripcion Clase Repositorio de la entidad OficinaFuncionarioEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface OficinaFuncionarioRepository extends CrudRepository<OficinaFuncionarioEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findByFuncionarioCedula que permite consultar Oficina Funcionario.
     * @param cedula String numeroDocIdentidad Numero de documento de identidad de ciudadano.
     * @return OficinaFuncionarioEntidad - objetos del modelo ontologico que contiene la informacion de Oficina
     * Funcionario.
     */
    OficinaFuncionarioEntidad findByFuncionarioCedula(@Param("cedula") String cedula);

    /**
     * @metodo metodo de acceso a datos
     * findByOficinaCodigoAndTipoFuncionarioCodigoTipoFuncionarioAndOficFuncEstatusCodOficFuncEstatus que permite
     * consultar por id de oficina,id tipo funcionario y id funcionario estatus
     * @param id Long de Oficina
     * @param idTipoFuncionario Long de tipo Funcionarios
     * @param idOficFuncEstatus Long de Estatus de oficina
     * @return OficinaFuncionarioEntidad - objeto del modelo ontologico que contiene la informacion de Oficina
     * Funcionario
     */
    OficinaFuncionarioEntidad
            findByOficinaIdAndTipoFuncionarioIdAndOficFuncEstatusId(@Param("id") Long id,
                    @Param("idTipoFuncionario") Long idTipoFuncionario,
                    @Param("idOficFuncEstatus") Long idOficFuncEstatus);

    /**
     * @metodo metodo de acceso a datos findByOficinaNombreAndTipoFuncionarioNombreAndOficFuncEstatusNombre que permite
     * consultar por nombre de oficina,nombre tipo funcionario y nombre funcionario estatus
     * @param nombreOficina String Nombre de Oficina
     * @param nombreTipoFuncionario String Nombre de Tipo Funcionario
     * @param nombreEstatus String Nombre de OficFuncEstatus
     * @return OficinaFuncionarioEntidad - objeto del modelo ontologico que contiene la informacion de Oficina
     * Funcionario
     */
    OficinaFuncionarioEntidad
            findByOficinaNombreAndTipoFuncionarioNombreAndOficFuncEstatusNomOficFuncEstatus(
                    @Param("nombreOficina") String nombreOficina,
                    @Param("nombreTipoFuncionario") String nombreTipoFuncionario,
                    @Param("nomOficFuncEstatus") String nomOficFuncEstatus);

}
