package ve.gob.cne.sarc.autenticarCiudadano.formularios;


public class Formly{
	
//	Propiedades de un campo Formly
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
	public void setTemplateOptions(TemplateOptions templateOptions) {
		this.templateOptions = templateOptions;
	}
}
