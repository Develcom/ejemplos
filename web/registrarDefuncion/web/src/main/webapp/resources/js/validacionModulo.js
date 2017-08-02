/**
 * 
 */
(function(){
	'use-strict';
	var app=angular.module('validaciones',[]);
	
	app.controller('validadorDocumento',function($scope){
		$scope.nroDocumento=undefined;
		$scope.datosValidacion={} 
		$scope.$watch('ocupacion',function (newValue, oldValue) {
			console.log('cambio de ocupacion: '+newValue+' '+oldValue);
			console.log("altura: "+$(window).height());
			if (newValue !== oldValue){
				if(newValue=='CEDULA'){
				$scope.datosValidacion={
					placeHolder:'123456789',
					minima_longitud:6,
					maxima_longitud:11,
					patron:new RegExp('^[0-9]*$'),
				}
				console.log($scope.datosValidacion);
				return;
			}
			if(newValue=='NUI'){
				$scope.datosValidacion={
						placeHolder:'123456789',
						minima_longitud:9,
						maxima_longitud:9,
						patron:new RegExp('^[0-9]*$'),
					}
				return;
			}
			if(newValue=='PASAPORTE'){
				$scope.datosValidacion={
						placeHolder:'123456789',
						minima_longitud:6,
						maxima_longitud:11,
						patron:new RegExp('^[0-9]*$'),
				mensaje:'hola mi'
					}
			}
		}
		});
	});

	app.controller('validadorDocumento1',function($scope){
		$scope.nroDocumento=undefined;
//		$scope.datosValidacion={} 
				$scope.datosValidacion={
					placeHolder:'123456789',
					minima_longitud:6,
					maxima_longitud:11,
					patron:new RegExp('^[0-9]*$'),
				}
				console.log($scope.datosValidacion);
	});

	
	app.directive('inputConValidacion',function(){
		console.log('ejecutando la directiva');
		  return {
			    restrict: 'E',
		    scope: {
			      datosvalidacion: '=datosvalidacion'
			    },
			    templateUrl: '../pages/templates/inputTextDocumento.html'
			  };
	});	
})();
