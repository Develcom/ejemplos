package ve.gob.cne.sarc.ciudadano.core.ciudadano.business;

import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;

/**
 * CiudadanoBF.java
 * @descripcion [BusinessFacade con la logica de negocio de manejo de ciudadano]
 * @version 1.0 24/11/2015
 * @author Oscar Eubieda
 */
public interface CiudadanoBF {

    /**
     * Función que retorna el ciudadano por numero de documento
     *
     *
     * @param numeroDocumento String que contiene el numero de documento de identificacion del Ciudadano.
     * @return Ciudadano objeto del modelo ontologico que contiene la informacion del Ciudadano
     */
    public Ciudadano consultarPorNumeroDocumento(String numeroDocumento);
}
