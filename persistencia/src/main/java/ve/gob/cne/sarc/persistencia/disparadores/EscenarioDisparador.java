/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.EscenarioEntidad;



/**
 * <p>
 * Esta clase se encarga de validar que los atributos de la clase
 * EscenarioEntidad sean correctos en cuanto a obligatoriedad y formato.
 * </p>
 * 
 * @author SoaintUSer
 * @version 1.1
 */
public class EscenarioDisparador {

    /**
     * <p>
     * Este método recibe como parámetro la entidad EscenarioEntidad y llama a
     * otros métodos privados para validar los atributos que no puedan ser
     * nulos, vacios o deban cumplir con un formato específico.
     * </p>
     * 
     * @param escenario
     * @throws Exception
     */
    @PrePersist
    @PreUpdate
    public void validacionesEscenario(EscenarioEntidad escenario)
            throws Exception {
        validarNumeroCero(escenario);
        validarNombreNulo(escenario);
        validarNombreVacio(escenario);
        validarIndicadorContinuaCero(escenario);
        validarIndicadorAutomaticoCero(escenario);
        validarFechaInicioNulo(escenario);
    }

    private void validarNumeroCero(EscenarioEntidad escenario) throws Exception {
        if (escenario.getNumero() == 0) {
            throw new Exception(
                    "2#Debe introducir el valor número en la entidad escenario");
        }
    }

    private void validarNombreNulo(EscenarioEntidad escenario) throws Exception {
        if (escenario.getNombre() == null) {
            throw new Exception(
                    "2#Debe introducir el nombre que identifique al escenario");
        }
    }

    private void validarNombreVacio(EscenarioEntidad escenario)
            throws Exception {
        if ("".equals(escenario.getNombre().trim())) {
            throw new Exception(
                    "2#Debe introducir el nombre que identifique al escenario");
        }
    }

    private void validarIndicadorContinuaCero(EscenarioEntidad escenario)
            throws Exception {
        if (escenario.getContinua() == 0) {
            throw new Exception(
                    "2#Debe introducir el valor indicadorContinua en la entidad escenario");
        }
    }

    private void validarIndicadorAutomaticoCero(EscenarioEntidad escenario)
            throws Exception {
        if (escenario.getAutomatico() == 0) {
            throw new Exception(
                    "2#Debe introducir el valor indicadorAutomatico en la entidad escenario");
        }
    }

    private void validarFechaInicioNulo(EscenarioEntidad escenario) {
        if (escenario.getFechaInicio() == null) {
            escenario.setFechaInicio(new Date());
        }
    }

}
