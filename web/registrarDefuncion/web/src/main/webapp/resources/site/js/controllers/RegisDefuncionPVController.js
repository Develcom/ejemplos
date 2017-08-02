App.controller('controladorRDEFUPV',function($scope,$http,$rootScope,$uibModal){

	var vm = this;
	$scope.tituloWizard = "Registrar defunción";
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
	vm.pasos=0;
	vm.modelo.permiso ={};
	
	
	
	/**
	 * 
	 * @param $http: servicio ajax de angularjs
	 * @param $scope: scope del controlador
	 * @param vm : identificador del controlador
	 * @param url : ruta a la cual se realiza el llamado ajax 
	 * @param datos : enviados en la solicitud
	 * @param successCallback : función a ejecutar cuando la respuesta es satisfactoria
	 * @param metodo : POST, GET, PUT...
	 */
//	llamadaAjax = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
//	 $http({
//	  method: metodo,
//	  url: url,
//	  data: angular.toJson(datos)//JSON.stringify(datos)
//	 }).then(function successCallback(response) {
//	  accionSatisfactoria($scope, vm, response);
//	 }, function errorCallback(response) {
//	  console.log("error: "+response.data);
//	 });
//	}
	
	
	
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
	 * Maneja como se muestran las vistas.
	 */
	vm.presentarVista = function($scope, vm, response){
		vm.vista = response.data.vista;
		console.log("VISTA traida desde el java-----> "+vm.vista);
		
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.estado = 'conforme';

		
	}
	
	vm.presentarVistaConforme= function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		  if(vm.pasos >= 1){
			  vm.confirmarDatosForm = true;
		  }
	}
	
	 vm.funcion = function(parametro){
		 if(parametro == "conforme"){
			 vm.abrirModalConforme();
		 }else if (parametro == "noConforme"){
			 vm.abrirModalNoConforme();
		 }
	 }
	 
	 vm.abrirModalConforme = function(){
		 presentarModal($scope,$uibModal,vm.okConforme,'Verificación de oficio conforme',$rootScope.tituloWizard,'c');
	 }
	 vm.abrirModalNoConforme = function (){
		 presentarModal($scope,$uibModal,vm.noConforme,'Verificación de oficio no conforme',$rootScope.tituloWizard,'c');	
	 }
	 vm.okConforme = function(){
		 vm.estado = 'actualizarConforme';
		 llamadaAjax2($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	 }
	 vm.noConforme = function(){
		 vm.estado = 'actualizarNoConforme';
		 llamadaAjax2($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	 }
	 
	 vm.confirmarSalidaModulo = function(){
		 presentarModal($scope,$uibModal,vm.salirModulo,'¿Desea cancelar la solicitud?',$rootScope.tituloWizard,'s');
	 }
	 vm.salirModulo = function(){
		 $rootScope.cancelar1();
	 }
	 
	 vm.finalizar = function(){
		 vm.cancelar();
	 }

	/**
	 * Carga los datos iniciales del proceso
	 */
	vm.rutas = {
			inicializacion : {ruta: '/web-registrarDefuncion/iniciarTramiteRDEFU', funcion: vm.presentarVista, metodo : "POST"},
			conforme :       {ruta: '/web-registrarDefuncion/validarReporteConformeORE2', funcion: vm.presentarVistaConforme, metodo : "POST"},
			actualizarConforme :  {ruta: '/web-registrarDefuncion/actualizarEstado', funcion: vm.finalizar, metodo : "POST"},
			actualizarNoConforme :  {ruta: '/web-registrarDefuncion/actualizarEstado', funcion: vm.finalizar, metodo : "POST"},
			
			
			
			
	};
	
	

	//Datos a ser enviados al momento de cargar el modal  
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, pasos:0, estatus:$rootScope.objectSelected.codigoEstadoSolicitud};
	//llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	llamadaAjax2($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);

	vm.siguiente = function(){
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modelo);
		console.log("vm.modelo.permiso "+vm.confirmarDatosForm);
		  if(vm.confirmarDatosForm){
			  vm.funcion(vm.modelo.permiso);
		  return;
		  }
		vm.pasos++;
		llamadaAjax2($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	}

	vm.regresar= function(){
		  vm.vista = vm.vista[vm.vista.length-1];
		  vm.modelo = vm.modelos[vm.modelos.length-1];
		  vm.modelos.splice(vm.modelos.length-1,1);
		  vm.vistas.splice(vm.vistas.length-1,1);
	}

	vm.cancelar = function (){
		$rootScope.cancelar1();
	}
	
	 //cierra wizard desde el boton x
	vm.cancel = function(){
		vm.confirmarSalidaModulo();
	}

	$scope.mostrarCampoObservacion = function(){		
		$scope.campoObservacion = true;
	}
	
	$scope.ocultarCampoObservacion = function(){		
		$scope.campoObservacion = false;
	}


});
	