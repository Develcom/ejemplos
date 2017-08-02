App.controller('ValidarCertificadoInvalidoCtrl', function ($scope, $uibModalInstance,numCertificado,$rootScope,$uibModal) {
	var vm=this;
	$scope.numCertificado=numCertificado;
	console.log($scope.numCertificado);
	$scope.ok = function (parametro){
		
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-registrarDefuncion/pages/modal/verificarDatosEV14.html',
			controller: 'VerificarDatosEV14Ctrl',
			resolve: {
				
			}
		});

		modalInstance.result.then(function () {
			//$log.info('ok');
			//$parent.cont=true;
			//vm.cancelByEV14=true;
			 $uibModalInstance.close('ok');

		}, function () {
			//$log.info('Modal dismissed at: ' + new Date());
		});
		
		console.log('modal ValidarCertificadoInvalidoCtrl');
		//$uibModalInstance.close();
	}
	$scope.cancel = function (parametro) {
	    $uibModalInstance.dismiss('cancel');
	  };
});