package ve.gob.cne.sarc.ciudadano.core.ciudadano.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.*;
import ve.gob.cne.sarc.ciudadano.core.ciudadano.business.CiudadanoBF;
import ve.gob.cne.sarc.ciudadano.core.ciudadano.mapper.CiudadanoMapper;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.CiudadanoRepository;

/**
 * CiudadanoBFImpl.java
 * @descripcion [Implementacion del BusinessFacade con la logica de negocio de manejo de ciudadanos]
 * @version 1.0 24/11/2015
 * @author Oscar Eubieda
 */
@Component
public class CiudadanoBFImpl implements CiudadanoBF {

    private static final Logger LOGGER = LoggerFactory.getLogger(CiudadanoBFImpl.class);

    @Autowired
    private CiudadanoMapper ciudadanoMapper;

    @Autowired
    private CiudadanoRepository ciudadanoRepository;

    /**
     * 
     * Metodo consultarPorNumeroDocumento que permite consultar un ciudadano segun el numero de documento.
     * 
     *
     * @param numeroDocIdentidad String que contiene el numero de documento de identidad del ciudadano
     * @return Ciudadano objeto del modelo ontologico que contiene la informacion del Ciudadano
     */
    @Override
    @Transactional
    public Ciudadano consultarPorNumeroDocumento(String numeroDocIdentidad) {
        LOGGER.debug("=====INICIANDO CiudadanoBFImpl.consultarPorNumeroDocumento==========");
        CiudadanoEntidad ciudadanoEntidad = ciudadanoRepository.findByNumeroDocIdentidad(numeroDocIdentidad);
        
        if (ciudadanoEntidad == null) {
            LOGGER.error("ERROR: consultando el documento asociado al Ciudadano - Ciudadano no existe");
            return new Ciudadano();
        }
        
        Ciudadano ciudadano = ciudadanoMapper.entityToBO(ciudadanoEntidad);
   
        LOGGER.debug("=====FINALIZADO CiudadanoBFImpl.consultarPorNumeroDocumento=========="
                + ciudadano.getPrimerNombre());
        return ciudadano;
    }

}
