package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.IntegranteCartaConsejoComunalEntidad;

/**
 * IntegranteCartaConsejoComunalDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase IntegranteCartaConsejoComunalEntidad sean
 * correctos en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class IntegranteCartaConsejoComunalDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad IntegranteCartaConsejoComunalEntidad y llama a otros métodos
     * privados para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param integranteCartaConsejoComunal Objeto del modelo ontologico de Integrante Carta Consejo Comunal
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesIntegrantesCartasConsejosComunales(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        validarCartaConsejoComunalNulo(integranteCartaConsejoComunal);
        validarTipoDocumentoNulo(integranteCartaConsejoComunal);
        validarApellidoNulo(integranteCartaConsejoComunal);
        validarApellidoVacio(integranteCartaConsejoComunal);
        validarNombreNulo(integranteCartaConsejoComunal);
        validarNombreVacio(integranteCartaConsejoComunal);
        validarNumeroDocumentoNulo(integranteCartaConsejoComunal);
        validarIndicadorIntegranteNulo(integranteCartaConsejoComunal);
    }

    private void validarCartaConsejoComunalNulo(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        if (integranteCartaConsejoComunal.getCartaConsejoComunal() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la carta del consejo comunal correspondiente al "
                    + "integrante de la carta del consejo comunal");
        }
    }

    private void validarApellidoNulo(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        if (integranteCartaConsejoComunal.getApellidoIntegrante() == null) {
            throw new ExceptionDisparador("2#Debe introducir el apellido del integrante");
        }
    }

    private void validarApellidoVacio(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        if ("".equals(integranteCartaConsejoComunal.getApellidoIntegrante()
                .trim())) {
            throw new ExceptionDisparador("2#Debe introducir el apellido del integrante");
        }
    }

    private void validarNombreNulo(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        if (integranteCartaConsejoComunal.getNombreIntegrante() == null) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del integrante");
        }
    }

    private void validarNombreVacio(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        if ("".equals(integranteCartaConsejoComunal.getNombreIntegrante()
                .trim())) {
            throw new ExceptionDisparador("2#Debe introducir el nombre del integrante");
        }
    }

    private void validarTipoDocumentoNulo(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        if (integranteCartaConsejoComunal.getTipoDocumento() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir alguna de las siguientes letras para el tipo de documento: V o E o P");
        }
    }

    private void validarNumeroDocumentoNulo(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        if (integranteCartaConsejoComunal.getNumeroDocumento() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el número de documento del integrante");
        }
    }

    private void validarIndicadorIntegranteNulo(
            IntegranteCartaConsejoComunalEntidad integranteCartaConsejoComunal)
            throws ExceptionDisparador {
        if (integranteCartaConsejoComunal.getIndicadorIntegrante() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir alguna de las siguientes letras para el indicador de integrante: I o D");
        }
    }
}
