App.controller('ModalCertificadoEV14Ctrl', function ($rootScope, $scope, $uibModal, $log,$location) {
	$scope.animationsEnabled = true;
	$scope.open = function (size) {
		//Carga el nombre del Proceso en el titulo del wizard
		$rootScope.tituloWizard='Registrar Defunción';
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: 'modal_certificado_EV14.html',
			controller: 'ModalInstanceCertificadoEV14Ctrl',
			size:size//,
			//windowClass: 'app-modal-window'
		});

		modalInstance.result.then(function () {
			$log.info('ok');
			$log.info('en el result');	
			$location.path('/actaCertificado');
//				$location.path() == '/actaCertificado'
//			redirect:"/actaCertificado";
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};
});