App.controller('FooterController',function($scope,$http,footerFactory, $rootScope, $uibModal, $log,$location){
	var vm=this;
	vm.continuarEvaluando=footerFactory.getContinuarEvaluando();
	$scope.$watch(function(){return footerFactory.getAccion()},function(newValue, oldValue){
		console.log("Entro al watch del getAccion");
		console.log(newValue +" - " + oldValue);
	});

	$scope.$watch(function(){return footerFactory.getModelo()},function(newValue, oldValue){
		console.log("Entro al watch del modelos");
		console.log(newValue +" - " + oldValue);
		vm.model=footerFactory.getModelo();
	});

	open = function (size){
		//Carga el nombre del Proceso en el titulo del wizard
		$rootScope.tituloWizard='Registrar Defunción';
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: 'modal_certificado_EV14.html',
			controller: 'ModalInstanceCertificadoEV14Ctrl',
			size:size//,
			//windowClass: 'app-modal-window'
		});

		modalInstance.result.then(function () {
			$log.info('ok');
			$log.info('en el result');
			footerFactory.setContinuarEvaluando(false);
			vm.continuarEvaluando=false;
			$location.path('/actaCertificado');
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};

	vm.submit=function(){

		if(footerFactory.getContinuarEvaluando()){
			$http({
				method: 'POST',
				url: '/web-registrarDefuncion/consultarDrools',
				data:vm.model
			}).then(function successCallback(response) {
				console.log(response.data);
				if(response.data.evaluar){
					footerFactory.addFormulario(response.data.ruta);
				}else{
					console.log("Antes redirije a la vista del acta");
					open('lg');
				}

			}, function errorCallback(response) {
				console.log("error: "+response.data);
			});//Fin llamada ajax
		}else{

		}


	}

	vm.regresar=function(){
		footerFactory.removerFormulario();
	}

	vm.drool=function(value){
		if(value=="estaFallecidoEV14"){
			redirect
		}
	}

});