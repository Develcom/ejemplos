package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.MudanzaOurcEntidad;

/**
 * Clase Repositorio de la entidad MudanzaOurcEntidad
 * @author carlos.castillo
 *
 */
public interface MudanzaOurcRepository extends CrudRepository<MudanzaOurcEntidad, Long>{
	 /**
     *@Metodo Metodo para buscar un traslado de una oficina por su codigo
     * @author carlos.castillo
     * @param numero Numero de la Oficina.
     * @return MudanzaOurcEntidad
     * @author carlos.castillo
     *
     */
    public MudanzaOurcEntidad buscarPorCodigo(@Param("codigo") String numero);
    
    /**
     * @Metodo Metodo para buscar un traslado de una oficina por su ID
     * @param id ID del traslado
     * @return MudanzaOurcEntidad
     * @author carlos.castillo
     */
    public MudanzaOurcEntidad findById(@Param("Id") long id);
    
    /**
     * @Metodo Metodo para buscar el ID de la oficina nueva a la que se traslado una oficina vieja
     * @param nombre el nombre de la oficina vieja
     * @return MudanzaOurcEntidad
     * @author carlos.castillo
     */
    public MudanzaOurcEntidad buscarOurcNueva(@Param("nombre") String nombre);
}
