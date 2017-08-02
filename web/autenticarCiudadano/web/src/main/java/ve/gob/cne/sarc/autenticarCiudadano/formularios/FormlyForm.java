package ve.gob.cne.sarc.autenticarCiudadano.formularios;


public class FormlyForm {
	private FormlyField[] fields;
	private Modelo modelo=new Modelo();
	
	public FormlyField[] getFields() {
		return fields;
	}
	
	public void setFields(String[] listaNombres, String[] listaCodigos, String type) {
		System.out.println("Creando campos del formulario: "+type);
		FormlyField[] formlyFields=new FormlyField[listaNombres.length+1];
		formlyFields[0]=new FormlyField();
		formlyFields[0].setType("enunciadoLista");
		formlyFields[0].setKey("");
		formlyFields[0].setTemplateOptions("");
		for (int i = 1; i <= listaNombres.length; i++) {
			formlyFields[i]=new FormlyField();
			formlyFields[i].setType(type);
			formlyFields[i].setKey(listaCodigos[i-1]);
			formlyFields[i].setTemplateOptions(listaNombres[i-1]);
		}
		this.fields = formlyFields;
	}
	public Modelo getModelo(){
		return modelo;
	}
	public void setModelo(Modelo modelo){
		this.modelo = modelo;
	}
	public void setAutenticar(boolean autenticar){
		modelo.setAutenticar(autenticar);
	}
	public void setActivo(int activo){
		modelo.setActivo(activo);
	}
	
	public void setSolicitarDatos(boolean solicitarDatos){
		modelo.setSolicitarDatos(solicitarDatos);
	}
	
	public class FormlyField{
		private String type;
		private String key;
		private TemplateOptions templateOptions;
		
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public TemplateOptions getTemplateOptions() {
			return templateOptions;
		}

		public void setTemplateOptions(String label) {
			TemplateOptions to= new TemplateOptions();
			to.setLabel(label);
			this.templateOptions = to;
		}
	}
	public class Modelo{
		boolean validador=true;
		int activo =0;
		boolean autenticar=true;
		boolean solicitarDatos=true;
		boolean autenticado=true;

		public boolean isSolicitarDatos() {
			return solicitarDatos;
		}

		public void setSolicitarDatos(boolean solicitarDatos) {
			this.solicitarDatos = solicitarDatos;
		}

		public boolean isAutenticar() {
			return autenticar;
		}

		public void setAutenticar(boolean autenticar) {
			this.autenticar = autenticar;
		}

		public int getActivo() {
			return activo;
		}

		public void setActivo(int activo) {
			this.activo = activo;
		}

		public boolean isValidador() {
			return validador;
		}

		public void setValidador(boolean validador) {
			this.validador = validador;
		}
	}

}
