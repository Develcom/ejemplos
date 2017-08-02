//var App = angular.module('app', ['ngRoute','formly', 'ngMessages', 'formlyBootstrap','ngSanitize','ui.bootstrap','sw.calendar','ngValid']);
//App.config(function($routeProvider) {
//	console.log("Route");
//	$routeProvider
//	.when("/actaCertificado",{
//		templateUrl:"certificado.html",
//		controller: "actaController"
//	})
//	.when("/actaImprimir",{
//		templateUrl:"templates/vista_resumen_acta.html",
//		controller: "printActaController"
//	})
//	.when("/",{
//		templateUrl: "wizard.html",
//		controller: "ModalDemoCtrl"
//        });
////	.when("/",{
////		templateUrl: "wizard.html",
////		controller: "testController"
////	});
//	;
//});
//
//App.controller('fechaHora', function($scope, $interval){
//	$interval(function(){
//		$scope.fecha=new Date;
//	},1000);
//});
//
//App.controller('printActaController', function($scope) {
//	$scope.data="data del scope";
//});
//
//App.controller('header', function($scope) {
//	$scope.myVar = false;
//	$scope.toggle = function() {
//		$scope.myVar = !$scope.myVar;
//	};
//});
//
////Please note that $modalInstance represents a modal window (instance) dependency.
////It is not the same as the $uibModal service used above.
////pasar el rootScope para identificar el módulo y hacer las solicitudes a drools
//
//App.directive('carta', function ($compile) {
//	return {
//		//scope de la directiva
//		//ele: elemento donde es declarada la directiva
//		//attrs: atributos del elemento, compartidos por todas las directivas enlazadas
//		link: function (scope, ele, attrs) {
//			scope.$watch(attrs.carta, function(html) {
//				ele.html(html);
//				$compile(ele.contents())(scope);
//			});
//		}
//	};
//});
//
//App.controller('pasosController',function($scope,pasosFactory){
//	var vm=this;
//	vm.pasos = pasosFactory.getPasos();
//});
//
////factory que permite mantener los pasos en el que se encuentra el usuario
////para el registro de defuncion
//App.factory('pasosFactory',function(){
//	var pasos=['Solicitud de Registro de Defuncion'];
//	var funciones={
//			addPasos:function(paso){
//				pasos.push(paso);
//			},
//			getPasos:function(){
//				return pasos;
//			}
//	}
//	return funciones;
//});
//
