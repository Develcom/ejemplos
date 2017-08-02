App.controller('ModalEcuController', function ($scope, $uibModalInstance,mensaje) {
	var vm=this;
	$scope.mensaje=mensaje;
	$scope.ok = function (){
		$uibModalInstance.close();
	}
});