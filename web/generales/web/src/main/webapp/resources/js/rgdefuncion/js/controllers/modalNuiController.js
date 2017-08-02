App.controller('ModalNuiController', function ($scope, $uibModalInstance,mensaje) {
	var vm=this;
	$scope.mensaje=mensaje;
	$scope.ok = function (){
		$uibModalInstance.close();
	}
});