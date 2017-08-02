//alert conforme con datos verificados
App.controller('VerificarDatosEV14Ctrl', function($scope,$uibModalInstance,$http,$rootScope,$uibModal) {
	$scope.ok = function (parametro){
		

			
			$http({
				method: 'GET',
				url: '/web-permisoInhumacionCremacion/actualizarEstado/'+parametro,
				data: {numSolicitud:$rootScope.objectSelected.numeroSolicitud}
			}).then(function successCallback(response) {
				
				console.log(response.data);
				
				//abrirModalsolicitudCancelada();

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			
			
			abrirModalsolicitudCancelada();
		
		$uibModalInstance.close();
		
		
	}
	$scope.cancel = function (parametro) {
		
	    $uibModalInstance.dismiss('cancel');
	  };
	  
	  
	  abrirModalsolicitudCancelada = function(){
		  var modalInstance = $uibModal.open({
				backdrop: 'static',
				keyboard: false,
				animation: $scope.animationsEnabled,
				templateUrl: '/web-permisoInhumacionCremacion/pages/modal/modalSolicitudCancelada.html',
				controller: 'ModalSolicitudCanceladaCtrl',
				resolve: {
					
				}
			});

			modalInstance.result.then(function () {

				//$uibModalInstance.dismiss('ok');

			}, function () {
				//$log.info('Modal dismissed at: ' + new Date());
			});
			
			console.log('modal ValidarCertificadoInvalidoCtrl');
	  }
	  
})