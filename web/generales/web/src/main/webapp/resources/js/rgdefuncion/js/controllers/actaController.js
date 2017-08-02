App.controller('ActaController',function($scope, $routeParams, $uibModal, $log){
	vm = this;
	vm.mensaje="Usted Desea imprimir el oficio?";//mensaje a enviar al modal

	vm.submit = function(){
		console.log("submit");
		open('md');
	};

	vm.cancelar = function(){
		console.log("cancelar");
	};

	vm.regresar = function(){
		console.log("atras");
	};

	open = function (size) {

		var modalInstance = $uibModal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'modal/modal_confir_impr_EV14.html',
			controller: 'ModalInstancePrintEV14Ctrl',
			size: size,
			backdrop: 'static',
			keyboard: false,
			resolve: {
				mensaje: function () {
					return vm.mensaje;
				}
			}
		});

		modalInstance.result.then(function (selectedItem) {
			$scope.selected = selectedItem;
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};



});