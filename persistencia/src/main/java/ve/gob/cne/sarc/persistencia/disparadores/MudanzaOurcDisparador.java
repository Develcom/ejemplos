package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.disparadores.ExceptionDisparador;
import ve.gob.cne.sarc.persistencia.entidades.MudanzaOurcEntidad;

public class MudanzaOurcDisparador {


    /**
     * Este método recibe como parámetro la entidad MudanzaOurcEntidad y llama a otros métodos privados para validar los
     * atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     *
     * @param operacion
     * @throws ExceptionDisparador
     */
    @PrePersist
    @PreUpdate
    public void validacionesOperacion(MudanzaOurcEntidad operacion)
            throws ExceptionDisparador {
        validarCodigoViejoNulo(operacion);
        validarCodigoViejoNuevo(operacion);
        validarFechaInicioNulo(operacion);
    }

    private void validarCodigoViejoNulo(MudanzaOurcEntidad operacion) throws ExceptionDisparador {
        if (operacion.getCodigoOurcVieja() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el codigo viejo de la ourc que identifique la operación");
        }
    }

    private void validarCodigoViejoNuevo(MudanzaOurcEntidad operacion)
            throws ExceptionDisparador {
    	if (operacion.getCodigoOurcNueva() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el codigo nuevo de la ourc que identifique la operación");
        }
    }

    private void validarFechaInicioNulo(MudanzaOurcEntidad operacion) throws ExceptionDisparador {
        if (operacion.getFeInicio() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la fecha que identifique la operación");
        }
    }

}
