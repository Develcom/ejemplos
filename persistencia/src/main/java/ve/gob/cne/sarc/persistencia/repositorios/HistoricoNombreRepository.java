package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.HistoricoNombreEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * Clase Repositorio de la entidad HistoricoNombreEntidad
 * @author carlos.castillo
 *
 */
public interface HistoricoNombreRepository extends CrudRepository<HistoricoNombreEntidad, Long> {
	 /**
     *@Metodo Metodo para buscar un nombre en el historico de nombres
     * @author carlos.castillo
     * @param nbViejo nombre viejo de la oficina
     * @return HistoricoNombreEntidad.
     *
     */
    public HistoricoNombreEntidad buscarPorNombre(@Param("nbViejo") String nbViejo, @Param("tipoOficina") TipoOficinaEntidad tipoOficina);
    
    /**
     * @Metodo Metodo para buscar el nombre en el historico de nombre por medio de su ID
     * @author carlos.castillo
     * @param id ID del nombre historico
     * @return HistoricoNombreEntidad
     */
    public HistoricoNombreEntidad findById(@Param("Id") long id);
}
