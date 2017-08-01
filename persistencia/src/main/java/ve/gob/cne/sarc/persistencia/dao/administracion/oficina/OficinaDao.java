package ve.gob.cne.sarc.persistencia.dao.administracion.oficina;

import java.util.Collection;
import java.util.Date;

import ve.gob.cne.sarc.persistencia.dao.generic.GenericDao;
import ve.gob.cne.sarc.persistencia.entidades.GeograficoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SincronizadorEntidad;

/**
 * @author HiSoft
 */
public interface OficinaDao extends GenericDao {

    /**
     * Actualizar el Estatus de una Oficina
     * @param oficinaEntidad oficina a la cual se le quiere actualizar el Estatus
     * @param oficinaEstatusEntidad Estatus que tendrá la oficina
     */
    void actualizarStatus(OficinaEntidad oficinaEntidad,
                          OficinaEstatusEntidad oficinaEstatusEntidad);

    /**
     * Abrir o Cerrar una Oficina
     * @param oficinaEntidad Oficina que se quiere abrir o cerra
     * @param date fecha en la cual se va a abrir o cerrar la oficina
     * @param b verdadero sí la oficina está abierta y se va a cerrar, falso de otra manera
     */
    void abrirCerrarOficina(OficinaEntidad oficinaEntidad, Date date, boolean b);

    /**
     * Cargar la colección de Sincronizadores Asociados a la oficina por
     * GeograficoEntidad
     * @param geografico GeograficoEntidad
     * @return Sincronizadores Asociados a la oficina
     */
    Collection<SincronizadorEntidad> cargarSynPorGeo(GeograficoEntidad geografico);
    
    /**
     * Obtiene el ultimo sincronizador almacenado.
     * @return
     */
    SincronizadorEntidad obtenerUltimoSincronizador();
}
