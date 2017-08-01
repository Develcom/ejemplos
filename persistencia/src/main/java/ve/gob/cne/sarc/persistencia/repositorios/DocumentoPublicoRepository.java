package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.DocumentoPublicoEntidad;

/**
 * DocumentoPublicoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad DocumentoPublicoEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface DocumentoPublicoRepository extends CrudRepository<DocumentoPublicoEntidad, Long> {

    /**
     *
     * @metodo Clase Repositorio de la entidad DocumentoPublicoEntidad
     * @param id Long el id de registro de la tabla de Documento Publico.
     * @return DocumentoPublicoEntidad - Objeto del modelo ontologico que contiene la informacion de Documento Publico.
     *
     */
    public DocumentoPublicoEntidad findById(@Param("id") long id);
}
