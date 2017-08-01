package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.InhumacionYCremacionEntidad;

/**
 * InhumacionYCremacionRepository.java
 *
 * @descripcion Clase Repositorio de la entidad InhumacionYCremacion
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface InhumacionYCremacionRepository extends
        CrudRepository<InhumacionYCremacionEntidad, Long> {

    /**
     *
     * @metodo Metodo que busca un String de buscarPoridInhumacionYCremacion basado en el nombre de la oficina
     * @param codigo String codigo de Oficina
     * @String - Retorna un objeto Max(codigoOficina)
     */
    String buscarPorCodigoInhumacionYCremacion(@Param("codigo") String codigo);

    /**
     *
     * @metodo Metodo que busca un String de findByTramiteSolicitudes basado en la oficina de la InhumacionYCremacion
     * @param numero String id de Oficina
     * @String - Retorna un objeto Max(InhumacionYCremacion)
     */
    public InhumacionYCremacionEntidad findBySolicitudNumero(@Param("numero") String numero);

    /**
     *
     * @metodo Metodo que busca findBySolicitudId basado en la solicitud de la InhumacionYCremacion }
     * @param id Long de solicitud
     * @return InhumacionYCremacionEntidad
     */
    public InhumacionYCremacionEntidad findBySolicitudId(@Param("id") long id);

    /**
     *
     * @param nombre String Nombre de Oficina
     * @return InhumacionYCremacionEntidad - Objeto del modelo ontologico que contiene la informacion e una Inhumacion Y
     * Cremacion
     */
    InhumacionYCremacionEntidad findBySolicitudOficinaFuncionarioOficinaNombre(@Param("nombre") String nombre);
}
