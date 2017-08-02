App.controller('controladorRDEFUPI',function($scope,$http,$rootScope,$uibModal){

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
	}
	 vm.confirmarImpresion = function(){
		 presentarModal($scope,$uibModal,vm.confirmar,'¿Se confirma la impresión?',$rootScope.tituloWizard,'c');
	 }
	 
	 vm.confirmarSalidaModulo = function(){
		 presentarModal($scope,$uibModal,vm.salirModulo,'¿Desea cancelar la solicitud?',$rootScope.tituloWizard,'s');
	 }

	 vm.confirmar = function(){
		 vm.estado = 'impresion';
		 llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	 } 
	 vm.siguienteSatisfactorio = function(){
		 vm.estado = 'fin';
		 llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	 }
	 vm.siguienteImprimir = function(){
		 vm.estado = 'fin';
		 llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	 }

	 vm.salirModulo = function(){
		 $rootScope.cancelar1();
	 }
	 
	 vm.finCancelar = function(){
		 $rootScope.imprimirRDEFU = false;
		 vm.cancelar();
	 }

	/**
	 * Carga los datos iniciales del proceso
	 */
	vm.rutas = {
			inicializacion : {ruta: '/web-registrarDefuncion/imprimirORE', funcion: vm.presentarVista, metodo : "POST"},
			impresion : {ruta: '/web-registrarDefuncion/finalizar', funcion: vm.presentarVista, metodo : "POST"},
			//cancelarSolcicitud : {ruta: '/web-registrarDefuncion/cancelarSolicitud', funcion: vm.presentarVista, metodo : "POST"},
			fin : {ruta: '/web-registrarDefuncion/actualizarEstado', funcion: vm.finCancelar, metodo : "POST"},
	};
	
	

	//Datos a ser enviados al momento de cargar el modal  
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, pasos:0, estatus:$rootScope.objectSelected.codigoEstadoSolicitud};
	llamadaAjax2($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);

	vm.siguiente = function(){
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modelo);
		console.log("vm.modelo.permiso "+vm.confirmarDatosForm);
		  if(vm.siguiente){
			  vm.confirmarImpresion();
		  return;
		  }
			if(vm.impreso){
				console.log("si ta fina la impresion");
				vm.confirmarImpresion();
				 return;
			 }
		vm.pasos++;
		llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	}

	vm.regresar= function(){
		  vm.vista = vm.vista[vm.vista.length-1];
		  vm.modelo = vm.modelos[vm.modelos.length-1];
		  vm.modelos.splice(vm.modelos.length-1,1);
		  vm.vistas.splice(vm.vistas.length-1,1);
	}

	 //cierra wizard desde el boton x
	vm.cancel = function(){
		vm.confirmarSalidaModulo();
	}

	vm.cancelar = function (){
		$rootScope.cancelar1();
	}
	
	vm.imprimir = function() {
		vm.windowPrintBrowser();
		presentarModal($scope, $uibModal, vm.okF,'¿La impresión es satisfactoria?',$rootScope.tituloWizard, 's');
	}
    vm.okF = function(){
		console.log("entroooo");
		vm.impreso = true;
	}

	
    vm.windowPrintBrowser = function () {
        document.getElementById("plugin").focus();
        document.getElementById("plugin").contentWindow.print();
        
    }
    
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
    llamadaAjax2 = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
    	datos.ruta = 'http://localhost:8080' + url;
     $http({
      method: metodo,
      url: '/web-generales/direccionesControlador',
      data: angular.toJson(datos)//JSON.stringify(datos)
     }).then(function successCallback(response) {
      accionSatisfactoria($scope, vm, response);
     }, function errorCallback(response) {
      console.log("error: "+response.data);
     });
    }

});
	