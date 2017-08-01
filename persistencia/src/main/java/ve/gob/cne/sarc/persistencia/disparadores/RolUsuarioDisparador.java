package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.RolUsuarioEntidad;

/**
 * RolUsuarioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase RolUsuarioEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @version 1.0 08 de jun. de 2016
 * @author Oscar Montilla
 *
 */
public class RolUsuarioDisparador {

    /**
     *
     * Este método recibe como parámetro la entidad RolUsuarioEntidad y llama a otros métodos privados para validar los
     * atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     *
     * @param rolUsuario objeto del modelo ontologico de rol usuario.
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesRolUsuario(RolUsuarioEntidad rolUsuario)
            throws ExceptionDisparador {
        validarUsuarioNulo(rolUsuario);
        validarRolNulo(rolUsuario);
    }

    private void validarUsuarioNulo(RolUsuarioEntidad rolUsuario)
            throws ExceptionDisparador {
        if (rolUsuario.getUsuario() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el Usuario correspondiente al RolUsuario");
        }
    }

    private void validarRolNulo(RolUsuarioEntidad rolUsuario) throws ExceptionDisparador {
        if (rolUsuario.getRol() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el Rol correspondiente al RolUsuario");
        }
    }
}
