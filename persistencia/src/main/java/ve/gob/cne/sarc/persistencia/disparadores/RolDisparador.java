package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.RolEntidad;

/**
 * RolDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase RolEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 07/09/2016
 */
public class RolDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad RolEntidad y llama a otros métodos privados para validar los
     * atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param rol objeto del modelo ontologico de rol.
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesRol(RolEntidad rol) throws ExceptionDisparador {
        validarNombreNulo(rol);
        validarNombreVacio(rol);
    }

    private void validarNombreNulo(RolEntidad rol) throws ExceptionDisparador {
        if (rol.getNombre()== null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al rol");
        }
    }

    private void validarNombreVacio(RolEntidad rol) throws ExceptionDisparador {
        if ("".equals(rol.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al rol");
        }
    }
}
