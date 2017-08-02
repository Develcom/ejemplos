package ve.gob.cne.sarc.atencionComunitaria.web.atencionComunitaria.form.atencionCiudadano;

import java.util.List;
import ve.gob.cne.sarc.comunes.catalogo.EntePublico;
import ve.gob.cne.sarc.comunes.catalogo.Modulo;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;


public class AtencionFormulario {
	
	private List<TipoDocumento> documentos;
	private List<EntePublico> entes;
	private List<TipoDocumento> documentosEnte;
	private List<Modulo> modulos;
	private List<Tramite> tramites;
	
	public List<Tramite> getTramites() {
		return tramites;
	}
	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}
	public List<Modulo> getModulos() {
		return modulos;
	}
	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}
	public List<TipoDocumento> getDocumentosEnte() {
		return documentosEnte;
	}
	public void setDocumentosEnte(List<TipoDocumento> documentosEnte) {
		this.documentosEnte = documentosEnte;
	}
	public List<TipoDocumento> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<TipoDocumento> documentos) {
		this.documentos = documentos;
	}
	public List<EntePublico> getEntes() {
		return entes;
	}
	public void setEntes(List<EntePublico> entes) {
		this.entes = entes;
	}

}
