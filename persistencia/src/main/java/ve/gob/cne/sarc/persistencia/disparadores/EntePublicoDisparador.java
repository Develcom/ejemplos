package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EntePublicoEntidad;

/**
 * EntePublicoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase EntePublicoEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class EntePublicoDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad EntePublicoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param entePublico objeto del modelo ontologico de Ente Publico
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesEntePublico(EntePublicoEntidad entePublico)
            throws ExceptionDisparador {
        validarNombreNulo(entePublico);
        validarNombreVacio(entePublico);
        validarFechaInicioNulo(entePublico);
    }

    private void validarNombreNulo(EntePublicoEntidad entePublico)
            throws ExceptionDisparador {
        if (entePublico.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al entePúblico");
        }
    }

    private void validarNombreVacio(EntePublicoEntidad entePublico)
            throws ExceptionDisparador {
        if ("".equals(entePublico.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al entePúblico");
        }
    }

    private void validarFechaInicioNulo(EntePublicoEntidad entePublico) {
        if (entePublico.getFechaInicio() == null) {
            entePublico.setFechaInicio(new Date());
        }
    }
}
