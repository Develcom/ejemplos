
//modal verificar datos
App.controller('ValidarDatosFormCtrl', function($scope,$uibModalInstance) {
	$scope.ok = function(){
		$uibModalInstance.close();
		
	}
})