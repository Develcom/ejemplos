
App.controller('controladorRDEFUPPD',function($scope,$http,$rootScope,$uibModal){

	var vm = this;
	$scope.tituloWizard = "Registrar defunción";
	vm.pasos = 0;
	//$scope.validaciones = validaciones;
	vm.errorTecla = {};
	//Guarda en un array las vistas presentadas durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//representa los datos actualmente presentes en pantalla
	vm.modelo = {};
	vm.titulos = [];
	vm.activo = -1;
	vm.estado = 'inicializacion';
	vm.cerrarSolicitud = false;
	vm.cargarDocModal = false;

	
	
	
	
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
	llamadaAjax = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
	 $http({
	  method: metodo,
	  url: url,
	  data: angular.toJson(datos)//JSON.stringify(datos)
	 }).then(function successCallback(response) {
	  accionSatisfactoria($scope, vm, response);
	 }, function errorCallback(response) {
	  console.log("error: "+response.data);
	 });
	}
	
	
	
	
	
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
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.cargarDocModal = true;
		


	}

	vm.presentarVista2 = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		
	}


	vm.cargarDoc = function(){
		presentarModal($scope,$uibModal,vm.cancelarSolicitud,'¿Se cargó el documento de forma correcta?',$rootScope.tituloWizard,'c');
	}

//	vm.okCarga = function(){
//		vm.cancelarSolicitud();
//		//vm.mensajeFinalizar();
//
//	}
	
	 vm.mensajeFinalizar = function(){ 
		 llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/finalizar', vm.modelo, vm.finalizar, "POST");
	 }
	 
		vm.finalizar = function($scope, vm, response){
			vm.vista = response.data.vista;
			vm.modelo = response.data.modelo;
			vm.presentarTitulo(response.data.modelo.titulo);
			vm.estado = "finCarga";
		//	vm.solicitudCerrada();
		}

//	vm.respuestaActualizar = function(){
//        presentarModal($scope,$uibModal,vm.solicitudCerrada,'Solicitud cerrada correctamente, registro de defuncion generado',$rootScope.tituloWizard,'a');
//	}
	
	vm.solicitudCerrada = function(){
		presentarModal($scope,$uibModal,vm.finCancelar,'El libro diario fue actualizado exitosamente',$rootScope.tituloWizard,'a');

	}
	
	vm.cancelarSolicitud = function(){
		llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/cancelarSolicitud', vm.modelo, vm.presentarVista2, "POST");
		
	}
	
	vm.confirmarSalidaModulo = function(){
		presentarModal($scope,$uibModal,vm.finCancelar,'¿Desea cancelar la solicitud?',$rootScope.tituloWizard,'s');
	}

	 vm.siguienteImprimir = function(){
		 vm.solicitudCerrada();	 
		 llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/actualizarEstado', vm.modelo, vm.finalizar, "POST");
	 }
	
	 vm.finCancelar = function(){
		 llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/actualizarEstado', vm.modelo, vm.fin, "POST");
	 }
	 vm.fin = function(){
		 vm.cancelar2();
	 }
	 

	/**
	 * Carga los datos iniciales del proceso
	 */
	vm.rutas = {
			inicializacion : {ruta: '/web-registrarDefuncion/iniciarTramiteRDEFU', funcion: vm.presentarVista, metodo : "POST"},
	};

	//Datos a ser enviados al momento de cargar el modal  
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, pasos:0, estatus:$rootScope.objectSelected.codigoEstadoSolicitud, tramite: $rootScope.objectSelected.tramite.codigo};
	llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);


	vm.siguiente = function(){
		//guarda en la pila la vista actual
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modelo);
		console.log("vm.cargarDocModal---- " + vm.cargarDocModal);
		if(vm.cargarDocModal){
			vm.cargarDoc();
			return;
		}if (vm.estado=="finCarga"){
			vm.solicitudCerrada();
		}

		vm.paso++;

	}
	
	vm.siguienteSatisfactorio = function(){
		vm.solicitudCerrada();
	}


	function NombreSugeridoAgregarDocumento() {
		if(vm.modelo.uploadedfile !="") {
			$scope.banderaDisabled = true;
		}else{
			$scope.banderaDisabled = false;
		}
	} 

	//cierra wizard desde el boton x
	vm.cancel = function(){
		vm.confirmarSalidaModulo();
	}
	//cierra wizard desde el boton cancelar
	vm.cancelar = function (){
		vm.confirmarSalidaModulo();
	}
	
	vm.cancelar2 = function (){
		$rootScope.cancelar1();
	}
	

})

