package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.DiarioEstatusEntidad;

/**
 * DiarioEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad DiarioEstatusRepository
 * @version 1.0
 * @author Anabell De Faria
 *
 */
public interface DiarioEstatusRepository extends
        CrudRepository<DiarioEstatusEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findByCodigoDiarioEstatus que permite consultar todos los datos de un
     * DiarioEstatusRepository
     * @param id Long de Diario Estatus.
     * @return DiarioEstatusEntidad - objeto del modelo ontologico que pose la informacion de Diario Estatus
     */
    public DiarioEstatusEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombreDiarioEstatus que permite consultar todos los adtos por el nombre de
     * Diario estatus
     * @param nombre String Nombre de diario estatus
     * @return DiarioEstatusEntidad - objeto del modelo ontologico que pose la informacion de Diario Estatus
     */
    DiarioEstatusEntidad findByNombreDiarioEstatus(@Param("nombre") String nombre);

    /**
     *
     * @metodo Metodo de acceso a datos findByCodigoDiarioEstatus que permite consultar todos los datos de un
     * DiarioEstatusRepository
     * @author Oscar Montilla
     * @param codigo String codigo de Diario Estatus.
     * @return DiarioEstatusEntidad - objeto del modelo ontologico que pose la informacion de Diario Estatus
     */
    public DiarioEstatusEntidad findByCodigoDiarioEstatus(@Param("codigo") String codigo);

}
