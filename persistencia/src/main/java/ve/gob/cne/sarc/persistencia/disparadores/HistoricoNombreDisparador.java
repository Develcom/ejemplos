package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.disparadores.ExceptionDisparador;
import ve.gob.cne.sarc.persistencia.entidades.HistoricoNombreEntidad;

public class HistoricoNombreDisparador {

    /**
     * Este método recibe como parámetro la entidad MudanzaOurcEntidad y llama a otros métodos privados para validar los
     * atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     *
     * @param operacion
     * @throws ExceptionDisparador
     */
    @PrePersist
    @PreUpdate
    public void validacionesOperacion(HistoricoNombreEntidad operacion)
            throws ExceptionDisparador {
        validarCodigoNulo(operacion);
        validarNombreViejo(operacion);
        validarFechCambio(operacion);
    }

    private void validarCodigoNulo(HistoricoNombreEntidad operacion) throws ExceptionDisparador {
        if (operacion.getOficina() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el codigo viejo de la ourc que identifique la operación");
        }
    }

    private void validarNombreViejo(HistoricoNombreEntidad operacion)
            throws ExceptionDisparador {
        if (operacion.getNbViejo() == null || "".equals(operacion.getNbViejo().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre viejo que identifique la operación");
        }
    }

    private void validarFechCambio(HistoricoNombreEntidad operacion) throws ExceptionDisparador {
        if (operacion.getFeCambioNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la fecha de cambio que identifique la operación");
        }
    }


}
