//alert conforme con datos verificados
App.controller('ModalSolicitudCanceladaCtrl', function($scope,$uibModalInstance,$uibModal,$http,$rootScope) {
	$scope.aceptar = function (){
		
		$uibModalInstance.dismiss('aceptar');
	}
		
	  
})