App.controller('VerificacionControlador',function($scope,$http,$rootScope,$uibModal, $log, validaciones,registrarRutasService){
	
	$scope.validaciones = validaciones;
	//var mmTitulo = 'Declaraci\u00f3n jurada ';
	var mmTitulo1 = 'D.J ';
	var vm = this;
	//indica el paso actual del proceso
	vm.paso = 0;
	//Guarda en un array los formularios presentados durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//representa los datos actualmente presentes en pantalla
	vm.modeloActual = {};
	vm.titulos = [];
	vm.activo = -1;
	//estado del proceso
	vm.estado = 'declaracion';
	vm.conforme = false;
	vm.conforme1 = true;
	vm.conformar = false;
	vm.observaciones = '';
	vm.finalizar_modal = false;
	vm.disabled = true;
	vm.errorTecla = false;
	vm.contador = 1;
	//vm.confirmarOficio = false;
	
	
	
	llamadaAjax = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
	 $http({
	  method: metodo,
	  url: url,
	  data: angular.toJson(datos)
	 }).then(function successCallback(response) {
	  accionSatisfactoria($scope, vm, response);
	 }, function errorCallback(response) {
	  console.log("error: "+response.data);
	 });
	}
	
	
	/*identifica el titulo de la etapa del proceso y el indice activo para 
	  resaltarlo con el color azul */
	vm.presentarTitulo = function(mTitulo){
		vm.activo = vm.titulos.indexOf(mTitulo);
		if(vm.activo == -1){
			vm.titulos.push(mTitulo);
			vm.activo = vm.titulos.length-1;
		}
	}
	
	/*vm.conformidad= function($scope, vm, response){
		console.log("vm.conformidad");
		vm.vista = response.data.vista;
		vm.modeloActual = response.data.modelo;
		//var elTitulo1 = mmTitulo + response.data.modelo.titulo;
		vm.presentarTitulo(response.data.modelo.titulo);
		//vm.modeloActual.titulo = elTitulo1;
		vm.confirmarOficio = true;
		//vm.finalizar_modal = true;
	}*/
	
	vm.funcion = function(parametro){
		if(parametro == true){
			vm.abrirModalConforme();
		}else if (parametro == false){
			vm.abrirModalNoConforme();
		}
	}	
	
	 vm.abrirModalConforme = function(){
		 presentarModal($scope,$uibModal,vm.finalizarYactualizar,'Verificación de actas CONFORME',$rootScope.tituloWizard,'a');
		 
	 }
	 vm.abrirModalNoConforme = function (){
		 presentarModal($scope,$uibModal,vm.finalizarYactualizar,'Verificación de actas NO CONFORME',$rootScope.tituloWizard,'a');	
		 
	 }
	 
	 /*vm.okConforme = function(){
		 //vm.estado = 'confirmar'; //vm.rutas['confirmar'];
		 console.log("Cambiando status a PI");
		 vm.finalizar();
		 //llamadaAjax($http, $scope, vm,registrarRutasService.propiedades['sarc.web.autenticarCiudadano.actualizarSolicitud.url'], vm.modeloActual, vm.finalizar, "POST");
	 }
	 
	 vm.noConforme = function(){
		 //vm.estado = 'confirmar'; //vm.rutas['confirmar']; 
		 console.log("Cambiando status a NC");
		 vm.finalizar();
		 //llamadaAjax($http, $scope, vm,registrarRutasService.propiedades['sarc.web.autenticarCiudadano.actualizarSolicitud.url'], vm.modeloActual, vm.finalizar, "POST");
	 }*/
	
	 /*vm.autorizar = function(){
		 vm.estado = 'declaracion';
		}*/
	 
	vm.finalizarYactualizar = function(){
		var datosConsulta = vm.rutas['confirmar'];
		if(vm.conforme === '0' || vm.conforme === 0){
			vm.observaciones += "\n";
			if (vm.modeloActual.hasOwnProperty('observaciones')){
				vm.observaciones += vm.modeloActual.observaciones.trim();
				delete vm.modeloActual['observaciones'];
			}
		}
		vm.modeloActual.observaciones = vm.observaciones;
		vm.modeloActual.conforme = vm.conforme;
		llamadaAjax1($http, $scope, vm, datosConsulta.ruta, vm.modeloActual, datosConsulta.funcion, datosConsulta.metodo);
		//llamadaAjax1($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modeloActual, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
		vm.finalizar();
	}
	
	vm.manejarActualizacion = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modeloActual = response.data.modelo;
		vm.finalizar_modal = true;
		//vm.finalizar();
	}
	
	//Inicia el Modal cuando no existe participante
	/*vm.openConfirmacionModal = function () {
		console.log("Solicitud Cerrada");
		//presentarModal1($scope, vm,$uibModal,vm.finalizarYactualizar,'\u00bfDesea guardar las observaciones?',$rootScope.tituloWizard,'s');
	};*/
	vm.finalizar = function(){
		$rootScope.cancelar1();
	}
	vm.cancelar = function(){
		presentarModal1($scope, vm,$uibModal,vm.finalizar,'\u00bfDesea cancelar la operaci\u00f3n?',$rootScope.tituloWizard,'s');
	}

	/**
	 * Maneja la etapa de verificacion de la declaracion jurada
	 */
	vm.presentarPdf = function($scope, vm, response){
		console.log("Estado: "+vm.estado);
		vm.vista = response.data.vista;
		vm.modeloActual = response.data.modelo;
		console.log("Modelo: "+vm.modeloActual.toString());
		var elTitulo = mmTitulo1 + response.data.modelo.titulo;
		if(vm.contador%2 != 0){
			vm.presentarTitulo(elTitulo);
			vm.modeloActual.titulo = elTitulo;
		}
		var nConformaciones = parseInt(response.data.modelo.nConformaciones);
		console.log("nConfirmaciones: "+nConformaciones);
		vm.contador++;
		var numProcesados = parseInt(response.data.modelo.numTestigo);
		console.log("numProcesados: "+numProcesados);
		var lDeclarantes = JSON.parse(response.data.modelo.declarantes);//.length
		console.log("lDeclarantes: "+lDeclarantes);
		console.log("Observaciones: "+vm.observaciones);
		if(nConformaciones >= (2*lDeclarantes.length)){
			vm.estado = 'confirmar';
			return
		}
		vm.modeloActual.codigoDeclarante = numProcesados > 2 ? lDeclarantes[1] : lDeclarantes[0];
	}
	
	vm.rutas = {
	              declaracion : {ruta : registrarRutasService.propiedades['sarc.web.autenticarCiudadano.procesarVerificacion.url'], funcion : vm.presentarPdf, metodo : "POST"},
	              conforme : {ruta : registrarRutasService.propiedades['sarc.web.autenticarCiudadano.conformidad.url'], funcion : vm.presentarPdf, metodo : "POST"},
	              confirmar : {ruta : registrarRutasService.propiedades['sarc.web.autenticarCiudadano.actualizarSolicitud.url'], funcion : vm.manejarActualizacion, metodo : "POST"}
			   };

	//Datos a ser enviados al momento de cargar el modal 
	//autorizar : {ruta : registrarRutasService.propiedades['sarc.web.autenticarCiudadano.iniciarVerificacion.url'], funcion : vm.autorizar, metodo : "GET"},
	vm.modeloActual={id:$rootScope.objectSelected.numeroSolicitud, numTestigo : 1, nConformaciones : 0};
	llamadaAjax1($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modeloActual, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	//llamadaAjax1($http, $scope, vm, datosConsulta.ruta, vm.modeloActual, datosConsulta.funcion, datosConsulta.metodo);

	vm.siguiente = function(){
		if(vm.estado ===  'confirmar'){
			vm.funcion(vm.conforme1);
		}
		//guarda en la pila la vista actual
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modeloActual);
		vm.disabled = vm.vistas.lenght == 0;
		//incrementa el paso
		vm.paso++;
		//verifica si es conforme
		if(vm.modeloActual.conforme == false){
			vm.conforme1 = false;
		}
		if(vm.modeloActual.hasOwnProperty('conforme')){
			vm.conforme |= (vm.modeloActual.conforme === 'true');
			delete vm.modeloActual['conforme'];
			if(vm.conforme && vm.modeloActual.hasOwnProperty('observaciones')){
				delete vm.modeloActual['observaciones'];
			}
			if(!vm.conforme){
				vm.observaciones += "\n";
				vm.observaciones += vm.modeloActual.observaciones.trim();
				delete vm.modeloActual['observaciones'];
			}
		}
		switch (vm.estado) {
		case 'declaracion':
			var datosConsulta = vm.paso%2 === 0 ? vm.rutas['declaracion'] : vm.rutas['conforme'] ;
			vm.conformar = vm.paso%2 === 0 ? false : true;
			//llamadaAjax1($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modeloActual, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
			llamadaAjax1($http, $scope, vm, datosConsulta.ruta, vm.modeloActual, datosConsulta.funcion, datosConsulta.metodo);
			break;
		default:
			break;
		}
	}
	
	vm.regresar= function(){
		vm.contador--;
		vm.paso--;
		vm.vista = vm.vistas[vm.paso];
		vm.modeloActual = vm.modelos[vm.paso];
		vm.modelos.splice(vm.modelos.length-1,1);
		vm.vistas.splice(vm.vistas.length-1,1);
		vm.disabled = vm.vistas.length == 0;
		vm.disabled = vm.vistas.length == 0;

		vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);
		
		if(vm.activo < vm.titulos.length-1){
			vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
		}
		vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);		
	}
	
	vm.onKeyDown = function(event,validacion){
		var excepciones = validaciones[validacion].excepciones;
		for(var i = 0; i < excepciones.length; i++){
			if (event.key === excepciones[i])
				return;
		}
		var patron = new RegExp(validaciones[validacion].expReg);
		if (!patron.test(event.key)) {
			event.preventDefault();
			vm.errorTecla = true;
			return;
		}
		vm.errorTecla = false;
	}
	
	vm.onBlur = function(){
		vm.errorTecla = false;
	}
});	