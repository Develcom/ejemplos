App.controller('validarActaController', function($scope,$uibModalInstance) {	
	var vm=this;
	$scope.ok = function (){
		$uibModalInstance.close();
	}
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
})