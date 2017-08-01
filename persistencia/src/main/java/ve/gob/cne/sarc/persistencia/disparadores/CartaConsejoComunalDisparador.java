package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.CartaConsejoComunalEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;

/**
 * CartaConsejoComunalDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase CartaConsejoComunalEntidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class CartaConsejoComunalDisparador {

    /**
     *
     * Este método recibe como parámetro la entidad CartaConsejoComunalEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     *
     *
     * @param cartaConsejoComunal objeto del modelo ontologico de Carta Consejo Comunal
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesCartaConsejoComunal(
            CartaConsejoComunalEntidad cartaConsejoComunal) throws ExceptionDisparador {
        validarNombreConsejoComunalNulo(cartaConsejoComunal);
        validarNombreConsejoComunalVacio(cartaConsejoComunal);
        validarParticipantesNulo(cartaConsejoComunal);
    }

    private void validarNombreConsejoComunalNulo(
            CartaConsejoComunalEntidad cartaConsejoComunal) throws ExceptionDisparador {
        if (cartaConsejoComunal.getNombreConsejoComunal() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor nombreConsejoComunal en la entidad cartaConsejoComunal");
        }
    }

    private void validarNombreConsejoComunalVacio(
            CartaConsejoComunalEntidad cartaConsejoComunal) throws ExceptionDisparador {
        if ("".equals(cartaConsejoComunal.getNombreConsejoComunal().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor nombreConsejoComunal en la entidad cartaConsejoComunal");
        }
    }

    private void validarParticipantesNulo(
            CartaConsejoComunalEntidad cartaConsejoComunal) {
        if (cartaConsejoComunal.getParticipante() == null) {
            cartaConsejoComunal.setParticipante(new ParticipanteEntidad());
        }
    }
}
