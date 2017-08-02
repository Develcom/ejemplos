App.controller('ImprimirControlador',function($scope,$http,$rootScope,$uibModal,registrarRutasService){

	var mmTitulo = 'Declaraci\u00f3n jurada del ';
	var vm = this;
	//Guarda en un array las vistas presentadas durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//representa los datos actualmente presentes en pantalla
	vm.modelo = {};
	vm.titulos = [];
	vm.activo = -1;
	//estado del proceso
	vm.estado = 'inicializacion';
	/**
	 *identifica el titulo de la etapa del proceso y el indice activo para 
	 *resaltarlo con el color azul 
	 **/
	vm.presentarTitulo = function(mTitulo){
		vm.activo = vm.titulos.indexOf(mTitulo);
		if(vm.activo == -1){
			vm.titulos.push(mTitulo);
			vm.activo = vm.titulos.length-1;
		}
	}
	/**
	 * Maneja la etapa de verificacion de la declaracion jurada
	 */
	vm.presentarPdf = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		var elTitulo = mmTitulo + response.data.modelo.titulo;
		vm.presentarTitulo(elTitulo);
		vm.modelo.titulo = elTitulo;
		var lDeclarantes = JSON.parse(response.data.modelo.declarantes);
		if(lDeclarantes.indexOf(vm.modelo.codigoDeclarante) >= (lDeclarantes.length-1)){
			vm.estado = 'confirmar';
			return
		}
		vm.modelo.codigoDeclarante = lDeclarantes[lDeclarantes.indexOf(vm.modelo.codigoDeclarante)+1];
	}
	vm.finalizarYactualizar = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
	}
	vm.finalizar = function(){
		$rootScope.imprimir = false;
	}
	
	/**
	 * Carga los datos iniciales del proceso
	 */
	vm.inicializarDatos = function($scope, vm, response){
		vm.modelo = response.data.modelo;
		var lDeclarantes = JSON.parse(response.data.modelo.declarantes);
		vm.modelo.codigoDeclarante = lDeclarantes[0];
		vm.estado = 'declaracion';
		llamadaAjax1($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	}
	vm.okF = function(){
		console.log('ok****************');
	}
	//Evento del botón imprimir
	vm.imprimir = function(){
		presentarModal1($scope,vm,$uibModal,vm.okF,'\u00bfImpresi\u00f3n satisfactoria?',$rootScope.tituloWizard,'s');
	}
	vm.confirmarYAvanzar = function(){
	}

	vm.okDigital = function(){
		vm.impreso=true;
	}
	//Evento del boton digitalizar
	vm.digitalizar = function(){
		presentarModal1($scope ,vm, $uibModal,vm.okDigital,'La declaraci\u00f3n jurada del testigo ha sido impresa y digitalizada satisfactoriamente',$rootScope.tituloWizard,'c');
	}
	vm.comunicarResultado = function(){
		llamadaAjax1($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	}
	vm.rutas = {
				  inicializacion : {ruta: registrarRutasService.propiedades['sarc.web.autenticarCiudadano.infoImpresion.url'], funcion: vm.inicializarDatos, metodo : "POST"},
	              declaracion : {ruta : registrarRutasService.propiedades['sarc.web.autenticarCiudadano.procesarImpresion.url'], funcion : vm.presentarPdf, metodo : "POST"},
	              confirmar : {ruta : registrarRutasService.propiedades['sarc.web.autenticarCiudadano.actualizarAbierta.url'], funcion : vm.finalizarYactualizar, metodo : "POST"}
			   };

	//Datos a ser enviados al momento de cargar el modal 	
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud};
	llamadaAjax1($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);

	vm.siguiente = function(){
		if(!vm.impresionConfirmada){
			presentarModal1($scope, vm, $uibModal,function(vm){vm.impresionConfirmada = true; console.log('ffffffgggg'); vm.siguiente();},'\u00bfEl documento se ha impreso correctamente?',$rootScope.tituloWizard,'s');
			return;
		}
		
		if(vm.estado == 'confirmar'){
			vm.comunicarResultado();
			return;
		}
		vm.impreso=false;
		vm.impresionConfirmada = false;
		//guarda en la pila la vista actual
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modelo);
		switch (vm.estado) {
		case 'declaracion':
			var datosConsulta = vm.rutas['declaracion'];
			llamadaAjax1($http, $scope, vm, datosConsulta.ruta, vm.modelo, datosConsulta.funcion, datosConsulta.metodo);
			break;
		default:
			break;
		}
	}
	
	vm.regresar= function(){
		vm.vista = vm.vistas[vm.vistas.length-1];
		vm.modelo = vm.modelos[vm.modelos.length-1];
		vm.modelos.splice(vm.modelos.length-1,1);
		vm.vistas.splice(vm.vistas.length-1,1);
		vm.disabled = vm.vistas.length == 0;
		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
		
		if(vm.activo < vm.titulos.length-1){
			vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
		}
		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);		
	}
});
	