package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * <p>
 * Esta clase se encarga de validar que los atributos de la clase TipoOficinaEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * </p>
 *
 * @author carlos.castillo
 * @version 1.0
 */
public class TipoOficinaDisparador {

    /**
     * <p>
     * Este método recibe como parámetro la entidad TipoOficinaEntidad y llama a otros métodos privados para validar los
     * atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * </p>
     *
     * @param tipoOficina
     * @throws ExceptionDisparador
     */
    @PrePersist
    @PreUpdate
    public void validacionesTipoOficina(TipoOficinaEntidad tipoOficina)
            throws ExceptionDisparador {
        validarNombreNulo(tipoOficina);
        validarNombreVacio(tipoOficina);
        validarFechaInicioNulo(tipoOficina);
    }

    private void validarNombreNulo(TipoOficinaEntidad tipoOficina)
            throws ExceptionDisparador {
        if (tipoOficina.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique el tipo de oficina");
        }
    }

    private void validarNombreVacio(TipoOficinaEntidad tipoOficina)
            throws ExceptionDisparador {
        if ("".equals(tipoOficina.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique el tipo de oficina");
        }
    }

    private void validarFechaInicioNulo(TipoOficinaEntidad tipoOficina) {
        if (tipoOficina.getFechaInicio() == null) {
            tipoOficina.setFechaInicio(new Date());
        }
    }
}
