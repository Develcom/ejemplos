package ve.gob.cne.sarc.gestionLibros.web.gestionLibros.formulario;

import java.util.ArrayList;

import ve.gob.cne.sarc.comunes.oficina.Libro;

/**
 * LibroCivilFormulario.java
 * 
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version Dec 21, 2016
 * @author Anderson Delgado
 */
public class LibroCivilFormulario {

    /**
	 * 
	 */
    private ArrayList<Libro> librosList = new ArrayList<>();

    public LibroCivilFormulario() {
        // instance class
    }

    public ArrayList<Libro> getLibrosList() {
        return librosList;
    }

    public void setLibrosList(ArrayList<Libro> librosList) {
        this.librosList = librosList;
    }
}
