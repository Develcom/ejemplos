package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.LibroEntidad;

/**
 * LibroRepository.java
 *
 * @descripcion Clase Repositorio de la entidad LibroEntidad
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public interface LibroRepository extends CrudRepository<LibroEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorCodigoOficinaEstatusAnio que permite consultar todos los datos de un
     * LibroEntidad
     * @param idOficina long codigoOfic de Libro
     * @param idEstatus long estatus de Libro
     * @param numeroAnio int anio de Libro
     * @return List<LibroEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Libro.
     */
    List<LibroEntidad> findByOficinaIdAndEstatusIdAndNumeroAnio(@Param("idOficina") long idOficina,
            @Param("idEstatus") long idEstatus, @Param("numeroAnio") int numeroAnio);

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorCodigoOficinaEstatusAnio que permite consultar todos los datos de un
     * LibroEntidad
     * @author Oscar Montilla
     * @param nombreOficina String nombre de Oficina.
     * @param nombreEstatus String nombre de Libro
     * @param anio String anio de Libro
     * @return List<LibroEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Libro.
     */
    List<LibroEntidad> buscarPorCodigoOficinaEstatusAnio(@Param("nombreOficina") String nombreOficina,
            @Param("nombreEstatus") String nombreEstatus, @Param("anio") int anio);

    /**
     *
     * @metodo Metodo de acceso a datos findByOficinaId que permite consultar todos los datos de un LibroEntidad
     * @param id Long codigoOfic de Libro
     * @return List<LibroEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Libro.
     */
    List<LibroEntidad> findByOficinaId(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByOficinaNombre que permite consultar todos los datos LibroEntidad por el
     * nombre de oficina
     * @param nombre string Nombre de
     * @return LibroEntidad - Lista de Objetos del modelo ontologico que contiene la informacion de Libro.
     */
    List<LibroEntidad> findByOficinaNombre(@Param("nombre") String nombre);

    /**
     * @metodo Metodo de acceso findByOficinaNombreAndEstatusNombreAndNumeroAnio que permite consultar todos los datos
     * de Libro por el nombre de oficina, nombre de estatus y numero de año del libro
     * @param nombreOficina String Nombre de Oficina
     * @param nombreEstatus String Nombre de estatus de libro
     * @param numeroAnio int Numero de año del libro
     * @return List<LibroEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Libro.
     */
    List<LibroEntidad> findByOficinaNombreAndEstatusNombreAndNumeroAnio(@Param("nombreOficina") String nombreOficina,
            @Param("nombreEstatus") String nombreEstatus, @Param("numeroAnio") int numeroAnio);

    /**
     * @metodo Metodo de acceso findByOficinaNombreAndEstatusNombreAndNumeroAnio que permite consultar todos los datos
     * de Libro por el codigo de oficina, id de estatus, id de tipo libro y numero de año del libro
     * @param codigo String codigo identificador de Oficina
     * @param idEstatus Long id de Estatus
     * @param idTipoLibro Long id de Tipo Libro
     * @param numeroAnio String Numero de Año de libro
     * @return List<LibroEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Libro.
     */
    List<LibroEntidad> findByOficinaCodigoAndEstatusIdAndTipoLibroIdAndNumeroAnio(@Param("codigo") String codigo,
            @Param("idEstatus") long idEstatus, @Param("idTipoLibro") long idTipoLibro,
            @Param("numeroAnio") int numeroAnio);

}
