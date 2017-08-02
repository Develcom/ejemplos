package ve.gob.cne.sarc.gestionLibros.web.gestionLibros.formulario;

import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.gestionLibros.web.gestionLibros.constantes.ConstantesLibro;
import ve.gob.cne.sarc.gestionLibros.web.gestionLibros.excepcion.GestionLibroException;
import ve.gob.cne.sarc.gestionLibros.web.gestionLibros.utilitario.Utilitario;

/**
 * DetalleTablaLibro.java
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version 
 * Dec 21, 2016
 * @author Anderson Delgado
 */
public class DetalleTablaLibro {

    private String nombreLibro;
    private String operacion;
    private String nombreCompletoReg;
    private String parroquia;
    private String numTomo;
    private String numActaInicio;
    private String numActaFin;
    private String estatus;

    /**
     * @param libro
     * @param operacion
     * @throws GestionLibroException
     */
    public DetalleTablaLibro(Libro libro, String operacion) throws GestionLibroException {

        this.numTomo = libro.getNumeroTomo();
        this.estatus = libro.getEstatus().trim();
        this.nombreLibro = libro.getTipoLibro().getNombre();

        if (Utilitario.esIgualCampos(operacion, ConstantesLibro.OPERACION_APERTURA)) {
            this.nombreCompletoReg = libro.getFuncionarioApertura().getPrimerNombre() + " - "
                    + libro.getFuncionarioApertura().getSegundoNombre() + "-"
                    + libro.getFuncionarioApertura().getPrimerApellido() + ""
                    + libro.getFuncionarioApertura().getSegundoApellido();
        } else if (Utilitario.esIgualCampos(operacion, ConstantesLibro.OPERACION_CIERRE)) {
            this.nombreCompletoReg = libro.getFuncionarioCierre().getPrimerNombre() + " - "
                    + libro.getFuncionarioCierre().getSegundoNombre() + "-"
                    + libro.getFuncionarioCierre().getPrimerApellido() + ""
                    + libro.getFuncionarioCierre().getSegundoApellido();
        }
        this.parroquia = libro.getOficina().getDireccion().getParroquia().getNombre();
        this.operacion = operacion;

    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getNombreCompletoReg() {
        return nombreCompletoReg;
    }

    public void setNombreCompletoReg(String nombreCompletoReg) {
        this.nombreCompletoReg = nombreCompletoReg;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getNumTomo() {
        return numTomo;
    }

    public void setNumTomo(String numTomo) {
        this.numTomo = numTomo;
    }

    public String getNumActaInicio() {
        return numActaInicio;
    }

    public void setNumActaInicio(String numActaInicio) {
        this.numActaInicio = numActaInicio;
    }

    public String getNumActaFin() {
        return numActaFin;
    }

    public void setNumActaFin(String numActaFin) {
        this.numActaFin = numActaFin;
    }
}
