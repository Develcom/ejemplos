
 
 App.directive('campoTexto', ['validaciones',function(validaciones){
    return {
      restrict: 'AE',
	  require: 'ngModel',
      //transclude: true,
      scope: {
        ruta : '@',
		validacion : '@',
		regExp : '@',
		modelo : '=ngModel'
		
      },
      controller: ['$scope', '$attrs', '$http', function($scope, $attrs, $http, $element, ngModel) {
        var vm = this;
		vm.validacion = validaciones[$scope.validacion];
		vm.regExp = validaciones[$scope.validacion.expReg];
        var etiqueta = $scope.etiqueta = 'Nombre';
      }],
      controllerAs : 'vm',
	  template : '<div class="col-sm-6" ng-class=""has-error": modelo == undefined , "has-success":  modelo != undefined">'
	  +'<div class="input-group col-sm-6">'
	  +'<label>gfdgf</label>'
	  +'</div>'
	  +'<div class="input-group row-sm-6">'
	  +'<input type="text" ng-model = "modelo" ng-maxlength="vm.validacion.maximo" ng-minlength="vm.validacion.minimo" class="input-sm form-control" >'
	  +'<span class="input-group-addon" id="basic-addon1">'
	  +'<span class="glyphicon glyphicon-asterisk" ng-hide="modelo != undefined">'
	  +'</span>'
	  +'<span class="glyphicon glyphicon-ok" ng-show="modelo != undefined">'
	  +'</span>'
	  +'</span>'
	  +'</div>'
	  +'</div>'
    };
}]);


//var constantes = angular.module('constantes', [])

App.constant('validaciones',{'cedula':{maximo : '10', minimo : '6'},
'pasaporte':{maximo : '10' , minimo : '6' },'expresionRegulada':{expReg : '^[a-z0-9]'}});
	

