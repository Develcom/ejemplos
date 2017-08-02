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
	datos.ruta = 'https://localhost:8443' + url;
 $http({
  method: metodo,
  url: '/web-generales/direccionesControlador',
  data: angular.toJson(datos)
 }).then(function successCallback(response) {
  accionSatisfactoria($scope, vm, response);
 }, function errorCallback(response) {
  console.log("error: "+response.data);
 });
}

/**
 * 
 * @param mscope
 * @param $uibModal
 * @param okFuncion funcion asociada al boton ok
 * @param mensaje
 * @param titulo
 * @param tipo
 */
presentarModal = function(mscope,$uibModal, okFuncion, mensaje, titulo, tipo){
	var modalInstance = $uibModal.open({
		animation: true,
		templateUrl:  '/web-generales/pages/templates/imprimir/modalConfirmacion.html',
		controller: function($scope,$uibModalInstance){
			$scope.tipo = tipo;
			$scope.mensaje=mensaje;
			$scope.titulo = titulo;
		    $scope.cancel = function () {
		        $uibModalInstance.dismiss('cancel');
		    };
		    $scope.ok = function () {
		    	    $uibModalInstance.close(true);
		    	    okFuncion();
		    };
		},
		scope:mscope,
		//resolve: mresolve
	});
	
    modalInstance.result.then(function (resultado) {
        
      }, function () {
      });
}

/**
 * 
 * @param mscope
 * @param $uibModal
 * @param okFuncion funcion asociada al boton ok
 * @param mensaje
 * @param titulo
 * @param tipo
 */
presentarModal2 = function(mscope,$uibModal, okFuncion, cancelarFuncion, mensaje, titulo, tipo){
	var modalInstance = $uibModal.open({
		animation: true,
		templateUrl:  '/web-generales/pages/templates/imprimir/modalConfirmacion.html',
		controller: function($scope,$uibModalInstance){
			$scope.tipo = tipo;
			$scope.mensaje=mensaje;
			$scope.titulo = titulo;
		    $scope.cancel = function () {
		        $uibModalInstance.dismiss('cancel');
		    };
		    $scope.ok = function () {
		    	    $uibModalInstance.close(true);
		    	    okFuncion();
		    };
		    $scope.cancelar = function () {
	    	    $uibModalInstance.close(true);
	    	    cancelarFuncion();
	    };
		},
		scope:mscope,
		//resolve: mresolve
	});
	
    modalInstance.result.then(function (resultado) {
        
      }, function () {
      });
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
llamadaAjax1 = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
 $http({
  method: metodo,
  url: url,
  data:JSON.stringify(datos)
 }).then(function successCallback(response) {
  accionSatisfactoria($scope, vm, response);
 }, function errorCallback(response) {
  console.log("error: "+response.data);
 });
}

llamadaAjax3 = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
	 $http({
	  method: metodo,
	  url: url,
	  data:JSON.stringify(datos)
	 }).then(function successCallback(response) {
	  accionSatisfactoria($scope, vm, response);
	 }, function errorCallback(response) {
	  console.log("error: "+response.data);
	 });
	}




/**
 * 
 * @param mscope
 * @param $uibModal
 * @param okFuncion funcion asociada al boton ok
 * @param mensaje
 * @param titulo
 * @param tipo
 */
presentarModal1 = function(mscope,vm,$uibModal, okFuncion, mensaje, titulo, tipo){
	var modalInstance = $uibModal.open({
		animation: true,
		templateUrl:  '/web-autenticarCiudadano/pages/templates/imprimir/modalConfirmacion.html',
		controller: function($scope,$uibModalInstance){
			$scope.tipo = tipo;
			$scope.mensaje=mensaje;
			$scope.titulo = titulo;
		    $scope.cancel = function () {
		        $uibModalInstance.dismiss('cancel');
		    };
		    $scope.ok = function () {
		    	    $uibModalInstance.close(true);
		    	    okFuncion(vm);
		    };
		},
		scope:mscope,
	});
	
    modalInstance.result.then(function (resultado) {
        
      }, function () {
      });
}


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
