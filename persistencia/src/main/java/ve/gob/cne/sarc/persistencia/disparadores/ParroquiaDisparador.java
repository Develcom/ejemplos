package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * ParroquiaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ParroquiaEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
public class ParroquiaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad ParroquiaEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param parroquia objeto del modelo ontologico de parroquia
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesParroquia(ParroquiaEntidad parroquia)
            throws ExceptionDisparador {
        validarMunicipioNulo(parroquia);
        validarNombreNulo(parroquia);
        validarNombreVacio(parroquia);
        validarFechaInicioNulo(parroquia);
    }

    private void validarMunicipioNulo(ParroquiaEntidad parroquia)
            throws ExceptionDisparador {
        if (parroquia.getMunicipio() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el parroquia correspondiente a la parroquia");
        }
    }

    private void validarNombreNulo(ParroquiaEntidad parroquia) throws ExceptionDisparador {
        if (parroquia.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique a la parroquia");
        }
    }

    private void validarNombreVacio(ParroquiaEntidad parroquia)
            throws ExceptionDisparador {
        if ("".equals(parroquia.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique a la parroquia");
        }
    }

    private void validarFechaInicioNulo(ParroquiaEntidad parroquia) {
        if (parroquia.getFechaInicio() == null) {
            parroquia.setFechaInicio(new Date());
        }
    }
}
