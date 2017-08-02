/**
 * 
 */
App.controller('SelectServicioControlador', function($scope,$http) {
	//$scope.opciones = [{name:'nombre1',id:'1'},{name:'nombre2',id:'2'},{name:'nombre3',id:'3'}];
	  $scope.opciones= [
	                    {id: '1', name: 'Option A'},
	                    {id: '2', name: 'Option B'},
	                    {id: '3', name: 'Option C'}
	                  ];
	  $scope.naomi = { name: 'Naomi', address: '1600 Amphitheatre' };
	  $scope.igor = { name: 'Igor', address: '123 Somewhere' };
//	$http({
//		method: 'POST',
//		url: '/web-autenticarCiudadano/iniciarTramite',
//		data:{id:$rootScope.objectSelected.numeroSolicitud}
//	}).then(function successCallback(response) {
//	}, function errorCallback(response) {
//	});
});
App.directive('servicioSelect', function(){
  return {
    restrict: 'E',
    scope: {
//      informacion: '=info',
      opciones: '=nopciones'
    },
//    transclude : true,
    controller: ['$scope', function($scope) {
    	console.log('controlador de la directiva');
  	  var opciones = $scope.opciones= [
	                    {id: '1', name: 'Option A'},
	                    {id: '2', name: 'Option B'},
	                    {id: '3', name: 'Option C'}
	                  ];
  	  var etiqueta = $scope.etiqueta = 'hola';
  	  this.cargarSelect = function(opcion){
  		  //console.log('seleccionado: '+opcion.name);
  	  }
    }],
    link: function(scope, element, attrs, tabsCtrl) {
        //tabsCtrl.cargarSelect(attrs.nopciones[0]);
    },
    template : '<label>{{etiqueta}}</label><select ng-options="option.name for option in opciones track by option.id" ng-model="data.selectedOption"></select>{{opciones[0].name}}'
//        template : '<label>{{informacion.name}}</label><select ng-options="option.name for option in mopciones track by option.id" ng-model="data.selectedOption"></select>{{mopciones[0].name}}'
  };
 });

App.directive('miSelect',function(){
	return {
	    restrict: 'E',
	    //transclude: true,
	    scope: {},
	    controller: ['$scope', function($scope) {
	    	console.log('controlador de la directiva');
	    }],
	    template: '<b>hola</b>'

	};
});