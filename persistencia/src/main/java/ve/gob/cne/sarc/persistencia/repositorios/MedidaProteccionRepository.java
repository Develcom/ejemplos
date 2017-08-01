package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.MedidaProteccionEntidad;

/**
 * MedidaProteccionRepository.java
 *
 * @descripcion Clase Repositorio de la entidad MedidaProteccionEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface MedidaProteccionRepository extends CrudRepository<MedidaProteccionEntidad, Long> {

    /**
     * @metodo Busca una Medida de Proteccion por su Id
     * @param id Long id de registro de la tabla de Medida Proteccion.
     * @return	MedidaProteccionEntidad - Objeto del modelo ontologico que contiene la informacion de Medida proteccion.
     */
    public MedidaProteccionEntidad findById(@Param("id") long id);

    /**
     * @metodo Busca una Medida de Proteccion por su numeroMedida
     * @param numeroMedida String Numero Medida Proteccion
     * @return MedidaProteccionEntidad - Objeto del modelo ontologico que contiene la informacion de Medida proteccion.
     */
    MedidaProteccionEntidad findByNumeroMedida(@Param("numeroMedida") int numeroMedida);

    /**
     * @metodo Busca una Medida de Proteccion por id de procedencia
     * @param id Consecutivo de procedencia
     * @return MedidaProteccionEntidad - Objeto del modelo ontologico que contiene la informacion de Medida proteccion.
     */
    MedidaProteccionEntidad findByProcedenciaId(@Param("id") long id);

    /**
     * @metodo Metodo que busca una Medida de proteccion por el Numero de acta y el nombre de Tipo Procedencia
     * @param numeroActa String Numero de Acta
     * @param id Long Nombre de Tipo de Procedencia
     * @return MedidaProteccionEntidad - Objeto del modelo ontologico que contiene la informacion de Medida proteccion.
     */
    MedidaProteccionEntidad findByProcedenciaActaNumeroActaAndProcedenciaTipoProcedenciaId(
            @Param("numeroActa") String numeroActa,
            @Param("id") Long id);

}
