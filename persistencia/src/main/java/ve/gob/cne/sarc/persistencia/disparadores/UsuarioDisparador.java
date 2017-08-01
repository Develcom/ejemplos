package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;

/**
 * UsuarioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase UsuarioEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @author Oscar Montilla\
 * @version 1.0 07/09/2016
 */
public class UsuarioDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad UsuarioEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param usuario objeto del modelo ontologico de Usuario
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesUsuario(UsuarioEntidad usuario) throws ExceptionDisparador {
        validarFuncionarioNulo(usuario);
        validarRolUsuarioNulo(usuario);
        validarCodigoNulo(usuario);
        validarCodigoVacio(usuario);
        validarClaveUsuarioNulo(usuario);
        validarClaveUsuarioVacio(usuario);
        validarNombreNulo(usuario);
        validarNombreVacio(usuario);
        validarCorreoElectronicoNulo(usuario);
        validarCorreoElectronicoVacio(usuario);
        validarEstatusNulo(usuario);
        validarOficinaFuncionarioNulo(usuario);
    }

    private void validarFuncionarioNulo(UsuarioEntidad usuario)
            throws ExceptionDisparador {
        if (usuario.getOficinaFuncionario() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el funcionario correspondiente al usuario");
        }
    }

    private void validarRolUsuarioNulo(UsuarioEntidad usuario) throws ExceptionDisparador {
        if (usuario.getRolUsuario() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el rol correspondiente al usuario");
        }
    }

    private void validarCodigoNulo(UsuarioEntidad usuario) throws ExceptionDisparador {
        if (usuario.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el código que identifique al usuario");
        }
    }

    private void validarCodigoVacio(UsuarioEntidad usuario) throws ExceptionDisparador {
        if ("".equals(usuario.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el código que identifique al usuario");
        }
    }

    private void validarClaveUsuarioNulo(UsuarioEntidad usuario)
            throws ExceptionDisparador {
        if (usuario.getClaveUsuario() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la clave de usuario del usuario");
        }
    }

    private void validarClaveUsuarioVacio(UsuarioEntidad usuario)
            throws ExceptionDisparador {
        if ("".equals(usuario.getClaveUsuario().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la clave de usuario del usuario");
        }
    }

    private void validarNombreNulo(UsuarioEntidad usuario) throws ExceptionDisparador {
        if (usuario.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al usuario");
        }
    }

    private void validarNombreVacio(UsuarioEntidad usuario) throws ExceptionDisparador {
        if ("".equals(usuario.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al usuario");
        }
    }

    private void validarCorreoElectronicoNulo(UsuarioEntidad usuario)
            throws ExceptionDisparador {
        if (usuario.getCorreoElectronico() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el correo electrónico del usuario");
        }
    }

    private void validarCorreoElectronicoVacio(UsuarioEntidad usuario)
            throws ExceptionDisparador {
        if ("".equals(usuario.getCorreoElectronico().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el correo electrónico del usuario");
        }
    }

    private void validarEstatusNulo(UsuarioEntidad usuario) throws ExceptionDisparador {
        if (usuario.getEstatus() == null) {
            throw new ExceptionDisparador("2#Debe introducir el estatus del usuario");
        }
    }

    private void validarOficinaFuncionarioNulo(UsuarioEntidad usuario) throws ExceptionDisparador {
        if (usuario.getOficinaFuncionario() == null) {
            throw new ExceptionDisparador("2#Debe asociar el registro a una oficina y aun funcioanrio");
        }
    }
}
