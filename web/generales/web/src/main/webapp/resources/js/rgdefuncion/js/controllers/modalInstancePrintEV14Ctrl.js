App.controller('ModalInstancePrintEV14Ctrl', function ($scope, $uibModalInstance,mensaje) {
	var vm=this;
	$scope.mensaje=mensaje;
	console.log($scope.mensaje);
	$scope.ok = function (){
		console.log('modal sobre modal');
		$uibModalInstance.close();
	}
	$scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
});