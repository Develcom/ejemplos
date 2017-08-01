package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.DeclaracionJuradaEntidad;

/**
 * DeclaracionJuradaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase DeclaracionJuradaEntidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class DeclaracionJuradaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad DeclaracionJuradaEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param declaracionJurada objeto del modelo ontologico de Declaracion Jurada
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesDeclaracionJurada(DeclaracionJuradaEntidad declaracionJurada)
            throws ExceptionDisparador {
        validarIndicadorTipoNulo(declaracionJurada);
        validarIndicadorTipoVacio(declaracionJurada);
    }

    private void validarIndicadorTipoNulo(
            DeclaracionJuradaEntidad declaracionJurada) throws ExceptionDisparador {
        if (declaracionJurada.getIndicadorTipo() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor indicadorTipo en la entidad declaraciónJurada");
        }
    }

    private void validarIndicadorTipoVacio(
            DeclaracionJuradaEntidad declaracionJurada) throws ExceptionDisparador {
        if ("".equals(declaracionJurada.getIndicadorTipo().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor indicadorTipo en la entidad declaraciónJurada");
        }
    }
}
