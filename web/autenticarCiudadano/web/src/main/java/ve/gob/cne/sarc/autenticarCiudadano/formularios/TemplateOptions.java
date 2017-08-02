package ve.gob.cne.sarc.autenticarCiudadano.formularios;

public class TemplateOptions {
		String label;
		Option[] options;
		public Option[] getOptions() {
			return options;
		}

		
		public void setOptions(String[] names, Object[] values) {
			options=new Option[names.length];
			for (int i = 0; i < options.length; i++) {
				Option opcion=new Option();
				opcion.setName(names[i]);
//				opcion.setValue(values[i]);
				opcion.setValue(values[i]);
				options[i]=opcion;
			}
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public class Option{
			String name;
			Object value;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public Object getValue() {
				return value;
			}
			public void setValue(Object value) {
				this.value = value;
			}
		}
}
