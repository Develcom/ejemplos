package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TipoDocIdentidadEntidad;

/**
 * TipoDocIdentidadRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TipoDocIdentidadEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TipoDocIdentidadRepository extends CrudRepository<TipoDocIdentidadEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById, que permite consultar los datos de un Tipo Documento de Identidad por
     * el id
     * @param id Long identificador de TipoDocIdentidadEntidad
     * @return TipoDocIdentidadEntidad - objeto con la informacion de la entidad TipoDocIdentidad
     */
    TipoDocIdentidadEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombreTipoDocIdentidad, que permite consultar los datos de un Tipo
     * Documento de Identidad por Nombre de tipo Documento de Indentidad
     * @param nombreTipoDocIdentidad
     * @return TipoDocIdentidadEntidad - objeto con la informacion de la entidad TipoDocIdentidad
     */
    TipoDocIdentidadEntidad findByNombreTipoDocIdentidad(@Param("nombreTipoDocIdentidad") String nombreTipoDocIdentidad);
}
