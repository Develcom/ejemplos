package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.EstatusTramiteEntidad;

/**
 * EstatusTramiteRepository.java
 *
 * @descripcion Clase Repositorio de la entidad EstatusTramiteEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface EstatusTramiteRepository extends
        CrudRepository<EstatusTramiteEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findByEstatusIdAndTramiteId que permite consultar todos los datos de un
     * EstatusTramiteEntidad
     * @param idEstatus Long id del estatus del tramite.
     * @param idTramite Long id del tramite.
     * @return EstatusTramiteEntidad - Lista de Objetos del modelo ontologico que contiene la informacion de Tramite.
     */
    public EstatusTramiteEntidad findByEstatusIdAndTramiteId(
            @Param("idEstatus") Long idEstatus,
            @Param("idTramite") Long idTramite);

    /**
     *
     * @metodo Metodo de acceso a datos findByEstatusIdAndTramiteCodigo que permite consultar todos los datos de un
     * EstatusTramiteEntidad
     * @param nombre String nombre del estatus del tramite.
     * @param codigo String Codigo del tramite.
     * @return EstatusTramiteEntidad - Lista de Objetos del modelo ontologico que contiene la informacion de Tramite.
     */
    public EstatusTramiteEntidad findByEstatusNombreAndTramiteCodigo(
            @Param("nombre") String nombre,
            @Param("codigo") String codigo);
    
    /**
    *
    * @metodo Metodo de acceso a datos findByEstatusIdAndTramiteCodigo que permite consultar todos los datos de un
    * EstatusTramiteEntidad
    * @param id long id del estatus del tramite.
    * @param codigo String Codigo del tramite.
    * @return EstatusTramiteEntidad - Lista de Objetos del modelo ontologico que contiene la informacion de Tramite.
    */
   public EstatusTramiteEntidad findByEstatusIdAndTramiteCodigo(
           @Param("id") long nombre,
           @Param("codigo") String codigo);

}
