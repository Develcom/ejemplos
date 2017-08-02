App.controller('ModalInvalidEV14Controller', function ($scope, $uibModalInstance,numCertificado) {
	var vm=this;
	$scope.numCertificado=numCertificado;
	console.log($scope.numCertificado);
	$scope.ok = function (){
		console.log('modal ModalInvalidEV14Controller');
		$uibModalInstance.close();
	}
	$scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
});