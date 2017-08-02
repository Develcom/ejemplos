App.controller('controladorRDEFUNoConforme',function($scope,$http,$rootScope,$uibModal){

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
		vm.estado = 'OficioORE';
	}
	
	vm.verOficio = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		console.log('verificar datos');
		
	}
	
	vm.CorregirDatos = function(){
	presentarModal($scope,$uibModal,vm.okverif,'Por favor confirme los datos antes de enviar',$rootScope.tituloWizard,'a');
	}
	
	vm.okverif = function(){
		console.log('verificar datos');
	}
	
	vm.Confirmardatos = function(){
		presentarModal($scope,$uibModal,vm.actualizarEstadoPV,'\u00bfLos datos ingresados son correctos?',$rootScope.tituloWizard,'s');
	}
	
	vm.actualizarEstadoPV = function(){
		 vm.estado = 'pendienteVer';
		 llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
		
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
			OficioORE :      {ruta: '/web-registrarDefuncion/oficio', funcion: vm.verOficio, metodo : "POST"},
			pendienteVer : {ruta: '/web-registrarDefuncion/actualizarEstado', funcion: vm.finalizar, metodo : "POST"},

	};
	
	

	//Datos a ser enviados al momento de cargar el modal  
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, paso:0, estatus:$rootScope.objectSelected.codigoEstadoSolicitud};
	llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);

	vm.siguiente = function(){
		//guarda en la pila la vista actual
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modelo);
		vm.pasos++;
		if(vm.pasos == 2){
			vm.CorregirDatos();
			return;
		}if(vm.pasos == 3){
			vm.Confirmardatos();
			return;
		}
		llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	}

	vm.regresar= function(){
		vm.vista = vm.vista[vm.vista.length-1];
		vm.modelo = vm.modelos[vm.modelos.length-1];
		vm.modelos.splice(vm.modelos.length-1,1);
		vm.vista.splice(vm.vista.length-1,1);
	}

	 //cierra wizard desde el boton x
	vm.cancel = function(){
		vm.confirmarSalidaModulo();
	}

	vm.cancelar = function (){
		$rootScope.cancelar1();
	}
});