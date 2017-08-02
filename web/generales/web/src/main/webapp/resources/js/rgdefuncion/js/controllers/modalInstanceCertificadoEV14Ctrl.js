
App.controller('ModalInstanceCertificadoEV14Ctrl', function ($rootScope, $scope, $uibModalInstance,$http) {
	var vm=this;
	$scope.ok = function (){
		$uibModalInstance.close();
	}
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
});