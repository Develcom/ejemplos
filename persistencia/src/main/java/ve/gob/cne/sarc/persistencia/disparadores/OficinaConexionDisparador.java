package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.OficinaConexionEntidad;

/**
 * OficinaConexionDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase FuncionarioEstatusEntidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
public class OficinaConexionDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad FuncionarioEstatusEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param oficinaConexion objeto del modelo ontologico de Oficina Conexion
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesOficinaConexion(OficinaConexionEntidad oficinaConexion) throws ExceptionDisparador {

        validarNombreNulo(oficinaConexion);
        validarNombreVacio(oficinaConexion);
        validarFechaInicioNulo(oficinaConexion);

    }

    private void validarNombreNulo(OficinaConexionEntidad oficinaConexion) throws ExceptionDisparador {
        if (oficinaConexion.getNombreOficConexion() == null) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique al OficinaConexion");
        }
    }

    private void validarNombreVacio(OficinaConexionEntidad oficinaConexion) throws ExceptionDisparador {
        if ("".equals(oficinaConexion.getNombreOficConexion().trim())) {
            throw new ExceptionDisparador(
                    "Debe introducir el nombre que identifique al OficinaConexion");
        }
    }

    private void validarFechaInicioNulo(OficinaConexionEntidad oficinaConexion) {
        if (oficinaConexion.getFechaInicio() == null) {
            oficinaConexion.setFechaInicio(new Date());
        }
    }
}
