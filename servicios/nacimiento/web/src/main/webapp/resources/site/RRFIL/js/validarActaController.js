App.controller('validarActaCtrl', function($scope,$uibModalInstance) {
//	$scope.ok = function(){
//		$uibModalInstance.close();
//		
//	}
	
	var vm=this;
	$scope.ok = function (){
		$uibModalInstance.close();
	}
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
})