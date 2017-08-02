
//controller modal desea enviar datos
App.controller('EnviarDatosFormCtrl', function($scope,$uibModalInstance,$http,$rootScope) {
	
	$scope.ok = function (parametro1){
		
		$http({
			method: 'POST',
			url: '/web-permisoInhumacionCremacion/actualizarEstado',
			data: {numSolicitud:$rootScope.objectSelected.numeroSolicitud, parametro:parametro1}
		}).then(function successCallback(response) {
			
			console.log(response.data);
			
			$rootScope.cancelar1();//cerrar modal
			//$scope.certificado = certificadoDefuncion.data;
			
//			if(!$scope.certificado){
//				console.log('niooo');
//				abrirVentanaCertificadoinvalido();
//			
//			}

		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
		
		
	
	
	$uibModalInstance.close();
		
	}
	$scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
	  
	//  $rootScope.cerrarModal();
	
})