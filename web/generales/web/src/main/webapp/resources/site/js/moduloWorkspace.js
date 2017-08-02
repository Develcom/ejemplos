App.controller('logoutController', function ($scope, $http, $log, $location, $state, $stateParams, $window, $rootScope, $timeout, $interval, $uibModal, registrarRutasService) {
	var datos = {};
	var metodo = "POST";
	var modulo = registrarRutasService.propiedades['sarc.web.atc.logout.url'];
});


/**
 * Controlador para de la bandeja de entrada en el listado de solicitudfes
 */
App.controller('workspace', function ($scope, $http, $log, $location, $state, $stateParams, $window, $rootScope, $timeout, $interval, $uibModal, registrarRutasService) {
	//
	$scope.panel = 1;
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.maxSize = 5;
    $scope.totalItems = 10;
    $scope.BlnLoad = 0;
    $scope.dataLoaded = "0%";
    movementScale("glyphicon.glyphicon-comment.glyphSizeMin","span.");
	$scope.tramitesCol = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())*1.6) + 'px;';
	var datos = {};
	var metodo = "POST";
	var modulo = "/web-generales/listarSolicitudes";
	var performFunction = functionListadoPaquetes;
	$scope.dataLoaded = "10%";
	genericAjax($http, $state, $stateParams, $scope, metodo, modulo, datos, performFunction);
	
	$scope.$watch("listSolicitudes", function(newValue, oldValue){
		if(newValue != null){
			$timeout(function() {
				$scope.BlnLoad = 1;
			}, 1000);
		}
	});
	$scope.$watch("itemsPerPage", function(newValue, oldValue){
	    $rootScope.itemsPerPage = newValue;
	});
	$scope.$watchCollection("objectSelected", function(newValue, oldValue){
	    $rootScope.objectSelected = newValue;
	});
	$scope.cambioItemPerPage = function(newItemPerPage, e){
		$timeout(function() {
			var h = $("#table_id").css("height").replace("px","");
			var total = Number(h) + 300;
			$scope.tramitesCol = total + "px";
			
		}, 1000);
	}
	$scope.selectNumeroSolicitud=function(item, e){
		if($scope.solicitudSelected == item.id){
			$scope.solicitudSelected = undefined;
		}else{
			$scope.objectSelected = item;
			$scope.solicitudSelected = item.id;
		}
		
	}
	
	
	/**
	 * Inicia el modal del wizard para los trámites
	 */
	$scope.openModal = function (size, item) {
		$rootScope.objectSelected = item;
		$rootScope.tituloWizard=item.tramite.nombre;
		$scope.wizardHeight = $window.outerHeight;
		var modalInstance;
		
		
		$http({
			method: 'POST',
			url: registrarRutasService.propiedades['sarc.web.generales.iniciarModuloSegunEstado.url'],
			data:{estado:$rootScope.objectSelected.codigoEstadoSolicitud, tramite : $rootScope.objectSelected.tramite.codigo, numero : $rootScope.objectSelected.numeroSolicitud}
		}).then(function successCallback(response) {
			if($rootScope.objectSelected.tramite.codigo === 'RNACI' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PI'){
				$rootScope.imprimir = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'EPDIC' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PI'){
				$rootScope.imprimirEPDIC = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'RADOP' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PI'){
				$rootScope.imprimirRADOP = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'RADOP' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PPI'){
				$rootScope.imprimirRADOPACTA = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'RADOP' && $rootScope.objectSelected.codigoEstadoSolicitud === 'IDJ'){
				$rootScope.imprimirRADOPDJ = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'RRFIL' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PI'){
				$rootScope.imprimirRRFIL = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'RRFIL' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PPIO'){
				$rootScope.imprimirRRFIL_PPIO = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'RDEFU' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PI'){
				$rootScope.imprimirRDEFU = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'RDEFU' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PPI'){
				$rootScope.imprimir_acta = true;
				return;
			}else if($rootScope.objectSelected.tramite.codigo === 'RRFIL' && $rootScope.objectSelected.codigoEstadoSolicitud === 'PPI'){
				$rootScope.imprimirRRFIL_PPI = true;
				return;
			}
				console.log("Respuesta: "+response.data);
				modalInstance = $uibModal.open({
				  backdrop: 'static',
				  keyboard: false,
				  size: size,
				  animation: true,
				  template: response.data[0],
				  windowClass: 'app-modal-window'
				});
		}, function errorCallback(response) {
			console.log("error");
		});
		
		$rootScope.cancelar1 = function (){
			modalInstance.dismiss('cancel');
		}
		
		
	}	  
});

App.controller('errorCreacionControlador', function ($scope, $http, $location, $stateParams, $state) {
	$scope.error_message = $stateParams.errorMsg;
	$scope.finalizar = function(){
		$state.transitionTo("atencionComunitaria", $stateParams);
	}
});

App.controller('errorECUCreacionControlador', function ($scope, $http, $location, $stateParams, $state) {
	$scope.error_message = $stateParams.errorMsg;
	$scope.finalizar = function(){
		$state.transitionTo("gestionECU", $stateParams);
	}
});