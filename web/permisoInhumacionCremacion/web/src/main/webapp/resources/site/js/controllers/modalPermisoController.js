//var App = angular.module('app', ['ngRoute','ui.bootstrap','sw.calendar'])
//.config(function($routeProvider){
//	$routeProvider
//	.when("/",{
//		templateUrl: "prueba_home.html",
//		controller: "modalController"
//	})
//	.when("/",{
//		templateUrl: "vista_permiso.html",
//		controller: "modalVistaPermisoCtrl"
//	})
//	.when("/",{
//		templateUrl: "imprimir_permiso.html",
//		controller: "imprimirPermisoCtrl"
//	});
//	
//})

App.controller('modalController', function($scope,$uibModal,$rootScope) {
	
	console.log("********ENTRANDO AL CONTROLADOR***********");
	var modalInstance = $uibModal.open({
		templateUrl: 'plantilla.html',
		controller: 'modalInstanceCtrl',
		windowClass: 'app-modal-window',
		resolve: {
	       //enviar parametros a la vista
	      }
	});
	$rootScope.cerrarModal = function (){
		modalInstance.dismiss('cancel');
	}
})

//llamada a

App.controller('modalVistaPermisoCtrl', function($scope) {
	$scope.text= 'modalVistaPermisoCtrl';
})

App.controller('imprimirPermisoCtrl', function($scope) {
	$scope.text = "imprimirPermisoCtrl";
})

//App.controller('modalController', function($scope,$uibModal){
//	
//
//		//backdrop: 'static',
//		//keyboard: false,
//		//animation: $scope.animationsEnabled,
//		templateUrl: 'plantilla.html',
//		controller: 'modalInstanceCtrl',
//		windowClass: 'app-modal-window',
//		resolve: {
//	       //enviar parametros a la vista
//	      }
//	});
//	
//})
//
App.controller('modalInstanceCtrl', function($scope){
	
	$scope.text ='chao';
	
})
//	var modalInstance = $uibModal.open({
//
