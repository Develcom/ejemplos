package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.MatrimonioEntidad;

/**
 * MatrimonioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase MatrimonioEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar montilla
 * @version 1.0 06/09/2016
 */
public class MatrimonioDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad MatrimonioEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param matrimonio Objeto del modelo Ontologico que tiene la informacion de matrimonio
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesMatrimonio(MatrimonioEntidad matrimonio) throws ExceptionDisparador {
        validarDiMatrimonioNulo(matrimonio);
        validarDiMatrimonioVacio(matrimonio);
        validarFeMatrimonio(matrimonio);
        validarActaNulo(matrimonio);
    }

    private void validarDiMatrimonioNulo(MatrimonioEntidad matrimonio) throws ExceptionDisparador {
        if (matrimonio.getDireccion() == null) {
            throw new ExceptionDisparador("2#Debe introducir la dirección donde se llevó a cabo el matrimonio");
        }
    }

    private void validarDiMatrimonioVacio(MatrimonioEntidad matrimonio) throws ExceptionDisparador {
        if ("".equals(matrimonio.getDireccion().trim())) {
            throw new ExceptionDisparador("2#Debe introducir la dirección donde se llevó a cabo el matrimonio");
        }
    }

    private void validarFeMatrimonio(MatrimonioEntidad matrimonio) throws ExceptionDisparador {
        if (matrimonio.getFecha() == null) {
            throw new ExceptionDisparador("2#Debe introducir la fecha del matrimonio");
        }
    }

    private void validarActaNulo(MatrimonioEntidad matrimonio) throws ExceptionDisparador {
        if (matrimonio.getActa() == null) {
            throw new ExceptionDisparador("2#Debe indicar el acta asociada");
        }
    }
}
