//alert conforme con datos verificados
App.controller('ConformeDatosPermisoCtrl', function($scope,$uibModalInstance,$http,$rootScope) {
	$scope.confirmar = function (parametro1){
		console.log("****************parametro conforme**** "+parametro1);

			
			$http({
				method: 'POST',
				url: '/web-permisoInhumacionCremacion/actualizarEstado',
				data: {numSolicitud:$rootScope.objectSelected.numeroSolicitud, parametro:parametro1}
			}).then(function successCallback(response) {
				
				console.log(response.data);
				$rootScope.cancelar1();

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			
			
		
		
		$uibModalInstance.close();
		
		
	}
	$scope.cancelar = function (parametro) {
		
	    $uibModalInstance.dismiss('cancel');
	  };
	
})