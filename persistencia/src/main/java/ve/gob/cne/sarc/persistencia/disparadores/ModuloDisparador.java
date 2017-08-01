package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.ModuloEntidad;

/**
 * ModuloDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ModuloEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 */
public class ModuloDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad ModuloEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param modulo objeto del modelo ontologico de modulo
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesModulo(ModuloEntidad modulo) throws ExceptionDisparador {
        validarNombreNulo(modulo);
        validarNombreVacio(modulo);
        validarFechaInicioNulo(modulo);
    }

    private void validarNombreNulo(ModuloEntidad modulo) throws ExceptionDisparador {
        if (modulo.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que describa el código del módulo");
        }
    }

    private void validarNombreVacio(ModuloEntidad modulo) throws ExceptionDisparador {
        if ("".equals(modulo.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que describa el código del módulo");
        }
    }

    private void validarFechaInicioNulo(ModuloEntidad modulo) {
        if (modulo.getFechaInicio() == null) {
            modulo.setFechaInicio(new Date());
        }
    }
}
