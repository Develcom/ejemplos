package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;

/**
 * FuncionarioRepository.java
 *
 * @descripcion Clase Repositorio de la entidad FuncionarioEntidad
 * @version 1.0 12/08/2016
 * @author Anabell De Faria
 */
public interface FuncionarioRepository extends
        CrudRepository<FuncionarioEntidad, Long> {

    /**
     * @metodo Metodo de acceso a datos buscarPorCedula que permite consultar los datos de un FuncionarioEntidad
     * @param cedula String Numero de la cedula del Funcionario.
     * @return ParticipanteEntidad - Objeto del modelo ontologico que contiene la informacion de Funcionario.
     */
    public FuncionarioEntidad buscarPorCedula(@Param("cedula") String cedula);

    /**
     * @metodo Metodo de acceso a datos
     * findByOficinasFuncionariosOficinaCodigoAndOficinasFuncionariosTipoFuncionarioCodigoTipoFuncionario que permite
     * consultar los datos de un FuncionarioEntidad
     *
     * @param idOficina Long id de Oficina.
     * @param idTipoFuncionario Long id de tipo funcionario.
     * @return ParticipanteEntidad - Objeto del modelo ontologico que contiene la informacion de Funcionario.
     */
    FuncionarioEntidad
            findByOficinasFuncionariosOficinaIdAndOficinasFuncionariosTipoFuncionarioId(@Param("idOficina") Long idOficina, @Param("idTipoFuncionario") Long idTipoFuncionario);

    /**
     * @metodo Metodo de acceso de datos a findById, que obtiene la informacion de Funcionario por medio de id
     * @param id consecutivo de Funcionario.
     * @autor Oscar Montilla
     * @return FuncionarioEntidad - Objeto del modelo ontologico que contiene la informacion de Funcionario.
     */
    FuncionarioEntidad findById(@Param("id") Long id);

    /**
     * @metodo metodo de acceso a datos findByNombre, que obtiene la informacion de Funcioario por el nombre
     * @param primerNombre String nombre de Funcionario
     * @return FuncionarioEntidad - Objeto del modelo ontologico que contiene la informacion de Funcionario.
     */
    FuncionarioEntidad findByPrimerNombre(@Param("primerNombre") String primerNombre);

    /**
     * @metodo metodo de acceso a datos
     * findByOficinasFuncionariosOficinaNombreAndOficinasFuncionariosTipoFuncionarioNombre, que obiene la informacion de
     * un Funcionario por el nombre de oficina y el nombre de tipo funcionario
     * @param oficinaNombre String nombre de Oficina
     * @param tipoFuncionarioNombre String nombre de Tipo Funcionario
     * @return FuncionarioEntidad - Objeto del modelo ontologico que contiene la informacion de Funcionario.
     */
    FuncionarioEntidad
            findByOficinasFuncionariosOficinaNombreAndOficinasFuncionariosTipoFuncionarioNombre(
                    @Param("oficinaNombre") String oficinaNombre,
                    @Param("tipoFuncionarioNombre") String tipoFuncionarioNombre);

    /**
     * .
     * @metodo Metodo de acceso a datos findByCedula
     * @param cedula String cedula del Funcionario
     * @return FuncionarioEntidad - Objeto del modelo ontologico que contiene la informacion de Funcionario.
     */
    FuncionarioEntidad findByCedula(@Param("cedula") String cedula);

}
