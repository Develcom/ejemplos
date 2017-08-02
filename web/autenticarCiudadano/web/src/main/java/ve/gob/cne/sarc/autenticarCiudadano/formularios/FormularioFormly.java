package ve.gob.cne.sarc.autenticarCiudadano.formularios;

import java.util.Vector;

public class FormularioFormly {
	private Vector<Formly> fields;
	public FormularioFormly(){
		fields=new Vector<Formly>();
	}
	public void addField(Formly field){
	 fields.add(field);
	}
	public Vector<Formly> getFields(){
		return fields;
	}
}
