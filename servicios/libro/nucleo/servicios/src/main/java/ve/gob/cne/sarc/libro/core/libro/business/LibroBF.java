package ve.gob.cne.sarc.libro.core.libro.business;

import java.util.Date;
import java.util.List;

import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.comunes.oficina.Tomo;
import ve.gob.cne.sarc.comunes.plantilla.Acta;

/**
 * LibroBF.java
 *
 * @descripcion BusinessFacade con la logica de negocio de manejo de Libro
 * @version 1.0 10/10/2015
 * @author Oscar Eubieda
 */
public interface LibroBF {

    /**
     * Método consultarTodos que permite obtener la colección de libros dado un codigo de oficina
     *
     * @param codigoOfic String que contiene el codigo de la oficina a consultar
     * @return Colección de objetos <tt>Libro</tt>
     */
    List<Libro> consultarTodos(String codigoOfic);

    /**
     * Método buscarLibrosEstatusPorTipoOficina que permite recuperar la lista de libros por oficina, estatus y anio
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @param estatus long id del estatus de libros a consultar.
     * @param anio el año de libros a consultar.
     * @return Colección de objetos <tt>Libro</tt> en formato <tt>JSON</tt>.
     */
    List<Libro> buscarLibrosEstatusPorTipoOficina(String codigoOfic, long estatus, int anio);

    /**
     * Método validarLibrosActivosPorOficina que permite validar los libros para una oficina, anio, estatus y tipo de
     * libro dado
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @param estatus long id del estatus de libros a consultar.
     * @param anio el año de libros a consultar.
     * @return <tt>boolean</tt> que indica la validez del libro, en formato <tt>JSON</tt>.
     */
    boolean validarLibrosActivosPorOficina(String codigoOfic, long estatus, int anio);

    /**
     * Metodo gestionarLibro que permite crear o actualizar un libro
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @return <tt>boolean</tt> que indica si la operación de ha completado con éxito, en formato <tt>JSON</tt>.
     */
    boolean gestionarLibro(Libro libro);

    /**
     * Metodo aperturaCierreLibro que permite aperturar o cerrar el Libro segun sea el caso
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @return <tt>boolean</tt> que indica si la operación de ha completado con éxito, en formato <tt>JSON</tt>.
     */
    boolean aperturaCierreLibro(Libro libro);

    /**
     * Metodo actualizarLibroCivil que permite actualizar el Libro Civil
     *
     * @param acta objeto <tt>Acta</tt> en formato <tt>JSON</tt>.
     * @return <tt>boolean</tt> que indica si la operación de ha completado con éxito, en formato <tt>JSON</tt>.
     */
    boolean actualizarLibroCivil(Acta acta);

    /**
     * Método validarTopeTomo que permite validar el tope del numero del Tomo para un tipo libro activo en un año y
     * oficina dada.recuperar la lista.
     *
     * @param codigoOfic String codigo de oficina.
     * @param tipoLibro long Id de tipo de libro.
     * @param anio integer año del libro.
     * @return Verdadero si el Numero del Tomo llego al tope establecido.
     */
    boolean validarTopeTomo(String codigoOfic, long tipoLibro, int anio);

    /**
     * Busca el codigo del tipo libro dado un codigo de tramite
     *
     * @param codigoTramite String codigo del tramite.
     * @return String codigo del Tipo Libro
     */
    String obtenerTipoLibro(String codigoTramite);

    /**
     * Busca el tomo actual dado el codigo de la oficina, el año, el tipo libro y estatus.
     *
     * @param codigoOfic String codigo de la oficina a consultar
     * @param estatus long Id de estatus a consultar
     * @param anio int año del libro a consultar
     * @param idTipoLibro long id del tipo libro a consultar
     * @return Objeto tipo Tomo que contiene la informacion del tomo consultado
     */
    Tomo buscarTomoActual(String codigoOfic, long estatus, int anio, long idTipoLibro);

    /**
     * Valida el tope de numeros de acta por tomo dado un codigo de oficina, un codigo de tipo libro, un año y un tomo.
     *
     * @param codOficina String codigo de la oficina
     * @param idTipoLibro long Id del tipo libro
     * @param anio int año del libro
     * @param numeroTomo String numero de tomo a validar
     * @return Verdadero si el numero de actas por tomo dado una oficina, tipo libro, año y tomo
     */
    boolean validarActasPorTomo(String codOficina, long idTipoLibro, int anio, String numeroTomo);

    /**
     * Crea libro diario dado el n&uacute;mero de solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @return Verdadero si cre&oacute; satisfactoriamente el libro diario, en caso contrario devuelve falso
     */
    boolean crearLibroDiario(String numeroSolicitud);
    
    /**
     * Actualiza el libro diario dado un n&uacute;mero de solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @param codigoEstatus String Codigo del estatus de la solicitud
     * @return Verdadero si hizo la actualizaci&oacute;n satisfactoriamente, en caso contrario devuelve falso
     */
    boolean actualizarLibroDiario(String numeroSolicitud, String codigoEstatus);
    
     /**
     * Método validarLibroDiarioActivoPorOficina que permite validar el libro diario para una oficina, dia y 
     * estatus 
     * @param codOficina el codigo de oficina a consultar.
     * @param fecha fecha del libro diario a consultar.
     * @param estatus estatus del libro diario a consultar.
     * @return <tt>boolean</tt> que indica la validez del libro, en formato <tt>JSON</tt>.
     */
    boolean validarLibroDiarioActivoPorOficina (String codOficina, String fecha, long estatus);
    
}
