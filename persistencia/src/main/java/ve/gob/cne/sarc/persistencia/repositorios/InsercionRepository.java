package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.InsercionEntidad;

/**
 * InsercionRepository.java
 *
 * @descripcion Repositorio de la entidad InsercionEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface InsercionRepository
        extends CrudRepository<InsercionEntidad, Long> {

    /**
     * @metodo Metodo de acceso a datos findByActaNumeroActa que permite consultar los datos de un InsercionEntidad por
     * el numero del Acta
     * @param numeroActa String Numero de Acta
     * @return InsercionEntidad - Objeto del modelo Ontologico que contiene la informacion de Insercion
     */
    public InsercionEntidad findByActaNumeroActa(@Param("numeroActa") String numeroActa);

}
