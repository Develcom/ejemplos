package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.OficinaCitaEntidad;

public interface OficinaCitaRepository extends
CrudRepository<OficinaCitaEntidad, Long> {
    
    /**
     * buscarPorCodigoOficina
     * Metodo que Busca las configuraciones de las Citas por el Código
     * @since 17 de oct. de 2016
     * @param oficina
     * @return
     * @return OficinaCitaEntidad
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-201
     */
    public OficinaCitaEntidad buscarPorCodigoOficina(@Param("oficina") String oficina);

}
