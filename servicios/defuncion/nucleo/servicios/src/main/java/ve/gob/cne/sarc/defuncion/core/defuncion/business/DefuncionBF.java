package ve.gob.cne.sarc.defuncion.core.defuncion.business;

import java.text.ParseException;

import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.comunes.defuncion.PermisoInhCre;



/**
 * DefuncionBF.java
 * @descripcion [Interfaz para la logica de negocio del controlador]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
public interface DefuncionBF {
    
    /**
     * Valida si el certificado medico de defuncion existe.
     *
     * @param numeroCertificado long que contiene el numero de certificado
     * medico
     * @return Verdadero si existe en caso contrario falso
     */
    public boolean validarCertificadoMedicoDefuncion(long numeroCertificado);
    
    /**
     *
     * Metodo guardarPermisoInhCre que permite guardar la informacion de permiso
     * inhumacion y cremacion.
     *
     * @param permisoInhCre objeto del modelo ontologico que contiene la
     * informacion de permiso inhumacion y cremacion
     * @return permiso objeto del modelo ontologico que contiene la informacion
     * de permiso inhumacion y cremacion
     */
    public PermisoInhCre guardarPermisoInhCre(PermisoInhCre permisoInhCre);
     
    /**
     * Metodo consultaPermisoInhCre que permite consultar el numero de permiso
     * inhumacion y cremacion.
     *
     * @param numeroSolicitud String que describe el numero de la solicitud
     * @return PermisoInhCre objeto del modelo ontologico que contiene la
     * informacion de permiso inhumacion y cremacion
     * @throws ParseException 
     * @throws
     * ve.gob.cne.sarc.defuncion.core.defuncion.exception.DefuncionException
     */
    public PermisoInhCre consultaPermisoInhCre(String numeroSolicitud) throws ParseException;
   /**
     * 
     * Metodo buscarProxNumPermiso que permite buscar el proximo numero de
     * permiso de inhumacion y cremacion.
     *
     * @param codOficina String describe el id de la Oficina
     * @return long con el proximo numero de permiso
     */
    public long buscarProxNumPermiso(String codOficina);
   
    /**
     * Metodo que permite guardar Defuncion
     *
     * @param defuncion pojo con la informacion de Defuncion
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     */
    boolean guardarDefuncion(Defuncion defuncion);
    /**
     * Metodo consultaDefuncion que permite consultar Defuncion
     *
     * @param numeroSolicitud String que describe el numero de la solicitud
     * @return Defuncion objeto del modelo ontologico que contiene la
     * informacion de Defuncion
     */
    public Defuncion consultarDefuncion(String numeroSolicitud);
}
