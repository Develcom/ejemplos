App.factory('footerFactory',function(){
	var visible=true;//usada para ocultar seccion del acta
	var accion;
	var escenario;
	var continuarEvaluando=true;
	var idReglaEjecutada;
	var vista;
	var lista;
	var gate;
	var model={};/////////////
	var inscripcion;
	var insercion;
	var estaFallecidoConID;
	var vzlanoOExtrajFallecidoDentroTN;
	var estaFallecidoEV14;
	var modelos=[];
	var formulario;
	var formularios=[];
	var modelo={"accion":"","value":true};
	
	var interfaz ={
			setInscripcion:function(){
				console.log("entro a inscripcion del factory");
				model.Inscripcion="true"
				model.Insercion="false";
				console.log(model);
			},
			setInsercion:function(){
				console.log("Entro al insercion de factory");
				model.Inscripcion="false";
				model.Insercion="true";
				console.log(model);
			},
			setEstaFallecidoConID:function(value){
				model.EstaFallecidoConID=value;
			},
			setVzlanoOExtrajFallecidoDentroTN:function(value){
				console.log("vzlanoOExtrajFallecidoDentroTN");
				model.VzlanoOExtrajFallecidoDentroTN=value;
			},
			setEstaFallecidoEV14:function(value){
				model.EstaFallecidoEV14=value;
			},
			setFormulario:function(mFormulario){
				formulario=mFormulario;
			},
			setModelo:function(accion){
				modelo={"accion":accion,"value":true};
			},
			getFormulario:function(){
				return formulario;
			},
			addFormulario:function(mFormulario){
				formularios.push(mFormulario);
			},
			addAccion:function(mAccion){
				accion=mAccion;
			},
			addModelos:function(mModelo){
				modelos.push(mModelo);
			},
			setGate:function(mGate){
				gate=mGate;
			},
			getModelo:function(){
				return modelo;
			},
			getAccion:function(){
				return accion;
			},
			getModelos:function(){
				return modelos;
			},
			removerFormulario:function(){
				formularios.splice(formularios.length-1,1);
			},
			getFormularios:function(){
				return formularios.join('');
			},
			getGate:function(){
				return gate;
			},
			asignarModelo:function(vmm){
				//model=vmm;
			},
			
			
			
			restartModelo:function(){
				model={};
				formularios=[];
			},
			
			
			
			
			getModelo:function(){
				return model;
			},
			setVisible:function(value){
				visible=value;
			},
			getVisible:function(){
				return visible;
			},
			setContinuarEvaluando:function(value){
				continuarEvaluando=value;
			},
			getContinuarEvaluando: function(){
				return continuarEvaluando;
			}
			
	}
	return interfaz;
}
);