App.controller('CorreccionControlador',function($scope,$http,$rootScope,$uibModal,registrarRutasService){
	console.log('Controlador del modal de Correcciones');

	var vm = this;
	//Guarda en un array las vistas presentadas durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//representa los datos actualmente presentes en pantalla
	vm.modeloActual = {};
	vm.titulos = [];
	vm.activo = -1;
	vm.disabled = true;
	//estado del proceso
	vm.estado = 'inicializacion';
	vm.finalizar_modal = false;
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
		vm.modeloActual = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		var lDeclarantes = JSON.parse(response.data.modelo.declarantes);
		var numProcesados = lDeclarantes.indexOf(vm.modeloActual.codigoDeclarante);
		if(numProcesados >= (lDeclarantes.length-1)){
			vm.estado = 'confirmar';
			return
		}
		vm.modeloActual.codigoDeclarante = lDeclarantes[lDeclarantes.indexOf(vm.modeloActual.codigoDeclarante)+1];
	}
	/**
	 * Carga los datos iniciales del proceso
	 */
	vm.inicializarDatos = function($scope, vm, response){
		vm.modeloActual = response.data.modelo;
		vm.vista =  response.data.vista;
		vm.presentarTitulo(response.data.modelo.titulo);
		var lDeclarantes = JSON.parse(response.data.modelo.declarantes);
		vm.modeloActual.codigoDeclarante = lDeclarantes[0];
		vm.estado = 'declaracion';
	}
	vm.finalizarYactualizar = function(){
		var datosConsulta = vm.rutas['confirmar'];
		llamadaAjax1($http, $scope, vm, datosConsulta.ruta, vm.modeloActual, datosConsulta.funcion, datosConsulta.metodo);
	}
	vm.manejarActualizacion = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modeloActual = response.data.modelo;
		vm.finalizar_modal = true;
	}
	vm.finalizar = function($scope, vm){
		$rootScope.cancelar1();
	}
	vm.cancelar = function(){
		presentarModal1($scope,vm,$uibModal,vm.finalizar,'\u00bfDesea cancelar la operaci\u00f3?',$rootScope.tituloWizard,'s');
	}
	//Inicia el Modal cuando no existe participante
	vm.openConfirmacionModal = function () {
		presentarModal1($scope,vm,$uibModal,vm.finalizarYactualizar,'\u00bfDesea guardar los cambios realizados?',$rootScope.tituloWizard,'s');
	};
	vm.rutas = {
				  inicializacion : {ruta: registrarRutasService.propiedades['sarc.web.autenticarCiudadano.infoDeclaracion.url'], funcion: vm.inicializarDatos, metodo : "POST"},
	              declaracion : {ruta : registrarRutasService.propiedades['sarc.web.autenticarCiudadano.procesarCorreccion.url'], funcion : vm.presentarPdf, metodo : "POST"},
	              conforme : {ruta : registrarRutasService.propiedades['sarc.web.autenticarCiudadano.conformidad.url'], funcion : vm.presentarPdf, metodo : "POST"},
	              confirmar : {ruta : registrarRutasService.propiedad['sarc.web.autenticarCiudadano.enviarAverificacion.url'], funcion : vm.manejarActualizacion, metodo : "POST"}
			   };

	//Datos a ser enviados al momento de cargar el modal 	
	vm.modeloActual={id:$rootScope.objectSelected.numeroSolicitud};
	llamadaAjax1($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modeloActual, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);

	vm.siguiente = function(){
		if(vm.estado ===  'confirmar'){
			vm.openConfirmacionModal(vm.conforme);
			return;
		}
		
		//guarda en la pila la vista actual
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modeloActual);
		vm.disabled = vm.vistas.length == 0;
		switch (vm.estado) {
		case 'declaracion':
			var datosConsulta = vm.rutas['declaracion'];
			llamadaAjax1($http, $scope, vm, datosConsulta.ruta, vm.modeloActual, datosConsulta.funcion, datosConsulta.metodo);
			break;
		default:
			break;
		}
	}
	
	vm.regresar= function(){
		vm.vista = vm.vistas[vm.vistas.length-1];
		vm.modeloActual = vm.modelos[vm.modelos.length-1];
		vm.modelos.splice(vm.modelos.length-1,1);
		vm.vistas.splice(vm.vistas.length-1,1);
		vm.disabled = vm.vistas.length == 0;
		vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);
		
		if(vm.activo < vm.titulos.length-1){
			vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
		}
		vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);	
	}
});
	