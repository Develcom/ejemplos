package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DireccionCiudadanoEntidad;

/**
 * CiudadanoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase CiudadanoEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class CiudadanoDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad CiudadanoEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param ciudadano Objeto del modelo ontologico de Ciudadano
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesCiudadano(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        validarNacionalidadNulo(ciudadano);
        validarPrimerApellidoNulo(ciudadano);
        validarPrimerApellidoVacio(ciudadano);
        validarPrimerNombreNulo(ciudadano);
        validarPrimerNombreVacio(ciudadano);
        validarFechaNacimientoNulo(ciudadano);
        validarSexoNulo(ciudadano);
        validarSexoVacio(ciudadano);
        validarEstadoCivilNulo(ciudadano);
        validarEstadoCivilVacio(ciudadano);
        validarDireccionNacimientoNulo(ciudadano);
        validarDireccionNacimientoVacio(ciudadano);
        validarDireccionesCiudadanosNulo(ciudadano);
    }

    private void validarNacionalidadNulo(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if (ciudadano.getNacionalidad() == null) {
            throw new ExceptionDisparador("2#Debe introducir la nacionalidad del ciudadano");
        }
    }

    private void validarPrimerNombreNulo(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if (ciudadano.getPrimerNombre() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del ciudadano");
        }

    }

    private void validarPrimerNombreVacio(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if ("".equals(ciudadano.getPrimerNombre().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer nombre del ciudadano");
        }
    }

    private void validarPrimerApellidoNulo(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if (ciudadano.getPrimerApellido() == null) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del ciudadano");
        }

    }

    private void validarPrimerApellidoVacio(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if ("".equals(ciudadano.getPrimerApellido().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el primer apellido del ciudadano");
        }
    }

    private void validarDireccionNacimientoNulo(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if (ciudadano.getDireccionNacimiento() == null) {
            throw new ExceptionDisparador("2#Debe introducir el valor "
                    + "direcciónNacimiento en la entidad ciudadano");
        }
    }

    private void validarDireccionNacimientoVacio(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if ("".equals(ciudadano.getDireccionNacimiento().trim())) {
            throw new ExceptionDisparador("2#Debe introducir el valor "
                    + "direcciónNacimiento en la entidad ciudadano");
        }
    }

    private void validarFechaNacimientoNulo(CiudadanoEntidad ciudadano) {
        if (ciudadano.getFechaNacimiento() == null) {
            ciudadano.setFechaNacimiento(new Date());
        }
    }

    private void validarSexoNulo(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if (ciudadano.getSexo() == null) {
            throw new ExceptionDisparador("2#Debe indicar el sexo del ciudadano");
        }
    }

    private void validarSexoVacio(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if ("".equals(ciudadano.getSexo().trim())) {
            throw new ExceptionDisparador("2#Debe indicar el sexo del ciudadano");
        }
    }

    private void validarEstadoCivilNulo(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if (ciudadano.getEstatusCivil() == null) {
            throw new ExceptionDisparador("2#Debe indicar el estado civil del ciudadano");
        }
    }

    private void validarEstadoCivilVacio(CiudadanoEntidad ciudadano) throws ExceptionDisparador {
        if ("".equals(ciudadano.getEstatusCivil().trim())) {
            throw new ExceptionDisparador("2#Debe indicar el estado civil del ciudadano");
        }
    }

    private void validarDireccionesCiudadanosNulo(CiudadanoEntidad ciudadano) {
        if (ciudadano.getDireccionesCiudadanos() == null) {
            ciudadano
                    .setDireccionesCiudadanos(new ArrayList<DireccionCiudadanoEntidad>());
        }
    }
}
